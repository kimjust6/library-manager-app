package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.postgresql.util.PSQLException;

import database.postgreSQLHeroku;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
		
		Image image = new Image(getClass().getResourceAsStream("images/add.png"));
	    ImageView imgV= new ImageView(image);
	    imgV.setFitHeight(40);
	    imgV.setFitWidth(40);
		
	    Image image1 = new Image(getClass().getResourceAsStream("images/coffee.png"));
	    ImageView imgV1= new ImageView(image1);
	    imgV1.setFitHeight(40);
	    imgV1.setFitWidth(40);
	    
	    Image image2 = new Image(getClass().getResourceAsStream("images/request.png"));
	    ImageView imgV2= new ImageView(image2);
	    imgV2.setFitHeight(40);
	    imgV2.setFitWidth(40);
	    
	    Image image3 = new Image(getClass().getResourceAsStream("images/return.png"));
	    ImageView imgV3= new ImageView(image3);
	    imgV3.setFitHeight(40);
	    imgV3.setFitWidth(40);
	    
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
		logoutBtn.setStyle("-fx-background-color: #FE615A");
		addBtn.setStyle("-fx-background-color: #789E9E; -fx-text-fill: white");
	    viewBtn.setStyle("-fx-background-color: #789E9E; -fx-text-fill: white");
	    issueBtn.setStyle("-fx-background-color: #789E9E; -fx-text-fill: white");
	    returnBtn.setStyle("-fx-background-color: #789E9E; -fx-text-fill: white");
	    
		pane.add(imgV, 0, 0);	
		pane.add(addBtn, 1, 0);
		pane.add(imgV1, 0, 1);
		pane.add(viewBtn, 1, 1);
		pane.add(imgV2, 0, 2);
		pane.add(issueBtn, 1, 2);
		pane.add(imgV3, 0, 3);
		pane.add(returnBtn, 1, 3);
		pane.add(logoutBtn, 1, 5);
		
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
