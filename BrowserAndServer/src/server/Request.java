package server;

import java.util.HashMap;

public class Request {
	//Ϊ��װ���������󴫵ݹ����������Ϣ
	private String requestContent;
	private HashMap<String,String> requestParameters;
	
	public Request() {}
	public Request(String requestContent, HashMap<String, String> requestParameters) {
		this.requestContent = requestContent;
		this.requestParameters = requestParameters;
	}
	
	//��װһ������  ȡ��requestParameters��ĳһ���ֵ��
	public String getParameter(String key){
		return this.requestParameters.get(key);
	}
	
	public String getRequestContent() {
		return requestContent;
	}
	public void setRequestContent(String requestContent) {
		this.requestContent = requestContent;
	}
	public HashMap<String, String> getRequestParameters() {
		return requestParameters;
	}
	public void setRequestParameters(HashMap<String, String> requestParameters) {
		this.requestParameters = requestParameters;
	}
	
}
