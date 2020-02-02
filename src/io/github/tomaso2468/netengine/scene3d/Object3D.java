package io.github.tomaso2468.netengine.scene3d;

import org.joml.Matrix4f;
import org.joml.Vector3f;

import io.github.tomaso2468.netengine.Game;
import io.github.tomaso2468.netengine.input.Input;
import io.github.tomaso2468.netengine.render.Renderer;

public interface Object3D {
	public void draw(Game game, Renderer renderer, SceneParams params, Matrix4f transform);
	
	public Vector3f getPosition();
	public void setPosition(Vector3f transform);
	
	public Vector3f getRotation();
	public void setRotation(Vector3f transform);
	
	public default Matrix4f getTransform() {
		Vector3f position = getPosition();
		Vector3f rotation = getRotation();
		
		Matrix4f transform = new Matrix4f()
				.rotate(rotation.x, new Vector3f(1, 0, 0))
				.rotate(rotation.y, new Vector3f(0, 1, 0))
				.rotate(rotation.z, new Vector3f(0, 0, 1));
		
		Matrix4f detransform = new Matrix4f(transform).invert();
		
		transform = transform.translate(detransform.transformPosition(new Vector3f(position)));
		
		return transform;
	}
	
	public default void update(Game game, Input input, float delta) {
		
	}
	public default void init(Game game, Renderer renderer) {
		
	}
}
