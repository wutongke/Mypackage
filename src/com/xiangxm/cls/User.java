package com.xiangxm.cls;

import java.io.Serializable;

public class User implements Serializable {
	
	public int _id;
	
	public String username;
	
	public String userpwd;
	
	public String mobilePhone;

	public String address;
	//1注册用户0其他用户
	public int privacy;
	//1fasong2jieshou
	public int senderorreceive;

}
