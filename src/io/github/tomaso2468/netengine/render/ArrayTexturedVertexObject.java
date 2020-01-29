package io.github.tomaso2468.netengine.render;

public class ArrayTexturedVertexObject implements TexturedVertexObject {

	private float[] data;
	private int[] indices;
	
	public ArrayTexturedVertexObject(float[] data, int[] indices) {
		this.data = data;
		this.indices = indices;
	}

	public float[] getData() {
		return data;
	}
	
	public int[] getIndices() {
		return indices;
	}
	
	@Override
	public void draw(Renderer renderer) {
		renderer.drawTrianglesTextured(data, indices);
	}

	@Override
	public void bind() {
		
	}

	@Override
	public void unbind() {
		
	}

}
