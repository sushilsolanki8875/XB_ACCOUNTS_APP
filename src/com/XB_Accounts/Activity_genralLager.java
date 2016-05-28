package com.XB_Accounts;

import java.util.ArrayList;
import java.util.Calendar;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class Activity_genralLager extends Activity {

	private TextView FromDate;
	private TextView ToDate;
	private int year;
	private int month;
	private int day;
	static final int DATE_DIALOG_ID = 333;
	static final int DATE_DIALOG_ID_to = 334;
	String fromDate;
	String toDate;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_genral_lager);
		// addListenerOnbutton();
		Object_AppConfig config = new Object_AppConfig(this);
		if (config.getAccountId() != 0) {
			DBHandler_Account Dbh = new DBHandler_Account(this);
			Object_Account obj = Dbh.getAccountById(config.getAccountId());
			TextView txtAcName = (TextView) findViewById(R.id.txtChooseAc);
			txtAcName.setText(obj.name);
			FromDate = (TextView) findViewById((R.id.textVidate));
			FromDate.setOnClickListener(new OnClickListener() {

				public void onClick(View v) {
					showDialog(DATE_DIALOG_ID);
				}
			});
			ToDate = (TextView) findViewById((R.id.txttodate));
			ToDate.setOnClickListener(new OnClickListener() {

				public void onClick(View v) {
					showDialog(DATE_DIALOG_ID_to);
				}
			});
			setCurrentDateOnview();
			controlResize();

		} else {

			Intent i = new Intent(this, Activity_accountChooser.class);
			i.putExtra("Type", "Accounts");
			startActivity(i);
		}

	}

	private void controlResize() {
		int font = Globals.getAppFontSize(this);
		TextView txtAccount = (TextView) findViewById(R.id.txtAc);
		TextView txtAccountchos = (TextView) findViewById(R.id.txtChooseAc);
		TextView txtdateFrom = (TextView) findViewById(R.id.txtfrom);
		TextView txtDateTo = (TextView) findViewById(R.id.txtTo);
		Button btnGo = (Button) findViewById(R.id.btnGO);
		TextView txtGldate = (TextView) findViewById(R.id.txtGLdate);
		TextView txtGlparti = (TextView) findViewById(R.id.txtGLparti);
		TextView txtGldebit = (TextView) findViewById(R.id.txtGLdebitRS);
		TextView txtGlcredit = (TextView) findViewById(R.id.txtGLcreditRS);
		TextView txtGlAmount = (TextView) findViewById(R.id.txtGLbalence);

		txtAccount.setTextSize(font);
		txtAccountchos.setTextSize(font);
		txtdateFrom.setTextSize(font);
		txtDateTo.setTextSize(font);
		btnGo.setTextSize(font);
		FromDate.setTextSize(font);
		ToDate.setTextSize(font);
		txtGldate.setTextSize(font);
		txtGlparti.setTextSize(font);
		txtGldebit.setTextSize(font);
		txtGlcredit.setTextSize(font);
		txtGlAmount.setTextSize(font);

	}

	public void setCurrentDateOnview() {
		final Calendar c = Calendar.getInstance();
		year = c.get(Calendar.YEAR);
		month = c.get(Calendar.MONTH);
		day = c.get(Calendar.DAY_OF_MONTH);
		FromDate.setText(add(year) + "-" + add(month) + "-" + add(day));
		ToDate.setText(add(year) + "-" + add(month + 1) + "-" + add(day));
		fromDate = add(year) + "-" + add(month) + "-" + add(day - 1);
		toDate = add(year) + "-" + add(month + 1) + "-" + add(day + 1);
		// dpresult.init(year, month, day, null);
	}

	/*
	 * public void onClickbutton(View v){ showDialog(DATE_DIALOG_ID); }
	 */

	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case DATE_DIALOG_ID:
			return new DatePickerDialog(this, dateFrom(FromDate), year, month,
					day);
		case DATE_DIALOG_ID_to:
			return new DatePickerDialog(this, dateTo(ToDate), year, month, day);
		}
		return null;
	}

	/*
	 * private DatePickerDialog.OnDateSetListener datePickerListener = new
	 * DatePickerDialog.OnDateSetListener() {
	 * 
	 * public void onDateSet(DatePicker view, int selectedYear, int
	 * selectedMonth, int selectedDay) { year = selectedYear; month =
	 * selectedMonth; month = month + 1; day = selectedDay;
	 * 
	 * FromDate.setText(day + "/" + month + "/" + year);
	 * 
	 * // dpresult.init(year, month, day, null); } }; private
	 * DatePickerDialog.OnDateSetListener datePickerListenerTo = new
	 * DatePickerDialog.OnDateSetListener() {
	 * 
	 * public void onDateSet(DatePicker view, int selectedYear, int
	 * selectedMonth, int selectedDay) { year = selectedYear; month =
	 * selectedMonth; month = month + 1; day = selectedDay;
	 * 
	 * ToDate.setText(day + "/" + month + "/" + year); // dpresult.init(year,
	 * month, day, null); } };
	 * 
	 * public void onDateSet(DatePicker arg0, int arg1, int arg2, int arg3) { //
	 * TODO Auto-generated method stub
	 * 
	 * }
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.action_Print) {
			Intent i = new Intent(this, Activity_printPreview.class);
			startActivity(i);
		}
		return true;
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

	public void accountChoose(View v) {

		Intent i = new Intent(this, Activity_accountChooser.class);
		i.putExtra("Type", "Accounts");
		startActivity(i);
	}

	@Override
	protected void onResume() {
		super.onResume();
		LinearLayout linearList = (LinearLayout) findViewById(R.id.linearList);
		LinearLayout empty = (LinearLayout) findViewById(R.id.empty);
		Object_AppConfig config = new Object_AppConfig(this);
		if (config.getAccountId() != 0) {
			DBHandler_Account Dbh = new DBHandler_Account(this);
			Object_Account obj = Dbh.getAccountById(config.getAccountId());
			TextView txtAcName = (TextView) findViewById(R.id.txtChooseAc);
			txtAcName.setText(obj.name);
			ListView listview = (ListView) findViewById(R.id.listViewGL);
			DBHandler_Txn DbhTxn = new DBHandler_Txn(this);
			ArrayList<Object_Voucher_Txn> list = DbhTxn.getGeneralLedger(
					config.getAccountId(), fromDate, toDate);
			if (list.size() != 0) {
				empty.setVisibility(View.GONE);
				linearList.setVisibility(View.VISIBLE);
				Custom_adapterGladger adapter = new Custom_adapterGladger(this,
						list, 20);
				listview.setAdapter(adapter);
			} else {
				empty.setVisibility(View.VISIBLE);
				linearList.setVisibility(View.GONE);
			}
		}
		
		//Object_AppConfig config = new Object_AppConfig(this);
		Globals.setLocale(config.getLocale(), this);
	}

	private DatePickerDialog.OnDateSetListener dateFrom(final TextView txtView) {

		return new DatePickerDialog.OnDateSetListener() {

			public void onDateSet(DatePicker view, int Year, int monthOfYear,
					int dayOfMonth) {

				txtView.setText(add(Year) + "-" + add((monthOfYear + 1)) + "-"
						+ add(dayOfMonth));
				fromDate = add(Year) + "-" + add((monthOfYear + 1)) + "-"
						+ add(dayOfMonth - 1);
				// createFancyDateTimePicker(DATE_DIALOG_ID).show();
			}
		};

	}

	private DatePickerDialog.OnDateSetListener dateTo(final TextView txtView) {

		return new DatePickerDialog.OnDateSetListener() {

			public void onDateSet(DatePicker view, int Year, int monthOfYear,
					int dayOfMonth) {

				txtView.setText(add(Year) + "-" + add((monthOfYear + 1)) + "-"
						+ add(dayOfMonth));
				toDate = add(Year) + "-" + add((monthOfYear + 1)) + "-"
						+ add(dayOfMonth + 1);
				// createFancyDateTimePicker(DATE_DIALOG_ID).show();
			}
		};

	}

	public void onSubmit(View v) {
		Object_AppConfig config = new Object_AppConfig(this);
		LinearLayout linearList = (LinearLayout) findViewById(R.id.linearList);
		LinearLayout empty = (LinearLayout) findViewById(R.id.empty);
		ListView listview = (ListView) findViewById(R.id.listViewGL);
		DBHandler_Txn DbhTxn = new DBHandler_Txn(this);
		ArrayList<Object_Voucher_Txn> list = DbhTxn.getGeneralLedger(
				config.getAccountId(), fromDate, toDate);
		if(list.size() != 0) {
			empty.setVisibility(View.GONE);
			linearList.setVisibility(View.VISIBLE);
			Custom_adapterGladger adapter = new Custom_adapterGladger(this,
					list, 20);
			listview.setAdapter(adapter);
		} else {
			empty.setVisibility(View.VISIBLE);
			linearList.setVisibility(View.GONE);
		}
	}

	
	
	
}
