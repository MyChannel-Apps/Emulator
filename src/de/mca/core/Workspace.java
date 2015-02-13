package de.mca.core;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Arrays;

public class Workspace {
	private String path					= "";
	private PropertyChangeSupport event = new PropertyChangeSupport(this);
	private ArrayList<App> apps			= new ArrayList<App>();
	
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
		
		String[] directories = file.list(new FilenameFilter() {
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
		
		System.out.println(Arrays.toString(directories));
		event.firePropertyChange("initComplete", false, true);
	}
}
