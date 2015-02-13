package de.mca.core;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.File;
import java.io.FilenameFilter;
import java.nio.file.Path;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

public class Workspace {
	private String path						= "";
	private PropertyChangeSupport event 	= new PropertyChangeSupport(this);
	private HashMap<String, App> apps		= new HashMap<String, App>();
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
	
	public PropertyChangeSupport getPropertyChangeListener() {
		return event;
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
				return registerApp(current, name);
			}
		});
		
		event.firePropertyChange("initComplete", hasApps(), !hasApps());
		new WorkspaceWatcher(this);
	}
	
	public boolean registerApp(File current, String name) {
		if(new File(current, name).isDirectory() && new File(current, name + File.separator + "app.config").exists() && new File(current, name + File.separator + "main.js").exists()) {
			if(appAlreadyRegistred(new File(current, name))) {
				refreshApp(name);
				System.err.println("App already registred. #1");
				return false;
			}
			
			App app = new App(current + File.separator + name);
			apps.put(name, app);
			event.firePropertyChange("addApp", null, app);
			return true;
		}
		
		return false;
	}
	
	public void refreshApp(String name) {
		App app = apps.get(name);
		if(app != null) {
			app.refresh();
			event.firePropertyChange("refresh", false, true);
		}
	}
	
	public boolean appAlreadyRegistred(File a) {
		return apps.containsKey(a.getName());
	}
	
	public void selectApp(String name) {
		App app = apps.get(name);
		
		if(app == null) {
			for(Entry<String, App> entry : apps.entrySet()) {
				App a = entry.getValue();
				if(a.getConfig("appName") == name) {
					app = a;
					break;
				}
			}
		}
		
		event.firePropertyChange("activateApp", app_active, app);
		app_active = app;
	}

	public void exploreApp(String name) {
		App app = apps.get(name);
		if(app != null) {
			SystemHelper.openExplorer(new File(app.getPath()));
		}
	}

	public void deleteApp(String name) {
		App app = apps.get(name);
		HashMap<String, App> old_apps;
		
		if(app == null) {
			for(Entry<String, App> entry : apps.entrySet()) {
				App a = entry.getValue();
				if(a.getConfig("appName") == name) {
					app = a;
					break;
				}
			}
		}
		
		if(app != null) {
			old_apps = apps;
			if(app_active == app) {
				event.firePropertyChange("deactivateApp", app, null);
				app_active = null;
			}
			
			SystemHelper.deleteDirectory(new File(app.getPath()));
			event.firePropertyChange("deleteApp", null, app);
			apps.remove(new File(app.getPath()).getName());
			event.firePropertyChange("reloadApps", old_apps, apps);
		}
		
		apps.remove(name);
	}
	
	private class WorkspaceWatcher implements Runnable {
		private Workspace workspace;
		
		public WorkspaceWatcher(Workspace workspace) {
			this.workspace = workspace;
			new Thread(this).start();
		}
		
		@Override
		public void run() {
			Path dir = new File(workspace.getPath()).toPath();
			
			try {
				WatchService watcher = dir.getFileSystem().newWatchService();
				dir.register(watcher, StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_DELETE, StandardWatchEventKinds.ENTRY_MODIFY);
				WatchKey watckKey = watcher.take();
				
				while(true) {
					List<WatchEvent<?>> events = watckKey.pollEvents();
					for(WatchEvent<?> event : events) {
						if(event.kind() == StandardWatchEventKinds.ENTRY_CREATE) {
							workspace.registerApp(dir.toFile(), event.context().toString());
						}
						
						if(event.kind() == StandardWatchEventKinds.ENTRY_DELETE) {
							workspace.deleteApp(event.context().toString());
						}
						
						if(event.kind() == StandardWatchEventKinds.ENTRY_MODIFY) {
							workspace.registerApp(dir.toFile(), event.context().toString());
						}
					}
				}
			} catch (Exception e) {
				System.out.println("Error: " + e.toString());
			}
		}
	}
}
