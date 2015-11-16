/************************************************************
 *	��Ȩ����  (c)2011,   hxf<p>	
 *  �ļ�����	��ChooseTableAdapter.java<p>
 *
 *  ����ʱ��	��2015��10��16�� ����11:05:56 
 *  ��ǰ�汾�ţ�v1.0
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
 *  ����ժҪ	��<p>
 *
 *  ����	��hxf
 *  ����ʱ��	��2015��10��16�� ����11:05:56 
 *  ��ǰ�汾�ţ�v1.0
 *  ��ʷ��¼	:
 *  	����	: 2015��10��16�� ����11:05:56 	�޸��ˣ�
 *  	����	:
 ************************************************************/
public class ChooseTableAdapter extends ArrayAdapter<TableInfoBean> {

	private int resourceId;
	/**
	 * ���캯����ChooseTableAdapter
	 * ��������:
	 * ����˵����
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
		 * ���Ҫ��ʾ���������������ˣ������������
		 * int sizeId = tableInfoBean.getSizeId();
		 * int size =  ��ѯID = sizeId��TABLE_SIZE��
		 * textView.setText(size+ ":����");
		 */
		textView.setText(String.valueOf(tableInfoBean.getNo())+" ��");//ע������setText���������Ĳ�����String����
		
			
		return convertView;
	}
	

}
