package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class postgreSQLHeroku {

	//final strings to connect to the database
    public static final String DATABASE_URL = "jdbc:postgresql://ec2-54-209-43-223.compute-1.amazonaws.com:5432/d19rc88931g1bi";
    public static final String DATABASE_USERNAME = "luwrnpzmqrzvln";
    public static final String DATABASE_PASSWORD = "9b76f4cfa5a87feb4cf28e8b90e485b183bca39b2b20c09e323b4a04b524b2ce";
    
    //final strings for table names 
    public static final String TABLE_USERS = "users";
    public static final String TABLE_USERLOGINS = "userlogins";
    public static final String TABLE_STUDENTS = "students";
    public static final String TABLE_LIBRARY = "library";
    public static final String TABLE_BORROWED_OBJECTS = "borrowedobjects";
    public static final String TABLE_WAITLIST_OBJECTS = "waitlistobjects";
    
    //final strings for column names in table user
    public static final String COL_USERNAME = "username"; 
    public static final String COL_PASSWORD = "password"; 
    
    //final strings for column names in table admins
    public static final String COL_USERS_FNAME = "firstname";
    public static final String COL_USERS_LNAME = "lastname";
    public static final String COL_USERS_EMAIL = "email";
    public static final String COL_USERS_PHONE = "phone";
    public static final String COL_USERS_ADMINTYPE = "admintype";
	public static final String COL_USERS_HIREDATE = "hiredate";
    
    //final strings for user types
    public static final String TYPE_ADMIN = "Admin";
    public static final String TYPE_MANAGER = "Manager";
    public static final String TYPE_LIBRARIAN = "Librarian";
    
    //final strings for column names in table library (book)    
  	public static final String COL_TITLE = "title";
    public static final String COL_AUTHOR = "author";
    public static final String COL_PUBLISHER = "publisher";
    public static final String COL_MEDIA_TYPE = "media_type";
    public static final String COL_QTY_AVAIL = "qtyAvailable";
    public static final String COL_QTY_REQD = "qtyRequested";
    public static final String COL_QTY_BOR = "qtyBorrowed";
    public static final String COL_ID = "libid";
    public static final String COL_WAITID = "waitid";
    
    //final strings for library (book) types
    public static final String TYPE_BOOK = "book";
    public static final String TYPE_MAG = "magazine";
    public static final String TYPE_VID = "video";
    
    //final strings for column names in table student
    public static final String COL_STUD_NO = "studentno";
    public static final String COL_STUD_LVL = "studentLvl";
    public static final String COL_STUD_FNAME = "fname";
    public static final String COL_STUD_LNAME = "lname";

    //final strings for student levels
    public static final String TYPE_ALUM = "alumni";
    public static final String TYPE_CURR = "current";
    public static final String TYPE_UNDER = "undergrad";
    public static final String TYPE_GRAD = "graduate";
    
    
}