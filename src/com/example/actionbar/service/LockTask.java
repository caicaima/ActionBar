package com.example.actionbar.service;

import java.util.Map;
import java.util.TimerTask;

import com.example.actionbar.Lock;
import com.example.actionbar.PasswordActivity;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

public class LockTask extends TimerTask {
  private Context mContext;
  private ActivityManager mActivityManager;//Interact with the overall activities running in the system
  private Map<String, ?> map;
  
	public LockTask(Context context) {
		this.mContext = context;
		//得到运行的Task
		mActivityManager  = (ActivityManager) mContext.getSystemService("activity");
		getData();
	}
	private void getData() {
		SharedPreferences getPreferences = this.mContext.getSharedPreferences("packageName", Context.MODE_PRIVATE);
//		SharedPreferences.Editor editor = getPreferences.edit();
		map = getPreferences.getAll();
		for(int i = 0;i<map.size();i++)
			//得到存储在map里的对象，此处为包名
		System.out.println("LockTask 32行 map的值为。。"+map.get(i+""));
		
	}
	@Override
	public void run() {
		//得到运行栈里最上层的task(即正在运行 的Activity)
		ComponentName topActivity = mActivityManager.getRunningTasks(1).get(0).topActivity;
        String nowPackageName =  topActivity.getPackageName();
//        System.out.println("LockTask.java 28行 nowPackageName.."+nowPackageName);
//        System.out.println("LockTask.java 30行，选中的PackageName..."+Lock.packageName);
       
        
        
        
        
        
        
        for(int i = 0;i<map.size();i++){
        if(nowPackageName.equals(map.get(i+""))){
        	Intent intent = new Intent();
        	intent.setClassName("com.example.actionbar", "com.example.actionbar.PasswordActivity");
        	intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        	
        	mContext.startActivity(intent);
        	System.out.println("LockTask.java 38行,拦截页面一启动");
      }
        }

	}

}
