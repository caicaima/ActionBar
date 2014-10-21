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
 * app������Ҫ�����ǣ�
 * 1���ܹ���ʱ��Ϊ�������趨��app������
 * @author Administrator
 *
 */
public class Lock extends Fragment implements OnItemClickListener{
private PackageManager mPackageManager;//��������
private List<ResolveInfo>allApps;
private Context mContext;
private ListView mListView;
private View view;
private ProgressDialog mDialog;
private ActionBar mActionBar;
//��179�У�Ҫ��
public static String packageName;
//��181��
public static String className;
//����Ϊȫ�ֱ���Ŀ�����ڵ�����ʱ�����ѡ�е�app������ÿ��һ�δ�һ�Σ�1
private ViewHolder holder;
//�õ�Adadpter�з��ص�View
private View mView;
 //���Adapter�е�position
 int mPosition;
 private static int newtHour;
 private static int newMinute;
private static int oldDay;
 Handler mHandler = new Handler(){
	
	  public void handleMessage(android.os.Message msg) {
		super.handleMessage(msg);
		if(0==msg.what){
			System.out.println("��������Ϊ0");
		}else{
			getAppView(view);
			System.out.println("�������ݲ�Ϊ0");
		}
	};
};
	
    public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.mContext = getActivity();
		//��ʾ�Ӳ˵�
		setHasOptionsMenu(true);
		setMenuVisibility(true);
	};
	/**
	 * ��ѯ�Ѱ�װ��app���������������Collections������
	 */
	private void bindAllApps() {
		
		mPackageManager = mContext.getPackageManager();
		//�Ӱ�װ�������ڿ�ʼ��ѯ,ͨ��IntentFliter������
		Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
		mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
		// ���������������Ե�ȫ�������,��������
		allApps = mPackageManager.queryIntentActivities(mainIntent, 0);
		Collections.sort(allApps, new ResolveInfo.DisplayNameComparator(mPackageManager));
		
	}
	
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		    view = inflater.inflate(R.layout.lock  , container, false);
		    //���ȶԻ���
		    CharSequence body  = getString(R.string.waiting);
		   mDialog = ProgressDialog.show(getActivity(), "���Եȡ�����", body,false);
		   
		   
        
//		/**
//		  * �������̣߳�
//		  * ����Handler����ҳ�����
//		  */
		       new Thread(new Runnable() {
						@Override
						public void run() {
							try {
								//�����ֻ���İ�װ���
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
			this.mActionBar.setTitle(R.string.title_lock);//�Ժ���String.xml ����
			this.mActionBar.setDisplayShowTitleEnabled(true);
//			/**
//			  * �������̣߳�
//			  * ����Handler����ҳ�����
//			  */
//			       new Thread(new Runnable() {
//							@Override
//							public void run() {
//								try {
//									//�����ֻ���İ�װ���
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
	 * ��ҳ����и���
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
//		//���ѡ�к󣬾͵õ�ѡ��app�����֣��洢����
//		if(holder.cb.isChecked()){
//			ResolveInfo res = allApps.get(position);
////			//���Ӧ�ó̳����Label
////			String name =res.loadLabel(mPackageManager).toString();
////			//���Ӧ�ó�����ڵ�����
////			className =res.activityInfo.applicationInfo.className;
//			//���Ӧ�ó���İ���
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
		    System.out.println("Lock.java 191�У��˵�һ����");
		    if(id==R.id.lock_finish){
		    	//���ѡ�е�ѡ��洢
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
	 * ����SharePreferences���洢����
	 * ��һ�����ʱ���ݿ�ֵ��ı䣬����һ����ӵĻḲ��ǰһ�ε�ֵ����Ϊbug
	 */
	private void CheckSaveApp() {

		SharedPreferences preferences = mContext.getSharedPreferences("packageName", Context.MODE_PRIVATE);
		 SharedPreferences.Editor editor= preferences.edit();
		 //keyֵ
		 int count = 0;
		for(int i=0;i<allApps.size();i++){
			if(AppListAdapter.getSelected().get(i)){
				ResolveInfo res = allApps.get(i);
				packageName = res.activityInfo.packageName;
				editor.putString(count+++"", packageName);
				editor.commit();
				System.out.println("Lock.java  220�С���Package...."+packageName);
			}
		}
		System.out.println("Lock.java 225�� ѡ��app�Ա���");
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
				    	System.out.println("Lock.java ��199�У�Service�Ѿ�����");
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
