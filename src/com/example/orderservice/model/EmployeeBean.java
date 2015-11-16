/************************************************************
 *	版权所有  (c)2011,   hxf<p>	
 *  文件名称	：EmployeeBean.java<p>
 *
 *  创建时间	：2015年10月15日 下午8:21:03 
 *  当前版本号：v1.0
 ************************************************************/
package com.example.orderservice.model;

/************************************************************
 *  内容摘要	：<p>
 *
 *  作者	：hxf
 *  创建时间	：2015年10月15日 下午8:21:03 
 *  当前版本号：v1.0
 *  历史记录	:
 *  	日期	: 2015年10月15日 下午8:21:03 	修改人：
 *  	描述	:
 ************************************************************/
public class EmployeeBean {
	
	private int id;
	
	private String code;//账号
	
	private String PWD;//密码
	
	private String userName;//用户名
	
	private int roleId;//外键
	
	//通过外键获得的数据 ---------------------------》
	//不清楚是否需要给下面这个字段设置set方法
	private String roleName; //通过ROLE_ID这个外键获得T_ROLE表中的ID值，然后查出NAME字段值
	
	
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
