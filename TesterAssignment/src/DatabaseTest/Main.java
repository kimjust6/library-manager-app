package DatabaseTest;

import java.sql.Connection;

public class Main {
	
	
    public static void main(String[] args) {
    	postgreSQLHeroku db = new postgreSQLHeroku();

        
        db.create_table(db.TABLE_STUDENTS, "sid SERIAL", "firstname varchar(50)", "lastname varchar(50)");
        db.create_table("users", "id SERIAL", "username varchar(50)", "password varchar(50)", "userType varchar(50)");
        db.insert(db.TABLE_STUDENTS,"1","justin","kim");
        db.insert("users","2","gaben","mypassword",db.TYPE_ADMIN);
        db.update(db.TABLE_STUDENTS, "sid", "1", "lastname", "anewlastname");
        db.update(db.TABLE_STUDENTS, "sid", "1", "lastname", "anewerlastname");
        
//        db.insert(conn, db.tableUsers, "johnsnow", "myverysecurepassword", "Librarian");
//        db.insert(conn, "admins", "harryPotter", "myverysecurepassword", "Admin");
//        db.select(conn,"admins");
//        db.update(conn,"admins","harryPotter","myverysecurepassword3");
//        db.select(conn,"admins");
        
    }
}
