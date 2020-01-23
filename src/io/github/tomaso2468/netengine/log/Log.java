package io.github.tomaso2468.netengine.log;

public class Log {
	private static LogSystem logSystem = new ConsoleLog();
	
	public static void debug(String s) {
		logSystem.debug(s);
	}
	public static void info(String s) {
		logSystem.info(s);
	}
	public static void error(String s) {
		logSystem.error(s);
	}
	public static void error(String s, Throwable e) {
		logSystem.error(s, e);
	}
	public static void error(Throwable e) {
		logSystem.error("", e);
	}
	public static void crash(Throwable e) {
		logSystem.crash("The game has crashed.", e);
	}
	public static void warn(String s) {
		logSystem.warn(s);
	}
	public static void warn(String s, Throwable e) {
		logSystem.warn(s, e);
	}
	public static void warn(Throwable e) {
		logSystem.warn("", e);
	}
	
	public static LogSystem getLogSystem() {
		return logSystem;
	}
	
	public static void setLogSystem(LogSystem logSystem) {
		Log.logSystem = logSystem;
	}
}
