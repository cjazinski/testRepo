package com.jazinski.helloBot.IRC;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class UserMapper implements RowMapper<User> {
	public User mapRow(ResultSet rs, int rowNum) throws SQLException {
		User user = new User();
		user.setId(rs.getInt("id"));
		user.setUserName(rs.getString("username"));
		user.setLoginName(rs.getString("loginName"));
		user.setHostname(rs.getString("hostname"));
		//TODO implement a createdat
		return user;
	}
	
}