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

import com.example.orderservice.model.RoleBean;

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
public class RoleDao {

	/*
	 * ���ݿ���
	 */
	public static final String DB_NAME = "orderdish";
	
	/*
	 * ����
	 */
	public static final String TABLE_NAME = "T_ROLE";
	
	/*
	 * ���ݿ�汾
	 */
	public static final int VERSION = 1;
	
	
	private static RoleDao roleDao;
	
	private SQLiteDatabase db;
	
	/*
	 * �����췽��˽�л�,��ʵ�ֵ���ģʽ
	 * ʵ����ʱ������Context����������������Ҫ�õ�
	 */
	private RoleDao(Context context){
		MyDatabaseHelper dbHelper = new MyDatabaseHelper(context, DB_NAME, null, VERSION);
		db = dbHelper.getWritableDatabase();
	}
	
	/*
	 * �ṩgetInstance�������ڻ�ȡRoleDao��ʵ��
	 */
	public synchronized static RoleDao getInstance(Context context){
		if (roleDao == null) {
			roleDao = new RoleDao(context);
		}
		return roleDao;
	}
	
	
	//CRUD����------------------------------------------------��
	
	/*
	 * ��RoleBeanʵ������T_ROLE��
	 */
	public long saveRole(RoleBean roleBean){
		long newRow = 0;
		
		if (roleBean != null) {
			ContentValues values = new ContentValues();
			values.put("NAME", roleBean.getName());
			newRow = db.insert(TABLE_NAME, null, values);		
		}
				
		return newRow;		
	}
	
	/*
	 * ��ָ��roleBean��T_ROLE����ɾ��
	 */
	public boolean deleteRole(RoleBean roleBean){
		if (roleBean != null) {
			int id = roleBean.getId();
			db.delete(TABLE_NAME, "ID = ?", new String[]{String.valueOf(id)});
			return  true;
		}
		return false;
	}
	
	/*
	 * �޸�ָ��Role����Ϣ
	 */
	public boolean modifyRole(RoleBean roleBean){
		
		if (roleBean != null) {
			int id = roleBean.getId();			
			String name = roleBean.getName();
			
			ContentValues values = new ContentValues();
			values.put("NAME", name);
			
			db.update(TABLE_NAME, values, "ID = ?", new String[]{String.valueOf(id)});
			return true;
		}
		
		return false;
	}
	
	/*
	 * T_ROLE���ȡ����RoleBeanʵ��
	 */
	public List<RoleBean> loadRoleBean(){
		List<RoleBean> list = new ArrayList<RoleBean>();
		Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, null);
		
		if (cursor.moveToFirst()) {
			do {
				RoleBean roleBean = new RoleBean();
				roleBean.setId(cursor.getInt(cursor.getColumnIndex("ID")));
				roleBean.setName(cursor.getString(cursor.getColumnIndex("NAME")));
				list.add(roleBean);
				
			} while (cursor.moveToNext());
		}
		return list;
	}
	
	/*
	 * ��ѯT_ROLE����ָ��ID��NAMEֵ
	 */
	public String queryName(int id){
		String name = null;
		
		Cursor cursor = db.query(TABLE_NAME, null, "ID = ?", new String[]{String.valueOf(id)}, null, null, null);
		if (cursor.moveToNext()) {
			name = cursor.getString(cursor.getColumnIndex("NAME"));
		}
		
		return name;	
	}
	
}
