package io.github.tomaso2468.netengine.scene2d.material;

import io.github.tomaso2468.netengine.scene3d.material.Material;

public interface Material2D extends Material {
	public int getWidth();
	public int getHeight();
	public float getTextureX();
	public float getTextureY();
	public float getTextureW();
	public float getTextureH();
}
