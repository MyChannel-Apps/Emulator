package de.mca.core;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.Scanner;

import sun.misc.IOUtils;

public class SystemHelper {
	public static boolean deleteDirectory(File directory) {
	    if(directory.exists()){
	        File[] files = directory.listFiles();
	        
	        if(null != files){
	            for(int i = 0; i < files.length; i++) {
	                if(files[i].isDirectory()) {
	                    deleteDirectory(files[i]);
	                } else {
	                    files[i].delete();
	                }
	            }
	        }
	    }
	    
	    return(directory.delete());
	}

	public static void openExplorer(File file) {
		try {
			java.awt.Desktop.getDesktop().open(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String fileContents(File file) {
		StringBuffer content = new StringBuffer();
		
		try {
			Scanner sc = new Scanner(file);
			
			while(sc.hasNextLine()){
				content.append(sc.nextLine());                     
			}
		} catch(Exception e) {
			/* Do Nothing */
		}
		
		return content.toString();
	}
}
