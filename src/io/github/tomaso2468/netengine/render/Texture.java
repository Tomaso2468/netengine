package io.github.tomaso2468.netengine.render;

public interface Texture extends RenderResource {
	public int getWidthPixels();
	public int getHeightPixels();
	public void bind(int unit);
	public void unbind(int unit);
}
