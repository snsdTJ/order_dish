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

import com.example.orderservice.model.DishInfoBean;
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
public class DishInfoDao {

	/*
	 * 数据库名
	 */
	public static final String DB_NAME = "orderdish";
	
	/*
	 * 表名
	 */
	public static final String TABLE_NAME = "T_DISH_INFO";
	
	/*
	 * 数据库版本
	 */
	public static final int VERSION = 1;
	
	private static DishInfoDao dishInfoDao;
	
	private SQLiteDatabase db;
	
	/*
	 * 将构造方法私有化,以实现单列模式
	 * 实例化时，传递Context对象进来，后面代码要用到
	 */
	private DishInfoDao(Context context){
		MyDatabaseHelper dbHelper = new MyDatabaseHelper(context, DB_NAME, null, VERSION);
		db = dbHelper.getWritableDatabase();
	}
	
	/*
	 * 获取dishInfoDao的实例
	 */
	public synchronized static DishInfoDao getInstance(Context context){
		if (dishInfoDao == null) {
			dishInfoDao = new DishInfoDao(context);
		}
		return dishInfoDao;
	}
	
	
	//CRUD操作------------------------------------------------》
	
	/*
	 * 将DishInfoBean实例插入T_Dish_INFO表
	 */
	public long saveDishInfo(DishInfoBean dishInfoBean){
		long newRow = 0;
		
		if (dishInfoBean != null) {
			ContentValues values = new ContentValues();
			values.put("NAME", dishInfoBean.getName());
			values.put("DISH_TYPE", dishInfoBean.getDishType());
			values.put("PRICE", dishInfoBean.getPrice());
			values.put("CURRENCY_TYPE", dishInfoBean.getCurrencyType());
			values.put("UNIT", dishInfoBean.getUnit());
			values.put("PHOTO", dishInfoBean.getPhoto());
			newRow = db.insert(TABLE_NAME, null, values);		
		}
				
		return newRow;		
	}
	
	/*
	 * 将指定dishInfoBean从T_Dish_INFO表中删除
	 */
	public boolean deleteDishInfo(DishInfoBean dishInfoBean){
		if (dishInfoBean != null) {
			int id = dishInfoBean.getId();
			int flag = db.delete(TABLE_NAME, "ID = ?", new String[]{String.valueOf(id)});
			if (flag==0) {
				return false;
			}
			return  true;
		}
		return false;
	}
	
	/*
	 * 修改指定桌的信息
	 */
	public boolean modifyDishInfo(DishInfoBean dishInfoBean){
		
		if (dishInfoBean != null) {
			int id = dishInfoBean.getId();	
			
			String name = dishInfoBean.getName();			
			int dishType = dishInfoBean.getDishType();			
			float price = dishInfoBean.getPrice();			
			int currencyType = dishInfoBean.getCurrencyType();
			int unit = dishInfoBean.getUnit();
			String photo = dishInfoBean.getPhoto();
			
			ContentValues values = new ContentValues();
			values.put("NAME", name);
			values.put("DISH_TYPE", dishType);
			values.put("PRICE", price);
			values.put("CURRENCY_TYPE", currencyType);
			values.put("UNIT", unit);
			values.put("PHOTO", photo);
			
			db.update(TABLE_NAME, values, "ID = ?", new String[]{String.valueOf(id)});
			return true;
		}
		
		return false;
	}
	
	/*
	 * T_Dish_INFO表读取所有dishInfoBean实例
	 */
	public List<DishInfoBean> loadDishInfoBean(){
		List<DishInfoBean> list = new ArrayList<DishInfoBean>();
		Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, null);
		
		if (cursor.moveToFirst()) {
			do {
				DishInfoBean dishInfoBean = new DishInfoBean();
				
				dishInfoBean.setId(cursor.getInt(cursor.getColumnIndex("ID")));
				dishInfoBean.setName(cursor.getString(cursor.getColumnIndex("NAME")));
				
				int dishType = cursor.getInt(cursor.getColumnIndex("DISH_TYPE"));
				dishInfoBean.setDishType(dishType);
				//查询DISH_TYPE这个外键所对应的数据
				Cursor cursorDishType = db.query("T_DATA_DICTIONARY", new String[]{"NAME"}, "ID = ?", 
						new String[]{String.valueOf(dishType)}, null, null, null);
				if (cursorDishType.moveToNext()) {
					dishInfoBean.setDishTypeName(cursorDishType.getString(cursorDishType.getColumnIndex("NAME")));
				}	
				cursorDishType.close();
				
				dishInfoBean.setPrice(cursor.getFloat(cursor.getColumnIndex("PRICE")));	
				
				int currencyType = cursor.getInt(cursor.getColumnIndex("CURRENCY_TYPE"));
				dishInfoBean.setCurrencyType(currencyType);
				//查询CURRENCY_TYPE这个外键所对应的数据
				Cursor cursorCurrencyType = db.query("T_DATA_DICTIONARY", new String[]{"NAME"}, "ID = ?", 
						new String[]{String.valueOf(currencyType)}, null, null, null);
				if (cursorCurrencyType.moveToNext()) {
					dishInfoBean.setCurrencyTypeName(cursorCurrencyType.getString(cursorCurrencyType.getColumnIndex("NAME")));
				}
				cursorCurrencyType.close();
				
				int unit = cursor.getInt(cursor.getColumnIndex("UNIT"));
				dishInfoBean.setUnit(unit);
				//查询UNIT这个外键所对应的数据
				Cursor cursorUnit = db.query("T_DATA_DICTIONARY", new String[]{"NAME"}, "ID = ?", 
						new String[]{String.valueOf(unit)}, null, null, null);
				if (cursorUnit.moveToNext()) {
					dishInfoBean.setUnitName(cursorUnit.getString(cursorUnit.getColumnIndex("NAME")));
				}				
				cursorUnit.close();
				
				dishInfoBean.setPhoto(cursor.getString(cursor.getColumnIndex("PHOTO")));
				
				
				
				list.add(dishInfoBean);
				
			} while (cursor.moveToNext());
		}
		return list;
	}
	
	
}
