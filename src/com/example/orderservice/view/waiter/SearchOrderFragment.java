/************************************************************
 *	��Ȩ����  (c)2011,   hxf<p>	
 *  �ļ�����	��ChooseTableFragment.java<p>
 *
 *  ����ʱ��	��2015��10��26�� ����4:30:13 
 *  ��ǰ�汾�ţ�v1.0
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
 * ����ժҪ ��
 * <p>
 *
 * ���� ��hxf ����ʱ�� ��2015��10��26�� ����4:30:13 ��ǰ�汾�ţ�v1.0 ��ʷ��¼ : ���� : 2015��10��26��
 * ����4:30:13 �޸��ˣ� ���� :
 ************************************************************/
public class SearchOrderFragment extends Fragment {

	private ListView mlvSearchOrder;
	// ListView������Դ��������
	private List<OrderBean> orderBeanList = new ArrayList<OrderBean>();
	private SearchOrderAdapter soAdapter;

	private EditText etInputOrderId;

	private Button btnQuary;

	private Button btnPay;

	private OrderBean orderBean;

	// ������Listenners
	private Button.OnClickListener btnOnClickListener;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_search_order, container, false);

		// ��ʼ���������
		initComponent(view);

		// ��ʼ�������� --- Listeners
		initListeners();

		// ���ü�����
		registerListener();

		return view;
	}

	/**
	 * �������� : initListeners �������� : ����������ֵ˵����
	 *
	 */
	private void initListeners() {
		// TODO Auto-generated method stub
		// ��ʼ����ť������OnClickListener
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
						Toast.makeText(getActivity(), "����ѡ��Ҫ���˵Ķ���", Toast.LENGTH_SHORT).show();
					}
					break;
				case R.id.btn_query:
					String etText = etInputOrderId.getText().toString();
					if (!TextUtils.isEmpty(etText)) {
						int orderId = Integer.parseInt(etText);
						OrderBean ob = OrderDao.getInstance(getActivity()).getOrderBean(orderId);
						if (ob == null) {
							Toast.makeText(getActivity(), "����Ķ���������", Toast.LENGTH_SHORT).show();
						} else {
							orderBeanList.clear();
							orderBeanList.add(ob);
							soAdapter.notifyDataSetChanged();
						}

					} else {
						Toast.makeText(getActivity(), "��������Ҫ��ѯ�Ķ�����", Toast.LENGTH_SHORT).show();
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
	 * �������� : registerListener �������� : ����������ֵ˵����
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
		
		//��ť����¼�
		btnPay.setBackgroundResource(0);//ȥ����ť����
		btnPay.setOnClickListener(btnOnClickListener);
		//��ť����¼�
		btnQuary.setBackgroundResource(0);//ȥ����ť����
		btnQuary.setOnClickListener(btnOnClickListener);
	}

	/**
	 * �������� : initComponent �������� : ����������ֵ˵����
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
	 * �������� : getOrderBeanList �������� : ����������ֵ˵����
	 * 
	 * @return
	 *
	 * 		�޸ļ�¼�� ���� ��2015��10��28�� ����4:39:07 �޸��ˣ�hxf ���� ��
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
