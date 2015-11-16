package com.example.orderservice.view.waiter;
///************************************************************
// *	��Ȩ����  (c)2011,   hxf<p>	
// *  �ļ�����	��ChooseTableActivity.java<p>
// *
// *  ����ʱ��	��2015��10��15�� ����9:02:15 
// *  ��ǰ�汾�ţ�v1.0
// ************************************************************/
//package com.example.orderservice.view;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import com.example.orderservice.R;
//import com.example.orderservice.adapter.ChooseTableAdapter;
//import com.example.orderservice.dao.OrderDao;
//import com.example.orderservice.dao.TableInfoDao;
//import com.example.orderservice.model.OrderBean;
//import com.example.orderservice.model.TableInfoBean;
//
//import android.app.Activity;
//import android.content.Intent;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.widget.AdapterView;
//import android.widget.AdapterView.OnItemClickListener;
//import android.widget.Button;
//import android.widget.GridView;
//import android.widget.TextView;
//
///************************************************************
// *  ����ժҪ	��<p>
// *
// *  ����	��hxf
// *  ����ʱ��	��2015��10��15�� ����9:02:15 
// *  ��ǰ�汾�ţ�v1.0
// *  ��ʷ��¼	:
// *  	����	: 2015��10��15�� ����9:02:15 	�޸��ˣ�
// *  	����	:
// ************************************************************/
//public class ChooseTableActivity extends Activity {
//	
//	private TextView textView;
//	
//	private GridView gridView;
//	
//	private List<TableInfoBean> tableInfoList;
//	
//	private ChooseTableAdapter adapter;
//	
//	private Button btnOff;
//	
//	
//	
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		// TODO Auto-generated method stub
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.fragment_choose_table);
//		
//		textView = (TextView) findViewById(R.id.text_view);
//		gridView = (GridView) findViewById(R.id.grid_view);
//		
//		Intent intent = getIntent();
//		final int waiterId = intent.getIntExtra("waiterId", 0);
//		final String waiterAccount = intent.getStringExtra("waiterAccount");
//		textView.setText("����Ա��ţ� "+waiterAccount);
//		
//		tableInfoList = getTableInfo();
//		
//		adapter = new ChooseTableAdapter(this, R.layout.table_item, tableInfoList);
//		
//		gridView.setAdapter(adapter);
//		
//		gridView.setOnItemClickListener(new OnItemClickListener() {
//
//			@Override
//			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//				TableInfoBean tableInfoBean = tableInfoList.get(position);
//				int state = tableInfoBean.getState();
//				Log.i("tag", String.valueOf(state));
//				switch (state) {
//				case 1:
//					//state==1˵�� ---δ����---��ת���������� ---ͬʱҪ��tableInfoBean���ݹ���
//					Intent intent = new Intent(ChooseTableActivity.this, TableInfoActivity.class);
//					intent.putExtra("tableInfoBean", tableInfoBean);
//					intent.putExtra("waiterId", waiterId);
//					intent.putExtra("WaiterAccount", waiterAccount);
//					startActivity(intent);
//					break;
//				case 2:
//					//state==2 ˵�� ---�ѿ���---��ת�����״̬����OrderStateActivity
//					Intent intent2 = new Intent(ChooseTableActivity.this, OrderStateActivity.class);
//					
//					//�������Ż�� ��������Ӧ�� δ�����  OrderBean���� ����ͬһ�����ӿ����кܶඩ������ֻ����һ��δ�����
//					int tableInfoId = tableInfoBean.getId();//��T_ORDER�����TABLE_INFO_ID���T_TABLE_INFO��ID�ֶι���
//					//�õ���T_ORDER��TABLE_INFO_ID�ֶκ�������Ƿ񸶿�ISPAY�ֶ�(δ������10���������Ӧ��ͨ����ѯ���ݿ��ã����Ϳ���ȷ�����ĸ�����		
//					OrderBean orderBean = OrderDao.getInstance(ChooseTableActivity.this).getOrderBean(tableInfoId, 10);
//					
//					intent2.putExtra("OrderBean", orderBean);
//					intent2.putExtra("TableInfoBean", tableInfoBean);
//					startActivity(intent2);
//
//				default:
//					break;
//				}
//				
//			}
//		});
//		
//		btnOff = (Button) findViewById(R.id.btn_off);
//		btnOff.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				Intent intent = new Intent(ChooseTableActivity.this, LoginActivity.class);
//				startActivity(intent);
//				
//			}
//		});
//	}
//
//
//
//	/**
//	 *  �������� : getTableInfo
//	 *  �������� : Ϊ����Դ��ʼ������ 
//	 *  ����������ֵ˵����
//	 *  	@return
//	 *
//	 *  �޸ļ�¼��
//	 *  	���� ��2015��10��16�� ����3:57:48	�޸��ˣ�hxf
//	 *  	����	��
//	 * 					
//	 */
//	private List<TableInfoBean> getTableInfo() {
//		tableInfoList = TableInfoDao.getInstance(this).loadTableInfoBean();
//		return tableInfoList;
//	}
//
//}
