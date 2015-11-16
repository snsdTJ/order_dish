/************************************************************
 *	版权所有  (c)2011,   hxf<p>	
 *  文件名称	：WaiterMainActivity.java<p>
 *
 *  创建时间	：2015年10月26日 下午3:46:47 
 *  当前版本号：v1.0
 ************************************************************/
package com.example.orderservice.view.waiter;

import java.util.ArrayList;
import java.util.List;

import com.example.orderservice.R;
import com.example.orderservice.util.ManageActivity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

/************************************************************
 * 内容摘要 ：
 * <p>
 *
 * 作者 ：何雪峰
 * 创建时间 ：2015年10月21日 下午3:46:47 
 * 下午3:46:47 修改人： 描述 :
 ************************************************************/
public class WaiterMainActivity extends FragmentActivity {

	private TextView mtvChooseTable, mtvSearchOrder, mtvSpecial;
	
	private LinearLayout mllChooseTable, mllSearcherOrder, mllSpecial;

	// ViewPager用于管理三个Fragment
	private ViewPager mViewPager;
	// ViewPager的适配器
	private FragmentPagerAdapter fpAdapter;
	// ViewPager的数据源
	private List<Fragment> fragmentList;
	// ViewPager的当前页面索引
	private int mCurrentPageIndex;

	// 用于设置屏幕三分之一宽度
	private int mScreen1_3;
	// Tab栏的下划线
	private ImageView mivTabline;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_waiter_main);
		
		// 把当前activity添加到管理activity的ManageActivity工具类的集合中
		ManageActivity.addActivity(this);

		// 初始化布局控件
		initView();

		// 初始化下划线
		initTabline();

		// 设置监听器
		registerListener();

	}

	/**
	 * 函数名称 : registerListener 功能描述 : 参数及返回值说明：
	 *
	 * 
	 */
	private void registerListener() {
		// 添加Tab栏的点击事件
		LLOnclickListener llListener = new LLOnclickListener();
		mllChooseTable.setOnClickListener(llListener);
		mllSearcherOrder.setOnClickListener(llListener);
		mllSpecial.setOnClickListener(llListener);

		// 为ViewPager添加事件
		// mViewPager.setOnPageChangeListener(listener); 该监听器方法已经过时
		mViewPager.addOnPageChangeListener(new OnPageChangeListener() {
			@Override
			public void onPageSelected(int arg0) {// arg0就是当前页面的索引值，相当于ListView中的position
				
				// 把所有标题bar的所有TextView控件的字体设置为黑色
				resetTextView();
				
				switch (arg0) {
				case 0:// 使用Color.parseColor("#008000")方法就可以把RGB颜色值直接使用
					mtvChooseTable.setTextColor(Color.parseColor("#008000"));
					break;
				case 1:
					mtvSearchOrder.setTextColor(Color.parseColor("#008000"));
					break;
				case 2:
					mtvSpecial.setTextColor(Color.parseColor("#008000"));
					break;

				default:
					break;
				}
				mCurrentPageIndex = arg0;
			}
			
			// 第一个参数是要滑动到的页的索引
			// ，第二个参数是偏移量百分比（注意：从左到右是从0.0到1.0，从右到左是从1.0到0.0）.第三个参数是偏移量像素大小
			@Override 
			public void onPageScrolled(int position, float positionOffset, int positionOffsetPx) {
				Log.i("tag", position + ", " + positionOffset + ", " + positionOffsetPx);

				// 拿到mivTabline的属性参数LayoutParams，用来后面获取或设置它的left-margin值
				// 注意：设置leftMargin的时候，需要将LayoutParams强制转成LinearLayout.LayoutParams。
				LinearLayout.LayoutParams lp = (LayoutParams) mivTabline.getLayoutParams();

				// 注意：从左到右是从0.0到1.0，从右到左是从1.0到0.0）
				// if (mCurrentPageIndex == 0 && position == 0)// 0->1
				// {
				// lp.leftMargin = (int) (positionOffset * mScreen1_3 +
				// mCurrentPageIndex* mScreen1_3);
				//
				// } else if (mCurrentPageIndex == 1 && position == 0)// 1->0
				// {
				// lp.leftMargin = (int) (mCurrentPageIndex * mScreen1_3 +
				// (positionOffset - 1)* mScreen1_3);
				//
				// } else if (mCurrentPageIndex == 1 && position == 1) // 1->2
				// {
				// lp.leftMargin = (int) (mCurrentPageIndex * mScreen1_3 +
				// positionOffset* mScreen1_3);
				//
				// } else if (mCurrentPageIndex == 2 && position == 1) // 2->1
				// {
				// lp.leftMargin = (int) (mCurrentPageIndex * mScreen1_3 + (
				// positionOffset-1)* mScreen1_3);
				//
				// }
				// 其实上面那些情况可以用一行代码解决
				lp.leftMargin = (int) ((position + positionOffset) * mScreen1_3);
				// 把获得新left-margin的属性参数设置给下划线ImageView控件
				mivTabline.setLayoutParams(lp);
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub

			}
		});
	}

	/**
	 * 函数名称 : initTabline 功能描述 : 用于设置下划线图片
	 */
	private void initTabline() {

		// 获取手机屏幕信息，并把屏幕的三分之一宽度设置给mScreen1_3成员变量
		Display display = getWindow().getWindowManager().getDefaultDisplay();
		DisplayMetrics outMetrics = new DisplayMetrics();// 该对象用于存放屏幕信息，如大小等值
		display.getMetrics(outMetrics);// display这个屏幕调用这个方法就可以给outMetrics赋display这个屏幕的值
		mScreen1_3 = outMetrics.widthPixels / 3;// 从outMetrics中取出屏幕宽度并把其三分之一赋值给mScreen1_3变量

		// 获取mivTabline这个ImageView的属性参数，并给其赋值新宽度参数
		LayoutParams lp = (LayoutParams) mivTabline.getLayoutParams();
		lp.width = mScreen1_3;
		mivTabline.setLayoutParams(lp);
	}

	/**
	 * 函数名称 : initView 功能描述 : 参数及返回值说明：
	 *
	 * 修改记录： 日期 ：2015年10月26日 下午4:56:50 修改人：hxf 描述 ：
	 * 
	 */
	private void initView() {

		mtvChooseTable = (TextView) findViewById(R.id.id_tv_choose_table);
		mtvSearchOrder = (TextView) findViewById(R.id.id_tv_search_order);
		mtvSpecial = (TextView) findViewById(R.id.id_tv_special);
		mivTabline = (ImageView) findViewById(R.id.id_iv_tabline);

		// 初始化每一个tab，注意,这里要初始化LinearLayout，而不是TextView，因为我想要即使点击tab的空白处也能引发点击事件
		mllChooseTable = (LinearLayout) findViewById(R.id.id_ll_choose_table);
		mllSearcherOrder = (LinearLayout) findViewById(R.id.id_ll_search_order);
		mllSpecial = (LinearLayout) findViewById(R.id.id_ll_special);

		mViewPager = (ViewPager) findViewById(R.id.id_viewpager);
		// 初始化Fragment实例，
		ChooseTableFragment chooseTableFragment = new ChooseTableFragment();
		SearchOrderFragment searchOrderFragment = new SearchOrderFragment();
		SpecialFragment specialFragment = new SpecialFragment();

		// 把Fragment实例添加到数据源集合中
		fragmentList = new ArrayList<Fragment>();
		fragmentList.add(chooseTableFragment);
		fragmentList.add(searchOrderFragment);
		fragmentList.add(specialFragment);

		/*
		 * 注意：当前页面需要继承 FragmentActivity, 然后才能拿到
		 * getSupportFragmentManager()这个参数，该方法返回FragmentManager对象
		 */
		fpAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {

			@Override
			public int getCount() {
				// TODO Auto-generated method stub
				return fragmentList.size();
			}

			@Override
			public Fragment getItem(int arg0) {
				// TODO Auto-generated method stub
				return fragmentList.get(arg0);
			}
		};

		mViewPager.setAdapter(fpAdapter);

	}

	/**
	 * 函数名称 : resetTextView 功能描述 : 参数及返回值说明：
	 *
	 * 修改记录： 日期 ：2015年10月27日 上午10:32:58 修改人：hxf 描述 ：
	 * 
	 */
	protected void resetTextView() {
		mtvChooseTable.setTextColor(Color.parseColor("#d3d3d3"));
		mtvSearchOrder.setTextColor(Color.parseColor("#d3d3d3"));
		mtvSpecial.setTextColor(Color.parseColor("#d3d3d3"));
	}

	class LLOnclickListener implements OnClickListener {

		/**
		 * 函数名称 ：onClick 功能描述 ： 参数说明 ：
		 * 
		 * @param v
		 *            返回值：
		 * 
		 *            修改记录： 日期 ：2015年10月27日 下午5:35:43 修改人：hxf 描述 ：
		 * 
		 */
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.id_ll_choose_table:
				mViewPager.setCurrentItem(0, true);// 第一个参数是页面索引值，第二个参数是是否平滑切换
				break;
			case R.id.id_ll_search_order:
				mViewPager.setCurrentItem(1, true);
				break;
			case R.id.id_ll_special:
				mViewPager.setCurrentItem(2, true);
				break;

			default:
				break;
			}
		}
	}

	/**
	 * 函数名称 ：onDestroy 功能描述 ： 参数说明 ： 返回值：
	 * 
	 * 修改记录： 日期 ：2015年11月1日 下午2:34:23 修改人：hxf 描述 ：
	 * 
	 */
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		ManageActivity.removeActivity(this);
	}

}
