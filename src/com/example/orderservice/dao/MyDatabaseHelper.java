/************************************************************
 *	版权所有  (c)2011,   hxf<p>	
 *  文件名称	：MyDatabaseHelper.java<p>
 *
 *  创建时间	：2015年10月14日 下午3:33:52 
 *  当前版本号：v1.0
 ************************************************************/
package com.example.orderservice.dao;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;

import com.example.orderservice.R;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.Resources.NotFoundException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

/************************************************************
 *  内容摘要	：<p>
 *
 *  作者	：hxf
 *  创建时间	：2015年10月14日 下午3:33:52 
 *  当前版本号：v1.0
 *  历史记录	:
 *  	日期	: 2015年10月14日 下午3:33:52 	修改人：
 *  	描述	:
 ************************************************************/
public class MyDatabaseHelper extends SQLiteOpenHelper {
	
	private Context context;
	
//	public static final String CREATE_DICTIONARY = "CREATE TABLE T_DATA_DICTIONARY ("
//			+ "ID INTEGER PRIMARY KEY AUTOINCREMENT," 
//			+ "CODE VARCHAR(50) NOT NULL, " 
//			+ "NAME VARCHAR(50) NOT NULL, "
//			+ "TYPE INTEGER NOT NULL)";
//	public static final String CREATE_DICTIONARY_TYPE = "CREATE TABLE T_DATA_DICTIONARY_TYPE ("
//            +"ID INTEGER PRIMARY KEY AUTOINCREMENT, "
//            +"NAME VARCHAR(100) NOT NULL)";
//	public static final String CREATE_DISH_INFO = "CREATE TABLE T_DISH_INFO ("
//			+ "ID INTEGER PRIMARY KEY AUTOINCREMENT, " 
//			+ "NAME VARCHAR(100) NOT NULL, " 
//			+ "DISH_TYPE INTEGER NOT NULL,"
//			+ "PRICE FLOAT NOT NULL, " 
//			+ "CURRENCY_TYPE INTEGER," 
//			+ "UNIT INTEGER NOT NULL,"
//			+ "PHOTO VARCHAR(200) NOT NULL)";
//	public static final String CREATE_EMPLOYEE = "CREATE TABLE T_EMPLOYEE ("
//            + "ID INTEGER PRIMARY KEY AUTOINCREMENT,"
//			+ "CODE CHAR(6) NOT NULL," 
//            + "PWD VARCHAR(20) NOT NULL, " 
//			+ "USERNAME VARCHAR(100) NOT NULL, "
//			+ "ROLE_ID INTEGER NOT NULL)";
//	public static final String CREATE_ORDER = "CREATE TABLE T_ORDER ("
//            + "ID INTEGER PRIMARY KEY AUTOINCREMENT, "
//			+ "TABLE_INFO_ID INTEGER NOT NULL," 
//            + "WAITER_ID INTEGER NOT NULL," 
//			+ "CASHIER_ID INTEGER, "
//			+ "BEGIN_DATETIME DATETIME NOT NULL, " 
//			+ "END_DATETIME DATETIME, " 
//			+ "TOTAL_MONEY FLOAT,"
//			+ "ISPAY INTEGER NOT NULL)";
//	public static final String CREATE_ORDER_DETAIL = "CREATE TABLE T_ORDER_DETAIL ("
//			+ "ID INTEGER PRIMARY KEY AUTOINCREMENT, " 
//			+ "ORDER_ID INTEGER NOT NULL," 
//			+ "DISH_INFO_ID INTEGER NOT NULL,"
//			+ "CURRENT_PRICE FLOAT NOT NULL," 
//			+ "DISH_NUMBER INTEGER NOT NULL)";
//	public static final String CREATE_ROLE = "CREATE TABLE T_ROLE ("
//            + "ID INTEGER PRIMARY KEY AUTOINCREMENT,"
//			+ "NAME VARCHAR(100) NOT NULL)";
//	public static final String CREATE_TABLE_INFO = "CREATE TABLE T_TABLE_INFO ("
//			+ "ID INTEGER PRIMARY KEY AUTOINCREMENT, " 
//			+ "NO INTEGER NOT NULL," 
//			+ "SIZE_ID INTEGER NOT NULL, "
//			+ "STATE INTEGER NOT NULL, " 
//			+ "DESCRIBE VARCHAR(200))";
//	public static final String CREATE_TABLE_SIZE = "CREATE TABLE T_TABLE_SIZE ("
//			+ "ID INTEGER PRIMARY KEY AUTOINCREMENT, " 
//			+ "SIZE INTEGER NOT NULL)";
	
	
	
	/**
	 * 构造函数：MyDatabaseHelper
	 * 函数功能:
	 * 参数说明：
	 * 		@param context
	 * 		@param name
	 * 		@param factory
	 * 		@param version
	 */
	public MyDatabaseHelper(Context context, String name, CursorFactory factory, int version) {
		super(context, name, factory, version);
		this.context = context;
	}

	/**
	 *  函数名称 ：onCreate
	 *  功能描述 ：  
	 *  参数说明 ：
	 *  	@param db
	 *  返回值：
	 *  	
	 *  修改记录：
	 *  日期 ：2015年10月14日 下午3:33:52	修改人：hxf
	 *  描述 ：
	 * 					
	 */
	@Override
	public void onCreate(SQLiteDatabase db) {
//		//执行SQL语句来创建表 
//		db.execSQL(CREATE_DICTIONARY);
//		db.execSQL(CREATE_DICTIONARY_TYPE);
//		db.execSQL(CREATE_DISH_INFO);
//		db.execSQL(CREATE_EMPLOYEE);
//		db.execSQL(CREATE_ORDER);
//		db.execSQL(CREATE_ORDER_DETAIL);
//		db.execSQL(CREATE_ROLE);
//		db.execSQL(CREATE_TABLE_INFO);
//		db.execSQL(CREATE_TABLE_SIZE);
		
		 //传染SQLiteDatabase对象和建表语句所在的文件，实现建表
		createTableOrInit(db, R.raw.db_create_table);
		//初始化表中数据
		createTableOrInit(db, R.raw.db_init_data);
		
		

	}

	/**
	 *  函数名称 : createTable
	 *  功能描述 :  
	 *  参数及返回值说明：
	 *
	 *  修改记录：
	 *  	日期 ：2015年11月3日 下午8:31:55	修改人：hxf
	 *  	描述	：
	 * 					
	 */
	private void createTableOrInit(SQLiteDatabase db, int fileNameId) {
		Resources res = context.getResources();
		BufferedReader br = null;
		
		try {             //由于保存建表和插入数据的文件是用GBK编写的，所以读取时也要是要GBK码
			br = new BufferedReader(new InputStreamReader(res.openRawResource(fileNameId),"GBK"));
		} catch (UnsupportedEncodingException | NotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String str="";
		try {
			while((str = br.readLine())!=null){
				//执行建表语句
				db.execSQL(str);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			if (br!=null) {
				try {
					br.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
	}

	/**
	 *  函数名称 ：onUpgrade
	 *  功能描述 ：  
	 *  参数说明 ：
	 *  	@param db
	 *  	@param oldVersion
	 *  	@param newVersion
	 *  返回值：
	 *  	
	 *  修改记录：
	 *  日期 ：2015年10月14日 下午3:33:52	修改人：hxf
	 *  描述 ：
	 * 					
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

}
