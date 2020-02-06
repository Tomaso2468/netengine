package io.github.tomaso2468.netengine.render;

public class ArrayVertexObject implements VertexObject {
	private float[] vertices;
	
	public ArrayVertexObject(float[] vertices) {
		this.vertices = vertices;
	}

	public float[] getVertices() {
		return vertices;
	}
	
	@Override
	public void draw(Renderer renderer) {
		renderer.drawTriangles(vertices);
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
	}
}
