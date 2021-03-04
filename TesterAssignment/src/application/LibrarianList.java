package application;

import java.sql.ResultSet;

import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.stage.Stage;
import DatabaseTest.postgreSQLHeroku;

public class LibrarianList implements AutoCloseable {

	private Stage stage;
	private Scene scene;
	
	public LibrarianList(Stage stage, Scene scene) {
		this.stage = stage;
		this.scene = scene;
	}
	
//	@SuppressWarnings({ "unchecked", "rawtypes" })		// is this bad ... ?
	public Scene getLibrarians() throws Exception {
//		ResultSet rs = DB.select(DB.TABLE_ADMINS, DB.COL_ADMINTYPE, "=", "Librarian");
//		
//		ObservableList<ObservableList> data = FXCollections.observableArrayList();
//		TableView tableview = new TableView();
//
//		for (int i = 0; i < rs.getMetaData().getColumnCount() - 1; i++) {
//            final int j = i;
//            TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i + 1));
//            col.setCellValueFactory(new Callback<CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {
//                public ObservableValue<String> call(CellDataFeatures<ObservableList, String> param) {
//                    return new SimpleStringProperty(param.getValue().get(j).toString());
//                }
//            });
//            tableview.getColumns().addAll(col);
//        }
//		
//		while (rs.next()) {
//            ObservableList<String> row = FXCollections.observableArrayList();
//            for (int i = 1; i < rs.getMetaData().getColumnCount(); i++) {
//                row.add(rs.getString(i));
//            }
//            data.add(row);
//        }
//		tableview.setItems(data);
//		
//		Scene scene = new Scene(tableview, 445, 450);
//	    scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
	    return scene;
	}

	@Override
	public void close() throws Exception {
		// TODO Auto-generated method stub
		
	}

}
