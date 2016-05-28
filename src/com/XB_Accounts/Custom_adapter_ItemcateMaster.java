package com.XB_Accounts;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnLongClickListener;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.PopupMenu.OnMenuItemClickListener;

public class Custom_adapter_ItemcateMaster extends BaseAdapter implements Filterable{

	  private List<Object_Item_Category>originalData = null;
			private List<Object_Item_Category>filteredData = null;
			private LayoutInflater mInflater;
			private ItemFilter mFilter = new ItemFilter();
			Context con;
			public Custom_adapter_ItemcateMaster(Context context, List<Object_Item_Category> data) {
				this.filteredData = data ;
				this.originalData = data ;
				this.con = context;
		    	mInflater = LayoutInflater.from(context);
		    }

			public int getCount() {
				return filteredData.size();
			}

			public Object getItem(int position) {
				return filteredData.get(position);
			}

			public long getItemId(int position) {
				return position;
			}

		    public View getView(int position, View convertView, ViewGroup parent) {
		        // A ViewHolder keeps references to children views to avoid unnecessary calls
		        // to findViewById() on each row.
		        ViewHolder holder;
               final int id = position;
		        // When convertView is not null, we can reuse it directly, there is no need
		        // to reinflate it. We only inflate a new View when the convertView supplied
		        // by ListView is null.
		        if (convertView == null) {
		            convertView = mInflater.inflate(R.layout.custom_showacgroup, null);

		            // Creates a ViewHolder and store references to the two children views
		            // we want to bind data to.
		            holder = new ViewHolder();
		            holder.textName = (TextView) convertView.findViewById(R.id.txtitem);
		            holder.textParent = (TextView) convertView.findViewById(R.id.txtitem2);
		            holder.textDes = (TextView) convertView.findViewById(R.id.txtitem3);

		            // Bind the data efficiently with the holder.

		            convertView.setTag(holder);
		        } else {
		            // Get the ViewHolder back to get fast access to the TextView
		            // and the ImageView.
		            holder = (ViewHolder) convertView.getTag();
		        }

		        // If weren't re-ordering this you could rely on what you set last time
		        holder.textName.setText(filteredData.get(position).name);
		        holder.textParent.setText(filteredData.get(position).parrentName);
		        holder.textDes.setText(filteredData.get(position).description);
		        
		        convertView.setOnLongClickListener(new OnLongClickListener() {
					
					@Override
					public boolean onLongClick(View v) {
						
						popup(con,v,filteredData.get(id).id);
						
						return true;
					}
				});

		        return convertView;
		    }
			
		    static class ViewHolder {
		        TextView textName;
		        TextView textParent;
		        TextView textDes;
		    }

			public Filter getFilter() {
				return mFilter;
			}

			private class ItemFilter extends Filter {
				@Override
				protected FilterResults performFiltering(CharSequence constraint) {
					
					String filterString = constraint.toString().toLowerCase();
					
					FilterResults results = new FilterResults();
					
					final List<Object_Item_Category> list = originalData;

					int count = list.size();
					final ArrayList<Object_Item_Category> nlist = new ArrayList<Object_Item_Category>(count);

					Object_Item_Category filterable;
					
					for (int i = 0; i < count; i++) {
						filterable = list.get(i);
						if (filterable.name.toLowerCase().contains(filterString)) {
							nlist.add(filterable);
						}else if (filterable.parrentName.toLowerCase().contains(filterString)) {
							nlist.add(filterable);
						}
					}
					
					results.values = nlist;
					results.count = nlist.size();

					return results;
				}

				@SuppressWarnings("unchecked")
				@Override
				protected void publishResults(CharSequence constraint, FilterResults results) {
					filteredData = (ArrayList<Object_Item_Category>) results.values;
					notifyDataSetChanged();
				}

			}
	
			
			
			private void popup(Context m,View v,final int idmain){
				/** Instantiating PopupMenu class */
		        final PopupMenu popup = new PopupMenu(con, v);

		        /** Adding menu items to the popumenu */
		        popup.getMenuInflater().inflate(R.menu.popup, popup.getMenu());
	              
		        /** Defining menu item click listener for the popup menu */
		        popup.setOnMenuItemClickListener(new OnMenuItemClickListener() {

		            @Override
					public boolean onMenuItemClick(MenuItem item) {
		            	int id = item.getItemId();
		            	switch (id) {
						case R.id.action1:
							Intent i = new Intent(con,Activity_SettingAddNew.class);
					         i.putExtra("Id", idmain);
					         i.putExtra("entry", 1);
					         i.putExtra("into", "Edit");
					         ((Activity) con).startActivity(i);
							break;
	                    case R.id.action2:
	                    	DBHandler_ItemCategory dbh = new DBHandler_ItemCategory(con);
	                    	dbh.disableItem_cate(idmain);
	                    	Toast.makeText(con, "Succesfull item category is Delete! ", Toast.LENGTH_SHORT).show();
	                    	((Activity) con).finish();
	                    	Intent in = ((Activity) con).getIntent();
	                    	((Activity) con).startActivity(in);
	                    	break;
	                    	
	                    case R.id.action3:
	                    	popup.dismiss();
	                    	break;
	                    
						default:
							break;
						}
		            	
				         
						return true;
					}
		        });

		        /** Showing the popup menu */
		        popup.show();
			}
}
