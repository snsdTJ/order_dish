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

import com.example.orderservice.model.DictionaryTypeBean;

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
public class DictionaryTypeDao {

	/*
	 * ���ݿ���
	 */
	public static final String DB_NAME = "orderdish";
	
	/*
	 * ����
	 */
	public static final String TABLE_NAME = "T_DATA_DICTIONARY_TYPE";
	
	/*
	 * ���ݿ�汾
	 */
	public static final int VERSION = 1;
	
	
	private static DictionaryTypeDao dictionaryTypeDao;
	
	private SQLiteDatabase db;
	
	/*
	 * �����췽��˽�л�,��ʵ�ֵ���ģʽ
	 * ʵ����ʱ������Context����������������Ҫ�õ�
	 */
	private DictionaryTypeDao(Context context){
		MyDatabaseHelper dbHelper = new MyDatabaseHelper(context, DB_NAME, null, VERSION);
		db = dbHelper.getWritableDatabase();
	}
	
	/*
	 * �ṩgetInstance�������ڻ�ȡDictionaryTypeDao��ʵ��
	 */
	public synchronized static DictionaryTypeDao getInstance(Context context){
		if (dictionaryTypeDao == null) {
			dictionaryTypeDao = new DictionaryTypeDao(context);
		}
		return dictionaryTypeDao;
	}
	
	
	//CRUD����------------------------------------------------��
	
	/*
	 * ��DictionaryTypeBeanʵ������T_DATA_DICTIONARY_TYPE��
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
	 * ��ָ��dictionaryTypeBean��T_DATA_DICTIONARY_TYPE����ɾ��
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
	 * �޸�ָ��IDֵ����Ϣ
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
	 * T_DATA_DICTIONARY_TYPE���ȡ����DictionaryTypeBeanʵ��
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
	 * ��ѯT_DATA_DICTIONARY_TYPE����ָ��ID��NAMEֵ
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
