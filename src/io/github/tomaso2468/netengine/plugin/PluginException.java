package io.github.tomaso2468.netengine.plugin;

import io.github.tomaso2468.netengine.EngineException;

public class PluginException extends EngineException {
	private static final long serialVersionUID = -6569410416534052155L;

	public PluginException() {
	}

	public PluginException(String message) {
		super(message);
	}

	public PluginException(Throwable cause) {
		super(cause);
	}

	public PluginException(String message, Throwable cause) {
		super(message, cause);
	}

	public PluginException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
