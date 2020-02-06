package io.github.tomaso2468.netengine.scene3d.phong;

import org.joml.Vector3f;

import io.github.tomaso2468.netengine.Color;
import io.github.tomaso2468.netengine.render.Shader;
import io.github.tomaso2468.netengine.scene3d.ShaderLight;

public class PhongDirectionalLight implements ShaderLight {
	public Vector3f direction;
	
	public Color ambient;
	public Color diffuse;
	public Color specular;

	public PhongDirectionalLight(Vector3f direction, Color ambient, Color diffuse, Color specular) {
		super();
		this.direction = direction;
		this.ambient = ambient;
		this.diffuse = diffuse;
		this.specular = specular;
	}
	
	public PhongDirectionalLight(Vector3f direction, Color color) {
		this(direction, color.multiply(0.1f), color, color);
	}

	@Override
	public void load(Shader shader, int index) {
		shader.setUniformColor("directionalLights[" + index + "].specular", specular);
		shader.setUniformColor("directionalLights[" + index + "].ambient", ambient);
		shader.setUniformColor("directionalLights[" + index + "].diffuse", diffuse);
		
		shader.setUniform3f("directionalLights[" + index + "].direction", direction);
	}

	@Override
	public Vector3f getPosition() {
		return new Vector3f(direction).negate();
	}

}
