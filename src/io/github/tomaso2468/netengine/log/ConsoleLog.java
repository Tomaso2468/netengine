package io.github.tomaso2468.netengine.log;

public class ConsoleLog implements LogSystem {
	@Override
	public void debug(String s) {
		System.out.println("[debug] " + s);
	}

	@Override
	public void info(String s) {
		System.out.println("[INFO] " + s);
	}

	@Override
	public void warn(String s) {
		System.out.println("[WARNING] " + s);
	}

	@Override
	public void error(String s) {
		System.out.println("[ERROR] " + s);
	}

	@Override
	public void warn(String s, Throwable e) {
		System.out.println("[WARNING] " + s);
		e.printStackTrace(System.out);
	}

	@Override
	public void error(String s, Throwable e) {
		System.out.println("[ERROR] " + s);
		e.printStackTrace(System.out);
	}

	@Override
	public void crash(String s, Throwable e) {
		System.out.println();
		System.out.println("[CRASH] " + s);
		e.printStackTrace(System.out);
		System.out.println();
	}

}
