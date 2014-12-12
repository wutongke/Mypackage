package com.xiangxm.checkpackage;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.xiangxm.DB.OrderDB;
import com.xiangxm.cls.Order;
import com.xiangxm.utils.Constants;
import com.xiangxm.utils.LoginConstant;

public class SubmitOrderActivity extends Activity {
	private TextView sender;
	private TextView receive;
	private TextView weight;
	private TextView volume;
	private TextView sendTime;
	private TextView content;
	private TextView company;
	private TextView costType;
	private TextView otherInfo;
	private TextView messageInfo;
	private TextView cost;
	private Button payBtn;
	private Intent fromParentIntent;
	private Order order = new Order();
	private Handler uiHandler = new Handler();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_ding);
		ActionBar mainBar = getActionBar();
        mainBar.setDisplayHomeAsUpEnabled(true);
        mainBar.setTitle("返回");
		fromParentIntent = getIntent();
		
		sender = (TextView) findViewById(R.id.submit_ordersenderperson);
		receive = (TextView) findViewById(R.id.submit_orderreveiveperson);
		weight = (TextView) findViewById(R.id.submit_orderweight);
		volume = (TextView)findViewById(R.id.submit_ordervolume);
		content = (TextView)findViewById(R.id.submit_ordertype);
		company = (TextView)findViewById(R.id.submit_ordercompany);
		costType = (TextView)findViewById(R.id.submit_ordercosttype);
		otherInfo = (TextView)findViewById(R.id.submit_otherinfo);
		messageInfo = (TextView)findViewById(R.id.submit_message);
		cost = (TextView)findViewById(R.id.submit_cost);
		sendTime = (TextView)findViewById(R.id.submit_ordersendtime);
		payBtn = (Button)findViewById(R.id.submit_ordertopay);
		payBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				LinearLayout ll = (LinearLayout)getLayoutInflater().inflate(R.layout.alert_pay_layout,null);
				final TextView cost =(TextView) ll.findViewById(R.id.alert_cost);
				final EditText costpwd =(EditText) ll.findViewById(R.id.alert_pwd);
				final TextView costnum =(TextView) ll.findViewById(R.id.alert_number);
				cost.setText(order.cost);
				costnum.setText(LoginConstant.loginMobileNum.substring(0, 3)+"****"+LoginConstant.loginMobileNum.substring(7));
				
				new AlertDialog.Builder(SubmitOrderActivity.this).setTitle("支付宝手机支付密码")
				.setView(ll)
				.setCancelable(false)
				.setNegativeButton("取消",new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						Toast.makeText(SubmitOrderActivity.this, "付款取消", Toast.LENGTH_SHORT).show();
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
								final ProgressDialog dialog = ProgressDialog.show(SubmitOrderActivity.this, "Loading...", "Please wait...", true, false);  
								try {
									Thread.sleep(3000);
									dialog.cancel();
									OrderDB myOrderHelper = new OrderDB(SubmitOrderActivity.this);
									myOrderHelper.openDatabase();
									order.isover = 1;
									myOrderHelper.modify(order);
									
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								Toast.makeText(SubmitOrderActivity.this, "付款成功", Toast.LENGTH_SHORT).show();
							}
						});
						
					}
				}).create().show();
				
			}
		});
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		OrderDB myOrderHelper = new OrderDB(SubmitOrderActivity.this);
		myOrderHelper.openDatabase();
		if(fromParentIntent.getIntExtra(Constants.ORDERID, -1)!=-1){
			order = myOrderHelper.getOrderById(fromParentIntent.getIntExtra(Constants.ORDERID, -1));
			sender.setText(order.sender);
			receive.setText(order.receiver);
			weight.setText(order.weight+"");
			volume.setText(order.volume);
			sendTime.setText(order.time);
			content.setText(order.content);
			company.setText(order.company);
			costType.setText(order.type);
			otherInfo.setText(order.otherinfo);
			if(order.message==1){
				messageInfo.setText("是");
			}else{
				messageInfo.setText("否");
			}
			cost.setText("15元");
			
		}else if(!fromParentIntent.getStringExtra(Constants.ORDERNUMBER).equals("")){
			order = myOrderHelper.getOrderByNumber(fromParentIntent.getStringExtra(Constants.ORDERNUMBER));
			if (order!=null) {
				sender.setText(order.sender);
				receive.setText(order.receiver);
				weight.setText(order.weight + "");
				volume.setText(order.volume);
				sendTime.setText(order.time);
				content.setText(order.content);
				company.setText(order.company);
				costType.setText(order.type);
				otherInfo.setText(order.otherinfo);
				if (order.message == 1) {
					messageInfo.setText("是");
				} else {
					messageInfo.setText("否");
				}
				cost.setText("15元");
				payBtn.setVisibility(View.GONE);
			}else{
				Toast.makeText(SubmitOrderActivity.this, "未提交过的订单", Toast.LENGTH_SHORT).show();
			}
		}
		else{
			Toast.makeText(SubmitOrderActivity.this, "订单获取失败", Toast.LENGTH_SHORT).show();
		}
		
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
	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		// TODO Auto-generated method stub
		if(item.getItemId() == android.R.id.home){
			this.finish();
		}
       
     return true; 
	}
}
