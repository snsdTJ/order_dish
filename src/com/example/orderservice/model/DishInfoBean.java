/************************************************************
 *	��Ȩ����  (c)2011,   hxf<p>	
 *  �ļ�����	��DishInfoBean.java<p>
 *
 *  ����ʱ��	��2015��10��15�� ����8:18:24 
 *  ��ǰ�汾�ţ�v1.0
 ************************************************************/
package com.example.orderservice.model;

/************************************************************
 *  ����ժҪ	��<p>
 *
 *  ����	��hxf
 *  ����ʱ��	��2015��10��15�� ����8:18:24 
 *  ��ǰ�汾�ţ�v1.0
 *  ��ʷ��¼	:
 *  	����	: 2015��10��15�� ����8:18:24 	�޸��ˣ�
 *  	����	:
 ************************************************************/
public class DishInfoBean {
	
	private int id;
	
	private String name;
	
	private int dishType;//���
	
	private float price;
	
	private int currencyType;//���
	
	private int unit;//���
	
	private String photo;
	
	//ͨ�������õ����� ---------------------------��
	//������Ƿ���Ҫ�����������ֶ�����set����
	private String dishTypeName; //ͨ��DISH_TYPE���������T_DATA_DICTIONARY���е�IDֵ��Ȼ����NAME�ֶ�ֵ
	private String currencyTypeName;//ͨ��CURRENCY_TYPE���������T_DATA_DICTIONARY���е�IDֵ��Ȼ����NAME�ֶ�ֵ
	private String unitName;//ͨ��UNIT���������T_DATA_DICTIONARY���е�IDֵ��Ȼ����NAME�ֶ�ֵ
	

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
