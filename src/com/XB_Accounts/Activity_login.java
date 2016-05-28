package com.XB_Accounts;

import java.util.Locale;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Typeface;
import android.os.Bundle;
import android.provider.Settings.Secure;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class Activity_login extends Activity {
	Locale myLocale;
	DBHandler_Main dbh;
	EditText edtUsername;
	EditText edtPassword;
	String deviceImei = null;
	private String android_id;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		android_id = Secure.getString(this.getContentResolver(),
				Secure.ANDROID_ID);
		Log.i("SUSHIL","android device id "+android_id);
		resizeImages();
		CheckBox cb = (CheckBox) findViewById(R.id.checkBoxRemeber);
		Button btn = (Button) findViewById(R.id.button1);
		Typeface font = Typeface.createFromAsset(getAssets(), "Royalacid.ttf");
		cb.setTypeface(font);
		// btn.setTypeface(font);
		dbh = new DBHandler_Main(Activity_login.this);
		dbh.createDataBase();
		edtUsername = (EditText) findViewById(R.id.edtUsername);
		edtPassword = (EditText) findViewById(R.id.edtPassword);

		// *********checkbox is true*********
		CheckBox loginCheckBox = (CheckBox) findViewById(R.id.checkBoxRemeber);
		Object_AppConfig objConfig = new Object_AppConfig(this);

		if (objConfig.getRemberMeCheckedStatus()) {

			edtUsername.setText(objConfig.getLoginUserName());
			edtPassword.setText(objConfig.getLoginPassword());
			loginCheckBox.setChecked(true);
		}
         Log.i("SUSHIL", "lacale is "+objConfig.getLocale());
		setLocale(objConfig.getLocale());
		// TelephonyManager telephonyManager =
		// (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
		// deviceImei = telephonyManager.getDeviceId();
	}

	Boolean validateCredentials() {

		String username = edtUsername.getText().toString();
		String password = edtPassword.getText().toString();
		String msg = "";
		boolean returnType = true;
		if (username == null || password == null) {
			returnType = false;
			msg = "Please try again";

		} else if (username.trim().isEmpty()) {
			msg = "Please enter username";
			returnType = false;
		} else if (password.trim().isEmpty()) {
			msg = "Please enter password";
			returnType = false;
		}
		if (!returnType) {
			Globals.showShortToast(this, msg);
			// Globals.hideLoadingDialog(dialog);
		}
		return returnType;
	}

	public void onclick(View v) {
		/*
		 * Intent i = new Intent(this, Activity_companyItems.class);
		 * startActivity(i); this.finish();
		 */
		
	if(android_id.equals("6776ad9d2293f1a0")){
		if (validateCredentials()) {
			/*
			 * dialog = Globals.showLoadingDialog(dialog, Activity_login.this,
			 * false,"Login");
			 */

			DBHandler_Login dblogin = new DBHandler_Login(Activity_login.this);
			Boolean login = false;
			if (android_id != null) {
				login = dblogin.isValidUser(edtUsername.getText().toString(),
						edtPassword.getText().toString(), android_id);
			}
			if (login) {
				loginSuccess();
				// Globals.hideLoadingDialog(dialog);
			} else {
				// Globals.hideLoadingDialog(dialog);
				Globals.showAlert("Failled",
						"Username & Password did not match",
						Activity_login.this);
			}

		}
	}else{
		showAlert("Failled",
				"You are not eligible this app,Contact for us",
				Activity_login.this);
		
	}
	}

	private void loginSuccess() {

		CheckBox loginCheckBox = (CheckBox) findViewById(R.id.checkBoxRemeber);
		Object_AppConfig objConfig = new Object_AppConfig(this);

		if (loginCheckBox.isChecked()) {
			objConfig.setLoginPassword(edtPassword.getText().toString().trim());
			objConfig.setLoginUserName(edtUsername.getText().toString().trim());
			objConfig.setRemberMeCheckedStatus(true);
		} else {
			objConfig.setLoginPassword("");
			objConfig.setLoginUserName("");
			objConfig.setRemberMeCheckedStatus(false);
		}

		Intent i = new Intent(this, Activity_companyItems.class);
		startActivity(i);
		this.finish();
	}

	public void resizeImages() {
		ImageView imgViewLogo = (ImageView) findViewById(R.id.imgViewLogo);
		int screenWidth = Globals.getScreenSize(this).x;
		int logoWidth = screenWidth / 100 * 60;
		Options options = new BitmapFactory.Options();
		options.inScaled = false;
		Bitmap logo = BitmapFactory.decodeResource(getResources(),
				R.drawable.xb, options);
		logo = Globals.scaleToWidth(logo, logoWidth);
		imgViewLogo.setImageBitmap(logo);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

		/*
		 * Object_AppConfig obj = new Object_AppConfig(this);
		 * setLocale(obj.getLocale());
		 */
	}

	private void setLocale(String lang) {
		myLocale = new Locale(lang);
		Resources res = getResources();
		DisplayMetrics dm = res.getDisplayMetrics();
		Configuration conf = res.getConfiguration();
		conf.locale = myLocale;
		res.updateConfiguration(conf, dm);

		/*Intent intent = getIntent();
		finish();
		startActivity(intent);*/
	}
	
	public void showAlert(String tiString,String msgString,Context context) {
		AlertDialog.Builder builder = new AlertDialog.Builder(
				context,AlertDialog.THEME_HOLO_LIGHT);
				builder.setTitle(tiString);
				builder.setMessage(msgString)
						.setCancelable(false)
						.setPositiveButton("OK",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int id) {
										Activity_login.this.finish();
									}
								});
				AlertDialog alert = builder.create();
				alert.show();
			
	}
}
