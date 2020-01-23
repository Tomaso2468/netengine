package io.github.tomaso2468.netengine;

import io.github.tomaso2468.netengine.render.Renderer;
import io.github.tomaso2468.netengine.render.RendererCreateException;

public abstract class Game {
	private Class<?> rendererClass;
	private Renderer renderer;
	private int targetWidth = 720;
	private int targetHeight = 480;
	private boolean fullscreen = false;
	private boolean windowResizable = true;
	private boolean vsync = true;
	
	public void start() {
		init();
		
		while (true) {
			loop();
		}
	}
	
	public void exit(int code) {
		renderer.destroy();
		System.exit(code);
	}
	
	public void loop() {
		renderer.startFrame();
		renderer.clearScreen(Color.white);
		
		if (renderer.windowClosePressed()) {
			exit(0);
		}
		
		renderer.sync();
		renderer.update();
	}
	
	public void init() {
		pluginRegister();
		preInitGame();
		initEngine();
		initGame();
	}
	
	public void pluginRegister() {
		// TODO
	}
	
	public void pluginLoad() {
		// TODO
	}
	
	public void preInitGame() {
		
	}
	
	public void initGame() {
		
	}
	
	public void initEngine() {
		initRenderer();
	}
	
	public void initRenderer() {
		if (rendererClass == null) {
			throw new NullPointerException("The renderer is not defined. Please set the renderer in the preInitGame method.");
		}
		
		try {
			Object rendererObject = rendererClass.newInstance();
			setRenderer((Renderer) rendererObject);
		} catch (InstantiationException | IllegalAccessException e) {
			throw new RendererCreateException("An error occured creating the renderer.", e);
		}
		renderer.preInit();
		renderer.preInitWindow();
		
		renderer.setVSync(vsync);
		
		renderer.createWindow(targetWidth, targetHeight, getName(), fullscreen, windowResizable);
		renderer.showWindow();
		
		renderer.init();
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
			// TODO Renderer size change
		}
	}
	
	public boolean isFullscreen() {
		// TODO Get renderer
		return fullscreen;
	}
	
	public void setFullscreen(boolean fullscreen) {
		this.fullscreen = fullscreen;
		if (renderer != null) {
			// TODO Renderer fullscreen change
		}
	}

	public boolean isWindowResizable() {
		return windowResizable;
	}

	public void setWindowResizable(boolean windowResizable) {
		this.windowResizable = windowResizable;
		if (renderer != null) {
			// TODO Renderer fullscreen change
		}
	}
	
	public void setVSync(boolean vsync) {
		this.vsync = vsync;
		if (renderer != null) {
			// TODO Renderer fullscreen change
		}
	}
	
	public boolean isVsync() {
		return vsync;
	}
	
	public abstract String getName();
}
