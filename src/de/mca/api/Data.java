package de.mca.api;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;

@SuppressWarnings("serial")
public class Data extends HashMap<String, Object> {
	private String UTF8Encode(String s) {
		try {
			return URLEncoder.encode(s, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		return s;
	}
	
	public String getQuery() {
		StringBuffer query = new StringBuffer();
		
		for(Entry<String, Object> entry : entrySet()) {
			if(query.length() > 0) {
				query.append("&");
            }
			
			query.append(String.format("%s=%s", this.UTF8Encode(entry.getKey().toString()), this.UTF8Encode(entry.getValue().toString())));
		}
		
		return query.toString();
	}

	public int getLength() {
		return this.getQuery().length();
	}
}