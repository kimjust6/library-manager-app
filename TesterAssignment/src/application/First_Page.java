package application;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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

public class First_Page extends Application {
	private Stage stage;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		stage = primaryStage;
		primaryStage.setTitle("First Page");
		Scene scene = homePage();
	    primaryStage.setScene(scene); 
	    primaryStage.show(); 
	}
	
	public Scene homePage() throws Exception {
		GridPane pane = new GridPane();
		pane.setAlignment(Pos.CENTER);
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
	    
	    Label wlcMSG = new Label("Welcome!\nWho are you logging in as...?\n");

		Button btAdmin = new Button("Admin");
	    Button btStu = new Button("Student");

	    btAdmin.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent arg0) {
				try {
					stage.setScene(loginPage());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
	    });
	    btStu.setOnAction(e -> {System.out.println("Student button is clicked");});
	    
	    pane.add(wlcMSG, 0 , 0);
	    pane.add(imgV, 0 ,1);
	    pane.add(imgV2, 1 ,1);
	    pane.add(btAdmin, 0, 2);
	    pane.add(btStu, 1, 2);
	    
	    return new Scene(pane, 350, 450);
	}
	
	public Scene loginPage() throws Exception {
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
			btn.setOnAction(e -> {
				System.out.println("You tried to login.");
			});
			loginPane.add(btn, 1, 3);
			
			rootPane.setTop(imageview);
			rootPane.setCenter(loginPane);
			
			return new Scene(rootPane, 350, 450);
	}
	
	
	public static void main(String[] args) {
		launch(args);
	}
}


