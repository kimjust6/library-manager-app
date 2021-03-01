package application;
	
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			BorderPane root = new BorderPane();
			Scene scene = new Scene(root,400,400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		System.out.println("Test by Harsh");
	}
	
	public static void main(String[] args) {
<<<<<<< HEAD
		//This is a test by Justin Kim
		console.log("Test by Justin to see if Push works");
		
		System.out.println("Natalie is here");
=======
>>>>>>> branch 'master' of https://github.com/hershk17/BTP400_Assignment-1.git
		launch(args);
		
		System.out.println("Another test");
	}
}
