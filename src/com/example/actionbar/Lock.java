package com.example.actionbar;

import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.actionbar.adapter.AppListAdapter;
import com.example.actionbar.adapter.AppListAdapter.ViewHolder;
import com.example.actionbar.service.LockService;
/**
 * app锁，主要功能是：
 * 1、能够以时间为工具来设定对app的锁定
 * @author Administrator
 *
 */
public class Lock extends Fragment implements OnItemClickListener{
private PackageManager mPackageManager;//包管理器
private List<ResolveInfo>allApps;
private Context mContext;
private ListView mListView;
private View view;
private ProgressDialog mDialog;
private ActionBar mActionBar;
//第179行，要改
public static String packageName;
//第181行
public static String className;
//设置为全局变量目的是在点击完成时获得所选中的app而不是每点一次存一次；1
private ViewHolder holder;
//得到Adadpter中返回的View
private View mView;
 //获得Adapter中的position
 int mPosition;
 private static int newtHour;
 private static int newMinute;
private static int oldDay;
 Handler mHandler = new Handler(){
	
	  public void handleMessage(android.os.Message msg) {
		super.handleMessage(msg);
		if(0==msg.what){
			System.out.println("发送数据为0");
		}else{
			getAppView(view);
			System.out.println("发送数据不为0");
		}
	};
};
	
    public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.mContext = getActivity();
		//显示子菜单
		setHasOptionsMenu(true);
		setMenuVisibility(true);
	};
	/**
	 * 查询已安装的app，并进行排序放入Collections集合中
	 */
	private void bindAllApps() {
		
		mPackageManager = mContext.getPackageManager();
		//从安装程序的入口开始查询,通过IntentFliter来过滤
		Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
		mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
		// 包含上面两个属性的全部查出来,并且排序
		allApps = mPackageManager.queryIntentActivities(mainIntent, 0);
		Collections.sort(allApps, new ResolveInfo.DisplayNameComparator(mPackageManager));
		
	}
	
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		    view = inflater.inflate(R.layout.lock  , container, false);
		    //进度对话框
		    CharSequence body  = getString(R.string.waiting);
		   mDialog = ProgressDialog.show(getActivity(), "请稍等。。。", body,false);
		   
		   
        
//		/**
//		  * 开启子线程，
//		  * 利用Handler进行页面更新
//		  */
		       new Thread(new Runnable() {
						@Override
						public void run() {
							try {
								//检索手机里的安装软件
								bindAllApps();
//								Message msg = new Message();
								mHandler.sendEmptyMessage(1);
							} catch (Exception e) {
								// TODO: handle exception
							}finally{
								mDialog.dismiss();
							}
						}
					}).start();
		return view;
	}

	@Override
		public void onViewCreated(View view, Bundle savedInstanceState) {
			// TODO Auto-generated method stub
			super.onViewCreated(view, savedInstanceState);
			this.mActionBar  = MainActivity.getMainActionBar();
			this.mActionBar.setTitle(R.string.title_lock);//以后用String.xml 代替
			this.mActionBar.setDisplayShowTitleEnabled(true);
//			/**
//			  * 开启子线程，
//			  * 利用Handler进行页面更新
//			  */
//			       new Thread(new Runnable() {
//							@Override
//							public void run() {
//								try {
//									//检索手机里的安装软件
//									bindAllApps();
////									Message msg = new Message();
//									mHandler.sendEmptyMessage(1);
//								} catch (Exception e) {
//									// TODO: handle exception
//								}finally{
//									mDialog.dismiss();
//								}
//							}
//						}).start();
		}
	
	/**
	 * 对页面进行更新
	 * @param mListView
	 * @param adapter
	 * @return View
	 */
	private View getAppView(View view) {
		mListView = (ListView) view.findViewById(R.id.appListView);
		AppListAdapter adapter = new AppListAdapter(mContext,allApps,mPackageManager);
        mListView.setAdapter(adapter);
        mListView.setOnItemClickListener(this);
        System.out.println("view1......."+view);
        return view;
		
	}


	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		 holder = (ViewHolder) view.getTag();
		 holder.cb.toggle();
		AppListAdapter.getSelected().put(position,holder.cb.isChecked());
		
		mView = view;
		mPosition = position;
//		//如果选中后，就得到选中app的名字，存储起来
//		if(holder.cb.isChecked()){
//			ResolveInfo res = allApps.get(position);
////			//获得应用程程序的Label
////			String name =res.loadLabel(mPackageManager).toString();
////			//获得应用程序入口的类名
////			className =res.activityInfo.applicationInfo.className;
//			//获得应用程序的包名
//			 packageName = res.activityInfo.packageName;
//			System.out.println("packageName..."+packageName);
//			
//		}
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		// TODO Auto-generated method stub
	     inflater.inflate(R.menu.lock_menu, menu);
		super.onCreateOptionsMenu(menu, inflater);
	}	
	@Override
		public boolean onOptionsItemSelected(MenuItem item) {
		    int id = item.getItemId();
		    System.out.println("Lock.java 191行，菜单一出来");
		    if(id==R.id.lock_finish){
		    	//检查选中的选项并存储
		    	new Thread(new Runnable() {
					
					@Override
					public void run() {
						CheckSaveApp();
				    	
					}
				}).start();
		    	setTime();
		    }else{
		    	this.mContext.stopService(new Intent(mContext, LockService.class));
		    }
		    
			return super.onOptionsItemSelected(item);
		}
	
	/*
	 * 利用SharePreferences来存储数据
	 * 后一次添加时数据库值会改变，即后一次添加的会覆盖前一次的值，，为bug
	 */
	private void CheckSaveApp() {

		SharedPreferences preferences = mContext.getSharedPreferences("packageName", Context.MODE_PRIVATE);
		 SharedPreferences.Editor editor= preferences.edit();
		 //key值
		 int count = 0;
		for(int i=0;i<allApps.size();i++){
			if(AppListAdapter.getSelected().get(i)){
				ResolveInfo res = allApps.get(i);
				packageName = res.activityInfo.packageName;
				editor.putString(count+++"", packageName);
				editor.commit();
				System.out.println("Lock.java  220行。。Package...."+packageName);
			}
		}
		System.out.println("Lock.java 225行 选中app以保存");
	}
	
	//setTime
	private void setTime() {
		TimePickerDialog pickerDialog = new TimePickerDialog(mContext,
				new TimePickerDialog.OnTimeSetListener() {
					@Override
					public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
						Calendar c = Calendar.getInstance();
						int oldHour = c.get(Calendar.HOUR_OF_DAY);
						int oldMinute  = c.get(Calendar.MINUTE);
						 oldDay = c.get(Calendar.DAY_OF_YEAR);
						newtHour = oldHour +hourOfDay;
						newMinute = oldMinute +minute;
						System.out.println("newHour....."+newtHour+".......newMinute"+newMinute);
						startLockService();
					}
					
					private void startLockService() {
						getActivity().startService(new Intent(mContext, LockService.class));
				    	System.out.println("Lock.java 第199行，Service已经启动");
					}
				}, 0, 0, true);
		pickerDialog.setIcon(getResources().getDrawable(R.drawable.tab_lock_pressed));
		pickerDialog.show();
	}
	public static  void setHour(int newHour){
		 Lock.newtHour = newHour;
	}
	public static int getHour(){
		System.out.println(">>>>"+newtHour);
		return newtHour;
		}
	public static void setMinute(int newMinte){
		Lock.newMinute = newMinte;
	}
	public static int getMinute(){
		System.out.println(">>>>"+newMinute);
		return newMinute;
	}
	public static void setDay(int oldDay){
		Lock.oldDay = oldDay;
	}
	public static int getDay(){
		System.out.println(">>>>"+oldDay);
		return oldDay;
	}
	
	
}
