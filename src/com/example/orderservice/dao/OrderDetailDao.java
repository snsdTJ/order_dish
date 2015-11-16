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
import com.example.orderservice.model.OrderDetailBean;

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
public class OrderDetailDao {
	
	private Context context;

	/*
	 * ���ݿ���
	 */
	public static final String DB_NAME = "orderdish";
	
	/*
	 * ����
	 */
	public static final String TABLE_NAME = "T_ORDER_DETAIL";
	
	/*
	 * ���ݿ�汾
	 */
	public static final int VERSION = 1;
	
	
	private static OrderDetailDao orderDetailDao;
	
	private SQLiteDatabase db;
	
	/*
	 * �����췽��˽�л�,��ʵ�ֵ���ģʽ
	 * ʵ����ʱ������Context����������������Ҫ�õ�
	 */
	private OrderDetailDao(Context context){
		MyDatabaseHelper dbHelper = new MyDatabaseHelper(context, DB_NAME, null, VERSION);
		db = dbHelper.getWritableDatabase();
		this.context = context;
	}
	
	/*
	 * ��ȡOrderDetailDao��ʵ��
	 */
	public synchronized static OrderDetailDao getInstance(Context context){
		if (orderDetailDao == null) {
			orderDetailDao = new OrderDetailDao(context);
		}
		return orderDetailDao;
	}
	
	
	//CRUD����------------------------------------------------��
	
	/*
	 * ��OrderDetailBeanʵ������T_ORDER_DETAIL��
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
	 * ��ָ��OrderDetailBean�ӱ���ɾ��
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
	 * ��ָ������ORDER_ID���дӱ���ɾ��//ORDER_ID��������T_ORDER���е�ID�ֶ������
	 */
	public boolean deleteOrderDetailBean(int orderId){
		if (orderId != 0) {
			db.delete(TABLE_NAME, "ORDER_ID = ?", new String[]{String.valueOf(orderId)});
			return  true;
		}
		return false;
	}
	
	/*
	 * �޸�ָIDֵ����Ϣ
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
	 * ��ȡ����OrderDetailBeanʵ��
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
				
				//��ѯDISH_INFO_ID����������Ӧ������
				int dishInfoId = cursor.getInt(cursor.getColumnIndex("DISH_INFO_ID"));
				Cursor cursorDishInfoId = db.query("T_DISH_INFO", new String[]{"NAME"}, "ID = ?", 
						new String[]{String.valueOf(dishInfoId)}, null, null, null);
				if (cursorDishInfoId.moveToNext()) {
					orderDetailBean.setDishName(cursorDishInfoId.getString(cursorDishInfoId.getColumnIndex("NAME")));
				}
				cursorDishInfoId.close();
				
				//��ѯORDER_ID����������Ӧ��T_ORDER���ָ��ID������������,
				//ORDER_ID���  -- T_ORDER�� -- ID
				int orderId = cursor.getInt(cursor.getColumnIndex("ORDER_ID"));
				OrderBean orderBean = OrderDao.getInstance(context).getOrderBean(orderId);
				orderDetailBean.setOrderBean(orderBean);
		
				list.add(orderDetailBean);			
			} while (cursor.moveToNext());
		}
		return list;
	}
	
	
}
