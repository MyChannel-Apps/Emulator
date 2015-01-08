package de.mca.core;
import com.knuddels.apps.core.RhinoApp;

public class Main {
	public static void main(String args[]) {
		RhinoApp container = new RhinoApp(args[0]);
		container.load();
		
		container.call("App");
	}
}
