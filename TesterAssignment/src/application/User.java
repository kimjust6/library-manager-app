package application;

public abstract class User extends Person
{
	
	private String m_name = "";
	private String m_email = "";
	private String m_phoneNo = "";
	private String m_username = "";
	private String m_password = "";
	
	
	boolean login()
	{
		// TODO Auto-generated method stub
		return false;
	}
	
	
	//accessor and modifier functions
	 public String getM_name() {
		return m_name;
	}

	public void setM_name(String m_name) {
		this.m_name = m_name;
	}



	public String getM_email() {
		return m_email;
	}



	public void setM_email(String m_email) {
		this.m_email = m_email;
	}



	public String getM_phoneNo() {
		return m_phoneNo;
	}



	public void setM_phoneNo(String m_phoneNo) {
		this.m_phoneNo = m_phoneNo;
	}



	public String getM_username() {
		return m_username;
	}



	public void setM_username(String m_username) {
		this.m_username = m_username;
	}



	public String getM_password() {
		return m_password;
	}



	public void setM_password(String m_password) {
		this.m_password = m_password;
	}





}
