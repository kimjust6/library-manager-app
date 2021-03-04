package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import org.postgresql.util.PSQLException;
import DatabaseTest.postgreSQLHeroku;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Callback;

public class Main extends Application {
	private final postgreSQLHeroku DB = new postgreSQLHeroku();
	
	private Stage stage;
	private Scene scene;
	
	@Override public void start(Stage primaryStage) throws Exception {
		stage = primaryStage;
		stage.setTitle("First Page");
		scene = home();
		stage.setScene(scene); 
		stage.show(); 
	}
	
	
	// user can choose ADMIN or STUDENT
	
	public Scene home() throws Exception {
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
					stage.setScene(adminLogin());
					stage.setTitle("Admin Login Page");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
	    });
	    
	    btStu.setOnAction(new EventHandler<ActionEvent>(){
			@Override public void handle(ActionEvent arg0) {
				try {
					stage.setScene(studentLogin());
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
	
	
	// if ADMIN, then LOGIN using details
	
	public Scene adminLogin() throws Exception {
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
							stage.setScene(adminMenu());
							stage.setTitle("Admin Menu");
							
						} else if(userType.equalsIgnoreCase("Librarian")) {
							System.out.println("You are a librarian!");
							stage.setScene(librarianMenu());
							stage.setTitle("Librarian Menu");
							
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

	
	// if ADMIN is LOGGED IN, show MENU OPTIONS
	
	public Scene adminMenu() throws Exception {
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
		
//		regBtn.setOnAction(new EventHandler<ActionEvent>(){
//			@Override public void handle(ActionEvent arg0) {
//				try (Scanner in = new Scanner(System.in)) {
//					stage.setScene(regLibrarian());
//					stage.setTitle("Registering Librarian");
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//	    });
		
		regBtn.setOnAction(new RegisterLibrarian(stage, scene, DB));
		
		
		delBtn.setOnAction(new EventHandler<ActionEvent>(){
			@Override public void handle(ActionEvent arg0) {
				try (Scanner in = new Scanner(System.in)) {
					stage.setScene(delLibrariane());
					stage.setTitle("Removing Librarian");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
	    });
		
		viewBtn.setOnAction(new EventHandler<ActionEvent>(){
			@Override public void handle(ActionEvent arg0) {
				try (Scanner in = new Scanner(System.in)) {
					stage.setScene(getLibrarians());
					stage.setTitle("Viewing Librarians");
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
	
	
	// if ADMIN is LOGGED IN, show MENU OPTIONS
	
	public Scene librarianMenu() throws Exception {
		GridPane pane = new GridPane();
		pane.setAlignment(Pos.CENTER);
		pane.setHgap(10);
		pane.setVgap(20);
		    
		Button addBtn = new Button("Add Book");
		Button viewBtn = new Button("View Books");
		Button issueBtn = new Button("Issue Book");
		Button returnBtn = new Button("Return Book");
			
		addBtn.setOnAction(new EventHandler<ActionEvent>(){
			@Override public void handle(ActionEvent arg0) {
				try (Scanner in = new Scanner(System.in)) {
//					stage.setScene(pagegoeshere);
					stage.setTitle("Adding Book");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
			
		viewBtn.setOnAction(new EventHandler<ActionEvent>(){
			@Override public void handle(ActionEvent arg0) {
				try (Scanner in = new Scanner(System.in)) {
//					stage.setScene(pagegoeshere);
					stage.setTitle("Viewing Books");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
			
		issueBtn.setOnAction(new EventHandler<ActionEvent>(){
			@Override public void handle(ActionEvent arg0) {
				try (Scanner in = new Scanner(System.in)) {
//					stage.setScene(pagegoeshere);
					stage.setTitle("Issuing Book");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		returnBtn.setOnAction(new EventHandler<ActionEvent>(){
			@Override public void handle(ActionEvent arg0) {
				try (Scanner in = new Scanner(System.in)) {
//					stage.setScene(pagegoeshere);
					stage.setTitle("Returning Book");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
			
		pane.add(addBtn, 0, 0);
		pane.add(viewBtn, 0, 1);
		pane.add(issueBtn, 0, 2);
		pane.add(returnBtn, 0, 3);
			
		Scene scene = new Scene(pane, 350, 450);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		return scene;
	}
	
	
	// ADMIN chooses to REGISTER LIBRARIAN
	
//	public Scene regLibrarian() throws Exception {
//		GridPane pane = new GridPane();
//		pane.setAlignment(Pos.CENTER);
//		pane.setPadding(new Insets(11.5, 12.5, 13.5, 14.5));
//		pane.setHgap(5.5);
//		pane.setVgap(5.5);
//			
//		TextField username = new TextField();
//		PasswordField password = new PasswordField();
//		TextField name = new TextField();
//		TextField email = new TextField();
//		TextField phone = new TextField();
//		
//		pane.add(new Label("Username:"), 0, 1);
//		pane.add(username, 1, 1);
//		pane.add(new Label("Password:"), 0, 2);
//		pane.add(password, 1, 2);
//		pane.add(new Label("Name:"), 0, 3);
//		pane.add(name, 1, 3);
//		pane.add(new Label("Email:"), 0, 4);
//		pane.add(email, 1, 4);
//		pane.add(new Label("Phone No:"), 0, 5);
//		pane.add(phone, 1, 5);
//
//		Button btn = new Button("Submit");
//		pane.add(btn, 1, 6);
//		btn.setOnAction(new EventHandler<ActionEvent>() {
//			@Override public void handle(ActionEvent arg0) {
//				try (Connection connection = DriverManager.getConnection(DB.DATABASE_URL, DB.DATABASE_USERNAME, DB.DATABASE_PASSWORD)) {
//
//					Statement statement = connection.createStatement();
//		    		
//					String query1 = String.format("insert into %s values('%s','%s');", DB.TABLE_USERS, username.getText(), password.getText());
//					String query2 = String.format("insert into %s values('%s','%s','%s',%s,'%s');", DB.TABLE_ADMINS, username.getText(), name.getText(), email.getText(), phone.getText(), "Librarian");
//					
//					if(statement.executeUpdate(query1) == 1) {
//						if(statement.executeUpdate(query2) == 1) {
//							System.out.println("Librarian created!");
//						} else {
//							System.out.println("Failed!");
//						}
//					} else {
//						System.out.println("Failed!");
//					}
//					
//				} catch (PSQLException e) {
//					e.printStackTrace();
//				} catch (SQLException e) {
//					e.printStackTrace();
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//		Scene scene = new Scene(pane, 350, 450);
//	    scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
//	    return scene;
//	}
	
	
	// ADMIN chooses to DELETE LIBRARIAN
	
	public Scene delLibrariane() throws Exception {
		GridPane pane = new GridPane();
		pane.setAlignment(Pos.CENTER);
		pane.setPadding(new Insets(11.5, 12.5, 13.5, 14.5));
		pane.setHgap(5.5);
		pane.setVgap(5.5);
			
		TextField username = new TextField();
		
		pane.add(new Label("Username:"), 0, 1);
		pane.add(username, 1, 1);
		
		Button btn = new Button("Submit");
		pane.add(btn, 1, 2);
		btn.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent arg0) {
				try {
					if(DB.delete(DB.TABLE_USERS, DB.COL_USERNAME, username.getText())) {
						if(DB.delete(DB.TABLE_ADMINS, DB.COL_USERNAME, username.getText())) {
							System.out.println("Added librarian successfully!");
						} else {
							System.out.println("Failed!");
						}
					} else {
						System.out.println("Failed!");
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				
//				try (Connection connection = DriverManager.getConnection(DB.DATABASE_URL, DB.DATABASE_USERNAME, DB.DATABASE_PASSWORD)) {
//
//					Statement statement = connection.createStatement();
//
//					
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
			}
		});
		Scene scene = new Scene(pane, 350, 450);
	    scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
	    return scene;
	}
	
	
	// ADMIN chooses to VIEW ALL LIBRARIANS
	
	@SuppressWarnings({ "unchecked", "rawtypes" })		// is this bad ... ?
	public Scene getLibrarians() throws Exception {
		ResultSet rs = DB.select(DB.TABLE_ADMINS, DB.COL_ADMINTYPE, "=", "Librarian");
		
		ObservableList<ObservableList> data = FXCollections.observableArrayList();
		TableView tableview = new TableView();

		for (int i = 0; i < rs.getMetaData().getColumnCount() - 1; i++) {
            final int j = i;
            TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i + 1));
            col.setCellValueFactory(new Callback<CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {
                public ObservableValue<String> call(CellDataFeatures<ObservableList, String> param) {
                    return new SimpleStringProperty(param.getValue().get(j).toString());
                }
            });
            tableview.getColumns().addAll(col);
        }
		
		while (rs.next()) {
            ObservableList<String> row = FXCollections.observableArrayList();
            for (int i = 1; i < rs.getMetaData().getColumnCount(); i++) {
                row.add(rs.getString(i));
            }
            data.add(row);
        }
		tableview.setItems(data);
		
		Scene scene = new Scene(tableview, 445, 450);
	    scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
	    return scene;
	}
	
	
	// if STUDENT, then AUTHENTICATE using STUDENTNO
	
	public Scene studentLogin() throws Exception {
		GridPane pane = new GridPane();
		pane.setAlignment(Pos.CENTER);
		pane.setHgap(5.5);
	    pane.setVgap(10);
	    
		Label userLabel = new Label("Enter Student ID:");
        TextField studentNoField = new TextField();
        
        Button btn = new Button("Login");
        btn.setOnAction(new EventHandler<ActionEvent>(){
			@Override public void handle(ActionEvent arg0) {
				
				// need to authenticate student as well
				String studentNo = studentNoField.getText();
				
				try {
					stage.setScene(studentHomePage(studentNo));
					stage.setTitle("Student Home Page");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
       });
       pane.add(userLabel, 0 , 0);
       pane.add(studentNoField, 1 , 0);
       pane.add(btn, 1, 1);
       
       Scene scene = new Scene(pane, 350, 450);
	   scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
	   return scene;
	}
	
	
	// if STUDENT is LOGGED IN, show MENU OPTIONS
	
	public Scene studentHomePage(String studentNoField) throws Exception {
		GridPane pane = new GridPane();
      	pane.setAlignment(Pos.CENTER);
      	pane.setHgap(5.5);
    	pane.setVgap(10);
      	Label heading = new Label("Welcome student " + studentNoField + "\nWhat would you like to do today?\n");
      	
      	
      	Button searchBtn = new Button("Search a book");
      	searchBtn.setOnAction(new EventHandler<ActionEvent>(){
			@Override public void handle(ActionEvent arg0) {
				try {
//					stage.setScene(pagegoeshere);
					stage.setTitle("Book Search");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
      	});
      	Button reqIssueBtn = new Button("Request an issue");
      	reqIssueBtn.setOnAction(new EventHandler<ActionEvent>(){
			@Override public void handle(ActionEvent arg0) {
				try {
//					stage.setScene(pagegoeshere);
					stage.setTitle("Book Issue Request");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
      	});
      	Button viewBorrowedBtn = new Button("View borrowed books");
      	viewBorrowedBtn.setOnAction(new EventHandler<ActionEvent>(){
			@Override public void handle(ActionEvent arg0) {
				try {
//					stage.setScene(pagegoeshere);
					stage.setTitle("Borrowed Books");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
      	});
      	
      	
      	viewBorrowedBtn.setMinSize(150, 40);
      	viewBorrowedBtn.setMaxWidth(200);
      	pane.add(heading, 0, 0);
      	pane.add(searchBtn, 0, 1);
      	pane.add(reqIssueBtn, 0, 2);
      	pane.add(viewBorrowedBtn, 0, 3);
      	
      	Scene scene = new Scene(pane, 350, 450);
	    scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
	    return scene;
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}