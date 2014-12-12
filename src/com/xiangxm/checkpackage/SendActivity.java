package com.xiangxm.checkpackage;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SendActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_send);
		ActionBar mainBar = getActionBar();
		mainBar.setDisplayHomeAsUpEnabled(true);
		mainBar.setTitle("返回");
		TextView newDing = (TextView) findViewById(R.id.newDing);
		newDing.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(SendActivity.this,
						SubmitOrderActivity.class);
				startActivity(intent);
			}
		});
		findViewById(R.id.ordermore).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(SendActivity.this,
						PackageQueryActivity.class);
				startActivity(intent);
			}
		});
		findViewById(R.id.ordertrack).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(SendActivity.this,
						PackageQueryActivity.class);
				startActivity(intent);
			}
		});
		final EditText comment = (EditText)findViewById(R.id.ordercomment);
		findViewById(R.id.commentsubmit).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(!comment.getText().toString().equals("")){
					Toast.makeText(SendActivity.this, "提交成功", Toast.LENGTH_SHORT).show();
				}else{
					Toast.makeText(SendActivity.this, "请输入内容", Toast.LENGTH_SHORT).show();
				}
			}
		});
		findViewById(R.id.ordersendconfirm).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(SendActivity.this, "发送成功", Toast.LENGTH_SHORT).show();
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
}
