package io.github.tomaso2468.netengine.plugin;

public class PluginLoadException extends PluginException {
	private static final long serialVersionUID = -421405449259930195L;

	public PluginLoadException() {
	}

	public PluginLoadException(String message) {
		super(message);
	}

	public PluginLoadException(Throwable cause) {
		super(cause);
	}

	public PluginLoadException(String message, Throwable cause) {
		super(message, cause);
	}

	public PluginLoadException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
