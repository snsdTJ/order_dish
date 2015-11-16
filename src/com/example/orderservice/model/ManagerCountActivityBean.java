/************************************************************
 *	��Ȩ����  (c)2011,   hxf<p>	
 *  �ļ�����	��ManagerCountActivityBean.java<p>
 *
 *  ����ʱ��	��2015��10��29�� ����10:29:23 
 *  ��ǰ�汾�ţ�v1.0
 ************************************************************/
package com.example.orderservice.model;

import java.util.List;

import com.example.orderservice.dao.ManagerCountActivityDao;

import android.content.Context;

/************************************************************
 * ����ժҪ ���������ManagerCountActivity�ҳ���ʵ����
 *
 * ���� ��hxf ����ʱ�� ��2015��10��29�� ����10:29:23 ��ǰ�汾�ţ�v1.0 ��ʷ��¼ : ���� : 2015��10��29��
 * ����10:29:23 �޸��ˣ� ���� :
 ************************************************************/
public class ManagerCountActivityBean {

	private List<OrderBean> orderBeanList;

	private float sumTurnover;

	public List<OrderBean> getOrderBeanList() {
		return orderBeanList;
	}

	// ���뿪ʼ���ںͽ������ڣ�Ȼ�����ManagerCountActivityDAO���еķ�����ȡ���ݿ������ݣ��洢�ڼ�����
	public boolean setOrderBeanList(String startDate, String endDate, Context context) {

		orderBeanList = ManagerCountActivityDao.getInstance(context).getOrderBeanList(startDate, endDate);
		
		if (orderBeanList==null) {
			return false;
		}

		return true;
	}

	public float getSumTurnover() {
		return sumTurnover;
	}

	public boolean setSumTurnover() {
		if (orderBeanList != null && !orderBeanList.isEmpty()) {
			for (OrderBean ob : orderBeanList) {
				sumTurnover += ob.getTotalMoney();
			}
			return true;
		}
		return false;
	}

}
