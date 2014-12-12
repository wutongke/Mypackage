package com.xiangxm.checkpackage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xiangxm.DB.DBHelper;
import com.xiangxm.cls.User;
import com.xiangxm.utils.Constants;
import com.xiangxm.utils.LoginConstant;

public class PersonActivity extends Activity {
	private User user = new User();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_person);
		
		ActionBar mainBar = getActionBar();
        mainBar.setDisplayHomeAsUpEnabled(true);
        mainBar.setTitle("返回");
		
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
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
			if(LoginConstant.loginMobileNum.equals(tempU.mobilePhone)){
				String a = tempU.mobilePhone.substring(0,3);
				String b = tempU.mobilePhone.substring(7);
				name.setText(a+"****"+b);
				phone.setText("注册会员");
				user = tempU;
				break;
			}
		}
		RelativeLayout modify = (RelativeLayout)findViewById(R.id.personmodifyinfo);
		modify.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(PersonActivity.this,PersonModifyActivity.class);
				intent.putExtra(Constants.UserID, user._id);
				startActivity(intent);
			}
		});
		findViewById(R.id.contactinfo).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(PersonActivity.this,ContactActivity.class);
				startActivity(intent);
			}
		});
		findViewById(R.id.order_info).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(PersonActivity.this,MyOrderActivity.class);
				startActivity(intent);
			}
		});
	}
	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		// TODO Auto-generated method stub
		if(item.getItemId() == android.R.id.home){
			this.finish();
		}
       
     return true; 
	}
	public void contactInfo(View view){
		Intent intent = new Intent(PersonActivity.this,ContactActivity.class);
		startActivity(intent);
	}
	
	public void wayBillInfo(View view) {
		// TODO Auto-generated method stub
		
	}
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		if(DBHelper.dbInstance != null) {
			DBHelper.dbInstance.close();
			DBHelper.dbInstance = null;
		}
	}
}
