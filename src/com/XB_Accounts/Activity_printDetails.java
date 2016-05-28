package com.XB_Accounts;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class Activity_printDetails extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_print_details);
		ListView list = (ListView)findViewById(R.id.listViewSheet);
		String[] items = { "Balance Sheet All Accounts","Balance Sheet Loans & FD","Balance sheet Capital Accounts","Balance Sheet Bank Accounts","Balance Sheet Sundary Creditors","Purchase & Sale Sheet"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                    R.layout.custom_settings_list, items);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if(position==0){
					onBalanceSheetAll();
				}else if(position==1){
					onBalanceSheetLoans();
				}else if(position==2){
					onBalanceSheetCapital();
				}else if(position==3){
					onBalanceSheetBank();
				}else if(position==4){
					onBalanceSheetSun();
				}else if(position==5){
					onPurSaleSheet();
				}
				
			}
		});
	}
	
	private void onBalanceSheetAll(){
		Intent i = new Intent(this,Activity_printPreview.class);
		i.putExtra("Sheet","balance");
		i.putExtra("type","all");
		startActivity(i);
	}
	private void onBalanceSheetLoans(){
	
		Intent i = new Intent(this,Activity_printPreview.class);
		i.putExtra("Sheet","balance");
		i.putExtra("type","loan");
		startActivity(i);
	}
	private void onBalanceSheetCapital(){
		Intent i = new Intent(this,Activity_printPreview.class);
		i.putExtra("Sheet","balance");
		i.putExtra("type","capital");
		startActivity(i);
	}
	private void onBalanceSheetBank(){
		Intent i = new Intent(this,Activity_printPreview.class);
		i.putExtra("Sheet","balance");
		i.putExtra("type","bank");
		startActivity(i);
	}
	private void onBalanceSheetSun(){
		Intent i = new Intent(this,Activity_printPreview.class);
		i.putExtra("Sheet","balance");
		i.putExtra("type","sun");
		startActivity(i);
	}
	private void onPurSaleSheet(){
		Intent i = new Intent(this,Activity_printPreview.class);
		i.putExtra("Sheet","purchase");
		startActivity(i);
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
		Object_AppConfig config = new Object_AppConfig(this);
		Globals.setLocale(config.getLocale(), this);
		
	}
}
