/************************************************************
 *	��Ȩ����  (c)2011,   hxf<p>	
 *  �ļ�����	��TableInfoBean.java<p>
 *
 *  ����ʱ��	��2015��10��15�� ����8:36:36 
 *  ��ǰ�汾�ţ�v1.0
 ************************************************************/
package com.example.orderservice.model;

import java.io.Serializable;

/************************************************************
 *  ����ժҪ	��<p>
 *
 *  ����	��hxf
 *  ����ʱ��	��2015��10��15�� ����8:36:36 
 *  ��ǰ�汾�ţ�v1.0
 *  ��ʷ��¼	:
 *  	����	: 2015��10��15�� ����8:36:36 	�޸��ˣ�
 *  	����	:
 ************************************************************/
public class TableInfoBean implements Serializable {
	
	/**
	 * serialVersionUID:TODO
	 * �ֶ�
	 * @since Ver 1.1
	 */
	
	private static final long serialVersionUID = 3415729876383387900L;

	private int id;
	
	private int no;
	
	private int sizeId;//���
	
	private int state;//���
	
	private String describe;
	
	//ͨ�������õ����� ---------------------------��
	//������Ƿ���Ҫ��������2���ֶ�����set����
	private int tableSize;//ͨ��SIZE_ID���������T_TABLE_SIZE���ID�ֶ�ֵ��Ȼ����SIZE�ֶ�ֵ
	private String stateName;//ͨ��STATE���������T_DATA_DICTIONARY���ID�ֶ�ֵ��Ȼ����NAME�ֶ�ֵ
	

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
