package com.knuddels.apps.core;
import org.mozilla.javascript.ScriptableObject;

import com.knuddels.apps.channel.Channel;

@SuppressWarnings("serial")
public class KnuddelsServer extends ScriptableObject {
	private final static KnuddelsServer instance;
	private KLogger logger;
	private Channel channel;
	private int timer_id;
	
	static {
		instance = new KnuddelsServer();
	}
	
	public KnuddelsServer() {
		this.logger				= new KLogger();
		this.channel			= new Channel("Beispiel");
		this.timer_id			= 1;
	}

	public static KnuddelsServer get() {
		return instance;
	}
	
	@Override
	public String getClassName() {
		return "KnuddelsServer";
	}
	
	private KLogger getDefaultLoggerInstance() {
		return this.logger;
	}
	
	private Channel getChannelInstance() {
		return this.channel;
	}

	public static void require(String fileName) {
		KLogger.debug("Executing script: " + fileName);
	}
	
	private int setIntervalInstance(Object fn, int delay) {
		int id = this.timer_id++; 
		/*
        ids[id] = new JavaAdapter(java.util.TimerTask,{run: fn});
        timer.schedule(ids[id],delay,delay);*/
        return id;
	}
	
	public static int setInterval(Object fn, int delay) {
		return KnuddelsServer.get().setIntervalInstance(fn, delay);
	}
	
	/* Public Methods */
	public Object getPersistence() {
		return null; // AppPersistence
	}
	
	public Object getChannel() {
		return KnuddelsServer.get().getChannelInstance();
	}
	
	public Object getAppDeveloper() {
		return null; // User
	}
	
	public String getAppId() {
		return "KnuddelsDE.12345." + this.getAppName();
	}
	
	public String getAppName() {
		return "AppName";
	}
	
	public int getAppVersion() {
		return 1;
	}
	
	public boolean userExists(String nick) {
		return false;
	}
	
	public int getUserId(String nick) {
		return 0;
	}
	
	public boolean canAccessUser(int userId) {
		return false;
	}
	
	public String getNickCorrectCase(int userId) {
		return "";
	}
	
	public Object getUser(int userId) {
		return null; // User
	}
	
	public static void refreshHooks() {
		
	}
	
	public Object getDefaultLogger() {
		return KnuddelsServer.get().getDefaultLoggerInstance();
	}
	
	public String getFullImagePath(String imageName) {
		return imageName;
	}
	
	public String getFullSystemImagePath(String imageName) {
		return imageName;
	}
	
	public boolean isTestSystem() {
		return true;
	}
	
	public Object getAppManagers() {
		return null; // User[]
	}
	
	public void debug(String msg) {
		
	}
	
	public void info(String msg) {
		
	}
	
	public void warn(String msg) {
		
	}
	
	public void error(String msg) {
		
	}
	
	public void fatal(String msg) {
		
	}
}
