package com.xiangxm.checkpackage;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.xiangxm.DB.OrderDB;
import com.xiangxm.utils.LoginConstant;
import com.zxing.activity.CaptureActivity;

public class MyReceiveActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_receive);
		if(!LoginConstant.isLogin){
			Toast.makeText(MyReceiveActivity.this, "请先登录", Toast.LENGTH_SHORT).show();
			Intent intent = new Intent(MyReceiveActivity.this,LoginActivity.class);
			startActivity(intent);
			this.finish();
		}
		LinearLayout queryNum = (LinearLayout)findViewById(R.id.receive_query_number);
		LinearLayout queryImg = (LinearLayout)findViewById(R.id.receive_query_image);
		EditText number = (EditText)findViewById(R.id.receive_number);
		queryImg.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent openCameraIntent = new Intent(MyReceiveActivity.this,CaptureActivity.class);
				startActivityForResult(openCameraIntent, 0);
			}
		});
	}
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		if (OrderDB.dbInstance != null) {
			OrderDB.dbInstance.close();
			OrderDB.dbInstance = null;
		}
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		//处理扫描结果（在界面上显示）
		if (resultCode == RESULT_OK) {
			Bundle bundle = data.getExtras();
			String scanResult = bundle.getString("result");
			Toast.makeText(MyReceiveActivity.this, scanResult, Toast.LENGTH_SHORT).show();
		}
	}
}
