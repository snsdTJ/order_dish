/************************************************************
 *	版权所有  (c)2011,   hxf<p>	
 *  文件名称	：AdminMainActivity.java<p>
 *
 *  创建时间	：2015年10月23日 上午10:04:23 
 *  当前版本号：v1.0
 ************************************************************/
package com.example.orderservice.view.admin;

import com.example.orderservice.R;
import com.example.orderservice.control.AdminOnClickListener;
import com.example.orderservice.view.BaseActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;

/************************************************************
 * 内容摘要 ：
 * <p>
 *
 * 作者 ：hxf 创建时间 ：2015年10月23日 上午10:04:23 当前版本号：v1.0 历史记录 : 日期 : 2015年10月23日
 * 上午10:04:23 修改人： 描述 :
 ************************************************************/
public class AdminMainActivity extends BaseActivity {

	private ImageView ivAddDish, ivManageDish;

	private AdminOnClickListener adminOnClickListener = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		// 去掉标题栏
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_admin_main);
		
		// 初始化组件
		initComponent();

		// 初始化监听器s --- Listeners
		initListeners();

		// 设置监听器
		registerListener();
	}

	
	/**
	 *  函数名称 : registerListener
	 *  功能描述 :  
	 *  参数及返回值说明：
	 *
	 *  修改记录：
	 *  	日期 ：2015年11月9日 下午4:48:17	修改人：hxf
	 *  	描述	：
	 * 					
	 */
	private void registerListener() {
		// TODO Auto-generated method stub
		ivAddDish.setOnClickListener(adminOnClickListener);
		ivManageDish.setOnClickListener(adminOnClickListener);
	}

	/**
	 *  函数名称 : initListeners
	 *  功能描述 :  
	 *  参数及返回值说明：
	 *
	 *  修改记录：
	 *  	日期 ：2015年11月9日 下午4:46:59	修改人：hxf
	 *  	描述	：
	 * 					
	 */
	private void initListeners() {
		// TODO Auto-generated method stub
		adminOnClickListener = new AdminOnClickListener(this);
	}

	/**
	 *  函数名称 : initComponent
	 *  功能描述 :  
	 *  参数及返回值说明：
	 *
	 *  修改记录：
	 *  	日期 ：2015年11月9日 下午4:46:35	修改人：hxf
	 *  	描述	：
	 * 					
	 */
	private void initComponent() {
		// TODO Auto-generated method stub
		ivAddDish = (ImageView) findViewById(R.id.iv_add_dish);
		ivManageDish = (ImageView) findViewById(R.id.iv_manage_dish);
	}

}
