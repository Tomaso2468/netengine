package io.github.tomaso2468.netengine.scene3d.material;

import org.joml.Matrix4f;

import io.github.tomaso2468.netengine.render.Shader;

public class ShaderMaterial implements Material {
	private final Shader shader;
	
	public ShaderMaterial(Shader shader) {
		this.shader = shader;
	}

	@Override
	public void bind() {
		shader.startUse();
	}

	@Override
	public void unbind() {
		shader.endUse();
	}

	public Shader getShader() {
		return shader;
	}

	@Override
	public void setTransform(Matrix4f transform) {
		shader.setUniformMatrix4("model", transform);
	}

	@Override
	public void setSceneTransform(Matrix4f projection, Matrix4f view) {
		shader.setUniformMatrix4("projection", projection);
		shader.setUniformMatrix4("view", view);
	}

	@Override
	public int getNextTextureUnit() {
		return 0;
	}

}
