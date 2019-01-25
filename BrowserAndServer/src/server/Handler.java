package server;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.net.Socket;
import java.util.HashMap;
import java.util.Properties;

//�������е�һ���߳�  ����ĳһ��������Ķ�д
public class Handler extends Thread{

	private Socket socket;
	public Handler(Socket socket) {
		this.socket = socket;
	}

	public void run(){
		this.receiveRequestFromBrowser();
	}
	
	//���մ���������͹�����������Ϣ  String
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
		String requestContent = null;//�ô˱������洢���������
		HashMap<String,String> requestParameters = null;//�ô˱����洢����Я�������в���
		//   content?key=value&key=value
		int index = content.indexOf("?");//�ʺ����ڵ�����λ��
		if(index!=-1){//Я���˲���
			requestParameters = new HashMap<String,String>();
			requestContent = content.substring(0,index);
			//key=value&key=value
			String parameters = content.substring(index+1);
			String[] keyAndValues = parameters.split("&");
			for(String keyAndValue : keyAndValues){
				String[] KV = keyAndValue.split("=");
				requestParameters.put(KV[0],KV[1]);
			}
		}else{//û��Я������
			requestContent = content;
		}
		//---------------------------------------
		//������������˰���  ���ջ�ȡ��������Ϣ
		//  String  requestContent=====������Դ������
		//  Map     requestParameters==����Я���Ĳ���ֵ
		Request request = new Request(requestContent,requestParameters);
		Response response = new Response();
		this.findAction(request,response);
	}
	
	//ͨ�������������Ѱ��ʵ��Action ���ҽ�����Я���Ĳ���Ҳ������
	@SuppressWarnings("all")
	public void findAction(Request request,Response response){
		try {
			String content = request.getRequestContent();
			//�ڷ�������дһ�������ļ�  
			//Ŀ��  ��¼ ���������----��ʵ����֮��Ķ�Ӧ��ϵ
			//index------action.IndexAction
			Properties pro = new Properties();
			InputStream inStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("ApplicationContext.properties");
			pro.load(inStream);
			String realActionName = pro.getProperty(content);
			Class clazz = Class.forName(realActionName);
			Object obj = clazz.newInstance();
			Method serviceMethod = clazz.getMethod("service",Request.class,Response.class);
			serviceMethod.invoke(obj,request,response);
			//��ȡIndexAction���е���Ӧ��Ϣ
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
