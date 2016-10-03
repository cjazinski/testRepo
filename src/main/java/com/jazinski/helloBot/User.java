package com.jazinski.helloBot;

import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

public class User {
	
	final Logger logger = Logger.getLogger(getClass().getName());
	
	private String userName;
	private String loginName;
	private String hostName;
	private List<Message> messages = new ArrayList<Message>();
		
	public User() {
		logger.info("Error has occured :(");
	}
	
	public User(String u, String l, String h) {
		this.userName = u;
		this.loginName = l;
		this.hostName = h;
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
