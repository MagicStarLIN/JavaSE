package server;

import java.util.HashMap;

public class Request {
	//为了装载所有请求传递过来的相关信息
	private String requestContent;
	private HashMap<String,String> requestParameters;
	
	public Request() {}
	public Request(String requestContent, HashMap<String, String> requestParameters) {
		this.requestContent = requestContent;
		this.requestParameters = requestParameters;
	}
	
	//封装一个方法  取得requestParameters中某一组键值对
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
