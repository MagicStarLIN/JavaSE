package server;

//空包
public class Response {
	
	private String responseContent;

	public void write(String str){
		this.responseContent = str;
	}

	public String getResponseContent() {
		return responseContent;
	}
	
	
	
	

}
