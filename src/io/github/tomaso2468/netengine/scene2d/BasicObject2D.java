package io.github.tomaso2468.netengine.scene2d;

import org.joml.Matrix4f;
import org.joml.Vector3f;

import io.github.tomaso2468.netengine.Game;
import io.github.tomaso2468.netengine.render.MultiTextureVertexObject;
import io.github.tomaso2468.netengine.render.RenderState;
import io.github.tomaso2468.netengine.render.Renderer;
import io.github.tomaso2468.netengine.scene3d.material.Material;

public class BasicObject2D implements Object2D {
	private final RenderState state;
	private MultiTextureVertexObject model;
	private Material material;
	private Vector3f position;
	private float rotation;
	private final boolean transparent;
	
	public BasicObject2D(Renderer renderer, RenderState state, MultiTextureVertexObject model, Material material, Vector3f position, float rotation, boolean transparent) {
		super();
		this.state = state;
		this.model = model;
		this.material = material;
		this.position = position;
		this.rotation = rotation;
		this.transparent = transparent;
	}
	
	public BasicObject2D(Renderer renderer, RenderState state, MultiTextureVertexObject model, Material material, boolean transparent) {
		this(renderer, state, model, material, new Vector3f(), 0, transparent);
	}
	
	public BasicObject2D(Renderer renderer, float[] data, int[] indices, Material material, Vector3f position, float rotation, boolean transparent) {
		super();
		this.state = renderer.createRenderState();
		
		state.enterState();
		
		this.model = renderer.createStaticVOMultiTexture(data, indices);
		model.configureVO(0);
		model.configureVOTexture(1);
		model.configureVONormal(2);
		model.configureVOSelect(3);
		model.unbind();
		
		state.leaveState();
		
		this.material = material;
		this.position = position;
		this.rotation = rotation;
		this.transparent = transparent;
	}
	
	public BasicObject2D(Renderer renderer, float[] data, int[] indices, Material material, boolean transparent) {
		this(renderer, data, indices, material, new Vector3f(), 0, transparent);
	}
	
	public MultiTextureVertexObject getModel() {
		return model;
	}
	
	public RenderState getRenderState() {
		return state;
	}
	
	public Material getMaterial() {
		return material;
	}
	
	@Override
	public void draw(Game game, Renderer renderer, SceneParams params, Matrix4f transform) {
		if (params.state != state) {
			if (params.state != null) params.state.leaveState();
		}
		if (!material.equals(params.material)) {
			if (params.material != null) params.material.unbind();
			material.bind();
			params.material = material;
			params.applySceneTransform(renderer, material);
		}
		if (params.state != state) {
			state.enterState();
			params.state = state;
		}
		
		material.setTransform(new Matrix4f(transform).mul(new Matrix4f(getTransform())));
		model.draw(renderer);
	}

	public Vector3f getPosition() {
		return new Vector3f(position);
	}

	public void setPosition(Vector3f position) {
		this.position = new Vector3f(position);
	}

	public float getRotation() {
		return rotation;
	}

	public void setRotation(float rotation) {
		this.rotation = rotation;
	}
	
	public boolean isTransparent() {
		return transparent;
	}

	@Override
	public void drawDepth(Game game, Renderer renderer, SceneParams params, Matrix4f transform) {
		if (params.state != state) {
			if (params.state != null) params.state.leaveState();
		}
		if (!material.equals(params.material)) {
			if (params.material != null) params.material.unbindDepth();
			material.bindDepth();
			params.material = material;
			params.applySceneTransform(renderer, material);
		}
		if (params.state != state) {
			state.enterState();
			params.state = state;
		}
		
		material.setTransform(new Matrix4f(transform).mul(new Matrix4f(getTransform())));
		model.draw(renderer);
	}
}
