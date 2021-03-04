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
import javafx.scene.text.Font;
import javafx.stage.Stage;
import DatabaseTest.postgreSQLHeroku;

public class Main extends Application {
	private postgreSQLHeroku DB = new postgreSQLHeroku();
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
	    pane.setHgap(10);
	    pane.setVgap(2);
	    
	    Image image = new Image(getClass().getResourceAsStream("admin.png"));
	    ImageView imgV= new ImageView(image);
	    imgV.setFitHeight(70);
	    imgV.setFitWidth(70);
	    
	    Image image2 = new Image(getClass().getResourceAsStream("student.png"));
	    ImageView imgV2= new ImageView(image2);
	    imgV2.setFitHeight(70);
	    imgV2.setFitWidth(70);
	    
	    Label wlcMSG = new Label("Welcome!\nWho are you logging in as...?\n");
	    wlcMSG.setFont(new Font(18));
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
	public Scene adminOptionsPage() throws Exception {
		GridPane pane = new GridPane();
		pane.setAlignment(Pos.CENTER);
		pane.setHgap(10);
		pane.setVgap(20);
		
		Image image = new Image(getClass().getResourceAsStream("register.png"));
	    ImageView imgV= new ImageView(image);
	    imgV.setFitHeight(40);
	    imgV.setFitWidth(40);
		
	    Image image1 = new Image(getClass().getResourceAsStream("delete.png"));
	    ImageView imgV1= new ImageView(image1);
	    imgV1.setFitHeight(40);
	    imgV1.setFitWidth(40);
	    
	    Image image2 = new Image(getClass().getResourceAsStream("view.png"));
	    ImageView imgV2= new ImageView(image2);
	    imgV2.setFitHeight(40);
	    imgV2.setFitWidth(40);
	    
		Button btn0 = new Button("Register Librarian");
		Button btn1 = new Button("Delete Librarian");
		Button btn2 = new Button("View Librarians");
		
		btn0.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				try {
					stage.setScene(registerLibrarianP());
					stage.setTitle("Register Librarian");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			
		});
		pane.add(imgV, 0, 0);
		pane.add(btn0, 1, 0);
		pane.add(imgV1, 0, 1);
		pane.add(btn1, 1, 1);
		pane.add(imgV2, 0, 2);
		pane.add(btn2, 1, 2);
		
		Scene scene = new Scene(pane, 350, 450);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		return scene;
	}
	public Scene registerLibrarianP() throws Exception {
		GridPane pane = new GridPane();
		pane.setAlignment(Pos.CENTER);
		pane.setPadding(new Insets(11.5, 12.5, 13.5, 14.5));
		pane.setHgap(5.5);
		pane.setVgap(5.5);
			
		TextField username = new TextField();
		PasswordField password = new PasswordField();
		TextField email = new TextField();
		TextField phone = new TextField();
		
		pane.add(new Label("Username:"), 0, 1);
		pane.add(username, 1, 1);
		pane.add(new Label("Password:"), 0, 2);
		pane.add(password, 1, 2);
		pane.add(new Label("Email:"), 0, 3);
		pane.add(email, 1, 3);
		pane.add(new Label("Phone No:"), 0, 4);
		pane.add(phone, 1, 4);
		
		//should redirect to inside, no need to log in, and add librarian to DB
		Button btn = new Button("Submit");
		pane.add(btn, 1, 5);
		btn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				// TODO
				System.out.println("You are registered successfully");
				// call Register Librarian here
			}
			
		});
		Scene scene = new Scene(pane, 350, 450);
	    scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
	    return scene;
	}
	public Scene loginPage() throws Exception {
		GridPane pane = new GridPane();
		pane.setAlignment(Pos.CENTER);
		pane.setPadding(new Insets(11.5, 12.5, 13.5, 14.5));
		pane.setHgap(5.5);
		pane.setVgap(5.5);
			
		TextField username = new TextField();
		PasswordField password = new PasswordField();
		
		pane.add(new Label("Username:"), 0, 1);
		pane.add(username, 1, 1);
		pane.add(new Label("Password:"), 0, 2);
		pane.add(password, 1, 2);
			
		Button btn = new Button("Login");
		btn.setOnAction(new EventHandler<ActionEvent>(){
			@Override public void handle(ActionEvent arg0) {
				String userType = DB.initialize(username.getText(), password.getText());
				
				if(userType.equalsIgnoreCase("Admin")) {
					System.out.println("You are an admin!");
					try {
						stage.setScene(adminOptionsPage());
						stage.setTitle("Admin Page");
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else if(userType.equalsIgnoreCase("Librarian")) {
					System.out.println("You are a librarian!");
				} else {
					System.out.println("You are not an admin or librarian!");
				}
			}
		});
		pane.add(btn, 1, 3);
		
		Scene scene = new Scene(pane, 350, 450);
	    scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
	    return scene;
		}
	
	public Scene studentHomePage() throws Exception {
		GridPane pane = new GridPane();
		pane.setAlignment(Pos.CENTER);
		pane.setHgap(5.5);
	    pane.setVgap(10);
	    
		Label userLabel = new Label("Enter Student ID:");
        TextField studentNoField = new TextField();
        Button btn = new Button("Login");
        btn.setOnAction(new EventHandler<ActionEvent>(){
			@Override public void handle(ActionEvent arg0) {
				try {
					stage.setScene(studentLoginPage(studentNoField));
					stage.setTitle("Student Home Page");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
       });
       pane.add(userLabel, 0 , 0);
       pane.add(studentNoField, 1 , 0);
       pane.add(btn, 0, 1);
       
       Scene scene = new Scene(pane, 450, 450);
	    scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
	    return scene;
	}
	
	public Scene studentLoginPage(TextField studentNoField) throws Exception {
		GridPane pane = new GridPane();
      	pane.setAlignment(Pos.CENTER);
      	pane.setHgap(5.5);
    	pane.setVgap(10);
      	Label heading = new Label("Welcome student " + studentNoField.getText() + "\nWhat would you like to do today?\n");
      	Button btn0 = new Button("Search a book");
      	Button btn1 = new Button("Request an issue");
      	Button btn2 = new Button("View borrowed books");
      	btn2.setMinSize(150, 40);
      	btn2.setMaxWidth(200);
      	pane.add(heading, 0, 0);
      	pane.add(btn0, 0, 1);
      	pane.add(btn1, 0, 2);
      	pane.add(btn2, 0, 3);
      	
      	Scene scene = new Scene(pane, 350, 450);
	    scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
	    return scene;
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}


