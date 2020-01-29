package io.github.tomaso2468.netengine.render;

public class UnknownImplException extends RenderException {
	private static final long serialVersionUID = 940349381897459160L;

	public UnknownImplException() {
	}

	public UnknownImplException(Object o) {
		super("The object with class " + o.getClass().getSimpleName() + " is not from the implementation used.");
	}

}
