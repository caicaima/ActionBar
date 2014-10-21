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
   //����һЩ����ʱ��ͼƬ�ܷ���һЩ�仯���������drawable�е�selector������ѡ��ɲ���
   int []drawables = {R.drawable.tab_selector_btn,R.drawable.tab_selector_lock,
		              R.drawable.tab_selector_radio,R.drawable.tab_selector_settings};
   String []tabs= {"��Ѷ","app��","����","����"};
   Class<?>[]fragment = {News.class,Lock.class,Radiu.class,Setting.class};
   
   //�õ�ÿ����Ŀ��View
   private View getItemView(int i){
	View localView = this.mInflater.inflate(R.layout.tab_item, null);
	ImageView imag=(ImageView)localView.findViewById(R.id.tabimag);
//	System.out.println(imag);
	imag.setImageResource(drawables[i]);
	TextView mTextView = (TextView)localView.findViewById(R.id.textview);//����©��localView
//	System.out.println(mTextView);
	mTextView.setText(tabs[i]);
	return localView;
   }
   
   //Ϊ�����Activity���ñ�����ʱʹ��,������һ����ʹ�ø÷���ʱ ��Ҫ�Ѹ÷�������Ϊpublic static
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
//		��һ������Ҫ��    ��R.id.realcontainer��xml��frameLayout�����ݵ�����
       /**
        *  When placing this in a view hierarchy, after inflating the hierarchy 
        *  you must call setup(Context, FragmentManager, int) 
        *  to complete the initialization of the tab host
        */
		mTabHost.setup(this, getSupportFragmentManager(), R.id.realcontainer);
		
		for(int i=0;i<fragment.length;i++){
//			System.out.println(mTabHost);
			//Ϊ�����������Ŀ,setIndicator()Ϊÿ����Ŀ������ݣ��磺ImageView��TextView��
			//
			mTabHost.addTab(this.mTabHost.newTabSpec(this.tabs[i]).setIndicator(getItemView(i)),this.fragment[i],null);
			//Ϊÿ����Ŀ��ӱ���ͼƬ��Ҳ������xml�����navigation����
//			this.mTabHost.getTabWidget().getChildAt(i).setBackgroundResource(R.drawable.tab_background);
//			���÷ָ��ߵı�����Tab����һ�����ﵽ���طָ��ߵ�Ч��
//			this.mTabHost.getTabWidget().setDividerDrawable(R.drawable.tab_background);
		}
	}
}
