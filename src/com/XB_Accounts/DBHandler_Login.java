package com.XB_Accounts;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHandler_Login extends SQLiteOpenHelper {

	private final String TABLE_LOGIN = "Login";
	private final String KEY_LOGIN_ID = "Id";
	private final String KEY_USERNAME = "Username";
	private final String KEY_PASSWORD = "Password";
	private final String KEY_UNIQUE = "unique_id";
	Context context;

	// CREATE TABLE "Login" ("Id" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL
	// UNIQUE , "Username" TEXT, "Password" TEXT)
	public DBHandler_Login(Context context) {
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

	public Boolean isValidUser(String UserName, String Password, String UniqueId) {

		Boolean exists = false;
		String selectQuery = "SELECT * FROM " + TABLE_LOGIN + " WHERE "
				+ KEY_USERNAME + " = '" + UserName + "' AND " + KEY_PASSWORD
				+ " = '" + Password +"'";/*"' AND " + KEY_UNIQUE + " = '" + UniqueId
				+ "'";*/
		Log.i("SUSHIL", selectQuery);
		SQLiteDatabase db = this.getReadableDatabase();// myDataBase;//
		Cursor cursor = db.rawQuery(selectQuery, null);
		// looping through all rows and adding to list
		if (cursor != null)
			if (cursor.moveToFirst()) {
				exists = true;
			}

		return exists;
	}

	public void setUniqueId(String uniqueId,String UserName, String Password ) {

		SQLiteDatabase database = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(KEY_UNIQUE,uniqueId);
		database.update(TABLE_LOGIN, values, KEY_USERNAME + " = ?",
	            new String[] { UserName });
		database.close();
		isValidUser(UserName,Password,uniqueId);
		
	}

}
