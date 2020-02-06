package io.github.tomaso2468.netengine.scene3d.deferred;

import java.io.IOException;
import java.util.Collections;

import org.joml.Matrix4f;
import org.joml.Vector3f;

import io.github.tomaso2468.netengine.Color;
import io.github.tomaso2468.netengine.Game;
import io.github.tomaso2468.netengine.render.BlendFactor;
import io.github.tomaso2468.netengine.render.GBuffer;
import io.github.tomaso2468.netengine.render.RenderException;
import io.github.tomaso2468.netengine.render.Renderer;
import io.github.tomaso2468.netengine.render.Shader;
import io.github.tomaso2468.netengine.scene3d.Material;
import io.github.tomaso2468.netengine.scene3d.Scene3D;
import io.github.tomaso2468.netengine.scene3d.SceneParams;
import io.github.tomaso2468.netengine.scene3d.ShaderLoader;
import io.github.tomaso2468.netengine.scene3d.TransparentObject;

public class DeferredScene extends Scene3D {
	protected GBuffer gbuffer;
	protected Shader shader;
	
	public void draw(Game game, Renderer renderer) {
		if (gbuffer != null && (gbuffer.getWidth() != renderer.getWidth() || gbuffer.getHeight() != renderer.getHeight())) {
			gbuffer.dispose();
			
			gbuffer = null;
		}
		if (gbuffer == null) {
			int count = 0;
			count += 1; // position
			count += 1; // normal
			count += 1; // albedo
			count += 1; // specular
			count += 1; // screen space
			
			gbuffer = renderer.createGBuffer(renderer.getWidth(), renderer.getHeight(), count);
			gbuffer.unbind();
		}
		if (shader == null) {
			try {
				shader = ShaderLoader.createDefaultSimpleShader(renderer);
			} catch (IOException e) {
				throw new RenderException(e);
			}
			shader.startUse();
			shader.setUniformTextureUnitO("position", 0);
			shader.setUniformTextureUnitO("normal", 1);
			shader.setUniformTextureUnitO("diffuse", 2);
			shader.setUniformTextureUnitO("specular", 3);
			shader.setUniformTextureUnitO("screenSpacePosition", 4);
			shader.endUse();
		}
		
		gbuffer.bind();
		renderer.clearScreen(Color.black);
		
		SceneParams params = new SceneParams() {
			@Override
			public void applySceneTransform(Renderer renderer, Material material) {
				configureMaterial(renderer, material);
			}
		};
		
		params.cull = true;
		renderer.setDepthTest(true);
		renderer.setBlend(BlendFactor.DISABLE, BlendFactor.DISABLE);
		renderer.setFaceCull(true);
		
		draw(game, renderer, params, new Matrix4f());
		
		if (params.state != null) {
			params.state.leaveState();
			params.state = null;
		}
		if (params.material != null) {
			params.material.unbind();
			params.material = null;
		}
		
		gbuffer.unbind();
		
		renderer.setDepthTest(true);
		renderer.setBlend(BlendFactor.SRC_ALPHA, BlendFactor.ONE_MINUS_SRC_ALPHA);
		renderer.setFaceCull(false);

		shader.startUse();
		gbuffer.bind(0);
		gbuffer.draw(renderer);
		gbuffer.unbind(0);
		shader.endUse();
		
		gbuffer.copyDepthToScreen();
		
		Vector3f position = getCamera().getPosition();
		Collections.sort(params.transparentObjects, (o1, o2) -> {
			float d1 = position.distanceSquared(o1.transform.transformPosition(new Vector3f(o1.object.getPosition())));
			float d2 = position.distanceSquared(o2.transform.transformPosition(new Vector3f(o2.object.getPosition())));
			return Float.compare(d1, d2);
		});
		
		for (TransparentObject o : params.transparentObjects) {
			gbuffer.bind();
			renderer.clearScreen(new Color(0, 0, 0, 0));
			
			renderer.setDepthTest(true);
			renderer.setBlend(BlendFactor.DISABLE, BlendFactor.DISABLE);
			renderer.setFaceCull(true);
			
			o.object.draw(game, renderer, params, o.transform);
			
			if (params.state != null) {
				params.state.leaveState();
				params.state = null;
			}
			if (params.material != null) {
				params.material.unbind();
				params.material = null;
			}
			
			gbuffer.unbind();
			
			renderer.setDepthTest(true);
			renderer.setBlend(BlendFactor.SRC_ALPHA, BlendFactor.ONE_MINUS_SRC_ALPHA);
			renderer.setFaceCull(false);
			
			shader.startUse();
			gbuffer.bind(0);
			gbuffer.draw(renderer);
			gbuffer.unbind(0);
			shader.endUse();
		}
		
		renderer.setDepthTest(false);
		renderer.setBlend(BlendFactor.SRC_ALPHA, BlendFactor.ONE_MINUS_SRC_ALPHA);
		renderer.setFaceCull(false);
	}

}
