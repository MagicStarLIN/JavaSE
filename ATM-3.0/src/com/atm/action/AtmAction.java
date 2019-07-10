package com.atm.action;

import java.util.Scanner;

import com.atm.service.AtmService;
import com.atm.vo.Card;
import com.atm.vo.User;

/**
 * 控制器
 * 与用户交互的 控制层
 */
public class AtmAction {
	private AtmService service = new AtmService();
	private Scanner sc = new Scanner(System.in);
	private User currentUser;
	
	public void welcome(){
		System.out.println("欢迎大侠访问ATM-3.0-FINAL");
		boolean boo = true;
		while(boo){
			System.out.println("请选择菜单：");
			System.out.println("1、加入我们");
			System.out.println("2、进入系统");
			System.out.println("3、离开我们");
			int choose = sc.nextInt();
			if(choose==1){
				registry();
			}else if(choose==2){
				login();
			}else if(choose==3){
				boo = false;
				System.out.println("欢迎下次再来！");
			}else{
				System.out.println("请选择正确菜单");
			}
		}
	}

	/**
	 * 登录
	 */
	private void login() {
		System.out.println("请输入卡号：");
		String card_no = sc.next();
		System.out.println("请输入卡密：");
		String card_pass = sc.next();
		currentUser = service.checkUser(card_no,card_pass);
		if(currentUser!=null){
			//TODO 记录日志
//			Log log = new Log();
//			String date = service.getSystemDate();
//			log.setLog_id(User.ID++);
//			log.setLog_content(currentUser.getUser_name()+","+date+"，登录系统");
//			log.setDate(date);
//			currentUser.addLog(log);
			menu();
		}else{
			System.out.println("登录失败，请重新登录");
		}
	}

	private void menu() {
		System.out.println("欢迎登录ATM-3.0-FINAL");
		System.out.println("请选择菜单：");
		boolean isExit = true;
		while(isExit){
			System.out.println("1、余额");
			System.out.println("2、存款");
			System.out.println("3、取款");
			System.out.println("4、转账");
			System.out.println("5、改密");
			System.out.println("6、销户");
			System.out.println("7、退出");
			int choose = sc.nextInt();
			switch(choose){
				case 1:
					queryMoney();
					break;
				case 2:
					saveMoney();
					break;
				case 3:
					getMoney();
					break;
				case 4:
					transferMoney();
					break;
				case 5:
					changePassword();
					break;
				case 6:
					destroyUser();
					isExit = false;
					break;
				case 7:
					isExit = false;
					System.out.println("欢迎下次光临！");
					break;
			}
		}
	}

	private void destroyUser() {
		service.destroyUser(currentUser);
		currentUser = null;
		System.out.println("注销成功");
	}

	private void changePassword() {
		System.out.println("请输入原始密码：");
		String password = sc.next();
		if(password.equals(currentUser.getCard().getCard_pass())){
			System.out.println("请输入新密码");
			String newPassword = sc.next();
			System.out.println("请再次输入新密码");
			String newPassword2 = sc.next();
			if(newPassword.equals(newPassword2)){
				currentUser.getCard().setCard_pass(newPassword);
				service.addUser(currentUser);
				System.out.println("密码修改成功，新密码请牢记："+newPassword);
			}else{
				System.out.println("两次输入的密码不一致，请重新修改");
				changePassword();
			}
		}else{
			System.out.println("原始密码不正确！");
			changePassword();
		}
	}

	private void transferMoney() {
		queryMoney();
		System.out.println("请输入对方卡号：");
		String to_Cardno = sc.next();
		User toUser = service.checkUser(to_Cardno);
		if(toUser!=null){
			System.out.println("请输入转账金额：");
			double money = sc.nextDouble();
			if(money<=currentUser.getCard().getMoney()){
				currentUser.getCard().setMoney(currentUser.getCard().getMoney()-money);
				toUser.getCard().setMoney(toUser.getCard().getMoney()+money);
				//保存两个用户的信息
				service.addUser(currentUser);
				service.addUser(toUser);
				System.out.println("转账成功！");
				queryMoney();
			}
		}
	}

	private void getMoney() {
		queryMoney();
		System.out.println("请输入取款金额：");
		double money = sc.nextDouble();
		if(money<=currentUser.getCard().getMoney()){
			currentUser.getCard().setMoney(currentUser.getCard().getMoney()-money);
			System.out.println("取款成功！");
			service.addUser(currentUser);
			queryMoney();
		}else{
			System.out.println("余额不足！");
		}
	}

	private void saveMoney() {
		System.out.println("请输入存款金额：");
		double money = sc.nextDouble();
		currentUser.getCard().setMoney(currentUser.getCard().getMoney()+money);
		//重新保存用户文件
		service.addUser(currentUser);
		System.out.println("存款成功！");
		queryMoney();
	}

	private void queryMoney() {
		System.out.println("账户可用余额为："+currentUser.getCard().getMoney());
	}

	/**
	 * 注册
	 */
	private void registry() {
		System.out.println("欢迎加入ATM-3.0-FINAL");
		System.out.println("请输入用户名：");
		String user_name = sc.next();
		System.out.println("请输入真实姓名：");
		String user_real_name = sc.next();
		boolean isPass = true;
		String card_pass = null;
		while(isPass){
			System.out.println("请输入密码：");
			card_pass = sc.next();
			System.out.println("请再次输入密码：");
			String card_pass2 = sc.next();
			if(card_pass.equals(card_pass2)){
				isPass=false;
			}else{
				System.out.println("两次输入不一致！");
			}
		}
//		User user = new User();
//		user.setUser_name(user_name);
//		user.setUser_real_name(user_real_name);
//		Card card = new Card();
//		card.setCard_pass(card_pass);
//		user.setCard(card);
		
		User user = new User(user_name,user_real_name);
		user.setCard(new Card(card_pass));
		//保存用户
		service.addUser(user);
		System.out.println("注册成功，卡号为："+user.getCard().getCard_no());
	}
}
