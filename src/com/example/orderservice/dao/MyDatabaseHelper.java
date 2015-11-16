/************************************************************
 *	��Ȩ����  (c)2011,   hxf<p>	
 *  �ļ�����	��MyDatabaseHelper.java<p>
 *
 *  ����ʱ��	��2015��10��14�� ����3:33:52 
 *  ��ǰ�汾�ţ�v1.0
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
 *  ����ժҪ	��<p>
 *
 *  ����	��hxf
 *  ����ʱ��	��2015��10��14�� ����3:33:52 
 *  ��ǰ�汾�ţ�v1.0
 *  ��ʷ��¼	:
 *  	����	: 2015��10��14�� ����3:33:52 	�޸��ˣ�
 *  	����	:
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
	 * ���캯����MyDatabaseHelper
	 * ��������:
	 * ����˵����
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
	 *  �������� ��onCreate
	 *  �������� ��  
	 *  ����˵�� ��
	 *  	@param db
	 *  ����ֵ��
	 *  	
	 *  �޸ļ�¼��
	 *  ���� ��2015��10��14�� ����3:33:52	�޸��ˣ�hxf
	 *  ���� ��
	 * 					
	 */
	@Override
	public void onCreate(SQLiteDatabase db) {
//		//ִ��SQL����������� 
//		db.execSQL(CREATE_DICTIONARY);
//		db.execSQL(CREATE_DICTIONARY_TYPE);
//		db.execSQL(CREATE_DISH_INFO);
//		db.execSQL(CREATE_EMPLOYEE);
//		db.execSQL(CREATE_ORDER);
//		db.execSQL(CREATE_ORDER_DETAIL);
//		db.execSQL(CREATE_ROLE);
//		db.execSQL(CREATE_TABLE_INFO);
//		db.execSQL(CREATE_TABLE_SIZE);
		
		 //��ȾSQLiteDatabase����ͽ���������ڵ��ļ���ʵ�ֽ���
		createTableOrInit(db, R.raw.db_create_table);
		//��ʼ����������
		createTableOrInit(db, R.raw.db_init_data);
		
		

	}

	/**
	 *  �������� : createTable
	 *  �������� :  
	 *  ����������ֵ˵����
	 *
	 *  �޸ļ�¼��
	 *  	���� ��2015��11��3�� ����8:31:55	�޸��ˣ�hxf
	 *  	����	��
	 * 					
	 */
	private void createTableOrInit(SQLiteDatabase db, int fileNameId) {
		Resources res = context.getResources();
		BufferedReader br = null;
		
		try {             //���ڱ��潨��Ͳ������ݵ��ļ�����GBK��д�ģ����Զ�ȡʱҲҪ��ҪGBK��
			br = new BufferedReader(new InputStreamReader(res.openRawResource(fileNameId),"GBK"));
		} catch (UnsupportedEncodingException | NotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String str="";
		try {
			while((str = br.readLine())!=null){
				//ִ�н������
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
	 *  �������� ��onUpgrade
	 *  �������� ��  
	 *  ����˵�� ��
	 *  	@param db
	 *  	@param oldVersion
	 *  	@param newVersion
	 *  ����ֵ��
	 *  	
	 *  �޸ļ�¼��
	 *  ���� ��2015��10��14�� ����3:33:52	�޸��ˣ�hxf
	 *  ���� ��
	 * 					
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

}
