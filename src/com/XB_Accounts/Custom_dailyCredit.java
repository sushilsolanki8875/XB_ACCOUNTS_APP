package com.XB_Accounts;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.ClipData.Item;
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

public class Custom_dailyCredit extends Fragment {
	ImageView btndailyentry;
	static TextView txt;
	// private TextView txtFrom;
	private int year;
	private int month;
	private int day;
	static final int DATE_DIALOG_ID = 333;
	static final int DATE_DIALOG_ID_to = 334;
	static ListView list;
	TextView txttotal;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		final View accounts = inflater.inflate(R.layout.custom_daily_credit,
				container, false);
		txt = (TextView) accounts.findViewById(R.id.txttodate);
		txttotal = (TextView) accounts.findViewById(R.id.txtTotalcash);
		// txtFrom = (TextView)accounts.findViewById(R.id.textVidate);

		setCurrentDateOnview();

		list = ((ListView) accounts.findViewById(R.id.listViewcredit));
		DBHandler_Txn dbhtxn = new DBHandler_Txn(getActivity());
		ArrayList<Object_Account_Group> listAccount_Group = dbhtxn
				.getDailyLedger(txt.getText().toString(), 1);
		// Log.i("SUSHIL", "list size is....."+listAccount_Group.size());
		Custom_adapterDailylg_Credit adapter = new Custom_adapterDailylg_Credit(
				getActivity(), listAccount_Group);
		list.setAdapter(adapter);
		
		double cash = dbhtxn.getDailyCash();
		txttotal.setText(cash+"");
		//Log.i("SUSHIL", "SUSHil cash "+cash);
		txt.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// getActivity().showDialog(DATE_DIALOG_ID);
				createFancyDatePicker(DATE_DIALOG_ID).show();
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

		Button btn = (Button) accounts.findViewById(R.id.button2);
		btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				getdataOnGo();
			}
		});

		/*TextView txtcash = (TextView)accounts.findViewById(R.id.txtCreditgrandTotalcash);
		   TextView txttran = (TextView)accounts.findViewById(R.id.txtCreditgrandTotalTransfer);
		   TextView txtotal = (TextView)accounts.findViewById(R.id.txtCreditgrandTotal);
		   txtcash.setText(Custom_adapterDailylg_Credit.cash+"");
		   txttran.setText(Custom_adapterDailylg_Credit.transfer+"");
		   txtotal.setText(Custom_adapterDailylg_Credit.total+"");*/
		
		
		
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

	/*
	 * private void DailyEntry(View view) { //btndailyentry = (ImageView)
	 * view.findViewById(R.id.imageEntryDaily);
	 * btndailyentry.setOnClickListener(new OnClickListener() {
	 * 
	 * @Override public void onClick(View v) {
	 * 
	 * Intent i = new Intent(getActivity(), Activity_dataEntrydailyLager.class);
	 * i.putExtra("Accounts", "All"); i.putExtra("Context",
	 * "Custom_dailyCredit.class"); startActivity(i);
	 * 
	 * } });
	 * 
	 * }
	 */
	/*
	 * protected Dialog onCreateDialog(int id){ switch (id){ case
	 * DATE_DIALOG_ID_to: return new
	 * DatePickerDialog(getActivity(),datePickerListenerTo,year,month,day); case
	 * DATE_DIALOG_ID: return new
	 * DatePickerDialog(getActivity(),Globals.datepic(txt),year,month,day); }
	 * return null; }
	 * 
	 * private DatePickerDialog.OnDateSetListener datePickerListenerTo = new
	 * DatePickerDialog.OnDateSetListener(){
	 * 
	 * public void onDateSet(DatePicker view,int selectedYear,int
	 * selectedMonth,int selectedDay) { year= selectedYear; month=
	 * selectedMonth; month = month+1; day =selectedDay; txt.setText(day+ "/"
	 * +(month+1)+ "/" + year); //dpresult.init(year, month, day, null); } };
	 * public void onDateSet(DatePicker arg0, int arg1, int arg2, int arg3) { //
	 * TODO Auto-generated method stub
	 * 
	 * }
	 */

	protected Dialog createFancyDatePicker(int id) {
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
		// get credit data
		DBHandler_Txn dbhtxn = new DBHandler_Txn(getActivity());
		ArrayList<Object_Account_Group> listAccount_Group = dbhtxn
				.getDailyLedger(txt.getText().toString(), 1);
		// Log.i("SUSHIL", "list size is....."+listAccount_Group.size());
		Custom_adapterDailylg_Credit adapter = new Custom_adapterDailylg_Credit(
				getActivity(), listAccount_Group);
		list.setAdapter(adapter);

		// Custom_dailyDebit.date = txt.getText().toString();
		// get debit data

		ArrayList<Object_Account_Group> listAccount_Groupde = dbhtxn
				.getDailyLedger(txt.getText().toString(), 2);
		Custom_adapterDailylg_Credit adapterDe = new Custom_adapterDailylg_Credit(
				getActivity(), listAccount_Groupde);
		Custom_dailyDebit.list.setAdapter(adapterDe);
		
		Custom_dailyDebit.txt.setText(txt.getText().toString());
	}
}
