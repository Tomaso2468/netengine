package io.github.tomaso2468.netengine.render;

public interface OpenGLRenderer extends Renderer {
	public int getGLSLVersion();
	public int getGLSLVersionMax();
	public int getOpenGLVersionInt();
	public int getOpenGLVersionMax();
	public boolean isES();
}
