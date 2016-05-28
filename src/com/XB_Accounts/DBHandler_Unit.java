package com.XB_Accounts;

import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHandler_Unit extends SQLiteOpenHelper {

	final static String TABLE_NAME_UNIT = "unit";
	final static String KEY_UNIT_ID = "id";
	final static String KEY_UNIT_NAME = "name";
	final static String KEY_UNIT_PRINT_NAME = "print_name";
	
	public DBHandler_Unit(Context context) {
		super(context, DBHandler_Main.DB_NAME, null, DBHandler_Main.DB_VERSION);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub

	}

	public Object_Unit getUnitById(int id)
	{
		Object_Unit obj = new Object_Unit();
		
		SQLiteDatabase db = this.getReadableDatabase();
		String sqlString = "SELECT * FROM "+TABLE_NAME_UNIT+" WHERE "+KEY_UNIT_ID+" = "+id;
		Cursor cursor = db.rawQuery(sqlString, null);
		if(cursor != null)
		{
			if(cursor.moveToFirst())
			{
				do {
					obj.id = cursor.getInt(cursor.getColumnIndex(KEY_UNIT_ID));
					obj.name = cursor.getString(cursor.getColumnIndex(KEY_UNIT_NAME));
					obj.printName = cursor.getString(cursor.getColumnIndex(KEY_UNIT_PRINT_NAME));
				} while (cursor.moveToNext());
			}
		}
		return obj;
	}
	public ArrayList<Object_Unit> getAllUnits()
	{
		ArrayList<Object_Unit> allUnits = new ArrayList<Object_Unit>();
		SQLiteDatabase db = this.getReadableDatabase();
		String sqlQuery = "SELECT * FROM "+TABLE_NAME_UNIT+" ORDER BY "+KEY_UNIT_ID+" DESC";
		Cursor cursor = db.rawQuery(sqlQuery, null);
		if(cursor != null)
		{
			if(cursor.moveToFirst())
			{
				do {
					Object_Unit obj = new Object_Unit();
					obj.id = cursor.getInt(cursor.getColumnIndex(KEY_UNIT_ID));
					obj.name = cursor.getString(cursor.getColumnIndex(KEY_UNIT_NAME));
					obj.printName = cursor.getString(cursor.getColumnIndex(KEY_UNIT_PRINT_NAME));
					
					allUnits.add(obj);
				} while (cursor.moveToNext());
			}
		}
		return allUnits;
	}
}
