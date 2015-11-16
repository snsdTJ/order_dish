/************************************************************
 *	��Ȩ����  (c)2011,   hxf<p>	
 *  �ļ�����	��AdminManageDishActivity.java<p>
 *
 *  ����ʱ��	��2015��10��23�� ����9:32:39 
 *  ��ǰ�汾�ţ�v1.0
 ************************************************************/
package com.example.orderservice.view.admin;

import java.util.ArrayList;
import java.util.List;

import com.example.orderservice.R;
import com.example.orderservice.adapter.OrderDishAdapter;
import com.example.orderservice.adapter.SpinnerDishTypeAdapter;
import com.example.orderservice.dao.DictionaryDao;
import com.example.orderservice.dao.DishInfoDao;
import com.example.orderservice.model.DictionaryBean;
import com.example.orderservice.model.DishInfoBean;
import com.example.orderservice.model.OrderDetailBean;
import com.example.orderservice.view.BaseActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;

/************************************************************
 * ����ժҪ ��
 * <p>
 *
 * ���� ��hxf ����ʱ�� ��2015��10��23�� ����9:32:39 ��ǰ�汾�ţ�v1.0 ��ʷ��¼ : ���� : 2015��10��23��
 * ����9:32:39 �޸��ˣ� ���� :
 ************************************************************/
public class AdminManageDishActivity extends BaseActivity {

	private Spinner spinnerDishType;
	private GridView gridViewDish;
	private Button btnModifyDish, btnDeleteDish, btnAddDish, btnOff;

	// ���ڻ�ȡSpinner��ѡ�еĲ�Ʒ��Ȼ�󴫵ݸ�GridView��չʾ���Ըò�Ʒ����Ӧ�Ĳ˵�
	// Ĭ����ʾ����, ����Ĭ��dishType=3
	private int dishType = 3;

	// Spinner
	private List<DictionaryBean> dictionaryBeanList = new ArrayList<DictionaryBean>();
	private SpinnerDishTypeAdapter spinnerDishTypeAdapter;

	// GridView
	private List<DishInfoBean> dishInfoBeanList = new ArrayList<DishInfoBean>();
	private OrderDishAdapter orderDishAdapter;

	// ��ѡ�е��Ǹ��˵�ʵ��
	private DishInfoBean dishInfoBean;

	// Listeners
	private Button.OnClickListener btnOnClickListener;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		// ȥ��������
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_admin_manage_dish);

		// ��ʼ�����
		initComponent();

		// ��ʼ��������s --- Listeners
		initListeners();

		// ���ü�����
		registerListener();

	}

	/**
	 * �������� : initComponent �������� : ����������ֵ˵����
	 *
	 * �޸ļ�¼�� ���� ��2015��11��9�� ����4:51:05 �޸��ˣ�hxf ���� ��
	 * 
	 */
	private void initComponent() {

		spinnerDishType = (Spinner) findViewById(R.id.spinner_dish_type);
		gridViewDish = (GridView) findViewById(R.id.grid_view_dishes);
		btnModifyDish = (Button) findViewById(R.id.btn_modify_dish);
		btnDeleteDish = (Button) findViewById(R.id.btn_delete_dish);
		btnAddDish = (Button) findViewById(R.id.btn_add_dish);
		btnOff = (Button) findViewById(R.id.btn_off);

		// Spinner����ѡ���ϵ�����ˣ����ˡ�����
		dictionaryBeanList = getDictionaryBean();
		spinnerDishTypeAdapter = new SpinnerDishTypeAdapter(this, R.layout.spinner_item, dictionaryBeanList);
		// ע��R.layout.spinner_item�������ǩ������SpinnerDishTypeAdapter��ʹ�õ��Ǹ��ؼ�
		spinnerDishTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnerDishType.setAdapter(spinnerDishTypeAdapter);

		// GridView����չʾ�˵�
		getDishInfoBean();
		orderDishAdapter = new OrderDishAdapter(this, R.layout.dish_grid_view_item, dishInfoBeanList);
		gridViewDish.setAdapter(orderDishAdapter);

	}

	/**
	 *  �������� : initListeners
	 *  �������� :  
	 *  ����������ֵ˵����
	 *
	 *  �޸ļ�¼��
	 *  	���� ��2015��11��9�� ����4:51:03	�޸��ˣ�hxf
	 *  	����	��
	 * 					
	 */
	private void initListeners() {
		// TODO Auto-generated method stub
		btnOnClickListener = new Button.OnClickListener(){
			@Override
			public void onClick(View v) {
				switch (v.getId()) {
				
				case R.id.btn_modify_dish:
					
					if (dishInfoBean != null) {
						showModifyDishDialog();
					} else {
						Toast.makeText(AdminManageDishActivity.this, "����ѡ��Ҫ�޸ĵĲ�", Toast.LENGTH_SHORT).show();
					}
					break;
					
				case R.id.btn_delete_dish:
					boolean flag = DishInfoDao.getInstance(AdminManageDishActivity.this).deleteDishInfo(dishInfoBean);
					if (flag) {
						getDishInfoBean();
						orderDishAdapter.notifyDataSetChanged();
						Toast.makeText(AdminManageDishActivity.this, "�ɹ�ɾ���ˣ�  " + dishInfoBean.getName(),
								Toast.LENGTH_SHORT).show();
					} else {
						Toast.makeText(AdminManageDishActivity.this, "ɾ��ʧ��", Toast.LENGTH_SHORT).show();
					}
					break;
					
				case R.id.btn_add_dish:
					Intent addDishIntent = new Intent(AdminManageDishActivity.this, AdminAddDishActivity.class);
					startActivity(addDishIntent);
					break;
					
				case R.id.btn_off:
					finish();
					break;

				default:
					break;
				}				
			}			
		};	
	}

	/**
	 *  �������� : showModifyDishDialog
	 *  �������� :  
	 *  ����������ֵ˵����
	 *
	 *  �޸ļ�¼��
	 *  	���� ��2015��11��9�� ����5:05:57	�޸��ˣ�hxf
	 *  	����	��
	 * 					
	 */
	protected void showModifyDishDialog() {
		// TODO Auto-generated method stub
		AlertDialog.Builder builder = new AlertDialog.Builder(AdminManageDishActivity.this);
		View view = LayoutInflater.from(AdminManageDishActivity.this).inflate(R.layout.layout_modify_dish,
				null, false);
		final EditText etNewDishName = (EditText) view.findViewById(R.id.et_new_dish_name);
		final EditText etNewDishPrice = (EditText) view.findViewById(R.id.et_new_dish_price);
		builder.setView(view);
		builder.setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		builder.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {

				String newDishName = etNewDishName.getText().toString();
				String newDishPrice = etNewDishPrice.getText().toString();

				if (!TextUtils.isEmpty(newDishName) && !TextUtils.isEmpty(newDishPrice)) {
					dishInfoBean.setName(newDishName);
					dishInfoBean.setPrice(Float.parseFloat(newDishPrice));

					boolean flag = DishInfoDao.getInstance(AdminManageDishActivity.this)
							.modifyDishInfo(dishInfoBean);
					if (flag) {
						getDishInfoBean();
						orderDishAdapter.notifyDataSetChanged();
						Toast.makeText(AdminManageDishActivity.this, "�ɹ��޸Ĳˣ�  " + dishInfoBean.getName(),
								Toast.LENGTH_SHORT).show();
					}

				} else {
					Toast.makeText(AdminManageDishActivity.this, "���������¼۸���²���", Toast.LENGTH_SHORT).show();
				}
			}
		});

		AlertDialog dialog = builder.create();
		dialog.show();
	}

	/**
	 * �������� : registerListener �������� : ����������ֵ˵����
	 *
	 * �޸ļ�¼�� ���� ��2015��11��9�� ����4:51:01 �޸��ˣ�hxf ���� ��
	 * 
	 */
	private void registerListener() {
		
		spinnerDishType.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				// ͨ��adapterȥ��ȡʵ��
				DictionaryBean dictionaryBean = spinnerDishTypeAdapter.getItem(position);
				dictionaryBeanList.get(position);
				// ������ı�GridView������Դ��Ȼ��֪ͨGridView��������
				dishType = dictionaryBean.getId();
				getDishInfoBean();
				orderDishAdapter.setSelectedItem(-1);// ��������Ļ������¼�������Դ��ʾʱ���ϴα�������Ǹ�λ�û��ǻ�������͸����70
				orderDishAdapter.notifyDataSetChanged();
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
			}
		});

		gridViewDish.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

				dishInfoBean = dishInfoBeanList.get(position);

				// �ı�ͼƬ��͸����
				orderDishAdapter.setSelectedItem(position);
				orderDishAdapter.notifyDataSetChanged();
			}
		});

		btnModifyDish.setOnClickListener(btnOnClickListener);
		btnAddDish.setOnClickListener(btnOnClickListener);
		btnDeleteDish.setOnClickListener(btnOnClickListener);
		btnOff.setOnClickListener(btnOnClickListener);

	}
	

	/**
	 * �������� : getDictionaryBean �������� : ����������ֵ˵����
	 * 
	 * @return
	 *
	 * 		�޸ļ�¼�� ���� ��2015��10��23�� ����10:23:46 �޸��ˣ�hxf ���� ��
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

}
