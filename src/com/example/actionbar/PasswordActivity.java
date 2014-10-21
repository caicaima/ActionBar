package com.example.actionbar;

import java.util.Calendar;

import com.example.actionbar.service.LockService;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.PendingIntent;
import android.app.PendingIntent.CanceledException;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.widget.Chronometer;
import android.widget.Toast;

public class PasswordActivity extends Activity {
 private ActionBar mActionBar;
 private ActivityManager manager;
 private int mHour;
 private int mMinute;
 private int currentHour;
 private int currentMinute;
 private int oldTime;
 private int currentTime;
 private int oldDay;
 private int currentDay;
 ComponentName topActivity;
 public PasswordActivity() {
	mHour = Lock.getHour();
	mMinute = Lock.getMinute();
	oldDay  = Lock.getDay();
	System.out.println("mHour....."+mHour+"......mMinute..."+mMinute);
	judgeTime();
}
    private void judgeTime() {
	if(mMinute>60){
		mMinute = mMinute-60;
		mHour = mHour+1;
	}
	if(mHour>24){
		mHour=mHour-24;
		oldDay = oldDay+1;
	}
	System.out.println("mHour.....2..."+mHour+"......mMinute...2..."+mMinute);
}
	@Override
    protected void onCreate(Bundle savedInstanceState) {
    	// TODO Auto-generated method stub
    	super.onCreate(savedInstanceState);
//    	mActionBar = MainActivity.getMainActionBar();
//    	mActionBar.hide();
    	setContentView(R.layout.activity_password);
    
    	Calendar c = Calendar.getInstance();
    	currentHour = c.get(Calendar.HOUR_OF_DAY);
    	currentMinute = c.get(Calendar.MINUTE);
    	currentDay = c.get(Calendar.DAY_OF_YEAR);
    	oldTime = mHour*60+mMinute;
    	currentTime = currentHour*60+currentMinute;
    	System.out.println("oldTime......"+oldTime+"......currentTime...."+currentTime);
    
      
    	if(currentTime-oldTime>=0 && currentDay == oldDay){
    		
    		this.stopService(new Intent(PasswordActivity.this, LockService.class));
    	}else{
    		Toast.makeText(this, "亲，还不到时间哦~", Toast.LENGTH_LONG).show();
    	}
    }
    
    
                
    @Override
    public void onBackPressed() {
    	manager = (ActivityManager) this.getSystemService(ACTIVITY_SERVICE);
    	
			
			
		        topActivity = manager.getRunningTasks(2).get(1).topActivity;
		        
		        System.out.println("topActivity...."+topActivity);	
//		        String packageName = topActivity.getPackageName();
//		        manager.killBackgroundProcesses(packageName);     
//		        String className = topActivity.getClassName();
//		        System.out.println("packageName:"+packageName+".....className:"+className);
//		        ComponentName cn = new ComponentName(packageName, className);
//		        Intent intent = new Intent();
//		        intent.setComponent(cn);
//		        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
//		        startActivityForResult(intent, RESULT_CANCELED);
//		        intent.setClassName(packageName, className);
//		        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
//		        
//		        PendingIntent pIntent = PendingIntent.getActivity(PasswordActivity.this, 0, intent,  PendingIntent.FLAG_CANCEL_CURRENT);
//		        try {
//					pIntent.send();
//					
//					System.out.println("pIntent:执行了");
//				} catch (CanceledException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//				
//		
		        this.finish();
    	System.out.println("onBackPressed执行");
    	System.out.println("进程已杀死");
    	
    	
    	super.onBackPressed();
    }
    @Override
    	protected void onPause() {
    	this.finish();
    		System.out.println("onPause执行");
    		super.onPause();
    	}
}
