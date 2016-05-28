package com.XB_Accounts;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHandler_ItemCategory extends SQLiteOpenHelper {

	final static String TABLE_NAME_ITEM_CATEGORY = "item_category";
	
	final static String KEY_ITEMCATEGORY_ID = "id";
	final static String KEY_ITEMCATEGORY_NAME = "name";
	final static String KEY_ITEMCATEGORY_ACCOUNT_ID = "account_id";
	final static String KEY_ITEMCATEGORY_DESCRIPTION = "description";
	final static String KEY_ITEMCATEGORY_DAYTIME = "daytime";
	
	Context context;
	public DBHandler_ItemCategory(Context context) {
		super(context, DBHandler_Main.DB_NAME, null, DBHandler_Main.DB_VERSION);
		this.context = context;
	}

	@Override
	public void onCreate(SQLiteDatabase arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub

	}
	
	public ArrayList<Object_Item_Category> getItmCatsByAccountId(int id)
	{
		ArrayList<Object_Item_Category> allItemCats = new ArrayList<Object_Item_Category>();
		SQLiteDatabase db = this.getReadableDatabase();
		String sqlQuery = "SELECT * FROM "+TABLE_NAME_ITEM_CATEGORY+" WHERE is_active = 1 AND "+KEY_ITEMCATEGORY_ACCOUNT_ID+" = "+id+" ORDER BY "+KEY_ITEMCATEGORY_NAME+" ASC";
		Cursor cursor = db.rawQuery(sqlQuery, null);
		if(cursor != null)
		{
			if(cursor.moveToFirst())
			{
				do {
					Object_Item_Category itemCat = new Object_Item_Category();
					itemCat.id = cursor.getInt(cursor.getColumnIndex(KEY_ITEMCATEGORY_ID));
					itemCat.name = cursor.getString(cursor.getColumnIndex(KEY_ITEMCATEGORY_NAME));
					itemCat.accountId = cursor.getInt(cursor.getColumnIndex(KEY_ITEMCATEGORY_ACCOUNT_ID));
					itemCat.description = cursor.getString(cursor.getColumnIndex(KEY_ITEMCATEGORY_DESCRIPTION));
					itemCat.daytime = cursor.getString(cursor.getColumnIndex(KEY_ITEMCATEGORY_DAYTIME));
						DBHandler_Item dbhItm = new DBHandler_Item(context);
						itemCat.items = dbhItm.getItemsByCatId(itemCat.id);
					allItemCats.add(itemCat);
				} while (cursor.moveToNext());
			}
		}
		return allItemCats;
	}
	
	public boolean createItemCategory(Object_Item_Category itemCatObj)
	 {
	  SQLiteDatabase db = this.getWritableDatabase();
	  boolean status = false;
	  db.beginTransaction();
	  try {
	   
	   ContentValues valuePairs = new ContentValues();
	   
	   valuePairs.put(KEY_ITEMCATEGORY_NAME, itemCatObj.name);
	   valuePairs.put(KEY_ITEMCATEGORY_DESCRIPTION, itemCatObj.description);
	   valuePairs.put(KEY_ITEMCATEGORY_ACCOUNT_ID, itemCatObj.accountId);
	   
	   long rowinsert = db.insert(TABLE_NAME_ITEM_CATEGORY, null, valuePairs);
	   Log.i("accountsappdb", "inserted row Id: "+rowinsert);
	   
	   valuePairs.clear();
	   db.setTransactionSuccessful();
	   status = true;
	  } catch (Exception e) {
	   e.printStackTrace();
	  }
	  finally{
	   db.endTransaction();
	  }
	  db.close();
	  return status;
	 }
	
	public Object_Item_Category getItem_CategorybyId(int id){
		SQLiteDatabase db = this.getReadableDatabase();
		String sqlQuery = "SELECT * FROM "+TABLE_NAME_ITEM_CATEGORY+" WHERE "+KEY_ITEMCATEGORY_ID+" = "+id;
		Cursor cursor = db.rawQuery(sqlQuery, null);
		Object_Item_Category obj = null;
		try{
		if(cursor != null)
		{
		  if (cursor.moveToFirst()) {
				do {
			obj = new Object_Item_Category();
			obj.id = cursor.getInt(cursor.getColumnIndex(KEY_ITEMCATEGORY_ID));
			obj.name = cursor.getString(cursor.getColumnIndex(KEY_ITEMCATEGORY_NAME));
			obj.accountId = cursor.getInt(cursor.getColumnIndex(KEY_ITEMCATEGORY_ACCOUNT_ID));
			obj.description = cursor.getString(cursor.getColumnIndex(KEY_ITEMCATEGORY_DESCRIPTION));
			obj.daytime = cursor.getString(cursor.getColumnIndex(KEY_ITEMCATEGORY_DAYTIME));
				
		  }while (cursor.moveToNext());
			
		  }
		
		}
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		db.close();
		return obj;
	}
	
	
	
	/*public ArrayList<AA_Object_ItemCategory_2> getAllItemCategories()
	{
		ArrayList<AA_Object_ItemCategory_2> allItemCategory = new ArrayList<AA_Object_ItemCategory_2>();
		
		SQLiteDatabase db = this.getReadableDatabase();
		String sqlQuery = "SELECT * FROM "+TABLE_NAME_ITEM_CATEGORY+" ORDER BY "+KEY_ITEMCATEGORY_ID+" DESC";
		Cursor cursor = db.rawQuery(sqlQuery, null);
		try {
			if(cursor != null)
			{
				if(cursor.moveToFirst())
				{
					do{
						AA_Object_ItemCategory_2 obj = new AA_Object_ItemCategory_2();
						
						obj.id = cursor.getInt(cursor.getColumnIndex(KEY_ITEMCATEGORY_ID));
						obj.name = cursor.getString(cursor.getColumnIndex(KEY_ITEMCATEGORY_NAME));
						obj.accountid = cursor.getInt(cursor.getColumnIndex(KEY_ITEMCATEGORY_ACCOUNT_ID));
						obj.description = cursor.getString(cursor.getColumnIndex(KEY_ITEMCATEGORY_DESCRIPTION));
						obj.daytime = cursor.getString(cursor.getColumnIndex(KEY_ITEMCATEGORY_DAYTIME));
						
						allItemCategory.add(obj);
					}while(cursor.moveToNext());
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return allItemCategory;
	}*/
	public boolean updateItem_Cate(Object_Item_Category obj,int id)
	{
		SQLiteDatabase db = this.getWritableDatabase();
		boolean status = false;
		db.beginTransaction();
		try {
			
			ContentValues valuePairs = new ContentValues();
			    valuePairs.put(KEY_ITEMCATEGORY_NAME, obj.name);
			   valuePairs.put(KEY_ITEMCATEGORY_DESCRIPTION, obj.description);
			   valuePairs.put(KEY_ITEMCATEGORY_ACCOUNT_ID, obj.accountId);
			
			long rowinsert = db.update(TABLE_NAME_ITEM_CATEGORY, valuePairs, KEY_ITEMCATEGORY_ID +"="+ id,null);
			Log.i("accountsappdb", "updated row Id: "+rowinsert);
			
			valuePairs.clear();
			db.setTransactionSuccessful();
			status = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			db.endTransaction();
		}
		db.close();
		return status;
	}
	
	public void disableItem_cate(int Id){
		  SQLiteDatabase db = this.getWritableDatabase();
		  String sql = "Update "+TABLE_NAME_ITEM_CATEGORY+" Set is_active = 0 WHERE "+KEY_ITEMCATEGORY_ID+" = "+Id;
		  db.execSQL(sql);
	  }
}
