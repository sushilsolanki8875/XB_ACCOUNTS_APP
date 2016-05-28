package com.XB_Accounts;


public class Object_Account_Txn {

	public int id;
	public int voucher_Txn_Id;
	public double amount;
	public int isCash;
	public int isInterest = 0;
	public int isOpening = 0;
	public String narration;
	
	public Object_Txn_Master txnType;
	//public ArrayList<Object_Item_Txn> itemTxns;
}
