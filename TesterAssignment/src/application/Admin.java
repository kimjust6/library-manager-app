package application;

import java.util.Scanner;
import DatabaseTest.postgreSQLHeroku;

public class Admin extends Person {

//	private String username = "";

	public Admin() {
//		super(userInfo.getString(2), userInfo.getString(3), userInfo.getString(4));

//		this.username = userInfo.getString(1);
	}
	
	// method to verify if admin info is being read
	public void display() {
//		System.out.println(this.username + " : " + this.getM_name() + " : " + this.getM_email() + " : " + this.getM_phoneNo());
	}
	
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
	
	public boolean delLibrarian() {
		return false;
	}
	
	public String viewLibrarians() {
		return "";
	}
	
	public void getLibrariansReport() {
		
	}
}
