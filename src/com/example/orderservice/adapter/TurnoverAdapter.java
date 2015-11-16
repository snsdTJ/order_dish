/************************************************************
 *	版权所有  (c)2011,   hxf<p>	
 *  文件名称	：TurnoverAdapter.java<p>
 *
 *  创建时间	：2015年10月25日 下午12:21:53 
 *  当前版本号：v1.0
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
 *  内容摘要	：<p>
 *
 *  作者	：hxf
 *  创建时间	：2015年10月25日 下午12:21:53 
 *  当前版本号：v1.0
 *  历史记录	:
 *  	日期	: 2015年10月25日 下午12:21:53 	修改人：
 *  	描述	:
 ************************************************************/
public class TurnoverAdapter extends ArrayAdapter<OrderBean> {
	
	private int resourceId;
	
	private List<OrderBean> orderBeanList;
	
	
	
	/**
	 * 构造函数：TurnoverAdapter
	 * 函数功能:
	 * 参数说明：
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
//			tvCashierName.setText("收银员");
//			tvTurnover.setText("营收");
//			tvDate.setText("时间");
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
//	 *  函数名称 ：getCount
//	 *  功能描述 ：  
//	 *  参数说明 ：
//	 *  	@return
//	 *  返回值：
//	 *  	
//	 *  修改记录：
//	 *  日期 ：2015年10月25日 下午1:35:44	修改人：hxf
//	 *  描述 ：
//	 * 					
//	 */
//	@Override
//	public int getCount() {
//		
//		return orderBeanList.size()+1;
//	}
	

}
