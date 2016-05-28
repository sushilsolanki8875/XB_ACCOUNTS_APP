package com.XB_Accounts;

import java.util.Locale;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class Activity_SettingLanguage extends Activity {
  int a=0;
  Locale myLocale;
  
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting_language);
		
		final CheckBox hindi = (CheckBox)findViewById(R.id.checkBoxhindi);
		final CheckBox english = (CheckBox)findViewById(R.id.checkBoxenglishmain);
		
		
		
		hindi.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				//pd = Globals.showLoadingDialog(pd,Activity_SettingLanguage.this, false, "");
				Log.i("SUSHIL", "call hindi");
				Object_AppConfig objcon = new Object_AppConfig(Activity_SettingLanguage.this);
				if(isChecked){
					english.setChecked(false);
					objcon.setCheckBoxId(hindi.getId());
					a = 1;
				}else{
					english.setChecked(true);
					setLocale("en");
					//objcon.setLocale("en");
				 }
				
			}
		});
		
		english.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				//pd = Globals.showLoadingDialog(pd,Activity_SettingLanguage.this, false, "");
				Log.i("SUSHIL", "call english");
				Object_AppConfig objcon = new Object_AppConfig(Activity_SettingLanguage.this);
				if(isChecked){
					hindi.setChecked(false);
					//Object_AppConfig objcon = new Object_AppConfig(Activity_SettingLanguage.this);
					objcon.setCheckBoxId(english.getId());
					a =1;
				}else{
					hindi.setChecked(true);
					setLocale("hi");
					
				}
				
			}
		});
		
		Object_AppConfig objcon = new Object_AppConfig(this);
		CheckBox all = (CheckBox)findViewById(objcon.getCheckBoxId());
		all.setChecked(true);
		
		
	}
	
	private void setLocale(String lang) {

		/*Locale myLocale = new Locale(lang);
		Locale.setDefault(myLocale);
		Configuration config= new Configuration();
		config.locale= myLocale;
		getBaseContext().getResources().updateConfiguration(config,getBaseContext().getResources().getDisplayMetrics());
         Globals.hideLoadingDialog(pd);
		Intent intent = getIntent();
         finish();
         startActivity(intent);*/
		
		/*Locale locale = new Locale(lang);
		Locale.setDefault(locale);
		Configuration config = new Configuration();
		config.locale = locale;
		getBaseContext().getResources().updateConfiguration(config,
		                getBaseContext().getResources().getDisplayMetrics());
		                this.setContentView(id);*/
		
		Object_AppConfig objcon = new Object_AppConfig(Activity_SettingLanguage.this);
		myLocale = new Locale(lang); 
	    Resources res = getResources(); 
	    DisplayMetrics dm = res.getDisplayMetrics(); 
	    Configuration conf = res.getConfiguration(); 
	    conf.locale = myLocale; 
	    res.updateConfiguration(conf, dm); 
	    objcon.setLocale(lang);
	    
	    Intent intent = getIntent();
        finish();
        startActivity(intent);
	}
	
	
}
