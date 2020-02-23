package io.github.tomaso2468.netengine.render;

import java.io.IOException;

public final class ShaderLoader {
	private ShaderLoader() {
		// Prevent instantiation.
	}

	public static Shader createDefaultDepthShader(Renderer renderer) throws IOException {
		return renderer.createShader(
				ShaderLoader.class.getResource("/io/github/tomaso2468/netengine/scene3d/deferred/depth.vs"),
				ShaderLoader.class.getResource("/io/github/tomaso2468/netengine/scene3d/deferred/depth.fs"));
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
				ShaderLoader.class.getResource("/io/github/tomaso2468/netengine/scene3d/simple.fs"));
	}
	
	public static Shader createDefaultLightingShader(Renderer renderer) throws IOException {
		return renderer.createShader(
				ShaderLoader.class.getResource("/io/github/tomaso2468/netengine/scene3d/deferred/lighting.vs"),
				ShaderLoader.class.getResource("/io/github/tomaso2468/netengine/scene3d/deferred/lighting.fs"));
	}
	
	public static Shader createDefaultDeferredShader(Renderer renderer) throws IOException {
		return renderer.createShader(
				ShaderLoader.class.getResource("/io/github/tomaso2468/netengine/scene3d/deferred/vertex.vs"),
				ShaderLoader.class.getResource("/io/github/tomaso2468/netengine/scene3d/deferred/fragment.fs"));
	}
}
