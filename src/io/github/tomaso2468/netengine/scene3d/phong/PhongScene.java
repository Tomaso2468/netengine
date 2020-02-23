package io.github.tomaso2468.netengine.scene3d.phong;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.joml.Matrix4f;
import org.joml.Vector3f;

import io.github.tomaso2468.netengine.Color;
import io.github.tomaso2468.netengine.Game;
import io.github.tomaso2468.netengine.input.Key;
import io.github.tomaso2468.netengine.log.Log;
import io.github.tomaso2468.netengine.render.BlendFactor;
import io.github.tomaso2468.netengine.render.GBuffer;
import io.github.tomaso2468.netengine.render.RenderException;
import io.github.tomaso2468.netengine.render.Renderer;
import io.github.tomaso2468.netengine.render.Shader;
import io.github.tomaso2468.netengine.render.ShaderLoader;
import io.github.tomaso2468.netengine.render.ShadowBuffer;
import io.github.tomaso2468.netengine.scene3d.Scene3D;
import io.github.tomaso2468.netengine.scene3d.SceneParams;
import io.github.tomaso2468.netengine.scene3d.ShaderLight;
import io.github.tomaso2468.netengine.scene3d.TransparentObject3D;
import io.github.tomaso2468.netengine.scene3d.material.Material;

public class PhongScene extends Scene3D {
	public static final int MAX_POINT_LIGHTS = 16;
	public static final int MAX_DIRECTIONAL_LIGHTS = 8;
	public static final int MAX_SPOT_LIGHTS = 8;
	public static final int GBUFFER_SIZE = 5;
	
	private final List<PhongPointLight> pointLights = new ArrayList<>();
	private final List<PhongDirectionalLight> directionalLights = new ArrayList<>(MAX_DIRECTIONAL_LIGHTS);
	private final List<PhongSpotLight> spotLights = new ArrayList<>(MAX_SPOT_LIGHTS);
	private final List<ShadowBuffer> shadowBuffers = new ArrayList<>(MAX_SPOT_LIGHTS + MAX_DIRECTIONAL_LIGHTS);
	
	private float gamma = 2.2f;
	private boolean srgbTextures = true;
	private boolean srgbOutput = true;
	private boolean shadow = false;
	private int shadowResolution = 1024;
	private float shadowDistance = 5f;
	private ShaderLight currentLight;
	
	protected GBuffer gbuffer;
	protected Shader shader;
	protected Shader debugShader;
	
	public void renderShadow(Game game, Renderer renderer) {
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
		
		drawDepth(game, renderer, params, new Matrix4f());
		
		if (params.state != null) {
			params.state.leaveState();
			params.state = null;
		}
		if (params.material != null) {
			params.material.unbind();
			params.material = null;
		}
	}
	
	public void draw(Game game, Renderer renderer) {
		while (shadowBuffers.size() < spotLights.size() + directionalLights.size()) {
			Log.debug("Generating shadow buffer");
			ShadowBuffer buffer = renderer.createShadowBuffer(shadowResolution, shadowResolution);
			buffer.unbind();
			
			shadowBuffers.add(buffer);
		}
		if (gbuffer != null && (gbuffer.getWidth() != renderer.getWidth() || gbuffer.getHeight() != renderer.getHeight())) {
			gbuffer.dispose();
			
			gbuffer = null;
		}
		if (gbuffer == null) {
			gbuffer = renderer.createGBuffer(renderer.getWidth(), renderer.getHeight(), GBUFFER_SIZE);
			gbuffer.unbind();
		}
		for (int i = 0; i < spotLights.size(); i++) {
			ShadowBuffer sb = shadowBuffers.get(i);
			ShaderLight light = spotLights.get(i);
			
			light.setLightIndex(i);
			
			currentLight = light;
			shadow = true;
			
			sb.bind();
			renderShadow(game, renderer);
			sb.unbind();
		}
		for (int i = 0; i < directionalLights.size(); i++) {
			ShadowBuffer sb = shadowBuffers.get(spotLights.size() + i);
			ShaderLight light = directionalLights.get(i);
			
			light.setLightIndex(spotLights.size() + i);
			
			currentLight = light;
			shadow = true;
			
			sb.bind();
			renderShadow(game, renderer);
			sb.unbind();
		}
		shadow = false;
		currentLight = null;
		
		if (shader == null) {
			try {
				shader = ShaderLoader.createDefaultLightingShader(renderer);
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
		if (debugShader == null) {
			try {
				debugShader = ShaderLoader.createDefaultSimpleShader(renderer);
			} catch (IOException e) {
				throw new RenderException(e);
			}
			debugShader.startUse();
			debugShader.setUniformTextureUnit("aTexture", 0);
			debugShader.endUse();
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
		for (int i = 0; i < shadowBuffers.size(); i++) {
			shadowBuffers.get(i).bind(GBUFFER_SIZE + i);
		}
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
		
		for (TransparentObject3D o : params.transparentObjects) {
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
		
		if (renderer.getInput().isKeyDown(Key.F1)) {
			debugShader.startUse();
			shadowBuffers.get(0).bind(0);
			shadowBuffers.get(0).draw(renderer);
			shadowBuffers.get(0).unbind(0);
			debugShader.endUse();
		}
	}
	
	protected void configureShader(Renderer renderer, Shader shader) {
		for (int i = 0; i < pointLights.size(); i++) {
			if (i >= MAX_POINT_LIGHTS - 1) {
				break;
			}
			
			pointLights.get(i).load(shader, i, shadowDistance);
		}
		shader.setUniform1i("pointLightCount", Math.min(pointLights.size(), MAX_POINT_LIGHTS));
		
		for (int i = 0; i < spotLights.size(); i++) {
			if (i >= MAX_SPOT_LIGHTS - 1) {
				break;
			}
			
			spotLights.get(i).load(shader, i, shadowDistance);
		}
		shader.setUniform1i("spotLightCount", Math.min(spotLights.size(), MAX_SPOT_LIGHTS));
		//shader.setUniformMatrix4O("spotLights[0].matrix", new Matrix4f());
		
		for (int i = 0; i < directionalLights.size(); i++) {
			if (i >= MAX_DIRECTIONAL_LIGHTS - 1) {
				break;
			}
			
			directionalLights.get(i).load(shader, i, shadowDistance);
		}
		shader.setUniform1i("directionalLightCount", Math.min(directionalLights.size(), MAX_DIRECTIONAL_LIGHTS));
		
		shader.setUniform3fO("viewPos", getCamera().getPosition());
		
		shader.setUniform1fO("gamma", gamma);
		shader.setUniform1bO("srgbOutput", srgbOutput);
		
		for (int i = 0; i < shadowBuffers.size(); i++) {
			shader.setUniformTextureUnitO("shadowBuffers[" + i + "]", GBUFFER_SIZE + i);
		}
	}

	@Override
	protected void configureMaterial(Renderer renderer, Material material) {
		if (shadow) {
			material.setSceneTransform(currentLight.getProjection(shadowDistance), currentLight.getView());
		} else {
			Matrix4f projection = getCamera().getProjection(renderer);
			Matrix4f view = getCamera().getView(renderer);
			
			material.setSceneTransform(projection, view);
		}
		
		material.getShader().setUniform1fO("gamma", gamma);
		material.getShader().setUniform1bO("srgbTextures", false);
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
