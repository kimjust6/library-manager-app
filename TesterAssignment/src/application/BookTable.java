package application;

import java.sql.ResultSet;
import java.sql.SQLException;

import DatabaseTest.postgreSQLHeroku;
import classes.LibraryObjects;
import classes.Student;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class BookTable  implements AutoCloseable {

	final int WIDTH = 250;
	final int PADDING = 10;
	private Stage stage;
	private Scene scene;
	
	private ObservableList<LibraryObjects> lo =  FXCollections.observableArrayList();
	TableView<LibraryObjects> table = new TableView<>();
	
	public BookTable(Stage stage, Scene scene) {
		this.stage = stage;
		this.scene = scene;
	}
	
	
	public Scene showMenu(ResultSet rs, Student stud)  {

    	VBox vbox = new VBox();
    	HBox hbox = new HBox();
    	hbox.setPadding(new Insets(PADDING,PADDING,PADDING,PADDING));
    	hbox.setSpacing(PADDING);
    	
      	//Label heading = new Label("Here are your results.\n");
      	

      	Button backBtn = new Button("Back");
      	Button waitlistBtn= new Button("Request Waitlist");
      	hbox.getChildren().addAll(waitlistBtn,backBtn);
      	backBtn.setMaxWidth(WIDTH);
      	

      	//try and create the table
      	try
      	{
      		while (rs.next())
          	{
      			
          		lo.add(new LibraryObjects(rs.getString(postgreSQLHeroku.COL_TITLE),
          				rs.getString(postgreSQLHeroku.COL_AUTHOR),
          				rs.getString(postgreSQLHeroku.COL_PUBLISHER),
          				rs.getString(postgreSQLHeroku.COL_MEDIA_TYPE),
          				rs.getInt(postgreSQLHeroku.COL_QTY_AVAIL),
          				rs.getInt(postgreSQLHeroku.COL_QTY_BOR),
          				rs.getInt(postgreSQLHeroku.COL_ID)));
          				
          	}
      	}
      	catch (SQLException e)
      	{
      		e.printStackTrace();
      	}
      	
      	
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
			
      		try (StudentMenu studentMenu = new StudentMenu(stage, scene)) 
			{

				stage.setScene(studentMenu.showMenu(stud));
				stage.setTitle("Student Home Page");
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			
		});
      	
      	waitlistBtn.setOnAction(e-> {
      		ObservableList<LibraryObjects> selected, allItems;
      		allItems = table.getItems();
      		selected = table.getSelectionModel().getSelectedItems();
      		System.out.println(selected.get(0).getTitle());
			LibraryObjects lObj = new LibraryObjects();
		});
      	
      	//viewBorrowedBtn.setMinSize(150, 40);
      	
      	//pane.add(heading, 0, 0);
      	//pane.add(table, 0, 0);
      	//pane.add(searchBtn, 0, 1);
      	//pane.add(reqIssueBtn, 0, 2);
      	//pane.add(viewBorrowedBtn, 0, 3);
      	//pane.add(backBtn, 0, 4);
      	
      	vbox.getChildren().addAll(table,hbox);
      	
      	//Scene scene = new Scene(vbox, 350, 450);
      	Scene scene = new Scene(vbox);
	    scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
	    return scene;
	}
	
  	@FXML
	public void clickItem(MouseEvent event)
	{
	    if (event.getClickCount() == 1) //Checking double click
	    {
	        System.out.println(table.getSelectionModel().getSelectedItem().getTitle());
	        System.out.println(table.getSelectionModel().getSelectedItem().getAuthor());
	        System.out.println(table.getSelectionModel().getSelectedItem().getPublisher());
	    }
	}
	

	
	@Override
	public void close() throws Exception {
		// TODO Auto-generated method stub
		
	}





}
