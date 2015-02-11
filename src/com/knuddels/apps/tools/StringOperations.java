package com.knuddels.apps.tools;

public class StringOperations {
	public static String escapeKCode() {
		return "".replaceAll("(<|>|\\||°)", "\\$1");
	}
	
	public static boolean startsWith(String prefix) {
		return "".startsWith(prefix);
	}
	
	public static boolean endsWith(String suffix) {
		return "".endsWith(suffix);
	}
	
	public static int getPixelWidth(int fontSize, boolean isBold) {
		return 0;
	}
	
	public static String limitString(int fontSize, boolean isBold, int maxPixelWidth, String abbreviationMarker) {
		return "";
	}
}
