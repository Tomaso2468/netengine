package io.github.tomaso2468.netengine.scene3d;

import java.util.ArrayList;
import java.util.List;

import io.github.tomaso2468.netengine.render.RenderState;
import io.github.tomaso2468.netengine.render.Renderer;

public abstract class SceneParams {
	public Material material;
	public RenderState state;
	public final List<TransparentObject> transparentObjects = new ArrayList<>(64);
	public boolean cull;
	
	public abstract void applySceneTransform(Renderer renderer, Material material);
}
