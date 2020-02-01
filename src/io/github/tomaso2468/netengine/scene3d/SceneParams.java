package io.github.tomaso2468.netengine.scene3d;

import io.github.tomaso2468.netengine.render.RenderState;

public abstract class SceneParams {
	public Material material;
	public RenderState state;
	
	public abstract void applySceneTransform(Material material);
}
