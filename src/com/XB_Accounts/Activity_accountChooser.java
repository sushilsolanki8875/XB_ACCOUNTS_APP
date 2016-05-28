package com.XB_Accounts;

import java.util.ArrayList;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.SearchView;

public class Activity_accountChooser extends Activity {
	public static ExpandableListView expList;
	public static ListView listView;
	public static GridView gridView;
	float fontSize;
	//EditText editSearch;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_account_chooser);
		
		Intent i = getIntent();
	    String type = i.getStringExtra("Type");
		expList = (ExpandableListView) findViewById(R.id.expandablelistAcgpShow);
		// listView = (ListView)findViewById(R.id.listviewAccount);
		gridView = (GridView) findViewById(R.id.gridView1);
		//editSearch = (EditText) findViewById(R.id.edtSearch);
		fontSize = Globals.getAppFontSize(this);
		if(type.equals("Accounts"))
		    showAccounts();
		else if(type.equals("Items"))
			showItems();
		
		
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		Object_AppConfig config = new Object_AppConfig(this);
		Globals.setLocale(config.getLocale(), this);
	}

	private void showAccounts() {

		// hideSpinnerCategories();
		try {
			DBHandler_AccountsGroup DBaccountGp = new DBHandler_AccountsGroup(this);
			ArrayList<Object_Account_Group> listGroup = DBaccountGp.getAllAccountGrp();
			/*ArrayList<Object_Account_Group> Listgp = new ArrayList<Object_Account_Group>();
			ArrayList<Object_Account> listAc = new ArrayList<Object_Account>();
			for (int i = 0; i < 2; i++) {
				Object_Account_Group obj = new Object_Account_Group();
				obj.id = 1;
				obj.daytime = "531655";
				obj.description = "sdgfdg";
				obj.name = "Sub items";
				obj.parentId = 0;
				if (i == 0) {
					for (int j = 0; j < 2; j++) {
						Object_Account objAc = new Object_Account();
						if (j == 0) {
							objAc.name = "Accontname";
						} else {
							objAc.name = "SUSHil kumar 56";
						}
						listAc.add(objAc);
					}
					obj.accounts = listAc;
				}
				Listgp.add(obj);
			}
			ArrayList<Object_Account_Group> listAcgp = new ArrayList<Object_Account_Group>();
			for (int i = 0; i < 12; i++) {
				Object_Account_Group obj = new Object_Account_Group();
				obj.id = 1;
				obj.daytime = "531655";
				obj.description = "sdgfdg";
				obj.parentId = 0;
				obj.name = "Testing";
				obj.accountGroups = Listgp;
				listAcgp.add(obj);
			}*/
			// Log.i("SUSHIL", "category de "+listNewsCategory.size());
			Custom_adapterAccounts adapter = new Custom_adapterAccounts(this,
					listGroup, fontSize);
			expList.setAdapter(adapter);
		} catch (Exception e) {
			e.printStackTrace();
		}

		/*
		 * if (listNewsCategory.size() > 0) { currentCategoryId =
		 * listNewsCategory.get(0).getId();
		 * //getNewsDataFromServer(currentCategoryId, Globals.CALLTYPE_FRESH,
		 * 0,false); }else{ hideLoadingScreen(); }
		 */
		// Object_AppConfig obj = new Object_AppConfig(this);
		// showNewsList(obj.getRootCatId());
		// hideLoadingScreen();
		/*
		editSearch.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {

				Custom_adapterGrid adapter = (Custom_adapterGrid) gridView
						.getAdapter();
				if (adapter != null) {
					//Log.i("SUSHIL", "this searchable text " + s.toString());
					adapter.getFilter().filter(s.toString());
				}

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {

			}
		});*/
	}
	
	private void showItems(){
	try{
		/*ArrayList<Object_Account> listAccount = new ArrayList<Object_Account>();
		ArrayList<Object_Item_Category> listItems = new  ArrayList<Object_Item_Category>();
		ArrayList<Object_Item> Listitems = new ArrayList<Object_Item>();
      for(int k=0;k<1;k++){
    	  Object_Account obj = new Object_Account();
    	  obj.name = "Goods";
        for(int i=0;i<1;i++){
			Object_Item_Category cate = new Object_Item_Category();
			cate.name = "fertilizers";
			for(int j =0;j<4;j++){
				Object_Item item = new Object_Item();
				item.name = "Urea";
			Listitems.add(item);
			}
			cate.items = Listitems;
			listItems.add(cate);
		}
        obj.itemCategories = listItems;
        listAccount.add(obj);
      }*/
		ArrayList<Object_Account> listAccount;
		DBHandler_Account dbhAccount = new DBHandler_Account(this);
		listAccount = dbhAccount.getItemAccounts();
		Custom_adapterItems adapter = new Custom_adapterItems(this,
				listAccount, fontSize);
		expList.setAdapter(adapter);
	   } catch (Exception e) {
		e.printStackTrace();
	  }
	    

/*	editSearch.addTextChangedListener(new TextWatcher() {

		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {

			Custom_adapterGrid_Items adapter = (Custom_adapterGrid_Items) gridView
					.getAdapter();
			if (adapter != null) {
				//Log.i("SUSHIL", "this searchable text " + s.toString());
				adapter.getFilter().filter(s.toString());
			}

		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
			// TODO Auto-generated method stub

		}

		@Override
		public void afterTextChanged(Editable s) {

		}
	  });*/
	}
	
	public void End(){
		this.finish();
	}
	
	 @Override
	    public void onCreateContextMenu(ContextMenu menu, View v,
	    		ContextMenuInfo menuInfo) {
	    	/*SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
		     SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
	         searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
		     searchView.setIconifiedByDefault(false); */
	    }
	    
	    @Override
		public boolean onCreateOptionsMenu(Menu menu) {
		    MenuInflater inflater = getMenuInflater();
		    inflater.inflate(R.menu.main, menu);

		    SearchManager searchManager = (SearchManager)
		                            getSystemService(Context.SEARCH_SERVICE);
		   MenuItem searchMenuItem = menu.findItem(R.id.action_search);
		    SearchView  searchView = (SearchView) searchMenuItem.getActionView();
	         searchView.setSearchableInfo(searchManager.
		                            getSearchableInfo(getComponentName()));
	         
	         searchView.setOnQueryTextListener(new OnQueryTextListener() {
	 			
	 			@Override
	 			public boolean onQueryTextSubmit(String query) {
	 				// TODO Auto-generated method stub
	 				return false;
	 			}
	 			
	 			@Override
	 			public boolean onQueryTextChange(String newText) {
	 				
	 				try{
	 					
	 					/*Custom_adapterGrid_Items adapter = (Custom_adapterGrid_Items) gridView
	 							.getAdapter();
	 					if (adapter != null) {
	 						//Log.i("SUSHIL", "this searchable text " + s.toString());
	 						adapter.getFilter().filter(newText.toString());
	 					}*/
	 					
	 					Custom_adapterGrid adapter1 = (Custom_adapterGrid) gridView
	 							.getAdapter();
	 					if (adapter1 != null) {
	 						//Log.i("SUSHIL", "this searchable text " + s.toString());
	 						adapter1.getFilter().filter(newText.toString());
	 					}
	 					
	 				}catch(Exception E){
	 					E.printStackTrace();
	 				}
	 				
	 				return true;
	 			}
	 		});
	         
	 	    return true;
	         
	    }
	
}
