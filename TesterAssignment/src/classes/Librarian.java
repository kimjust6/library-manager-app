package classes;

import java.sql.Date;

public class Librarian {
	private String username;
	private String fname;
	private String lname;
	private String email;
	private String phone;
	private Date date;
	
	public Librarian() {
		super();
		this.username = "";
		this.fname = "";
		this.lname = "";
		this.email = "";
		this.phone = "";
		this.date = null;
	}

	public Librarian(String username, String fname, String lname, String email, String phone, Date date) {
		super();
		this.username = username;
		this.fname = fname;
		this.lname = lname;
		this.email = email;
		this.phone = phone;
		this.date = date;
	}

	public String getUsername() { return username; }
	public void setUsername(String username) { this.username = username; }
	
	public String getFname() { return fname; }
	public void setFname(String fname) { this.fname = fname; }
	
	public String getLname() { return lname; }
	public void setLname(String lname) { this.lname = lname; }
	
	public String getEmail() { return email; }
	public void setEmail(String email) { this.email = email; }
	
	public String getPhone() { return phone; }
	public void setPhone(String phone) { this.phone = phone; }
	
	public Date getDate() { return date; }
	public void setDate(Date date) { this.date = date; }
}
