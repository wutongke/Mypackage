package com.xiangxm.DB;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;

import com.xiangxm.cls.Order;

public class OrderDB {

public static final String DB_DBNAME="order";
	
	public static final String DB_TABLENAME="myorder";
	
	public static final int VERSION = 1;
	
	public static SQLiteDatabase dbInstance; 
	
	private MyDBHelper myDBHelper;
	
	private StringBuffer tableCreate;
	
	private Context context;
	
	public OrderDB(Context context) {
		this.context = context;
	}
	
	public void openDatabase() {
		if(dbInstance == null) {
			myDBHelper = new MyDBHelper(context,DB_DBNAME,VERSION);
			dbInstance = myDBHelper.getWritableDatabase();
		}
	}
	/**
	 * 往数据库里面的user表插入一条数据，若失败返回-1
	 * @param user
	 * @return   失败返回-1
	 */
	public boolean insert(Order order) {
		ContentValues values = new ContentValues();
		values.put("senderName", order.chestNum1);
		values.put("senderNum", order.receiveMessage);
		values.put("sendAddress", order.chestNum2);
		values.put("receiverName", order.receiverName);
		values.put("receiverNum", order.receiverNum);
		values.put("receiverAddress", order.receiverAddress);
		values.put("weight", order.weight);
		values.put("volume", order.volume);
		values.put("type", order.type);
		values.put("company", order.company);
		values.put("time", order.time);
		values.put("otherinfo", order.otherinfo);
		values.put("two_code", order.two_code);
		values.put("message", order.message);
		values.put("number", order.number);
		values.put("content", order.content);
		values.put("sendorreceive", order.sendorreceive);
		values.put("cost", order.cost);
		values.put("sender", order.sender);
		values.put("receiver", order.receiver);
		values.put("isover", order.isover);
		
		
		if( dbInstance.insert(DB_TABLENAME, null, values)>0){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 获得数据库中所有的数据
	 * @param privacy 
	 * @return list
	 */
	
	public ArrayList<Order> getAllOrder() {
		ArrayList<Order> list = new ArrayList<Order>();
		Cursor cursor = null;
			cursor = dbInstance.query(DB_TABLENAME, 
					null, 
					null, 
					null, 
					null, 
					null, 
					null);
		
		while(cursor.moveToNext()) {
			@SuppressWarnings("rawtypes")
			Order item = new Order();
			item._id = cursor.getInt(cursor.getColumnIndex("_id"));
			item.chestNum1 = cursor.getString(cursor.getColumnIndex("senderName"));
			item.chestNum2 = cursor.getString(cursor.getColumnIndex("sendAddress"));
			item.receiveMessage = cursor.getString(cursor.getColumnIndex("senderNum"));
			item.receiverName = cursor.getString(cursor.getColumnIndex("receiverName"));
			item.receiverNum = cursor.getString(cursor.getColumnIndex("receiverNum"));
			item.receiverAddress = cursor.getString(cursor.getColumnIndex("receiverAddress"));
			item.weight = cursor.getInt(cursor.getColumnIndex("weight"));
			item.volume = cursor.getString(cursor.getColumnIndex("volume"));
			item.type = cursor.getString(cursor.getColumnIndex("type"));
			item.company = cursor.getString(cursor.getColumnIndex("company"));
			item.time = cursor.getString(cursor.getColumnIndex("time"));
			item.otherinfo = cursor.getString(cursor.getColumnIndex("otherinfo"));
			item.two_code = cursor.getString(cursor.getColumnIndex("two_code"));
			item.message = cursor.getInt(cursor.getColumnIndex("message"));
			item.number = cursor.getString(cursor.getColumnIndex("number"));
			item.content = cursor.getString(cursor.getColumnIndex("content"));
			item.sendorreceive = cursor.getInt(cursor.getColumnIndex("sendorreceive"));
			item.sender = cursor.getString(cursor.getColumnIndex("sender"));
			item.receiver = cursor.getString(cursor.getColumnIndex("receiver"));
			item.cost = cursor.getString(cursor.getColumnIndex("cost"));
			item.isover = cursor.getInt(cursor.getColumnIndex("isover"));
			list.add(item);
		}
		
		return list;
	}
	public ArrayList<Order> getAllOrder(int sendOrReceive) {
		ArrayList<Order> list = new ArrayList<Order>();
		Cursor cursor = null;
			cursor = dbInstance.query(DB_TABLENAME, 
					new String[]{"_id","name","company","number","time","content","cost","sender","receiver","otherinfo",
					"isover","sendorreceive"}, 
					"sendorreceive="+sendOrReceive, 
					null, 
					null, 
					null, 
					null);
		
		while(cursor.moveToNext()) {
			Order item = new Order();
			item._id = cursor.getInt(cursor.getColumnIndex("_id"));
			item.chestNum1 = cursor.getString(cursor.getColumnIndex("senderName"));
			item.chestNum2 = cursor.getString(cursor.getColumnIndex("sendAddress"));
			item.receiveMessage = cursor.getString(cursor.getColumnIndex("senderNum"));
			item.receiverName = cursor.getString(cursor.getColumnIndex("receiverName"));
			item.receiverNum = cursor.getString(cursor.getColumnIndex("receiverNum"));
			item.receiverAddress = cursor.getString(cursor.getColumnIndex("receiverAddress"));
			item.weight = cursor.getInt(cursor.getColumnIndex("weight"));
			item.volume = cursor.getString(cursor.getColumnIndex("volume"));
			item.type = cursor.getString(cursor.getColumnIndex("type"));
			item.company = cursor.getString(cursor.getColumnIndex("company"));
			item.time = cursor.getString(cursor.getColumnIndex("time"));
			item.otherinfo = cursor.getString(cursor.getColumnIndex("otherinfo"));
			item.two_code = cursor.getString(cursor.getColumnIndex("two_code"));
			item.message = cursor.getInt(cursor.getColumnIndex("message"));
			item.number = cursor.getString(cursor.getColumnIndex("number"));
			item.content = cursor.getString(cursor.getColumnIndex("content"));
			item.sendorreceive = cursor.getInt(cursor.getColumnIndex("sendorreceive"));
			item.sender = cursor.getString(cursor.getColumnIndex("sender"));
			item.receiver = cursor.getString(cursor.getColumnIndex("receiver"));
			item.cost = cursor.getString(cursor.getColumnIndex("cost"));
			item.isover = cursor.getInt(cursor.getColumnIndex("isover"));
			list.add(item);
		}
		
		return list;
	}
	
	public void modify(Order order) {
		ContentValues values = new ContentValues();
		values.put("senderName", order.chestNum1);
		values.put("senderNum", order.receiveMessage);
		values.put("sendAddress", order.chestNum2);
		values.put("receiverName", order.receiverName);
		values.put("receiverNum", order.receiverNum);
		values.put("receiverAddress", order.receiverAddress);
		values.put("weight", order.weight);
		values.put("volume", order.volume);
		values.put("type", order.type);
		values.put("company", order.company);
		values.put("time", order.time);
		values.put("otherinfo", order.otherinfo);
		values.put("two_code", order.two_code);
		values.put("message", order.message);
		values.put("number", order.number);
		values.put("content", order.content);
		values.put("sendorreceive", order.sendorreceive);
		values.put("sender", order.sender);
		values.put("receiver", order.receiver);
		values.put("cost", order.cost);
		values.put("isover", order.isover);
		
		dbInstance.update(DB_TABLENAME, values, "_id=?", new String[]{String.valueOf(order._id)});
	}
	/**
	 * 获取Order
	 * @param _id
	 */
	public Order getOrderById(int _id){
		Cursor cursor = null;
		cursor = dbInstance.query(DB_TABLENAME, 
				null, 
				"_id="+_id, 
				null, 
				null, 
				null, 
				null);
		if(cursor.moveToFirst()){
			Order item = new Order();
			item._id = cursor.getInt(cursor.getColumnIndex("_id"));
			item.chestNum1 = cursor.getString(cursor.getColumnIndex("senderName"));
			item.chestNum2 = cursor.getString(cursor.getColumnIndex("sendAddress"));
			item.receiveMessage = cursor.getString(cursor.getColumnIndex("senderNum"));
			item.receiverName = cursor.getString(cursor.getColumnIndex("receiverName"));
			item.receiverNum = cursor.getString(cursor.getColumnIndex("receiverNum"));
			item.receiverAddress = cursor.getString(cursor.getColumnIndex("receiverAddress"));
			item.weight = cursor.getInt(cursor.getColumnIndex("weight"));
			item.volume = cursor.getString(cursor.getColumnIndex("volume"));
			item.type = cursor.getString(cursor.getColumnIndex("type"));
			item.company = cursor.getString(cursor.getColumnIndex("company"));
			item.time = cursor.getString(cursor.getColumnIndex("time"));
			item.otherinfo = cursor.getString(cursor.getColumnIndex("otherinfo"));
			item.two_code = cursor.getString(cursor.getColumnIndex("two_code"));
			item.message = cursor.getInt(cursor.getColumnIndex("message"));
			item.number = cursor.getString(cursor.getColumnIndex("number"));
			item.content = cursor.getString(cursor.getColumnIndex("content"));
			item.sendorreceive = cursor.getInt(cursor.getColumnIndex("sendorreceive"));
			item.sender = cursor.getString(cursor.getColumnIndex("sender"));
			item.receiver = cursor.getString(cursor.getColumnIndex("receiver"));
			item.cost = cursor.getString(cursor.getColumnIndex("cost"));
			item.isover = cursor.getInt(cursor.getColumnIndex("isover"));
			return item;
		}
		return null;
	}
	public Order getOrderByNumber(String Number) {
		// TODO Auto-generated method stub
		Cursor cursor = null;
		cursor = dbInstance.query(DB_TABLENAME, 
				null, 
				"number="+Number, 
				null, 
				null, 
				null, 
				null);
		if(cursor.moveToFirst()){
			Order item = new Order();
			item._id = cursor.getInt(cursor.getColumnIndex("_id"));
			item.chestNum1 = cursor.getString(cursor.getColumnIndex("senderName"));
			item.chestNum2 = cursor.getString(cursor.getColumnIndex("sendAddress"));
			item.receiveMessage = cursor.getString(cursor.getColumnIndex("senderNum"));
			item.receiverName = cursor.getString(cursor.getColumnIndex("receiverName"));
			item.receiverNum = cursor.getString(cursor.getColumnIndex("receiverNum"));
			item.receiverAddress = cursor.getString(cursor.getColumnIndex("receiverAddress"));
			item.weight = cursor.getInt(cursor.getColumnIndex("weight"));
			item.volume = cursor.getString(cursor.getColumnIndex("volume"));
			item.type = cursor.getString(cursor.getColumnIndex("type"));
			item.company = cursor.getString(cursor.getColumnIndex("company"));
			item.time = cursor.getString(cursor.getColumnIndex("time"));
			item.otherinfo = cursor.getString(cursor.getColumnIndex("otherinfo"));
			item.two_code = cursor.getString(cursor.getColumnIndex("two_code"));
			item.message = cursor.getInt(cursor.getColumnIndex("message"));
			item.number = cursor.getString(cursor.getColumnIndex("number"));
			item.content = cursor.getString(cursor.getColumnIndex("content"));
			item.sendorreceive = cursor.getInt(cursor.getColumnIndex("sendorreceive"));
			item.sender = cursor.getString(cursor.getColumnIndex("sender"));
			item.receiver = cursor.getString(cursor.getColumnIndex("receiver"));
			item.cost = cursor.getString(cursor.getColumnIndex("cost"));
			item.isover = cursor.getInt(cursor.getColumnIndex("isover"));
			return item;
		}
		return null;
	}
	public void delete(int _id) {
		dbInstance.delete(DB_TABLENAME, "_id=?", new String[]{String.valueOf(_id)});
	}
	public void deleteAll(int privacy) {
		dbInstance.delete(DB_TABLENAME, "privacy=?", new String[]{String.valueOf(privacy)});
	}
	
	public int getTotalCount() {
		Cursor cursor = dbInstance.query(DB_TABLENAME, new String[]{"count(*)"}, null, null, null, null, null);
		cursor.moveToNext();
		return cursor.getInt(0);
	}
	
	private void saveDataToFile(String strData,boolean privacy) {
		String fileName = "";
		if(privacy) {
			fileName = "priv_data.bk";
		} else {
			fileName = "comm_data.bk";
		}
		try {
		String SDPATH = Environment.getExternalStorageDirectory() + "/";
		File fileParentPath = new File(SDPATH + "zpContactData/");
		fileParentPath.mkdirs();
		File file = new File(SDPATH + "zpContactData/" + fileName);
		System.out.println("the file previous path = " + file.getAbsolutePath());
		
		file.createNewFile();
		System.out.println("the file next path = " + file.getAbsolutePath());
		FileOutputStream fos = new FileOutputStream(file);
		
		fos.write(strData.getBytes());
		fos.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void restoreData(String fileName) {
		try {
		String SDPATH = Environment.getExternalStorageDirectory() + "/";
		File file = null;
		if(fileName.endsWith(".bk")) {
			file = new File(SDPATH + "zpContactData/"+ fileName);
		} else {
			file = new File(SDPATH + "zpContactData/"+ fileName + ".bk");
		}
		BufferedReader br = new BufferedReader(new FileReader(file));
		String str = "";
		while((str=br.readLine())!=null) {
			System.out.println(str);
			dbInstance.execSQL(str);
		}
		
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public boolean findFile(String fileName) {
		String SDPATH = Environment.getExternalStorageDirectory() + "/";
		File file = null;
		if(fileName.endsWith(".bk")) {
			file = new File(SDPATH + "zpContact/"+fileName);
		} else {
			file = new File(SDPATH + "zpContact/"+fileName + ".bk");
		}
		
		if(file.exists()) {
			return true;
		} else {
			return false;
		}
		
		
	}


	class MyDBHelper extends SQLiteOpenHelper {

		public MyDBHelper(Context context, String name,
				int version) {
			super(context, name, null, version);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			tableCreate = new StringBuffer();
			tableCreate.append("create table ")
					   .append(DB_TABLENAME)
					   .append(" (")
					   .append("_id integer primary key autoincrement,")
					   .append("senderName text,")
					   .append("senderNum text,")
					   .append("sendAddress text,")
					   .append("receiverName text,")
					   .append("receiverNum text,")
					   .append("receiverAddress text,")
					   .append("weight int,")
					   .append("volume text,")
					   .append("type text,")
					   .append("company text,")
					   .append("time text,")
					   .append("two_code text,")
					   .append("message int,")
					   .append("number text,")
					   .append("content text,")
					   .append("cost text,")
					   .append("sender text,")
					   .append("receiver text,")
					   .append("otherinfo text,")
					   .append("isover integer,")
					   .append("sendorreceive integer")
					   .append(")");
			System.out.println(tableCreate.toString());
			db.execSQL(tableCreate.toString());
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			String sql = "drop table if exists " + DB_TABLENAME;
			db.execSQL(sql);
			myDBHelper.onCreate(db);
		}
		
	}
}
