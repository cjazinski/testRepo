package com.jazinski.helloBot.IRC;

import java.util.Date;

public class Message {
	
	private String channel;
	private String message;
	private Date messageDate;
	
	public Message() {
		this.messageDate = new Date();
	}
	
	public Message(String chan, String msg) {
		this.channel = chan;
		this.messageDate = new Date();
		this.message = msg;
	}
	
	public void printMessage() {
		System.out.println("Message: " + message);
		System.out.println("When: " + messageDate.toString());
	}
	
	public String getChannel() {
		return channel;
	}
	
	public void setChannel(String c) {
		channel = c;
	}
	
	public String getMessage() {
		return message;
	}
	
	public void setMessage(String m) {
		message = m;
	}
		
}