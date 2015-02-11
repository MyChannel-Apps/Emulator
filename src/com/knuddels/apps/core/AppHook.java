package com.knuddels.apps.core;

public class AppHook {
	public Object mayJoinChannel(Object user) {
		return null; // ChannelJoinPermission
	}
	
	public boolean maySendPublicMessage(Object user) {
		return false;
	}
	
	public void onAppStart() {
		
	}
	
	public void onKnuddelReceived(Object sender, Object receiver, Object knuddelAmount) {
		
	}
	
	public void onPrepareShutdown(int secondsTillShutdown) {
		
	}
	
	public void onPrivateMessage(Object privateMessage) {
		
	}
	
	public void onPublicMessage(Object publicMessage) {
		
	}
	
	public void onShutdown() {
		
	}
	
	public void onUserDiced(Object diceEvent) {
		
	}
	
	public void onUserJoined(Object user) {
		
	}
	
	public void onUserLeft(Object user) {
		
	}
	
	Object chatCommands = null;
}
