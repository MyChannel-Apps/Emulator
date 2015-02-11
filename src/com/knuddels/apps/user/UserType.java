package com.knuddels.apps.user;

public enum UserType {
	AppBot("AppBot"),
	SystemBot("SystemBot"),
	Human("Human");
	
	private String type;

	private UserType(String type) {
		this.type = type;
	}

	public String getValue() {
		return type;
	}
}
