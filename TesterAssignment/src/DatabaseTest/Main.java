package DatabaseTest;

import java.sql.Connection;

public class Main {
	
	
    public static void main(String[] args) {
    	postgreSQLHeroku db = new postgreSQLHeroku();
        //Connection conn = db.connect();

        
        db.create_table("students", "sid SERIAL", "firstname varchar(50)", "lastname varchar(50)");
        
//        db.insert(conn, db.tableUsers, "johnsnow", "myverysecurepassword", "Librarian");
//        db.insert(conn, "admins", "harryPotter", "myverysecurepassword", "Admin");
//        db.select(conn,"admins");
//        db.update(conn,"admins","harryPotter","myverysecurepassword3");
//        db.select(conn,"admins");
        
    }
}
