package com.xiangxm.checkpackage;

import com.xiangxm.utils.Constants;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;

public class PersonModifyActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_person_modify);
		ActionBar mainBar = getActionBar();
        mainBar.setDisplayHomeAsUpEnabled(true);
        mainBar.setTitle("返回");
        final Intent intent = getIntent();
        
        findViewById(R.id.personmodifyl1).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent toModiryNum = new Intent(PersonModifyActivity.this,ModifyMobileNumActivity.class);
				toModiryNum.putExtra(Constants.UserID, intent.getIntExtra(Constants.UserID, 1));
				startActivity(toModiryNum);
			}
		});
        findViewById(R.id.personmodifyl2).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent toModiryNum = new Intent(PersonModifyActivity.this,ModifyPwdActivity.class);
				toModiryNum.putExtra(Constants.UserID, intent.getIntExtra(Constants.UserID, 1));
				startActivity(toModiryNum);
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
