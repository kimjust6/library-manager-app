package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.postgresql.util.PSQLException;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import javafx.util.Callback;
import DatabaseTest.postgreSQLHeroku;
import classes.Librarian;

public class LibrarianTable implements AutoCloseable {

	private Stage stage;
	private Scene scene;
	private final int WIDTH = 250;
	private final int PADDING = 10;
	
	public LibrarianTable(Stage stage, Scene scene) {
		this.stage = stage;
		this.scene = scene;
	}

	public Scene display() {
		try(Connection connection = DriverManager.getConnection(postgreSQLHeroku.DATABASE_URL, postgreSQLHeroku.DATABASE_USERNAME, postgreSQLHeroku.DATABASE_PASSWORD);
				Statement statement = connection.createStatement()) {
			
			String query = String.format("select * from %s where %s='%s';", postgreSQLHeroku.TABLE_USERS, postgreSQLHeroku.COL_USERS_ADMINTYPE, postgreSQLHeroku.TYPE_LIBRARIAN);
			ResultSet rs = statement.executeQuery(query);

			VBox vbox = new VBox();
	    	HBox hbox = new HBox();
	    	hbox.setPadding(new Insets(PADDING,PADDING,PADDING,PADDING));
	    	hbox.setSpacing(PADDING);
	    	
			ObservableList<Librarian> data = FXCollections.observableArrayList();
			TableView<Librarian> tableview = new TableView<>();
			
			if(rs != null) {
				while (rs.next()) {
					for (int i = 0; i < rs.getMetaData().getColumnCount() - 1; i++) {
			            TableColumn<Librarian, String> col = new TableColumn<>(rs.getMetaData().getColumnName(i + 1));
			            col.setCellValueFactory(new PropertyValueFactory<>(rs.getMetaData().getColumnName(i + 1)));
			            tableview.getColumns().addAll(col);
			        }
		            data.add(new Librarian(
		            				rs.getString(postgreSQLHeroku.COL_USERNAME), 
		            				rs.getString(postgreSQLHeroku.COL_USERS_FNAME), 
		            				rs.getString(postgreSQLHeroku.COL_USERS_LNAME), 
		            				rs.getString(postgreSQLHeroku.COL_USERS_EMAIL), 
		            				rs.getString(postgreSQLHeroku.COL_USERS_PHONE), 
		            				rs.getDate(postgreSQLHeroku.COL_USERS_HIREDATE)
		            			));
		        }
				
				scene = new Scene(tableview, 445, 450);
			    scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			} else {
				System.out.println("No Librarians in database.");
			}
			
			tableview.setItems(data);
			
	        Button backBtn = new Button("Back");
	        backBtn.setOnAction(new EventHandler<ActionEvent>(){
				@Override public void handle(ActionEvent arg0) {
					try (AdminMenu adminMenu = new AdminMenu(stage, scene)) {
						stage.setScene(adminMenu.showMenu()); 
						stage.setTitle("Admin Menu");
					} catch (Exception e2) {
						e2.printStackTrace();
					}
				}
			}); 

	        hbox.getChildren().addAll(backBtn);
	        vbox.getChildren().addAll(tableview,hbox);
	        
		} catch (PSQLException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	    return scene;
	}

	@Override
	public void close() throws Exception {
		// TODO Auto-generated method stub
		
	}

}
