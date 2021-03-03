package DatabaseTest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author postgresqltutorial.com
 */
public class PostgresqlTest{

	//final strings to connect to the database
    private final String url = "jdbc:postgresql://ec2-54-209-43-223.compute-1.amazonaws.com:5432/d19rc88931g1bi";
    private final String user = "luwrnpzmqrzvln";
    private final String password = "9b76f4cfa5a87feb4cf28e8b90e485b183bca39b2b20c09e323b4a04b524b2ce";
    


    /**
     * Connect to the PostgreSQL database
     *
     * @return a Connection object
     */
    public Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, user, password);
            System.out.println("Connected to the PostgreSQL server successfully.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return conn;
    }


    
    public boolean create_table(Connection conn, String tableName)
    {
    	Statement statement;
    	boolean returnValue = true;
    	try
    	{
    		String query = "CREATE TABLE IF NOT EXISTS " + tableName + "(userid SERIAL, username varchar(50), password varchar(50), usertype varchar(100), primary key (username));";
    		System.out.println(query);
    		statement = conn.createStatement();
    		statement.executeUpdate(query);
    		System.out.println("Table Created! (probably)");
    		returnValue = true;;
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
    		statement = conn.createStatement();
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
    		statement = conn.createStatement();
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
    		statement = conn.createStatement();
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

