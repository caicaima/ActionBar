package com.example.actionbar.adapter;

import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.actionbar.R;

public class AppListAdapter extends BaseAdapter {
 private Context mContext;
 private List<ResolveInfo>allApps;
 private LayoutInflater mInflater;
 private PackageManager mPManager;

 private static HashMap<Integer,Boolean> isSelected;
 

 
 
	public AppListAdapter(Context mContext, List<ResolveInfo> allApps,PackageManager pm) {
		this.mContext = mContext;
		this.allApps = allApps;
		mPManager = pm;
		mInflater = LayoutInflater.from(this.mContext);
//		isSelected = new HashMap<Integer,Boolean>(0);
	   isSelected = new HashMap<Integer,Boolean>();
	   //初始化数据
	   initData();
	}
     private void initData() {

        for(int i= 0;i<allApps.size();i++){
        	getSelected().put(i, false);
        }
		
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return allApps.size();
	}

	@Override
	public Object getItem(int position) {
		// 返回条目对象
		return allApps.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
     ViewHolder holder;
         if(convertView==null){
        	 holder = new ViewHolder();
        	 convertView = mInflater.inflate(R.layout.list_item, null, false);
        	 holder.img = (ImageView) convertView.findViewById(R.id.app_icon);
        	 holder.txt = (TextView) convertView.findViewById(R.id.app_name);
        	 holder.cb = (CheckBox) convertView.findViewById(R.id.checkbox);
        	 convertView.setTag(holder);
         }else{
        	 holder = (ViewHolder) convertView.getTag();
         }
         ResolveInfo res = allApps.get(position);
         holder.img.setImageDrawable(res.loadIcon(mPManager));
         holder.txt.setText(res.loadLabel(mPManager).toString());
         //设置checkBox的状态
         //
         //
         holder.cb.setChecked(getSelected().get(position));
         return convertView;
	}
	
	public static HashMap<Integer,Boolean> getSelected(){
		return isSelected;
		
	}
	//设置属性
	public static void  setSelect(HashMap<Integer,Boolean>isSelected){
		AppListAdapter.isSelected = isSelected;
	}
 public static class ViewHolder{
	ImageView img;
	TextView txt;
	public CheckBox cb;
   }
}
