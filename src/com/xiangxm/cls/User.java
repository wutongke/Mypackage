package com.xiangxm.cls;

import com.xiangxm.sortlistview.SortAdapter;

import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable {
	
	public User(){
		
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + _id;
		result = prime * result
				+ ((mobilePhone == null) ? 0 : mobilePhone.hashCode());
		result = prime * result + privacy;
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
		User other = (User) obj;
		if (_id != other._id)
			return false;
		if (mobilePhone == null) {
			if (other.mobilePhone != null)
				return false;
		} else if (!mobilePhone.equals(other.mobilePhone))
			return false;
		if (privacy != other.privacy)
			return false;
		return true;
	}
	public int _id;
	
	public String username;
	
	public String userpwd;
	
	public String mobilePhone;

	public String address;
	//1注册用户0其他用户
	public int privacy;
	//1fasong2jieshou
	public int senderorreceive;
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		dest.writeInt(_id);
		dest.writeString(username);
		dest.writeString(userpwd);
		dest.writeString(mobilePhone);
		dest.writeString(address);
		dest.writeInt(privacy);
		dest.writeInt(senderorreceive);
	}
	
	public static final Parcelable.Creator<User> CREATOR = new Creator<User>() {
		
		@Override
		public User[] newArray(int size) {
			// TODO Auto-generated method stub
			return new User[size];
		}
		
		@Override
		public User createFromParcel(Parcel source) {
			// TODO Auto-generated method stub
			
			return new User(source);
		}
	};
	private User (Parcel source){
		_id = source.readInt();
		username = source.readString();
		userpwd = source.readString();
		mobilePhone = source.readString();
		address = source.readString();
		privacy = source.readInt();
		senderorreceive = source.readInt();
	}
}
