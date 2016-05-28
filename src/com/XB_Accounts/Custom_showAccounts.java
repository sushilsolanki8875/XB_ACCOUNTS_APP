package com.XB_Accounts;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class Custom_showAccounts extends Fragment {
	
	public static Custom_adapter_AccountsMaster adapter;
	int selectedId;
	//ProgressDialog pd;
	@Override
	    public View onCreateView(LayoutInflater inflater, ViewGroup container,
	            Bundle savedInstanceState) {
		  
	        View accounts = inflater.inflate(R.layout.custom_showaccounts, container, false);
	        DBHandler_AccountsGroup DBGP = new DBHandler_AccountsGroup(getActivity());
	        ArrayList<Object_Account_Group> ListAcGp =  DBGP.getAllAccountGrp();
	        List<Object_Account> listAccounts = new ArrayList<Object_Account>();
	       // List<Map<String, String>> data = new ArrayList<Map<String,String>>();
	        for(Object_Account_Group Gp : ListAcGp){
	      if(Gp.accountGroups!=null){
	    	  for (Object_Account_Group subGroup : Gp.accountGroups) {
	    		  for (Object_Account account : subGroup.accounts) {
	    			  account.grpName = subGroup.name;
	    			  listAccounts.add(account);
	    			 /* Map<String,String>map=new HashMap<String,String>(2);
		    		  map.put("parent", subGroup.name);
	    			  map.put("name", account.name);
	    			  map.put("des",account.description);
	    			  data.add(map);*/
				}
				}
	          }
	      if(Gp.accounts!=null){
	    	 
	    	for (Object_Account account : Gp.accounts) {
	    		  account.grpName = Gp.name;
	    		  listAccounts.add(account);
	    		  /*Map<String,String>map=new HashMap<String,String>(2);
    			  map.put("name", account.name);
    			  map.put("parent", Gp.name);
    			  map.put("des",account.description);
    			  data.add(map);*/
			}
	      }
	        }
	        adapter = new Custom_adapter_AccountsMaster(getActivity(), listAccounts);
	      // adaptor = new SimpleAdapter(getActivity(), data, R.layout.custom_showacgroup, new String[]{"name","parent","des"}, new int[]{R.id.txtitem,R.id.txtitem2,R.id.txtitem3});
		  /*Custom_adapter adaptor = new Custom_adapter(getActivity());
		   dapter = adaptor.show();*/
		   ((ListView)accounts.findViewById(R.id.listshowaccounts)).setAdapter(adapter);
		   /*((ListView)accounts.findViewById(R.id.listshowaccounts)).setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				selectedId = position;
				Log.i("SUSHIL", "selected id "+selectedId);
				 
				return true;
			}
		});*/
		    registerForContextMenu(accounts.findViewById(R.id.listshowaccounts));
		   // Globals.hideLoadingDialog(Activity_accounts.pd);
	        return accounts;
}
	
	@Override
	  public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		//getActivity().getMenuInflater().inflate(R.menu.main, menu);
		if(v.getId()==R.id.listshowaccounts){
			AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
			String[] menuitems = getResources().getStringArray(R.array.menu);
			for(int i =0;i<menuitems.length;i++){
				menu.add(Menu.NONE, i, i, menuitems[i]);
			}
			
			
			
		}
		   

	       /* SearchView.OnQueryTextListener textChangeListener = new SearchView.OnQueryTextListener() 
	        {
	            @Override
	            public boolean onQueryTextChange(String newText) 
	            {
	                // this is your adapter that will be filtered
	                dapter.getFilter().filter(newText);
	                System.out.println("on text chnge text: "+newText);
	                return true;
	            }
	            @Override
	            public boolean onQueryTextSubmit(String query) 
	            {
	                // this is your adapter that will be filtered
	                dapter.getFilter().filter(query);
	                System.out.println("on query submit: "+query);
	                return true;
	            }
	        };
	        searchView.setOnQueryTextListener(textChangeListener);*/
	  }

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
		int menuItemindex = item.getItemId();
		String[] menuitems = getResources().getStringArray(R.array.menu);
		String name = menuitems[menuItemindex];
		//Toast.makeText(Activity_accounts.this, name, Toast.LENGTH_SHORT).show();
	   if(name.equals("Edit")){
		  
	   }
		return false;
	}
	
}
