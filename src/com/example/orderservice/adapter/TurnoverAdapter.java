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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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
public class TurnoverAdapter extends ArrayAdapter<OrderBean> {
	
	private int resourceId;
	
	private List<OrderBean> orderBeanList;
	
	
	
	/**
	 * ���캯����TurnoverAdapter
	 * ��������:
	 * ����˵����
	 * 		@param context
	 * 		@param resource
	 * 		@param objects
	 */
	public TurnoverAdapter(Context context, int resource, List<OrderBean> orderBeanList) {
		super(context, resource, orderBeanList);
		
		this.resourceId = resource;
		
		this.orderBeanList = orderBeanList;
		
	}
	
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
		}
		TextView tvCashierName = (TextView) convertView.findViewById(R.id.tv_cashier_name);
		TextView tvTurnover = (TextView) convertView.findViewById(R.id.tv_turnover);
		TextView tvDate = (TextView) convertView.findViewById(R.id.tv_date);
		
//		if (position==0) {
//			tvCashierName.setText("����Ա");
//			tvTurnover.setText("Ӫ��");
//			tvDate.setText("ʱ��");
//		}else {
//			OrderBean orderBean = getItem(position-1);
//			tvCashierName.setText(orderBean.getCashierCode());
//			tvTurnover.setText(String.valueOf(orderBean.getTotalMoney()));
//			tvDate.setText(orderBean.getEndDateTime());
//		}
		OrderBean orderBean = getItem(position);
		tvCashierName.setText(orderBean.getCashierCode());
		tvTurnover.setText(String.valueOf(orderBean.getTotalMoney()));
		tvDate.setText(orderBean.getEndDateTime());
		
		for (OrderBean ob : orderBeanList) {
			System.out.println("D"+ob.getEndDateTime()+"---"+ob.getTotalMoney()+"---"+ob.getId()+"---"+ob.getBeginDateTime());
		}
		
		return convertView;
	}
//	/**
//	 *  �������� ��getCount
//	 *  �������� ��  
//	 *  ����˵�� ��
//	 *  	@return
//	 *  ����ֵ��
//	 *  	
//	 *  �޸ļ�¼��
//	 *  ���� ��2015��10��25�� ����1:35:44	�޸��ˣ�hxf
//	 *  ���� ��
//	 * 					
//	 */
//	@Override
//	public int getCount() {
//		
//		return orderBeanList.size()+1;
//	}
	

}
