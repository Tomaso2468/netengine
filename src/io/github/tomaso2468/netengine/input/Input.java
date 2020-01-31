package io.github.tomaso2468.netengine.input;

public interface Input {
	public boolean isKeyDown(Key key);
	public void clearMouseDelta();
	
	public float getMouseDX();
	public float getMouseDY();
	
	public float getMouseX();
	public float getMouseY();
	
	public float getMouseWheelDX();
	public float getMouseWheelDY();
}
