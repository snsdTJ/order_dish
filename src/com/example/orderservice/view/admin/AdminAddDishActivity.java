/************************************************************
 *	��Ȩ����  (c)2011,   hxf<p>	
 *  �ļ�����	��AdminAddDishActivity.java<p>
 *
 *  ����ʱ��	��2015��10��23�� ����10:58:39 
 *  ��ǰ�汾�ţ�v1.0
 ************************************************************/
package com.example.orderservice.view.admin;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.example.orderservice.R;
import com.example.orderservice.adapter.GridViewImageAdapter;
import com.example.orderservice.adapter.SpinnerDishTypeAdapter;
import com.example.orderservice.dao.DictionaryDao;
import com.example.orderservice.dao.DishInfoDao;
import com.example.orderservice.model.DictionaryBean;
import com.example.orderservice.model.DishInfoBean;
import com.example.orderservice.util.FileHelper;
import com.example.orderservice.view.BaseActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Spinner;
import android.widget.Toast;

/************************************************************
 * ����ժҪ ��
 * <p>
 *
 * ���� ��hxf ����ʱ�� ��2015��10��23�� ����10:58:39 ��ǰ�汾�ţ�v1.0 ��ʷ��¼ : ���� : 2015��10��23��
 * ����10:58:39 �޸��ˣ� ���� :
 ************************************************************/
public class AdminAddDishActivity extends BaseActivity {
	// ���
	private EditText etDishName;
	private EditText etDishPrice;
	private RadioGroup rgDishCurrency;
	private RadioButton rbRMB, rbUSDollar;
	private RadioGroup rgDishUnit;
	private RadioButton rbPlate, rbBowl;
	private Button btnChoosePhoto;
	private ImageView ivDishPhoto;
	private Button btnSubmit, btnCancel;
	// Spinner���������Դ��������
	private Spinner spinnerDishType;
	private List<DictionaryBean> dictionaryBeanList = new ArrayList<DictionaryBean>();
	private SpinnerDishTypeAdapter spDishTypeAdapter;
	// ������
	private String dishName;
	private int dishType;
	private float dishPrice;
	private int dishCurrency = 8;
	private String dishCurrencyName = "Ԫ";
	private int dishUnit = 6;
	private String dishUnitName = "��";
	private String dishPhotoPath;

	// Listeners
	private Button.OnClickListener btnOnClickListener;
	private RadioGroup.OnCheckedChangeListener rgOnCheckedChangeListener;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_admin_add_dish);

		// ��ʼ���������
		initComponent(savedInstanceState);

		// ��ʼ��������s --- Listeners
		initListeners();

		// ���ü�����
		registerListener();

	}

	/**
	 * �������� ��onSaveInstanceState �������� ��
	 * �����ٸ�ҳ��ʱ����ø÷��������Կ��԰�Ҫ��������ݱ��������������Bundle������ Ȼ����onCreate���������п��԰�����ȡ������
	 */
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
		outState.putString("dishName", etDishName.getText().toString());
		if (!TextUtils.isEmpty(etDishPrice.getText().toString())) {
			outState.putFloat("dishPrice", Float.parseFloat(etDishPrice.getText().toString()));
		}	
		outState.putString("dishPhotoPath", dishPhotoPath);

		Log.i("save bundle", outState.toString());

	}

	/**
	 * �������� : initComponent �������� :
	 */
	private void initComponent(Bundle savedInstanceState) {

		etDishName = (EditText) findViewById(R.id.et_dish_name);
		spinnerDishType = (Spinner) findViewById(R.id.spinner_dish_type);
		etDishPrice = (EditText) findViewById(R.id.et_dish_price);
		rgDishCurrency = (RadioGroup) findViewById(R.id.rg_dish_currency);
		rbRMB = (RadioButton) findViewById(R.id.rb_rmb);
		rbUSDollar = (RadioButton) findViewById(R.id.rb_us_dollar);
		rgDishUnit = (RadioGroup) findViewById(R.id.rg_dish_unit);
		rbPlate = (RadioButton) findViewById(R.id.rb_plate);
		rbBowl = (RadioButton) findViewById(R.id.rb_bowl);
		btnChoosePhoto = (Button) findViewById(R.id.btn_choose_photo);
		ivDishPhoto = (ImageView) findViewById(R.id.iv_dish_photo);
		btnSubmit = (Button) findViewById(R.id.btn_submit);
		btnCancel = (Button) findViewById(R.id.btn_cancel);

		/*
		 * ����ϴ�ҳ�汻�ر�ʱ�е��� onSaveInstanceState�����������ݣ���ô�Ϳ��Դ�Bundle������ȡ������
		 */
		if (savedInstanceState != null) {
			Log.i("bundle", savedInstanceState.toString());
			dishName = savedInstanceState.getString("dishName", "");
			etDishName.setText(dishName);
			dishPrice = savedInstanceState.getFloat("dishPrice", 0);
			etDishPrice.setText(String.valueOf(dishPrice));
			dishPhotoPath = savedInstanceState.getString("dishPhotoPath", "sgh");
			// ʹ�ù������assets�ļ��м�������²˽����Ԥ��ͼƬ��·��
			Bitmap bitmap = FileHelper.getImageFromAssetsFile(AdminAddDishActivity.this, dishPhotoPath);
			ivDishPhoto.setImageBitmap(bitmap);
		}

		// Spinner
		int dictionaryType = 2;
		dictionaryBeanList = DictionaryDao.getInstance(this).loadDictionaryBean(dictionaryType);
		spDishTypeAdapter = new SpinnerDishTypeAdapter(this, R.layout.spinner_item, dictionaryBeanList);
		spinnerDishType.setAdapter(spDishTypeAdapter);
	}

	/**
	 * �������� : initListeners �������� : ����������ֵ˵���� ��ʼ�������� �޸ļ�¼�� ���� ��2015��11��9��
	 * ����12:18:28 �޸��ˣ�hxf ���� ��
	 * 
	 */
	private void initListeners() {

		// ��ť�ĵ���¼�OnClickListener
		btnOnClickListener = new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				switch (v.getId()) {
				case R.id.btn_choose_photo:
					showChoosePhotoDialog();
					break;
				case R.id.btn_submit:
					dishSubmit();
					break;
				case R.id.btn_cancel:
					finish();
					break;
				default:
					break;
				}
			}
		};

		// RadioGroup��OnCheckedChangeListener������
		rgOnCheckedChangeListener = new RadioGroup.OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				switch (checkedId) {
				case R.id.rb_rmb:
					dishCurrency = 8;
					break;
				case R.id.rb_us_dollar:
					dishCurrency = 9;
					break;
				case R.id.rb_plate:
					dishUnit = 6;
					break;
				case R.id.rb_bowl:
					dishUnit = 7;
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
	 * �޸ļ�¼�� ���� ��2015��11��9�� ����11:44:16 �޸��ˣ�hxf ���� ��
	 * 
	 */
	private void registerListener() {

		spinnerDishType.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				DictionaryBean dictionaryBean = dictionaryBeanList.get(position);
				dishType = dictionaryBean.getId();
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub

			}
		});

		rgDishCurrency.setOnCheckedChangeListener(rgOnCheckedChangeListener);

		rgDishUnit.setOnCheckedChangeListener(rgOnCheckedChangeListener);

		btnChoosePhoto.setOnClickListener(btnOnClickListener);

		btnSubmit.setOnClickListener(btnOnClickListener);

		btnCancel.setOnClickListener(btnOnClickListener);

	}

	/**
	 * �������� : dishSubmit �������� : ����������ֵ˵����
	 *
	 * �޸ļ�¼�� ���� ��2015��11��9�� ����12:15:40 �޸��ˣ�hxf ���� ��
	 * 
	 */
	protected void dishSubmit() {

		String strName = etDishName.getText().toString();
		if (!TextUtils.isEmpty(strName)) {
			dishName = strName.trim();
		}
		String strPrice = etDishPrice.getText().toString();
		if (!TextUtils.isEmpty(strPrice)) {
			dishPrice = Float.parseFloat(strPrice);
		}
		if (!TextUtils.isEmpty(strName) && dishPrice != 0) {
			DishInfoBean dishInfoBean = new DishInfoBean();
			dishInfoBean.setName(dishName);
			dishInfoBean.setDishType(dishType);
			dishInfoBean.setPrice(dishPrice);
			dishInfoBean.setCurrencyType(dishCurrency);
			dishInfoBean.setUnit(dishUnit);
			dishInfoBean.setPhoto(dishPhotoPath);
			DishInfoDao.getInstance(AdminAddDishActivity.this).saveDishInfo(dishInfoBean);
			Toast.makeText(AdminAddDishActivity.this, dishName + " �ɹ����", Toast.LENGTH_SHORT).show();
			finish();
		} else {
			Toast.makeText(AdminAddDishActivity.this, "����д�����˵���Ϣ", Toast.LENGTH_LONG).show();
		}

	}

	/**
	 * �������� : showChoosePhotoDialog �������� : ����������ֵ˵����
	 *
	 * �޸ļ�¼�� ���� ��2015��11��9�� ����12:10:20 �޸��ˣ�hxf ���� ��
	 * 
	 */
	protected void showChoosePhotoDialog() {

		AlertDialog.Builder builder = new AlertDialog.Builder(AdminAddDishActivity.this);
		builder.setTitle("ѡ���ͼ");

		GridView gridView = new GridView(AdminAddDishActivity.this);
		gridView.setPadding(5, 10, 5, 10);
		gridView.setNumColumns(2);
		gridView.setHorizontalSpacing(5);
		gridView.setVerticalSpacing(5);
		// ��ȡ����Դ
		final String[] photoNameArray = getfileFromAssets("dish_photos");
		final GridViewImageAdapter gviAdapter = new GridViewImageAdapter(AdminAddDishActivity.this, R.layout.image_item,
				photoNameArray);
		gridView.setAdapter(gviAdapter);
		gridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// �޸�����²˽����Ԥ��ͼ��·��
				dishPhotoPath = photoNameArray[position];

				// �ı��������е�clickNumberֵ���ѵ�ǰ����������Ǹ�item���������ֵ��clickNumber
				gviAdapter.setSelection(position);
				// �ǵ�֪ͨ��������
				gviAdapter.notifyDataSetChanged();

			}
		});

		builder.setView(gridView);
		builder.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// ʹ�ù������assets�ļ��м�������²˽����Ԥ��ͼƬ��·��
				Bitmap bitmap = FileHelper.getImageFromAssetsFile(AdminAddDishActivity.this, dishPhotoPath);

				ivDishPhoto.setImageBitmap(bitmap);
			}
		});
		builder.setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub

			}
		});
		AlertDialog dialog = builder.create();
		builder.show();

	}

	/**
	 * �������� : getfileFromAssets �������� : ����������ֵ˵����
	 * 
	 * @param string
	 * @return
	 *
	 * 		�޸ļ�¼�� ���� ��2015��10��23�� ����3:03:09 �޸��ˣ�hxf ���� ��
	 * 
	 */
	protected String[] getfileFromAssets(String fileName) {
		AssetManager assetManager = getAssets();
		String[] fileArray = null;
		try {
			fileArray = assetManager.list(fileName);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return fileArray;
	}

}
