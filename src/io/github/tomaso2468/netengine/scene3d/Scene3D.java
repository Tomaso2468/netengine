package io.github.tomaso2468.netengine.scene3d;

import java.util.Collections;

import org.joml.Matrix4f;
import org.joml.Vector3f;

import io.github.tomaso2468.netengine.Game;
import io.github.tomaso2468.netengine.camera.Camera3D;
import io.github.tomaso2468.netengine.camera.SimpleCamera3D;
import io.github.tomaso2468.netengine.input.Input;
import io.github.tomaso2468.netengine.render.BlendFactor;
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
				
				configureMaterial(material);
			}
		};
		
		params.cull = true;
		renderer.setDepthTest(true);
		renderer.setBlend(BlendFactor.SRC_ALPHA, BlendFactor.ONE_MINUS_SRC_ALPHA);
		renderer.setFaceCull(true);
		
		draw(game, renderer, params, new Matrix4f());
		
		Vector3f position = camera.getPosition();
		Collections.sort(params.transparentObjects, (o1, o2) -> {
			float d1 = position.distanceSquared(o1.transform.transformPosition(new Vector3f(o1.object.getPosition())));
			float d2 = position.distanceSquared(o2.transform.transformPosition(new Vector3f(o2.object.getPosition())));
			return Float.compare(d1, d2);
		});
		
		for (TransparentObject o : params.transparentObjects) {
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
	
	protected void configureMaterial(Material material) {
		
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
	
	@Override
	public void init(Game game, Renderer renderer) {
		super.init(game, renderer);
	}
}
