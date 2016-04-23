package event;

import java.awt.Checkbox;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import Data.DataList;
import application.Main;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Stop;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import test.Connector.ServerConnector;
import utill.MessageGenerator;

public class Modify implements Initializable {

	@FXML
	VBox list;
	@FXML
	Button delete;
	@FXML
	Button ok;
	

	@Override
	
	public void initialize(URL location, ResourceBundle resources) {
		ArrayList<String> lists = DataList.getList();
		ArrayList<CheckBox> ch = new ArrayList<>();
		ArrayList<AnchorPane> ap = new ArrayList<>();
		ArrayList<String> deletelist = new ArrayList<String>();
		EventHandler eh = new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				if (event.getSource() instanceof CheckBox) {
					CheckBox chk = (CheckBox) event.getSource();

					
					if (chk.isSelected()) {
						deletelist.add(chk.getText());
					} else {
						deletelist.remove(chk.getText());
					}
				}
			}
		};

		for (String k : lists) {
			ap.add(new AnchorPane());
			ch.add(new CheckBox(k));
		}
		for (int i = 0; i < ch.size(); i++) {
			ap.get(i).getChildren().add(ch.get(i));
			Checkbox check = new Checkbox();

			list.getChildren().add(ap.get(i));
			ch.get(i).setOnAction(eh);
		}

		delete.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				for (int i = 0; i < list.getChildren().size(); i++) {
					for (int j = 0; j < deletelist.size(); j++) {
						if (lists.get(i).equals(deletelist.get(j))) {
							lists.remove(i);
							list.getChildren().remove(i);
						}
					}
				}
				String msg = "";
				for(String k : lists){
					msg += k + "*";
				}
				System.out.println(msg);
				ServerConnector.getInstance().write(MessageGenerator.getInstance().classUpdate(msg));
				msg = ServerConnector.getInstance().readString();
				ArrayList<String> list = new ArrayList<>();
				
				for(int i=0; i<msg.length(); i++){
					int oper = msg.indexOf("*");
					if(oper>-1){		
						String imsimsg = msg.substring(0,oper);
						msg = msg.substring(oper+1);
													
						list.add(imsimsg);
					}
				}
				DataList.setList(list);
				Parent root;
				try {
					root = FXMLLoader.load(getClass().getResource("../TimeTableUi.fxml"));
					Scene scene = new Scene(root,250,400);
					
					Main.StageInstance.setScene(scene);

				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			
		});
		ok.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				TimeTableEvent.modifyStage.close();
				TimeTableEvent.modifyStage.setOnCloseRequest(new EventHandler<WindowEvent>() {

					@Override
					public void handle(WindowEvent event) {
						// TODO Auto-generated method stub
				
					}
				});
			}
		});
	}
}
