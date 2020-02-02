package io.github.tomaso2468.netengine;

public class Color {
	public static final Color red = new Color(1, 0, 0);
	public static final Color black = new Color(0, 0, 0);
	public static final Color white = new Color(1, 1, 1);
	
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

	public Color multiply(float f) {
		return new Color(r * f, g * f, b * f, a);
	}

}
