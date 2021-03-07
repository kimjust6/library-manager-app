package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.postgresql.util.PSQLException;

import classes.Student;
import database.postgreSQLHeroku;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class StudentMenu implements AutoCloseable {

	final int WIDTH = 250;
	private Stage stage;
	private Scene scene;
	
	public StudentMenu(Stage stage, Scene scene) {
		this.stage = stage;
		this.scene = scene;
	}
	
	
	public Scene showMenu(Student stud)  {
		GridPane pane = new GridPane();
      	pane.setAlignment(Pos.CENTER);
      	pane.setHgap(5.5);
    	pane.setVgap(10);
      	Label heading = new Label("Welcome, " + stud.getFName() + "\nWhat would you like to do today?\n");
      	
      	Button searchBtn = new Button("Find Books");
      	Button viewBorrowedBtn = new Button("View Borrowed Items");
      	Button viewWaitListBtn = new Button("View Requested Items");
      	Button backBtn = new Button("Logout");
      	
      	searchBtn.setMaxWidth(WIDTH);
      	viewWaitListBtn.setMaxWidth(WIDTH);
      	viewBorrowedBtn.setMaxWidth(WIDTH);
      	
      	backBtn.setMaxWidth(WIDTH);
      	
      	
      	searchBtn.setOnAction(new EventHandler<ActionEvent>(){
			@Override public void handle(ActionEvent arg0) {
				try (BookSearchMenu bsm = new BookSearchMenu(stage, scene)) {
					stage.setScene(bsm.showMenu(stud));
					stage.setTitle("Book Search");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
      	});
      	
      	viewBorrowedBtn.setOnAction(new EventHandler<ActionEvent>(){
			@Override public void handle(ActionEvent arg0) {
				try(Connection connection = DriverManager.getConnection(postgreSQLHeroku.DATABASE_URL, postgreSQLHeroku.DATABASE_USERNAME, postgreSQLHeroku.DATABASE_PASSWORD)) {

					Statement statement = connection.createStatement();
					String query = "";
					
					
					query = String.format("select s.studentno, s.fname, s.lname, lib.libid, lib.title, lib.author, lib.publisher, lib.media_type from students s "
							+ "join borrowedobjects bo on (s.studentno = bo.studentno) join library lib on (bo.libid = lib.libid) where s.%s='%s';",
							postgreSQLHeroku.COL_STUD_NO,stud.getStudentNo());
					//query = String.format("select * from %s where %s = %s;", postgreSQLHeroku.TABLE_BORROWED_OBJECTS, postgreSQLHeroku.COL_USERNAME, stud.getStudentNo());
					

					//System.out.println(query);
					
					ResultSet queryResult = statement.executeQuery(query); 
					
					try (BorrowedLibTable borrowTable = new BorrowedLibTable(stage, scene)) 
					{
						stage.setScene(borrowTable.showMenu(queryResult, stud));
						stage.setTitle("Your Borrowed Items");
					} catch (Exception e) {
						e.printStackTrace();
					}
					

				} catch (PSQLException e2) {
					e2.printStackTrace();
				} catch (SQLException e1) {
					e1.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
      	});
      	
      	viewWaitListBtn.setOnAction(new EventHandler<ActionEvent>(){
			@Override public void handle(ActionEvent arg0) {
				try(Connection connection = DriverManager.getConnection(postgreSQLHeroku.DATABASE_URL, postgreSQLHeroku.DATABASE_USERNAME, postgreSQLHeroku.DATABASE_PASSWORD)) {

					Statement statement = connection.createStatement();
					String query = "";
					
					
					query = String.format("select s.studentno, s.fname, s.lname, lib.libid, lib.title, lib.author, lib.publisher, lib.media_type from students s "
							+ "join waitlistobjects bo on (s.studentno = bo.studentno) join library lib on (bo.libid = lib.libid) where s.%s='%s';",
							postgreSQLHeroku.COL_STUD_NO,stud.getStudentNo());
					//query = String.format("select * from %s where %s = %s;", postgreSQLHeroku.TABLE_BORROWED_OBJECTS, postgreSQLHeroku.COL_USERNAME, stud.getStudentNo());
					

					//System.out.println(query);
					
					ResultSet queryResult = statement.executeQuery(query); 
					
					try (WaitListLibTable waitListTable = new WaitListLibTable(stage, scene)) 
					{
						stage.setScene(waitListTable.showMenu(queryResult, stud));
						stage.setTitle("Your Queued Items");
					} catch (Exception e) {
						e.printStackTrace();
					}
					

				} catch (PSQLException e2) {
					e2.printStackTrace();
				} catch (SQLException e1) {
					e1.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
      	});

      	backBtn.setOnAction(e-> {
			
			try (Home homePage = new Home(stage, scene)) {
    			stage.setScene(homePage.showHomePage()); 
    			stage.setTitle("Library");
			} catch (Exception e2) {
				e2.printStackTrace();
			}
			
		}); 
      	
      	
      	//viewBorrowedBtn.setMinSize(150, 40);
      	backBtn.setStyle("-fx-background-color: #FE615A");
      	searchBtn.setStyle("-fx-background-color: #789E9E; -fx-text-fill: white");
      	viewWaitListBtn.setStyle("-fx-background-color: #789E9E; -fx-text-fill: white");
      	viewBorrowedBtn.setStyle("-fx-background-color: #789E9E; -fx-text-fill: white");
	    
      	pane.add(heading, 0, 0);
      	pane.add(searchBtn, 0, 1);
      	pane.add(viewWaitListBtn, 0, 2);
      	pane.add(viewBorrowedBtn, 0, 3);
      	pane.add(backBtn, 0, 4);
      	
      	pane.setStyle("-fx-background-color: #EEF3DB");
      	Scene scene = new Scene(pane, 350, 450);
	    scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
	    return scene;
	}

	@Override
	public void close() throws Exception {
		// TODO Auto-generated method stub
		
	}





}
