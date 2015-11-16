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

import com.example.orderservice.model.DictionaryBean;

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
public class DictionaryDao {

	/*
	 * ���ݿ���
	 */
	public static final String DB_NAME = "orderdish";
	
	/*
	 * ����
	 */
	public static final String TABLE_NAME = "T_DATA_DICTIONARY";
	
	/*
	 * ���ݿ�汾
	 */
	public static final int VERSION = 1;
	
	
	private static DictionaryDao dictionaryDao;
	
	private SQLiteDatabase db;
	
	/*
	 * �����췽��˽�л�,��ʵ�ֵ���ģʽ
	 * ʵ����ʱ������Context����������������Ҫ�õ�
	 */
	private DictionaryDao(Context context){
		MyDatabaseHelper dbHelper = new MyDatabaseHelper(context, DB_NAME, null, VERSION);
		db = dbHelper.getWritableDatabase();
	}
	
	/*
	 * �ṩgetInstance(Context context)������ȡDictionaryDao��ʵ��
	 */
	public synchronized static DictionaryDao getInstance(Context context){
		if (dictionaryDao == null) {
			dictionaryDao = new DictionaryDao(context);
		}
		return dictionaryDao;
	}
	
	
	//CRUD����------------------------------------------------��
	
	/*
	 * ��DictionaryBeanʵ�������
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
	 * ��ָ��DictionaryBean�ӱ���ɾ��
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
	 * �޸�ָ��IDֵ����Ϣ
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
	 * T_DATA_DICTIONARY���ȡ����DictionaryBeanʵ��
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
				//��ѯTYPE����������Ӧ������
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
	 * ͨ��TYPE�ֶζ�ȡT_DATA_DICTIONARY��ָ��DictionaryBeanʵ��
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
				//��ѯTYPE����������Ӧ������
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
