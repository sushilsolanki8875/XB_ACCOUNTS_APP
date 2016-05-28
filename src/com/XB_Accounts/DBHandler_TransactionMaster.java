package com.XB_Accounts;

import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHandler_TransactionMaster extends SQLiteOpenHelper {


	final static String TABLE_NAME_TXN_MASTER = "txn_master";
	final static String KEY_TXN_MASTER_ID = "id";
	final static String KEY_TXN_MASTER_NAME = "name";
	final static String KEY_TXN_MASTER_PRINTNAME = "print_name";
	
	Context context;
	public DBHandler_TransactionMaster(Context context) {
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
	
	public int getTxnId(String printString)
	{
		int txnid = 0;
		
		SQLiteDatabase db = this.getReadableDatabase();
		
		String sqlQuery = "SELECT * FORM "+TABLE_NAME_TXN_MASTER+" WHERE "+KEY_TXN_MASTER_PRINTNAME+" = "+printString;
		Cursor cursor = db.rawQuery(sqlQuery, null);
		try {
			if(cursor != null)
			{
				if(cursor.moveToFirst())
				{
					do {
						txnid = cursor.getInt(cursor.getColumnIndex(KEY_TXN_MASTER_ID));
					} while (cursor.moveToNext());
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return txnid;
	}
	public String getTxnName(int id)
	{
		String txnName = "";
		
		SQLiteDatabase db = this.getReadableDatabase();
		
		String sqlQuery = "SELECT * FORM "+TABLE_NAME_TXN_MASTER+" WHERE "+KEY_TXN_MASTER_NAME+" = "+id;
		Cursor cursor = db.rawQuery(sqlQuery, null);
		try {
			if(cursor != null)
			{
				if(cursor.moveToFirst())
				{
					do {
						txnName = cursor.getString(cursor.getColumnIndex(KEY_TXN_MASTER_NAME));
					} while (cursor.moveToNext());
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return txnName;
	}
	
	public ArrayList<Object_Txn_Master> getAllTxnTypes()
	{
		ArrayList<Object_Txn_Master> allTxnTypes = new ArrayList<Object_Txn_Master>();
		SQLiteDatabase db = this.getReadableDatabase();
		
		String sqlQuery = "SELECT * FORM "+TABLE_NAME_TXN_MASTER+" ORDER BY "+KEY_TXN_MASTER_ID+" DESC";
		Cursor cursor = db.rawQuery(sqlQuery, null);
		try {
			if(cursor != null)
			{
				if(cursor.moveToFirst())
				{
					do {
						Object_Txn_Master obj = new Object_Txn_Master();
						obj.id = cursor.getInt(cursor.getColumnIndex(KEY_TXN_MASTER_ID));
						obj.name = cursor.getString(cursor.getColumnIndex(KEY_TXN_MASTER_NAME));
						obj.printName = cursor.getString(cursor.getColumnIndex(KEY_TXN_MASTER_PRINTNAME));
						
						allTxnTypes.add(obj);
					} while (cursor.moveToNext());
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return allTxnTypes;
	}
}
