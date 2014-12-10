package com.xiangxm.contact;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Gallery;
import android.widget.ImageButton;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.ViewSwitcher.ViewFactory;

import com.xiangxm.DB.DBHelper;
import com.xiangxm.checkpackage.R;
import com.xiangxm.cls.User;

public class UserDetail extends Activity implements ViewFactory {

	EditText et_name;
	EditText et_mobilePhone;
	EditText et_address;
	EditText et_otherContact;
	EditText et_email;
	EditText et_remark;
	
	Button btn_save;
	Button btn_return;
	Button btn_delete;
	//头像的按钮
	ImageButton imageButton;
	//用flag来判断按钮的状态   false表示查看点击修改状态  true表示点击修改保存状态
	boolean flag = false;
	boolean imageChanged = false;
	boolean isDataChanged = false;
	
	int currentImagePosition;
	int previousImagePosition;
	
	String[] callData;
	//表示状态：打电话，发短信，发邮件
	String status;
	//拥有一个user实例，这个对象由Intent传过来
	User user;
	Gallery gallery;
	ImageSwitcher is;
	
	View numChooseView;
	View imageChooseView;
	
	//号码选择的对话框
	AlertDialog numChooseDialog;
	AlertDialog imageChooseDialog;
	/**
	 * 所有的图像图片
	 */
	private  int[] images 
			= new int[]{R.drawable.icon
		,R.drawable.image1,R.drawable.image2,R.drawable.image3
		,R.drawable.image4,R.drawable.image5,R.drawable.image6
		,R.drawable.image7,R.drawable.image8,R.drawable.image9
		,R.drawable.image10,R.drawable.image11,R.drawable.image12
		,R.drawable.image13,R.drawable.image14,R.drawable.image15
		,R.drawable.image16,R.drawable.image17,R.drawable.image18
		,R.drawable.image19,R.drawable.image20,R.drawable.image21
		,R.drawable.image22,R.drawable.image23,R.drawable.image24
		,R.drawable.image25,R.drawable.image26,R.drawable.image27
		,R.drawable.image28,R.drawable.image29,R.drawable.image30};
	
	

	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.userdetail);

		//获得意图
		Intent intent = getIntent();
		//从意图中得到需要的user对象
		user = (User) intent.getSerializableExtra("user");
		// 加载数据,往控件上赋值
		loadUserData();
		// 设置EditText不可编辑
		setEditTextDisable();
		
		//为按钮添加监听类
		btn_save.setOnClickListener(new OnClickListener(){
			
			public void onClick(View arg0) {
				if(!flag) {
					btn_save.setText("保存修改");
					setEditTextAble();
					flag = true;
				} else {
					//往数据库里面更新数据
					setTitle("modify");
					modify();
					setEditTextDisable();
					setColorToWhite();
					btn_save.setText("修改");
					flag = false;
				}
				
			}});
		
		
		btn_return.setOnClickListener(new OnClickListener(){

			
			public void onClick(View v) {
				if(isDataChanged) {
					setResult(4);
				}  else {
					setResult(5);
				}
				finish();
			}});
		
		
		btn_delete.setOnClickListener(new OnClickListener(){

			
			public void onClick(View v) {
				new AlertDialog.Builder(UserDetail.this).
				setPositiveButton("确定",new DialogInterface.OnClickListener() {
					
					public void onClick(DialogInterface dialog, int which) {
						delete();
						setResult(4);
						finish();
					}
				}).setNegativeButton("取消", new DialogInterface.OnClickListener() {
					
					
					public void onClick(DialogInterface dialog, int which) {
						
					}
				}).setTitle("是否要删除?").create().show();
				
			}});
		
		
		imageButton.setOnClickListener(new OnClickListener(){

			
			public void onClick(View v) {
				loadImage();//加载imageChooseView，只加载一次
				initImageChooseDialog();//加载imageChooseDialog，只加载一次
				imageChooseDialog.show();
				
				
			}});
		
		

	}

	/**
	 * 获得布局文件中的控件，并且根据传递过来user对象对控件进行赋值
	 */
	public void loadUserData() {
		// 获得EditText控件
		et_name = (EditText) findViewById(R.id.username);
		et_mobilePhone = (EditText) findViewById(R.id.mobilephone);
		et_address = (EditText) findViewById(R.id.address);
		et_otherContact = (EditText) findViewById(R.id.othercontact);
		et_email = (EditText) findViewById(R.id.email);
		et_remark = (EditText) findViewById(R.id.remark);
		
		// 获得Button控件
		btn_save = (Button)findViewById(R.id.save);
		btn_return = (Button)findViewById(R.id.btn_return);
		btn_delete = (Button)findViewById(R.id.delete);
		imageButton = (ImageButton)findViewById(R.id.image_button);
		
		// 为控件赋值
		et_name.setText(user.username);
		et_mobilePhone.setText(user.mobilePhone);
		et_address.setText(user.address);
		et_otherContact.setText(user.otherContact);
		et_email.setText(user.email);
		et_remark.setText(user.remark);
		imageButton.setImageResource(user.imageId);
	}

	/**
	 * 设置EditText为不可用
	 */
	private void setEditTextDisable() {
		et_name.setEnabled(false);
		et_mobilePhone.setEnabled(false);
		et_address.setEnabled(false);
		et_otherContact.setEnabled(false);
		et_email.setEnabled(false);
		et_remark.setEnabled(false);
		imageButton.setEnabled(false);
		setColorToWhite();

	}

	/**
	 * 设置EditText为可用状态
	 */
	private void setEditTextAble() {
		et_name.setEnabled(true);
		et_mobilePhone.setEnabled(true);
		et_address.setEnabled(true);
		et_otherContact.setEnabled(true);
		et_email.setEnabled(true);
		et_remark.setEnabled(true);
		imageButton.setEnabled(true);
		setColorToBlack();
	}
	
	/**
	 *  设置显示的字体颜色为黑色
	 */
	private void setColorToBlack() {
		
		et_name.setTextColor(Color.BLACK);
		et_mobilePhone.setTextColor(Color.BLACK);
		et_address.setTextColor(Color.BLACK);
		et_otherContact.setTextColor(Color.BLACK);
		et_email.setTextColor(Color.BLACK);
		et_remark.setTextColor(Color.BLACK);
	}
	
	/**
	 *  设置显示的字体颜色为白色
	 */
	private void setColorToWhite() {
		et_name.setTextColor(Color.WHITE);
		et_mobilePhone.setTextColor(Color.WHITE);
		et_address.setTextColor(Color.WHITE);
		et_otherContact.setTextColor(Color.WHITE);
		et_email.setTextColor(Color.WHITE);
		et_remark.setTextColor(Color.WHITE);
	}

	/**
	 * 获得最新数据，创建DBHelper对象，更新数据库
	 */
	private void modify() {
		user.username = et_name.getText().toString();
		user.address = et_address.getText().toString();
		user.email = et_email.getText().toString();
		user.mobilePhone = et_mobilePhone.getText().toString();
		user.otherContact = et_otherContact.getText().toString();
		user.remark = et_remark.getText().toString();
		if(imageChanged) {
			user.imageId = images[currentImagePosition%images.length];
		}
		
		DBHelper helper = new DBHelper(this);
		//打开数据库
		helper.openDatabase();
		helper.modify(user);
		isDataChanged = true;
	}
	
	private void delete() {
		DBHelper helper = new DBHelper(this);
		//打开数据库
		helper.openDatabase();
		helper.delete(user._id);
	}
	
	/**
	 * 为Menu添加几个选项
	 */
	
	public boolean onCreateOptionsMenu(Menu menu) {

		menu.addSubMenu(0, Menu.FIRST, 1, "打电话");
		menu.addSubMenu(0, Menu.FIRST+1, 2, "发短信");
		menu.addSubMenu(0, Menu.FIRST+2, 3, "发邮件");
		
		//为每一个Item设置图标
		MenuItem item = menu.getItem(Menu.FIRST-1);
		item.setIcon(R.drawable.dial);
		MenuItem item1 = menu.getItem(Menu.FIRST);
		item1.setIcon(R.drawable.send_sms);
		MenuItem item2 = menu.getItem(Menu.FIRST+1);
		item2.setIcon(R.drawable.mail);
		return super.onCreateOptionsMenu(menu);
	}
	
	/**
	 * 为每一个MenuItem添加事件
	 */
	
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		
		switch(item.getItemId()){
			case Menu.FIRST: {
				//将状态设置为打电话
				status = Intent.ACTION_CALL;
				if(callData == null) {
					//加载可用的号码
					loadAvailableCallData();
				}
				
				if(callData.length == 0) {
					//提示没有可用的号码
					Toast.makeText(this, "没有可用的号码！", Toast.LENGTH_LONG).show();
				} else if(callData.length == 1) {
					//如果之有一个可用的号码，这直接使用这个号码拨出
					Intent intent = 
						new Intent(Intent.ACTION_CALL,Uri.parse("tel://" + callData[0]));
					startActivity(intent);
				} else {
					//如果有2个或者2个以上号码，弹出号码选择对话框
					initNumChooseDialog();
				}
				break;
			}
			case Menu.FIRST+1: {
				status = Intent.ACTION_SENDTO;
				if(callData == null) {
					loadAvailableCallData();
				}
				if(callData.length == 0) {
					//提示没有可用的号码
					Toast.makeText(this, "没有可用的号码！", Toast.LENGTH_LONG).show();
				} else if(callData.length == 1) {
					//如果之后又一个可用的号码，这直接使用这个号码拨出
					Intent intent = 
						new Intent(Intent.ACTION_SENDTO,Uri.parse("smsto://" + callData[0]));
					startActivity(intent);
				} else {
					initNumChooseDialog();
				}
				break;
			}
			case Menu.FIRST+2: {
				
				if(user.email.equals("")) {
					Toast.makeText(this, "没有可用的邮箱！", Toast.LENGTH_LONG).show();
				} else {
					Uri emailUri = Uri.parse("mailto:" + user.email);
					Intent intent = new Intent(Intent.ACTION_SENDTO, emailUri);
					startActivity(intent);
				}
				break;
			}
		
		}
		
		return super.onMenuItemSelected(featureId, item);
	}
	/**
	 * 装载头像
	 */
	public void loadImage() {
		if(imageChooseView == null) {
			LayoutInflater li = LayoutInflater.from(UserDetail.this);
			imageChooseView = li.inflate(R.layout.imageswitch, null);
			gallery = (Gallery)imageChooseView.findViewById(R.id.gallery);
			gallery.setAdapter(new ImageAdapter(this));
			gallery.setSelection(images.length/2);
			is = (ImageSwitcher)imageChooseView.findViewById(R.id.imageswitch);
			is.setFactory(this);
			gallery.setOnItemSelectedListener(new OnItemSelectedListener(){

				
				public void onItemSelected(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					// TODO Auto-generated method stub
					currentImagePosition = arg2 % images.length;
					is.setImageResource(images[arg2 % images.length]);
					
				}

				
				public void onNothingSelected(AdapterView<?> arg0) {
					
				}});
		}
		
	}
	
	public void initNumChooseDialog() {
		if(numChooseDialog == null) {
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			LayoutInflater inflater = LayoutInflater.from(this);
			numChooseView = inflater.inflate(R.layout.numchoose, null);
			ListView lv = (ListView)numChooseView.findViewById(R.id.num_list);
		    ArrayAdapter array = 
		        	new ArrayAdapter(this,android.R.layout.simple_list_item_1,callData);
		    lv.setAdapter(array);
		    lv.setOnItemClickListener(new OnItemClickListener(){
				
				public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
						long arg3) {
					String num = String.valueOf(arg0.getItemAtPosition(arg2));
					Intent intent = null;
					if(status.equals(Intent.ACTION_CALL)) {
						intent = new Intent(Intent.ACTION_CALL,Uri.parse("tel://" + num));
					} else {
						intent = new Intent(Intent.ACTION_SENDTO,Uri.parse("smsto://" + num));
					}
					
					startActivity(intent);
					//对话框消失
					numChooseDialog.dismiss();
				}});
		    
		    
			builder.setView(numChooseView);
			numChooseDialog = builder.create();
			
		}
		numChooseDialog.show();
	}
	
	public void initImageChooseDialog() {
		if(imageChooseDialog == null) {
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("请选择图像")
			.setView(imageChooseView).setPositiveButton("确定", new DialogInterface.OnClickListener() {
				
				public void onClick(DialogInterface dialog, int which) {
					imageChanged = true;
					previousImagePosition = currentImagePosition;
					imageButton.setImageResource(images[currentImagePosition%images.length]);
				}
			})
			.setNegativeButton("取消", new DialogInterface.OnClickListener() {
				
				
				public void onClick(DialogInterface dialog, int which) {
					currentImagePosition = previousImagePosition;
					
				}
			});
			imageChooseDialog = builder.create();
		}
	}
	/**
	 * 装载可用的号码
	 */
	public void loadAvailableCallData() {
		ArrayList<String> callNums = new ArrayList<String>();
		if(!user.mobilePhone.equals("")) {
			callNums.add(user.mobilePhone);
		}
		if(!user.familyPhone.equals("")) {
			callNums.add(user.familyPhone);
		}
		
		if(!user.officePhone.equals("")) {
			callNums.add(user.officePhone);
		}
		
		
		callData = new String[callNums.size()];
		
		for(int i=0;i<callNums.size();i++) {
			callData[i] = callNums.get(i);
		}
		
		
	}
	
	
	/**
	 * 自定义头像适配器
	 * @author Administrator
	 *
	 */
	class ImageAdapter extends BaseAdapter {

		private Context context;
		
		public ImageAdapter(Context context) {
			this.context = context;
		}
		
		
		public int getCount() {
			// TODO Auto-generated method stub
			return Integer.MAX_VALUE;
		}

		
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		
		/**
		 * gallery从这个方法中拿到image
		 */
		
		public View getView(int position, View convertView, ViewGroup parent) {
			ImageView iv = new ImageView(context);
			iv.setImageResource(images[position%images.length]);
			iv.setAdjustViewBounds(true);
			iv.setLayoutParams(new Gallery.LayoutParams(80,80));
			iv.setPadding(15, 10, 15, 10);
			return iv;
		}
		
	}

	
	public View makeView() {
		ImageView view = new ImageView(this);
		view.setBackgroundColor(0xff000000);
		view.setScaleType(ScaleType.FIT_CENTER);
		view.setLayoutParams(new ImageSwitcher.LayoutParams(90,90));
		return view;
	}
	/**
	 * 当退出的时候，回收资源
	 */
	
	protected void onDestroy() {
		if(is != null) {
			is = null;
		}
		if(gallery != null) {
			gallery = null;
		}
		if(imageChooseDialog != null) {
			imageChooseDialog = null;
		}
		if(imageChooseView != null) {
			imageChooseView = null;
		}
		if(imageButton != null) {
			imageButton = null;
		}
		if(numChooseDialog != null) {
			numChooseDialog = null;
		}
		if(numChooseView != null) {
			numChooseView = null;
		}
		
		super.onDestroy();
	}
}