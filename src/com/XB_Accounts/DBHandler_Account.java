package com.XB_Accounts;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHandler_Account extends SQLiteOpenHelper {

	final static String TABLE_NAME_ACCOUNT = "account";

	final static String KEY_ACCOUNT_ID = "id";
	final static String KEY_ACCOUNT_NAME = "name";
	final static String KEY_ACCOUNT_GRPID = "grp_id";
	final static String KEY_ACCOUNT_ADDRESS = "address";
	final static String KEY_ACCOUNT_CONTACT = "contact";
	final static String KEY_ACCOUNT_DESCRIPTION = "description";
	final static String KEY_ACCOUNT_DAYTIME = "daytime";
	final static String KEY_ACCOUNT_RATE = "rate";
	final static String KEY_ACCOUNT_TIME = "time";
	final static String KEY_ACCOUNT_OPENINGTIME = "day_time_opening";
	
	final static String TABLE_NAME_ACCOUNT_TXN = "account_txn";
	
	final static String KEY_ACCOUNTTXN_ID = "id";
	final static String KEY_ACCOUNTTXN_ACCOUNT_ID = "account_id";
	final static String KEY_ACCOUNTTXN_ACCOUNT_VOUCHER_TXN_ID = "voucher_txn_id";
	final static String KEY_ACCOUNTTXN_ACCOUNT_AMOUNT = "amount";
	final static String KEY_ACCOUNTTXN_ACCOUNT_TXN_TYPE_ID = "txn_type_id";
	final static String KEY_ACCOUNTTXN_ACCOUNT_NARRATION = "narration";
	final static String KEY_ACCOUNTTXN_ACCOUNT_ISCASH = "is_cash";
	final static String KEY_ACCOUNTTXN_ACCOUNT_ISINTEREST = "is_interest";
	final static String KEY_ACCOUNTTXN_ACCOUNT_ISOPENING = "is_opening_balance";
	
	final static String KEY_ACCOUNT_MAINID = "main_id";
	final static String KEY_ACCOUNTTXN_TXNID = "txn_id";
	
	Context context;

	public DBHandler_Account(Context context) {
		super(context, DBHandler_Main.DB_NAME, null, DBHandler_Main.DB_VERSION);
		this.context = context;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

	public boolean createNewAccount(Object_Account obj) {
		SQLiteDatabase db = this.getWritableDatabase();
		boolean status = false;
		db.beginTransaction();
		try {

			ContentValues valuePairs = new ContentValues();

			valuePairs.put(KEY_ACCOUNT_NAME, obj.name);
			valuePairs.put(KEY_ACCOUNT_GRPID, obj.grpId);
			valuePairs.put(KEY_ACCOUNT_ADDRESS, obj.address);
			valuePairs.put(KEY_ACCOUNT_CONTACT, obj.contact);
			valuePairs.put(KEY_ACCOUNT_DESCRIPTION, obj.description);
			valuePairs.put(KEY_ACCOUNT_RATE, obj.rate);
			valuePairs.put(KEY_ACCOUNT_TIME, obj.time);
			valuePairs.put(KEY_ACCOUNT_OPENINGTIME, obj.daytime_open);
			long rowinsert = db.insert(TABLE_NAME_ACCOUNT, null, valuePairs);
			Log.i("accountsappdb", "inserted row Id: " + rowinsert);
			valuePairs.clear();

			/* Insertion in Account_Txn */
		if(obj.accountTxn!=null){	
			valuePairs.put(KEY_ACCOUNTTXN_ACCOUNT_ID, rowinsert);
			valuePairs.put(KEY_ACCOUNTTXN_ACCOUNT_VOUCHER_TXN_ID,"");
			valuePairs.put(KEY_ACCOUNTTXN_ACCOUNT_AMOUNT,
					obj.accountTxn.amount);
			valuePairs.put(KEY_ACCOUNTTXN_ACCOUNT_TXN_TYPE_ID,
					obj.accountTxn.txnType.id);
			valuePairs.put(KEY_ACCOUNTTXN_ACCOUNT_NARRATION,
					obj.accountTxn.narration);
			valuePairs.put(KEY_ACCOUNTTXN_ACCOUNT_ISCASH,
					obj.accountTxn.isCash);
			valuePairs.put(KEY_ACCOUNTTXN_ACCOUNT_ISINTEREST,
					obj.accountTxn.isInterest);
			valuePairs.put(KEY_ACCOUNTTXN_ACCOUNT_ISOPENING,
					obj.accountTxn.isOpening);
			
			
			long txnAccountId = db.insert(TABLE_NAME_ACCOUNT_TXN, null,
					valuePairs);
			Log.i("SUSHIL", "inserted into AccounTxn row Id: "
					+ txnAccountId);
		}
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

	public boolean updateAccount(Object_Account obj, int accountId) {
		SQLiteDatabase db = this.getWritableDatabase();
		boolean status = false;
		db.beginTransaction();
		try {

			ContentValues valuePairs = new ContentValues();

			valuePairs.put(KEY_ACCOUNT_NAME, obj.name);
			valuePairs.put(KEY_ACCOUNT_GRPID, obj.grpId);
			valuePairs.put(KEY_ACCOUNT_ADDRESS, obj.address);
			valuePairs.put(KEY_ACCOUNT_CONTACT, obj.contact);
			valuePairs.put(KEY_ACCOUNT_DESCRIPTION, obj.description);
			valuePairs.put(KEY_ACCOUNT_RATE, obj.rate);
			valuePairs.put(KEY_ACCOUNT_TIME, obj.time);
			valuePairs.put(KEY_ACCOUNT_OPENINGTIME, obj.daytime_open);
			long rowinsert = db.update(TABLE_NAME_ACCOUNT, valuePairs,
					KEY_ACCOUNT_ID + "=" + accountId, null);
			Log.i("SUSHIL", "updated row Id: " + rowinsert);

			valuePairs.clear();

			/* Insertion in Account_Txn */
		if(obj.accountTxn!=null){
			if(obj.accountTxn.id!=-1){
			valuePairs.put(KEY_ACCOUNTTXN_ACCOUNT_ID, rowinsert);
			valuePairs.put(KEY_ACCOUNTTXN_ACCOUNT_VOUCHER_TXN_ID,"");
			valuePairs.put(KEY_ACCOUNTTXN_ACCOUNT_AMOUNT,
					obj.accountTxn.amount);
			valuePairs.put(KEY_ACCOUNTTXN_ACCOUNT_TXN_TYPE_ID,
					obj.accountTxn.txnType.id);
			valuePairs.put(KEY_ACCOUNTTXN_ACCOUNT_NARRATION,
					obj.accountTxn.narration);
			valuePairs.put(KEY_ACCOUNTTXN_ACCOUNT_ISCASH,
					obj.accountTxn.isCash);
			valuePairs.put(KEY_ACCOUNTTXN_ACCOUNT_ISINTEREST,
					obj.accountTxn.isInterest);
			valuePairs.put(KEY_ACCOUNTTXN_ACCOUNT_ISOPENING,
					obj.accountTxn.isOpening);
			
			long rowinsertTxn = db.update(TABLE_NAME_ACCOUNT_TXN, valuePairs,
					KEY_ACCOUNTTXN_ID + "=" +obj.accountTxn.id, null);
			Log.i("SUSHIL", "updated row Id: " + rowinsertTxn);
			}else{
				
				valuePairs.put(KEY_ACCOUNTTXN_ACCOUNT_ID, rowinsert);
				valuePairs.put(KEY_ACCOUNTTXN_ACCOUNT_VOUCHER_TXN_ID,"");
				valuePairs.put(KEY_ACCOUNTTXN_ACCOUNT_AMOUNT,
						obj.accountTxn.amount);
				valuePairs.put(KEY_ACCOUNTTXN_ACCOUNT_TXN_TYPE_ID,
						obj.accountTxn.txnType.id);
				valuePairs.put(KEY_ACCOUNTTXN_ACCOUNT_NARRATION,
						obj.accountTxn.narration);
				valuePairs.put(KEY_ACCOUNTTXN_ACCOUNT_ISCASH,
						obj.accountTxn.isCash);
				valuePairs.put(KEY_ACCOUNTTXN_ACCOUNT_ISINTEREST,
						obj.accountTxn.isInterest);
				valuePairs.put(KEY_ACCOUNTTXN_ACCOUNT_ISOPENING,
						obj.accountTxn.isOpening);
				
				
				long txnAccountId = db.insert(TABLE_NAME_ACCOUNT_TXN, null,
						valuePairs);
				Log.i("SUSHIL", "inserted into AccounTxn row Id: "
						+ txnAccountId);
			}
		}
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

	private Object_Account getAccountCursor2Object(Cursor cursor) {
		Object_Account obj = new Object_Account();
		obj.id = cursor.getInt(cursor.getColumnIndex(KEY_ACCOUNT_ID));
		obj.name = cursor.getString(cursor.getColumnIndex(KEY_ACCOUNT_NAME));
		obj.grpId = cursor.getInt(cursor.getColumnIndex(KEY_ACCOUNT_GRPID));
		obj.address = cursor.getString(cursor
				.getColumnIndex(KEY_ACCOUNT_ADDRESS));
		obj.contact = cursor.getString(cursor
				.getColumnIndex(KEY_ACCOUNT_CONTACT));
		obj.description = cursor.getString(cursor
				.getColumnIndex(KEY_ACCOUNT_DESCRIPTION));
		obj.rate = cursor.getDouble(cursor.getColumnIndex(KEY_ACCOUNT_RATE));
		obj.time = cursor.getDouble(cursor.getColumnIndex(KEY_ACCOUNT_TIME));
		return obj;
	}

	private Object_Account getAccountCursor2ObjectWithTxn(Cursor cursor) {
		Object_Account obj = new Object_Account();
		obj.id = cursor.getInt(cursor.getColumnIndex(KEY_ACCOUNT_MAINID));
		obj.name = cursor.getString(cursor.getColumnIndex(KEY_ACCOUNT_NAME));
		obj.grpId = cursor.getInt(cursor.getColumnIndex(KEY_ACCOUNT_GRPID));
		obj.address = cursor.getString(cursor
				.getColumnIndex(KEY_ACCOUNT_ADDRESS));
		obj.contact = cursor.getString(cursor
				.getColumnIndex(KEY_ACCOUNT_CONTACT));
		obj.description = cursor.getString(cursor
				.getColumnIndex(KEY_ACCOUNT_DESCRIPTION));
		obj.rate = cursor.getDouble(cursor.getColumnIndex(KEY_ACCOUNT_RATE));
		obj.time = cursor.getDouble(cursor.getColumnIndex(KEY_ACCOUNT_TIME));
		obj.daytime_open = cursor.getString(cursor.getColumnIndex(KEY_ACCOUNT_OPENINGTIME));
		
		Object_Account_Txn obj_txn = new Object_Account_Txn();
		obj_txn.id = cursor.getInt(cursor.getColumnIndex(KEY_ACCOUNTTXN_TXNID));
		obj_txn.amount = cursor.getDouble(cursor.getColumnIndex(KEY_ACCOUNTTXN_ACCOUNT_AMOUNT));
		obj_txn.isOpening = cursor.getInt(cursor.getColumnIndex(KEY_ACCOUNTTXN_ACCOUNT_ISOPENING));
		Object_Txn_Master objMaster = new Object_Txn_Master();
		objMaster.id = cursor.getInt(cursor.getColumnIndex(KEY_ACCOUNTTXN_ACCOUNT_TXN_TYPE_ID));
		obj_txn.txnType = objMaster;
		
		obj.accountTxn = obj_txn;
		
		return obj;
	}
	
	
	
	public ArrayList<Object_Account> getAllAccounts() {
		ArrayList<Object_Account> allAccnts = new ArrayList<Object_Account>();
		SQLiteDatabase db = this.getReadableDatabase();
		String sqlQuery = "SELECT * FROM " + TABLE_NAME_ACCOUNT +" WHERE is_active = 1 ORDER BY "
				+ KEY_ACCOUNT_NAME + " ASC";
		Cursor cursor = db.rawQuery(sqlQuery, null);
		if (cursor != null) {
			if (cursor.moveToFirst()) {
				do {
					Object_Account obj = getAccountCursor2Object(cursor);
					allAccnts.add(obj);
				} while (cursor.moveToNext());
			}
		}
		return allAccnts;
	}

	public ArrayList<Object_Account> getAccountsByGrpId(int id) {
		ArrayList<Object_Account> allAccnts = new ArrayList<Object_Account>();
		SQLiteDatabase db = this.getReadableDatabase();
		String sqlQuery = "SELECT * FROM " + TABLE_NAME_ACCOUNT + " WHERE is_active = 1 AND "
				+ KEY_ACCOUNT_GRPID + " = " + id + " ORDER BY "
				+ KEY_ACCOUNT_NAME + " ASC";
		Cursor cursor = db.rawQuery(sqlQuery, null);
		if (cursor != null) {
			if (cursor.moveToFirst()) {
				do {
					Object_Account obj = getAccountCursor2Object(cursor);
					allAccnts.add(obj);
				} while (cursor.moveToNext());
			}
		}
		return allAccnts;
	}

	public Object_Account getAccountById(int id) {
		Object_Account obj = new Object_Account();
		SQLiteDatabase db = this.getReadableDatabase();
		String sqlQuery = "SELECT *,"+TABLE_NAME_ACCOUNT+".id "+KEY_ACCOUNT_MAINID+",account_txn.id txn_id FROM " + TABLE_NAME_ACCOUNT + " LEFT JOIN "+TABLE_NAME_ACCOUNT_TXN+" on "+TABLE_NAME_ACCOUNT+"."+KEY_ACCOUNT_ID+" = "+TABLE_NAME_ACCOUNT_TXN+"."+KEY_ACCOUNTTXN_ACCOUNT_ID
				+" where is_active = 1 AND "+TABLE_NAME_ACCOUNT+"."+KEY_ACCOUNT_ID+" = "+id+" order by "+TABLE_NAME_ACCOUNT_TXN+"."+KEY_ACCOUNTTXN_ACCOUNT_ISOPENING+" Desc";
				
		//Select * from account left join account_txn on account.id = account_txn.account_id
		//where account.id = 3 order by account_txn.is_opening_balance desc
		Cursor cursor = db.rawQuery(sqlQuery, null);
		if (cursor != null) {
			if (cursor.moveToFirst()) {
				do {
					obj = getAccountCursor2ObjectWithTxn(cursor);
				} while (cursor.moveToNext());
			}
		}
		return obj;
	}

	public ArrayList<Object_Account> getItemAccounts() {
		ArrayList<Object_Account> allAccntsWOitems = this.getAccountsByGrpId(8); // GroupId
																					// of
																					// Stock-in-hand
																					// account
																					// group
																					// is
																					// 8
		ArrayList<Object_Account> allAccnts = new ArrayList<Object_Account>();
		for (Object_Account account : allAccntsWOitems) {
			Object_Account accountTemp = account;
			DBHandler_ItemCategory dbhItemCat = new DBHandler_ItemCategory(
					context);
			accountTemp.itemCategories = dbhItemCat
					.getItmCatsByAccountId(account.id);
			allAccnts.add(accountTemp);
		}
		return allAccnts;
	}

	public ArrayList<Object_Account> getAccounts(String searchStr) {
		ArrayList<Object_Account> allAccnts = new ArrayList<Object_Account>();
		SQLiteDatabase db = this.getReadableDatabase();
		String sqlQuery = "SELECT * FROM " + TABLE_NAME_ACCOUNT + " WHERE is_active = 1 AND "
				+ KEY_ACCOUNT_NAME + " LIKE '%" + searchStr + "%' "
				+ " ORDER BY " + KEY_ACCOUNT_NAME + " ASC";
		Cursor cursor = db.rawQuery(sqlQuery, null);
		if (cursor != null) {
			if (cursor.moveToFirst()) {
				do {
					Object_Account obj = getAccountCursor2Object(cursor);
					allAccnts.add(obj);
				} while (cursor.moveToNext());
			}
		}
		return allAccnts;
	}
	
  public void disableAccount(int acId){
	  SQLiteDatabase db = this.getWritableDatabase();
	  String sql = "Update "+TABLE_NAME_ACCOUNT+" Set is_active = 0 WHERE "+KEY_ACCOUNT_ID+" = "+acId;
	  db.execSQL(sql);
  }
}
