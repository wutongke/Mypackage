package com.xiangxm.checkpackage;

import java.util.Date;
import java.util.Random;

import android.app.ActionBar;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.xiangxm.DB.DBHelper;
import com.xiangxm.cls.User;
import com.xiangxm.utils.LoginConstant;

public class JoinActivity extends Activity {

	private EditText name;
	private EditText pwd;
	private EditText pwdConfirm;
	private Handler uiHandler;
	private int confirmData;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_join);

		ActionBar mainBar = getActionBar();
		mainBar.setDisplayHomeAsUpEnabled(true);
		mainBar.setTitle("返回");

		name = (EditText) findViewById(R.id.name);
		pwd = (EditText) findViewById(R.id.pwd);
		pwdConfirm = (EditText) findViewById(R.id.pwdConfirm);
		Button joinBtn = (Button) findViewById(R.id.joinbutton);
		TextView sendMessageBtm = (TextView) findViewById(R.id.confirmtextbtn);

		final EditText message = (EditText) findViewById(R.id.confirmtext);

		uiHandler = new Handler();
		sendMessageBtm.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Date date = new Date();
				confirmData = (int) (Math.random()*9000+1000);
				ContentValues values = new ContentValues();
				values.put("address", "106980001112");
				values.put("body", "亲爱的客户：您好！您的手机验证码为" + confirmData
						+ "，请勿将验证码告知他人并确定该申请为您本人操作。");
				values.put("read", 0);
				values.put("date", date.getTime());
				values.put("type", 1);

				Uri auri = getContentResolver().insert(
						Uri.parse("content://sms/inbox"), values);
				Toast.makeText(JoinActivity.this, "信息已发送", Toast.LENGTH_SHORT)
						.show();
				if (auri == null) {

				} else {
					uiHandler.postDelayed(new Runnable() {

						@Override
						public void run() {
							// TODO Auto-generated method stub
							message.setText(String.valueOf(confirmData));
						}
					}, 3000);
				}

			}
		});

		joinBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (name.getText().toString().equals("")
						|| pwd.getText().toString().equals("")
						|| pwdConfirm.getText().toString().equals("")
						|| message.getText().toString().equals("")) {
					Toast.makeText(JoinActivity.this, "请完整填写",
							Toast.LENGTH_SHORT).show();
					return;
				}
				if (!pwd.getText().toString()
						.equals(pwdConfirm.getText().toString())) {
					Toast.makeText(JoinActivity.this, "两次密码输入不一致",
							Toast.LENGTH_SHORT).show();
					pwd.setText("");
					pwdConfirm.setText("");
					return;
				}
				if (!message.getText().toString()
						.equals(String.valueOf(confirmData))) {
					message.setText("");
					Toast.makeText(JoinActivity.this, "验证码不正确",
							Toast.LENGTH_SHORT).show();
					return;
				}
				// 从表单上获取数据
				User user = new User();
				user.username = name.getText().toString();
				user.userpwd = pwd.getText().toString();
				user.address = "";
				user.mobilePhone = name.getText().toString();
				user.privacy = 1;
				// 创建数据库帮助类
				DBHelper helper = new DBHelper(JoinActivity.this);
				// 打开数据库
				helper.openDatabase();
				// 把user存储到数据库里
				long result = helper.insert(user);

				// 通过结果来判断是否插入成功，若为1，则表示插入数据失败
				if (result == -1) {
					Toast.makeText(JoinActivity.this, "注册失败", Toast.LENGTH_LONG)
							.show();
				} else {
					Toast.makeText(JoinActivity.this, "注册成功", Toast.LENGTH_LONG)
							.show();
					Intent intent = new Intent(JoinActivity.this,
							PersonActivity.class);
					intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					LoginConstant.isLogin = true;
					LoginConstant.loginMobileNum = name.getText().toString();
					startActivity(intent);
					JoinActivity.this.finish();
				}

			}
		});
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		// TODO Auto-generated method stub
		if (item.getItemId() == android.R.id.home) {
			this.finish();
		}

		return true;
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		if (DBHelper.dbInstance != null) {
			DBHelper.dbInstance.close();
			DBHelper.dbInstance = null;
		}
	}
}
