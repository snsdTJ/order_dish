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

import com.example.orderservice.model.RoleBean;

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
public class RoleDao {

	/*
	 * 数据库名
	 */
	public static final String DB_NAME = "orderdish";
	
	/*
	 * 表名
	 */
	public static final String TABLE_NAME = "T_ROLE";
	
	/*
	 * 数据库版本
	 */
	public static final int VERSION = 1;
	
	
	private static RoleDao roleDao;
	
	private SQLiteDatabase db;
	
	/*
	 * 将构造方法私有化,以实现单列模式
	 * 实例化时，传递Context对象进来，后面代码要用到
	 */
	private RoleDao(Context context){
		MyDatabaseHelper dbHelper = new MyDatabaseHelper(context, DB_NAME, null, VERSION);
		db = dbHelper.getWritableDatabase();
	}
	
	/*
	 * 提供getInstance（）用于获取RoleDao的实例
	 */
	public synchronized static RoleDao getInstance(Context context){
		if (roleDao == null) {
			roleDao = new RoleDao(context);
		}
		return roleDao;
	}
	
	
	//CRUD操作------------------------------------------------》
	
	/*
	 * 将RoleBean实例插入T_ROLE表
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
	 * 将指定roleBean从T_ROLE表中删除
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
	 * 修改指定Role的信息
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
	 * T_ROLE表读取所有RoleBean实例
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
	 * 查询T_ROLE表中指定ID的NAME值
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
