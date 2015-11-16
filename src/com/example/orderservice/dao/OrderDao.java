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

import com.example.orderservice.model.OrderBean;
import com.example.orderservice.model.TableInfoBean;

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
public class OrderDao {

	/*
	 * 数据库名
	 */
	public static final String DB_NAME = "orderdish";
	
	/*
	 * 表名
	 */
	public static final String TABLE_NAME = "T_ORDER";
	
	/*
	 * 数据库版本
	 */
	public static final int VERSION = 1;
	
	
	private static OrderDao orderDao;
	
	private SQLiteDatabase db;
	
	/*
	 * 将构造方法私有化,以实现单列模式
	 * 实例化时，传递Context对象进来，后面代码要用到
	 */
	private OrderDao(Context context){
		MyDatabaseHelper dbHelper = new MyDatabaseHelper(context, DB_NAME, null, VERSION);
		db = dbHelper.getWritableDatabase();
	}
	
	/*
	 * 获取OrderDao的实例
	 */
	public synchronized static OrderDao getInstance(Context context){
		if (orderDao == null) {
			orderDao = new OrderDao(context);
		}
		return orderDao;
	}
	
	
	//CRUD操作------------------------------------------------》
	
	/*
	 * 将OrderBean实例插入T_ORDER表
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
	 * 将指定OrderBean从T_ORDER表中删除
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
	 * 修改指Order的信息
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
	 * T_ORDER表读取所有OrderBean实例
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
				
				//查询TABLE_INFO_ID这个外键所对应的数据
				int tableInfoId = cursor.getInt(cursor.getColumnIndex("TABLE_INFO_ID"));
				Cursor cursorTableInfoId = db.query("T_TABLE_INFO", new String[]{"NO"}, "ID = ?", 
						new String[]{String.valueOf(tableInfoId)}, null, null, null);
				if (cursorTableInfoId.moveToNext()) {
					orderBean.setTableNo(cursorTableInfoId.getInt(cursorTableInfoId.getColumnIndex("NO")));
				}
				cursorTableInfoId.close();
				
				//查询WAITER_ID这个外键所对应的数据
				int waiterId = cursor.getInt(cursor.getColumnIndex("WAITER_ID"));
				Cursor cursorWaiterId = db.query("T_EMPLOYEE", new String[]{"CODE"}, "ID = ?", 
						new String[]{String.valueOf(waiterId)}, null, null, null);
				if (cursorWaiterId.moveToNext()) {
					orderBean.setWaiterCode(cursorWaiterId.getString(cursorWaiterId.getColumnIndex("CODE")));
				}
				cursorWaiterId.close();
				
				//查询CASHIER_ID这个外键所对应的数据
				int cashierId = cursor.getInt(cursor.getColumnIndex("CASHIER_ID"));
				Cursor cursorCashierId = db.query("T_EMPLOYEE", new String[]{"CODE"}, "ID = ?", 
						new String[]{String.valueOf(cashierId)}, null, null, null);
				if (cursorCashierId.moveToNext()) {
					orderBean.setCashierCode(cursorCashierId.getString(cursorCashierId.getColumnIndex("CODE")));
				}
				cursorCashierId.close();
				
				//查询ISPAY这个外键所对应的数据
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
	 * 通过T_ORDER表的ID值，读取指定OrderBean实例
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

			//查询TABLE_INFO_ID这个外键所对应的数据
			int tableInfoId = cursor.getInt(cursor.getColumnIndex("TABLE_INFO_ID"));
			Cursor cursorTableInfoId = db.query("T_TABLE_INFO", new String[]{"NO"}, "ID = ?", 
					new String[]{String.valueOf(tableInfoId)}, null, null, null);
			if (cursorTableInfoId.moveToNext()) {
				orderBean.setTableNo(cursorTableInfoId.getInt(cursorTableInfoId.getColumnIndex("NO")));
			}
			cursorTableInfoId.close();
			
			//查询WAITER_ID这个外键所对应的数据
			int waiterId = cursor.getInt(cursor.getColumnIndex("WAITER_ID"));
			Cursor cursorWaiterId = db.query("T_EMPLOYEE", new String[]{"CODE"}, "ID = ?", 
					new String[]{String.valueOf(waiterId)}, null, null, null);
			if (cursorWaiterId.moveToNext()) {
				orderBean.setWaiterCode(cursorWaiterId.getString(cursorWaiterId.getColumnIndex("CODE")));
			}
			cursorWaiterId.close();
			
			//查询CASHIER_ID这个外键所对应的数据
			int cashierId = cursor.getInt(cursor.getColumnIndex("CASHIER_ID"));
			Cursor cursorCashierId = db.query("T_EMPLOYEE", new String[]{"CODE"}, "ID = ?", 
					new String[]{String.valueOf(cashierId)}, null, null, null);
			if (cursorCashierId.moveToNext()) {
				orderBean.setCashierCode(cursorCashierId.getString(cursorCashierId.getColumnIndex("CODE")));
			}
			cursorCashierId.close();
			
			//查询ISPAY这个外键所对应的数据
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
	 * 通过T_ORDER表的TABLE_INFO_ID字段和ISPAY字段，获得指定订单订单
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

			//查询TABLE_INFO_ID这个外键所对应的数据
			int tInfoId = cursor.getInt(cursor.getColumnIndex("TABLE_INFO_ID"));
			Cursor cursorTableInfoId = db.query("T_TABLE_INFO", new String[]{"NO"}, "ID = ?", 
					new String[]{String.valueOf(tInfoId)}, null, null, null);
			if (cursorTableInfoId.moveToNext()) {
				orderBean.setTableNo(cursorTableInfoId.getInt(cursorTableInfoId.getColumnIndex("NO")));
			}
			cursorTableInfoId.close();
			
			//查询WAITER_ID这个外键所对应的数据
			int waiterId = cursor.getInt(cursor.getColumnIndex("WAITER_ID"));
			Cursor cursorWaiterId = db.query("T_EMPLOYEE", new String[]{"CODE"}, "ID = ?", 
					new String[]{String.valueOf(waiterId)}, null, null, null);
			if (cursorWaiterId.moveToNext()) {
				orderBean.setWaiterCode(cursorWaiterId.getString(cursorWaiterId.getColumnIndex("CODE")));
			}
			cursorWaiterId.close();
			
			//查询CASHIER_ID这个外键所对应的数据
			int cashierId = cursor.getInt(cursor.getColumnIndex("CASHIER_ID"));
			Cursor cursorCashierId = db.query("T_EMPLOYEE", new String[]{"CODE"}, "ID = ?", 
					new String[]{String.valueOf(cashierId)}, null, null, null);
			if (cursorCashierId.moveToNext()) {
				orderBean.setCashierCode(cursorCashierId.getString(cursorCashierId.getColumnIndex("CODE")));
			}
			cursorCashierId.close();
			
			//查询ISPAY这个外键所对应的数据
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
