package application;
	
import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) throws Exception {
		try {
			BorderPane rootPane = new BorderPane();
			ImageView imageview = new ImageView(new Image("https://ih1.redbubble.net/image.1361936297.3666/tst,small,507x507-pad,600x600,f8f8f8.jpg"));
			imageview.setFitHeight(250);
			imageview.setFitWidth(250);
			
			GridPane loginPane = new GridPane();
			loginPane.setAlignment(Pos.CENTER);
			loginPane.setPadding(new Insets(11.5, 12.5, 13.5, 14.5));
			loginPane.setHgap(5.5);
			loginPane.setVgap(5.5);
			
			loginPane.add(new Label("Username:"), 0, 1);
			loginPane.add(new TextField(), 1, 1);
			loginPane.add(new Label("Password:"), 0, 2);
			loginPane.add(new PasswordField(), 1, 2);
			
			Button btn = new Button("Login");
			loginPane.add(btn, 1, 3);
			
			rootPane.setTop(imageview);
			rootPane.setCenter(loginPane);
			Scene scene = new Scene(rootPane);
			
			primaryStage.setResizable(false);
			primaryStage.setTitle("brr"); 
		    primaryStage.setScene(scene); 
			primaryStage.show();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}