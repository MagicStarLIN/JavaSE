package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;
import java.util.HashMap;
import java.util.Properties;


/**
 * 线程  负责对浏览器的读写
 * @author 75196
 *
 */
public class Handler implements Runnable{
	private Socket socket;

	@Override
	public void run() {
		this.ReceiveRequestFromBrowser();
	}
	
	public Handler(Socket socket) {
		this.socket = socket;
	}

	//读取浏览器的请求
	//content?key=value&key=value
	private void ReceiveRequestFromBrowser(){
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			String content = br.readLine();
			this.parseContent(content);
		} catch (IOException e) {
			e.printStackTrace();
		}
	//解析content
	
		
	}
	private void parseContent(String content){
		String requestcontent = null;//储存请求的名字
		HashMap<String,String> requestParameters = null;//储存携带的参数
		
		int index = content.indexOf("?");
		System.out.println("index是"+index);
		if(index!=-1){//确认携带了参数
			requestParameters = new HashMap<String, String>();
			requestcontent = content.substring(0, index);
			System.out.println("requestcontent 是"+requestcontent);
			String parameters = content.substring(index+1);
			String[] keyAndvalue = parameters.split("&");
			for(String kv :keyAndvalue){
				String[] kav = kv.split("=");
				System.out.println(kav[0]+kav[1]);
				requestParameters.put(kav[0], kav[1]);
			}
			
		}else{
			requestcontent = content;
		}
		Request request = new Request(requestcontent,requestParameters);
		Response response = new Response();
		this.findAction(request, response);
	}
	private void findAction(Request request,Response response){
		try {
		String content = request.getRequestContent();
		Properties pro = new Properties();
		InputStream inStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("ApplicationContext.properties");
		pro.load(inStream);
		String realActionName = pro.getProperty(content);
		Class clazz = Class.forName(realActionName);
		Object obj = clazz.newInstance();
		Method serviceMethod = clazz.getMethod("service", Request.class,Response.class);
		serviceMethod.invoke(obj,request,response);
		this.sendResponseContentToBrowser(response);
		
		
		} catch (IOException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}
	private void sendResponseContentToBrowser(Response response){
		try {
			PrintWriter pw = new PrintWriter(socket.getOutputStream());
			pw.println(response.getResponseContent());
			pw.flush();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
