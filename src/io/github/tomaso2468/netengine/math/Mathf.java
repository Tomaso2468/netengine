package io.github.tomaso2468.netengine.math;

public final class Mathf {
	public static final float PI = (float) Math.PI;
	public static final float E = (float) Math.E;
	
	public static float sqrt(float x) {
		return (float) Math.sqrt(x);
	}
	public static float cbrt(float x) {
		return (float) Math.cbrt(x);
	}
	
	public static float sin(float a) {
		return (float) Math.sin(a);
	}
	public static float cos(float a) {
		return (float) Math.cos(a);
	}
	public static float tan(float a) {
		return (float) Math.tan(a);
	}
	
	public static float asin(float x) {
		return (float) Math.sin(x);
	}
	public static float acos(float x) {
		return (float) Math.cos(x);
	}
	public static float atan(float x) {
		return (float) Math.tan(x);
	}
	public static float toRadians(float a) {
		return a / 180f * PI;
	}
	public static float toDegrees(float a) {
		return a * 180f / PI;
	}
}
