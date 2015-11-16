/************************************************************
 *	版权所有  (c)2011,   hxf<p>	
 *  文件名称	：AdminAddDishActivity.java<p>
 *
 *  创建时间	：2015年10月23日 上午10:58:39 
 *  当前版本号：v1.0
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
 * 内容摘要 ：
 * <p>
 *
 * 作者 ：hxf 创建时间 ：2015年10月23日 上午10:58:39 当前版本号：v1.0 历史记录 : 日期 : 2015年10月23日
 * 上午10:58:39 修改人： 描述 :
 ************************************************************/
public class AdminAddDishActivity extends BaseActivity {
	// 组件
	private EditText etDishName;
	private EditText etDishPrice;
	private RadioGroup rgDishCurrency;
	private RadioButton rbRMB, rbUSDollar;
	private RadioGroup rgDishUnit;
	private RadioButton rbPlate, rbBowl;
	private Button btnChoosePhoto;
	private ImageView ivDishPhoto;
	private Button btnSubmit, btnCancel;
	// Spinner组件，数据源，适配器
	private Spinner spinnerDishType;
	private List<DictionaryBean> dictionaryBeanList = new ArrayList<DictionaryBean>();
	private SpinnerDishTypeAdapter spDishTypeAdapter;
	// 表单变量
	private String dishName;
	private int dishType;
	private float dishPrice;
	private int dishCurrency = 8;
	private String dishCurrencyName = "元";
	private int dishUnit = 6;
	private String dishUnitName = "盘";
	private String dishPhotoPath;

	// Listeners
	private Button.OnClickListener btnOnClickListener;
	private RadioGroup.OnCheckedChangeListener rgOnCheckedChangeListener;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_admin_add_dish);

		// 初始化布局组件
		initComponent(savedInstanceState);

		// 初始化监听器s --- Listeners
		initListeners();

		// 设置监听器
		registerListener();

	}

	/**
	 * 函数名称 ：onSaveInstanceState 功能描述 ：
	 * 当销毁该页面时会调用该方法，所以可以把要保存的数据保存在这个方法的Bundle对象中 然后在onCreate（）方法中可以把数据取出来。
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
	 * 函数名称 : initComponent 功能描述 :
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
		 * 如果上次页面被关闭时有调用 onSaveInstanceState（）保存数据，那么就可以从Bundle对象中取出数据
		 */
		if (savedInstanceState != null) {
			Log.i("bundle", savedInstanceState.toString());
			dishName = savedInstanceState.getString("dishName", "");
			etDishName.setText(dishName);
			dishPrice = savedInstanceState.getFloat("dishPrice", 0);
			etDishPrice.setText(String.valueOf(dishPrice));
			dishPhotoPath = savedInstanceState.getString("dishPhotoPath", "sgh");
			// 使用工具类从assets文件夹加载添加新菜界面的预览图片的路径
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
	 * 函数名称 : initListeners 功能描述 : 参数及返回值说明： 初始化监听器 修改记录： 日期 ：2015年11月9日
	 * 下午12:18:28 修改人：hxf 描述 ：
	 * 
	 */
	private void initListeners() {

		// 按钮的点击事件OnClickListener
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

		// RadioGroup的OnCheckedChangeListener监听器
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
	 * 函数名称 : registerListener 功能描述 : 参数及返回值说明：
	 *
	 * 修改记录： 日期 ：2015年11月9日 上午11:44:16 修改人：hxf 描述 ：
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
	 * 函数名称 : dishSubmit 功能描述 : 参数及返回值说明：
	 *
	 * 修改记录： 日期 ：2015年11月9日 下午12:15:40 修改人：hxf 描述 ：
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
			Toast.makeText(AdminAddDishActivity.this, dishName + " 成功添加", Toast.LENGTH_SHORT).show();
			finish();
		} else {
			Toast.makeText(AdminAddDishActivity.this, "你填写完整菜单信息", Toast.LENGTH_LONG).show();
		}

	}

	/**
	 * 函数名称 : showChoosePhotoDialog 功能描述 : 参数及返回值说明：
	 *
	 * 修改记录： 日期 ：2015年11月9日 下午12:10:20 修改人：hxf 描述 ：
	 * 
	 */
	protected void showChoosePhotoDialog() {

		AlertDialog.Builder builder = new AlertDialog.Builder(AdminAddDishActivity.this);
		builder.setTitle("选择菜图");

		GridView gridView = new GridView(AdminAddDishActivity.this);
		gridView.setPadding(5, 10, 5, 10);
		gridView.setNumColumns(2);
		gridView.setHorizontalSpacing(5);
		gridView.setVerticalSpacing(5);
		// 获取数据源
		final String[] photoNameArray = getfileFromAssets("dish_photos");
		final GridViewImageAdapter gviAdapter = new GridViewImageAdapter(AdminAddDishActivity.this, R.layout.image_item,
				photoNameArray);
		gridView.setAdapter(gviAdapter);
		gridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// 修改添加新菜界面的预览图的路径
				dishPhotoPath = photoNameArray[position];

				// 改变适配器中的clickNumber值，把当前所被点击的那个item项的索引赋值给clickNumber
				gviAdapter.setSelection(position);
				// 记得通知适配器，
				gviAdapter.notifyDataSetChanged();

			}
		});

		builder.setView(gridView);
		builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// 使用工具类从assets文件夹加载添加新菜界面的预览图片的路径
				Bitmap bitmap = FileHelper.getImageFromAssetsFile(AdminAddDishActivity.this, dishPhotoPath);

				ivDishPhoto.setImageBitmap(bitmap);
			}
		});
		builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub

			}
		});
		AlertDialog dialog = builder.create();
		builder.show();

	}

	/**
	 * 函数名称 : getfileFromAssets 功能描述 : 参数及返回值说明：
	 * 
	 * @param string
	 * @return
	 *
	 * 		修改记录： 日期 ：2015年10月23日 下午3:03:09 修改人：hxf 描述 ：
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
