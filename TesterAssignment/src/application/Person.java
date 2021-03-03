package application;

public abstract class Person {
	private String m_name;
	private String m_email;
	private String m_phoneNo;

	public Person() {
		this.m_name = "";
		this.m_email = "";
		this.m_phoneNo = "";
	}
	
	public Person(String m_name, String m_email, String m_phoneNo) {
		this.m_name = m_name;
		this.m_email = m_email;
		this.m_phoneNo = m_phoneNo;
	}

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
}
