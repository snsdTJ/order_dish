/************************************************************
 *	��Ȩ����  (c)2011,   hxf<p>	
 *  �ļ�����	��DictionaryBean.java<p>
 *
 *  ����ʱ��	��2015��10��15�� ����8:12:30 
 *  ��ǰ�汾�ţ�v1.0
 ************************************************************/
package com.example.orderservice.model;

/************************************************************
 *  ����ժҪ	��<p>
 *
 *  ����	��hxf
 *  ����ʱ��	��2015��10��15�� ����8:12:30 
 *  ��ǰ�汾�ţ�v1.0
 *  ��ʷ��¼	:
 *  	����	: 2015��10��15�� ����8:12:30 	�޸��ˣ�
 *  	����	:
 ************************************************************/
public class DictionaryBean {
	
	private int id;
	
	private String code;
	
	private String name;
	
	private int type;//���
	
	//ͨ�������õ����� ---------------------------��
	//�����ִ������õ����ݣ�Ҫ��Ҫ���� public void setTypeName(String typeName) {this.typeName = typeName;}������������
	private String typeName; //ͨ��TYPE���������T_DATA_DICTIONARY_TYPE���е�IDֵ��Ȼ����NAME�ֶ�ֵ
	
	
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
	 *  �������� ��toString
	 *  �������� ��  
	 *  ����˵�� ��
	 *  	@return
	 *  ����ֵ��
	 *  	
	 *  �޸ļ�¼��
	 *  ���� ��2015��10��20�� ����11:11:20	�޸��ˣ�hxf
	 *  ���� ��
	 * 					
	 */
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.name;
	}
}
