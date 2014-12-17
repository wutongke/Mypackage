package com.xiangxm.cls;

import android.os.Parcel;
import android.os.Parcelable;

public class Order implements Parcelable {
	public int _id;
	public String senderName;
	public String receiveMessage;
	public String sendAddress;
	public String receiverName;
	public String receiverNum;
	public String receiverAddress;
	public int weight;
	public String volume;
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
	public int message;// 1有短信2无短信
	public String number;
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
	public int isover;// 是否确认过 0 没有确认，1确认过

	public Order(Parcel in) {
		// TODO Auto-generated constructor stub
	}
	public Order() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + _id;
		result = prime * result + ((number == null) ? 0 : number.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Order other = (Order) obj;
		if (_id != other._id)
			return false;
		if (number == null) {
			if (other.number != null)
				return false;
		} else if (!number.equals(other.number))
			return false;
		return true;
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub

	}

	public static final Parcelable.Creator<Order> CREATOR = new Parcelable.Creator<Order>() {
		public Order createFromParcel(Parcel in) {
			return new Order(in);
		}

		public Order[] newArray(int size) {
			return new Order[size];
		}
	};
}
