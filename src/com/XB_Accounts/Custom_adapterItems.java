package com.XB_Accounts;

import java.util.ArrayList;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

public class Custom_adapterItems extends BaseExpandableListAdapter {

	private final ArrayList<Object_Account> listCategory;
	public LayoutInflater inflater;
	public Activity_accountChooser activity;
	int lastgroupPosition = 0;
	private final int DEFAULT_PADDING_ROW = 8;
	private int rowHeight = 35;
	int selectedChild;
	int selectedParrent;
	private float fontSize;
	private View view;
	private Custom_adapterGrid_Items adapter;
	public static ArrayList<Object_Item> listAccounts;
	private Object_Account objAccount;
	public Custom_adapterItems(Activity_accountChooser act,
			ArrayList<Object_Account> list, float fontSize) {
		activity = act;
		listCategory = list;
		// listView = listview;
		this.fontSize = fontSize;
		inflater = act.getLayoutInflater();
		rowHeight = Globals.getScreenSize(activity).y / 16;
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {

		if (listCategory != null && listCategory.size() > groupPosition)
			if (listCategory.get(groupPosition) != null
					&& listCategory.get(groupPosition).itemCategories != null)
				if (listCategory.get(groupPosition).itemCategories.size() > childPosition)
					return listCategory.get(groupPosition).itemCategories
							.get(childPosition);

		return null;
	}

	/*
	 * public void updateNewNewsForCategory(int catId) {
	 * 
	 * activity.toggle(); //Globals.showSpinnerNews(activity.mDialog, activity);
	 * activity.getNewsDataFromServer(catId,Globals.CALLTYPE_FRESH,
	 * 0,false,Globals.FINAL_NEWS_LIMIT_FIRST_CALL);
	 * 
	 * System.out.println("child id : " + catId);
	 * 
	 * }
	 */

	@Override
	public int getChildrenCount(int groupPosition) {

		// System..out.println("getChild count Called!");

		if (listCategory != null && listCategory.size() > groupPosition)
			if (listCategory.get(groupPosition).itemCategories != null)
				return listCategory.get(groupPosition).itemCategories.size();

		return 0;
	}

	@Override
	public Object getGroup(int groupPosition) {

		// System..out.println("get group Called!");
		if (listCategory != null && listCategory.size() > groupPosition)
			return listCategory.get(groupPosition);

		return null;
	}

	@Override
	public int getGroupCount() {

		if (listCategory != null)
			return listCategory.size();

		return 0;
	}

	@Override
	public long getGroupId(int groupPos) {

		if (listCategory != null && listCategory.size() > groupPos)
			if (listCategory.get(groupPos) != null)
				return listCategory.get(groupPos).id;
		return 0;
	}

	@Override
	public void onGroupCollapsed(int groupPosition) {

		if (getChildrenCount(groupPosition) < 1) {

			Log.i("SUSHIL", "onGroupExpanded");

		}

		super.onGroupCollapsed(groupPosition);

	}

	@Override
	public void onGroupExpanded(int groupPosition) {
		if (groupPosition != lastgroupPosition) {
			activity.expList.collapseGroup(lastgroupPosition);

		}
		if (getChildrenCount(groupPosition) < 1) {

			// updateNewNewsForCategory(group_cat_id);
			Log.i("SUSHIL", "onGroupExpanded");

		}
		super.onGroupExpanded(groupPosition);
		lastgroupPosition = groupPosition;
	}

	@Override
	public View getChildView(final int groupPosition, final int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
        
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.row_item_categories, null);
		}

		final Object_Item_Category childCat = (Object_Item_Category) getChild(
				groupPosition, childPosition);
	
		// Log.i("SUSHIL","child cat is "+childCat.name);
		if (childCat == null) {

			return convertView;
		}

		
		
		/*
		 * ImageView indicator = ((ImageView) convertView
		 * .findViewById(R.id.img_GroupIndicator));
		 * indicator.setVisibility(View.GONE);
		 */
		/*
		 * ImageView imgViewChild = (ImageView) convertView
		 * .findViewById(R.id.img_GroupCatgeory);
		 * 
		 * if(childCat.getImageName() != null &&
		 * !childCat.getImageName().trim().isEmpty())
		 * Globals.loadImageIntoImageView(imgViewChild, childCat.getImageName(),
		 * activity, R.drawable.loading_image_small,
		 * R.drawable.category_default); else
		 * imgViewChild.setImageResource(R.drawable.category_default);
		 */

		TextView tv = (TextView) convertView.findViewById(R.id.txt_GroupText);
		tv.setText(childCat.name);
		tv.setTextSize(Globals.getAppFontSize_Small(activity));
		tv.getLayoutParams().height = rowHeight;

		/*
		 * ImageView indicator = ((ImageView) convertView
		 * .findViewById(R.id.img_GroupIndicator));
		 */
		// indicator.setImageResource(R.color.app_transparent);

		if (selectedParrent != groupPosition) {
			convertView.setBackground(activity.getResources().getDrawable(
					R.drawable.list_selecter));
		}

		// childlist click
		convertView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				
		          ArrayList<Object_Item_Category> list = new ArrayList<Object_Item_Category>();
				// v.setBackgroundDrawable(activity.getResources().getDrawable(R.drawable.list_selecter));
				/*
				 * // TODO Auto-generated method stub dataSet = new
				 * Custom_accountDataset();
				 * 
				 * String sectionOne = "Sushil"; String sectionTwo = "JASPAL";
				 * String sectionThree = "Vikas"; String sectionFour =
				 * "Abhinav"; String sectionFive = "Pankaj";
				 * 
				 * dataSet.addSection(sectionOne, 5);
				 * dataSet.addSection(sectionTwo, 7);
				 * dataSet.addSection(sectionThree, 10);
				 * dataSet.addSection(sectionFour, 1);
				 * dataSet.addSection(sectionFive, 9);
				 * 
				 * cursorMap = dataSet.getSectionCursorMap(); // treeItem();
				 * Log.i("SUSHIL","call for adaptor");
				 */
				list.add(childCat);
				objAccount.itemCategories = list;
				listAccounts = childCat.items;

				if (listAccounts != null) {
					adapter = new Custom_adapterGrid_Items(activity, listAccounts,
							fontSize,objAccount);
					activity.gridView.setAdapter(adapter);
				} else {
					ArrayList<Object_Item> listAccount = new ArrayList<Object_Item>();
					adapter = new Custom_adapterGrid_Items(activity, listAccount,
							fontSize,objAccount);
					activity.gridView.setAdapter(adapter);
				}
				try {
					if (selectedParrent == groupPosition) {
						v.setBackgroundColor(activity.getResources().getColor(
								R.color.WhiteSmoke));
						if (selectedChild != childPosition) {
							view.setBackground(activity.getResources()
									.getDrawable(R.drawable.list_selecter));
						} else {
							v.setBackgroundColor(activity.getResources()
									.getColor(R.color.WhiteSmoke));
						}
					} else {
						selectedChild = -1;
						selectedParrent = -1;
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				view = v;
				selectedChild = childPosition;
				selectedParrent = groupPosition;
			}

		});

		convertView.setPadding(DEFAULT_PADDING_ROW * 4, DEFAULT_PADDING_ROW,
				DEFAULT_PADDING_ROW, DEFAULT_PADDING_ROW);

		return convertView;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {

		if (convertView == null) {
			convertView = inflater.inflate(R.layout.row_item_categories, null);
		}

		Object obj = getGroup(groupPosition);
		Object_Account cat;

		if (obj != null){
			cat = (Object_Account) obj;
		    objAccount = cat;
		}
		else
			return convertView;

		// Log.i("HARSH", "getGroupView "+cat.getId() +" Position" +
		// groupPosition);
		TextView tv = (TextView) convertView.findViewById(R.id.txt_GroupText);
		tv.setText(cat.name);
		tv.setTextSize(fontSize);
		tv.getLayoutParams().height = rowHeight;
       Log.i("SUSHIL", "size is child "+cat.itemCategories.size());
		/*
		 * ImageView img = (ImageView) convertView
		 * .findViewById(R.id.img_GroupCatgeory);
		 * 
		 * if(cat.getImageName() != null &&
		 * !cat.getImageName().trim().isEmpty())
		 * Globals.loadImageIntoImageView(img, cat.getImageName(), activity,
		 * R.drawable.loading_image_small, R.drawable.category_default); else
		 * img.setImageResource(R.drawable.category_default);
		 */

		/*
		 * if (getChildrenCount(groupPosition) > 0) { ImageView indicator =
		 * ((ImageView) convertView .findViewById(R.id.img_GroupIndicator));
		 * indicator.setVisibility(View.VISIBLE);
		 * //indicator.setImageResource(isExpanded ?
		 * Custom_ThemeUtil.getExpandImageId(activity):
		 * Custom_ThemeUtil.getNextImageId(activity));
		 * //indicator.setImageDrawable(isExpanded ?
		 * Custom_ThemeUtil.getDrawableExpandImage(activity) :
		 * Custom_ThemeUtil.getDrawableNextImage(activity));
		 * 
		 * } else { ImageView indicator = ((ImageView) convertView
		 * .findViewById(R.id.img_GroupIndicator));
		 * indicator.setVisibility(View.GONE);
		 * 
		 * }
		 */
		
		   
         /*convertView.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
		if(listgp==null && listgp.size()== 0){
			if(listAccounts!=null && listAccounts.size()!=0){
				adapter = new custom_adapterGrid(activity, listAccounts,
						fontSize);
				activity.gridView.setAdapter(adapter);
			  }
			}
		}
	});*/
		convertView.setPadding(DEFAULT_PADDING_ROW, DEFAULT_PADDING_ROW,
				DEFAULT_PADDING_ROW, DEFAULT_PADDING_ROW);
		// Log.i("SUSHIL","getGroupView " + groupPosition);

		return convertView;
	}

	@Override
	public boolean hasStableIds() {
		return false;
	}

	@Override
	public boolean isChildSelectable(int groupID, int childId) {

		/*
		 * activity.listView.getViewTreeObserver().addOnGlobalLayoutListener(
		 * new OnGlobalLayoutListener() {
		 * 
		 * @Override public void onGlobalLayout() {
		 * activity.listView.getViewTreeObserver()
		 * .removeGlobalOnLayoutListener(this);
		 * Log.i("SUSHIL","call for adaptor"); // now check the width of the
		 * list view int width = activity.listView.getWidth();
		 * SectionedGridViewAdapter adapter = new SectionedGridViewAdapter(
		 * activity,cursorMap, activity.listView .getWidth(),
		 * activity.listView.getHeight(), gridItemSquareSize);
		 * activity.listView.setAdapter(adapter);
		 * activity.listView.setDividerHeight(adapter
		 * .gapBetweenChildrenInRow()); } });
		 */
		Log.i("SUSHIL", "click on child");
		try {
			View v = getChildView(groupID, childId, isEmpty(), null, null);
			v.setBackground(activity.getResources().getDrawable(
					R.color.WhiteSmoke));
			if (selectedChild != childId) {
				v.setBackground(activity.getResources().getDrawable(
						R.color.WhiteSmoke));
				View view = getChildView(selectedParrent, selectedChild,
						isEmpty(), null, null);
				view.setBackground(activity.getResources().getDrawable(
						R.drawable.list_selecter));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		selectedChild = childId;
		selectedParrent = groupID;
		return true;
	}

	@Override
	public long getChildId(int groupPos, int childPos) {
		if (listCategory != null && listCategory.size() > groupPos)
			if (listCategory.get(groupPos).itemCategories != null
					&& listCategory.get(groupPos).itemCategories.size() > childPos)
				if (listCategory.get(groupPos).itemCategories.get(childPos) != null)
					return listCategory.get(groupPos).itemCategories
							.get(childPos).id;

		return 0;
	}
}
