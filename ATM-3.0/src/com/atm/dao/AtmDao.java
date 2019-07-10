package com.atm.dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * 序列化，反序列化的数据处理层
 * 只处理数据，不管逻辑
 */
public class AtmDao {
	

	public void addObject(String path,String name,Object obj) throws FileNotFoundException, IOException {
		File dirctory = new File(Constant.URL+"/"+path);
		if(!dirctory.exists()){
			dirctory.mkdirs();
		}
		//序列化
		ObjectOutputStream out 
			= new ObjectOutputStream(
					new FileOutputStream(dirctory+"/"+name+Constant.SUFFIX));
		out.writeObject(obj);
		out.flush();
		out.close();
	}

	/**
	 * 根据文件对象，反序列化
	 * @param file
	 * @return Object
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 * @throws ClassNotFoundException 
	 */
	public Object getObject(File file) throws FileNotFoundException, IOException, ClassNotFoundException {
		Object obj = null;
		ObjectInputStream in 
				= new ObjectInputStream(
						new FileInputStream(file)); 
		obj = in.readObject();
		in.close();
		return obj;
	}
	
//	public Object getObject(){
//		
//	}

}
