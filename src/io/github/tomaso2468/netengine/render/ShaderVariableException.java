package io.github.tomaso2468.netengine.render;

public class ShaderVariableException extends ShaderException {
	private static final long serialVersionUID = -8410258843741658265L;

	public ShaderVariableException() {
	}

	public ShaderVariableException(String var) {
		super("Could not find variable: " + var);
	}

}
