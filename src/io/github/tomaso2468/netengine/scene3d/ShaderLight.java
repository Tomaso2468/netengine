package io.github.tomaso2468.netengine.scene3d;

import org.joml.Matrix4f;
import org.joml.Vector3f;

import io.github.tomaso2468.netengine.render.Shader;

public interface ShaderLight {
	public void load(Shader shader, int index, float distance);
	
	public Vector3f getPosition();
	
	public void setLightIndex(int index);
	
	public Matrix4f getProjection(float distance);
	public Matrix4f getView();
}
