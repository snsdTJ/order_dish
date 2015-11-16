/************************************************************
 *	版权所有  (c)2011,   hxf<p>	
 *  文件名称	：DishInfoBean.java<p>
 *
 *  创建时间	：2015年10月15日 下午8:18:24 
 *  当前版本号：v1.0
 ************************************************************/
package com.example.orderservice.model;

/************************************************************
 *  内容摘要	：<p>
 *
 *  作者	：hxf
 *  创建时间	：2015年10月15日 下午8:18:24 
 *  当前版本号：v1.0
 *  历史记录	:
 *  	日期	: 2015年10月15日 下午8:18:24 	修改人：
 *  	描述	:
 ************************************************************/
public class DishInfoBean {
	
	private int id;
	
	private String name;
	
	private int dishType;//外键
	
	private float price;
	
	private int currencyType;//外键
	
	private int unit;//外键
	
	private String photo;
	
	//通过外键获得的数据 ---------------------------》
	//不清楚是否需要给下面三个字段设置set方法
	private String dishTypeName; //通过DISH_TYPE这个外键获得T_DATA_DICTIONARY表中的ID值，然后查出NAME字段值
	private String currencyTypeName;//通过CURRENCY_TYPE这个外键获得T_DATA_DICTIONARY表中的ID值，然后查出NAME字段值
	private String unitName;//通过UNIT这个外键获得T_DATA_DICTIONARY表中的ID值，然后查出NAME字段值
	

	public String getDishTypeName() {
		return dishTypeName;
	}

	public void setDishTypeName(String dishTypeName) {
		this.dishTypeName = dishTypeName;
	}

	public String getCurrencyTypeName() {
		return currencyTypeName;
	}

	public void setCurrencyTypeName(String currencyTypeName) {
		this.currencyTypeName = currencyTypeName;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getDishType() {
		return dishType;
	}

	public void setDishType(int dishType) {
		this.dishType = dishType;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public int getCurrencyType() {
		return currencyType;
	}

	public void setCurrencyType(int currencyType) {
		this.currencyType = currencyType;
	}

	public int getUnit() {
		return unit;
	}

	public void setUnit(int unit) {
		this.unit = unit;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}
	
	

}
