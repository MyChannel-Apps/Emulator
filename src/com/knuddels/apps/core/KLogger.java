package com.knuddels.apps.core;

public class KLogger {
	public void debug(String message) {
		// grey
		System.out.println(String.format("DEBUG: %s", message));
	}
	
	public void info(String message) {
		// black
		System.out.println(String.format("INFO: %s", message));
	}
	
	public void warn(String message) {
		// yellow
		System.out.println(String.format("WARN: %s", message));
	}
	
	public void error(String message) {
		// red
		System.err.println(String.format("ERROR: %s", message));
	}

	public void fatal(String message) {
		// red, bold
		System.err.println(String.format("FATAL: %s", message));
	}
}
