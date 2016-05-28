package com.XB_Accounts;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Custom_adapterDailylg_Debit extends BaseAdapter implements Filterable {

	private Context mContext;
	private ArrayList<Object_Account_Group> listAccountsGroupTxn;
	
	/*private int mPosition;
	private View view;*/
	AccountFilter mFilter;
   public static Object_Account object;
	// Gets the context so it can be used later
	public Custom_adapterDailylg_Debit(Context c,
			ArrayList<Object_Account_Group> listAccountsGroupTxn) {
		mContext = c;
		this.listAccountsGroupTxn = listAccountsGroupTxn;
		
	}

	// Total number of things contained within the adapter
	public int getCount() {
		return listAccountsGroupTxn.size();
	}

	// Require for structure, not really used in my code.
	public Object getItem(int position) {
		return null;
	}

	// Require for structure, not really used in my code. Can
	// be used to get the id of an item in the adapter for
	// manual control.
	public long getItemId(int position) {
		return position;
	}

	public View getView(final int position, View convertView, ViewGroup parent) {
        View row = convertView;
		//final Object_Account_Group obj = listAccountsGroupTxn.get(position);
		if (convertView == null) {
	        // This a new view we inflate the new layout
	        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	        row = inflater.inflate(R.layout.custom_row_daily_ladger, parent, false);
	    }
		
		LinearLayout llMainContainer = (LinearLayout)row.findViewById(R.id.mainContainer);
	        // Now we can fill the layout with the right values
		//for (Object_Account_Group AcGroup : listAccountsGroupTxn) {
		Object_Account_Group AcGroup = listAccountsGroupTxn.get(position);
			       LinearLayout linearMain = new  LinearLayout(mContext);
			        LinearLayout.LayoutParams linearMainLP = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT,0.7f);
			        linearMain.setLayoutParams(linearMainLP);
			        linearMain.setOrientation(LinearLayout.HORIZONTAL);
			
			
			       TextView txtDateGroup = new TextView(mContext);
 	               LinearLayout.LayoutParams txtDateGroupP = new LinearLayout.LayoutParams(0,LayoutParams.WRAP_CONTENT,20f);
 	               txtDateGroup.setLayoutParams(txtDateGroupP);
 	               txtDateGroup.setText(AcGroup.daytime);
 	               txtDateGroup.setGravity(Gravity.CENTER);
 	               linearMain.addView(txtDateGroup);
			
	    	       TextView txtMainGroup = new TextView(mContext);
	    	       LinearLayout.LayoutParams AcGroupLP = new LinearLayout.LayoutParams(0,LayoutParams.WRAP_CONTENT,100f);
	    	       txtMainGroup.setLayoutParams(AcGroupLP);
	    	       txtMainGroup.setText(AcGroup.name);
	    	       txtMainGroup.setTextColor(mContext.getResources().getColor(R.color.Red));
	    	       linearMain.addView(txtMainGroup);
	    	       llMainContainer.addView(linearMain);
	    	    if(AcGroup.accountGroups!=null){
	    	      for (Object_Account_Group subGroup : AcGroup.accountGroups) {
	    	    	  LinearLayout linearsubMain = new  LinearLayout(mContext);
				        LinearLayout.LayoutParams linearsubMainLP = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT,0.7f);
				        linearsubMain.setLayoutParams(linearsubMainLP);
				        linearsubMain.setOrientation(LinearLayout.HORIZONTAL);
				        
				        LinearLayout li = new LinearLayout(mContext);
				        LinearLayout.LayoutParams txtDateGroupP1 = new LinearLayout.LayoutParams(0,LayoutParams.WRAP_CONTENT,25f);
				        li.setLayoutParams(txtDateGroupP1);
				        linearsubMain.addView(li);
				        
	    	    	   TextView txtGroup = new TextView(mContext);
		    	       LinearLayout.LayoutParams AcsubGroupLP = new LinearLayout.LayoutParams(0,LayoutParams.WRAP_CONTENT,100f);
		    	       txtGroup.setLayoutParams(AcsubGroupLP);
		    	       txtGroup.setText(subGroup.name);
		    	       txtGroup.setTextColor(mContext.getResources().getColor(R.color.Red));
		    	       linearsubMain.addView(txtGroup);
		    	       
		    	       llMainContainer.addView(linearsubMain);
		    	      if(subGroup.accounts!=null){
			    	         for (Object_Account objAccount : subGroup.accounts) {
			    	        	 
			    	        	    LinearLayout linearmainContainer = new LinearLayout(mContext);
						            LinearLayout.LayoutParams linearmainContainerLp = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT,0f);
						            linearmainContainer.setLayoutParams(linearmainContainerLp);
						            linearmainContainer.setOrientation(LinearLayout.VERTICAL);
			    	        	 
							         LinearLayout linearBase = new LinearLayout(mContext);
							         LinearLayout.LayoutParams linearBaseLp = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT,0f);
							         linearBase.setLayoutParams(linearBaseLp);
							         linearBase.setOrientation(LinearLayout.HORIZONTAL);
							       
							         LinearLayout li1 = new LinearLayout(mContext);
							         li1.setLayoutParams(txtDateGroupP);
							         linearBase.addView(li1);
							         
							        TextView acName = new TextView(mContext);
							         LinearLayout.LayoutParams acNameLp = new LinearLayout.LayoutParams(0,LayoutParams.WRAP_CONTENT,40f);
							         acName.setLayoutParams(acNameLp);
							         acName.setText(objAccount.name);
							         acName.setGravity(Gravity.CENTER);
							         Log.i("SUSHIL", "Account name is "+objAccount.name);
							         linearBase.addView(acName);
							         
							         TextView Account = new TextView(mContext);
							         LinearLayout.LayoutParams AccountLp = new LinearLayout.LayoutParams(0,LayoutParams.WRAP_CONTENT,30f);
							         Account.setLayoutParams(AccountLp);
							         Account.setText("-");
							         Account.setGravity(Gravity.CENTER);
							         linearBase.addView(Account);
							         
							         TextView cashAmount = new TextView(mContext);
							         LinearLayout.LayoutParams cashAmountLp = new LinearLayout.LayoutParams(0,LayoutParams.WRAP_CONTENT,30f);
							         cashAmount.setLayoutParams(cashAmountLp);
							         cashAmount.setText("459088");
							         cashAmount.setGravity(Gravity.CENTER);
							         linearBase.addView(cashAmount);
							         
							         TextView transferAmount = new TextView(mContext);
							         LinearLayout.LayoutParams transferAmountLp = new LinearLayout.LayoutParams(0,LayoutParams.WRAP_CONTENT,30f);
							         transferAmount.setLayoutParams(transferAmountLp);
							         transferAmount.setText(objAccount.accountTxn.amount+"");
							         transferAmount.setGravity(Gravity.CENTER);
							         linearBase.addView(transferAmount);
							         
							         TextView TotalAmount = new TextView(mContext);
							         LinearLayout.LayoutParams TotalAmountLp = new LinearLayout.LayoutParams(0,LayoutParams.WRAP_CONTENT,30f);
							         TotalAmount.setLayoutParams(TotalAmountLp);
							         TotalAmount.setText(objAccount.accountTxn.amount+"");
							         TotalAmount.setGravity(Gravity.CENTER);
							         linearBase.addView(TotalAmount);
							         
							         LinearLayout linearItem = new LinearLayout(mContext);
							         LinearLayout.LayoutParams linearItemLp = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT,0.7f);
							         linearItem.setLayoutParams(linearItemLp);
							         linearItem.setOrientation(LinearLayout.HORIZONTAL);
							         
							         LinearLayout linearItemVer = new LinearLayout(mContext);
							         LinearLayout.LayoutParams linearItemVerLp = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);
							         linearItemVer.setLayoutParams(linearItemVerLp);
							         linearItemVer.setOrientation(LinearLayout.VERTICAL);
							         
							       if(objAccount.itemCategories!=null){  
							         for(Object_Item_Category objCat : objAccount.itemCategories){
							        	 for(Object_Item objItem : objCat.items){
							        		   Object_Item_Txn objTxnItem =  objItem.itemTxn;
							        		   
							        		   LinearLayout li11 = new LinearLayout(mContext);
										       li11.setLayoutParams(txtDateGroupP);
										       linearItem.addView(li11);  
							        		   
							        		  TextView txtItem = new TextView(mContext);
							        		   LinearLayout.LayoutParams txtItemLp = new LinearLayout.LayoutParams(0,LayoutParams.WRAP_CONTENT,80f);
							        		   txtItem.setLayoutParams(txtItemLp);
							        		   txtItem.setText("500 Bags");
							        		   linearItem.addView(txtItem);
							        		   linearItemVer.addView(linearItem);
							        		   
							        	 }
							        	 
							         }
							       }
							       linearmainContainer.addView(linearBase);
							       linearmainContainer.addView(linearItemVer);
							       llMainContainer.addView(linearmainContainer);
						       }
			    	         
		    	       }
		    	      
				  }
	    	    }
			
	//	} 
		
		
	     
	    return row;
	
		

	}

	private class AccountFilter extends Filter {

		@SuppressLint("DefaultLocale")
		@Override
		protected FilterResults performFiltering(CharSequence constraint) {
			// Create a FilterResults object
			FilterResults results = new FilterResults();

			// If the constraint (search string/pattern) is null
			// or its length is 0, i.e., its empty then
			// we just set the `values` property to the
			// original contacts list which contains all of them
			if (constraint == null || constraint.length() == 0) {
				results.values = Custom_adapterAccounts.listAccounts;
				results.count = Custom_adapterAccounts.listAccounts.size();
			} else {
				// Some search copnstraint has been passed
				// so let's filter accordingly
				ArrayList<Object_Account> filteredAccounts = new ArrayList<Object_Account>();

				// We'll go through all the contacts and see
				// if they contain the supplied string
				Log.i("SUSHIL", "List size "
						+ Custom_adapterAccounts.listAccounts.size());
				for (Object_Account c : Custom_adapterAccounts.listAccounts) {
					if (c.name.toUpperCase().contains(
							constraint.toString().toUpperCase())) {
						filteredAccounts.add(c);
					}
				}

				// Finally set the filtered values and size/count
				results.values = filteredAccounts;
				results.count = filteredAccounts.size();
				Log.i("SUSHIL", "Filtered class return result");
			}

			// Return our FilterResults object
			return results;
		}

		@Override
		protected void publishResults(CharSequence constraint,
				FilterResults results) {
			listAccountsGroupTxn = (ArrayList<Object_Account_Group>) results.values;
			notifyDataSetChanged();
		}
	}

	@Override
	public Filter getFilter() {
		if (mFilter == null)
			mFilter = new AccountFilter();

		return mFilter;
	}
	
}
