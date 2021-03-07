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
		GridPane pane = new GridPane();
		pane.setAlignment(Pos.CENTER);
		pane.setHgap(5.5);
	    pane.setVgap(10);
	    
		Label userLabel = new Label("Enter Student ID:");
        TextField studentNoField = new TextField();
        Button backBtn = new Button("Back");
        Button btn = new Button("Login");
        
        btn.setOnAction((event) -> { this.handle(studentNoField.getText()); });
		pane.setOnKeyPressed((event) -> {
			if(event.getCode() == KeyCode.ENTER) {
				this.handle(studentNoField.getText());
			}
		});        

       backBtn.setOnAction(e-> {
        	try (Home homePage = new Home(stage, scene);) {
    			stage.setScene(homePage.showHomePage()); 
            	stage.setTitle("Library");
    		} catch (Exception e2) {
    			e2.printStackTrace();
    		}
       }); 
       pane.add(userLabel, 0 , 0);
       pane.add(studentNoField, 1 , 0);
       pane.add(btn, 1, 1);
       pane.add(backBtn, 1, 2);
       
       Scene scene = new Scene(pane, 350, 450);
	   scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
	   return scene;
	}
	public void handle(String studentNo) {
		if (studentNo == "") 
		{
			
			System.out.println("Please enter a valid Student Number!");
			AlertBox.display("Error!", "Please enter a valid Student Number!");
		}
		else
		{
			try(Connection connection = DriverManager.getConnection(postgreSQLHeroku.DATABASE_URL, postgreSQLHeroku.DATABASE_USERNAME, postgreSQLHeroku.DATABASE_PASSWORD)) {

				Statement statement = connection.createStatement();
				
				String query = "select * from " + postgreSQLHeroku.TABLE_STUDENTS + " where " + postgreSQLHeroku.COL_STUD_NO + "=" + studentNo + ";";
				ResultSet queryResult = statement.executeQuery(query); 

				if(queryResult.next()) {
					//System.out.println("Student is " + queryResult.getString(postgreSQLHeroku.COL_STUD_LVL));
					
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