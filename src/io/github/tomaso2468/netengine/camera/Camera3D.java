package io.github.tomaso2468.netengine.camera;

import org.joml.Matrix4f;
import org.joml.Vector3f;

import io.github.tomaso2468.netengine.input.Input;
import io.github.tomaso2468.netengine.render.Renderer;

public class Camera3D implements Camera {
	protected Vector3f position = new Vector3f();
	protected Vector3f rotation = new Vector3f(0, -90, 0);
	protected float fov = 45f;
	protected float zfar = 1000f;
	protected float znear = 0.1f;

	@Override
	public Matrix4f getProjection(Renderer renderer) {
		return new Matrix4f().setPerspective(fov, (float) renderer.getWidth() / (float) renderer.getHeight(), znear, zfar);
	}

	@Override
	public Matrix4f getView(Renderer renderer) {
		Vector3f cameraPos = position;
		Vector3f cameraUp = new Vector3f(0, 1, 0);
		float pitch = (float) Math.toRadians(rotation.x);
		float yaw = (float) Math.toRadians(rotation.y);
		float roll = (float) Math.toRadians(rotation.z);
		
		Vector3f cameraFront = new Vector3f(
				(float) (Math.cos(yaw) * Math.cos(pitch)),
				(float) (Math.sin(pitch)),
				(float) (Math.sin(yaw) * Math.cos(pitch)));
		
		return new Matrix4f().lookAt(cameraPos, new Vector3f(cameraPos).add(cameraFront), cameraUp);
	}
	
	public void move(Vector3f v) {
		
	}

	@Override
	public void update(Input input) {
		
	}

}
