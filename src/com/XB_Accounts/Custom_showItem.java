package com.XB_Accounts;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class Custom_showItem extends Fragment {

	public static Custom_adapter_ItemMaster adaptor;
	int selectedId;

	@Override
	    public View onCreateView(LayoutInflater inflater, ViewGroup container,
	            Bundle savedInstanceState) {
	          
	       View accounts = inflater.inflate(R.layout.custom_showitem_list, container, false);
	       
	       DBHandler_Account DBGP = new DBHandler_Account(
					getActivity());
			ArrayList<Object_Account> listAccounts = DBGP.getItemAccounts();
			List<Object_Item> listItem = new ArrayList<Object_Item>();
	      // List<Map<String, String>> data = new ArrayList<Map<String,String>>();
	       for (Object_Account account : listAccounts) {
	 			    	  if(account.itemCategories!=null){
	 					for (Object_Item_Category itemCat : account.itemCategories) {
	 						for(Object_Item item : itemCat.items){
	 							item.parrentName = itemCat.name;
	 							listItem.add(item);
	 						/*Map<String,String>map=new HashMap<String,String>(2);
	 						 try{
	 						      map.put("name", item.name);
	 						      map.put("parent", itemCat.name);
	 							  map.put("qty",item.qtyRemaining+" "+item.unit.name);
	 							  map.put("saleprice", item.salePrice+"");
	 							  map.put("purchaseprice", item.purchasePrice+"");
	 							  map.put("mrp", item.mrp+"");
	 							  map.put("minsaleprice", item.minSalePrice+"");
	 							  data.add(map);
	 						      }catch(Exception e){
	 						    	  e.printStackTrace();
	 						      }*/
	 					  }
	 					}
	 			    	  }
	 				  }
	 		     adaptor = new Custom_adapter_ItemMaster(getActivity(), listItem);
	     	//adaptor = new SimpleAdapter(getActivity(), data, R.layout.custom_showitems, new String[]{"name","parent","qty","saleprice","purchaseprice","mrp","minsaleprice"}, new int[]{R.id.textname,R.id.textparent,R.id.textqty,R.id.textsaleprice,R.id.textpurchasesprice,R.id.textmrp,R.id.textminsalePrice});
		 /* Custom_adapter adaptor = new Custom_adapter(getActivity());
		   dapter = adaptor.show();*/
		   ((ListView)accounts.findViewById(R.id.listshowItems)).setAdapter(adaptor);
		  /* ((ListView)accounts.findViewById(R.id.listshowItems)).setOnItemLongClickListener(new OnItemLongClickListener() {

				@Override
				public boolean onItemLongClick(AdapterView<?> parent, View view,
						int position, long id) {
					selectedId = position;
					Log.i("SUSHIL", "selected id "+selectedId);
					Intent i = new Intent(getActivity(),Activity_SettingAddNew.class);
					   Log.i("SUSHIL", "Slected id "+selectedId);
				         i.putExtra("Id", position);
				         i.putExtra("entry", 3);
				         i.putExtra("into", "Edit");
				         startActivity(i);
					return true;
				}
			});*/
		   registerForContextMenu(accounts.findViewById(R.id.listshowItems));
	        return accounts;
	       
      }

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		// getActivity().getMenuInflater().inflate(R.menu.main, menu);
		if (v.getId() == R.id.listshowItems) {
			AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
			String[] menuitems = getResources().getStringArray(R.array.menu);
			for (int i = 0; i < menuitems.length; i++) {
				menu.add(Menu.NONE, i, i, menuitems[i]);
			}
		}

		/*
		 * SearchView.OnQueryTextListener textChangeListener = new
		 * SearchView.OnQueryTextListener() {
		 * 
		 * @Override public boolean onQueryTextChange(String newText) { // this
		 * is your adapter that will be filtered
		 * dapter.getFilter().filter(newText);
		 * System.out.println("on text chnge text: "+newText); return true; }
		 * 
		 * @Override public boolean onQueryTextSubmit(String query) { // this is
		 * your adapter that will be filtered dapter.getFilter().filter(query);
		 * System.out.println("on query submit: "+query); return true; } };
		 * searchView.setOnQueryTextListener(textChangeListener);
		 */
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item
				.getMenuInfo();
		int menuItemindex = item.getItemId();
		String[] menuitems = getResources().getStringArray(R.array.menu);
		String name = menuitems[menuItemindex];
		// Toast.makeText(Activity_accounts.this, name,
		// Toast.LENGTH_SHORT).show();
		if (name.equals("Edit")) {

		}
		return false;
	}

}
