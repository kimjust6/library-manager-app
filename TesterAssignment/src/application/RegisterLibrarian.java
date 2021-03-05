package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.postgresql.util.PSQLException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import DatabaseTest.postgreSQLHeroku;

public class RegisterLibrarian implements AutoCloseable {

	private Stage stage;
	private Scene scene;
	
	public RegisterLibrarian(Stage stage, Scene scene) {
		this.stage = stage;
		this.scene = scene;
	}

	public Scene register() throws Exception {
		GridPane pane = new GridPane();
		pane.setAlignment(Pos.CENTER);
		pane.setPadding(new Insets(11.5, 12.5, 13.5, 14.5));
		pane.setHgap(5.5);
		pane.setVgap(5.5);
			
		TextField usernameField = new TextField();
		PasswordField passwordField = new PasswordField();
		TextField fnameField = new TextField();
		TextField lnameField = new TextField();
		TextField emailField = new TextField();
		TextField phoneField = new TextField();
		
		pane.add(new Label("Username:"), 0, 1);
		pane.add(usernameField, 1, 1);
		pane.add(new Label("Password:"), 0, 2);
		pane.add(passwordField, 1, 2);
		pane.add(new Label("First Name:"), 0, 3);
		pane.add(fnameField, 1, 3);
		pane.add(new Label("Last Name:"), 0, 4);
		pane.add(lnameField, 1, 4);
		pane.add(new Label("Email:"), 0, 5);
		pane.add(emailField, 1, 5);
		pane.add(new Label("Phone No:"), 0, 6);
		pane.add(phoneField, 1, 6);

		Button btn = new Button("Submit");
		btn.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent arg0) {
				String username = usernameField.getText();
				String password = passwordField.getText();
				String fname = fnameField.getText();
				String lname = lnameField.getText();
				String email = emailField.getText();
				String phone = phoneField.getText();
				
				String query1 = String.format("insert into %s values(?,?);", postgreSQLHeroku.TABLE_USERLOGINS);
				String query2 = String.format("insert into %s values(?,?,?,?,?,?,?)", postgreSQLHeroku.TABLE_USERS);
				
				if(username == "") {
					AlertBox.display("Error", "Please enter a username!");
				} else if(password == "") {
					AlertBox.display("Error", "Please enter a password!");
				} else if(fname == "" ) {
					AlertBox.display("Error", "Please enter a first name!");
				} else if(lname == "" ) {
					AlertBox.display("Error", "Please enter a first name!");
				} else {
					try (Connection connection = DriverManager.getConnection(postgreSQLHeroku.DATABASE_URL, postgreSQLHeroku.DATABASE_USERNAME, postgreSQLHeroku.DATABASE_PASSWORD);
							PreparedStatement insertUserLogin = connection.prepareStatement(query1);
							PreparedStatement insertUser = connection.prepareStatement(query2)) {

						connection.setAutoCommit(false);

						insertUserLogin.setString(1, username);
						insertUserLogin.setString(2, password);
						
						insertUser.setString(1, username);
						insertUser.setString(2, fname);
						insertUser.setString(3, lname);
						insertUser.setString(4, email);
						insertUser.setString(5, phone);
						insertUser.setDate(6, new java.sql.Date((new java.util.Date()).getTime()));
						insertUser.setString(7, postgreSQLHeroku.TYPE_LIBRARIAN);
						
						if(insertUserLogin.executeUpdate() == 1 && insertUser.executeUpdate() == 1) {
							AlertBox.display("Success", "Librarian registered!");
							connection.commit();
							
							try (AdminMenu adminMenu = new AdminMenu(stage, scene)) {
								stage.setScene(adminMenu.showMenu()); 
								stage.setTitle("Admin Menu");
							} catch (Exception e2) {
								e2.printStackTrace();
							}
						} else {
							AlertBox.display("Error", "Could not register librarian!");
							connection.rollback();
						}
						
					} catch (PSQLException e) {
						e.printStackTrace();
					} catch (SQLException e) {
						e.printStackTrace();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		});
		
        Button backBtn = new Button("Back");
        backBtn.setOnAction(new EventHandler<ActionEvent>(){
			@Override public void handle(ActionEvent arg0) {
				try (AdminMenu adminMenu = new AdminMenu(stage, scene)) {
					stage.setScene(adminMenu.showMenu()); 
					stage.setTitle("Admin Menu");
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		}); 

		pane.add(btn, 1, 7);
		pane.add(backBtn, 1, 9);
		
		Scene scene = new Scene(pane, 350, 450);
	    scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
	    return scene;
	}
	
	@Override
	public void close() throws Exception {
		// TODO Auto-generated method stub
		
	}
}
