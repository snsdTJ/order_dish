/************************************************************
 *	版权所有  (c)2011,   hxf<p>	
 *  文件名称	：ChooseTableFragment.java<p>
 *
 *  创建时间	：2015年10月26日 下午4:30:13 
 *  当前版本号：v1.0
 ************************************************************/
package com.example.orderservice.view.waiter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.example.orderservice.R;
import com.example.orderservice.adapter.SearchOrderAdapter;
import com.example.orderservice.adapter.TurnoverAdapter;
import com.example.orderservice.dao.OrderDao;
import com.example.orderservice.dao.TableInfoDao;
import com.example.orderservice.model.OrderBean;
import com.example.orderservice.model.TableInfoBean;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/************************************************************
 * 内容摘要 ：
 * <p>
 *
 * 作者 ：hxf 创建时间 ：2015年10月26日 下午4:30:13 当前版本号：v1.0 历史记录 : 日期 : 2015年10月26日
 * 下午4:30:13 修改人： 描述 :
 ************************************************************/
public class SearchOrderFragment extends Fragment {

	private ListView mlvSearchOrder;
	// ListView的数据源和适配器
	private List<OrderBean> orderBeanList = new ArrayList<OrderBean>();
	private SearchOrderAdapter soAdapter;

	private EditText etInputOrderId;

	private Button btnQuary;

	private Button btnPay;

	private OrderBean orderBean;

	// 监听器Listenners
	private Button.OnClickListener btnOnClickListener;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_search_order, container, false);

		// 初始化布局组件
		initComponent(view);

		// 初始化监听器 --- Listeners
		initListeners();

		// 设置监听器
		registerListener();

		return view;
	}

	/**
	 * 函数名称 : initListeners 功能描述 : 参数及返回值说明：
	 *
	 */
	private void initListeners() {
		// TODO Auto-generated method stub
		// 初始化按钮监听器OnClickListener
		btnOnClickListener = new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				switch (v.getId()) {
				case R.id.btn_pay:
					if (orderBean != null) {
						TableInfoBean tableInfoBean = TableInfoDao.getInstance(getActivity())
								.getTableInfoBean(orderBean.getTableInfoId());
						Intent intent = new Intent(getActivity(), OrderStateActivity.class);
						intent.putExtra("OrderBean", orderBean);
						intent.putExtra("TableInfoBean", tableInfoBean);
						startActivity(intent);
					} else {
						Toast.makeText(getActivity(), "请先选择要结账的订单", Toast.LENGTH_SHORT).show();
					}
					break;
				case R.id.btn_query:
					String etText = etInputOrderId.getText().toString();
					if (!TextUtils.isEmpty(etText)) {
						int orderId = Integer.parseInt(etText);
						OrderBean ob = OrderDao.getInstance(getActivity()).getOrderBean(orderId);
						if (ob == null) {
							Toast.makeText(getActivity(), "输入的订单号有误", Toast.LENGTH_SHORT).show();
						} else {
							orderBeanList.clear();
							orderBeanList.add(ob);
							soAdapter.notifyDataSetChanged();
						}

					} else {
						Toast.makeText(getActivity(), "请先输入要查询的订单号", Toast.LENGTH_SHORT).show();
						orderBeanList.clear();
						getOrderBeanList();
						soAdapter.notifyDataSetChanged();
					}
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
	 */
	private void registerListener() {
		mlvSearchOrder.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				if (position > 0) {
					soAdapter.setClickItem(position);
					soAdapter.notifyDataSetChanged();

					orderBean = orderBeanList.get(position - 1);
				}
			}
		});
		
		//按钮点击事件
		btnPay.setBackgroundResource(0);//去掉按钮背景
		btnPay.setOnClickListener(btnOnClickListener);
		//按钮点击事件
		btnQuary.setBackgroundResource(0);//去掉按钮背景
		btnQuary.setOnClickListener(btnOnClickListener);
	}

	/**
	 * 函数名称 : initComponent 功能描述 : 参数及返回值说明：
	 *
	 */
	private void initComponent(View view) {
		// TODO Auto-generated method stub
		mlvSearchOrder = (ListView) view.findViewById(R.id.id_lv_search_order);
		etInputOrderId = (EditText) view.findViewById(R.id.et_input_order_id);
		btnQuary = (Button) view.findViewById(R.id.btn_query);
		btnPay = (Button) view.findViewById(R.id.btn_pay);

		orderBeanList = getOrderBeanList();
		soAdapter = new SearchOrderAdapter(getActivity(), R.layout.item_search_order_lv, orderBeanList);
		mlvSearchOrder.setAdapter(soAdapter);
	}

	/**
	 * 函数名称 : getOrderBeanList 功能描述 : 参数及返回值说明：
	 * 
	 * @return
	 *
	 * 		修改记录： 日期 ：2015年10月28日 下午4:39:07 修改人：hxf 描述 ：
	 * 
	 */
	private List<OrderBean> getOrderBeanList() {

		List<OrderBean> list = OrderDao.getInstance(getActivity()).loadOrderBean();
		for (OrderBean ob : list) {
			if (ob.getIsPay() == 10) {

				orderBeanList.add(ob);

			}
		}
		return orderBeanList;
	}

}
