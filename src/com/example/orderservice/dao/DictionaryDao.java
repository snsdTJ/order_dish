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

import com.example.orderservice.model.DictionaryBean;

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
public class DictionaryDao {

	/*
	 * 数据库名
	 */
	public static final String DB_NAME = "orderdish";
	
	/*
	 * 表名
	 */
	public static final String TABLE_NAME = "T_DATA_DICTIONARY";
	
	/*
	 * 数据库版本
	 */
	public static final int VERSION = 1;
	
	
	private static DictionaryDao dictionaryDao;
	
	private SQLiteDatabase db;
	
	/*
	 * 将构造方法私有化,以实现单列模式
	 * 实例化时，传递Context对象进来，后面代码要用到
	 */
	private DictionaryDao(Context context){
		MyDatabaseHelper dbHelper = new MyDatabaseHelper(context, DB_NAME, null, VERSION);
		db = dbHelper.getWritableDatabase();
	}
	
	/*
	 * 提供getInstance(Context context)给外界获取DictionaryDao的实例
	 */
	public synchronized static DictionaryDao getInstance(Context context){
		if (dictionaryDao == null) {
			dictionaryDao = new DictionaryDao(context);
		}
		return dictionaryDao;
	}
	
	
	//CRUD操作------------------------------------------------》
	
	/*
	 * 将DictionaryBean实例插入表
	 */
	public long saveDictionary(DictionaryBean dictionaryBean){
		long newRow = 0;
		
		if (dictionaryBean != null) {
			ContentValues values = new ContentValues();
			values.put("CODE", dictionaryBean.getCode());
			values.put("NAME", dictionaryBean.getName());
			values.put("TYPE", dictionaryBean.getType());
			newRow = db.insert(TABLE_NAME, null, values);		
		}
				
		return newRow;		
	}
	
	/*
	 * 将指定DictionaryBean从表中删除
	 */
	public boolean deleteDictionary(DictionaryBean dictionaryBean){
		if (dictionaryBean != null) {
			int id = dictionaryBean.getId();
			db.delete(TABLE_NAME, "ID = ?", new String[]{String.valueOf(id)});
			return  true;
		}
		return false;
	}
	
	/*
	 * 修改指定ID值的信息
	 */
	public boolean modifyDictionary(DictionaryBean dictionaryBean){
		
		if (dictionaryBean != null) {
			int id = dictionaryBean.getId();			
			
			ContentValues values = new ContentValues();
			values.put("CODE", dictionaryBean.getCode());
			values.put("NAME", dictionaryBean.getName());
			values.put("TYPE", dictionaryBean.getType());
			
			db.update(TABLE_NAME, values, "ID = ?", new String[]{String.valueOf(id)});
			return true;
		}
		
		return false;
	}
	
	/*
	 * T_DATA_DICTIONARY表读取所有DictionaryBean实例
	 */
	public List<DictionaryBean> loadDictionaryBean(){
		List<DictionaryBean> list = new ArrayList<DictionaryBean>();
		Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, null);
		
		if (cursor.moveToFirst()) {
			do {
				DictionaryBean dictionaryBean = new DictionaryBean();
				dictionaryBean.setId(cursor.getInt(cursor.getColumnIndex("ID")));
				dictionaryBean.setCode(cursor.getString(cursor.getColumnIndex("CODE")));
				dictionaryBean.setName(cursor.getString(cursor.getColumnIndex("NAME")));
				
				int type = cursor.getInt(cursor.getColumnIndex("TYPE"));
				dictionaryBean.setType(type);
				//查询TYPE这个外键所对应的数据
				Cursor cursorType = db.query("T_DATA_DICTIONARY_TYPE", new String[]{"NAME"}, "ID = ?", 
						new String[]{String.valueOf(type)}, null, null, null);
				if (cursorType.moveToNext()) {
					dictionaryBean.setTypeName(cursorType.getString(cursorType.getColumnIndex("NAME")));
				}				
				cursorType.close();
				
				list.add(dictionaryBean);
				
			} while (cursor.moveToNext());
		}
		return list;
	}
	
	/*
	 * 通过TYPE字段读取T_DATA_DICTIONARY表指定DictionaryBean实例
	 */
	public List<DictionaryBean> loadDictionaryBean(int type){
		List<DictionaryBean> list = new ArrayList<DictionaryBean>();
		Cursor cursor = db.query(TABLE_NAME, null, "TYPE = ?", new String[]{String.valueOf(type)}, null, null, null);
		
		if (cursor.moveToFirst()) {
			do {
				DictionaryBean dictionaryBean = new DictionaryBean();
				dictionaryBean.setId(cursor.getInt(cursor.getColumnIndex("ID")));
				dictionaryBean.setCode(cursor.getString(cursor.getColumnIndex("CODE")));
				dictionaryBean.setName(cursor.getString(cursor.getColumnIndex("NAME")));
				dictionaryBean.setType(type);
				//查询TYPE这个外键所对应的数据
				Cursor cursorType = db.query("T_DATA_DICTIONARY_TYPE", new String[]{"NAME"}, "ID = ?", 
						new String[]{String.valueOf(type)}, null, null, null);
				if (cursorType.moveToNext()) {
					dictionaryBean.setTypeName(cursorType.getString(cursorType.getColumnIndex("NAME")));
				}				
				cursorType.close();
				
				list.add(dictionaryBean);
				
			} while (cursor.moveToNext());
		}
		return list;
	}
	
	
	
	
	
	
}
