/************************************************************
 *	��Ȩ����  (c)2011,   hxf<p>	
 *  �ļ�����	��TopView.java<p>
 *
 *  ����ʱ��	��2015��10��27�� ����9:50:08 
 *  ��ǰ�汾�ţ�v1.0
 ************************************************************/
package com.example.orderservice.view;

import com.example.orderservice.R;
import com.example.orderservice.util.ManageActivity;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

/************************************************************
 *  ����ժҪ	��<p>
 *
 *  ����	��hxf
 *  ����ʱ��	��2015��10��27�� ����9:50:08 
 *  ��ǰ�汾�ţ�v1.0
 *  ��ʷ��¼	:
 *  	����	: 2015��10��27�� ����9:50:08 	�޸��ˣ�
 *  	����	:
 ************************************************************/
public class TopView extends RelativeLayout {

	

	/**
	 * ���캯����TopView
	 * ��������:
	 * ����˵����
	 * 		@param context
	 */
	public TopView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public TopView(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		View view = LayoutInflater.from(context).inflate(R.layout.top_layout, this, true);
		
		
		View tvOff = view.findViewById(R.id.id_tv_off);
		
		tvOff.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				//�������Ѵ򿪵�Activity����ر�
				ManageActivity.finishAll();
				
			}
		});
	}
	
	

}
