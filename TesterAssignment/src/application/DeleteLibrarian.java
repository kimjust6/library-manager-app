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
import java.sql.SQLException;
import java.sql.Statement;

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
			
		TextField username = new TextField();
		
		pane.add(new Label("Username:"), 0, 1);
		pane.add(username, 1, 1);
		
		Button btn = new Button("Submit");
		btn.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent arg0) {
				try (Connection connection = DriverManager.getConnection(postgreSQLHeroku.DATABASE_URL, postgreSQLHeroku.DATABASE_USERNAME, postgreSQLHeroku.DATABASE_PASSWORD)) {

					Statement statement = connection.createStatement();
		    		
					String query1 = String.format("delete from %s where %s = '%s' and %s = '%s'", postgreSQLHeroku.TABLE_ADMINS, postgreSQLHeroku.COL_USERNAME, username.getText(), postgreSQLHeroku.COL_ADMINTYPE, postgreSQLHeroku.TYPE_LIBRARIAN);
					String query2 = String.format("delete from %s where %s = '%s'", postgreSQLHeroku.TABLE_USERS, postgreSQLHeroku.COL_USERNAME, username.getText());
					
					if(statement.executeUpdate(query1) == 1) {
						if(statement.executeUpdate(query2) == 1) {
							System.out.println("Libarian deleted!");
						} else {
							System.out.println("Failed!");
						}
					} else {
						System.out.println("Failed!");
					}
					
				} catch (PSQLException e) {
					e.printStackTrace();
				} catch (SQLException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
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
