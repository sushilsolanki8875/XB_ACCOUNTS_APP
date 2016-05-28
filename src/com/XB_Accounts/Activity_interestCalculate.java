package com.XB_Accounts;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings.Global;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Activity_interestCalculate extends Activity {
	static final int DATE_DIALOG_ID = 333;
	private int year;
	private int month;
	private int day;
	private TextView txt;
	String dayTime = "";
    double Amount = 0;
    double Rate = 0;
    double interest = 0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_interest_calculate);
		Custom_adapterGrid.object = null;
		txt = (TextView) findViewById(R.id.txtDateInterest);
		setCurrentDateOnview();
		// ImageView datepicker = (ImageView)findViewById(R.id.Datepickker);
		/*
		 * datepicker.setOnClickListener(new OnClickListener() {
		 * 
		 * @Override public void onClick(View v) {
		 * //getActivity().showDialog(DATE_DIALOG_ID);
		 * createFancyDateTimePicker(DATE_DIALOG_ID).show(); } });
		 */

		// getDays();
		// DBHandler_Txn dbhTxn = new DBHandler_Txn(this);
		// dbhTxn.getInterestCalucation(39);
	}

	@Override
	protected void onResume() {
		super.onResume();
		TextView txtAmount = (TextView) findViewById(R.id.interestxtamountShow);
		TextView txtTime = (TextView) findViewById(R.id.txtDateInterest);
		TextView txtrate = (TextView) findViewById(R.id.interestxtrateShow);
		txtAmount.setText("");
		txtTime.setText("");
		txtrate.setText("");
		
		Object_AppConfig config = new Object_AppConfig(this);
		Globals.setLocale(config.getLocale(), this);
		
		if (Custom_adapterGrid.object != null) {
			TextView txtAccount = (TextView) findViewById(R.id.interestxtchooseAc);
			txtAccount.setText(Custom_adapterGrid.object.name);
			if (Custom_adapterGrid.object.rate!= 0
					&& Custom_adapterGrid.object.time!= 0) {
				
				txtrate.setText(Custom_adapterGrid.object.rate + "%");
				Rate = Custom_adapterGrid.object.rate;
				DBHandler_Txn dbhTxn = new DBHandler_Txn(this);
				Object_Interest obj = dbhTxn
						.getInterestCalucation(Custom_adapterGrid.object.id);
				if (obj.credit!= 0 && obj.debit!= 0) {
					Amount = Math.abs(obj.debit - obj.credit);
					//txtAmount = (TextView) findViewById(R.id.interestxtamountShow);
					txtAmount.setText(Amount + " RS.");
				}
				if (obj.dayTime != null && !obj.dayTime.trim().isEmpty()) {
					dayTime = obj.dayTime.substring(0, 10);
					Log.i("SUSHIL", "SUSHIL date " + dayTime);
					
					if (!dayTime.trim().isEmpty())
						txtTime.setText(getDays(dayTime) + " Days");
				}
			} else {
				Toast.makeText(this,
						"Interest is not calucate of this account",
						Toast.LENGTH_SHORT).show();
			}
		}
	}

	private void setCurrentDateOnview() {

		final Calendar c = Calendar.getInstance();
		year = c.get(Calendar.YEAR);
		month = c.get(Calendar.MONTH);
		day = c.get(Calendar.DAY_OF_MONTH);

		// txt.setText(day+ "/" +(month+1)+ "/" + year);

		// dpresult.init(year, month, day, null);

	}

	protected Dialog createFancyDateTimePicker(int id) {
		switch (id) {
		case DATE_DIALOG_ID:
			return new DatePickerDialog(this, Globals.datepic(txt), year,
					month, day);
		}

		return null;
	}

	/*
	 * private void resizeControl(){ TextView txt =
	 * (TextView)findViewById(R.id.txtDateInterest); TextView txtAmount =
	 * (TextView)findViewById(R.id.interestxtAmount); TextView txt =
	 * (TextView)findViewById(R.id.interestxtamountShow); TextView txt =
	 * (TextView)findViewById(R.id.interestxtRate); TextView txt =
	 * (TextView)findViewById(R.id.interestxtrateShow); TextView txt =
	 * (TextView)findViewById(R.id.interestxtDuration);
	 * txt.setTextSize(Globals.getAppFontSize(this)); }
	 */

	public void chooseAc(View v) {
		Intent i = new Intent(this, Activity_accountChooser.class);
		i.putExtra("Type", "Accounts");
		startActivity(i);
	}

	private int getDays(String date) {
		SimpleDateFormat dfDate = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date d = null;
		java.util.Date d1 = null;
		Calendar cal = Calendar.getInstance();
		try {
			d = dfDate.parse(date);
			d1 = dfDate.parse(dfDate.format(cal.getTime()));// Returns
															// 15/10/2012
		} catch (java.text.ParseException e) {
			e.printStackTrace();
		}

		int diffInDays = (int) ((d1.getTime() - d.getTime()) / (1000 * 60 * 60 * 24));
		return diffInDays;
	}

	public void interest(View v){
		//boolean calucate = false;
		if(Amount!=0 && Rate!=0){
			//calucate = true;
			
			CheckBox check = (CheckBox)findViewById(R.id.checkBoxChooseInrest);
			if(!check.isChecked())
			interest = caluclateInterest(Amount,Rate);
			else
			interest = calucateCompount(Amount,Rate);
			
			
			TextView txt = (TextView)findViewById(R.id.txtInrestShow);
			txt.setVisibility(View.VISIBLE);
			txt.setText("Your interest is Rs. = "+interest);
			
		}else{
			Toast.makeText(this, "Amount/Rate must not be 0", Toast.LENGTH_SHORT).show();
		}
	}
	
	
	
	private double caluclateInterest(double Amount, double rate) {
		int days = -1;
		if (!dayTime.trim().isEmpty()) {
			days = getDays(dayTime);
		}
		double interest = 0;
		if (days != -1) {
			interest = (Amount * rate * days) / (365 * 100);
		}else{
			Toast.makeText(this, "Calucation Error", Toast.LENGTH_SHORT).show();
		}
		return interest;
	}
	
	private double calucateCompount(double Amount, double rate){
		int days = -1;
		if (!dayTime.trim().isEmpty()) {
			days = getDays(dayTime);
		}
		if (days != -1) {
		if(days>=365){
		   int year = days/365;
		   int day = days % 30;
		  for(int i = 1;i<=year;i++){
			  Amount = Amount + interest; 
			  interest = (Amount * rate * days) / 100;
			}
		 if(day!=0) 
		   interest = interest + (Amount * rate * day) / (365 * 100);
		}else{
			interest = (Amount * rate * days) / (365 * 100);
		}
		}else{
			Toast.makeText(this, "Calucation Error", Toast.LENGTH_SHORT).show();
		}
		return interest;
	}
	
	
	public void trasaction(View v){
		
		if(interest!=0){
			Globals.showAlertDialog("Message", "Your interest is Rs. = "+interest, this, "Ok",  new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog,int id) {
                	nevigation(); 
                }
              }, "Cancel", new DialogInterface.OnClickListener() {
                  public void onClick(DialogInterface dialog,int id) {
                  }
                }, false);
			
		    }else{
			Toast.makeText(this,"Interest amount is 0", Toast.LENGTH_SHORT).show();
		}
	}
	
	private void nevigation(){
		Intent i = new Intent(this,Activity_transaction.class);
		i.putExtra("into","new");
		startActivity(i);
	}
}
