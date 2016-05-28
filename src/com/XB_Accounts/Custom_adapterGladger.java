package com.XB_Accounts;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.text.SpannableStringBuilder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class Custom_adapterGladger extends BaseAdapter {

	
	private Context mContext;
	private ArrayList<Object_Voucher_Txn> listAccounts;
	float font;
	double amount = 0;
	//AccountFilter mFilter;
   
	// Gets the context so it can be used later
	public Custom_adapterGladger(Context c,
			ArrayList<Object_Voucher_Txn> listAccounts, float font) {
		mContext = c;
		this.font = font;
		this.listAccounts = listAccounts;
	}

	// Total number of things contained within the adapter
	public int getCount() {
		return listAccounts.size();
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

		Object_Voucher_Txn obj = listAccounts.get(position);
		ArrayList<Object_Account> account = obj.accounts;
		View row = convertView;
		Custom_holderGl holder = null;

		if (row == null) {
			LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
			row = inflater.inflate(R.layout.custom_row_gl, parent, false);
			holder = new Custom_holderGl();
			holder.date = (TextView) row.findViewById(R.id.rowtxtGLdate);
			holder.parti = (TextView) row.findViewById(R.id.rowtxtGLparti);
			holder.drAmount = (TextView) row.findViewById(R.id.rowtxtGLdebitRS);
			holder.CrAmount = (TextView) row.findViewById(R.id.rowtxtGLcreditRS);
			holder.Amount = (TextView) row.findViewById(R.id.rowtxtGLbalence);
			row.setTag(holder);
		} else {
			holder = (Custom_holderGl) row.getTag();
		}
		
		if(account.size()!=0){
		 Object_Account_Txn txn = account.get(0).accountTxn;
		  if(txn!=null){
        holder.date.setText(obj.daytime);
		holder.date.setTextSize(font);
		holder.parti.setText("");
		holder.parti.setTextSize(font);
		if(txn.txnType.id!=1){
		holder.drAmount.setText(txn.amount+"");
		holder.CrAmount.setText(" - ");
		holder.drAmount.setTextSize(font);
		holder.CrAmount.setTextSize(font);
		amount = amount+txn.amount;
		 }else{
			   holder.drAmount.setText(" - ");
				holder.CrAmount.setText(txn.amount+"");
				holder.drAmount.setTextSize(font);
				holder.CrAmount.setTextSize(font);
				amount = amount-txn.amount;
		  }
		holder.Amount.setText(amount+"");
		holder.Amount.setTextSize(font);
		
		  }
		}
		row.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				//try {
					/*if (mPosition != position) {
						v.setBackground(mContext.getResources().getDrawable(
								R.drawable.list_selecter));
						view.setBackground(mContext.getResources().getDrawable(
								R.drawable.view_press));
					} else {
						v.setBackground(mContext.getResources().getDrawable(
								R.drawable.list_selecter));
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				view = v;
				mPosition = position;*/
			}
		});
		return row;

		/*
		 * Button btn; if (convertView == null) { // if it's not recycled,
		 * initialize some attributes btn = new Button(mContext);
		 * btn.setLayoutParams(new
		 * GridView.LayoutParams(LayoutParams.WRAP_CONTENT
		 * ,LayoutParams.WRAP_CONTENT )); btn.setPadding(8, 20, 8, 8); } else {
		 * btn = (Button) convertView; }
		 * 
		 * btn.setText("sushil"); // filenames is an array of strings
		 * btn.setTextColor(Color.WHITE);
		 * btn.setBackgroundResource(R.color.Black); btn.setId(position);
		 */

	}

	/*private class AccountFilter extends Filter {

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
			listAccounts = (ArrayList<Object_Account>) results.values;
			notifyDataSetChanged();
		}
	}

	@Override
	public Filter getFilter() {
		if (mFilter == null)
			mFilter = new AccountFilter();

		return mFilter;
	}*/
}
