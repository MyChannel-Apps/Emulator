package com.knuddels.apps.channel;
import org.mozilla.javascript.ScriptableObject;
import com.knuddels.apps.user.User;

@SuppressWarnings("serial")
public class Channel extends ScriptableObject {
	private String name = "";
	private ChannelConfiguration configuration;
	private ChannelRestrictions restrictions;
	private ChannelRights rights;
	
	@Override
	public String getClassName() {
		return "Channel";
	}
	
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
	
	public User[] getOnlineUsers() {
		return new User[] {};
	}
}
