/************************************************************
 *	版权所有  (c)2011,   hxf<p>	
 *  文件名称	：TopView.java<p>
 *
 *  创建时间	：2015年10月27日 下午9:50:08 
 *  当前版本号：v1.0
 ************************************************************/
package com.example.orderservice.view;

import com.example.orderservice.R;
import com.example.orderservice.util.ManageActivity;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

/************************************************************
 *  内容摘要	：<p>
 *
 *  作者	：hxf
 *  创建时间	：2015年10月27日 下午9:50:08 
 *  当前版本号：v1.0
 *  历史记录	:
 *  	日期	: 2015年10月27日 下午9:50:08 	修改人：
 *  	描述	:
 ************************************************************/
public class TopView extends RelativeLayout {

	

	/**
	 * 构造函数：TopView
	 * 函数功能:
	 * 参数说明：
	 * 		@param context
	 */
	public TopView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public TopView(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		View view = LayoutInflater.from(context).inflate(R.layout.top_layout, this, true);
		
		
		View tvOff = view.findViewById(R.id.id_tv_off);
		
		tvOff.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				//把所以已打开的Activity对象关闭
				ManageActivity.finishAll();
				
			}
		});
	}
	
	

}
