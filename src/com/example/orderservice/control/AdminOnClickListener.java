/************************************************************
 *	��Ȩ����  (c)2011,   hxf<p>	
 *  �ļ�����	��AdminOnClickListener.java<p>
 *
 *  ����ʱ��	��2015��10��23�� ����10:46:37 
 *  ��ǰ�汾�ţ�v1.0
 ************************************************************/
package com.example.orderservice.control;

import com.example.orderservice.R;
import com.example.orderservice.view.admin.AdminAddDishActivity;
import com.example.orderservice.view.admin.AdminManageDishActivity;
import com.example.orderservice.view.LoginActivity;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;

/************************************************************
 * ����ժҪ ��
 * <p>
 *
 * ���� ��hxf ����ʱ�� ��2015��10��23�� ����10:46:37 ��ǰ�汾�ţ�v1.0 ��ʷ��¼ : ���� : 2015��10��23��
 * ����10:46:37 �޸��ˣ� ���� :
 ************************************************************/
public class AdminOnClickListener implements OnClickListener {
	
	private Context context;
	
	public AdminOnClickListener(Context context){
		this.context = context;
	}

	/**
	 * �������� ��onClick �������� �� ����˵�� ��
	 * 
	 * @param v
	 *            ����ֵ��
	 * 
	 *            �޸ļ�¼�� ���� ��2015��10��23�� ����10:46:37 �޸��ˣ�hxf ���� ��
	 * 
	 */
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_add_dish:
			Intent addDishIntent = new Intent(context, AdminAddDishActivity.class);
			context.startActivity(addDishIntent);
			
			break;
		case R.id.iv_manage_dish:
			Intent manageDishIntent = new Intent(context, AdminManageDishActivity.class);
			context.startActivity(manageDishIntent);
			break;
		


		default:
			break;
		}

	}

}
