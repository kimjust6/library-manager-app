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
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Main extends Application {
	private Stage stage;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		stage = primaryStage;
		stage.setTitle("First Page");
		Scene scene = homePage();
		stage.setScene(scene); 
		stage.show(); 
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
			@Override public void handle(ActionEvent arg0) {
				try {
					stage.setScene(loginPage());
					stage.setTitle("Admin Login Page");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
	    });
	    btStu.setOnAction(new EventHandler<ActionEvent>(){
			@Override public void handle(ActionEvent arg0) {
				try {
					stage.setScene(studentHomePage());
					stage.setTitle("Student Login Page");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
	    });
	    
	    pane.add(wlcMSG, 0 , 0);
	    pane.add(imgV, 0 ,1);
	    pane.add(imgV2, 1 ,1);
	    pane.add(btAdmin, 0, 2);
	    pane.add(btStu, 1, 2);
	    
	    return new Scene(pane, 350, 450);
	}
	
	public Scene loginPage() throws Exception {
		GridPane pane = new GridPane();
		pane.setAlignment(Pos.CENTER);
		pane.setPadding(new Insets(11.5, 12.5, 13.5, 14.5));
		pane.setHgap(5.5);
		pane.setVgap(5.5);
			
		pane.add(new Label("Username:"), 0, 1);
		pane.add(new TextField(), 1, 1);
		pane.add(new Label("Password:"), 0, 2);
		pane.add(new PasswordField(), 1, 2);
			
		Button btn = new Button("Login");
		btn.setOnAction(new EventHandler<ActionEvent>(){
			@Override public void handle(ActionEvent arg0) {
				System.out.println("You tried to login as an admin.");
			}
		});
		pane.add(btn, 1, 3);
			
		return new Scene(pane, 350, 450);
	}
	
	public Scene studentHomePage() throws Exception {
		GridPane pane = new GridPane();
		pane.setAlignment(Pos.CENTER);
		pane.setHgap(5.5);
	    pane.setVgap(10);
	    
		Label userLabel = new Label("Enter Student ID:");
        final TextField userField = new TextField();
        Button btn = new Button("Login");
        btn.setOnAction(new EventHandler<ActionEvent>(){
			@Override public void handle(ActionEvent arg0) {
				try {
					stage.setScene(studentLoginPage(userField));
					stage.setTitle("Student Home Page");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
       });
       pane.add(userLabel, 0 , 0);
       pane.add(userField, 1 , 0);
       pane.add(btn, 0, 1);
       
       return new Scene(pane, 350, 450);
	}
	
	public Scene studentLoginPage(TextField userField) throws Exception {
		GridPane pane = new GridPane();
      	pane.setAlignment(Pos.CENTER);
      	pane.setHgap(5.5);
    	pane.setVgap(10);
      	Label heading = new Label("Welcome student " + userField.getText() + "\nWhat would you like to do today?\n");
      	Button btn0 = new Button("Search a book");
      	Button btn1 = new Button("Request an issue");
      	Button btn2 = new Button("View borrowed books");
      	pane.add(heading, 0, 0);
      	pane.add(btn0, 0, 1);
      	pane.add(btn1, 0, 2);
      	pane.add(btn2, 0, 3);
      	
		return new Scene(pane, 350, 450);
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}


