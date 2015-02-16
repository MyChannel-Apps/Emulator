package com.knuddels.apps.persistence;
import java.lang.reflect.Member;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.mozilla.javascript.FunctionObject;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;

@SuppressWarnings("serial")
public class Persistence  extends FunctionObject {
	public Persistence(String arg0, Member arg1, Scriptable arg2) {
		super(arg0, arg1, arg2);
		// TODO Auto-generated constructor stub
	}

	private String table = "app";
	
	@Override
	public String getClassName() {
		return "Persistence";
	}
	
	/*
	public Persistence() {
		this("app");
	}
	
	public Persistence(String table) {
		this.table = table;
		connect();
	}*/
	
	private Connection connect() {
		Connection connection = null;
		
	    try {
	      Class.forName("org.sqlite.JDBC");
	      connection		= DriverManager.getConnection("jdbc:sqlite:.persistence");
	      Statement stmt	= connection.createStatement();
	      
	      // AppPersistence
	      stmt.executeUpdate("CREATE TABLE IF NOT EXISTS `app` (`id` INTEGER PRIMARY KEY AUTOINCREMENT, `app` VARCHAR(255), `name` VARCHAR(255), `value` TEXT);");

	      // UserPersistence
	      stmt.executeUpdate("CREATE TABLE IF NOT EXISTS `user` (`id` INTEGER PRIMARY KEY AUTOINCREMENT, `user_id` VARCHAR(255), `name` VARCHAR(255), `value` TEXT);");
	      
	      // Users
	      stmt.executeUpdate("CREATE TABLE IF NOT EXISTS users (`id` INTEGER PRIMARY KEY AUTOINCREMENT, `nickname` VARCHAR(255), `age` INT(11), `gender` VARCHAR(255) DEFAULT('Unknown'));");
	    } catch(Exception e) {
	    	e.printStackTrace();
	    }
	    
	    return connection;
	}
	
	public boolean hasString(String key) {
		return false;
	}
	
	public void setString(String key, String value) {
		try {
			Connection con			= connect();
			PreparedStatement ps	= con.prepareStatement("INSERT INTO `" + this.table  + "` (`id`, `app`, `name`, `value`) VALUES (NULL, ?, ?, ?)");
			ps.setString(1,	"APP NAME");
			ps.setString(2,	key);
			ps.setString(3, value);
			ps.executeUpdate();
		    ps.close();
		    con.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public String getString(String key) {
		return getString(key, null);
	}
	
	public String getString(String key, String defaultValue) {
		Connection con	= null;
		PreparedStatement ps = null;
		ResultSet rs = null;
	
		try {
			con	= connect();
			ps	= con.prepareStatement("SELECT * FROM `" + this.table  + "` WHERE `name`=? AND `app`=? LIMIT 1");
			ps.setString(1, key);
			ps.setString(2, "APP NAME");
			rs = ps.executeQuery();
			
			if(rs.next()) {
				return rs.getString("value");
			}
		} catch(Exception e) {
			//e.printStackTrace();
		} finally {
		   if(con != null) {
			   try {
				   con.close();
			   } catch (SQLException e) {
				   e.printStackTrace();
			   }
		   }
		}
		
		return defaultValue;
	}
	
	public void deleteString(String key) {
		
	}

	public boolean hasNumber(String key) {
		return false;
	}
	
	public void setNumber(String key, int value) {
		
	}
	
	public int addNumber(String key, int value) {
		return 0;
	}
	
	public int getNumber(String key, int defaultValue) {
		return 0;
	}
	
	public void deleteNumber(String key) {
		
	}
	
	public boolean hasObject(String key) {
		return false;
	}
	
	public void setObject(String key, Object value) {
		
	}
	
	public Object getObject(String key, Object defaultValue) {
		return defaultValue;
	}
	
	public void deleteObject(String key) {
		
	}
}
