/************************************************************
 *	��Ȩ����  (c)2011,   hxf<p>	
 *  �ļ�����	��OrderBean.java<p>
 *
 *  ����ʱ��	��2015��10��15�� ����8:23:28 
 *  ��ǰ�汾�ţ�v1.0
 ************************************************************/
package com.example.orderservice.model;

import java.io.Serializable;
import java.util.Date;

/************************************************************
 *  ����ժҪ	��<p>
 *
 *  ����	��hxf
 *  ����ʱ��	��2015��10��15�� ����8:23:28 
 *  ��ǰ�汾�ţ�v1.0
 *  ��ʷ��¼	:
 *  	����	: 2015��10��15�� ����8:23:28 	�޸��ˣ�
 *  	����	:
 ************************************************************/
public class OrderBean implements Serializable{
	
	/**
	 * serialVersionUID:TODO
	 * �ֶ�
	 * @since Ver 1.1
	 */
	
	private static final long serialVersionUID = 1L;

	private int id;
	
	private int  tableInfoId;//���
	
	private int waiterId;//���
	
	private int cashierId;//���
	
	//private Date endDateTime;  private Date beginDateTime; 
	//������ContentValues��put������ֵ�����������ݴ������ݿ�ʱ���÷�����ֵ��֧��DateTime����
	//������sqlite�������ԣ�����ĳ�е�������DateTime���ͣ�Ҳ�ǿ���ֱ�Ӱ�String���ʹ洢����
	//����Ϊ�˷����Ժ����ڴ洢ʱת�����ݣ����������ʵ�����аѻ�õ���Date��������ת��ΪString��������
	private String beginDateTime;
	
	private String endDateTime;
	
	private float totalMoney;
	
	private int isPay;//���
	
	
	//ͨ�������õ����� ---------------------------��
	//������Ƿ���Ҫ��������4���ֶ�����set����
	private int tableNo; //ͨ��TABLE_INFO_ID���������T_TABLE_INFO���е�IDֵ��Ȼ����NO�ֶ�ֵ
	private String waiterCode;//ͨ��WAITER_ID���������T_EMPLOYEE���е�IDֵ��Ȼ����CODE�ֶ�ֵ
	private String cashierCode;//ͨ��CASHIER_ID���������T_EMPLOYEE���е�IDֵ��Ȼ����CODE�ֶ�ֵ
	private String isPayName;//ͨ��ISPAY���������T_DATA_DICTIONARY���е�IDֵ��Ȼ����NAME�ֶ�ֵ
	
	

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
