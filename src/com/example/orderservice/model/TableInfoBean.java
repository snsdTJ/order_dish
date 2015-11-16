/************************************************************
 *	版权所有  (c)2011,   hxf<p>	
 *  文件名称	：TableInfoBean.java<p>
 *
 *  创建时间	：2015年10月15日 下午8:36:36 
 *  当前版本号：v1.0
 ************************************************************/
package com.example.orderservice.model;

import java.io.Serializable;

/************************************************************
 *  内容摘要	：<p>
 *
 *  作者	：hxf
 *  创建时间	：2015年10月15日 下午8:36:36 
 *  当前版本号：v1.0
 *  历史记录	:
 *  	日期	: 2015年10月15日 下午8:36:36 	修改人：
 *  	描述	:
 ************************************************************/
public class TableInfoBean implements Serializable {
	
	/**
	 * serialVersionUID:TODO
	 * 字段
	 * @since Ver 1.1
	 */
	
	private static final long serialVersionUID = 3415729876383387900L;

	private int id;
	
	private int no;
	
	private int sizeId;//外键
	
	private int state;//外键
	
	private String describe;
	
	//通过外键获得的数据 ---------------------------》
	//不清楚是否需要给下面这2个字段设置set方法
	private int tableSize;//通过SIZE_ID这个外键获得T_TABLE_SIZE表的ID字段值，然后查出SIZE字段值
	private String stateName;//通过STATE这个外键获得T_DATA_DICTIONARY表的ID字段值，然后查出NAME字段值
	

	public int getTableSize() {
		return tableSize;
	}

	public void setTableSize(int tableSize) {
		this.tableSize = tableSize;
	}

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public int getSizeId() {
		return sizeId;
	}

	public void setSizeId(int sizeId) {
		this.sizeId = sizeId;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getDescribe() {
		return describe;
	}

	public void setDescribe(String describe) {
		this.describe = describe;
	}
	
	

}
