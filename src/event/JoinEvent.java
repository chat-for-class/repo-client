package event;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import test.Connector.ServerConnector;
import utill.MessageGenerator;

public class JoinEvent implements Initializable {

	@FXML
	private TextField idField;
	@FXML
	private PasswordField pwField;
	@FXML
	private PasswordField pwCheckField;
	@FXML
	private ComboBox<String> schollist;
	@FXML
	private TextField mailId;
	@FXML
	private TextField mailAddr;
	@FXML
	private Button sendMaillNumberBtn;
	@FXML
	private TextField number;
	@FXML
	private Button checkNumberBtn;
	@FXML
	private Label checkLable;
	@FXML
	private Button JoinBtn;
	@FXML
	private Label errorField;
	@FXML
	private Label TimeCheckLabel;
	@FXML private Label checkPasswordLabel;
	@FXML private Label checkIDLabel;
	
	@Override
	public void initialize(URL location, ResourceBundle resource) {

		schollist.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				String value = schollist.getValue();

				if (value != null && value.trim().equals("가천대학교")) {
					mailAddr.setText("@gmail.com");
					sendMaillNumberBtn.setVisible(true);
				}

			}
		});

		sendMaillNumberBtn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				number.setVisible(true);
				checkNumberBtn.setVisible(true);
				String idStr = mailId.getText();
				if(!idStr.equals("")){
					
					String mailAddress = idStr + mailAddr.getText();
					ServerConnector.getInstance().write(MessageGenerator.getInstance().createAccreditationMessage(mailAddress));
					sendMaillNumberBtn.setDisable(false);
					mailId.setEditable(false);
					
					Task<Void> task = new Task<Void>() {
						
						@Override
						protected Void call() throws Exception {
							
							int time = 180;
							String timeText = null;
							
							while (time >= 0) {
								try {
									timeText = (time / 60) + ":" + (time % 60 >= 0 && time % 60 <= 9 ? ("0"+time % 60) : time % 60);
									
									updateMessage(timeText);
									
									time--;
									Thread.sleep(1000);
								} catch (InterruptedException e) {
									e.printStackTrace();
								}
							}
							
							updateMessage("");
							
							return null;
						}
						
					};
					
					TimeCheckLabel.textProperty().bind(task.messageProperty());
					
					Thread thread = new Thread(task);
					thread.setDaemon(true);
					thread.start();
				}
				
			}
		});

		checkNumberBtn.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				String sendNumber = number.getText();
				if(!sendNumber.equals("")){
					ServerConnector.getInstance().write(MessageGenerator.getInstance().createAccreditationInfomationMessage(sendNumber));
					String result = ServerConnector.getInstance().readString();
//					ServerConnector.getInstance().resetbuffer();
					
					if(result.equals("true")){
						TimeCheckLabel.textProperty().unbind();
						TimeCheckLabel.setText("");
						checkLable.setTextFill(Color.GREEN);
						checkLable.setText("인증 성공");
					}else{
						checkLable.setTextFill(Color.RED);
						checkLable.setText("인증 실패");
					}
				}
			}
		});
		
		pwCheckField.setOnKeyReleased(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				
				String password = pwField.getText();
				String checkdata = pwCheckField.getText();
				
				if(!checkdata.equals("")){
					if(password.equals(checkdata)){
						checkPasswordLabel.setTextFill(Color.GREEN);
						checkPasswordLabel.setText("password check success");
					}else if(!password.equals(checkdata)){
						checkPasswordLabel.setTextFill(Color.RED);
						checkPasswordLabel.setText("password check fail");
					}
				}else{
					checkPasswordLabel.setText("");
				}
				
			}
		});
		
		idField.setOnKeyReleased(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				
				String id = idField.getText();
				
				if(!id.equals("")){
					ServerConnector.getInstance().write(MessageGenerator.getInstance().createCheckIdduplicationMessage(id));
					String result = ServerConnector.getInstance().readString();
					
					if(result.equals("true")){
						checkIDLabel.setTextFill(Color.GREEN);
						checkIDLabel.setText("success");
					}else{
						checkIDLabel.setTextFill(Color.RED);
						checkIDLabel.setText("fail");
					}
				}
				
			}
		});
		
		JoinBtn.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {

				String id = idField.getText();
				String password = pwField.getText();
				String mail = mailId.getText();
				String pwflag = checkPasswordLabel.getTextFill() == Color.GREEN ? "true" : "false";
				String accreditationflag = checkLable.getTextFill() == Color.GREEN ? "true" : "false";
				String idflag = checkIDLabel.getTextFill() == Color.GREEN ? "true" : "false";
				
				if(id != null && !id.equals("") && password != null && !password.equals("") && pwflag.equals("true") && accreditationflag.equals("true") && idflag.equals("true")){
					String mailAddress = mail + mailAddr.getText();
					ServerConnector.getInstance().write(MessageGenerator.getInstance().createjoinMessage(id, password, mailAddress));
					String result = ServerConnector.getInstance().readString();
//					ServerConnector.getInstance().resetbuffer();
//					System.out.println("result : " + result);
					errorField.setTextFill(Color.GREEN);
					errorField.setText("회원 가입 완료");
				}else{
					errorField.setTextFill(Color.RED);
					errorField.setText("회원 정보 체크 필요");
				}
				
				
				
			}
		});
		
	}
}
