package io.github.tomaso2468.netengine.scene2d;

import org.joml.Matrix4f;
import org.joml.Vector3f;

public class TransparentObject2D {
	public Object2D object;
	public Matrix4f transform;
	
	public TransparentObject2D(Object2D object, Matrix4f transform) {
		super();
		this.object = object;
		this.transform = transform;
	}

	public Vector3f getPosition() {
		return transform.transformPosition(object.getPosition());
	}
}
