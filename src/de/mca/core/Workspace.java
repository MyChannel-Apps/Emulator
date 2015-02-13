package de.mca.core;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;

public class Workspace {
	private String path					= "";
	private PropertyChangeSupport event = new PropertyChangeSupport(this);
	private ArrayList<App> apps			= new ArrayList<App>();
	private App app_active;
	
	public Workspace(String path) {
		if(path == null) {
			this.path = String.format("%s%sKnuddels%sApps%s", System.getProperty("user.home"), File.separator, File.separator, File.separator);
			return;
		}
		
		this.path = path;
	}
	
	public Workspace() {
		this(null);
	}
	
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		event.addPropertyChangeListener(listener);
	}
	
	public void removePropertyChangeListener(PropertyChangeListener listener) {
		event.removePropertyChangeListener(listener);
	}
	
	public String getPath() {
		return this.path;
	}
	
	public boolean hasApps() {
		return !this.apps.isEmpty();
	}
	
	public void init() {
		System.out.println("Workspace: " + this.getPath());
		File file = new File(this.getPath());
		file.mkdirs();
		
		file.list(new FilenameFilter() {
			@Override
			public boolean accept(File current, String name) {
				if(new File(current, name).isDirectory() && new File(current, name + File.separator + "app.config").exists() && new File(current, name + File.separator + "main.js").exists()) {
					App app = new App(current + File.separator + name);
					event.firePropertyChange("addApp", null, app);
					apps.add(app);
					return true;
				}
				
				return false;
			}
		});
		
		event.firePropertyChange("initComplete", hasApps(), !hasApps());
	}
	
	public void selectApp(String name) {
		for(App app : apps) {
			if(app.getConfig("appName") == name) {
				event.firePropertyChange("activateApp", app_active, app);
				app_active = app;
				return;
			}
		}
	}

	public void exploreApp(String name) {
		for(App app : apps) {
			if(app.getConfig("appName") == name) {
				SystemHelper.openExplorer(new File(app.getPath()));
				return;
			}
		}
	}

	public void deleteApp(String name) {
		for(App app : apps) {
			if(app.getConfig("appName") == name) {
				ArrayList<App> old_apps = apps;
				
				if(app_active == app) {
					event.firePropertyChange("deactivateApp", app, null);
					app_active = null;
				}
				
				SystemHelper.deleteDirectory(new File(app.getPath()));
				event.firePropertyChange("deleteApp", null, app);
				apps.remove(app);
				event.firePropertyChange("reloadApps", old_apps, apps);
				return;
			}
		}
	}
}
