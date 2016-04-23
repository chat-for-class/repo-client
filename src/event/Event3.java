package event;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import Data.DataList;
import application.Main;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import test.Connector.ServerConnector;
import test.config.data.RememberMeData;
import utill.MessageGenerator;


public class Event3 implements Initializable{

	@FXML private Text scenetitle;
	@FXML private TextField idBox;
	@FXML private PasswordField pwBox;
	@FXML private Button SignInBtn;
	@FXML private CheckBox checkBox;
	@FXML private Text checkloginText;
	@FXML private HBox linkbox;
	@FXML private Hyperlink idAndPwlink;
	@FXML private Hyperlink join;

	@Override
	public void initialize(URL location, ResourceBundle resource) {

		try {
			
			File file = new File("./data/checkdata.dat");
			RememberMeData config = null;
			
			if(file.exists()){
				FileInputStream fis = new FileInputStream(file);
				ObjectInputStream ois = new ObjectInputStream(fis);
				
				config = (RememberMeData)ois.readObject();
				
				ois.close();
				fis.close();
			}
			
			if(config != null){
				idBox.setText(config.getId());
				pwBox.setText(config.getPassword());
				checkBox.setSelected(config.isChecked());
	        }
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		SignInBtn.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {

				System.out.println("call login Event!!");
				FileOutputStream fos = null;
				ObjectOutputStream oos = null;
				String userName = idBox.getText();
				String password = pwBox.getText();
				try {
					
					ServerConnector.getInstance().write(MessageGenerator.getInstance().createLoginMessage(userName, password));
					String msg = ServerConnector.getInstance().readString(); 
				
					int mode = msg.indexOf("^");
					String flag = msg.substring(0,mode);
					
	//					ServerConnector.getInstance().resetbuffer();
					File file = new File("./data/checkdata.dat");
					if(flag.equals("true")){
						checkloginText.setFill(Color.BLUE);
						checkloginText.setText("login success");
						
						boolean chkflag = checkBox.isSelected();
						
						if(chkflag){
							if(!file.exists()){
								file.createNewFile();
							}
							RememberMeData rmd = new RememberMeData();
							
							rmd.setChecked(chkflag);
							rmd.setId(userName);
							rmd.setPassword(password);
							
							fos = new FileOutputStream(file);
							oos = new ObjectOutputStream(fos);
							
							oos.writeObject(rmd);
							oos.flush();
							
						
						}else{
							if(file.exists()){
								file.delete();
							}
						}
						//¼öÁ¤
						ArrayList<String> list = new ArrayList<>();
						int a= msg.indexOf("^");
						msg = msg.substring(a+1);
						System.out.println(msg);
						for(int i=0; i<msg.length(); i++){
							int oper = msg.indexOf("*");
							if(oper>-1){		
								String imsimsg = msg.substring(0,oper);
								msg = msg.substring(oper+1);
															
								list.add(imsimsg);
							}
						}
						DataList.setList(list);
						Parent	root = FXMLLoader.load(getClass().getResource("../TimeTableUi.fxml"));
						
						Scene scene = new Scene(root,250,400);
						
						Main.StageInstance.setScene(scene);
						
					}else{
						checkloginText.setFill(Color.RED);
						checkloginText.setText("login fail");
						
						if(file.exists()){
							file.delete();
						}
					}
					
				} catch (IOException e) {
					e.printStackTrace();
				}finally{
					try {
						if(oos != null) oos.close();
						if(fos != null) fos.close();
					} catch (Exception e2) {
						e2.printStackTrace();
					}
				}
				
			}
		});
		
		join.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				
				Parent root;
				try {
					root = FXMLLoader.load(getClass().getResource("../joinLayout.fxml"));
					

					Scene scene = new Scene(root, 500, 500);
					
					Main.StageInstance.setScene(scene);
					
				} catch (IOException e) {
					e.printStackTrace();
				}
				
			}
		});
		
	}

}
