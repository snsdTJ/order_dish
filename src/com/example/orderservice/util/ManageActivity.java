/************************************************************
 *	��Ȩ����  (c)2011,   hxf<p>	
 *  �ļ�����	��ManageActivity.java<p>
 *
 *  ����ʱ��	��2015��11��1�� ����1:12:25 
 *  ��ǰ�汾�ţ�v1.0
 ************************************************************/
package com.example.orderservice.util;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.util.Log;

/************************************************************
 *  ����ժҪ	��<p>
 *
 *  ����	��hxf
 *  ����ʱ��	��2015��11��1�� ����1:12:25 
 *  ��ǰ�汾�ţ�v1.0
 *  ��ʷ��¼	:
 *  	����	: 2015��11��1�� ����1:12:25 	�޸��ˣ�
 *  	����	:
 ************************************************************/
public class ManageActivity {
	
private static List<Activity> listActivity = new ArrayList<Activity>();
	
	public static void  addActivity(Activity activity){
		listActivity.add(activity);
	}
	
	public static void removeActivity(Activity activity){
		listActivity.remove(activity);
	}
	
	public static void finishAll(){
		for(Activity activity: listActivity){
			if (!activity.isFinishing()) {
				Log.i("�ر�����ҳ��  ", "�ر�����ҳ��");
				activity.finish();
				
			}
		}
		Log.i("��ʣ���ٻҳ���ڼ��� ", String.valueOf(listActivity.size()));
	}

}
