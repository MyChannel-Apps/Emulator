package com.knuddels.apps.persistence;

import java.lang.reflect.Member;
import java.lang.reflect.Method;

import org.mozilla.javascript.FunctionObject;
import org.mozilla.javascript.Scriptable;

public class AppPersistence extends Persistence {

	public AppPersistence(String arg0, Member arg1, Scriptable arg2) {
		super(arg0, arg1, arg2);
		// TODO Auto-generated constructor stub
	}

	public AppPersistence() {	
		super("AppPersistence", null, null);
	}
}
