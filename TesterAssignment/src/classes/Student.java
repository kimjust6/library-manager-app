package classes;

public class Student {
	private String fName;
	private String lName;
	private String studentLvl;
	private int studentNo;
	
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
	
	public String getfName() {
		return fName;
	}
	public void setfName(String fName) {
		this.fName = fName;
	}
	public String getlName() {
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
	
	
}
