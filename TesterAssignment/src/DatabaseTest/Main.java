package DatabaseTest;

import java.sql.Connection;

public class Main {
	
	
    public static void main(String[] args) {
    	PostgresqlTest app = new PostgresqlTest();
        Connection conn = app.connect();
        app.create_table(conn, "admins" );
        app.insert(conn, "admins", "johnsnow", "myverysecurepassword", "Librarian");
        app.select(conn,"admins");
        
        
    }
}
