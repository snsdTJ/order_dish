/************************************************************
 *	��Ȩ����  (c)2011,   hxf<p>	
 *  �ļ�����	��ManagerCountActivityDao.java<p>
 *
 *  ����ʱ��	��2015��10��29�� ����10:34:57 
 *  ��ǰ�汾�ţ�v1.0
 ************************************************************/
package com.example.orderservice.dao;

import java.util.ArrayList;
import java.util.List;

import com.example.orderservice.model.OrderBean;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/************************************************************
 *  ����ժҪ	��<p>
 *
 *  ����	��hxf
 *  ����ʱ��	��2015��10��29�� ����10:34:57 
 *  ��ǰ�汾�ţ�v1.0
 *  ��ʷ��¼	:
 *  	����	: 2015��10��29�� ����10:34:57 	�޸��ˣ�
 *  	����	:
 ************************************************************/
public class ManagerCountActivityDao {
	
	/*
	 * ���ݿ���
	 */
	private final String DB_NAME = "orderdish";
	
	/*
	 * ����
	 */
	private final String TABLE_NAME = "T_ORDER";
	
	/*
	 * ���ݿ�汾
	 */
	private final int VERSION = 1;
	
	
	
	
	private static ManagerCountActivityDao managerCountActivityDAO;
	
	private SQLiteDatabase db;
	
	/*
	 * �����췽��˽�л�,��ʵ�ֵ���ģʽ
	 * ʵ����ʱ������Context����������������Ҫ�õ�
	 */
	private ManagerCountActivityDao(Context context){
		MyDatabaseHelper dbHelper = new MyDatabaseHelper(context, DB_NAME, null, VERSION);
		db = dbHelper.getWritableDatabase();
	}
	
	/*
	 * ��ȡOrderDao��ʵ��
	 */
	public synchronized static ManagerCountActivityDao getInstance(Context context){
		if (managerCountActivityDAO == null) {
			managerCountActivityDAO = new ManagerCountActivityDao(context);
		}
		return managerCountActivityDAO;
	}
	
	
	//���뿪ʼ���ںͽ������ڣ�Ȼ���T_ORDER���ж�ȡ����ӦEND_DATETIME�ֶ�����Ӧ�������У��ѽ�������ڼ�����
	public List<OrderBean> getOrderBeanList(String startDate, String endDate){
		
		List<OrderBean> orderBeanList = null;
		
		Cursor cursor = db.query(TABLE_NAME, null, "END_DATETIME between ? and ?", 
				new String[]{startDate, endDate}, null, null, null);
//		Cursor cursor1 = db.rawQuery("select * from T_ORDER where END_DATETIME between ? and ?", 
//				new String[]{startDate, endDate});
		
		if (cursor.moveToFirst()) {
			
			orderBeanList = new ArrayList<OrderBean>();
			
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
			
				orderBeanList.add(orderBean);
				
			} while (cursor.moveToNext());
			
			cursor.close();
		}
		
		return orderBeanList;
	}

}
