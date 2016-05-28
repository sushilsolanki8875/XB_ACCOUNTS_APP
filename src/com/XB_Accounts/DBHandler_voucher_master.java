package com.XB_Accounts;

import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class DBHandler_voucher_master extends SQLiteOpenHelper {

	final static String TABLE_NAME_VOUCHER_MASTER = "voucher_master";
	final static String KEY_VOUCHER_MASTER_ID = "id";
	final static String KEY_VOUCHER_MASTER_NAME = "name";
	final static String KEY_VOUCHER_MASTER_DEF_CR_ACCNT_ID = "def_cr_account_id";
	final static String KEY_VOUCHER_MASTER_DEF_DR_ACCNT_ID = "def_dr_account_id";
	final static String KEY_VOUCHER_MASTER_SCREENTYPE = "screen_type";
	
	
	final static String KEY_JOINVAL_VMID = "vmId";
	final static String KEY_JOINVAL_VMNAME = "vmName";
	final static String KEY_JOINVAL_VMSCRNTYPE = "vmScrnType";

	final static String KEY_JOINVAL_DEFCRID = "defCrId";
	final static String KEY_JOINVAL_DEFCRNAME = "defCrName";
	final static String KEY_JOINVAL_DEFCRGRPID = "defCrGrpId";
	final static String KEY_JOINVAL_DEFCRADDRESS = "defCrAddress";
	final static String KEY_JOINVAL_DEFCRCONTACT = "defCrContact";
	final static String KEY_JOINVAL_DEFCRDESC = "defCrDesc";
	final static String KEY_JOINVAL_DEFCRDAYTIME = "defCrDaytime";

	final static String KEY_JOINVAL_DEFDRID = "defDrId";
	final static String KEY_JOINVAL_DEFDRNAME = "defDrName";
	final static String KEY_JOINVAL_DEFDRGRPID = "defDrGrpId";
	final static String KEY_JOINVAL_DEFDRADDRESS = "defDrAddress";
	final static String KEY_JOINVAL_DEFDRCONTACT = "defDrContact";
	final static String KEY_JOINVAL_DEFDRDESC = "defDrDesc";
	final static String KEY_JOINVAL_DEFDRDAYTIME = "defDrDaytime";
	
	private Context context;
	
	@Override
	public void onCreate(SQLiteDatabase db) {
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	}
	
	public DBHandler_voucher_master(Context context) {
		super(context, DBHandler_Main.DB_NAME, null, DBHandler_Main.DB_VERSION);
		this.context = context;
	}
	
	public ArrayList<Object_Voucher_Master> getAllVouchers()
	{
		ArrayList<Object_Voucher_Master> allVouchers = new ArrayList<Object_Voucher_Master>();
		
		SQLiteDatabase db = this.getReadableDatabase();
		//String sqlQuery = "SELECT * FROM "+TABLE_NAME_VOUCHER_MASTER+" ORDER BY "+KEY_VOUCHER_MASTER_ID+" ASC";
		
		String sqlQuery = 	"SELECT vm."+KEY_VOUCHER_MASTER_ID+" "+KEY_JOINVAL_VMID+",vm."+KEY_VOUCHER_MASTER_NAME+" "+KEY_JOINVAL_VMNAME+",vm."+KEY_VOUCHER_MASTER_SCREENTYPE+" "+KEY_JOINVAL_VMSCRNTYPE+","+
							"defCr."+DBHandler_Account.KEY_ACCOUNT_ID+" "+KEY_JOINVAL_DEFCRID+",defCr."+DBHandler_Account.KEY_ACCOUNT_NAME+" "+KEY_JOINVAL_DEFCRNAME+",defCr."+DBHandler_Account.KEY_ACCOUNT_GRPID+" "+KEY_JOINVAL_DEFCRGRPID+",defCr."+DBHandler_Account.KEY_ACCOUNT_ADDRESS+" "+KEY_JOINVAL_DEFCRADDRESS+",defCr."+DBHandler_Account.KEY_ACCOUNT_CONTACT+" "+KEY_JOINVAL_DEFCRCONTACT+",defCr."+DBHandler_Account.KEY_ACCOUNT_DESCRIPTION+" "+KEY_JOINVAL_DEFCRDESC+",defCr."+DBHandler_Account.KEY_ACCOUNT_DAYTIME+" "+KEY_JOINVAL_DEFCRDAYTIME+","+
							"defDr."+DBHandler_Account.KEY_ACCOUNT_ID+" "+KEY_JOINVAL_DEFDRID+",defDr."+DBHandler_Account.KEY_ACCOUNT_NAME+" "+KEY_JOINVAL_DEFDRNAME+",defDr."+DBHandler_Account.KEY_ACCOUNT_GRPID+" "+KEY_JOINVAL_DEFDRGRPID+",defDr."+DBHandler_Account.KEY_ACCOUNT_ADDRESS+" "+KEY_JOINVAL_DEFDRADDRESS+",defDr."+DBHandler_Account.KEY_ACCOUNT_CONTACT+" "+KEY_JOINVAL_DEFDRCONTACT+",defDr."+DBHandler_Account.KEY_ACCOUNT_DESCRIPTION+" "+KEY_JOINVAL_DEFDRDESC+",defDr."+DBHandler_Account.KEY_ACCOUNT_DAYTIME+" "+KEY_JOINVAL_DEFDRDAYTIME+" "+
							"FROM "+TABLE_NAME_VOUCHER_MASTER+" vm "+
							"LEFT JOIN "+DBHandler_Account.TABLE_NAME_ACCOUNT+" defCr ON defCr."+DBHandler_Account.KEY_ACCOUNT_ID+" = vm."+KEY_VOUCHER_MASTER_DEF_CR_ACCNT_ID+" "+ 
							"LEFT JOIN "+DBHandler_Account.TABLE_NAME_ACCOUNT+" defDr ON defDr."+DBHandler_Account.KEY_ACCOUNT_ID+" = vm."+KEY_VOUCHER_MASTER_DEF_DR_ACCNT_ID+" "+
							"ORDER BY vm."+KEY_VOUCHER_MASTER_ID+" ASC";
		//Log.i("accountsappdb", "sqlQuery for VoucherMaster :\n"+sqlQuery);
		
		Cursor cursor = db.rawQuery(sqlQuery, null);
		
		try {
			if(cursor != null)
			{
				if(cursor.moveToFirst())
				{
					do{
						Object_Voucher_Master obj = new Object_Voucher_Master();
						obj.id = cursor.getInt(cursor.getColumnIndex(KEY_JOINVAL_VMID));
						obj.name = cursor.getString(cursor.getColumnIndex(KEY_JOINVAL_VMNAME));
						int defCrAcntId = cursor.getInt(cursor.getColumnIndex(KEY_JOINVAL_DEFCRID));
						int defDrAcntId = cursor.getInt(cursor.getColumnIndex(KEY_JOINVAL_DEFDRID));
						if(defCrAcntId != 0)
						{
							Object_Account defCrAcnt = new Object_Account();
							defCrAcnt.id = cursor.getInt(cursor.getColumnIndex(KEY_JOINVAL_DEFCRID));
							defCrAcnt.name = cursor.getString(cursor.getColumnIndex(KEY_JOINVAL_DEFCRNAME));
							defCrAcnt.grpId = cursor.getInt(cursor.getColumnIndex(KEY_JOINVAL_DEFCRGRPID));
							defCrAcnt.address = cursor.getString(cursor.getColumnIndex(KEY_JOINVAL_DEFCRADDRESS));
							defCrAcnt.contact = cursor.getString(cursor.getColumnIndex(KEY_JOINVAL_DEFCRCONTACT));
							defCrAcnt.description = cursor.getString(cursor.getColumnIndex(KEY_JOINVAL_DEFCRDESC));
							defCrAcnt.daytime = cursor.getString(cursor.getColumnIndex(KEY_JOINVAL_DEFCRDAYTIME));
							
							obj.def_CR_account = defCrAcnt;
						}
						if(defDrAcntId != 0)
						{
							Object_Account defDrAcnt = new Object_Account();
							defDrAcnt.id = cursor.getInt(cursor.getColumnIndex(KEY_JOINVAL_DEFDRID));
							defDrAcnt.name = cursor.getString(cursor.getColumnIndex(KEY_JOINVAL_DEFDRNAME));
							defDrAcnt.grpId = cursor.getInt(cursor.getColumnIndex(KEY_JOINVAL_DEFDRGRPID));
							defDrAcnt.address = cursor.getString(cursor.getColumnIndex(KEY_JOINVAL_DEFDRADDRESS));
							defDrAcnt.contact = cursor.getString(cursor.getColumnIndex(KEY_JOINVAL_DEFDRCONTACT));
							defDrAcnt.description = cursor.getString(cursor.getColumnIndex(KEY_JOINVAL_DEFDRDESC));
							defDrAcnt.daytime = cursor.getString(cursor.getColumnIndex(KEY_JOINVAL_DEFDRDAYTIME));
							
							obj.def_DR_account = defDrAcnt;
						}
						obj.screenType = cursor.getString(cursor.getColumnIndex(KEY_JOINVAL_VMSCRNTYPE));
						
						allVouchers.add(obj);
					}while(cursor.moveToNext());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return allVouchers;
	}
	
	/*public AA_Object_Voucher_2 getVoucherById(int Id)
	 {
	  SQLiteDatabase db = this.getReadableDatabase();
	  String sqlQuery = "SELECT * FROM "+TABLE_NAME_VOUCHER_MASTER+" WHERE "+KEY_VOUCHER_MASTER_ID+" = "+Id;
	  
	  Cursor cursor = db.rawQuery(sqlQuery, null);
	  AA_Object_Voucher_2 obj = null;
	  try {
	   if(cursor != null)
	   {
	    if(cursor.moveToFirst())
	    {
	     do{
	      obj = new AA_Object_Voucher_2();
	      obj.id = cursor.getInt(cursor.getColumnIndex(KEY_VOUCHER_MASTER_ID));
	      obj.name = cursor.getString(cursor.getColumnIndex(KEY_VOUCHER_MASTER_NAME));
	      obj.def_cr_accnt_id = cursor.getInt(cursor.getColumnIndex(KEY_VOUCHER_MASTER_DEF_CR_ACCNT_ID));
	      obj.def_dr_accnt_id = cursor.getInt(cursor.getColumnIndex(KEY_VOUCHER_MASTER_DEF_DR_ACCNT_ID));
	      obj.screentype = cursor.getString(cursor.getColumnIndex(KEY_VOUCHER_MASTER_SCREENTYPE));
	     }while(cursor.moveToNext());
	    }
	   }
	  } catch (Exception e) {
	   e.printStackTrace();
	  }
	  
	  return obj;
	 }*/
}
