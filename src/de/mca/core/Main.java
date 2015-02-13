package de.mca.core;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.jar.Manifest;
import java.util.jar.Attributes;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import com.knuddels.apps.core.RhinoApp;
import de.mca.ui.window.Emulator;

@SuppressWarnings("static-access")
public class Main {
	private Emulator window;
	private static CommandLine cli;
	private static Options options;
	private Workspace workspace;
	
	public static void main(String arguments[]) {
		CommandLineParser parser	= new GnuParser();
		options						= new Options();
		options.addOption("h", "help", false, "print this message");
		options.addOption("v", "version", false, "print the current version");
		options.addOption("e", "emulator", false, "Start the emulator");
		options.addOption(OptionBuilder.withLongOpt("logfile").withArgName("file").hasArg().withDescription("use given file for log").create("l"));
		
		try {
            cli = parser.parse(options, arguments);
        } catch (ParseException e) {
        	help();
			System.exit(-1);
            return;
        }
		
		if(cli.hasOption("h")) {
			help();
			System.exit(-1);
            return;
        }
		
		if(cli.hasOption("v")) {
			version();
			System.exit(-1);
            return;
        }
		
		if(cli.hasOption("e")) {
			new Main();
            return;
        }
		
		String[] args = cli.getArgs();
		if(args.length == 0) {
			help();
			return;
		}
		
		if(cli.hasOption("l")) {
			System.out.println("Logfile: " + cli.getOptionValue("l"));
        }
		
		System.out.println("App Path: " + args[0]);
		
		try {
			RhinoApp container = new RhinoApp(args[0]);
			container.load();
			container.call("App");
		} catch(Exception e) {
			System.err.println("PANIC: " + e.toString());
		}
 	}
	
	public static void help() {
		new HelpFormatter().printHelp("Apps [options] <directory>", options, false);
	}
	
	public static void version() {
		String build_date			= "Unknown";
		String build_version		= "Unknown";
		
		try {
			InputStream stream		= Main.class.getResourceAsStream("/META-INF/MANIFEST.MF");
	        Manifest manifest		= new Manifest(stream);
	        Attributes attributes	= manifest.getMainAttributes();
	        build_date				= attributes.getValue("Built-Date");
	        build_version			= attributes.getValue("Version");
	    } catch(IOException e) {
	    	/* Do Nothing */
	    }
		
		System.out.println("Emulator " + build_version + " (cli) (built: " + build_date + ")");
		System.out.println("Copyright (c) 2015 MyChannel-Apps.de");
		System.out.println(RhinoApp.getRhinoVersion());
	}
	
	public Main() {
		this.workspace		= new Workspace();
		this.window			= new Emulator();

        this.workspace.addPropertyChangeListener(new PropertyChangeListener() {
        	@SuppressWarnings("unchecked")
			@Override
        	public void propertyChange(PropertyChangeEvent event) {
        		switch(event.getPropertyName()) {
        			case "addApp":
                		window.addApp((App) event.getNewValue());
        			break;
        			case "initComplete":
                		window.initComplete();
        			break;
        			case "activateApp":
        				window.selectApp((App) event.getNewValue());
        			break;
        			case "reloadApps":
        				window.reloadApps((ArrayList<App>) event.getNewValue());
        			break;
        			case "deactivateApp":
        				window.removeSelection();
        			break;
        			case "deleteApp":
        				window.removeApp((App) event.getNewValue());        				
        			break;
        			case "refresh":
        				window.redraw();
        			break;
        		}
        	}
		});
        
        this.window.addPropertyChangeListener(new PropertyChangeListener() {
        	@Override
        	public void propertyChange(PropertyChangeEvent event) {
        		switch(event.getPropertyName()) {
        			case "selectApp":
        				workspace.selectApp((String) event.getNewValue());
        			break;
        			case "exploreApp":
        				workspace.exploreApp((String) event.getNewValue());
        			break;
        			case "deleteApp":
        				workspace.deleteApp((String) event.getNewValue());
        			break;
        		}
        	}
		});
        
        this.workspace.init();
        this.window.open();
	}
}
