package io.github.tomaso2468.netengine.render;

public class ShaderException extends RenderException {
	private static final long serialVersionUID = 2715208836730386976L;

	public ShaderException() {
	}

	public ShaderException(String message) {
		super(message);
	}

	public ShaderException(Throwable cause) {
		super(cause);
	}

	public ShaderException(String message, Throwable cause) {
		super(message, cause);
	}

	public ShaderException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
