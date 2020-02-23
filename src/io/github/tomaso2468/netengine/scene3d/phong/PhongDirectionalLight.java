package io.github.tomaso2468.netengine.scene3d.phong;

import org.joml.Matrix4f;
import org.joml.Vector3f;

import io.github.tomaso2468.netengine.Color;
import io.github.tomaso2468.netengine.render.Shader;
import io.github.tomaso2468.netengine.scene3d.ShaderLight;

public class PhongDirectionalLight implements ShaderLight {
	public Vector3f direction;
	
	public Color ambient;
	public Color diffuse;
	public Color specular;
	
	public int bufferIndex;

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
	public void load(Shader shader, int index, float distance) {
		shader.setUniformColor("directionalLights[" + index + "].specular", specular);
		shader.setUniformColor("directionalLights[" + index + "].ambient", ambient);
		shader.setUniformColor("directionalLights[" + index + "].diffuse", diffuse);
		
		shader.setUniform3f("directionalLights[" + index + "].direction", direction);
		
		shader.setUniform1iO("directionalLights[" + index + "].bufferIndex", bufferIndex);
		
		shader.setUniformMatrix4O("directionalLights[" + index + "].matrix", getProjection(distance).mul(getView()));
	}

	@Override
	public Vector3f getPosition() {
		return new Vector3f(direction).negate();
	}

	@Override
	public void setLightIndex(int index) {
		bufferIndex = index;
	}
	
	@Override
	public Matrix4f getProjection(float shadowDistance) {
		Matrix4f projection = new Matrix4f().setOrtho(-shadowDistance, shadowDistance, -shadowDistance, shadowDistance, 0.1f, 100f);
		return projection;
	}

	@Override
	public Matrix4f getView() {
		Matrix4f view = new Matrix4f().lookAt(getPosition(), new Vector3f(direction), new Vector3f(0.0f, 1.0f, 0.0f));
		return view;
	}

}
