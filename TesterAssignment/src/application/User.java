package application;

import DatabaseTest.postgreSQLHeroku;

public class User extends Person
{
	private String m_username;
	private String m_password;
	
	public User() {
		this.m_username = "";
		this.m_password = "";
	}
	
	String getM_username() {
		return m_username;
	}

	void setM_username(String m_username) {
		this.m_username = m_username;
	}

	String getM_password() {
		return m_password;
	}
	
	void setM_password(String m_password) {
		this.m_password = m_password;
	}
	
	boolean login(String username, String password) {
		System.out.println("Username: " + username + ", Password: " + password);
		
		postgreSQLHeroku DB = new postgreSQLHeroku(); 
		
		return false;
	}
}
