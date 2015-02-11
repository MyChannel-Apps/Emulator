package com.knuddels.apps.core;
import com.knuddels.apps.channel.ChannelJoinPermission;
import com.knuddels.apps.gamesupport.dice.DiceEvent;
import com.knuddels.apps.messages.PrivateMessage;
import com.knuddels.apps.messages.PublicMessage;
import com.knuddels.apps.tools.KnuddelAmount;
import com.knuddels.apps.user.BotUser;
import com.knuddels.apps.user.User;

public class AppHook {
	public ChannelJoinPermission mayJoinChannel(User user) {
		return null; // ChannelJoinPermission
	}
	
	public boolean maySendPublicMessage(User user) {
		return false;
	}
	
	public void onAppStart() {
		
	}
	
	public void onKnuddelReceived(User sender, BotUser receiver, KnuddelAmount knuddelAmount) {
		
	}
	
	public void onPrepareShutdown(int secondsTillShutdown) {
		
	}
	
	public void onPrivateMessage(PrivateMessage privateMessage) {
		
	}
	
	public void onPublicMessage(PublicMessage publicMessage) {
		
	}
	
	public void onShutdown() {
		
	}
	
	public void onUserDiced(DiceEvent diceEvent) {
		
	}
	
	public void onUserJoined(User user) {
		
	}
	
	public void onUserLeft(User user) {
		
	}
	
	//Object chatCommands = new Object();
}
