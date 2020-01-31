package io.github.tomaso2468.netengine.render;

import io.github.tomaso2468.netengine.input.Input;

public interface WindowingSystem {
	public default void preInitWindow() {
		
	}
	public void createWindow(int width, int height, String title, boolean fullscreen, boolean resizable);
	public void showWindow();
	
	public void sync();
	public void update();
	
	public void destroy();
	
	public boolean windowClosePressed();
	
	public void setVSync(boolean vsync);
	public boolean isVSync();
	public void setFullscreen(boolean fullscreen);
	public boolean isFullscreen();
	public void setResizable(boolean resizable);
	public boolean isResizable();
	
	public void setWindowSize(int width, int height);
	public int getWindowWidth();
	public int getWindowHeight();
	
	public default int getSize() {
		return Math.min(getWidth(), getHeight());
	}
	
	public int getWidth();
	public int getHeight();
	
	public int getScreenWidth();
	public int getScreenHeight();
	
	public void setDisplay(int width, int height, boolean fullscreen);
	
	public Input getInput();
	public void setCaptureMouse(boolean capture);
}
