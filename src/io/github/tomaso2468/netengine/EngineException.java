package io.github.tomaso2468.netengine;

public class EngineException extends RuntimeException {
	private static final long serialVersionUID = -5063224850668212599L;

	public EngineException() {
	}

	public EngineException(String message) {
		super(message);
	}

	public EngineException(Throwable cause) {
		super(cause);
	}

	public EngineException(String message, Throwable cause) {
		super(message, cause);
	}

	public EngineException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
