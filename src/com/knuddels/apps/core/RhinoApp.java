package com.knuddels.apps.core;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Properties;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Function;
import org.mozilla.javascript.NativeObject;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;
import org.mozilla.javascript.commonjs.module.ModuleScriptProvider;
import org.mozilla.javascript.commonjs.module.Require;
import org.mozilla.javascript.commonjs.module.RequireBuilder;
import org.mozilla.javascript.commonjs.module.provider.ModuleSourceProvider;
import org.mozilla.javascript.commonjs.module.provider.SoftCachingModuleScriptProvider;
import org.mozilla.javascript.commonjs.module.provider.UrlModuleSourceProvider;

public class RhinoApp {
	private String app_path		= "";
	private String app_version	= "";
	private String app_name		= "";
	private Context context;
	private Scriptable scope;
	
	public RhinoApp(String app_path) {
		this.app_path = app_path.replace("\\", "/");
		
		if(this.app_path.endsWith("/") || this.app_path.endsWith(File.separator)) {
			this.app_path = this.app_path.substring(0, this.app_path.length() - 1);
		}
		
		System.out.println("Path: " + this.app_path);
	}
	
	private String getPath() {
		return this.app_path;
	}
	
	private String getName() {
		return this.app_name;
	}
	
	private String getAbsoluteName() {
		return this.getName() + "@" + this.getVersion();
	}
	
	private String getVersion() {
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
			// Prepare for require("Filename"); function
			/*Object paths						= Arrays.asList(new URI("file:///" + this.getPath().replaceAll(" ", "%20") + "/"));
			ModuleSourceProvider sourceProvider = new UrlModuleSourceProvider(paths, null);
			ModuleScriptProvider scriptProvider = new SoftCachingModuleScriptProvider(sourceProvider);
			RequireBuilder builder				= new RequireBuilder();
			builder.setModuleScriptProvider(scriptProvider);*/
			this.context						= Context.enter();
			
			// Set language version
			this.context.setLanguageVersion(Context.VERSION_1_8);
			
			// Bind call permissions
			this.context.setClassShutter(new RhinoAppPermissions());
			this.scope							= this.context.initStandardObjects();
			
			// Bind KnuddelsServer
			ScriptableObject.putProperty(this.scope, "KnuddelsServer", Context.javaToJS(KnuddelsServer.get(), this.scope));
			//Require require = builder.createRequire(this.context, this.scope);
			
			// Define/install the require function
			//require.install(this.scope);
			
			// run the Code
			this.context.evaluateString(this.scope, api_content + app_content, this.getAbsoluteName(), 1, null);
		} catch(Exception e) {
	         e.printStackTrace();
		} finally {
			if(Context.getCurrentContext() != null) {
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
			NativeObject obj	= (NativeObject) test;
			Object[] o			= obj.getIds();
			
			for(Object a : o) {
				System.out.println(a.toString());
			}			
		} else {
			System.err.println("Unknown Call");
		}
	}

	public static String getRhinoVersion() {
		Context context = Context.getCurrentContext();
		
		if(context == null) {
			context = Context.enter();
		}
		
		return context.getImplementationVersion();
	}
	
	/* JS Methods */	
	public Object getPersistence() {
		return null; // AppPersistence
	}
	
	public Object getChannel() {
		return null; // Channel
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
		return null; // KLogger
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
