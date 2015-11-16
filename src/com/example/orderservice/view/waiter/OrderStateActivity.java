/************************************************************
 *	��Ȩ����  (c)2011,   hxf<p>	
 *  �ļ�����	��OrderStateActivity.java<p>
 *
 *  ����ʱ��	��2015��10��21�� ����2:49:28 
 *  ��ǰ�汾�ţ�v1.0
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
 * ����ժҪ ��
 * <p>
 *
 * ���� ��hxf ����ʱ�� ��2015��10��21�� ����2:49:28 ��ǰ�汾�ţ�v1.0 ��ʷ��¼ : ���� : 2015��10��21��
 * ����2:49:28 �޸��ˣ� ���� :
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

	// ���ڼ��������ܶ�
	private float totalPay;

	// ��ǰ����
	private int tableNo;
	// ��ǰ���Ӷ���
	private TableInfoBean tableInfoBean;

	// ��ǰ����
	private OrderBean orderBean;

	// ������Listenners
	private Button.OnClickListener btnOnClickListener;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_order_state);

		// ��ȡ��Intent���ݹ��������ݡ�
		getDataFromIntent();

		// ��ʼ���������
		initComponent();

		// ��ʼ��������s --- Listeners
		initListeners();

		// ���ü�����
		registerListener();

	}

	/**
	 * �������� : getDataFromIntent �������� : ����������ֵ˵����
	 * 
	 */
	private void getDataFromIntent() {
		// TODO Auto-generated method stub
		Intent intent = getIntent();
		orderBean = (OrderBean) intent.getSerializableExtra("OrderBean");// ��ǰ����
		tableInfoBean = (TableInfoBean) intent.getSerializableExtra("TableInfoBean");
	}

	/**
	 * �������� : initComponent �������� : ����������ֵ˵����
	 *
	 */
	private void initComponent() {

		// tvWaiterInfo = (TextView) findViewById(R.id.tv_waiter_info);
		tvTableInfo = (TextView) findViewById(R.id.tv_table_info);
		listView = (ListView) findViewById(R.id.list_view);
		btnPay = (Button) findViewById(R.id.btn_pay);
		btnOrderMore = (Button) findViewById(R.id.btn_order_more);
		btnOff = (Button) findViewById(R.id.btn_off);

		tableNo = orderBean.getTableNo();// ����
		String beginDateTime = orderBean.getBeginDateTime();// ������ʼʱ��
		tvTableInfo.setText(tableNo + "������ ����ʱ�� " + beginDateTime);

		/*
		 * ��ʼ��ListView
		 */
		int orderId = orderBean.getId();// T_ORDER_DETAIL���ORDER_ID�������T_ORDER���ID�ֶ�
		getOrderDetailBeanList(orderId);// ��ʼ��ListView������Դ
		listViewAdapter = new ListViewOrdeStateAdapter(this, R.layout.order_state_listview_item, orderDetailBeanList);
		listView.setAdapter(listViewAdapter);

	}

	/**
	 * �������� : initListeners �������� : ����������ֵ˵����
	 *
	 * 
	 */
	private void initListeners() {
		
		//��ʼ����ť������OnClickListener
		btnOnClickListener = new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				switch (v.getId()) {
				case R.id.btn_order_more:
					//��ת��OrderDishActivityҳ��
					Intent intent = new Intent(OrderStateActivity.this, OrderDishActivity.class);
					intent.putExtra("OrderDetailBeanList", (Serializable) orderDetailBeanList);
					intent.putExtra("TableInfoBean", tableInfoBean);
					startActivity(intent);
					break;
				case R.id.btn_pay:
					// ����Ի���
					showPayDailog();
					break;
				case R.id.btn_off:
					//��ת��WaiterMainActivityҳ��
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
	 * �������� : registerListener �������� : ����������ֵ˵����
	 *
	 * 
	 */
	private void registerListener() {
		
		// TODO Auto-generated method stub
		btnOrderMore.setBackgroundResource(0);// ȥ����ť����ɫ
		btnOrderMore.setOnClickListener(btnOnClickListener);

		btnOff.setBackgroundResource(0);// ȥ����ť����ɫ
		btnOff.setOnClickListener(btnOnClickListener);

		btnPay.setBackgroundResource(0);// ȥ����ť����ɫ
		btnPay.setOnClickListener(btnOnClickListener);

	}

	/**
	 * �������� : showPayDailog �������� :
	 * ����Ի���,�㸶�ť����ִ�и�����ʣ����������ݿ��и���״̬��Ϊ���ã��͸ö���״̬Ϊ�Ѹ�� ����������ֵ˵����
	 *
	 * �޸ļ�¼�� ���� ��2015��10��22�� ����10:45:13 �޸��ˣ�hxf ���� ��
	 * 
	 */
	protected void showPayDailog() {
		// ����ʹ���Զ��岼�ֶԻ�������Ҫ�����Զ��岼����ͼ(���ﲢ���ǰ������Ի�������Ϊ�ò��֣�ֻ�ǰѸò�����ͼ��Ϊ��ͼ����Ի���

		// LayoutInflater layoutInflater =
		// LayoutInflater.from(this);//�������ͼ����ListView�ؼ����������ؼ��Ļ�������Ҫ�������ַ�ʽ
		// View view = layoutInflater.inflate(int resource, ViewGroup
		// root);//resource �����ļ���Ҫ����ListView�ؼ�
		// ListView dialogListView =
		// view.findViewById(R.id.list_view);//ListView���ڲ�item��������adpter���Ƶ�

		ListView dialogListView = new ListView(this);// ������Ҫ�ӵ���ͼֻ��Ҫ��ListView�ؼ���
														// ����ֱ��new һ���Ϳ�����
		dialogListView.setBackgroundColor(Color.parseColor("#3e3e3e"));

		// �������dialogListView������Դ����������OrderStateActivityҳ���ListViewһģһ�������Կ���ֱ��ʹ��
		dialogListView.setAdapter(listViewAdapter);

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("�˵�����");
		builder.setView(dialogListView);
		builder.setMessage("�ϼ� �� " + totalPay);
		builder.setPositiveButton("����", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {

				// ������״̬��Ϊδ����, ��ʵ�ڶ�������state��Ӧ��ֱ�Ӵ�1�����Ǵ�
				// ��δ��������Ȼ����modifyTableInfo�����������洦�����
				TableInfoDao.getInstance(OrderStateActivity.this).modifyTableInfo(tableNo, 1);

				// ���¶�����Ϣ������Աid������ʱ�䣬�ܽ�����״̬
				orderBean.setCashierId(3);// �Ҳ�֪����λ�ȡ������Ա��id��������
				Date date = new Date();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String endDateTime = sdf.format(date);
				orderBean.setEndDateTime(endDateTime);
				orderBean.setTotalMoney(totalPay);
				orderBean.setIsPay(11);// ��ʵӦͨ�����Ѹ��ȥ��������ø�ISPAY�ֶ�
				OrderDao.getInstance(OrderStateActivity.this).modifyOrder(orderBean);

				// ���ʺ�Ҫ��ת��ChooseTableActivity����
				Intent intent = new Intent(OrderStateActivity.this, WaiterMainActivity.class);
				startActivity(intent);

			}
		});
		builder.setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		AlertDialog alertDialog = builder.create();

		alertDialog.show();

	}

	/**
	 * �������� : getOrderDetailBeanList �������� : ΪListiew �ṩ����Դ ����������ֵ˵����
	 * 
	 * @param orderId
	 *
	 *            �޸ļ�¼�� ���� ��2015��10��21�� ����9:19:52 �޸��ˣ�hxf ���� ��
	 * 
	 */
	private void getOrderDetailBeanList(int orderId) {

		// ��ȡT_ORDER_DETAIL��������OrderDetail����
		List<OrderDetailBean> odbList = OrderDetailDao.getInstance(this).loadOrderDetailBean();
		// �����漯����ȡ��ָ��orderId��OrderDetail���󣬲��洢��ΪListView�ṩ���ݵļ�����
		for (OrderDetailBean odb : odbList) {
			if (odb.getOrderId() == orderId) {
				orderDetailBeanList.add(odb);
				totalPay += (odb.getCurrentPrice() * odb.getDishNumber());
			}
		}
	}
}
