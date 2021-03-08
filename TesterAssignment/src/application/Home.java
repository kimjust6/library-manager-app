package application;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Home implements AutoCloseable {
	
	private Stage stage;
	private Scene scene;
	
	public Home(Stage stage, Scene scene) {
		this.stage = stage;
		this.scene = scene;
	}
	
	public Scene showHomePage() throws Exception {
		GridPane pane = new GridPane();
		pane.setAlignment(Pos.CENTER);
	    pane.setHgap(10);
	    pane.setVgap(2);
	    
	    Image image = new Image(getClass().getResourceAsStream("images/admin.png"));
	    ImageView imgV= new ImageView(image);
	    imgV.setFitHeight(70);
	    imgV.setFitWidth(70);
	    
	    Image image2 = new Image(getClass().getResourceAsStream("images/student.png"));
	    ImageView imgV2= new ImageView(image2);
	    imgV2.setFitHeight(70);
	    imgV2.setFitWidth(70);
	    
	    Label wlcMSG = new Label("Welcome!\nWho are you logging in as...?\n");
	    wlcMSG.setFont(new Font(18));
		Button btAdmin = new Button("Staff");
	    Button btStu = new Button("Student");

	    
	    btAdmin.setOnAction(new EventHandler<ActionEvent>(){
			@Override public void handle(ActionEvent arg0) {
				try (AdminLogin adminLogin = new AdminLogin(stage, scene)) {
					stage.setScene(adminLogin.showLoginPage());
					stage.setTitle("Admin Login Page");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
	    });
	    
	    btStu.setOnAction(new EventHandler<ActionEvent>(){
			@Override public void handle(ActionEvent arg0) {
				try (StudentLogin studentLogin = new StudentLogin(stage, scene)) {
					stage.setScene(studentLogin.showLoginPage());
					stage.setTitle("Student Login Page");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
	    });
	    btAdmin.setStyle("-fx-background-color: #4D6466; -fx-text-fill: white");
	    btStu.setStyle("-fx-background-color: #4D6466; -fx-text-fill: white");
	    pane.add(wlcMSG, 0 , 0);
	    pane.add(imgV, 0 ,1);
	    pane.add(imgV2, 1 ,1);
	    pane.add(btAdmin, 0, 2);
	    pane.add(btStu, 1, 2);
	    
	    pane.setStyle("-fx-background-color: #EEF3DB");
	    return new Scene(pane, 350, 450);
	}

	@Override
	public void close() throws Exception {
		// TODO Auto-generated method stub
	}
}
