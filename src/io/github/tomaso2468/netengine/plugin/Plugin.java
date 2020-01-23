package io.github.tomaso2468.netengine.plugin;

import io.github.tomaso2468.netengine.Game;

public interface Plugin {
	public String getID();
	
	public String getName();
	
	public String getVersion();
	
	public void preInit(Game game);
	public void init(Game game);
	public void postInit(Game game);
}
