package DatabaseTest;

import java.sql.Connection;

public class Main {
	
	
    public static void main(String[] args) {
    	PostgresqlTest app = new PostgresqlTest();
        Connection conn = app.connect();
        app.create_table(conn, "admins" );
        app.insert(conn, "admins", "johnsnow", "myverysecurepassword", "Librarian");
        app.insert(conn, "admins", "harryPotter", "myverysecurepassword", "Admin");
        app.select(conn,"admins");
        app.update(conn,"admins","harryPotter","myverysecurepassword2");
        app.select(conn,"admins");
        
        
    }
}
