package application;

import java.io.*;
import javafx.fxml.FXML;
import classes.LibraryObjects;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import DatabaseTest.postgreSQLHeroku;
import classes.BorrowedBooksTableLine;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class ViewBooks implements AutoCloseable {
	final int WIDTH = 250;
	final int PADDING = 10;
	private Stage stage;
	private Scene scene;
	private String lines = "";
	private ObservableList<LibraryObjects> lo =  FXCollections.observableArrayList();
	TableView<LibraryObjects> table = new TableView<>();
	
	ViewBooks(Stage stage, Scene scene){
		this.stage = stage;
		this.scene = scene;
	}
	
	public Scene viewBooks() throws Exception, IOException, FileNotFoundException{
		//READ THE BOOKS FROM DB AND GENERATE OUTPUT FILE

    	Button saveFileBtn = new Button("Save to File");
		
    	try (Connection connection = DriverManager.getConnection(postgreSQLHeroku.DATABASE_URL, postgreSQLHeroku.DATABASE_USERNAME, postgreSQLHeroku.DATABASE_PASSWORD))
    	{
    		String query = String.format("SELECT * FROM %s;", postgreSQLHeroku.TABLE_LIBRARY);
    		Statement statement = connection.createStatement();
    		ResultSet rs = statement.executeQuery(query);


	    		while(rs.next())
	    		{
	    			lo.add(new LibraryObjects(rs.getString(postgreSQLHeroku.COL_TITLE),
              				rs.getString(postgreSQLHeroku.COL_AUTHOR),
              				rs.getString(postgreSQLHeroku.COL_PUBLISHER),
              				rs.getString(postgreSQLHeroku.COL_MEDIA_TYPE),
              				rs.getInt(postgreSQLHeroku.COL_QTY_AVAIL),
              				rs.getInt(postgreSQLHeroku.COL_QTY_BOR),
              				rs.getInt(postgreSQLHeroku.COL_ID)));

	          		lines += rs.getString(postgreSQLHeroku.COL_TITLE) + ", "
	          				+ rs.getString(postgreSQLHeroku.COL_AUTHOR) + ", "
	          				+ rs.getString(postgreSQLHeroku.COL_PUBLISHER) + ", "
	          				+ rs.getString(postgreSQLHeroku.COL_MEDIA_TYPE) + ", "
	          				+ rs.getInt(postgreSQLHeroku.COL_QTY_AVAIL) + ", "
              				+ rs.getInt(postgreSQLHeroku.COL_QTY_BOR) + ", "
	          				+ rs.getString(postgreSQLHeroku.COL_ID) + "\n";

	    			System.out.println("");
	    		}
	    		
	    		
    		}

    	
    	saveFileBtn.setOnAction(e-> {

      		//create the file
      		try {
      	      File myObj = new File("All Books.csv");
      	      if (myObj.createNewFile()) {
      	        System.out.println("File created: " + myObj.getName());
      	      } else {
      	        System.out.println("File already exists.");
      	      }
      	    } catch (IOException e2) {
      	      System.out.println("An error occurred.");
      	      AlertBox.display("Error!", "Could not open the file!");
      	      e2.printStackTrace();
      	    }
      		
      		try {
      	      FileWriter myWriter = new FileWriter("All Books.csv");
      	      myWriter.write("");
      	      myWriter.write(lines);
      	      myWriter.close();
      	      System.out.println("Successfully wrote to the file.");
      	      AlertBox.display("Success!", "Successfully saved to file!");
      	    } catch (IOException e2) {
      	      System.out.println("An error occurred.");
      	      AlertBox.display("Error!", "Could not save to the file!");
      	      e2.printStackTrace();
      	    }
      		

      	});
    	
    	
    	//DISPLAY IN A NICE TABLE
    	Scene scene = new Scene(new Group(), 450, 450);
    	VBox vbox = new VBox();
    	HBox hbox = new HBox();
    	hbox.setPadding(new Insets(PADDING,PADDING,PADDING,PADDING));
    	hbox.setSpacing(PADDING);
    	
    	Button backBtn = new Button("Back");
      	hbox.getChildren().addAll(backBtn,saveFileBtn);
      	backBtn.setMaxWidth(WIDTH);
      	
		 
      //try and create the table
      	
		
      //Column Title
      	TableColumn<LibraryObjects,String> titleCol = new TableColumn<>("Title");
      	titleCol.setMinWidth(200);
      	titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
      	
      	//Column Author
      	TableColumn<LibraryObjects,String> authCol = new TableColumn<>("Author");
      	authCol.setMinWidth(150);
      	authCol.setCellValueFactory(new PropertyValueFactory<>("author"));
      	
      	//Column Publisher
      	TableColumn<LibraryObjects,String> pubCol = new TableColumn<>("Publisher");
      	pubCol.setMinWidth(150);
      	pubCol.setCellValueFactory(new PropertyValueFactory<>("publisher"));
      	
      	//Column MediaType
      	TableColumn<LibraryObjects,String> typeCol = new TableColumn<>("Type");
      	typeCol.setMinWidth(100);
      	typeCol.setCellValueFactory(new PropertyValueFactory<>("mediaType"));
      	
      	//Column Qty Available
      	TableColumn<LibraryObjects,String> aQtyCol = new TableColumn<>("# Available");
      	aQtyCol.setMinWidth(120);
      	aQtyCol.setCellValueFactory(new PropertyValueFactory<>("qtyAvailable"));
      	
      	//Column Qty Borrowed
      	TableColumn<LibraryObjects,String> bQtyCol = new TableColumn<>("# Borrowed");
      	bQtyCol.setMinWidth(120);
      	bQtyCol.setCellValueFactory(new PropertyValueFactory<>("qtyBorrowed"));
      	
      	//Column ID
      	TableColumn<LibraryObjects,String> idCol = new TableColumn<>("ID");
      	//idCol.setMinWidth(80);
      	idCol.setCellValueFactory(new PropertyValueFactory<>("libid"));
      	
      	
      	table.setItems(lo);
      	table.getColumns().addAll(idCol, titleCol, authCol, pubCol, typeCol, aQtyCol, bQtyCol);
      	

      	backBtn.setOnAction(e-> {
      		try (LibrarianMenu librarianMenu = new LibrarianMenu(stage, scene)) {
				stage.setScene(librarianMenu.showMenu());
				stage.setTitle("Librarian Menu");
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			
		}); 
      	vbox.getChildren().addAll(table,hbox);
      	
      	//Scene scene = new Scene(vbox, 350, 450);
      	Scene scene1 = new Scene(vbox);
	    scene1.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
	    return scene1;
	}
	@Override
	public void close() throws Exception {
		// TODO Auto-generated method stub
		
	}

}
