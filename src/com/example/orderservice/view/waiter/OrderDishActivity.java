/************************************************************
 *	版权所有  (c)2011,   hxf<p>	
 *  文件名称	：OrderDishActivity.java<p>
 *
 *  创建时间	：2015年10月17日 上午10:43:12 
 *  当前版本号：v1.0
 ************************************************************/
package com.example.orderservice.view.waiter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ListIterator;

import com.example.orderservice.R;
import com.example.orderservice.adapter.ListViewOrderDishAdapter;
import com.example.orderservice.adapter.OrderDishAdapter;
import com.example.orderservice.adapter.SpinnerDishTypeAdapter;
import com.example.orderservice.control.OrderDishOnItemClickListener;
import com.example.orderservice.dao.DictionaryDao;
import com.example.orderservice.dao.DishInfoDao;
import com.example.orderservice.dao.OrderDao;
import com.example.orderservice.dao.OrderDetailDao;
import com.example.orderservice.dao.TableInfoDao;
import com.example.orderservice.model.DictionaryBean;
import com.example.orderservice.model.DishInfoBean;
import com.example.orderservice.model.OrderBean;
import com.example.orderservice.model.OrderDetailBean;
import com.example.orderservice.model.TableInfoBean;
import com.example.orderservice.view.BaseActivity;

import android.R.anim;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

/************************************************************
 * 内容摘要 ：
 * <p>
 *
 * 作者 ：hxf 创建时间 ：2015年10月17日 上午10:43:12 当前版本号：v1.0 历史记录 : 日期 : 2015年10月17日
 * 上午10:43:12 修改人： 描述 :
 ************************************************************/
public class OrderDishActivity extends BaseActivity {

	// private TextView tvWaiterInfo;

	private TextView tvTableInfo;

	private Spinner spinnerDishType;

	private GridView gridView;

	private ListView listView;

	private Button btnOk;
	private Button btnCancel;

	// 当前桌子实体类
	private TableInfoBean tableInfoBean;

	// 当前桌子为付款的订单
	private OrderBean orderBean;

	// 用于区分该桌是否已经点菜了，在点击取消按钮时就要判断该参数，如果已点菜的就弹出对话框提示一下，并询问是否确定取消订单并退出，
	// 这么做主要是防止点过餐的，结果误点取消按钮，尤其是从OrderStateActivity页面返回来继续点餐时，
	private boolean ifOrdered;

	// Spinner
	private List<DictionaryBean> dictionaryBeanList = new ArrayList<DictionaryBean>();
	private SpinnerDishTypeAdapter spinnerDishTypeAdapter;

	// GridView
	private List<DishInfoBean> dishInfoBeanList = new ArrayList<DishInfoBean>();
	private OrderDishAdapter orderDishAdapter;

	// 用于获取Spinner中选中的菜品，然后传递给GridView中展示所以该菜品所对应的菜单
	// 默认显示闽菜, 所以默认dishType=3
	private int dishType = 3;

	// ListView
	private List<OrderDetailBean> orderDetailBeanList = new ArrayList<OrderDetailBean>();
	private ListViewOrderDishAdapter listViewOrderDishAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_order_dish);

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
	 * 
	 */
	private void getDataFromIntent() {
		Intent intent = getIntent();
		tableInfoBean = (TableInfoBean) intent.getSerializableExtra("TableInfoBean");
		if (intent.hasExtra("OrderDetailBeanList")) {// intent的hasExtra(键)方法用于判断该intent中是否有插入该键的键值对
			// 有OrderDetailBeanList说明是从OrderStateActivity跳转过来的，就需要把为ListView提供数据orderDetailBeanList集合初始化
			orderDetailBeanList = (List<OrderDetailBean>) intent.getSerializableExtra("OrderDetailBeanList");
		}

		// 根据桌号获得 该桌所对应的 未付款的 OrderBean对象 ，（同一个桌子可能有很多订单，但只会有一个未付款订单
		int tableId = tableInfoBean.getId();
		int tableInfoId = tableId;// 表T_ORDER的外键TABLE_INFO_ID与表T_TABLE_INFO的ID字段关联
		// 得到表T_ORDER的TABLE_INFO_ID字段后，再配合是否付款ISPAY字段(未付款是10，这个本来应该通过查询数据库获得），就可以确定是哪个订单
		orderBean = OrderDao.getInstance(this).getOrderBean(tableInfoId, 10);
	}

	/**
	 * 函数名称 : initComponent 功能描述 : 参数及返回值说明：
	 *
	 * 
	 */
	private void initComponent() {

		// tvWaiterInfo = (TextView) findViewById(R.id.tv_waiter_info);
		tvTableInfo = (TextView) findViewById(R.id.tv_table_info);
		spinnerDishType = (Spinner) findViewById(R.id.spinner_dish_type);
		gridView = (GridView) findViewById(R.id.grid_view);
		listView = (ListView) findViewById(R.id.list_view);
		btnOk = (Button) findViewById(R.id.btn_ok);
		btnCancel = (Button) findViewById(R.id.btn_cancel);

		tvTableInfo.setText(tableInfoBean.getNo() + "号桌开桌时间：" + orderBean.getBeginDateTime());

		/*
		 * Spinner用来选择菜系（闽菜，川菜。。。
		 */
		dictionaryBeanList = getDictionaryBean();
		spinnerDishTypeAdapter = new SpinnerDishTypeAdapter(this, R.layout.spinner_item, dictionaryBeanList);
		// 注意R.layout.spinner_item这个根标签必须是SpinnerDishTypeAdapter中使用的那个控件
		spinnerDishTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnerDishType.setAdapter(spinnerDishTypeAdapter);

		/*
		 * GridView用来展示菜单
		 */
		getDishInfoBean();
		orderDishAdapter = new OrderDishAdapter(this, R.layout.dish_grid_view_item, dishInfoBeanList);
		gridView.setAdapter(orderDishAdapter);

		/*
		 * ListView用来展示所点的菜
		 */
		listViewOrderDishAdapter = new ListViewOrderDishAdapter(this, R.layout.order_dish_listview_item,
				orderDetailBeanList);
		TextView tvHeaderView = new TextView(this);
		tvHeaderView.setText("订单详情");
		tvHeaderView.setGravity(Gravity.CENTER_HORIZONTAL);
		tvHeaderView.setTextSize(22);
		tvHeaderView.setPadding(0, 0, 0, 15);
		tvHeaderView.setTextColor(Color.parseColor("#ffffff"));
		listView.addHeaderView(tvHeaderView);
		listView.setAdapter(listViewOrderDishAdapter);
	}

	/**
	 * 函数名称 : initListeners 功能描述 : 参数及返回值说明：
	 *
	 * 
	 */
	private void initListeners() {
		// TODO Auto-generated method stub

	}

	/**
	 * 函数名称 : registerListener 功能描述 : 参数及返回值说明：
	 *
	 * 修改记录： 日期 ：2015年11月9日 下午7:29:53 修改人：hxf 描述 ：
	 * 
	 */
	private void registerListener() {
		spinnerDishType.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				// 通过adapter去获取实例
				DictionaryBean dictionaryBean = spinnerDishTypeAdapter.getItem(position);

				dishType = dictionaryBean.getId();
				getDishInfoBean();
				orderDishAdapter.notifyDataSetChanged();
			}
			
			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
			}
		});

		gridView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				DishInfoBean dishInfoBean = dishInfoBeanList.get(position);

				OrderDetailBean orderDetailBean = new OrderDetailBean();
				int orderId = orderBean.getId();
				Log.i("orderId", String.valueOf(orderId));
				orderDetailBean.setOrderId(orderId);
				orderDetailBean.setDishInfoId(dishInfoBean.getId());
				orderDetailBean.setDishName(dishInfoBean.getName());// 之前忘记传这个了，所以在ListView中菜名显示不出来
				orderDetailBean.setCurrentPrice(dishInfoBean.getPrice());
				orderDetailBean.setDishNumber(1);

				getOrderDetailBean(orderDetailBean);
				listViewOrderDishAdapter.notifyDataSetChanged();
			}
		});

		// btnOk.setBackgroundResource(0);
		// 点击确认按钮跳转到OrderStateActivity页面
		// 需要传递 服务员名字，桌号，开桌时间，订单号，所点的菜，但其实只要传递订单号就可以，因为其它信息都可以根据订单号从订单表中取出
		// 更新T_ORDER_DETAIL表，把所点的菜存储到数据库,即把orderDetailBeanList集合中OrderDetailBean实例存储到数据库
		btnOk.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// 之前是通过下面代码直接整个ListView的数据都直接插入T_ORDER_DETAIL表中，这样就会造成如果是从OrderStateActivity页面
				// 返回的，需要进行加菜 或
				// 减菜时，由于在点菜时已经把点完的菜存入数据库，而且从OrderStateActivity跳转过来时，该界面已经把
				// 点完的菜加载进ListView的orderDetailBeanList集合中，这就造成数据的重复存储进数据库
				// for(OrderDetailBean odb:orderDetailBeanList){
				// OrderDetailDao.getInstance(OrderDishActivity.this).saveOrderDetailBean(odb);
				// }

				// 应该采用每次把点的菜存进数据库时，都先把该订单所对应的菜删掉，然后再把现在点的菜的情况存进数据库（当然前提是，打开
				// 这个活动页时要把数据库中该订单所对应的菜都读取出来放到ListView的数据源里，点新菜，增加菜或者减菜都要在该数据源里改变
				int orderId = orderBean.getId();
				OrderDetailDao.getInstance(OrderDishActivity.this).deleteOrderDetailBean(orderId);

				for (OrderDetailBean odb : orderDetailBeanList) {
					OrderDetailDao.getInstance(OrderDishActivity.this).saveOrderDetailBean(odb);
				}

				Intent intent = new Intent(OrderDishActivity.this, OrderStateActivity.class);
				intent.putExtra("OrderBean", orderBean);
				intent.putExtra("TableInfoBean", tableInfoBean);
				startActivity(intent);
			}
		});

		// btnCancel.setBackgroundResource(0);
		btnCancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (!orderDetailBeanList.isEmpty()) {
					ifOrdered = true;
				}
				if (ifOrdered) {
					// 弹出提示对话框，询问该用户已经点菜了，是否真的要取消所有菜，并取消订单，停止开桌
					showAlertDialog();
				} else {
					// 把该桌的未付款的那个订单从数据库删除
					OrderDao.getInstance(OrderDishActivity.this).deleteOrder(orderBean);
					// 把桌子状态改为 未开桌 状态
					tableInfoBean.setState(1);
					TableInfoDao.getInstance(OrderDishActivity.this).modifyTableInfo(tableInfoBean);
					//跳转到WaiterMainActivity页面
					Intent intent = new Intent(OrderDishActivity.this, WaiterMainActivity.class);
					startActivity(intent);
				}
			}
		});

	}

	/**
	 *  函数名称 : showAlertDialog
	 *  功能描述 :  弹出提示对话框，询问该用户已经点菜了，是否真的要取消所有菜，并取消订单，停止开桌
	 *  参数及返回值说明： 					
	 */
	protected void showAlertDialog() {
		
		AlertDialog.Builder builder = new AlertDialog.Builder(OrderDishActivity.this);
		// 加载自定义布局
		LinearLayout ll = (LinearLayout) LayoutInflater.from(OrderDishActivity.this)
				.inflate(R.layout.alert_dialog_important_info, null);
		Button btnSubmit = (Button) ll.findViewById(R.id.btn_submit);
		Button btnCancel = (Button) ll.findViewById(R.id.btn_cancel);
		builder.setView(ll);

		final AlertDialog dailog = builder.create();
		btnSubmit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				// 把该桌的未付款的那个订单从数据库删除
				OrderDao.getInstance(OrderDishActivity.this).deleteOrder(orderBean);

				// 注意: 还要把该订单所点的菜从T_ORDER_DETAIL表中删除
				int orderId = orderBean.getId();
				OrderDetailDao.getInstance(OrderDishActivity.this).deleteOrderDetailBean(orderId);

				// 把桌子状态改为 未开桌 状态
				tableInfoBean.setState(1);
				TableInfoDao.getInstance(OrderDishActivity.this).modifyTableInfo(tableInfoBean);

				Intent intent = new Intent(OrderDishActivity.this, WaiterMainActivity.class);
				startActivity(intent);

			}
		});
		btnCancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				dailog.dismiss();
			}
		});
		dailog.show();
		
	}

	/**
	 * 函数名称 : getOrderDetailBean 功能描述 : 这个ListView数据源由点击GridView 和
	 * ListView中的增减按钮来改变数据 由于点击GridView的item时只能增加数据，但是把数据加入集合时要区分是否第一次添加
	 * 可以通过查看OrderDetailBean对象的dishInfoId这个字段来判断 是：把该对象加入数据源
	 * 否：把已存在的集合中的该对象的dishNumber字段加1 参数及返回值说明：
	 * 
	 * @return
	 *
	 * 		修改记录： 日期 ：2015年10月20日 下午9:03:29 修改人：hxf 描述 ：
	 * 
	 */
	private List<OrderDetailBean> getOrderDetailBean(OrderDetailBean orderDetailBean) {
		if (orderDetailBeanList.isEmpty()) {
			orderDetailBeanList.add(orderDetailBean);
		} else {

			// 不能直接用下面这种方法，会出现java.util.ConcurrentModificationException异常，即并发修改异常，
			// 也就是当使用增强for或迭代器时不能边遍历一个集合时边增加，或删除该集合中的元素（但是迭代器可以用迭代器遍历且迭代器修改元素）
			// for(OrderDetailBean odb:orderDetailBeanList){
			// if (orderDetailBean.getDishInfoId() == odb.getDishInfoId()) {
			// odb.setDishNumber(odb.getDishNumber()+1);
			// }else {
			// orderDetailBeanList.add(orderDetailBean);
			// }
			// }

			// //会造成只要取出的orderDetailBean.getDishInfoId() ==
			// odb.getDishInfoId()就往集合中添加元素
			// //这就造成如果集合中不存在dishInfoId相同或者相同的在集合中靠后边，就会重复添加orderDetailBean元素
			// ListIterator<OrderDetailBean> lit =
			// orderDetailBeanList.listIterator();
			// while (lit.hasNext()) {
			// OrderDetailBean odb = lit.next();
			//
			// if (orderDetailBean.getDishInfoId() == odb.getDishInfoId()) {
			// odb.setDishNumber(odb.getDishNumber()+1);
			// }
			// else{
			// lit.add(orderDetailBean);
			// }
			// }

			// 解决方法：用普通for方式 或
			// 用迭代器遍历集合，同时迭代器修改元素方式,而且要用ListIterator，因为Iterator的方法太少，没有增加元素方法
			ListIterator<OrderDetailBean> lit = orderDetailBeanList.listIterator();
			boolean flag = true;// 这个很重要，上面那个就是少了它而出现重复添加
			while (lit.hasNext()) {
				OrderDetailBean odb = lit.next();

				if (orderDetailBean.getDishInfoId() == odb.getDishInfoId()) {
					odb.setDishNumber(odb.getDishNumber() + 1);
					flag = false;
				}
			}
			if (flag) {
				lit.add(orderDetailBean);
			}
		}

		return orderDetailBeanList;
	}

	/**
	 * 函数名称 : getDishInfoBean 功能描述 : 为GridView提供数据初始化 参数及返回值说明：
	 * 
	 * @return
	 *
	 * 		修改记录： 日期 ：2015年10月20日 下午3:40:40 修改人：hxf 描述 ：
	 * 
	 */
	private List<DishInfoBean> getDishInfoBean() {
		// 查询T_DISH_INFO表，获得所有DishInfoBean实例，保存在集合中
		List<DishInfoBean> listAll = DishInfoDao.getInstance(this).loadDishInfoBean();
		// 从上面集合中筛选出指定dishType的DishInfoBean实例，保存在为GridView的提供数据源的那个集合
		// 先把集合中的数据清空，否则每次数据都是追加进来
		dishInfoBeanList.clear();
		// 注意这里不能用新集合保存筛选出的DishInfoBean实例，然后把新集合返回，这样做的话只是产生新集合，而数据源集合里的数据却没变
		for (DishInfoBean difb : listAll) {
			if (difb.getDishType() == dishType) {
				dishInfoBeanList.add(difb);
			}
		}

		return dishInfoBeanList;
	}

	/**
	 * 函数名称 : getDictionaryBean 功能描述 : 为Spinner提供数据初始化 参数及返回值说明：
	 * 
	 * @return
	 *
	 * 		修改记录： 日期 ：2015年10月19日 下午9:21:54 修改人：hxf 描述 ：
	 * 
	 */
	private List<DictionaryBean> getDictionaryBean() {
		// 查询出所有T_DATA_DICTIONARY表中DictionaryBean实例
		List<DictionaryBean> listAll = DictionaryDao.getInstance(this).loadDictionaryBean();

		// 把type字段为2的DictionaryBean提取出来保存在为Spinner提供数据的那个集合，
		// 选中2是因为表中type字段为2的代表菜品
		for (DictionaryBean dictionaryBean : listAll) {
			if (dictionaryBean.getType() == 2) {
				dictionaryBeanList.add(dictionaryBean);
			}
		}
		return dictionaryBeanList;
	}
}
