package io.github.tomaso2468.netengine.scene3d.deferred;

import io.github.tomaso2468.netengine.render.Shader;
import io.github.tomaso2468.netengine.render.Texture;
import io.github.tomaso2468.netengine.scene3d.TextureMaterial;

public class DeferredMaterial extends TextureMaterial {
	public DeferredMaterial(Shader shader, Texture diffuse, Texture specular) {
		super(shader, new Texture[] {diffuse, specular});
		
		shader.startUse();
		
		shader.setUniformTextureUnit("diffuseLight", 0);
		shader.setUniformTextureUnit("specularLight", 1);
		
		shader.endUse();
	}
}
