package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import org.postgresql.util.PSQLException;

import DatabaseTest.postgreSQLHeroku;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class LibrarianMenu implements AutoCloseable {

	private Stage stage;
	private Scene scene;
	
	public LibrarianMenu(Stage stage, Scene scene) {
		this.stage = stage;
		this.scene = scene;
	}
	public Scene showMenu() throws Exception {
		GridPane pane = new GridPane();
		pane.setAlignment(Pos.CENTER);
		pane.setHgap(10);
		pane.setVgap(20);
		    
		Button addBtn = new Button("Add Book");
		addBtn.setOnAction(new EventHandler<ActionEvent>(){
			@Override public void handle(ActionEvent arg0) {
				try (AddBook book = new AddBook(stage, scene)) {
					stage.setScene(book.addBook());
					stage.setTitle("Adding Book");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		Button viewBtn = new Button("View Books");
		viewBtn.setOnAction(new EventHandler<ActionEvent>(){
			@Override public void handle(ActionEvent arg0) {
				try (ViewBooks book = new ViewBooks(stage, scene)) {
					stage.setScene(book.viewBooks());
					stage.setTitle("Viewing Book");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});


		Button issueBtn = new Button("Book Requests");
		issueBtn.setOnAction(new EventHandler<ActionEvent>(){

			@Override public void handle(ActionEvent arg0) {
				try (BookRequests issueBooks = new BookRequests(stage, scene)) {
					stage.setScene(issueBooks.viewRequests());
					stage.setTitle("Issuing Book");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		Button returnBtn = new Button("Return Book");
		
		returnBtn.setOnAction(new EventHandler<ActionEvent>(){
			@Override public void handle(ActionEvent arg0) {
				try(Connection connection = DriverManager.getConnection(postgreSQLHeroku.DATABASE_URL, postgreSQLHeroku.DATABASE_USERNAME, postgreSQLHeroku.DATABASE_PASSWORD)) {

					Statement statement = connection.createStatement();
					String query = "";
					
					
					query = String.format("select s.studentno, s.fname, s.lname, lib.libid, lib.title, lib.author, lib.publisher, lib.media_type from students s "
							+ "join borrowedobjects bo on (s.studentno = bo.studentno) join library lib on (bo.libid = lib.libid);",
							postgreSQLHeroku.COL_STUD_NO);
					//query = String.format("select * from %s where %s = %s;", postgreSQLHeroku.TABLE_BORROWED_OBJECTS, postgreSQLHeroku.COL_USERNAME, stud.getStudentNo());
					

					//System.out.println(query);
					
					ResultSet queryResult = statement.executeQuery(query); 
					
					try (BorrowedLibTableFull borrowTable = new BorrowedLibTableFull(stage, scene)) 
					{
						stage.setScene(borrowTable.showMenu(queryResult));
						stage.setTitle("All Borrowed Items");
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
		
		Button logoutBtn = new Button("Log out");
		logoutBtn.setOnAction(new EventHandler<ActionEvent>(){
			@Override public void handle(ActionEvent arg0) {
				try (Home homePage= new Home(stage, scene)) {
					stage.setScene(homePage.showHomePage()); 
					stage.setTitle("Library");
				} catch (Exception e2) {
					e2.printStackTrace();
				}	
			}
        }); 
			
		pane.add(addBtn, 0, 0);
		pane.add(viewBtn, 0, 1);
		pane.add(issueBtn, 0, 2);
		pane.add(returnBtn, 0, 3);
		pane.add(logoutBtn, 0, 5);
			
		Scene scene = new Scene(pane, 350, 450);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		return scene;
	}
	@Override
	public void close() throws Exception {
		// TODO Auto-generated method stub
		
	}
}
