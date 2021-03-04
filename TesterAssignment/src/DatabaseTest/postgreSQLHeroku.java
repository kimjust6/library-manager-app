package DatabaseTest;

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
    public static final String TABLE_ADMINS = "admins";
    public static final String TABLE_USERS = "users";
    public static final String TABLE_STUDENTS = "students";
    public static final String TABLE_LIBRARY = "library";
    public static final String TABLE_BORROWED_OBJECTS = "borrowedobjects";
    public static final String TABLE_WAITLIST_OBJECTS = "waitlistobjects";
    
    //final strings for column names in table user
    public static final String COL_USERNAME = "username"; 
    public static final String COL_PASSWORD = "password"; 
    public static final String COL_ADMINTYPE = "admintype";
    
    //final strings for user types
    public static final String TYPE_ADMIN = "admin";
    public static final String TYPE_MANAGER = "manager";
    public static final String TYPE_LIBRARIAN = "librarian";
    
    //final strings for column names in table library (book)    
    public static final String COL_TITLE = "title";
    public static final String COL_QTY_AVAIL = "qtyAvailable";
    public static final String COL_QTY_BOR = "qtyBorrowed";
    public static final String COL_TYPE = "type";
    public static final String COL_PUBLISHER = "publisher";
    public static final String COL_ID = "libid";
    
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
    
    //private variable that connects to database
    private Connection m_conn = null;
    
    //constructor
    public postgreSQLHeroku()
    {
//    	connect();
    }
    
    
    /**
     * Connect to the PostgreSQL database
     *
     * @return a Connection object
     */ 
    public void connect() {
    	
        try {
        	
            this.m_conn = DriverManager.getConnection(this.DATABASE_URL, this.DATABASE_USERNAME, this.DATABASE_PASSWORD);
            System.out.println("Connected to the PostgreSQL server successfully.");
        
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
        // connection closes automatically


    }
    
    public void disconnect() {
        try {
        	
            this.m_conn = DriverManager.getConnection(this.DATABASE_URL, this.DATABASE_USERNAME, this.DATABASE_PASSWORD);
            System.out.println("Connected to the PostgreSQL server successfully.");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return;
    }
    
    public boolean insert_librarian(String tableName, String username, String password, String usertype, String onHandQty, String borrowedQty)
    {
    	Statement statement;
    	boolean returnValue = true;
    	try
    	{
    		String query = String.format("INSERT INTO %s(username,password,usertype) VALUES ('%s','%s','%s')",tableName,username,password,usertype);
    		
    		System.out.println(query);
    		statement = this.m_conn.createStatement();
    		statement.executeUpdate(query);
    		System.out.println("Inserted values!");
    		returnValue = true;
    	}catch(Exception e)
    	{
    		System.out.println(e);
    		returnValue = false;
    	}
    	
    	return returnValue;
    }
    
    
    //****************************************************************************
    //USER CRUD OPERATIONS
    //****************************************************************************
    
    
  
    //****************************************************************************
    //LIBRARY OBJECT TABLE (BOOK) CRUD OPERATIONS
    //****************************************************************************
    public boolean insert_lib(String title, String author, String publisher, String mediatype, String qty)
    {
    	//get library length
    	return insert(this.TABLE_LIBRARY, title, author, publisher, mediatype, qty, "0");
    }
    
    
    public boolean return_lib(String id, String username)
    {
    	//increment the available qty of the  book
    	boolean returnValue1 = update(this.TABLE_LIBRARY, this.COL_QTY_AVAIL, this.COL_QTY_AVAIL + "+ 1",this.COL_ID, id );
    	//decrement the borrowed qty of the  book
    	boolean returnValue2 = update(this.TABLE_LIBRARY, this.COL_QTY_BOR, this.COL_QTY_BOR + "- 1", this.COL_ID, id );;
    	//remove the book from the borrowed table
    	
    	return returnValue1 && returnValue2;
    }
    
    public boolean checkout_lib(String id, String username)
    {
    	//String query = String.format("UPDATE %s SET %s='%s' WHERE %s = '%s';",tableName, colChange, newValue, colPK, PK);
    	
    	//decrement the available qty of the  book
    	boolean returnValue1 = update(this.TABLE_LIBRARY, this.COL_QTY_AVAIL, this.COL_QTY_AVAIL + "- 1",this.COL_ID, id );
    	//increment the borrowed qty of the book
    	boolean returnValue2 = update(this.TABLE_LIBRARY, this.COL_QTY_BOR, this.COL_QTY_BOR + "+ 1", this.COL_ID, id );
    	//add the book the borrowed table
    	insert(this.TABLE_BORROWED_OBJECTS, username, id);
    	
    	
    	return returnValue1 && returnValue2;
    }
    
    public boolean delete_lib(String colId, String id)
    {
    	return delete(this.TABLE_LIBRARY, colId, id);
    }
    
    //use this to find matching id
    public ResultSet search_lib_exact(String colId, String id)
    {
    	return select(this.TABLE_LIBRARY, colId, "=", id);
    }
    
    //use this for searching similar titles
    public ResultSet search_lib_partial(String colId, String id)
    {
    	return select(this.TABLE_LIBRARY, colId, " like ", "%" + id + "%");
    }
    
    //****************************************************************************
    //BASIC CRUD OPERATIONS
    //****************************************************************************
    
 
    
    
    public ResultSet select_all(String tableName, String colOrder)
    {
    	Statement statement;
    	ResultSet rs = null;
    	
    	try
    	{
    		String query = String.format("SELECT * FROM %s ORDER BY %s;", tableName, colOrder);
    		statement = this.m_conn.createStatement();
    		rs = statement.executeQuery(query);
    		int colCount = rs.getMetaData().getColumnCount();
    		while(rs.next())
    		{
    			for (int i = 1; i <= colCount; ++i )
    			{
    				System.out.print(rs.getString(i) + " ");
    			}
    			System.out.println("");
    		}
    	}catch(Exception e)
    	{
    		System.out.println(e);
    	}
    	
    	return rs;
    	
    }
    
    public ResultSet select(String tableName, String col, String operator, String tupleMatch )
    {
    	Statement statement;
    	ResultSet rs = null;
    	try
    	{
    		String query = String.format("SELECT * FROM %s WHERE %s %s '%s';", tableName, col, operator, tupleMatch);
    		statement = this.m_conn.createStatement();
    		rs = statement.executeQuery(query);
//    		while(rs.next())
//    		{
//    			System.out.println("Output: ");
//    			System.out.println(rs.getString("usertype") + ": ");
//    			System.out.print(rs.getString("username") + " : ");
//    			System.out.println(rs.getString("password") + " ");
//    			
//    		}
    	}catch(Exception e)
    	{
    		System.out.println(e);
    	}
    	
    	return rs;
    }
    
    public boolean insert(String tableName, String ... col)
    {
    	Statement statement;
    	boolean returnValue = true;
    	try
    	{
    		String query = "INSERT INTO " + tableName + " VALUES (";
    		
    		if (col.length > 0)
    		{
    			 query += "'" + col[0] + "'";
    		}
    		
    		for(int i = 1; i < col.length; ++i)
    		{
    			query += ", " + "'" + col[i] + "'"; 
    		}
    		query += ");";
    		
    		System.out.println(query);
    		statement = this.m_conn.createStatement();
    		statement.executeUpdate(query);
    		System.out.println("Table Inserted!");
    		returnValue = true;
    		
    	}catch(Exception e)
    	{
    		System.out.println(e);
    		returnValue = false;
    	}
    	
    	return returnValue;
    }
    

    //this method is a little hacky
    //right now it works well if the update values are integers, but not if they are strings.
    //if newValue and PK are strings, you will need to add additiona single quotations around them for it to work
    public boolean update(String tableName, String colChange, String newValue, String colPK, String PK)
    {
    	Statement statement;
    	boolean returnValue = true;
    	try
    	{
    		//String query = String.format("UPDATE %s SET %s='%s' WHERE %s = '%s';",tableName, colChange, newValue, colPK, PK);
    		String query = String.format("UPDATE %s SET %s=%s WHERE %s = %s;",tableName, colChange, newValue, colPK, PK);
    		System.out.println(query);
    		statement = this.m_conn.createStatement();
    		statement.executeUpdate(query);
    		System.out.println("Table updated!");
    		returnValue = true;;
    	}catch(Exception e)
    	{
    		System.out.println(e);
    		returnValue = false;
    	}
    	return returnValue;
    }
    
    
    public boolean delete(String tableName, String colPK, String PK)
    {
    	Statement statement;
    	boolean returnValue = true;
    	try
    	{
    		String query = String.format("DELETE FROM %s WHERE %s = '%s';",tableName, colPK, PK);
    		System.out.println(query);
    		statement = this.m_conn.createStatement();
    		statement.executeUpdate(query);
    		System.out.println("tuple deleted!");
    		returnValue = true;
    	} catch(Exception e)
    	{
    		System.out.println(e);
    		returnValue = false;
    	}
    	return returnValue;
    }
    
    public boolean create_table(String tableName, String ... col)
    {
    	Statement statement;
    	boolean returnValue = true;
    	try
    	{
    		String query = "CREATE TABLE IF NOT EXISTS " + tableName + "(";
    		
    		if (col.length > 0)
    		{
    			 query += col[0];
    		}
    		
    		for(int i = 1; i < col.length; ++i)
    		{
    			query += ", " + col[i]; 
    		}
    		
    		String anArray[] = col[0].split(" ");
    		query += String.format(", PRIMARY KEY( %s ));", anArray[0]);
    		
    		System.out.println(query);
    		statement = this.m_conn.createStatement();
    		statement.executeUpdate(query);
    		System.out.println("Table Created! (probably)");
    		returnValue = true;
    		
    	}catch(Exception e)
    	{
    		System.out.println(e);
    		returnValue = false;
    	}
    	
    	return returnValue;
    }
}


//****************************************************************************
//SQL Create Table Commands
//****************************************************************************



//create table borrowedobjects (
//		  username varchar(50),
//		  libid INT,
//		  PRIMARY KEY(username,libid)
//		);


//create table waitlistobjects (
//		  username varchar(50) NOT NULL,
//		  libid INT NOT NULL,
//		  waitid INT GENERATED ALWAYS AS IDENTITY,
//		  PRIMARY KEY(waitid)
//		);


//create table library (
//		  title            VARCHAR(20)        NOT NULL,
//		  author            VARCHAR(20)        NOT NULL,
//		  publisher        VARCHAR(20),
//		  media_type        VARCHAR(20)        NOT NULL,
//		  qtyAvailable INT NOT NULL,
//		  qtyBorrowed INT NOT NULL,
//		    
//		  libid INT GENERATED ALWAYS AS IDENTITY,
//		  PRIMARY KEY(libid)
//		);

//create table students (
//		fname VARCHAR(20) NOT NULL,
//		lname VARCHAR(20) NOT NULL,
//		studentLvl VARCHAR(20) NOT NULL,
//		studentno INT GENERATED ALWAYS AS IDENTITY,
//		PRIMARY KEY(studentno)
//		);
//


