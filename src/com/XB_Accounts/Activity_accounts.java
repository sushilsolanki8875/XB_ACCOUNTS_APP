package com.XB_Accounts;

import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RatingBar.OnRatingBarChangeListener;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.SimpleAdapter;

public class Activity_accounts extends FragmentActivity {
	ViewPager Tab;
    Custom_TabpagerAdapter TabAdapter;
	ActionBar actionBar;
	//static int AcId = -1;
	SearchView searchView;
	SimpleAdapter adapter;
	int selectedTab = -1;
	public ProgressDialog pd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accounts);
       pd = Globals.showLoadingDialog(pd, this, false, "");
        inti();
        Globals.hideLoadingDialog(pd);
    }
    /*public static void move(int position) {
    	actionBar.setSelectedNavigationItem(position); 
		
	}*/
    
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
    		ContextMenuInfo menuInfo) {
    	
    	/*if (v.getId()==R.id.) {
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)menuInfo;
            menu.setHeaderTitle(Countries[info.position]);
            String[] menuItems = getResources().getStringArray(R.array.menu);
            for (int i = 0; i<menuItems.length; i++) {
              menu.add(Menu.NONE, i, i, menuItems[i]);
            }*/
    	
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
	    //searchView.setSubmitButtonEnabled(true);
	    searchView.setOnQueryTextListener(new OnQueryTextListener() {
			
			@Override
			public boolean onQueryTextSubmit(String query) {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public boolean onQueryTextChange(String newText) {
				
				try{
					/* adapter.notifyDataSetChanged();
					Log.i("SUSHIL","SUSHIL text is "+newText);
					((SimpleAdapter)adapter).getFilter().filter(newText.toString().toLowerCase());*/
			if(selectedTab!=-1){
				if(selectedTab==0){	
					if(Custom_showAccounts.adapter!=null){
					Custom_showAccounts.adapter.getFilter().filter(newText.toString().toLowerCase());
					}
				}else if(selectedTab==1){
					if(Custom_showItem_Category.adaptor!=null){
						Custom_showItem_Category.adaptor.getFilter().filter(newText.toString().toLowerCase());
					}
				}else if(selectedTab==2){
					if(Custom_showItem.adaptor!=null){
						Custom_showItem.adaptor.getFilter().filter(newText.toString().toLowerCase());
					}
				}
					
			}     
				}catch(Exception E){
					E.printStackTrace();
				}
				
				return true;
			}
		});
        
	    return true;
	}
	/*@Override
	public boolean onQueryTextSubmit(String query) {
		// TODO Auto-generated method stub
		//friendListAdapter.getFilter().filter(newText);
		return false;
	}
	@Override
	public boolean onQueryTextChange(String newText) {
		// TODO Auto-generated method stub
	try{
		 
		Log.i("SUSHIL","SUSHIL text is "+newText);
		
		((SimpleAdapter)adapter).getFilter().filter(newText.toString().toLowerCase());
		 adapter.notifyDataSetChanged();
	     
	}catch(Exception E){
		E.printStackTrace();
	}
		return true;
	}*/
    private void inti(){
    	 FragmentManager fm = getSupportFragmentManager();
         actionBar = getActionBar();
          TabAdapter = new Custom_TabpagerAdapter(fm,Activity_accounts.this,3);
          
          Tab = (ViewPager)findViewById(R.id.pager);
          Tab.setOnPageChangeListener(
                  new ViewPager.SimpleOnPageChangeListener() {
                      @Override
                      public void onPageSelected(int position) {
                      	actionBar.setSelectedNavigationItem(position);
                      }
                  });
          Tab.setAdapter(TabAdapter);
          
          actionBar = getActionBar();
          //Enable Tabs on Action Bar
          actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
          ActionBar.TabListener tabListener = new ActionBar.TabListener(){

  			@Override
  			public void onTabReselected(android.app.ActionBar.Tab tab,
  					FragmentTransaction ft) {
  				// TODO Auto-generated method stub
  				
  			}

  			@Override
  			 public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
  	          
  	            Tab.setCurrentItem(tab.getPosition());
  	           android.support.v4.app.Fragment gp = TabAdapter.getItem(tab.getPosition());
  	          selectedTab = tab.getPosition();
  	        /* try{
  	         if(tab.getPosition()==0){
  	        	 Log.i("SUSHIL","this is show account gp");
  	        	 adapter = null;
  	        	 adapter = Custom_showAccounts.adaptor;
  	          }
  	          else if(tab.getPosition()==1){
  	        	  Log.i("SUSHIL","this is show item gp");
  	        	  adapter = null;
  		          adapter = Custom_showItem_Category.adaptor;
  	          }
  	           else if(tab.getPosition()==2){
  	        	  Log.i("SUSHIL","this is show item");
  	        	   adapter = null;
  	        	   adapter = Custom_showItem.adaptor;
  	          }
  	         }catch(Exception e){
  	        	 e.printStackTrace();
  	         }*/
  	        }

  			@Override
  			public void onTabUnselected(android.app.ActionBar.Tab tab,
  					FragmentTransaction ft) {
  				// TODO Auto-generated method stub
  				
  			}};
  			//Add New Tab
  			//actionBar.addTab(actionBar.newTab().setText("Account Group").setTabListener(tabListener));
  			actionBar.addTab(actionBar.newTab().setText("Accounts").setTabListener(tabListener));
  			actionBar.addTab(actionBar.newTab().setText("Item Category").setTabListener(tabListener));
  			actionBar.addTab(actionBar.newTab().setText("Items").setTabListener(tabListener));
  			//actionBar.addTab(actionBar.newTab().setText("Windows").setTabListener(tabListener));
  			//ListView list = (ListView)findViewById(R.id.listshowaccounts);
  			
  			
    }
    
   
    
    
    @Override
    protected void onResume() {
    	// TODO Auto-generated method stub
    	super.onResume();
    	
    	Object_AppConfig config = new Object_AppConfig(this);
		Globals.setLocale(config.getLocale(), this);
    }
	
	}


