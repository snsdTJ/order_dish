/************************************************************
 *	��Ȩ����  (c)2011,   hxf<p>	
 *  �ļ�����	��WaiterMainActivity.java<p>
 *
 *  ����ʱ��	��2015��10��26�� ����3:46:47 
 *  ��ǰ�汾�ţ�v1.0
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
 * ����ժҪ ��
 * <p>
 *
 * ���� ����ѩ��
 * ����ʱ�� ��2015��10��21�� ����3:46:47 
 * ����3:46:47 �޸��ˣ� ���� :
 ************************************************************/
public class WaiterMainActivity extends FragmentActivity {

	private TextView mtvChooseTable, mtvSearchOrder, mtvSpecial;
	
	private LinearLayout mllChooseTable, mllSearcherOrder, mllSpecial;

	// ViewPager���ڹ�������Fragment
	private ViewPager mViewPager;
	// ViewPager��������
	private FragmentPagerAdapter fpAdapter;
	// ViewPager������Դ
	private List<Fragment> fragmentList;
	// ViewPager�ĵ�ǰҳ������
	private int mCurrentPageIndex;

	// ����������Ļ����֮һ���
	private int mScreen1_3;
	// Tab�����»���
	private ImageView mivTabline;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_waiter_main);
		
		// �ѵ�ǰactivity��ӵ�����activity��ManageActivity������ļ�����
		ManageActivity.addActivity(this);

		// ��ʼ�����ֿؼ�
		initView();

		// ��ʼ���»���
		initTabline();

		// ���ü�����
		registerListener();

	}

	/**
	 * �������� : registerListener �������� : ����������ֵ˵����
	 *
	 * 
	 */
	private void registerListener() {
		// ���Tab���ĵ���¼�
		LLOnclickListener llListener = new LLOnclickListener();
		mllChooseTable.setOnClickListener(llListener);
		mllSearcherOrder.setOnClickListener(llListener);
		mllSpecial.setOnClickListener(llListener);

		// ΪViewPager����¼�
		// mViewPager.setOnPageChangeListener(listener); �ü����������Ѿ���ʱ
		mViewPager.addOnPageChangeListener(new OnPageChangeListener() {
			@Override
			public void onPageSelected(int arg0) {// arg0���ǵ�ǰҳ�������ֵ���൱��ListView�е�position
				
				// �����б���bar������TextView�ؼ�����������Ϊ��ɫ
				resetTextView();
				
				switch (arg0) {
				case 0:// ʹ��Color.parseColor("#008000")�����Ϳ��԰�RGB��ɫֱֵ��ʹ��
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
			
			// ��һ��������Ҫ��������ҳ������
			// ���ڶ���������ƫ�����ٷֱȣ�ע�⣺�������Ǵ�0.0��1.0�����ҵ����Ǵ�1.0��0.0��.������������ƫ�������ش�С
			@Override 
			public void onPageScrolled(int position, float positionOffset, int positionOffsetPx) {
				Log.i("tag", position + ", " + positionOffset + ", " + positionOffsetPx);

				// �õ�mivTabline�����Բ���LayoutParams�����������ȡ����������left-marginֵ
				// ע�⣺����leftMargin��ʱ����Ҫ��LayoutParamsǿ��ת��LinearLayout.LayoutParams��
				LinearLayout.LayoutParams lp = (LayoutParams) mivTabline.getLayoutParams();

				// ע�⣺�������Ǵ�0.0��1.0�����ҵ����Ǵ�1.0��0.0��
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
				// ��ʵ������Щ���������һ�д�����
				lp.leftMargin = (int) ((position + positionOffset) * mScreen1_3);
				// �ѻ����left-margin�����Բ������ø��»���ImageView�ؼ�
				mivTabline.setLayoutParams(lp);
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub

			}
		});
	}

	/**
	 * �������� : initTabline �������� : ���������»���ͼƬ
	 */
	private void initTabline() {

		// ��ȡ�ֻ���Ļ��Ϣ��������Ļ������֮һ������ø�mScreen1_3��Ա����
		Display display = getWindow().getWindowManager().getDefaultDisplay();
		DisplayMetrics outMetrics = new DisplayMetrics();// �ö������ڴ����Ļ��Ϣ�����С��ֵ
		display.getMetrics(outMetrics);// display�����Ļ������������Ϳ��Ը�outMetrics��display�����Ļ��ֵ
		mScreen1_3 = outMetrics.widthPixels / 3;// ��outMetrics��ȡ����Ļ��Ȳ���������֮һ��ֵ��mScreen1_3����

		// ��ȡmivTabline���ImageView�����Բ����������丳ֵ�¿�Ȳ���
		LayoutParams lp = (LayoutParams) mivTabline.getLayoutParams();
		lp.width = mScreen1_3;
		mivTabline.setLayoutParams(lp);
	}

	/**
	 * �������� : initView �������� : ����������ֵ˵����
	 *
	 * �޸ļ�¼�� ���� ��2015��10��26�� ����4:56:50 �޸��ˣ�hxf ���� ��
	 * 
	 */
	private void initView() {

		mtvChooseTable = (TextView) findViewById(R.id.id_tv_choose_table);
		mtvSearchOrder = (TextView) findViewById(R.id.id_tv_search_order);
		mtvSpecial = (TextView) findViewById(R.id.id_tv_special);
		mivTabline = (ImageView) findViewById(R.id.id_iv_tabline);

		// ��ʼ��ÿһ��tab��ע��,����Ҫ��ʼ��LinearLayout��������TextView����Ϊ����Ҫ��ʹ���tab�Ŀհ״�Ҳ����������¼�
		mllChooseTable = (LinearLayout) findViewById(R.id.id_ll_choose_table);
		mllSearcherOrder = (LinearLayout) findViewById(R.id.id_ll_search_order);
		mllSpecial = (LinearLayout) findViewById(R.id.id_ll_special);

		mViewPager = (ViewPager) findViewById(R.id.id_viewpager);
		// ��ʼ��Fragmentʵ����
		ChooseTableFragment chooseTableFragment = new ChooseTableFragment();
		SearchOrderFragment searchOrderFragment = new SearchOrderFragment();
		SpecialFragment specialFragment = new SpecialFragment();

		// ��Fragmentʵ����ӵ�����Դ������
		fragmentList = new ArrayList<Fragment>();
		fragmentList.add(chooseTableFragment);
		fragmentList.add(searchOrderFragment);
		fragmentList.add(specialFragment);

		/*
		 * ע�⣺��ǰҳ����Ҫ�̳� FragmentActivity, Ȼ������õ�
		 * getSupportFragmentManager()����������÷�������FragmentManager����
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
	 * �������� : resetTextView �������� : ����������ֵ˵����
	 *
	 * �޸ļ�¼�� ���� ��2015��10��27�� ����10:32:58 �޸��ˣ�hxf ���� ��
	 * 
	 */
	protected void resetTextView() {
		mtvChooseTable.setTextColor(Color.parseColor("#d3d3d3"));
		mtvSearchOrder.setTextColor(Color.parseColor("#d3d3d3"));
		mtvSpecial.setTextColor(Color.parseColor("#d3d3d3"));
	}

	class LLOnclickListener implements OnClickListener {

		/**
		 * �������� ��onClick �������� �� ����˵�� ��
		 * 
		 * @param v
		 *            ����ֵ��
		 * 
		 *            �޸ļ�¼�� ���� ��2015��10��27�� ����5:35:43 �޸��ˣ�hxf ���� ��
		 * 
		 */
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.id_ll_choose_table:
				mViewPager.setCurrentItem(0, true);// ��һ��������ҳ������ֵ���ڶ����������Ƿ�ƽ���л�
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
	 * �������� ��onDestroy �������� �� ����˵�� �� ����ֵ��
	 * 
	 * �޸ļ�¼�� ���� ��2015��11��1�� ����2:34:23 �޸��ˣ�hxf ���� ��
	 * 
	 */
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		ManageActivity.removeActivity(this);
	}

}
