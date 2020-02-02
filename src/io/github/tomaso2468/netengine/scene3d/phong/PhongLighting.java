package io.github.tomaso2468.netengine.scene3d.phong;

import java.io.IOException;

import io.github.tomaso2468.netengine.render.Renderer;
import io.github.tomaso2468.netengine.render.Shader;

public final class PhongLighting {
	private PhongLighting() {
		// Prevent instantiation.
	}

	public static Shader createDefaultPhongShader(Renderer renderer) throws IOException {
		return renderer.createShader(
				PhongLighting.class.getResource("/io/github/tomaso2468/netengine/scene3d/phong/vertex.vs"),
				PhongLighting.class.getResource("/io/github/tomaso2468/netengine/scene3d/phong/fragment.fs"));
	}
	
	public static Shader createDefaultBlinnPhongShader(Renderer renderer) throws IOException {
		return renderer.createShader(
				PhongLighting.class.getResource("/io/github/tomaso2468/netengine/scene3d/blinnphong/vertex.vs"),
				PhongLighting.class.getResource("/io/github/tomaso2468/netengine/scene3d/blinnphong/fragment.fs"));
	}
}
