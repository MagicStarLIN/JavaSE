package browser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Browser {

	public static void main(String[] args) {
		new Browser().open();
	}
	
	Socket socket = null;
	@SuppressWarnings("resource")
	public void open(){
		System.out.print("URL:");
		Scanner input = new Scanner(System.in);
		String url = input.nextLine();
		//  ip:port/content?key=value&key=value
		//  localhost:9999/index?name=zzt&pass=123
		this.parseURL(url);
	}
	
	private void parseURL(String url){
		int index1 = url.indexOf(":");//ð�����ڵ�����λ��
		int index2 = url.indexOf("/");//��һ��б������λ��
		String ip = url.substring(0,index1);
		int port = new Integer(url.substring(index1+1,index2));
		String content = url.substring(index2+1);
		this.createSocketAndSendRequest(ip, port, content);
	}
	
	private void createSocketAndSendRequest(String ip,int port,String content){
		try {
			socket = new Socket(ip,port);
			PrintWriter out = new PrintWriter(socket.getOutputStream());
			out.println(content);
			out.flush();
			//�ȴ���Ӧ��Ϣ
			this.receiveResponseFromServer();
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	
	private void receiveResponseFromServer(){
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			String responseContent = br.readLine();
			this.parseResponseContentAndShow(responseContent);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void parseResponseContentAndShow(String responseContent){
		//����---->HTML
		System.out.println("����������������Ӧ��Ϣ:"+responseContent);
	}
}
