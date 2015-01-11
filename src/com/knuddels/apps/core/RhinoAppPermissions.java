package com.knuddels.apps.core;
import org.mozilla.javascript.ClassShutter;

public class RhinoAppPermissions implements ClassShutter {
	public boolean visibleToScripts(String class_name) {
		//System.err.println("Permission Request: " + class_name);
		
		if(
			class_name.contains("java.lang.String") ||
			class_name.contains("java.util.Timer") ||
			class_name.contains("java.util.TimerTask") ||
			class_name.contains("com.knuddels.apps.core.KnuddelsServer") ||
			class_name.contains("com.knuddels.apps.channel") ||
			class_name.contains("com.knuddels.apps.gamesupport.dice.DiceConfigurationFactory") ||
			class_name.contains("com.knuddels.apps.channel.ChannelJoinPermission") ||
			class_name.contains("com.knuddels.apps.tools.KnuddelAmount") ||
			class_name.contains("com.knuddels.apps.tools.StringOperations") ||
			class_name.contains("com.knuddels.apps.tools.RandomOperations")
		) {
			return true;
		}
		
		return false;
	}
}
