package com.jazinski.helloBot.IRC;

import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

public class User {
	
	final Logger logger = Logger.getLogger(getClass().getName());
	
	private Integer id;
	private String userName;
	private String loginName;
	private String hostName;
	private Message message;
	private List<Message> messages = new ArrayList<Message>();
	
	// User shouldn't worry about the Message impl - We are using IoC
	// This Message class will be given to User at init time by Spring
	// Example of DI at INIT - Also can do via setter
	public User(Message message) {
		this.message = message;
		logger.info("Error has occured :(");
	}
	
	public User(String u, String l, String h) {
		this.userName = u;
		this.loginName = l;
		this.hostName = h;
	}
	
	public User() {
		
	}
	
	public void addMessage(Message msg) {
		messages.add(msg);
	}
	
	public void printMessages() {
		for (Message msg : messages) {
			msg.printMessage();
		}
	}
	//TODO Remove me after debug
	public void printInfo() {
		System.out.println("User: " + userName);
		System.out.println("Login: " + loginName);
		System.out.println("Host: " + hostName);
	}
	
	public Message getMessage() {
		return message;
	}
	
	public void setMessage(String channel, String msg) {
		message.setChannel(channel);
		message.setMessage(msg);
		messages.add(message);
	}
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer i) {
		id = i;
	}
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getHostname() {
		return hostName;
	}

	public void setHostname(String hostname) {
		this.hostName = hostname;
	}
}
