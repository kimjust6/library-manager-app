package application;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import DatabaseTest.postgreSQLHeroku;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Main extends Application {
	private postgreSQLHeroku DB = new postgreSQLHeroku();
	private Stage stage;
	private Person person;
	
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
					person = new Student();
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
			
		TextField username = new TextField();
		PasswordField password = new PasswordField();
		
		pane.add(new Label("Username:"), 0, 1);
		pane.add(username, 1, 1);
		pane.add(new Label("Password:"), 0, 2);
		pane.add(password, 1, 2);
			
		Button btn = new Button("Login");
		btn.setOnAction(new EventHandler<ActionEvent>(){
			@Override public void handle(ActionEvent arg0) {
				ResultSet userInfo = DB.initialize(username.getText(), password.getText());
				
				if(userInfo != null) {
					try {
						String userType = userInfo.getString(DB.COL_ADMINTYPE);
						
						if(userType.equalsIgnoreCase("Admin")) {
							
							System.out.println("You are an Admin!");
							person = new Admin();
							stage.setScene(adminHomePage());
							stage.setTitle("Admin Page");
							
						} else if(userType.equalsIgnoreCase("Librarian")) {

							System.out.println("You are a librarian!");
							person = new Librarian();
//							stage.setScene(librarianHomePage());
//							stage.setTitle("Librarian Page");
							
						} else {
							
							System.out.println("You are not an admin or librarian!");
							
						}
						
					} catch (SQLException e1) {
						e1.printStackTrace();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		});
		pane.add(btn, 1, 3);
		
		Scene scene = new Scene(pane, 350, 450);
	    scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
	    return scene;
	}

	public Scene adminHomePage() throws Exception {
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
	    
		Button regBtn = new Button("Register Librarian");
		Button delBtn = new Button("Delete Librarian");
		Button viewBtn = new Button("View Librarians");
		
		regBtn.setOnAction(new EventHandler<ActionEvent>(){
			@Override public void handle(ActionEvent arg0) {
				try (Scanner in = new Scanner(System.in)) {
					
					// need some foolproofing
					
					stage.setScene(new Scene(new Group(new Text("check console!")), 350, 450));
					stage.setTitle("Registering Librarian");
					Librarian librarian = new Librarian();
					
					// getting details
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
	    });
		
		delBtn.setOnAction(new EventHandler<ActionEvent>(){
			@Override public void handle(ActionEvent arg0) {
				try (Scanner in = new Scanner(System.in)) {
					
					// need some foolproofing
					
					stage.setScene(new Scene(new Group(new Text("check console!")), 350, 450));
					stage.setTitle("Removing Librarian");
					System.out.println("Trying to delete librarian(s).");
					
					
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
	    });
		
		viewBtn.setOnAction(new EventHandler<ActionEvent>(){
			@Override public void handle(ActionEvent arg0) {
				try (Scanner in = new Scanner(System.in)) {
					
					// need some foolproofing
					
					stage.setScene(new Scene(new Group(new Text("check console"))));
					stage.setTitle("Viewing Librarians");
					System.out.println("Trying to view librarian(s).");
					
					
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
	    });
		
		pane.add(imgV, 0, 0);
		pane.add(regBtn, 1, 0);
		pane.add(imgV1, 0, 1);
		pane.add(delBtn, 1, 1);
		pane.add(imgV2, 0, 2);
		pane.add(viewBtn, 1, 2);
		
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
       
       Scene scene = new Scene(pane, 350, 450);
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
      	btn2.setWrapText(true);
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