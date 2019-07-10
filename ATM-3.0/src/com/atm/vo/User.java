package com.atm.vo;//vo/pojo/domain

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * 储户
 * 需要保存，序列化
 */
public class User implements Serializable{
	private static final long serialVersionUID = 9199900729343852244L;
	
	public User(){}
	
	public User(String user_name,String user_real_name){
		this.user_name = user_name;
		this.user_real_name = user_real_name;
	}
	
	/**
	 * 添加卡密
	 */
	public void addCard(String card_pass){
		card.setCard_pass(card_pass);
	}
	
	public static int ID = 1;
	
	private String user_name;
	private String user_real_name;
	
	//卡
	private Card card;
	//日志 TODO 序列化List集合，测试是否可以序列化
	private Set<Log> sets = new HashSet<Log>();
	
	public void addLog(Log log){
		sets.add(log);
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public String getUser_real_name() {
		return user_real_name;
	}

	public void setUser_real_name(String user_real_name) {
		this.user_real_name = user_real_name;
	}

	public Card getCard() {
		return card;
	}

	public void setCard(Card card) {
		this.card = card;
	}
	
}
