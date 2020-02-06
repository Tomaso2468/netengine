package io.github.tomaso2468.netengine.scene3d.phong;

import org.joml.Vector3f;

import io.github.tomaso2468.netengine.Color;
import io.github.tomaso2468.netengine.math.Mathf;
import io.github.tomaso2468.netengine.render.Shader;
import io.github.tomaso2468.netengine.scene3d.ShaderLight;

public class PhongSpotLight implements ShaderLight {
	public Vector3f position;
	public Vector3f direction;
	
	public Color ambient;
	public Color diffuse;
	public Color specular;
	
	public float attenuation;
	
	public float cutoff;
	public float outerCutoff;
	
	public PhongSpotLight(Vector3f position, Vector3f direction, Color ambient, Color diffuse, Color specular,
			float attenuation, float cutoff, float outerCutoff) {
		super();
		this.position = position;
		this.direction = direction;
		this.ambient = ambient;
		this.diffuse = diffuse;
		this.specular = specular;
		this.attenuation = attenuation;
		this.cutoff = cutoff;
		this.outerCutoff = outerCutoff;
	}
	
	public PhongSpotLight(Vector3f position, Vector3f direction, Color color, float attenuation, float cutoff, float outerCutoff) {
		this(position, direction, color.multiply(0.1f), color, color, attenuation, cutoff, outerCutoff);
	}
	
	public PhongSpotLight(Vector3f position, Vector3f direction, Color color, float attenuation, float cutoff) {
		this(position, direction, color, attenuation, cutoff * 0.95f, cutoff);
	}
	
	public PhongSpotLight(Vector3f position, Vector3f direction, Color ambient, Color diffuse, Color specular,
			float attenuation, float cutoff) {
		this(position, direction, ambient, diffuse, specular, attenuation, cutoff * 0.95f, cutoff);
	}

	@Override
	public void load(Shader shader, int index) {
		shader.setUniform3f("spotLights[" + index + "].position", position);
		shader.setUniform3f("spotLights[" + index + "].direction", direction);
		
		shader.setUniformColor("spotLights[" + index + "].ambient", ambient);
		shader.setUniformColor("spotLights[" + index + "].diffuse", diffuse);
		shader.setUniformColor("spotLights[" + index + "].specular", specular);
		
		shader.setUniform1f("spotLights[" + index + "].attenuation", attenuation);
		shader.setUniform1f("spotLights[" + index + "].cutoff", Mathf.cos(cutoff));
		shader.setUniform1f("spotLights[" + index + "].cutoff2", Mathf.cos(outerCutoff));
	}

	public Vector3f getPosition() {
		return new Vector3f(position);
	}
}
