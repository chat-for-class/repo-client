package test.Connector;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.List;

public class ServerConnector {

	private Socket socket = null;
	private BufferedReader br = null;
	private BufferedWriter bw = null;
	private static ServerConnector instance;
	private String hostIp = null;
	private int port = -1;
	private static String subject;
	private ServerConnector(String hostIp, int port) {
		this.hostIp = hostIp;
		this.port = port;
	}
	public static String  getList(){
		
		return subject;
	}
	public static ServerConnector getInstance(String hostIp, int port) {
		if(instance == null){
			instance = new ServerConnector(hostIp, port);
		}
		
		return instance;
	}
	
	public static ServerConnector getInstance() {
		return instance;
	}

	public void initConnection(){
		
		try {
			if(socket == null){
				SocketAddress info = new InetSocketAddress(hostIp, port);
				socket = new Socket();
				socket.connect(info);
				
				br = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
				bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void write(String message){
		
		try {
			bw.write(message+"\n");
			bw.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public String readString(){
		
		String message = null;
		
		try {
			
			while(message == null){
				message = br.readLine();
				System.out.println(message);
			}
			subject = message;
			System.out.println(subject);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return message;
	}
	
	public void close() {

		try {
			if (bw != null)
				bw.close();
			if (br != null)
				br.close();
			if (socket != null)
				socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void resetbuffer(){
		try {
			br.reset();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
