package com.xiangxm.checkpackage;

import java.util.ArrayList;
import java.util.Iterator;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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
		name = (EditText) findViewById(R.id.namelogin);
		pwd = (EditText) findViewById(R.id.pwdlogin);
		Button loginBtn = (Button) findViewById(R.id.joinbutton);
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
				Iterator temp = result.iterator();
				while(temp.hasNext()){
					User tempU = (User) temp.next();
					if(name.getText().toString().equals(tempU.username)){
						Intent intent = new Intent(LoginActivity.this,WelcomeActivity.class);
						startActivity(intent);
						LoginConstant.isLogin = true;
						LoginConstant.loginName = tempU.username;
						break;
					}
				}
			}
		});
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
