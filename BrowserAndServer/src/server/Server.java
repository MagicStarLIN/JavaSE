package server;

import java.net.ServerSocket;

public class Server {

	public static void main(String[] args) {
		new Server().startServer();
	}
	@SuppressWarnings("resource")
	public void startServer(){
		try {
			System.out.println("·þÎñÆ÷Æô¶¯À²");
			ServerSocket server = new ServerSocket(9999);
			while(true){
				new Handler(server.accept()).start();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
