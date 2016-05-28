package com.XB_Accounts;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class Activity_transaction_Receipt extends Activity {
      
	public Activity_transaction activity;
	TextView txtVcName;
	Object_Voucher_Txn obj;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_transaction__recepit);
		activity = (Activity_transaction) Activity_transaction.mContext;
		Intent i = getIntent();
		txtVcName = (TextView)findViewById(R.id.voucherName);
		TextView txtDate = (TextView)findViewById(R.id.voucherDate);
		txtVcName.setText(i.getStringExtra("vcName"));
		txtDate.setText(i.getStringExtra("date"));
		creatVoucher();
		
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
		Object_AppConfig config = new Object_AppConfig(this);
		Globals.setLocale(config.getLocale(), this);
	}
	
	public void creatVoucher(){
		double Amountcre = 0;
		double Amountde = 0;
		ListView list =(ListView)findViewById(R.id.listvoucher);
		//AA_Object_Txn objTran = Activity_transaction.objTran;
	//	ArrayList<AA_Object_AccountTxn> AcList = objTran.accountTxn;
		//Log.i("SUSHIL", "Ac list size "+AcList.size());
		 obj = Activity_transaction.objVchTran;
		
		ArrayList<Map<String,String>>li = new ArrayList<Map<String,String>>();
		 for(Object_Account Ac :obj.accounts)
		 {
		  HashMap<String,String>map=new HashMap<String,String>();
		   Object_Account_Txn txn = Ac.accountTxn;
		      map.put("acname",Ac.name);
			  map.put("lf", "");
			 if(txn.txnType.id==1){
			  map.put("CreAmount", txn.amount+""); 
			  Amountcre = Amountcre+txn.amount;
			 }else{
			  map.put("CreAmount", "-"); 
			 }
			if(txn.txnType.id==2){
				  map.put("DeAmount", txn.amount+"");
				  Amountde = Amountde+txn.amount;
			}else{
			 map.put("DeAmount", "-"); 
			}
			 li.add(map);
		 }
		 SimpleAdapter ad=new SimpleAdapter(this,li,R.layout.custom_show_vouchereceipt,new String[]{"acname","lf","DeAmount","CreAmount"},new int[]{R.id.txtAc,R.id.txtLF,R.id.txtDebit,R.id.txtCredit});
		 list.setAdapter(ad);
		 
		 TextView txtdebit = (TextView)findViewById(R.id.edtDebitTotal);
	    TextView txtcredit = (TextView)findViewById(R.id.edtCreditTotal);
	     txtdebit.setText(Amountde+"");
	     txtcredit.setText(Amountcre+"");
	}
	
	public void save(View v){
		DBHandler_Txn dBtra = new DBHandler_Txn(this);
		boolean succes =  dBtra.createNewTxn(Activity_transaction.objVchTran);
		if(succes){
			activity.End();
			Toast.makeText(this,"Transaction saved succesfully", Toast.LENGTH_SHORT).show();
			Intent i = new Intent(this,Activity_transaction.class);
			i.putExtra("into", "new");
			startActivity(i);
			this.finish();
			
		   }else{
			   Toast.makeText(this,"Transaction not saved,retry !", Toast.LENGTH_SHORT).show(); 
		   }
	}
	
	public void edit(View v){
		//Log.i("JASPAL", "Vocher name is "+obj.voucher.id);
		if(obj.voucher.id == 1 || obj.voucher.id == 2){
	    Activity_transaction.objVchTran.accounts.remove(Activity_transaction.objVchTran.accounts.size()-1);
		//Log.i("JASPAL", "size of accounts "+Activity_transaction.objVchTran.accounts.size());
		}
	    this.finish();
	}
}
