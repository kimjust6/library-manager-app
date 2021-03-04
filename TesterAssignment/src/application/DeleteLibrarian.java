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
		pane.add(btn, 1, 2);
		btn.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent arg0) {
//				try {
//					if(DB.delete(DB.TABLE_USERS, DB.COL_USERNAME, username.getText())) {
//						if(DB.delete(DB.TABLE_ADMINS, DB.COL_USERNAME, username.getText())) {
//							System.out.println("Added librarian successfully!");
//						} else {
//							System.out.println("Failed!");
//						}
//					} else {
//						System.out.println("Failed!");
//					}
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
				
//				try (Connection connection = DriverManager.getConnection(DB.DATABASE_URL, DB.DATABASE_USERNAME, DB.DATABASE_PASSWORD)) {
//
//					Statement statement = connection.createStatement();
//
//					
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
			}
		});
		Scene scene = new Scene(pane, 350, 450);
	    scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
	    return scene;
	}
	
	@Override
	public void close() throws Exception {
		// TODO Auto-generated method stub
		
	}

}
