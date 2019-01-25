package server;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.net.Socket;
import java.util.HashMap;
import java.util.Properties;

//服务器中的一个线程  负责某一个浏览器的读写
public class Handler extends Thread{

	private Socket socket;
	public Handler(Socket socket) {
		this.socket = socket;
	}

	public void run(){
		this.receiveRequestFromBrowser();
	}
	
	//接收从浏览器发送过来的请求信息  String
	//   content?key=value&key=value
	private void receiveRequestFromBrowser(){
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			String content =br.readLine();
			this.parseContent(content);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void parseContent(String content){
		String requestContent = null;//用此变量来存储请求的名字
		HashMap<String,String> requestParameters = null;//用此变量存储请求携带的所有参数
		//   content?key=value&key=value
		int index = content.indexOf("?");//问号所在的索引位置
		if(index!=-1){//携带了参数
			requestParameters = new HashMap<String,String>();
			requestContent = content.substring(0,index);
			//key=value&key=value
			String parameters = content.substring(index+1);
			String[] keyAndValues = parameters.split("&");
			for(String keyAndValue : keyAndValues){
				String[] KV = keyAndValue.split("=");
				requestParameters.put(KV[0],KV[1]);
			}
		}else{//没有携带参数
			requestContent = content;
		}
		//---------------------------------------
		//这个方法分析了半天  最终获取了两个信息
		//  String  requestContent=====请求资源的名字
		//  Map     requestParameters==请求携带的参数值
		Request request = new Request(requestContent,requestParameters);
		Response response = new Response();
		this.findAction(request,response);
	}
	
	//通过请求的名字找寻真实的Action 并且将请求携带的参数也穿给他
	@SuppressWarnings("all")
	public void findAction(Request request,Response response){
		try {
			String content = request.getRequestContent();
			//在服务器中写一个配置文件  
			//目的  记录 请求的名字----真实类名之间的对应关系
			//index------action.IndexAction
			Properties pro = new Properties();
			InputStream inStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("ApplicationContext.properties");
			pro.load(inStream);
			String realActionName = pro.getProperty(content);
			Class clazz = Class.forName(realActionName);
			Object obj = clazz.newInstance();
			Method serviceMethod = clazz.getMethod("service",Request.class,Response.class);
			serviceMethod.invoke(obj,request,response);
			//获取IndexAction类中的响应信息
			this.sendResponseContentToBrowser(response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void sendResponseContentToBrowser(Response response){
		try {
			PrintWriter out = new PrintWriter(socket.getOutputStream());
			out.println(response.getResponseContent());
			out.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
