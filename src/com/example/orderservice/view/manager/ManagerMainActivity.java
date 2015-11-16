/************************************************************
 *	版权所有  (c)2011,   hxf<p>	
 *  文件名称	：ManagerMainActivity.java<p>
 *
 *  创建时间	：2015年10月24日 下午2:53:02 
 *  当前版本号：v1.0
 ************************************************************/
package com.example.orderservice.view.manager;

import com.example.orderservice.R;
import com.example.orderservice.view.BaseActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/************************************************************
 * 内容摘要 ：
 * <p>
 *
 * 作者 ：hxf 创建时间 ：2015年10月24日 下午2:53:02 当前版本号：v1.0 历史记录 : 日期 : 2015年10月24日
 * 下午2:53:02 修改人： 描述 :
 ************************************************************/
public class ManagerMainActivity extends BaseActivity {

	private TextView tvManagerInfo;

	private ImageView ivSalaryCount;
	// 经理编码
	private String managerAccount;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		// 去掉标题栏
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_manager_main);

		// 获取通过Intent传递过来的数据
		Intent getIntent = getIntent();
		managerAccount = getIntent.getStringExtra("ManagerAccount");

		// 初始化布局组件
		initComponent();

		// 设置监听器
		registerListener();
	}
	

	/**
	 * 函数名称 : registerListener 功能描述 : 参数及返回值说明：
	 *
	 * 修改记录： 日期 ：2015年10月24日 下午6:36:20 修改人：hxf 描述 ：
	 * 
	 */
	private void registerListener() {

		ivSalaryCount.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(ManagerMainActivity.this, ManagerCountActivity.class);
				intent.putExtra("ManagerAccount", managerAccount);
				startActivity(intent);
			}
		});
	}

	/**
	 * 函数名称 : initComponent 功能描述 : 参数及返回值说明：
	 *
	 * 修改记录： 日期 ：2015年10月24日 下午2:15:20 修改人：hxf 描述 ：
	 * 
	 */
	private void initComponent() {

		tvManagerInfo = (TextView) findViewById(R.id.tv_manager_info);
		ivSalaryCount = (ImageView) findViewById(R.id.iv_salary_count);

		tvManagerInfo.setText("经理： " + managerAccount);
	}

}
