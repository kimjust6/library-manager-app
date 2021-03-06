package application;

import java.io.*;
import javafx.fxml.FXML;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import DatabaseTest.postgreSQLHeroku;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class ViewBooks implements AutoCloseable {
	private Stage stage;
	private Scene scene;
	private TableView table = new TableView();
	ViewBooks(Stage stage, Scene scene){
		this.stage = stage;
		this.scene = scene;
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Scene viewBooks() throws Exception, IOException, FileNotFoundException{
		//READ THE BOOKS FROM DB AND GENERATE OUTPUT FILE
		
		ResultSet rs = null;
    	
    	try (Connection connection = DriverManager.getConnection(postgreSQLHeroku.DATABASE_URL, postgreSQLHeroku.DATABASE_USERNAME, postgreSQLHeroku.DATABASE_PASSWORD))
    	{
    		String query = String.format("SELECT * FROM %s;", postgreSQLHeroku.TABLE_LIBRARY);
    		Statement statement = connection.createStatement();
    		rs = statement.executeQuery(query);
    		int colCount = rs.getMetaData().getColumnCount();
    		File file = new File("books.txt");
    		//create file
    		if (!file.exists()) {
				file.createNewFile();
			}
			//clear file first
			PrintWriter writer = new PrintWriter("books.txt");
			writer.print("");
			writer.close();
    		try (BufferedWriter bw = new BufferedWriter(new FileWriter("books.txt", true))) {
	    		while(rs.next())
	    		{
	    			String line = "";
	    			for (int i = 1; i <= colCount; ++i )
	    			{
	    				line += rs.getString(i) + " ";
	    			}
	    			bw.append(line);
    				bw.newLine();
	    			System.out.println("");
	    		}
	    		
	    		bw.flush();
    		}
    		System.out.println("file created "+file.getCanonicalPath()); 
    		try (BufferedReader br = new BufferedReader(new FileReader("books.txt"))) {
    			String line;
    			while ((line = br.readLine()) != null) {
    				System.out.println(line);
    			}
    		}
    	}catch(Exception e)
    	{
    		System.out.println(e);
    	}
    	
    	
    	//DISPLAY IN A NICE TABLE
    	Scene scene = new Scene(new Group(), 450, 450);
		final Label label = new Label("Books Library");
		label.setFont(new Font("Arial", 20));
		 
        table.setEditable(true);
		
        TableColumn titleCol = new TableColumn("Title");
        TableColumn authorCol = new TableColumn("Author");
        TableColumn publisherCol = new TableColumn("Publisher");
        TableColumn mediatypeCol = new TableColumn("Media Type");
        TableColumn qtyavailCol = new TableColumn("Available");
        TableColumn qtyborrowCol = new TableColumn("Borrowed");
        TableColumn qtyCol = new TableColumn("Quantity");
        
        qtyCol.getColumns().addAll(qtyavailCol, qtyborrowCol);
        table.getColumns().addAll(titleCol, authorCol, publisherCol, mediatypeCol, qtyCol);
        
        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(label, table);
 
        ((Group) scene.getRoot()).getChildren().addAll(vbox);
		
	    scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
	    return scene;
	}
	@Override
	public void close() throws Exception {
		// TODO Auto-generated method stub
		
	}

}
