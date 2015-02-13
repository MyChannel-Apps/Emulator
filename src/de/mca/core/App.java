package de.mca.core;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class App {
	private String path = "";
	private Properties config;
	
	public App(String path) {
		this.path			= path;
		this.config			= new Properties();
		
		refresh();
	}
	
	public void refresh() {
		InputStream input	= null;
	 
		try {
			input = new FileInputStream(path + File.separator + "app.config");
			this.config.load(input);
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if(input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}		
	}
	
	public String getPath() {
		return this.path;
	}
	
	public String getConfig(String key) {
		return this.config.getProperty(key);
	}
	
	public String toString() {
		return this.getConfig("appName");
	}
}
