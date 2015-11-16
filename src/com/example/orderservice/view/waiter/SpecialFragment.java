/************************************************************
 *	版权所有  (c)2011,   hxf<p>	
 *  文件名称	：ChooseTableFragment.java<p>
 *
 *  创建时间	：2015年10月26日 下午4:30:13 
 *  当前版本号：v1.0
 ************************************************************/
package com.example.orderservice.view.waiter;

import com.example.orderservice.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/************************************************************
 *  内容摘要	：<p>
 *
 *  作者	：hxf
 *  创建时间	：2015年10月26日 下午4:30:13 
 *  当前版本号：v1.0
 *  历史记录	:
 *  	日期	: 2015年10月26日 下午4:30:13 	修改人：
 *  	描述	:
 ************************************************************/
public class SpecialFragment extends Fragment {
	

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		View view = inflater.inflate(R.layout.fragment_special, container, false);
		
		TextView textView = (TextView) view.findViewById(R.id.text_view);
		
		textView.setText("本店特色菜： 芝士黄油古巴龙虾");
		
		return view;
	}

}
