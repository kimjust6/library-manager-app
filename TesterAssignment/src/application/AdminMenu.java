package application;



import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class AdminMenu implements AutoCloseable {

	private Stage stage;
	private Scene scene;
	
	public AdminMenu(Stage stage, Scene scene) {
		this.stage = stage;
		this.scene = scene;
	}

	public Scene showMenu() throws Exception {
		GridPane pane = new GridPane();
		pane.setAlignment(Pos.CENTER);
		pane.setHgap(10);
		pane.setVgap(20);
		
		Image image = new Image(getClass().getResourceAsStream("images/register.png"));
	    ImageView imgV= new ImageView(image);
	    imgV.setFitHeight(40);
	    imgV.setFitWidth(40);
		
	    Image image1 = new Image(getClass().getResourceAsStream("images/delete.png"));
	    ImageView imgV1= new ImageView(image1);
	    imgV1.setFitHeight(40);
	    imgV1.setFitWidth(40);
	    
	    Image image2 = new Image(getClass().getResourceAsStream("images/view.png"));
	    ImageView imgV2= new ImageView(image2);
	    imgV2.setFitHeight(40);
	    imgV2.setFitWidth(40);
	    
		Button regBtn = new Button("Register Librarian");
		regBtn.setOnAction(new EventHandler<ActionEvent>(){
			@Override public void handle(ActionEvent arg0) {
				try (RegisterLibrarian regLibrarian = new RegisterLibrarian(stage, scene)) {
					stage.setScene(regLibrarian.register());
					stage.setTitle("Register Librarian");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
	    });

		Button delBtn = new Button("Delete Librarian");
		delBtn.setOnAction(new EventHandler<ActionEvent>(){
			@Override public void handle(ActionEvent arg0) {
				try (DeleteLibrarian delLibrarian = new DeleteLibrarian(stage, scene)) {
					stage.setScene(delLibrarian.delete());
					stage.setTitle("Remove Librarian");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
	    });

		Button viewBtn = new Button("View Librarians");
		viewBtn.setOnAction(new EventHandler<ActionEvent>(){
			@Override public void handle(ActionEvent arg0) {
				try (LibrarianTable librarianList = new LibrarianTable(stage, scene)) {
					stage.setScene(librarianList.display());
					stage.setTitle("View Librarians");
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
		regBtn.setStyle("-fx-background-color: #292b2c; -fx-text-fill: white");
		delBtn.setStyle("-fx-background-color: #789E9E; -fx-text-fill: white");
		viewBtn.setStyle("-fx-background-color: #789E9E; -fx-text-fill: white");
	    
		pane.add(imgV, 0, 0);
		pane.add(regBtn, 1, 0);
		pane.add(imgV1, 0, 1);
		pane.add(delBtn, 1, 1);
		pane.add(imgV2, 0, 2);
		pane.add(viewBtn, 1, 2);
		pane.add(logoutBtn, 1, 4);
		
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
