package com.knuddels.apps.core;
import org.mozilla.javascript.ClassShutter;

public class RhinoAppPermissions implements ClassShutter {
	public boolean visibleToScripts(String class_name) {
		
		if(
			class_name.contains("java.lang.Object") ||
			class_name.contains("java.lang.String") ||
			class_name.contains("com.knuddels.apps.core.KnuddelsServer") ||
			class_name.contains("com.knuddels.apps.channel") ||
			class_name.contains("com.knuddels.apps.gamesupport.dice.DiceConfigurationFactory") ||
			class_name.contains("com.knuddels.apps.channel.ChannelJoinPermission") ||
			class_name.contains("com.knuddels.apps.tools.KnuddelAmount") ||
			class_name.contains("com.knuddels.apps.tools.StringOperations") ||
			class_name.contains("com.knuddels.apps.tools.RandomOperations") ||
			class_name.contains("com.knuddels.apps.channel.Channel") ||
			class_name.contains("com.knuddels.apps.channel.ChannelConfiguration") ||
			class_name.contains("com.knuddels.apps.channel.ChannelRestrictions") ||
			class_name.contains("com.knuddels.apps.channel.ChannelRights") ||
			class_name.contains("com.knuddels.apps.persistence.AppPersistence")
		) {
			System.out.println("	Permission Request: " + class_name);
			return true;
		}

		System.err.println("	Permission Request: " + class_name);
		return false;
	}
}
