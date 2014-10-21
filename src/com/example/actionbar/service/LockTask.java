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
		//�õ����е�Task
		mActivityManager  = (ActivityManager) mContext.getSystemService("activity");
		getData();
	}
	private void getData() {
		SharedPreferences getPreferences = this.mContext.getSharedPreferences("packageName", Context.MODE_PRIVATE);
//		SharedPreferences.Editor editor = getPreferences.edit();
		map = getPreferences.getAll();
		for(int i = 0;i<map.size();i++)
			//�õ��洢��map��Ķ��󣬴˴�Ϊ����
		System.out.println("LockTask 32�� map��ֵΪ����"+map.get(i+""));
		
	}
	@Override
	public void run() {
		//�õ�����ջ�����ϲ��task(���������� ��Activity)
		ComponentName topActivity = mActivityManager.getRunningTasks(1).get(0).topActivity;
        String nowPackageName =  topActivity.getPackageName();
//        System.out.println("LockTask.java 28�� nowPackageName.."+nowPackageName);
//        System.out.println("LockTask.java 30�У�ѡ�е�PackageName..."+Lock.packageName);
       
        
        
        
        
        
        
        for(int i = 0;i<map.size();i++){
        if(nowPackageName.equals(map.get(i+""))){
        	Intent intent = new Intent();
        	intent.setClassName("com.example.actionbar", "com.example.actionbar.PasswordActivity");
        	intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        	
        	mContext.startActivity(intent);
        	System.out.println("LockTask.java 38��,����ҳ��һ����");
      }
        }

	}

}
