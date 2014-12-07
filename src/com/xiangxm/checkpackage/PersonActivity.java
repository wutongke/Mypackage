package com.xiangxm.checkpackage;

import java.util.ArrayList;
import java.util.Iterator;

import com.xiangxm.DB.DBHelper;
import com.xiangxm.cls.User;
import com.xiangxm.contact.ContactActivity;
import com.xiangxm.utils.LoginConstant;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class PersonActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_person);
		TextView name = (TextView)findViewById(R.id.personname);
		TextView phone = (TextView)findViewById(R.id.personphone);
		DBHelper helper = new DBHelper(PersonActivity.this);
		//打开数据库
		helper.openDatabase();
		//把user存储到数据库里
		ArrayList<User> result = helper.getAllUser(true);
		Iterator temp = result.iterator();
		while(temp.hasNext()){
			User tempU = (User) temp.next();
			if(LoginConstant.loginName.equals(tempU.username)){
				name.setText(tempU.username);
				phone.setText(tempU.mobilePhone);
				break;
			}
		}
	}
	public void contactInfo(View view){
		Intent intent = new Intent(PersonActivity.this,ContactActivity.class);
		startActivity(intent);
	}
	
	public void wayBillInfo(View view) {
		// TODO Auto-generated method stub
		
	}
}
