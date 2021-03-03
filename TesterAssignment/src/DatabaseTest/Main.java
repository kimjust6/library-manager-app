package DatabaseTest;

import java.sql.Connection;

public class Main {
	
	
    public static void main(String[] args) {
    	postgreSQLHeroku app = new postgreSQLHeroku();
        Connection conn = app.connect();
        app.create_table(conn, "admins" );
        
        
        String array[] = new String[1];
        array[0] = "studentID SERIAL";
        array[1] = " varchar(50)";
        
        
        //app.create_table_1("Students",array );
        //app.insert(conn, app.tableUsers, "johnsnow", "myverysecurepassword", "Librarian");
        //app.insert(conn, "admins", "harryPotter", "myverysecurepassword", "Admin");
        //app.select(conn,"admins");
        //app.update(conn,"admins","harryPotter","myverysecurepassword3");
        //app.select(conn,"admins");
        
        
    }
}
