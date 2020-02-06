package io.github.tomaso2468.netengine.scene3d;

import org.joml.Matrix4f;

import io.github.tomaso2468.netengine.render.Shader;
import io.github.tomaso2468.netengine.render.Texture;

public class TextureMaterial implements Material {
	private final Shader shader;
	private final Texture[] textures;

	public TextureMaterial(Shader shader, Texture[] textures) {
		super();
		this.shader = shader;
		this.textures = textures;
	}

	@Override
	public void bind() {
		for (int i = 0; i < textures.length; i++) {
			textures[i].bind(i);
		}
		shader.startUse();
	}

	@Override
	public void unbind() {
		shader.endUse();
		for (int i = 0; i < textures.length; i++) {
			textures[i].unbind(i);
		}
	}

	public Shader getShader() {
		return shader;
	}

	public Texture[] getTextures() {
		return textures;
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
		return textures.length;
	}

}
