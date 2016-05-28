package com.XB_Accounts;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.Locale;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.PopupMenu;
import android.widget.PopupMenu.OnMenuItemClickListener;
import android.widget.TextView;
import android.widget.Toast;

public class Globals {
  
    static public Point getAppButtonSize(Activity context) {

		int screenWidth = Globals.getScreenSize(context).x;

		Point size = new Point();

		size.x = 4 * screenWidth / 10;
		size.y = size.x / 3;

		return size;
	}

	
	
	@SuppressLint("NewApi")
	static public Point getScreenSize(Activity currentActivity) {
		Display display = currentActivity.getWindowManager()
				.getDefaultDisplay();
		Point size = new Point();

		if (android.os.Build.VERSION.SDK_INT >= 13) {
			display.getSize(size);
		} else {
			size.x = display.getWidth();
			size.y = display.getHeight();
		}

		return size;
	}

	static public int getAppFontSize(Activity context) {

		return (getScreenSize(context).x / 120 + 12);
	}

	static public int getAppFontSize_Small(Activity context) {

		return (getScreenSize(context).x / 120 + 7);
	}

	static public int getAppFontSize_Large(Activity context) {

		return (getScreenSize(context).x / 120 + 15);
	}

	static public Bitmap scaleToWidth(Bitmap bitmap, int scaledWidth) {
		if (bitmap != null) {

			int bitmapHeight = bitmap.getHeight();
			int bitmapWidth = bitmap.getWidth();

			// scale According to WIDTH
			int scaledHeight = (scaledWidth * bitmapHeight) / bitmapWidth;

			try {

				bitmap = Bitmap.createScaledBitmap(bitmap, scaledWidth,
						scaledHeight, true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return bitmap;
	}

	public static String capitalize(String s) {
		if (s == null || s.length() == 0) {
			return "";
		}
		char first = s.charAt(0);
		if (Character.isUpperCase(first)) {
			return s;
		} else {
			return Character.toUpperCase(first) + s.substring(1);
		}
	}
	
	public static void showAlert(String tiString,String msgString,Context context) {
		AlertDialog.Builder builder = new AlertDialog.Builder(
				context,AlertDialog.THEME_HOLO_LIGHT);
				builder.setTitle(tiString);
				builder.setMessage(msgString)
						.setCancelable(false)
						.setPositiveButton("OK",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int id) {
										
									}
								});
				AlertDialog alert = builder.create();
				alert.show();
			
	}
	
	
	
	public static void popup(Context m,View v){
		/** Instantiating PopupMenu class */
        PopupMenu popup = new PopupMenu(m, v);

        /** Adding menu items to the popumenu */
        popup.getMenuInflater().inflate(R.menu.popup, popup.getMenu());

        /** Defining menu item click listener for the popup menu */
        popup.setOnMenuItemClickListener(new OnMenuItemClickListener() {

            @Override
			public boolean onMenuItemClick(MenuItem arg0) {
				
				return true;
			}
        });

        /** Showing the popup menu */
        popup.show();
	}

	 public static void CopyStream(InputStream is, OutputStream os)
	    {
	        final int buffer_size=1024;
	        try
	        {
	            byte[] bytes=new byte[buffer_size];
	            for(;;)
	            {
	              int count=is.read(bytes, 0, buffer_size);
	              if(count==-1)
	                  break;
	              os.write(bytes, 0, count);
	            }
	        }
	        catch(Exception ex){}
	    }
	 
	 public static ProgressDialog showLoadingDialog(ProgressDialog mDialog, Activity act , Boolean cancelable,String title) {
		  
		  if(mDialog == null){
		    mDialog = new ProgressDialog(act,
		    ProgressDialog.THEME_HOLO_LIGHT);
		    mDialog.setTitle(title);
		    mDialog.setMessage("Please wait for a moment...");
		    mDialog.setCancelable(cancelable); 
		    mDialog.setProgressDrawable(null);
		    mDialog.show();
		  }else if(!mDialog.isShowing()) {
		   mDialog.show();
		  }
		   
		  return mDialog;
		 }

		 public static void hideLoadingDialog(ProgressDialog mDialog) {

		   if (mDialog != null) {
		    mDialog.dismiss(); 
		   }
		   

		 }
		 
		 public static void showShortToast(Context con , String msg){
			  Toast.makeText(con, msg, Toast.LENGTH_SHORT).show();
			}
	
		 public static void showAlertDialog(String title,String msg,Context context,String positiveButtonText,DialogInterface.OnClickListener listnerPositive,String negativeButtonText ,DialogInterface.OnClickListener listnerNegative,Boolean isCancelable){
				
				AlertDialog alertDialog = new AlertDialog.Builder(
						context,AlertDialog.THEME_HOLO_LIGHT).create();

				alertDialog.setTitle(title);
				alertDialog.setMessage(msg);
				alertDialog.setCancelable(isCancelable);
				alertDialog.setIcon(android.R.drawable.ic_dialog_alert);
				alertDialog.setButton(AlertDialog.BUTTON_POSITIVE,positiveButtonText,listnerPositive);
				
				if(negativeButtonText!= null && !negativeButtonText.equals("")){
					alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE,negativeButtonText,listnerNegative);
				}
				alertDialog.show();
				
			}
		
     
	/*public static void getcurtentDate(TextView txt){
		Calendar c = Calendar.getInstance();
		int year= c.get(Calendar.YEAR);
		int month=c.get(Calendar.MONTH);
		month = month+1;
		int day =c.get(Calendar.DAY_OF_MONTH);
		
		txt.setText(day+"-"+month+"-"+year);
	}*/
public static DatePickerDialog.OnDateSetListener datepic(final TextView txt){ 
/* DatePickerDialog.OnDateSetListener datePickerListener =*/
	return new DatePickerDialog.OnDateSetListener() {

        public void onDateSet(DatePicker view, int Year,
                              int monthOfYear, int dayOfMonth) {
        
        	txt.setText(add(Year)+ "-" +add((monthOfYear+1))+ "-" +add(dayOfMonth));
           // createFancyDateTimePicker(DATE_DIALOG_ID).show();
        }
    };	
   }
   public static void setCurrentDateOnview(TextView txt) {
	
	final Calendar c = Calendar.getInstance();
	int year = c.get(Calendar.YEAR);
	int month = c.get(Calendar.MONTH);
	int day = c.get(Calendar.DAY_OF_MONTH);
	txt.setText(add(year)+ "-" +add((month+1))+ "-" +add(day));
	
	// dpresult.init(year, month, day, null);

}
   
   public static String getcurrentTime(){
	   Calendar cal = Calendar.getInstance(); 
       int second = cal.get(Calendar.SECOND);
	   int minute = cal.get(Calendar.MINUTE);
	   int hour = cal.get(Calendar.HOUR_OF_DAY);
	  // HH:MM:SS
	   String time = add(hour)+":"+add(minute)+":"+add(second);
			   
	  return time;
   }
   
   private static String add(int c){
	   String str = "";
	if(c<=9){
		str = "0"+c;
	 }else{
	  str = c+"";  
	 }
	return str;
   }
   
   public static void setLocale(String lang,Context m) {

		Locale myLocale = new Locale(lang); 
	    Resources res = m.getResources(); 
	    DisplayMetrics dm = res.getDisplayMetrics(); 
	    Configuration conf = res.getConfiguration(); 
	    conf.locale = myLocale; 
	    res.updateConfiguration(conf, dm); 
	}
   
}
