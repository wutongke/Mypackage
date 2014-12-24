package com.xiangxm.checkpackage;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.app.ActionBar;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.zxing.WriterException;
import com.xiangxm.DB.DBHelper;
import com.xiangxm.DB.OrderDB;
import com.xiangxm.cls.Order;
import com.xiangxm.utils.Constants;
import com.xiangxm.utils.LoginConstant;
import com.zxing.encoding.EncodingHandler;

public class MySendActivity extends Activity {

	private Order order = new Order();
	private Button sender;
	private Button receive;
	public static final String FORPERSON = "forPerson";
	public static final int FORPERSONSEND = 1;
	public static final int FORPERSONRECEIVE = 2;
	private Button weightP;
	private Button weightM;
	private TextView weight;
	private Spinner volume;
	private Spinner goodCompany;
	private EditText goodType;
	private TextView timeSort;
	private TextView costSort;
	private TextView markSrot;
	private TextView chestNum1;
	private TextView chestNum2;
	private Spinner costType;
	private Button sendTime1;
	private Button sendTime2;
	private Button sendTime3;
	private TextView sendTimeView;
	private EditText otherInfo;
	private Button createOrder;
	private CheckBox sendMmessageCheckBox;
	private CheckBox receiveMessageCheckBox;
	private Button twoCode;
	private ImageView twoCodeImageView;
	private StringBuilder sendDate;
	private StringBuilder sendTime;
	private static final int requestForSender = 1;
	private static final int requestForReceive = 2;
	private static final int requestForChest = 3;

	//
	private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	private static final int SHOW_DATAPICK = 0;
	private static final int DATE_DIALOG_ID = 1;
	private static final int SHOW_TIMEPICK = 2;
	private static final int TIME_DIALOG_ID = 3;

	private int mYear;
	private int mMonth;
	private int mDay;
	private int mHour;
	private int mMinute;

	private void initView() {
		// TODO Auto-generated method stub
		sender = (Button) findViewById(R.id.send_person);
		receive = (Button) findViewById(R.id.reveive_person);
		weightP = (Button) findViewById(R.id.send_weightP);
		weightM = (Button) findViewById(R.id.send_weightm);
		weight = (TextView) findViewById(R.id.send_orderweight);
		volume = (Spinner) findViewById(R.id.send_ordervolume);
		goodType = (EditText) findViewById(R.id.send_ordertype);
		goodCompany = (Spinner) findViewById(R.id.send_company);
		timeSort = (TextView) findViewById(R.id.send_timesort);
		costSort = (TextView) findViewById(R.id.send_costsort);
		markSrot = (TextView)findViewById(R.id.send_commentsort);
		costType = (Spinner) findViewById(R.id.send_ordercosttype);
		sendTime1 = (Button) findViewById(R.id.send_ordersendtype1);
		sendTime2 = (Button) findViewById(R.id.send_ordersendtype2);
		sendTime3 = (Button) findViewById(R.id.send_ordersendtype3);
		sendTimeView = (TextView) findViewById(R.id.send_ordersendtime);
		otherInfo = (EditText) findViewById(R.id.send_otherinfo);
		createOrder = (Button) findViewById(R.id.ordertodingdan);
		sendMmessageCheckBox = (CheckBox) findViewById(R.id.ordersendtomessage);
		receiveMessageCheckBox = (CheckBox) findViewById(R.id.orderreceivetomessage);
		twoCode = (Button) findViewById(R.id.ordertocode);
		twoCodeImageView = (ImageView) findViewById(R.id.two_code_view);
		chestNum2 = (TextView)findViewById(R.id.send_chest);
		chestNum1 = (TextView)findViewById(R.id.send_chest_num);
	}

	private void initButton() {
		// TODO Auto-generated method stub
		sender.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MySendActivity.this,
						ContactActivity.class);
				intent.putExtra(FORPERSON, FORPERSONSEND);
				startActivityForResult(intent, requestForSender);
			}
		});
		receive.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MySendActivity.this,
						ContactActivity.class);
				intent.putExtra(FORPERSON, FORPERSONRECEIVE);
				startActivityForResult(intent, requestForReceive);
			}
		});
		weightP.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				int tempWeight = Integer.valueOf(weight.getText().toString()) + 1;
				weight.setText(tempWeight + "");
			}
		});
		weightM.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				int tempWeight = Integer.valueOf(weight.getText().toString()) - 1;
				weight.setText(tempWeight + "");
			}
		});
		sendTime1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				sendTimeView.setText(dateFormat.format(new Date()));
			}
		});
		sendTime2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				setDateTime();
				Message msg = new Message();
				msg.what = SHOW_DATAPICK;
				MySendActivity.this.dateandtimeHandler.sendMessage(msg);
			}
		});
		sendTime3.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				setDateTime();
				Message msg = new Message();
				msg.what = SHOW_TIMEPICK;
				MySendActivity.this.dateandtimeHandler.sendMessage(msg);
			}
		});

		createOrder.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (sender.getText().toString().equals("")
						|| receive.getText().toString().equals("")
						|| weight.getText().toString().equals("")
						|| goodType.getText().toString().equals("")
						|| sendTimeView.getText().toString().equals("")
						|| chestNum1.getText().toString().equals("")
						||	chestNum2.getText().toString().equals("")
						) {
					Toast.makeText(MySendActivity.this, "请填写完整信息",
							Toast.LENGTH_SHORT).show();
					return;
				}

				order.sender = sender.getText().toString();
				order.receiver = receive.getText().toString();
				order.weight = Integer.valueOf(weight.getText().toString());
				order.content = goodType.getText().toString();
				order.volume = volume.getSelectedItem().toString();
				order.company = goodCompany.getSelectedItem().toString();
				order.type = costType.getSelectedItem().toString();
				order.time = sendTimeView.getText().toString();
				order.otherinfo = otherInfo.getText().toString();
				order.chestNum1 = chestNum1.getText().toString();
				order.chestNum2 = chestNum2.getText().toString();
				order.cost = 15 + "";
				if (sendMmessageCheckBox.isChecked()) {
					order.message = 1;
				} else {
					order.message = 2;
				}
				if (receiveMessageCheckBox.isChecked()) {
					order.receiveMessage = "1";
				} else {
					order.receiveMessage = "2";
				}
				OrderDB myOrderHelper = new OrderDB(MySendActivity.this);
				myOrderHelper.openDatabase();
				if (myOrderHelper.insert(order)) {
					Toast.makeText(MySendActivity.this, "订单生成",
							Toast.LENGTH_SHORT).show();
					Intent intent = new Intent(MySendActivity.this,
							SubmitOrderActivity.class);
					intent.putExtra(
							Constants.ORDERID,
							myOrderHelper.getOrderByNumber(order.number + "")._id);
					startActivity(intent);
				} else {
					Toast.makeText(MySendActivity.this, "订单生成失败",
							Toast.LENGTH_SHORT).show();
				}

			}
		});
		twoCode.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				try {
					Bitmap qrCodeBitmap = EncodingHandler.createQRCode(
							order.number, 350);
					twoCodeImageView.setImageBitmap(qrCodeBitmap);
					twoCodeImageView.setVisibility(View.VISIBLE);
				} catch (WriterException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		});
		findViewById(R.id.illegalorder).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						Intent intent = new Intent(MySendActivity.this,
								IllegalGoodsActivity.class);
						startActivity(intent);
					}
				});
		findViewById(R.id.send_timesortbtn).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						timeSort.setText("中通，圆通，申通，汇通，天天，韵达，全峰，EMS");
					}
				});
		findViewById(R.id.send_costsortbtn).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						costSort.setText("申通，圆通，汇通，天天，EMS，中通，韵达，全峰");
					}
				});
		findViewById(R.id.send_commentsortbtn).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						markSrot.setText("EMS，申通，中通，圆通，，全峰天天，汇通，韵达");
					}
				});
		findViewById(R.id.send_chest_choose).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intentForChoose = new Intent(MySendActivity.this,ChestActivity.class);
				startActivityForResult(intentForChoose, requestForChest);
			}
		});

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_send);
		if (!LoginConstant.isLogin) {
			Toast.makeText(MySendActivity.this, "请先登录", Toast.LENGTH_SHORT)
					.show();
			Intent intent = new Intent(MySendActivity.this, LoginActivity.class);
			startActivity(intent);
			this.finish();
		}
		ActionBar mainBar = getActionBar();
		mainBar.setTitle("发快递");

		initView();
		order.number = System.currentTimeMillis() + "";
		sender.setFocusable(true);
		sender.setFocusableInTouchMode(true);
		sender.requestFocus();
		initButton();
		setDateTime();
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
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case requestForSender:
			if (data != null) {
				sender.setText(data.getStringExtra(Constants.PERSONINFO));
			}

			break;
		case requestForReceive:
			if (data != null) {
				receive.setText(data.getStringExtra(Constants.PERSONINFO));
			}
			break;
		case requestForChest:
			if(data!=null){
				chestNum2.setText(data.getStringExtra("chest"));
			}
		default:
			break;
		}
	}

	/**
	 * 初始化日期时间
	 */
	private void setDateTime() {
		final Calendar c = Calendar.getInstance();

		mYear = c.get(Calendar.YEAR);
		mMonth = c.get(Calendar.MONTH);
		mDay = c.get(Calendar.DAY_OF_MONTH);
		mHour = c.get(Calendar.HOUR_OF_DAY);
		mMinute = c.get(Calendar.MINUTE);
		updateTimeDisplay();
		updateDateDisplay();
	}

	/**
	 * 更新日期显示
	 */
	private void updateDateDisplay() {
		sendDate = new StringBuilder().append(mYear).append("-")
				.append((mMonth + 1) < 10 ? "0" + (mMonth + 1) : (mMonth + 1))
				.append("-").append((mDay < 10) ? "0" + mDay : mDay);
		sendTimeView.setText(sendDate + " " + sendTime);
	}

	/**
	 * 日期控件的事件
	 */
	private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {

		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			mYear = year;
			mMonth = monthOfYear;
			mDay = dayOfMonth;
			updateDateDisplay();
		}
	};

	/**
	 * 更新时间显示
	 */
	private void updateTimeDisplay() {
		sendTime = new StringBuilder().append(mHour).append(":")
				.append((mMinute < 10) ? "0" + mMinute : mMinute);
		sendTimeView.setText(sendDate + " " + sendTime);
	}

	/**
	 * 时间控件事件
	 */
	private TimePickerDialog.OnTimeSetListener mTimeSetListener = new TimePickerDialog.OnTimeSetListener() {

		@Override
		public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
			mHour = hourOfDay;
			mMinute = minute;

			updateTimeDisplay();
		}
	};

	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case DATE_DIALOG_ID:
			return new DatePickerDialog(this, mDateSetListener, mYear, mMonth,
					mDay);
		case TIME_DIALOG_ID:
			return new TimePickerDialog(this, mTimeSetListener, mHour, mMinute,
					true);
		}

		return null;
	}

	@Override
	protected void onPrepareDialog(int id, Dialog dialog) {
		switch (id) {
		case DATE_DIALOG_ID:
			((DatePickerDialog) dialog).updateDate(mYear, mMonth, mDay);
			break;
		case TIME_DIALOG_ID:
			((TimePickerDialog) dialog).updateTime(mHour, mMinute);
			break;
		}
	}

	/**
	 * 处理日期和时间控件的Handler
	 */
	Handler dateandtimeHandler = new Handler() {

		@SuppressWarnings("deprecation")
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case MySendActivity.SHOW_DATAPICK:
				showDialog(DATE_DIALOG_ID);
				break;
			case MySendActivity.SHOW_TIMEPICK:
				showDialog(TIME_DIALOG_ID);
				break;
			}
		}

	};

}
