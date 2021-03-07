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

	ObservableList<LibraryObjects> data = FXCollections.observableArrayList();
	TableView<LibraryObjects> tableview = new TableView<>();
	
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
	    	
	    	while (rs.next()) {
				data.add(new LibraryObjects(rs.getString(postgreSQLHeroku.COL_TITLE),
											rs.getInt(postgreSQLHeroku.COL_QTY_REQD),
											rs.getInt(postgreSQLHeroku.COL_QTY_AVAIL),
											rs.getInt(postgreSQLHeroku.COL_ID)));
	        }
			
			for (int i = 1; i < rs.getMetaData().getColumnCount(); i++) {
	            TableColumn<LibraryObjects, String> col = new TableColumn<>(rs.getMetaData().getColumnName(i));
	            col.setCellValueFactory(new PropertyValueFactory<LibraryObjects, String>(rs.getMetaData().getColumnName(i)));
	            col.setMinWidth(160);
	            tableview.getColumns().add(col);
	        }
			
			tableview.setItems(data);
			
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
	        viewWaitListBtn.setOnAction((event) -> { 
	        	if(!tableview.getSelectionModel().isEmpty()) {
	        		
	        		Stage window = new Stage();
	    			window.initModality(Modality.APPLICATION_MODAL);
	    			window.setTitle("Book Wait List");
	    			window.setMinWidth(350);
	    			window.setMinHeight(400);
	        		
	        		this.waitList(tableview.getSelectionModel().getSelectedItem().getLibid(), window);
	        		
	    			window.showAndWait();
	        		
					try(BookRequests bookRequests = new BookRequests(stage, scene)) {
	        			stage.setScene(bookRequests.viewRequests());
	        		} catch (Exception e3) {
	        			e3.printStackTrace();
	        		}
	        	} else {
	        		AlertBox.display("Error", "Please select a Book to issue for!");
	        	}
	        });

			VBox vbox = new VBox();
	    	HBox hbox = new HBox();
	    	hbox.setPadding(new Insets(10,10,10,10));
	    	hbox.setSpacing(10);
	    	
	        if(tableview.getItems().isEmpty()) hbox.getChildren().addAll(backBtn);
	        else hbox.getChildren().addAll(backBtn, viewWaitListBtn);	
	        
	        vbox.getChildren().addAll(tableview, hbox);
	        
			scene = new Scene(vbox, 500, 450);
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
	
	public void waitList(int libID, Stage window) {
		try (Connection connection = DriverManager.getConnection(postgreSQLHeroku.DATABASE_URL, postgreSQLHeroku.DATABASE_USERNAME, postgreSQLHeroku.DATABASE_PASSWORD); 
				Statement statement = connection.createStatement()) {

			ObservableList<Student> studentData = FXCollections.observableArrayList();
			TableView<Student> waitListTable = new TableView<>();
			
//			Stage window = new Stage();
//			window.initModality(Modality.APPLICATION_MODAL);
//			window.setTitle("Book Wait List");
//			window.setMinWidth(350);
//			window.setMinHeight(400);
			
			
			String query = String.format("select row_number() over(order by A.%s) as \"%s\", B.%s, B.%s, B.%s, B.%s from %s A join %s B on A.%s=B.%s where A.%s=" + libID,     
							postgreSQLHeroku.COL_WAITID,
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
			ResultSet rs = statement.executeQuery(query);
			
			while(rs.next()) {
				studentData.add(new Student(
									rs.getInt(postgreSQLHeroku.COL_WAITID),
									rs.getString(postgreSQLHeroku.COL_STUD_FNAME),
									rs.getString(postgreSQLHeroku.COL_STUD_LNAME),
									rs.getString(postgreSQLHeroku.COL_STUD_LVL),
									rs.getInt(postgreSQLHeroku.COL_STUD_NO)));
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

			waitListTable.setItems(studentData);
			waitListTable.getColumns().addAll(waitidCol, fnameCol, lnameCol, levelCol, studnoCol);
			
	    	Button issueBooks = new Button("Issue Book");
	    	
	    	issueBooks.setOnAction(e-> { 
	    		try (Connection connection2 = DriverManager.getConnection(postgreSQLHeroku.DATABASE_URL, postgreSQLHeroku.DATABASE_USERNAME, postgreSQLHeroku.DATABASE_PASSWORD);
	    				Statement getQty = connection2.createStatement()) {
	    			ResultSet rs2 = getQty.executeQuery(String.format("select %s from %s where %s=" + libID, postgreSQLHeroku.COL_QTY_AVAIL, postgreSQLHeroku.TABLE_LIBRARY, postgreSQLHeroku.COL_ID));
	    			
	    			if(rs2.next()) {
	    				if(rs2.getInt(postgreSQLHeroku.COL_QTY_AVAIL) > 0) {
				    		if(!waitListTable.getSelectionModel().isEmpty()) {
				    			int studentID = waitListTable.getSelectionModel().getSelectedItem().getStudentNo();
				    			
				    			if(this.issueBooks(studentID, libID)) {	
				    				AlertBox.display("Success", "Issued Book for student #" + studentID + "!");
				    				this.waitList(libID, window);
				    			} else {
				    				AlertBox.display("Error", "Failed to issue book!");
				    			}
				    		} else {
				    			AlertBox.display("Error", "Please select a student to issue book for!");
				    		} 
			    		} else {
			    			AlertBox.display("Error", "Not enough books to issue!");
			    		}
	    			}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
	    	});
			
			Button closeBtn = new Button("Close");
			closeBtn.setOnAction(e-> { window.close(); });
			
			VBox layout = new VBox(10);
			HBox hbox = new HBox();
	    	hbox.setPadding(new Insets(10,10,10,10));
	    	hbox.setSpacing(10);
	    	hbox.getChildren().addAll(issueBooks, closeBtn);
			layout.getChildren().addAll(waitListTable, hbox);
			layout.setAlignment(Pos.CENTER);
			
			Scene scene = new Scene(layout);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			window.setScene(scene);
			
		} catch (Exception e2) {
			e2.printStackTrace();
		}
	}
	
	public boolean issueBooks(int studentID, int libID) {
		String insert = String.format("insert into %s values(?,?)", postgreSQLHeroku.TABLE_BORROWED_OBJECTS);
		
		String update = String.format("update %s set %s=%s+1, %s=%s-1 where %s=?;", 
							postgreSQLHeroku.TABLE_LIBRARY,
							postgreSQLHeroku.COL_QTY_BOR,
							postgreSQLHeroku.COL_QTY_BOR,
							postgreSQLHeroku.COL_QTY_AVAIL,
							postgreSQLHeroku.COL_QTY_AVAIL,
							postgreSQLHeroku.COL_ID);
		
		String delete = String.format("delete from %s where %s=? and %s=?", 
				 			postgreSQLHeroku.TABLE_WAITLIST_OBJECTS,
				 			postgreSQLHeroku.COL_STUD_NO,
				 			postgreSQLHeroku.COL_ID);
		
		try (Connection connection = DriverManager.getConnection(postgreSQLHeroku.DATABASE_URL, postgreSQLHeroku.DATABASE_USERNAME, postgreSQLHeroku.DATABASE_PASSWORD); 
				PreparedStatement insertBorrowedObj = connection.prepareStatement(insert);
				PreparedStatement updateLibraryObj = connection.prepareStatement(update);
				PreparedStatement deleteWaitListObj = connection.prepareStatement(delete)) {
	    	
			connection.setAutoCommit(false);
			
			insertBorrowedObj.setInt(1, studentID);
			insertBorrowedObj.setInt(2, libID);
			
			updateLibraryObj.setInt(1, libID);

			deleteWaitListObj.setInt(1, studentID);
			deleteWaitListObj.setInt(2, libID);
			
			if(insertBorrowedObj.executeUpdate() == 1 && updateLibraryObj.executeUpdate() == 1 && deleteWaitListObj.executeUpdate() == 1) {
				connection.commit();
				return true;
			} else {
				connection.rollback();
				return false;
			}
			
		} catch (Exception e2) {
			e2.printStackTrace();
			return false;
		}
	}
	
	@Override
	public void close() throws Exception {
		// TODO Auto-generated method stub
	}
	
}
