package io.github.tomaso2468.netengine.render;

public class TextureLoadException extends RenderException {
	private static final long serialVersionUID = 6327253910069460257L;

	public TextureLoadException() {
	}

	public TextureLoadException(String message) {
		super(message);
	}

	public TextureLoadException(Throwable cause) {
		super(cause);
	}

	public TextureLoadException(String message, Throwable cause) {
		super(message, cause);
	}

	public TextureLoadException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
