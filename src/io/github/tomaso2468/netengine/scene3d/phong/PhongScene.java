package io.github.tomaso2468.netengine.scene3d.phong;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.joml.Matrix4f;
import org.joml.Vector3f;

import io.github.tomaso2468.netengine.Game;
import io.github.tomaso2468.netengine.log.Log;
import io.github.tomaso2468.netengine.render.Framebuffer;
import io.github.tomaso2468.netengine.render.Renderer;
import io.github.tomaso2468.netengine.scene3d.Material;
import io.github.tomaso2468.netengine.scene3d.Scene3D;
import io.github.tomaso2468.netengine.scene3d.ShaderLight;

public class PhongScene extends Scene3D {
	public static final int MAX_POINT_LIGHTS = 16;
	public static final int MAX_DIRECTIONAL_LIGHTS = 8;
	public static final int MAX_SPOT_LIGHTS = 8;
	
	private final List<PhongPointLight> pointLights = new ArrayList<>();
	private final List<PhongDirectionalLight> directionalLights = new ArrayList<>();
	private final List<PhongSpotLight> spotLights = new ArrayList<>();
	private final List<Framebuffer> shadowBuffer = new ArrayList<Framebuffer>();
	
	private float gamma = 2.2f;
	private boolean srgbTextures = true;
	private boolean srgbOutput = true;
	private boolean shadow = false;
	private int shadowResolution = 1024;
	private float shadowDistance = 10f;
	private ShaderLight currentLight;
	
	private void checkShadowBuffers(Renderer renderer) {
		if (shadowBuffer.size() < directionalLights.size() + spotLights.size()) {
			int dif = (directionalLights.size() + spotLights.size()) - shadowBuffer.size();
			
			Log.debug("Generating " + dif + " shadow buffers.");
			
			for (int i = 0; i < dif; i++) {
				Framebuffer fb = renderer.createShadowbuffer(shadowResolution, shadowResolution);
				fb.unbind();
				shadowBuffer.add(fb);
			}
		}
	}
	
	@Override
	public void draw(Game game, Renderer renderer) {
		checkShadowBuffers(renderer);
		
		Collections.sort(pointLights, (o1, o2) -> {
			Vector3f camera = getCamera().getPosition();
			
			float d1 = camera.distance(o1.position);
			float d2 = camera.distance(o2.position);
			
			if (d1 < d2) {
				return -1;
			} else {
				return 1;
			}
		});
		Collections.sort(spotLights, (o1, o2) -> {
			Vector3f camera = getCamera().getPosition();
			
			float d1 = camera.distance(o1.position);
			float d2 = camera.distance(o2.position);
			
			if (d1 < d2) {
				return -1;
			} else {
				return 1;
			}
		});
		
//		int bufferIndex = 0;
//		for (PhongDirectionalLight directionalLight : directionalLights) {
//			currentLight = directionalLight;
//			
//			shadow = true;
//			super.draw(game, renderer);
//		}
//		for (PhongSpotLight spotLight : spotLights) {
//			currentLight = spotLight;
//			
//			shadow = true;
//			super.draw(game, renderer);
//		}
		shadow = false;
		super.draw(game, renderer);
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
		material.getShader().setUniform1b("depth", false);
		material.getShader().setUniform1b("shadow", shadow);
		
		for (int i = 0; i < pointLights.size(); i++) {
			if (i >= MAX_POINT_LIGHTS - 1) {
				break;
			}
			
			pointLights.get(i).load(material.getShader(), i);
		}
		material.getShader().setUniform1i("pointLightCount", Math.min(pointLights.size(), MAX_POINT_LIGHTS));
		
		for (int i = 0; i < spotLights.size(); i++) {
			if (i >= MAX_SPOT_LIGHTS - 1) {
				break;
			}
			
			Matrix4f projection = new Matrix4f().setOrtho(-shadowDistance, shadowDistance, -shadowDistance, shadowDistance, 0.1f, 7.5f);
			Matrix4f view = new Matrix4f().lookAt(spotLights.get(i).getPosition(), new Vector3f(0.1f, 0, 0), new Vector3f(0.0f, 1.0f, 0.0f));
			
			spotLights.get(i).load(material.getShader(), i);
			
			material.getShader().setUniformMatrix4("spotLights[" + i + "].lightSpaceMatrix", projection.mul(view));
		}
		material.getShader().setUniform1i("spotLightCount", Math.min(spotLights.size(), MAX_SPOT_LIGHTS));
		
		for (int i = 0; i < directionalLights.size(); i++) {
			if (i >= MAX_DIRECTIONAL_LIGHTS - 1) {
				break;
			}
			
			Matrix4f projection = new Matrix4f().setOrtho(-shadowDistance, shadowDistance, -shadowDistance, shadowDistance, 0.1f, 7.5f);
			Matrix4f view = new Matrix4f().lookAt(directionalLights.get(i).getPosition(), new Vector3f(0.1f, 0, 0), new Vector3f(0.0f, 1.0f, 0.0f));
			
			directionalLights.get(i).load(material.getShader(), i);
			
			material.getShader().setUniformMatrix4("directionalLights[" + i + "].lightSpaceMatrix", projection.mul(view));
		}
		material.getShader().setUniform1i("directionalLightCount", Math.min(directionalLights.size(), MAX_DIRECTIONAL_LIGHTS));
		
		material.getShader().setUniform3f("viewPos", getCamera().getPosition());
		
		material.getShader().setUniform1f("gamma", gamma);
		material.getShader().setUniform1b("srgbTextures", srgbTextures);
		material.getShader().setUniform1b("srgbOutput", srgbOutput);
		
		material.getShader().setUniform1i("debugLightPos", 8);
	}
	
	@Override
	public void init(Game game, Renderer renderer) {
		super.init(game, renderer);
		
		checkShadowBuffers(renderer);
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
