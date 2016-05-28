package com.XB_Accounts;



import android.app.ActionBar;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.FragmentTransaction;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.widget.DatePicker;

public class Activity_dailyLager extends FragmentActivity {

	ViewPager Tab;
	Custom_TabpagerAdapter TabAdapter;
	static ActionBar actionBar;
	static Typeface fontFamily;
	private int year = 0;
	private int month = 0;
	private int day = 0;
	static final int DATE_DIALOG_ID = 333;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_daily_lager);
		
	    actionBar = getActionBar();
		fontFamily = Typeface.createFromAsset(getAssets(),
				"fonts/fontawesome-webfont.ttf");
		TabAdapter = new Custom_TabpagerAdapter(getSupportFragmentManager(),
				Activity_dailyLager.this,2);

		Tab = (ViewPager) findViewById(R.id.pager);
		Tab.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
			@Override
			public void onPageSelected(int position) {
				move(position);
			}
		});
		Tab.setAdapter(TabAdapter);

		actionBar = getActionBar();
		// Enable Tabs on Action Bar
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		ActionBar.TabListener tabListener = new ActionBar.TabListener() {

			@Override
			public void onTabReselected(android.app.ActionBar.Tab tab,
					FragmentTransaction ft) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {

				Tab.setCurrentItem(tab.getPosition());
			}

			@Override
			public void onTabUnselected(android.app.ActionBar.Tab tab,
					FragmentTransaction ft) {
				// TODO Auto-generated method stub

			}
		};
		// Add New Tab
		actionBar.addTab(actionBar.newTab().setText("Credit")
				.setTabListener(tabListener));
		actionBar.addTab(actionBar.newTab().setText("Debit")
				.setTabListener(tabListener));

		// actionBar.addTab(actionBar.newTab().setText("Windows").setTabListener(tabListener));
		// ListView list = (ListView)findViewById(R.id.listshowaccounts);
		
		
		//Dbtxn.getTxnByDate(Date, voucherTypeId)
		
		
	}

	public static void move(int position) {
		actionBar.setSelectedNavigationItem(position);

	}

	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case DATE_DIALOG_ID:
			return new DatePickerDialog(this, datePickerListener, year, month,
					day);

		}
		return null;
	}

	private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {

		public void onDateSet(DatePicker view, int selectedYear,
				int selectedMonth, int selectedDay) {
			year = selectedYear;
			month = selectedMonth;
			day = selectedDay;
			//txt.setText(day + "/" + (month + 1) + "/" + year);
			// dpresult.init(year, month, day, null);
		}
	};

	
	
	public void onDateSet(DatePicker arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub

	}
	
/*private void setdata(String date){
	AA_DBHandler_Txn Dbtxn = new AA_DBHandler_Txn(this);
	HashMap<Integer, ArrayList<AA_Object_Txn>> map = new HashMap<Integer, ArrayList<AA_Object_Txn>>();
	ArrayList<AA_Object_Txn> Listtxn = Dbtxn.getTxnByDate(date,1);
	for(int i=0;i<Listtxn.size();i++){
		AA_Object_Txn obj = Listtxn.get(i);
		if (!map.containsKey(obj.id)) {
		     ArrayList<AA_Object_Txn> ListAc = new ArrayList<AA_Object_Txn>();
		     ListAc.add(obj);
		     map.put(obj.id,ListAc);
		    }else {
		   ArrayList<AA_Object_Txn> ListAc = map.get(obj.id);
		   ListAc.add(obj);
		   map.put(obj.id, ListAc);
		   }
	  }
	
	
	}*/
	
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
		Object_AppConfig config = new Object_AppConfig(this);
		Globals.setLocale(config.getLocale(), this);
	}
}
