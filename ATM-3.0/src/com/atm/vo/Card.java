package com.atm.vo;

import java.io.Serializable;

public class Card implements Serializable{
	private static final long serialVersionUID = -765806613851353965L;
	private String card_no;		//卡号
	private double money;		//卡钱
	private String card_pass;	//卡密
	
	public Card(){}
	
	public Card(String card_pass){
		this.card_pass = card_pass;
	}
	
	public String getCard_no() {
		return card_no;
	}
	public void setCard_no(String card_no) {
		this.card_no = card_no;
	}
	public double getMoney() {
		return money;
	}
	public void setMoney(double money) {
		this.money = money;
	}
	public String getCard_pass() {
		return card_pass;
	}
	public void setCard_pass(String card_pass) {
		this.card_pass = card_pass;
	}
	
}
