/************************************************************
 *	��Ȩ����  (c)2011,   hxf<p>	
 *  �ļ�����	��TableInfoDao.java<p>
 *
 *  ����ʱ��	��2015��10��16�� ����4:20:33 
 *  ��ǰ�汾�ţ�v1.0
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
 *  ����ժҪ	��<p>
 *
 *  ����	��hxf
 *  ����ʱ��	��2015��10��16�� ����4:20:33 
 *  ��ǰ�汾�ţ�v1.0
 *  ��ʷ��¼	:
 *  	����	: 2015��10��16�� ����4:20:33 	�޸��ˣ�
 *  	����	:
 ************************************************************/
public class EmployeeDao {

	/*
	 * ���ݿ���
	 */
	public static final String DB_NAME = "orderdish";
	
	/*
	 * ����
	 */
	public static final String TABLE_NAME = "T_EMPLOYEE";
	
	/*
	 * ���ݿ�汾
	 */
	public static final int VERSION = 1;
	
	
	private static EmployeeDao employeeDao;
	
	private SQLiteDatabase db;
	
	/*
	 * �����췽��˽�л�,��ʵ�ֵ���ģʽ
	 * ʵ����ʱ������Context����������������Ҫ�õ�
	 */
	private EmployeeDao(Context context){
		MyDatabaseHelper dbHelper = new MyDatabaseHelper(context, DB_NAME, null, VERSION);
		db = dbHelper.getWritableDatabase();
	}
	
	/*
	 * �ṩgetInstance(Context context)������ȡEmployeeDao��ʵ��
	 */
	public synchronized static EmployeeDao getInstance(Context context){
		if (employeeDao == null) {
			employeeDao = new EmployeeDao(context);
		}
		return employeeDao;
	}
	
	
	//CRUD����------------------------------------------------��
	
	/*
	 * ��EmployeeBeanʵ�������
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
	 * ��ָ��EmployeeBean�ӱ���ɾ��
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
	 * �޸�ָ��IDֵ����Ϣ
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
	 * T_EMPLOYEE���ȡ����EmployeeBeanʵ��
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
				//��ѯROLE_ID����������Ӧ������
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
