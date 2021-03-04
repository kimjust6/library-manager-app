package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import DatabaseTest.postgreSQLHeroku;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
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
        
        Button btn = new Button("Login");
        btn.setOnAction(new EventHandler<ActionEvent>(){
			@Override public void handle(ActionEvent arg0) {
				
				// need to authenticate student as well
				String studentNo = studentNoField.getText();
				
				try(Connection connection = DriverManager.getConnection(postgreSQLHeroku.DATABASE_URL, postgreSQLHeroku.DATABASE_USERNAME, postgreSQLHeroku.DATABASE_PASSWORD)) {

					Statement statement = connection.createStatement();
					
					String query = "select * from " + postgreSQLHeroku.TABLE_STUDENTS + " where " + postgreSQLHeroku.COL_STUD_NO + "=" + studentNo + ";";
					ResultSet queryResult = statement.executeQuery(query); 
					
					if(queryResult.next()) {
						System.out.println("Student is " + queryResult.getString(postgreSQLHeroku.COL_STUD_LVL));
						try (StudentMenu studentMenu = new StudentMenu(stage, scene)) {
							stage.setScene(studentMenu.showMenu(studentNo));
							stage.setTitle("Student Home Page");
						} catch (Exception e) {
							e.printStackTrace();
						}
					} else {
						System.out.println("Student not found!");
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
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
	
	@Override
	public void close() throws Exception {
		// TODO Auto-generated method stub
		
	}
	
}
