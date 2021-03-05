package application;

import classes.Student;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class StudentMenu implements AutoCloseable {

	final int WIDTH = 250;
	private Stage stage;
	private Scene scene;
	
	public StudentMenu(Stage stage, Scene scene) {
		this.stage = stage;
		this.scene = scene;
	}
	
	
	public Scene showMenu(Student stud)  {
		GridPane pane = new GridPane();
      	pane.setAlignment(Pos.CENTER);
      	pane.setHgap(5.5);
    	pane.setVgap(10);
      	Label heading = new Label("Welcome, " + stud.getfName() + "\nWhat would you like to do today?\n");
      	
      	Button searchBtn = new Button("Find Books");
      	Button reqIssueBtn = new Button("Request a book");
      	Button viewBorrowedBtn = new Button("View Borrowed");
      	Button backBtn = new Button("Logout");
      	
      	searchBtn.setMaxWidth(WIDTH);
      	reqIssueBtn.setMaxWidth(WIDTH);
      	viewBorrowedBtn.setMaxWidth(WIDTH);
      	backBtn.setMaxWidth(WIDTH);
      	
      	
      	searchBtn.setOnAction(new EventHandler<ActionEvent>(){
			@Override public void handle(ActionEvent arg0) {
				try (BookSearchMenu bsm = new BookSearchMenu(stage, scene)) {
					stage.setScene(bsm.showMenu(stud));
					stage.setTitle("Book Search");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
      	});
      	
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
      	
      	viewBorrowedBtn.setOnAction(new EventHandler<ActionEvent>(){
			@Override public void handle(ActionEvent arg0) {
				try {
//					stage.setScene(pagegoeshere);
					stage.setTitle("Borrowed");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
      	});
      	
      	backBtn.setOnAction(e-> {
			
			try (Home homePage = new Home(stage, scene)) {
    			stage.setScene(homePage.showHomePage()); 
    			stage.setTitle("Library");
			} catch (Exception e2) {
				e2.printStackTrace();
			}
			
		}); 
      	
      	
      	//viewBorrowedBtn.setMinSize(150, 40);
      	
      	pane.add(heading, 0, 0);
      	pane.add(searchBtn, 0, 1);
      	//pane.add(reqIssueBtn, 0, 2);
      	pane.add(viewBorrowedBtn, 0, 2);
      	pane.add(backBtn, 0, 3);
      	
      	Scene scene = new Scene(pane, 350, 450);
	    scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
	    return scene;
	}

	@Override
	public void close() throws Exception {
		// TODO Auto-generated method stub
		
	}





}
