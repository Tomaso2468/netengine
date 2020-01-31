package io.github.tomaso2468.netengine.camera;

import org.joml.Matrix4f;
import org.joml.Vector3f;

import io.github.tomaso2468.netengine.input.Input;
import io.github.tomaso2468.netengine.math.Mathf;
import io.github.tomaso2468.netengine.render.Renderer;

public class Camera3D implements Camera {
	protected Vector3f position = new Vector3f();
	protected Vector3f rotation = new Vector3f(20, -90, 0);
	protected float fov = 70f;
	protected float zfar = 1000f;
	protected float znear = 0.01f;
	protected boolean limitPitch = false;

	@Override
	public Matrix4f getProjection(Renderer renderer) {
		return new Matrix4f().setPerspective((float) Mathf.toRadians(fov), (float) renderer.getWidth() / (float) renderer.getHeight(), znear, zfar);
	}

	@Override
	public Matrix4f getView(Renderer renderer) {
		float pitch = (float) Math.toRadians(rotation.x % 360);
		float yaw = (float) Math.toRadians(rotation.y % 360);
		float roll = (float) Math.toRadians(rotation.z % 360);
		
		Vector3f cameraPos = position;
		Vector3f cameraUp = new Vector3f(0, 1, 0).rotateZ(roll);
		Vector3f cameraFront = new Vector3f(
				(float) (Mathf.cos(yaw) * Mathf.cos(pitch)),
				(float) (Mathf.sin(pitch)),
				(float) (Mathf.sin(yaw) * Mathf.cos(pitch)));
		
		return new Matrix4f().lookAt(cameraPos, new Vector3f(cameraPos).add(cameraFront), cameraUp);
	}
	
	public Vector3f getPosition() {
		return new Vector3f(position);
	}
	
	public void setPosition(Vector3f pos) {
		this.position = pos;
	}
	
	public void move(Vector3f v) {
		setPosition(position.add(v));
	}
	
	public void moveFacing(float distance) {
		float pitch = (float) Mathf.toRadians(rotation.x % 360);
		float yaw = (float) Mathf.toRadians(rotation.y % 360);
		
		Vector3f cameraFront = new Vector3f(
				(float) (Mathf.cos(yaw) * Mathf.cos(pitch)),
				(float) (Mathf.sin(pitch)),
				(float) (Mathf.sin(yaw) * Mathf.cos(pitch)));
		
		setPosition(position.add(new Vector3f(cameraFront).mul(distance)));
	}
	
	public void moveSide(float distance) {
		float pitch = (float) Mathf.toRadians(rotation.x % 360);
		float yaw = (float) Mathf.toRadians(rotation.y % 360);
		
		Vector3f cameraFront = new Vector3f(
				(float) (Mathf.cos(yaw) * Mathf.cos(pitch)),
				(float) (Mathf.sin(pitch)),
				(float) (Mathf.sin(yaw) * Mathf.cos(pitch)));
		Vector3f cameraUp = new Vector3f(0, 1, 0);
		
		setPosition(position.add(new Vector3f(cameraFront).cross(cameraUp).mul(distance)));
	}
	
	public void moveSideYawOnly(float distance) {
		float pitch = 0;
		float yaw = (float) Mathf.toRadians(rotation.y % 360);
		
		Vector3f cameraFront = new Vector3f(
				(float) (Mathf.cos(yaw) * Mathf.cos(pitch)),
				(float) (Mathf.sin(pitch)),
				(float) (Mathf.sin(yaw) * Mathf.cos(pitch)));
		Vector3f cameraUp = new Vector3f(0, 1, 0);
		
		setPosition(position.add(new Vector3f(cameraFront).cross(cameraUp).mul(distance)));
	}
	
	public void moveFacingYawOnly(float distance) {
		float pitch = 0;
		float yaw = (float) Math.toRadians(rotation.y % 360);
		
		Vector3f cameraFront = new Vector3f(
				(float) (Mathf.cos(yaw) * Mathf.cos(pitch)),
				(float) (Mathf.sin(pitch)),
				(float) (Mathf.sin(yaw) * Mathf.cos(pitch)));
		
		setPosition(position.add(new Vector3f(cameraFront).mul(distance)));
	}
	
	public Vector3f getRotation() {
		return new Vector3f(rotation);
	}
	
	public float getYaw() {
		return rotation.y;
	}
	
	public float getPitch() {
		return rotation.x;
	}
	
	public float getRoll() {
		return rotation.z;
	}
	
	public void setRotation(Vector3f rot) {
		float x = rot.x;
		if (limitPitch) {
			if (x > 180) {
				x = 180;
			}
			if (x < -180) {
				x = -180;
			}
		}
		this.rotation = new Vector3f(x, rot.y % 360, rot.z % 360);
	}
	
	public void setYaw(float yaw) {
		setRotation(new Vector3f(rotation.x, yaw, rotation.z));
	}
	
	public void setPitch(float pitch) {
		setRotation(new Vector3f(pitch, rotation.y, rotation.z));
	}
	
	public void setRoll(float roll) {
		setRotation(new Vector3f(rotation.x, rotation.y, roll));
	}
	
	public void rotate(Vector3f v) {
		setRotation(new Vector3f(rotation).add(v));
	}
	
	public float getFOV() {
		return fov;
	}
	
	public void setFOV(float fov) {
		if (fov <= 0) {
			throw new IllegalArgumentException("FOV cannot be less than or equal to 0 degrees: " + fov);
		}
		if (fov >= 180) {
			throw new IllegalArgumentException("FOV cannot be greater than or equal to 180 degrees: " + fov);
		}
		this.fov = fov;
	}
	
	public float getZFar() {
		return zfar;
	}
	
	public void setZFar(float zfar) {
		this.zfar = zfar;
	}
	
	public float getZNear() {
		return znear;
	}
	
	public void setZNear(float znear) {
		this.znear = znear;
	}
	
	public boolean isLimitPitch() {
		return limitPitch;
	}
	
	public void setLimitPitch(boolean limitPitch) {
		this.limitPitch = limitPitch;
	}

	@Override
	public void update(Input input, float delta) {
		
	}

}
