package event;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import Data.DataList;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Callback;
import test.Connector.ServerConnector;

public class TimeTableEvent implements Initializable{
	@FXML private Button modify;
	@FXML private Button addSubject;
	@FXML private ListView listView;
	@FXML private Pane panestage;
	
	static Stage modifyStage;
	private String deleteText;
	private ObservableList obserbleList = FXCollections.observableArrayList();
	private List<String> stringList = new ArrayList<String>(5);
	@Override
	public void initialize(URL location, ResourceBundle resources) {
			
			ObservableList<String> items = listView.getItems();
			ArrayList<String> list =DataList.getList();
			for(String k :list){
				items.add(k);
			}
			
	
			modify.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub

				try {
					Parent	root = FXMLLoader.load(getClass().getResource("../modify.fxml"));
					Scene scene = new Scene(root, 250, 400);					
					/*Stage stage*/ 
					modifyStage = new Stage();
					modifyStage.setScene(scene);
					modifyStage.show();
					} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}				
				}
		});
		addSubject.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				
				System.out.println("ssss");
			}			
		});
		listView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				// TODO Auto-generated method stub
				deleteText = observable.getValue();
			}
		});
	}
	public void update(){
		
		ObservableList<String> items = listView.getItems();
		ArrayList<String> list =DataList.getList();
		for(String k :list){
			items.add(k);
		}

	}

}
