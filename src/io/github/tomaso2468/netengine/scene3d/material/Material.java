package io.github.tomaso2468.netengine.scene3d.material;

import org.joml.Matrix4f;

import io.github.tomaso2468.netengine.render.Shader;

public interface Material {
	public void bind();
	public void unbind();
	public default void bindDepth() {
		getDepthShader().startUse();
	}
	public default void unbindDepth() {
		getDepthShader().endUse();
	}
	
	public void setTransform(Matrix4f transform);
	
	public void setSceneTransform(Matrix4f projection, Matrix4f view);
	
	public Shader getShader();
	public default Shader getDepthShader() {
		return getShader();
	}
	
	public int getNextTextureUnit();
}
