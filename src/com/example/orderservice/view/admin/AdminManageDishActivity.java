/************************************************************
 *	版权所有  (c)2011,   hxf<p>	
 *  文件名称	：AdminManageDishActivity.java<p>
 *
 *  创建时间	：2015年10月23日 下午9:32:39 
 *  当前版本号：v1.0
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
 * 内容摘要 ：
 * <p>
 *
 * 作者 ：hxf 创建时间 ：2015年10月23日 下午9:32:39 当前版本号：v1.0 历史记录 : 日期 : 2015年10月23日
 * 下午9:32:39 修改人： 描述 :
 ************************************************************/
public class AdminManageDishActivity extends BaseActivity {

	private Spinner spinnerDishType;
	private GridView gridViewDish;
	private Button btnModifyDish, btnDeleteDish, btnAddDish, btnOff;

	// 用于获取Spinner中选中的菜品，然后传递给GridView中展示所以该菜品所对应的菜单
	// 默认显示闽菜, 所以默认dishType=3
	private int dishType = 3;

	// Spinner
	private List<DictionaryBean> dictionaryBeanList = new ArrayList<DictionaryBean>();
	private SpinnerDishTypeAdapter spinnerDishTypeAdapter;

	// GridView
	private List<DishInfoBean> dishInfoBeanList = new ArrayList<DishInfoBean>();
	private OrderDishAdapter orderDishAdapter;

	// 被选中的那个菜的实例
	private DishInfoBean dishInfoBean;

	// Listeners
	private Button.OnClickListener btnOnClickListener;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		// 去掉标题栏
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_admin_manage_dish);

		// 初始化组件
		initComponent();

		// 初始化监听器s --- Listeners
		initListeners();

		// 设置监听器
		registerListener();

	}

	/**
	 * 函数名称 : initComponent 功能描述 : 参数及返回值说明：
	 *
	 * 修改记录： 日期 ：2015年11月9日 下午4:51:05 修改人：hxf 描述 ：
	 * 
	 */
	private void initComponent() {

		spinnerDishType = (Spinner) findViewById(R.id.spinner_dish_type);
		gridViewDish = (GridView) findViewById(R.id.grid_view_dishes);
		btnModifyDish = (Button) findViewById(R.id.btn_modify_dish);
		btnDeleteDish = (Button) findViewById(R.id.btn_delete_dish);
		btnAddDish = (Button) findViewById(R.id.btn_add_dish);
		btnOff = (Button) findViewById(R.id.btn_off);

		// Spinner用来选择菜系（闽菜，川菜。。。
		dictionaryBeanList = getDictionaryBean();
		spinnerDishTypeAdapter = new SpinnerDishTypeAdapter(this, R.layout.spinner_item, dictionaryBeanList);
		// 注意R.layout.spinner_item这个根标签必须是SpinnerDishTypeAdapter中使用的那个控件
		spinnerDishTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnerDishType.setAdapter(spinnerDishTypeAdapter);

		// GridView用来展示菜单
		getDishInfoBean();
		orderDishAdapter = new OrderDishAdapter(this, R.layout.dish_grid_view_item, dishInfoBeanList);
		gridViewDish.setAdapter(orderDishAdapter);

	}

	/**
	 *  函数名称 : initListeners
	 *  功能描述 :  
	 *  参数及返回值说明：
	 *
	 *  修改记录：
	 *  	日期 ：2015年11月9日 下午4:51:03	修改人：hxf
	 *  	描述	：
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
						Toast.makeText(AdminManageDishActivity.this, "请先选中要修改的菜", Toast.LENGTH_SHORT).show();
					}
					break;
					
				case R.id.btn_delete_dish:
					boolean flag = DishInfoDao.getInstance(AdminManageDishActivity.this).deleteDishInfo(dishInfoBean);
					if (flag) {
						getDishInfoBean();
						orderDishAdapter.notifyDataSetChanged();
						Toast.makeText(AdminManageDishActivity.this, "成功删除菜：  " + dishInfoBean.getName(),
								Toast.LENGTH_SHORT).show();
					} else {
						Toast.makeText(AdminManageDishActivity.this, "删除失败", Toast.LENGTH_SHORT).show();
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
	 *  函数名称 : showModifyDishDialog
	 *  功能描述 :  
	 *  参数及返回值说明：
	 *
	 *  修改记录：
	 *  	日期 ：2015年11月9日 下午5:05:57	修改人：hxf
	 *  	描述	：
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
		builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
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
						Toast.makeText(AdminManageDishActivity.this, "成功修改菜：  " + dishInfoBean.getName(),
								Toast.LENGTH_SHORT).show();
					}

				} else {
					Toast.makeText(AdminManageDishActivity.this, "请先输入新价格和新菜名", Toast.LENGTH_SHORT).show();
				}
			}
		});

		AlertDialog dialog = builder.create();
		dialog.show();
	}

	/**
	 * 函数名称 : registerListener 功能描述 : 参数及返回值说明：
	 *
	 * 修改记录： 日期 ：2015年11月9日 下午4:51:01 修改人：hxf 描述 ：
	 * 
	 */
	private void registerListener() {
		
		spinnerDishType.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				// 通过adapter去获取实例
				DictionaryBean dictionaryBean = spinnerDishTypeAdapter.getItem(position);
				dictionaryBeanList.get(position);
				// 在这里改变GridView的数据源，然后通知GridView的适配器
				dishType = dictionaryBean.getId();
				getDishInfoBean();
				orderDishAdapter.setSelectedItem(-1);// 不加这个的话，重新加载数据源显示时，上次被点击的那个位置还是还会设置透明度70
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

				// 改变图片的透明度
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
	 * 函数名称 : getDictionaryBean 功能描述 : 参数及返回值说明：
	 * 
	 * @return
	 *
	 * 		修改记录： 日期 ：2015年10月23日 下午10:23:46 修改人：hxf 描述 ：
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

}
