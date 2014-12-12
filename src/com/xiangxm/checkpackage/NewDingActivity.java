package com.xiangxm.checkpackage;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.xiangxm.DB.OrderDB;
import com.xiangxm.cls.Order;

public class NewDingActivity extends Activity {
	TextView orderName;
	TextView orderCompany;
	TextView orderNumber;
	TextView orderSender;
	TextView orderReceive;
	TextView orderContent;
	TextView orderOther;
	TextView orderCost;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_ding);
		Button ding = (Button) findViewById(R.id.dingbutton);
		orderName = (TextView) findViewById(R.id.ordername);
		orderCompany = (TextView) findViewById(R.id.ordercompany);
		orderNumber = (TextView) findViewById(R.id.ordernumber);
		orderSender = (TextView)findViewById(R.id.ordersender);
		orderReceive = (TextView)findViewById(R.id.orderreveive);
		orderContent = (TextView)findViewById(R.id.ordercontent);
		orderOther = (TextView)findViewById(R.id.orderother);
		orderCost = (TextView)findViewById(R.id.ordercost);
		ding.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (orderName.getText().toString().equals("")
						|| orderCompany.getText().toString().equals("")
						|| orderNumber.getText().toString().equals("")){
					Toast.makeText(NewDingActivity.this, "请填写完整", Toast.LENGTH_SHORT).show();
				}else{
					OrderDB myOrder = new OrderDB(NewDingActivity.this);
					myOrder.openDatabase();
					Order temp = new Order();
					temp.senderName = orderName.getText().toString();
					temp.receiverName = orderCompany.getText().toString();
					temp.number = orderNumber.getText().toString();
					temp.sender = orderSender.getText().toString();
					temp.receiver = orderReceive.getText().toString();
					temp.content = orderContent.getText().toString();
					temp.otherinfo = orderOther.getText().toString();
					temp.cost = orderCost.getText().toString();
					temp.isover = 0;
					temp.sendorreceive = 1;
					
					if(myOrder.insert(temp)){
						Toast.makeText(NewDingActivity.this, "保存成功", Toast.LENGTH_SHORT).show();
						NewDingActivity.this.finish();
					}else{
						Toast.makeText(NewDingActivity.this, "保存失败", Toast.LENGTH_SHORT).show();
					}
				}
			}
		});
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
