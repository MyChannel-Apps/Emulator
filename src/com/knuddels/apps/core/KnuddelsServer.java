package com.knuddels.apps.core;
import java.util.HashMap;
import java.util.Timer;
import org.mozilla.javascript.ScriptableObject;
import com.knuddels.apps.channel.Channel;

@SuppressWarnings("serial")
public class KnuddelsServer extends ScriptableObject {
	private final static KnuddelsServer instance;
	private KLogger logger;
	private Channel channel;
	private int timer_id;
	private HashMap<Integer, Timer> timer_instances;
	
	static {
		instance = new KnuddelsServer();
	}
	
	public KnuddelsServer() {
		this.logger				= new KLogger();
		this.channel			= new Channel("Beispiel");
		this.timer_id			= 1;
		this.timer_instances	= new HashMap<Integer, Timer>();
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
	
	public static KLogger getDefaultLogger() {
		return KnuddelsServer.get().getDefaultLoggerInstance();
	}
	
	public static Object getDefaultBotUser() {
		return null;
	}
	
	private Channel getChannelInstance() {
		return this.channel;
	}
	
	public static Channel getChannel() {
		return KnuddelsServer.get().getChannelInstance();
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
}
