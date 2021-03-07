package application;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AlertBox {
	public static void display(String title, String message) {
		Stage window = new Stage();
		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle(title);
		window.setMinWidth(300);
		window.setMinHeight(125);
		
		
		Label label = new Label();
		label.setText(message);
		label.setFont(Font.font("Verdana", FontWeight.BLACK, 12));
		
		Button closeBtn = new Button("OK");
		closeBtn.setMinWidth(50);
		closeBtn.setMinHeight(25);
		
		if(title == "Error!" || title == "Error")
		{
			label.setTextFill(Color.web("#FE615A")); //light red
			//closeBtn.setTextFill(Color.WHITE);
			//closeBtn.setStyle("-fx-background-color: #FE615A"); //light red
		}
		else if (title == "Success!" || title == "Success")
		{
			label.setTextFill(Color.GREEN); //green
		}
		
		
		
		closeBtn.setOnAction(e-> { window.close(); });
		
		VBox layout = new VBox(10);
		layout.getChildren().addAll(label, closeBtn);
		layout.setAlignment(Pos.CENTER);
		
		Scene scene = new Scene(layout);
		window.setScene(scene);
		window.showAndWait();
	}
}
