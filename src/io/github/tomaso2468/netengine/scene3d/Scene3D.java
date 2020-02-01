package io.github.tomaso2468.netengine.scene3d;

import org.joml.Matrix4f;

import io.github.tomaso2468.netengine.Game;
import io.github.tomaso2468.netengine.camera.Camera3D;
import io.github.tomaso2468.netengine.camera.SimpleCamera3D;
import io.github.tomaso2468.netengine.input.Input;
import io.github.tomaso2468.netengine.render.Renderer;

public class Scene3D extends GroupedObject3D {
	private Camera3D camera = new SimpleCamera3D();
	
	public void draw(Game game, Renderer renderer) {
		Matrix4f projection = camera.getProjection(renderer);
		Matrix4f view = camera.getView(renderer);	
		
		SceneParams params = new SceneParams() {
			@Override
			public void applySceneTransform(Material material) {
				material.setSceneTransform(projection, view);
			}
		};
		
		draw(game, renderer, params, new Matrix4f());
		
		if (params.state != null) {
			params.state.leaveState();
		}
		if (params.material != null) {
			params.material.unbind();
		}
	}
	
	public Camera3D getCamera() {
		return camera;
	}
	
	public void setCamera(Camera3D camera) {
		this.camera = camera;
	}
	
	public void update(Game game, Input input, float delta) {
		camera.update(input, delta);
		super.update(game, input, delta);
	}
}
