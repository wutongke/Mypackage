package com.xiangxm.checkpackage;

import com.xiangxm.utils.LoginConstant;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class WelcomeActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_welcome);
	}
	public void ic_person(View view) {
		// TODO Auto-generated method stub
		Intent intent;
		if(LoginConstant.isLogin){
			intent = new Intent(WelcomeActivity.this,PersonActivity.class);
			
		}else{
			intent = new Intent(WelcomeActivity.this,LoginActivity.class);
			Toast.makeText(WelcomeActivity.this, "请先登录", Toast.LENGTH_SHORT).show();
		}
		startActivity(intent);
	}
	public void ic_other(View view) {
		// TODO Auto-generated method stub
		Intent intent;
		if(LoginConstant.isLogin){
			intent = new Intent(WelcomeActivity.this,PersonActivity.class);
			
		}else{
			intent = new Intent(WelcomeActivity.this,LoginActivity.class);
			Toast.makeText(WelcomeActivity.this, "请先登录", Toast.LENGTH_SHORT).show();
		}
		startActivity(intent);
	}
	
	public void ic_system(View view) {
		// TODO Auto-generated method stub
		Toast.makeText(WelcomeActivity.this, "请选择其他按钮", Toast.LENGTH_SHORT).show();
	}
	public void ic_query(View view) {
		// TODO Auto-generated method stub
		Intent query = new Intent(WelcomeActivity.this,PackageQueryActivity.class);
		startActivity(query);
	}
	public void ic_send(View view) {
		// TODO Auto-generated method stub
		Intent query = new Intent(WelcomeActivity.this,SendActivity.class);
		startActivity(query);
	}
	public void ic_receive(View view) {
		// TODO Auto-generated method stub
		Intent query = new Intent(WelcomeActivity.this,ReceiveActivity.class);
		startActivity(query);
	}
	public void ic_join(View view) {
		// TODO Auto-generated method stub
		
		Intent joinIntent = new Intent(WelcomeActivity.this,JoinActivity.class);
		startActivity(joinIntent);
	}
	public void ic_login(View view) {
		// TODO Auto-generated method stub
		Intent joinIntent = new Intent(WelcomeActivity.this,LoginActivity.class);
		startActivity(joinIntent);
	}
	public void ic_contact(View view) {
		// TODO Auto-generated method stub
		Intent joinIntent = new Intent(WelcomeActivity.this,ContactUsActivity.class);
		startActivity(joinIntent);
	}
	public void ic_set(View view) {
		// TODO Auto-generated method stub
		Intent joinIntent = new Intent(WelcomeActivity.this,MySetActivity.class);
		startActivity(joinIntent);
	}
	
}
