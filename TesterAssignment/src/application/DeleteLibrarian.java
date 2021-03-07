package application;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.postgresql.util.PSQLException;

import DatabaseTest.postgreSQLHeroku;

public class DeleteLibrarian implements AutoCloseable {

	private Stage stage;
	private Scene scene;
	
	public DeleteLibrarian(Stage stage, Scene scene) {
		this.stage = stage;
		this.scene = scene;
	}
	
	public Scene delete() throws Exception {
		GridPane pane = new GridPane();
		pane.setAlignment(Pos.CENTER);
		pane.setPadding(new Insets(11.5, 12.5, 13.5, 14.5));
		pane.setHgap(5.5);
		pane.setVgap(5.5);
			
		TextField usernameField = new TextField();
		
		pane.add(new Label("Username:"), 0, 1);
		pane.add(usernameField, 1, 1);
		
		Button btn = new Button("Submit");
		btn.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent arg0) {
				
				String username = usernameField.getText();
				
				if(username == "") {
					AlertBox.display("Error", "Please enter a librarian username!");
				} else {
					String query1 = String.format("delete from %s where %s = ?;", postgreSQLHeroku.TABLE_USERLOGINS, postgreSQLHeroku.COL_USERNAME);
					String query2 = String.format("delete from %s where %s = ? and %s = ?;", postgreSQLHeroku.TABLE_USERS, postgreSQLHeroku.COL_USERNAME, postgreSQLHeroku.COL_USERS_ADMINTYPE);
					
					try (Connection connection = DriverManager.getConnection(postgreSQLHeroku.DATABASE_URL, postgreSQLHeroku.DATABASE_USERNAME, postgreSQLHeroku.DATABASE_PASSWORD);
							PreparedStatement deleteUserLogin = connection.prepareStatement(query1);
							PreparedStatement deleteUser = connection.prepareStatement(query2)) {

						connection.setAutoCommit(false);
			    		
						deleteUserLogin.setString(1, usernameField.getText());
						
						deleteUser.setString(1, usernameField.getText());
						deleteUser.setString(2, postgreSQLHeroku.TYPE_LIBRARIAN);
						
						if(deleteUserLogin.executeUpdate() == 1 && deleteUser.executeUpdate() == 1) {
							AlertBox.display("Success", "Librarian deleted!");
							connection.commit();
						} else {
							AlertBox.display("Error", "Librarian does not exist!");
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
        
		pane.add(btn, 1, 2);
		pane.add(backBtn, 1, 4);
		
		Scene scene = new Scene(pane, 350, 450);
	    scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
	    return scene;
	}
	
	@Override
	public void close() throws Exception {
		// TODO Auto-generated method stub
		
	}

}
