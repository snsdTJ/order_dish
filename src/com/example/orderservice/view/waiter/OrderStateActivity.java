/************************************************************
 *	版权所有  (c)2011,   hxf<p>	
 *  文件名称	：OrderStateActivity.java<p>
 *
 *  创建时间	：2015年10月21日 下午2:49:28 
 *  当前版本号：v1.0
 ************************************************************/
package com.example.orderservice.view.waiter;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.example.orderservice.R;
import com.example.orderservice.adapter.ListViewOrdeStateAdapter;
import com.example.orderservice.dao.OrderDao;
import com.example.orderservice.dao.OrderDetailDao;
import com.example.orderservice.dao.TableInfoDao;
import com.example.orderservice.model.OrderBean;
import com.example.orderservice.model.OrderDetailBean;
import com.example.orderservice.model.TableInfoBean;
import com.example.orderservice.view.BaseActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;

import android.widget.Button;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.TextView;

/************************************************************
 * 内容摘要 ：
 * <p>
 *
 * 作者 ：hxf 创建时间 ：2015年10月21日 下午2:49:28 当前版本号：v1.0 历史记录 : 日期 : 2015年10月21日
 * 下午2:49:28 修改人： 描述 :
 ************************************************************/
public class OrderStateActivity extends BaseActivity {

	// private TextView tvWaiterInfo;
	private TextView tvTableInfo;
	private Button btnPay;
	private Button btnOrderMore;
	private Button btnOff;

	private ListView listView;
	private List<OrderDetailBean> orderDetailBeanList = new ArrayList<OrderDetailBean>();
	private ListViewOrdeStateAdapter listViewAdapter;

	// 用于计算消费总额
	private float totalPay;

	// 当前桌号
	private int tableNo;
	// 当前桌子对象
	private TableInfoBean tableInfoBean;

	// 当前订单
	private OrderBean orderBean;

	// 监听器Listenners
	private Button.OnClickListener btnOnClickListener;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_order_state);

		// 获取从Intent传递过来的数据。
		getDataFromIntent();

		// 初始化布局组件
		initComponent();

		// 初始化监听器s --- Listeners
		initListeners();

		// 设置监听器
		registerListener();

	}

	/**
	 * 函数名称 : getDataFromIntent 功能描述 : 参数及返回值说明：
	 * 
	 */
	private void getDataFromIntent() {
		// TODO Auto-generated method stub
		Intent intent = getIntent();
		orderBean = (OrderBean) intent.getSerializableExtra("OrderBean");// 当前订单
		tableInfoBean = (TableInfoBean) intent.getSerializableExtra("TableInfoBean");
	}

	/**
	 * 函数名称 : initComponent 功能描述 : 参数及返回值说明：
	 *
	 */
	private void initComponent() {

		// tvWaiterInfo = (TextView) findViewById(R.id.tv_waiter_info);
		tvTableInfo = (TextView) findViewById(R.id.tv_table_info);
		listView = (ListView) findViewById(R.id.list_view);
		btnPay = (Button) findViewById(R.id.btn_pay);
		btnOrderMore = (Button) findViewById(R.id.btn_order_more);
		btnOff = (Button) findViewById(R.id.btn_off);

		tableNo = orderBean.getTableNo();// 桌号
		String beginDateTime = orderBean.getBeginDateTime();// 订单开始时间
		tvTableInfo.setText(tableNo + "号桌： 开桌时间 " + beginDateTime);

		/*
		 * 初始化ListView
		 */
		int orderId = orderBean.getId();// T_ORDER_DETAIL表的ORDER_ID外键关联T_ORDER表的ID字段
		getOrderDetailBeanList(orderId);// 初始化ListView的数据源
		listViewAdapter = new ListViewOrdeStateAdapter(this, R.layout.order_state_listview_item, orderDetailBeanList);
		listView.setAdapter(listViewAdapter);

	}

	/**
	 * 函数名称 : initListeners 功能描述 : 参数及返回值说明：
	 *
	 * 
	 */
	private void initListeners() {
		
		//初始化按钮监听器OnClickListener
		btnOnClickListener = new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				switch (v.getId()) {
				case R.id.btn_order_more:
					//跳转到OrderDishActivity页面
					Intent intent = new Intent(OrderStateActivity.this, OrderDishActivity.class);
					intent.putExtra("OrderDetailBeanList", (Serializable) orderDetailBeanList);
					intent.putExtra("TableInfoBean", tableInfoBean);
					startActivity(intent);
					break;
				case R.id.btn_pay:
					// 付款对话框
					showPayDailog();
					break;
				case R.id.btn_off:
					//跳转到WaiterMainActivity页面
					Intent intent2 = new Intent(OrderStateActivity.this, WaiterMainActivity.class);
					startActivity(intent2);
					break;

				default:
					break;
				}

			}

		};
	}

	/**
	 * 函数名称 : registerListener 功能描述 : 参数及返回值说明：
	 *
	 * 
	 */
	private void registerListener() {
		
		// TODO Auto-generated method stub
		btnOrderMore.setBackgroundResource(0);// 去掉按钮背景色
		btnOrderMore.setOnClickListener(btnOnClickListener);

		btnOff.setBackgroundResource(0);// 去掉按钮背景色
		btnOff.setOnClickListener(btnOnClickListener);

		btnPay.setBackgroundResource(0);// 去掉按钮背景色
		btnPay.setOnClickListener(btnOnClickListener);

	}

	/**
	 * 函数名称 : showPayDailog 功能描述 :
	 * 付款对话框,点付款按钮可以执行付款结帐，并更新数据库中该桌状态设为可用，和该订单状态为已付款。 参数及返回值说明：
	 *
	 * 修改记录： 日期 ：2015年10月22日 上午10:45:13 修改人：hxf 描述 ：
	 * 
	 */
	protected void showPayDailog() {
		// 这里使用自定义布局对话框，所以要生成自定义布局视图(这里并不是把整个对话框设置为该布局，只是把该布局视图作为视图加入对话框

		// LayoutInflater layoutInflater =
		// LayoutInflater.from(this);//如果该视图除了ListView控件还有其它控件的话，就需要采用这种方式
		// View view = layoutInflater.inflate(int resource, ViewGroup
		// root);//resource 布局文件需要含有ListView控件
		// ListView dialogListView =
		// view.findViewById(R.id.list_view);//ListView的内部item布局是由adpter控制的

		ListView dialogListView = new ListView(this);// 由于我要加的视图只需要有ListView控件，
														// 所以直接new 一个就可以了
		dialogListView.setBackgroundColor(Color.parseColor("#3e3e3e"));

		// 由于这个dialogListView的数据源和适配器与OrderStateActivity页面的ListView一模一样，所以可以直接使用
		dialogListView.setAdapter(listViewAdapter);

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("账单详情");
		builder.setView(dialogListView);
		builder.setMessage("合计 ： " + totalPay);
		builder.setPositiveButton("付款", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {

				// 把桌子状态改为未开桌, 其实第二个参数state不应该直接传1，而是传
				// “未开桌”，然后在modifyTableInfo（）方法里面处理（查表）
				TableInfoDao.getInstance(OrderStateActivity.this).modifyTableInfo(tableNo, 1);

				// 更新订单信息：收银员id，结束时间，总金额，付款状态
				orderBean.setCashierId(3);// 我不知道如何获取到收银员的id？？？？
				Date date = new Date();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String endDateTime = sdf.format(date);
				orderBean.setEndDateTime(endDateTime);
				orderBean.setTotalMoney(totalPay);
				orderBean.setIsPay(11);// 其实应通过“已付款”去查表来设置给ISPAY字段
				OrderDao.getInstance(OrderStateActivity.this).modifyOrder(orderBean);

				// 结帐后要跳转到ChooseTableActivity界面
				Intent intent = new Intent(OrderStateActivity.this, WaiterMainActivity.class);
				startActivity(intent);

			}
		});
		builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		AlertDialog alertDialog = builder.create();

		alertDialog.show();

	}

	/**
	 * 函数名称 : getOrderDetailBeanList 功能描述 : 为Listiew 提供数据源 参数及返回值说明：
	 * 
	 * @param orderId
	 *
	 *            修改记录： 日期 ：2015年10月21日 下午9:19:52 修改人：hxf 描述 ：
	 * 
	 */
	private void getOrderDetailBeanList(int orderId) {

		// 获取T_ORDER_DETAIL表中所有OrderDetail对象
		List<OrderDetailBean> odbList = OrderDetailDao.getInstance(this).loadOrderDetailBean();
		// 从上面集合中取出指定orderId的OrderDetail对象，并存储到为ListView提供数据的集合中
		for (OrderDetailBean odb : odbList) {
			if (odb.getOrderId() == orderId) {
				orderDetailBeanList.add(odb);
				totalPay += (odb.getCurrentPrice() * odb.getDishNumber());
			}
		}
	}
}
