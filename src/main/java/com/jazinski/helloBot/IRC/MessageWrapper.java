package com.jazinski.helloBot.IRC;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class MessageWrapper implements RowMapper<Message> {

	public Message mapRow(ResultSet rs, int rowNum) throws SQLException {
		Message msg = new Message();
		msg.setChannel(rs.getString("channel"));
		msg.setMessage(rs.getString("message"));
		return msg;
	}

}
