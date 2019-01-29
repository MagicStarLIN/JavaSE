package browser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Browser {
	Socket socket = null;
	/**
	 * 接收URL
	 */
	public void open(){
		System.out.println("输入URL");
		Scanner sc = new Scanner(System.in);
	//  ip:port/content?key=value&key=value
		String url = sc.nextLine();
		this.parseURL(url);
		
	}
	/**
	 * 解析URL
	 * @param url
	 */
	private void parseURL(String url){
		int index1 = url.indexOf(":");
		int index2 = url.indexOf("/");
		String ip = url.substring(0, index1);
		int port = Integer.parseInt(url.substring(index1+1, index2));
		String content = url.substring(index2+1);
		System.out.println("解析完成");
		this.createSocketAndSendRequest(ip, port, content);
		}
	private void createSocketAndSendRequest(String ip,int port,String content){
		try {
			socket = new Socket(ip,port);
			PrintWriter pw = new PrintWriter(socket.getOutputStream());
			pw.println(content);
			pw.flush();
			System.out.println("start send");
			this.receiveResponseFromServer();
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	private void receiveResponseFromServer(){
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			String responseContent = br.readLine();
			this.parseResponseContent(responseContent);
			System.out.println("finish receive");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	private void parseResponseContent(String responseContent){
		System.out.println("响应信息"+responseContent);
	}
	public static void main(String[] args) {
		new Browser().open();
		
	}
}
