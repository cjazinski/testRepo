package com.jazinski.helloBot;

import java.io.IOException;
import java.util.Map;

import org.apache.log4j.Logger;
import org.jibble.pircbot.IrcException;
import org.jibble.pircbot.NickAlreadyInUseException;
import org.jibble.pircbot.PircBot;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.dao.EmptyResultDataAccessException;

import com.jazinski.weather.Weather;
import com.jazinski.helloBot.IRC.*;


public class MyBot extends PircBot {
	
	final Logger logger = Logger.getLogger(getClass().getName());
	final Weather weatherAPI = new Weather("AIzaSyCr5UT60DJQY8No2rMgI0nzxLz7WnxaPyE");
	
	public MyBot() {
		this.setName("testBot");
	}

	public void onMessage(String channel, String username, String login, String hostname, String message) {
		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		UserJDBCTemplate userJDBCTemplate = (UserJDBCTemplate) context.getBean("userJDBCTemplate");
		MessageJDBCTemplate messageJDBCTemplate = (MessageJDBCTemplate) context.getBean("messageJDBCTemplate");
		
		// Let me Search for the user
		User user = null;
		try {
			user = userJDBCTemplate.getUser(username);
			userJDBCTemplate.update(user);
			System.out.println("Updated last access date...");
			System.out.println("Adding New Message..");
			messageJDBCTemplate.create(user.getId(), channel, message);
			
		} catch (EmptyResultDataAccessException ex) {
			System.out.println("Username: " + username + " was not found. Adding..");
			userJDBCTemplate.create(username, login, hostname);
			user = userJDBCTemplate.getUser(username);
			System.out.println("Adding New Message..");
			messageJDBCTemplate.create(user.getId(), channel, message);
		}
		user.printInfo();		
		checkForCommand(channel, message);
		((ClassPathXmlApplicationContext) context).close();
	}

	public void onDisconnect() {
		System.out.println("Disconnected :-|");
		try {
			this.reconnect();
			this.joinChannel("#cjazinski");
		} catch (NickAlreadyInUseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IrcException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void checkForCommand(String channel, String msg) {
		if ("!sayHello".equals(getFirstWord(msg))) {
			sayHello(channel, msg);
		}
		if ("!weather".equals(getFirstWord(msg)) || "!w".equals(getFirstWord(msg))) {
			sayWeather(channel, msg);
		}
	}
	
	private void sayWeather(String channel, String msg) {
		String zipCode = getDataAfterCommand(msg);
		try {
			Map<String, String> weather = weatherAPI.getWeather(zipCode);
			sendMessage(channel, "Weather for: " + weather.get("location") + " Currently: " + weather.get("summary") + " and " + weather.get("temp"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			sendMessage(channel, "Error getting the weather :(");
		}
		
	}	
	
	private void sayHello(String channel, String msg) {
		// TODO Auto-generated method stub
		sendMessage(channel, "Hello, You sent me this: " + getDataAfterCommand(msg));
	}

	private String getFirstWord(String msg) {
		return msg.split(" ", 2)[0];
	}
	
	private String getDataAfterCommand(String msg) {
		return msg.split(" ", 2)[1];
	}
}