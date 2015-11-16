/************************************************************
 *	��Ȩ����  (c)2011,   hxf<p>	
 *  �ļ�����	��TableInfoActivity.java<p>
 *
 *  ����ʱ��	��2015��10��16�� ����8:58:09 
 *  ��ǰ�汾�ţ�v1.0
 ************************************************************/
package com.example.orderservice.view.waiter;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.example.orderservice.R;
import com.example.orderservice.dao.OrderDao;
import com.example.orderservice.dao.TableInfoDao;
import com.example.orderservice.dao.TableSizeDao;
import com.example.orderservice.model.OrderBean;
import com.example.orderservice.model.TableInfoBean;
import com.example.orderservice.view.BaseActivity;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

/************************************************************
 * ����ժҪ ��
 * <p>
 *
 * ���� ����ѩ�� 
 * ����ʱ�� ��2015��10��16�� ����8:58:09 ��ǰ�汾�ţ�v1.0 ��ʷ��¼ : ���� : 2015��10��16��
 * ����8:58:09 �޸��ˣ� ���� :
 ************************************************************/
public class TableInfoActivity extends BaseActivity {
	private TextView tvWaiterAccount;
	private TextView tvTableNo;
	private TextView tvTableDescribe;
	private TextView tvTableSize;
	private Button btnOk;
	private Button btnCancel;
	private TableInfoBean tableInfoBean;

	private String waiterAccount;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_table_info);

		// ��ȡ��Intent���ݹ��������ݡ�
		getDataFromIntent();

		// ��ʼ���������
		initComponent();

		// ���ü�����
		registerListener();
	}

	/**
	 * �������� : getDataFromIntent �������� : ����������ֵ˵����
	 *
	 */
	private void getDataFromIntent() {

		// ��ȡ���ݹ�����TableInfoBeanʵ��
		Intent intentPass = getIntent();
		tableInfoBean = (TableInfoBean) intentPass.getSerializableExtra("tableInfoBean");
		waiterAccount = intentPass.getStringExtra("WaiterAccount");
	}

	/**
	 * �������� : initComponent �������� : ����������ֵ˵����
	 *
	 */
	private void initComponent() {
		tvWaiterAccount = (TextView) findViewById(R.id.text_view);
		tvTableNo = (TextView) findViewById(R.id.tv_table_no);
		tvTableDescribe = (TextView) findViewById(R.id.tv_table_describe);
		tvTableSize = (TextView) findViewById(R.id.tv_table_size);
		btnOk = (Button) findViewById(R.id.btn_ok);
		btnCancel = (Button) findViewById(R.id.btn_cancel);
		
		tvWaiterAccount.setText(waiterAccount);

		// ͨ��tableInfoBean.getSizeId()���T_TABLE_INFO���SIZE_ID
		int sizeId = tableInfoBean.getSizeId();
		// Ȼ���T_TABLE_SIZE��������������������
		int size = TableSizeDao.getInstance(this).querySize(sizeId);

		tvTableNo.setText(tableInfoBean.getNo() + "���� ---�� δ����");
		tvTableDescribe.setText(tableInfoBean.getDescribe());
		tvTableSize.setText(size + "����");

	}

	/**
	 * �������� : registerListener �������� : ����������ֵ˵����
	 *
	 */
	private void registerListener() {
		btnOk.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
				OrderBean orderBean = new OrderBean();
				
				orderBean.setTableInfoId(tableInfoBean.getId());

				int waiterId = new Intent().getIntExtra("waiterId", 0);
				orderBean.setWaiterId(waiterId);

				Date date = new Date();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String beginDateTime = sdf.format(date);
				orderBean.setBeginDateTime(beginDateTime);

				orderBean.setIsPay(10);
				
				// ��������T_ORDER�в���һ��������ֻҪ����tableInfoId������Ա��id����ʼʱ�䣬����״̬���ĸ�����
				OrderDao.getInstance(TableInfoActivity.this).saveOrder(orderBean);

				// ����T_TABLE_INFO��Ķ�Ӧ���ӵ�STATE�ֶ�
				tableInfoBean.setState(2);
				TableInfoDao.getInstance(TableInfoActivity.this).modifyTableInfo(tableInfoBean);

				// ��ת��OrderDishActivityҳ���ǵ�Ҫ��TableInfoBean���󴫹�ȥ
				Intent intent = new Intent(TableInfoActivity.this, OrderDishActivity.class);
				intent.putExtra("TableInfoBean", tableInfoBean);

				// ע�⣺���ﲻ��ֱ�Ӱ����洴����OrderBean���󵱳ɶ������д��ݣ���Ϊ�Լ�����OrderBean����ʱ���ܸ��ö�������IDֵ
				// ����ͬһ����������Ӧ�� δ����� ����ֻ��һ����
				// ���ԣ�Ҫô������������Ż�� ��������Ӧ�� δ����� OrderBean���� �������ݣ�
				// Ҫô�������ţ�Ȼ����֮��ͨ�����Ż�� ��������Ӧ�� δ����� OrderBean����
				// intent.putExtra("OrderBean", orderBean);

				startActivity(intent);
			}
		});
		
		btnCancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}

}
