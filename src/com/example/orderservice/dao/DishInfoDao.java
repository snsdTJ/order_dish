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

import com.example.orderservice.model.DishInfoBean;
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
public class DishInfoDao {

	/*
	 * ���ݿ���
	 */
	public static final String DB_NAME = "orderdish";
	
	/*
	 * ����
	 */
	public static final String TABLE_NAME = "T_DISH_INFO";
	
	/*
	 * ���ݿ�汾
	 */
	public static final int VERSION = 1;
	
	private static DishInfoDao dishInfoDao;
	
	private SQLiteDatabase db;
	
	/*
	 * �����췽��˽�л�,��ʵ�ֵ���ģʽ
	 * ʵ����ʱ������Context����������������Ҫ�õ�
	 */
	private DishInfoDao(Context context){
		MyDatabaseHelper dbHelper = new MyDatabaseHelper(context, DB_NAME, null, VERSION);
		db = dbHelper.getWritableDatabase();
	}
	
	/*
	 * ��ȡdishInfoDao��ʵ��
	 */
	public synchronized static DishInfoDao getInstance(Context context){
		if (dishInfoDao == null) {
			dishInfoDao = new DishInfoDao(context);
		}
		return dishInfoDao;
	}
	
	
	//CRUD����------------------------------------------------��
	
	/*
	 * ��DishInfoBeanʵ������T_Dish_INFO��
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
	 * ��ָ��dishInfoBean��T_Dish_INFO����ɾ��
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
	 * �޸�ָ��������Ϣ
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
	 * T_Dish_INFO���ȡ����dishInfoBeanʵ��
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
				//��ѯDISH_TYPE����������Ӧ������
				Cursor cursorDishType = db.query("T_DATA_DICTIONARY", new String[]{"NAME"}, "ID = ?", 
						new String[]{String.valueOf(dishType)}, null, null, null);
				if (cursorDishType.moveToNext()) {
					dishInfoBean.setDishTypeName(cursorDishType.getString(cursorDishType.getColumnIndex("NAME")));
				}	
				cursorDishType.close();
				
				dishInfoBean.setPrice(cursor.getFloat(cursor.getColumnIndex("PRICE")));	
				
				int currencyType = cursor.getInt(cursor.getColumnIndex("CURRENCY_TYPE"));
				dishInfoBean.setCurrencyType(currencyType);
				//��ѯCURRENCY_TYPE����������Ӧ������
				Cursor cursorCurrencyType = db.query("T_DATA_DICTIONARY", new String[]{"NAME"}, "ID = ?", 
						new String[]{String.valueOf(currencyType)}, null, null, null);
				if (cursorCurrencyType.moveToNext()) {
					dishInfoBean.setCurrencyTypeName(cursorCurrencyType.getString(cursorCurrencyType.getColumnIndex("NAME")));
				}
				cursorCurrencyType.close();
				
				int unit = cursor.getInt(cursor.getColumnIndex("UNIT"));
				dishInfoBean.setUnit(unit);
				//��ѯUNIT����������Ӧ������
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
