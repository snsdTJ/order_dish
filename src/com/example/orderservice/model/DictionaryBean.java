/************************************************************
 *	版权所有  (c)2011,   hxf<p>	
 *  文件名称	：DictionaryBean.java<p>
 *
 *  创建时间	：2015年10月15日 下午8:12:30 
 *  当前版本号：v1.0
 ************************************************************/
package com.example.orderservice.model;

/************************************************************
 *  内容摘要	：<p>
 *
 *  作者	：hxf
 *  创建时间	：2015年10月15日 下午8:12:30 
 *  当前版本号：v1.0
 *  历史记录	:
 *  	日期	: 2015年10月15日 下午8:12:30 	修改人：
 *  	描述	:
 ************************************************************/
public class DictionaryBean {
	
	private int id;
	
	private String code;
	
	private String name;
	
	private int type;//外键
	
	//通过外键获得的数据 ---------------------------》
	//像这种从外键获得的数据，要不要设置 public void setTypeName(String typeName) {this.typeName = typeName;}方法？？？？
	private String typeName; //通过TYPE这个外键获得T_DATA_DICTIONARY_TYPE表中的ID值，然后查出NAME字段值
	
	
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	
	
	/**
	 *  函数名称 ：toString
	 *  功能描述 ：  
	 *  参数说明 ：
	 *  	@return
	 *  返回值：
	 *  	
	 *  修改记录：
	 *  日期 ：2015年10月20日 上午11:11:20	修改人：hxf
	 *  描述 ：
	 * 					
	 */
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.name;
	}
}
