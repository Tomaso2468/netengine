package io.github.tomaso2468.netengine.scene3d;

import org.joml.Matrix4f;

public interface Material {
	public void bind();
	public void unbind();
	
	public void setTransform(Matrix4f transform);
	
	public void setSceneTransform(Matrix4f projection, Matrix4f view);
}
