/************************************************************
 *	版权所有  (c)2011,   hxf<p>	
 *  文件名称	：OrderDetailBean.java<p>
 *
 *  创建时间	：2015年10月15日 下午8:32:51 
 *  当前版本号：v1.0
 ************************************************************/
package com.example.orderservice.model;

import java.io.Serializable;

/************************************************************
 *  内容摘要	：<p>
 *
 *  作者	：hxf
 *  创建时间	：2015年10月15日 下午8:32:51 
 *  当前版本号：v1.0
 *  历史记录	:
 *  	日期	: 2015年10月15日 下午8:32:51 	修改人：
 *  	描述	:
 ************************************************************/
public class OrderDetailBean implements Serializable {
	
	/**
	 * serialVersionUID:TODO
	 * 字段
	 * @since Ver 1.1
	 */
	
	private static final long serialVersionUID = 1L;

	private int id;
	
	private int orderId;//外键
	
	private int dishInfoId;//外键
	
	private float currentPrice;
	
	private int dishNumber;
	
	//通过外键获得的数据 ---------------------------》
	//不清楚是否需要给下面这2个字段设置set方法
	private OrderBean orderBean = new OrderBean();//通过ORDER_ID这个外键获得T_ORDER表中的ID值，然后查出该值所对应的所有字段
	private String dishName;//通过DISH_INFO_ID这个外键获得T_DISH_INFO表中的ID值，然后查出NAME字段值
	
	
	
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
