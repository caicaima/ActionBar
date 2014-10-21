package com.example.actionbar.broadcast;

import java.util.Calendar;

import com.example.actionbar.SendData;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

public class ScreenBroadCastReceiver extends BroadcastReceiver  {
private static  int count=0;
private Calendar c;
private static SharedPreferences preferences;

private SharedPreferences.Editor editor;
	@Override
	public void onReceive(Context context, Intent intent) {
		String  action = intent.getAction();
		 if(Intent.ACTION_USER_PRESENT.equals(action)){
			 preferences = context.getSharedPreferences("Rate", Context.MODE_PRIVATE);
			 editor = preferences.edit();
			 int i = ++count;
			 editor.putInt("rate", i);
			 editor.commit();
		}
	}
}
