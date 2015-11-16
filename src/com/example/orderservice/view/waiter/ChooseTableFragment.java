/************************************************************
 *	��Ȩ����  (c)2011,   hxf<p>	
 *  �ļ�����	��ChooseTableFragment.java<p>
 *
 *  ����ʱ��	��2015��10��26�� ����4:30:13 
 *  ��ǰ�汾�ţ�v1.0
 ************************************************************/
package com.example.orderservice.view.waiter;

import java.util.List;

import com.example.orderservice.R;
import com.example.orderservice.adapter.ChooseTableAdapter;
import com.example.orderservice.dao.OrderDao;
import com.example.orderservice.dao.TableInfoDao;
import com.example.orderservice.model.OrderBean;
import com.example.orderservice.model.TableInfoBean;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

/************************************************************
 * ����ժҪ ��
 * <p>
 *
 * ���� ��hxf ����ʱ�� ��2015��10��26�� ����4:30:13 ��ǰ�汾�ţ�v1.0 ��ʷ��¼ : ���� : 2015��10��26��
 * ����4:30:13 �޸��ˣ� ���� :
 ************************************************************/
public class ChooseTableFragment extends Fragment {

	private TextView textView;

	private GridView gridView;

	private List<TableInfoBean> tableInfoList;

	private ChooseTableAdapter adapter;
	
	//ͨ��Intent���ݹ���������
	private int waiterId;
	private String waiterAccount;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		View view = inflater.inflate(R.layout.fragment_choose_table, container, false);
		
		// ��ʼ��ͨ��Intent���ݹ�������
		Intent intent = getActivity().getIntent();
		waiterId = intent.getIntExtra("waiterId", 0);
		waiterAccount = intent.getStringExtra("waiterAccount");

		// ��ʼ���������
		initComponent(view);

		// ���ü�����
		registerListener();

		return view;
	}

	/**
	 *  �������� : initComponent
	 *  �������� :  
	 *  ����������ֵ˵���� 					
	 */
	private void initComponent(View view) {

		textView = (TextView) view.findViewById(R.id.text_view);
		gridView = (GridView) view.findViewById(R.id.grid_view);

		textView.setText("����Ա��ţ� " + waiterAccount);
		
		//��ʼ��GridView������Դ
		tableInfoList = getTableInfo();

		adapter = new ChooseTableAdapter(getActivity(), R.layout.table_item, tableInfoList);

		// ��ȡ�ֻ���Ļ��Ϣ������ȡ��Ļ�Ŀ��
		Display display = getActivity().getWindow().getWindowManager().getDefaultDisplay();
		DisplayMetrics outMetrics = new DisplayMetrics();// �ö������ڴ����Ļ��Ϣ�����С��ֵ
		display.getMetrics(outMetrics);// display�����Ļ������������Ϳ��Ը�outMetrics��display�����Ļ��ֵ
		int screenWidth = outMetrics.widthPixels;
		if (screenWidth >= 700) {
			gridView.setNumColumns(3);
		} else {
			gridView.setNumColumns(2);
		}

		gridView.setAdapter(adapter);
		
	}

	/**
	 *  �������� : registerListener
	 *  �������� :  
	 *  ����������ֵ˵����
	 *
	 *  �޸ļ�¼��
	 *  	���� ��2015��11��9�� ����6:23:02	�޸��ˣ�hxf
	 *  	����	��
	 * 					
	 */
	private void registerListener() {
		
		gridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				TableInfoBean tableInfoBean = tableInfoList.get(position);
				//state������״̬��state==1˵�� ---δ������ // state==2 ˵�� ---�ѿ���
				int state = tableInfoBean.getState();
				Log.i("tag", String.valueOf(state));
				
				switch (state) {
				case 1:
					// state==1˵�� ---δ����---��ת���������� ---ͬʱҪ��tableInfoBean���ݹ���
					Intent intent = new Intent(getActivity(), TableInfoActivity.class);
					intent.putExtra("tableInfoBean", tableInfoBean);
					intent.putExtra("waiterId", waiterId);
					intent.putExtra("WaiterAccount", waiterAccount);
					startActivity(intent);
					break;
				case 2:
					// state==2 ˵�� ---�ѿ���---��ת�����״̬����OrderStateActivity
					Intent intent2 = new Intent(getActivity(), OrderStateActivity.class);

					// �������Ż�� ��������Ӧ�� δ����� OrderBean���� ����ͬһ�����ӿ����кܶඩ������ֻ����һ��δ�����
					int tableInfoId = tableInfoBean.getId();// ��T_ORDER�����TABLE_INFO_ID���T_TABLE_INFO��ID�ֶι���
					// �õ���T_ORDER��TABLE_INFO_ID�ֶκ�������Ƿ񸶿�ISPAY�ֶ�(δ������10���������Ӧ��ͨ����ѯ���ݿ��ã����Ϳ���ȷ�����ĸ�����
					OrderBean orderBean = OrderDao.getInstance(getActivity()).getOrderBean(tableInfoId, 10);

					intent2.putExtra("OrderBean", orderBean);
					intent2.putExtra("TableInfoBean", tableInfoBean);
					startActivity(intent2);

				default:
					break;
				}
			}
		});		
	}

	/**
	 * �������� : getTableInfo �������� : Ϊ����Դ��ʼ������ ����������ֵ˵����
	 * 
	 * @return
	 *
	 * 		�޸ļ�¼�� ���� ��2015��10��16�� ����3:57:48 �޸��ˣ�hxf ���� ��
	 * 
	 */
	private List<TableInfoBean> getTableInfo() {
		tableInfoList = TableInfoDao.getInstance(getActivity()).loadTableInfoBean();
		return tableInfoList;
	}

}
