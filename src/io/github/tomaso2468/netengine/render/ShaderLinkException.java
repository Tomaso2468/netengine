package io.github.tomaso2468.netengine.render;

public class ShaderLinkException extends ShaderException {
	private static final long serialVersionUID = -5077121358504207224L;

	public ShaderLinkException() {
	}

	public ShaderLinkException(String message) {
		super(message);
	}

	public ShaderLinkException(Throwable cause) {
		super(cause);
	}

	public ShaderLinkException(String message, Throwable cause) {
		super(message, cause);
	}

	public ShaderLinkException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
