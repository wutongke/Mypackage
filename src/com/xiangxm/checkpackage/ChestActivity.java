package com.xiangxm.checkpackage;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class ChestActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_chest);
		final Button button1 = (Button) findViewById(R.id.btn1);
		button1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(ChestActivity.this, "已使用，请选择其他", Toast.LENGTH_SHORT).show();
			}
		});
		final Button button2 = (Button) findViewById(R.id.btn2);
		button2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent  = new Intent();
				intent.putExtra("chest", button2.getText().toString());
				setResult(RESULT_OK, intent);
				ChestActivity.this.finish();
			}
		});
		final Button button3 = (Button) findViewById(R.id.btn3);
		button3.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent  = new Intent();
				intent.putExtra("chest", button3.getText().toString());
				setResult(RESULT_OK, intent);
				ChestActivity.this.finish();
			}
		});
		final Button button4 = (Button) findViewById(R.id.btn4);
		button4.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent  = new Intent();
				intent.putExtra("chest", button4.getText().toString());
				setResult(RESULT_OK, intent);
				ChestActivity.this.finish();
			}
		});
		final Button button5 = (Button) findViewById(R.id.btn5);
		button5.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(ChestActivity.this, "已使用，请选择其他", Toast.LENGTH_SHORT).show();
			}
		});
		final Button button6= (Button) findViewById(R.id.btn6);
		button6.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent  = new Intent();
				intent.putExtra("chest", button6.getText().toString());
				setResult(RESULT_OK, intent);
				ChestActivity.this.finish();
			}
		});
		final Button button7 = (Button) findViewById(R.id.btn7);
		button7.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent  = new Intent();
				intent.putExtra("chest", button7.getText().toString());
				setResult(RESULT_OK, intent);
				ChestActivity.this.finish();
			}
		});
		final Button button8 = (Button) findViewById(R.id.btn8);
		button8.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(ChestActivity.this, "已使用，请选择其他", Toast.LENGTH_SHORT).show();
			}
		});
		final Button button9 = (Button) findViewById(R.id.btn9);
		button9.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(ChestActivity.this, "已使用，请选择其他", Toast.LENGTH_SHORT).show();
			}
		});
		final Button button10 = (Button) findViewById(R.id.btn10);
		button10.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent  = new Intent();
				intent.putExtra("chest", button10.getText().toString());
				setResult(RESULT_OK, intent);
				ChestActivity.this.finish();
			}
		});
		final Button button11 = (Button) findViewById(R.id.btn11);
		button11.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent  = new Intent();
				intent.putExtra("chest", button11.getText().toString());
				setResult(RESULT_OK, intent);
				ChestActivity.this.finish();
			}
		});
		final Button button12 = (Button) findViewById(R.id.btn12);
		button12.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(ChestActivity.this, "已使用，请选择其他", Toast.LENGTH_SHORT).show();
			}
		});
		final Button button13 = (Button) findViewById(R.id.btn13);
		button13.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(ChestActivity.this, "已使用，请选择其他", Toast.LENGTH_SHORT).show();
			}
		});
		final Button button14 = (Button) findViewById(R.id.btn14);
		button14.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent  = new Intent();
				intent.putExtra("chest", button14.getText().toString());
				setResult(RESULT_OK, intent);
				ChestActivity.this.finish();
			}
		});
		final Button button15 = (Button) findViewById(R.id.btn15);
		button15.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent  = new Intent();
				intent.putExtra("chest", button15.getText().toString());
				setResult(RESULT_OK, intent);
				ChestActivity.this.finish();
			}
		});
		final Button button16 = (Button) findViewById(R.id.btn16);
		button16.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(ChestActivity.this, "已使用，请选择其他", Toast.LENGTH_SHORT).show();
			}
		});
		final Button button17= (Button) findViewById(R.id.btn17);
		button17.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(ChestActivity.this, "已使用，请选择其他", Toast.LENGTH_SHORT).show();
			}
		});
		final Button button18 = (Button) findViewById(R.id.btn18);
		button18.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent  = new Intent();
				intent.putExtra("chest", button18.getText().toString());
				setResult(RESULT_OK, intent);
				ChestActivity.this.finish();
			}
		});
		final Button button19 = (Button) findViewById(R.id.btn19);
		button19.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent  = new Intent();
				intent.putExtra("chest", button19.getText().toString());
				setResult(RESULT_OK, intent);
				ChestActivity.this.finish();
			}
		});
		final Button button20 = (Button) findViewById(R.id.btn20);
		button20.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent  = new Intent();
				intent.putExtra("chest", button20.getText().toString());
				setResult(RESULT_OK, intent);
				ChestActivity.this.finish();
			}
		});
		final Button button21 = (Button) findViewById(R.id.btn21);
		button21.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(ChestActivity.this, "已使用，请选择其他", Toast.LENGTH_SHORT).show();
			}
		});
		final Button button22 = (Button) findViewById(R.id.btn22);
		button22.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent  = new Intent();
				intent.putExtra("chest", button22.getText().toString());
				setResult(RESULT_OK, intent);
				ChestActivity.this.finish();
			}
		});
		final Button button23 = (Button) findViewById(R.id.btn23);
		button23.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(ChestActivity.this, "已使用，请选择其他", Toast.LENGTH_SHORT).show();
			}
		});
		final Button button24 = (Button) findViewById(R.id.btn24);
		button24.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(ChestActivity.this, "已使用，请选择其他", Toast.LENGTH_SHORT).show();
			}
		});
		final Button button25 = (Button) findViewById(R.id.btn25);
		button25.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent  = new Intent();
				intent.putExtra("chest", button25.getText().toString());
				setResult(RESULT_OK, intent);
				ChestActivity.this.finish();
			}
		});
		final Button button26 = (Button) findViewById(R.id.btn26);
		button26.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent  = new Intent();
				intent.putExtra("chest", button26.getText().toString());
				setResult(RESULT_OK, intent);
				ChestActivity.this.finish();
			}
		});
		final Button button27 = (Button) findViewById(R.id.btn27);
		button27.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent  = new Intent();
				intent.putExtra("chest", button27.getText().toString());
				setResult(RESULT_OK, intent);
				ChestActivity.this.finish();
			}
		});
		final Button button28 = (Button) findViewById(R.id.btn28);
		button28.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent  = new Intent();
				intent.putExtra("chest", button28.getText().toString());
				setResult(RESULT_OK, intent);
				ChestActivity.this.finish();
			}
		});
		final Button button29 = (Button) findViewById(R.id.btn29);
		button29.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent  = new Intent();
				intent.putExtra("chest", button29.getText().toString());
				setResult(RESULT_OK, intent);
				ChestActivity.this.finish();
			}
		});
		final Button button30 = (Button) findViewById(R.id.btn30);
		button30.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent  = new Intent();
				intent.putExtra("chest", button30.getText().toString());
				setResult(RESULT_OK, intent);
				ChestActivity.this.finish();
			}
		});
		final Button button31 = (Button) findViewById(R.id.btn31);
		button31.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(ChestActivity.this, "已使用，请选择其他", Toast.LENGTH_SHORT).show();
			}
		});
		final Button button32 = (Button) findViewById(R.id.btn32);
		button32.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent  = new Intent();
				intent.putExtra("chest", button32.getText().toString());
				setResult(RESULT_OK, intent);
				ChestActivity.this.finish();
			}
		});
	}
	
}
