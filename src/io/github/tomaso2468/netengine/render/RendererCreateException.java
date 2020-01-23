package io.github.tomaso2468.netengine.render;

public class RendererCreateException extends RenderException {
	private static final long serialVersionUID = 6488710609804891672L;

	public RendererCreateException() {
	}

	public RendererCreateException(String message) {
		super(message);
	}

	public RendererCreateException(Throwable cause) {
		super(cause);
	}

	public RendererCreateException(String message, Throwable cause) {
		super(message, cause);
	}

	public RendererCreateException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
