package com.xiangxm.checkpackage;

import java.util.ArrayList;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.xiangxm.DB.DBHelper;
import com.xiangxm.cls.User;
import com.xiangxm.utils.Constants;

public class ContactActivity extends Activity {
	
	private RadioGroup personSendOrReceiveRadiaGroup;
	
	private ListView personListView;
	private ArrayList<String> personListData;
	private ArrayList<User> personListForIntent;
	private ArrayAdapter<String>personListAdapter;
	private RadioButton personTypeSend;
	private RadioButton personTypeReceive;
	
	private TextView newPersonButotn;
	private Intent fromParentIntent;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_contact);
		ActionBar mainBar = getActionBar();
        mainBar.setDisplayHomeAsUpEnabled(true);
        mainBar.setTitle("返回");
        fromParentIntent = getIntent();
		personSendOrReceiveRadiaGroup = (RadioGroup)findViewById(R.id.personlistbtn);
		personTypeSend = (RadioButton)findViewById(R.id.persontypea);
		personTypeReceive = (RadioButton)findViewById(R.id.persontypeb);
		personListView = (ListView)findViewById(R.id.personlist);
		newPersonButotn = (TextView)findViewById(R.id.addnewperson);
		
		personListData = new  ArrayList<String>();
		personListAdapter = new ArrayAdapter<String>(ContactActivity.this, android.R.layout.simple_list_item_1, personListData);
		personListView.setAdapter(personListAdapter);
		
		personSendOrReceiveRadiaGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				if(checkedId==personTypeSend.getId()){
					loadpersonList(1);
				}else if(checkedId==personTypeReceive.getId()){
					loadpersonList(2);
				}
				
			}
		});
		
		newPersonButotn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(ContactActivity.this,NewPersonActivity.class);
				startActivity(intent);
			}
		});
		personListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if (fromParentIntent.getIntExtra(MySendActivity.FORPERSON, -1)==-1) {
					// TODO Auto-generated method stub
					Intent intent = new Intent(ContactActivity.this,
							NewPersonActivity.class);
					intent.putExtra(Constants.UserID,
							personListForIntent.get((int) id)._id);
					startActivity(intent);
				}else{
					Intent intent = new Intent();
					intent.putExtra(Constants.PERSONINFO, personListData.get(position));
						setResult(RESULT_OK, intent);
						ContactActivity.this.finish();
				}
			}
		});
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		loadpersonList(1);
	}
	private void loadpersonList(int senderOrReveiver){
		DBHelper helper = new DBHelper(ContactActivity.this);
		helper.openDatabase();
		personListForIntent = new ArrayList<User>();
		if (senderOrReveiver==1) {
			personListForIntent = helper.getAllSender();
		}else if(senderOrReveiver==2){
			personListForIntent = helper.getAllRecriver();
		}
		ArrayList<String> tempList = new ArrayList<String>();
		if (personListForIntent!=null&&!personListForIntent.isEmpty()) {
			for (User temp : personListForIntent) {
				tempList.add("姓名： " + temp.username + "   电话："
						+ temp.mobilePhone + "\n地址： " + temp.address);
			}
		}
		personListData.clear();
		personListData.addAll(tempList);
		personListAdapter.notifyDataSetInvalidated();
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
