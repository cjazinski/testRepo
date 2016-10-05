package com.jazinski.helloBot.IRC;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;

public class MessageJDBCTemplate implements MessageDAO {
	private DataSource dataSource;
	private JdbcTemplate jdbcTemplateObject;

	public void setDataSource(DataSource ds) {
		this.dataSource = ds;
		this.jdbcTemplateObject = new JdbcTemplate(dataSource);
	}

	public void create(Integer userID, String channel, String message) {
		String SQL = "INSERT INTO Message (userID, channel, message, createdAt) values (?, ?, ?, ?)";
		jdbcTemplateObject.update(SQL, userID, channel, message, currentDateTime());
		System.out.println("Created Message for UserID: " + userID);
		return; // Not really needed			
	}

	public Message getMessage(Integer messageID) {
		String SQL = "SELECT * FROM Message where ID = ?";
		Message msg = jdbcTemplateObject.queryForObject(SQL, new Object[] {messageID}, new MessageWrapper());
		return msg;
	}

	public List<Message> listMessages(Integer userID) {
		String SQL = "SELECT * FROM Message WHERE userId = ?";
		List<Message> messages = jdbcTemplateObject.query(SQL,  new Object[] {userID}, new MessageWrapper());
		return messages;
	}

	public void delete(Integer id) {
		String SQL = "DELETE FROM Message WHERE id = ?";
		jdbcTemplateObject.update(SQL, id);
		System.out.println("Deleted record with ID: " + id);
		return;			
	}

	public void update(Integer id, String channel, String message) {
		String SQL = "UPDATE Message SET channel = ?, message = ? WHERE id = ?";
		jdbcTemplateObject.update(SQL, channel, message, id);
		System.out.println("Updated record with ID: " + id);
		return;			
	}

	private String currentDateTime() {
		Date dt = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(dt);
	}
}