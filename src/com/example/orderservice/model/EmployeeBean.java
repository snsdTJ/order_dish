/************************************************************
 *	��Ȩ����  (c)2011,   hxf<p>	
 *  �ļ�����	��EmployeeBean.java<p>
 *
 *  ����ʱ��	��2015��10��15�� ����8:21:03 
 *  ��ǰ�汾�ţ�v1.0
 ************************************************************/
package com.example.orderservice.model;

/************************************************************
 *  ����ժҪ	��<p>
 *
 *  ����	��hxf
 *  ����ʱ��	��2015��10��15�� ����8:21:03 
 *  ��ǰ�汾�ţ�v1.0
 *  ��ʷ��¼	:
 *  	����	: 2015��10��15�� ����8:21:03 	�޸��ˣ�
 *  	����	:
 ************************************************************/
public class EmployeeBean {
	
	private int id;
	
	private String code;//�˺�
	
	private String PWD;//����
	
	private String userName;//�û���
	
	private int roleId;//���
	
	//ͨ�������õ����� ---------------------------��
	//������Ƿ���Ҫ����������ֶ�����set����
	private String roleName; //ͨ��ROLE_ID���������T_ROLE���е�IDֵ��Ȼ����NAME�ֶ�ֵ
	
	
	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
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

	public String getPWD() {
		return PWD;
	}

	public void setPWD(String pWD) {
		PWD = pWD;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}
	
	

}
