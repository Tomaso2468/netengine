package io.github.tomaso2468.netengine.scene3d;

import org.joml.Matrix4f;
import org.joml.Vector3f;

import io.github.tomaso2468.netengine.Game;
import io.github.tomaso2468.netengine.render.MultiTextureVertexObject;
import io.github.tomaso2468.netengine.render.RenderState;
import io.github.tomaso2468.netengine.render.Renderer;

public class BasicObject3D implements Object3D {
	private final RenderState state;
	private final MultiTextureVertexObject model;
	private final Material material;
	private Vector3f position;
	private Vector3f rotation;
	private final boolean transparent;
	private boolean cull = true;
	
	public BasicObject3D(Renderer renderer, RenderState state, MultiTextureVertexObject model, Material material, Vector3f position, Vector3f rotation, boolean transparent) {
		super();
		this.state = state;
		this.model = model;
		this.material = material;
		this.position = position;
		this.rotation = rotation;
		this.transparent = transparent;
	}
	
	public BasicObject3D(Renderer renderer, RenderState state, MultiTextureVertexObject model, Material material, boolean transparent) {
		this(renderer, state, model, material, new Vector3f(), new Vector3f(), transparent);
	}
	
	public BasicObject3D(Renderer renderer, float[] data, int[] indices, Material material, Vector3f position, Vector3f rotation, boolean transparent) {
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
	
	public BasicObject3D(Renderer renderer, float[] data, int[] indices, Material material, boolean transparent) {
		this(renderer, data, indices, material, new Vector3f(), new Vector3f(), transparent);
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
		if (params.cull != cull) {
			renderer.setFaceCull(cull);
			params.cull = cull;
		}
		if (params.state != state) {
			if (params.state != null) params.state.leaveState();
		}
		if (params.material != material) {
			if (params.material != null) params.material.unbind();
			material.bind();
			params.material = material;
			params.applySceneTransform(material);
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

	public Vector3f getRotation() {
		return new Vector3f(rotation);
	}

	public void setRotation(Vector3f rotation) {
		this.rotation = new Vector3f(rotation);
	}
	
	public boolean isTransparent() {
		return transparent;
	}

	public void setCull(boolean cull) {
		this.cull = cull;
	}
}
