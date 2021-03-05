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
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import javafx.util.Callback;
import DatabaseTest.postgreSQLHeroku;

public class LibrarianTable implements AutoCloseable {

	private Stage stage;
	private Scene scene;
	private final int WIDTH = 250;
	private final int PADDING = 10;
	
	public LibrarianTable(Stage stage, Scene scene) {
		this.stage = stage;
		this.scene = scene;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Scene display() {
		try(Connection connection = DriverManager.getConnection(postgreSQLHeroku.DATABASE_URL, postgreSQLHeroku.DATABASE_USERNAME, postgreSQLHeroku.DATABASE_PASSWORD)) {
			
			Statement statement = connection.createStatement();
			String query = String.format("select * from %s where %s='%s';", postgreSQLHeroku.TABLE_USERS, postgreSQLHeroku.COL_USERS_ADMINTYPE, postgreSQLHeroku.TYPE_LIBRARIAN);
			ResultSet rs = statement.executeQuery(query);

			VBox vbox = new VBox();
	    	HBox hbox = new HBox();
	    	hbox.setPadding(new Insets(PADDING,PADDING,PADDING,PADDING));
	    	hbox.setSpacing(PADDING);
	    	
			ObservableList<ObservableList> data = FXCollections.observableArrayList();
			TableView tableview = new TableView();
			
			if(rs != null) {
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
				
				scene = new Scene(tableview, 445, 450);
			    scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			} else {
				System.out.println("No Librarians in database.");
			}
			
			tableview.setItems(data);
			
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
