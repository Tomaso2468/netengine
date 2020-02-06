package io.github.tomaso2468.netengine.scene3d.phong;

import org.joml.Vector3f;

import io.github.tomaso2468.netengine.Color;
import io.github.tomaso2468.netengine.render.Shader;
import io.github.tomaso2468.netengine.scene3d.ShaderLight;

public class PhongPointLight implements ShaderLight {
	public Vector3f position;

	public Color ambient;
	public Color diffuse;
	public Color specular;

	public float attenuation;

	public PhongPointLight(Vector3f position, Color ambient, Color diffuse, Color specular, float attenuation) {
		super();
		this.position = position;
		this.ambient = ambient;
		this.diffuse = diffuse;
		this.specular = specular;
		this.attenuation = attenuation;
	}
	
	public PhongPointLight(Vector3f position, Color color, float attenuation) {
		this(position, color.multiply(0.1f), color, color, attenuation);
	}
	
	public void load(Shader shader, int index) {
		shader.setUniform3f("pointLights[" + index + "].position", position);
		
		shader.setUniformColor("pointLights[" + index + "].specular", specular);
		shader.setUniformColor("pointLights[" + index + "].ambient", ambient);
		shader.setUniformColor("pointLights[" + index + "].diffuse", diffuse);
		
		shader.setUniform1f("pointLights[" + index + "].attenuation", attenuation);
	}

	public Vector3f getPosition() {
		return new Vector3f(position);
	}
}
