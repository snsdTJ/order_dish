/************************************************************
 *	��Ȩ����  (c)2011,   hxf<p>	
 *  �ļ�����	��ManagerCountActivity.java<p>
 *
 *  ����ʱ��	��2015��10��24�� ����3:24:36 
 *  ��ǰ�汾�ţ�v1.0
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
 * ����ժҪ ��
 * <p>
 *
 * ���� ��hxf ����ʱ�� ��2015��10��24�� ����3:24:36 ��ǰ�汾�ţ�v1.0 ��ʷ��¼ : ���� : 2015��10��24��
 * ����3:24:36 �޸��ˣ� ���� :
 ************************************************************/
public class ManagerCountActivity extends BaseActivity {

	private EditText etBeginDate;
	private EditText etEndDate;
	private ListView listView;
	private Button btnReturn, btnQuery;

	private int year;
	private int monthOfYear;
	private int dayOfMonth;

	// ListView������Դ��������
	private List<OrderBean> orderBeanList = new ArrayList<OrderBean>();
	private TurnoverAdapter turnoverAdapter;

	// Ӫҵ�ܶ�
	private TextView tvSumTurnover;
	private float sumTurnover;

	// Listeners
	private OnClickListener onClickListener;
	
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// ȥ��������
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_manager_count);

		// ��ʼ���������
		initComponent();

		// ��ʼ��������s --- Listeners
		initListeners();

		// ���ü�����
		registerListener();

		// ���ڳ�ʼ�����ڶԻ���ĳ�ʼʱ��
		initDatePickerDialogTime();
	}

	/**
	 * �������� : initComponent �������� : ����������ֵ˵����
	 *
	 * �޸ļ�¼�� ���� ��2015��10��30�� ����5:32:16 �޸��ˣ�hxf ���� ��
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
		 *  ��ʼ��ListView
		 */
		turnoverAdapter = new TurnoverAdapter(ManagerCountActivity.this, R.layout.item_turnover_lv, orderBeanList);
		// �Զ���ListView�ĵ�һ����ʾ�Ĳ��ֺ�����listView.addHeaderView(v)
		View v = LayoutInflater.from(ManagerCountActivity.this).inflate(R.layout.item_turnover_lv, null);
		TextView tvCashierName = (TextView) v.findViewById(R.id.tv_cashier_name);
		TextView tvTurnover = (TextView) v.findViewById(R.id.tv_turnover);
		TextView tvDate = (TextView) v.findViewById(R.id.tv_date);
		tvCashierName.setText("����Ա");
		tvTurnover.setText("Ӫ��");
		tvDate.setText("ʱ��");
		listView.addHeaderView(v);

		listView.setAdapter(turnoverAdapter);

		// ����һ��TextView�ؼ�������ʾ��ѡ���Ķ�����Ӫҵ�ܶ���ֱ���ڲ��������ListView֮�µģ������TextView��
		// ���涩��֮��պܴ�
		tvSumTurnover = new TextView(ManagerCountActivity.this);
		tvSumTurnover.setText("Ӫҵ�ܶ " + sumTurnover);
		tvSumTurnover.setPadding(10, 5, 20, 5);
		tvSumTurnover.setTextSize(24);
		tvSumTurnover.setGravity(Gravity.RIGHT);
		listView.addFooterView(tvSumTurnover);
	}

	/**
	 * �������� : initListeners �������� : ����������ֵ˵����
	 *
	 * �޸ļ�¼�� ���� ��2015��11��9�� ����5:32:14 �޸��ˣ�hxf ���� ��
	 * 
	 */
	private void initListeners() {
		// TODO Auto-generated method stub
		onClickListener = new OnClickListener() {

			@Override
			public void onClick(View v) {
				switch (v.getId()) {
				case R.id.et_begin_date:
					// ��ȡ��ʼ����
					getBeginDate();
					break;
				case R.id.et_end_date:
					// ��ȡ��������
					getEndDate();
					break;
				case R.id.btn_query:
					//��ѯ������������Ӫҵ��
					queryOrders();					
					break;
				case R.id.btn_return:
					// ����ҳ��
					finish();
					break;

				default:
					break;
				}
			}
		};
	}

	/**
	 *  �������� : queryOrders
	 *  �������� :  
	 *  ����������ֵ˵����
	 *
	 *  �޸ļ�¼��
	 *  	���� ��2015��10��30�� ����6:08:09	�޸��ˣ�hxf
	 *  	����	��
	 * 					
	 */
	protected void queryOrders() {
		// �����֮ǰ�鵽�����ݣ�Ҫ��Ȼ��ε����ʹ���ɹ���������ϴε�������ʾ����
		sumTurnover = 0;

		String beginDate = etBeginDate.getText().toString();
		String endDate = etEndDate.getText().toString();

		getOrderBeanList(beginDate, endDate);

		turnoverAdapter.notifyDataSetChanged();

		tvSumTurnover.setText("Ӫҵ�ܶ " + sumTurnover);
	}

	/**
	 * �������� : getEndDate �������� : ����������ֵ˵����
	 * 
	 * @param etEndDate2
	 *
	 *            �޸ļ�¼�� ���� ��2015��10��30�� ����5:48:49 �޸��ˣ�hxf ���� ��
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
	 * �������� : getBeginDate �������� : ����������ֵ˵����
	 * 
	 * @param etBeginDate2
	 *
	 *            �޸ļ�¼�� ���� ��2015��10��30�� ����5:48:49 �޸��ˣ�hxf ���� ��
	 * 
	 */
	protected void getBeginDate() {
		// TODO Auto-generated method stub
		// ʹ��DatePickerDialog�Ի�������ʹ�������ڲ��෽ʽ����һ�����ڶԻ���
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
		}, year, monthOfYear, dayOfMonth).show();// ���������������ڶԻ����ʼ��ʱ���õ�ʱ�䣬���һ��Ҫ����show��������
	}

	/**
	 * �������� : registerListener �������� : ����������ֵ˵����
	 *
	 * �޸ļ�¼�� ���� ��2015��10��30�� ����5:40:29 �޸��ˣ�hxf ���� ��
	 * 
	 */
	private void registerListener() {

		etBeginDate.setOnClickListener(onClickListener);

		etEndDate.setOnClickListener(onClickListener);
		
		// ��ѯ��ť����ѯ�ƶ����ڶ�������ÿ�������ĸ�������ʱ��Ϊ׼
		btnQuery.setOnClickListener(onClickListener);

		btnReturn.setOnClickListener(onClickListener);
	}

	/**
	 * �������� : getOrderBeanList �������� : ����������ֵ˵����
	 * 
	 * @return
	 *
	 * 		�޸ļ�¼�� ���� ��2015��10��30�� ����11:55:36 �޸��ˣ�hxf ���� ��
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
				// adapter���յ�����Դ��ָ����ڴ棬Ҳ����new�������Ǹ���ֵַ��
				// ���Բ�����orderBeanList =
				// mcaBean.getOrderBeanList(),��Ϊ�����൱��orderBeanListָ������һ���ڴ��еļ���
				// ��adapter������Դ��ֻ֮ǰprivate List<OrderBean> orderBeanList = new
				// ArrayList<OrderBean>();�����ַ��
				// Ҳ����˵��ʱorderBeanListָ�����¼��ϣ� ����adapter������Դ����֮ǰ�����new
				// ArrayList<OrderBean>()��
				// ����adapter������Դû�б仯����ô��turnoverAdapter.notifyDataSetChanged();�Ͳ���������
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
				textView.setText("���ʱ���û�ж�������������������");
				textView.setTextColor(Color.WHITE);
				textView.setTextSize(22);
				textView.setEllipsize(TruncateAt.END);
				textView.setGravity(Gravity.CENTER_VERTICAL);
				ll.addView(textView);
				ll.setGravity(Gravity.CENTER_VERTICAL);
				toastWrong.setView(ll);
				toastWrong.show();
				// Toast.makeText(ManagerCountActivity.this,
				// "���ʱ���û�ж�������������������", Toast.LENGTH_SHORT).show();
			}
		} else {
			Toast toast = Toast.makeText(ManagerCountActivity.this, "����δ����Ҫ��ѯ������", Toast.LENGTH_SHORT);
			toast.setGravity(Gravity.CENTER, 0, 0);
			toast.show();
		}

		return orderBeanList;
	}

	/**
	 * �������� : initDatePickerDialogTime �������� : ����������ֵ˵����
	 *
	 * �޸ļ�¼�� ���� ��2015��10��30�� ����8:59:07 �޸��ˣ�hxf ���� ��
	 *
	 */
	private void initDatePickerDialogTime() {
		Calendar calendar = Calendar.getInstance();
		year = calendar.get(Calendar.YEAR);
		monthOfYear = calendar.get(Calendar.MONTH);
		dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
	}

}
