package utill;

public class MessageGenerator {

	private static MessageGenerator instance = new MessageGenerator();
	private MessageGenerator(){}
	
	public static MessageGenerator getInstance(){
		return instance;
	}
	
	public String createLoginMessage(String id, String password){
		return "01/Data:id="+id+";"+"password="+password;
	}
	
	public String createAccreditationMessage(String mailAddress){
		return "02/Data:mailAddress="+mailAddress;
	}
	
	public String createAccreditationInfomationMessage(String number){
		return "03/Data:time="+System.currentTimeMillis()+";number="+number;
	}
	
	public String createCheckIdduplicationMessage(String id){
		return "04/Data:id="+id;
	}
	
	public String createjoinMessage(String id, String password, String mailAddress){
		return "05/Data:id="+id+";"+"password="+password+";"+"mailAddress="+mailAddress;
	}
	public String classUpdate(String msg){
		return "06/Data:msg=" + msg;
	}
}
