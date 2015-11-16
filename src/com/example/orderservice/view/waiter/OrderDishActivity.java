/************************************************************
 *	��Ȩ����  (c)2011,   hxf<p>	
 *  �ļ�����	��OrderDishActivity.java<p>
 *
 *  ����ʱ��	��2015��10��17�� ����10:43:12 
 *  ��ǰ�汾�ţ�v1.0
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
 * ����ժҪ ��
 * <p>
 *
 * ���� ��hxf ����ʱ�� ��2015��10��17�� ����10:43:12 ��ǰ�汾�ţ�v1.0 ��ʷ��¼ : ���� : 2015��10��17��
 * ����10:43:12 �޸��ˣ� ���� :
 ************************************************************/
public class OrderDishActivity extends BaseActivity {

	// private TextView tvWaiterInfo;

	private TextView tvTableInfo;

	private Spinner spinnerDishType;

	private GridView gridView;

	private ListView listView;

	private Button btnOk;
	private Button btnCancel;

	// ��ǰ����ʵ����
	private TableInfoBean tableInfoBean;

	// ��ǰ����Ϊ����Ķ���
	private OrderBean orderBean;

	// �������ָ����Ƿ��Ѿ�����ˣ��ڵ��ȡ����ťʱ��Ҫ�жϸò���������ѵ�˵ľ͵����Ի�����ʾһ�£���ѯ���Ƿ�ȷ��ȡ���������˳���
	// ��ô����Ҫ�Ƿ�ֹ����͵ģ�������ȡ����ť�������Ǵ�OrderStateActivityҳ�淵�����������ʱ��
	private boolean ifOrdered;

	// Spinner
	private List<DictionaryBean> dictionaryBeanList = new ArrayList<DictionaryBean>();
	private SpinnerDishTypeAdapter spinnerDishTypeAdapter;

	// GridView
	private List<DishInfoBean> dishInfoBeanList = new ArrayList<DishInfoBean>();
	private OrderDishAdapter orderDishAdapter;

	// ���ڻ�ȡSpinner��ѡ�еĲ�Ʒ��Ȼ�󴫵ݸ�GridView��չʾ���Ըò�Ʒ����Ӧ�Ĳ˵�
	// Ĭ����ʾ����, ����Ĭ��dishType=3
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
	 * 
	 */
	private void getDataFromIntent() {
		Intent intent = getIntent();
		tableInfoBean = (TableInfoBean) intent.getSerializableExtra("TableInfoBean");
		if (intent.hasExtra("OrderDetailBeanList")) {// intent��hasExtra(��)���������жϸ�intent���Ƿ��в���ü��ļ�ֵ��
			// ��OrderDetailBeanList˵���Ǵ�OrderStateActivity��ת�����ģ�����Ҫ��ΪListView�ṩ����orderDetailBeanList���ϳ�ʼ��
			orderDetailBeanList = (List<OrderDetailBean>) intent.getSerializableExtra("OrderDetailBeanList");
		}

		// �������Ż�� ��������Ӧ�� δ����� OrderBean���� ����ͬһ�����ӿ����кܶඩ������ֻ����һ��δ�����
		int tableId = tableInfoBean.getId();
		int tableInfoId = tableId;// ��T_ORDER�����TABLE_INFO_ID���T_TABLE_INFO��ID�ֶι���
		// �õ���T_ORDER��TABLE_INFO_ID�ֶκ�������Ƿ񸶿�ISPAY�ֶ�(δ������10���������Ӧ��ͨ����ѯ���ݿ��ã����Ϳ���ȷ�����ĸ�����
		orderBean = OrderDao.getInstance(this).getOrderBean(tableInfoId, 10);
	}

	/**
	 * �������� : initComponent �������� : ����������ֵ˵����
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

		tvTableInfo.setText(tableInfoBean.getNo() + "��������ʱ�䣺" + orderBean.getBeginDateTime());

		/*
		 * Spinner����ѡ���ϵ�����ˣ����ˡ�����
		 */
		dictionaryBeanList = getDictionaryBean();
		spinnerDishTypeAdapter = new SpinnerDishTypeAdapter(this, R.layout.spinner_item, dictionaryBeanList);
		// ע��R.layout.spinner_item�������ǩ������SpinnerDishTypeAdapter��ʹ�õ��Ǹ��ؼ�
		spinnerDishTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnerDishType.setAdapter(spinnerDishTypeAdapter);

		/*
		 * GridView����չʾ�˵�
		 */
		getDishInfoBean();
		orderDishAdapter = new OrderDishAdapter(this, R.layout.dish_grid_view_item, dishInfoBeanList);
		gridView.setAdapter(orderDishAdapter);

		/*
		 * ListView����չʾ����Ĳ�
		 */
		listViewOrderDishAdapter = new ListViewOrderDishAdapter(this, R.layout.order_dish_listview_item,
				orderDetailBeanList);
		TextView tvHeaderView = new TextView(this);
		tvHeaderView.setText("��������");
		tvHeaderView.setGravity(Gravity.CENTER_HORIZONTAL);
		tvHeaderView.setTextSize(22);
		tvHeaderView.setPadding(0, 0, 0, 15);
		tvHeaderView.setTextColor(Color.parseColor("#ffffff"));
		listView.addHeaderView(tvHeaderView);
		listView.setAdapter(listViewOrderDishAdapter);
	}

	/**
	 * �������� : initListeners �������� : ����������ֵ˵����
	 *
	 * 
	 */
	private void initListeners() {
		// TODO Auto-generated method stub

	}

	/**
	 * �������� : registerListener �������� : ����������ֵ˵����
	 *
	 * �޸ļ�¼�� ���� ��2015��11��9�� ����7:29:53 �޸��ˣ�hxf ���� ��
	 * 
	 */
	private void registerListener() {
		spinnerDishType.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				// ͨ��adapterȥ��ȡʵ��
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
				orderDetailBean.setDishName(dishInfoBean.getName());// ֮ǰ���Ǵ�����ˣ�������ListView�в�����ʾ������
				orderDetailBean.setCurrentPrice(dishInfoBean.getPrice());
				orderDetailBean.setDishNumber(1);

				getOrderDetailBean(orderDetailBean);
				listViewOrderDishAdapter.notifyDataSetChanged();
			}
		});

		// btnOk.setBackgroundResource(0);
		// ���ȷ�ϰ�ť��ת��OrderStateActivityҳ��
		// ��Ҫ���� ����Ա���֣����ţ�����ʱ�䣬�����ţ�����Ĳˣ�����ʵֻҪ���ݶ����žͿ��ԣ���Ϊ������Ϣ�����Ը��ݶ����ŴӶ�������ȡ��
		// ����T_ORDER_DETAIL��������Ĳ˴洢�����ݿ�,����orderDetailBeanList������OrderDetailBeanʵ���洢�����ݿ�
		btnOk.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// ֮ǰ��ͨ���������ֱ������ListView�����ݶ�ֱ�Ӳ���T_ORDER_DETAIL���У������ͻ��������Ǵ�OrderStateActivityҳ��
				// ���صģ���Ҫ���мӲ� ��
				// ����ʱ�������ڵ��ʱ�Ѿ��ѵ���Ĳ˴������ݿ⣬���Ҵ�OrderStateActivity��ת����ʱ���ý����Ѿ���
				// ����Ĳ˼��ؽ�ListView��orderDetailBeanList�����У����������ݵ��ظ��洢�����ݿ�
				// for(OrderDetailBean odb:orderDetailBeanList){
				// OrderDetailDao.getInstance(OrderDishActivity.this).saveOrderDetailBean(odb);
				// }

				// Ӧ�ò���ÿ�ΰѵ�Ĳ˴�����ݿ�ʱ�����ȰѸö�������Ӧ�Ĳ�ɾ����Ȼ���ٰ����ڵ�Ĳ˵����������ݿ⣨��Ȼǰ���ǣ���
				// ����ҳʱҪ�����ݿ��иö�������Ӧ�Ĳ˶���ȡ�����ŵ�ListView������Դ����²ˣ����Ӳ˻��߼��˶�Ҫ�ڸ�����Դ��ı�
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
					// ������ʾ�Ի���ѯ�ʸ��û��Ѿ�����ˣ��Ƿ����Ҫȡ�����вˣ���ȡ��������ֹͣ����
					showAlertDialog();
				} else {
					// �Ѹ�����δ������Ǹ����������ݿ�ɾ��
					OrderDao.getInstance(OrderDishActivity.this).deleteOrder(orderBean);
					// ������״̬��Ϊ δ���� ״̬
					tableInfoBean.setState(1);
					TableInfoDao.getInstance(OrderDishActivity.this).modifyTableInfo(tableInfoBean);
					//��ת��WaiterMainActivityҳ��
					Intent intent = new Intent(OrderDishActivity.this, WaiterMainActivity.class);
					startActivity(intent);
				}
			}
		});

	}

	/**
	 *  �������� : showAlertDialog
	 *  �������� :  ������ʾ�Ի���ѯ�ʸ��û��Ѿ�����ˣ��Ƿ����Ҫȡ�����вˣ���ȡ��������ֹͣ����
	 *  ����������ֵ˵���� 					
	 */
	protected void showAlertDialog() {
		
		AlertDialog.Builder builder = new AlertDialog.Builder(OrderDishActivity.this);
		// �����Զ��岼��
		LinearLayout ll = (LinearLayout) LayoutInflater.from(OrderDishActivity.this)
				.inflate(R.layout.alert_dialog_important_info, null);
		Button btnSubmit = (Button) ll.findViewById(R.id.btn_submit);
		Button btnCancel = (Button) ll.findViewById(R.id.btn_cancel);
		builder.setView(ll);

		final AlertDialog dailog = builder.create();
		btnSubmit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				// �Ѹ�����δ������Ǹ����������ݿ�ɾ��
				OrderDao.getInstance(OrderDishActivity.this).deleteOrder(orderBean);

				// ע��: ��Ҫ�Ѹö�������Ĳ˴�T_ORDER_DETAIL����ɾ��
				int orderId = orderBean.getId();
				OrderDetailDao.getInstance(OrderDishActivity.this).deleteOrderDetailBean(orderId);

				// ������״̬��Ϊ δ���� ״̬
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
	 * �������� : getOrderDetailBean �������� : ���ListView����Դ�ɵ��GridView ��
	 * ListView�е�������ť���ı����� ���ڵ��GridView��itemʱֻ���������ݣ����ǰ����ݼ��뼯��ʱҪ�����Ƿ��һ�����
	 * ����ͨ���鿴OrderDetailBean�����dishInfoId����ֶ����ж� �ǣ��Ѹö����������Դ
	 * �񣺰��Ѵ��ڵļ����еĸö����dishNumber�ֶμ�1 ����������ֵ˵����
	 * 
	 * @return
	 *
	 * 		�޸ļ�¼�� ���� ��2015��10��20�� ����9:03:29 �޸��ˣ�hxf ���� ��
	 * 
	 */
	private List<OrderDetailBean> getOrderDetailBean(OrderDetailBean orderDetailBean) {
		if (orderDetailBeanList.isEmpty()) {
			orderDetailBeanList.add(orderDetailBean);
		} else {

			// ����ֱ�����������ַ����������java.util.ConcurrentModificationException�쳣���������޸��쳣��
			// Ҳ���ǵ�ʹ����ǿfor�������ʱ���ܱ߱���һ������ʱ�����ӣ���ɾ���ü����е�Ԫ�أ����ǵ����������õ����������ҵ������޸�Ԫ�أ�
			// for(OrderDetailBean odb:orderDetailBeanList){
			// if (orderDetailBean.getDishInfoId() == odb.getDishInfoId()) {
			// odb.setDishNumber(odb.getDishNumber()+1);
			// }else {
			// orderDetailBeanList.add(orderDetailBean);
			// }
			// }

			// //�����ֻҪȡ����orderDetailBean.getDishInfoId() ==
			// odb.getDishInfoId()�������������Ԫ��
			// //��������������в�����dishInfoId��ͬ������ͬ���ڼ����п���ߣ��ͻ��ظ����orderDetailBeanԪ��
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

			// �������������ͨfor��ʽ ��
			// �õ������������ϣ�ͬʱ�������޸�Ԫ�ط�ʽ,����Ҫ��ListIterator����ΪIterator�ķ���̫�٣�û������Ԫ�ط���
			ListIterator<OrderDetailBean> lit = orderDetailBeanList.listIterator();
			boolean flag = true;// �������Ҫ�������Ǹ������������������ظ����
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
	 * �������� : getDishInfoBean �������� : ΪGridView�ṩ���ݳ�ʼ�� ����������ֵ˵����
	 * 
	 * @return
	 *
	 * 		�޸ļ�¼�� ���� ��2015��10��20�� ����3:40:40 �޸��ˣ�hxf ���� ��
	 * 
	 */
	private List<DishInfoBean> getDishInfoBean() {
		// ��ѯT_DISH_INFO���������DishInfoBeanʵ���������ڼ�����
		List<DishInfoBean> listAll = DishInfoDao.getInstance(this).loadDishInfoBean();
		// �����漯����ɸѡ��ָ��dishType��DishInfoBeanʵ����������ΪGridView���ṩ����Դ���Ǹ�����
		// �ȰѼ����е�������գ�����ÿ�����ݶ���׷�ӽ���
		dishInfoBeanList.clear();
		// ע�����ﲻ�����¼��ϱ���ɸѡ����DishInfoBeanʵ����Ȼ����¼��Ϸ��أ��������Ļ�ֻ�ǲ����¼��ϣ�������Դ�����������ȴû��
		for (DishInfoBean difb : listAll) {
			if (difb.getDishType() == dishType) {
				dishInfoBeanList.add(difb);
			}
		}

		return dishInfoBeanList;
	}

	/**
	 * �������� : getDictionaryBean �������� : ΪSpinner�ṩ���ݳ�ʼ�� ����������ֵ˵����
	 * 
	 * @return
	 *
	 * 		�޸ļ�¼�� ���� ��2015��10��19�� ����9:21:54 �޸��ˣ�hxf ���� ��
	 * 
	 */
	private List<DictionaryBean> getDictionaryBean() {
		// ��ѯ������T_DATA_DICTIONARY����DictionaryBeanʵ��
		List<DictionaryBean> listAll = DictionaryDao.getInstance(this).loadDictionaryBean();

		// ��type�ֶ�Ϊ2��DictionaryBean��ȡ����������ΪSpinner�ṩ���ݵ��Ǹ����ϣ�
		// ѡ��2����Ϊ����type�ֶ�Ϊ2�Ĵ����Ʒ
		for (DictionaryBean dictionaryBean : listAll) {
			if (dictionaryBean.getType() == 2) {
				dictionaryBeanList.add(dictionaryBean);
			}
		}
		return dictionaryBeanList;
	}
}
