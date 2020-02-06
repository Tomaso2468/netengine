package io.github.tomaso2468.netengine.render;

public interface VertexObject extends RenderResource {
	public default void configureVO(int location) {
		
	}
	public void draw(Renderer renderer);
	public void bind();
	public void unbind();
}
