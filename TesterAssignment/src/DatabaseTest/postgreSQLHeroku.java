package DatabaseTest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;



public class postgreSQLHeroku{

	//final strings to connect to the database
    private final String DATABASE_URL = "jdbc:postgresql://ec2-54-209-43-223.compute-1.amazonaws.com:5432/d19rc88931g1bi";
    private final String DATABASE_USERNAME = "luwrnpzmqrzvln";
    private final String DATABASE_PASSWORD = "9b76f4cfa5a87feb4cf28e8b90e485b183bca39b2b20c09e323b4a04b524b2ce";
    
    //final strings for table names 
    public final String TABLE_USERS = "users";
    public final String TABLE_STUDENTS = "students";
    public final String TABLE_LIBRARY = "library";
    
    //final strings for column names in table user
    public final String COL_USERNAME = "username"; 
    public final String COL_PASSWORD = "password"; 
    public final String COL_USERTYPE = "usertype";
    
    //final strings for user types
    public final String TYPE_ADMIN = "admin";
    public final String TYPE_MANAGER = "manager";
    public final String TYPE_LIBRARIAN = "librarian";
    
    //final strings for column names in table library (book)    
    public final String COL_TITLE = "title";
    public final String COL_QTY_AVAIL = "qtyAvailable";
    public final String COL_QTY_BOR = "qtyBorrowed";
    public final String COL_TYPE = "type";
    public final String COL_PUBLISHER = "publisher";
    
    //final strings for library (book) types
    public final String TYPE_BOOK = "book";
    public final String TYPE_MAG = "magazine";
    public final String TYPE_VID = "video";
    
    //final strings for column names in table student
    public final String COL_STUD_NO = "studentno";
    public final String COL_STUD_LVL = "studentLvl";
    
    //final strings for student levels
    public final String TYPE_ALUM = "alumni";
    public final String TYPE_CURR = "current";
    public final String TYPE_UNDER = "undergrad";
    public final String TYPE_GRAD = "graduate";
    
    //private variable that connects to database
    private Connection m_conn = null;
    
    //constructor
    public postgreSQLHeroku()
    {
    	m_conn = this.connect();
    }
    
    
    /**
     * Connect to the PostgreSQL database
     *
     * @return a Connection object
     */ 
    private Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
            System.out.println("Connected to the PostgreSQL server successfully.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return conn;
    }


    // temporary method - Harsh K
    
    public boolean loginQuery(String username, String password) {
    	try {
    		String query = "select * from " + TABLE_USERS + " where " + COL_USERNAME + "='" + username + "' and " + COL_PASSWORD + "='" + password + "';";
    		Statement statement = this.m_conn.createStatement();
    		ResultSet queryResult = statement.executeQuery(query); 
    		
    		return queryResult.next();
    	} catch (Exception e) {
    		e.printStackTrace();
    		return false;
    	}
    	
    }
    
    
    
//    public boolean create_table(Connection conn, String tableName)
//    {
//    	Statement statement;
//    	boolean returnValue = true;
//    	try
//    	{
//    		String query = "CREATE TABLE IF NOT EXISTS " + tableName + "(userid SERIAL, username varchar(50), password varchar(50), usertype varchar(100), primary key (username));";
//    		System.out.println(query);
//    		statement = this.m_conn.createStatement();
//    		statement.executeUpdate(query);
//    		System.out.println("Table Created! (probably)");
//    		returnValue = true;
//    	}catch(Exception e)
//    	{
//    		System.out.println(e);
//    		returnValue = false;
//    	}
//    	
//    	return returnValue;
//    }
    
    
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
    		query += ");";
    		
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
    
    public boolean insert_librarian(String tableName, String username, String password, String usertype)
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
    
    
    public boolean insert(String tableName, String ... col)
    {
    	Statement statement;
    	boolean returnValue = true;
    	try
    	{
    		String query = "INSERT INTO " + tableName + "VALUES (";
    		
    		if (col.length > 0)
    		{
    			 query += col[0];
    		}
    		
    		for(int i = 1; i < col.length; ++i)
    		{
    			query += ", " + col[i]; 
    		}
    		query += ");";
    		
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
    
    public boolean insert(Connection conn, String tableName, String username, String password, String usertype)
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
    
    public ResultSet select(Connection conn, String tableName)
    {
    	Statement statement;
    	ResultSet rs = null;
    	try
    	{
    		String query = String.format("SELECT * FROM %s;", tableName);
    		statement = this.m_conn.createStatement();
    		rs = statement.executeQuery(query);
    		while(rs.next())
    		{
    			System.out.println("Output: ");
    			System.out.println(rs.getString("usertype") + ": ");
    			System.out.print(rs.getString("username") + " : ");
    			System.out.println(rs.getString("password") + " ");
    			
    		}
    	}catch(Exception e)
    	{
    		System.out.println(e);
    	}
    	
    	return rs;
    	
    }
    
    public boolean update(Connection conn, String tableName, String username, String password)
    {
    	Statement statement;
    	boolean returnValue = true;
    	try
    	{
    		String query = String.format("UPDATE %s SET password='%s' WHERE username = '%s';",tableName, password, username);
    		//System.out.println(query);
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
}

