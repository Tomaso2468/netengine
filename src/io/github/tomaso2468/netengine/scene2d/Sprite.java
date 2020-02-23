package io.github.tomaso2468.netengine.scene2d;

import io.github.tomaso2468.netengine.render.Renderer;
import io.github.tomaso2468.netengine.scene2d.material.Material2D;

public class Sprite extends BasicObject2D {
	public Sprite(Renderer renderer, float width, float height, Material2D material, boolean transparent) {
		super(renderer,
				new float[] { 0, 0, 0, material.getTextureX(), material.getTextureY() + material.getTextureH(),
						material.getWidth(), 0, 0, material.getTextureX() + material.getTextureW(),
						material.getTextureY() + material.getTextureH(), material.getWidth(), material.getHeight(), 0,
						material.getTextureX() + material.getTextureW(), material.getTextureY(), 0,
						material.getHeight(), 0, material.getTextureX(), material.getTextureY(), },
				new int[] { 0, 1, 3, 1, 2, 3, }, material, transparent);
	}
}
