package io.github.tomaso2468.netengine.scene2d.material;

import org.joml.Matrix4f;

import io.github.tomaso2468.netengine.render.Shader;

public class ShaderSprite implements Material2D {
	private final Shader shader;
	private final int width;
	private final int height;
	
	public ShaderSprite(Shader shader, int width, int height) {
		this.shader = shader;
		this.width = width;
		this.height = height;
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

	@Override
	public float getTextureX() {
		return 0;
	}

	@Override
	public float getTextureY() {
		return 0;
	}

	@Override
	public float getTextureW() {
		return 1;
	}

	@Override
	public float getTextureH() {
		return 1;
	}

	@Override
	public int getWidth() {
		return width;
	}

	@Override
	public int getHeight() {
		return height;
	}

}
