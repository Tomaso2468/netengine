package io.github.tomaso2468.netengine.scene2d.material;

import org.joml.Matrix4f;

import io.github.tomaso2468.netengine.render.Shader;
import io.github.tomaso2468.netengine.render.Texture;

public class TextureSprite implements Material2D {
	private final Shader shader;
	private final Texture[] textures;
	private final float x;
	private final float y;
	private final float w;
	private final float h;

	public TextureSprite(Shader shader, Texture[] textures) {
		super();
		this.shader = shader;
		this.textures = textures;
		this.x = 0;
		this.y = 0;
		this.w = 1;
		this.h = 1;
	}

	@Override
	public void bind() {
		for (int i = 0; i < textures.length; i++) {
			textures[i].bind(i);
		}
		shader.startUse();
	}
	
	@Override
	public void bindDepth() {
		for (int i = 0; i < textures.length; i++) {
			textures[i].bind(i);
		}
		getDepthShader().startUse();
	}

	@Override
	public void unbind() {
		shader.endUse();
		for (int i = 0; i < textures.length; i++) {
			textures[i].unbind(i);
		}
	}
	
	@Override
	public void unbindDepth() {
		getDepthShader().endUse();
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

	@Override
	public int getWidth() {
		return textures[0].getWidthPixels();
	}

	@Override
	public int getHeight() {
		return textures[0].getHeightPixels();
	}

	@Override
	public float getTextureX() {
		return x;
	}

	@Override
	public float getTextureY() {
		return y;
	}

	@Override
	public float getTextureW() {
		return w;
	}

	@Override
	public float getTextureH() {
		return h;
	}
}
