package application;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.scene.control.Label;

public class First_Page extends Application {
	@Override
	public void start(Stage primaryStage) throws Exception {
		GridPane pane = new GridPane();
		pane.setAlignment(Pos.CENTER);
		Button btAdmin = new Button("Admin");
	    Button btStu = new Button("Student");
	    
	    pane.setHgap(5.5);
	    pane.setVgap(10);
	    
	    Image image = new Image(getClass().getResourceAsStream("admin.png"));
	    ImageView imgV= new ImageView(image);
	    imgV.setFitHeight(70);
	    imgV.setFitWidth(70);
	    
	    Image image2 = new Image(getClass().getResourceAsStream("student.png"));
	    ImageView imgV2= new ImageView(image2);
	    imgV2.setFitHeight(70);
	    imgV2.setFitWidth(70);
	    
	    Label wlcMSG = new Label("Welcome!\nPlease choose an option:\n");
	    
	    pane.add(wlcMSG, 0 , 0);
	    pane.add(imgV, 0 ,1);
	    pane.add(imgV2, 1 ,1);
	    pane.add(btAdmin, 0, 2);
	    pane.add(btStu, 1, 2);
	    
	    btAdmin.setOnAction(e -> {System.out.println("Admin button is clicked");});
	    btStu.setOnAction(e -> {System.out.println("Student button is clicked");});
	    
	    Scene scene = new Scene(pane, 400, 400);
	    primaryStage.setTitle("First Page"); 
	    primaryStage.setScene(scene); 
	    primaryStage.show(); 
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
