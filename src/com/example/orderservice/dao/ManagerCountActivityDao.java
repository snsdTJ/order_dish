/************************************************************
 *	版权所有  (c)2011,   hxf<p>	
 *  文件名称	：ManagerCountActivityDao.java<p>
 *
 *  创建时间	：2015年10月29日 下午10:34:57 
 *  当前版本号：v1.0
 ************************************************************/
package com.example.orderservice.dao;

import java.util.ArrayList;
import java.util.List;

import com.example.orderservice.model.OrderBean;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/************************************************************
 *  内容摘要	：<p>
 *
 *  作者	：hxf
 *  创建时间	：2015年10月29日 下午10:34:57 
 *  当前版本号：v1.0
 *  历史记录	:
 *  	日期	: 2015年10月29日 下午10:34:57 	修改人：
 *  	描述	:
 ************************************************************/
public class ManagerCountActivityDao {
	
	/*
	 * 数据库名
	 */
	private final String DB_NAME = "orderdish";
	
	/*
	 * 表名
	 */
	private final String TABLE_NAME = "T_ORDER";
	
	/*
	 * 数据库版本
	 */
	private final int VERSION = 1;
	
	
	
	
	private static ManagerCountActivityDao managerCountActivityDAO;
	
	private SQLiteDatabase db;
	
	/*
	 * 将构造方法私有化,以实现单列模式
	 * 实例化时，传递Context对象进来，后面代码要用到
	 */
	private ManagerCountActivityDao(Context context){
		MyDatabaseHelper dbHelper = new MyDatabaseHelper(context, DB_NAME, null, VERSION);
		db = dbHelper.getWritableDatabase();
	}
	
	/*
	 * 获取OrderDao的实例
	 */
	public synchronized static ManagerCountActivityDao getInstance(Context context){
		if (managerCountActivityDAO == null) {
			managerCountActivityDAO = new ManagerCountActivityDao(context);
		}
		return managerCountActivityDAO;
	}
	
	
	//输入开始日期和结束日期，然后从T_ORDER表中读取出相应END_DATETIME字段所对应的所有列，把结果保存在集合中
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
			
				orderBeanList.add(orderBean);
				
			} while (cursor.moveToNext());
			
			cursor.close();
		}
		
		return orderBeanList;
	}

}
