package application;

import java.sql.ResultSet;
import java.util.Scanner;
import DatabaseTest.postgreSQLHeroku;

public class Admin extends Person {

	public Admin() {}
	
	public boolean addLibrarian(Scanner in, postgreSQLHeroku DB) {
	
		System.out.println("Enter information for new librarian: ");
		System.out.print("Username: ");
		String username = in.nextLine();
		
		// update table structure to accommodate for password reset for first login
		System.out.print("Password: ");
		String password = in.nextLine();
		System.out.print("Name: ");
		String name = in.nextLine();
		System.out.print("Email: ");
		String email = in.nextLine();
		System.out.print("Phone: ");
		String phone = in.nextLine();
		
		if(DB.insert(DB.TABLE_USERS, username, password)) {
			return DB.insert(DB.TABLE_ADMINS, username, name, email, phone, "Librarian");
		}
		return false;
	}
	
	public boolean delLibrarian(Scanner in, postgreSQLHeroku DB) {
		System.out.print("Enter username of librarian to remove: ");
		String username = in.nextLine();
		
		if(DB.delete(DB.TABLE_USERS, DB.COL_USERNAME, username)) {
			return DB.delete(DB.TABLE_ADMINS, DB.COL_USERNAME, username);
		}
		return false;
	}
	
	public ResultSet viewLibrarians() {
		
		
		
		return null;
	}
	
	public void getLibrariansReport() {
		
	}
}
