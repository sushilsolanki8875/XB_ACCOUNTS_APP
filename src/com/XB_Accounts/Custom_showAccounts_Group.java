package com.XB_Accounts;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.SimpleAdapter;

public class Custom_showAccounts_Group extends Fragment {
	public static SimpleAdapter adaptor;
	int selectedId;
	@Override
	    public View onCreateView(LayoutInflater inflater, ViewGroup container,
	            Bundle savedInstanceState) {
	          
	       View accounts = inflater.inflate(R.layout.custom_showaccount_group, container, false);
	      /* AA_DBHandler_AccountsGroup DBGP = new AA_DBHandler_AccountsGroup(getActivity());
	        ArrayList<AA_Object_AccountsGroup> ListAcGp =  DBGP.getAllAccountGrp();
	        List<Map<String, String>> data = new ArrayList<Map<String,String>>();
	        //adaptor.setn
	        
	        for(AA_Object_AccountsGroup Gp : ListAcGp){
	        Map<String,String>map=new HashMap<String,String>(2);
	        try{
	        map.put("name", Gp.name);
	      AA_Object_AccountsGroup group =  DBGP.getAccountGrpwithId(Gp.parentid);
			map.put("parent", group.name);
			map.put("des",Gp.description);
			data.add(map);
	        }catch(Exception e){
	        	e.printStackTrace();
	        }
	        }
	        
	        adaptor = new SimpleAdapter(getActivity(), data, R.layout.custom_showacgroup, new String[]{"name","parent","des"}, new int[]{R.id.txtitem,R.id.txtitem2,R.id.txtitem3});
		    Custom_adapter adaptor = new Custom_adapter(getActivity(),data);
		    dapter = adaptor.show();
	       // adaptor.setNotifyOnChange(false);
		   ((ListView)accounts.findViewById(R.id.listshowaccounts_gp)).setAdapter(adaptor);
		   registerForContextMenu(accounts.findViewById(R.id.listshowaccounts_gp));
		   ((ListView)accounts.findViewById(R.id.listshowaccounts_gp)).setOnItemLongClickListener(new OnItemLongClickListener() {
		   
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				selectedId = position+1;
				Log.i("SUSHIL", "selected id "+selectedId);
				Intent i = new Intent(getActivity(),Activity_SettingAddNew.class);
		         i.putExtra("Id", selectedId);
		         i.putExtra("entry", 0);
		         i.putExtra("into", "Edit");
		         startActivity(i);
				return true;
			}
		});
		   */
		   return accounts;
      }

	@Override
	  public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		//getActivity().getMenuInflater().inflate(R.menu.main, menu);
		if(v.getId()==R.id.listshowaccounts_gp){
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
		  //this is edit
		   
	       
	       //i.putExtra("Object", objAcgroup);
	       
	   }
		return false;
	}
	
	
}
