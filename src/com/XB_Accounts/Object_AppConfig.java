package com.XB_Accounts;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;

public class Object_AppConfig {
   private Context context;
	private final String KEY_APP_CONFIG = "appConfig";
	private SharedPreferences prefs;
	private SharedPreferences.Editor editor = null;
	
	public Object_AppConfig(Context context){
		
		this.context = context;
		prefs = this.context.getSharedPreferences(KEY_APP_CONFIG, 0);
		editor = prefs.edit();
	}
	
	public String getLoginUserName() {
		String userName = "" ;
		if(prefs != null)
			userName = prefs.getString("appConfig_LoginUserName","");	
		
		return userName;
	}
	public void setLoginUserName(String userName) {
		
		if (editor != null) {
			editor.putString("appConfig_LoginUserName", userName);
			editor.commit();
		}

	}
	
	public String getLoginPassword() {
		String password = "" ;
		
		if(prefs != null)
			password = prefs.getString("appConfig_LoginPassword","");	
		
		return password;
	}
	public void setLoginPassword(String password) {
		
		if (editor != null) {
			editor.putString("appConfig_LoginPassword", password);
			editor.commit();
		}

	}
	public boolean getRemberMeCheckedStatus() {
		boolean status =false ;
		
		if(prefs != null)
			status = prefs.getBoolean("appConfig_RememberMeStatus",false);	
		
		return status;
	}
	public void setRemberMeCheckedStatus(boolean status) {
		
		if (editor != null) {
			editor.putBoolean("appConfig_RememberMeStatus", status);
			editor.commit();
		}

	}
	public int getCheckBoxId() {
		int CheckBoxId =  R.id.checkBoxenglishmain;
		
		if(prefs != null)
			CheckBoxId = prefs.getInt("appConfig_id",R.id.checkBoxenglishmain);	
		
		return CheckBoxId;
	}
	public void setCheckBoxId(int id) {
		
		if (editor != null) {
			editor.putInt("appConfig_id", id);
			editor.commit();
		}

	}
	
	public boolean getfirstTime() {
		boolean status = true ;
		
		if(prefs != null)
			status = prefs.getBoolean("appConfig_firstTimeStatus",true);	
		
		return status;
	}
	public void setfirstTime(boolean status) {
		
		if (editor != null) {
			editor.putBoolean("appConfig_firstTimeStatus", status);
			editor.commit();
		}

	}
	
	public int getAccountId() {
		int AccountId =  0;
		
		if(prefs != null)
			AccountId = prefs.getInt("appConfig_AccountId",0);	
		
		return AccountId;
	}
	public void setAccountId(int id) {
		
		if (editor != null) {
			editor.putInt("appConfig_AccountId", id);
			editor.commit();
		}

	}
	
public void setLocale(String localeName) {
		
		if (editor != null) {
			editor.putString("appConfig_localeName", localeName);
			editor.commit();
		}

	}
	
	public String getLocale() {
		String Locale = "en" ;
		
		if(prefs != null)
			Locale = prefs.getString("appConfig_localeName","");	
		
		return Locale;
	}
	
}
