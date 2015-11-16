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

import com.example.orderservice.model.TableInfoBean;
import com.example.orderservice.model.TableSizeBean;

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
public class TableSizeDao {

	/*
	 * ���ݿ���
	 */
	public static final String DB_NAME = "orderdish";
	
	/*
	 * ����
	 */
	public static final String TABLE_NAME = "T_TABLE_SIZE";
	
	/*
	 * ���ݿ�汾
	 */
	public static final int VERSION = 1;
	
	private static TableSizeDao tableSizeDao;
	
	private SQLiteDatabase db;
	
	/*
	 * �����췽��˽�л�,��ʵ�ֵ���ģʽ
	 * ʵ����ʱ������Context����������������Ҫ�õ�
	 */
	private TableSizeDao(Context context){
		MyDatabaseHelper dbHelper = new MyDatabaseHelper(context, DB_NAME, null, VERSION);
		db = dbHelper.getWritableDatabase();
	}
	
	/*
	 * ��ȡReaderDB��ʵ��
	 */
	public synchronized static TableSizeDao getInstance(Context context){
		if (tableSizeDao == null) {
			tableSizeDao = new TableSizeDao(context);
		}
		return tableSizeDao;
	}
	
	
	//CRUD����------------------------------------------------��
	
	/*
	 * ��TableSizeBeanʵ������T_TABLE_SIZE��
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
	 * ��ָ��TableSizeBean��T_TABLE_SIZE����ɾ��
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
	 * �޸�ָ��������Ϣ
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
	 * T_TABLE_SIZE���ȡ����TableSizeBeanʵ��
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
	 * ��ѯT_TABLE_SIZE����ָ��ID��SIZE
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
