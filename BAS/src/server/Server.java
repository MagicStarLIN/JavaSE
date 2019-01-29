package server;

import java.io.IOException;
import java.net.ServerSocket;

public class Server {
//实现多线程
	public void startServer(){
		try {
			ServerSocket socket = new ServerSocket(9999);
			System.out.println("服务器启动");
			while(true){
				new Handler(socket.accept()).run();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		new Server().startServer();
	}
}
