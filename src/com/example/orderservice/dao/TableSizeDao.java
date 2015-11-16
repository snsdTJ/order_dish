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

import com.example.orderservice.model.TableInfoBean;
import com.example.orderservice.model.TableSizeBean;

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
public class TableSizeDao {

	/*
	 * 数据库名
	 */
	public static final String DB_NAME = "orderdish";
	
	/*
	 * 表名
	 */
	public static final String TABLE_NAME = "T_TABLE_SIZE";
	
	/*
	 * 数据库版本
	 */
	public static final int VERSION = 1;
	
	private static TableSizeDao tableSizeDao;
	
	private SQLiteDatabase db;
	
	/*
	 * 将构造方法私有化,以实现单列模式
	 * 实例化时，传递Context对象进来，后面代码要用到
	 */
	private TableSizeDao(Context context){
		MyDatabaseHelper dbHelper = new MyDatabaseHelper(context, DB_NAME, null, VERSION);
		db = dbHelper.getWritableDatabase();
	}
	
	/*
	 * 获取ReaderDB的实例
	 */
	public synchronized static TableSizeDao getInstance(Context context){
		if (tableSizeDao == null) {
			tableSizeDao = new TableSizeDao(context);
		}
		return tableSizeDao;
	}
	
	
	//CRUD操作------------------------------------------------》
	
	/*
	 * 将TableSizeBean实例插入T_TABLE_SIZE表
	 */
	public long saveTableSize(TableSizeBean tableSizeBean){
		long newRow = 0;
		
		if (tableSizeBean != null) {
			ContentValues values = new ContentValues();
			values.put("SIZE", tableSizeBean.getSize());
			newRow = db.insert(TABLE_NAME, null, values);		
		}
				
		return newRow;		
	}
	
	/*
	 * 将指定TableSizeBean从T_TABLE_SIZE表中删除
	 */
	public boolean deleteTableSize(TableSizeBean tableSizeBean){
		if (tableSizeBean != null) {
			int id = tableSizeBean.getId();
			db.delete(TABLE_NAME, "ID = ?", new String[]{String.valueOf(id)});
			return  true;
		}
		return false;
	}
	
	/*
	 * 修改指定桌的信息
	 */
	public boolean modifyTableSize(TableSizeBean tableSizeBean){
		
		if (tableSizeBean != null) {
			int id = tableSizeBean.getId();			
			int size = tableSizeBean.getSize();
			
			ContentValues values = new ContentValues();
			values.put("SIZE", size);
			
			db.update(TABLE_NAME, values, "ID = ?", new String[]{String.valueOf(id)});
			return true;
		}
		
		return false;
	}
	
	/*
	 * T_TABLE_SIZE表读取所有TableSizeBean实例
	 */
	public List<TableSizeBean> loadTableSizeBean(){
		List<TableSizeBean> list = new ArrayList<TableSizeBean>();
		Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, null);
		
		if (cursor.moveToFirst()) {
			do {
				TableSizeBean tableSizeBean = new TableSizeBean();
				tableSizeBean.setId(cursor.getInt(cursor.getColumnIndex("ID")));
				tableSizeBean.setSize(cursor.getInt(cursor.getColumnIndex("SIZE")));
				list.add(tableSizeBean);
				
			} while (cursor.moveToNext());
		}
		return list;
	}
	
	/*
	 * 查询T_TABLE_SIZE表中指定ID的SIZE
	 */
	public int querySize(int id){
		int size = 0;
		
		Cursor cursor = db.query(TABLE_NAME, null, "ID = ?", new String[]{String.valueOf(id)}, null, null, null);
		if (cursor.moveToNext()) {
			size = cursor.getInt(cursor.getColumnIndex("SIZE"));
		}
		
		return size;	
	}
	
}
