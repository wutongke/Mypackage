package com.xiangxm.checkpackage;

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

public class JoinActivity extends Activity {

	private EditText name;
	private EditText pwd;
	private EditText pwdConfirm;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_join);
		name = (EditText) findViewById(R.id.name);
		pwd = (EditText) findViewById(R.id.pwd);
		pwdConfirm = (EditText) findViewById(R.id.pwdConfirm);
		Button joinBtn = (Button) findViewById(R.id.joinbutton);
		joinBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (name.getText().toString().equals("")
						|| pwd.getText().toString().equals("")
						|| pwdConfirm.getText().toString().equals("")) {
					Toast.makeText(JoinActivity.this, "请完整填写", Toast.LENGTH_SHORT).show();
					return;
				}
				if(!pwd.getText().toString().equals(pwdConfirm.getText().toString())){
					Toast.makeText(JoinActivity.this, "两次密码输入不一致", Toast.LENGTH_SHORT).show();
					pwd.setText("");
					pwdConfirm.setText("");
					return;
				}
				//从表单上获取数据
				User user = new User();
				user.username = name.getText().toString();
				user.address = "";
				user.company = "";
				user.email = "";
				user.familyPhone = "";
				user.mobilePhone = "";
				user.officePhone = "";
				user.otherContact = "";
				user.position = "";
				user.remark = "";
				user.zipCode = "";
				
				user.privacy = 1;
				//创建数据库帮助类
				DBHelper helper = new DBHelper(JoinActivity.this);
				//打开数据库
				helper.openDatabase();
				//把user存储到数据库里
				long result = helper.insert(user);
				
				//通过结果来判断是否插入成功，若为1，则表示插入数据失败
				if(result == -1 ) {
					Toast.makeText(JoinActivity.this, "注册失败", Toast.LENGTH_LONG);
				}else{
					Toast.makeText(JoinActivity.this, "注册成功", Toast.LENGTH_LONG);
				}
				Intent intent = new Intent(JoinActivity.this,WelcomeActivity.class);
				startActivity(intent);
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
