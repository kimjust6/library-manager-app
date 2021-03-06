package classes;

public class Student {
	private String fName;
	private String lName;
	private String studentLvl;
	private int studentNo;
	private int waitid;
	
	public Student()
	{
		
	}
	
	public Student(String fName, String lName, String studentLvl, int studentNo) {
		super();
		this.fName = fName;
		this.lName = lName;
		this.studentLvl = studentLvl;
		this.studentNo = studentNo;
	}
	
	public Student(int waitid, String fName, String lName, String studentLvl, int studentNo) {
		super();
		this.waitid = waitid;
		this.fName = fName;
		this.lName = lName;
		this.studentLvl = studentLvl;
		this.studentNo = studentNo;
	}
	
	public String getFName() {
		return fName;
	}
	public void setfName(String fName) {
		this.fName = fName;
	}
	public String getLName() {
		return lName;
	}
	public void setlName(String lName) {
		this.lName = lName;
	}
	public String getStudentLvl() {
		return studentLvl;
	}
	public void setStudentLvl(String studentLvl) {
		this.studentLvl = studentLvl;
	}
	public int getStudentNo() {
		return studentNo;
	}
	public void setStudentNo(int studentNo) {
		this.studentNo = studentNo;
	}
	public int getWaitid() {
		return waitid;
	}
	public void setWaitid(int waitid) {
		this.waitid = waitid;
	}
}
