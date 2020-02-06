package io.github.tomaso2468.netengine.render;

public class ArrayIndexedVertexObject implements IndexedVertexObject {
	private float[] vertices;
	private int[] indices;
	
	public ArrayIndexedVertexObject(float[] vertices, int[] indices) {
		this.vertices = vertices;
		this.indices = indices;
	}

	public float[] getVertices() {
		return vertices;
	}
	
	public int[] getIndices() {
		return indices;
	}
	
	@Override
	public void draw(Renderer renderer) {
		renderer.drawTriangles(vertices, indices);
	}

	@Override
	public void bind() {
		
	}

	@Override
	public void unbind() {
		
	}
	
	@Override
	public void dispose() {
		vertices = null;
		indices = null;
	}

}
