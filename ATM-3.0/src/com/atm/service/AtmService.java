package com.atm.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.atm.dao.AtmDao;
import com.atm.dao.Constant;
import com.atm.vo.Card;
import com.atm.vo.User;

/**
 * 处理业务逻辑，处理异常信息，校验参数内容....
 * 业务处理层
 */
public class AtmService {
	private AtmDao dao = new AtmDao();
	//银行对象
	private IBank bank = new Harbin_ICBC_Bank();
	

	public void addUser(User user) {
		try {
			//生成卡号，先判断，该用户是否有卡号，如果没有，则生成
			if(user.getCard().getCard_no()==null || user.getCard().getCard_no().equals("")){
				String card_no = bank.generateCardNo();
				user.getCard().setCard_no(card_no);
			}
			//保存用户信息
			dao.addObject(user.getUser_name()+"-"+user.getCard().getCard_no(),user.getUser_name(),user);
			//保存卡信息 TODO 不保存卡信息
			dao.addObject(user.getUser_name()+"-"+user.getCard().getCard_no(),user.getCard().getCard_no(),user.getCard());
		} catch (FileNotFoundException e) {
			System.out.println("数据访问异常，请联系管理员！ERROR-1528");
		} catch (IOException e) {
			//邮件处理 javamail
			System.out.println("数据访问异常，请联系管理员！ERROR-1847");
		}
	}


	public User checkUser(String card_no, String card_pass) {
		User user = null;
		//查卡号和密码是否正确，扫描所有的文件
		File file = new File(Constant.URL);
		File userDirectory[] = file.listFiles();
		for(File directory : userDirectory){
			//如果文件夹名包含输入的卡号
			if(directory.getName().contains(card_no)){
				File[] fileArr = directory.listFiles();
				try {
					Card card = null;
					for(int i=0;i<fileArr.length;i++){
						File f = fileArr[i];
						//判断文件名是否等于卡号+后缀
						if(f.getName().equals(card_no+Constant.SUFFIX)){
							//找到文件，先反序列化，再比较密码
							card = (Card)dao.getObject(f);
							if(card_pass.equals(card.getCard_pass())){
								if(i==0){
									user = (User)dao.getObject(fileArr[i+1]);
								}else if(i==1){
									user = (User)dao.getObject(fileArr[i-1]);
								}
								user.setCard(card);//TODO 可以不需要加
								//TODO 重新将所有日志，写回user对象
								break;
							}
						}
					}
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return user;
	}


	public User checkUser(String to_Cardno) {
		User user = null;
		//查卡号和密码是否正确，扫描所有的文件
		File file = new File(Constant.URL);
		File userDirectory[] = file.listFiles();
		for(File directory : userDirectory){
			//如果文件夹名包含输入的卡号
			if(directory.getName().contains(to_Cardno)){
				File[] fileArr = directory.listFiles();
				try {
					Card card = null;
					for(int i=0;i<fileArr.length;i++){
						File f = fileArr[i];
						//判断文件名是否等于卡号+后缀
						if(f.getName().equals(to_Cardno+Constant.SUFFIX)){
							//找到文件，先反序列化，再比较密码
							card = (Card)dao.getObject(f);
							if(i==0){
								user = (User)dao.getObject(fileArr[i+1]);
							}else if(i==1){
								user = (User)dao.getObject(fileArr[i-1]);
							}
							user.setCard(card);
							break;
						}
					}
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return user;
	}


	/**
	 * 销户
	 * @param currentUser
	 */
	public void destroyUser(User currentUser) {
		try {
			File file = new File(Constant.URL+"/"+currentUser.getUser_name()+"-"+currentUser.getCard().getCard_no());
			for(File f : file.listFiles()){
				f.delete();//删文件
			}
			file.delete();//删除文件夹
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 获取当前系统时间
	 * 格式为：yyyy-MM-dd HH:mm:ss
	 */
	public String getSystemDate(){
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
	}
}
