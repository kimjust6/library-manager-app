package fxTest;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Main extends Application {
	
	Button button;
	Stage window;
	Scene scene1, scene2;

	
	public static void main (String[] args)
	{
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception
	{
		//main javafx code
		
		primaryStage.setTitle("Stage 1");
		button = new Button();
		button.setText("To Stage 2");
		
		Button button2 = new Button("To Stage 1");
		
		StackPane layout = new StackPane();
		StackPane layout2 = new StackPane();
		
		layout.getChildren().add(button);
		layout2.getChildren().add(button2);
		Scene scene = new Scene(layout, 300, 250);
		Scene scene2 = new Scene(layout2, 300, 400);
		
		//if you want to handle it in another class, you would have to pass another class 
		//that has a handle function
		//button.setOnAction(this);
		button.setOnAction(e -> {
			System.out.println("Hello world");

			primaryStage.setScene(scene2);
			primaryStage.setTitle("Stage 2");
		});
		
		button2.setOnAction(e -> {
			System.out.println("this is me");
			primaryStage.setScene(scene);
			primaryStage.setTitle("Stage 1");
		});
		
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
//	@Override
//	public void handle(ActionEvent event)
//	{
//		//check using  button name
//		if(event.getSource() == button)
//		{
//			System.out.println("Button pressed");
//		}
//	}
}
