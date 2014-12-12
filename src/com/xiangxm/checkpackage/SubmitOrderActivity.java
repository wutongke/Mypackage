package com.xiangxm.checkpackage;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.xiangxm.DB.OrderDB;
import com.xiangxm.cls.Order;
import com.xiangxm.utils.Constants;

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
	private 	Button payBtm;
	private Intent fromParentIntent;
	private Order order = new Order();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_ding);
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
		payBtm = (Button)findViewById(R.id.submit_ordertopay);
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
			
		}else{
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
}
