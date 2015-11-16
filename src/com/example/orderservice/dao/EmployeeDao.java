/************************************************************
 *	版权所有  (c)2011,   hxf<p>	
 *  文件名称	：TableInfoDao.java<p>
 *
 *  创建时间	：2015年10月16日 下午4:20:33 
 *  当前版本号：v1.0
 ************************************************************/
package com.example.orderservice.dao;



import java.util.ArrayList;
import java.util.List;

import com.example.orderservice.model.EmployeeBean;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.UrlQuerySanitizer.ValueSanitizer;

/************************************************************
 *  内容摘要	：<p>
 *
 *  作者	：hxf
 *  创建时间	：2015年10月16日 下午4:20:33 
 *  当前版本号：v1.0
 *  历史记录	:
 *  	日期	: 2015年10月16日 下午4:20:33 	修改人：
 *  	描述	:
 ************************************************************/
public class EmployeeDao {

	/*
	 * 数据库名
	 */
	public static final String DB_NAME = "orderdish";
	
	/*
	 * 表名
	 */
	public static final String TABLE_NAME = "T_EMPLOYEE";
	
	/*
	 * 数据库版本
	 */
	public static final int VERSION = 1;
	
	
	private static EmployeeDao employeeDao;
	
	private SQLiteDatabase db;
	
	/*
	 * 将构造方法私有化,以实现单列模式
	 * 实例化时，传递Context对象进来，后面代码要用到
	 */
	private EmployeeDao(Context context){
		MyDatabaseHelper dbHelper = new MyDatabaseHelper(context, DB_NAME, null, VERSION);
		db = dbHelper.getWritableDatabase();
	}
	
	/*
	 * 提供getInstance(Context context)给外界获取EmployeeDao的实例
	 */
	public synchronized static EmployeeDao getInstance(Context context){
		if (employeeDao == null) {
			employeeDao = new EmployeeDao(context);
		}
		return employeeDao;
	}
	
	
	//CRUD操作------------------------------------------------》
	
	/*
	 * 将EmployeeBean实例插入表
	 */
	public long saveEmployee(EmployeeBean employeeBean){
		long newRow = 0;
		
		if (employeeBean != null) {
			ContentValues values = new ContentValues();
			values.put("CODE", employeeBean.getCode());
			values.put("PWD", employeeBean.getPWD());
			values.put("USERNAME", employeeBean.getUserName());
			values.put("ROLE_ID", employeeBean.getRoleId());
			newRow = db.insert(TABLE_NAME, null, values);		
		}
				
		return newRow;		
	}
	
	/*
	 * 将指定EmployeeBean从表中删除
	 */
	public boolean deleteEmployee(EmployeeBean employeeBean){
		if (employeeBean != null) {
			int id = employeeBean.getId();
			db.delete(TABLE_NAME, "ID = ?", new String[]{String.valueOf(id)});
			return  true;
		}
		return false;
	}
	
	/*
	 * 修改指定ID值的信息
	 */
	public boolean modifyEmployee(EmployeeBean employeeBean){
		
		if (employeeBean != null) {
			int id = employeeBean.getId();			
			
			ContentValues values = new ContentValues();
			values.put("CODE", employeeBean.getCode());
			values.put("PWD", employeeBean.getPWD());
			values.put("USERNAME", employeeBean.getUserName());
			values.put("ROLE_ID", employeeBean.getRoleId());
			
			db.update(TABLE_NAME, values, "ID = ?", new String[]{String.valueOf(id)});
			return true;
		}
		
		return false;
	}
	
	/*
	 * T_EMPLOYEE表读取所有EmployeeBean实例
	 */
	public List<EmployeeBean> loadEmployeeBean(){
		List<EmployeeBean> list = new ArrayList<EmployeeBean>();
		Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, null);
		
		if (cursor.moveToFirst()) {
			do {
				EmployeeBean employeeBean = new EmployeeBean();
				employeeBean.setId(cursor.getInt(cursor.getColumnIndex("ID")));
				employeeBean.setCode(cursor.getString(cursor.getColumnIndex("CODE")));
				employeeBean.setPWD(cursor.getString(cursor.getColumnIndex("PWD")));
				employeeBean.setUserName(cursor.getString(cursor.getColumnIndex("USERNAME")));
				
				int roleId = cursor.getInt(cursor.getColumnIndex("ROLE_ID"));
				employeeBean.setRoleId(roleId);
				//查询ROLE_ID这个外键所对应的数据
				Cursor cursorRoleId = db.query("T_ROLE", new String[]{"NAME"}, "ID = ?", 
						new String[]{String.valueOf(roleId)}, null, null, null);
				if (cursorRoleId.moveToNext()) {
					employeeBean.setRoleName(cursorRoleId.getString(cursorRoleId.getColumnIndex("NAME")));
				}
				cursorRoleId.close();
				
			} while (cursor.moveToNext());
		}
		return list;
	}
	
	
}
