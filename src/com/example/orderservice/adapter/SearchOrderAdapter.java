/************************************************************
 *	��Ȩ����  (c)2011,   hxf<p>	
 *  �ļ�����	��TurnoverAdapter.java<p>
 *
 *  ����ʱ��	��2015��10��25�� ����12:21:53 
 *  ��ǰ�汾�ţ�v1.0
 ************************************************************/
package com.example.orderservice.adapter;

import java.util.List;

import com.example.orderservice.R;
import com.example.orderservice.model.OrderBean;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TableLayout;
import android.widget.TextView;

/************************************************************
 *  ����ժҪ	��<p>
 *
 *  ����	��hxf
 *  ����ʱ��	��2015��10��25�� ����12:21:53 
 *  ��ǰ�汾�ţ�v1.0
 *  ��ʷ��¼	:
 *  	����	: 2015��10��25�� ����12:21:53 	�޸��ˣ�
 *  	����	:
 ************************************************************/
public class SearchOrderAdapter extends ArrayAdapter<OrderBean> {
	
	private int resourceId;
	
	private List<OrderBean> orderBeanList;
	
	private int clickItem = -1;
	
	
	public void setClickItem(int position){
		this.clickItem = position;
	}
	
	
	
	/**
	 * ���캯����TurnoverAdapter
	 * ��������:
	 * ����˵����
	 * 		@param context
	 * 		@param resource
	 * 		@param objects
	 */
	public SearchOrderAdapter(Context context, int resource, List<OrderBean> orderBeanList) {
		super(context, resource, orderBeanList);
		
		this.resourceId = resource;
		
		this.orderBeanList = orderBeanList;
		
	}
	
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
		}
		TextView tvOrderId = (TextView) convertView.findViewById(R.id.tv_order_id);
		TextView tvTableNumber = (TextView) convertView.findViewById(R.id.tv_table_number);
		TextView tvBeginTime = (TextView) convertView.findViewById(R.id.tv_begin_time);
		TableLayout tableLayout = (TableLayout) convertView.findViewById(R.id.table_layout);
		if (position==0) {
			tvOrderId.setText("������");
			tvTableNumber.setText("����");
			tvBeginTime.setText("����ʱ��");
		}else {
			OrderBean orderBean = getItem(position-1);
			tvOrderId.setText(String.valueOf(orderBean.getId()));
			tvTableNumber.setText(String.valueOf(orderBean.getTableNo()));
			tvBeginTime.setText(orderBean.getBeginDateTime());
		}
		if (clickItem == position) {
			tableLayout.setBackgroundColor(Color.parseColor("#008000"));
		}else{
			tableLayout.setBackgroundColor(Color.parseColor("#31312d"));
		}
		
		return convertView;
	}
	/**
	 *  �������� ��getCount
	 *  �������� ��  
	 *  ����˵�� ��
	 *  	@return
	 *  ����ֵ��
	 *  	
	 *  �޸ļ�¼��
	 *  ���� ��2015��10��25�� ����1:35:44	�޸��ˣ�hxf
	 *  ���� ��
	 * 					
	 */
	@Override
	public int getCount() {
		
		return orderBeanList.size()+1;
	}
	

}
