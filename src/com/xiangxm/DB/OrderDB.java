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
	
	public static final String DB_TABLENAME="my_order";
	
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
		values.put("name", order.name);
		values.put("company", order.company);
		values.put("number", order.number);
		values.put("time", order.time);
		values.put("content", order.content);
		
		if( dbInstance.insert(DB_TABLENAME, null, values)>0){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 获得数据库中所有的用户，将每一个用户放到一个map中去，然后再将map放到list里面去返回
	 * @param privacy 
	 * @return list
	 */
	
	@SuppressWarnings("unchecked")
	public ArrayList getAllUser(boolean privacy) {
		ArrayList list = new ArrayList();
		Cursor cursor = null;
			cursor = dbInstance.query(DB_TABLENAME, 
					new String[]{"_id","name","company","number","time","content"}, 
					null, 
					null, 
					null, 
					null, 
					null);
		
		while(cursor.moveToNext()) {
			@SuppressWarnings("rawtypes")
			HashMap item = new HashMap();
			item.put("_id", cursor.getInt(cursor.getColumnIndex("_id")));
			item.put("name", cursor.getString(cursor.getColumnIndex("name")));
			item.put("company", cursor.getString(cursor.getColumnIndex("company")));
			item.put("number", cursor.getString(cursor.getColumnIndex("number")));
			item.put("time", cursor.getString(cursor.getColumnIndex("time")));
			item.put("content", cursor.getString(cursor.getColumnIndex("content")));
			
			list.add(item);
		}
		
		return list;
	}

	
	public void modify(Order order) {
		ContentValues values = new ContentValues();
		values.put("name", order.name);
		values.put("company", order.company);
		values.put("number", order.number);
		values.put("time", order.time);
		values.put("content", order.content);
		
		dbInstance.update(DB_TABLENAME, values, "_id=?", new String[]{String.valueOf(order._id)});
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
					   .append("name text,")
					   .append("company text,")
					   .append("number text,")
					   .append("time text,")
					   .append("content text,")
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
