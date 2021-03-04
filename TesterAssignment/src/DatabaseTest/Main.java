package DatabaseTest;

import java.sql.ResultSet;

public class Main {
	
	
    public static void main(String[] args) {
    	postgreSQLHeroku db = new postgreSQLHeroku();

        
//        db.create_table(db.TABLE_STUDENTS, "sid SERIAL", "firstname varchar(50)", "lastname varchar(50)");
//        db.create_table("users", "id SERIAL", "username varchar(50)", "password varchar(50)", "userType varchar(50)");
//        db.insert(db.TABLE_STUDENTS,"3","draco","malfoy");
//        db.insert(db.TABLE_STUDENTS,"1","harry","potter");
//        db.insert(db.TABLE_STUDENTS,"2","ron","weasley");
//        db.insert("users","2","gaben","mypassword",db.TYPE_ADMIN);
//        db.update(db.TABLE_STUDENTS, "sid", "1", "lastname", "anewlastname");
//        db.update(db.TABLE_STUDENTS, "sid", "1", "lastname", "anewerlastname");
        
//        db.select_all("students","firstname");
//        db.delete("students","sid","1");
//        db.select_all("students","firstname");
//        db.delete("students","sid","1");
        
//        db.insert(conn, db.tableUsers, "johnsnow", "myverysecurepassword", "Librarian");
//        db.insert(conn, "admins", "harryPotter", "myverysecurepassword", "Admin");
//        db.select(conn,"admins");
//        db.update(conn,"admins","harryPotter","myverysecurepassword3");
//        db.select(conn,"admins");
    	
//    	db.insert_library("harry potter","j.k. rowling","a publisher", db.TYPE_BOOK, "20");
    	//db.insert_lib("harry potter and the order of mcdonalds","j.k. rowling","a publisher", db.TYPE_BOOK, "10");
    	//db.insert_lib("ron weasley and the deathly gingers","j.k. rowling","a publisher", db.TYPE_BOOK, "10");
    	//db.checkout_lib("2");
    	//db.return_lib("1");
    	
    	//db.update("users","password","'myverysecurepassword3'","username","'harryPotter'");
    	ResultSet value = db.search_lib_exact("id","1");
    	
        
    }
}
