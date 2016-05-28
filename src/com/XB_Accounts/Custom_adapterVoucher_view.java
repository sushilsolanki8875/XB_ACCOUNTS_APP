package com.XB_Accounts;

import java.util.ArrayList;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Custom_adapterVoucher_view extends ArrayAdapter<Object_Voucher_Txn>{

	Activity context;
	int resourceId;
	ArrayList<Object_Voucher_Txn> data;
	//static Object_Voucher_Txn objVxn;
	public Custom_adapterVoucher_view(Activity context, int resourceId,ArrayList<Object_Voucher_Txn> data)
	{
		super(context, resourceId, data);
		
		this.context = context;
		this.resourceId = resourceId;
		this.data = data;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		//Log.i("accountsappdb", "inside GETView !!!");
		View row = convertView;
		final Object_Voucher_Txn vTxn = data.get(position);
		
		LayoutInflater inflater = context.getLayoutInflater();
		row = inflater.inflate(resourceId, parent,false);
		
		LinearLayout llMainContainer = (LinearLayout)row.findViewById(R.id.ll_voucher_view_single_row);
		
		int index = 0;
		for (Object_Account account : vTxn.accounts) {
			

			LinearLayout llItemContainer = new LinearLayout(context);
			LinearLayout.LayoutParams llItemContainerLP = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
			llItemContainer.setPadding(0, 6, 0, 0);
			llItemContainer.setOrientation(LinearLayout.HORIZONTAL);
			llItemContainer.setLayoutParams(llItemContainerLP);
			
			TextView srNo = new TextView(context);
			LinearLayout.LayoutParams srNoLP = new LinearLayout.LayoutParams(0,LayoutParams.WRAP_CONTENT,10f);
			srNo.setLayoutParams(srNoLP);
			srNo.setGravity(Gravity.CENTER_HORIZONTAL);
			if(index++ == 0)
				srNo.setText(String.valueOf(position+1));
			llItemContainer.addView(srNo);
			
			LinearLayout llParticularsContainer = new LinearLayout(context);
			LinearLayout.LayoutParams llParticularsContainerLP = new LinearLayout.LayoutParams(0,LayoutParams.WRAP_CONTENT,40f);
			llParticularsContainer.setLayoutParams(llParticularsContainerLP);
			llParticularsContainer.setOrientation(LinearLayout.VERTICAL);
			
				TextView accountName = new TextView(context);
				LinearLayout.LayoutParams accountNameLP = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
				accountName.setLayoutParams(accountNameLP);
				accountName.setText(account.name);
				llParticularsContainer.addView(accountName);
				
				//Log.i("accountsappdb","ArrayAdapter => size of itemCats Arraylist : "+account.itemCategories.size());
				for (Object_Item_Category itemCat : account.itemCategories) {
					for (Object_Item item : itemCat.items) {
						
						LinearLayout llItemsNPriceContainer = new LinearLayout(context);
						LinearLayout.LayoutParams llItemsNPriceContainerLP = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
						llItemsNPriceContainer.setLayoutParams(llItemsNPriceContainerLP);
						llItemsNPriceContainer.setOrientation(LinearLayout.HORIZONTAL);
							
							TextView itemName = new TextView(context);
							LinearLayout.LayoutParams itemNameLP = new LinearLayout.LayoutParams(0,LayoutParams.WRAP_CONTENT,60f);
							itemName.setLayoutParams(itemNameLP);
							itemName.setText(item.name);
							llItemsNPriceContainer.addView(itemName);
							
							TextView qtyNPrice = new TextView(context);
							LinearLayout.LayoutParams qtyNPriceLP = new LinearLayout.LayoutParams(0,LayoutParams.WRAP_CONTENT,40f);
							qtyNPrice.setLayoutParams(qtyNPriceLP);
							qtyNPrice.setText(String.valueOf(item.itemTxn.price)+" x "+String.valueOf(item.itemTxn.qty)+" "+item.unit.name);
							llItemsNPriceContainer.addView(qtyNPrice);
						llParticularsContainer.addView(llItemsNPriceContainer);
					}
					
				}

				if(account.accountTxn.narration != null)
				{
					TextView narration = new TextView(context);
					LinearLayout.LayoutParams narrationLP = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
					narration.setLayoutParams(narrationLP);
					narration.setText("\t - "+account.accountTxn.narration);
					llParticularsContainer.addView(narration);
				}
		
			
			llItemContainer.addView(llParticularsContainer);
			
			TextView Credit = new TextView(context);
			LinearLayout.LayoutParams creditLP = new LinearLayout.LayoutParams(0,LayoutParams.WRAP_CONTENT,15f);
			Credit.setLayoutParams(creditLP);
			Credit.setGravity(Gravity.CENTER_HORIZONTAL);
			//Credit.setBackgroundColor(color.Aquamarine);
			llItemContainer.addView(Credit);
			
			TextView Debit = new TextView(context);
			LinearLayout.LayoutParams debitLP = new LinearLayout.LayoutParams(0,LayoutParams.WRAP_CONTENT,15f);
			Debit.setLayoutParams(debitLP);
			Debit.setGravity(Gravity.CENTER_HORIZONTAL);
			//Debit.setBackgroundColor(color.Wheat);
			llItemContainer.addView(Debit);	
			
			
			if(account.accountTxn.txnType.name.equals("Credit"))
			{
				Credit.setText(String.valueOf(account.accountTxn.amount));
			}
			else if(account.accountTxn.txnType.name.equals("Debit"))
			{
				Debit.setText(String.valueOf(account.accountTxn.amount));
			}

			llMainContainer.addView(llItemContainer);
		}
		
		row.setOnLongClickListener(new OnLongClickListener() {
			
			@Override
			public boolean onLongClick(View v) {
				
				/*objVxn  = vTxn;
				Intent i = new Intent(context,Activity_transaction.class);
				i.putExtra("into", "edit");
				((Activity) context)
				.startActivity(i);*/
				Globals.showAlertDialog("Message", "Are you sure to Delete", context, "Ok",  new DialogInterface.OnClickListener() {
	                public void onClick(DialogInterface dialog,int id) {
	                	DBHandler_Txn txn = new DBHandler_Txn(context);
	    				txn.deleteTxn(vTxn.id);
	    				Intent intent = ((Activity) context).getIntent();
	    				((Activity) context).finish();
	    				((Activity) context).startActivity(intent);
	                }
	              }, "Cancel", new DialogInterface.OnClickListener() {
	                  public void onClick(DialogInterface dialog,int id) {
	                  }
	                }, false);
				
				
				return true;
			}
		});
		

		return row;
	}

/*	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Log.i("accountsappdb", "inside GETView !!!");
		View row = convertView;
		Object_Voucher_Txn item = data.get(position);
		//ArrayList<AA_Object_ItemTxn_2> itemTxn = item.itemTxn;
		//ArrayList<AA_Object_AccountTxn_2> accountTxn = item.accountTxn;
		int itemCrOrDr = 0;
		
		
		//DBHandler_voucher_master vchMstr = new DBHandler_voucher_master(getContext());
		//AA_Object_Voucher_2 voucherMaster = vchMstr.getVoucherById(item.voucherId);
		
		AA_Object_Accounts_2 defAccount = null;
		if(voucherMaster.screentype.equals("Goods"))
		{
			Log.i("accountsappdb", "def_CR_accountId:"+voucherMaster.def_cr_accnt_id+"    def_DR_accountId:"+voucherMaster.def_dr_accnt_id);
			if(voucherMaster.def_cr_accnt_id != 0)
			{
				DBHandler_Account dbhAccnts = new DBHandler_Account(getContext());
				defAccount = dbhAccnts.getAccountById(voucherMaster.def_cr_accnt_id);
				itemCrOrDr = 1;
			}
			else if(voucherMaster.def_dr_accnt_id != 0)
			{
				DBHandler_Account dbhAccnts = new DBHandler_Account(getContext());
				defAccount = dbhAccnts.getAccountById(voucherMaster.def_dr_accnt_id);
				itemCrOrDr = 2;
			}
		}
		
		LayoutInflater inflater = context.getLayoutInflater();
		row = inflater.inflate(resourceId, parent,false);
		

		LinearLayout llMainContainer = (LinearLayout)row.findViewById(R.id.ll_voucher_view_single_row);
		

		int indexCount = 0;
		for(AA_Object_AccountTxn_2 obj : accountTxn)
		{
			if(defAccount.id != obj.accountId)
			{
				DBHandler_Account dbhAccount = new DBHandler_Account(getContext());
				AA_Object_Accounts_2 accountDetail = dbhAccount.getAccountById(obj.accountId);
				
				LinearLayout llItemContainer = new LinearLayout(context);
				LinearLayout.LayoutParams llItemContainerLP = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
				llItemContainer.setPadding(0, 6, 0, 0);
				llItemContainer.setOrientation(LinearLayout.HORIZONTAL);
				llItemContainer.setLayoutParams(llItemContainerLP);
				
				TextView srNo = new TextView(context);
				LinearLayout.LayoutParams srNoLP = new LinearLayout.LayoutParams(0,LayoutParams.WRAP_CONTENT,10f);
				srNo.setLayoutParams(srNoLP);
				srNo.setGravity(Gravity.CENTER_HORIZONTAL);
				if(indexCount == 0)
				{
					srNo.setText(String.valueOf(position+1));
					indexCount++;
				}
				llItemContainer.addView(srNo);
				
				LinearLayout llParticularsContainer = new LinearLayout(context);
				LinearLayout.LayoutParams llParticularsContainerLP = new LinearLayout.LayoutParams(0,LayoutParams.WRAP_CONTENT,40f);
				llParticularsContainer.setLayoutParams(llParticularsContainerLP);
				llParticularsContainer.setOrientation(LinearLayout.VERTICAL);
				
					TextView accountName = new TextView(context);
					LinearLayout.LayoutParams accountNameLP = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
					accountName.setLayoutParams(accountNameLP);
					accountName.setText(accountDetail.name);
					llParticularsContainer.addView(accountName);
					
	
					if(obj.narration != null)
					{
						TextView narration = new TextView(context);
						LinearLayout.LayoutParams narrationLP = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
						narration.setLayoutParams(narrationLP);
						narration.setText("\t - "+obj.narration);
						llParticularsContainer.addView(narration);
					}
			
				
				llItemContainer.addView(llParticularsContainer);
				
				TextView Credit = new TextView(context);
				LinearLayout.LayoutParams creditLP = new LinearLayout.LayoutParams(0,LayoutParams.WRAP_CONTENT,15f);
				Credit.setLayoutParams(creditLP);
				Credit.setGravity(Gravity.CENTER_HORIZONTAL);
				//Credit.setBackgroundColor(color.Aquamarine);
				llItemContainer.addView(Credit);
				
				TextView Debit = new TextView(context);
				LinearLayout.LayoutParams debitLP = new LinearLayout.LayoutParams(0,LayoutParams.WRAP_CONTENT,15f);
				Debit.setLayoutParams(debitLP);
				Debit.setGravity(Gravity.CENTER_HORIZONTAL);
				//Debit.setBackgroundColor(color.Wheat);
				llItemContainer.addView(Debit);	
				
	
				if(obj.txnTypeId == 1)
					Credit.setText(String.valueOf(obj.amount));
				else if(obj.txnTypeId == 2)
					Debit.setText(String.valueOf(obj.amount));
				
				
				
				llMainContainer.addView(llItemContainer);
			}
		}
		
		for(AA_Object_ItemTxn_2 obj : itemTxn)
		{
			DBHandler_Items dbhItems = new DBHandler_Items(getContext());
			AA_Object_Items_2 goodItem = dbhItems.getItem(obj.itemId);
			
			DBHandler_Unit dbhUnits = new DBHandler_Unit(getContext());
			AA_Object_Unit_2 goodUnit = dbhUnits.getUnitById(goodItem.unitid);
		
			LinearLayout llItemContainer = new LinearLayout(context);
			LinearLayout.LayoutParams llItemContainerLP = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
			llItemContainer.setPadding(0, 6, 0, 0);
			llItemContainer.setOrientation(LinearLayout.HORIZONTAL);
			llItemContainer.setLayoutParams(llItemContainerLP);
			
			TextView srNo = new TextView(context);
			LinearLayout.LayoutParams srNoLP = new LinearLayout.LayoutParams(0,LayoutParams.WRAP_CONTENT,10f);
			srNo.setLayoutParams(srNoLP);
			srNo.setGravity(Gravity.CENTER_HORIZONTAL);
			//srNo.setText(String.valueOf(position));
			llItemContainer.addView(srNo);
			
			LinearLayout llParticularsContainer = new LinearLayout(context);
			LinearLayout.LayoutParams llParticularsContainerLP = new LinearLayout.LayoutParams(0,LayoutParams.WRAP_CONTENT,40f);
			llParticularsContainer.setLayoutParams(llParticularsContainerLP);
			llParticularsContainer.setOrientation(LinearLayout.VERTICAL);
			
				TextView accountName = new TextView(context);
				LinearLayout.LayoutParams accountNameLP = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
				accountName.setLayoutParams(accountNameLP);
				accountName.setText(defAccount.name);
				llParticularsContainer.addView(accountName);
				
				LinearLayout llItemsNPriceContainer = new LinearLayout(context);
				LinearLayout.LayoutParams llItemsNPriceContainerLP = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
				llItemsNPriceContainer.setLayoutParams(llItemsNPriceContainerLP);
				llItemsNPriceContainer.setOrientation(LinearLayout.HORIZONTAL);
					
					TextView itemName = new TextView(context);
					LinearLayout.LayoutParams itemNameLP = new LinearLayout.LayoutParams(0,LayoutParams.WRAP_CONTENT,60f);
					itemName.setLayoutParams(itemNameLP);
					itemName.setText(goodItem.name);
					llItemsNPriceContainer.addView(itemName);
					
					TextView qtyNPrice = new TextView(context);
					LinearLayout.LayoutParams qtyNPriceLP = new LinearLayout.LayoutParams(0,LayoutParams.WRAP_CONTENT,40f);
					qtyNPrice.setLayoutParams(qtyNPriceLP);
					qtyNPrice.setText(String.valueOf(obj.price)+" x "+String.valueOf(obj.qty)+" "+goodUnit.name);
					llItemsNPriceContainer.addView(qtyNPrice);
				llParticularsContainer.addView(llItemsNPriceContainer);

				if(obj.narration != null)
				{
					TextView narration = new TextView(context);
					LinearLayout.LayoutParams narrationLP = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
					narration.setLayoutParams(narrationLP);
					narration.setText("\t - "+obj.narration);
					llParticularsContainer.addView(narration);
				}
		
			
			llItemContainer.addView(llParticularsContainer);
			
			TextView Credit = new TextView(context);
			LinearLayout.LayoutParams creditLP = new LinearLayout.LayoutParams(0,LayoutParams.WRAP_CONTENT,15f);
			Credit.setLayoutParams(creditLP);
			Credit.setGravity(Gravity.CENTER_HORIZONTAL);
			//Credit.setBackgroundColor(color.Aquamarine);
			llItemContainer.addView(Credit);
			
			TextView Debit = new TextView(context);
			LinearLayout.LayoutParams debitLP = new LinearLayout.LayoutParams(0,LayoutParams.WRAP_CONTENT,15f);
			Debit.setLayoutParams(debitLP);
			Debit.setGravity(Gravity.CENTER_HORIZONTAL);
			//Debit.setBackgroundColor(color.Wheat);
			llItemContainer.addView(Debit);	
			

			if(itemCrOrDr == 1)
				Credit.setText(String.valueOf(obj.amount));
			else if(itemCrOrDr == 2)
				Debit.setText(String.valueOf(obj.amount));
			
			
			
			llMainContainer.addView(llItemContainer);
		}
		
		return row;
	}*/
	
}
