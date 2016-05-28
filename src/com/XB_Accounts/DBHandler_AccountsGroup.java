package com.XB_Accounts;

import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHandler_AccountsGroup extends SQLiteOpenHelper {

final static String TABLE_NAME_ACCOUNT_GRP = "account_grp";
	
	final static String KEY_ACCOUNTGRP_ID = "id";
	final static String KEY_ACCOUNTGRP_NAME = "name";
	final static String KEY_ACCOUNTGRP_PARENTID = "parent_id";
	final static String KEY_ACCOUNTGRP_description = "description";
	final static String KEY_ACCOUNTGRP_DAYTIME = "daytime";
	
	Context context;
	public DBHandler_AccountsGroup(Context context) {
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

	public ArrayList<Object_Account_Group> getAllAccountGrp()
	{
		ArrayList<Object_Account_Group> allAccntGrp = new ArrayList<Object_Account_Group>();
		SQLiteDatabase db = this.getReadableDatabase();
		/*String sqlQuery = 	"SELECT subgrp.id subId,subgrp.name subName,subgrp.parent_id subParentId,subgrp.description subDesc,subgrp.daytime subDaytime,"+
							"maingrp.id mainId,maingrp.name mainName,maingrp.parent_id mainParentId,maingrp.description mainDesc,maingrp.daytime mainDaytime "+
							"FROM account_grp subgrp "+
							"INNER JOIN account_grp maingrp ON subgrp.parent_id = maingrp.id "+ 
							"WHERE subgrp.parent_id != 0";*/
		String sqlQueryMainGrp = "SELECT * FROM "+TABLE_NAME_ACCOUNT_GRP+" WHERE "+KEY_ACCOUNTGRP_PARENTID+" = 0";
		Cursor cursorMainGrp = db.rawQuery(sqlQueryMainGrp, null);
		if(cursorMainGrp != null)
		{
			if(cursorMainGrp.moveToFirst())
			{
				do {
					Object_Account_Group mainAccntGrp = new Object_Account_Group();
					mainAccntGrp.id = cursorMainGrp.getInt(cursorMainGrp.getColumnIndex(KEY_ACCOUNTGRP_ID));
					mainAccntGrp.name = cursorMainGrp.getString(cursorMainGrp.getColumnIndex(KEY_ACCOUNTGRP_NAME));
					mainAccntGrp.parentId = cursorMainGrp.getInt(cursorMainGrp.getColumnIndex(KEY_ACCOUNTGRP_PARENTID));
					mainAccntGrp.description = cursorMainGrp.getString(cursorMainGrp.getColumnIndex(KEY_ACCOUNTGRP_description));
					mainAccntGrp.daytime = cursorMainGrp.getString(cursorMainGrp.getColumnIndex(KEY_ACCOUNTGRP_DAYTIME));
					
						String sqlQuerySubGrp = "SELECT * FROM "+TABLE_NAME_ACCOUNT_GRP+" WHERE "+KEY_ACCOUNTGRP_PARENTID+" = "+mainAccntGrp.id;
						Cursor cursorSubGrp = db.rawQuery(sqlQuerySubGrp, null);
						ArrayList<Object_Account_Group> subGrpList = new ArrayList<Object_Account_Group>();
						if(cursorSubGrp != null)
						{
							if(cursorSubGrp.moveToFirst())
							{
								do {
									 int subAcGrpId = cursorSubGrp.getInt(cursorSubGrp.getColumnIndex(KEY_ACCOUNTGRP_ID));
							         if(subAcGrpId != 8)
							         {
											Object_Account_Group subAccntGrp = new Object_Account_Group();
											subAccntGrp.id = cursorSubGrp.getInt(cursorSubGrp.getColumnIndex(KEY_ACCOUNTGRP_ID));
											subAccntGrp.name = cursorSubGrp.getString(cursorSubGrp.getColumnIndex(KEY_ACCOUNTGRP_NAME));
											subAccntGrp.parentId = cursorSubGrp.getInt(cursorSubGrp.getColumnIndex(KEY_ACCOUNTGRP_PARENTID));
											subAccntGrp.description = cursorSubGrp.getString(cursorSubGrp.getColumnIndex(KEY_ACCOUNTGRP_description));
											subAccntGrp.daytime = cursorSubGrp.getString(cursorSubGrp.getColumnIndex(KEY_ACCOUNTGRP_DAYTIME));
											
												DBHandler_Account dbhAccnt = new DBHandler_Account(context);
												subAccntGrp.accounts = dbhAccnt.getAccountsByGrpId(subAccntGrp.id);
											
											subGrpList.add(subAccntGrp);
							         }
								} while (cursorSubGrp.moveToNext());
							}
						}
					mainAccntGrp.accountGroups = subGrpList;
						DBHandler_Account dbhAccnt = new DBHandler_Account(context);
						mainAccntGrp.accounts = dbhAccnt.getAccountsByGrpId(mainAccntGrp.id);
					allAccntGrp.add(mainAccntGrp);
				} while (cursorMainGrp.moveToNext());
			}
		}
		return allAccntGrp;
	}

	
	
}
