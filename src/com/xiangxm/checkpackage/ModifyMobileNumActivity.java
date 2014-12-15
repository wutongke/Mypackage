package com.xiangxm.checkpackage;

import java.util.Date;

import android.app.ActionBar;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Toast;

import com.xiangxm.DB.DBHelper;
import com.xiangxm.cls.User;
import com.xiangxm.utils.Constants;
import com.xiangxm.utils.LoginConstant;

public class ModifyMobileNumActivity extends Activity {
	private int confirmData;
	private Intent parentIntent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_modify_mobile_num);
		ActionBar mainBar = getActionBar();
		mainBar.setDisplayHomeAsUpEnabled(true);
		mainBar.setTitle("返回");
		parentIntent = getIntent();

		final Handler uiHandler = new Handler();

		final EditText oldNumber = (EditText) findViewById(R.id.modify_mobile_num_oldnum);
		final EditText messageText = (EditText) findViewById(R.id.modify_mobile_num_messagenum);
		final EditText newNumber = (EditText) findViewById(R.id.modify_mobile_num_newnum);

		oldNumber.setText(LoginConstant.loginMobileNum.subSequence(0, 3)
				+ "****" + LoginConstant.loginMobileNum.substring(7));
		findViewById(R.id.modify_mobile_num_btn).setOnClickListener(
				new OnClickListener() {

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
						Toast.makeText(ModifyMobileNumActivity.this, "信息已发送",
								Toast.LENGTH_SHORT).show();
						if (auri == null) {

						} else {
							uiHandler.postDelayed(new Runnable() {

								@Override
								public void run() {
									// TODO Auto-generated method stub
									messageText.setText(String
											.valueOf(confirmData));
								}
							}, 3000);
						}
					}
				});
		findViewById(R.id.modify_mobile_num_submit).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						if (newNumber.getText().toString().equals("")
								|| messageText.getText().toString().equals("")) {
							Toast.makeText(ModifyMobileNumActivity.this,
									"请填写完整", Toast.LENGTH_SHORT).show();
							return;
						}
						if (!messageText.getText().toString()
								.equals(String.valueOf(confirmData))) {
							Toast.makeText(ModifyMobileNumActivity.this,
									"验证码不正确", Toast.LENGTH_SHORT).show();
							return;
						}

						if (parentIntent.getIntExtra(Constants.UserID, -1) != -1) {
							User user = new User();
							// 创建数据库帮助类
							DBHelper helper = new DBHelper(
									ModifyMobileNumActivity.this);
							// 打开数据库
							helper.openDatabase();
							// 把user存储到数据库里
							user = helper.getById(parentIntent.getIntExtra(
									Constants.UserID, -1));
							user.mobilePhone = newNumber.getText().toString();
							helper.modify(user);
							Toast.makeText(ModifyMobileNumActivity.this, "新号码绑定成功",
									Toast.LENGTH_LONG).show();
							Intent intent = new Intent(ModifyMobileNumActivity.this,
									PersonActivity.class);
							intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
							LoginConstant.isLogin = true;
							LoginConstant.loginMobileNum = newNumber.getText().toString()
									.toString();
							startActivity(intent);
							ModifyMobileNumActivity.this.finish();
						}else{
							Toast.makeText(ModifyMobileNumActivity.this, "新号码绑定失败",
									Toast.LENGTH_LONG).show();
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
		if(DBHelper.dbInstance != null) {
			DBHelper.dbInstance.close();
			DBHelper.dbInstance = null;
		}
	}

}
