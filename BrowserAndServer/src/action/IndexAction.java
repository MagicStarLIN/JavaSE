package action;

import server.HttpAction;
import server.Request;
import server.Response;

public class IndexAction extends HttpAction{

	//��ʵ����������ķ���
	public void service(Request request,Response response){
		String name = request.getParameter("name");
		String pass = request.getParameter("pass");
		System.out.println("���յ�������Ĳ���"+name+","+pass);
		System.out.println("����IndexAction�е�service����  ������һ������");
		String result = "������֮�����Ӧ��Ϣ";
		response.write(result);//����Ӧ��Ϣ��д�������
	}
}
