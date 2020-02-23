package io.github.tomaso2468.netengine.camera;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;

import io.github.tomaso2468.netengine.input.Input;
import io.github.tomaso2468.netengine.render.Renderer;

public class Camera2D implements Camera {
	protected Vector2f position = new Vector2f();
	protected Vector3f rotation = new Vector3f(0, 0, 0);
	protected Vector2f size = new Vector2f(720, 480);
	protected float scale = 1;

	@Override
	public Matrix4f getProjection(Renderer renderer) {
		return new Matrix4f().setOrthoSymmetric(size.x, size.y, -10, 10);
	}

	@Override
	public Matrix4f getView(Renderer renderer) {
		return new Matrix4f()
				.translate(new Vector3f(-position.x, -position.y, 0))
				.rotate(rotation.x, new Vector3f(1, 0, 0))
				.rotate(rotation.y, new Vector3f(0, 1, 0))
				.rotate(rotation.z, new Vector3f(0, 0, 1))
				.scale(scale);
	}
	
	public Vector2f getSize() {
		return new Vector2f(size);
	}
	
	public Vector3f getRotation() {
		return new Vector3f(rotation);
	}
	
	public Vector2f getPosition() {
		return new Vector2f(position);
	}
	
	public void setSize(double width, double height) {
		this.size = new Vector2f((float) width, (float) height);
	}
	
	public void setPosition(double x, double y) {
		this.position = new Vector2f((float) x, (float) y);
	}
	
	public void move(double x, double y) {
		setPosition(position.x + x, position.y + y);
	}
	
	public void setRotation(double z) {
		this.rotation = new Vector3f(0, 0, (float) z);
	}
	
	public void setRotation(double x, double y, double z) {
		this.rotation = new Vector3f((float) x, (float) y, (float) z);
	}
	
	public void setScale(float scale) {
		this.scale = scale;
	}

	@Override
	public void update(Input input, float delta) {
		
	}

}
