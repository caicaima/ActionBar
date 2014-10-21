package com.example.actionbar.service;

import java.util.Timer;

import android.app.Notification;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;

public class LockService extends Service {
	private Timer mTimer;
	private static final int FOREGROUND_ID = 0;
	private void startTimer(){
		if(mTimer==null){
			mTimer = new Timer();
			LockTask mLockTask = new LockTask(this);
			//
			mTimer.schedule(mLockTask, 0L, 1000L);
			System.out.println("LockService.java,第19行，LockTask执行");
		}
	}
	
	
    @Override
    public void onCreate() {
    	// TODO Auto-generated method stub
    	super.onCreate();
    	/*
    	 * Make this service run in the foreground, 
    	 * supplying the ongoing notification to be shown to the user while in this state.
    	 * By default services are background, meaning that if the system needs to kill
    	 * them to reclaim more memory (such as to display a large page in a web browser),
    	 * they can be killed without too much harm. You can set this flag if killing your 
    	 * service would be disruptive to the user, such as if your service is performing 
    	 * background music playback, so the user would notice if their music stopped playing. 
         * If you need your application to run on platform versions prior to API level 5,
         *  you can use the following model to call the the older setForeground() 
         * or this modern method as appropriate: {@sample development/samples/ApiDemos/src/com/example/android/apis/app/ForegroundService.java foreground_compatibility}
    	 */
    	//Notification的作用   Constructs a Notification object with default values. 
    	//You might want to consider using Builder instead.
    	startForeground(FOREGROUND_ID, new Notification());
    }
    
    
    //在onCreate()之后开始启动Service
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	
	/*
	 * Called by the system every time a client explicitly starts the service by calling
	 *  android.content.Context.startService, providing the arguments it supplied and a 
	 *  unique integer token representing the start request. Do not call this method directly. 
	 */
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		startTimer();
		System.out.println("LockSevice.java 63行，Timer已启动");
		return super.onStartCommand(intent, flags, startId);
	}
	
	
	//在Service被销毁的时候执行
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		stopForeground(true);
		mTimer.cancel();
		/**
		 * Removes all canceled tasks from the task queue. 
		 * If there are no other references on the tasks, then after this call they are free to be garbage collected.
         * Returns:
         * the number of canceled tasks that were removed from the task queue.
		 */
		mTimer.purge();
		mTimer=null;
		//清除数据库
		SharedPreferences preferences = this.getSharedPreferences("packageName", Context.MODE_PRIVATE);
		 SharedPreferences.Editor editor= preferences.edit();
		 editor.clear();
		 editor.commit();
		System.out.println("LockService.java第80行，Service已经停止");
		super.onDestroy();
	}

}
