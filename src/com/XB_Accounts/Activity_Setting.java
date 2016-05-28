package com.XB_Accounts;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.channels.FileChannel;
import java.util.Locale;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Environment;
import android.text.method.HideReturnsTransformationMethod;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class Activity_Setting extends Activity {
	
	//ProgressDialog pd;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting);
		ListView listsettings = (ListView)findViewById(R.id.listViewSettings);
        String[] items = { "Choose Language", "Change Keyboard","Add new Accounts & Items ","Backup Data","Print Sheets","About Us"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                    R.layout.custom_settings_list, items);
        listsettings.setAdapter(adapter);
        
        listsettings.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if(position==0){
					onLanguage();
				}else if(position ==1){
					showInputMethodPicker();
				}else if(position==2){
					Addnew();
				}else if(position==3){
				
					backUpData();
			  
				}else if(position==4){
				
					onPrint();
			  
				}
				
			}
		});
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
		Object_AppConfig config = new Object_AppConfig(this);
		Globals.setLocale(config.getLocale(), this);
	}
	
	private void Addnew(){
		Intent i = new Intent(this,Activity_SettingAddNew.class);
		i.putExtra("into", "new");
		startActivity(i);
	}
	private void onLanguage(){
		Intent i = new Intent(this,Activity_SettingLanguage.class);
		startActivity(i);
	}
	
	private void onPrint(){
		Intent i = new Intent(this,Activity_printDetails.class);
		startActivity(i);
	}
	
	private void showInputMethodPicker() {

		InputMethodManager imeManager = (InputMethodManager) getApplicationContext()
				.getSystemService(INPUT_METHOD_SERVICE);

		if (imeManager != null) {
			imeManager.showInputMethodPicker();
		} else {
			Toast.makeText(this, "No Input Method Found", Toast.LENGTH_LONG)
					.show();
		}
	}
	
	private void backUpData(){
	try{
		//Globals.showLoadingDialog(pd, this, true, "Backup Data");
		DBHandler_Main dbh = new DBHandler_Main(this);
		 final String inFileName = dbh.get_db_path();
		    File dbFile = new File(inFileName);
		    FileInputStream fis = new FileInputStream(dbFile);
	
		    String outFileName = Environment.getExternalStorageDirectory()+"/dbexport.sqlite";
		    Log.d("dbexportdemo","output file is : "+outFileName);
		    // Open the empty db as the output stream
		    OutputStream output = new FileOutputStream(outFileName);
	
		    // Transfer bytes from the inputfile to the outputfile
		    byte[] buffer = new byte[1024];
		    int length;
		    while ((length = fis.read(buffer))>0){
		        output.write(buffer, 0, length);
		    }
	
		    // Close the streams
		    output.flush();
		    output.close();
		    fis.close();
		    Log.d("dbexportdemo","export Completed !@!!!");
		  //  Globals.hideLoadingDialog(pd);
		    Toast.makeText(this, "Data backup succesfully", Toast.LENGTH_SHORT).show();
		}
	
		catch(Exception e)
		{
			//Globals.hideLoadingDialog(pd);
			e.printStackTrace();
			Toast.makeText(this, "Unable to backup !Retry", Toast.LENGTH_SHORT).show();
		}

	}

   /*public void exportDB(View v){
	System.out.print("exportDB called !!!");
    File sd = Environment.getExternalStorageDirectory();
        File data = Environment.getDataDirectory();
       FileChannel source=null;
       FileChannel destination=null;
       
       DBHandler_Main dbh = new DBHandler_Main(getApplicationContext());
       
       String currentDBPath = dbh.get_db_path();
       String backupDBPath = "dbbackup.sqlite";
       File currentDB = new File(data, currentDBPath);
       File backupDB = new File(sd, backupDBPath);
       try {
            source = new FileInputStream(currentDB).getChannel();
            destination = new FileOutputStream(backupDB).getChannel();
            destination.transferFrom(source, 0, source.size());
            source.close();
            destination.close();
            Toast.makeText(this, "DB Exported!", Toast.LENGTH_LONG).show();
        } catch(IOException e) {
            e.printStackTrace();
        }
}*/

	
	
	
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		
		Intent i = new Intent(Activity_Setting.this,Activity_companyItems.class);
		startActivity(i);
	}
}
