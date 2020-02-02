package io.github.tomaso2468.netengine.scene3d.phong;

import io.github.tomaso2468.netengine.render.Shader;
import io.github.tomaso2468.netengine.render.Texture;
import io.github.tomaso2468.netengine.scene3d.TextureMaterial;

public class PhongMaterial extends TextureMaterial {
	public PhongMaterial(Shader shader, Texture texture, Texture ambient, Texture diffuse, Texture specular, Texture shiny) {
		super(shader, new Texture[] {texture, ambient, diffuse, specular, shiny});
		
		shader.startUse();
		
		shader.setUniformTextureUnit("objectTexture", 0);
		shader.setUniformTextureUnit("ambientLight", 1);
		shader.setUniformTextureUnit("diffuseLight", 2);
		shader.setUniformTextureUnit("specularLight", 3);
		shader.setUniformTextureUnit("shinyLight", 4);
		
		shader.endUse();
	}
}
