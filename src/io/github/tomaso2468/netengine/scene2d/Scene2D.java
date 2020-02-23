package io.github.tomaso2468.netengine.scene2d;

import java.util.Collections;

import org.joml.Matrix4f;

import io.github.tomaso2468.netengine.Game;
import io.github.tomaso2468.netengine.camera.Camera2D;
import io.github.tomaso2468.netengine.camera.SimpleCamera2D;
import io.github.tomaso2468.netengine.input.Input;
import io.github.tomaso2468.netengine.render.BlendFactor;
import io.github.tomaso2468.netengine.render.Renderer;
import io.github.tomaso2468.netengine.scene3d.material.Material;

public class Scene2D extends GroupedObject2D {
	private Camera2D camera = new SimpleCamera2D();
	
	public void draw(Game game, Renderer renderer) {
		SceneParams params = new SceneParams() {
			@Override
			public void applySceneTransform(Renderer renderer, Material material) {
				configureMaterial(renderer, material);
			}
		};
		
		renderer.setDepthTest(true);
		renderer.setBlend(BlendFactor.SRC_ALPHA, BlendFactor.ONE_MINUS_SRC_ALPHA);
		renderer.setFaceCull(false);
		
		draw(game, renderer, params, new Matrix4f());
		
		Collections.sort(params.transparentObjects, (o1, o2) -> {
			return Float.compare(o1.getPosition().z, o2.getPosition().z);
		});
		
		for (TransparentObject2D o : params.transparentObjects) {
			o.object.draw(game, renderer, params, o.transform);
		}
		
		if (params.state != null) {
			params.state.leaveState();
		}
		if (params.material != null) {
			params.material.unbind();
		}
		
		renderer.setDepthTest(false);
		renderer.setBlend(BlendFactor.SRC_ALPHA, BlendFactor.ONE_MINUS_SRC_ALPHA);
		renderer.setFaceCull(false);
	}
	
	protected void configureMaterial(Renderer renderer, Material material) {
		camera.setSize(renderer.getWidth(), renderer.getHeight());
		
		Matrix4f projection = camera.getProjection(renderer);
		Matrix4f view = camera.getView(renderer);
		
		material.setSceneTransform(projection, view);
	}
	
	public Camera2D getCamera() {
		return camera;
	}
	
	public void setCamera(Camera2D camera) {
		this.camera = camera;
	}
	
	public void update(Game game, Input input, float delta) {
		camera.update(input, delta);
		super.update(game, input, delta);
	}
	
	@Override
	public void init(Game game, Renderer renderer) {
		super.init(game, renderer);
	}
}
