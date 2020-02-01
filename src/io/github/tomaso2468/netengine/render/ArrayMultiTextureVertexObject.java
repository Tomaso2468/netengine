package io.github.tomaso2468.netengine.render;

public class ArrayMultiTextureVertexObject implements MultiTextureVertexObject {

	private float[] data;
	private int[] indices;
	
	public ArrayMultiTextureVertexObject(float[] data, int[] indices) {
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
		throw new UnsupportedOperationException("Drawing must be done in prepared mode.");
	}

	@Override
	public void bind() {
		
	}

	@Override
	public void unbind() {
		
	}
}
