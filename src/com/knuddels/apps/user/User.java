package com.knuddels.apps.user;

public class User {
	public int getUserId() {
		return 0;
	}
	
	public String getNick() {
		return "";
	}
	
	public int getAge() {
		return 0;
	}
	
	public Gender getGender() {
		return Gender.Unknown;
	}
	
	public Object getRegDate() {
		return null; // Date
	}
	
	public UserStatus getUserStatus() {
		return null; //return UserStatus.Member;
	}
	
	public UserType getUserType() {
		return null; //return UserType.Human;
	}
	
	public Object getPersistence() {
		return null; // UserPersistence
	}
	
	public void sendPrivateMessage(String message) {
		
	}
	
	public void sendPostMessage(String topic, String text) {
		
	}
	
	public boolean isChannelOwner() {
		return false;
	}
	
	public boolean isAppManager() {
		return false;
	}
	
	public boolean isMuted() {
		return false;
	}
	
	public boolean isColorMuted() {
		return false;
	}
	
	public boolean isLocked() {
		return false;
	}
	
	public boolean isChannelModerator() {
		return false;
	}
	
	public boolean isEventModerator() {
		return false;
	}
	
	public boolean isAppDeveloper() {
		return false;
	}
	
	public String getProfileLink(String displayText) {
		return String.format("°>%s|/serverpp \"<°", (displayText == null ? this.getNick() : displayText));
	}
	
	public boolean isOnlineInChannel() {
		return false;
	}
	
	public Object getKnuddelAmount() {
		return null; // KnuddelAmount
	}
	
	public boolean isOnline() {
		return false;
	}
	
	public String getReadme() {
		return "";
	}
	
	public int getOnlineMinutes() {
		return 0;
	}
	
	public boolean isAway() {
		return false;
	}
	
	public boolean equals(Object user) {
		return false;
	}
}
