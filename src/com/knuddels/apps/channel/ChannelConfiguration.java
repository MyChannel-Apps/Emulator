package com.knuddels.apps.channel;

public class ChannelConfiguration {
	private ChannelRights rights;
	
	public ChannelConfiguration() {
		 this.rights = new ChannelRights();
	}
	
	public ChannelRights getChannelRights() {
		return this.rights;
	}
}
