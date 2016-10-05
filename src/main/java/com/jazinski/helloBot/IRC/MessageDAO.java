package com.jazinski.helloBot.IRC;

import java.util.List;
import javax.sql.DataSource;

public interface MessageDAO {
	public void setDataSource(DataSource ds);
	public void create(Integer userID, String channel, String message);
	public Message getMessage(Integer messageID);
	public List<Message> listMessages(Integer UserId);
	public void delete(Integer id);
	public void update(Integer id, String channel, String message);
}