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
 * ����ժҪ ��
 * <p>
 *
 * ���� ����ѩ�� 
 * ����ʱ�� ��2015��10��17�� ����10:58:39 ��ǰ�汾�ţ�v1.0 
 * ����10:58:39 �޸��ˣ� ���� :
 ************************************************************/
public class MainActivity extends Activity {

	private ViewFlipper mViewFilpper;

	private int[] mFlipperArray = new int[] { R.drawable.android_guide_step_1, R.drawable.android_guide_step_2,
			R.drawable.android_guide_step_3, R.drawable.android_guide_step_4 };

	private float startX;

	// ���ڴ�SharedPreference��ȡ��д������
	private SharedPreferences sp;

	private SharedPreferences.Editor sp_editor;
	//�����ж��Ƿ��һ�ε���Ӧ��
	private boolean ifFirstTime;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);

		// ��ʼ��SharedPreferences
		initSharedPreferences();

		// ��ʼ�����
		initComponent();
	}

	/**
	 * �������� : initComponent �������� : ����������ֵ˵����
	 *
	 * 
	 */
	private void initComponent() {

		mViewFilpper = (ViewFlipper) findViewById(R.id.view_flipper);

		// ��̬����ķ�ʽΪViewFlipper������view
		for (int i = 0; i < mFlipperArray.length; i++) {
			mViewFilpper.addView(getView(mFlipperArray[i]));
		}

		// ���ö���Ч��
		mViewFilpper.setInAnimation(this, R.anim.right_in);
		mViewFilpper.setOutAnimation(this, R.anim.right_out);

		// �趨ViewFlipper��ͼ�л�ʱ����
		mViewFilpper.setFlipInterval(4000);

		// ����ViewFlipper����ʼ����
		mViewFilpper.setAutoStart(true);

		if (mViewFilpper.isAutoStart() && !mViewFilpper.isFlipping()) {
			mViewFilpper.startFlipping();
		}

	}

	/**
	 * �������� : initSharedPreferences �������� : ����������ֵ˵����
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
	 * �������� ��onTouchEvent �������� �� ����˵�� ��
	 * 
	 * @param event
	 * @return ����ֵ��
	 * 
	 * 
	 */
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// ����������Ļ�󣬾�ֹͣ�Զ�����ͼƬ
		mViewFilpper.stopFlipping();
		mViewFilpper.setAutoStart(false);

		switch (event.getAction()) {

		// ��ָ����ʱ
		case MotionEvent.ACTION_DOWN:
			startX = event.getX();
			break;

		// ��ָ����
		case MotionEvent.ACTION_MOVE:
			break;

		// ��ָ�뿪ʱ
		case MotionEvent.ACTION_UP:

			// ���һ���
			if (event.getX() - startX > 100) {
				mViewFilpper.setInAnimation(this, R.anim.left_in);
				mViewFilpper.setOutAnimation(this, R.anim.left_out);
				mViewFilpper.showPrevious();// ��ʾǰһҳ
			}
			// ���󻬶�
			if (event.getX() - startX < 100) {
				mViewFilpper.setInAnimation(this, R.anim.right_in);
				mViewFilpper.setOutAnimation(this, R.anim.right_out);
				mViewFilpper.showNext();// ��ʾ��һҳ
			}

			break;

		default:
			break;
		}

		return super.onTouchEvent(event);
	}

	/**
	 * �������� : getImageView �������� :
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
