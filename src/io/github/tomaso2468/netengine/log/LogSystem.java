package io.github.tomaso2468.netengine.log;

public interface LogSystem {
	public void debug(String s);
	public void info(String s);
	public void warn(String s);
	public void error(String s);
	public void warn(String s, Throwable e);
	public void error(String s, Throwable e);
	public void crash(String s, Throwable e);
}
