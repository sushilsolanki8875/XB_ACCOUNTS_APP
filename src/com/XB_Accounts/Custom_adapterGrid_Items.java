package com.XB_Accounts;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

public class Custom_adapterGrid_Items extends BaseAdapter implements Filterable  {

	private Context mContext;
	private ArrayList<Object_Item> listItems;
	float font;
	/*private int mPosition;
	private View view;*/
	AccountFilter mFilter;
	public static Object_Account object;
	private Object_Account objAc;
   
	// Gets the context so it can be used later
	public Custom_adapterGrid_Items(Context c,ArrayList<Object_Item> listItems, float font,Object_Account obj) {
		mContext = c;
		this.font = font;
		this.objAc = obj;
		Log.i("JASPAL","Account name setting acnt obj:"+obj.name);
		this.listItems = listItems;
	}

	// Total number of things contained within the adapter
	public int getCount() {
		return listItems.size();
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

		final Object_Item obj = listItems.get(position);
		View row = convertView;
		Custom_holder holder = null;

		if (row == null) {
			LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
			row = inflater.inflate(R.layout.account_row, parent, false);
			holder = new Custom_holder();
			holder.txt = (TextView) row.findViewById(R.id.txt_account);
			row.setTag(holder);
		} else {
			holder = (Custom_holder) row.getTag();
		}

		holder.txt.setText(obj.name);
		// holder.txt.setGravity(Gravity.CENTER_VERTICAL);
		holder.txt.setTextSize(font);
		// holder.txt.setLayoutParams(new LayoutParams(100,100));
		row.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				//objAc = new Object_Account();
				ArrayList<Object_Item> items = new ArrayList<Object_Item>();
				items.add(obj);
				objAc.itemCategories.get(0).items  = items;
			    //object.itemCategories = objAc.itemCategories;
				 object = objAc;
			   
			 /*  Log.i("SUSHIL", "Item name is "+object.itemCategories.get(0).items.get(0).name);
			   Log.i("JASPAL","Account name is from item adapter :"+object.name);*/
            	((Activity)mContext).finish();
				/*try {
					if (mPosition != position) {
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
				results.values = Custom_adapterItems.listAccounts;
				results.count = Custom_adapterItems.listAccounts.size();
			} else {
				// Some search copnstraint has been passed
				// so let's filter accordingly
				ArrayList<Object_Item> filteredAccounts = new ArrayList<Object_Item>();

				// We'll go through all the contacts and see
				// if they contain the supplied string
				Log.i("SUSHIL", "List size "
						+ Custom_adapterItems.listAccounts.size());
				for (Object_Item c : Custom_adapterItems.listAccounts) {
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
			listItems = (ArrayList<Object_Item>) results.values;
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
