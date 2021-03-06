package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.postgresql.util.PSQLException;

import DatabaseTest.postgreSQLHeroku;
import classes.BorrowedBooksTableLine;
import classes.Librarian;
import classes.LibraryObjects;
import classes.Student;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class BookRequests implements AutoCloseable {

	private Stage stage;
	private Scene scene;
	
	private ObservableList<LibraryObjects> data = FXCollections.observableArrayList();
	private TableView<LibraryObjects> tableview = new TableView<>();
	
	public BookRequests(Stage stage, Scene scene) {
		this.stage = stage;
		this.scene = scene;
	}

	public Scene viewRequests() {
		try(Connection connection = DriverManager.getConnection(postgreSQLHeroku.DATABASE_URL, postgreSQLHeroku.DATABASE_USERNAME, postgreSQLHeroku.DATABASE_PASSWORD);
				Statement statement = connection.createStatement()) {
			
			String query = String.format("select B.%s, count(*) as \"%s\", B.%s as \"%s\", B.%s from %s A join %s B on A.%s=B.%s group by B.%s, B.%s, B.%s;",
											postgreSQLHeroku.COL_TITLE,
											postgreSQLHeroku.COL_QTY_REQD,
											postgreSQLHeroku.COL_QTY_AVAIL,
											postgreSQLHeroku.COL_QTY_AVAIL,
											postgreSQLHeroku.COL_ID,
											postgreSQLHeroku.TABLE_WAITLIST_OBJECTS,
											postgreSQLHeroku.TABLE_LIBRARY,
											postgreSQLHeroku.COL_ID,
											postgreSQLHeroku.COL_ID,
											postgreSQLHeroku.COL_ID,
											postgreSQLHeroku.COL_TITLE,
											postgreSQLHeroku.COL_QTY_AVAIL);
			
			ResultSet rs = statement.executeQuery(query);

			VBox vbox = new VBox();
	    	HBox hbox = new HBox();
	    	hbox.setPadding(new Insets(10,10,10,10));
	    	hbox.setSpacing(10);
			
			if(rs != null) {
				while (rs.next()) {
					data.add(new LibraryObjects(rs.getString(postgreSQLHeroku.COL_TITLE),
												rs.getInt(postgreSQLHeroku.COL_QTY_REQD),
												rs.getInt(postgreSQLHeroku.COL_QTY_AVAIL),
												rs.getInt(postgreSQLHeroku.COL_ID)));
		        }
				
				for (int i = 1; i < rs.getMetaData().getColumnCount(); i++) {
		            TableColumn<LibraryObjects, String> col = new TableColumn<>(rs.getMetaData().getColumnName(i));
		            col.setCellValueFactory(new PropertyValueFactory<LibraryObjects, String>(rs.getMetaData().getColumnName(i)));
		            col.setMinWidth(115);
		            tableview.getColumns().add(col);
		        }
				
				tableview.setItems(data);

			} else {
				System.out.println("No Book Issue Requests!");
			}
			
	        Button backBtn = new Button("Back");
	        backBtn.setOnAction(new EventHandler<ActionEvent>(){
				@Override public void handle(ActionEvent arg0) {
					try (LibrarianMenu librarianMenu = new LibrarianMenu(stage, scene)) {
						stage.setScene(librarianMenu.showMenu()); 
						stage.setTitle("Librarian Menu");
					} catch (Exception e2) {
						e2.printStackTrace();
					}
				}
			}); 
	        
	        Button viewWaitListBtn = new Button("View Wait List");
	        viewWaitListBtn.setOnAction((event) -> { this.waitList(); });

	        if(tableview.getItems().isEmpty()) {
	        	hbox.getChildren().addAll(backBtn);
	        } else {
	        	hbox.getChildren().addAll(backBtn, viewWaitListBtn);	
	        }
	        
	        vbox.getChildren().addAll(tableview, hbox);
	        
			scene = new Scene(vbox, 350, 450);
		    scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
	        
		} catch (PSQLException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return scene;
	}
	
	public void waitList() {
		if(tableview.getSelectionModel().isEmpty()) {
			AlertBox.display("Error", "Please select a Book to issue for!");
		} else {
			try (Connection connection2 = DriverManager.getConnection(postgreSQLHeroku.DATABASE_URL, postgreSQLHeroku.DATABASE_USERNAME, postgreSQLHeroku.DATABASE_PASSWORD); 
					Statement statement2 = connection2.createStatement()) {

		    	ObservableList<Student> data2 = FXCollections.observableArrayList();
		    	TableView<Student> waitListTable = new TableView<>();
		    	
				Stage window = new Stage();
				window.initModality(Modality.APPLICATION_MODAL);
				window.setTitle("Book Wait List");
				window.setMinWidth(350);
				window.setMinHeight(400);

				int id = tableview.getSelectionModel().getSelectedItem().getLibid();
				
				String query2 = String.format("select A.%s, B.%s, B.%s, B.%s, B.%s from %s A join %s B on A.%s=B.%s where A.%s=" + id,     
								postgreSQLHeroku.COL_WAITID,
								postgreSQLHeroku.COL_STUD_FNAME,
								postgreSQLHeroku.COL_STUD_LNAME,
								postgreSQLHeroku.COL_STUD_LVL,
								postgreSQLHeroku.COL_STUD_NO,
								postgreSQLHeroku.TABLE_WAITLIST_OBJECTS,
								postgreSQLHeroku.TABLE_STUDENTS,
								postgreSQLHeroku.COL_STUD_NO,
								postgreSQLHeroku.COL_STUD_NO,
								postgreSQLHeroku.COL_ID);
				ResultSet rs2 = statement2.executeQuery(query2);

				System.out.println(query2);
				
				while(rs2.next()) {
					data2.add(new Student(
										rs2.getInt(postgreSQLHeroku.COL_WAITID),
										rs2.getString(postgreSQLHeroku.COL_STUD_FNAME),
										rs2.getString(postgreSQLHeroku.COL_STUD_LNAME),
										rs2.getString(postgreSQLHeroku.COL_STUD_LVL),
										rs2.getInt(postgreSQLHeroku.COL_STUD_NO)));
				}
				
				TableColumn<Student, String> waitidCol = new TableColumn<>("waitID");
				waitidCol.setMinWidth(80);
				waitidCol.setCellValueFactory(new PropertyValueFactory<>("waitid"));
				
				TableColumn<Student, String> fnameCol = new TableColumn<>("firstName");
		      	fnameCol.setMinWidth(120);
		      	fnameCol.setCellValueFactory(new PropertyValueFactory<>("fName"));
		      	
		      	TableColumn<Student, String> lnameCol = new TableColumn<>("lastName");
		      	lnameCol.setMinWidth(120);
		      	lnameCol.setCellValueFactory(new PropertyValueFactory<>("lName"));
				
		      	TableColumn<Student, String> levelCol = new TableColumn<>("level");
		      	levelCol.setMinWidth(120);
		      	levelCol.setCellValueFactory(new PropertyValueFactory<>("studentLvl"));
		      	
		      	TableColumn<Student,String> studnoCol = new TableColumn<>("studentNo");
		      	studnoCol.setMinWidth(120);
		      	studnoCol.setCellValueFactory(new PropertyValueFactory<>("studentNo"));

				waitListTable.setItems(data2);
				waitListTable.getColumns().addAll(waitidCol, fnameCol, lnameCol, levelCol, studnoCol);
				
		    	Button issueBooks = new Button("Issue Books");
		    	issueBooks.setOnAction(e-> { System.out.println("gonna issue books"); });
				
				Button closeBtn = new Button("Close");
				closeBtn.setOnAction(e-> { window.close(); });
				
				VBox layout = new VBox(10);
				HBox hbox2 = new HBox();
		    	hbox2.setPadding(new Insets(10,10,10,10));
		    	hbox2.setSpacing(10);
		    	hbox2.getChildren().addAll(issueBooks, closeBtn);
				layout.getChildren().addAll(waitListTable, hbox2);
				layout.setAlignment(Pos.CENTER);
				
				Scene scene2 = new Scene(layout);
				scene2.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
				window.setScene(scene2);
				window.showAndWait();
				
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}
	
	@Override
	public void close() throws Exception {
		// TODO Auto-generated method stub
	}
	
}
