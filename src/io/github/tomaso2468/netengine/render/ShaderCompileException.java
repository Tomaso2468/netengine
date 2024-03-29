package io.github.tomaso2468.netengine.render;

public class ShaderCompileException extends ShaderException {
	private static final long serialVersionUID = 5356628788037414661L;

	public ShaderCompileException() {
	}

	public ShaderCompileException(String message) {
		super(message);
	}

	public ShaderCompileException(Throwable cause) {
		super(cause);
	}

	public ShaderCompileException(String message, Throwable cause) {
		super(message, cause);
	}

	public ShaderCompileException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
