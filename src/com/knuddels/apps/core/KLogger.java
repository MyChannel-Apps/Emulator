package com.knuddels.apps.core;
import org.mozilla.javascript.ScriptableObject;

@SuppressWarnings("serial")
public class KLogger extends ScriptableObject {
	@Override
	public String getClassName() {
		return "KLogger";
	}
	
	public static void debug(String message) {
		// grey
		System.out.println(String.format("DEBUG: %s", message));
	}
	
	public static void info(String message) {
		// black
		System.out.println(String.format("INFO: %s", message));
	}
	
	public static void warn(String message) {
		// yellow
		System.out.println(String.format("WARN: %s", message));
	}
	
	public static void error(String message) {
		// red
		System.err.println(String.format("ERROR: %s", message));
	}

	
	public static void fatal(String message) {
		// red, bold
		System.err.println(String.format("FATAL: %s", message));
	}
	// STATUS: 
}
