package com.example.actionbar;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Build;

public class MainActivity extends ActionBarActivity {
   static ActionBar mActionBar;
   FragmentTabHost mTabHost;
   LayoutInflater mInflater;
   //对有一些操作时，图片能发生一些变化，则可以用drawable中的selector来进行选择可操作
   int []drawables = {R.drawable.tab_selector_btn,R.drawable.tab_selector_lock,
		              R.drawable.tab_selector_radio,R.drawable.tab_selector_settings};
   String []tabs= {"资讯","app锁","辐射","设置"};
   Class<?>[]fragment = {News.class,Lock.class,Radiu.class,Setting.class};
   
   //得到每个条目的View
   private View getItemView(int i){
	View localView = this.mInflater.inflate(R.layout.tab_item, null);
	ImageView imag=(ImageView)localView.findViewById(R.id.tabimag);
//	System.out.println(imag);
	imag.setImageResource(drawables[i]);
	TextView mTextView = (TextView)localView.findViewById(R.id.textview);//不能漏掉localView
//	System.out.println(mTextView);
	mTextView.setText(tabs[i]);
	return localView;
   }
   
   //为后面的Activity设置标题栏时使用,当被另一个类使用该方法时 需要把该方法定义为public static
   public static ActionBar getMainActionBar()
   {
     return mActionBar;
   }
   public static void setActionBar(ActionBar mActionBar){
	   MainActivity.mActionBar = mActionBar;
   }
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_main);
		this.mInflater = getLayoutInflater();
		mActionBar=getSupportActionBar();
		mActionBar.setDisplayHomeAsUpEnabled(false);
	    mActionBar.setDisplayShowHomeEnabled(false);
	    mActionBar.setDisplayShowCustomEnabled(false);
		mTabHost=(FragmentTabHost) findViewById(android.R.id.tabhost);
//		System.out.println(mTabHost+"........");
//		这一步必须要有    （R.id.realcontainer是xml中frameLayout中内容的容器
       /**
        *  When placing this in a view hierarchy, after inflating the hierarchy 
        *  you must call setup(Context, FragmentManager, int) 
        *  to complete the initialization of the tab host
        */
		mTabHost.setup(this, getSupportFragmentManager(), R.id.realcontainer);
		
		for(int i=0;i<fragment.length;i++){
//			System.out.println(mTabHost);
			//为导航栏添加条目,setIndicator()为每个条目添加内容（如：ImageView，TextView）
			//
			mTabHost.addTab(this.mTabHost.newTabSpec(this.tabs[i]).setIndicator(getItemView(i)),this.fragment[i],null);
			//为每个条目添加背景图片，也可以在xml中添加navigation背景
//			this.mTabHost.getTabWidget().getChildAt(i).setBackgroundResource(R.drawable.tab_background);
//			设置分割线的背景与Tab背景一致来达到隐藏分割线的效果
//			this.mTabHost.getTabWidget().setDividerDrawable(R.drawable.tab_background);
		}
	}
}
