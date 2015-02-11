package com.knuddels.apps.core;
import org.mozilla.javascript.Function;
import org.mozilla.javascript.ScriptableObject;

import com.knuddels.apps.channel.Channel;
import com.knuddels.apps.persistence.AppPersistence;
import com.knuddels.apps.user.BotUser;
import com.knuddels.apps.user.User;

@SuppressWarnings("serial")
public class KnuddelsServer extends ScriptableObject {
	private final static KnuddelsServer instance;
	private Channel channel;
	private int timer_id;
	private RhinoApp app;
	private AppPersistence persistence;
	
	static {
		instance = new KnuddelsServer();
	}
	
	public KnuddelsServer() {
		this.persistence		= new AppPersistence();
		this.channel			= new Channel("Testchannel");
		this.timer_id			= 1;
	}
	
	public void bindApp(RhinoApp app) {
		this.app = app;
	}
	
	public RhinoApp getApp() {
		return this.app;
	}

	public static KnuddelsServer get() {
		return instance;
	}
	
	@Override
	public String getClassName() {
		return "KnuddelsServer";
	}
	
	protected KLogger getDefaultLoggerInstance() {
		return this.getApp().getLogger();
	}
	
	private Channel getChannelInstance() {
		return this.channel;
	}
	
	private AppPersistence getAppPersistenceInstance() {
		return this.persistence;
	}

	public static void require(String fileName) {
		if(KnuddelsServer.get().getApp() == null) {
			KLogger.error("Executing script: " + fileName);
		} else {
			KLogger.debug("Executing script: " + fileName);
			KnuddelsServer.get().getApp().include(fileName);
		}
	}
	
	private int setIntervalInstance(Function fn, int delay) {
		int id = this.timer_id++; 
		/*
        ids[id] = new JavaAdapter(java.util.TimerTask,{run: fn});
        timer.schedule(ids[id],delay,delay);*/
        return id;
	}
	
	public static int setInterval(Function fn, int delay) {
		return KnuddelsServer.get().setIntervalInstance(fn, delay);
	}
	
	/* Public Methods */
	public static AppPersistence getPersistence() {
		return KnuddelsServer.get().getAppPersistenceInstance();
	}
	
	public static Channel getChannel() {
		return KnuddelsServer.get().getChannelInstance();
	}
	
	public static User getAppDeveloper() {
		return null;
	}
	
	public static String getAppId() {
		return "KnuddelsEMU.0." + KnuddelsServer.getAppName();
	}
	
	public static String getAppName() {
		return KnuddelsServer.get().getApp().getName();
	}
	
	public static String getAppVersion() {
		return KnuddelsServer.get().getApp().getVersion();
	}
	
	public static boolean userExists(String nick) {
		return false;
	}
	
	public static int getUserId(String nick) {
		return 0;
	}
	
	public static boolean canAccessUser(int userId) {
		return false;
	}
	
	public static String getNickCorrectCase(int userId) {
		return "";
	}
	
	public static User getUser(int userId) {
		return null;
	}
	
	public static void refreshHooks() {
		
	}
	
	public static KLogger getDefaultLogger() {
		return KnuddelsServer.get().getDefaultLoggerInstance();
	}
	
	public static BotUser getDefaultBotUser() {
		return null;
	}
	
	public static String getFullImagePath(String imageName) {
		return String.format("%s/%s", KnuddelsServer.get().getApp().getPath(), imageName);
	}
	
	public static String getFullSystemImagePath(String imageName) {
		return String.format("%s/%s", KnuddelsServer.get().getApp().getPath(), imageName);
	}
	
	public static boolean isTestSystem() {
		return true;
	}
	
	public static User[] getAppManagers() {
		return null;
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
