package io.github.tomaso2468.netengine.camera;

import org.joml.Matrix4f;

import io.github.tomaso2468.netengine.input.Input;
import io.github.tomaso2468.netengine.render.Renderer;

public interface Camera {
	public Matrix4f getProjection(Renderer renderer);
	public Matrix4f getView(Renderer renderer);
	public void update(Input input, float delta);
}
