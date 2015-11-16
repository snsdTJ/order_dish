/************************************************************
 *	版权所有  (c)2011,   hxf<p>	
 *  文件名称	：ManageActivity.java<p>
 *
 *  创建时间	：2015年11月1日 下午1:12:25 
 *  当前版本号：v1.0
 ************************************************************/
package com.example.orderservice.util;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.util.Log;

/************************************************************
 *  内容摘要	：<p>
 *
 *  作者	：hxf
 *  创建时间	：2015年11月1日 下午1:12:25 
 *  当前版本号：v1.0
 *  历史记录	:
 *  	日期	: 2015年11月1日 下午1:12:25 	修改人：
 *  	描述	:
 ************************************************************/
public class ManageActivity {
	
private static List<Activity> listActivity = new ArrayList<Activity>();
	
	public static void  addActivity(Activity activity){
		listActivity.add(activity);
	}
	
	public static void removeActivity(Activity activity){
		listActivity.remove(activity);
	}
	
	public static void finishAll(){
		for(Activity activity: listActivity){
			if (!activity.isFinishing()) {
				Log.i("关闭所有页面  ", "关闭所有页面");
				activity.finish();
				
			}
		}
		Log.i("还剩多少活动页面在集合 ", String.valueOf(listActivity.size()));
	}

}
