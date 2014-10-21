package com.example.actionbar;

import com.example.actionbar.broadcast.ScreenBroadCastReceiver;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

public class Setting extends Fragment implements OnClickListener{
	ScreenBroadCastReceiver receiver ;
	 
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		IntentFilter filter = new IntentFilter();
	     receiver = new ScreenBroadCastReceiver();
		filter.addAction(Intent.ACTION_USER_PRESENT);
		getActivity().registerReceiver(receiver, filter);
	}
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.settings, container, false);
		RelativeLayout useRate = (RelativeLayout) view.findViewById(R.id.use_rate);
		RelativeLayout useTime = (RelativeLayout) view.findViewById(R.id.use_time);
		RelativeLayout screenBright = (RelativeLayout) view.findViewById(R.id.screen_bright);
		useRate.setOnClickListener(this);
		useTime.setOnClickListener(this);
		screenBright.setOnClickListener(this);
		return view;
	}
	@Override
	public void onClick(View v) {
		int id = v.getId();
		switch (id) {
		case R.id.use_rate:
			Intent in  = new Intent(getActivity(), UseRate.class);
			startActivity(in);
			break;
		case R.id.use_time:
			
			break;
		case R.id.screen_bright:
			
			break;
		}
		
	}


}
