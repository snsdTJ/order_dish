/************************************************************
 *	版权所有  (c)2011,   hxf<p>	
 *  文件名称	：AdminOnClickListener.java<p>
 *
 *  创建时间	：2015年10月23日 上午10:46:37 
 *  当前版本号：v1.0
 ************************************************************/
package com.example.orderservice.control;

import com.example.orderservice.R;
import com.example.orderservice.view.admin.AdminAddDishActivity;
import com.example.orderservice.view.admin.AdminManageDishActivity;
import com.example.orderservice.view.LoginActivity;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;

/************************************************************
 * 内容摘要 ：
 * <p>
 *
 * 作者 ：hxf 创建时间 ：2015年10月23日 上午10:46:37 当前版本号：v1.0 历史记录 : 日期 : 2015年10月23日
 * 上午10:46:37 修改人： 描述 :
 ************************************************************/
public class AdminOnClickListener implements OnClickListener {
	
	private Context context;
	
	public AdminOnClickListener(Context context){
		this.context = context;
	}

	/**
	 * 函数名称 ：onClick 功能描述 ： 参数说明 ：
	 * 
	 * @param v
	 *            返回值：
	 * 
	 *            修改记录： 日期 ：2015年10月23日 上午10:46:37 修改人：hxf 描述 ：
	 * 
	 */
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_add_dish:
			Intent addDishIntent = new Intent(context, AdminAddDishActivity.class);
			context.startActivity(addDishIntent);
			
			break;
		case R.id.iv_manage_dish:
			Intent manageDishIntent = new Intent(context, AdminManageDishActivity.class);
			context.startActivity(manageDishIntent);
			break;
		


		default:
			break;
		}

	}

}
