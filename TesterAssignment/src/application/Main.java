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
		
		try (Home homePage = new Home(stage, scene)) {
			
			stage.setScene(homePage.showHomePage()); 
		} catch (Exception e2) {
			e2.printStackTrace();
		}
		
		stage.show(); 
	}

	public static void main(String[] args) {
		launch(args);
	}	
}