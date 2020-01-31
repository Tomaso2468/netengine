package io.github.tomaso2468.netengine.camera;

import org.joml.Matrix4f;

import io.github.tomaso2468.netengine.input.Input;
import io.github.tomaso2468.netengine.render.Renderer;

public class IdentityCamera implements Camera {
	@Override
	public Matrix4f getProjection(Renderer renderer) {
		return new Matrix4f();
	}
	@Override
	public Matrix4f getView(Renderer renderer) {
		return new Matrix4f();
	}
	@Override
	public void update(Input input, float delta) {
		
	}
}
