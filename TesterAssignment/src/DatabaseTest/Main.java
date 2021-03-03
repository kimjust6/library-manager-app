package DatabaseTest;

import java.sql.Connection;

public class Main {
	
	
    public static void main(String[] args) {
    	postgreSQLHeroku app = new postgreSQLHeroku();
        Connection conn = app.connect();
        app.create_table(conn, "admins" );
        app.insert(conn, app.tableUsers, "johnsnow", "myverysecurepassword", "Librarian");
        app.insert(conn, "admins", "harryPotter", "myverysecurepassword", "Admin");
        app.select(conn,"admins");
        app.update(conn,"admins","harryPotter","myverysecurepassword2");
        app.select(conn,"admins");
        
        
    }
}
