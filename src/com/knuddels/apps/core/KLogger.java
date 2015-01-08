package com.knuddels.apps.core;
import org.mozilla.javascript.ScriptableObject;

@SuppressWarnings("serial")
public class KLogger extends ScriptableObject {
	@Override
	public String getClassName() {
		return "KLogger";
	}
}
