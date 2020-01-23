package io.github.tomaso2468.netengine.render;

import io.github.tomaso2468.netengine.EngineException;

public class RenderException extends EngineException {
	private static final long serialVersionUID = -4714980349784101907L;

	public RenderException() {
	}

	public RenderException(String message) {
		super(message);
	}

	public RenderException(Throwable cause) {
		super(cause);
	}

	public RenderException(String message, Throwable cause) {
		super(message, cause);
	}

	public RenderException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
