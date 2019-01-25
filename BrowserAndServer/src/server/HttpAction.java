package server;

public abstract class HttpAction {
	//规定一个规则
	//给以后我们自己写的Action用
	//统一的方法名字 便于服务器查找
	public abstract void service(Request request,Response response);
	
}
