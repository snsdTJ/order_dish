/************************************************************
 *	��Ȩ����  (c)2011,   hxf<p>	
 *  �ļ�����	��SpinnerDishTypeAdapter.java<p>
 *
 *  ����ʱ��	��2015��10��19�� ����9:00:05 
 *  ��ǰ�汾�ţ�v1.0
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
 *  ����ժҪ	��<p>
 *
 *  ����	��hxf
 *  ����ʱ��	��2015��10��19�� ����9:00:05 
 *  ��ǰ�汾�ţ�v1.0
 *  ��ʷ��¼	:
 *  	����	: 2015��10��19�� ����9:00:05 	�޸��ˣ�
 *  	����	:
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
