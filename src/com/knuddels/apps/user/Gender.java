package com.knuddels.apps.user;

public enum Gender {
	Male("Male"),
	Female("Female"),
	Unknown("Unknown");
	
	private String gender;

	private Gender(String gender) {
		this.gender = gender;
	}

	public String getValue() {
		return gender;
	}
}
