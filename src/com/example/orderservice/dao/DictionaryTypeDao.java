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

import com.example.orderservice.model.DictionaryTypeBean;

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
public class DictionaryTypeDao {

	/*
	 * 数据库名
	 */
	public static final String DB_NAME = "orderdish";
	
	/*
	 * 表名
	 */
	public static final String TABLE_NAME = "T_DATA_DICTIONARY_TYPE";
	
	/*
	 * 数据库版本
	 */
	public static final int VERSION = 1;
	
	
	private static DictionaryTypeDao dictionaryTypeDao;
	
	private SQLiteDatabase db;
	
	/*
	 * 将构造方法私有化,以实现单列模式
	 * 实例化时，传递Context对象进来，后面代码要用到
	 */
	private DictionaryTypeDao(Context context){
		MyDatabaseHelper dbHelper = new MyDatabaseHelper(context, DB_NAME, null, VERSION);
		db = dbHelper.getWritableDatabase();
	}
	
	/*
	 * 提供getInstance（）用于获取DictionaryTypeDao的实例
	 */
	public synchronized static DictionaryTypeDao getInstance(Context context){
		if (dictionaryTypeDao == null) {
			dictionaryTypeDao = new DictionaryTypeDao(context);
		}
		return dictionaryTypeDao;
	}
	
	
	//CRUD操作------------------------------------------------》
	
	/*
	 * 将DictionaryTypeBean实例插入T_DATA_DICTIONARY_TYPE表
	 */
	public long saveDictionaryType(DictionaryTypeBean dictionaryTypeBean){
		long newRow = 0;
		
		if (dictionaryTypeBean != null) {
			ContentValues values = new ContentValues();
			values.put("NAME", dictionaryTypeBean.getName());
			newRow = db.insert(TABLE_NAME, null, values);		
		}
				
		return newRow;		
	}
	
	/*
	 * 将指定dictionaryTypeBean从T_DATA_DICTIONARY_TYPE表中删除
	 */
	public boolean deleteDictionaryType(DictionaryTypeBean dictionaryTypeBean){
		if (dictionaryTypeBean != null) {
			int id = dictionaryTypeBean.getId();
			db.delete(TABLE_NAME, "ID = ?", new String[]{String.valueOf(id)});
			return  true;
		}
		return false;
	}
	
	/*
	 * 修改指定ID值的信息
	 */
	public boolean modifyDictionaryType(DictionaryTypeBean dictionaryTypeBean){
		
		if (dictionaryTypeBean != null) {
			int id = dictionaryTypeBean.getId();			
			String name = dictionaryTypeBean.getName();
			
			ContentValues values = new ContentValues();
			values.put("NAME", name);
			
			db.update(TABLE_NAME, values, "ID = ?", new String[]{String.valueOf(id)});
			return true;
		}
		
		return false;
	}
	
	/*
	 * T_DATA_DICTIONARY_TYPE表读取所有DictionaryTypeBean实例
	 */
	public List<DictionaryTypeBean> loadDictionaryType(){
		List<DictionaryTypeBean> list = new ArrayList<DictionaryTypeBean>();
		Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, null);
		
		if (cursor.moveToFirst()) {
			do {
				DictionaryTypeBean dictionaryTypeBean = new DictionaryTypeBean();
				dictionaryTypeBean.setId(cursor.getInt(cursor.getColumnIndex("ID")));
				dictionaryTypeBean.setName(cursor.getString(cursor.getColumnIndex("NAME")));
				list.add(dictionaryTypeBean);
				
			} while (cursor.moveToNext());
		}
		return list;
	}
	
	/*
	 * 查询T_DATA_DICTIONARY_TYPE表中指定ID的NAME值
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
