package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import org.postgresql.util.PSQLException;

import DatabaseTest.postgreSQLHeroku;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.scene.control.ComboBox;

public class AddBook implements AutoCloseable{
	private Stage stage;
	private Scene scene;
	
	AddBook(Stage stage, Scene scene){
		this.stage = stage;
		this.scene = scene;
	}
	public Scene addBook() throws Exception {
		GridPane pane = new GridPane();
		pane.setAlignment(Pos.CENTER);
		pane.setPadding(new Insets(11.5, 12.5, 13.5, 14.5));
		pane.setHgap(5.5);
		pane.setVgap(5.5);
		
		Image image = new Image(getClass().getResourceAsStream("open-book.png"));
	    ImageView imgV= new ImageView(image);
	    imgV.setFitHeight(40);
	    imgV.setFitWidth(40);
		
		ComboBox<String> media_type = new ComboBox<>();

		media_type.getItems().add("Book");
		media_type.getItems().add("Magazine");
		media_type.getItems().add("Newspaper");
		
		TextField title = new TextField();
		TextField author = new TextField();
		TextField publisher = new TextField();

		
		pane.add(new Label("ADD A BOOK"), 0, 0);
		pane.add(imgV, 1, 0);
		pane.add(new Label("Title:"), 0, 1);
		pane.add(title, 1, 1);
		pane.add(new Label("Author:"), 0, 2);
		pane.add(author, 1, 2);
		pane.add(new Label("Publisher:"), 0, 3);
		pane.add(publisher, 1, 3);
		pane.add(new Label("Media Type:"), 0, 4);
		pane.add(media_type, 1, 4);

		Button btn = new Button("Add");
		pane.add(btn, 1, 5);
		
		
		btn.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent arg0) {
				try (Connection connection = DriverManager.getConnection(postgreSQLHeroku.DATABASE_URL, postgreSQLHeroku.DATABASE_USERNAME, postgreSQLHeroku.DATABASE_PASSWORD)) {
					String selected_media = (String) media_type.getValue();
					Statement statement = connection.createStatement();
		    		
					String query1 = String.format("insert into %s values('%s','%s','%s','%s', %d, %d);", postgreSQLHeroku.TABLE_LIBRARY, title.getText(), author.getText(), publisher.getText(), selected_media, 1, 0);
					
					
						if(statement.executeUpdate(query1) == 1) {
							System.out.println("Book added");
							try (LibrarianMenu librarianMenu = new LibrarianMenu(stage, scene)) {
								stage.setScene(librarianMenu.showMenu());
								stage.setTitle("Librarian Menu");
							} catch (Exception e) {
								e.printStackTrace();
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
		
		
		Scene scene = new Scene(pane, 350, 450);
	    scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
	    return scene;
	}
	@Override
	public void close() throws Exception {
		// TODO Auto-generated method stub
		
	}
}

