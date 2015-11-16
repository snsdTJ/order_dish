/************************************************************
 *	版权所有  (c)2011,   hxf<p>	
 *  文件名称	：ChooseTableAdapter.java<p>
 *
 *  创建时间	：2015年10月16日 上午11:05:56 
 *  当前版本号：v1.0
 ************************************************************/
package com.example.orderservice.adapter;

import java.util.List;

import com.example.orderservice.R;
import com.example.orderservice.model.TableInfoBean;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/************************************************************
 *  内容摘要	：<p>
 *
 *  作者	：hxf
 *  创建时间	：2015年10月16日 上午11:05:56 
 *  当前版本号：v1.0
 *  历史记录	:
 *  	日期	: 2015年10月16日 上午11:05:56 	修改人：
 *  	描述	:
 ************************************************************/
public class ChooseTableAdapter extends ArrayAdapter<TableInfoBean> {

	private int resourceId;
	/**
	 * 构造函数：ChooseTableAdapter
	 * 函数功能:
	 * 参数说明：
	 * 		@param context
	 * 		@param resource
	 * 		@param objects
	 */
	public ChooseTableAdapter(Context context, int resourceId, List<TableInfoBean> tableInfoList) {
		super(context, resourceId, tableInfoList);
		// TODO Auto-generated constructor stub
		this.resourceId = resourceId;
	}
	
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		TableInfoBean tableInfoBean = getItem(position);
		
		if (convertView == null) {
			convertView = LayoutInflater.from(getContext()).inflate(resourceId, null);
		}
		ImageView imageView = (ImageView) convertView.findViewById(R.id.image_view);
		TextView textView = (TextView) convertView.findViewById(R.id.text_view);
		
		if (tableInfoBean.getState()==1) {
			imageView.setBackgroundResource(R.drawable.table_unorder);
		}else if (tableInfoBean.getState() == 2) {
			imageView.setBackgroundResource(R.drawable.table_ordered);
		}
		/*
		 * 如果要显示该桌可以坐多少人，则用下面代码
		 * int sizeId = tableInfoBean.getSizeId();
		 * int size =  查询ID = sizeId的TABLE_SIZE表
		 * textView.setText(size+ ":人桌");
		 */
		textView.setText(String.valueOf(tableInfoBean.getNo())+" 号");//注意这里setText（）方法的参数是String类型
		
			
		return convertView;
	}
	

}
