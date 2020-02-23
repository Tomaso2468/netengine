package io.github.tomaso2468.netengine.camera;

import io.github.tomaso2468.netengine.input.Input;
import io.github.tomaso2468.netengine.input.Key;

public class SimpleCamera2D extends Camera2D {
	private float moveSpeed = 10f;
	
	@Override
	public void update(Input input, float delta) {
		if (input.isKeyDown(Key.W)) {
			move(0, -moveSpeed * delta);
		}
		if (input.isKeyDown(Key.S)) {
			move(0, moveSpeed * delta);
		}
		if (input.isKeyDown(Key.A)) {
			move(-moveSpeed * delta, 0);
		}
		if (input.isKeyDown(Key.D)) {
			move(moveSpeed * delta, 0);
		}
	}
}
