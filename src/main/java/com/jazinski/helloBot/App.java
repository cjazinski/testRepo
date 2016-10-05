package com.jazinski.helloBot;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.jibble.pircbot.IrcException;
import org.jibble.pircbot.NickAlreadyInUseException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Hello world!
 *
 */
public class App  {
	
	final static Logger logger = Logger.getLogger("MyApp - Main");
	
    public static void main( String[] args ) {	
		//@SuppressWarnings("resource")
		//ApplicationContext factory = new ClassPathXmlApplicationContext("applicationContext.xml"); 
        		
        //User user = (User) factory.getBean("userbean");
    		
    		MyBot bot = new MyBot();
    		bot.setAutoNickChange(true);
    		bot.setVerbose(true);
    		try {
    			bot.connect("us.undernet.org");
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
    		bot.joinChannel("#cjazinski");
        
    }
}
