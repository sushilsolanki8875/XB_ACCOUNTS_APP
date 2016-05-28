package com.XB_Accounts;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class Activity_transaction extends Activity {
	int selectedId = 0;
	int AcId = -1;
	int idlay = 1;
	int tagIndex = -1;
	int clickedTagIndex = -1;
	public String itemName = "";
	TextView txtVoucherDate;
	Typeface fontFamily;
  public static Context mContext;
	public static Object_Voucher_Txn objVchTran;
	public ArrayList<Object_Ac_Icat_Itm_map> mapAll;
	public Object_Account partyAccount;
	boolean partyAc = false;
  boolean editable = false;
	
   String into;
  
	public static ArrayList<Object_Account> ListAc;
	ArrayList<Object_Voucher_Master> Listvc;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_transaction);
		
		Intent i = getIntent();
		 into = i.getStringExtra("into");
		
		Custom_adapterGrid.object = null;
		fontFamily = Typeface.createFromAsset(getAssets(),
				"fonts/fontawesome-webfont.ttf");
		objVchTran = new Object_Voucher_Txn();
		mapAll = new ArrayList<Object_Ac_Icat_Itm_map>();
       mContext = this;
		objVchTran.accounts = new ArrayList<Object_Account>();

		
		ListAc = new ArrayList<Object_Account>();
		txtVoucherDate = (TextView) findViewById(R.id.txtvoucherDate);
		Globals.setCurrentDateOnview(txtVoucherDate);

		DBHandler_voucher_master DBvc = new DBHandler_voucher_master(this);
		Listvc = DBvc.getAllVouchers();
		spinnerDataVoucher();

		/*btnAdd.setTypeface(fontFamily);
		btnAdd.setText("\uf067  Add");
		btnDelete.setTypeface(fontFamily);
		btnDelete.setText("\uf00d Delete");*/
		
		TextView txtAc = (TextView) findViewById(R.id.txtchoosAc);
		txtAc.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				partyAc = true;
                Intent i = new Intent(Activity_transaction.this,
						Activity_accountChooser.class);
				i.putExtra("Type", "Accounts");
				startActivity(i);

			}
		});
		/*
		 * txtAc.addTextChangedListener(new TextWatcher() {
		 * 
		 * @Override public void onTextChanged(CharSequence s, int start, int
		 * before, int count) { // TODO Auto-generated method stub
		 * 
		 * }
		 * 
		 * @Override public void beforeTextChanged(CharSequence s, int start,
		 * int count, int after) { // TODO Auto-generated method stub
		 * 
		 * }
		 * 
		 * @Override public void afterTextChanged(Editable s) {
		 * ArrayList<AA_Object_Accounts> acList; String text = s.toString();
		 * AA_DBHandler_Accounts Dbac = new
		 * AA_DBHandler_Accounts(Activity_transaction.this); acList =
		 * Dbac.getAccounts(text);
		 * 
		 * for(int i = 0;i<acList.size();i++){ Acname.clear();
		 * AA_Object_Accounts obj = acList.get(i); Acname.add(obj.name);
		 * 
		 * 
		 * ArrayAdapter adapterAc = new
		 * ArrayAdapter(Activity_transaction.this,android
		 * .R.layout.simple_list_item_1,Acname); ac.setAdapter(adapterAc);
		 * ac.setThreshold(1); AcId = obj.id; Log.i("SUSHIL",
		 * "ac name added "+AcId); } } });
		 */
	}
	
	private void spinnerDataVoucher(){
		final Spinner spinner = (Spinner) findViewById(R.id.spinnerVoucher);
		spinner.setEnabled(true);
		ArrayList<String> voucherType = new ArrayList<String>();
		for (int i = 0; i < Listvc.size(); i++) {
			Object_Voucher_Master obj = Listvc.get(i);
			voucherType.add(obj.name);
		}
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, voucherType);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter);
		spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View v,
					int position, long id) {
				LinearLayout scRoll = (LinearLayout) findViewById(R.id.scRoll);

				if (selectedId != position) {
					idlay = 1;
					scRoll.removeAllViews();
					objVchTran = new Object_Voucher_Txn();
					objVchTran.accounts = new ArrayList<Object_Account>();
					partyAccount = null;
					tagIndex = -1;
					clickedTagIndex = -1;
					mapAll.clear();
				}
				selectedId = position;
				//Log.i("SUSHIL", "selected id " + selectedId);
				Object_Voucher_Master obj = Listvc.get(position);
				LinearLayout linearItems = (LinearLayout) findViewById(R.id.linearItems);
				LinearLayout linearAc = (LinearLayout) findViewById(R.id.linearAc);
				if (obj.screenType.equals("Goods")) {
					linearItems.setVisibility(View.VISIBLE);
					linearAc.setVisibility(View.GONE);
					TextView txt = (TextView) findViewById(R.id.txtAc);
					TextView auto = (TextView) findViewById(R.id.txtchoosAc);
					CheckBox checkInterest = (CheckBox)findViewById(R.id.CheckBoxInterest);
					checkInterest.setVisibility(View.GONE);
					txt.setVisibility(View.VISIBLE);
					auto.setVisibility(View.VISIBLE);
					tagIndex++;
					scRoll.addView(goodsEntry());
					if(into.equals("edit")){
						//setUpdateData(Custom_adapterVoucher_view.objVxn);
					}
					// linearList.add(goodsEntry());
				} else if (obj.screenType.equals("Transfer")) {
					linearItems.setVisibility(View.GONE);
					linearAc.setVisibility(View.VISIBLE);
					TextView txt = (TextView) findViewById(R.id.txtAc);
					TextView auto = (TextView) findViewById(R.id.txtchoosAc);
					CheckBox checkInterest = (CheckBox)findViewById(R.id.CheckBoxInterest);
					checkInterest.setVisibility(View.VISIBLE);
					txt.setVisibility(View.GONE);
					auto.setVisibility(View.GONE);
					tagIndex++;
					scRoll.addView(Entry());
					// linearList.add(Entry());
				}
				
				
            
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});
		
		
		/*if(into.equals("edit")){
			setdataSpinner(spinner);
		}*/
		
		
	}

	/*private void setdataSpinner(Spinner spinner){
		if(Custom_adapterVoucher_view.objVxn!=null){
			final Object_Voucher_Txn Objtran = Custom_adapterVoucher_view.objVxn;
			//spinnerDataVoucher();
			//((Spinner) spinner).getSelectedView().setEnabled(false);
			spinner.setSelection(Objtran.voucher.id-1);
			spinner.setEnabled(false);
			
			
			//spinnerDataVoucher();
			new CountDownTimer(30000, 1000) {

			     public void onTick(long millisUntilFinished) {
			        // mTextField.setText("seconds remaining: " + millisUntilFinished / 1000);
			     }

			     public void onFinish() {
			    	 setUpdateData(Objtran);
			     }
			  }.start();
		    
		  
		   for (Object_Account object : listAccount) {
			   Log.i("SUSHIL", "party ac  "+object.partyAc);
			   if(object.partyAc){
				   TextView txtAc = (TextView) findViewById(R.id.txtchoosAc);
				   txtAc.setText(object.name);
				   partyAccount.partyAc = true;
		        }else{
		        	setData(object);
		        }
		//  }
			
		}
	}*/
	
	
	
	private void setData(Object_Account Obj) {
        try{
		if (Listvc.get(selectedId).id == 1 || Listvc.get(selectedId).id == 2) {
          if (!partyAc) {
        	  
				for (int i = (clickedTagIndex * 5) + 1, j = 1; i < ((clickedTagIndex * 5) + 1) + 5; i++, j++) {
					switch (j) {
					case 1:
						//Log.i("SUSHIL", "size item "+Obj.itemCategories.get(0).items.get(0).name+" "+j);
						TextView txt = (TextView) findViewById(i);
						txt.setText(Obj.itemCategories.get(0).items.get(0).name);
						break;
					case 2:
						EditText edtQty = (EditText) findViewById(i);
						if(into.equals("edit")){
						if(Obj.itemCategories.get(0).items.get(0).itemTxn.qty!= 0)
					       edtQty.setText(String.valueOf(Obj.itemCategories.get(0).items.get(0).itemTxn.qty));
						}
						break;
					case 3:
						TextView txtUnit = (TextView) findViewById(i);
						txtUnit.setText(Obj.itemCategories.get(0).items.get(0).unit.name);
						break;
					case 4:
						EditText edtPrice = (EditText) findViewById(i);
						if (Listvc.get(selectedId).id == 1)
							edtPrice.setText(Obj.itemCategories.get(0).items
									.get(0).purchasePrice + "");
						else
							edtPrice.setText(Obj.itemCategories.get(0).items
									.get(0).salePrice + "");
						break;
					case 5:
						EditText edtAmount = (EditText) findViewById(i);
						break;
					default:
						break;
					}
				}
			}
		} else if (Listvc.get(selectedId).id == 3
				|| Listvc.get(selectedId).id == 4
				|| Listvc.get(selectedId).id == 5) {
			for (int i = (clickedTagIndex * 5) + 1, j = 1; i < ((clickedTagIndex * 5) + 1) + 5; i++, j++) {
				//Log.i("JASPAL", "value of clickedTagIndex :" + clickedTagIndex);
				//Log.i("JASPAL", "value of i :" + i);
				switch (j) {
				case 1:
					AutoCompleteTextView edtDRCR = (AutoCompleteTextView) findViewById(i);
					break;
				case 2:
					TextView txtAc = (TextView) findViewById(i);
					txtAc.setText(Obj.name);
					break;
				case 3:
					EditText edtCredit = (EditText) findViewById(i);
					break;
				case 4:
					EditText edtDebit = (EditText) findViewById(i);
					break;
				case 5:
					EditText edtShortnoti = (EditText) findViewById(i);
					break;
				default:
					break;
				}
			}
		}
        }catch(Exception e){
        	e.printStackTrace();
        }
	}
	
	
	/*private void setDataUpdate(Object_Account Obj) {
        try{
		if (Listvc.get(selectedId).id == 1 || Listvc.get(selectedId).id == 2) {
          if (!partyAc) {
       for(int k =0;k<Obj.itemCategories.size();k++){
        		 for(int t = 0;t<Obj.itemCategories.get(k).items.size();t++){
        			 if(t!=0){
        	        		Additem(null);
        	        	}
        	  for (int i = ((count-1) * 5) + 1, j = 1; i < (((count-1) * 5) + 1) + 5; i++, j++) {
					switch (j) {
					case 1:
						//Log.i("SUSHIL", "size item "+Obj.itemCategories.get(0).items.get(0).name+" "+j);
						TextView txt = (TextView) findViewById(i);
						txt.setText(Obj.itemCategories.get(k).items.get(t).name);
						break;
					case 2:
						EditText edtQty = (EditText) findViewById(i);
						if(into.equals("edit")){
						if(Obj.itemCategories.get(k).items.get(t).itemTxn.qty!= 0)
					       edtQty.setText(String.valueOf(Obj.itemCategories.get(k).items.get(t).itemTxn.qty));
						}
						break;
					case 3:
						TextView txtUnit = (TextView) findViewById(i);
						txtUnit.setText(Obj.itemCategories.get(k).items.get(t).unit.name);
						break;
					case 4:
						EditText edtPrice = (EditText) findViewById(i);
						if (Listvc.get(selectedId).id == 1)
							edtPrice.setText(Obj.itemCategories.get(k).items
									.get(t).purchasePrice + "");
						else
							edtPrice.setText(Obj.itemCategories.get(k).items
									.get(t).salePrice + "");
						break;
					case 5:
						EditText edtAmount = (EditText) findViewById(i);
						break;
					default:
						break;
					}
				    }
        	   
        	}
        	 }
			}
		} else if (Listvc.get(selectedId).id == 3
				|| Listvc.get(selectedId).id == 4
				|| Listvc.get(selectedId).id == 5) {
			for (int i = (clickedTagIndex * 5) + 1, j = 1; i < ((clickedTagIndex * 5) + 1) + 5; i++, j++) {
				//Log.i("JASPAL", "value of clickedTagIndex :" + clickedTagIndex);
				//Log.i("JASPAL", "value of i :" + i);
				switch (j) {
				case 1:
					AutoCompleteTextView edtDRCR = (AutoCompleteTextView) findViewById(i);
					break;
				case 2:
					TextView txtAc = (TextView) findViewById(i);
					txtAc.setText(Obj.name);
					break;
				case 3:
					EditText edtCredit = (EditText) findViewById(i);
					break;
				case 4:
					EditText edtDebit = (EditText) findViewById(i);
					break;
				case 5:
					EditText edtShortnoti = (EditText) findViewById(i);
					break;
				default:
					break;
				}
			}
		}
        }catch(Exception e){
        	e.printStackTrace();
        }
	}*/
	private int getItemsCount(ArrayList<Object_Account> accounts)
	{
		int size = 0;
		
		for (Object_Account account : accounts) {
			if(account.itemCategories != null)
			{
				for (Object_Item_Category itemCat : account.itemCategories) {
					if(itemCat != null)
					{
						size += itemCat.items.size();
					}
				}
			}
		}
		
		
		return size;
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

		Object_AppConfig config = new Object_AppConfig(this);
		Globals.setLocale(config.getLocale(), this);
		
		if (Custom_adapterGrid.object != null) {
			setData(Custom_adapterGrid.object);
			if (partyAc) {
				TextView txtAc = (TextView) findViewById(R.id.txtchoosAc);
				txtAc.setText(Custom_adapterGrid.object.name);
				partyAccount = Custom_adapterGrid.object;
				//partyAccount.partyAc = true;
				Custom_adapterGrid.object = null;
				partyAc = false;
				//Log.i("JASPAL", "iT IS A PARTY ac");
			} 
			else
			{
				//Log.i("JASPAL", "Not a party aCcount");
				if (clickedTagIndex > -1 && (objVchTran.accounts.size() == 0 || objVchTran.accounts.size() == clickedTagIndex)) 
				{

					//Log.i("JASPAL", "Either new Account of first Account!");
					// add first item in AL
					objVchTran.accounts.add(Custom_adapterGrid.object);

					Object_Ac_Icat_Itm_map tempMap = new Object_Ac_Icat_Itm_map();
					tempMap.accountIndex = objVchTran.accounts.size() - 1;
					mapAll.add(tempMap);

					//Log.i("JASPAL", "mapall size in onResume:"+mapAll.size());
					Custom_adapterGrid.object = null;
					clickedTagIndex = -1;
				}
				else if (clickedTagIndex > -1 && objVchTran.accounts.size() > 0) 
				{

					//Log.i("JASPAL", "Editing Account");
					// edit child already in AL
					objVchTran.accounts.remove(clickedTagIndex);
					objVchTran.accounts.add(clickedTagIndex,
							Custom_adapterGrid.object);

					Custom_adapterGrid.object = null;
					clickedTagIndex = -1;
				}

			}
		}
		if (Custom_adapterGrid_Items.object != null) {
			setData(Custom_adapterGrid_Items.object);
			
			//objVchTran.accounts.size()
			if (clickedTagIndex > -1 && (getItemsCount(objVchTran.accounts) == 0)) 
			{
				//Log.i("JASPAL", "fIRST iTEM CHOSEN WITH aCCOUNT");
				// add first item in AL
				objVchTran.accounts.add(Custom_adapterGrid_Items.object);

				Object_Ac_Icat_Itm_map tempMap = new Object_Ac_Icat_Itm_map();
				tempMap.accountIndex = objVchTran.accounts.size() - 1;
				tempMap.itemCatIndex = 0;
				tempMap.itemIndex = 0;
				mapAll.add(tempMap);

				Custom_adapterGrid_Items.object = null;
				clickedTagIndex = -1;

			} 
			else if (clickedTagIndex > -1 && getItemsCount(objVchTran.accounts) == clickedTagIndex) 
			{
				//Log.i("JASPAL", "nEW Item but not First");
				// check if account already exists.
					// if exists then check if it contains itemCategory then add
					// item in it.
				// else add new itemCategory with item in it
				// else create account with itemCategory in it and then item in
				// itemCategory

				Object_Account selectedAccountObj = Custom_adapterGrid_Items.object;
				boolean accountExists = false;
				int accountFoundOnIndex = -1;

				for (Object_Account accnt : objVchTran.accounts) {
					accountFoundOnIndex++;
					if (accnt.id == selectedAccountObj.id) {
						accountExists = true;

						break;
					}
				}
				if (accountExists) {
					// Account already exists
					Object_Account accountFound = objVchTran.accounts
							.get(accountFoundOnIndex);
					boolean itemCatExists = false;
					int itemCatFoundOnIndex = -1;

					for (Object_Item_Category itemCat : accountFound.itemCategories) {
						itemCatFoundOnIndex++;
						if (itemCat.id == selectedAccountObj.itemCategories
								.get(0).id) {
							itemCatExists = true;

							break;
						}
					}
					if (itemCatExists) {
						// itemCategory found in account
						accountFound.itemCategories.get(itemCatFoundOnIndex).items
								.add(selectedAccountObj.itemCategories.get(0).items
										.get(0));

						Object_Ac_Icat_Itm_map tempMap = new Object_Ac_Icat_Itm_map();
						tempMap.accountIndex = accountFoundOnIndex;
						tempMap.itemCatIndex = itemCatFoundOnIndex;
						tempMap.itemIndex = accountFound.itemCategories
								.get(itemCatFoundOnIndex).items.size() - 1;
						mapAll.add(tempMap);
					} else {
						// item category not found
						accountFound.itemCategories
								.add(selectedAccountObj.itemCategories.get(0));

						Object_Ac_Icat_Itm_map tempMap = new Object_Ac_Icat_Itm_map();
						tempMap.accountIndex = accountFoundOnIndex;
						tempMap.itemCatIndex = accountFound.itemCategories
								.size() - 1;
						tempMap.itemIndex = 0;
						mapAll.add(tempMap);
					}
				} else {
					// Account Not found
					objVchTran.accounts.add(Custom_adapterGrid_Items.object);

					Object_Ac_Icat_Itm_map tempMap = new Object_Ac_Icat_Itm_map();
					tempMap.accountIndex = objVchTran.accounts.size() - 1;
					tempMap.itemCatIndex = 0;
					tempMap.itemIndex = 0;
					mapAll.add(tempMap);
				}

				Custom_adapterGrid_Items.object = null;
				clickedTagIndex = -1;
			} 
			else if (clickedTagIndex > -1 && getItemsCount(objVchTran.accounts) > 0) 
			{
				//Log.i("JASPAL", "Clicked on Item 4 editing");
				// edit child already in AL

				Object_Account accountTemp = objVchTran.accounts.get(clickedTagIndex);
				Object_Account accountSelected = Custom_adapterGrid_Items.object;
				int selectItemCatId = accountSelected.itemCategories.get(0).id;
				boolean foundItemCat = false;

				for (Object_Item_Category itemCat : accountTemp.itemCategories) {
					if (itemCat.id == selectItemCatId) {
						// ItemCategory already exits
						foundItemCat = true;

						int tempIndexOfItemCat = accountTemp.itemCategories
								.indexOf(itemCat);
						Object_Item_Category tempItemCat = itemCat;
						tempItemCat.items.add(accountSelected.itemCategories
								.get(0).items.get(0));

						accountTemp.itemCategories.remove(tempIndexOfItemCat);
						accountTemp.itemCategories.add(tempIndexOfItemCat,
								tempItemCat);

						break;
					}
				}
				if (foundItemCat) {
					// ItemCategory found already and done
					objVchTran.accounts.remove(clickedTagIndex);
					objVchTran.accounts.add(clickedTagIndex, accountTemp);
				} else {
					objVchTran.accounts.add(accountSelected);
				}

				Custom_adapterGrid_Items.object = null;
				clickedTagIndex = -1;
			}

		}
		
	/*if(Custom_adapterVoucher_view.objVxn!=null){
		editable = true;
		clickedTagIndex = 0;
		Spinner spinner = (Spinner) findViewById(R.id.spinnerVoucher);
		final Object_Voucher_Txn Objtran = Custom_adapterVoucher_view.objVxn;
		spinnerDataVoucher();
		//((Spinner) spinner).getSelectedView().setEnabled(false);
		spinner.setSelection(Objtran.voucher.id-1);
		spinner.setEnabled(false);
		//spinnerDataVoucher();
		new CountDownTimer(30000, 1000) {

		     public void onTick(long millisUntilFinished) {
		        // mTextField.setText("seconds remaining: " + millisUntilFinished / 1000);
		     }

		     public void onFinish() {
		    	 setUpdateData(Objtran);
		     }
		  }.start();
	    
	  
	   for (Object_Account object : listAccount) {
		   Log.i("SUSHIL", "party ac  "+object.partyAc);
		   if(object.partyAc){
			   TextView txtAc = (TextView) findViewById(R.id.txtchoosAc);
			   txtAc.setText(object.name);
			   partyAccount.partyAc = true;
	        }else{
	        	setData(object);
	        }
	//  }

		 
	}*/

	}

	private void setUpdateData(Object_Voucher_Txn Objtran){
		int clicked = 0;
		ArrayList<Object_Account> listAccount =  Objtran.accounts;
		   for (Object_Account object : listAccount) {
			 if(object.itemCategories.size()==0){
				 TextView txtAc = (TextView) findViewById(R.id.txtchoosAc);
				 txtAc.setText(object.name);
				 partyAccount = object;
				 partyAc = false;
				 //
			 }else{
				 //set(object);
				 }
				
			    
		    }
	}
	
	
	private LinearLayout goodsEntry() {
		//Log.i("SUSHIL", "selected id " + idlay);
		Point size = Globals.getScreenSize(this);
		double price = 0;
		LinearLayout BaseLinear = new LinearLayout(this);
		BaseLinear.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT));
		BaseLinear.setPadding(10, 3, 10, 0);
		BaseLinear.setOrientation(LinearLayout.HORIZONTAL);
		// EditText edt

		final TextView edtItem = new TextView(this);
		edtItem.setLayoutParams(new LayoutParams((size.x * 26) / 100,
				LayoutParams.WRAP_CONTENT));
		edtItem.setHint(R.string.Item);
		edtItem.setId(idlay);
		//Log.i("JASPAL", "setting tagIndex to item button : " + tagIndex);
		edtItem.setTag(tagIndex);
		Log.i("SUSHIL","id value"+idlay);
		//Log.i("JASPAL", "get tagIndex to item button after setting it : "+ edtItem.getTag());

		idlay++;
		edtItem.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				clickedTagIndex = (Integer) v.getTag();
				//Log.i("JASPAL", "getting clickedTagIndex value from onclick: "+ clickedTagIndex);
				Intent i = new Intent(Activity_transaction.this,
						Activity_accountChooser.class);
				i.putExtra("Type", "Items");
				startActivity(i);
			}
		});
		// final Object_SalesPurchases obj = ListItems.get();
		/*
		 * ArrayAdapter adapter = new
		 * ArrayAdapter(this,android.R.layout.simple_list_item_1,itemname);
		 * edtItem.setAdapter(adapter); edtItem.setThreshold(1);
		 */
		BaseLinear.addView(edtItem);
		final EditText edtQty = new EditText(this);
		edtQty.setLayoutParams(new LayoutParams((size.x * 17) / 100,
				LayoutParams.WRAP_CONTENT));
		edtQty.setInputType(InputType.TYPE_CLASS_TEXT
				| InputType.TYPE_CLASS_NUMBER);
		edtQty.setHint(R.string.Qty);
		edtQty.setText(0 + "");
		edtQty.setId(idlay);

		idlay++;
		BaseLinear.addView(edtQty);
		final TextView edtUnit = new TextView(this);
		edtUnit.setLayoutParams(new LayoutParams((size.x * 18) / 100,
				LayoutParams.WRAP_CONTENT));
		edtUnit.setHint(R.string.Unit);
		edtUnit.setId(idlay);
		idlay++;
		/*
		 * final ArrayList<String> untname = new ArrayList<String>();
		 * ArrayList<AA_Object_Unit> unitList = new ArrayList<AA_Object_Unit>();
		 * AA_DBHandler_Unit Dbitems = new
		 * AA_DBHandler_Unit(Activity_transaction.this); unitList =
		 * Dbitems.getAllUnits(); for(int i = 0;i<unitList.size();i++){
		 * untname.clear(); AA_Object_Unit obj = unitList.get(i);
		 * untname.add(obj.name); }
		 */

		/*
		 * ArrayAdapter adapterUnit = new
		 * ArrayAdapter(this,android.R.layout.simple_list_item_1,untname);
		 * edtUnit.setAdapter(adapterUnit); edtUnit.setThreshold(1);
		 */
		/*
		 * edtUnit.addTextChangedListener(new TextWatcher() {
		 * 
		 * @Override public void onTextChanged(CharSequence s, int start, int
		 * before, int count) { // TODO Auto-generated method stub
		 * 
		 * }
		 * 
		 * @Override public void beforeTextChanged(CharSequence s, int start,
		 * int count, int after) { // TODO Auto-generated method stub
		 * 
		 * }
		 * 
		 * @Override public void afterTextChanged(Editable s) { String text =
		 * s.toString();
		 * 
		 * 
		 * } });
		 */
		BaseLinear.addView(edtUnit);
		final EditText edtprice = new EditText(this);
		edtprice.setLayoutParams(new LayoutParams((size.x * 18) / 100,
				LayoutParams.WRAP_CONTENT));
		edtprice.setInputType(InputType.TYPE_CLASS_TEXT
				| InputType.TYPE_CLASS_NUMBER);
		edtprice.setHint(R.string.Price);
		edtprice.setId(idlay);

		/*
		 * String itemName = edtItem.getText().toString(); DBHandler_Items items
		 * = new DBHandler_Items(this); ArrayList<Object_Items> item =
		 * items.getItems(itemName); Object_Items selectedItem = item.get(0);
		 * edtprice.setText(selectedItem.purchaseprice+"");
		 */
		idlay++;
		BaseLinear.addView(edtprice);
		final EditText edtAmount = new EditText(this);
		edtAmount.setLayoutParams(new LayoutParams((size.x * 20) / 100,
				LayoutParams.WRAP_CONTENT));
		edtAmount.setInputType(InputType.TYPE_CLASS_TEXT
				| InputType.TYPE_CLASS_NUMBER);
		edtAmount.setHint(R.string.Amount);
		edtAmount.setId(idlay);
		idlay++;
		// Toast.makeText(this,"id is all"+idlay,Toast.LENGTH_SHORT).show();
		
		BaseLinear.addView(edtAmount);
		
		
		
		
		/*
		 * edtItem.setOnClickListener(new OnClickListener() {
		 * 
		 * @Override public void onClick(View v) { // TODO Auto-generated method
		 * stub Intent i = new
		 * Intent(Activity_transaction.this,Activity_accountChooser.class);
		 * i.putExtra("Type","Items"); startActivity(i); } });
		 */
		/*
		 * edtItem.addTextChangedListener(new TextWatcher() {
		 * 
		 * @Override public void onTextChanged(CharSequence s, int start, int
		 * before, int count) { // TODO Auto-generated method stub
		 * 
		 * }
		 * 
		 * @Override public void beforeTextChanged(CharSequence s, int start,
		 * int count, int after) { // TODO Auto-generated method stub
		 * 
		 * }
		 * 
		 * @Override public void afterTextChanged(Editable s) {
		 * ArrayList<AA_Object_Items> itemsList; String text = s.toString();
		 * AA_DBHandler_Items Dbitems = new
		 * AA_DBHandler_Items(Activity_transaction.this); itemsList =
		 * Dbitems.getItems(text); AA_DBHandler_Unit DBunit = new
		 * AA_DBHandler_Unit(Activity_transaction.this); for(int i =
		 * 0;i<itemsList.size();i++){ itemname.clear(); AA_Object_Items obj =
		 * itemsList.get(i); itemname.add(obj.name); Log.i("SUSHIL",
		 * "items name added "+obj.name); ArrayAdapter adapter = new
		 * ArrayAdapter
		 * (Activity_transaction.this,android.R.layout.simple_list_item_1
		 * ,itemname); edtItem.setAdapter(adapter); edtItem.setThreshold(1);
		 * if(Listvc.get(selectedId).def_DR_account.id!=0){
		 * edtprice.setText(obj.purchaseprice+""); }else{
		 * edtprice.setText(obj.saleprice+""); }
		 * 
		 * edtUnit.setText(DBunit.getUnitById(obj.unitid).name);
		 * edtAmount.setText
		 * (Integer.parseInt(edtQty.getText().toString())*Double
		 * .parseDouble(edtprice.getText().toString())+""); }
		 * 
		 * 
		 * 
		 * } });
		 */

		edtQty.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
				// TODO Auto-generated method stub

			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable arg0) {
				try {
					if (!edtQty.getText().toString().trim().isEmpty()) {
						edtAmount.setText(Integer.parseInt(edtQty.getText()
								.toString())
								* Double.parseDouble(edtprice.getText()
										.toString()) + "");
					} else {
						edtAmount.setText(0
								* Double.parseDouble(edtprice.getText()
										.toString()) + "");
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		});
		edtprice.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
				// TODO Auto-generated method stub

			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable arg0) {
				if (!edtQty.getText().toString().trim().isEmpty()) {
					edtAmount.setText(Integer.parseInt(edtQty.getText()
							.toString())
							* Double.parseDouble(edtprice.getText().toString())
							+ "");
				} else {
					edtAmount.setText(0
							* Double.parseDouble(edtprice.getText().toString())
							+ "");
				}
			}
		});

		
		
		
		return BaseLinear;

	}

	private LinearLayout Entry() {
		Point size = Globals.getScreenSize(this);
		LinearLayout BaseLinear = new LinearLayout(this);
		BaseLinear.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT));
		BaseLinear.setOrientation(LinearLayout.HORIZONTAL);
		BaseLinear.setPadding(10, 3, 10, 0);
		final AutoCompleteTextView edtDRCR = new AutoCompleteTextView(this);
		edtDRCR.setLayoutParams(new LayoutParams((size.x * 14) / 100,
				LayoutParams.WRAP_CONTENT));
		edtDRCR.setHint(R.string.CRDR);
		edtDRCR.setId(idlay);
		idlay++;
		String[] type = { "Credit", "Debit" };
		ArrayAdapter adapterCrDr = new ArrayAdapter(this,android.R.layout.simple_list_item_1, type);
		edtDRCR.setAdapter(adapterCrDr);
		edtDRCR.setThreshold(1);
		edtDRCR.showDropDown();
		BaseLinear.addView(edtDRCR);
		final TextView txtAc = new TextView(this);
		txtAc.setLayoutParams(new LayoutParams((size.x * 27) / 100,
				LayoutParams.WRAP_CONTENT));
		txtAc.setHint(R.string.Ac);
		txtAc.setId(idlay);
		txtAc.setTag(tagIndex);
		//Log.i("JASPAL", "tagIndex set value is :" + tagIndex);
		idlay++;
		txtAc.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				clickedTagIndex = (Integer) v.getTag();
				Intent i = new Intent(Activity_transaction.this,
						Activity_accountChooser.class);
				i.putExtra("Type", "Accounts");
				startActivity(i);
			}
		});
		// final ArrayList<String> acName = new ArrayList<String>();
		/*
		 * edtAc.addTextChangedListener(new TextWatcher() {
		 * 
		 * @Override public void onTextChanged(CharSequence s, int start, int
		 * before, int count) { // TODO Auto-generated method stub
		 * 
		 * }
		 * 
		 * @Override public void beforeTextChanged(CharSequence s, int start,
		 * int count, int after) { // TODO Auto-generated method stub
		 * 
		 * }
		 * 
		 * @Override public void afterTextChanged(Editable s) {
		 * ArrayList<AA_Object_Accounts> acList = new
		 * ArrayList<AA_Object_Accounts>(); String text = s.toString();
		 * AA_DBHandler_Accounts Dbitems = new
		 * AA_DBHandler_Accounts(Activity_transaction.this); acList =
		 * Dbitems.getAccounts(text);
		 * 
		 * for(int i = 0;i<acList.size();i++){ acName.clear();
		 * AA_Object_Accounts obj = acList.get(i); acName.add(obj.name); }
		 * ArrayAdapter adapterAc = new
		 * ArrayAdapter(Activity_transaction.this,android
		 * .R.layout.simple_list_item_1,acName); edtAc.setAdapter(adapterAc);
		 * edtAc.setThreshold(1);
		 * 
		 * } });
		 */

		BaseLinear.addView(txtAc);
		final EditText edtCredit = new EditText(this);
		edtCredit.setLayoutParams(new LayoutParams((size.x * 15) / 100,
				LayoutParams.WRAP_CONTENT));
		edtCredit.setInputType(InputType.TYPE_CLASS_TEXT
				| InputType.TYPE_CLASS_NUMBER);
		edtCredit.setHint(R.string.Credit);
		edtCredit.setId(idlay);
		idlay++;
		BaseLinear.addView(edtCredit);
		final EditText edtDebit = new EditText(this);
		edtDebit.setLayoutParams(new LayoutParams((size.x * 15) / 100,
				LayoutParams.WRAP_CONTENT));
		edtDebit.setInputType(InputType.TYPE_CLASS_TEXT
				| InputType.TYPE_CLASS_NUMBER);
		edtDebit.setHint(R.string.Debit);
		edtDebit.setId(idlay);
		idlay++;
		BaseLinear.addView(edtDebit);
		EditText edtshortNaration = new EditText(this);
		edtshortNaration.setLayoutParams(new LayoutParams((size.x * 26) / 100,
				LayoutParams.WRAP_CONTENT));
		edtshortNaration.setHint(R.string.Narration);
		edtshortNaration.setId(idlay);
		idlay++;
		BaseLinear.addView(edtshortNaration);
		// Toast.makeText(this,"id is all"+idlay,Toast.LENGTH_SHORT).show();

		edtDRCR.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				String tranType = edtDRCR.getText().toString();
				if (!tranType.trim().isEmpty()) {
					if (tranType.equals("credit") || tranType.equals("Credit") || tranType.equals("cr") || tranType.equals("Cr") ) {
						edtDebit.setEnabled(false);
						edtCredit.setEnabled(true);
					} else {
						edtDebit.setEnabled(true);
						edtCredit.setEnabled(false);

					}
				}

			}
		});

		return BaseLinear;

	}

	public void Additem(View v) {

		LinearLayout scRoll = (LinearLayout) findViewById(R.id.scRoll);

		//Log.i("JASPAL", "Map Size is from AddItem : "+mapAll.size());
		//Log.i("JASPAL", "scRoll Size is from AddItem : "+scRoll.getChildCount());
		if (scRoll.getChildCount() == mapAll.size()) 
		{
			tagIndex++;
			if (Listvc.get(selectedId).screenType.equals("Goods"))
			{
				
                scRoll.addView(goodsEntry());
				// linearList.add(goodsEntry());
			}
			else if (Listvc.get(selectedId).screenType.equals("Transfer"))
			{
				// obj.ScreenType.equals("Tranfer"
				scRoll.addView(Entry());
				// linearList.add(Entry());
			}
		}
		else
		{
			Toast.makeText(this, R.string.alreadyEmptyItemPresent,Toast.LENGTH_SHORT).show();
		}
	}

	public void Deleteitem(View v) {
		LinearLayout scRoll = (LinearLayout) findViewById(R.id.scRoll);
		// View v1 = scRoll.getFocusedChild();

		if (scRoll.getChildCount() != 0) 
		{
			if (scRoll.getChildCount() != 1) 
			{
				scRoll.removeViewAt(scRoll.getChildCount() - 1);
				Log.i("JASPAL", "map size in at start "+mapAll.size());
				if (tagIndex == mapAll.size()) 
				{
					Log.i("JASPAL", "If true state "+mapAll.size());
					
                 }else if(tagIndex+1 == mapAll.size()){
                	 
                	 Log.i("JASPAL", "If false state "+mapAll.size()); 
					if (Listvc.get(selectedId).id == 1 || Listvc.get(selectedId).id == 2) 
					{
						Object_Ac_Icat_Itm_map tempMapObj = mapAll.get(tagIndex);
						if (objVchTran.accounts.get(tempMapObj.accountIndex).itemCategories.get(tempMapObj.itemCatIndex).items.size() <= 1) 
						{
							if (objVchTran.accounts.get(tempMapObj.accountIndex).itemCategories.size() <= 1) 
							{
								// delete complete account
								objVchTran.accounts.remove(tempMapObj.accountIndex);
							}
							else 
							{
								// delete specific category
								objVchTran.accounts.get(tempMapObj.accountIndex).itemCategories.remove(tempMapObj.itemCatIndex);
							}
						} 
						else 
						{
							// delete specific item only
							objVchTran.accounts.get(tempMapObj.accountIndex).itemCategories.get(tempMapObj.itemCatIndex).items.remove(tempMapObj.itemIndex);
						}
						mapAll.remove(tagIndex);
						Log.i("JASPAL", "Map All size is "+mapAll.size());

						// idlay = idlay-5;
					} 
					else if (Listvc.get(selectedId).id == 3 || Listvc.get(selectedId).id == 4 || Listvc.get(selectedId).id == 5)
					{
						objVchTran.accounts.remove(tagIndex);
						mapAll.remove(tagIndex);
					}
				}
			}
			idlay = idlay - 5;

		} else 
		{
			idlay = 1;
			Toast.makeText(this, R.string.DeleteItemsg, Toast.LENGTH_SHORT).show();
		}
		tagIndex--;
	}

	public void onsave(View v) {
		// Log.i("SUSHIL",
		// "this is sales voucher"+Listvc.get(selectedId).def_cr_accnt_id);
		if (Listvc.get(selectedId).id == 1){ 
			if (partyAccount != null) {
				try {
					//
					
					boolean succes = false;
					int id = 0;
					// this is purchases voucher
					double Amount = 0;
					LinearLayout scRoll = (LinearLayout) findViewById(R.id.scRoll);
					Object_Voucher_Master objvc = Listvc.get(selectedId);
					objVchTran.voucher = objvc;
					// YYYY-MM-DD HH:MM:SS
					objVchTran.daytime = txtVoucherDate.getText().toString()
							+ " " + Globals.getcurrentTime();
					/*
					 * ArrayList<Object_Account> listAccounts =
					 * objVchTran.accounts; Object_Account objAccounts =
					 * listAccounts.get(0);
					 */
					Object_Account_Txn objTxnAccounts = new Object_Account_Txn();

					if (mapAll.size() == scRoll.getChildCount()) {
						  
						for (int i = 0; i < scRoll.getChildCount(); i++) {
							Object_Ac_Icat_Itm_map objMap = mapAll.get(i);
							Object_Account Account = objVchTran.accounts
									.get(objMap.accountIndex);
							Object_Item_Category Itemcat = Account.itemCategories
									.get(objMap.itemCatIndex);
							Object_Item objItem = Itemcat.items
									.get(objMap.itemIndex);
							Object_Item_Txn itemTxn = new Object_Item_Txn();
							for (int j = 1; j <= 5; j++) {
								id++;
								if (j == 1) {
									TextView textView = (TextView) findViewById(id);
									itemName = textView.getText().toString();
									// obj.Item = itemName;
									if (!itemName.trim().isEmpty()) {

										/*
										 * ArrayList<AA_Object_Items> listItems
										 * = DBitems.getItems(itemName);
										 * objItems = listItems.get(0);
										 * obj.itemId = objItems.id;
										 */
										succes = true;
									} else {
										succes = false;
										Toast.makeText(this,
												R.string.NameItemToast,
												Toast.LENGTH_SHORT).show();
										break;

									}

								}
								if (j == 2) {
									EditText edt = (EditText) findViewById(id);
									String qyt = edt.getText().toString();
									int Qyt = Integer.parseInt(qyt);
									if (Qyt != 0) {
										// obj.qty = Qyt;
										itemTxn.qty = Qyt;
										succes = true;
									} else {
										succes = false;
										Toast.makeText(this, R.string.QytToast,
												Toast.LENGTH_SHORT).show();
										break;
									}
								}
								if (j == 3) {
									TextView textView = (TextView) findViewById(id);
									String unitName = textView.getText()
											.toString();
									if (!unitName.trim().isEmpty()) {
										// obj.unitId = objItems.unitid;
										succes = true;
									} else {
										succes = false;
										Toast.makeText(this,
												R.string.UnitToast,
												Toast.LENGTH_SHORT).show();
										break;
									}
								}
								if (j == 4) {
									EditText edtprice = (EditText) findViewById(id);
									String price = edtprice.getText()
											.toString();
									itemTxn.price = Double.parseDouble(price);
									succes = true;
								}
								if (j == 5) {
									EditText edtAmount = (EditText) findViewById(id);
									if (itemTxn.qty != 0 && itemTxn.price != 0) {
										if (Double.parseDouble(edtAmount
												.getText().toString()) == itemTxn.qty
												* itemTxn.price) {
											itemTxn.amount = Double
													.parseDouble(edtAmount
															.getText()
															.toString());
											Amount = Amount
													+ Double.parseDouble(edtAmount
															.getText()
															.toString());
											succes = true;
										} else {
											succes = false;
											Toast.makeText(this,
													R.string.AmountToast,
													Toast.LENGTH_SHORT).show();
											break;

										}
									}
								}

								// Object_Items items =
								// DBitems.getItems(itemName);
							}

							objItem.itemTxn = itemTxn;
							objVchTran.accounts.get(objMap.accountIndex).itemCategories
									.get(objMap.itemCatIndex).items
									.remove(objMap.itemIndex);
							objVchTran.accounts.get(objMap.accountIndex).itemCategories
									.get(objMap.itemCatIndex).items.add(
									objMap.itemIndex, objItem);
						}
						// objTran.itemTxn = ListItems;
						
						Log.i("JASPAL", "Boolean check "+succes);
				if(succes){
						Object_Txn_Master txnTypeDebit = new Object_Txn_Master();
						txnTypeDebit.id = 2;// debit type
						objTxnAccounts.txnType = txnTypeDebit;
						objTxnAccounts.amount = Amount;
						objVchTran.accounts.get(0).accountTxn = objTxnAccounts;
						Object_Account_Txn ObjTxnparty = new Object_Account_Txn();
						ObjTxnparty.amount = Amount;
						Object_Txn_Master txnTypeCredit = new Object_Txn_Master();
						txnTypeCredit.id = 1;// credit type
						ObjTxnparty.txnType = txnTypeCredit;
						partyAccount.accountTxn = ObjTxnparty;
						objVchTran.accounts.add(partyAccount);
						if(partyAccount.grpId == 5){
							for (int i =0;i<objVchTran.accounts.size();i++) {
								objVchTran.accounts.get(i).accountTxn.isCash = 1;
							}
						}else{
							for (int i =0;i<objVchTran.accounts.size();i++) {
								objVchTran.accounts.get(i).accountTxn.isCash = 0;
							}
						}
                     /* if(succes){
						DBHandler_Txn DBtran = new DBHandler_Txn(this);
						boolean save = DBtran.createNewTxn(objVchTran);
                      }*/
						
							
							//Toast.makeText(this, "saved", Toast.LENGTH_SHORT).show();
							Intent i = new
							 Intent(this,Activity_transaction_Receipt.class);
							  Object_Voucher_Master obj =
							  Listvc.get(selectedId);
							  i.putExtra("vcName",obj.name); i.putExtra("date",
							  txtVoucherDate.getText().toString());
							  startActivity(i);
							 

						} else {
							/*
							 * ListAc.clear(); ListItems.clear();
							 */
						}
					}
				  
				} catch (Exception e) {
					Toast.makeText(this, R.string.TransactionToast,
							Toast.LENGTH_SHORT).show();
					e.printStackTrace();
				}
			  }else{
				  Toast.makeText(this, " First main account add ", Toast.LENGTH_SHORT).show();
			  }
			} 
			else if (Listvc.get(selectedId).id == 2) {
				if (partyAccount != null) {
					try {
						//
						
						Log.i("SUSHIL", "This is sales voucher");
						int id = 0;
						boolean succes = false;
						// this is purchases voucher
						double Amount = 0;
						LinearLayout scRoll = (LinearLayout) findViewById(R.id.scRoll);
						Object_Voucher_Master objvc = Listvc.get(selectedId);
						objVchTran.voucher = objvc;
						// YYYY-MM-DD HH:MM:SS
						objVchTran.daytime = txtVoucherDate.getText()
								.toString() + " " + Globals.getcurrentTime();
						/*
						 * ArrayList<Object_Account> listAccounts =
						 * objVchTran.accounts; Object_Account objAccounts =
						 * listAccounts.get(0);
						 */
				if (mapAll.size() == scRoll.getChildCount()) {	
						Object_Account_Txn objTxnAccounts = new Object_Account_Txn();
						for (int i = 0; i < scRoll.getChildCount(); i++) {
							Object_Ac_Icat_Itm_map objMap = mapAll.get(i);
							Object_Account Account = objVchTran.accounts
									.get(objMap.accountIndex);
							Object_Item_Category Itemcat = Account.itemCategories
									.get(objMap.itemCatIndex);
							Object_Item objItem = Itemcat.items
									.get(objMap.itemIndex);
							Object_Item_Txn itemTxn = new Object_Item_Txn();
							for (int j = 1; j <= 5; j++) {
								id++;
								if (j == 1) {
									TextView textView = (TextView) findViewById(id);
									itemName = textView.getText().toString();

									// obj.Item = itemName;
									if (!itemName.trim().isEmpty()) {
										succes = true;
									} else {
										succes = false;
										Toast.makeText(this,
												R.string.NameItemToast,
												Toast.LENGTH_SHORT).show();
										break;
									}

								}
								if (j == 2) {
									EditText edt = (EditText) findViewById(id);
									String qyt = edt.getText().toString();
									int Qyt = Integer.parseInt(qyt);
									if (Qyt != 0) {
										Log.i("SUSHIL","SUSIl qyt remaining "+objItem.qtyRemaining);
										if (objItem.qtyRemaining > Qyt) {
											itemTxn.qty = Qyt;
											succes = true;
										} else {
											succes = false;
											Toast.makeText(this,
													"Not enough of this item",
													Toast.LENGTH_SHORT).show();
											break;
										}

									} else {
										succes = false;
			 							Toast.makeText(this, R.string.QytToast,
												Toast.LENGTH_SHORT).show();
										break;
									}
								}
								if (j == 3) {
									TextView textView = (TextView) findViewById(id);
									String unitName = textView.getText()
											.toString();
									if (!unitName.trim().isEmpty()) {
										succes = true;
									} else {
										succes = false;
										Toast.makeText(this,
												R.string.UnitToast,
												Toast.LENGTH_SHORT).show();
										break;
									}
								}
								if (j == 4) {
									EditText edtprice = (EditText) findViewById(id);
									String price = edtprice.getText()
											.toString();
									itemTxn.price = Double.parseDouble(price);
									if (itemTxn.price < objItem.mrp
											&& itemTxn.price > objItem.minSalePrice) {
										succes = true;
									} else {
										succes = false;
										Toast.makeText(this,
												R.string.Itemtoast,
												Toast.LENGTH_SHORT).show();
										break;
									}

								}
								if (j == 5) {
									EditText edtAmount = (EditText) findViewById(id);
									if (itemTxn.qty != 0 && itemTxn.price != 0) {
										if (Double.parseDouble(edtAmount
												.getText().toString()) == itemTxn.qty
												* itemTxn.price) {
											itemTxn.amount = Double
													.parseDouble(edtAmount
															.getText()
															.toString());
											Amount = Amount
													+ Double.parseDouble(edtAmount
															.getText()
															.toString());
											succes = true;
										} else {
											succes = false;
											Toast.makeText(this,
													R.string.AmountToast,
													Toast.LENGTH_SHORT).show();
											break;
										}
									}
								}

								// Object_Items items =
								// DBitems.getItems(itemName);
							}
							objItem.itemTxn = itemTxn;
							objVchTran.accounts.get(objMap.accountIndex).itemCategories
									.get(objMap.itemCatIndex).items
									.remove(objMap.itemIndex);
							objVchTran.accounts.get(objMap.accountIndex).itemCategories
									.get(objMap.itemCatIndex).items.add(
									objMap.itemIndex, objItem);
						}
						if (succes) {
						Object_Txn_Master txnTypeDebit = new Object_Txn_Master();
						txnTypeDebit.id = 1;// debit type
						objTxnAccounts.txnType = txnTypeDebit;
						objTxnAccounts.amount = Amount;
						objVchTran.accounts.get(0).accountTxn = objTxnAccounts;
						Object_Account_Txn ObjTxnparty = new Object_Account_Txn();
						ObjTxnparty.amount = Amount;
						Object_Txn_Master txnTypeCredit = new Object_Txn_Master();
						txnTypeCredit.id = 2;// credit type
						ObjTxnparty.txnType = txnTypeCredit;
						partyAccount.accountTxn = ObjTxnparty;
						objVchTran.accounts.add(partyAccount);
						if(partyAccount.grpId == 5){
							for (int i =0;i<objVchTran.accounts.size();i++) {
								objVchTran.accounts.get(i).accountTxn.isCash = 1;
							}
						}else{
							for (int i =0;i<objVchTran.accounts.size();i++) {
								objVchTran.accounts.get(i).accountTxn.isCash = 0;
							}
						}
						
						
						/*DBHandler_Txn DBtran = new DBHandler_Txn(this);
						boolean save = DBtran.createNewTxn(objVchTran);*/
						
							Intent i = new
									 Intent(this,Activity_transaction_Receipt.class);
									  Object_Voucher_Master obj =
									  Listvc.get(selectedId);
									  i.putExtra("vcName",obj.name); i.putExtra("date",
									  txtVoucherDate.getText().toString());
									  startActivity(i);
						} else {

						}
				    }
					} catch (Exception e) {

						Toast.makeText(this, R.string.TransactionToast,
								Toast.LENGTH_SHORT).show();
						e.printStackTrace();
					}
				}else{
					  Toast.makeText(this, " First main account add ", Toast.LENGTH_SHORT).show();
				  }
			} 
			else {
				// this is Recipt ,jounral ,payment voucher\
				Log.i("SUSHIL", "this is re. jo. pa ");
				try {
					int id = 0;
					// this is purchases voucher
					double AmountCredit = 0;
					double AmountDebit = 0;
					LinearLayout scRoll = (LinearLayout) findViewById(R.id.scRoll);
					Object_Voucher_Master objvc = Listvc.get(selectedId);
					objVchTran.voucher = objvc;
					// YYYY-MM-DD HH:MM:SS
					objVchTran.daytime = txtVoucherDate.getText().toString()
							+ " " + Globals.getcurrentTime();
			if (mapAll.size() == scRoll.getChildCount()) {
					for (int i = 0; i < scRoll.getChildCount(); i++) {
						Object_Ac_Icat_Itm_map objMap = mapAll.get(i);
						Object_Account Account = objVchTran.accounts
								.get(objMap.accountIndex);
						Object_Account_Txn objTxn = new Object_Account_Txn();
						for (int j = 1; j <= 5; j++) {
							id++;
							Log.i("SUSHIL", "Id is " + id);
							if (j == 1) {
								AutoCompleteTextView tranType = (AutoCompleteTextView) findViewById(id);
								String traj = tranType.getText().toString();
								if (!traj.trim().isEmpty()) {
									if (traj.equals("Credit")) {
										Object_Txn_Master txnType = new Object_Txn_Master();
										txnType.id = 1;
										objTxn.txnType = txnType;
									} else {
										Object_Txn_Master txnType = new Object_Txn_Master();
										txnType.id = 2;
										objTxn.txnType = txnType;
									}
								} else {
									Toast.makeText(this, "Plz provide cr/dr ",
											Toast.LENGTH_SHORT).show();
									break;
								}
							}
							if (j == 2) {
								TextView tranAc = (TextView) findViewById(id);
								String Ac = tranAc.getText().toString();
								if (!Ac.trim().isEmpty()) {

								} else {
									Toast.makeText(this, R.string.NameToast,
											Toast.LENGTH_SHORT).show();
									break;
								}
							}
							if (j == 3) {
								if (objTxn.txnType.id == 1) {
									EditText edttran = (EditText) findViewById(id);
									String amt = edttran.getText().toString();
									if (!amt.trim().isEmpty()) {
										objTxn.amount = Double.parseDouble(amt);
										AmountCredit = AmountCredit
												+ Double.parseDouble(amt);
									} else {
										Toast.makeText(
												this,
												"Credit amount did not empty !",
												Toast.LENGTH_SHORT).show();
										break;
									}
								}
							}
							if (j == 4) {
								if (objTxn.txnType.id == 2) {
									EditText edttran = (EditText) findViewById(id);
									String amt = edttran.getText().toString();
									if (!amt.trim().isEmpty()) {
										objTxn.amount = Double.parseDouble(amt);
										AmountDebit = AmountDebit
												+ Double.parseDouble(amt);
									} else {
										Toast.makeText(this,
												"Debit amount did not empty !",
												Toast.LENGTH_SHORT).show();
										break;
									}
								}
							}
							if (j == 5) {
								EditText edtnarra = (EditText) findViewById(id);
								String narr = edtnarra.getText().toString();
								if (!narr.trim().isEmpty()) {
									objTxn.narration = narr;
								} else {
									objTxn.narration = "";
								}
							}
						}
						objVchTran.accounts.get(objMap.accountIndex).accountTxn = objTxn;
					}
					if (AmountCredit == AmountDebit) {
                       
						boolean cashAcFound = false;
						int cashAcFoundOnIndex = -1;
						for (Object_Account account : objVchTran.accounts) {
							cashAcFoundOnIndex++;
							if(account.grpId == 5)
							{
								cashAcFound = true;
								break;
							}
						}
						if(cashAcFound)
						{
							for(int i=0;i<objVchTran.accounts.size();i++)
							{
								objVchTran.accounts.get(i).accountTxn.isCash = 1;
								CheckBox check = (CheckBox)findViewById(R.id.CheckBoxInterest);
			                       if(check.isChecked()){
			                    	   objVchTran.accounts.get(i).accountTxn.isInterest = 1;
			                       }
							}
						}else
						{
							for(int i=0;i<objVchTran.accounts.size();i++)
							{
								objVchTran.accounts.get(i).accountTxn.isCash = 0;
							}
						}
						
						/*DBHandler_Txn txn = new DBHandler_Txn(this);
						txn.createNewTxn(objVchTran);*/
						Intent i = new
								 Intent(this,Activity_transaction_Receipt.class);
								  Object_Voucher_Master obj =
								  Listvc.get(selectedId);
								  i.putExtra("vcName",obj.name); i.putExtra("date",
								  txtVoucherDate.getText().toString());
								  startActivity(i);
						/*
						 * Log.i("SUSHIL",
						 * "SUSHIL list of account "+ListAc.size());
						 * objTran.accountTxn = ListAc; objTran.itemTxn =
						 * ListItems; Intent i = new
						 * Intent(this,Activity_transaction_Receipt.class);
						 * Object_Voucher_Master obj = Listvc.get(selectedId);
						 * i.putExtra("vcName",obj.name); i.putExtra("date",
						 * txtVoucherDate.getText().toString());
						 * startActivity(i)
						 */;
					} else {
						Toast.makeText(this, R.string.CrDR, Toast.LENGTH_SHORT)
								.show();
						// ListAc.clear();
					}
			     }
				} catch (Exception e) {
					Toast.makeText(this, R.string.TransactionToast,
							Toast.LENGTH_SHORT).show();
					e.printStackTrace();
				}

			}
           }
	/*
	 * @Override public boolean onKeyDown(int keyCode, KeyEvent event) { if
	 * (keyCode == KeyEvent.KEYCODE_BACK) { //preventing default implementation
	 * previous to android.os.Build.VERSION_CODES.ECLAIR return true; } return
	 * true; }
	 */

	public void End(){
		this.finish();
	}
	
}
