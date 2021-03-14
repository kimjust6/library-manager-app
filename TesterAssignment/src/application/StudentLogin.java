package application;

import classes.Student;
import database.postgreSQLHeroku;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.postgresql.util.PSQLException;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class StudentLogin implements AutoCloseable {

	private Stage stage;
	private Scene scene;
	
	public StudentLogin(Stage stage, Scene scene) {
		this.stage = stage;
		this.scene = scene;
	}
	
	public Scene showLoginPage() throws Exception {
		//declare pane
		GridPane pane = new GridPane();
		//set alignment
		pane.setAlignment(Pos.CENTER);
		//set gaps
		pane.setHgap(5.5);
	    pane.setVgap(10);
	    
		Label userLabel = new Label("Enter Student ID:");
        TextField studentNoField = new TextField();
        Button backBtn = new Button("Back");
        Button btn = new Button("Login");
        
        //action handler for pressing enter
        btn.setOnAction((event) -> { this.handle(studentNoField.getText()); });
		pane.setOnKeyPressed((event) -> {
			if(event.getCode() == KeyCode.ENTER) {
				this.handle(studentNoField.getText());
			}
		});        
		
		//action handler for back button
       backBtn.setOnAction(e-> {
        	try (Home homePage = new Home(stage, scene);) {
    			stage.setScene(homePage.showHomePage()); 
            	stage.setTitle("Library");
    		} catch (Exception e2) {
    			e2.printStackTrace();
    		}
       }); 
       
       //style
       btn.setStyle("-fx-background-color: mediumaquamarine");
       backBtn.setStyle("-fx-background-color: coral");
       
       //adding to the pane
       pane.add(userLabel, 0 , 0);
       pane.add(studentNoField, 1 , 0);
       pane.add(btn, 1, 1);
       pane.add(backBtn, 1, 2);
       
       //style
       pane.setStyle("-fx-background-color: #B7D8D6");

       //creating a new scene and adding pane to it
       Scene scene = new Scene(pane, 350, 450);
       
       //adding an external css styling doc
	   scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
	   return scene;
	}
	//function that handles the enter key/button press
	public void handle(String studentNo) {
		if (studentNo == "") 
		{
			
			System.out.println("Please enter a valid Student Number!");
			AlertBox.display("Error!", "Please enter a valid Student Number!");
		}
		else
		{
			//connect to the database
			try(Connection connection = DriverManager.getConnection(postgreSQLHeroku.DATABASE_URL, postgreSQLHeroku.DATABASE_USERNAME, postgreSQLHeroku.DATABASE_PASSWORD)) {

				Statement statement = connection.createStatement();
				//generating the query string
				String query = "select * from " + postgreSQLHeroku.TABLE_STUDENTS + " where " + postgreSQLHeroku.COL_STUD_NO + "=" + studentNo + ";";
				//getting the result of the sql query
				ResultSet queryResult = statement.executeQuery(query); 
				//checking if there is a result
				if(queryResult.next()) {
					//System.out.println("Student is " + queryResult.getString(postgreSQLHeroku.COL_STUD_LVL));
					
					//if there is a result, create a new student object and pass it to the StudentMenu class
					try (StudentMenu studentMenu = new StudentMenu(stage, scene)) 
					{
						//create new Student object
						Student stud = new Student(
								queryResult.getString(postgreSQLHeroku.COL_STUD_FNAME),
								queryResult.getString(postgreSQLHeroku.COL_STUD_LNAME),
								queryResult.getString(postgreSQLHeroku.COL_STUD_LVL),
								queryResult.getInt(postgreSQLHeroku.COL_STUD_NO)
								);
						stage.setScene(studentMenu.showMenu(stud));
						stage.setTitle("Student Home Page");
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else {
					//otherwise there is no matches
					AlertBox.display("Error!", "Student not found!");
				}
			} catch (PSQLException e2) {
				e2.printStackTrace();
			} catch (SQLException e1) {
				e1.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
    }
	@Override
	public void close() throws Exception {
		// TODO Auto-generated method stub
		
	}
}