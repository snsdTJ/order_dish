/************************************************************
 *	��Ȩ����  (c)2011,   hxf<p>	
 *  �ļ�����	��ChooseTableFragment.java<p>
 *
 *  ����ʱ��	��2015��10��26�� ����4:30:13 
 *  ��ǰ�汾�ţ�v1.0
 ************************************************************/
package com.example.orderservice.view.waiter;

import com.example.orderservice.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/************************************************************
 *  ����ժҪ	��<p>
 *
 *  ����	��hxf
 *  ����ʱ��	��2015��10��26�� ����4:30:13 
 *  ��ǰ�汾�ţ�v1.0
 *  ��ʷ��¼	:
 *  	����	: 2015��10��26�� ����4:30:13 	�޸��ˣ�
 *  	����	:
 ************************************************************/
public class SpecialFragment extends Fragment {
	

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		View view = inflater.inflate(R.layout.fragment_special, container, false);
		
		TextView textView = (TextView) view.findViewById(R.id.text_view);
		
		textView.setText("������ɫ�ˣ� ֥ʿ���͹Ű���Ϻ");
		
		return view;
	}

}
