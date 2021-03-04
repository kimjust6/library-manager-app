package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
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
			
		TextField username = new TextField();
		PasswordField password = new PasswordField();
		TextField name = new TextField();
		TextField email = new TextField();
		TextField phone = new TextField();
		
		pane.add(new Label("Username:"), 0, 1);
		pane.add(username, 1, 1);
		pane.add(new Label("Password:"), 0, 2);
		pane.add(password, 1, 2);
		pane.add(new Label("Name:"), 0, 3);
		pane.add(name, 1, 3);
		pane.add(new Label("Email:"), 0, 4);
		pane.add(email, 1, 4);
		pane.add(new Label("Phone No:"), 0, 5);
		pane.add(phone, 1, 5);

		Button btn = new Button("Submit");
		btn.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent arg0) {
				try (Connection connection = DriverManager.getConnection(postgreSQLHeroku.DATABASE_URL, postgreSQLHeroku.DATABASE_USERNAME, postgreSQLHeroku.DATABASE_PASSWORD)) {

					Statement statement = connection.createStatement();
		    		
					String query1 = String.format("insert into %s values('%s','%s');", postgreSQLHeroku.TABLE_USERS, username.getText(), password.getText());
					String query2 = String.format("insert into %s values('%s','%s','%s',%s,'%s');", postgreSQLHeroku.TABLE_ADMINS, username.getText(), name.getText(), email.getText(), phone.getText(), "Librarian");
					
					if(statement.executeUpdate(query1) == 1) {
						if(statement.executeUpdate(query2) == 1) {
							System.out.println("Librarian created!");
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

		pane.add(btn, 1, 6);
		pane.add(backBtn, 1, 8);
		
		Scene scene = new Scene(pane, 350, 450);
	    scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
	    return scene;
	}
	
	@Override
	public void close() throws Exception {
		// TODO Auto-generated method stub
		
	}
}
