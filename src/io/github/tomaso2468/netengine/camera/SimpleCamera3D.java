package io.github.tomaso2468.netengine.camera;

import org.joml.Vector3f;

import io.github.tomaso2468.netengine.input.Input;
import io.github.tomaso2468.netengine.input.Key;

public class SimpleCamera3D extends Camera3D {
	private float moveSpeed = 2.5f;
	private float mouseSensitivity = 360f * 8;
	
	public SimpleCamera3D() {
		setLimitPitch(true);
	}
	
	@Override
	public void update(Input input, float delta) {
		if (input.isKeyDown(Key.W)) {
			moveFacingYawOnly(moveSpeed * delta);
		}
		if (input.isKeyDown(Key.S)) {
			moveFacingYawOnly(-moveSpeed * delta);
		}
		if (input.isKeyDown(Key.A)) {
			moveSideYawOnly(-moveSpeed * delta);
		}
		if (input.isKeyDown(Key.D)) {
			moveSideYawOnly(moveSpeed * delta);
		}
		if (input.isKeyDown(Key.Q)) {
			setRoll(getRoll() - 90 * delta);
		}
		if (input.isKeyDown(Key.E)) {
			setRoll(getRoll() + 90 * delta);
		}
		if (input.isKeyDown(Key.R)) {
			setRoll(0);
			setFOV(70);
		}
		if (input.isKeyDown(Key.SPACE)) {
			setPosition(getPosition().add(new Vector3f(0, moveSpeed * delta, 0)));
		}
		if (input.isKeyDown(Key.LSHIFT)) {
			setPosition(getPosition().add(new Vector3f(0, -moveSpeed * delta, 0)));
		}
		
		float fov = getFOV();
		fov -= input.getMouseWheelDY();
		if (fov <= 10f) {
			fov = 10f;
		}
		if (fov >= 150) {
			fov = 150;
		}
		setFOV(fov);
		
		rotate(new Vector3f(input.getMouseDY() * mouseSensitivity * delta, input.getMouseDX() * mouseSensitivity * delta, 0));
	}
}
