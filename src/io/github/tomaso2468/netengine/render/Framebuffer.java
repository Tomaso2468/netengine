package io.github.tomaso2468.netengine.render;

public interface Framebuffer extends Texture {
	public int getWidth();
	public int getHeight();
	
	public void bind();
	public void unbind();
	
	public default void draw(Renderer renderer) {
		renderer.drawQuadUV(-1, -1, 2, 2);
	}
}
