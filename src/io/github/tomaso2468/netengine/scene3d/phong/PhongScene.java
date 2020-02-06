package io.github.tomaso2468.netengine.scene3d.phong;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.joml.Matrix4f;
import org.joml.Vector3f;

import io.github.tomaso2468.netengine.Color;
import io.github.tomaso2468.netengine.Game;
import io.github.tomaso2468.netengine.render.BlendFactor;
import io.github.tomaso2468.netengine.render.GBuffer;
import io.github.tomaso2468.netengine.render.RenderException;
import io.github.tomaso2468.netengine.render.Renderer;
import io.github.tomaso2468.netengine.render.Shader;
import io.github.tomaso2468.netengine.scene3d.Material;
import io.github.tomaso2468.netengine.scene3d.Scene3D;
import io.github.tomaso2468.netengine.scene3d.SceneParams;
import io.github.tomaso2468.netengine.scene3d.ShaderLight;
import io.github.tomaso2468.netengine.scene3d.ShaderLoader;
import io.github.tomaso2468.netengine.scene3d.TransparentObject;

public class PhongScene extends Scene3D {
	public static final int MAX_POINT_LIGHTS = 16;
	public static final int MAX_DIRECTIONAL_LIGHTS = 8;
	public static final int MAX_SPOT_LIGHTS = 8;
	
	private final List<PhongPointLight> pointLights = new ArrayList<>();
	private final List<PhongDirectionalLight> directionalLights = new ArrayList<>();
	private final List<PhongSpotLight> spotLights = new ArrayList<>();
	
	private float gamma = 2.2f;
	private boolean srgbTextures = true;
	private boolean srgbOutput = true;
	private boolean shadow = false;
	private int shadowResolution = 1024;
	private float shadowDistance = 10f;
	private ShaderLight currentLight;
	
	protected GBuffer gbuffer;
	protected Shader shader;
	
	public void draw(Game game, Renderer renderer) {
		if (gbuffer != null && (gbuffer.getWidth() != renderer.getWidth() || gbuffer.getHeight() != renderer.getHeight())) {
			gbuffer.dispose();
			
			gbuffer = null;
		}
		if (gbuffer == null) {
			int count = 0;
			count += 1; // position
			count += 1; // normal
			count += 1; // albedo
			count += 1; // specular
			count += 1; // screen space
			
			gbuffer = renderer.createGBuffer(renderer.getWidth(), renderer.getHeight(), count);
			gbuffer.unbind();
		}
		if (shader == null) {
			try {
				shader = ShaderLoader.createDefaultSimpleShader(renderer);
			} catch (IOException e) {
				throw new RenderException(e);
			}
			shader.startUse();
			shader.setUniformTextureUnitO("position", 0);
			shader.setUniformTextureUnitO("normal", 1);
			shader.setUniformTextureUnitO("diffuse", 2);
			shader.setUniformTextureUnitO("specular", 3);
			shader.setUniformTextureUnitO("screenSpacePosition", 4);
			shader.endUse();
		}
		
		gbuffer.bind();
		renderer.clearScreen(Color.black);
		
		SceneParams params = new SceneParams() {
			@Override
			public void applySceneTransform(Renderer renderer, Material material) {
				configureMaterial(renderer, material);
			}
		};
		
		params.cull = true;
		renderer.setDepthTest(true);
		renderer.setBlend(BlendFactor.DISABLE, BlendFactor.DISABLE);
		renderer.setFaceCull(true);
		
		draw(game, renderer, params, new Matrix4f());
		
		if (params.state != null) {
			params.state.leaveState();
			params.state = null;
		}
		if (params.material != null) {
			params.material.unbind();
			params.material = null;
		}
		
		gbuffer.unbind();
		
		renderer.setDepthTest(true);
		renderer.setBlend(BlendFactor.SRC_ALPHA, BlendFactor.ONE_MINUS_SRC_ALPHA);
		renderer.setFaceCull(false);

		shader.startUse();
		configureShader(renderer, shader);
		gbuffer.bind(0);
		gbuffer.draw(renderer);
		gbuffer.unbind(0);
		shader.endUse();
		
		gbuffer.copyDepthToScreen();
		
		Vector3f position = getCamera().getPosition();
		Collections.sort(params.transparentObjects, (o1, o2) -> {
			float d1 = position.distanceSquared(o1.transform.transformPosition(new Vector3f(o1.object.getPosition())));
			float d2 = position.distanceSquared(o2.transform.transformPosition(new Vector3f(o2.object.getPosition())));
			return Float.compare(d1, d2);
		});
		
		for (TransparentObject o : params.transparentObjects) {
			gbuffer.bind();
			renderer.clearScreen(new Color(0, 0, 0, 0));
			
			renderer.setDepthTest(true);
			renderer.setBlend(BlendFactor.DISABLE, BlendFactor.DISABLE);
			renderer.setFaceCull(true);
			params.cull = true;
			
			o.object.draw(game, renderer, params, o.transform);
			
			if (params.state != null) {
				params.state.leaveState();
				params.state = null;
			}
			if (params.material != null) {
				params.material.unbind();
				params.material = null;
			}
			
			gbuffer.unbind();
			
			renderer.setDepthTest(true);
			renderer.setBlend(BlendFactor.SRC_ALPHA, BlendFactor.ONE_MINUS_SRC_ALPHA);
			renderer.setFaceCull(false);
			
			shader.startUse();
			configureShader(renderer, shader);
			gbuffer.bind(0);
			gbuffer.draw(renderer);
			gbuffer.unbind(0);
			shader.endUse();
		}
		
		renderer.setDepthTest(false);
		renderer.setBlend(BlendFactor.SRC_ALPHA, BlendFactor.ONE_MINUS_SRC_ALPHA);
		renderer.setFaceCull(false);
	}
	
	protected void configureShader(Renderer renderer, Shader shader) {
		for (int i = 0; i < pointLights.size(); i++) {
			if (i >= MAX_POINT_LIGHTS - 1) {
				break;
			}
			
			pointLights.get(i).load(shader, i);
		}
		shader.setUniform1i("pointLightCount", Math.min(pointLights.size(), MAX_POINT_LIGHTS));
		
		for (int i = 0; i < spotLights.size(); i++) {
			if (i >= MAX_SPOT_LIGHTS - 1) {
				break;
			}
			
			spotLights.get(i).load(shader, i);
		}
		shader.setUniform1i("spotLightCount", Math.min(spotLights.size(), MAX_SPOT_LIGHTS));
		
		for (int i = 0; i < directionalLights.size(); i++) {
			if (i >= MAX_DIRECTIONAL_LIGHTS - 1) {
				break;
			}
			
			directionalLights.get(i).load(shader, i);
		}
		shader.setUniform1i("directionalLightCount", Math.min(directionalLights.size(), MAX_DIRECTIONAL_LIGHTS));
		
		shader.setUniform3f("viewPos", getCamera().getPosition());
		
		shader.setUniform1f("gamma", gamma);
		shader.setUniform1b("srgbOutput", srgbOutput);
	}

	@Override
	protected void configureMaterial(Renderer renderer, Material material) {
		if (shadow) {
			Matrix4f projection = new Matrix4f().setOrtho(-shadowDistance, shadowDistance, -shadowDistance, shadowDistance, 0.1f, 7.5f);
			Matrix4f view = new Matrix4f().lookAt(currentLight.getPosition(), new Vector3f(0.1f, 0, 0), new Vector3f(0.0f, 1.0f, 0.0f));
			
			material.setSceneTransform(projection, view);
		} else {
			Matrix4f projection = getCamera().getProjection(renderer);
			Matrix4f view = getCamera().getView(renderer);
			
			material.setSceneTransform(projection, view);
		}
		
		material.getShader().setUniform1f("gamma", gamma);
		material.getShader().setUniform1b("srgbTextures", srgbTextures);
	}
	
	public boolean add(PhongPointLight e) {
		return pointLights.add(e);
	}
	
	public boolean remove(PhongPointLight e) {
		return pointLights.remove(e);
	}

	public boolean add(PhongDirectionalLight e) {
		return directionalLights.add(e);
	}
	
	public boolean remove(PhongDirectionalLight e) {
		return directionalLights.remove(e);
	}

	public boolean add(PhongSpotLight e) {
		return spotLights.add(e);
	}
	
	public boolean remove(PhongSpotLight e) {
		return spotLights.remove(e);
	}
	
	public void setGamma(float gamma) {
		this.gamma = gamma;
	}
	
	public float getGamma() {
		return gamma;
	}
	
	public void setSRGBTextures(boolean srgbTextures) {
		this.srgbTextures = srgbTextures;
	}
	
	public boolean isSRGBTextures() {
		return srgbTextures;
	}
	
	public void setSRGBOutput(boolean srgbOutput) {
		this.srgbOutput = srgbOutput;
	}
	
	public boolean isSRGBOutput() {
		return srgbOutput;
	}

}
