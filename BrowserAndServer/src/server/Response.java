package server;

//¿Õ°ü
public class Response {
	
	private String responseContent;

	public void write(String str){
		this.responseContent = str;
	}

	public String getResponseContent() {
		return responseContent;
	}
	
	
	
	

}
