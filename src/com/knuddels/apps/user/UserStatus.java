package com.knuddels.apps.user;

public enum UserStatus {
	Newbie("Newbie"),
	Family("Family"),
	Stammi("Stammi"),
	HonoryMember("HonoryMember"),
	Admin("Admin"),
	SystemBot("SystemBot"),
	Sysadmin("Sysadmin"),
	AppBot("AppBot");
	
	private String status;

	private UserStatus(String status) {
		this.status = status;
	}

	public String getValue() {
		return status;
	}
}
