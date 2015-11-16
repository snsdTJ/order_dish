/************************************************************
 *	版权所有  (c)2011,   hxf<p>	
 *  文件名称	：BaseActivity.java<p>
 *
 *  创建时间	：2015年11月1日 下午1:01:10 
 *  当前版本号：v1.0
 ************************************************************/
package com.example.orderservice.view;

import com.example.orderservice.util.ManageActivity;

import android.app.Activity;
import android.os.Bundle;

/************************************************************
 *  内容摘要	：<p>
 *
 *  作者	：hxf
 *  创建时间	：2015年10月27日 下午1:01:10 
 *  当前版本号：v1.0
 *  历史记录	:
 *  	日期	: 2015年10月27日 下午1:01:10	修改人：
 *  	描述	:
 ************************************************************/
public class BaseActivity extends Activity {
	
	/**
	 *  函数名称 ：onCreate
	 *  功能描述 ：  
	 *  参数说明 ：
	 *  	@param savedInstanceState
	 *  返回值：
	 *  	
	 *  修改记录：
	 *  日期 ：2015年10月27日 下午1:01:56	修改人：hxf
	 *  描述 ：
	 * 					
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		ManageActivity.addActivity(this);
	}
	
	/**
	 *  函数名称 ：onDestroy
	 *  功能描述 ：  
	 *  参数说明 ：
	 *  返回值：
	 *  	
	 *  修改记录：
	 *  日期 ：2015年10月27日 下午1:01:56		修改人：hxf
	 *  描述 ：
	 * 					
	 */
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		ManageActivity.removeActivity(this);
	}

}
