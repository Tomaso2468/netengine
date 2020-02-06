package io.github.tomaso2468.netengine.scene3d;

import org.joml.Vector3f;

import io.github.tomaso2468.netengine.render.Shader;

public interface ShaderLight {
	public void load(Shader shader, int index);
	
	public Vector3f getPosition();
}
