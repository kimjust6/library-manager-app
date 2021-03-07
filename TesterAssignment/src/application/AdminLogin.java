package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import DatabaseTest.postgreSQLHeroku;

public class AdminLogin implements AutoCloseable {
	
	private Stage stage;
	private Scene scene;
	
	public AdminLogin(Stage stage, Scene scene) {
		this.stage = stage;
		this.scene = scene;
	}
	
	public Scene showLoginPage() throws Exception {
		GridPane pane = new GridPane();
		pane.setAlignment(Pos.CENTER);
		pane.setPadding(new Insets(11.5, 12.5, 13.5, 14.5));
		pane.setHgap(5.5);
		pane.setVgap(5.5);
			
		TextField usernameField = new TextField();
		PasswordField passwordField = new PasswordField();
		Label title = new Label("LOG IN");
		title.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
		pane.add(title, 0, 0);
		pane.add(new Label("Username:"), 0, 1);
		pane.add(usernameField, 1, 1);
		pane.add(new Label("Password:"), 0, 2);
		pane.add(passwordField, 1, 2);
			
		Button btn = new Button("Login");
		
		btn.setOnAction((event) -> { this.handle(usernameField.getText(), passwordField.getText()); });
		pane.setOnKeyPressed((event) -> {
			if(event.getCode() == KeyCode.ENTER) {
				this.handle(usernameField.getText(), passwordField.getText());
			}
		});
		
        Button backBtn = new Button("Back");
        backBtn.setOnAction(new EventHandler<ActionEvent>(){
			@Override public void handle(ActionEvent arg0) {
				try (Home homePage = new Home(stage, scene)) {
					stage.setScene(homePage.showHomePage()); 
					stage.setTitle("Library");
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		});
        btn.setStyle("-fx-background-color: mediumaquamarine");
        backBtn.setStyle("-fx-background-color: coral");
		pane.add(btn, 1, 3);
		pane.add(backBtn, 1, 5);
		
		pane.setStyle("-fx-background-color: #79A6E0"); //lightblue
		Scene scene = new Scene(pane, 350, 450);
	    scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
	    return scene;
	}
	
	public void handle(String username, String password) {
		if(username != "" && password != "") {
			try (Connection connection = DriverManager.getConnection(postgreSQLHeroku.DATABASE_URL, postgreSQLHeroku.DATABASE_USERNAME, postgreSQLHeroku.DATABASE_PASSWORD)) {
				
				Statement statement = connection.createStatement();
				String query = "select * from " + postgreSQLHeroku.TABLE_USERLOGINS + " where " + postgreSQLHeroku.COL_USERNAME + "='" + username + "';";
									
				ResultSet queryResult = statement.executeQuery(query); 
				
				if(queryResult.next()) {
	    			if (queryResult.getString(postgreSQLHeroku.COL_PASSWORD).equals(password)) { 
	    				String query2 = "select * from " + postgreSQLHeroku.TABLE_USERS + " where " + postgreSQLHeroku.COL_USERNAME + "='" + username + "';";
	    				ResultSet userInfo = statement.executeQuery(query2);
	    				
	    				if(userInfo.next()) {
	    					String userType = userInfo.getString(postgreSQLHeroku.COL_USERS_ADMINTYPE);
							if(userType.equalsIgnoreCase(postgreSQLHeroku.TYPE_ADMIN)) {
								stage.setTitle("Admin Menu");
								try (AdminMenu adminMenu = new AdminMenu(stage, scene)) {
									stage.setScene(adminMenu.showMenu());
								} catch (Exception e) {
									e.printStackTrace();
								}
								
							} else if(userType.equalsIgnoreCase(postgreSQLHeroku.TYPE_LIBRARIAN)) {
								stage.setTitle("Librarian Menu");
								try (LibrarianMenu librarianMenu = new LibrarianMenu(stage, scene)) {
									stage.setScene(librarianMenu.showMenu());
								} catch (Exception e) {
									e.printStackTrace();
								}
								
							} else {
								System.out.println("You are not an admin or librarian!");
							}
	    				}
	    			} else {
	    				AlertBox.display("Error", "Invalid password!");
	    			}
	    		} else {
	    			AlertBox.display("Error", "User does not exist!");
	    		}
			} catch (SQLException e1) {
				e1.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (username == "") {
			AlertBox.display("Error", "Please enter a username!");
		} else if (password == "") {
			AlertBox.display("Error", "Please enter a password!");
		}
	}

	@Override
	public void close() throws Exception {
		// TODO Auto-generated method stub
		
	}
}
