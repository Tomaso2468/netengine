package io.github.tomaso2468.netengine.scene3d.phong;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.joml.Vector3f;

import io.github.tomaso2468.netengine.Game;
import io.github.tomaso2468.netengine.render.Renderer;
import io.github.tomaso2468.netengine.scene3d.Material;
import io.github.tomaso2468.netengine.scene3d.Scene3D;

public class PhongScene extends Scene3D {
	public static final int MAX_POINT_LIGHTS = 32;
	public static final int MAX_DIRECTIONAL_LIGHTS = 32;
	public static final int MAX_SPOT_LIGHTS = 32;
	
	private final List<PhongPointLight> pointLights = new ArrayList<>();
	private final List<PhongDirectionalLight> directionalLights = new ArrayList<>();
	private final List<PhongSpotLight> spotLights = new ArrayList<>();
	
	private float gamma = 2.2f;
	private boolean srgbTextures = true;
	private boolean srgbOutput = true;
	
	@Override
	public void draw(Game game, Renderer renderer) {
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
		
		super.draw(game, renderer);
	}

	@Override
	protected void configureMaterial(Material material) {
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
			
			spotLights.get(i).load(material.getShader(), i);
		}
		material.getShader().setUniform1i("spotLightCount", Math.min(spotLights.size(), MAX_SPOT_LIGHTS));
		
		for (int i = 0; i < directionalLights.size(); i++) {
			if (i >= MAX_DIRECTIONAL_LIGHTS - 1) {
				break;
			}
			
			directionalLights.get(i).load(material.getShader(), i);
		}
		material.getShader().setUniform1i("directionalLightCount", Math.min(directionalLights.size(), MAX_DIRECTIONAL_LIGHTS));
		
		material.getShader().setUniform3f("viewPos", getCamera().getPosition());
		
		material.getShader().setUniform1f("gamma", gamma);
		material.getShader().setUniform1b("srgbTextures", srgbTextures);
		material.getShader().setUniform1b("srgbOutput", srgbOutput);
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
