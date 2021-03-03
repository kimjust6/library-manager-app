package application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;

public class Admin_Login_Page implements EventHandler<ActionEvent>{

	@Override
	public void handle(ActionEvent arg0) {
		// TODO Auto-generated method stub
		System.out.println("Admin button is clicked");
		GridPane pane = new GridPane();
		pane.setAlignment(Pos.CENTER);
		
		 pane.setHgap(5.5);
		 pane.setVgap(10);
	}

}
