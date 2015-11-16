/************************************************************
 *	版权所有  (c)2011,   hxf<p>	
 *  文件名称	：GridViewImageAdapter.java<p>
 *
 *  创建时间	：2015年10月23日 下午3:26:32 
 *  当前版本号：v1.0
 ************************************************************/
package com.example.orderservice.adapter;

import java.io.IOException;
import java.io.InputStream;

import com.example.orderservice.R;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

/************************************************************
 *  内容摘要	：<p>
 *
 *  作者	：hxf
 *  创建时间	：2015年10月23日 下午3:26:32 
 *  当前版本号：v1.0
 *  历史记录	:
 *  	日期	: 2015年10月23日 下午3:26:32 	修改人：
 *  	描述	:
 ************************************************************/
public class GridViewImageAdapter extends ArrayAdapter<String> {
	
	private int clickNumber = -1;
	
	private int resource;
	private Context context;
	
	/**
	 * 构造函数：GridViewImageAdapter
	 * 函数功能:
	 * 参数说明：
	 * 		@param context
	 * 		@param resource
	 * 		@param objects
	 */
	public GridViewImageAdapter(Context context, int resource, String[] objects) {
		super(context, resource, objects);
		this.resource = resource;
		this.context = context;
	}
	
	//该方法用于给字段clickNumber赋值；
	public void setSelection(int position){
		clickNumber = position;
	}
	
	/**
	 *  函数名称 ：getView
	 *  功能描述 ：  
	 *  参数说明 ：
	 *  	@param position
	 *  	@param convertView
	 *  	@param parent
	 *  	@return
	 *  返回值：
	 *  	
	 *  修改记录：
	 *  日期 ：2015年10月23日 下午3:27:26	修改人：hxf
	 *  描述 ：
	 * 					
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		String photoPath = getItem(position);
		if (convertView == null) {
			convertView = LayoutInflater.from(getContext()).inflate(resource, parent, false);
		}
		ImageView imageView = (ImageView) convertView.findViewById(R.id.image_view);
		Resources res = context.getResources();
		AssetManager am = res.getAssets();
		InputStream is = null;
		try {
			is = am.open(photoPath);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Bitmap bitmap = BitmapFactory.decodeStream(is);
		imageView.setImageBitmap(bitmap);
		if (clickNumber == position) {
			imageView.setImageAlpha(100);
		}else {
			imageView.setImageAlpha(255);
		}
		
		return convertView;
	}

}
