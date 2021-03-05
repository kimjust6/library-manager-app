package application;

import java.util.Scanner;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class LibrarianMenu implements AutoCloseable {

	private Stage stage;
	private Scene scene;
	
	public LibrarianMenu(Stage stage, Scene scene) {
		this.stage = stage;
		this.scene = scene;
	}
	public Scene showMenu() throws Exception {
		GridPane pane = new GridPane();
		pane.setAlignment(Pos.CENTER);
		pane.setHgap(10);
		pane.setVgap(20);
		    
		Button addBtn = new Button("Add Book");
		addBtn.setOnAction(new EventHandler<ActionEvent>(){
			@Override public void handle(ActionEvent arg0) {
				try (AddBook book = new AddBook(stage, scene)) {
					stage.setScene(book.addBook());
					stage.setTitle("Adding Book");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		Button viewBtn = new Button("View Books");
		viewBtn.setOnAction(new EventHandler<ActionEvent>(){
			@Override public void handle(ActionEvent arg0) {
				try (Scanner in = new Scanner(System.in)) {
//					stage.setScene(pagegoeshere);
					stage.setTitle("Viewing Books");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		Button issueBtn = new Button("Issue Book");
		issueBtn.setOnAction(new EventHandler<ActionEvent>(){
			@Override public void handle(ActionEvent arg0) {
				try (Scanner in = new Scanner(System.in)) {
//					stage.setScene(pagegoeshere);
					stage.setTitle("Issuing Book");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		Button returnBtn = new Button("Return Book");
		returnBtn.setOnAction(new EventHandler<ActionEvent>(){
			@Override public void handle(ActionEvent arg0) {
				try (Scanner in = new Scanner(System.in)) {
//					stage.setScene(pagegoeshere);
					stage.setTitle("Returning Book");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		Button logoutBtn = new Button("Log out");
		logoutBtn.setOnAction(new EventHandler<ActionEvent>(){
			@Override public void handle(ActionEvent arg0) {
				try (Home homePage= new Home(stage, scene)) {
					stage.setScene(homePage.showHomePage()); 
					stage.setTitle("Library");
				} catch (Exception e2) {
					e2.printStackTrace();
				}	
			}
        }); 
			
		pane.add(addBtn, 0, 0);
		pane.add(viewBtn, 0, 1);
		pane.add(issueBtn, 0, 2);
		pane.add(returnBtn, 0, 3);
		pane.add(logoutBtn, 0, 5);
			
		Scene scene = new Scene(pane, 350, 450);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		return scene;
	}
	@Override
	public void close() throws Exception {
		// TODO Auto-generated method stub
		
	}
}
