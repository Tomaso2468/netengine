package io.github.tomaso2468.netengine.plugin;

import io.github.tomaso2468.netengine.Game;
import io.github.tomaso2468.netengine.render.Renderer;

public interface Plugin {
	public String getID();
	
	public String getName();
	
	public String getVersion();
	
	public void preInit(Game game);
	public void init(Game game);
	public void postInit(Game game);

	public default void startLoop(Game game) {
		
	}
	public default void endLoop(Game game) {
		
	}
	public default void startFrame(Game game, Renderer renderer) {
		
	}
	public default void endFrame(Game game, Renderer renderer) {
		
	}
}
