package application;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class StudentMenu implements AutoCloseable {

	private Stage stage;
	private Scene scene;
	
	public StudentMenu(Stage stage, Scene scene) {
		this.stage = stage;
		this.scene = scene;
	}
	
	public Scene showMenu(String studentNoField) throws Exception {
		GridPane pane = new GridPane();
      	pane.setAlignment(Pos.CENTER);
      	pane.setHgap(5.5);
    	pane.setVgap(10);
      	Label heading = new Label("Welcome student " + studentNoField + "\nWhat would you like to do today?\n");
      	Button backBtn = new Button("Back");
      	
      	Button searchBtn = new Button("Search a book");
      	searchBtn.setOnAction(new EventHandler<ActionEvent>(){
			@Override public void handle(ActionEvent arg0) {
				try {
//					stage.setScene(pagegoeshere);
					stage.setTitle("Book Search");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
      	});
      	Button reqIssueBtn = new Button("Request an issue");
      	reqIssueBtn.setOnAction(new EventHandler<ActionEvent>(){
			@Override public void handle(ActionEvent arg0) {
				try {
//					stage.setScene(pagegoeshere);
					stage.setTitle("Book Issue Request");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
      	});
      	Button viewBorrowedBtn = new Button("View borrowed books");
      	viewBorrowedBtn.setOnAction(new EventHandler<ActionEvent>(){
			@Override public void handle(ActionEvent arg0) {
				try {
//					stage.setScene(pagegoeshere);
					stage.setTitle("Borrowed Books");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
      	});
      	
      	backBtn.setOnAction(e-> 
		{
			
			try (StudentLogin studentLogin = new StudentLogin(stage, scene)) {
				
					stage.setScene(studentLogin.showLoginPage());
					stage.setTitle("Student Login Page");
			} catch (Exception e2) {
				e2.printStackTrace();
			}
			
		}); 
      	
      	
      	viewBorrowedBtn.setMinSize(150, 40);
      	viewBorrowedBtn.setMaxWidth(200);
      	pane.add(heading, 0, 0);
      	pane.add(searchBtn, 0, 1);
      	pane.add(reqIssueBtn, 0, 2);
      	pane.add(viewBorrowedBtn, 0, 3);
      	pane.add(backBtn, 0, 4);
      	
      	Scene scene = new Scene(pane, 350, 450);
	    scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
	    return scene;
	}

	@Override
	public void close() throws Exception {
		// TODO Auto-generated method stub
		
	}

}
