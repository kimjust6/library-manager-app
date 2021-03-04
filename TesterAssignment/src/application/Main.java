package application;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
	private Stage stage;
	private Scene scene;
	
	@Override public void start(Stage primaryStage) throws Exception {
		stage = primaryStage;
		stage.setTitle("Library");
		
		Home homePage = new Home(stage, scene);
		stage.setScene(homePage.showHomePage()); 
		
		stage.show(); 
	}

	public static void main(String[] args) {
		launch(args);
	}	
}