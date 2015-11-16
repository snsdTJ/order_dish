/************************************************************
 *	��Ȩ����  (c)2011,   hxf<p>	
 *  �ļ�����	��ManagerMainActivity.java<p>
 *
 *  ����ʱ��	��2015��10��24�� ����2:53:02 
 *  ��ǰ�汾�ţ�v1.0
 ************************************************************/
package com.example.orderservice.view.manager;

import com.example.orderservice.R;
import com.example.orderservice.view.BaseActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/************************************************************
 * ����ժҪ ��
 * <p>
 *
 * ���� ��hxf ����ʱ�� ��2015��10��24�� ����2:53:02 ��ǰ�汾�ţ�v1.0 ��ʷ��¼ : ���� : 2015��10��24��
 * ����2:53:02 �޸��ˣ� ���� :
 ************************************************************/
public class ManagerMainActivity extends BaseActivity {

	private TextView tvManagerInfo;

	private ImageView ivSalaryCount;
	// �������
	private String managerAccount;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		// ȥ��������
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_manager_main);

		// ��ȡͨ��Intent���ݹ���������
		Intent getIntent = getIntent();
		managerAccount = getIntent.getStringExtra("ManagerAccount");

		// ��ʼ���������
		initComponent();

		// ���ü�����
		registerListener();
	}
	

	/**
	 * �������� : registerListener �������� : ����������ֵ˵����
	 *
	 * �޸ļ�¼�� ���� ��2015��10��24�� ����6:36:20 �޸��ˣ�hxf ���� ��
	 * 
	 */
	private void registerListener() {

		ivSalaryCount.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(ManagerMainActivity.this, ManagerCountActivity.class);
				intent.putExtra("ManagerAccount", managerAccount);
				startActivity(intent);
			}
		});
	}

	/**
	 * �������� : initComponent �������� : ����������ֵ˵����
	 *
	 * �޸ļ�¼�� ���� ��2015��10��24�� ����2:15:20 �޸��ˣ�hxf ���� ��
	 * 
	 */
	private void initComponent() {

		tvManagerInfo = (TextView) findViewById(R.id.tv_manager_info);
		ivSalaryCount = (ImageView) findViewById(R.id.iv_salary_count);

		tvManagerInfo.setText("���� " + managerAccount);
	}

}
