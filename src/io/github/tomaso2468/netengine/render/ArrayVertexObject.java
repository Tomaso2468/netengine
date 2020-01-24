package io.github.tomaso2468.netengine.render;

public class ArrayVertexObject implements VertexObject {
	private float[] vertices;
	
	protected ArrayVertexObject(float[] vertices) {
		this.vertices = vertices;
	}

	@Override
	public float[] getVertices() {
		return vertices;
	}
}
