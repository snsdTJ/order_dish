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

import com.example.orderservice.model.TableInfoBean;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.UrlQuerySanitizer.ValueSanitizer;

/************************************************************
 * 内容摘要 ：
 * <p>
 *
 * 作者 ：hxf 创建时间 ：2015年10月16日 下午4:20:33 当前版本号：v1.0 历史记录 : 日期 : 2015年10月16日
 * 下午4:20:33 修改人： 描述 :
 ************************************************************/
public class TableInfoDao {

	/*
	 * 数据库名
	 */
	public static final String DB_NAME = "orderdish";

	/*
	 * 表名
	 */
	public static final String TABLE_NAME = "T_TABLE_INFO";

	/*
	 * 数据库版本
	 */
	public static final int VERSION = 1;

	private static TableInfoDao tableInfoDao;

	private SQLiteDatabase db;

	/*
	 * 将构造方法私有化,以实现单列模式 实例化时，传递Context对象进来，后面代码要用到
	 */
	private TableInfoDao(Context context) {
		MyDatabaseHelper dbHelper = new MyDatabaseHelper(context, DB_NAME, null, VERSION);
		db = dbHelper.getWritableDatabase();
	}

	/*
	 * 获取tableInfoDao的实例
	 */
	public synchronized static TableInfoDao getInstance(Context context) {
		if (tableInfoDao == null) {
			tableInfoDao = new TableInfoDao(context);
		}
		return tableInfoDao;
	}

	// CRUD操作------------------------------------------------》

	/*
	 * 将TableInfoBean实例插入T_TABLE_INFO表
	 */
	public long saveTableInfo(TableInfoBean tableInfoBean) {
		long newRow = 0;

		if (tableInfoBean != null) {
			ContentValues values = new ContentValues();
			values.put("NO", tableInfoBean.getNo());
			values.put("SIZE_ID", tableInfoBean.getSizeId());
			values.put("STATE", tableInfoBean.getState());
			values.put("DESCRIBE", tableInfoBean.getDescribe());
			newRow = db.insert(TABLE_NAME, null, values);
		}

		return newRow;
	}

	/*
	 * 将指定TableInfoBean从T_TABLE_INFO表中删除
	 */
	public boolean deleteTableInfo(TableInfoBean tableInfoBean) {
		if (tableInfoBean != null) {
			int id = tableInfoBean.getId();
			db.delete(TABLE_NAME, "ID = ?", new String[] { String.valueOf(id) });
			return true;
		}
		return false;
	}

	/*
	 * 修改指定桌的信息
	 */
	public boolean modifyTableInfo(TableInfoBean tableInfoBean) {

		if (tableInfoBean != null) {
			int id = tableInfoBean.getId();
			int no = tableInfoBean.getNo();
			int sizeId = tableInfoBean.getSizeId();
			int state = tableInfoBean.getState();
			String describe = tableInfoBean.getDescribe();

			ContentValues values = new ContentValues();
			values.put("NO", no);
			values.put("SIZE_ID", sizeId);
			values.put("STATE", state);
			values.put("DESCRIBE", describe);

			db.update(TABLE_NAME, values, "ID = ?", new String[] { String.valueOf(id) });
			return true;
		}

		return false;
	}

	/*
	 * 通过桌号修改指定桌的状态
	 */
	public boolean modifyTableInfo(int tableNumber, int state) {

		if (tableNumber > 0) {

			ContentValues values = new ContentValues();

			values.put("STATE", state);

			db.update(TABLE_NAME, values, "NO = ?", new String[] { String.valueOf(tableNumber) });
			return true;
		}

		return false;
	}

	/*
	 * T_TABLE_INFO表读取所有TableInfoBean实例
	 */
	public List<TableInfoBean> loadTableInfoBean() {
		List<TableInfoBean> list = new ArrayList<TableInfoBean>();
		Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, null);

		if (cursor.moveToFirst()) {
			do {
				TableInfoBean tableInfoBean = new TableInfoBean();

				tableInfoBean.setId(cursor.getInt(cursor.getColumnIndex("ID")));
				tableInfoBean.setNo(cursor.getInt(cursor.getColumnIndex("NO")));

				int sizeId = cursor.getInt(cursor.getColumnIndex("SIZE_ID"));
				tableInfoBean.setSizeId(sizeId);
				// 查询SIZE_ID这个外键所对应的数据
				Cursor cursorSizeId = db.query("T_TABLE_SIZE", new String[] { "SIZE" }, "ID = ?",
						new String[] { String.valueOf(sizeId) }, null, null, null);
				if (cursorSizeId.moveToNext()) {
					tableInfoBean.setTableSize(cursorSizeId.getInt(cursorSizeId.getColumnIndex("SIZE")));
				}
				cursorSizeId.close();

				int state = cursor.getInt(cursor.getColumnIndex("STATE"));
				tableInfoBean.setState(state);
				// 查询STATE这个外键所对应的数据
				Cursor cursorState = db.query("T_DATA_DICTIONARY", null, "ID = ?",
						new String[] { String.valueOf(state) }, null, null, null);
				if (cursorState.moveToNext()) {
					tableInfoBean.setStateName(cursorState.getString(cursorState.getColumnIndex("NAME")));
				}
				cursorState.close();

				tableInfoBean.setDescribe(cursor.getString(cursor.getColumnIndex("DESCRIBE")));
				list.add(tableInfoBean);

			} while (cursor.moveToNext());
		}
		return list;
	}

	/*
	 * T_TABLE_INFO表读取所有TableInfoBean实例
	 */
	public TableInfoBean getTableInfoBean(int tableInfoId) {
		Cursor cursor = db.query(TABLE_NAME, null, "ID = ?", new String[] { String.valueOf(tableInfoId) }, null, null,
				null);
		TableInfoBean tableInfoBean = null;
		if (cursor != null) {
			while (cursor.moveToNext()) {
				tableInfoBean = new TableInfoBean();

				tableInfoBean.setId(cursor.getInt(cursor.getColumnIndex("ID")));
				tableInfoBean.setNo(cursor.getInt(cursor.getColumnIndex("NO")));

				int sizeId = cursor.getInt(cursor.getColumnIndex("SIZE_ID"));
				tableInfoBean.setSizeId(sizeId);
				// 查询SIZE_ID这个外键所对应的数据
				Cursor cursorSizeId = db.query("T_TABLE_SIZE", new String[] { "SIZE" }, "ID = ?",
						new String[] { String.valueOf(sizeId) }, null, null, null);
				if (cursorSizeId.moveToNext()) {
					tableInfoBean.setTableSize(cursorSizeId.getInt(cursorSizeId.getColumnIndex("SIZE")));
				}
				cursorSizeId.close();

				int state = cursor.getInt(cursor.getColumnIndex("STATE"));
				tableInfoBean.setState(state);
				// 查询STATE这个外键所对应的数据
				Cursor cursorState = db.query("T_DATA_DICTIONARY", null, "ID = ?", new String[] { String.valueOf(state) },
						null, null, null);
				if (cursorState.moveToNext()) {
					tableInfoBean.setStateName(cursorState.getString(cursorState.getColumnIndex("NAME")));
				}
				cursorState.close();

				tableInfoBean.setDescribe(cursor.getString(cursor.getColumnIndex("DESCRIBE")));
				
			}
			

		}
		return tableInfoBean;
	}

}
