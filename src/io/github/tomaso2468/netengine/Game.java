package io.github.tomaso2468.netengine;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import io.github.tomaso2468.netengine.log.Log;
import io.github.tomaso2468.netengine.plugin.Plugin;
import io.github.tomaso2468.netengine.plugin.PluginLoadException;
import io.github.tomaso2468.netengine.render.Renderer;
import io.github.tomaso2468.netengine.render.RendererCreateException;

public abstract class Game {
	public static final int EXIT_CODE_CRASH = Integer.MIN_VALUE;
	
	private Class<?> rendererClass;
	private Renderer renderer;
	private int targetWidth = 1080;
	private int targetHeight = 720;
	private boolean fullscreen = false;
	private boolean windowResizable = true;
	private boolean vsync = true;
	// Hello
	private Map<String, Plugin> plugins = new HashMap<String, Plugin>();
	private Map<String, Class<?>> renderers = new HashMap<String, Class<?>>();
	private Map<String, Class<?>> renderersBackwardsCompatible = new HashMap<String, Class<?>>();
	
	public void start() {
		try {
			Log.info("Starting Game");
			init();
			
			Log.info("Main loop starting");
			while (true) {
				loop();
			}
		} catch (Throwable e) {
			Log.crash(e);
			Log.error("Exiting: " + EXIT_CODE_CRASH);
			System.exit(EXIT_CODE_CRASH);
		}
	}
	
	public void exit(int code) {
		Log.info("Exiting: " + code);
		renderer.destroy();
		System.exit(code);
	}
	
	private void init() {
		Log.info("Registering Plugins");
		
		pluginRegister();
		Log.info("Pre-Initialising Plugins");
		pluginPreInit();
		Log.info("Pre-Initialising Game");
		preInitGame();
		
		Log.info("Initialising Engine");
		initEngine();
		Log.info("Initialising Plugins");
		pluginInit();
		Log.info("Initialising Game");
		initGame();
		
		Log.info("Post-Initialising Plugins");
		pluginPostInit();
		Log.info("Post-Initialising Game");
		postInitGame();
		
		Log.info("Initialising Render State");
		renderInit(renderer);
	}
	
	public void registerPlugin(Class<?> pluginClass) {
		try {
			Log.debug("Attempting to load plugin " + pluginClass.getSimpleName());
			
			Object pluginObject = pluginClass.newInstance();
			Plugin plugin = (Plugin) pluginObject;
			
			if (plugins.containsKey(plugin.getID())) {
				Log.warn("Duplicate plugins: " + plugin.getClass().getCanonicalName() + " " + plugins.get(plugin.getID()).getClass().getCanonicalName() + " with id: " + plugin.getID());
			}
			
			Log.info("Loaded plugin " + plugin.getID());
			
			plugins.put(plugin.getID(), plugin);
		} catch (InstantiationException | IllegalAccessException e) {
			throw new PluginLoadException("Failed to load plugin.", e);
		}
	}
	
	public void registerPlugin(String pluginClass) {
		try {
			Log.debug("Attempting to find plugin " + pluginClass);
			registerPlugin(Class.forName(pluginClass));
		} catch (ClassNotFoundException e) {
			throw new PluginLoadException("Failed to load plugin.", e);
		}
	}
	
	public void registerPluginOptional(String pluginClass) {
		try {
			registerPlugin(pluginClass);
		} catch (PluginLoadException e) {
			Log.debug(pluginClass + " was not found.");
		}
	}
	
	protected void pluginRegister() {
		registerPlugin(NetEngine.class);
		
		registerPluginOptional("io.github.tomaso2468.netengine.render.opengl.LWJGL3OpenGL");
	}
	
	private void pluginPreInit() {
		for (Plugin plugin : plugins.values()) {
			Log.debug("Pre-Initialising " + plugin.getID());
			plugin.preInit(this);
		}
	}
	
	protected void preInitGame() {
		// TODO
	}
	
	private void initEngine() {
		Log.info("Initialising Renderer");
		initRenderer();
	}
	
	public void registerRenderer(String id, Class<?> rendererClass) {
		renderers.put(id, rendererClass);
	}
	
	public void registerRendererBackwardsCompatible(String id, Class<?> rendererClass) {
		renderersBackwardsCompatible.put(id, rendererClass);
	}
	
	public Class<?> getRendererClassByID(String id) {
		if (!id.endsWith("-exact")) {
			for (Entry<String, Class<?>> e : renderersBackwardsCompatible.entrySet()) {
				if (e.getKey().equalsIgnoreCase(id)) {
					return e.getValue();
				}
			}
		} else {
			Log.warn("An exact renderer has been requested. If this causes issues remove -exact from the renderer setting.");
			id = id.replace("-exact", "");
		}
		
		for (Entry<String, Class<?>> e : renderers.entrySet()) {
			if (e.getKey().equalsIgnoreCase(id)) {
				return e.getValue();
			}
		}
		
		throw new RendererCreateException("Could not find renderer supporting: " + id);
	}
	
	public void setRendererID(String id) {
		setRendererClass(getRendererClassByID(id));
	}
	
	private void initRenderer() {
		Log.debug("Renderer Settings: ");
		Log.debug("Renderer Class: " + rendererClass.getSimpleName());
		Log.debug("VSync: " + (vsync ? "Enabled" : "Disabled"));
		Log.debug("Target Resolution: " + targetWidth + "x" + targetHeight + (fullscreen ? " (Fullscreen)" : " (Windowed)"));
		Log.debug("Resizable: " + windowResizable);
		
		if (rendererClass == null) {
			throw new NullPointerException("The renderer is not defined. Please set the renderer in the preInitGame method.");
		}
		
		try {
			Object rendererObject = rendererClass.newInstance();
			setRenderer((Renderer) rendererObject);
		} catch (InstantiationException | IllegalAccessException e) {
			throw new RendererCreateException("An error occured creating the renderer.", e);
		}
		
		Log.debug("Configuring Renderer");
		configure(renderer);
		
		Log.debug("Pre-Initialising Renderer");
		renderer.preInit();
		Log.debug("Pre-Initialising Windowing System");
		renderer.preInitWindow();
		
		renderer.setVSync(vsync);
		
		Log.debug("Creating Window");
		renderer.createWindow(targetWidth, targetHeight, getName(), fullscreen, windowResizable);
		renderer.showWindow();
		
		Log.debug("Initialising Renderer");
		renderer.init();
	}
	
	protected void initGame() {
		// TODO
	}
	
	private void pluginInit() {
		for (Plugin plugin : plugins.values()) {
			Log.debug("Initialising " + plugin.getID());
			plugin.init(this);
		}
	}
	
	protected void postInitGame() {
		// TODO
	}
	
	private void pluginPostInit() {
		for (Plugin plugin : plugins.values()) {
			Log.debug("Post-Initialising " + plugin.getID());
			plugin.postInit(this);
		}
	}
	
	private void pluginStartFrame() {
		for (Plugin plugin : plugins.values()) {
			plugin.startFrame(this, renderer);
		}
	}
	
	private void pluginEndFrame() {
		for (Plugin plugin : plugins.values()) {
			plugin.endFrame(this, renderer);
		}
	}
	
	private void pluginStartLoop() {
		for (Plugin plugin : plugins.values()) {
			plugin.startLoop(this);
		}
	}
	
	private void pluginEndLoop() {
		for (Plugin plugin : plugins.values()) {
			plugin.endLoop(this);
		}
	}
	
	private void loop() {
		pluginStartLoop();
		renderFrame();
		pluginEndLoop();
	}
	
	protected void renderInit(Renderer renderer) {
		
	}
	
	private void renderFrame() {
		renderer.startFrame();
		renderer.clearScreen(Color.black);
		
		pluginStartFrame();
		
		if (renderer.windowClosePressed()) {
			exit(0);
		}
		
		render(renderer);
		
		pluginEndFrame();
		
		//TODO
		renderer.getInput().clearMouseDelta();
		
		renderer.sync();
		renderer.update();
	}
	
	protected void render(Renderer renderer) {
		
	}
	
	protected void configure(Renderer renderer) {
		
	}

	public Class<?> getRendererClass() {
		return rendererClass;
	}

	public void setRendererClass(Class<?> rendererClass) {
		this.rendererClass = rendererClass;
	}

	public Renderer getRenderer() {
		return renderer;
	}

	public void setRenderer(Renderer renderer) {
		this.renderer = renderer;
	}
	
	public int getTargetHeight() {
		return targetHeight;
	}
	
	public int getTargetWidth() {
		return targetWidth;
	}
	
	public void setSize(int width, int height) {
		this.targetWidth = width;
		this.targetHeight = height;
		if (renderer != null) {
			renderer.setWindowSize(width, height);
		}
	}
	
	public boolean isFullscreen() {
		if (renderer != null) {
			return renderer.isFullscreen();
		}
		return fullscreen;
	}
	
	public void setFullscreen(boolean fullscreen) {
		this.fullscreen = fullscreen;
		if (renderer != null) {
			renderer.setFullscreen(fullscreen);
		}
	}

	public boolean isWindowResizable() {
		return windowResizable;
	}

	public void setWindowResizable(boolean windowResizable) {
		this.windowResizable = windowResizable;
		if (renderer != null) {
			renderer.setResizable(windowResizable);
		}
	}
	
	public void setVSync(boolean vsync) {
		this.vsync = vsync;
		if (renderer != null) {
			renderer.setVSync(vsync);
		}
	}
	
	public void setDisplay(int width, int height, boolean fullscreen) {
		this.targetWidth = width;
		this.targetHeight = height;
		this.fullscreen = fullscreen;
		if (renderer != null) {
			renderer.setDisplay(width, height, fullscreen);
		}
	}
	
	public boolean isVsync() {
		return vsync;
	}
	
	public abstract String getName();
}
