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

import com.xiangxm.cls.User;

public class DBHelper {
	
	public static final String DB_DBNAME="contact";
	
	public static final String DB_TABLENAME="user";
	
	public static final int VERSION = 4;
	
	public static SQLiteDatabase dbInstance; 
	
	private MyDBHelper myDBHelper;
	
	private StringBuffer tableCreate;
	
	private Context context;
	
	public DBHelper(Context context) {
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
	public long insert(User user) {
		ContentValues values = new ContentValues();
		values.put("name", user.username);
		values.put("pwd", user.userpwd);
		values.put("mobilephone", user.mobilePhone);
		values.put("address", user.address);
		values.put("privacy", user.privacy);
		values.put("senderorreceive", user.senderorreceive);
		return dbInstance.insert(DB_TABLENAME, null, values);
	}
	
	/**
	 * 获得数据库中所有的用户，将每一个用户放到一个map中去，然后再将map放到list里面去返回
	 * @param privacy 
	 * @return list
	 */
	
	public ArrayList<User> getAllUser(boolean privacy) {
		ArrayList<User> list = new ArrayList<User>();
		Cursor cursor = null;
		if(privacy) {
			cursor = dbInstance.query(DB_TABLENAME, 
					new String[]{"_id","name","pwd","mobilephone","address","senderorreceive"},  
					"privacy=1", 
					null, 
					null, 
					null, 
					null);
		} else {
			cursor = dbInstance.query(DB_TABLENAME, 
					new String[]{"_id","name","pwd","mobilephone","address","senderorreceive"}, 
					"privacy=0",
					null, 
					null, 
					null, 
					null);
		}
		 
		
		while(cursor.moveToNext()) {
			User user = new User();
			user._id = cursor.getInt(cursor.getColumnIndex("_id"));
			user.username = cursor.getString(cursor.getColumnIndex("name"));
			user.userpwd = cursor.getString(cursor.getColumnIndex("pwd"));
			user.mobilePhone = cursor.getString(cursor.getColumnIndex("mobilephone"));
			user.address = cursor.getString(cursor.getColumnIndex("address"));
			user.senderorreceive = cursor.getInt(cursor.getColumnIndex("senderorreceive"));
			list.add(user);
		}
		
		return list;
	}
	public ArrayList<User> getAllSender() {
		ArrayList<User> list = new ArrayList<User>();
		Cursor cursor = null;
			cursor = dbInstance.query(DB_TABLENAME, 
					new String[]{"_id","name","pwd","mobilephone","address","senderorreceive"}, 
					"senderorreceive=1", 
					null, 
					null, 
					null, 
					null);
		
		while(cursor.moveToNext()) {
			User user = new User();
			user._id = cursor.getInt(cursor.getColumnIndex("_id"));
			user.username = cursor.getString(cursor.getColumnIndex("name"));
			user.userpwd = cursor.getString(cursor.getColumnIndex("pwd"));
			user.mobilePhone = cursor.getString(cursor.getColumnIndex("mobilephone"));
			user.address = cursor.getString(cursor.getColumnIndex("address"));
			user.senderorreceive = cursor.getInt(cursor.getColumnIndex("senderorreceive"));
			list.add(user);
		}
		
		return list;
	}
	public ArrayList<User> getAllRecriver() {
		ArrayList<User> list = new ArrayList<User>();
		Cursor cursor = null;
			cursor = dbInstance.query(DB_TABLENAME, 
					new String[]{"_id","name","pwd","mobilephone","address","senderorreceive"}, 
					"senderorreceive=2", 
					null, 
					null, 
					null, 
					null);
		
		while(cursor.moveToNext()) {
			User user = new User();
			user._id = cursor.getInt(cursor.getColumnIndex("_id"));
			user.username = cursor.getString(cursor.getColumnIndex("name"));
			user.userpwd = cursor.getString(cursor.getColumnIndex("pwd"));
			user.mobilePhone = cursor.getString(cursor.getColumnIndex("mobilephone"));
			user.address = cursor.getString(cursor.getColumnIndex("address"));
			user.senderorreceive = cursor.getInt(cursor.getColumnIndex("senderorreceive"));
			list.add(user);
		}
		
		return list;
	}
	
	public User getById(int id){
		User user = new User();
		Cursor cursor = null;
			cursor = dbInstance.query(DB_TABLENAME, 
					new String[]{"_id","name","pwd","mobilephone","address","senderorreceive"}, 
					"_id="+id, 
					null, 
					null, 
					null, 
					null);
		
		while(cursor.moveToNext()) {
			
			user._id = cursor.getInt(cursor.getColumnIndex("_id"));
			user.username = cursor.getString(cursor.getColumnIndex("name"));
			user.userpwd = cursor.getString(cursor.getColumnIndex("pwd"));
			user.mobilePhone = cursor.getString(cursor.getColumnIndex("mobilephone"));
			user.address = cursor.getString(cursor.getColumnIndex("address"));
			user.senderorreceive = cursor.getInt(cursor.getColumnIndex("senderorreceive"));
		}
		
		return user;
	}
	public void modify(User user) {
		ContentValues values = new ContentValues();
		values.put("name", user.username);
		values.put("pwd", user.userpwd);
		values.put("mobilephone", user.mobilePhone);
		values.put("address", user.address);
		values.put("senderorreceive", user.senderorreceive);
		dbInstance.update(DB_TABLENAME, values, "_id=?", new String[]{String.valueOf(user._id)});
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
	
	
	
	public void deleteMarked(ArrayList<Integer> deleteId) {
		StringBuffer  strDeleteId = new StringBuffer();
		strDeleteId.append("_id=");
		for(int i=0;i<deleteId.size();i++) {
			if(i!=deleteId.size()-1) {
				strDeleteId.append(deleteId.get(i) + " or _id=");
			} else {
				strDeleteId.append(deleteId.get(i));
			}
			
			
		}
		dbInstance.delete(DB_TABLENAME, strDeleteId.toString(), null);
		System.out.println(strDeleteId.toString());
		
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
					   .append("pwd text,")
					   .append("mobilephone text,")
					   .append("address text,")
					   .append("privacy int,")
					   .append("senderorreceive int")
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
