package com.atm.service;

import java.io.File;
import java.util.Random;

import com.atm.dao.Constant;

public abstract class ICBC_Bank implements IBank{
	//卡号的位数
	private final int CARDNOLENGTH = 6;
	
	@Override
	public String generateCardNo() {
		Random random = new Random();
		int num = random.nextInt(1000000);
		String strnum = String.valueOf(num);
		if(strnum.length()!=CARDNOLENGTH){
			//补零
			for(int i=0;i<CARDNOLENGTH-strnum.length();i++){
				strnum = "0" + strnum;
			}
		}
		//TODO 查重
		File file = new File(Constant.URL);
		File[] fileArr = file.listFiles();
		for(File directory : fileArr){
			if(directory.getName().contains(strnum)){
				//卡号重复
				System.out.println("卡号重复"+strnum);
				generateCardNo();
			}else{
				return strnum;
			}
		}
		return strnum;
	}
}
