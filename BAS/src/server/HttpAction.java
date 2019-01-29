package server;

public abstract class HttpAction {
	public abstract void service(Request request,Response response);
}
