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
import android.widget.TextView;

import com.xiangxm.DB.DBHelper;
import com.xiangxm.cls.User;
import com.xiangxm.contact.ContactActivity;
import com.xiangxm.contact.UserDetail;
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
		ArrayList result = helper.getAllUser(true);
		Iterator temp = result.iterator();
		while(temp.hasNext()){
			HashMap tempU = (HashMap) temp.next();
			if(LoginConstant.loginName.equals(tempU.get("name"))){
				name.setText((String)tempU.get("name"));
				phone.setText((String)tempU.get("mobilephone"));
				user._id = (Integer) tempU.get("_id");
				user.address = (String) tempU.get("address");
				user.company = (String) tempU.get("company");
				user.email = (String) tempU.get("email");
				user.familyPhone = (String) tempU.get("familyphone");
				user.imageId = (Integer) tempU.get("imageid");
				user.mobilePhone = (String) tempU.get("mobilephone");
				user.officePhone = (String) tempU.get("officephone");
				user.otherContact = (String) tempU.get("othercontact");
				user.position = (String) tempU.get("position");
				user.remark = (String) tempU.get("remark");
				user.username = (String) tempU.get("name");
				break;
			}
		}
		Button modify = (Button)findViewById(R.id.modifyinfo);
		modify.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(PersonActivity.this,UserDetail.class);
				intent.putExtra("user", user);
				startActivityForResult(intent, 1);
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
