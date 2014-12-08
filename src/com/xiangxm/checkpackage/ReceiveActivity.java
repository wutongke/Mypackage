package com.xiangxm.checkpackage;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

public class ReceiveActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_receive);
		ActionBar mainBar = getActionBar();
        mainBar.setDisplayHomeAsUpEnabled(true);
        mainBar.setTitle("返回");
        TextView confirmOrder = (TextView)findViewById(R.id.confirmorder);
        confirmOrder.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent orderIntent = new Intent(ReceiveActivity.this,MyOrderActivity.class);
				startActivity(orderIntent);
			}
		});
        findViewById(R.id.getgoods).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(ReceiveActivity.this, "请到指定地点提取快递", Toast.LENGTH_SHORT).show();
				
			}
		});
        findViewById(R.id.orderfinish).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(ReceiveActivity.this, "完成订单", Toast.LENGTH_SHORT).show();
				
			}
		});
        findViewById(R.id.orderforother).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(ReceiveActivity.this, "请自行联系快递公司", Toast.LENGTH_SHORT).show();
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
}
