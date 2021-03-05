package classes;

public class BorrowedBooksTableLine {
	private int studentno;
	private String fname;
	private String lname;
	private int libid;
	private String title;
	private String author;
	private String publisher;
	private String media_type;
	
	
	public BorrowedBooksTableLine()
	{
		
	}
	

	public BorrowedBooksTableLine(int studentno, String fname, String lname, int libid, String title, String author,
			String publisher, String media_type) {
		this.studentno = studentno;
		this.fname = fname;
		this.lname = lname;
		this.libid = libid;
		this.title = title;
		this.author = author;
		this.publisher = publisher;
		this.media_type = media_type;
	}


	public String getPublisher() {
		return publisher;
	}


	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}


	public int getStudentno() {
		return studentno;
	}
	public void setStudentno(int studentno) {
		this.studentno = studentno;
	}
	public String getFname() {
		return fname;
	}
	public void setFname(String fname) {
		this.fname = fname;
	}
	public String getLname() {
		return lname;
	}
	public void setLname(String lname) {
		this.lname = lname;
	}
	public int getLibid() {
		return libid;
	}
	public void setLibid(int libid) {
		this.libid = libid;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getMedia_type() {
		return media_type;
	}
	public void setMedia_type(String media_type) {
		this.media_type = media_type;
	}
	
	
}
