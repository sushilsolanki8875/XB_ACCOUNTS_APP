package com.XB_Accounts;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;

import jxl.HeaderFooter;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

public class Activity_printPreview extends Activity {
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_print_preview);
		Intent i = getIntent();
		if(i!=null){
		if(i.getStringExtra("Sheet").equals("balance")){
			String type = i.getStringExtra("type");
			DBHandler_Txn dbTxn = new DBHandler_Txn(this);
		if(type.equals("all")){
			ArrayList<Object_balanceSheet>list = dbTxn.balanceSheet("all","");
            excelBalancesheet(list);
			}else if(type.equals("loan")){
				ArrayList<Object_balanceSheet>list = dbTxn.balanceSheet("loan","15,18,20,21");
	            excelBalancesheet(list);
			}else if(type.equals("capital")){
				ArrayList<Object_balanceSheet>list = dbTxn.balanceSheet("capital","1,2");
	            excelBalancesheet(list);
			}else if(type.equals("bank")){
				ArrayList<Object_balanceSheet>list = dbTxn.balanceSheet("bank","14");
	            excelBalancesheet(list);
			}else if(type.equals("sun")){
				ArrayList<Object_balanceSheet>list = dbTxn.balanceSheet("sun","13");
	            excelBalancesheet(list);
			}
		}
		else if(i.getStringExtra("Sheet").equals("purchase")){
	    DBHandler_Txn txn = new DBHandler_Txn(this);
        ArrayList<Object_SheetPurchase> list = txn.getPurchaseSaleItems();
		  purchaseSaleItem(list);
		}
		}
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		Object_AppConfig config = new Object_AppConfig(this);
		Globals.setLocale(config.getLocale(), this);
		
	}
	
	private void excelBalancesheet(ArrayList<Object_balanceSheet>list) {
		String filename = "Balance Sheet";
	    String type = "Balance Sheet";
		int widthInChars = 10;
		String Fnamexls = filename + ".xls";
		
		String[] acc = { "SR.NO", "NAME ACCOUNT", "AC", "BALANCE", "CREDIT",
				"DEBIT", "INTERSET", "BALANCE"};
		File sdCard = Environment.getExternalStorageDirectory();

		File directory = new File(sdCard.getAbsolutePath() + "/Accounts");
		directory.mkdirs();

		File file = new File(directory, Fnamexls);

		WorkbookSettings wbSettings = new WorkbookSettings();
		wbSettings.setLocale(new Locale("en", "EN"));

		WritableWorkbook workbook;
		try {
			int a = 1;
			workbook = Workbook.createWorkbook(file, wbSettings);
			WritableSheet sheet = workbook.createSheet("Balence Sheet", 0);
			HeaderFooter header = new HeaderFooter();
		      header.getLeft().appendWorkbookName();
		      header.getCentre().append("BE THE CODER");
		      header.getRight().appendWorkSheetName();
		      sheet.getSettings().setHeader(header);
		      sheet.getSettings().setPageBreakPreviewMode(true);
		      sheet.getSettings().setShowGridLines(true);
		      sheet.getSettings().setPrintGridLines(true);
		      
			Label lable2 = new Label(0, 0, type+" 3 KSD GSSS LTD 3KSD",
					getCellFormat());
			sheet.addCell(new Label(3, 1, "31-03-2014", getCellFormat()));
			
			sheet.mergeCells(0, 0, 1, 0);
			sheet.mergeCells(4, 1, 7, 1);
			sheet.mergeCells(8, 1, 11, 1);
			sheet.mergeCells(12, 1, 15, 1);

			for (int i = 0; i < acc.length; i++) {

				Label label1 = new Label(i, 2, acc[i], getCellFormat());

				try {
					sheet.setColumnView(i, widthInChars);
					sheet.addCell(label1);
					sheet.addCell(lable2);
					sheet.setColumnView(0, widthInChars);

				} catch (RowsExceededException e) {
					e.printStackTrace();

				} catch (WriteException e) {
					e.printStackTrace();
				}

			}
			
			for (int i = 0; i < list.size(); i++) {
				
				Label lab = new Label(0,i+3, (i+1)+"");
				Label labName = new Label(1,i+3,list.get(i).name);
				Label labAc = new Label(2,i+3,list.get(i).accountId+"");
				Label preBalance = new Label(3, i+3, "-");
				Label credit = null;
				Label debit = null;
				Label inrest = new Label(6, i+3, "-");
				Label total = null;
				
				if(list.get(i).credit+""!=null){
				credit = new Label(4, i+3,list.get(i).credit+"");
				}else{
				credit = new Label(4, i+3, "-");
				}
				if(list.get(i).debit+""!=null){
					debit = new Label(5, i+3,list.get(i).debit+"");
					}else{
					debit = new Label(5, i+3, "-");
					}
				
				
				//this is total
				if(list.get(i).credit+""!=null && list.get(i).debit+""!=null){
					total = new Label(7,i+3,(list.get(i).debit-list.get(i).credit)+"");
				}else if(list.get(i).credit+"" == null){
					total = new Label(7,i+3,list.get(i).debit+"");
				}else if(list.get(i).debit+"" == null){
					total = new Label(7,i+3,"-"+list.get(i).credit);
				}else{
					total = new Label(7,i+3,"-");
				}
				//Label labAmount = new Label(0,i+3,listAc.get(i).accountTxn.amount+"");
				try {
					sheet.setColumnView(0, 5);
					sheet.setColumnView(1, 20);
					sheet.setColumnView(2, 5);
					sheet.setColumnView(3, 15);
					sheet.setColumnView(4, 15);
					sheet.setColumnView(5, 15);
					sheet.setColumnView(6, 15);
					sheet.setColumnView(7, 15);
					sheet.addCell(lab);
					sheet.addCell(labName);
					sheet.addCell(labAc);
					sheet.addCell(preBalance);
					sheet.addCell(credit);
					sheet.addCell(debit);
					sheet.addCell(inrest);
					sheet.addCell(total);
					
					//sheet.addCell(labAmount);
					//sheet.addCell(lable2);
					sheet.setColumnView(0, widthInChars);

				} catch (RowsExceededException e) {
					e.printStackTrace();

				} catch (WriteException e) {
					e.printStackTrace();
				}
				
			}
			
			
		workbook.write();

			try {
				workbook.close();
				showExcel(file);
			} catch (WriteException e) {

				e.printStackTrace();
			}

		} catch (IOException e) {
			e.printStackTrace();
		} catch (RowsExceededException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (WriteException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}
	
	/*private void excelpurchaseSheet(){
		String filename = "Purchase & Sale Sheet";
	    String type = "Purchase & Sale Sheet";
		int widthInChars = 20;
		int merge = 0;
		String Fnamexls = filename + ".xls";
		String[] mainHead = { "SR.NO", "NAME OF GOODS", "UNIT", "OPENING STOCK", "PURCHASE",
				"TOTAL", "SALE", "LAST STOCK","PROFIT/LOSS"};
		String[] subHead = {"QTY","RATE","AMOUNT"};
		File sdCard = Environment.getExternalStorageDirectory();

		File directory = new File(sdCard.getAbsolutePath() + "/Accounts");
		directory.mkdirs();

		File file = new File(directory, Fnamexls);

		WorkbookSettings wbSettings = new WorkbookSettings();
		wbSettings.setLocale(new Locale("en", "EN"));

		WritableWorkbook workbook;
		try {
			int a = 1;
			workbook = Workbook.createWorkbook(file, wbSettings);
			WritableSheet sheet = workbook.createSheet(type, 0);
			HeaderFooter header = new HeaderFooter();
		      header.getLeft().appendWorkbookName();
		      header.getCentre().append(type+" 3 KSD GSSS LTD 3KSD");
		      header.getRight().appendWorkSheetName();
		      sheet.getSettings().setHeader(header);
		      sheet.getSettings().setPageBreakPreviewMode(true);
		      sheet.getSettings().setShowGridLines(true);
		      sheet.getSettings().setPrintGridLines(true);
		      
			sheet.mergeCells(0, 0, 1, 0);
			sheet.mergeCells(4, 1, 7, 1);
			sheet.mergeCells(8, 1, 11, 1);
			sheet.mergeCells(12, 1, 15, 1);
		      sheet.mergeCells(3,0,5, 0);
		      sheet.mergeCells(6,0,8, 0);
		      sheet.mergeCells(9,0,11, 0);
		      sheet.mergeCells(12,0,14, 0);
		      sheet.mergeCells(15,0,17, 0);
		      //sheet.mergeCells(18,0,20, 0);
			for(int i = 0;i<mainHead.length;i++){
				Label label1 = null;
				if(i>2){
					if(mainHead.length-1==i){
						label1 = new Label((i+merge),0, mainHead[i], getCellFormat());
					}else{
			      label1 = new Label((i+merge),0, mainHead[i], getCellFormat());
			      
			      
			      if(i!=mainHead.length-1){
			      Label sub = null;
			      //if(i==3){
			      int place = (i+merge);
			       for(int j=0;j<3;j++){
			    	  Log.i("SUSHIL", "Merge"+place);
						sub = new Label(place,1, subHead[j]);
						try {
							if(sub!=null){
							//sheet.setColumnView(i, widthInChars);
							sheet.addCell(sub);
							//sheet.setColumnView(0, widthInChars);
							}

						} catch (RowsExceededException e) {
							e.printStackTrace();

						} catch (WriteException e) {
							e.printStackTrace();
						}
						place++;
						
			        }
			      }
			       merge = merge+2;
			      }else{
						label1 = new Label(i,0, mainHead[i], getCellFormat());
					}
			      
			     
				try {
					sheet.setColumnView(i, widthInChars);
					sheet.addCell(label1);
					
				} catch (RowsExceededException e) {
					e.printStackTrace();

				} catch (WriteException e) {
					e.printStackTrace();
				}	
			}
			workbook.write();

			try {
				workbook.close();
				showExcel(file);
			} catch (WriteException e) {

				e.printStackTrace();
			}
		
		
	} catch (IOException e) {
		e.printStackTrace();
	} catch (RowsExceededException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	} catch (WriteException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
		
	}*/
	
	private void purchaseSaleItem(ArrayList<Object_SheetPurchase> list){
		String filename = "Purchase & Sale Sheet";
	    String type = "Purchase & Sale Sheet";
		int widthInChars = 10;
		String Fnamexls = filename + ".xls";
		String[] mainHead = { "SR.NO", "NAME OF ITEMS", "UNIT", "TYPE", "QTY",
				"PRICE", "AMOUNT"};
		
		File sdCard = Environment.getExternalStorageDirectory();

		File directory = new File(sdCard.getAbsolutePath() + "/Accounts");
		directory.mkdirs();

		File file = new File(directory, Fnamexls);

		WorkbookSettings wbSettings = new WorkbookSettings();
		wbSettings.setLocale(new Locale("en", "EN"));

		WritableWorkbook workbook;
		try {
			int a = 1;
			workbook = Workbook.createWorkbook(file, wbSettings);
			WritableSheet sheet = workbook.createSheet(type, 0);
			HeaderFooter header = new HeaderFooter();
		      header.getLeft().appendWorkbookName();
		      header.getCentre().append(type+" 3 KSD GSSS LTD 3KSD");
		      header.getRight().appendWorkSheetName();
		      sheet.getSettings().setHeader(header);
		      sheet.getSettings().setPageBreakPreviewMode(true);
		      sheet.getSettings().setShowGridLines(true);
		      sheet.getSettings().setPrintGridLines(true);
		      
		      for (int i = 0; i < mainHead.length; i++) {

					Label label1 = new Label(i, 2, mainHead[i], getCellFormat());

					try {
						sheet.setColumnView(i, widthInChars);
						sheet.addCell(label1);
						//sheet.addCell(lable2);
						sheet.setColumnView(0, widthInChars);

					} catch (RowsExceededException e) {
						e.printStackTrace();

					} catch (WriteException e) {
						e.printStackTrace();
					}

				}
		      double AmountP = 0;
		      String itemName = "";
		      for (int i = 0; i <=list.size(); i++) {
		    	  if(i!=list.size()){
		    	    Label lab = new Label(0,i+3, (i+1)+"");
		    	    Label labName;
		    	    
		    	    if(!itemName.equals(list.get(i).nameItem)){
		    	    	labName = new Label(1,i+3,list.get(i).nameItem);
		    	    	itemName = list.get(i).nameItem;
		    	    }else{
		    	        labName = new Label(1,i+3,"");
		    	    }
					Label labUnitName = new Label(2,i+3,list.get(i).unitName);
					//double amount = 0;
					Label type_txn = null;
					Label qty = null;
					Label price = null;
					Label Amount= null;
					if(list.get(i).txnType_id!= 0){
						if(list.get(i).txnType_id==1){
							type_txn = new Label(3,i+3,"Sale");
							qty = new Label(4,i+3,list.get(i).saleqty+"");
							price = new Label(5,i+3,list.get(i).saleprice+"");
							Amount = new Label(6,i+3,(list.get(i).saleprice*list.get(i).saleqty)+"");
							AmountP = AmountP + (list.get(i).saleprice*list.get(i).saleqty);
						}else if(list.get(i).txnType_id==2){
							type_txn = new Label(3,i+3,"Purchase");
							qty = new Label(4,i+3,list.get(i).purchaseqty+"");
							price = new Label(5,i+3,list.get(i).purchaseprice+"");
							Amount = new Label(6,i+3,"-"+(list.get(i).purchaseprice*list.get(i).purchaseqty));
							AmountP = AmountP - (list.get(i).purchaseprice*list.get(i).purchaseqty);
						}
					}else{
						type_txn = new Label(3,i+3,"-");
						qty = new Label(4,i+3,"-");
						price = new Label(5,i+3,"-");
						Amount = new Label(6,i+3,"-");
					}
		    	  
					try {
						
						sheet.addCell(lab);
						sheet.addCell(labName);
						sheet.addCell(labUnitName);
						sheet.addCell(type_txn);
						sheet.addCell(qty);
						sheet.addCell(price);
						sheet.addCell(Amount);
						//sheet.addCell(labAmount);
						//sheet.addCell(lable2);
						sheet.setColumnView(0, widthInChars);

					} catch (RowsExceededException e) {
						e.printStackTrace();

					} catch (WriteException e) {
						e.printStackTrace();
					}
		    	  }else{
		    		  Label lab1 = new Label(4,i+3,"PROFIT/LOSS"); 
		    		  Label Amount1 = new Label(6,i+3,AmountP+""); 
		    		  
		    		  try {
							
							sheet.addCell(lab1);
							sheet.addCell(Amount1);
							
							//sheet.addCell(labAmount);
							//sheet.addCell(lable2);
							sheet.setColumnView(0, widthInChars);

						} catch (RowsExceededException e) {
							e.printStackTrace();

						} catch (WriteException e) {
							e.printStackTrace();
						}
		    	  }
		      }
		      
		      workbook.write();

				try {
					workbook.close();
					showExcel(file);
				} catch (WriteException e) {

					e.printStackTrace();
				}  
		      
		      
		} catch (IOException e) {
			e.printStackTrace();
		} catch (RowsExceededException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (WriteException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	}
	

	private static WritableCellFormat getCellFormat() throws WriteException {
		WritableFont font = new WritableFont(WritableFont.TIMES, 10,
				WritableFont.BOLD);
		WritableCellFormat cellFormat = new WritableCellFormat(font);
		cellFormat.setAlignment(jxl.format.Alignment.CENTRE);
		return cellFormat;
	}

	private void showExcel(File file) {
		if (file != null) {
			Uri path = Uri.fromFile(file);
			Intent intent = new Intent(Intent.ACTION_VIEW);
			intent.setDataAndType(path, "application/vnd.ms-excel");
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

			try {
				startActivity(intent);
			} catch (ActivityNotFoundException e) {
				Toast.makeText(this, "No Application Available to View Excel",
						Toast.LENGTH_SHORT).show();
			}
		} else {
			Toast.makeText(this, "No File Available to View",
					Toast.LENGTH_SHORT).show();
		}
	}

	/*
	 * public List<String> read(String key) throws IOException { List<String>
	 * resultSet = new ArrayList<String>();
	 * 
	 * File inputWorkbook = new File(inputFile); if(inputWorkbook.exists()){
	 * Workbook w; try { w = Workbook.getWorkbook(inputWorkbook); // Get the
	 * first sheet Sheet sheet = w.getSheet(0); // Loop over column and lines
	 * for (int j = 0; j < sheet.getRows(); j++) { Cell cell = sheet.getCell(0,
	 * j); if(cell.getContents().equalsIgnoreCase(key)){ for (int i = 0; i <
	 * sheet.getColumns(); i++) { Cell cel = sheet.getCell(i, j);
	 * resultSet.add(cel.getContents()); } } continue; } } catch (BiffException
	 * e) { e.printStackTrace(); } catch (Exception e) { e.printStackTrace(); }
	 * } else { resultSet.add("File not found..!"); } if(resultSet.size()==0){
	 * resultSet.add("Data not found..!"); } return resultSet; }
	 */
	/*public void next(View v) {
		Intent i = new Intent(this, NextActivity.class);
		startActivity(i);
	}
	public void Third(View v) {
		Intent i = new Intent(this, ThirdActivity.class);
		startActivity(i);
	}
	public void Fourth(View v) {
		Intent i = new Intent(this, FourthActivity.class);
		startActivity(i);
	}
	public void Fifth(View v) {
		Intent i = new Intent(this, FifthActivity.class);
		startActivity(i);
	}*/

	
}
