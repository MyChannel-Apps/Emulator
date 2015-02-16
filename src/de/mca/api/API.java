package de.mca.api;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import de.mca.core.Main;

public class API {	
	public static JSONObject request(Data object) {
		try {
			JSONParser parser	= new JSONParser();
			Request http		= new Request(Request.Method.POST, "www.mychannel-apps.de");
			http.addHeader("User-Agent",		Main.version(false, "gui"));
			http.addHeader("Content-Length",	object.getLength());
			http.setData(object);
			return (JSONObject) parser.parse(http.exec("/api"));
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
}
