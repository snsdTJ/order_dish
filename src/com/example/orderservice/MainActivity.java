package com.example.orderservice;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.example.orderservice.dao.MyDatabaseHelper;
import com.example.orderservice.dao.OrderDao;
import com.example.orderservice.dao.OrderDetailDao;
import com.example.orderservice.model.OrderBean;
import com.example.orderservice.model.OrderDetailBean;
import com.example.orderservice.view.LoginActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ViewFlipper;

/************************************************************
 * 内容摘要 ：
 * <p>
 *
 * 作者 ：何雪峰 
 * 创建时间 ：2015年10月17日 上午10:58:39 当前版本号：v1.0 
 * 上午10:58:39 修改人： 描述 :
 ************************************************************/
public class MainActivity extends Activity {

	private ViewFlipper mViewFilpper;

	private int[] mFlipperArray = new int[] { R.drawable.android_guide_step_1, R.drawable.android_guide_step_2,
			R.drawable.android_guide_step_3, R.drawable.android_guide_step_4 };

	private float startX;

	// 用于从SharedPreference读取或写入数据
	private SharedPreferences sp;

	private SharedPreferences.Editor sp_editor;
	//用于判断是否第一次登入应用
	private boolean ifFirstTime;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);

		// 初始化SharedPreferences
		initSharedPreferences();

		// 初始化组件
		initComponent();
	}

	/**
	 * 函数名称 : initComponent 功能描述 : 参数及返回值说明：
	 *
	 * 
	 */
	private void initComponent() {

		mViewFilpper = (ViewFlipper) findViewById(R.id.view_flipper);

		// 动态导入的方式为ViewFlipper加入子view
		for (int i = 0; i < mFlipperArray.length; i++) {
			mViewFilpper.addView(getView(mFlipperArray[i]));
		}

		// 设置动画效果
		mViewFilpper.setInAnimation(this, R.anim.right_in);
		mViewFilpper.setOutAnimation(this, R.anim.right_out);

		// 设定ViewFlipper视图切换时间间隔
		mViewFilpper.setFlipInterval(4000);

		// 启动ViewFlipper，开始播放
		mViewFilpper.setAutoStart(true);

		if (mViewFilpper.isAutoStart() && !mViewFilpper.isFlipping()) {
			mViewFilpper.startFlipping();
		}

	}

	/**
	 * 函数名称 : initSharedPreferences 功能描述 : 参数及返回值说明：
	 *
	 * 
	 */
	private void initSharedPreferences() {

		sp = getSharedPreferences("pref_flipper", MODE_PRIVATE);
		sp_editor = sp.edit();
		ifFirstTime = sp.getBoolean("IfFirstTime", true);
		if (!ifFirstTime) {

			Intent intent = new Intent(MainActivity.this, LoginActivity.class);
			startActivity(intent);
			finish();
		}
		sp_editor.putBoolean("IfFirstTime", false);
		sp_editor.commit();
	}

	/**
	 * 函数名称 ：onTouchEvent 功能描述 ： 参数说明 ：
	 * 
	 * @param event
	 * @return 返回值：
	 * 
	 * 
	 */
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// 当触摸到屏幕后，就停止自动播放图片
		mViewFilpper.stopFlipping();
		mViewFilpper.setAutoStart(false);

		switch (event.getAction()) {

		// 手指落下时
		case MotionEvent.ACTION_DOWN:
			startX = event.getX();
			break;

		// 手指滑动
		case MotionEvent.ACTION_MOVE:
			break;

		// 手指离开时
		case MotionEvent.ACTION_UP:

			// 向右滑动
			if (event.getX() - startX > 100) {
				mViewFilpper.setInAnimation(this, R.anim.left_in);
				mViewFilpper.setOutAnimation(this, R.anim.left_out);
				mViewFilpper.showPrevious();// 显示前一页
			}
			// 向左滑动
			if (event.getX() - startX < 100) {
				mViewFilpper.setInAnimation(this, R.anim.right_in);
				mViewFilpper.setOutAnimation(this, R.anim.right_out);
				mViewFilpper.showNext();// 显示后一页
			}

			break;

		default:
			break;
		}

		return super.onTouchEvent(event);
	}

	/**
	 * 函数名称 : getImageView 功能描述 :
	 */
	private View getView(int resId) {
		LayoutInflater inflater = LayoutInflater.from(this);
		RelativeLayout relativeLayout = (RelativeLayout) inflater.inflate(R.layout.flipper_view_layout, null);
		relativeLayout.setBackgroundResource(resId);
		Button button = (Button) relativeLayout.findViewById(R.id.button);
		if (resId == mFlipperArray[3]) {
			button.setVisibility(View.VISIBLE);
			button.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent intent = new Intent(MainActivity.this, LoginActivity.class);
					startActivity(intent);
					finish();
				}
			});
		}
		return relativeLayout;
	}

}
