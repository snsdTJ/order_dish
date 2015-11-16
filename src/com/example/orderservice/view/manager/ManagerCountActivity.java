/************************************************************
 *	版权所有  (c)2011,   hxf<p>	
 *  文件名称	：ManagerCountActivity.java<p>
 *
 *  创建时间	：2015年10月24日 下午3:24:36 
 *  当前版本号：v1.0
 ************************************************************/
package com.example.orderservice.view.manager;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.example.orderservice.R;
import com.example.orderservice.adapter.TurnoverAdapter;
import com.example.orderservice.dao.OrderDao;
import com.example.orderservice.model.ManagerCountActivityBean;
import com.example.orderservice.model.OrderBean;
import com.example.orderservice.view.BaseActivity;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.TextUtils.TruncateAt;
import android.util.Log;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/************************************************************
 * 内容摘要 ：
 * <p>
 *
 * 作者 ：hxf 创建时间 ：2015年10月24日 下午3:24:36 当前版本号：v1.0 历史记录 : 日期 : 2015年10月24日
 * 下午3:24:36 修改人： 描述 :
 ************************************************************/
public class ManagerCountActivity extends BaseActivity {

	private EditText etBeginDate;
	private EditText etEndDate;
	private ListView listView;
	private Button btnReturn, btnQuery;

	private int year;
	private int monthOfYear;
	private int dayOfMonth;

	// ListView的数据源和适配器
	private List<OrderBean> orderBeanList = new ArrayList<OrderBean>();
	private TurnoverAdapter turnoverAdapter;

	// 营业总额
	private TextView tvSumTurnover;
	private float sumTurnover;

	// Listeners
	private OnClickListener onClickListener;
	
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 去掉标题栏
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_manager_count);

		// 初始化布局组件
		initComponent();

		// 初始化监听器s --- Listeners
		initListeners();

		// 设置监听器
		registerListener();

		// 用于初始化日期对话框的初始时间
		initDatePickerDialogTime();
	}

	/**
	 * 函数名称 : initComponent 功能描述 : 参数及返回值说明：
	 *
	 * 修改记录： 日期 ：2015年10月30日 下午5:32:16 修改人：hxf 描述 ：
	 * 
	 */
	private void initComponent() {
		// TODO Auto-generated method stub
		etBeginDate = (EditText) findViewById(R.id.et_begin_date);
		etEndDate = (EditText) findViewById(R.id.et_end_date);
		listView = (ListView) findViewById(R.id.list_view);
		btnReturn = (Button) findViewById(R.id.btn_return);
		btnQuery = (Button) findViewById(R.id.btn_query);

		/*
		 *  初始化ListView
		 */
		turnoverAdapter = new TurnoverAdapter(ManagerCountActivity.this, R.layout.item_turnover_lv, orderBeanList);
		// 自定义ListView的第一项显示的布局和内容listView.addHeaderView(v)
		View v = LayoutInflater.from(ManagerCountActivity.this).inflate(R.layout.item_turnover_lv, null);
		TextView tvCashierName = (TextView) v.findViewById(R.id.tv_cashier_name);
		TextView tvTurnover = (TextView) v.findViewById(R.id.tv_turnover);
		TextView tvDate = (TextView) v.findViewById(R.id.tv_date);
		tvCashierName.setText("收银员");
		tvTurnover.setText("营收");
		tvDate.setText("时间");
		listView.addHeaderView(v);

		listView.setAdapter(turnoverAdapter);

		// 创建一个TextView控件用于显示所选出的订单的营业总额，如果直接在布局里加在ListView之下的，会造成TextView和
		// 上面订单之间空很大
		tvSumTurnover = new TextView(ManagerCountActivity.this);
		tvSumTurnover.setText("营业总额： " + sumTurnover);
		tvSumTurnover.setPadding(10, 5, 20, 5);
		tvSumTurnover.setTextSize(24);
		tvSumTurnover.setGravity(Gravity.RIGHT);
		listView.addFooterView(tvSumTurnover);
	}

	/**
	 * 函数名称 : initListeners 功能描述 : 参数及返回值说明：
	 *
	 * 修改记录： 日期 ：2015年11月9日 下午5:32:14 修改人：hxf 描述 ：
	 * 
	 */
	private void initListeners() {
		// TODO Auto-generated method stub
		onClickListener = new OnClickListener() {

			@Override
			public void onClick(View v) {
				switch (v.getId()) {
				case R.id.et_begin_date:
					// 获取开始日期
					getBeginDate();
					break;
				case R.id.et_end_date:
					// 获取结束日期
					getEndDate();
					break;
				case R.id.btn_query:
					//查询订单并计算总营业额
					queryOrders();					
					break;
				case R.id.btn_return:
					// 结束页面
					finish();
					break;

				default:
					break;
				}
			}
		};
	}

	/**
	 *  函数名称 : queryOrders
	 *  功能描述 :  
	 *  参数及返回值说明：
	 *
	 *  修改记录：
	 *  	日期 ：2015年10月30日 下午6:08:09	修改人：hxf
	 *  	描述	：
	 * 					
	 */
	protected void queryOrders() {
		// 先清空之前查到的数据，要不然这次点击即使不成功，还会把上次的数据显示出来
		sumTurnover = 0;

		String beginDate = etBeginDate.getText().toString();
		String endDate = etEndDate.getText().toString();

		getOrderBeanList(beginDate, endDate);

		turnoverAdapter.notifyDataSetChanged();

		tvSumTurnover.setText("营业总额： " + sumTurnover);
	}

	/**
	 * 函数名称 : getEndDate 功能描述 : 参数及返回值说明：
	 * 
	 * @param etEndDate2
	 *
	 *            修改记录： 日期 ：2015年10月30日 下午5:48:49 修改人：hxf 描述 ：
	 * 
	 */
	protected void getEndDate() {
		// TODO Auto-generated method stub
		new DatePickerDialog(ManagerCountActivity.this, new OnDateSetListener() {

			@Override
			public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
				String month = String.valueOf(monthOfYear + 1);
				String day = String.valueOf(dayOfMonth);

				if (dayOfMonth >= 1 && dayOfMonth <= 9) {
					day = "0" + day;
				}
				if (monthOfYear >= 0 && monthOfYear <= 8) {
					month = "0" + month;
				}
				etEndDate.setText(year + "-" + month + "-" + day);

			}
		}, year, monthOfYear, dayOfMonth).show();
	}

	/**
	 * 函数名称 : getBeginDate 功能描述 : 参数及返回值说明：
	 * 
	 * @param etBeginDate2
	 *
	 *            修改记录： 日期 ：2015年10月30日 下午5:48:49 修改人：hxf 描述 ：
	 * 
	 */
	protected void getBeginDate() {
		// TODO Auto-generated method stub
		// 使用DatePickerDialog对话框，这里使用匿名内部类方式生成一个日期对话框
		new DatePickerDialog(ManagerCountActivity.this, new OnDateSetListener() {

			@Override
			public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

				String month = String.valueOf(monthOfYear + 1);
				String day = String.valueOf(dayOfMonth);

				if (dayOfMonth >= 1 && dayOfMonth <= 9) {
					day = "0" + day;
				}
				if (monthOfYear >= 0 && monthOfYear <= 8) {
					month = "0" + month;
				}
				etBeginDate.setText(year + "-" + month + "-" + day);

			}
		}, year, monthOfYear, dayOfMonth).show();// 后三个参数是日期对话框初始化时设置的时间，最后一定要调用show（）方法
	}

	/**
	 * 函数名称 : registerListener 功能描述 : 参数及返回值说明：
	 *
	 * 修改记录： 日期 ：2015年10月30日 下午5:40:29 修改人：hxf 描述 ：
	 * 
	 */
	private void registerListener() {

		etBeginDate.setOnClickListener(onClickListener);

		etEndDate.setOnClickListener(onClickListener);
		
		// 查询按钮，查询制定日期订单，以每个订单的付款日期时间为准
		btnQuery.setOnClickListener(onClickListener);

		btnReturn.setOnClickListener(onClickListener);
	}

	/**
	 * 函数名称 : getOrderBeanList 功能描述 : 参数及返回值说明：
	 * 
	 * @return
	 *
	 * 		修改记录： 日期 ：2015年10月30日 上午11:55:36 修改人：hxf 描述 ：
	 * 
	 */
	private List<OrderBean> getOrderBeanList(String beginDate, String endDate) {

		orderBeanList.clear();

		if (!TextUtils.isEmpty(beginDate) && !TextUtils.isEmpty(endDate)) {

			ManagerCountActivityBean mcaBean = new ManagerCountActivityBean();

			boolean setOrderBeanList = mcaBean.setOrderBeanList(beginDate, endDate + " 23:59:59", ManagerCountActivity.this);
			boolean setSumTurnover = mcaBean.setSumTurnover();
			Log.i("Boolean", setOrderBeanList + " " + setSumTurnover);
			if (setOrderBeanList && setSumTurnover) {
				// adapter接收的数据源是指向堆内存，也就是new出来的那个地址值，
				// 所以不能用orderBeanList =
				// mcaBean.getOrderBeanList(),因为这样相当于orderBeanList指向了另一堆内存中的集合
				// 而adapter中数据源还只之前private List<OrderBean> orderBeanList = new
				// ArrayList<OrderBean>();这个地址，
				// 也就是说这时orderBeanList指向了新集合， 但是adapter的数据源还是之前的这个new
				// ArrayList<OrderBean>()，
				// 所以adapter的数据源没有变化，那么用turnoverAdapter.notifyDataSetChanged();就不起作用了
				orderBeanList.addAll(mcaBean.getOrderBeanList());

				sumTurnover = mcaBean.getSumTurnover();

			} else {
				Toast toastWrong = new Toast(ManagerCountActivity.this);
				toastWrong.setGravity(Gravity.CENTER, 0, 0);
				ImageView imageView = new ImageView(ManagerCountActivity.this);
				imageView.setImageResource(R.drawable.ic_launcher);
				LinearLayout ll = new LinearLayout(ManagerCountActivity.this);
				ll.setBackgroundColor(Color.parseColor("#000000"));
				ll.setPadding(10, 5, 10, 5);
				ll.addView(imageView);
				TextView textView = new TextView(ManagerCountActivity.this);
				textView.setText("这个时间段没有订单，请重新输入日期");
				textView.setTextColor(Color.WHITE);
				textView.setTextSize(22);
				textView.setEllipsize(TruncateAt.END);
				textView.setGravity(Gravity.CENTER_VERTICAL);
				ll.addView(textView);
				ll.setGravity(Gravity.CENTER_VERTICAL);
				toastWrong.setView(ll);
				toastWrong.show();
				// Toast.makeText(ManagerCountActivity.this,
				// "这个时间段没有订单，请重新输入日期", Toast.LENGTH_SHORT).show();
			}
		} else {
			Toast toast = Toast.makeText(ManagerCountActivity.this, "您还未输入要查询的日期", Toast.LENGTH_SHORT);
			toast.setGravity(Gravity.CENTER, 0, 0);
			toast.show();
		}

		return orderBeanList;
	}

	/**
	 * 函数名称 : initDatePickerDialogTime 功能描述 : 参数及返回值说明：
	 *
	 * 修改记录： 日期 ：2015年10月30日 上午8:59:07 修改人：hxf 描述 ：
	 *
	 */
	private void initDatePickerDialogTime() {
		Calendar calendar = Calendar.getInstance();
		year = calendar.get(Calendar.YEAR);
		monthOfYear = calendar.get(Calendar.MONTH);
		dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
	}

}
