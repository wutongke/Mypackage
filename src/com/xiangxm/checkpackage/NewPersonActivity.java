package com.xiangxm.checkpackage;

import java.util.ArrayList;

import org.jsoup.helper.Validate;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
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
	private String addressA;
	private String addressB;
	private String addressC;
	
	
	DBHelper myHelper;
	
	private Spinner provice;
	private Spinner city;
	private Spinner urban;
	ArrayList<String> cityList = new ArrayList<String>();
	ArrayList<String> urbanList = new ArrayList<String>();
	ArrayList<String> provinceList = new ArrayList<String>();
	
	ArrayAdapter<String> cityAdapter ;
	ArrayAdapter<String> urbanAdapter ;

	private String[] provinces = {"北京","上海","天津","江苏","重庆","河北","山西","辽宁","吉林","黑龙江",
			"浙江","安徽","福建","江西","山东","内蒙古","广西","宁夏","新疆","西藏",
			"河南","湖北","湖南","广东","海南","四川","贵州","云南",
			"陕西","甘肃","青海","台湾","香港","澳门"};
	
	private String[] beijing = {"东城区","西城区","朝阳区","海淀区","丰台区","石景山区","门头沟区","房山区","大兴区","通州区","顺义区", 
			"昌平区","平谷区","怀柔区","密云区","延庆区"};
	private String[] shanghai = {
			"黄浦区","浦东新区","徐汇区","长宁区","静安区","普陀区","闸北区","虹口区","杨浦区","闵行区","宝山区","嘉定区","金山区","松江区","青浦区","奉贤区","崇明县"
			
	};
	private String[] jiangsu = {"南京","无锡","徐州","常州","苏州","南通","连云港","淮安","盐城","扬州","镇江","泰州","宿迁"};
	private String[] nanjing = {"玄武区","秦淮区","鼓楼区","建邺区","栖霞区","雨花台区","江宁区","浦口区","六合区","溧水区","高淳区"};
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
		provice = (Spinner)findViewById(R.id.select_province);
		city = (Spinner)findViewById(R.id.select_city);
		urban = (Spinner)findViewById(R.id.select_urban);
		
		
		myHelper = new DBHelper(NewPersonActivity.this);
		myHelper.openDatabase();
		
		
		
		for(int i=0,n = provinces.length;i<n;i++){
			provinceList.add(provinces[i]);
		}
		ArrayAdapter<String> provinceAdapter = new ArrayAdapter<String>(NewPersonActivity.this, android.R.layout.simple_spinner_item,provinceList);
		cityAdapter = new ArrayAdapter<String>(NewPersonActivity.this, android.R.layout.simple_spinner_item,cityList);
		urbanAdapter = new ArrayAdapter<String>(NewPersonActivity.this, android.R.layout.simple_spinner_item,urbanList);
		provice.setAdapter(provinceAdapter);
		city.setAdapter(cityAdapter);
		urban.setAdapter(urbanAdapter);
		provice.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				cityList.clear();
				switch(position){
				case 0:
					for(int i=0,n = beijing.length;i<n;i++){
						cityList.add(beijing[i]);
					}
					cityAdapter.notifyDataSetChanged();
					break;
				case 1:
					for(int i=0,n = shanghai.length;i<n;i++){
						cityList.add(shanghai[i]);
					}
					cityAdapter.notifyDataSetChanged();
					break;
				case 3:
					for(int i=0,n = jiangsu.length;i<n;i++){
						cityList.add(jiangsu[i]);
					}
					cityAdapter.notifyDataSetChanged();
					break;
				default:
					break;
				}
				addressA = provinceList.get(position);
				addressB = "";
				addressC = "";
				addressTextView.setText(addressA+addressB+addressC);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				
			}
		});
		
		city.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				urbanList.clear();
				if(position==0&&provice.getSelectedItemPosition()==3){
					for(int i=0,n = nanjing.length;i<n;i++){
						urbanList.add(nanjing[i]);
					}
					urbanAdapter.notifyDataSetChanged();
				}
				addressB = cityList.get(position);
				addressC = "";
				addressTextView.setText(addressA+addressB+addressC);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				
			}
		});
		urban.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				// TODO Auto-generated method stub
				addressC = urbanList.get(position);
				addressTextView.setText(addressA+addressB+addressC);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				
			}
		});
		
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
