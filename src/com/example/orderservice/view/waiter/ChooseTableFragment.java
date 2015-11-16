/************************************************************
 *	版权所有  (c)2011,   hxf<p>	
 *  文件名称	：ChooseTableFragment.java<p>
 *
 *  创建时间	：2015年10月26日 下午4:30:13 
 *  当前版本号：v1.0
 ************************************************************/
package com.example.orderservice.view.waiter;

import java.util.List;

import com.example.orderservice.R;
import com.example.orderservice.adapter.ChooseTableAdapter;
import com.example.orderservice.dao.OrderDao;
import com.example.orderservice.dao.TableInfoDao;
import com.example.orderservice.model.OrderBean;
import com.example.orderservice.model.TableInfoBean;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

/************************************************************
 * 内容摘要 ：
 * <p>
 *
 * 作者 ：hxf 创建时间 ：2015年10月26日 下午4:30:13 当前版本号：v1.0 历史记录 : 日期 : 2015年10月26日
 * 下午4:30:13 修改人： 描述 :
 ************************************************************/
public class ChooseTableFragment extends Fragment {

	private TextView textView;

	private GridView gridView;

	private List<TableInfoBean> tableInfoList;

	private ChooseTableAdapter adapter;
	
	//通过Intent传递过来的数据
	private int waiterId;
	private String waiterAccount;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		View view = inflater.inflate(R.layout.fragment_choose_table, container, false);
		
		// 初始化通过Intent传递过来数据
		Intent intent = getActivity().getIntent();
		waiterId = intent.getIntExtra("waiterId", 0);
		waiterAccount = intent.getStringExtra("waiterAccount");

		// 初始化布局组件
		initComponent(view);

		// 设置监听器
		registerListener();

		return view;
	}

	/**
	 *  函数名称 : initComponent
	 *  功能描述 :  
	 *  参数及返回值说明： 					
	 */
	private void initComponent(View view) {

		textView = (TextView) view.findViewById(R.id.text_view);
		gridView = (GridView) view.findViewById(R.id.grid_view);

		textView.setText("服务员编号： " + waiterAccount);
		
		//初始化GridView的数据源
		tableInfoList = getTableInfo();

		adapter = new ChooseTableAdapter(getActivity(), R.layout.table_item, tableInfoList);

		// 获取手机屏幕信息，并获取屏幕的宽度
		Display display = getActivity().getWindow().getWindowManager().getDefaultDisplay();
		DisplayMetrics outMetrics = new DisplayMetrics();// 该对象用于存放屏幕信息，如大小等值
		display.getMetrics(outMetrics);// display这个屏幕调用这个方法就可以给outMetrics赋display这个屏幕的值
		int screenWidth = outMetrics.widthPixels;
		if (screenWidth >= 700) {
			gridView.setNumColumns(3);
		} else {
			gridView.setNumColumns(2);
		}

		gridView.setAdapter(adapter);
		
	}

	/**
	 *  函数名称 : registerListener
	 *  功能描述 :  
	 *  参数及返回值说明：
	 *
	 *  修改记录：
	 *  	日期 ：2015年11月9日 下午6:23:02	修改人：hxf
	 *  	描述	：
	 * 					
	 */
	private void registerListener() {
		
		gridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				TableInfoBean tableInfoBean = tableInfoList.get(position);
				//state代表开桌状态，state==1说明 ---未开桌， // state==2 说明 ---已开桌
				int state = tableInfoBean.getState();
				Log.i("tag", String.valueOf(state));
				
				switch (state) {
				case 1:
					// state==1说明 ---未开桌---跳转到开桌界面 ---同时要把tableInfoBean传递过来
					Intent intent = new Intent(getActivity(), TableInfoActivity.class);
					intent.putExtra("tableInfoBean", tableInfoBean);
					intent.putExtra("waiterId", waiterId);
					intent.putExtra("WaiterAccount", waiterAccount);
					startActivity(intent);
					break;
				case 2:
					// state==2 说明 ---已开桌---跳转到点餐状态界面OrderStateActivity
					Intent intent2 = new Intent(getActivity(), OrderStateActivity.class);

					// 根据桌号获得 该桌所对应的 未付款的 OrderBean对象 ，（同一个桌子可能有很多订单，但只会有一个未付款订单
					int tableInfoId = tableInfoBean.getId();// 表T_ORDER的外键TABLE_INFO_ID与表T_TABLE_INFO的ID字段关联
					// 得到表T_ORDER的TABLE_INFO_ID字段后，再配合是否付款ISPAY字段(未付款是10，这个本来应该通过查询数据库获得），就可以确定是哪个订单
					OrderBean orderBean = OrderDao.getInstance(getActivity()).getOrderBean(tableInfoId, 10);

					intent2.putExtra("OrderBean", orderBean);
					intent2.putExtra("TableInfoBean", tableInfoBean);
					startActivity(intent2);

				default:
					break;
				}
			}
		});		
	}

	/**
	 * 函数名称 : getTableInfo 功能描述 : 为数据源初始化数据 参数及返回值说明：
	 * 
	 * @return
	 *
	 * 		修改记录： 日期 ：2015年10月16日 下午3:57:48 修改人：hxf 描述 ：
	 * 
	 */
	private List<TableInfoBean> getTableInfo() {
		tableInfoList = TableInfoDao.getInstance(getActivity()).loadTableInfoBean();
		return tableInfoList;
	}

}
