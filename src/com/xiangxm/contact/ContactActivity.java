package com.xiangxm.contact;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.xiangxm.DB.DBHelper;
import com.xiangxm.checkpackage.R;
import com.xiangxm.cls.User;

public class ContactActivity extends Activity {
	
	//显示所有数据的ListView
	ListView lv;
	
	ArrayList list;
	
	//拥有所有数据的Adapter
	SimpleAdapter adapter;
	//屏幕下方的工具栏
	GridView bottomMenuGrid;
	//主菜单的布局
	GridView mainMenuGrid;
	//主菜单的视图
	View mainMenuView;
	//登录的视图
	View loginView;
	
	//装搜索框的linearlayout,默认情况下visibility=gone
	LinearLayout searchLinearout;
	LinearLayout mainLinearLayout;
	//搜索框
	EditText et_search;
	EditText et_enter_file_name;
	
	//主菜单的对话框
	AlertDialog mainMenuDialog;
	//确认对话框
	AlertDialog confirmDialog;
	//进度条对话框
	AlertDialog progressDialog;
	//输入文件名的对话框
	AlertDialog enterFileNameDialog;
	//输入用户名密码的对话框
	AlertDialog loginDialog;
	//表示保密状态
	boolean privacy = false;
	//存储标记的数目
	int markedNum;
	//存储标记条目的_id号
	ArrayList<Integer> deleteId;
	// 菜单文字 
	String[] main_menu_itemName = { "显示所有", "删除所有", "备份数据", "还原数据", "更新", "后退"};
	//主菜单图片
	int[] main_menu_itemSource = {
							   R.drawable.showall,
							   R.drawable.menu_delete,
							   R.drawable.menu_backup,
							   R.drawable.menu_restore,
							   R.drawable.menu_fresh,
							   R.drawable.menu_return};
	
	String[] bottom_menu_itemName = { "增加", "查找", "删除" };
	String fileName;
	int[] bottom_menu_itemSource = {
								R.drawable.menu_new_user,
								R.drawable.menu_search,
								R.drawable.menu_delete, 
								R.drawable.controlbar_showtype_list,
								R.drawable.menu_exit };
	
	
    /**
     * onCreate做的工作就是把listView显示出来
     * bottomMenuGrid，mainMenuGrid，searchLinearout都是到要用
     * 的时候再初始化，并且只初始化一次
     */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        ActionBar mainBar = getActionBar();
        mainBar.setDisplayHomeAsUpEnabled(true);
        mainBar.setTitle("返回");
        
        mainLinearLayout = (LinearLayout)findViewById(R.id.list_ll);
        DBHelper helper = new DBHelper(this);//获得所有用户的list
        helper.openDatabase(); //打开数据库，就打开这一次，因为Helper中的SQLiteDatabase是静态的。
        list = helper.getAllUser(privacy);//拿到所有保密状态为privacy的用户的list
        
        lv = (ListView)findViewById(R.id.lv_userlist); //创建ListView对象
        if(list.size() == 0) {
			Drawable nodata_bg = getResources().getDrawable(R.drawable.nodata_bg);
			mainLinearLayout.setBackgroundDrawable(nodata_bg);
			setTitle("没有查到任何数据");
        }
        //将数据与adapter集合起来
        adapter = new SimpleAdapter(this, 
									list, 
									R.layout.listitem, 
									new String[]{"imageid","name","mobilephone"}, 
									new int[]{R.id.user_image,R.id.tv_name,R.id.tv_mobilephone});
        
        lv.setAdapter(adapter);//将整合好的adapter交给listview，显示给用户看
        
      
        
        lv.setOnItemClickListener(new OnItemClickListener() {
        	/**
        	 * 响应单击事件，单点击某一个选项的时候，跳转到用户详细信息页面
        	 */
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				HashMap item = (HashMap)arg0.getItemAtPosition(arg2);
				int _id = Integer.parseInt(String.valueOf(item.get("_id")));
				
				Intent intent = new Intent(ContactActivity.this,UserDetail.class);
				User user = new User();
				user._id = Integer.parseInt(String.valueOf(item.get("_id")));
				user.address = String.valueOf(item.get("address"));
				user.company = String.valueOf(item.get("company"));
				user.email = String.valueOf(item.get("email"));
				user.familyPhone = String.valueOf(item.get("familyphone"));
				user.mobilePhone = String.valueOf(item.get("mobilephone"));
				user.officePhone = String.valueOf(item.get("officephone"));
				user.otherContact = String.valueOf(item.get("othercontact"));
				user.position = String.valueOf(item.get("position"));
				user.remark = String.valueOf(item.get("remark"));
				user.username = String.valueOf(item.get("name"));
				user.zipCode = String.valueOf(item.get("zipcode"));
				user.imageId = Integer.parseInt(String.valueOf(item.get("imageid")));
				
				intent.putExtra("user", user);
				
				if(searchLinearout != null && searchLinearout.getVisibility()==View.VISIBLE) {
					searchLinearout.setVisibility(View.GONE);
				}
				
				/*将arg2作为请求码传过去  用于标识修改项的位置*/
				startActivityForResult(intent, arg2);
			}
		});
       
        lv.setCacheColorHint(Color.TRANSPARENT); //设置ListView的背景为透明
        lv.setOnItemLongClickListener(new OnItemLongClickListener(){

			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				if(deleteId == null) {
					deleteId = new ArrayList<Integer>();
				}
				HashMap item = (HashMap)arg0.getItemAtPosition(arg2);
				Integer _id = Integer.parseInt(String.valueOf(item.get("_id")));
				
				RelativeLayout r = (RelativeLayout)arg1;
				ImageView markedView = (ImageView)r.getChildAt(2);
				if(markedView.getVisibility() == View.VISIBLE) {
					markedView.setVisibility(View.GONE);
					deleteId.remove(_id);
				} else {
					markedView.setVisibility(View.VISIBLE);
					deleteId.add(_id);
				}
				return true;
			}
        	
        	
        });
        //为list添加item选择器
        Drawable bgDrawable = getResources().getDrawable(R.drawable.list_bg);
        lv.setSelector(bgDrawable);
        
    }
    @Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
    	menu.add(0, 0,0, "菜单");
		return super.onCreateOptionsMenu(menu);
	}
	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		// TODO Auto-generated method stub
		if(item.getItemId() == android.R.id.home){
			this.finish();
		}
		 switch(item.getItemId()){  
         case 0:  
        	 loadBottomMenu();
 			if(bottomMenuGrid.getVisibility() == View.VISIBLE) {
 				if(searchLinearout != null && searchLinearout.getVisibility() == View.VISIBLE) {
 					searchLinearout.setVisibility(View.GONE);
 				}
 				bottomMenuGrid.setVisibility(View.GONE);
 			} else {
 				bottomMenuGrid.setVisibility(View.VISIBLE);
 			}  
             break;  
         default :break; 
       
     }  
       
     return true; 
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		//清除deleteId的内容
		if(deleteId != null) {
			deleteId.clear();
		}
		//当resultCode==3时代表添加了一个用户返回，当resultCode==4的时候代表修改了用户，或者删除了用户，其他条件代表数据没有变化
		if(resultCode == 3 || resultCode == 4) {
			DBHelper helper = new DBHelper(this);
	        list = helper.getAllUser(privacy);
	        adapter = 
	        	new SimpleAdapter(
	        					  this, 
	        					  list, 
	        					  R.layout.listitem, 
	        					  new String[]{"imageid","name","mobilephone"}, 
	        					  new int[]{R.id.user_image,R.id.tv_name,R.id.tv_mobilephone});
	        if(list.size() > 0){
	        	mainLinearLayout.setBackgroundDrawable(null);
	        }
		}
		
		lv.setAdapter(adapter); //将整合好的adapter交给listview，显示给用户看
		/**
		 * resultCode只有3、4、5 
		 * 当等于4或者5的时候，代表由UserDetail转过来的。在转想UserDetail的时候，requestCode的值设置的是选中项的位置
		 */
		if(resultCode == 3) {
			lv.setSelection(list.size());
		} else {
			lv.setSelection(requestCode);
		}
		
		
	}

	/**
	 * 响应点击Menu按钮时的事件，用于设置底部菜单是否可见
	 */
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode == KeyEvent.KEYCODE_MENU) {
			loadBottomMenu();
			if(bottomMenuGrid.getVisibility() == View.VISIBLE) {
				if(searchLinearout != null && searchLinearout.getVisibility() == View.VISIBLE) {
					searchLinearout.setVisibility(View.GONE);
				}
				bottomMenuGrid.setVisibility(View.GONE);
			} else {
				bottomMenuGrid.setVisibility(View.VISIBLE);
			}
		}
		return super.onKeyDown(keyCode, event);
	}
	
	
	private void loadBottomMenu() {
		
		if(bottomMenuGrid == null) {
			bottomMenuGrid = (GridView) findViewById(R.id.gv_buttom_menu);
			bottomMenuGrid.setBackgroundResource(R.drawable.channelgallery_bg);// 设置背景
			bottomMenuGrid.setNumColumns(5);// 设置每行列数
			bottomMenuGrid.setGravity(Gravity.CENTER);// 位置居中
			bottomMenuGrid.setVerticalSpacing(10);// 垂直间隔
			bottomMenuGrid.setHorizontalSpacing(10);// 水平间隔
			bottomMenuGrid.setAdapter(getMenuAdapter(bottom_menu_itemName,
					bottom_menu_itemSource));// 设置菜单Adapter
			/** 监听底部菜单选项 **/
			bottomMenuGrid.setOnItemClickListener(new OnItemClickListener() {
				public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
						long arg3) {
					
					switch (arg2) {
						case 0: {
							if(searchLinearout != null && searchLinearout.getVisibility()==View.VISIBLE) {
								searchLinearout.setVisibility(View.GONE);
							}
							if(bottomMenuGrid.getVisibility() == View.VISIBLE) {
								bottomMenuGrid.setVisibility(View.GONE);
							}
							
							Intent intent = new Intent(ContactActivity.this,AddNew.class);
							startActivityForResult(intent, 3);
							break;
						}
							
						case 1:
							loadSearchLinearout();
							if(searchLinearout.getVisibility()==View.VISIBLE) {
								searchLinearout.setVisibility(View.GONE);
							} else {
								searchLinearout.setVisibility(View.VISIBLE);
								et_search.requestFocus();
								et_search.selectAll();
							}
							break;
						case 2:
							if(searchLinearout != null && searchLinearout.getVisibility()==View.VISIBLE) {
								searchLinearout.setVisibility(View.GONE);
							}
							if(deleteId == null || deleteId.size() == 0) {
								Toast.makeText(ContactActivity.this, "    没有标记任何记录\n长按一条记录即可标记", Toast.LENGTH_LONG).show();
							} else {
								new AlertDialog.Builder(ContactActivity.this)
								.setTitle("确定要删除标记的"+deleteId.size()+"条记录吗?")
								.setPositiveButton("确定", new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog, int which) {
										DBHelper helper = new DBHelper(ContactActivity.this);
								        helper.deleteMarked(deleteId);
								        //重置视图
								        list = helper.getAllUser(privacy);
								        adapter = 
								        	new SimpleAdapter(
								        					  ContactActivity.this, 
								        					  list, 
								        					  R.layout.listitem, 
								        					  new String[]{"imageid","name","mobilephone"}, 
								        					  new int[]{R.id.user_image,R.id.tv_name,R.id.tv_mobilephone});
								        lv.setAdapter(adapter);
								        deleteId.clear();
									}
								})
								.setNegativeButton("取消", null)
								.create()
								.show()	;
							}
							
							break;
//						case 3:
//							if(searchLinearout != null && searchLinearout.getVisibility()==View.VISIBLE) {
//								searchLinearout.setVisibility(View.GONE);
//							}
//							loadMainMenuDialog();
//							mainMenuDialog.show();
//							
//							break;
//						case 4:
//							finish();
//							break;
					}
				}
			});
		}
			
	}
	
	private void loadMainMenuDialog() {
		if(mainMenuDialog == null) {
			LayoutInflater li = LayoutInflater.from(this);
			mainMenuView = li.inflate(R.layout.main_menu_grid, null);
			 //根据主菜单视图，创建主菜单对话框
	        mainMenuDialog = new AlertDialog.Builder(this).setView(mainMenuView).create();
	        //根据主菜单视图，拿到视图文件中的GridView，然后再往里面放Adapter
	        mainMenuGrid = (GridView)mainMenuView.findViewById(R.id.gridview);
	        SimpleAdapter menuAdapter = getMenuAdapter(main_menu_itemName, main_menu_itemSource);
	        mainMenuGrid.setAdapter(menuAdapter);
	        //响应点击事件
	        mainMenuGrid.setOnItemClickListener(new OnItemClickListener(){
				public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
						long arg3) {
					switch(arg2){
						case 0:{
							 DBHelper helper = new DBHelper(ContactActivity.this);
							 list = helper.getAllUser(privacy);
							 adapter = new SimpleAdapter(
		        					  ContactActivity.this, 
		        					  list, 
		        					  R.layout.listitem, 
		        					  new String[]{"imageid","name","mobilephone"}, 
		        					  new int[]{R.id.user_image,R.id.tv_name,R.id.tv_mobilephone});

							 lv.setAdapter(adapter);//显示所有数据
							 mainMenuDialog.dismiss();
							break;
						}
						case 1:{
							AlertDialog.Builder builder  = new AlertDialog.Builder(ContactActivity.this);
							confirmDialog = builder.create();
							builder.setTitle("是否删除所有！?");
							builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog, int which) {
										DBHelper helper = new DBHelper(ContactActivity.this);
								        helper.deleteAll(0);
										 list = helper.getAllUser(privacy);
										 adapter = new SimpleAdapter(
					        					  ContactActivity.this, 
					        					  list, 
					        					  R.layout.listitem, 
					        					  new String[]{"imageid","name","mobilephone"}, 
					        					  new int[]{R.id.user_image,R.id.tv_name,R.id.tv_mobilephone});

										 lv.setAdapter(adapter);//显示所有数据
								        mainMenuDialog.dismiss();
									}
								});
							builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog, int which) {
										confirmDialog.dismiss();
									}
								});
							builder.create().show();
							break;				
						}
						case 2:{
							mainMenuDialog.dismiss();
							new AlertDialog.Builder(ContactActivity.this)
							.setTitle("是否需要备份记录到SD卡？")
							.setPositiveButton("确定", new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog, int which) {
								LayoutInflater li = LayoutInflater.from(ContactActivity.this);
								View backup_view = li.inflate(R.layout.backup_progress, null);
								progressDialog =	new AlertDialog.Builder(ContactActivity.this)
								.setTitle("备份正在进行中...")
								.setView(backup_view)
								.create();
								progressDialog.show();
								DBHelper helper = new DBHelper(ContactActivity.this);
								helper.backupData(privacy);
								ProgressBar bar = (ProgressBar) backup_view.findViewById(R.id.pb_backup);
								Button btn_backup_ok = (Button)backup_view.findViewById(R.id.btn_backuup_ok);
								bar.setMax(list.size());
								for(int i=0;i<=list.size();i++) {
									bar.setProgress(i);
								}
								progressDialog.setTitle("备份完成！一共 "+ list.size() + " 条记录");
								btn_backup_ok.setVisibility(View.VISIBLE);
								btn_backup_ok.setOnClickListener(new OnClickListener() {

									public void onClick(View v) {
										progressDialog.dismiss();
										mainMenuDialog.dismiss();
									}
									
								});
								}
							
							})
							.setNegativeButton("取消", null)
							.create()
							.show()	;
							break;
						}
						case 3:{
							LayoutInflater li = LayoutInflater.from(ContactActivity.this);
							View enterFileNameView = li.inflate(R.layout.enterfilename, null);
							enterFileNameDialog =	new AlertDialog.Builder(ContactActivity.this)
							.setView(enterFileNameView).setNegativeButton("取消", null)
							.setPositiveButton("确定", new DialogInterface.OnClickListener() {
								
								public void onClick(DialogInterface dialog, int which) {
									DBHelper helper = new DBHelper(ContactActivity.this);
									fileName = et_enter_file_name.getText().toString();
									if(helper.findFile(fileName)){
										new AlertDialog.Builder(ContactActivity.this).setTitle("请选择方式")
										.setPositiveButton("覆盖", new DialogInterface.OnClickListener() {
											
											
											public void onClick(DialogInterface dialog, int which) {
												DBHelper helper = new DBHelper(ContactActivity.this);
												helper.deleteAll(0);
												helper.restoreData(fileName);
												list = helper.getAllUser(privacy);
												adapter = new SimpleAdapter(ContactActivity.this, 
														list, 
														R.layout.listitem, 
														new String[]{"imageid","name","mobilephone"}, 
														new int[]{R.id.user_image,R.id.tv_name,R.id.tv_mobilephone});
												LayoutInflater li = LayoutInflater.from(ContactActivity.this);
												View backup_view = li.inflate(R.layout.backup_progress, null);
												progressDialog =	new AlertDialog.Builder(ContactActivity.this)
												.setTitle("正在还原数据...")
												.setView(backup_view)
												.create();
												progressDialog.show();
												ProgressBar bar = (ProgressBar) backup_view.findViewById(R.id.pb_backup);
											
												Button btn_backup_ok = (Button)backup_view.findViewById(R.id.btn_backuup_ok);
												bar.setMax(list.size());
												for(int i=0;i<=list.size();i++) {
													bar.setProgress(i);
												}
												progressDialog.setTitle("还原完成！一共还原了 "+ list.size() + " 条记录");
												btn_backup_ok.setVisibility(View.VISIBLE);
												btn_backup_ok.setOnClickListener(new OnClickListener() {

													
													public void onClick(View v) {
														progressDialog.dismiss();
														mainMenuDialog.dismiss();
														if(list.size() != 0) {
															mainLinearLayout.setBackgroundDrawable(null);
														}
														lv.setAdapter(adapter);
														
													}
													
												});
											}
										})
										.setNegativeButton("添加", new DialogInterface.OnClickListener() {
											
											public void onClick(DialogInterface dialog, int which) {
												DBHelper helper = new DBHelper(ContactActivity.this);
												int preNum = list.size();
												helper.restoreData(fileName);
												list = helper.getAllUser(privacy);
												adapter = new SimpleAdapter(ContactActivity.this, 
														list, 
														R.layout.listitem, 
														new String[]{"imageid","name","mobilephone"}, 
														new int[]{R.id.user_image,R.id.tv_name,R.id.tv_mobilephone});
												LayoutInflater li = LayoutInflater.from(ContactActivity.this);
												View backup_view = li.inflate(R.layout.backup_progress, null);
												progressDialog =	new AlertDialog.Builder(ContactActivity.this)
												.setTitle("正在还原数据...")
												.setView(backup_view)
												.create();
												progressDialog.show();
												ProgressBar bar = (ProgressBar) backup_view.findViewById(R.id.pb_backup);
											
												Button btn_backup_ok = (Button)backup_view.findViewById(R.id.btn_backuup_ok);
												bar.setMax(list.size());
												for(int i=0;i<=list.size();i++) {
													bar.setProgress(i);
												}
												progressDialog.setTitle("还原完成！一共还原了 "+ (list.size()-preNum) + " 条记录");
												btn_backup_ok.setVisibility(View.VISIBLE);
												btn_backup_ok.setOnClickListener(new OnClickListener() {

													
													public void onClick(View v) {
														progressDialog.dismiss();
														mainMenuDialog.dismiss();
														lv.setAdapter(adapter);
													}
													
												});
											}
										})
										.setNeutralButton("取消", new DialogInterface.OnClickListener() {
											
											
											public void onClick(DialogInterface dialog, int which) {
												
											}
										}).create().show();
										
									} else {
										Toast.makeText(enterFileNameDialog.getContext(), "找不到备份文件", Toast.LENGTH_LONG).show();
									}
								}
							})
							.create();
							et_enter_file_name = (EditText)enterFileNameView.findViewById(R.id.et_enter_file_name);
							et_enter_file_name.setText("comm_data");
							et_enter_file_name.requestFocus();
							et_enter_file_name.selectAll();
							enterFileNameDialog.show();
							adapter = new SimpleAdapter(
		        					  ContactActivity.this, 
		        					  list, 
		        					  R.layout.listitem, 
		        					  new String[]{"imageid","name","mobilephone"}, 
		        					  new int[]{R.id.user_image,R.id.tv_name,R.id.tv_mobilephone});
	
							 lv.setAdapter(adapter);//显示所有数据
							mainMenuDialog.dismiss();
							break;
						}
						case 4:{
							mainMenuDialog.dismiss();
							new AlertDialog.Builder(ContactActivity.this)
							.setTitle("更新操作将需要支付20元的费用！是否继续？")
							.setPositiveButton("确定", new DialogInterface.OnClickListener() {
								
								public void onClick(DialogInterface dialog, int which) {
									//新建一个activity出来
									LayoutInflater li = LayoutInflater.from(ContactActivity.this);
									loginView = li.inflate(R.layout.login, null);
									
									Button btn_login_ok = (Button)loginView.findViewById(R.id.btn_login_ok);
									Button btn_login_cancel = (Button)loginView.findViewById(R.id.btn_login_cancel);
									final EditText et_account = (EditText)loginView.findViewById(R.id.et_account);
									final EditText et_password = (EditText)loginView.findViewById(R.id.et_password);
									btn_login_ok.setOnClickListener(new OnClickListener(){

										
										public void onClick(View v) {
											if(et_account.getText().toString().equals("admin") && et_password.getText().toString().equals("admin")) {
												et_account.setText("");
												et_password.setText("");
												loginDialog.dismiss();
												Intent intent = new Intent(ContactActivity.this,MainPrivacy.class);
												startActivity(intent);
												
											} else {
												Toast.makeText(ContactActivity.this, "失败", Toast.LENGTH_LONG).show();
											}
										}
										
									});
									btn_login_cancel.setOnClickListener(new OnClickListener(){

										
										public void onClick(View v) {
											loginDialog.dismiss();
										}
										
									});
									
									if(loginDialog == null) {
										loginDialog = new AlertDialog.Builder(ContactActivity.this).setView(loginView).create();
									}
									loginDialog.show();
									
									
								}
							})
							.setNegativeButton("取消", null)
							.create()
							.show()	;
							break;				
						}
						case 5:{
							mainMenuDialog.dismiss();
							break;
						}
					}
					
				}});
		}
		
	}

	private void loadSearchLinearout() {
		
		if(searchLinearout == null) {
			searchLinearout = (LinearLayout) findViewById(R.id.ll_search);
			et_search = (EditText)findViewById(R.id.et_search);
			et_search.setOnKeyListener(new OnKeyListener(){
				
				public boolean onKey(View arg0, int arg1, KeyEvent arg2) {
					String condition = et_search.getText().toString();
					if(condition.equals("")) {
						lv.setAdapter(adapter);
					}
					DBHelper helper = new DBHelper(ContactActivity.this);
					list = helper.getUsers(condition,privacy);
					SimpleAdapter searchAdapter = 
						new SimpleAdapter(
			        					ContactActivity.this, 
			        					list, 
			        					R.layout.listitem, 
			        					new String[]{"imageid","name","mobilephone"}, 
			        					new int[]{R.id.user_image,R.id.tv_name,R.id.tv_mobilephone});
					lv.setAdapter(searchAdapter);  //将整合好的adapter交给listview，显示给用户看
					if(list.size() == 0) {
						Drawable nodata_bg = getResources().getDrawable(R.drawable.nodata_bg);
						mainLinearLayout.setBackgroundDrawable(nodata_bg);
						setTitle("没有查到任何数据");
					} else {
						setTitle( "共查到 " + list.size()+" 条记录");
						
						mainLinearLayout.setBackgroundDrawable(null);
					}
					return false;
				}});
	       
		}
		  
	}

	private SimpleAdapter getMenuAdapter(String[] menuNameArray,
			int[] imageResourceArray) {
		ArrayList<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();
		for (int i = 0; i < menuNameArray.length; i++) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("itemImage", imageResourceArray[i]);
			map.put("itemText", menuNameArray[i]);
			data.add(map);
		}
		SimpleAdapter simperAdapter = 
				new SimpleAdapter(
								  this, 
								  data,
								  R.layout.item_menu, 
								  new String[] { "itemImage", "itemText" },
								  new int[] { R.id.item_image, R.id.item_text });
		return simperAdapter;
	}

	/**
	 * 当退出的时候，回收资源
	 */
	
	protected void onDestroy() {
		if(confirmDialog != null) {
			confirmDialog = null;
		}
		if(mainMenuDialog != null) {
			mainMenuDialog = null;
		}
		if(searchLinearout != null) {
			searchLinearout = null;
		}
		if(mainMenuView != null) {
			mainMenuView = null;
		}
		if(mainMenuGrid != null) {
			mainMenuGrid = null;
		}
		if(bottomMenuGrid != null) {
			bottomMenuGrid = null;
		}
		if(adapter != null) {
			adapter = null;
		}
		if(list != null) {
			list = null;
		}
		if(lv != null) {
			lv = null;
		}
		if(DBHelper.dbInstance != null) {
			DBHelper.dbInstance.close();
			DBHelper.dbInstance = null;
		}
		
		System.out.println("destory!!!");
		super.onDestroy();
	}

}