package com.example.actionbar;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class News extends Fragment {
	
	private ActionBar mActinBar;
	private Context mContext;
	
	
	
	@Override
		public void onCreate(Bundle savedInstanceState) {
			// TODO Auto-generated method stub
			super.onCreate(savedInstanceState);
			this.mContext = getActivity();
			this.mActinBar = MainActivity.getMainActionBar();
			
		}
  @Override
public View onCreateView(LayoutInflater inflater, ViewGroup container,
		Bundle savedInstanceState) {
	  mActinBar.setTitle(R.string.title_news);
	View view = inflater.inflate(R.layout.news, container, false);
	return view;
   }
  
}
