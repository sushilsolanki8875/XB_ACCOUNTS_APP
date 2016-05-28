package com.XB_Accounts;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHandler_Txn extends SQLiteOpenHelper {

	final static String TABLE_NAME_VOUCHER_TXN = "voucher_txn";
	final static String TABLE_NAME_ACCOUNT_TXN = "account_txn";
	final static String TABLE_NAME_ITEM_TXN = "item_txn";
	final static String TABLE_NAME_ACCOUNT = "account";

	final static String KEY_VOUCHER_TXN_ID = "id";
	final static String KEY_VOUCHER_VOUCHER_ID = "voucher_id";
	final static String KEY_VOUCHER_DAYTIME = "daytime";

	final static String KEY_ACCOUNTTXN_ID = "id";
	final static String KEY_ACCOUNTTXN_ACCOUNT_ID = "account_id";
	final static String KEY_ACCOUNTTXN_ACCOUNT_VOUCHER_TXN_ID = "voucher_txn_id";
	final static String KEY_ACCOUNTTXN_ACCOUNT_AMOUNT = "amount";
	final static String KEY_ACCOUNTTXN_ACCOUNT_TXN_TYPE_ID = "txn_type_id";
	final static String KEY_ACCOUNTTXN_ACCOUNT_NARRATION = "narration";
	final static String KEY_ACCOUNTTXN_ACCOUNT_ISCASH = "is_cash";
	final static String KEY_ACCOUNTTXN_ACCOUNT_ISINTEREST = "is_interest";
	final static String KEY_ACCOUNTTXN_ACCOUNT_ISOPEN = "is_opening_balance";

	final static String KEY_ITEMTXN_ID = "id";
	final static String KEY_ITEMTXN_ITEM_ID = "item_id";
	final static String KEY_ITEMTXN_QTY = "qty";
	final static String KEY_ITEMTXN_PRICE = "price";
	final static String KEY_ITEMTXN_AMOUNT = "amount";
	final static String KEY_ITEMTXN_TXN_ACCOUNT_ID = "txn_account_id";

	final static String KEY_JOINVAL_ATXNID = "atxnId";
	final static String KEY_JOINVAL_ATXNACCNTID = "atxnAccntId";
	final static String KEY_JOINVAL_ATXNVTXNID = "atxnVtxnId";
	final static String KEY_JOINVAL_ATXNAMOUNT = "atxnAmount";
	final static String KEY_JOINVAL_ATXNTYPEID = "atxnTypeId";
	final static String KEY_JOINVAL_ATXNNARRATION = "atxnNarration";
	final static String KEY_JOINVAL_ATXNISCASH = "atxnIsCash";

	final static String KEY_JOINVAL_TXNMSTRID = "tmId";
	final static String KEY_JOINVAL_TXNMSTRNAME = "tmName";
	final static String KEY_JOINVAL_TXNMSTRPRINTNAME = "tmPrntName";

	final static String KEY_JOINVAL_ACCNTID = "acntId";
	final static String KEY_JOINVAL_ACCNTNAME = "acntName";
	final static String KEY_JOINVAL_ACCNTGRPID = "acntGrpId";
	final static String KEY_JOINVAL_ACCNTADDRESS = "acntAddress";
	final static String KEY_JOINVAL_ACCNTCONTACT = "acntContact";
	final static String KEY_JOINVAL_ACCNTDESC = "acntDesc";
	final static String KEY_JOINVAL_ACCNTDAYTIME = "acntDaytime";

	final static String KEY_JOINVAL_SUBGRPID = "subGrpId";
	final static String KEY_JOINVAL_SUBGRPNAME = "subGrpName";
	final static String KEY_JOINVAL_SUBGRPPARENTID = "subGrpPrntId";
	final static String KEY_JOINVAL_SUBGRPDESC = "subGrpDesc";
	final static String KEY_JOINVAL_SUBGRPDAYTIME = "subGrpDaytime";

	final static String KEY_JOINVAL_MAINGRPID = "mainGrpId";
	final static String KEY_JOINVAL_MAINGRPNAME = "mainGrpName";
	final static String KEY_JOINVAL_MAINGRPPARENTID = "mainGrpPrntId";
	final static String KEY_JOINVAL_MAINGRPDESC = "mainGrpDesc";
	final static String KEY_JOINVAL_MAINGRPDAYTIME = "mainGrpDaytime";

	final static String KEY_JOINVAL_ITMTXNID = "itmTxnId";
	final static String KEY_JOINVAL_ITMTXNQTY = "itmTxnQty";
	final static String KEY_JOINVAL_ITMTXNPRICE = "itmTxnPrice";
	final static String KEY_JOINVAL_ITMTXNAMOUNT = "itmTxnPrice";

	final static String KEY_JOINVAL_ITMID = "itmId";
	final static String KEY_JOINVAL_ITMNAME = "itmName";
	final static String KEY_JOINVAL_ITMDESC = "itmDesc";
	final static String KEY_JOINVAL_ITMPRINTNAME = "itmPrntName";
	final static String KEY_JOINVAL_ITMSALEPRICE = "itmSaleP";
	final static String KEY_JOINVAL_ITMPURCPRICE = "itmPurcP";
	final static String KEY_JOINVAL_ITMMRP = "itmMrp";
	final static String KEY_JOINVAL_ITMMINSALEPRICE = "itmMinSaleP";
	final static String KEY_JOINVAL_ITMDAYTIME = "itmDaytime";
	final static String KEY_JOINVAL_ITMQTYREM = "itmQtyRem";

	final static String KEY_JOINVAL_UNITID = "untId";
	final static String KEY_JOINVAL_UNITNAME = "untName";
	final static String KEY_JOINVAL_UNITPRINTNAME = "untPrntName";

	final static String KEY_JOINVAL_ITMCATID = "itmCatId";
	final static String KEY_JOINVAL_ITMCATNAME = "itmCatName";
	final static String KEY_JOINVAL_ITMCATACCNTID = "itmCatAccntId";
	final static String KEY_JOINVAL_ITMCATDESC = "itmCatDesc";
	final static String KEY_JOINVAL_ITMCATDAYTIME = "itmCatDaytime";

	final static String KEY_BALANCE_ID = "id";
	final static String KEY_BALANCE_NAME = "name";
	final static String KEY_BALANCE_DEBIT = "Debit";
	final static String KEY_BALANCE_CREDIT = "Credit";
	final static String KEY_BALANCE_ACCOUNT_ID = "account_id";

	final static String KEY_JOINVAL_SHEET_ITMID = "id";
	final static String KEY_JOINVAL_SHEET_TXN_ID = "txn_type_id";
	final static String KEY_JOINVAL_SHEET_ITMNAME = "name";
	final static String KEY_JOINVAL_SHEET_SALEQTY = "sale_qty";
	final static String KEY_JOINVAL_SHEET_PURQTY = "purchase_qty";
	final static String KEY_JOINVAL_SHEET_SALEPRICE = "sale_price";
	final static String KEY_JOINVAL_SHEET_PURPRICE = "purchase_price";
	final static String KEY_JOINVAL_SHEET_UNITNAME = "unitname";

	Context context;

	public DBHandler_Txn(Context context) {
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

	/*
	 * public boolean createNewTxn(AA_Object_Txn_2 txnDetail) { SQLiteDatabase
	 * db = this.getWritableDatabase(); boolean status = false;
	 * db.beginTransaction(); try { ContentValues valuePairs = new
	 * ContentValues();
	 * 
	 * valuePairs.put(KEY_VOUCHER_VOUCHER_ID, txnDetail.voucherId);
	 * valuePairs.put(KEY_VOUCHER_DAYTIME, txnDetail.dayTime);
	 * 
	 * long voucherTxnInsertId = db.insert(TABLE_NAME_VOUCHER_TXN, null,
	 * valuePairs); Log.i("accountsappdb",
	 * "inserted into Voucher_TXn row Id: "+voucherTxnInsertId);
	 * 
	 * 
	 * for(AA_Object_AccountTxn_2 obj : txnDetail.accountTxn) {
	 * valuePairs.clear(); valuePairs.put(KEY_TXN_DETAIL_AMOUNT,obj.amount);
	 * valuePairs.put(KEY_TXN_DETAIL_TXN_TYPE_ID, obj.txnTypeId);
	 * valuePairs.put(KEY_TXN_DETAIL_NARRATION, obj.narration); long
	 * txnDetailInsertId = db.insert(TABLE_NAME_TXN_DETAIL, null, valuePairs);
	 * Log.i("accountsappdb", "inserted txn_detail row Id: "+txnDetailInsertId);
	 * 
	 * valuePairs.clear(); valuePairs.put(KEY_TXN_ACCOUNT_ACCOUNT_ID,
	 * obj.accountId); valuePairs.put(KEY_TXN_ACCOUNT_TXN_DETAIL,
	 * txnDetailInsertId); valuePairs.put(KEY_TXN_ACCOUNT_VOUCHER_TXN_ID,
	 * voucherTxnInsertId); long txnAccountInsertId =
	 * db.insert(TABLE_NAME_TXN_ACCOUNT, null, valuePairs);
	 * Log.i("accountsappdb",
	 * "inserted txn_detail row Id: "+txnAccountInsertId); }
	 * 
	 * for(AA_Object_ItemTxn_2 obj : txnDetail.itemTxn) { valuePairs.clear();
	 * valuePairs.put(KEY_TXN_DETAIL_AMOUNT,obj.amount);
	 * valuePairs.put(KEY_TXN_DETAIL_TXN_TYPE_ID, obj.txnTypeId);
	 * valuePairs.put(KEY_TXN_DETAIL_NARRATION, obj.narration); long
	 * txnDetailInsertId = db.insert(TABLE_NAME_TXN_DETAIL, null, valuePairs);
	 * Log.i("accountsappdb", "inserted txn_detail row Id: "+txnDetailInsertId);
	 * 
	 * valuePairs.clear(); valuePairs.put(KEY_TXN_ITEM_ITEM_ID, obj.itemId);
	 * valuePairs.put(KEY_TXN_ITEM_QTY, obj.qty);
	 * valuePairs.put(KEY_TXN_ITEM_PRICE, obj.price);
	 * valuePairs.put(KEY_TXN_ITEM_TXN_DETAIL_ID, txnDetailInsertId);
	 * valuePairs.put(KEY_TXN_ITEM_VOUCHER_TXN_ID,voucherTxnInsertId); long
	 * txnItemInsertId = db.insert(TABLE_NAME_TXN_ACCOUNT, null, valuePairs);
	 * Log.i("accountsappdb", "inserted txn_detail row Id: "+txnItemInsertId);
	 * 
	 * DBHandler_Items dbhItmDetail = new DBHandler_Items(context);
	 * AA_Object_Items_2 itmDetail = dbhItmDetail.getItem(obj.itemId); int
	 * remItms = itmDetail.qtyremaining; int remStock = remItms - obj.qty;
	 * 
	 * valuePairs.clear(); }
	 * 
	 * db.setTransactionSuccessful(); status = true; } catch (Exception e) { //
	 * TODO: handle exception e.printStackTrace(); } finally {
	 * db.endTransaction(); } return status; }
	 */

	public boolean createNewTxn(Object_Voucher_Txn txnDetail) {
		boolean status = false;
		SQLiteDatabase db = this.getWritableDatabase();
		db.beginTransaction();
		try {
			ContentValues valuePairs = new ContentValues();

			/* Insertion in Voucher_Txn */
			valuePairs.put(KEY_VOUCHER_DAYTIME, txnDetail.daytime);
			valuePairs.put(KEY_VOUCHER_VOUCHER_ID, txnDetail.voucher.id);

			long voucherTxnInsertId = db.insert(TABLE_NAME_VOUCHER_TXN, null,
					valuePairs);
			Log.i("accountsappdb", "inserted into Voucher_TXn row Id: "
					+ voucherTxnInsertId);

			for (Object_Account account : txnDetail.accounts) {
				valuePairs.clear();

				/* Insertion in Account_Txn */
				valuePairs.put(KEY_ACCOUNTTXN_ACCOUNT_ID, account.id);
				valuePairs.put(KEY_ACCOUNTTXN_ACCOUNT_VOUCHER_TXN_ID,
						voucherTxnInsertId);
				valuePairs.put(KEY_ACCOUNTTXN_ACCOUNT_AMOUNT,
						account.accountTxn.amount);
				valuePairs.put(KEY_ACCOUNTTXN_ACCOUNT_TXN_TYPE_ID,
						account.accountTxn.txnType.id);
				valuePairs.put(KEY_ACCOUNTTXN_ACCOUNT_NARRATION,
						account.accountTxn.narration);
				valuePairs.put(KEY_ACCOUNTTXN_ACCOUNT_ISCASH,
						account.accountTxn.isCash);
				valuePairs.put(KEY_ACCOUNTTXN_ACCOUNT_ISINTEREST,
						account.accountTxn.isInterest);

				long txnAccountId = db.insert(TABLE_NAME_ACCOUNT_TXN, null,
						valuePairs);
				Log.i("accountsappdb", "inserted into AccounTxn row Id: "
						+ txnAccountId);
				if (account.itemCategories != null) {
					for (Object_Item_Category itemCat : account.itemCategories) {
						for (Object_Item item : itemCat.items) {
							valuePairs.clear();

							valuePairs.put(KEY_ITEMTXN_ITEM_ID, item.id);
							valuePairs.put(KEY_ITEMTXN_QTY, item.itemTxn.qty);
							valuePairs.put(KEY_ITEMTXN_PRICE,
									item.itemTxn.price);
							valuePairs.put(KEY_ITEMTXN_AMOUNT,
									item.itemTxn.amount);
							valuePairs.put(KEY_ITEMTXN_TXN_ACCOUNT_ID,
									txnAccountId);

							DBHandler_Item itm = new DBHandler_Item(context);
							itm.updateItemStock(db, item.id, item.itemTxn.qty,
									account.accountTxn.txnType.id);

							long txnItemid = db.insert(TABLE_NAME_ITEM_TXN,
									null, valuePairs);
							Log.i("accountsappdb",
									"inserted into Item_Txn row Id: "
											+ txnItemid);
						}
					}
				}
			}
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

	public boolean updateTxn(Object_Voucher_Txn txnDetail,
			long voucherTxnInsertId) {
		boolean status = false;
		SQLiteDatabase db = this.getWritableDatabase();
		db.beginTransaction();
		ContentValues valuePairs = new ContentValues();
		try {
			/* Insertion in Voucher_Txn */
			valuePairs.put(KEY_VOUCHER_DAYTIME, txnDetail.daytime);
			valuePairs.put(KEY_VOUCHER_VOUCHER_ID, txnDetail.voucher.id);

			// db.update(TABLE_NAME_VOUCHER_TXN, null, valuePairs);
			db.update(TABLE_NAME_VOUCHER_TXN, valuePairs, KEY_VOUCHER_TXN_ID
					+ " = " + voucherTxnInsertId, null);
			// Log.i("accountsappdb",
			// "inserted into Voucher_TXn row Id: "+voucherTxnInsertId);

			for (Object_Account account : txnDetail.accounts) {
				valuePairs.clear();

				/* Insertion in Account_Txn */
				valuePairs.put(KEY_ACCOUNTTXN_ACCOUNT_ID, account.id);
				valuePairs.put(KEY_ACCOUNTTXN_ACCOUNT_VOUCHER_TXN_ID,
						voucherTxnInsertId);
				valuePairs.put(KEY_ACCOUNTTXN_ACCOUNT_AMOUNT,
						account.accountTxn.amount);
				valuePairs.put(KEY_ACCOUNTTXN_ACCOUNT_TXN_TYPE_ID,
						account.accountTxn.txnType.id);
				valuePairs.put(KEY_ACCOUNTTXN_ACCOUNT_NARRATION,
						account.accountTxn.narration);
				valuePairs.put(KEY_ACCOUNTTXN_ACCOUNT_ISCASH,
						account.accountTxn.isCash);

				long txnAccountId = db.update(TABLE_NAME_ACCOUNT_TXN,
						valuePairs, KEY_ACCOUNTTXN_ID + " = "
								+ account.accountTxn.id, null);
				// Log.i("accountsappdb",
				// "inserted into AccounTxn row Id: "+txnAccountId);
				if (account.itemCategories != null) {
					for (Object_Item_Category itemCat : account.itemCategories) {
						for (Object_Item item : itemCat.items) {
							valuePairs.clear();

							valuePairs.put(KEY_ITEMTXN_ITEM_ID, item.id);
							valuePairs.put(KEY_ITEMTXN_QTY, item.itemTxn.qty);
							valuePairs.put(KEY_ITEMTXN_PRICE,
									item.itemTxn.price);
							valuePairs.put(KEY_ITEMTXN_AMOUNT,
									item.itemTxn.amount);
							valuePairs.put(KEY_ITEMTXN_TXN_ACCOUNT_ID,
									txnAccountId);

							DBHandler_Item itm = new DBHandler_Item(context);
							itm.updateItemStock(db, item.id, item.itemTxn.qty,
									account.accountTxn.txnType.id);

							long txnItemid = db.update(TABLE_NAME_ITEM_TXN,
									valuePairs, KEY_ITEMTXN_ID + " = "
											+ item.itemTxn.id, null);
							// Log.i("accountsappdb",
							// "inserted into Item_Txn row Id: "+txnItemid);
						}
					}
				}
			}
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

	public ArrayList<Object_SheetPurchase> getPurchaseSaleItems() {

		ArrayList<Object_SheetPurchase> list = new ArrayList<Object_SheetPurchase>();
		SQLiteDatabase db = this.getWritableDatabase();
		/*
		 * SELECT t2.item_id as id,t2.name as name, (CASE WHEN t2.txn_type_id =1
		 * THEN t2.totalqty END) sale_qty, (CASE WHEN t2.txn_type_id =1 THEN
		 * t2.price END) sale_price, (CASE WHEN t2.txn_type_id =2 THEN
		 * t2.totalqty END) purchase_qty, (CASE WHEN t2.txn_type_id =2 THEN
		 * t2.price END) purchase_price, t2.unitname as unitname ,t2.txn_type_id
		 * as txn_type_id FROM (SELECT i.id as
		 * item_id,i.name,i.sale_price,i.qty_remaining,u.name as
		 * unitname,sum(t1.qty)
		 * totalqty,t1.price,t1.txn_account_id,t1.txn_type_id,t1.narration FROM
		 * item i LEFT JOIN (SELECT
		 * itxn.item_id,itxn.qty,itxn.price,itxn.txn_account_id
		 * ,atxn.txn_type_id,atxn.narration FROM item_txn itxn LEFT JOIN
		 * account_txn atxn ON atxn.id = itxn.txn_account_id GROUP BY
		 * itxn.price,itxn.item_id,atxn.txn_type_id,itxn.txn_account_id) AS t1
		 * ON t1.item_id = i.id LEFT JOIN unit u ON u.id = i.unit_id GROUP BY
		 * i.id,t1.price,t1.txn_type_id) as t2
		 */
		String sql = "SELECT t2.item_id as id,t2.name as name,(CASE WHEN t2.txn_type_id =1 THEN t2.totalqty END) sale_qty,(CASE WHEN t2.txn_type_id =1 THEN t2.price END) sale_price,"
				+ " (CASE WHEN t2.txn_type_id =2 THEN t2.totalqty END) purchase_qty,(CASE WHEN t2.txn_type_id =2 THEN t2.price END) purchase_price,t2.unitname as unitname ,t2.txn_type_id as txn_type_id"
				+ " FROM (SELECT i.id as item_id,i.name,i.sale_price,i.qty_remaining,u.name as unitname,sum(t1.qty) totalqty,t1.price,t1.txn_account_id,t1.txn_type_id,t1.narration FROM item i "
				+ "LEFT JOIN (SELECT itxn.item_id,itxn.qty,itxn.price,itxn.txn_account_id,atxn.txn_type_id,atxn.narration FROM item_txn itxn "
				+ "LEFT JOIN account_txn atxn ON atxn.id = itxn.txn_account_id GROUP BY itxn.price,itxn.item_id,atxn.txn_type_id,itxn.txn_account_id) AS t1 "
				+ "ON t1.item_id = i.id LEFT JOIN unit u ON u.id = i.unit_id GROUP BY i.id,t1.price,t1.txn_type_id) as t2";

		Log.i("SUSHIL", "query is /n " + sql);
		Cursor cursor = db.rawQuery(sql, null);
		if (cursor != null) {
			if (cursor.moveToFirst()) {
				do {
					Object_SheetPurchase obj = new Object_SheetPurchase();
					obj.id = cursor.getInt(cursor
							.getColumnIndex(KEY_JOINVAL_SHEET_ITMID));
					obj.nameItem = cursor.getString(cursor
							.getColumnIndex(KEY_JOINVAL_SHEET_ITMNAME));
					obj.unitName = cursor.getString(cursor
							.getColumnIndex(KEY_JOINVAL_SHEET_UNITNAME));
					obj.purchaseqty = cursor.getInt(cursor
							.getColumnIndex(KEY_JOINVAL_SHEET_PURQTY));
					obj.saleqty = cursor.getInt(cursor
							.getColumnIndex(KEY_JOINVAL_SHEET_SALEQTY));
					obj.purchaseprice = cursor.getDouble(cursor
							.getColumnIndex(KEY_JOINVAL_SHEET_PURPRICE));
					obj.saleprice = cursor.getDouble(cursor
							.getColumnIndex(KEY_JOINVAL_SHEET_SALEPRICE));
					obj.txnType_id = cursor.getInt(cursor
							.getColumnIndex(KEY_JOINVAL_SHEET_TXN_ID));

					list.add(obj);

				} while (cursor.moveToNext());
			}
		}
		db.close();
		return list;
	}

	public ArrayList<Object_Account_Group> getDailyLedger(String date,
			int txnTypeId) {
		ArrayList<Object_Account_Group> allAcGroups = new ArrayList<Object_Account_Group>();
		SQLiteDatabase db = this.getReadableDatabase();
		String sqlQueryVchTxn = "SELECT  vtxn." + KEY_VOUCHER_TXN_ID
				+ " vtxnId,vtxn." + KEY_VOUCHER_DAYTIME + " vtxndaytime,"
				+ "vm." + DBHandler_voucher_master.KEY_VOUCHER_MASTER_ID
				+ " vmId,vm."
				+ DBHandler_voucher_master.KEY_VOUCHER_MASTER_NAME
				+ " vmName,vm."
				+ DBHandler_voucher_master.KEY_VOUCHER_MASTER_SCREENTYPE
				+ " vmScrnType " + "FROM " + TABLE_NAME_VOUCHER_TXN + " vtxn "
				+ "INNER JOIN "
				+ DBHandler_voucher_master.TABLE_NAME_VOUCHER_MASTER
				+ " vm ON vtxn." + KEY_VOUCHER_VOUCHER_ID + " = vm."
				+ DBHandler_voucher_master.KEY_VOUCHER_MASTER_ID + " "
				+ "WHERE " + KEY_VOUCHER_DAYTIME + " LIKE '" + date + "%' ";
		Log.i("SUSHIL", "sqlQueryAccntList: \n" + sqlQueryVchTxn);
		Cursor vchTxnCursor = db.rawQuery(sqlQueryVchTxn, null);
		try {
			if (vchTxnCursor != null) {
				if (vchTxnCursor.moveToFirst()) {
					do {
						Object_Voucher_Txn objTxn = new Object_Voucher_Txn();
						objTxn.id = vchTxnCursor.getInt(vchTxnCursor
								.getColumnIndex("vtxnId"));
						objTxn.daytime = vchTxnCursor.getString(vchTxnCursor
								.getColumnIndex("vtxndaytime"));

						Object_Voucher_Master vm4objTxn = new Object_Voucher_Master();
						vm4objTxn.id = vchTxnCursor.getInt(vchTxnCursor
								.getColumnIndex("vmId"));
						vm4objTxn.name = vchTxnCursor.getString(vchTxnCursor
								.getColumnIndex("vmName"));
						vm4objTxn.screenType = vchTxnCursor
								.getString(vchTxnCursor
										.getColumnIndex("vmScrnType"));

						objTxn.voucher = vm4objTxn;

						objTxn.accounts = getAccountTxn(objTxn.id, txnTypeId);

						String sqlQueryAccntTxn = "SELECT atxn."
								+ KEY_ACCOUNTTXN_ID
								+ " "
								+ KEY_JOINVAL_ATXNID
								+ ",atxn."
								+ KEY_ACCOUNTTXN_ACCOUNT_ID
								+ " "
								+ KEY_JOINVAL_ATXNACCNTID
								+ ",atxn."
								+ KEY_ACCOUNTTXN_ACCOUNT_VOUCHER_TXN_ID
								+ " "
								+ KEY_JOINVAL_ATXNVTXNID
								+ ",atxn."
								+ KEY_ACCOUNTTXN_ACCOUNT_AMOUNT
								+ " "
								+ KEY_JOINVAL_ATXNAMOUNT
								+ ",atxn."
								+ KEY_ACCOUNTTXN_ACCOUNT_TXN_TYPE_ID
								+ " "
								+ KEY_JOINVAL_ATXNTYPEID
								+ ",atxn."
								+ KEY_ACCOUNTTXN_ACCOUNT_NARRATION
								+ " "
								+ KEY_JOINVAL_ATXNNARRATION
								+ ",atxn."
								+ KEY_ACCOUNTTXN_ACCOUNT_ISCASH
								+ " "
								+ KEY_JOINVAL_ATXNISCASH
								+ ", "
								+ "acnt."
								+ DBHandler_Account.KEY_ACCOUNT_ID
								+ " "
								+ KEY_JOINVAL_ACCNTID
								+ ",acnt."
								+ DBHandler_Account.KEY_ACCOUNT_NAME
								+ " "
								+ KEY_JOINVAL_ACCNTNAME
								+ ",acnt."
								+ DBHandler_Account.KEY_ACCOUNT_GRPID
								+ " "
								+ KEY_JOINVAL_ACCNTGRPID
								+ ",acnt."
								+ DBHandler_Account.KEY_ACCOUNT_ADDRESS
								+ " "
								+ KEY_JOINVAL_ACCNTADDRESS
								+ ",acnt."
								+ DBHandler_Account.KEY_ACCOUNT_CONTACT
								+ " "
								+ KEY_JOINVAL_ACCNTCONTACT
								+ ",acnt."
								+ DBHandler_Account.KEY_ACCOUNT_DESCRIPTION
								+ " "
								+ KEY_JOINVAL_ACCNTDESC
								+ ",acnt."
								+ DBHandler_Account.KEY_ACCOUNT_DAYTIME
								+ " "
								+ KEY_JOINVAL_ACCNTDAYTIME
								+ ","
								+ "agrp1."
								+ DBHandler_AccountsGroup.KEY_ACCOUNTGRP_ID
								+ " "
								+ KEY_JOINVAL_SUBGRPID
								+ ",agrp1."
								+ DBHandler_AccountsGroup.KEY_ACCOUNTGRP_NAME
								+ " "
								+ KEY_JOINVAL_SUBGRPNAME
								+ ",agrp1."
								+ DBHandler_AccountsGroup.KEY_ACCOUNTGRP_PARENTID
								+ " "
								+ KEY_JOINVAL_SUBGRPPARENTID
								+ ",agrp1."
								+ DBHandler_AccountsGroup.KEY_ACCOUNTGRP_description
								+ " "
								+ KEY_JOINVAL_SUBGRPDESC
								+ ",agrp1."
								+ DBHandler_AccountsGroup.KEY_ACCOUNTGRP_DAYTIME
								+ " "
								+ KEY_JOINVAL_SUBGRPDAYTIME
								+ ","
								+ "agrp2."
								+ DBHandler_AccountsGroup.KEY_ACCOUNTGRP_ID
								+ " "
								+ KEY_JOINVAL_MAINGRPID
								+ ",agrp2."
								+ DBHandler_AccountsGroup.KEY_ACCOUNTGRP_NAME
								+ " "
								+ KEY_JOINVAL_MAINGRPNAME
								+ ",agrp2."
								+ DBHandler_AccountsGroup.KEY_ACCOUNTGRP_PARENTID
								+ " "
								+ KEY_JOINVAL_MAINGRPPARENTID
								+ ",agrp2."
								+ DBHandler_AccountsGroup.KEY_ACCOUNTGRP_description
								+ " "
								+ KEY_JOINVAL_MAINGRPDESC
								+ ",agrp2."
								+ DBHandler_AccountsGroup.KEY_ACCOUNTGRP_DAYTIME
								+ " "
								+ KEY_JOINVAL_MAINGRPDAYTIME
								+ " "
								+ " FROM "
								+ TABLE_NAME_ACCOUNT_TXN
								+ " atxn "
								+ "INNER JOIN "
								+ DBHandler_Account.TABLE_NAME_ACCOUNT
								+ " acnt ON acnt."
								+ DBHandler_Account.KEY_ACCOUNT_ID
								+ " = atxn."
								+ KEY_ACCOUNTTXN_ACCOUNT_ID
								+ " "
								+ "LEFT JOIN "
								+ DBHandler_AccountsGroup.TABLE_NAME_ACCOUNT_GRP
								+ " agrp1 ON agrp1."
								+ DBHandler_AccountsGroup.KEY_ACCOUNTGRP_ID
								+ " = acnt."
								+ DBHandler_Account.KEY_ACCOUNT_GRPID
								+ " "
								+ "LEFT JOIN "
								+ DBHandler_AccountsGroup.TABLE_NAME_ACCOUNT_GRP
								+ " agrp2 ON agrp2."
								+ DBHandler_AccountsGroup.KEY_ACCOUNTGRP_ID
								+ " = agrp1."
								+ DBHandler_AccountsGroup.KEY_ACCOUNTGRP_PARENTID
								+ " " + "WHERE "
								+ KEY_ACCOUNTTXN_ACCOUNT_VOUCHER_TXN_ID + " = "
								+ objTxn.id + " " + "AND "
								+ KEY_ACCOUNTTXN_ACCOUNT_TXN_TYPE_ID + " = "
								+ txnTypeId + " " + "AND agrp1."
								+ DBHandler_AccountsGroup.KEY_ACCOUNTGRP_ID
								+ " != 5";

						Log.i("SUSHIL", "sqlQueryAccntList: \n"
								+ sqlQueryAccntTxn);

						Cursor acntTxnCursor = db.rawQuery(sqlQueryAccntTxn,
								null);
						if (acntTxnCursor != null) {
							if (acntTxnCursor.moveToFirst()) {
								do {

									Object_Account_Group AcGroupMain = new Object_Account_Group();
									AcGroupMain.id = acntTxnCursor
											.getInt(acntTxnCursor
													.getColumnIndex(KEY_JOINVAL_MAINGRPID));
									AcGroupMain.name = acntTxnCursor
											.getString(acntTxnCursor
													.getColumnIndex(KEY_JOINVAL_MAINGRPNAME));
									AcGroupMain.parentId = acntTxnCursor
											.getInt(acntTxnCursor
													.getColumnIndex(KEY_JOINVAL_MAINGRPPARENTID));
									AcGroupMain.description = acntTxnCursor
											.getString(acntTxnCursor
													.getColumnIndex(KEY_JOINVAL_MAINGRPDESC));
									AcGroupMain.daytime = acntTxnCursor
											.getString(acntTxnCursor
													.getColumnIndex(KEY_JOINVAL_MAINGRPDAYTIME));

									Object_Account_Group AcGroupSub = new Object_Account_Group();
									AcGroupSub.id = acntTxnCursor
											.getInt(acntTxnCursor
													.getColumnIndex(KEY_JOINVAL_SUBGRPID));
									AcGroupSub.name = acntTxnCursor
											.getString(acntTxnCursor
													.getColumnIndex(KEY_JOINVAL_SUBGRPNAME));
									AcGroupSub.parentId = acntTxnCursor
											.getInt(acntTxnCursor
													.getColumnIndex(KEY_JOINVAL_SUBGRPPARENTID));
									AcGroupSub.description = acntTxnCursor
											.getString(acntTxnCursor
													.getColumnIndex(KEY_JOINVAL_SUBGRPDESC));
									AcGroupSub.daytime = acntTxnCursor
											.getString(acntTxnCursor
													.getColumnIndex(KEY_JOINVAL_SUBGRPDAYTIME));

									Object_Account account = new Object_Account();
									account.id = acntTxnCursor
											.getInt(acntTxnCursor
													.getColumnIndex(KEY_JOINVAL_ACCNTID));
									account.name = acntTxnCursor
											.getString(acntTxnCursor
													.getColumnIndex(KEY_JOINVAL_ACCNTNAME));
									account.grpId = acntTxnCursor
											.getInt(acntTxnCursor
													.getColumnIndex(KEY_JOINVAL_ACCNTGRPID));
									account.address = acntTxnCursor
											.getString(acntTxnCursor
													.getColumnIndex(KEY_JOINVAL_ACCNTADDRESS));
									account.contact = acntTxnCursor
											.getString(acntTxnCursor
													.getColumnIndex(KEY_JOINVAL_ACCNTCONTACT));
									account.description = acntTxnCursor
											.getString(acntTxnCursor
													.getColumnIndex(KEY_JOINVAL_ACCNTDESC));
									account.daytime = acntTxnCursor
											.getString(acntTxnCursor
													.getColumnIndex(KEY_JOINVAL_ACCNTDAYTIME));

									account.accountTxn = new Object_Account_Txn();
									account.accountTxn.id = acntTxnCursor
											.getInt(acntTxnCursor
													.getColumnIndex(KEY_JOINVAL_ATXNID));
									account.accountTxn.voucher_Txn_Id = objTxn.id;
									account.accountTxn.amount = acntTxnCursor
											.getDouble(acntTxnCursor
													.getColumnIndex(KEY_JOINVAL_ATXNAMOUNT));
									account.accountTxn.narration = acntTxnCursor
											.getString(acntTxnCursor
													.getColumnIndex(KEY_JOINVAL_ATXNNARRATION));
									account.accountTxn.isCash = acntTxnCursor
											.getInt(acntTxnCursor
													.getColumnIndex(KEY_JOINVAL_ATXNISCASH));

									account.itemCategories = getItemTxnByAcTxnId(account.accountTxn.id);

									// if AcGroupMain exists in allAcGroup list
									// if AcGroupSub exists in the main group
									// selected
									// now add accountObj with AC txn and items
									// to this AcGroupSub
									// else if AcGroupSub not found in main
									// group selected
									// add AcGroupsub which contains Ac with
									// AcTxn into AcGroupMain
									// else add AcGroupmain in allAcGroup with
									// all SubGroup,Acc and AcTxn details

									boolean AcGroupMainFound = false;
									int AcGroupMainFoundOnIndex = -1;

									for (Object_Account_Group AcGrpMain : allAcGroups) {
										AcGroupMainFoundOnIndex++;
										if (AcGrpMain.id == AcGroupMain.id) {
											AcGroupMainFound = true;
											break;
										}
									}

									if (AcGroupMainFound) {
										// main AccountGroup found in main list
										boolean AcGroupSubFound = false;
										int AcGroupSubFoundOnIndex = -1;
										for (Object_Account_Group AcGrpSub : allAcGroups
												.get(AcGroupMainFoundOnIndex).accountGroups) {
											AcGroupSubFoundOnIndex++;
											if (AcGrpSub.id == AcGroupSub.id) {
												AcGroupSubFound = true;
												break;
											}
										}

										if (AcGroupSubFound) {
											// sub AccountGroup found in main
											// List's AccountGroup
											allAcGroups
													.get(AcGroupMainFoundOnIndex).accountGroups
													.get(AcGroupSubFoundOnIndex).accounts
													.add(account);
										} else {
											// sub AccountGroup NOT found in
											// main List's AccountGroup
											AcGroupSub.accounts = new ArrayList<Object_Account>();
											AcGroupSub.accounts.add(account);

											allAcGroups
													.get(AcGroupMainFoundOnIndex).accountGroups
													.add(AcGroupSub);
										}
									} else {
										// main AccountGroup Not found in main
										// list
										AcGroupSub.accounts = new ArrayList<Object_Account>();
										AcGroupSub.accounts.add(account);

										AcGroupMain.accountGroups = new ArrayList<Object_Account_Group>();
										AcGroupMain.accountGroups
												.add(AcGroupSub);

										allAcGroups.add(AcGroupMain);
									}

								} while (acntTxnCursor.moveToNext());
							}
						}

					} while (vchTxnCursor.moveToNext());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.close();
		}

		return allAcGroups;
	}

	public void deleteTxn(int voucherId) {
		ArrayList<Object_Account_Txn> listacTxn = new ArrayList<Object_Account_Txn>();
		SQLiteDatabase db = this.getReadableDatabase();
		String sqlQuery = "Select * from " + TABLE_NAME_VOUCHER_TXN + " WHERE "
				+ KEY_VOUCHER_TXN_ID + " = " + voucherId;
		Log.i("SUSHIL", "SUSHIl " + sqlQuery);
		Cursor cursor = db.rawQuery(sqlQuery, null);
		if (cursor != null) {
			if (cursor.moveToFirst()) {
				do {
					Object_Voucher_Txn objTxn = new Object_Voucher_Txn();
					objTxn.id = cursor.getInt(cursor
							.getColumnIndex(KEY_ACCOUNTTXN_ID));
					String sqlQuery1 = "Select * from "
							+ TABLE_NAME_ACCOUNT_TXN + " WHERE "
							+ KEY_ACCOUNTTXN_ACCOUNT_VOUCHER_TXN_ID + " = "
							+ objTxn.id;
					Cursor cursor1 = db.rawQuery(sqlQuery1, null);

					if (cursor1 != null) {
						if (cursor1.moveToFirst()) {
							do {
								Object_Account_Txn txn = new Object_Account_Txn();
								txn.id = cursor1.getInt(cursor1
										.getColumnIndex(KEY_ACCOUNTTXN_ID));
								String sqlQuery2 = "Select * from "
										+ TABLE_NAME_ITEM_TXN + " WHERE "
										+ KEY_ITEMTXN_TXN_ACCOUNT_ID + " = "
										+ txn.id;
								Cursor cursor2 = db.rawQuery(sqlQuery2, null);
								ArrayList<Object_Item_Txn> listtxn = new ArrayList<Object_Item_Txn>();
								if (cursor2 != null) {
									if (cursor2.moveToFirst()) {
										do {
											Object_Item_Txn obj = new Object_Item_Txn();
											obj.id = cursor2
													.getInt(cursor2
															.getColumnIndex(KEY_ITEMTXN_ID));
											listtxn.add(obj);
										} while (cursor2.moveToNext());
									}
								}
								String Delete = "Delete from "
										+ TABLE_NAME_ITEM_TXN + " where "
										+ KEY_ITEMTXN_ID + " in("
										+ getkomaseprated(listtxn) + ")";
								db.execSQL(Delete);
								listacTxn.add(txn);
							} while (cursor1.moveToNext());
						}
					}
					String DeleteAccounttxn = "Delete from "
							+ TABLE_NAME_ACCOUNT_TXN + " where "
							+ KEY_ACCOUNTTXN_ID + " in("
							+ getkomasepratedAcc(listacTxn) + ")";
					db.execSQL(DeleteAccounttxn);
				} while (cursor.moveToNext());
			}
		}

		String dele = "Delete from " + TABLE_NAME_VOUCHER_TXN + " where "
				+ KEY_VOUCHER_TXN_ID + " = " + voucherId;
		db.execSQL(dele);
	}

	private String getkomaseprated(ArrayList<Object_Item_Txn> list) {
		String key = "";
		for (Object_Item_Txn object : list) {
			key += object.id + ",";
		}
		if (key.contains(",")) {
			key = key.substring(0, key.length() - 1);
		}
		return key;
	}

	private String getkomasepratedAcc(ArrayList<Object_Account_Txn> list) {
		String key = "";
		for (Object_Account_Txn object : list) {
			key += object.id + ",";
		}
		if (key.contains(",")) {
			key = key.substring(0, key.length() - 1);
		}
		return key;
	}

	public Object_Interest getInterestCalucation(int accountId) {
		/*
		 * select voucher_txn.daytime , total (CAse WHEN txn_type_id= 1 THEN
		 * account_txn.amount END) credit ,total (CAse WHEN txn_type_id= 2 THEN
		 * account_txn.amount END) debit from account_txn Left join voucher_txn
		 * on account_txn.voucher_txn_id = voucher_txn.id WHERE
		 * account_txn.account_id = 39 Order By voucher_txn.daytime DESC
		 */
		SQLiteDatabase db = this.getReadableDatabase();
		String sqlQuery = "SELECT " + TABLE_NAME_VOUCHER_TXN + "."
				+ KEY_VOUCHER_DAYTIME + ", Total(CASE WHEN "
				+ KEY_ACCOUNTTXN_ACCOUNT_TXN_TYPE_ID + " = 1 THEN "
				+ TABLE_NAME_ACCOUNT_TXN + "." + KEY_ACCOUNTTXN_ACCOUNT_AMOUNT
				+ " END ) " + KEY_BALANCE_CREDIT + ", total (CASE WHEN "
				+ KEY_ACCOUNTTXN_ACCOUNT_TXN_TYPE_ID + " = 2 THEN "
				+ TABLE_NAME_ACCOUNT_TXN + "." + KEY_ACCOUNTTXN_ACCOUNT_AMOUNT
				+ " END ) " + KEY_BALANCE_DEBIT + " FROM "
				+ TABLE_NAME_ACCOUNT_TXN + " LEFT JOIN "
				+ TABLE_NAME_VOUCHER_TXN + " ON " + TABLE_NAME_ACCOUNT_TXN
				+ "." + KEY_ACCOUNTTXN_ACCOUNT_VOUCHER_TXN_ID + " = "
				+ TABLE_NAME_VOUCHER_TXN + "." + KEY_VOUCHER_TXN_ID + " WHERE "
				+ TABLE_NAME_ACCOUNT_TXN + "." + KEY_ACCOUNTTXN_ACCOUNT_ID
				+ " = " + accountId + " Order BY " + TABLE_NAME_VOUCHER_TXN
				+ "." + KEY_VOUCHER_DAYTIME + " DESC";

		Log.i("SUSHIL", "SUSHIL query " + sqlQuery);

		Cursor cursor = db.rawQuery(sqlQuery, null);
		Object_Interest objInterest = null;
		if (cursor != null) {
			if (cursor.moveToFirst()) {
				do {
					objInterest = new Object_Interest();
					objInterest.dayTime = cursor.getString(cursor
							.getColumnIndex(KEY_VOUCHER_DAYTIME));
					objInterest.credit = cursor.getDouble(cursor
							.getColumnIndex(KEY_BALANCE_CREDIT));
					objInterest.debit = cursor.getDouble(cursor
							.getColumnIndex(KEY_BALANCE_DEBIT));
				} while (cursor.moveToNext());
			}
		}
		db.close();
		return objInterest;
	}

	public ArrayList<Object_Voucher_Txn> getTxnByDate(String date,
			int voucherTypeId) {
		// Log.i("accountsappdb",
		// "getTxnByDate - date : "+date+"  voucherTypeId: "+voucherTypeId);
		ArrayList<Object_Voucher_Txn> allTxns = new ArrayList<Object_Voucher_Txn>();

		SQLiteDatabase db = this.getReadableDatabase();

		String sqlQuery = "SELECT  vtxn." + KEY_VOUCHER_TXN_ID
				+ " vtxnId,vtxn." + KEY_VOUCHER_DAYTIME + " vtxndaytime,"
				+ "vm." + DBHandler_voucher_master.KEY_VOUCHER_MASTER_ID
				+ " vmId,vm."
				+ DBHandler_voucher_master.KEY_VOUCHER_MASTER_NAME
				+ " vmName,vm."
				+ DBHandler_voucher_master.KEY_VOUCHER_MASTER_SCREENTYPE
				+ " vmScrnType " + "FROM " + TABLE_NAME_VOUCHER_TXN + " vtxn "
				+ "INNER JOIN "
				+ DBHandler_voucher_master.TABLE_NAME_VOUCHER_MASTER
				+ " vm ON vtxn." + KEY_VOUCHER_VOUCHER_ID + " = vm."
				+ DBHandler_voucher_master.KEY_VOUCHER_MASTER_ID + " "
				+ "WHERE " + KEY_VOUCHER_DAYTIME + " LIKE '" + date + "%' "
				+ "AND " + KEY_VOUCHER_VOUCHER_ID + " = " + voucherTypeId;

		Log.i("accountsappdb", "sqlQuery Main: \n" + sqlQuery);

		Cursor cursor = db.rawQuery(sqlQuery, null);

		try {
			if (cursor != null) {
				if (cursor.moveToFirst()) {
					do {
						Object_Voucher_Txn objTxn = new Object_Voucher_Txn();
						objTxn.id = cursor.getInt(cursor
								.getColumnIndex("vtxnId"));
						objTxn.daytime = cursor.getString(cursor
								.getColumnIndex("vtxndaytime"));

						Object_Voucher_Master vm4objTxn = new Object_Voucher_Master();
						vm4objTxn.id = cursor.getInt(cursor
								.getColumnIndex("vmId"));
						vm4objTxn.name = cursor.getString(cursor
								.getColumnIndex("vmName"));
						vm4objTxn.screenType = cursor.getString(cursor
								.getColumnIndex("vmScrnType"));

						objTxn.voucher = vm4objTxn;

						//
						// cODE pOSITION 2
						//
						//

						objTxn.accounts = getAccountTxn(objTxn.id, 0);
						allTxns.add(objTxn);
					} while (cursor.moveToNext()); // Query for voucher_TXn
													// table
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// Log.i("accountsappdb",
		// "size of Arraylist from DBHandler_Txn is :"+allTxns.size());
		return allTxns;
	}

	public ArrayList<Object_Account> getAccountTxn(int vchTxnId, int txnTypeId) {
		ArrayList<Object_Account> objAccntAL = new ArrayList<Object_Account>();

		SQLiteDatabase db = this.getReadableDatabase();
		String sqlQueryAccntList = "SELECT atxn." + KEY_ACCOUNTTXN_ID + " "
				+ KEY_JOINVAL_ATXNID + ",atxn." + KEY_ACCOUNTTXN_ACCOUNT_ID
				+ " " + KEY_JOINVAL_ATXNACCNTID + ",atxn."
				+ KEY_ACCOUNTTXN_ACCOUNT_VOUCHER_TXN_ID + " "
				+ KEY_JOINVAL_ATXNVTXNID + ",atxn."
				+ KEY_ACCOUNTTXN_ACCOUNT_AMOUNT + " " + KEY_JOINVAL_ATXNAMOUNT
				+ ",atxn." + KEY_ACCOUNTTXN_ACCOUNT_TXN_TYPE_ID + " "
				+ KEY_JOINVAL_ATXNTYPEID + ",atxn."
				+ KEY_ACCOUNTTXN_ACCOUNT_NARRATION + " "
				+ KEY_JOINVAL_ATXNNARRATION + ",atxn."
				+ KEY_ACCOUNTTXN_ACCOUNT_ISCASH + " " + KEY_JOINVAL_ATXNISCASH
				+ ", " + "acnt." + DBHandler_Account.KEY_ACCOUNT_ID + " "
				+ KEY_JOINVAL_ACCNTID + ",acnt."
				+ DBHandler_Account.KEY_ACCOUNT_NAME + " "
				+ KEY_JOINVAL_ACCNTNAME + ",acnt."
				+ DBHandler_Account.KEY_ACCOUNT_GRPID + " "
				+ KEY_JOINVAL_ACCNTGRPID + ",acnt."
				+ DBHandler_Account.KEY_ACCOUNT_ADDRESS + " "
				+ KEY_JOINVAL_ACCNTADDRESS + ",acnt."
				+ DBHandler_Account.KEY_ACCOUNT_CONTACT + " "
				+ KEY_JOINVAL_ACCNTCONTACT + ",acnt."
				+ DBHandler_Account.KEY_ACCOUNT_DESCRIPTION + " "
				+ KEY_JOINVAL_ACCNTDESC + ",acnt."
				+ DBHandler_Account.KEY_ACCOUNT_DAYTIME + " "
				+ KEY_JOINVAL_ACCNTDAYTIME + ", " + "tm."
				+ DBHandler_TransactionMaster.KEY_TXN_MASTER_ID + " "
				+ KEY_JOINVAL_TXNMSTRID + ",tm."
				+ DBHandler_TransactionMaster.KEY_TXN_MASTER_NAME + " "
				+ KEY_JOINVAL_TXNMSTRNAME + ",tm."
				+ DBHandler_TransactionMaster.KEY_TXN_MASTER_PRINTNAME + " "
				+ KEY_JOINVAL_TXNMSTRPRINTNAME + " " + " FROM "
				+ TABLE_NAME_ACCOUNT_TXN + " atxn " + "INNER JOIN "
				+ DBHandler_Account.TABLE_NAME_ACCOUNT + " acnt ON acnt."
				+ DBHandler_Account.KEY_ACCOUNT_ID + " = atxn."
				+ KEY_ACCOUNTTXN_ACCOUNT_ID + " " + "INNER JOIN "
				+ DBHandler_TransactionMaster.TABLE_NAME_TXN_MASTER
				+ " tm ON tm." + DBHandler_TransactionMaster.KEY_TXN_MASTER_ID
				+ " = atxn." + KEY_ACCOUNTTXN_ACCOUNT_TXN_TYPE_ID + " ";
		// "WHERE "+KEY_ACCOUNTTXN_ACCOUNT_VOUCHER_TXN_ID+" = "+vchTxnId+" ";
		if (vchTxnId > 0 && txnTypeId > 0) {
			sqlQueryAccntList += "WHERE "
					+ KEY_ACCOUNTTXN_ACCOUNT_VOUCHER_TXN_ID + " = " + vchTxnId
					+ " AND " + KEY_ACCOUNTTXN_ACCOUNT_TXN_TYPE_ID + " = "
					+ txnTypeId + " ";
		} else if (vchTxnId > 0) {
			sqlQueryAccntList += "WHERE "
					+ KEY_ACCOUNTTXN_ACCOUNT_VOUCHER_TXN_ID + " = " + vchTxnId
					+ " ";
		} else if (txnTypeId > 0) {
			sqlQueryAccntList += "WHERE " + KEY_ACCOUNTTXN_ACCOUNT_TXN_TYPE_ID
					+ " = " + txnTypeId + " ";
		}

		Log.i("SUSHIL", "main query " + sqlQueryAccntList);
		// Log.i("accountsappdb", "sqlQueryAccntList: \n"+sqlQueryAccntList);
		Cursor accntCursor = db.rawQuery(sqlQueryAccntList, null);
		if (accntCursor != null) {
			if (accntCursor.moveToFirst()) {
				do {
					int txnAccntId = accntCursor.getInt(accntCursor
							.getColumnIndex(KEY_JOINVAL_ATXNID));
					ArrayList<Object_Item_Category> itemCats = getItemTxnByAcTxnId(txnAccntId);

					/*
					 * 
					 * cODE pOSITION 1
					 */

					Object_Account accnt = new Object_Account();
					accnt.id = accntCursor.getInt(accntCursor
							.getColumnIndex(KEY_JOINVAL_ACCNTID));
					accnt.name = accntCursor.getString(accntCursor
							.getColumnIndex(KEY_JOINVAL_ACCNTNAME));
					accnt.grpId = accntCursor.getInt(accntCursor
							.getColumnIndex(KEY_JOINVAL_ACCNTGRPID));
					accnt.address = accntCursor.getString(accntCursor
							.getColumnIndex(KEY_JOINVAL_ACCNTADDRESS));
					accnt.contact = accntCursor.getString(accntCursor
							.getColumnIndex(KEY_JOINVAL_ACCNTCONTACT));
					accnt.description = accntCursor.getString(accntCursor
							.getColumnIndex(KEY_JOINVAL_ACCNTDESC));
					accnt.daytime = accntCursor.getString(accntCursor
							.getColumnIndex(KEY_JOINVAL_ACCNTDAYTIME));
					accnt.itemCategories = itemCats;

					Object_Account_Txn acntTxn = new Object_Account_Txn();
					acntTxn.id = accntCursor.getInt(accntCursor
							.getColumnIndex(KEY_JOINVAL_ATXNID));
					acntTxn.voucher_Txn_Id = vchTxnId;
					acntTxn.amount = accntCursor.getDouble(accntCursor
							.getColumnIndex(KEY_JOINVAL_ATXNAMOUNT));
					acntTxn.narration = accntCursor.getString(accntCursor
							.getColumnIndex(KEY_JOINVAL_ATXNNARRATION));
					acntTxn.isCash = accntCursor.getInt(accntCursor
							.getColumnIndex(KEY_JOINVAL_ATXNISCASH));

					Object_Txn_Master txnMstr = new Object_Txn_Master();
					txnMstr.id = accntCursor.getInt(accntCursor
							.getColumnIndex(KEY_JOINVAL_TXNMSTRID));
					txnMstr.name = accntCursor.getString(accntCursor
							.getColumnIndex(KEY_JOINVAL_TXNMSTRNAME));
					txnMstr.printName = accntCursor.getString(accntCursor
							.getColumnIndex(KEY_JOINVAL_TXNMSTRPRINTNAME));
					acntTxn.txnType = txnMstr;
					accnt.accountTxn = acntTxn;

					objAccntAL.add(accnt);
				} while (accntCursor.moveToNext()); // Query for Account Table
			}
		}
		return objAccntAL;
	}

	public ArrayList<Object_Item_Category> getItemTxnByAcTxnId(int txnAccntId) {
		SQLiteDatabase db = this.getReadableDatabase();
		String sqlQueryItmTxn = "SELECT itmTxn." + KEY_ITEMTXN_ID + " "
				+ KEY_JOINVAL_ITMTXNID + ",itmTxn." + KEY_ITEMTXN_QTY + " "
				+ KEY_JOINVAL_ITMTXNQTY + ",itmTxn." + KEY_ITEMTXN_PRICE + " "
				+ KEY_JOINVAL_ITMTXNPRICE + ",itmTxn." + KEY_ITEMTXN_AMOUNT
				+ " " + KEY_JOINVAL_ITMTXNAMOUNT + "," + "itm."
				+ DBHandler_Item.KEY_ITEM_ID + " " + KEY_JOINVAL_ITMID
				+ ",itm." + DBHandler_Item.KEY_ITEM_NAME + " "
				+ KEY_JOINVAL_ITMNAME + ",itm."
				+ DBHandler_Item.KEY_ITEM_DESCRIPTION + " "
				+ KEY_JOINVAL_ITMDESC + ",itm."
				+ DBHandler_Item.KEY_ITEM_PRINT_NAME + " "
				+ KEY_JOINVAL_ITMPRINTNAME + ",itm."
				+ DBHandler_Item.KEY_ITEM_SALE_PRICE + " "
				+ KEY_JOINVAL_ITMSALEPRICE + ",itm."
				+ DBHandler_Item.KEY_ITEM_PURCHASE_PRICE + " "
				+ KEY_JOINVAL_ITMPURCPRICE + ",itm."
				+ DBHandler_Item.KEY_ITEM_MRP + " " + KEY_JOINVAL_ITMMRP
				+ ",itm." + DBHandler_Item.KEY_ITEM_MIN_SALE_PRICE + " "
				+ KEY_JOINVAL_ITMMINSALEPRICE + ",itm."
				+ DBHandler_Item.KEY_ITEM_DAYTIME + " "
				+ KEY_JOINVAL_ITMDAYTIME + ",itm."
				+ DBHandler_Item.KEY_ITEM_QTY_REMAINING + " "
				+ KEY_JOINVAL_ITMQTYREM + "," + "unit."
				+ DBHandler_Unit.KEY_UNIT_ID + " " + KEY_JOINVAL_UNITID
				+ ",unit." + DBHandler_Unit.KEY_UNIT_NAME + " "
				+ KEY_JOINVAL_UNITNAME + ",unit."
				+ DBHandler_Unit.KEY_UNIT_PRINT_NAME + " "
				+ KEY_JOINVAL_UNITPRINTNAME + "," + "itmcat."
				+ DBHandler_ItemCategory.KEY_ITEMCATEGORY_ID + " "
				+ KEY_JOINVAL_ITMCATID + ",itmcat."
				+ DBHandler_ItemCategory.KEY_ITEMCATEGORY_NAME + " "
				+ KEY_JOINVAL_ITMCATNAME + ",itmcat."
				+ DBHandler_ItemCategory.KEY_ITEMCATEGORY_ACCOUNT_ID + " "
				+ KEY_JOINVAL_ITMCATACCNTID + ",itmcat."
				+ DBHandler_ItemCategory.KEY_ITEMCATEGORY_DESCRIPTION + " "
				+ KEY_JOINVAL_ITMCATDESC + ",itmcat."
				+ DBHandler_ItemCategory.KEY_ITEMCATEGORY_DAYTIME + " "
				+ KEY_JOINVAL_ITMCATDAYTIME + " " + "FROM "
				+ TABLE_NAME_ITEM_TXN + " itmTxn " + "INNER JOIN "
				+ DBHandler_Item.TABLE_NAME_ITEMS + " itm ON itmTxn."
				+ KEY_ITEMTXN_ITEM_ID + " = itm." + DBHandler_Item.KEY_ITEM_ID
				+ " " + "INNER JOIN " + DBHandler_Unit.TABLE_NAME_UNIT
				+ " ON unit." + DBHandler_Unit.KEY_UNIT_ID + " = itm."
				+ DBHandler_Item.KEY_ITEM_UNIT_ID + " " + "INNER JOIN "
				+ DBHandler_ItemCategory.TABLE_NAME_ITEM_CATEGORY
				+ " itmcat ON itmcat."
				+ DBHandler_ItemCategory.KEY_ITEMCATEGORY_ID + " = itm."
				+ DBHandler_Item.KEY_ITEM_ITEM_CATID + " " + "WHERE itmTxn."
				+ KEY_ITEMTXN_TXN_ACCOUNT_ID + " = " + txnAccntId;

		Cursor itemTxnCursor = db.rawQuery(sqlQueryItmTxn, null);

		ArrayList<Object_Item_Category> itemCats = new ArrayList<Object_Item_Category>();
		if (itemTxnCursor != null) {
			if (itemTxnCursor.moveToFirst()) {
				do {

					Object_Item itm = new Object_Item();
					itm.id = itemTxnCursor.getInt(itemTxnCursor
							.getColumnIndex(KEY_JOINVAL_ITMID));
					itm.name = itemTxnCursor.getString(itemTxnCursor
							.getColumnIndex(KEY_JOINVAL_ITMNAME));
					itm.itemCatId = itemTxnCursor.getInt(itemTxnCursor
							.getColumnIndex(KEY_JOINVAL_ITMCATID));
					itm.description = itemTxnCursor.getString(itemTxnCursor
							.getColumnIndex(KEY_JOINVAL_ITMDESC));

					Object_Unit itmUnit = new Object_Unit();
					itmUnit.id = itemTxnCursor.getInt(itemTxnCursor
							.getColumnIndex(KEY_JOINVAL_UNITID));
					itmUnit.name = itemTxnCursor.getString(itemTxnCursor
							.getColumnIndex(KEY_JOINVAL_UNITNAME));
					itmUnit.printName = itemTxnCursor.getString(itemTxnCursor
							.getColumnIndex(KEY_JOINVAL_UNITPRINTNAME));
					itm.unit = itmUnit;
					itm.printName = itemTxnCursor.getString(itemTxnCursor
							.getColumnIndex(KEY_JOINVAL_ITMPRINTNAME));
					itm.salePrice = itemTxnCursor.getDouble(itemTxnCursor
							.getColumnIndex(KEY_JOINVAL_ITMSALEPRICE));
					itm.purchasePrice = itemTxnCursor.getDouble(itemTxnCursor
							.getColumnIndex(KEY_JOINVAL_ITMPURCPRICE));
					itm.mrp = itemTxnCursor.getDouble(itemTxnCursor
							.getColumnIndex(KEY_JOINVAL_ITMMRP));
					itm.minSalePrice = itemTxnCursor.getDouble(itemTxnCursor
							.getColumnIndex(KEY_JOINVAL_ITMMINSALEPRICE));
					itm.daytime = itemTxnCursor.getString(itemTxnCursor
							.getColumnIndex(KEY_JOINVAL_ITMDAYTIME));
					itm.qtyRemaining = itemTxnCursor.getInt(itemTxnCursor
							.getColumnIndex(KEY_JOINVAL_ITMQTYREM));

					Object_Item_Txn itmTxn = new Object_Item_Txn();
					itmTxn.id = itemTxnCursor.getInt(itemTxnCursor
							.getColumnIndex(KEY_JOINVAL_ITMTXNID));
					itmTxn.qty = itemTxnCursor.getInt(itemTxnCursor
							.getColumnIndex(KEY_JOINVAL_ITMTXNQTY));
					itmTxn.price = itemTxnCursor.getDouble(itemTxnCursor
							.getColumnIndex(KEY_JOINVAL_ITMTXNPRICE));
					itmTxn.amount = itemTxnCursor.getDouble(itemTxnCursor
							.getColumnIndex(KEY_JOINVAL_ITMTXNAMOUNT));

					itm.itemTxn = itmTxn;

					Object_Item_Category itmCat = new Object_Item_Category();
					itmCat.id = itemTxnCursor.getInt(itemTxnCursor
							.getColumnIndex(KEY_JOINVAL_ITMCATID));
					itmCat.name = itemTxnCursor.getString(itemTxnCursor
							.getColumnIndex(KEY_JOINVAL_ITMCATNAME));
					itmCat.accountId = itemTxnCursor.getInt(itemTxnCursor
							.getColumnIndex(KEY_JOINVAL_ITMCATACCNTID));
					itmCat.description = itemTxnCursor.getString(itemTxnCursor
							.getColumnIndex(KEY_JOINVAL_ITMCATDESC));
					itmCat.daytime = itemTxnCursor.getString(itemTxnCursor
							.getColumnIndex(KEY_JOINVAL_ITMCATDAYTIME));

					/*
					 * ArrayList<Object_Item> itemsAL = new
					 * ArrayList<Object_Item>(); itemsAL.add(itm);
					 * 
					 * itmCat.items = itemsAL;
					 */

					boolean itemCatFound = false;
					int itemCatFoundOnIndex = -1;

					for (Object_Item_Category itemCatLoop : itemCats) {
						itemCatFoundOnIndex++;
						if (itemCatLoop.id == itmCat.id) {
							itemCatFound = true;
							break;
						}
					}

					if (itemCatFound) {
						itemCats.get(itemCatFoundOnIndex).items.add(itm);
					} else {
						ArrayList<Object_Item> itemsAL = new ArrayList<Object_Item>();
						itemsAL.add(itm);
						itmCat.items = itemsAL;

						itemCats.add(itmCat);
					}

				} while (itemTxnCursor.moveToNext()); // Query for Item_Txn
														// table
			}
		}
		return itemCats;
	}

	public ArrayList<Object_Voucher_Txn> getGeneralLedger(int accountId,
			String fromDate, String toDate) {
		ArrayList<Object_Voucher_Txn> allTxns = new ArrayList<Object_Voucher_Txn>();

		SQLiteDatabase db = this.getReadableDatabase();
		// String Sql = ""
		String openDate = "";
		String Sql = "Select day_time_opening from " + TABLE_NAME_ACCOUNT
				+ " WHERE " + KEY_ACCOUNTTXN_ID + " = " + accountId;
		Cursor cursorAc = db.rawQuery(Sql, null);
		if (cursorAc != null) {
			if (cursorAc.moveToFirst()) {
				do {
					openDate = cursorAc.getString(cursorAc
							.getColumnIndex("day_time_opening"));
				} while (cursorAc.moveToNext());
			}
		}
		Log.i("SUSHIL", "DATE of open " + getDate(openDate));

		if (getDate(fromDate).before(getDate(openDate))) {

			Object_Voucher_Txn objTxnOpen = new Object_Voucher_Txn();
			objTxnOpen.accounts = getAccountTxnGL(0, 0, accountId, true);
			allTxns.add(objTxnOpen);

		}
		String sqlQuery = "SELECT  vtxn." + KEY_VOUCHER_TXN_ID
				+ " vtxnId,vtxn." + KEY_VOUCHER_DAYTIME + " vtxndaytime,"
				+ "vm." + DBHandler_voucher_master.KEY_VOUCHER_MASTER_ID
				+ " vmId,vm."
				+ DBHandler_voucher_master.KEY_VOUCHER_MASTER_NAME
				+ " vmName,vm."
				+ DBHandler_voucher_master.KEY_VOUCHER_MASTER_SCREENTYPE
				+ " vmScrnType " + "FROM " + TABLE_NAME_VOUCHER_TXN + " vtxn "
				+ "INNER JOIN "
				+ DBHandler_voucher_master.TABLE_NAME_VOUCHER_MASTER
				+ " vm ON vtxn." + KEY_VOUCHER_VOUCHER_ID + " = vm."
				+ DBHandler_voucher_master.KEY_VOUCHER_MASTER_ID + " "
				+ "WHERE " + KEY_VOUCHER_DAYTIME + " between '" + fromDate
				+ "' " + " and '" + toDate + "' ";

		Log.i("SUSHIL", "query is excute " + sqlQuery);
		/*
		 * ( From_date >= '2013-08-19' AND To_date <= '2013-08-23' )
		 */
		// "+KEY_VOUCHER_DAYTIME+" LIKE '"+date+"%' "+
		// Log.i("accountsappdb", "sqlQuery Main: \n"+sqlQuery);

		Cursor cursor = db.rawQuery(sqlQuery, null);

		try {
			if (cursor != null) {
				if (cursor.moveToFirst()) {
					do {
						Object_Voucher_Txn objTxn = new Object_Voucher_Txn();
						objTxn.id = cursor.getInt(cursor
								.getColumnIndex("vtxnId"));
						objTxn.daytime = cursor.getString(cursor
								.getColumnIndex("vtxndaytime"));

						Object_Voucher_Master vm4objTxn = new Object_Voucher_Master();
						vm4objTxn.id = cursor.getInt(cursor
								.getColumnIndex("vmId"));
						vm4objTxn.name = cursor.getString(cursor
								.getColumnIndex("vmName"));
						vm4objTxn.screenType = cursor.getString(cursor
								.getColumnIndex("vmScrnType"));

						objTxn.voucher = vm4objTxn;

						//
						// cODE pOSITION 2
						//
						//

						objTxn.accounts = getAccountTxnGL(objTxn.id, 0,
								accountId, false);
						if (objTxn.accounts.size() != 0)
							allTxns.add(objTxn);
					} while (cursor.moveToNext()); // Query for voucher_TXn
													// table
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// Log.i("accountsappdb",
		// "size of Arraylist from DBHandler_Txn is :"+allTxns.size());
		return allTxns;
	}

	public ArrayList<Object_Account> getAccountTxnGL(int vchTxnId,
			int txnTypeId, int accountId, boolean isopen) {
		ArrayList<Object_Account> objAccntAL = new ArrayList<Object_Account>();

		SQLiteDatabase db = this.getReadableDatabase();
		String sqlQueryAccntList = "";
		if (!isopen) {
			sqlQueryAccntList = "SELECT atxn." + KEY_ACCOUNTTXN_ID + " "
					+ KEY_JOINVAL_ATXNID + ",atxn." + KEY_ACCOUNTTXN_ACCOUNT_ID
					+ " " + KEY_JOINVAL_ATXNACCNTID + ",atxn."
					+ KEY_ACCOUNTTXN_ACCOUNT_VOUCHER_TXN_ID + " "
					+ KEY_JOINVAL_ATXNVTXNID + ",atxn."
					+ KEY_ACCOUNTTXN_ACCOUNT_AMOUNT + " "
					+ KEY_JOINVAL_ATXNAMOUNT + ",atxn."
					+ KEY_ACCOUNTTXN_ACCOUNT_TXN_TYPE_ID + " "
					+ KEY_JOINVAL_ATXNTYPEID + ",atxn."
					+ KEY_ACCOUNTTXN_ACCOUNT_NARRATION + " "
					+ KEY_JOINVAL_ATXNNARRATION + ",atxn."
					+ KEY_ACCOUNTTXN_ACCOUNT_ISCASH + " "
					+ KEY_JOINVAL_ATXNISCASH + ", " + "acnt."
					+ DBHandler_Account.KEY_ACCOUNT_ID + " "
					+ KEY_JOINVAL_ACCNTID + ",acnt."
					+ DBHandler_Account.KEY_ACCOUNT_NAME + " "
					+ KEY_JOINVAL_ACCNTNAME + ",acnt."
					+ DBHandler_Account.KEY_ACCOUNT_GRPID + " "
					+ KEY_JOINVAL_ACCNTGRPID + ",acnt."
					+ DBHandler_Account.KEY_ACCOUNT_ADDRESS + " "
					+ KEY_JOINVAL_ACCNTADDRESS + ",acnt."
					+ DBHandler_Account.KEY_ACCOUNT_CONTACT + " "
					+ KEY_JOINVAL_ACCNTCONTACT + ",acnt."
					+ DBHandler_Account.KEY_ACCOUNT_DESCRIPTION + " "
					+ KEY_JOINVAL_ACCNTDESC + ",acnt."
					+ DBHandler_Account.KEY_ACCOUNT_DAYTIME + " "
					+ KEY_JOINVAL_ACCNTDAYTIME + ", " + "tm."
					+ DBHandler_TransactionMaster.KEY_TXN_MASTER_ID + " "
					+ KEY_JOINVAL_TXNMSTRID + ",tm."
					+ DBHandler_TransactionMaster.KEY_TXN_MASTER_NAME + " "
					+ KEY_JOINVAL_TXNMSTRNAME + ",tm."
					+ DBHandler_TransactionMaster.KEY_TXN_MASTER_PRINTNAME
					+ " " + KEY_JOINVAL_TXNMSTRPRINTNAME + " " + " FROM "
					+ TABLE_NAME_ACCOUNT_TXN + " atxn " + "INNER JOIN "
					+ DBHandler_Account.TABLE_NAME_ACCOUNT + " acnt ON acnt."
					+ DBHandler_Account.KEY_ACCOUNT_ID + " = atxn."
					+ KEY_ACCOUNTTXN_ACCOUNT_ID + " " + "INNER JOIN "
					+ DBHandler_TransactionMaster.TABLE_NAME_TXN_MASTER
					+ " tm ON tm."
					+ DBHandler_TransactionMaster.KEY_TXN_MASTER_ID
					+ " = atxn." + KEY_ACCOUNTTXN_ACCOUNT_TXN_TYPE_ID + " ";
			// "WHERE "+KEY_ACCOUNTTXN_ACCOUNT_VOUCHER_TXN_ID+" = "+vchTxnId+" ";
			if (vchTxnId > 0 && txnTypeId > 0) {
				sqlQueryAccntList += "WHERE "
						+ KEY_ACCOUNTTXN_ACCOUNT_VOUCHER_TXN_ID + " = "
						+ vchTxnId + " AND "
						+ KEY_ACCOUNTTXN_ACCOUNT_TXN_TYPE_ID + " = "
						+ txnTypeId + " AND " + "atxn."
						+ KEY_ACCOUNTTXN_ACCOUNT_ID + " = " + accountId;
			} else if (vchTxnId > 0) {
				sqlQueryAccntList += "WHERE "
						+ KEY_ACCOUNTTXN_ACCOUNT_VOUCHER_TXN_ID + " = "
						+ vchTxnId + " AND " + "atxn."
						+ KEY_ACCOUNTTXN_ACCOUNT_ID + " = " + accountId;
			} else if (txnTypeId > 0) {
				sqlQueryAccntList += "WHERE "
						+ KEY_ACCOUNTTXN_ACCOUNT_TXN_TYPE_ID + " = "
						+ txnTypeId + " AND " + "atxn."
						+ KEY_ACCOUNTTXN_ACCOUNT_ID + " = " + accountId;
			}
		} else {
			sqlQueryAccntList = "SELECT atxn." + KEY_ACCOUNTTXN_ID + " "
					+ KEY_JOINVAL_ATXNID + ",atxn." + KEY_ACCOUNTTXN_ACCOUNT_ID
					+ " " + KEY_JOINVAL_ATXNACCNTID + ",atxn."
					+ KEY_ACCOUNTTXN_ACCOUNT_VOUCHER_TXN_ID + " "
					+ KEY_JOINVAL_ATXNVTXNID + ",atxn."
					+ KEY_ACCOUNTTXN_ACCOUNT_AMOUNT + " "
					+ KEY_JOINVAL_ATXNAMOUNT + ",atxn."
					+ KEY_ACCOUNTTXN_ACCOUNT_TXN_TYPE_ID + " "
					+ KEY_JOINVAL_ATXNTYPEID + ",atxn."
					+ KEY_ACCOUNTTXN_ACCOUNT_NARRATION + " "
					+ KEY_JOINVAL_ATXNNARRATION + ",atxn."
					+ KEY_ACCOUNTTXN_ACCOUNT_ISCASH + " "
					+ KEY_JOINVAL_ATXNISCASH + ", " + "acnt."
					+ DBHandler_Account.KEY_ACCOUNT_ID + " "
					+ KEY_JOINVAL_ACCNTID + ",acnt."
					+ DBHandler_Account.KEY_ACCOUNT_NAME + " "
					+ KEY_JOINVAL_ACCNTNAME + ",acnt."
					+ DBHandler_Account.KEY_ACCOUNT_GRPID + " "
					+ KEY_JOINVAL_ACCNTGRPID + ",acnt."
					+ DBHandler_Account.KEY_ACCOUNT_ADDRESS + " "
					+ KEY_JOINVAL_ACCNTADDRESS + ",acnt."
					+ DBHandler_Account.KEY_ACCOUNT_CONTACT + " "
					+ KEY_JOINVAL_ACCNTCONTACT + ",acnt."
					+ DBHandler_Account.KEY_ACCOUNT_DESCRIPTION + " "
					+ KEY_JOINVAL_ACCNTDESC + ",acnt."
					+ DBHandler_Account.KEY_ACCOUNT_DAYTIME + " "
					+ KEY_JOINVAL_ACCNTDAYTIME + ", " + "tm."
					+ DBHandler_TransactionMaster.KEY_TXN_MASTER_ID + " "
					+ KEY_JOINVAL_TXNMSTRID + ",tm."
					+ DBHandler_TransactionMaster.KEY_TXN_MASTER_NAME + " "
					+ KEY_JOINVAL_TXNMSTRNAME + ",tm."
					+ DBHandler_TransactionMaster.KEY_TXN_MASTER_PRINTNAME
					+ " " + KEY_JOINVAL_TXNMSTRPRINTNAME + " " + " FROM "
					+ TABLE_NAME_ACCOUNT_TXN + " atxn " + "INNER JOIN "
					+ DBHandler_Account.TABLE_NAME_ACCOUNT + " acnt ON acnt."
					+ DBHandler_Account.KEY_ACCOUNT_ID + " = atxn."
					+ KEY_ACCOUNTTXN_ACCOUNT_ID + " " + "INNER JOIN "
					+ DBHandler_TransactionMaster.TABLE_NAME_TXN_MASTER
					+ " tm ON tm."
					+ DBHandler_TransactionMaster.KEY_TXN_MASTER_ID
					+ " = atxn." + KEY_ACCOUNTTXN_ACCOUNT_TXN_TYPE_ID
					+ " WHERE " + KEY_ACCOUNTTXN_ACCOUNT_ID + " = " + accountId
					+ " AND " + KEY_ACCOUNTTXN_ACCOUNT_ISOPEN + " = 1";
		}
		Log.i("SUSHIL", "main query " + sqlQueryAccntList);
		// Log.i("accountsappdb", "sqlQueryAccntList: \n"+sqlQueryAccntList);
		Cursor accntCursor = db.rawQuery(sqlQueryAccntList, null);
		if (accntCursor != null) {
			if (accntCursor.moveToFirst()) {
				do {
					int txnAccntId = accntCursor.getInt(accntCursor
							.getColumnIndex(KEY_JOINVAL_ATXNID));
					ArrayList<Object_Item_Category> itemCats = getItemTxnByAcTxnId(txnAccntId);

					/*
					 * 
					 * cODE pOSITION 1
					 */

					Object_Account accnt = new Object_Account();
					accnt.id = accntCursor.getInt(accntCursor
							.getColumnIndex(KEY_JOINVAL_ACCNTID));
					accnt.name = accntCursor.getString(accntCursor
							.getColumnIndex(KEY_JOINVAL_ACCNTNAME));
					accnt.grpId = accntCursor.getInt(accntCursor
							.getColumnIndex(KEY_JOINVAL_ACCNTGRPID));
					accnt.address = accntCursor.getString(accntCursor
							.getColumnIndex(KEY_JOINVAL_ACCNTADDRESS));
					accnt.contact = accntCursor.getString(accntCursor
							.getColumnIndex(KEY_JOINVAL_ACCNTCONTACT));
					accnt.description = accntCursor.getString(accntCursor
							.getColumnIndex(KEY_JOINVAL_ACCNTDESC));
					accnt.daytime = accntCursor.getString(accntCursor
							.getColumnIndex(KEY_JOINVAL_ACCNTDAYTIME));
					accnt.itemCategories = itemCats;

					Object_Account_Txn acntTxn = new Object_Account_Txn();
					acntTxn.id = accntCursor.getInt(accntCursor
							.getColumnIndex(KEY_JOINVAL_ATXNID));
					acntTxn.voucher_Txn_Id = vchTxnId;
					acntTxn.amount = accntCursor.getDouble(accntCursor
							.getColumnIndex(KEY_JOINVAL_ATXNAMOUNT));
					acntTxn.narration = accntCursor.getString(accntCursor
							.getColumnIndex(KEY_JOINVAL_ATXNNARRATION));
					acntTxn.isCash = accntCursor.getInt(accntCursor
							.getColumnIndex(KEY_JOINVAL_ATXNISCASH));

					Object_Txn_Master txnMstr = new Object_Txn_Master();
					txnMstr.id = accntCursor.getInt(accntCursor
							.getColumnIndex(KEY_JOINVAL_TXNMSTRID));
					txnMstr.name = accntCursor.getString(accntCursor
							.getColumnIndex(KEY_JOINVAL_TXNMSTRNAME));
					txnMstr.printName = accntCursor.getString(accntCursor
							.getColumnIndex(KEY_JOINVAL_TXNMSTRPRINTNAME));
					acntTxn.txnType = txnMstr;
					accnt.accountTxn = acntTxn;

					objAccntAL.add(accnt);
				} while (accntCursor.moveToNext()); // Query for Account Table
			}
		}
		return objAccntAL;
	}

	public double getDailyCash() {
		double cashcredit = 0;
		SQLiteDatabase db = this.getReadableDatabase();
		String cashQuery = "SELECT total(" + KEY_ACCOUNTTXN_ACCOUNT_AMOUNT
				+ ")" + " FROM " + TABLE_NAME_ACCOUNT_TXN + " WHERE "
				+ KEY_ACCOUNTTXN_ACCOUNT_TXN_TYPE_ID + " = '" + "1' AND "
				+ KEY_ACCOUNTTXN_ACCOUNT_ID + " = '" + "1'";
		Cursor cursor = db.rawQuery(cashQuery, null);
		if (cursor != null) {
			cursor.moveToFirst();
			cashcredit = cursor.getDouble(0);
		}
		double cashdebit = 0;
		String cashQuery1 = "SELECT total(" + KEY_ACCOUNTTXN_ACCOUNT_AMOUNT
				+ ")" + " FROM " + TABLE_NAME_ACCOUNT_TXN + " WHERE "
				+ KEY_ACCOUNTTXN_ACCOUNT_TXN_TYPE_ID + " = '" + "2' AND "
				+ KEY_ACCOUNTTXN_ACCOUNT_ID + " = '" + "1'";
		Cursor cursor1 = db.rawQuery(cashQuery1, null);
		if (cursor1 != null) {
			cursor1.moveToFirst();
			cashdebit = cursor1.getDouble(0);
		}

		double total = cashdebit - cashcredit;

		return total;

	}

	public ArrayList<Object_balanceSheet> balanceSheet(String type, String id) {

		/*
		 * SELECT tbl1.*,ac.name,ac.id FROM account AS ac LEFT JOIN (SELECT
		 * account_id,MAX( CASE WHEN txn_type_id=1 THEN total END ) Credit, MAX(
		 * CASE WHEN txn_type_id=2 THEN total END ) Debit FROM (SELECT
		 * atxn.account_id,atxn.txn_type_id,total(amount) total FROM account_txn
		 * as atxn INNER JOIN account accnt on atxn.account_id = accnt.id GROUP
		 * BY txn_type_id,account_id) GROUP BY account_id) AS tbl1 ON
		 * tbl1.account_id = ac.id
		 */
		ArrayList<Object_balanceSheet> list = new ArrayList<Object_balanceSheet>();
		SQLiteDatabase db = this.getReadableDatabase();
		String balanceSheetQuery = "";
		if (type.equals("all")) {
			balanceSheetQuery = "SELECT tbl1.*,ac." + KEY_BALANCE_ID + ",ac."
					+ KEY_BALANCE_NAME + " FROM " + TABLE_NAME_ACCOUNT
					+ " AS ac LEFT JOIN (Select " + KEY_BALANCE_ACCOUNT_ID
					+ ",MAX( CASE WHEN " + KEY_ACCOUNTTXN_ACCOUNT_TXN_TYPE_ID
					+ " = 1 THEN total END ) " + KEY_BALANCE_CREDIT
					+ ", MAX( CASE WHEN " + KEY_ACCOUNTTXN_ACCOUNT_TXN_TYPE_ID
					+ " = 2 THEN total END ) " + KEY_BALANCE_DEBIT
					+ " FROM (SELECT atxn." + KEY_BALANCE_ACCOUNT_ID + ",atxn."
					+ KEY_ACCOUNTTXN_ACCOUNT_TXN_TYPE_ID + ",total("
					+ KEY_ACCOUNTTXN_ACCOUNT_AMOUNT + ") total From "
					+ TABLE_NAME_ACCOUNT_TXN + " as atxn " + "INNER JOIN "
					+ TABLE_NAME_ACCOUNT + " accnt on atxn."
					+ KEY_BALANCE_ACCOUNT_ID + " = accnt." + KEY_BALANCE_ID
					+ " GROUP BY " + KEY_ACCOUNTTXN_ACCOUNT_TXN_TYPE_ID + ","
					+ KEY_BALANCE_ACCOUNT_ID + ") GROUP BY "
					+ KEY_BALANCE_ACCOUNT_ID + ") AS tbl1 ON tbl1."
					+ KEY_BALANCE_ACCOUNT_ID + " = ac." + KEY_BALANCE_ID;
		} else {
			balanceSheetQuery = "SELECT tbl1.*,ac." + KEY_BALANCE_ID + ",ac."
					+ KEY_BALANCE_NAME + " FROM " + TABLE_NAME_ACCOUNT
					+ " AS ac LEFT JOIN (Select " + KEY_BALANCE_ACCOUNT_ID
					+ ",MAX( CASE WHEN " + KEY_ACCOUNTTXN_ACCOUNT_TXN_TYPE_ID
					+ " = 1 THEN total END ) " + KEY_BALANCE_CREDIT
					+ ", MAX( CASE WHEN " + KEY_ACCOUNTTXN_ACCOUNT_TXN_TYPE_ID
					+ " = 2 THEN total END ) " + KEY_BALANCE_DEBIT
					+ " FROM (SELECT atxn." + KEY_BALANCE_ACCOUNT_ID + ",atxn."
					+ KEY_ACCOUNTTXN_ACCOUNT_TXN_TYPE_ID + ",total("
					+ KEY_ACCOUNTTXN_ACCOUNT_AMOUNT + ") total From "
					+ TABLE_NAME_ACCOUNT_TXN + " as atxn " + "INNER JOIN "
					+ TABLE_NAME_ACCOUNT + " accnt on atxn."
					+ KEY_BALANCE_ACCOUNT_ID + " = accnt." + KEY_BALANCE_ID
					+ " GROUP BY " + KEY_ACCOUNTTXN_ACCOUNT_TXN_TYPE_ID + ","
					+ KEY_BALANCE_ACCOUNT_ID + ") GROUP BY "
					+ KEY_BALANCE_ACCOUNT_ID + ") AS tbl1 ON tbl1."
					+ KEY_BALANCE_ACCOUNT_ID + " = ac." + KEY_BALANCE_ID
					+ " WHERE ac.grp_id in(" + id + ")";
		}
		Log.i("SUSHIL", "balance \n" + balanceSheetQuery);
		Cursor cursor = db.rawQuery(balanceSheetQuery, null);

		if (cursor != null) {
			if (cursor.moveToFirst()) {
				do {
					Object_balanceSheet obj = new Object_balanceSheet();
					obj.accountId = cursor.getInt(cursor
							.getColumnIndex(KEY_BALANCE_ID));
					obj.name = cursor.getString(cursor
							.getColumnIndex(KEY_BALANCE_NAME));
					obj.credit = cursor.getDouble(cursor
							.getColumnIndex(KEY_BALANCE_CREDIT));
					obj.debit = cursor.getDouble(cursor
							.getColumnIndex(KEY_BALANCE_DEBIT));
					list.add(obj);
				} while (cursor.moveToNext());
			}
		}
		db.close();
		return list;
	}

	private Date getDate(String string) {
		// String dtStart = "2010-10-15T09:27:37Z";if(!string.trim().isEmpty()){
		Date date = null;
		// String str_date="13-09-2011";
		DateFormat formatter;
		// Date date ;
		formatter = new SimpleDateFormat("yyyy-MM-dd");
		try {
			date = (Date) formatter.parse(string);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return date;
	}
	/*
	 * public ArrayList<Object_Voucher_Txn> getTxnByDate(String date,int
	 * voucherTypeId) { //Log.i("accountsappdb",
	 * "getTxnByDate - date : "+date+"  voucherTypeId: "+voucherTypeId);
	 * ArrayList<Object_Voucher_Txn> allTxns = new
	 * ArrayList<Object_Voucher_Txn>();
	 * 
	 * SQLiteDatabase db = this.getReadableDatabase();
	 * 
	 * String sqlQuery =
	 * "SELECT  vtxn."+KEY_VOUCHER_TXN_ID+" vtxnId,vtxn."+KEY_VOUCHER_DAYTIME
	 * +" vtxndaytime,"+
	 * "vm."+AA_DBHandler_voucher_master.KEY_VOUCHER_MASTER_ID+
	 * " vmId,vm."+AA_DBHandler_voucher_master
	 * .KEY_VOUCHER_MASTER_NAME+" vmName,vm."
	 * +AA_DBHandler_voucher_master.KEY_VOUCHER_MASTER_SCREENTYPE
	 * +" vmScrnType "+ "FROM "+TABLE_NAME_VOUCHER_TXN+" vtxn "+
	 * "INNER JOIN "+AA_DBHandler_voucher_master
	 * .TABLE_NAME_VOUCHER_MASTER+" vm ON vtxn."
	 * +KEY_VOUCHER_VOUCHER_ID+" = vm."+
	 * AA_DBHandler_voucher_master.KEY_VOUCHER_MASTER_ID+" "+
	 * "WHERE "+KEY_VOUCHER_DAYTIME+" LIKE '"+date+"%' "+
	 * "AND "+KEY_VOUCHER_VOUCHER_ID+" = "+voucherTypeId;
	 * 
	 * //Log.i("accountsappdb", "sqlQuery Main: \n"+sqlQuery);
	 * 
	 * Cursor cursor = db.rawQuery(sqlQuery, null);
	 * 
	 * try { if(cursor != null) { if(cursor.moveToFirst()) { do {
	 * Object_Voucher_Txn objTxn = new Object_Voucher_Txn(); objTxn.id =
	 * cursor.getInt(cursor.getColumnIndex("vtxnId")); objTxn.daytime =
	 * cursor.getString(cursor.getColumnIndex("vtxndaytime"));
	 * 
	 * Object_Voucher_Master vm4objTxn = new Object_Voucher_Master();
	 * vm4objTxn.id = cursor.getInt(cursor.getColumnIndex("vmId"));
	 * vm4objTxn.name = cursor.getString(cursor.getColumnIndex("vmName"));
	 * vm4objTxn.screenType =
	 * cursor.getString(cursor.getColumnIndex("vmScrnType"));
	 * 
	 * objTxn.voucher = vm4objTxn;
	 * 
	 * ArrayList<Object_Account> AL_objAccnt = new ArrayList<Object_Account>();
	 * 
	 * String sqlQueryAccntList =
	 * "SELECT atxn."+KEY_ACCOUNTTXN_ID+" "+KEY_JOINVAL_ATXNID
	 * +",atxn."+KEY_ACCOUNTTXN_ACCOUNT_ID
	 * +" "+KEY_JOINVAL_ATXNACCNTID+",atxn."+KEY_ACCOUNTTXN_ACCOUNT_VOUCHER_TXN_ID
	 * +" "+KEY_JOINVAL_ATXNVTXNID+",atxn."+KEY_ACCOUNTTXN_ACCOUNT_AMOUNT+" "+
	 * KEY_JOINVAL_ATXNAMOUNT
	 * +",atxn."+KEY_ACCOUNTTXN_ACCOUNT_TXN_TYPE_ID+" "+KEY_JOINVAL_ATXNTYPEID
	 * +",atxn."
	 * +KEY_ACCOUNTTXN_ACCOUNT_NARRATION+" "+KEY_JOINVAL_ATXNNARRATION+","+
	 * "acnt."
	 * +DBHandler_Account.KEY_ACCOUNT_ID+" "+KEY_JOINVAL_ACCNTID+",acnt."+
	 * DBHandler_Account
	 * .KEY_ACCOUNT_NAME+" "+KEY_JOINVAL_ACCNTNAME+",acnt."+DBHandler_Account
	 * .KEY_ACCOUNT_GRPID
	 * +" "+KEY_JOINVAL_ACCNTGRPID+",acnt."+DBHandler_Account.KEY_ACCOUNT_ADDRESS
	 * +
	 * " "+KEY_JOINVAL_ACCNTADDRESS+",acnt."+DBHandler_Account.KEY_ACCOUNT_CONTACT
	 * +" "+KEY_JOINVAL_ACCNTCONTACT+",acnt."+DBHandler_Account.
	 * KEY_ACCOUNT_DESCRIPTION
	 * +" "+KEY_JOINVAL_ACCNTDESC+",acnt."+DBHandler_Account
	 * .KEY_ACCOUNT_DAYTIME+" "+KEY_JOINVAL_ACCNTDAYTIME+", "+
	 * "tm."+DBHandler_TransactionMaster
	 * .KEY_TXN_MASTER_ID+" "+KEY_JOINVAL_TXNMSTRID
	 * +",tm."+DBHandler_TransactionMaster
	 * .KEY_TXN_MASTER_NAME+" "+KEY_JOINVAL_TXNMSTRNAME
	 * +",tm."+DBHandler_TransactionMaster
	 * .KEY_TXN_MASTER_PRINTNAME+" "+KEY_JOINVAL_TXNMSTRPRINTNAME+" "+
	 * " FROM "+TABLE_NAME_ACCOUNT_TXN+" atxn "+
	 * "INNER JOIN "+DBHandler_Account.
	 * TABLE_NAME_ACCOUNT+" acnt ON acnt."+DBHandler_Account
	 * .KEY_ACCOUNT_ID+" = atxn."+KEY_ACCOUNTTXN_ACCOUNT_ID+" "+
	 * "INNER JOIN "+DBHandler_TransactionMaster
	 * .TABLE_NAME_TXN_MASTER+" tm ON tm."
	 * +DBHandler_TransactionMaster.KEY_TXN_MASTER_ID
	 * +" = atxn."+KEY_ACCOUNTTXN_ACCOUNT_TXN_TYPE_ID+" "+
	 * "WHERE "+KEY_ACCOUNTTXN_ACCOUNT_VOUCHER_TXN_ID+" = "+objTxn.id;
	 * 
	 * //Log.i("accountsappdb", "sqlQueryAccntList: \n"+sqlQueryAccntList);
	 * Cursor accntCursor = db.rawQuery(sqlQueryAccntList, null); if(accntCursor
	 * != null) { if(accntCursor.moveToFirst()) { do { int txnAccntId =
	 * accntCursor.getInt(accntCursor.getColumnIndex(KEY_JOINVAL_ATXNID));
	 * String sqlQueryItmTxn =
	 * "SELECT itmTxn."+KEY_ITEMTXN_ID+" "+KEY_JOINVAL_ITMTXNID
	 * +",itmTxn."+KEY_ITEMTXN_QTY
	 * +" "+KEY_JOINVAL_ITMTXNQTY+",itmTxn."+KEY_ITEMTXN_PRICE
	 * +" "+KEY_JOINVAL_ITMTXNPRICE
	 * +",itmTxn."+KEY_ITEMTXN_AMOUNT+" "+KEY_JOINVAL_ITMTXNAMOUNT+","+
	 * "itm."+DBHandler_Item
	 * .KEY_ITEM_ID+" "+KEY_JOINVAL_ITMID+",itm."+DBHandler_Item
	 * .KEY_ITEM_NAME+" "
	 * +KEY_JOINVAL_ITMNAME+",itm."+DBHandler_Item.KEY_ITEM_DESCRIPTION
	 * +" "+KEY_JOINVAL_ITMDESC
	 * +",itm."+DBHandler_Item.KEY_ITEM_PRINT_NAME+" "+KEY_JOINVAL_ITMPRINTNAME
	 * +",itm."
	 * +DBHandler_Item.KEY_ITEM_SALE_PRICE+" "+KEY_JOINVAL_ITMSALEPRICE+",itm."
	 * +DBHandler_Item
	 * .KEY_ITEM_PURCHASE_PRICE+" "+KEY_JOINVAL_ITMPURCPRICE+",itm."
	 * +DBHandler_Item
	 * .KEY_ITEM_MRP+" "+KEY_JOINVAL_ITMMRP+",itm."+DBHandler_Item
	 * .KEY_ITEM_MIN_SALE_PRICE
	 * +" "+KEY_JOINVAL_ITMMINSALEPRICE+",itm."+DBHandler_Item
	 * .KEY_ITEM_DAYTIME+" "
	 * +KEY_JOINVAL_ITMDAYTIME+",itm."+DBHandler_Item.KEY_ITEM_QTY_REMAINING
	 * +" "+KEY_JOINVAL_ITMQTYREM+","+
	 * "unit."+AA_DBHandler_Unit.KEY_UNIT_ID+" "+
	 * KEY_JOINVAL_UNITID+",unit."+AA_DBHandler_Unit
	 * .KEY_UNIT_NAME+" "+KEY_JOINVAL_UNITNAME
	 * +",unit."+AA_DBHandler_Unit.KEY_UNIT_PRINT_NAME
	 * +" "+KEY_JOINVAL_UNITPRINTNAME+","+
	 * "itmcat."+DBHandler_ItemCategory.KEY_ITEMCATEGORY_ID
	 * +" "+KEY_JOINVAL_ITMCATID
	 * +",itmcat."+DBHandler_ItemCategory.KEY_ITEMCATEGORY_NAME
	 * +" "+KEY_JOINVAL_ITMCATNAME
	 * +",itmcat."+DBHandler_ItemCategory.KEY_ITEMCATEGORY_ACCOUNT_ID
	 * +" "+KEY_JOINVAL_ITMCATACCNTID
	 * +",itmcat."+DBHandler_ItemCategory.KEY_ITEMCATEGORY_DESCRIPTION
	 * +" "+KEY_JOINVAL_ITMCATDESC
	 * +",itmcat."+DBHandler_ItemCategory.KEY_ITEMCATEGORY_DAYTIME
	 * +" "+KEY_JOINVAL_ITMCATDAYTIME+" "+
	 * "FROM "+TABLE_NAME_ITEM_TXN+" itmTxn "+
	 * "INNER JOIN "+DBHandler_Item.TABLE_NAME_ITEMS
	 * +" itm ON itmTxn."+KEY_ITEMTXN_ITEM_ID
	 * +" = itm."+DBHandler_Item.KEY_ITEM_ID+" "+
	 * "INNER JOIN "+AA_DBHandler_Unit
	 * .TABLE_NAME_UNIT+" ON unit."+AA_DBHandler_Unit
	 * .KEY_UNIT_ID+" = itm."+DBHandler_Item.KEY_ITEM_UNIT_ID+" "+
	 * "INNER JOIN "+
	 * DBHandler_ItemCategory.TABLE_NAME_ITEM_CATEGORY+" itmcat ON itmcat."
	 * +DBHandler_ItemCategory
	 * .KEY_ITEMCATEGORY_ID+" = itm."+DBHandler_Item.KEY_ITEM_ITEM_CATID+" "+
	 * "WHERE itmTxn."+KEY_ITEMTXN_TXN_ACCOUNT_ID+" = "+txnAccntId;
	 * 
	 * 
	 * Cursor itemTxnCursor = db.rawQuery(sqlQueryItmTxn, null);
	 * 
	 * ArrayList<Object_Item_Category> itemCats = new
	 * ArrayList<Object_Item_Category>(); if(itemTxnCursor != null) {
	 * if(itemTxnCursor.moveToFirst()) { do {
	 * 
	 * Object_Item itm = new Object_Item(); itm.id =
	 * itemTxnCursor.getInt(itemTxnCursor.getColumnIndex(KEY_JOINVAL_ITMID));
	 * itm.name =
	 * itemTxnCursor.getString(itemTxnCursor.getColumnIndex(KEY_JOINVAL_ITMNAME
	 * )); itm.itemCatId =
	 * itemTxnCursor.getInt(itemTxnCursor.getColumnIndex(KEY_JOINVAL_ITMCATID));
	 * itm.description =
	 * itemTxnCursor.getString(itemTxnCursor.getColumnIndex(KEY_JOINVAL_ITMDESC
	 * ));
	 * 
	 * Object_Unit itmUnit = new Object_Unit(); itmUnit.id =
	 * itemTxnCursor.getInt(itemTxnCursor.getColumnIndex(KEY_JOINVAL_UNITID));
	 * itmUnit.name =
	 * itemTxnCursor.getString(itemTxnCursor.getColumnIndex(KEY_JOINVAL_UNITNAME
	 * )); itmUnit.printName =
	 * itemTxnCursor.getString(itemTxnCursor.getColumnIndex
	 * (KEY_JOINVAL_UNITPRINTNAME)); itm.unit = itmUnit; itm.printName =
	 * itemTxnCursor
	 * .getString(itemTxnCursor.getColumnIndex(KEY_JOINVAL_ITMPRINTNAME));
	 * itm.salePrice =
	 * itemTxnCursor.getDouble(itemTxnCursor.getColumnIndex(KEY_JOINVAL_ITMSALEPRICE
	 * )); itm.purchasePrice =
	 * itemTxnCursor.getDouble(itemTxnCursor.getColumnIndex
	 * (KEY_JOINVAL_ITMPURCPRICE)); itm.mrp =
	 * itemTxnCursor.getDouble(itemTxnCursor
	 * .getColumnIndex(KEY_JOINVAL_ITMMRP)); itm.minSalePrice =
	 * itemTxnCursor.getDouble
	 * (itemTxnCursor.getColumnIndex(KEY_JOINVAL_ITMMINSALEPRICE)); itm.daytime
	 * =
	 * itemTxnCursor.getString(itemTxnCursor.getColumnIndex(KEY_JOINVAL_ITMDAYTIME
	 * )); itm.qtyRemaining =
	 * itemTxnCursor.getInt(itemTxnCursor.getColumnIndex(KEY_JOINVAL_ITMQTYREM
	 * ));
	 * 
	 * Object_Item_Txn itmTxn = new Object_Item_Txn(); itmTxn.id =
	 * itemTxnCursor.getInt(itemTxnCursor.getColumnIndex(KEY_JOINVAL_ITMTXNID));
	 * itmTxn.qty =
	 * itemTxnCursor.getInt(itemTxnCursor.getColumnIndex(KEY_JOINVAL_ITMTXNQTY
	 * )); itmTxn.price =
	 * itemTxnCursor.getDouble(itemTxnCursor.getColumnIndex(KEY_JOINVAL_ITMTXNPRICE
	 * )); itmTxn.amount =
	 * itemTxnCursor.getDouble(itemTxnCursor.getColumnIndex(KEY_JOINVAL_ITMTXNAMOUNT
	 * ));
	 * 
	 * itm.itemTxn = itmTxn;
	 * 
	 * Object_Item_Category itmCat = new Object_Item_Category(); itmCat.id =
	 * itemTxnCursor.getInt(itemTxnCursor.getColumnIndex(KEY_JOINVAL_ITMCATID));
	 * itmCat.name =
	 * itemTxnCursor.getString(itemTxnCursor.getColumnIndex(KEY_JOINVAL_ITMCATNAME
	 * )); itmCat.accountId =
	 * itemTxnCursor.getInt(itemTxnCursor.getColumnIndex(KEY_JOINVAL_ITMCATACCNTID
	 * )); itmCat.description =
	 * itemTxnCursor.getString(itemTxnCursor.getColumnIndex
	 * (KEY_JOINVAL_ITMCATDESC)); itmCat.daytime =
	 * itemTxnCursor.getString(itemTxnCursor
	 * .getColumnIndex(KEY_JOINVAL_ITMCATDAYTIME));
	 * 
	 * ArrayList<Object_Item> itemsAL = new ArrayList<Object_Item>();
	 * itemsAL.add(itm);
	 * 
	 * itmCat.items = itemsAL;
	 * 
	 * if(itemCats.contains(itmCat)) { int itmCatIndex =
	 * itemCats.indexOf(itmCat); Object_Item_Category tempItemCat =
	 * itemCats.get(itmCatIndex); tempItemCat.items.add(itm);
	 * itemCats.remove(itmCatIndex); itemCats.add(itmCatIndex, tempItemCat); }
	 * else { itemCats.add(itmCat); }
	 * 
	 * } while (itemTxnCursor.moveToNext()); // Query for Item_Txn table } }
	 * //Log.i("accountsappdb","size of itemCats Arraylist : "+itemCats.size());
	 * 
	 * Object_Account accnt = new Object_Account(); accnt.id =
	 * accntCursor.getInt(accntCursor.getColumnIndex(KEY_JOINVAL_ACCNTID));
	 * accnt.name =
	 * accntCursor.getString(accntCursor.getColumnIndex(KEY_JOINVAL_ACCNTNAME));
	 * accnt.grpId =
	 * accntCursor.getInt(accntCursor.getColumnIndex(KEY_JOINVAL_ACCNTGRPID));
	 * accnt.address =
	 * accntCursor.getString(accntCursor.getColumnIndex(KEY_JOINVAL_ACCNTADDRESS
	 * )); accnt.contact =
	 * accntCursor.getString(accntCursor.getColumnIndex(KEY_JOINVAL_ACCNTCONTACT
	 * )); accnt.description =
	 * accntCursor.getString(accntCursor.getColumnIndex(KEY_JOINVAL_ACCNTDESC));
	 * accnt.daytime =
	 * accntCursor.getString(accntCursor.getColumnIndex(KEY_JOINVAL_ACCNTDAYTIME
	 * )); accnt.itemCategories = itemCats;
	 * 
	 * Object_Account_Txn acntTxn = new Object_Account_Txn(); acntTxn.id =
	 * accntCursor.getInt(accntCursor.getColumnIndex(KEY_JOINVAL_ATXNID));
	 * acntTxn.voucher_Txn_Id = objTxn.id; acntTxn.amount =
	 * accntCursor.getDouble
	 * (accntCursor.getColumnIndex(KEY_JOINVAL_ATXNAMOUNT)); acntTxn.narration =
	 * accntCursor
	 * .getString(accntCursor.getColumnIndex(KEY_JOINVAL_ATXNNARRATION));
	 * 
	 * Object_Txn_Master txnMstr = new Object_Txn_Master(); txnMstr.id =
	 * accntCursor.getInt(accntCursor.getColumnIndex(KEY_JOINVAL_TXNMSTRID));
	 * txnMstr.name =
	 * accntCursor.getString(accntCursor.getColumnIndex(KEY_JOINVAL_TXNMSTRNAME
	 * )); txnMstr.printName =
	 * accntCursor.getString(accntCursor.getColumnIndex(KEY_JOINVAL_TXNMSTRPRINTNAME
	 * )); acntTxn.txnType = txnMstr; accnt.accountTxn = acntTxn;
	 * 
	 * AL_objAccnt.add(accnt); }while(accntCursor.moveToNext()); // Query for
	 * Account Table } } objTxn.accounts = AL_objAccnt; allTxns.add(objTxn); }
	 * while (cursor.moveToNext()); // Query for voucher_TXn table } } } catch
	 * (Exception e) { e.printStackTrace(); } //Log.i("accountsappdb",
	 * "size of Arraylist from DBHandler_Txn is :"+allTxns.size()); return
	 * allTxns; }
	 */

	/*
	 * 
	 * USE THIS FOR DAILY LEDGER...
	 * 
	 * public ArrayList<Object_Voucher_Txn> getTxnByDate(String date,int
	 * voucherTypeId) {
	 * 
	 * 
	 * 
	 * Log.i("accountsappdb",
	 * "getTxnByDate - date : "+date+"  voucherTypeId: "+voucherTypeId);
	 * ArrayList<Object_Voucher_Txn> allTxns = new
	 * ArrayList<Object_Voucher_Txn>();
	 * 
	 * SQLiteDatabase db = this.getReadableDatabase(); //String sqlQuery =
	 * "select * from "+TABLE_NAME_VOUCHER_TXN
	 * +" WHERE "+KEY_VOUCHER_DAYTIME+" LIKE '"
	 * +date+"%' AND "+KEY_VOUCHER_VOUCHER_ID+" = "+voucherTypeId;
	 * 
	 * String sqlQuery =
	 * "SELECT  vtxn."+KEY_VOUCHER_TXN_ID+" vtxnId,vtxn."+KEY_VOUCHER_DAYTIME
	 * +" vtxndaytime,"+
	 * "vm."+DBHandler_voucher_master.KEY_VOUCHER_MASTER_ID+" vmId,vm."
	 * +DBHandler_voucher_master
	 * .KEY_VOUCHER_MASTER_NAME+" vmName,vm."+DBHandler_voucher_master
	 * .KEY_VOUCHER_MASTER_SCREENTYPE+" vmScrnType "+
	 * "FROM "+TABLE_NAME_VOUCHER_TXN+" vtxn "+
	 * "INNER JOIN "+DBHandler_voucher_master
	 * .TABLE_NAME_VOUCHER_MASTER+" vm ON vtxn."
	 * +KEY_VOUCHER_VOUCHER_ID+" = vm."+
	 * DBHandler_voucher_master.KEY_VOUCHER_MASTER_ID+" "+
	 * "WHERE "+KEY_VOUCHER_DAYTIME+" LIKE '"+date+"%' "+
	 * "AND "+KEY_VOUCHER_VOUCHER_ID+" = "+voucherTypeId;
	 * 
	 * Log.i("accountsappdb", "sqlQuery Main: \n"+sqlQuery);
	 * 
	 * Cursor cursor = db.rawQuery(sqlQuery, null);
	 * 
	 * try { if(cursor != null) { if(cursor.moveToFirst()) { do {
	 * Object_Voucher_Txn objTxn = new Object_Voucher_Txn(); objTxn.id =
	 * cursor.getInt(cursor.getColumnIndex("vtxnId")); objTxn.daytime =
	 * cursor.getString(cursor.getColumnIndex("vtxndaytime"));
	 * 
	 * Object_Voucher_Master vm4objTxn = new Object_Voucher_Master();
	 * vm4objTxn.id = cursor.getInt(cursor.getColumnIndex("vmId"));
	 * vm4objTxn.name = cursor.getString(cursor.getColumnIndex("vnName"));
	 * vm4objTxn.screenType =
	 * cursor.getString(cursor.getColumnIndex("vmScrnType"));
	 * 
	 * objTxn.voucher = vm4objTxn;
	 * 
	 * ArrayList<Object_Account> AL_objAccnt = new ArrayList<Object_Account>();
	 * 
	 * String sqlQueryAccntList =
	 * "SELECT atxn."+KEY_ACCOUNTTXN_ID+" "+KEY_JOINVAL_ATXNID
	 * +",atxn."+KEY_ACCOUNTTXN_ACCOUNT_ID
	 * +" "+KEY_JOINVAL_ATXNACCNTID+",atxn."+KEY_ACCOUNTTXN_ACCOUNT_VOUCHER_TXN_ID
	 * +" "+KEY_JOINVAL_ATXNVTXNID+",atxn."+KEY_ACCOUNTTXN_ACCOUNT_AMOUNT+" "+
	 * KEY_JOINVAL_ATXNAMOUNT
	 * +",atxn."+KEY_ACCOUNTTXN_ACCOUNT_TXN_TYPE_ID+" "+KEY_JOINVAL_ATXNTYPEID
	 * +",atxn."
	 * +KEY_ACCOUNTTXN_ACCOUNT_NARRATION+" "+KEY_JOINVAL_ATXNNARRATION+","+
	 * "acnt."
	 * +DBHandler_Account.KEY_ACCOUNT_ID+" "+KEY_JOINVAL_ACCNTID+",acnt."+
	 * DBHandler_Account
	 * .KEY_ACCOUNT_NAME+" "+KEY_JOINVAL_ACCNTNAME+",acnt."+DBHandler_Account
	 * .KEY_ACCOUNT_GRPID
	 * +" "+KEY_JOINVAL_ACCNTGRPID+",acnt."+DBHandler_Account.KEY_ACCOUNT_ADDRESS
	 * +
	 * " "+KEY_JOINVAL_ACCNTADDRESS+",acnt."+DBHandler_Account.KEY_ACCOUNT_CONTACT
	 * +" "+KEY_JOINVAL_ACCNTCONTACT+",acnt."+DBHandler_Account.
	 * KEY_ACCOUNT_DESCRIPTION
	 * +" "+KEY_JOINVAL_ACCNTDESC+",acnt."+DBHandler_Account
	 * .KEY_ACCOUNT_DAYTIME+" "+KEY_JOINVAL_ACCNTDAYTIME+","+
	 * "agrp1."+DBHandler_AccountsGroup
	 * .KEY_ACCOUNTGRP_ID+" "+KEY_JOINVAL_SUBGRPID
	 * +",agrp1."+DBHandler_AccountsGroup
	 * .KEY_ACCOUNTGRP_NAME+" "+KEY_JOINVAL_SUBGRPNAME
	 * +",agrp1."+DBHandler_AccountsGroup
	 * .KEY_ACCOUNTGRP_PARENTID+" "+KEY_JOINVAL_SUBGRPPARENTID
	 * +",agrp1."+DBHandler_AccountsGroup
	 * .KEY_ACCOUNTGRP_description+" "+KEY_JOINVAL_SUBGRPDESC
	 * +",agrp1."+DBHandler_AccountsGroup
	 * .KEY_ACCOUNTGRP_DAYTIME+" "+KEY_JOINVAL_SUBGRPDAYTIME+","+
	 * "agrp2."+DBHandler_AccountsGroup
	 * .KEY_ACCOUNTGRP_ID+" "+KEY_JOINVAL_MAINGRPID
	 * +",agrp2."+DBHandler_AccountsGroup
	 * .KEY_ACCOUNTGRP_NAME+" "+KEY_JOINVAL_MAINGRPNAME
	 * +",agrp2."+DBHandler_AccountsGroup
	 * .KEY_ACCOUNTGRP_PARENTID+" "+KEY_JOINVAL_MAINGRPPARENTID
	 * +",agrp2."+DBHandler_AccountsGroup
	 * .KEY_ACCOUNTGRP_description+" "+KEY_JOINVAL_MAINGRPDESC
	 * +",agrp2."+DBHandler_AccountsGroup
	 * .KEY_ACCOUNTGRP_DAYTIME+" "+KEY_JOINVAL_MAINGRPDAYTIME+" "+
	 * " FROM "+TABLE_NAME_ACCOUNT_TXN+" atxn "+
	 * "INNER JOIN "+DBHandler_Account.
	 * TABLE_NAME_ACCOUNT+" acnt ON acnt."+DBHandler_Account
	 * .KEY_ACCOUNT_ID+" = atxn."+KEY_ACCOUNTTXN_ACCOUNT_ID+" "+
	 * "LEFT JOIN "+DBHandler_AccountsGroup
	 * .TABLE_NAME_ACCOUNT_GRP+" agrp1 ON agrp1."
	 * +DBHandler_AccountsGroup.KEY_ACCOUNTGRP_ID
	 * +" = acnt."+DBHandler_Account.KEY_ACCOUNT_GRPID+" "+
	 * "LEFT JOIN "+DBHandler_AccountsGroup
	 * .TABLE_NAME_ACCOUNT_GRP+" agrp2 ON agrp2."
	 * +DBHandler_AccountsGroup.KEY_ACCOUNTGRP_ID
	 * +" = agrp1."+DBHandler_AccountsGroup.KEY_ACCOUNTGRP_PARENTID+" "+
	 * "WHERE "+KEY_ACCOUNTTXN_ACCOUNT_VOUCHER_TXN_ID+" = "+objTxn.id;
	 * 
	 * Log.i("accountsappdb", "sqlQueryAccntList: \n"+sqlQueryAccntList); Cursor
	 * accntCursor = db.rawQuery(sqlQueryAccntList, null); if(accntCursor !=
	 * null) { if(accntCursor.moveToFirst()) { do {
	 * if(accntCursor.getInt(accntCursor.getColumnIndex(KEY_JOINVAL_MAINGRPID))
	 * != 0) { Object_Account_Group mainGroup = new Object_Account_Group();
	 * mainGroup.id =
	 * accntCursor.getInt(accntCursor.getColumnIndex(KEY_JOINVAL_MAINGRPID));
	 * mainGroup.name =
	 * accntCursor.getString(accntCursor.getColumnIndex(KEY_JOINVAL_MAINGRPNAME
	 * )); mainGroup.parentId
	 * =accntCursor.getInt(accntCursor.getColumnIndex(KEY_JOINVAL_MAINGRPPARENTID
	 * )); mainGroup.description =
	 * accntCursor.getString(accntCursor.getColumnIndex
	 * (KEY_JOINVAL_MAINGRPDESC)); mainGroup.daytime =
	 * accntCursor.getString(accntCursor
	 * .getColumnIndex(KEY_JOINVAL_MAINGRPDAYTIME));
	 * 
	 * if(AL_objAccntGrps.contains(mainGroup)) { //check if subgroup exists
	 * Object_Account_Group subGroup = new Object_Account_Group(); subGroup.id =
	 * accntCursor.getInt(accntCursor.getColumnIndex(KEY_JOINVAL_SUBGRPID));
	 * subGroup.name =
	 * accntCursor.getString(accntCursor.getColumnIndex(KEY_JOINVAL_SUBGRPNAME
	 * )); subGroup.parentId
	 * =accntCursor.getInt(accntCursor.getColumnIndex(KEY_JOINVAL_SUBGRPPARENTID
	 * )); subGroup.description =
	 * accntCursor.getString(accntCursor.getColumnIndex
	 * (KEY_JOINVAL_SUBGRPDESC)); subGroup.daytime =
	 * accntCursor.getString(accntCursor
	 * .getColumnIndex(KEY_JOINVAL_SUBGRPDAYTIME));
	 * 
	 * int acGrpIndexInList = AL_objAccntGrps.indexOf(mainGroup);
	 * Object_Account_Group acGrpOfList = AL_objAccntGrps.get(acGrpIndexInList);
	 * if(acGrpOfList.accountGroups.contains(subGroup)) { //subGroup already
	 * Exists
	 * 
	 * } else { //No subGroup found } } else { //create maingroup which contains
	 * accountDetail also. } } else
	 * if(accntCursor.getInt(accntCursor.getColumnIndex(KEY_JOINVAL_MAINGRPID))
	 * == 0) {
	 * 
	 * } }while(accntCursor.moveToNext()); } } //objTxn.accountTxn =
	 * objAccntTxnList; //ArrayList<AA_Object_ItemTxn_2> objItemTxnList = new
	 * ArrayList<AA_Object_ItemTxn_2>();
	 * 
	 * String sqlQueryItemList =
	 * "SELECT "+TABLE_NAME_TXN_ITEM+"."+KEY_TXN_ITEM_ID
	 * +","+KEY_TXN_ITEM_ITEM_ID
	 * +","+KEY_TXN_ITEM_QTY+","+KEY_TXN_ITEM_PRICE+","+
	 * KEY_TXN_ITEM_VOUCHER_TXN_ID
	 * +","+KEY_TXN_DETAIL_AMOUNT+","+KEY_TXN_DETAIL_NARRATION
	 * +","+KEY_TXN_DETAIL_TXN_TYPE_ID+
	 * " FROM "+TABLE_NAME_TXN_ITEM+" INNER JOIN "
	 * +TABLE_NAME_TXN_DETAIL+" ON "+TABLE_NAME_TXN_ITEM
	 * +"."+KEY_TXN_ITEM_TXN_DETAIL_ID
	 * +" = "+TABLE_NAME_TXN_DETAIL+"."+KEY_TXN_DETAIL_ID
	 * +" WHERE "+TABLE_NAME_TXN_ITEM
	 * +"."+KEY_TXN_ITEM_VOUCHER_TXN_ID+" = "+objTxn.id;
	 * 
	 * Log.i("accountsappdb", "sqlQueryItemList: \n"+sqlQueryItemList);
	 * 
	 * Cursor itemCursor = db.rawQuery(sqlQueryItemList, null); if(itemCursor !=
	 * null) { if(itemCursor.moveToFirst()) { do { AA_Object_ItemTxn_2 itemTxn =
	 * new AA_Object_ItemTxn_2(); itemTxn.itemId =
	 * itemCursor.getInt(itemCursor.getColumnIndex(KEY_TXN_ITEM_ITEM_ID));
	 * itemTxn.qty =
	 * itemCursor.getInt(itemCursor.getColumnIndex(KEY_TXN_ITEM_QTY));
	 * itemTxn.price =
	 * itemCursor.getDouble(itemCursor.getColumnIndex(KEY_TXN_ITEM_PRICE));
	 * itemTxn.amount =
	 * itemCursor.getDouble(itemCursor.getColumnIndex(KEY_TXN_DETAIL_AMOUNT));
	 * itemTxn.txnTypeId =
	 * itemCursor.getInt(itemCursor.getColumnIndex(KEY_TXN_DETAIL_TXN_TYPE_ID));
	 * itemTxn.narration =
	 * itemCursor.getString(itemCursor.getColumnIndex(KEY_TXN_DETAIL_NARRATION
	 * )); objItemTxnList.add(itemTxn); } while (itemCursor.moveToNext()); } }
	 * objTxn.itemTxn = objItemTxnList;
	 * 
	 * //allTxns.add(objTxn); } while (cursor.moveToNext()); } } } catch
	 * (Exception e) { e.printStackTrace(); } //Log.i("accountsappdb",
	 * "at end of DBH TXN : size of arraylist: "+allTxns.size()); return
	 * allTxns; }
	 */

}
