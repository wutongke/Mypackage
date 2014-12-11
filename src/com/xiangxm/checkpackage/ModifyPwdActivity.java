package com.xiangxm.checkpackage;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Toast;

import com.xiangxm.DB.DBHelper;
import com.xiangxm.cls.User;
import com.xiangxm.utils.Constants;

public class ModifyPwdActivity extends Activity {
	private Intent parentIntent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_modify_pwd);
		ActionBar mainBar = getActionBar();
		mainBar.setDisplayHomeAsUpEnabled(true);
		mainBar.setTitle("返回");
		parentIntent = getIntent();

		final EditText oldPwd = (EditText) findViewById(R.id.modify_pwd_oldpwd);
		final EditText newPwd = (EditText) findViewById(R.id.modify_pwd_newpwd);
		final EditText newPwdConfirm = (EditText) findViewById(R.id.modify_pwd_confirm_newpwd);

		findViewById(R.id.modify_pwd_num_submit).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						if (oldPwd.getText().toString().equals("")
								|| newPwd.getText().toString().equals("")
								|| newPwdConfirm.getText().toString()
										.equals("")) {
							Toast.makeText(ModifyPwdActivity.this,
									"请填写完整", Toast.LENGTH_SHORT).show();
							return;
						}
						User user = new User();
						// 创建数据库帮助类
						DBHelper helper = new DBHelper(ModifyPwdActivity.this);
						// 打开数据库
						helper.openDatabase();
						if (parentIntent.getIntExtra(Constants.UserID, -1) != -1) {
							user = helper.getById(parentIntent.getIntExtra(
									Constants.UserID, -1));
						}
						if(!oldPwd.getText().toString().equals(user.userpwd)){
							Toast.makeText(ModifyPwdActivity.this,
									"原密码不正确", Toast.LENGTH_SHORT).show();
							return;
						}
						user.userpwd = newPwd.getText().toString();
						helper.modify(user);
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
