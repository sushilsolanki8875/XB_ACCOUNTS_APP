package com.XB_Accounts;

import java.util.ArrayList;
import java.util.Calendar;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class Custom_dailyDebit extends Fragment {
	ImageView btndailyentry;
	static TextView txt;
	// private TextView txtFrom;
	private int year;
	private int month;
	private int day;
	static final int DATE_DIALOG_ID = 333;
	static final int DATE_DIALOG_ID_to = 334;
	String DateTxn;
	static ListView list;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		final View accounts = inflater.inflate(R.layout.custom_daily_debit,
				container, false);
		txt = (TextView) accounts.findViewById(R.id.txttodate);
		// txtFrom = (TextView)accounts.findViewById(R.id.textVidate);

		setCurrentDateOnview();

		list = ((ListView) accounts.findViewById(R.id.listViewDailyDebit));
		/*
		 * ArrayList<Object_Item_Category > listCat = new
		 * ArrayList<Object_Item_Category>(); ArrayList<Object_Item > listitems
		 * = new ArrayList<Object_Item>(); ArrayList<Object_Account > listAc =
		 * new ArrayList<Object_Account>(); ArrayList< Object_Account_Group>
		 * list = new ArrayList<Object_Account_Group>(); ArrayList<
		 * Object_Account_Group> listMain = new
		 * ArrayList<Object_Account_Group>(); Object_Account_Group acgrop = new
		 * Object_Account_Group(); acgrop.name = "Rush"; acgrop.daytime =
		 * "8/6/2015"; for(int i=0;i<1;i++){ Object_Account_Group obj = new
		 * Object_Account_Group(); obj.name = "Fertilizers"; for(int
		 * j=0;j<1;j++){ Object_Account objAc = new Object_Account(); objAc.name
		 * = "Urea"; Object_Account_Txn txn = new Object_Account_Txn();
		 * txn.amount = 4500; objAc.accountTxn = txn; listAc.add(objAc);
		 * obj.accounts = listAc; for(int t =0;t<1;t++){ Object_Item_Category
		 * cate = new Object_Item_Category(); for(int y =0;y<1;y++){ Object_Item
		 * item = new Object_Item(); Object_Item_Txn txnItem = new
		 * Object_Item_Txn(); txnItem.qty = 500; Object_Unit uni = new
		 * Object_Unit(); uni.name = "Bags"; item.unit = uni;
		 * listitems.add(item); } cate.items = listitems; listCat.add(cate); }
		 * objAc.itemCategories = listCat; } list.add(obj); }
		 * acgrop.accountGroups = list; listMain.add(acgrop);
		 */
		DBHandler_Txn dbhtxn = new DBHandler_Txn(getActivity());
		ArrayList<Object_Account_Group> listAccount_Group = dbhtxn
				.getDailyLedger(txt.getText().toString(), 2);
		Custom_adapterDailylg_Credit adapter = new Custom_adapterDailylg_Credit(
				getActivity(), listAccount_Group);
		list.setAdapter(adapter);

		txt.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// getActivity().showDialog(DATE_DIALOG_ID);
				createFancyDateTimePicker(DATE_DIALOG_ID).show();
			}
		});

		Button btn = (Button) accounts.findViewById(R.id.button2);
		btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				getdataOnGo();
			}
		});

		/*
		 * txtFrom.setOnClickListener(new OnClickListener() {
		 * 
		 * @Override public void onClick(View v) {
		 * createFancyDateTimePicker(DATE_DIALOG_ID_to).show();
		 * 
		 * } });
		 */
		// DailyEntry(accounts);
		return accounts;
	}

	private String add(int c) {
		String str = "";
		if (c <= 9) {
			str = "0" + c;
		} else {
			str = c + "";
		}
		return str;
	}

	protected Dialog createFancyDateTimePicker(int id) {
		switch (id) {
		case DATE_DIALOG_ID:
			return new DatePickerDialog(getActivity(), Globals.datepic(txt),
					year, month, day);
		}

		return null;
	}

	private void setCurrentDateOnview() {

		final Calendar c = Calendar.getInstance();
		year = c.get(Calendar.YEAR);
		month = c.get(Calendar.MONTH);
		day = c.get(Calendar.DAY_OF_MONTH);
		txt.setText(add(year) + "-" + add((month + 1)) + "-" + add(day));
		// txtFrom.setText(day+ "/" +(month+1)+ "/" + year);
		// Log.i("SUSHIL","Current Date has been set "+day+ "/" +(month+1)+ "/"
		// + year);
		// dpresult.init(year, month, day, null);

	}

	private void getdataOnGo() {
		// get debit data
		DBHandler_Txn dbhtxn = new DBHandler_Txn(getActivity());
		ArrayList<Object_Account_Group> listAccount_Group = dbhtxn
				.getDailyLedger(txt.getText().toString(), 2);
		Custom_adapterDailylg_Credit adapter = new Custom_adapterDailylg_Credit(
				getActivity(), listAccount_Group);
		list.setAdapter(adapter);

		// get credit data
		ArrayList<Object_Account_Group> listAccount_GroupCr = dbhtxn
				.getDailyLedger(txt.getText().toString(), 1);
		// Log.i("SUSHIL", "list size is....."+listAccount_Group.size());
		Custom_adapterDailylg_Credit adapterCr = new Custom_adapterDailylg_Credit(
				getActivity(), listAccount_GroupCr);
		Custom_dailyCredit.list.setAdapter(adapterCr);
		Custom_dailyCredit.txt.setText(txt.getText().toString());
	}
}
