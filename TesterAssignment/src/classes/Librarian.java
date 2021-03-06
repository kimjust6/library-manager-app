package classes;

import java.sql.Date;

public class Librarian {
	private String username;
	private String firstname;
	private String lastname;
	private String email;
	private String phone;
	private Date hiredate;
	
	public Librarian() {
		super();
		this.username = "";
		this.firstname = "";
		this.lastname = "";
		this.email = "";
		this.phone = "";
		this.hiredate = null;
	}

	public Librarian(String username, String fname, String lname, String email, String phone, Date date) {
		super();
		this.username = username;
		this.firstname = fname;
		this.lastname = lname;
		this.email = email;
		this.phone = phone;
		this.hiredate = date;
	}

	public String getUsername() { return username; }
	public void setUsername(String username) { this.username = username; }
	
	public String getFirstname() { return firstname; }
	public void setFirstname(String fname) { this.firstname = fname; }
	
	public String getLastname() { return lastname; }
	public void setLastname(String lname) { this.lastname = lname; }
	
	public String getEmail() { return email; }
	public void setEmail(String email) { this.email = email; }
	
	public String getPhone() { return phone; }
	public void setPhone(String phone) { this.phone = phone; }
	
	public Date getHiredate() { return hiredate; }
	public void setHiredate(Date date) { this.hiredate = date; }
}
