package com.XB_Accounts;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class Custom_TabpagerAdapter extends FragmentStatePagerAdapter {
	Context context;
	int count;
    public Custom_TabpagerAdapter(FragmentManager fm,Context context,int count) {
		super(fm);
		this.context = context;
		this.count = count;
		// TODO Auto-generated constructor stub
	}

	@Override
	public Fragment getItem(int i) {
		if(context instanceof Activity_accounts){
		switch (i) {
        case 0:
            return new Custom_showAccounts();
        case 1:
        	return new Custom_showItem_Category();
        case 2:
        	return new Custom_showItem();
        }
		}else if(context instanceof Activity_dailyLager){
			switch (i) {
	        case 0:
	            return new Custom_dailyCredit();
	        case 1:
	            return new Custom_dailyDebit();
	          }
		}
		return null;
		
		
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return count; //No of Tabs
	}


    }