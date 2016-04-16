package application;
	
import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import test.Connector.ServerConnector;
import javafx.scene.Parent;
import javafx.scene.Scene;


public class Main extends Application {
	
	public static Stage StageInstance = null;
	
	 @Override
	public void init() throws Exception {
	   	ServerConnector.getInstance("172.30.1.28", 5000).initConnection();
	}
	
	@Override
	public void start(Stage primaryStage) {
		try {
			
			StageInstance = primaryStage;
			
			Parent root = FXMLLoader.load(getClass().getResource("../mainLayout.fxml"));
			System.out.println("layout URL : " + getClass().getResource("mainLayout.fxml"));
			Scene scene = new Scene(root, 500, 500);
			
			primaryStage.setScene(scene);
			primaryStage.show();
			
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
