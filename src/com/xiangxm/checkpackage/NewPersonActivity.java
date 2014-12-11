package com.xiangxm.checkpackage;

import org.jsoup.helper.Validate;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.xiangxm.DB.DBHelper;
import com.xiangxm.cls.User;
import com.xiangxm.utils.Constants;

public class NewPersonActivity extends Activity {
	private TextView nameTextView;
	private TextView title;
	private TextView numberTextView;
	private TextView addressTextView;
	private RadioGroup senderOrReceive;
	private RadioButton sendType;
	private RadioButton receiveType;
	private Button savebtn;
	private int userId;
	Intent intent;
	private volatile int checkPersonTypeId=1;
	
	DBHelper myHelper;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_person);
		ActionBar mainBar = getActionBar();
        mainBar.setDisplayHomeAsUpEnabled(true);
        mainBar.setTitle("返回");
		intent = getIntent();
		nameTextView = (TextView)findViewById(R.id.personname);
		numberTextView = (TextView)findViewById(R.id.personmobilenum);
		addressTextView  =(TextView)findViewById(R.id.personlocate);
		title = (TextView)findViewById(R.id.newpersontitle);
		senderOrReceive = (RadioGroup)findViewById(R.id.persontypebtn);
		savebtn = (Button)findViewById(R.id.savepersoninfo);
		sendType = (RadioButton)findViewById(R.id.sendtype);
		receiveType = (RadioButton)findViewById(R.id.receivetype);
		
		
		myHelper = new DBHelper(NewPersonActivity.this);
		myHelper.openDatabase();
		
		savebtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(nameTextView.getText().toString().equals("")||numberTextView.getText().toString().equals("")
						||addressTextView.getText().toString().equals("")){
					Toast.makeText(NewPersonActivity.this, "请填写完整", Toast.LENGTH_SHORT).show();
				}else{
					User user = new User();
					user.username = nameTextView.getText().toString();
					user.mobilePhone = numberTextView.getText().toString();
					user.address = addressTextView.getText().toString();
					user.senderorreceive = checkPersonTypeId;
					user.privacy = 0;
					if(intent.getIntExtra(Constants.UserID, -1)!=-1){
						user._id = userId;
						myHelper.modify(user);
						Toast.makeText(NewPersonActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
					}else{
						myHelper.insert(user);
						Toast.makeText(NewPersonActivity.this, "保存成功", Toast.LENGTH_SHORT).show();
					}
				}
			}
		});
		senderOrReceive.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				if(checkedId==sendType.getId()){
					checkPersonTypeId = 1;
				}else if(checkedId==receiveType.getId()){
					checkPersonTypeId = 2;
				}
				
			}
		});
		if(intent!=null){
			userId = intent.getIntExtra(Constants.UserID, -1);
			if (userId!=-1) {
				title.setText("修改信息");
				User tempuser = myHelper.getById(userId);
				nameTextView.setText(tempuser.username);
				numberTextView.setText(tempuser.mobilePhone);
				addressTextView.setText(tempuser.address);
				if(tempuser.senderorreceive==1){
					senderOrReceive.check(sendType.getId());
				}else if(tempuser.senderorreceive==2){
					senderOrReceive.check(receiveType.getId());
				}
				if (tempuser.senderorreceive < 1) {
					senderOrReceive.setVisibility(View.GONE);
				}
			}
		}
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}
	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		// TODO Auto-generated method stub
		if(item.getItemId() == android.R.id.home){
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
