package application;

import java.sql.ResultSet;
import java.sql.SQLException;

import DatabaseTest.postgreSQLHeroku;
import classes.LibraryObjects;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class BookTable  implements AutoCloseable {

	final int WIDTH = 250;
	private Stage stage;
	private Scene scene;
	
	public BookTable(Stage stage, Scene scene) {
		this.stage = stage;
		this.scene = scene;
	}
	
	
	public Scene showMenu(ResultSet rs)  {
		GridPane pane = new GridPane();
      	pane.setAlignment(Pos.CENTER);
      	pane.setHgap(5.5);
    	pane.setVgap(10);
      	//Label heading = new Label("Here are your results.\n");
      	
      	Button searchBtn = new Button("Find Books");
      	Button reqIssueBtn = new Button("Request a book");
      	Button viewBorrowedBtn = new Button("Borrow books");
      	Button backBtn = new Button("Back");
      	
      	searchBtn.setMaxWidth(WIDTH);
      	reqIssueBtn.setMaxWidth(WIDTH);
      	viewBorrowedBtn.setMaxWidth(WIDTH);
      	backBtn.setMaxWidth(WIDTH);
      	

      	
      	ObservableList<LibraryObjects> lo =  FXCollections.observableArrayList();
      	//try and create the table
      	try
      	{
      		while (rs.next())
          	{
      			//System.out.println(rs.getString(postgreSQLHeroku.COL_TITLE));
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
      	
      	//add dummy tester inputs
//  		lo.add(new LibraryObjects("harry potter and the nicee memes",
//  				"J.K.",
//  				"An authentic publisher",
//  				"Book",
//  				1000,
//  				69,
//  				40));
      	
      	//Column Title
      	TableColumn<LibraryObjects,String> titleCol = new TableColumn<>("Title");
      	titleCol.setMinWidth(200);
      	//titleCol.setCellValueFactory(new PropertyValueFactory<>(postgreSQLHeroku.COL_TITLE));
      	titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
      	
      	//Column Author
      	TableColumn<LibraryObjects,String> authCol = new TableColumn<>("Author");
      	authCol.setMinWidth(200);
      	//titleCol.setCellValueFactory(new PropertyValueFactory<>(postgreSQLHeroku.COL_AUTHOR));
      	authCol.setCellValueFactory(new PropertyValueFactory<>("author"));
      	
      	//Column Publisher
      	TableColumn<LibraryObjects,String> pubCol = new TableColumn<>("Publisher");
      	pubCol.setMinWidth(200);
      	//titleCol.setCellValueFactory(new PropertyValueFactory<>(postgreSQLHeroku.COL_PUBLISHER));
      	pubCol.setCellValueFactory(new PropertyValueFactory<>("publisher"));
      	
      	//Column MediaType
      	TableColumn<LibraryObjects,String> typeCol = new TableColumn<>("Type");
      	typeCol.setMinWidth(200);
      	//titleCol.setCellValueFactory(new PropertyValueFactory<>(postgreSQLHeroku.COL_MEDIA_TYPE));
      	typeCol.setCellValueFactory(new PropertyValueFactory<>("mediaType"));
      	
      	//Column Qty Available
      	TableColumn<LibraryObjects,String> aQtyCol = new TableColumn<>("# Available");
      	aQtyCol.setMinWidth(200);
      	//titleCol.setCellValueFactory(new PropertyValueFactory<>(postgreSQLHeroku.COL_QTY_AVAIL));
      	aQtyCol.setCellValueFactory(new PropertyValueFactory<>("qtyAvailable"));
      	
      	//Column Qty Borrowed
      	TableColumn<LibraryObjects,String> bQtyCol = new TableColumn<>("# Borrowed");
      	bQtyCol.setMinWidth(200);
      	//titleCol.setCellValueFactory(new PropertyValueFactory<>(postgreSQLHeroku.COL_QTY_BOR));
      	bQtyCol.setCellValueFactory(new PropertyValueFactory<>("qtyBorrowed"));
      	
      	//Column ID
      	TableColumn<LibraryObjects,String> idCol = new TableColumn<>("ID");
      	idCol.setMinWidth(200);
      	//titleCol.setCellValueFactory(new PropertyValueFactory<>(postgreSQLHeroku.COL_ID));
      	idCol.setCellValueFactory(new PropertyValueFactory<>("libid"));
      	
      	
      	
      	TableView<LibraryObjects> table = new TableView<>();
      	table.setItems(lo);
      	table.getColumns().addAll(idCol, titleCol, authCol, pubCol, typeCol, aQtyCol, bQtyCol);
      	
      	
//      	searchBtn.setOnAction(new EventHandler<ActionEvent>(){
//			@Override public void handle(ActionEvent arg0) {
//				try (BookSearchMenu bsm = new BookSearchMenu(stage, scene)) {
//					stage.setScene(bsm.showMenu(stud));
//					stage.setTitle("Book Search");
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//      	});
//      	
//      	reqIssueBtn.setOnAction(new EventHandler<ActionEvent>(){
//			@Override public void handle(ActionEvent arg0) {
//				try {
////					stage.setScene(pagegoeshere);
//					stage.setTitle("Book Issue Request");
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//      	});
//      	
//      	viewBorrowedBtn.setOnAction(new EventHandler<ActionEvent>(){
//			@Override public void handle(ActionEvent arg0) {
//				try {
////					stage.setScene(pagegoeshere);
//					stage.setTitle("Borrowed");
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//      	});
//      	
      	backBtn.setOnAction(e-> {
			
			try (Home homePage = new Home(stage, scene)) {
    			stage.setScene(homePage.showHomePage()); 
    			stage.setTitle("Library");
			} catch (Exception e2) {
				e2.printStackTrace();
			}
			
		}); 
      	
      	
      	//viewBorrowedBtn.setMinSize(150, 40);
      	
      	//pane.add(heading, 0, 0);
      	pane.add(table, 0, 0);
      	//pane.add(searchBtn, 0, 1);
      	//pane.add(reqIssueBtn, 0, 2);
      	//pane.add(viewBorrowedBtn, 0, 3);
      	pane.add(backBtn, 0, 4);
      	
      	Scene scene = new Scene(pane, 350, 450);
	    scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
	    return scene;
	}

	@Override
	public void close() throws Exception {
		// TODO Auto-generated method stub
		
	}





}
