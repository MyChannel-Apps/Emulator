package com.knuddels.apps.channel;

public class Channel {
	private String name = "";
	private ChannelConfiguration configuration;
	private ChannelRestrictions restrictions;
	private ChannelRights rights;
	
	public Channel(String name) {
		this.name			= name;
		this.configuration	= new ChannelConfiguration();
		this.restrictions	= new ChannelRestrictions();
		this.rights			= new ChannelRights();
	}
	
	public String getChannelName() {
		return this.name;
	}
	
	public ChannelRights getChannelRights() {
		return this.rights;
	}
	
	public ChannelRestrictions getChannelRestrictions() {
		return this.restrictions;
	}
	
	public ChannelConfiguration getChannelConfiguration() {
		return this.configuration;
	}
	
	public Object getOnlineUsers() {
		return null;
	}
}
