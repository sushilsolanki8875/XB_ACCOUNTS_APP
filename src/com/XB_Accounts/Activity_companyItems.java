package com.XB_Accounts;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import android.view.View;

public class Activity_companyItems extends Activity {

	//boolean first;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_company_items);
		ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
       // first = false;
       
	}
	
public void onselected(View v){
		
		Intent i = new Intent(this,Activity_accounts.class);
		startActivity(i);
		
	}
public void ondailyLager(View v){
	
	Intent i = new Intent(this,Activity_dailyLager.class);
	startActivity(i);
	
}
public void onsalePurchases(View v){
	
	Intent i = new Intent(this,Activity_transaction.class);
	i.putExtra("into", "new");
	startActivity(i);
	
}
public void onGL(View v){
	
	Intent i = new Intent(this,Activity_genralLager.class);
	startActivity(i);
	
}
public void onsettings(View v){
	
	
	Intent i = new Intent(this,Activity_Setting.class);
	startActivity(i);
	this.finish();
}
public void onInterest(View v){
	
	Intent i = new Intent(this,Activity_interestCalculate.class);
	i.putExtra("Type", "Accounts");
	startActivity(i);
	
}
public void onVoucher(View v){
	
	Intent i = new Intent(this,Activity_voucher.class);
	startActivity(i);
	
}

public boolean onOptionsItemSelected(MenuItem item) {
	   
	   switch (item.getItemId()) {
	      case android.R.id.home:
	      NavUtils.navigateUpFromSameTask(this);
	      }
	return true;	
	}
  @Override
  public void onBackPressed() {
	// TODO Auto-generated method stub
	super.onBackPressed();
     };
    
    @Override
    protected void onResume() {
    	// TODO Auto-generated method stub
    	super.onResume();
    	Object_AppConfig config = new Object_AppConfig(this);
		Globals.setLocale(config.getLocale(), this);
    } 
     
}
