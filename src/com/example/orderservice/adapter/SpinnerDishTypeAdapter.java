/************************************************************
 *	版权所有  (c)2011,   hxf<p>	
 *  文件名称	：SpinnerDishTypeAdapter.java<p>
 *
 *  创建时间	：2015年10月19日 下午9:00:05 
 *  当前版本号：v1.0
 ************************************************************/
package com.example.orderservice.adapter;

import java.util.List;

import com.example.orderservice.R;
import com.example.orderservice.model.DictionaryBean;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.TextView;

/************************************************************
 *  内容摘要	：<p>
 *
 *  作者	：hxf
 *  创建时间	：2015年10月19日 下午9:00:05 
 *  当前版本号：v1.0
 *  历史记录	:
 *  	日期	: 2015年10月19日 下午9:00:05 	修改人：
 *  	描述	:
 ************************************************************/
public class SpinnerDishTypeAdapter extends ArrayAdapter<DictionaryBean> {
	
	private int resourceId;
	
	
	public SpinnerDishTypeAdapter(Context context, int resource, List<DictionaryBean> dictionaryBeanList) {
		super(context, resource, dictionaryBeanList);
		this.resourceId = resource;
	}
	

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		DictionaryBean dictionaryBean = getItem(position);
		
		if (convertView == null) {
			convertView = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
		}
		
		TextView textView = (TextView) convertView.findViewById(R.id.text_view);
		textView.setText(dictionaryBean.getName());
		

		Log.i("tag", dictionaryBean.getName());
		return convertView;
	}

}
