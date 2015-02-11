package com.knuddels.apps.tools;

public class KnuddelAmount {
	private double amount = 0.00;
	
	public KnuddelAmount(double knuddel) {
		this.amount = knuddel;
	}
	
	public double asNumber() {
		return this.amount;
	}
	
	public String toString() {
		return this.amount + "Kn";
	}
}
