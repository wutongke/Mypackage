package com.xiangxm.cls;

public class Order {
	public int _id;
	public String senderName;
	public String senderNum;
	public String sendAddress;
	public String receiverName;
	public String receiverNum;
	public String receiverAddress;
	public int weight;
	public String volume ;
	/**
	 * 付款方式
	 */
	public String type;
	public String company;
	public String time;
	public String otherinfo;
	public String two_code;
	/**
	 * //1有短信2无短信
	 */
	public int message;//1有短信2无短信
	public String number ;
	/**
	 * 快递物品
	 */
	public String content;
	/**
	 * //1.fa2.shou
	 */
	public int sendorreceive;
	public String cost;
	public String sender;
	public String receiver;
	/**
	 * 0未付款，1已付款
	 */
	public int isover;//是否确认过 0 没有确认，1确认过
}
	
