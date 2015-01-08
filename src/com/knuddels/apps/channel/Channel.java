package com.knuddels.apps.channel;

public class Channel {
	private String name = "";
	private ChannelConfiguration configuration;
	
	public Channel(String name) {
		this.name			= name;
		this.configuration	= new ChannelConfiguration();
	}
	
	public String getName() {
		return this.name;
	}
	
	public ChannelConfiguration getChannelConfiguration() {
		return this.configuration;
	}
}
