package io.github.tomaso2468.netengine.render;

public interface WindowingSystem {
	public default void preInitWindow() {
		
	}
	public void setVSync(boolean vsync);
	public void createWindow(int width, int height, String title, boolean fullscreen, boolean resizable);
	public void showWindow();
	public boolean windowClosePressed();
	public void sync();
	public void update();
	public void destroy();
}
