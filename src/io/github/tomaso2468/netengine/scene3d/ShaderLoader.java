package io.github.tomaso2468.netengine.scene3d;

import java.io.IOException;

import io.github.tomaso2468.netengine.render.Renderer;
import io.github.tomaso2468.netengine.render.Shader;

public final class ShaderLoader {
	private ShaderLoader() {
		// Prevent instantiation.
	}

	public static Shader createDefaultPhongShader(Renderer renderer) throws IOException {
		return renderer.createShader(
				ShaderLoader.class.getResource("/io/github/tomaso2468/netengine/scene3d/phong/vertex.vs"),
				ShaderLoader.class.getResource("/io/github/tomaso2468/netengine/scene3d/phong/fragment.fs"));
	}
	
	public static Shader createDefaultBlinnPhongShader(Renderer renderer) throws IOException {
		return renderer.createShader(
				ShaderLoader.class.getResource("/io/github/tomaso2468/netengine/scene3d/blinnphong/vertex.vs"),
				ShaderLoader.class.getResource("/io/github/tomaso2468/netengine/scene3d/blinnphong/fragment.fs"));
	}
	
	public static Shader createDefaultSimpleShader(Renderer renderer) throws IOException {
		return renderer.createShader(
				ShaderLoader.class.getResource("/io/github/tomaso2468/netengine/scene3d/simple.vs"),
				ShaderLoader.class.getResource("/io/github/tomaso2468/netengine/scene3d/deferred/lighting.fs"));
	}
	
	public static Shader createDefaultDeferredShader(Renderer renderer) throws IOException {
		return renderer.createShader(
				ShaderLoader.class.getResource("/io/github/tomaso2468/netengine/scene3d/deferred/vertex.vs"),
				ShaderLoader.class.getResource("/io/github/tomaso2468/netengine/scene3d/deferred/fragment.fs"));
	}
}
