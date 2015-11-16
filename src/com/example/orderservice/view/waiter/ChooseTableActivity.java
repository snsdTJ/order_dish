package com.example.orderservice.view.waiter;
///************************************************************
// *	版权所有  (c)2011,   hxf<p>	
// *  文件名称	：ChooseTableActivity.java<p>
// *
// *  创建时间	：2015年10月15日 下午9:02:15 
// *  当前版本号：v1.0
// ************************************************************/
//package com.example.orderservice.view;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import com.example.orderservice.R;
//import com.example.orderservice.adapter.ChooseTableAdapter;
//import com.example.orderservice.dao.OrderDao;
//import com.example.orderservice.dao.TableInfoDao;
//import com.example.orderservice.model.OrderBean;
//import com.example.orderservice.model.TableInfoBean;
//
//import android.app.Activity;
//import android.content.Intent;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.widget.AdapterView;
//import android.widget.AdapterView.OnItemClickListener;
//import android.widget.Button;
//import android.widget.GridView;
//import android.widget.TextView;
//
///************************************************************
// *  内容摘要	：<p>
// *
// *  作者	：hxf
// *  创建时间	：2015年10月15日 下午9:02:15 
// *  当前版本号：v1.0
// *  历史记录	:
// *  	日期	: 2015年10月15日 下午9:02:15 	修改人：
// *  	描述	:
// ************************************************************/
//public class ChooseTableActivity extends Activity {
//	
//	private TextView textView;
//	
//	private GridView gridView;
//	
//	private List<TableInfoBean> tableInfoList;
//	
//	private ChooseTableAdapter adapter;
//	
//	private Button btnOff;
//	
//	
//	
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		// TODO Auto-generated method stub
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.fragment_choose_table);
//		
//		textView = (TextView) findViewById(R.id.text_view);
//		gridView = (GridView) findViewById(R.id.grid_view);
//		
//		Intent intent = getIntent();
//		final int waiterId = intent.getIntExtra("waiterId", 0);
//		final String waiterAccount = intent.getStringExtra("waiterAccount");
//		textView.setText("服务员编号： "+waiterAccount);
//		
//		tableInfoList = getTableInfo();
//		
//		adapter = new ChooseTableAdapter(this, R.layout.table_item, tableInfoList);
//		
//		gridView.setAdapter(adapter);
//		
//		gridView.setOnItemClickListener(new OnItemClickListener() {
//
//			@Override
//			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//				TableInfoBean tableInfoBean = tableInfoList.get(position);
//				int state = tableInfoBean.getState();
//				Log.i("tag", String.valueOf(state));
//				switch (state) {
//				case 1:
//					//state==1说明 ---未开桌---跳转到开桌界面 ---同时要把tableInfoBean传递过来
//					Intent intent = new Intent(ChooseTableActivity.this, TableInfoActivity.class);
//					intent.putExtra("tableInfoBean", tableInfoBean);
//					intent.putExtra("waiterId", waiterId);
//					intent.putExtra("WaiterAccount", waiterAccount);
//					startActivity(intent);
//					break;
//				case 2:
//					//state==2 说明 ---已开桌---跳转到点餐状态界面OrderStateActivity
//					Intent intent2 = new Intent(ChooseTableActivity.this, OrderStateActivity.class);
//					
//					//根据桌号获得 该桌所对应的 未付款的  OrderBean对象 ，（同一个桌子可能有很多订单，但只会有一个未付款订单
//					int tableInfoId = tableInfoBean.getId();//表T_ORDER的外键TABLE_INFO_ID与表T_TABLE_INFO的ID字段关联
//					//得到表T_ORDER的TABLE_INFO_ID字段后，再配合是否付款ISPAY字段(未付款是10，这个本来应该通过查询数据库获得），就可以确定是哪个订单		
//					OrderBean orderBean = OrderDao.getInstance(ChooseTableActivity.this).getOrderBean(tableInfoId, 10);
//					
//					intent2.putExtra("OrderBean", orderBean);
//					intent2.putExtra("TableInfoBean", tableInfoBean);
//					startActivity(intent2);
//
//				default:
//					break;
//				}
//				
//			}
//		});
//		
//		btnOff = (Button) findViewById(R.id.btn_off);
//		btnOff.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				Intent intent = new Intent(ChooseTableActivity.this, LoginActivity.class);
//				startActivity(intent);
//				
//			}
//		});
//	}
//
//
//
//	/**
//	 *  函数名称 : getTableInfo
//	 *  功能描述 : 为数据源初始化数据 
//	 *  参数及返回值说明：
//	 *  	@return
//	 *
//	 *  修改记录：
//	 *  	日期 ：2015年10月16日 下午3:57:48	修改人：hxf
//	 *  	描述	：
//	 * 					
//	 */
//	private List<TableInfoBean> getTableInfo() {
//		tableInfoList = TableInfoDao.getInstance(this).loadTableInfoBean();
//		return tableInfoList;
//	}
//
//}
