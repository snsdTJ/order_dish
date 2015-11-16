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
import com.example.orderservice.model.OrderDetailBean;

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
public class OrderDetailDao {
	
	private Context context;

	/*
	 * 数据库名
	 */
	public static final String DB_NAME = "orderdish";
	
	/*
	 * 表名
	 */
	public static final String TABLE_NAME = "T_ORDER_DETAIL";
	
	/*
	 * 数据库版本
	 */
	public static final int VERSION = 1;
	
	
	private static OrderDetailDao orderDetailDao;
	
	private SQLiteDatabase db;
	
	/*
	 * 将构造方法私有化,以实现单列模式
	 * 实例化时，传递Context对象进来，后面代码要用到
	 */
	private OrderDetailDao(Context context){
		MyDatabaseHelper dbHelper = new MyDatabaseHelper(context, DB_NAME, null, VERSION);
		db = dbHelper.getWritableDatabase();
		this.context = context;
	}
	
	/*
	 * 获取OrderDetailDao的实例
	 */
	public synchronized static OrderDetailDao getInstance(Context context){
		if (orderDetailDao == null) {
			orderDetailDao = new OrderDetailDao(context);
		}
		return orderDetailDao;
	}
	
	
	//CRUD操作------------------------------------------------》
	
	/*
	 * 将OrderDetailBean实例插入T_ORDER_DETAIL表
	 */
	public long saveOrderDetailBean(OrderDetailBean orderDetailBean){
		long newRow = 0;
		
		if (orderDetailBean != null) {
			ContentValues values = new ContentValues();
			values.put("ORDER_ID", orderDetailBean.getOrderId());
			values.put("DISH_INFO_ID", orderDetailBean.getDishInfoId());
			values.put("CURRENT_PRICE", orderDetailBean.getCurrentPrice());
			values.put("DISH_NUMBER", orderDetailBean.getDishNumber());
			
			newRow = db.insert(TABLE_NAME, null, values);		
		}
				
		return newRow;		
	}
	
	/*
	 * 将指定OrderDetailBean从表中删除
	 */
	public boolean deleteOrderDetailBean(OrderDetailBean orderDetailBean){
		if (orderDetailBean != null) {
			int id = orderDetailBean.getId();
			db.delete(TABLE_NAME, "ID = ?", new String[]{String.valueOf(id)});
			return  true;
		}
		return false;
	}
	/*
	 * 将指定订单ORDER_ID的行从表中删除//ORDER_ID这个外键和T_ORDER表中的ID字段相关联
	 */
	public boolean deleteOrderDetailBean(int orderId){
		if (orderId != 0) {
			db.delete(TABLE_NAME, "ORDER_ID = ?", new String[]{String.valueOf(orderId)});
			return  true;
		}
		return false;
	}
	
	/*
	 * 修改指ID值的信息
	 */
	public boolean modifyOrderDetailBean(OrderDetailBean orderDetailBean){
		
		if (orderDetailBean != null) {
			
			int id = orderDetailBean.getId();	
			
			ContentValues values = new ContentValues();
			values.put("ORDER_ID", orderDetailBean.getOrderId());
			values.put("DISH_INFO_ID", orderDetailBean.getDishInfoId());
			values.put("CURRENT_PRICE", orderDetailBean.getCurrentPrice());
			values.put("DISH_NUMBER", orderDetailBean.getDishNumber());
						
			db.update(TABLE_NAME, values, "ID = ?", new String[]{String.valueOf(id)});
			return true;
		}
		
		return false;
	}
	
	/*
	 * 读取所有OrderDetailBean实例
	 */
	public List<OrderDetailBean> loadOrderDetailBean(){
		List<OrderDetailBean> list = new ArrayList<OrderDetailBean>();
		Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, null);
		
		if (cursor.moveToFirst()) {
			do {
				OrderDetailBean orderDetailBean = new OrderDetailBean();
				orderDetailBean.setId(cursor.getInt(cursor.getColumnIndex("ID")));
				orderDetailBean.setOrderId(cursor.getInt(cursor.getColumnIndex("ORDER_ID")));
				orderDetailBean.setDishInfoId(cursor.getInt(cursor.getColumnIndex("DISH_INFO_ID")));
				orderDetailBean.setCurrentPrice(cursor.getFloat(cursor.getColumnIndex("CURRENT_PRICE")));
				orderDetailBean.setDishNumber(cursor.getInt(cursor.getColumnIndex("DISH_NUMBER")));				
				
				//查询DISH_INFO_ID这个外键所对应的数据
				int dishInfoId = cursor.getInt(cursor.getColumnIndex("DISH_INFO_ID"));
				Cursor cursorDishInfoId = db.query("T_DISH_INFO", new String[]{"NAME"}, "ID = ?", 
						new String[]{String.valueOf(dishInfoId)}, null, null, null);
				if (cursorDishInfoId.moveToNext()) {
					orderDetailBean.setDishName(cursorDishInfoId.getString(cursorDishInfoId.getColumnIndex("NAME")));
				}
				cursorDishInfoId.close();
				
				//查询ORDER_ID这个外键所对应的T_ORDER表的指定ID的所有行数据,
				//ORDER_ID外键  -- T_ORDER表 -- ID
				int orderId = cursor.getInt(cursor.getColumnIndex("ORDER_ID"));
				OrderBean orderBean = OrderDao.getInstance(context).getOrderBean(orderId);
				orderDetailBean.setOrderBean(orderBean);
		
				list.add(orderDetailBean);			
			} while (cursor.moveToNext());
		}
		return list;
	}
	
	
}
