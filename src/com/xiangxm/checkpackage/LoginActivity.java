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
import android.widget.EditText;
import android.widget.Toast;

import com.xiangxm.DB.DBHelper;
import com.xiangxm.cls.User;
import com.xiangxm.utils.LoginConstant;

public class LoginActivity extends Activity {
	private EditText name;
	private EditText pwd;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		ActionBar mainBar = getActionBar();
        mainBar.setDisplayHomeAsUpEnabled(true);
        mainBar.setTitle("返回");
        
		name = (EditText) findViewById(R.id.namelogin);
		pwd = (EditText) findViewById(R.id.pwdlogin);
		Button loginBtn = (Button) findViewById(R.id.loginbutton);
		loginBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(name.getText().toString().equals("")
						|| pwd.getText().toString().equals("")){
					Toast.makeText(LoginActivity.this, "请完整填写信息", Toast.LENGTH_SHORT).show();
					return;
				}
				DBHelper helper = new DBHelper(LoginActivity.this);
				//打开数据库
				helper.openDatabase();
				//把user存储到数据库里
				ArrayList<User> result = helper.getAllUser(true);
				int count = 0;
				Iterator temp = result.iterator();
				while(temp.hasNext()){
					HashMap tempU = (HashMap) temp.next();
					if(name.getText().toString().equals(tempU.get("name"))){
//						Intent intent = new Intent(LoginActivity.this,
//								WelcomeActivity.class);
//						startActivity(intent);
//						LoginConstant.isLogin = true;
//						LoginConstant.loginName = (String) tempU
//								.get("name");
//						LoginActivity.this.finish();
//						break;
						if (pwd.getText().toString().equals(tempU.get("pwd"))) {
							Intent intent = new Intent(LoginActivity.this,
									WelcomeActivity.class);
							intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
							startActivity(intent);
							LoginConstant.isLogin = true;
							LoginConstant.loginName = (String) tempU
									.get("name");
							LoginActivity.this.finish();
							break;
						}
					}
					count++;
				}
				if (count==result.size()) {
					Toast.makeText(LoginActivity.this, "账号或密码错误",
							Toast.LENGTH_SHORT).show();
				}
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
