package com.XB_Accounts;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map.Entry;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class Activity_SettingAddNew extends Activity {
	static final int DATE_DIALOG_ID = 333;
	TextView txtDate;
	private int year;
	private int month;
	private int day;
	int idAc;
	int selectedParrent = 0;
	int selectedUnit = 0;
	ArrayList<String> AcGpName;
	String into;
	int idSpin, updateId;
	HashMap<Integer, Integer> mapGp;
	HashMap<Integer, Integer> mapUnit;
	Object_Account account;
	Object_Item_Category objItemCate;
	Object_Item objItem;
    int typeId = -1;
    int typeSelection = -1;
    int txnId = -1;
	@SuppressLint("UseSparseArrays")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_activity__add_new);
		Intent i = getIntent();
		into = i.getStringExtra("into");
		mapGp = new HashMap<Integer, Integer>();
		mapUnit = new HashMap<Integer, Integer>();
		txtDate = (TextView) findViewById(R.id.txtdateView);
		setCurrentDateOnview();
		txtDate.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// getActivity().showDialog(DATE_DIALOG_ID);
				createFancyDateTimePicker(DATE_DIALOG_ID).show();
			}
		});

		Spinner spinner = (Spinner) findViewById(R.id.spinnerchooseAdd);
		ArrayList<String> AcType = new ArrayList<String>();
		AcType.add("Account");
		AcType.add("Item Category");
		AcType.add("Item");

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, AcType);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter);
		spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View v,
					int position, long id) {
				idAc = position;
				mapGp.clear();
				Log.i("SUSHIL", "Selected parrent is " + idAc);
				if (position == 0) {
					// accountGroup();
					account();
				} else if (position == 1) {

					itemCategory();

				} else if (position == 2) {

					items();

				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});
		
		
		if (into.equals("Edit")) {
			int id = i.getIntExtra("Id", 0);
			idSpin = i.getIntExtra("entry", 0);
			spinner.setSelection(idSpin);

			/*
			 * if(idSpin==0){ //accountGroup(); DBHandler_AccountsGroup ACGP =
			 * new DBHandler_AccountsGroup(this);
			 * //ArrayList<Object_Account_Group> obj = ACGP.getAllAccountGrp();
			 * //AA_Object_AccountsGroup objSelected = obj.get(id);
			 * Object_Account_Group objSelected = ACGP.getAccountGrp_byId(id);
			 * updateId = objSelected.id; EditText edtName = (EditText)
			 * findViewById(R.id.edtAcgpName); EditText edtDes = (EditText)
			 * findViewById(R.id.edtAcgpDes); Spinner spin = (Spinner)
			 * findViewById(R.id.spinnerParentGpAc);
			 * 
			 * spin.setSelection(objSelected.parentId);
			 * edtName.setText(objSelected.name);
			 * edtDes.setText(objSelected.description); }
			 */

			if (idSpin == 0) {

				// account();
				DBHandler_Account DBhaccount = new DBHandler_Account(this);
				account = DBhaccount.getAccountById(id);
				updateId = id;
				
				EditText edtName = (EditText) findViewById(R.id.edtAcgpName);
				EditText edtDes = (EditText) findViewById(R.id.edtAcgpDes);
				EditText edtAddress = (EditText) findViewById(R.id.edtAddress);
				EditText edtContact = (EditText) findViewById(R.id.edtContact);
				EditText edtRate = (EditText) findViewById(R.id.edtrate);
				EditText edtTime = (EditText) findViewById(R.id.edtTimeInterest);
				EditText edtAmount = (EditText) findViewById(R.id.edtAmount);
				EditText edtDate = (EditText) findViewById(R.id.edtDateOp);
				// Spinner spin = (Spinner)
				// findViewById(R.id.spinnerParentGpAc);

				edtName.setText(account.name);
				edtDes.setText(account.description);
				edtAddress.setText(account.address);
				edtContact.setText(account.contact);
				if (account.rate != 0) {
					CheckBox check = (CheckBox) findViewById(R.id.checkBox);
					check.setChecked(true);
					isChecked(true);
					edtRate.setText(account.rate + "");
					edtTime.setText(account.time + "");
				}
				if(account.accountTxn!=null){
					if(account.accountTxn.amount!=0 && account.daytime_open!=null){
					CheckBox check = (CheckBox) findViewById(R.id.checkBoxAmount);
					check.setChecked(true);
					isCheckedAmount(true);
					edtAmount.setText(account.accountTxn.amount + "");
					edtDate.setText(account.daytime_open);
					typeSelection = account.accountTxn.txnType.id-1;
					txnId = account.accountTxn.id;
					}
				}
				
				/*
				 * int spinSelection = -1; for (Entry<Integer, Integer> entry :
				 * mapGp.entrySet()) { if
				 * (entry.getValue().equals(account.grpId)) { spinSelection =
				 * entry.getKey(); } }
				 * 
				 * if(spinSelection!=-1) spin.setSelection(spinSelection);
				 */

			} else if (idSpin == 1) {
				// itemCategory();
				DBHandler_ItemCategory DbhItemCat = new DBHandler_ItemCategory(
						this);
				objItemCate = DbhItemCat.getItem_CategorybyId(id);
				updateId = objItemCate.id;
				EditText edtName = (EditText) findViewById(R.id.edtAcgpName);
				EditText edtDes = (EditText) findViewById(R.id.edtAcgpDes);
				// Spinner spin = (Spinner)
				// findViewById(R.id.spinnerParentGpAc);
				// spin.setSelection(objSelected.parentid);
				edtName.setText(objItemCate.name);
				edtDes.setText(objItemCate.description);
				/*
				 * int spinSelection = -1; for (Entry<Integer, Integer> entry :
				 * mapGp.entrySet()) { if
				 * (entry.getValue().equals(objItemCate.accountId)) {
				 * spinSelection = entry.getKey(); } }
				 * 
				 * if(spinSelection!=-1) spin.setSelection(spinSelection);
				 */
			}

			else if (idSpin == 2) {
				// items();

				DBHandler_Item DbhItem = new DBHandler_Item(this);
				objItem = DbhItem.getItembyId(id);

				updateId = objItem.id;
				EditText edtName = (EditText) findViewById(R.id.edtItemName);
				EditText edtDes = (EditText) findViewById(R.id.edtItemDes);
				EditText edtprintName = (EditText) findViewById(R.id.edtPrintName);
				EditText edtsaleprice = (EditText) findViewById(R.id.edtSalePrice);
				EditText edtQty = (EditText) findViewById(R.id.edtQTY);
				EditText edtpurchasesprice = (EditText) findViewById(R.id.edtPurchasesPrice);
				EditText edtmrp = (EditText) findViewById(R.id.edtMRP);
				EditText edtminsalePrice = (EditText) findViewById(R.id.edtmin_SalePrice);
				/*
				 * Spinner spin = (Spinner)
				 * findViewById(R.id.spinnerParentGpitem); Spinner spinnerUnit =
				 * (Spinner)findViewById(R.id.spinnerUnit);
				 */
				// spin.setSelection(objSelected.itemcatid);
				// spinnerUnit.setSelection(objSelected.unitid);
				edtName.setText(objItem.name);
				edtDes.setText(objItem.description);
				edtprintName.setText(objItem.printName);
				edtsaleprice.setText(objItem.salePrice + "");
				edtQty.setText(objItem.qtyRemaining + "");
				edtpurchasesprice.setText(objItem.purchasePrice + "");
				edtmrp.setText(objItem.mrp + "");
				edtminsalePrice.setText(objItem.minSalePrice + "");

			}

		}

		CheckBox check = (CheckBox) findViewById(R.id.checkBox);
		check.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {

				isChecked(isChecked);
			}
		});
		
		CheckBox checkAmount = (CheckBox) findViewById(R.id.checkBoxAmount);
		checkAmount.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {

				isCheckedAmount(isChecked);
			}
		});
	}

	protected Dialog createFancyDateTimePicker(int id) {
		switch (id) {
		case DATE_DIALOG_ID:
			return new DatePickerDialog(this, Globals.datepic(txtDate), year,
					month, day);
		}
		return null;

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
		Object_AppConfig config = new Object_AppConfig(this);
		Globals.setLocale(config.getLocale(), this);
	}
	
	private void isChecked(boolean check) {
		if (check) {
			LinearLayout lay = (LinearLayout) findViewById(R.id.RateLayout);
			lay.setVisibility(View.VISIBLE);
		} else {
			LinearLayout lay = (LinearLayout) findViewById(R.id.RateLayout);
			lay.setVisibility(View.GONE);
		}
	}
	private void isCheckedAmount(boolean check) {
		if (check) {
			LinearLayout lay = (LinearLayout) findViewById(R.id.AmountLayout);
			lay.setVisibility(View.VISIBLE);
			Spinner spinnerType = (Spinner)findViewById(R.id.spinnerType);
			ArrayList<String> typelist = new ArrayList<String>();
			typelist.add("Credit");
			typelist.add("Debit");
			
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
					android.R.layout.simple_spinner_item, typelist);
			adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			spinnerType.setAdapter(adapter);
			
			spinnerType.setOnItemSelectedListener(new OnItemSelectedListener() {

				@Override
				public void onItemSelected(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					typeId = arg2;
					
				}

				@Override
				public void onNothingSelected(AdapterView<?> arg0) {
					// TODO Auto-generated method stub
					
				}
			});
		} else {
			LinearLayout lay = (LinearLayout) findViewById(R.id.AmountLayout);
			lay.setVisibility(View.GONE);
		}
	}

	public void moveMasters(View v) {
		Intent i = new Intent(this, Activity_accounts.class);
		startActivity(i);
	}

	private void setCurrentDateOnview() {

		final Calendar c = Calendar.getInstance();
		year = c.get(Calendar.YEAR);
		month = c.get(Calendar.MONTH);
		day = c.get(Calendar.DAY_OF_MONTH);
		txtDate.setText(day + "/" + (month + 1) + "/" + year);

		// dpresult.init(year, month, day, null);

	}

	@SuppressLint("ShowToast")
	public void save(View v) {
		if (!into.equals("Edit")) {
			if (idAc == 0) {
				// save details Account group
				/*
				 * AA_DBHandler_AccountsGroup ACGP = new
				 * AA_DBHandler_AccountsGroup(this);
				 * ArrayList<AA_Object_AccountsGroup> obj =
				 * ACGP.getAllAccountGrp(); AA_Object_AccountsGroup objSelected
				 * = obj.get(selectedParrent); AA_Object_AccountsGroup objGp =
				 * new AA_Object_AccountsGroup(); EditText edtName = (EditText)
				 * findViewById(R.id.edtAcgpName); EditText edtDes = (EditText)
				 * findViewById(R.id.edtAcgpDes); if
				 * (!edtName.getText().toString().trim().isEmpty()) { objGp.name
				 * = edtName.getText().toString();
				 * Toast.makeText(this,edtName.getText().toString(),
				 * Toast.LENGTH_SHORT).show(); } else { Toast.makeText(this,
				 * R.string.NameToast, Toast.LENGTH_SHORT).show(); return; } if
				 * (edtDes.getText().toString() != null) { objGp.description =
				 * edtDes.getText().toString(); } else { objGp.description = "";
				 * } objGp.parentid = objSelected.id; boolean succes =
				 * ACGP.insertData(objGp); if (succes) { Toast.makeText(this,
				 * "Succesfully inserted account group",
				 * Toast.LENGTH_SHORT).show(); edtName.setText("");
				 * edtDes.setText(""); } else { Toast.makeText(this,
				 * "Failled to insert account group",
				 * Toast.LENGTH_SHORT).show();
				 */
				Object_Account objAc = new Object_Account();
				boolean success = false;
				;
				if (mapGp.size() != 0) {
					/*
					 * Log.i("SUSHIL", "hash map size is "+mapGp.size());
					 * Log.i("SUSHIL",
					 * "selected id is parrent "+selectedParrent);
					 */
					objAc.grpId = mapGp.get(selectedParrent);
					// edt values
					EditText edtName = (EditText) findViewById(R.id.edtAcgpName);
					EditText edtDes = (EditText) findViewById(R.id.edtAcgpDes);
					EditText edtAddress = (EditText) findViewById(R.id.edtAddress);
					EditText edtContact = (EditText) findViewById(R.id.edtContact);
					EditText edtRate = (EditText) findViewById(R.id.edtrate);
					EditText edtTime = (EditText) findViewById(R.id.edtTimeInterest);
					EditText edtDate = (EditText) findViewById(R.id.edtDateOp);
					EditText edtAmount = (EditText) findViewById(R.id.edtAmount);
					boolean save = false;
					if (!edtName.getText().toString().trim().isEmpty()) {
						objAc.name = edtName.getText().toString();
						save = true;
					} else {
						Toast.makeText(this, R.string.NameToast,
								Toast.LENGTH_SHORT).show();
						save = false;
						return;
					}
					if (edtDes.getText().toString() != null) {
						objAc.description = edtDes.getText().toString();
					} else {
						objAc.description = "";
					}
					if (edtAddress.getText().toString() != null) {
						objAc.address = edtAddress.getText().toString();
					} else {
						objAc.address = "";
					}
					if (edtContact.getText().toString() != null) {
						objAc.contact = edtContact.getText().toString();
					} else {
						objAc.contact = "";
					}
					CheckBox check = (CheckBox) findViewById(R.id.checkBox);
					CheckBox checkAmount = (CheckBox) findViewById(R.id.checkBoxAmount);
					if(checkAmount.isChecked()){
						Object_Account_Txn TXN = new Object_Account_Txn();
						if (!edtAmount.getText().toString().trim().isEmpty()) {
							TXN.amount = Double.parseDouble(edtAmount.getText()
									.toString());
							save = true;
						} else {
							Toast.makeText(this, R.string.Amounttoast,
									Toast.LENGTH_SHORT).show();
							save = false;
							return;
						}
						if (!edtDate.getText().toString().trim().isEmpty()) {
							objAc.daytime_open = edtDate.getText()
									.toString();
							save = true;
						} else {
							Toast.makeText(this, R.string.Datetoast,
									Toast.LENGTH_SHORT).show();
							save = false;
							return;
						}
						if(typeId!=-1){
						Object_Txn_Master objmaster = new Object_Txn_Master();
						objmaster.id = typeId+1;
						TXN.txnType = objmaster;
						save = true;
						}else{
							save = false;
							return;
						}
						TXN.isOpening = 1;
						objAc.accountTxn = TXN;
					}
					
					if (check.isChecked()) {
						if (!edtRate.getText().toString().trim().isEmpty()) {
							objAc.rate = Double.parseDouble(edtRate.getText()
									.toString());
							save = true;
						} else {
							Toast.makeText(this, R.string.RateToast,
									Toast.LENGTH_SHORT).show();
							save = false;
							return;
						}
						if (!edtTime.getText().toString().trim().isEmpty()) {
							objAc.time = Double.parseDouble(edtTime.getText()
									.toString());
							save = true;
						} else {
							Toast.makeText(this, R.string.TimeToast,
									Toast.LENGTH_SHORT).show();
							save = false;
							return;
						}
					}
					if (save) {
						DBHandler_Account DBAc = new DBHandler_Account(this);
						success = DBAc.createNewAccount(objAc);

						if (success) {
							Toast.makeText(this,
									"Succesfully inserted account",
									Toast.LENGTH_SHORT).show();
							edtName.setText("");
							edtAddress.setText("");
							edtContact.setText("");
							edtDes.setText("");
							edtRate.setText("");
							edtTime.setText("");
							edtAmount.setText("");
							edtDate.setText("");
						} else {
							Toast.makeText(this, "Failled to insert account",
									Toast.LENGTH_SHORT).show();
						}
					}
				} else {
					Toast.makeText(this, R.string.ToastParrent,
							Toast.LENGTH_SHORT).show();
				}

			} else if (idAc == 1) {
				// save details Item cate
				// Log.i("SUSHIL", "item cate in ");
				DBHandler_ItemCategory dbhItemCat = new DBHandler_ItemCategory(
						Activity_SettingAddNew.this);
				boolean save = false;
				Object_Item_Category obj = new Object_Item_Category();
				EditText edtName = (EditText) findViewById(R.id.edtAcgpName);
				EditText edtDes = (EditText) findViewById(R.id.edtAcgpDes);
				if (!edtName.getText().toString().trim().isEmpty()) {
					obj.name = edtName.getText().toString();
					save = true;
				} else {
					Toast.makeText(this, R.string.NameToast, Toast.LENGTH_SHORT)
							.show();
					return;
				}
				if (edtDes.getText().toString() != null) {
					obj.description = edtDes.getText().toString();
				} else {
					obj.description = "";
				}
				DBHandler_Account dbhAccount = new DBHandler_Account(this);
				ArrayList<Object_Account> ac = dbhAccount
						.getAccounts("Goods A/c");
				if (ac.size() != 0)
					obj.accountId = ac.get(0).id;
				if (save) {
					boolean success = dbhItemCat.createItemCategory(obj);
					if (success) {
						Toast.makeText(this,
								"Succesfully inserted Item category",
								Toast.LENGTH_SHORT).show();
						edtName.setText("");
						edtDes.setText("");
					} else {
						Toast.makeText(this, "Failled to insert Item category",
								Toast.LENGTH_SHORT).show();
					}
				}
			} else if (idAc == 2) {
				// save datails Item
				// Log.i("SUSHIL", "this is items add "+selectedParrent);
				boolean save = false;
				Object_Item objitem = new Object_Item();
				if (mapGp.size() != 0) {
					objitem.itemCatId = mapGp.get(selectedParrent);

					EditText edtName = (EditText) findViewById(R.id.edtItemName);
					EditText edtDes = (EditText) findViewById(R.id.edtItemDes);
					EditText edtprintName = (EditText) findViewById(R.id.edtPrintName);
					EditText edtsaleprice = (EditText) findViewById(R.id.edtSalePrice);
					EditText edtQty = (EditText) findViewById(R.id.edtQTY);
					EditText edtpurchasesprice = (EditText) findViewById(R.id.edtPurchasesPrice);
					EditText edtmrp = (EditText) findViewById(R.id.edtMRP);
					EditText edtminsalePrice = (EditText) findViewById(R.id.edtmin_SalePrice);
					if (!edtName.getText().toString().trim().isEmpty()) {
						objitem.name = edtName.getText().toString();
						save = true;
					} else {
						save = false;
						Toast.makeText(this, R.string.NameItemToast,
								Toast.LENGTH_SHORT).show();
						return;
					}
					if (edtDes.getText().toString() != null) {
						objitem.description = edtDes.getText().toString();
					} else {
						objitem.description = "";
					}
					if (!edtprintName.getText().toString().trim().isEmpty()) {
						objitem.printName = edtprintName.getText().toString();
						save = true;
					} else {
						save = false;
						Toast.makeText(this, R.string.NameItemPrintToast,
								Toast.LENGTH_SHORT).show();
						return;
					}
					if (!edtsaleprice.getText().toString().trim().isEmpty()) {
						objitem.salePrice = Double.parseDouble(edtsaleprice
								.getText().toString());
						save = true;
					} else {
						save = false;
						Toast.makeText(this, R.string.SalepriceToast,
								Toast.LENGTH_SHORT).show();
						return;
					}
					if (!edtpurchasesprice.getText().toString().trim()
							.isEmpty()) {
						objitem.purchasePrice = Double
								.parseDouble(edtpurchasesprice.getText()
										.toString());
						save = true;
					} else {
						save = false;
						Toast.makeText(this, R.string.PurchasespriceToast,
								Toast.LENGTH_SHORT).show();
						return;
					}
					if (!edtmrp.getText().toString().trim().isEmpty()) {
						objitem.mrp = Double.parseDouble(edtmrp.getText()
								.toString());
						save = true;
					} else {
						save = false;
						Toast.makeText(this, R.string.MRPToast,
								Toast.LENGTH_SHORT).show();
						return;
					}
					if (!edtminsalePrice.getText().toString().trim().isEmpty()) {
						objitem.minSalePrice = Double
								.parseDouble(edtminsalePrice.getText()
										.toString());
						save = true;
					} else {
						save = false;
						Toast.makeText(this, R.string.MinsaleToast,
								Toast.LENGTH_SHORT).show();
						return;
					}
					if (!edtQty.getText().toString().trim().isEmpty()) {
						objitem.qtyRemaining = Integer.parseInt(edtQty
								.getText().toString());
					} else {
						objitem.qtyRemaining = 0;
					}
					if (mapUnit.size() != 0) {
						Object_Unit unit = new Object_Unit();
						unit.id = mapUnit.get(selectedUnit);
						objitem.unit = unit;
					}
					if (save) {
						DBHandler_Item DBItem = new DBHandler_Item(this);
						Boolean suBoolean = DBItem.createNewItem(objitem);
						if (suBoolean) {
							Toast.makeText(this, "Succesfully inserted Item",
									Toast.LENGTH_SHORT).show();
							edtName.setText("");
							edtDes.setText("");
							edtminsalePrice.setText("");
							edtmrp.setText("");
							edtprintName.setText("");
							edtQty.setText("");
							edtsaleprice.setText("");
							edtpurchasesprice.setText("");
						} else {
							Toast.makeText(this, "Failled to insert Item ",
									Toast.LENGTH_SHORT).show();

						}
					}
				} else {
					Toast.makeText(this, R.string.ToastParrent,
							Toast.LENGTH_SHORT).show();
				}
			}
		} else {
			if (idSpin == 0) {
				// save details Account group
				/*
				 * AA_DBHandler_AccountsGroup ACGP = new
				 * AA_DBHandler_AccountsGroup(this);
				 * ArrayList<AA_Object_AccountsGroup> obj =
				 * ACGP.getAllAccountGrp(); AA_Object_AccountsGroup objSelected
				 * = obj.get(selectedParrent); AA_Object_AccountsGroup objGp =
				 * new AA_Object_AccountsGroup(); EditText edtName = (EditText)
				 * findViewById(R.id.edtAcgpName); EditText edtDes = (EditText)
				 * findViewById(R.id.edtAcgpDes); if
				 * (!edtName.getText().toString().trim().isEmpty()) { objGp.name
				 * = edtName.getText().toString();
				 * Toast.makeText(this,edtName.getText().toString(),
				 * Toast.LENGTH_SHORT).show(); } else { Toast.makeText(this,
				 * R.string.NameToast, Toast.LENGTH_SHORT).show(); return; } if
				 * (edtDes.getText().toString() != null) { objGp.description =
				 * edtDes.getText().toString(); } else { objGp.description = "";
				 * } objGp.parentid = objSelected.id; boolean succes =
				 * ACGP.updateAccount_Group(objGp, updateId); if (succes) {
				 * Toast.makeText(this, "Succesfully update account group",
				 * Toast.LENGTH_SHORT).show(); edtName.setText("");
				 * edtDes.setText(""); } else { Toast.makeText(this,
				 * "Failled to update account group",
				 * Toast.LENGTH_SHORT).show(); }
				 */
				Object_Account objAc = new Object_Account();
				boolean success = false;
				;
				if (mapGp.size() != 0) {
					/*
					 * Log.i("SUSHIL", "hash map size is "+mapGp.size());
					 * Log.i("SUSHIL",
					 * "selected id is parrent "+selectedParrent);
					 */
					objAc.grpId = mapGp.get(selectedParrent);
					// edt values
					EditText edtName = (EditText) findViewById(R.id.edtAcgpName);
					EditText edtDes = (EditText) findViewById(R.id.edtAcgpDes);
					EditText edtAddress = (EditText) findViewById(R.id.edtAddress);
					EditText edtContact = (EditText) findViewById(R.id.edtContact);
					EditText edtRate = (EditText) findViewById(R.id.edtrate);
					EditText edtTime = (EditText) findViewById(R.id.edtTimeInterest);
					EditText edtDate = (EditText) findViewById(R.id.edtDateOp);
					EditText edtAmount = (EditText) findViewById(R.id.edtAmount);
					
					boolean save = false;
					if (!edtName.getText().toString().trim().isEmpty()) {
						objAc.name = edtName.getText().toString();
						save = true;
					} else {
						Toast.makeText(this, R.string.NameToast,
								Toast.LENGTH_SHORT).show();
						save = false;
						return;
					}
					if (edtDes.getText().toString() != null) {
						objAc.description = edtDes.getText().toString();
					} else {
						objAc.description = "";
					}
					if (edtAddress.getText().toString() != null) {
						objAc.address = edtAddress.getText().toString();
					} else {
						objAc.address = "";
					}
					if (edtContact.getText().toString() != null) {
						objAc.contact = edtContact.getText().toString();
					} else {
						objAc.contact = "";
					}
					
					CheckBox check = (CheckBox) findViewById(R.id.checkBox);
					CheckBox checkAmount = (CheckBox) findViewById(R.id.checkBoxAmount);
					if(checkAmount.isChecked()){
						Object_Account_Txn TXN = new Object_Account_Txn();
						if (!edtAmount.getText().toString().trim().isEmpty()) {
							TXN.amount = Double.parseDouble(edtAmount.getText()
									.toString());
							save = true;
						} else {
							Toast.makeText(this, R.string.Amounttoast,
									Toast.LENGTH_SHORT).show();
							save = false;
							return;
						}
						if (!edtDate.getText().toString().trim().isEmpty()) {
							objAc.daytime_open = edtDate.getText()
									.toString();
							save = true;
						} else {
							Toast.makeText(this, R.string.Datetoast,
									Toast.LENGTH_SHORT).show();
							save = false;
							return;
						}
						if(typeId!=-1){
						Object_Txn_Master objmaster = new Object_Txn_Master();
						objmaster.id = typeId+1;
						TXN.txnType = objmaster;
						save = true;
						}else{
							save = false;
							return;
						}
						TXN.isOpening = 1;
						Log.i("SUSHIL", "update id from gone "+txnId);
						TXN.id = txnId;
						
						objAc.accountTxn = TXN;
					}
					
					if (check.isChecked()) {
						if (!edtRate.getText().toString().trim().isEmpty()) {
							objAc.rate = Double.parseDouble(edtRate.getText()
									.toString());
							save = true;
						} else {
							Toast.makeText(this, R.string.RateToast,
									Toast.LENGTH_SHORT).show();
							save = false;
							return;
						}
						if (!edtTime.getText().toString().trim().isEmpty()) {
							objAc.time = Double.parseDouble(edtTime.getText()
									.toString());
							save = true;
						} else {
							Toast.makeText(this, R.string.TimeToast,
									Toast.LENGTH_SHORT).show();
							save = false;
							return;
						}
					}
					
					if (save) {
						
						
						DBHandler_Account DBAc = new DBHandler_Account(this);
						success = DBAc.updateAccount(objAc, updateId);

						if (success) {
							Toast.makeText(this, "Succesfully Update account",
									Toast.LENGTH_SHORT).show();
							edtName.setText("");
							edtAddress.setText("");
							edtContact.setText("");
							edtDes.setText("");
						} else {
							Toast.makeText(this, "Failled to Update account",
									Toast.LENGTH_SHORT).show();
						}
					}
				} else {
					Toast.makeText(this, R.string.ToastParrent,
							Toast.LENGTH_SHORT).show();
				}

			} else if (idSpin == 1) {
				// save details Accounts
				/*
				 * AA_DBHandler_AccountsGroup ACGP = new
				 * AA_DBHandler_AccountsGroup(this);
				 * ArrayList<AA_Object_AccountsGroup> obj =
				 * ACGP.getAllAccountGrp(); AA_Object_AccountsGroup objSelected
				 * = null; if(selectedParrent!=0){ objSelected =
				 * obj.get(selectedParrent);
				 * 
				 * }else{ Toast.makeText(this,R.string.parentNotSelectedToast,
				 * Toast.LENGTH_SHORT).show(); return; } AA_Object_Accounts
				 * objAc = new AA_Object_Accounts(); EditText edtName =
				 * (EditText) findViewById(R.id.edtAcgpName); EditText edtDes =
				 * (EditText) findViewById(R.id.edtAcgpDes); EditText edtAddress
				 * = (EditText) findViewById(R.id.edtAddress); EditText
				 * edtContact = (EditText) findViewById(R.id.edtContact); if
				 * (!edtName.getText().toString().trim().isEmpty()) { objAc.name
				 * = edtName.getText().toString(); } else { Toast.makeText(this,
				 * R.string.NameToast, Toast.LENGTH_SHORT).show(); return; } if
				 * (edtDes.getText().toString() != null) { objAc.description =
				 * edtDes.getText().toString(); } else { objAc.description = "";
				 * } if (edtAddress.getText().toString() != null) {
				 * objAc.address = edtAddress.getText().toString(); } else {
				 * objAc.address = ""; } if (edtContact.getText().toString() !=
				 * null) { objAc.contact = edtContact.getText().toString(); }
				 * else { objAc.contact = ""; } objAc.grpid = objSelected.id;
				 * AA_DBHandler_Accounts DBAc = new AA_DBHandler_Accounts(this);
				 * boolean success = DBAc.updateAccount(objAc, updateId); if
				 * (success) { Toast.makeText(this,
				 * "Succesfully update account", Toast.LENGTH_SHORT).show();
				 * edtName.setText(""); edtAddress.setText("");
				 * edtContact.setText(""); edtDes.setText(""); } else {
				 * Toast.makeText(this, "Failled to update account",
				 * Toast.LENGTH_SHORT).show(); }
				 */
				DBHandler_ItemCategory dbhItemCat = new DBHandler_ItemCategory(
						Activity_SettingAddNew.this);
				boolean save = false;
				Object_Item_Category obj = new Object_Item_Category();
				EditText edtName = (EditText) findViewById(R.id.edtAcgpName);
				EditText edtDes = (EditText) findViewById(R.id.edtAcgpDes);
				if (!edtName.getText().toString().trim().isEmpty()) {
					obj.name = edtName.getText().toString();
					save = true;
				} else {
					Toast.makeText(this, R.string.NameToast, Toast.LENGTH_SHORT)
							.show();
					return;
				}
				if (edtDes.getText().toString() != null) {
					obj.description = edtDes.getText().toString();
				} else {
					obj.description = "";
				}
				DBHandler_Account dbhAccount = new DBHandler_Account(this);
				ArrayList<Object_Account> ac = dbhAccount
						.getAccounts("Goods A/c");
				if (ac.size() != 0)
					obj.accountId = ac.get(0).id;
				if (save) {
					boolean success = dbhItemCat.updateItem_Cate(obj, updateId);
					if (success) {
						Toast.makeText(this,
								"Succesfully updated Item category",
								Toast.LENGTH_SHORT).show();
						edtName.setText("");
						edtDes.setText("");
					} else {
						Toast.makeText(this,
								"Failled to updated Item category",
								Toast.LENGTH_SHORT).show();
					}

				}
			} else if (idSpin == 2) {
				// save datails Item Category
				/*
				 * AA_DBHandler_ItemCategory ListItemGP = new
				 * AA_DBHandler_ItemCategory( Activity_SettingAddNew.this);
				 * ArrayList<AA_Object_ItemCategory> objItemGp = ListItemGP
				 * .getAllItemCategories(); AA_Object_ItemCategory objCategory =
				 * objItemGp.get(selectedParrent); AA_Object_ItemCategory obj =
				 * new AA_Object_ItemCategory(); EditText edtName = (EditText)
				 * findViewById(R.id.edtAcgpName); EditText edtDes = (EditText)
				 * findViewById(R.id.edtAcgpDes); if
				 * (!edtName.getText().toString().trim().isEmpty()) { obj.name =
				 * edtName.getText().toString(); } else { Toast.makeText(this,
				 * R.string.NameToast, Toast.LENGTH_SHORT).show(); return; } if
				 * (edtDes.getText().toString() != null) { obj.description =
				 * edtDes.getText().toString(); } else { obj.description = ""; }
				 * obj.parentid = objCategory.id; boolean success =
				 * ListItemGP.updateItem_Category(obj, updateId); if (success) {
				 * Toast.makeText(this, "Succesfully update Item category",
				 * Toast.LENGTH_SHORT).show(); edtName.setText("");
				 * edtDes.setText(""); } else { Toast.makeText(this,
				 * "Failled to update Item category",
				 * Toast.LENGTH_SHORT).show(); }
				 */

				boolean save = false;
				Object_Item objitem = new Object_Item();
				if (mapGp.size() != 0) {
					objitem.itemCatId = mapGp.get(selectedParrent);

					EditText edtName = (EditText) findViewById(R.id.edtItemName);
					EditText edtDes = (EditText) findViewById(R.id.edtItemDes);
					EditText edtprintName = (EditText) findViewById(R.id.edtPrintName);
					EditText edtsaleprice = (EditText) findViewById(R.id.edtSalePrice);
					EditText edtQty = (EditText) findViewById(R.id.edtQTY);
					EditText edtpurchasesprice = (EditText) findViewById(R.id.edtPurchasesPrice);
					EditText edtmrp = (EditText) findViewById(R.id.edtMRP);
					EditText edtminsalePrice = (EditText) findViewById(R.id.edtmin_SalePrice);
					if (!edtName.getText().toString().trim().isEmpty()) {
						objitem.name = edtName.getText().toString();
						save = true;
					} else {
						save = false;
						Toast.makeText(this, R.string.NameItemToast,
								Toast.LENGTH_SHORT).show();
						return;
					}
					if (edtDes.getText().toString() != null) {
						objitem.description = edtDes.getText().toString();
					} else {
						objitem.description = "";
					}
					if (!edtprintName.getText().toString().trim().isEmpty()) {
						objitem.printName = edtprintName.getText().toString();
						save = true;
					} else {
						save = false;
						Toast.makeText(this, R.string.NameItemPrintToast,
								Toast.LENGTH_SHORT).show();
						return;
					}
					if (!edtsaleprice.getText().toString().trim().isEmpty()) {
						objitem.salePrice = Double.parseDouble(edtsaleprice
								.getText().toString());
						save = true;
					} else {
						save = false;
						Toast.makeText(this, R.string.SalepriceToast,
								Toast.LENGTH_SHORT).show();
						return;
					}
					if (!edtpurchasesprice.getText().toString().trim()
							.isEmpty()) {
						objitem.purchasePrice = Double
								.parseDouble(edtpurchasesprice.getText()
										.toString());
						save = true;
					} else {
						save = false;
						Toast.makeText(this, R.string.PurchasespriceToast,
								Toast.LENGTH_SHORT).show();
						return;
					}
					if (!edtmrp.getText().toString().trim().isEmpty()) {
						objitem.mrp = Double.parseDouble(edtmrp.getText()
								.toString());
						save = true;
					} else {
						save = false;
						Toast.makeText(this, R.string.MRPToast,
								Toast.LENGTH_SHORT).show();
						return;
					}
					if (!edtminsalePrice.getText().toString().trim().isEmpty()) {
						objitem.minSalePrice = Double
								.parseDouble(edtminsalePrice.getText()
										.toString());
						save = true;
					} else {
						save = false;
						Toast.makeText(this, R.string.MinsaleToast,
								Toast.LENGTH_SHORT).show();
						return;
					}
					if (!edtQty.getText().toString().trim().isEmpty()) {
						objitem.qtyRemaining = Integer.parseInt(edtQty
								.getText().toString());
					} else {
						objitem.qtyRemaining = 0;
					}
					if (mapUnit.size() != 0) {
						Object_Unit unit = new Object_Unit();
						unit.id = mapUnit.get(selectedUnit);
						objitem.unit = unit;
					}
					if (save) {
						DBHandler_Item DBItem = new DBHandler_Item(this);
						Boolean suBoolean = DBItem
								.updateItem(objitem, updateId);
						if (suBoolean) {
							Toast.makeText(this, "Succesfully updated Item",
									Toast.LENGTH_SHORT).show();
							edtName.setText("");
							edtDes.setText("");
							edtminsalePrice.setText("");
							edtmrp.setText("");
							edtprintName.setText("");
							edtQty.setText("");
							edtsaleprice.setText("");
							edtpurchasesprice.setText("");
						} else {
							Toast.makeText(this, "Failled to update Item ",
									Toast.LENGTH_SHORT).show();

						}
					}
				}
			}

			/*
			 * else if(idSpin ==3) { // save items Log.i("SUSHIL",
			 * "this is items add "+selectedParrent); AA_DBHandler_ItemCategory
			 * ListItemGP = new AA_DBHandler_ItemCategory(
			 * Activity_SettingAddNew.this); ArrayList<AA_Object_ItemCategory>
			 * objItemGp = ListItemGP .getAllItemCategories(); AA_Object_Items
			 * objitem = new AA_Object_Items(); AA_Object_ItemCategory
			 * objCategory = null; if(selectedParrent!=0){ objCategory =
			 * objItemGp.get(selectedParrent); }else{
			 * Toast.makeText(this,R.string.parentNotSelectedToast,
			 * Toast.LENGTH_SHORT).show(); return; }
			 * 
			 * EditText edtName = (EditText) findViewById(R.id.edtItemName);
			 * EditText edtDes = (EditText) findViewById(R.id.edtItemDes);
			 * EditText edtprintName = (EditText)
			 * findViewById(R.id.edtPrintName); EditText edtsaleprice =
			 * (EditText) findViewById(R.id.edtSalePrice); EditText edtQty =
			 * (EditText) findViewById(R.id.edtQTY); EditText edtpurchasesprice
			 * = (EditText) findViewById(R.id.edtPurchasesPrice); EditText
			 * edtmrp = (EditText) findViewById(R.id.edtMRP); EditText
			 * edtminsalePrice = (EditText) findViewById(R.id.edtmin_SalePrice);
			 * if (!edtName.getText().toString().trim().isEmpty()) {
			 * objitem.name = edtName.getText().toString(); } else {
			 * Toast.makeText(this, R.string.NameItemToast,
			 * Toast.LENGTH_SHORT).show(); return; } if
			 * (edtDes.getText().toString() != null) { objitem.description =
			 * edtDes.getText().toString(); } else { objitem.description = ""; }
			 * if (!edtprintName.getText().toString().trim().isEmpty()) {
			 * objitem.printname = edtprintName.getText().toString(); } else {
			 * Toast.makeText(this, R.string.NameItemPrintToast,
			 * Toast.LENGTH_SHORT).show(); return; } if
			 * (!edtsaleprice.getText().toString().trim().isEmpty()) {
			 * objitem.saleprice =
			 * Double.parseDouble(edtsaleprice.getText().toString()); } else {
			 * Toast.makeText(this, R.string.SalepriceToast,
			 * Toast.LENGTH_SHORT).show(); return; } if
			 * (!edtpurchasesprice.getText().toString().trim().isEmpty()) {
			 * objitem.purchaseprice =
			 * Double.parseDouble(edtpurchasesprice.getText().toString()); }
			 * else { Toast.makeText(this, R.string.PurchasespriceToast,
			 * Toast.LENGTH_SHORT).show(); return; } if
			 * (!edtmrp.getText().toString().trim().isEmpty()) { objitem.mrp =
			 * Double.parseDouble(edtmrp.getText().toString()); } else {
			 * Toast.makeText(this, R.string.MRPToast,
			 * Toast.LENGTH_SHORT).show(); return; } if
			 * (!edtminsalePrice.getText().toString().trim().isEmpty()) {
			 * objitem.minsaleprice =
			 * Double.parseDouble(edtminsalePrice.getText().toString()); } else
			 * { Toast.makeText(this, R.string.MinsaleToast,
			 * Toast.LENGTH_SHORT).show(); return; } if
			 * (!edtQty.getText().toString().trim().isEmpty()) {
			 * objitem.qtyremaining =
			 * Integer.parseInt(edtQty.getText().toString()); } else {
			 * objitem.qtyremaining = 0; } AA_DBHandler_Unit unitDB = new
			 * AA_DBHandler_Unit(this); ArrayList<AA_Object_Unit> Listunit =
			 * unitDB.getAllUnits(); AA_Object_Unit objunit =
			 * Listunit.get(selectedUnit); objitem.unitid = objunit.id;
			 * objitem.itemcatid = objCategory.id; AA_DBHandler_Items DBItem =
			 * new AA_DBHandler_Items(this); Boolean suBoolean =
			 * DBItem.updateItem(objitem, updateId); if (suBoolean) {
			 * Toast.makeText(this, "Succesfully update Item",
			 * Toast.LENGTH_SHORT).show(); edtName.setText("");
			 * edtDes.setText(""); edtminsalePrice.setText("");
			 * edtmrp.setText(""); edtprintName.setText(""); edtQty.setText("");
			 * edtsaleprice.setText(""); edtpurchasesprice.setText(""); } else {
			 * Toast.makeText(this, "Failled to update Item ",
			 * Toast.LENGTH_SHORT).show();
			 * 
			 * } }
			 */
		}
	}

	private void setspinnerData(Spinner spin, ArrayList<String> acGpName) {

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, acGpName);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spin.setAdapter(adapter);
		spin.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				selectedParrent = position;
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub

			}
		});
	}

	private void setspinnerUnitData(Spinner spin, ArrayList<String> acGpName) {

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, acGpName);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spin.setAdapter(adapter);
		spin.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				selectedUnit = position;

			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub

			}
		});
	}

	/*
	 * private void accountGroup(){ LinearLayout linear = (LinearLayout)
	 * findViewById(R.id.AcGp); LinearLayout linearItems = (LinearLayout)
	 * findViewById(R.id.Items); LinearLayout linearMoreDetails = (LinearLayout)
	 * findViewById(R.id.LinearMoreDetails); AA_DBHandler_AccountsGroup AcGp =
	 * new AA_DBHandler_AccountsGroup( Activity_SettingAddNew.this);
	 * ArrayList<AA_Object_AccountsGroup> objAcGp = AcGp .getAllAccountGrp();
	 * AcGpName = new ArrayList<String>(); if (objAcGp.size() != 0) { for (int i
	 * = 0; i < objAcGp.size(); i++) { String name = objAcGp.get(i).name; if
	 * (name != null) { AcGpName.add(name); } } } Spinner spin = (Spinner)
	 * findViewById(R.id.spinnerParentGpAc); setspinnerData(spin, AcGpName);
	 * linearItems.setVisibility(View.GONE);
	 * linearMoreDetails.setVisibility(View.GONE);
	 * linear.setVisibility(View.VISIBLE); }
	 */

	private void account() {
		/*
		 * LinearLayout linearRate = (LinearLayout)
		 * findViewById(R.id.RateLayout); CheckBox check =
		 * (CheckBox)findViewById(R.id.checkBox);
		 * linearRate.setVisibility(View.VISIBLE);
		 * check.setVisibility(View.VISIBLE);
		 */
		LinearLayout linear = (LinearLayout) findViewById(R.id.AcGp);
		LinearLayout linearItems = (LinearLayout) findViewById(R.id.Items);
		LinearLayout linearMoreDetails = (LinearLayout) findViewById(R.id.LinearMoreDetails);
		DBHandler_AccountsGroup dbhAcGroup = new DBHandler_AccountsGroup(
				Activity_SettingAddNew.this);
		ArrayList<Object_Account_Group> listGroup = dbhAcGroup
				.getAllAccountGrp();
		ArrayList<String> AcGpName = new ArrayList<String>();
		int index = -1;
		for (Object_Account_Group mainGroup : listGroup) {
			if (mainGroup.accountGroups != null) {
				for (Object_Account_Group subGroup : mainGroup.accountGroups) {
					index++;
					AcGpName.add(subGroup.name);
					mapGp.put(index, subGroup.id);
				}
			}

		}
		/*
		 * if (objAcGp.size() != 0) { for (int i = 0; i < objAcGp.size(); i++) {
		 * String name = objAcGp.get(i).name; if (name != null) {
		 * AcGpName.add(name); } }
		 */
		// }
		Spinner spin = (Spinner) findViewById(R.id.spinnerParentGpAc);
		setspinnerData(spin, AcGpName);
		linearItems.setVisibility(View.GONE);
		linear.setVisibility(View.VISIBLE);
		linearMoreDetails.setVisibility(View.VISIBLE);

		if (into.equals("Edit")) {
			setspinnerSelection(mapGp, spin, account.grpId);
			if(typeSelection!=-1){
			Spinner spinType = (Spinner) findViewById(R.id.spinnerType);
			spinType.setSelection(typeSelection);
			}
		}
	}

	private void itemCategory() {
		LinearLayout linearRate = (LinearLayout) findViewById(R.id.RateLayout);
		CheckBox check = (CheckBox) findViewById(R.id.checkBox);
		LinearLayout linearammount = (LinearLayout) findViewById(R.id.AmountLayout);
		CheckBox checkAmount = (CheckBox) findViewById(R.id.checkBoxAmount);
		linearammount.setVisibility(View.GONE);
		checkAmount.setVisibility(View.GONE);
		linearRate.setVisibility(View.GONE);
		check.setVisibility(View.GONE);
		LinearLayout linear = (LinearLayout) findViewById(R.id.AcGp);
		LinearLayout linearItems = (LinearLayout) findViewById(R.id.Items);
		LinearLayout linearMoreDetails = (LinearLayout) findViewById(R.id.LinearMoreDetails);
		DBHandler_Account dbhAccount = new DBHandler_Account(
				Activity_SettingAddNew.this);
		ArrayList<Object_Account> listItemGp = dbhAccount.getItemAccounts();
		ArrayList<String> ItemGpName = new ArrayList<String>();
		ItemGpName.add(listItemGp.get(0).name);
		/*
		 * for (Object_Account account : listItemGp) {
		 * if(account.itemCategories!=null){ for (Object_Item_Category itemCat :
		 * account.itemCategories) {
		 * 
		 * ItemGpName.add(itemCat.name); } }
		 * 
		 * }
		 */
		/*
		 * if (objItemGp.size() != 0) { for (int i = 0; i < objItemGp.size();
		 * i++) { String name = objItemGp.get(i).name; if (name != null) {
		 * ItemGpName.add(name); } } }
		 */
		Spinner spin = (Spinner) findViewById(R.id.spinnerParentGpAc);
		setspinnerData(spin, ItemGpName);
		linearMoreDetails.setVisibility(View.GONE);
		linearItems.setVisibility(View.GONE);
		linear.setVisibility(View.VISIBLE);

	}

	private void items() {
		LinearLayout linearRate = (LinearLayout) findViewById(R.id.RateLayout);
		CheckBox check = (CheckBox) findViewById(R.id.checkBox);
		LinearLayout linearammount = (LinearLayout) findViewById(R.id.AmountLayout);
		CheckBox checkAmount = (CheckBox) findViewById(R.id.checkBoxAmount);
		linearammount.setVisibility(View.GONE);
		checkAmount.setVisibility(View.GONE);
		linearRate.setVisibility(View.GONE);
		check.setVisibility(View.GONE);
		LinearLayout linear = (LinearLayout) findViewById(R.id.AcGp);
		LinearLayout linearItems = (LinearLayout) findViewById(R.id.Items);
		LinearLayout linearMoreDetails = (LinearLayout) findViewById(R.id.LinearMoreDetails);
		linear.setVisibility(View.GONE);
		linearMoreDetails.setVisibility(View.GONE);
		linearItems.setVisibility(View.VISIBLE);

		DBHandler_Account dbhAccount = new DBHandler_Account(
				Activity_SettingAddNew.this);
		ArrayList<Object_Account> listItemGp = dbhAccount.getItemAccounts();
		ArrayList<String> ItemGpName = new ArrayList<String>();
		int index = -1;
		for (Object_Account account : listItemGp) {
			if (account.itemCategories != null) {
				for (Object_Item_Category itemCat : account.itemCategories) {
					index++;
					ItemGpName.add(itemCat.name);
					mapGp.put(index, itemCat.id);
				}
			}

		}
		Spinner spin = (Spinner) findViewById(R.id.spinnerParentGpitem);
		setspinnerData(spin, ItemGpName);
		DBHandler_Unit DBunit = new DBHandler_Unit(Activity_SettingAddNew.this);
		ArrayList<Object_Unit> Listunit = DBunit.getAllUnits();
		ArrayList<String> ItemUnit = new ArrayList<String>();
		if (Listunit.size() != 0) {
			for (int i = 0; i < Listunit.size(); i++) {
				String name = Listunit.get(i).name;
				if (name != null) {
					ItemUnit.add(name);
					mapUnit.put(i, Listunit.get(i).id);
				}
			}
		}
		Spinner spinnerUnit = (Spinner) findViewById(R.id.spinnerUnit);
		setspinnerUnitData(spinnerUnit, ItemUnit);

		if (into.equals("Edit")) {
			setspinnerSelection(mapGp, spin, objItem.itemCatId);
			setspinnerSelection(mapUnit, spinnerUnit, objItem.unit.id);
		}

	}

	private void setspinnerSelection(HashMap<Integer, Integer> map,
			Spinner spin, int id) {

		Spinner spinner = (Spinner) findViewById(R.id.spinnerchooseAdd);
		((Spinner) spinner).getSelectedView().setEnabled(false);
		spinner.setEnabled(false);

		int spinSelection = -1;
		for (Entry<Integer, Integer> entry : map.entrySet()) {
			if (entry.getValue().equals(id)) {
				spinSelection = entry.getKey();
			}
		}

		if (spinSelection != -1)
			spin.setSelection(spinSelection);
	    }
}
