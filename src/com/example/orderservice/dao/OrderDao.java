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

import com.example.orderservice.model.OrderBean;
import com.example.orderservice.model.TableInfoBean;

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
public class OrderDao {

	/*
	 * ���ݿ���
	 */
	public static final String DB_NAME = "orderdish";
	
	/*
	 * ����
	 */
	public static final String TABLE_NAME = "T_ORDER";
	
	/*
	 * ���ݿ�汾
	 */
	public static final int VERSION = 1;
	
	
	private static OrderDao orderDao;
	
	private SQLiteDatabase db;
	
	/*
	 * �����췽��˽�л�,��ʵ�ֵ���ģʽ
	 * ʵ����ʱ������Context����������������Ҫ�õ�
	 */
	private OrderDao(Context context){
		MyDatabaseHelper dbHelper = new MyDatabaseHelper(context, DB_NAME, null, VERSION);
		db = dbHelper.getWritableDatabase();
	}
	
	/*
	 * ��ȡOrderDao��ʵ��
	 */
	public synchronized static OrderDao getInstance(Context context){
		if (orderDao == null) {
			orderDao = new OrderDao(context);
		}
		return orderDao;
	}
	
	
	//CRUD����------------------------------------------------��
	
	/*
	 * ��OrderBeanʵ������T_ORDER��
	 */
	public long saveOrder(OrderBean orderBean){
		long newRow = 0;
		
		if (orderBean != null) {
			ContentValues values = new ContentValues();
			values.put("TABLE_INFO_ID", orderBean.getTableInfoId());
			values.put("WAITER_ID", orderBean.getWaiterId());
			values.put("CASHIER_ID", orderBean.getCashierId());
			values.put("BEGIN_DATETIME", orderBean.getBeginDateTime());
			values.put("END_DATETIME", orderBean.getEndDateTime());
			values.put("TOTAL_MONEY", orderBean.getTotalMoney());
			values.put("ISPAY", orderBean.getIsPay());
			
			newRow = db.insert(TABLE_NAME, null, values);		
		}
				
		return newRow;		
	}
	
	/*
	 * ��ָ��OrderBean��T_ORDER����ɾ��
	 */
	public boolean deleteOrder(OrderBean orderBean){
		if (orderBean != null) {
			int id = orderBean.getId();
			db.delete(TABLE_NAME, "ID = ?", new String[]{String.valueOf(id)});
			return  true;
		}
		return false;
	}
	
	/*
	 * �޸�ָOrder����Ϣ
	 */
	public boolean modifyOrder(OrderBean orderBean){
		
		if (orderBean != null) {
			
			int id = orderBean.getId();	
			
			ContentValues values = new ContentValues();
			values.put("TABLE_INFO_ID", orderBean.getTableInfoId());
			values.put("WAITER_ID", orderBean.getWaiterId());
			values.put("CASHIER_ID", orderBean.getCashierId());
			values.put("BEGIN_DATETIME", orderBean.getBeginDateTime());
			values.put("END_DATETIME", orderBean.getEndDateTime());
			values.put("TOTAL_MONEY", orderBean.getTotalMoney());
			values.put("ISPAY", orderBean.getIsPay());
						
			db.update(TABLE_NAME, values, "ID = ?", new String[]{String.valueOf(id)});
			return true;
		}
		
		return false;
	}
	
	/*
	 * T_ORDER���ȡ����OrderBeanʵ��
	 */
	public List<OrderBean> loadOrderBean(){
		List<OrderBean> list = new ArrayList<OrderBean>();
		Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, null);
		
		if (cursor.moveToFirst()) {
			do {
				OrderBean orderBean = new OrderBean();
				
				orderBean.setId(cursor.getInt(cursor.getColumnIndex("ID")));
				orderBean.setTableInfoId(cursor.getInt(cursor.getColumnIndex("TABLE_INFO_ID")));
				orderBean.setWaiterId(cursor.getInt(cursor.getColumnIndex("WAITER_ID")));
				orderBean.setCashierId(cursor.getInt(cursor.getColumnIndex("CASHIER_ID")));
				orderBean.setBeginDateTime(cursor.getString(cursor.getColumnIndex("BEGIN_DATETIME")));
				orderBean.setEndDateTime(cursor.getString(cursor.getColumnIndex("END_DATETIME")));
				orderBean.setTotalMoney(cursor.getFloat(cursor.getColumnIndex("TOTAL_MONEY")));
				orderBean.setIsPay(cursor.getInt(cursor.getColumnIndex("ISPAY")));
				
				//��ѯTABLE_INFO_ID����������Ӧ������
				int tableInfoId = cursor.getInt(cursor.getColumnIndex("TABLE_INFO_ID"));
				Cursor cursorTableInfoId = db.query("T_TABLE_INFO", new String[]{"NO"}, "ID = ?", 
						new String[]{String.valueOf(tableInfoId)}, null, null, null);
				if (cursorTableInfoId.moveToNext()) {
					orderBean.setTableNo(cursorTableInfoId.getInt(cursorTableInfoId.getColumnIndex("NO")));
				}
				cursorTableInfoId.close();
				
				//��ѯWAITER_ID����������Ӧ������
				int waiterId = cursor.getInt(cursor.getColumnIndex("WAITER_ID"));
				Cursor cursorWaiterId = db.query("T_EMPLOYEE", new String[]{"CODE"}, "ID = ?", 
						new String[]{String.valueOf(waiterId)}, null, null, null);
				if (cursorWaiterId.moveToNext()) {
					orderBean.setWaiterCode(cursorWaiterId.getString(cursorWaiterId.getColumnIndex("CODE")));
				}
				cursorWaiterId.close();
				
				//��ѯCASHIER_ID����������Ӧ������
				int cashierId = cursor.getInt(cursor.getColumnIndex("CASHIER_ID"));
				Cursor cursorCashierId = db.query("T_EMPLOYEE", new String[]{"CODE"}, "ID = ?", 
						new String[]{String.valueOf(cashierId)}, null, null, null);
				if (cursorCashierId.moveToNext()) {
					orderBean.setCashierCode(cursorCashierId.getString(cursorCashierId.getColumnIndex("CODE")));
				}
				cursorCashierId.close();
				
				//��ѯISPAY����������Ӧ������
				int isPay = cursor.getInt(cursor.getColumnIndex("ISPAY"));
				Cursor cursorIsPay = db.query("T_DATA_DICTIONARY", new String[]{"NAME"}, "ID = ?", 
						new String[]{String.valueOf(isPay)}, null, null, null);
				if (cursorIsPay.moveToNext()) {
					orderBean.setIsPayName(cursorIsPay.getString(cursorIsPay.getColumnIndex("NAME")));
				}
				cursorIsPay.close();
			
				list.add(orderBean);
				
			} while (cursor.moveToNext());
		}
		return list;
	}
	
	/*
	 * ͨ��T_ORDER���IDֵ����ȡָ��OrderBeanʵ��
	 */
	public OrderBean getOrderBean(int id) {

		Cursor cursor = db.query(TABLE_NAME, null, "ID = ?", new String[] { String.valueOf(id) }, null, null, null);
		OrderBean orderBean = new OrderBean();
		if (cursor.moveToNext()) {

			orderBean.setId(cursor.getInt(cursor.getColumnIndex("ID")));
			orderBean.setTableInfoId(cursor.getInt(cursor.getColumnIndex("TABLE_INFO_ID")));
			orderBean.setWaiterId(cursor.getInt(cursor.getColumnIndex("WAITER_ID")));
			orderBean.setCashierId(cursor.getInt(cursor.getColumnIndex("CASHIER_ID")));
			orderBean.setBeginDateTime(cursor.getString(cursor.getColumnIndex("BEGIN_DATETIME")));
			orderBean.setEndDateTime(cursor.getString(cursor.getColumnIndex("END_DATETIME")));
			orderBean.setTotalMoney(cursor.getFloat(cursor.getColumnIndex("TOTAL_MONEY")));
			orderBean.setIsPay(cursor.getInt(cursor.getColumnIndex("ISPAY")));

			//��ѯTABLE_INFO_ID����������Ӧ������
			int tableInfoId = cursor.getInt(cursor.getColumnIndex("TABLE_INFO_ID"));
			Cursor cursorTableInfoId = db.query("T_TABLE_INFO", new String[]{"NO"}, "ID = ?", 
					new String[]{String.valueOf(tableInfoId)}, null, null, null);
			if (cursorTableInfoId.moveToNext()) {
				orderBean.setTableNo(cursorTableInfoId.getInt(cursorTableInfoId.getColumnIndex("NO")));
			}
			cursorTableInfoId.close();
			
			//��ѯWAITER_ID����������Ӧ������
			int waiterId = cursor.getInt(cursor.getColumnIndex("WAITER_ID"));
			Cursor cursorWaiterId = db.query("T_EMPLOYEE", new String[]{"CODE"}, "ID = ?", 
					new String[]{String.valueOf(waiterId)}, null, null, null);
			if (cursorWaiterId.moveToNext()) {
				orderBean.setWaiterCode(cursorWaiterId.getString(cursorWaiterId.getColumnIndex("CODE")));
			}
			cursorWaiterId.close();
			
			//��ѯCASHIER_ID����������Ӧ������
			int cashierId = cursor.getInt(cursor.getColumnIndex("CASHIER_ID"));
			Cursor cursorCashierId = db.query("T_EMPLOYEE", new String[]{"CODE"}, "ID = ?", 
					new String[]{String.valueOf(cashierId)}, null, null, null);
			if (cursorCashierId.moveToNext()) {
				orderBean.setCashierCode(cursorCashierId.getString(cursorCashierId.getColumnIndex("CODE")));
			}
			cursorCashierId.close();
			
			//��ѯISPAY����������Ӧ������
			int isPay = cursor.getInt(cursor.getColumnIndex("ISPAY"));
			Cursor cursorIsPay = db.query("T_DATA_DICTIONARY", new String[]{"NAME"}, "ID = ?", 
					new String[]{String.valueOf(isPay)}, null, null, null);
			if (cursorIsPay.moveToNext()) {
				orderBean.setIsPayName(cursorIsPay.getString(cursorIsPay.getColumnIndex("NAME")));
			}
			cursorIsPay.close();
		} else {
			return null;
		}
		return orderBean;
	}
	
	/*
	 * ͨ��T_ORDER���TABLE_INFO_ID�ֶκ�ISPAY�ֶΣ����ָ����������
	 */
	public OrderBean getOrderBean(int tableInfoId, int isPay) {

		Cursor cursor = db.query(TABLE_NAME, null, "TABLE_INFO_ID = ? and ISPAY = ?", 
				new String[] { String.valueOf(tableInfoId), String.valueOf(isPay) }, null, null, null);
		OrderBean orderBean = new OrderBean();
		if (cursor.moveToNext()) {

			orderBean.setId(cursor.getInt(cursor.getColumnIndex("ID")));
			orderBean.setTableInfoId(cursor.getInt(cursor.getColumnIndex("TABLE_INFO_ID")));
			orderBean.setWaiterId(cursor.getInt(cursor.getColumnIndex("WAITER_ID")));
			orderBean.setCashierId(cursor.getInt(cursor.getColumnIndex("CASHIER_ID")));
			orderBean.setBeginDateTime(cursor.getString(cursor.getColumnIndex("BEGIN_DATETIME")));
			orderBean.setEndDateTime(cursor.getString(cursor.getColumnIndex("END_DATETIME")));
			orderBean.setTotalMoney(cursor.getFloat(cursor.getColumnIndex("TOTAL_MONEY")));
			orderBean.setIsPay(cursor.getInt(cursor.getColumnIndex("ISPAY")));

			//��ѯTABLE_INFO_ID����������Ӧ������
			int tInfoId = cursor.getInt(cursor.getColumnIndex("TABLE_INFO_ID"));
			Cursor cursorTableInfoId = db.query("T_TABLE_INFO", new String[]{"NO"}, "ID = ?", 
					new String[]{String.valueOf(tInfoId)}, null, null, null);
			if (cursorTableInfoId.moveToNext()) {
				orderBean.setTableNo(cursorTableInfoId.getInt(cursorTableInfoId.getColumnIndex("NO")));
			}
			cursorTableInfoId.close();
			
			//��ѯWAITER_ID����������Ӧ������
			int waiterId = cursor.getInt(cursor.getColumnIndex("WAITER_ID"));
			Cursor cursorWaiterId = db.query("T_EMPLOYEE", new String[]{"CODE"}, "ID = ?", 
					new String[]{String.valueOf(waiterId)}, null, null, null);
			if (cursorWaiterId.moveToNext()) {
				orderBean.setWaiterCode(cursorWaiterId.getString(cursorWaiterId.getColumnIndex("CODE")));
			}
			cursorWaiterId.close();
			
			//��ѯCASHIER_ID����������Ӧ������
			int cashierId = cursor.getInt(cursor.getColumnIndex("CASHIER_ID"));
			Cursor cursorCashierId = db.query("T_EMPLOYEE", new String[]{"CODE"}, "ID = ?", 
					new String[]{String.valueOf(cashierId)}, null, null, null);
			if (cursorCashierId.moveToNext()) {
				orderBean.setCashierCode(cursorCashierId.getString(cursorCashierId.getColumnIndex("CODE")));
			}
			cursorCashierId.close();
			
			//��ѯISPAY����������Ӧ������
			int iSPay = cursor.getInt(cursor.getColumnIndex("ISPAY"));
			Cursor cursorIsPay = db.query("T_DATA_DICTIONARY", new String[]{"NAME"}, "ID = ?", 
					new String[]{String.valueOf(iSPay)}, null, null, null);
			if (cursorIsPay.moveToNext()) {
				orderBean.setIsPayName(cursorIsPay.getString(cursorIsPay.getColumnIndex("NAME")));
			}
			cursorIsPay.close();
		} else {
			return null;
		}
		return orderBean;
	}

}
