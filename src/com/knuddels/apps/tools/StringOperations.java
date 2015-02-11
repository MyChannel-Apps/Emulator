package com.knuddels.apps.tools;

public class StringOperations {
	public String escapeKCode() {
		return "".replaceAll("(<|>|\\||°)", "\\$1");
	}
	
	public boolean startsWith(String prefix) {
		return "".startsWith(prefix);
	}
	
	public boolean endsWith(String suffix) {
		return "".endsWith(suffix);
	}
	
	public int getPixelWidth(int fontSize, boolean isBold) {
		return 0;
	}
	
	public String limitString(int fontSize, boolean isBold, int maxPixelWidth, String abbreviationMarker) {
		return "";
	}
}
