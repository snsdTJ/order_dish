/************************************************************
 *	��Ȩ����  (c)2011,   hxf<p>	
 *  �ļ�����	��AdminMainActivity.java<p>
 *
 *  ����ʱ��	��2015��10��23�� ����10:04:23 
 *  ��ǰ�汾�ţ�v1.0
 ************************************************************/
package com.example.orderservice.view.admin;

import com.example.orderservice.R;
import com.example.orderservice.control.AdminOnClickListener;
import com.example.orderservice.view.BaseActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;

/************************************************************
 * ����ժҪ ��
 * <p>
 *
 * ���� ��hxf ����ʱ�� ��2015��10��23�� ����10:04:23 ��ǰ�汾�ţ�v1.0 ��ʷ��¼ : ���� : 2015��10��23��
 * ����10:04:23 �޸��ˣ� ���� :
 ************************************************************/
public class AdminMainActivity extends BaseActivity {

	private ImageView ivAddDish, ivManageDish;

	private AdminOnClickListener adminOnClickListener = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		// ȥ��������
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_admin_main);
		
		// ��ʼ�����
		initComponent();

		// ��ʼ��������s --- Listeners
		initListeners();

		// ���ü�����
		registerListener();
	}

	
	/**
	 *  �������� : registerListener
	 *  �������� :  
	 *  ����������ֵ˵����
	 *
	 *  �޸ļ�¼��
	 *  	���� ��2015��11��9�� ����4:48:17	�޸��ˣ�hxf
	 *  	����	��
	 * 					
	 */
	private void registerListener() {
		// TODO Auto-generated method stub
		ivAddDish.setOnClickListener(adminOnClickListener);
		ivManageDish.setOnClickListener(adminOnClickListener);
	}

	/**
	 *  �������� : initListeners
	 *  �������� :  
	 *  ����������ֵ˵����
	 *
	 *  �޸ļ�¼��
	 *  	���� ��2015��11��9�� ����4:46:59	�޸��ˣ�hxf
	 *  	����	��
	 * 					
	 */
	private void initListeners() {
		// TODO Auto-generated method stub
		adminOnClickListener = new AdminOnClickListener(this);
	}

	/**
	 *  �������� : initComponent
	 *  �������� :  
	 *  ����������ֵ˵����
	 *
	 *  �޸ļ�¼��
	 *  	���� ��2015��11��9�� ����4:46:35	�޸��ˣ�hxf
	 *  	����	��
	 * 					
	 */
	private void initComponent() {
		// TODO Auto-generated method stub
		ivAddDish = (ImageView) findViewById(R.id.iv_add_dish);
		ivManageDish = (ImageView) findViewById(R.id.iv_manage_dish);
	}

}
