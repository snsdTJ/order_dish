/************************************************************
 *	��Ȩ����  (c)2011,   hxf<p>	
 *  �ļ�����	��OrderDetailBean.java<p>
 *
 *  ����ʱ��	��2015��10��15�� ����8:32:51 
 *  ��ǰ�汾�ţ�v1.0
 ************************************************************/
package com.example.orderservice.model;

import java.io.Serializable;

/************************************************************
 *  ����ժҪ	��<p>
 *
 *  ����	��hxf
 *  ����ʱ��	��2015��10��15�� ����8:32:51 
 *  ��ǰ�汾�ţ�v1.0
 *  ��ʷ��¼	:
 *  	����	: 2015��10��15�� ����8:32:51 	�޸��ˣ�
 *  	����	:
 ************************************************************/
public class OrderDetailBean implements Serializable {
	
	/**
	 * serialVersionUID:TODO
	 * �ֶ�
	 * @since Ver 1.1
	 */
	
	private static final long serialVersionUID = 1L;

	private int id;
	
	private int orderId;//���
	
	private int dishInfoId;//���
	
	private float currentPrice;
	
	private int dishNumber;
	
	//ͨ�������õ����� ---------------------------��
	//������Ƿ���Ҫ��������2���ֶ�����set����
	private OrderBean orderBean = new OrderBean();//ͨ��ORDER_ID���������T_ORDER���е�IDֵ��Ȼ������ֵ����Ӧ�������ֶ�
	private String dishName;//ͨ��DISH_INFO_ID���������T_DISH_INFO���е�IDֵ��Ȼ����NAME�ֶ�ֵ
	
	
	
	public String getDishName() {
		return dishName;
	}

	public void setDishName(String dishName) {
		this.dishName = dishName;
	}

	public OrderBean getOrderBean() {
		return orderBean;
	}

	public void setOrderBean(OrderBean orderBean) {
		this.orderBean = orderBean;
	}

	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public int getDishInfoId() {
		return dishInfoId;
	}

	public void setDishInfoId(int dishInfoId) {
		this.dishInfoId = dishInfoId;
	}

	public float getCurrentPrice() {
		return currentPrice;
	}

	public void setCurrentPrice(float currentPrice) {
		this.currentPrice = currentPrice;
	}

	public int getDishNumber() {
		return dishNumber;
	}

	public void setDishNumber(int dishNumber) {
		this.dishNumber = dishNumber;
	}
	
	

}
