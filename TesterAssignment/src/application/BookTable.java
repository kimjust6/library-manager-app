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
      	
      	
      	//Column Title
      	TableColumn<LibraryObjects,String> titleCol = new TableColumn<>("Title");
      	titleCol.setMinWidth(200);
      	titleCol.setCellValueFactory(new PropertyValueFactory<>(postgreSQLHeroku.COL_TITLE));
      	
      	//Column Author
      	TableColumn<LibraryObjects,String> authCol = new TableColumn<>("Author");
      	titleCol.setMinWidth(200);
      	titleCol.setCellValueFactory(new PropertyValueFactory<>(postgreSQLHeroku.COL_AUTHOR));
      	
      	//Column Publisher
      	TableColumn<LibraryObjects,String> pubCol = new TableColumn<>("Publisher");
      	titleCol.setMinWidth(200);
      	titleCol.setCellValueFactory(new PropertyValueFactory<>(postgreSQLHeroku.COL_PUBLISHER));
      	
      	//Column MediaType
      	TableColumn<LibraryObjects,String> typeCol = new TableColumn<>("Type");
      	titleCol.setMinWidth(200);
      	titleCol.setCellValueFactory(new PropertyValueFactory<>(postgreSQLHeroku.COL_MEDIA_TYPE));
      	
      	//Column Qty Available
      	TableColumn<LibraryObjects,String> aQtyCol = new TableColumn<>("# Available");
      	titleCol.setMinWidth(200);
      	titleCol.setCellValueFactory(new PropertyValueFactory<>(postgreSQLHeroku.COL_QTY_AVAIL));
      	
      	//Column Qty Borrowed
      	TableColumn<LibraryObjects,String> bQtyCol = new TableColumn<>("# Borrowed");
      	titleCol.setMinWidth(200);
      	titleCol.setCellValueFactory(new PropertyValueFactory<>(postgreSQLHeroku.COL_QTY_BOR));
      	
      	//Column ID
      	TableColumn<LibraryObjects,String> idCol = new TableColumn<>("ID");
      	titleCol.setMinWidth(200);
      	titleCol.setCellValueFactory(new PropertyValueFactory<>(postgreSQLHeroku.COL_ID));
      	
      	
      	
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
