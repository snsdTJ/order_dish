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
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TableLayout;
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
public class SearchOrderAdapter extends ArrayAdapter<OrderBean> {
	
	private int resourceId;
	
	private List<OrderBean> orderBeanList;
	
	private int clickItem = -1;
	
	
	public void setClickItem(int position){
		this.clickItem = position;
	}
	
	
	
	/**
	 * 构造函数：TurnoverAdapter
	 * 函数功能:
	 * 参数说明：
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
			tvOrderId.setText("订单号");
			tvTableNumber.setText("桌号");
			tvBeginTime.setText("开桌时间");
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
	 *  函数名称 ：getCount
	 *  功能描述 ：  
	 *  参数说明 ：
	 *  	@return
	 *  返回值：
	 *  	
	 *  修改记录：
	 *  日期 ：2015年10月25日 下午1:35:44	修改人：hxf
	 *  描述 ：
	 * 					
	 */
	@Override
	public int getCount() {
		
		return orderBeanList.size()+1;
	}
	

}
