/************************************************************
 *	��Ȩ����  (c)2011,   hxf<p>	
 *  �ļ�����	��ListViewOrderDishAdapter.java<p>
 *
 *  ����ʱ��	��2015��10��20�� ����7:46:48 
 *  ��ǰ�汾�ţ�v1.0
 ************************************************************/
package com.example.orderservice.adapter;

import java.util.List;

import com.example.orderservice.R;
import com.example.orderservice.model.OrderDetailBean;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

/************************************************************
 *  ����ժҪ	��<p>
 *
 *  ����	��hxf
 *  ����ʱ��	��2015��10��20�� ����7:46:48 
 *  ��ǰ�汾�ţ�v1.0
 *  ��ʷ��¼	:
 *  	����	: 2015��10��20�� ����7:46:48 	�޸��ˣ�
 *  	����	:
 ************************************************************/
public class ListViewOrdeStateAdapter extends ArrayAdapter<OrderDetailBean> {
	
	private int resource;
	
	
	public ListViewOrdeStateAdapter(Context context, int resource, List<OrderDetailBean> orderDetailBeanList) {
		super(context, resource, orderDetailBeanList);
		this.resource = resource;
	}
	
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		final OrderDetailBean orderDetailBean = getItem(position);
		
		if (convertView == null) {
			convertView = LayoutInflater.from(getContext()).inflate(resource, parent, false);
		}
		
		TextView tvDishName = (TextView) convertView.findViewById(R.id.tv_dish_name);
		TextView tvDishNumber = (TextView) convertView.findViewById(R.id.tv_dish_Number);
		TextView tvDishPrice = (TextView) convertView.findViewById(R.id.tv_dish_price);
		TextView tvDishSum = (TextView) convertView.findViewById(R.id.tv_dish_sum);
		
		
		String dishName = orderDetailBean.getDishName();
		int dishNumber = orderDetailBean.getDishNumber();
		float price = orderDetailBean.getCurrentPrice();
		float sum = price*dishNumber;
		
		tvDishName.setText(dishName);
		tvDishNumber.setText(String.valueOf(dishNumber));
		tvDishPrice.setText(String.valueOf(price));
		tvDishSum.setText(String.valueOf(sum));


		return convertView;
	}

}
