package application;

import java.util.Scanner;

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
		
		Image image = new Image(getClass().getResourceAsStream("register.png"));
	    ImageView imgV= new ImageView(image);
	    imgV.setFitHeight(40);
	    imgV.setFitWidth(40);
		
	    Image image1 = new Image(getClass().getResourceAsStream("delete.png"));
	    ImageView imgV1= new ImageView(image1);
	    imgV1.setFitHeight(40);
	    imgV1.setFitWidth(40);
	    
	    Image image2 = new Image(getClass().getResourceAsStream("view.png"));
	    ImageView imgV2= new ImageView(image2);
	    imgV2.setFitHeight(40);
	    imgV2.setFitWidth(40);
	    
		Button regBtn = new Button("Register Librarian");
		Button delBtn = new Button("Delete Librarian");
		Button viewBtn = new Button("View Librarians");
		
		regBtn.setOnAction(new EventHandler<ActionEvent>(){
			@Override public void handle(ActionEvent arg0) {
				try (RegisterLibrarian regLibrarian = new RegisterLibrarian(stage, scene)) {
					stage.setScene(regLibrarian.register());
					stage.setTitle("Registering Librarian");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
	    });
		
		
		delBtn.setOnAction(new EventHandler<ActionEvent>(){
			@Override public void handle(ActionEvent arg0) {
				try (DeleteLibrarian delLibrarian = new DeleteLibrarian(stage, scene)) {
					stage.setScene(delLibrarian.delete());
					stage.setTitle("Removing Librarian");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
	    });
		
		viewBtn.setOnAction(new EventHandler<ActionEvent>(){
			@Override public void handle(ActionEvent arg0) {
				try (Scanner in = new Scanner(System.in)) {
//					stage.setScene(getLibrarians());
					stage.setTitle("Viewing Librarians");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
	    });
		
		pane.add(imgV, 0, 0);
		pane.add(regBtn, 1, 0);
		pane.add(imgV1, 0, 1);
		pane.add(delBtn, 1, 1);
		pane.add(imgV2, 0, 2);
		pane.add(viewBtn, 1, 2);
		
		Scene scene = new Scene(pane, 350, 450);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		return scene;
	}
	
	@Override
	public void close() throws Exception {
		// TODO Auto-generated method stub
		
	}

}
