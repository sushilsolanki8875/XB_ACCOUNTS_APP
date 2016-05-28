package com.XB_Accounts;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHandler_Item extends SQLiteOpenHelper {

	final static String TABLE_NAME_ITEMS = "item";

	final static String KEY_ITEM_ID = "id";
	final static String KEY_ITEM_NAME = "name";
	final static String KEY_ITEM_ITEM_CATID = "item_cat_id";
	final static String KEY_ITEM_DESCRIPTION = "description";
	final static String KEY_ITEM_UNIT_ID = "unit_id";
	final static String KEY_ITEM_PRINT_NAME = "print_name";
	final static String KEY_ITEM_SALE_PRICE = "sale_price";
	final static String KEY_ITEM_PURCHASE_PRICE = "purchase_price";
	final static String KEY_ITEM_MRP = "mrp";
	final static String KEY_ITEM_MIN_SALE_PRICE = "min_sale_price";
	final static String KEY_ITEM_DAYTIME = "daytime";
	final static String KEY_ITEM_QTY_REMAINING = "qty_remaining";

	Context context;

	public DBHandler_Item(Context context) {
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

	public boolean updateItem(Object_Item obj, int itemId) {
		SQLiteDatabase db = this.getWritableDatabase();
		boolean status = false;
		db.beginTransaction();
		try {

			ContentValues valuePairs = new ContentValues();

			valuePairs.put(KEY_ITEM_NAME, obj.name);
			valuePairs.put(KEY_ITEM_ITEM_CATID, obj.itemCatId);
			valuePairs.put(KEY_ITEM_DESCRIPTION, obj.description);
			valuePairs.put(KEY_ITEM_UNIT_ID, obj.unit.id);
			valuePairs.put(KEY_ITEM_PRINT_NAME, obj.printName);
			valuePairs.put(KEY_ITEM_SALE_PRICE, obj.salePrice);
			valuePairs.put(KEY_ITEM_PURCHASE_PRICE, obj.purchasePrice);
			valuePairs.put(KEY_ITEM_MRP, obj.mrp);
			valuePairs.put(KEY_ITEM_MIN_SALE_PRICE, obj.minSalePrice);
			valuePairs.put(KEY_ITEM_QTY_REMAINING, obj.qtyRemaining);

			long rowinsert = db.update(TABLE_NAME_ITEMS, valuePairs,
					KEY_ITEM_ID + "=" + itemId, null);
			Log.i("accountsappdb", "updated row Id: " + rowinsert);

			valuePairs.clear();
			db.setTransactionSuccessful();
			status = true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.endTransaction();
		}
		db.close();
		return status;
	}

	public boolean createNewItem(Object_Item obj) {
		SQLiteDatabase db = this.getWritableDatabase();
		boolean status = false;
		db.beginTransaction();
		try {

			ContentValues valuePairs = new ContentValues();

			valuePairs.put(KEY_ITEM_NAME, obj.name);
			valuePairs.put(KEY_ITEM_ITEM_CATID, obj.itemCatId);
			valuePairs.put(KEY_ITEM_DESCRIPTION, obj.description);
			valuePairs.put(KEY_ITEM_UNIT_ID, obj.unit.id);
			valuePairs.put(KEY_ITEM_PRINT_NAME, obj.printName);
			valuePairs.put(KEY_ITEM_SALE_PRICE, obj.salePrice);
			valuePairs.put(KEY_ITEM_PURCHASE_PRICE, obj.purchasePrice);
			valuePairs.put(KEY_ITEM_MRP, obj.mrp);
			valuePairs.put(KEY_ITEM_MIN_SALE_PRICE, obj.minSalePrice);
			valuePairs.put(KEY_ITEM_QTY_REMAINING, obj.qtyRemaining);

			long rowinsert = db.insert(TABLE_NAME_ITEMS, null, valuePairs);
			Log.i("accountsappdb", "inserted row Id: " + rowinsert);

			valuePairs.clear();
			db.setTransactionSuccessful();
			status = true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.endTransaction();
		}
		db.close();
		return status;
	} 

	private Object_Item getItemCursor2Object(Cursor cursor) {
		//Log.i("SUSHIL","]curcer    "+cursor.getCount());
		Object_Item obj = new Object_Item();
		obj.id = cursor.getInt(cursor.getColumnIndex(KEY_ITEM_ID));
		obj.name = cursor.getString(cursor.getColumnIndex(KEY_ITEM_NAME));
		obj.itemCatId = cursor.getInt(cursor
				.getColumnIndex(KEY_ITEM_ITEM_CATID));
		obj.description = cursor.getString(cursor
				.getColumnIndex(KEY_ITEM_DESCRIPTION));
		
		Object_Unit itmUnit = new Object_Unit();
		itmUnit.id = cursor.getInt(cursor.getColumnIndex(KEY_ITEM_UNIT_ID));
		itmUnit.name = cursor.getString(cursor.getColumnIndex("untName"));
		itmUnit.printName = cursor.getString(cursor
				.getColumnIndex("untPrntName"));
		obj.unit = itmUnit;
		obj.printName = cursor.getString(cursor
				.getColumnIndex(KEY_ITEM_PRINT_NAME));
		obj.salePrice = cursor.getDouble(cursor
				.getColumnIndex(KEY_ITEM_SALE_PRICE));
		obj.purchasePrice = cursor.getDouble(cursor
				.getColumnIndex(KEY_ITEM_PURCHASE_PRICE));
		obj.mrp = cursor.getDouble(cursor.getColumnIndex(KEY_ITEM_MRP));
		obj.minSalePrice = cursor.getDouble(cursor
				.getColumnIndex(KEY_ITEM_MIN_SALE_PRICE));
		obj.qtyRemaining = cursor.getInt(cursor
				.getColumnIndex(KEY_ITEM_QTY_REMAINING));
		//Log.i("SUSHIL","item id  "+obj.id);
		return obj;
	}

	public ArrayList<Object_Item> getAllItems() {
		ArrayList<Object_Item> allItems = new ArrayList<Object_Item>();

		SQLiteDatabase db = this.getReadableDatabase();
		// String sqlQuery =
		// "SELECT * FROM "+TABLE_NAME_ITEMS+" ORDER BY "+KEY_ITEM_NAME+" ASC";
		String sqlQuery = "SELECT itm." + KEY_ITEM_ID + ",itm." + KEY_ITEM_NAME
				+ ",itm." + KEY_ITEM_ITEM_CATID + ",itm."
				+ KEY_ITEM_DESCRIPTION + ",itm." + KEY_ITEM_UNIT_ID + ",itm."
				+ KEY_ITEM_PRINT_NAME + ",itm." + KEY_ITEM_SALE_PRICE + ",itm."
				+ KEY_ITEM_PURCHASE_PRICE + ",itm." + KEY_ITEM_MRP + ",itm."
				+ KEY_ITEM_MIN_SALE_PRICE + ",itm." + KEY_ITEM_DAYTIME
				+ ",itm." + KEY_ITEM_QTY_REMAINING + "," + "unt."
				+ DBHandler_Unit.KEY_UNIT_NAME + " untName,unt."
				+ DBHandler_Unit.KEY_UNIT_PRINT_NAME + " untPrntName  "
				+ "FROM " + TABLE_NAME_ITEMS + " itm " + "INNER JOIN "
				+ DBHandler_Unit.TABLE_NAME_UNIT + " unt ON unt."
				+ DBHandler_Unit.KEY_UNIT_ID + " = itm." + KEY_ITEM_UNIT_ID
				+ " Where itm.is_active = 1 " + " ORDER BY itm." + KEY_ITEM_NAME + " ASC";
		Cursor cursor = db.rawQuery(sqlQuery, null);
		try {
			if (cursor != null) {
				if (cursor.moveToFirst()) {
					do {
						Object_Item obj = getItemCursor2Object(cursor);

						allItems.add(obj);
					} while (cursor.moveToNext());
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return allItems;
	}

	public ArrayList<Object_Item> getItemsNoStock() {
		ArrayList<Object_Item> allItems = new ArrayList<Object_Item>();

		SQLiteDatabase db = this.getReadableDatabase();
		// String sqlQuery =
		// "SELECT * FROM "+TABLE_NAME_ITEMS+" WHERE "+KEY_ITEM_QTY_REMAINING+" = 0 "+" ORDER BY "+KEY_ITEM_NAME+" ASC";
		String sqlQuery = "SELECT itm." + KEY_ITEM_ID + ",itm." + KEY_ITEM_NAME
				+ ",itm." + KEY_ITEM_ITEM_CATID + ",itm."
				+ KEY_ITEM_DESCRIPTION + ",itm." + KEY_ITEM_UNIT_ID + ",itm."
				+ KEY_ITEM_PRINT_NAME + ",itm." + KEY_ITEM_SALE_PRICE + ",itm."
				+ KEY_ITEM_PURCHASE_PRICE + ",itm." + KEY_ITEM_MRP + ",itm."
				+ KEY_ITEM_MIN_SALE_PRICE + ",itm." + KEY_ITEM_DAYTIME
				+ ",itm." + KEY_ITEM_QTY_REMAINING + "," + "unt."
				+ DBHandler_Unit.KEY_UNIT_NAME + " untName,unt."
				+ DBHandler_Unit.KEY_UNIT_PRINT_NAME + " untPrntName  "
				+ "FROM " + TABLE_NAME_ITEMS + " itm " + "INNER JOIN "
				+ DBHandler_Unit.TABLE_NAME_UNIT + " unt ON unt."
				+ DBHandler_Unit.KEY_UNIT_ID + " = itm." + KEY_ITEM_UNIT_ID
				+ " " + " WHERE itm.is_active = 1 AND itm." + KEY_ITEM_QTY_REMAINING + " = 0 "
				+ " ORDER BY itm." + KEY_ITEM_NAME + " ASC";
		Cursor cursor = db.rawQuery(sqlQuery, null);
		try {
			if (cursor != null) {
				if (cursor.moveToFirst()) {
					do {
						Object_Item obj = getItemCursor2Object(cursor);

						allItems.add(obj);
					} while (cursor.moveToNext());
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return allItems;
	}

	public ArrayList<Object_Item> getItemsHaveStock() {
		ArrayList<Object_Item> allItems = new ArrayList<Object_Item>();

		SQLiteDatabase db = this.getReadableDatabase();
		// String sqlQuery =
		// "SELECT * FROM "+TABLE_NAME_ITEMS+" WHERE "+KEY_ITEM_QTY_REMAINING+" > 0 "+" ORDER BY "+KEY_ITEM_NAME+" ASC";
		String sqlQuery = "SELECT itm." + KEY_ITEM_ID + ",itm." + KEY_ITEM_NAME
				+ ",itm." + KEY_ITEM_ITEM_CATID + ",itm."
				+ KEY_ITEM_DESCRIPTION + ",itm." + KEY_ITEM_UNIT_ID + ",itm."
				+ KEY_ITEM_PRINT_NAME + ",itm." + KEY_ITEM_SALE_PRICE + ",itm."
				+ KEY_ITEM_PURCHASE_PRICE + ",itm." + KEY_ITEM_MRP + ",itm."
				+ KEY_ITEM_MIN_SALE_PRICE + ",itm." + KEY_ITEM_DAYTIME
				+ ",itm." + KEY_ITEM_QTY_REMAINING + "," + "unt."
				+ DBHandler_Unit.KEY_UNIT_NAME + " untName,unt."
				+ DBHandler_Unit.KEY_UNIT_PRINT_NAME + " untPrntName  "
				+ "FROM " + TABLE_NAME_ITEMS + " itm " + "INNER JOIN "
				+ DBHandler_Unit.TABLE_NAME_UNIT + " unt ON unt."
				+ DBHandler_Unit.KEY_UNIT_ID + " = itm." + KEY_ITEM_UNIT_ID
				+ " " + " WHERE itm.is_active = 1 AND itm." + KEY_ITEM_QTY_REMAINING + " > 0 "
				+ " ORDER BY itm." + KEY_ITEM_NAME + " ASC";
		Cursor cursor = db.rawQuery(sqlQuery, null);
		try {
			if (cursor != null) {
				if (cursor.moveToFirst()) {
					do {
						Object_Item obj = getItemCursor2Object(cursor);

						allItems.add(obj);
					} while (cursor.moveToNext());
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return allItems;
	}

	public ArrayList<Object_Item> getItems(String searchStr) {
		ArrayList<Object_Item> allItems = new ArrayList<Object_Item>();

		SQLiteDatabase db = this.getReadableDatabase();
		// String sqlQuery =
		// "SELECT * FROM "+TABLE_NAME_ITEMS+" WHERE "+KEY_ITEM_NAME+" LIKE %"+searchStr+"% OR "+KEY_ITEM_PRINT_NAME+" LIKE %"+searchStr+"% "+" ORDER BY "+KEY_ITEM_NAME+" ASC";
		String sqlQuery = "SELECT itm." + KEY_ITEM_ID + ",itm." + KEY_ITEM_NAME
				+ ",itm." + KEY_ITEM_ITEM_CATID + ",itm."
				+ KEY_ITEM_DESCRIPTION + ",itm." + KEY_ITEM_UNIT_ID + ",itm."
				+ KEY_ITEM_PRINT_NAME + ",itm." + KEY_ITEM_SALE_PRICE + ",itm."
				+ KEY_ITEM_PURCHASE_PRICE + ",itm." + KEY_ITEM_MRP + ",itm."
				+ KEY_ITEM_MIN_SALE_PRICE + ",itm." + KEY_ITEM_DAYTIME
				+ ",itm." + KEY_ITEM_QTY_REMAINING + "," + "unt."
				+ DBHandler_Unit.KEY_UNIT_NAME + " untName,unt."
				+ DBHandler_Unit.KEY_UNIT_PRINT_NAME + " untPrntName  "
				+ "FROM " + TABLE_NAME_ITEMS + " itm " + "INNER JOIN "
				+ DBHandler_Unit.TABLE_NAME_UNIT + " unt ON unt."
				+ DBHandler_Unit.KEY_UNIT_ID + " = itm." + KEY_ITEM_UNIT_ID
				+ " " + " WHERE itm.is_active = 1 AND itm." + KEY_ITEM_NAME + " LIKE %" + searchStr
				+ "% OR itm." + KEY_ITEM_PRINT_NAME + " LIKE %" + searchStr
				+ "% " + " ORDER BY itm." + KEY_ITEM_NAME + " ASC";
		Cursor cursor = db.rawQuery(sqlQuery, null);
		try {
			if (cursor != null) {
				if (cursor.moveToFirst()) {
					do {
						Object_Item obj = getItemCursor2Object(cursor);

						allItems.add(obj);
					} while (cursor.moveToNext());
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return allItems;
	}

	public ArrayList<Object_Item> getItemsByCatId(int id) {
		ArrayList<Object_Item> allItems = new ArrayList<Object_Item>();

		SQLiteDatabase db = this.getReadableDatabase();
		// String sqlQuery =
		// "SELECT * FROM "+TABLE_NAME_ITEMS+" WHERE "+KEY_ITEM_NAME+" LIKE %"+searchStr+"% OR "+KEY_ITEM_PRINT_NAME+" LIKE %"+searchStr+"% "+" ORDER BY "+KEY_ITEM_NAME+" ASC";
		String sqlQuery = "SELECT itm." + KEY_ITEM_ID + ",itm." + KEY_ITEM_NAME
				+ ",itm." + KEY_ITEM_ITEM_CATID + ",itm."
				+ KEY_ITEM_DESCRIPTION + ",itm." + KEY_ITEM_UNIT_ID + ",itm."
				+ KEY_ITEM_PRINT_NAME + ",itm." + KEY_ITEM_SALE_PRICE + ",itm."
				+ KEY_ITEM_PURCHASE_PRICE + ",itm." + KEY_ITEM_MRP + ",itm."
				+ KEY_ITEM_MIN_SALE_PRICE + ",itm." + KEY_ITEM_DAYTIME
				+ ",itm." + KEY_ITEM_QTY_REMAINING + "," + "unt."
				+ DBHandler_Unit.KEY_UNIT_NAME + " untName,unt."
				+ DBHandler_Unit.KEY_UNIT_PRINT_NAME + " untPrntName  "
				+ "FROM " + TABLE_NAME_ITEMS + " itm " + "INNER JOIN "
				+ DBHandler_Unit.TABLE_NAME_UNIT + " unt ON unt."
				+ DBHandler_Unit.KEY_UNIT_ID + " = itm." + KEY_ITEM_UNIT_ID
				+ " " + " WHERE itm.is_active = 1 AND  itm." + KEY_ITEM_ITEM_CATID + " = " + id + " "
				+ " ORDER BY itm." + KEY_ITEM_NAME + " ASC";
		Cursor cursor = db.rawQuery(sqlQuery, null);
		try {
			if (cursor != null) {
				if (cursor.moveToFirst()) {
					do {
						Object_Item obj = getItemCursor2Object(cursor);

						allItems.add(obj);
					} while (cursor.moveToNext());
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return allItems;
	}

	public Object_Item getItem(SQLiteDatabase db, int id) {
		Object_Item item = new Object_Item();

		// SQLiteDatabase db = this.getReadableDatabase();
		// String sqlQuery =
		// "SELECT * FROM "+TABLE_NAME_ITEMS+" WHERE "+KEY_ITEM_ID+" ="+id+" ORDER BY "+KEY_ITEM_NAME+" ASC";
		String sqlQuery = "SELECT itm." + KEY_ITEM_ID + ",itm." + KEY_ITEM_NAME
				+ ",itm." + KEY_ITEM_ITEM_CATID + ",itm."
				+ KEY_ITEM_DESCRIPTION + ",itm." + KEY_ITEM_UNIT_ID + ",itm."
				+ KEY_ITEM_PRINT_NAME + ",itm." + KEY_ITEM_SALE_PRICE + ",itm."
				+ KEY_ITEM_PURCHASE_PRICE + ",itm." + KEY_ITEM_MRP + ",itm."
				+ KEY_ITEM_MIN_SALE_PRICE + ",itm." + KEY_ITEM_DAYTIME
				+ ",itm." + KEY_ITEM_QTY_REMAINING + "," + "unt."
				+ DBHandler_Unit.KEY_UNIT_NAME + " untName,unt."
				+ DBHandler_Unit.KEY_UNIT_PRINT_NAME + " untPrntName  "
				+ "FROM " + TABLE_NAME_ITEMS + " itm " + "INNER JOIN "
				+ DBHandler_Unit.TABLE_NAME_UNIT + " unt ON unt."
				+ DBHandler_Unit.KEY_UNIT_ID + " = itm." + KEY_ITEM_UNIT_ID
				+ " " + "WHERE itm.is_active = 1 AND itm." + KEY_ITEM_ID + " = " + id;
		Cursor cursor = db.rawQuery(sqlQuery, null);
		try {
			if (cursor != null) {
				if (cursor.moveToFirst()) {
					do {
						item = getItemCursor2Object(cursor);

					} while (cursor.moveToNext());
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return item;
	}

	public boolean updateItemStock(SQLiteDatabase db, int itemId,
			int deductQty, int txnTyepId) {
		Object_Item item = this.getItem(db, itemId);
		int remQty = item.qtyRemaining;
		if (txnTyepId == 1)// i.e. account is Credited
			remQty = remQty - deductQty;
		else if (txnTyepId == 2)// i.e. account is Debited
			remQty = remQty + deductQty;

		// SQLiteDatabase db = this.getWritableDatabase();
		boolean status = false;
		// db.beginTransaction();
		try {

			ContentValues valuePairs = new ContentValues();

			valuePairs.put(KEY_ITEM_QTY_REMAINING, remQty);

			long rowinsert = db.update(TABLE_NAME_ITEMS, valuePairs,
					KEY_ITEM_ID + "=" + itemId, null);
			Log.i("accountsappdb", "updated stock row Id: " + rowinsert);

			valuePairs.clear();
			// db.setTransactionSuccessful();
			status = true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// db.endTransaction();
		}
		// db.close();
		return status;
	}

	public Object_Item getItembyId(int id) {
		SQLiteDatabase db = this.getReadableDatabase();
		String sqlQuery = "SELECT itm." + KEY_ITEM_ID + ",itm." + KEY_ITEM_NAME
				+ ",itm." + KEY_ITEM_ITEM_CATID + ",itm."
				+ KEY_ITEM_DESCRIPTION + ",itm." + KEY_ITEM_UNIT_ID + ",itm."
				+ KEY_ITEM_PRINT_NAME + ",itm." + KEY_ITEM_SALE_PRICE + ",itm."
				+ KEY_ITEM_PURCHASE_PRICE + ",itm." + KEY_ITEM_MRP + ",itm."
				+ KEY_ITEM_MIN_SALE_PRICE + ",itm." + KEY_ITEM_DAYTIME
				+ ",itm." + KEY_ITEM_QTY_REMAINING + "," + "unt."
				+ DBHandler_Unit.KEY_UNIT_NAME + " untName,unt."
				+ DBHandler_Unit.KEY_UNIT_PRINT_NAME + " untPrntName  "
				+ "FROM " + TABLE_NAME_ITEMS + " itm " + "INNER JOIN "
				+ DBHandler_Unit.TABLE_NAME_UNIT + " unt ON unt."
				+ DBHandler_Unit.KEY_UNIT_ID + " = itm." + KEY_ITEM_UNIT_ID
				+ " " + "WHERE itm.is_active = 1 AND itm." + KEY_ITEM_ID + " = " + id;
		Cursor cursor = db.rawQuery(sqlQuery, null);
		Object_Item obj = null;
		try{
			if(cursor != null)
			{
			  if (cursor.moveToFirst()) {
					do {
					   Log.i("SUSHIL","curser.....size "+cursor.getCount());
					   // obj = new Object_Item();
						obj = getItemCursor2Object(cursor);

					 }while (cursor.moveToNext());
					
			  }
			
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		db.close();
		return obj;
	}
	
	public void disableItem(int Id){
		  SQLiteDatabase db = this.getWritableDatabase();
		  String sql = "Update "+TABLE_NAME_ITEMS+" Set is_active = 0 WHERE "+KEY_ITEM_ID+" = "+Id;
		  db.execSQL(sql);
	  }
}
