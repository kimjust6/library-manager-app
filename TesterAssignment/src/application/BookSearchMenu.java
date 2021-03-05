package application;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class BookSearchMenu implements AutoCloseable {

	final int WIDTH = 250;
	private Stage stage;
	private Scene scene;
	
	public BookSearchMenu(Stage stage, Scene scene) {
		this.stage = stage;
		this.scene = scene;
	}
	
	public Scene showMenu() throws Exception {
		GridPane pane = new GridPane();
      	pane.setAlignment(Pos.CENTER);
      	pane.setHgap(1);
    	pane.setVgap(10);
      	Label heading = new Label("Welcome, What would you like to do today?\n");
      	
      	Button searchBtn = new Button("Find");
      	Button backBtn = new Button("Back");
      	TextField bookField = new TextField();
      	ChoiceBox <String> choiceBox = new ChoiceBox<>();
      	
      	//getItems
      	choiceBox.getItems().addAll("Title","Author","Publisher","ID");
      	
      	
      	//searchBtn.setMaxWidth(WIDTH);
      	
      	
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
      	
      	backBtn.setOnAction(e-> {
			
			try (Home homePage = new Home(stage, scene)) {
    			stage.setScene(homePage.showHomePage()); 
    			stage.setTitle("Library");
			} catch (Exception e2) {
				e2.printStackTrace();
			}
			
		});
      	
      	
      	pane.add(heading, 0, 0);
      	pane.add(choiceBox,0,1);
      	pane.add(bookField,2,1);
      	pane.add(searchBtn, 1, 2);
      	pane.add(backBtn, 1, 2);
      	
      	
      	Scene scene = new Scene(pane, 350, 450);
	    scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
	    return scene;
	}

	@Override
	public void close() throws Exception {
		// TODO Auto-generated method stub
		
	}

}
