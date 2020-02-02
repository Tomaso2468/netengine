package io.github.tomaso2468.netengine.scene3d;

import org.joml.Matrix4f;
import org.joml.Vector3f;

public class TransparentObject {
	public Object3D object;
	public Matrix4f transform;
	
	public TransparentObject(Object3D object, Matrix4f transform) {
		super();
		this.object = object;
		this.transform = transform;
	}

	public Vector3f getPosition() {
		return transform.transformPosition(object.getPosition());
	}
}
