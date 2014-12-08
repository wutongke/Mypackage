package com.xiangxm.checkpackage;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class SendActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_send);
		ActionBar mainBar = getActionBar();
        mainBar.setDisplayHomeAsUpEnabled(true);
        mainBar.setTitle("返回");
        TextView newDing = (TextView)findViewById(R.id.newDing);
        newDing.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(SendActivity.this,NewDingActivity.class);
				startActivity(intent);
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
