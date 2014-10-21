package com.example.actionbar;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

public class UseRate extends Activity {
   @Override
protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.use_rate);
	TextView tv = (TextView) findViewById(R.id.rate);
	SharedPreferences preferences = getSharedPreferences("Rate", Context.MODE_PRIVATE);
	int rate  = preferences.getInt("rate", 0);
	
	tv.setText(rate+"´Î/Ìì");
}
}
