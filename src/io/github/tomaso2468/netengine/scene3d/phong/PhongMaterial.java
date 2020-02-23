package io.github.tomaso2468.netengine.scene3d.phong;

import java.io.IOException;

import io.github.tomaso2468.netengine.render.RenderException;
import io.github.tomaso2468.netengine.render.Renderer;
import io.github.tomaso2468.netengine.render.Shader;
import io.github.tomaso2468.netengine.render.ShaderLoader;
import io.github.tomaso2468.netengine.render.Texture;
import io.github.tomaso2468.netengine.scene3d.material.TextureMaterial;

public class PhongMaterial extends TextureMaterial {
	private static Shader DEPTH_SHADER;
	public static Shader getDefaultDepthShader(Renderer renderer) {
		if (DEPTH_SHADER != null) {
			return DEPTH_SHADER;
		} else {
			try {
				DEPTH_SHADER = ShaderLoader.createDefaultDepthShader(renderer);
			} catch (IOException e) {
				throw new RenderException(e);
			}
			return DEPTH_SHADER;
		}
	}
	
	private final Shader depth;
	public PhongMaterial(Shader shader, Texture diffuse, Texture specular, Shader depth) {
		super(shader, new Texture[] {diffuse, specular});
		
		shader.startUse();
		
		shader.setUniformTextureUnit("diffuseLight", 0);
		shader.setUniformTextureUnit("specularLight", 1);
		
		shader.endUse();
		
		this.depth = depth;
		
		depth.startUse();
		
		depth.setUniformTextureUnitO("diffuseLight", 0);
		
		depth.endUse();
	}
	
	@Override
	public Shader getDepthShader() {
		return depth;
	}
}
