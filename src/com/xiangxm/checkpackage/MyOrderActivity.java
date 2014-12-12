package com.xiangxm.checkpackage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.xiangxm.DB.OrderDB;
import com.xiangxm.cls.Order;

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
		orderAdapter = new ArrayAdapter<String>(MyOrderActivity.this,
				android.R.layout.simple_list_item_1, orderListData);
		orderListView.setAdapter(orderAdapter);
		orderListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				final int pos = position;
				new AlertDialog.Builder(MyOrderActivity.this).setTitle("确定订单？")
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						Toast.makeText(MyOrderActivity.this, "确认成功", Toast.LENGTH_SHORT).show();
					}
				}).setNegativeButton("取消", null).show();
			}
		});
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
			HashMap order = (HashMap) tempIterator.next();
			temp.add("订单名： "+order.get("name")+"\n订单号： "+order.get("number"));
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
