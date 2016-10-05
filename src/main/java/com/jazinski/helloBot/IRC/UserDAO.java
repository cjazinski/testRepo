package com.jazinski.helloBot.IRC;

import java.util.List;
import javax.sql.DataSource;

public interface UserDAO {
	public void setDataSource(DataSource ds);
	public void create(String username, String loginname, String hostname);
	public User getUser(String username);
	public List<User> listUsers();
	public void delete(Integer id);
	public void update(Integer id, String username, String loginname, String hostname);
}