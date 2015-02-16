package de.mca.api;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map.Entry;

class Request {
	public enum Method {
		POST,
		GET
	}
	
	private String domain	= "";
	private Method method 	= Method.GET;
	private boolean cache	= false;
	private HttpURLConnection connection;
	private HashMap<String, Object> headers;
	private Data data;
	
	public Request(Method method, String domain) {
		this.headers	= new HashMap<String, Object>();
		this.method		= method;
		this.domain		= domain;
	}
	
	public void addHeader(String name, Object value) {
		this.headers.put(name, value);
	}

	public String exec(String path) {
		try {
			this.connection = (HttpURLConnection) new URL(String.format("http://%s%s", this.domain, path)).openConnection();
			
			if(this.method == Method.GET) {
				this.connection.setRequestMethod("GET");
			} else if(this.method == Method.POST) {
				this.connection.setRequestMethod("POST");
				this.connection.setDoInput(true);
			}
			
			this.connection.setDoOutput(true);
			this.connection.setUseCaches(this.cache);
			
			if(!this.headers.isEmpty()) {
				for(Entry<String, Object> header : this.headers.entrySet()) {
					connection.setRequestProperty(header.getKey(), String.format("%s", header.getValue()));
				}
			}
			
			if(!this.data.isEmpty()) {
				DataOutputStream wr = new DataOutputStream(this.connection.getOutputStream());
				wr.write(this.data.getQuery().getBytes());
				wr.flush();
				wr.close();
			}

			BufferedReader in = new BufferedReader(new InputStreamReader(this.connection.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();
	 
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
			
			return response.toString();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

	public void setData(Data data) {
		this.data = data;
	}
}