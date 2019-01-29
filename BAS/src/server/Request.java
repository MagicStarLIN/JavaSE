package server;

import java.util.HashMap;

public class Request {
	//装载请求信息
	private String requestContent;
	private HashMap<String,String> requestParameters;
	
	public Request(){
		
	}

	public Request(String requestContent, HashMap<String, String> requestParameters) {
		this.requestContent = requestContent;
		this.requestParameters = requestParameters;
	}
	//获取一个键值对
	public  String getParameters(String key){
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
