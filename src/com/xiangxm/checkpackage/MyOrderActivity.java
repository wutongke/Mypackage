package com.xiangxm.checkpackage;

import java.util.ArrayList;
import java.util.Iterator;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.xiangxm.DB.OrderDB;
import com.xiangxm.cls.Order;
import com.xiangxm.utils.Constants;
import com.xiangxm.utils.LoginConstant;

public class MyOrderActivity extends Activity {

	private ArrayList<String> orderListData = new ArrayList<String>();
	ArrayList<Order> tempOrder;
	OrderDB myOrder;
	private ArrayAdapter<String> orderAdapter;
	private ListView orderListView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_order);
		Intent fromParentIntent = getIntent();
		final Handler uiHandler = new Handler();
		orderAdapter = new ArrayAdapter<String>(MyOrderActivity.this,
				android.R.layout.simple_list_item_1, orderListData);
		orderListView = (ListView)findViewById(R.id.myorder_list);
		orderListView.setAdapter(orderAdapter);
		if (fromParentIntent.getStringExtra(Constants.MYRECEIVE)==null) {
			orderListView.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					// TODO Auto-generated method stub
					Intent intent = new Intent(MyOrderActivity.this,
							SubmitOrderActivity.class);
					intent.putExtra(Constants.ORDERNUMBER,
							tempOrder.get(position).number);
					startActivity(intent);
				}
			});
		}else{
			orderListView.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					// TODO Auto-generated method stub
					new AlertDialog.Builder(MyOrderActivity.this).setTitle("付费延期签收")
					.setCancelable(false)
					.setMessage("若您因事无法在36小时内签收该快件，可支付一定费用延长该快件在快递柜内的保留时间")
					.setNegativeButton("取消",new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							Toast.makeText(MyOrderActivity.this, "取消", Toast.LENGTH_SHORT).show();
						}
					})
					.setPositiveButton("付款", new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							LinearLayout ll = (LinearLayout)getLayoutInflater().inflate(R.layout.alert_pay_layout,null);
							final TextView cost =(TextView) ll.findViewById(R.id.alert_cost);
							final EditText costpwd =(EditText) ll.findViewById(R.id.alert_pwd);
							final TextView costnum =(TextView) ll.findViewById(R.id.alert_number);
							cost.setText("5");
							costnum.setText(LoginConstant.loginMobileNum.substring(0, 3)+"****"+LoginConstant.loginMobileNum.substring(7));
							
							new AlertDialog.Builder(MyOrderActivity.this).setTitle("支付宝手机支付密码")
							.setView(ll)
							.setCancelable(false)
							.setNegativeButton("取消",new DialogInterface.OnClickListener() {
								
								@Override
								public void onClick(DialogInterface dialog, int which) {
									// TODO Auto-generated method stub
									Toast.makeText(MyOrderActivity.this, "付款取消", Toast.LENGTH_SHORT).show();
								}
							})
							.setPositiveButton("付款", new DialogInterface.OnClickListener() {
								
								@Override
								public void onClick(DialogInterface dialog, int which) {
									// TODO Auto-generated method stub
									uiHandler.post(new Runnable() {
										
										@Override
										public void run() {
											// TODO Auto-generated method stub
											final ProgressDialog dialog = ProgressDialog.show(MyOrderActivity.this, "Loading...", "Please wait...", true, false);  
											try {
												Thread.sleep(1500);
												dialog.cancel();
												
											} catch (InterruptedException e) {
												// TODO Auto-generated catch block
												e.printStackTrace();
											}
											Toast.makeText(MyOrderActivity.this, "付款成功", Toast.LENGTH_SHORT).show();
										}
									});
									
								}
							}).create().show();
						}
					}).create().show();
				}
			});
		}
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		myOrder = new OrderDB(MyOrderActivity.this);
		myOrder.openDatabase();
		tempOrder = myOrder.getAllOrder();
		ArrayList<String> temp = new ArrayList<String>();
		Iterator tempIterator = tempOrder.iterator();
		while(tempIterator.hasNext()){
			Order order = (Order) tempIterator.next();
			temp.add("快递编号："+order.number+"\n发件人 :\n "+order.sender+"\n收件人:\n "+order.receiver);
		}
		orderListData.addAll(temp);
		orderAdapter.notifyDataSetInvalidated();
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		if(OrderDB.dbInstance != null) {
			OrderDB.dbInstance.close();
			OrderDB.dbInstance = null;
		}
	}
}
