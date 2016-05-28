package com.XB_Accounts;

import java.util.ArrayList;

public class Object_Account {

	public int id;
	public String name;
	public int grpId;
	public String address;
	public String contact;
	public String description;
	public String daytime;
	public String grpName;
	public double rate;
	public double time;
	public String daytime_open;
	public ArrayList<Object_Item_Category> itemCategories;
	//public ArrayList<Object_Account_Txn> accountTxn;
	public Object_Account_Txn accountTxn;
	public boolean partyAc = false;
}
