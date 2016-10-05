package com.jazinski.helloBot.IRC;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;

public class UserJDBCTemplate implements UserDAO {
	private DataSource dataSource;
	private JdbcTemplate jdbcTemplateObject;

	public void setDataSource(DataSource ds) {
		this.dataSource = ds;
		this.jdbcTemplateObject = new JdbcTemplate(dataSource);
	}

	public void create(String username, String loginname, String hostname) {
		String SQL = "INSERT INTO User (username, loginname, hostname, createdat) values (?, ?, ?, ?)";
		jdbcTemplateObject.update(SQL, username, loginname, hostname, currentDateTime());
		System.out.println("Created Record for: " + username);
		return; // Not really needed				
	}

	public User getUser(String username) {
		String SQL = "SELECT * FROM User where username = ?";
		User user = jdbcTemplateObject.queryForObject(SQL,  new Object[] {username}, new UserMapper());
		return user;
	}

	public List<User> listUsers() {
		String SQL = " SELECT * FROM User";
		List<User> users = jdbcTemplateObject.query(SQL, new UserMapper());
		return users;
	}

	public void delete(Integer id) {
		String SQL = "DELETE FROM User WHERE id = ?";
		jdbcTemplateObject.update(SQL, id);
		System.out.println("Deleted record with ID: " + id);
		return;		
	}

	public void update(Integer id, String username, String loginname, String hostname) {
		String SQL = "UPDATE User SET username = ?, loginname = ?, hostname = ? WHERE id = ?";
		jdbcTemplateObject.update(SQL, username, loginname, hostname, id);
		System.out.println("Updated record with ID: " + id);
		return;		
	}
	
	private String currentDateTime() {
		Date dt = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(dt);
	}

	public void update(User user) {
		String SQL = "UPDATE User SET username = ?, loginname = ?, hostname = ?, updatedAt = ? WHERE id = ?";
		jdbcTemplateObject.update(SQL, user.getUserName(), user.getLoginName(), user.getHostname(), currentDateTime(), user.getId());
		System.out.println("Updated record with ID: " + user.getId());
		return;			
	}

}
