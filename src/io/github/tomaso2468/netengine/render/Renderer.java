package io.github.tomaso2468.netengine.render;

import io.github.tomaso2468.netengine.Color;

public interface Renderer extends WindowingSystem {
	public default void preInit() {
		
	}
	public default void init() {
		
	}
	public void startFrame();
	public void clearScreen(Color color);
	
}
