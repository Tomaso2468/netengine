package io.github.tomaso2468.netengine.render;

public interface GBuffer extends Framebuffer {
	public void bind(int unit, int texture);
	public void unbind(int unit, int texture);
	public void copyDepthToScreen();
}
