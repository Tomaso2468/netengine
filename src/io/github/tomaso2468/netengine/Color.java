package io.github.tomaso2468.netengine;

public class Color {
	public static Color black = new Color(0, 0, 0);
	public static Color white = new Color(1, 1, 1);
	
	public final float r;
	public final float g;
	public final float b;
	public final float a;
	
	public Color(float r, float g, float b, float a) {
		super();
		this.r = r;
		this.g = g;
		this.b = b;
		this.a = a;
	}
	
	public Color(float r, float g, float b) {
		this(r, g, b, 1);
	}

}
