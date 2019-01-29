package action;

import server.HttpAction;
import server.Request;
import server.Response;

public class IndexAction extends HttpAction{
	public void service(Request request,Response response){
		String name = request.getParameters("name");
		String pass = request.getParameters("pass");
		System.out.println("收到请求"+name+" "+pass);
		System.out.println("indexaction执行");
		String result = "响应";
		response.write(result);
		
	}
}
