package com.XB_Accounts;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

public class Activity_voucher extends Activity {
    int selectId;
	Activity context;
	private TextView txt;
	//private TextView txtFrom;
	private int year;
	private int month;
	private int day;
	static final int DATE_DIALOG_ID = 333;
	ListView lv_mainContainer;
	 HashMap<Integer,Object_Voucher_Master> voucherMasterIdMapping;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_voucher);
		
		/*DBHandler_Main dbhMain = new DBHandler_Main(getApplicationContext());
		try {
			dbhMain.createDataBase();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		//DBHandler_AccountsGroup test = new DBHandler_AccountsGroup(getApplicationContext());
		//test.getAllAccountGrp();
		
		DBHandler_voucher_master dbhVoucherMaster = new DBHandler_voucher_master(getApplicationContext());
		
		final Spinner spinnerVoucherMaster = (Spinner)findViewById(R.id.spinner_voucher_master);
		 lv_mainContainer = (ListView)findViewById(R.id.lst_mainContainer);
		context = Activity_voucher.this;
		
		ArrayList<String> voucherMasterNameList = new ArrayList<String>();
		voucherMasterIdMapping = new HashMap<Integer,Object_Voucher_Master>();
		
		int i=0;
		for(Object_Voucher_Master voucher: dbhVoucherMaster.getAllVouchers())
		{
			voucherMasterNameList.add(voucher.name);
			voucherMasterIdMapping.put(i++, voucher);
		}
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,voucherMasterNameList);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnerVoucherMaster.setAdapter(adapter);
		
		
		spinnerVoucherMaster.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,int position, long id) {
				// TODO Auto-generated method stub
				//Log.i("accountsappdb","Position :"+position+"     voucherId:"+voucherMasterIdMapping.get(position).id+"   name:"+voucherMasterIdMapping.get(position).name+"    screentype:"+voucherMasterIdMapping.get(position).screenType);
				selectId = position;
				DBHandler_Txn txn = new DBHandler_Txn(getApplicationContext());
				ArrayList<Object_Voucher_Txn> allTxnData = txn.getTxnByDate(txt.getText().toString(), voucherMasterIdMapping.get(position).id);
				//Log.i("accountsappdb", "Size of arraylist for Adapter:"+allTxnData.size());
				Custom_adapterVoucher_view adapter = new Custom_adapterVoucher_view(context,R.layout.custom_row_voucher_view, allTxnData);
				lv_mainContainer.setAdapter(adapter);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				
			}
		});
		
		 txt = (TextView)findViewById(R.id.txt_date);
		 setCurrentDateOnview();
		 txt.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				createFancyDateTimePicker(DATE_DIALOG_ID).show();
				
			}
		});
	}
	
	protected Dialog createFancyDateTimePicker(int id) {
		switch (id) {
		case DATE_DIALOG_ID:
			return new DatePickerDialog(this, datePickerListener,
					year, month, day);
		}

		return null;
	}
    private void setCurrentDateOnview() {
		
		final Calendar c = Calendar.getInstance();
		year = c.get(Calendar.YEAR);
		month = c.get(Calendar.MONTH);
		day = c.get(Calendar.DAY_OF_MONTH);
		txt.setText(add(year)+ "-" +add((month+1))+ "-" +add(day));
		//txtFrom.setText(day+ "/" +(month+1)+ "/" + year);
		//Log.i("SUSHIL","Current Date has been set "+day+ "/" +(month+1)+ "/" + year);
		// dpresult.init(year, month, day, null);

	 }
    
    @Override
    protected void onResume() {
    	// TODO Auto-generated method stub
    	super.onResume();
    	
    	Object_AppConfig config = new Object_AppConfig(this);
		Globals.setLocale(config.getLocale(), this);
    }
    
    
    private String add(int c){
		   String str = "";
		if(c<=9){
			str = "0"+c;
		 }else{
		  str = c+"";  
		 }
		return str;
	   }
    
private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener(){
		
		public void onDateSet(DatePicker view,int selectedYear,int selectedMonth,int selectedDay) {
			year= selectedYear;
			month= selectedMonth;
			day =selectedDay;
			txt.setText(add(year)+ "-" +add((month+1))+ "-" +add(day));
			
			DBHandler_Txn txn = new DBHandler_Txn(getApplicationContext());
			ArrayList<Object_Voucher_Txn> allTxnData = txn.getTxnByDate(txt.getText().toString(), voucherMasterIdMapping.get(selectId).id);
			//Log.i("accountsappdb", "Size of arraylist for Adapter:"+allTxnData.size());
			Custom_adapterVoucher_view adapter = new Custom_adapterVoucher_view(context,R.layout.custom_row_voucher_view, allTxnData);
			lv_mainContainer.setAdapter(adapter);
			//dpresult.init(year, month, day, null);
		}
	};	
}
