package action;

import server.HttpAction;
import server.Request;
import server.Response;

public class IndexAction extends HttpAction{

	//真实类中做事情的方法
	public void service(Request request,Response response){
		String name = request.getParameter("name");
		String pass = request.getParameter("pass");
		System.out.println("接收到了请求的参数"+name+","+pass);
		System.out.println("我是IndexAction中的service方法  我做了一件事情");
		String result = "做事情之后的响应信息";
		response.write(result);//将响应信息回写给浏览器
	}
}
