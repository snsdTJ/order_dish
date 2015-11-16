/************************************************************
 *	��Ȩ����  (c)2011,   hxf<p>	
 *  �ļ�����	��BaseActivity.java<p>
 *
 *  ����ʱ��	��2015��11��1�� ����1:01:10 
 *  ��ǰ�汾�ţ�v1.0
 ************************************************************/
package com.example.orderservice.view;

import com.example.orderservice.util.ManageActivity;

import android.app.Activity;
import android.os.Bundle;

/************************************************************
 *  ����ժҪ	��<p>
 *
 *  ����	��hxf
 *  ����ʱ��	��2015��10��27�� ����1:01:10 
 *  ��ǰ�汾�ţ�v1.0
 *  ��ʷ��¼	:
 *  	����	: 2015��10��27�� ����1:01:10	�޸��ˣ�
 *  	����	:
 ************************************************************/
public class BaseActivity extends Activity {
	
	/**
	 *  �������� ��onCreate
	 *  �������� ��  
	 *  ����˵�� ��
	 *  	@param savedInstanceState
	 *  ����ֵ��
	 *  	
	 *  �޸ļ�¼��
	 *  ���� ��2015��10��27�� ����1:01:56	�޸��ˣ�hxf
	 *  ���� ��
	 * 					
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		ManageActivity.addActivity(this);
	}
	
	/**
	 *  �������� ��onDestroy
	 *  �������� ��  
	 *  ����˵�� ��
	 *  ����ֵ��
	 *  	
	 *  �޸ļ�¼��
	 *  ���� ��2015��10��27�� ����1:01:56		�޸��ˣ�hxf
	 *  ���� ��
	 * 					
	 */
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		ManageActivity.removeActivity(this);
	}

}
