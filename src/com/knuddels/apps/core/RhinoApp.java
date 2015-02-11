package com.knuddels.apps.core;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.EcmaError;
import org.mozilla.javascript.Function;
import org.mozilla.javascript.NativeObject;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;

public class RhinoApp {
	private KLogger logger;
	private String app_path		= "";
	private String app_version	= "";
	private String app_name		= "";
	private Context context;
	private Scriptable scope;
	
	public RhinoApp(String app_path) {
		KnuddelsServer.get().bindApp(this);
		
		this.logger		= new KLogger();
		this.app_path	= app_path.replace("\\", "/");
		
		if(this.app_path.endsWith("/") || this.app_path.endsWith(File.separator)) {
			this.app_path = this.app_path.substring(0, this.app_path.length() - 1);
		}
	}
	
	protected String getPath() {
		return this.app_path;
	}
	
	public String getName() {
		return this.app_name;
	}
	
	public KLogger getLogger() {
		return this.logger;
	}
	
	private String getAbsoluteName() {
		return this.getName() + "@" + this.getVersion();
	}
	
	protected String getVersion() {
		return this.app_version;
	}

	private String loadFile(String name) {
		try {
			byte[] encoded = Files.readAllBytes(Paths.get(this.app_path + File.separator + name));
			return new String(encoded, StandardCharsets.UTF_8);
		} catch (IOException e) {
			//e.printStackTrace();
		}
		
		return "";
	}
	
	private String loadAPI() {
		try {
			StringBuffer buffer		= new StringBuffer();
			BufferedReader reader	= new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/com/knuddels/apps/core/rhino.js"), "UTF-8"));
			
			for(int c = reader.read(); c != -1; c = reader.read()) {
				buffer.append((char) c);
			}

			return buffer.toString();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return "";
	}
	
	private void loadApp() {
		Properties config		= new Properties();
		String main				= this.loadFile("main.js");
		
		try {
			config.load(new StringReader(this.loadFile("app.config")));
			this.app_name		= config.getProperty("appName");
			this.app_version	= config.getProperty("appVersion");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		this.initApp(this.loadAPI(), main);
	}
	
	private void initApp(String api_content, String app_content) {
		try {
			this.context						= Context.enter();
			this.context.setOptimizationLevel(-1);
			
			// Set language version
			this.context.setLanguageVersion(Context.VERSION_1_8);
			
			// Bind call permissions
			this.context.setClassShutter(new RhinoAppPermissions());
			this.scope							= this.context.initStandardObjects();
			
			// Bind KnuddelsServer
			ScriptableObject.putProperty(this.scope, "KnuddelsServer", Context.javaToJS(KnuddelsServer.get(), this.scope));
			
			// run the Code
			KnuddelsServer.getDefaultLogger().debug("Executing script: main.js");
			this.context.evaluateString(this.scope, api_content + app_content, this.getAbsoluteName(), 1, null);
		} catch(Exception e) {
	        // e.getMessage();
		} finally {
			if(Context.getCurrentContext() != null) {
				System.err.println("Context EXIT");
				Context.exit();
			}
		}
	}
	
	public void load() {
		this.loadApp();
	}
	
	public Context getContext() {
		return this.context;
	}
	
	public Scriptable getScope() {
		return this.scope;
	}
	
	public void call(String fn) {
		Object test = this.scope.get(fn, this.scope);
		
		if(test instanceof NativeObject) {
			System.out.println("Call: " + fn);
			NativeObject obj	= (NativeObject) test;
			Object[] o			= obj.getIds();
		
			try {
				Function f = (Function) obj.get("onAppStart", this.scope);
				Object e = f.call(this.context, this.scope, this.scope, new Object[]{});
				System.out.println(Context.jsToJava(e, Object.class));
			} catch(EcmaError e) {
				
			}
			
			for(Object a : o) {
				System.out.println(a.toString());
			}	
		} else {
			System.err.println("Unknown Call: " + fn);
		}
	}
	
	public void eval(String content) {
		try {
			this.context.evaluateString(this.scope, content, this.getAbsoluteName(), 1, null);
		} catch(EcmaError e) {
			
		}
	}

	public void include(String fileName) {
		this.eval(this.loadFile(fileName));
	}

	public static String getRhinoVersion() {
		Context context = Context.getCurrentContext();
		
		if(context == null) {
			context = Context.enter();
		}
		
		return context.getImplementationVersion();
	}
}
