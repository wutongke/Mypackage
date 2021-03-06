package com.xiangxm.checkpackage;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

public class MySetActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_set);
		ActionBar mainBar = getActionBar();
        mainBar.setDisplayHomeAsUpEnabled(true);
        mainBar.setTitle("返回");
        TextView checkForUpdate = (TextView)findViewById(R.id.check_for_update);
        checkForUpdate.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(MySetActivity.this, "已经是最新版本", Toast.LENGTH_SHORT)
				.show();
			}
		});
        TextView showFunction = (TextView)findViewById(R.id.show_function);
        showFunction.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MySetActivity.this,ShowFunction.class);
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
