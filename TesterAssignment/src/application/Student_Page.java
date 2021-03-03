package application;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class Student_Page extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		GridPane pane = new GridPane();
		pane.setAlignment(Pos.CENTER);
		pane.setHgap(5.5);
	    pane.setVgap(10);
	    
		Label userLabel = new Label("Enter Student ID:");
        final TextField userField = new TextField();
        Button btn = new Button("Login");
        btn.setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent t){
                GridPane pane1 = new GridPane();
          		pane1.setAlignment(Pos.CENTER);
          		pane1.setHgap(5.5);
        	    pane1.setVgap(10);
          		Label heading = new Label("Welcome student " + userField.getText() + "\nWhat would you like to do today?\n");
          		Button btn0 = new Button("Search a book");
          		Button btn1 = new Button("Request an issue");
          		Button btn2 = new Button("View borrowed books");
          		pane1.add(heading, 0, 0);
          		pane1.add(btn0, 0, 1);
          		pane1.add(btn1, 0, 2);
          		pane1.add(btn2, 0, 3);
          		
          		Scene scene1 = new Scene(pane1, 400, 400);
        	    primaryStage.setTitle("Student Page"); 
        	    primaryStage.setScene(scene1); 
        	    primaryStage.show(); 
            }
       });
       pane.add(userLabel, 0 , 0);
       pane.add(userField, 1 , 0);
       pane.add(btn, 0, 1);
       
       Scene scene = new Scene(pane, 400, 400);
	    primaryStage.setTitle("Student Page"); 
	    primaryStage.setScene(scene); 
	    primaryStage.show(); 
	}
	public static void main(String[] args) {
		launch(args);
	}
}
