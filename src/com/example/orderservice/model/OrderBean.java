/************************************************************
 *	版权所有  (c)2011,   hxf<p>	
 *  文件名称	：OrderBean.java<p>
 *
 *  创建时间	：2015年10月15日 下午8:23:28 
 *  当前版本号：v1.0
 ************************************************************/
package com.example.orderservice.model;

import java.io.Serializable;
import java.util.Date;

/************************************************************
 *  内容摘要	：<p>
 *
 *  作者	：hxf
 *  创建时间	：2015年10月15日 下午8:23:28 
 *  当前版本号：v1.0
 *  历史记录	:
 *  	日期	: 2015年10月15日 下午8:23:28 	修改人：
 *  	描述	:
 ************************************************************/
public class OrderBean implements Serializable{
	
	/**
	 * serialVersionUID:TODO
	 * 字段
	 * @since Ver 1.1
	 */
	
	private static final long serialVersionUID = 1L;

	private int id;
	
	private int  tableInfoId;//外键
	
	private int waiterId;//外键
	
	private int cashierId;//外键
	
	//private Date endDateTime;  private Date beginDateTime; 
	//由于用ContentValues的put（键，值）方法把数据存入数据库时，该方法的值不支持DateTime类型
	//但由于sqlite的特殊性：就算某列的类型是DateTime类型，也是可以直接把String类型存储进来
	//所以为了方便以后不用在存储时转换数据，就先在这个实体类中把获得到的Date类型数据转换为String类型数据
	private String beginDateTime;
	
	private String endDateTime;
	
	private float totalMoney;
	
	private int isPay;//外键
	
	
	//通过外键获得的数据 ---------------------------》
	//不清楚是否需要给下面这4个字段设置set方法
	private int tableNo; //通过TABLE_INFO_ID这个外键获得T_TABLE_INFO表中的ID值，然后查出NO字段值
	private String waiterCode;//通过WAITER_ID这个外键获得T_EMPLOYEE表中的ID值，然后查出CODE字段值
	private String cashierCode;//通过CASHIER_ID这个外键获得T_EMPLOYEE表中的ID值，然后查出CODE字段值
	private String isPayName;//通过ISPAY这个外键获得T_DATA_DICTIONARY表中的ID值，然后查出NAME字段值
	
	

	public int getTableNo() {
		return tableNo;
	}

	public void setTableNo(int tableNo) {
		this.tableNo = tableNo;
	}

	public String getWaiterCode() {
		return waiterCode;
	}

	public void setWaiterCode(String waiterCode) {
		this.waiterCode = waiterCode;
	}

	public String getCashierCode() {
		return cashierCode;
	}

	public void setCashierCode(String cashierCode) {
		this.cashierCode = cashierCode;
	}

	public String getIsPayName() {
		return isPayName;
	}

	public void setIsPayName(String isPayName) {
		this.isPayName = isPayName;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getTableInfoId() {
		return tableInfoId;
	}

	public void setTableInfoId(int tableInfoId) {
		this.tableInfoId = tableInfoId;
	}

	public int getWaiterId() {
		return waiterId;
	}

	public void setWaiterId(int waiterId) {
		this.waiterId = waiterId;
	}

	public int getCashierId() {
		return cashierId;
	}

	public void setCashierId(int cashierId) {
		this.cashierId = cashierId;
	}

	public String getBeginDateTime() {
		return beginDateTime;
	}

	public void setBeginDateTime(String beginDateTime) {
		this.beginDateTime = beginDateTime;
	}

	public String getEndDateTime() {
		return endDateTime;
	}

	public void setEndDateTime(String endDateTime) {
		this.endDateTime = endDateTime;
	}

	public float getTotalMoney() {
		return totalMoney;
	}

	public void setTotalMoney(float totalMoney) {
		this.totalMoney = totalMoney;
	}

	public int getIsPay() {
		return isPay;
	}

	public void setIsPay(int isPay) {
		this.isPay = isPay;
	}
	
	
	
	

}
