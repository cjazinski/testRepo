package com.jazinski.helloBot;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;

import javax.net.ssl.HttpsURLConnection;

import org.apache.log4j.Logger;
import org.jibble.pircbot.PircBot;
import org.json.*;


public class MyBot extends PircBot {
	
	final Logger logger = Logger.getLogger(getClass().getName());
	
	public MyBot() {
		this.setName("testBot");
	}
	//AIzaSyCr5UT60DJQY8No2rMgI0nzxLz7WnxaPyE
	
	public void onMessage(String c, String u, String l, String h, String msg) {
		User user = new User(u, l, h);
		Message message = new Message(c, msg);
		System.out.println("First Word: " + getFirstWord(message));
		user.addMessage(message);
		user.printInfo();
		user.printMessages();
		try {
			getLatLong("78504");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		checkForCommand(user, message);
	}
	
	//https://maps.googleapis.com/maps/api/geocode/json?address=78504&key=AIzaSyCr5UT60DJQY8No2rMgI0nzxLz7WnxaPyE
	//https://api.darksky.net/forecast/abf2672d3299b8822b57541bc46288cb/26.2892001,-98.2322355
	public void onDisconnect() {
		System.out.println("Disconnected :-|");
	}
	
	public void checkForCommand(User user, Message msg) {
		if ("!sayHello".equals(getFirstWord(msg))) {
			sayHello(msg.getChannel(), msg.getMessage());
		}
	}
	
	private void getLatLong(String zip) throws Exception {
		URL APIUrl = new URL("https://maps.googleapis.com/maps/api/geocode/json?address=" + zip + "&key=AIzaSyCr5UT60DJQY8No2rMgI0nzxLz7WnxaPyE");
		
		HttpsURLConnection connection = (HttpsURLConnection) APIUrl.openConnection();
		connection.setDoOutput(true);
		connection.setDoInput(true);
		
		//OutputStream outputStream = connection.getOutputStream();
		//outputStream.write(rawMessage.getBytes(Charset.forName("UTF-8")));
		//outputStream.close();
		
		BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		
		String output = "";
		String outputCache = "";
		while ((outputCache = br.readLine()) != null) {
			output += outputCache;
		}
		br.close();
		// Parse the JSON return and check status value
		
		JSONObject obj = new JSONObject(output.toString());
		JSONArray res = (JSONArray) obj.get("results");
		JSONObject results = res.toJSONObject(res);
		JSONObject latLong = (JSONObject) results.get("location");
		System.out.println("Longitude: " + latLong.getString("lat"));
	}
	
	private void sayHello(String channel, String message) {
		// TODO Auto-generated method stub
		sendMessage(channel, "Hello, You sent me this: " + getDataAfterCommand(message));
	}

	private String getFirstWord(Message msg) {
		return msg.getMessage().split(" ", 2)[0];
	}
	
	private String getDataAfterCommand(String msg) {
		return msg.split(" ", 2)[1];
	}
}