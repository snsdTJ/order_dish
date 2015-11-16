/************************************************************
 *	版权所有  (c)2011,   hxf<p>	
 *  文件名称	：OrderDishAdapter.java<p>
 *
 *  创建时间	：2015年10月17日 下午6:55:30 
 *  当前版本号：v1.0
 ************************************************************/
package com.example.orderservice.adapter;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import com.example.orderservice.R;
import com.example.orderservice.model.DishInfoBean;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/************************************************************
 *  内容摘要	：<p>
 *
 *  作者	：hxf
 *  创建时间	：2015年10月17日 下午6:55:30 
 *  当前版本号：v1.0
 *  历史记录	:
 *  	日期	: 2015年10月17日 下午6:55:30 	修改人：
 *  	描述	:
 ************************************************************/
public class OrderDishAdapter extends ArrayAdapter<DishInfoBean> {
	
	private int resourceId;
	private Context context;
	
	//被选中的标志
	private int clickItem = -1;
	
	public void setSelectedItem(int position){
		clickItem = position;
	}
	
	/**
	 * 构造函数：OrderDishAdapter
	 * 函数功能:
	 * 参数说明：
	 * 		@param context
	 * 		@param resource
	 * 		@param objects
	 */
	public OrderDishAdapter(Context context, int resource, List<DishInfoBean> dishInfoList) {
		super(context, resource, dishInfoList);
		this.resourceId = resource;
		this.context = context;
		
	}
	
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		DishInfoBean dishInfoBean = getItem(position);
		if (convertView == null) {
			convertView = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
		}
		ImageView imageView = (ImageView) convertView.findViewById(R.id.iv_dish_photo);
		TextView textView = (TextView) convertView.findViewById(R.id.tv_dish_info);
		TextView tvDishPrice = (TextView) convertView.findViewById(R.id.tv_dish_price);

		//-----------------------------------------》
		//获取图片路径，把路径保存在数据库，然后从数据库取出路径，获得图片的输入流，用BitmapFactory解析成Bitmap 这个过程出现问题
		//-----------------------------------------》
		
		//从dishInfoBean中获取图片的路径（该路径是存在数据库PHOTO字段中）
		String photoPath = dishInfoBean.getPhoto();	
		//用Bitmap解析输入流获得Bitmap对象
		Bitmap bitmap = getImageFromAssetsFile(photoPath);	
		//把图片设置给ImageView
		imageView.setImageBitmap(bitmap);
		
		//设置选中时图片的透明度
		if (clickItem == position) {
			imageView.setImageAlpha(70);
		}else{ //如果不加else语句的话，那么选中一个图片后，再选择一个图片时，前那个图片透明度还是被设置为70，也就是说被选中后不能恢复
			imageView.setImageAlpha(255);
		}
		
		
		
		//从dishInfoBean中获得CURRENCY_TYPE所对应的T_TABLE_DICTIONARY表中的货币名称
		String currencyTypeName = dishInfoBean.getCurrencyTypeName();
		//获取菜的价格
		float price = dishInfoBean.getPrice();
		//获取菜的名字
		String dishName = dishInfoBean.getName();
		//从dishInfoBean中获得UNIT所对应的T_TABLE_DICTIONARY表中的菜的单位
		String dishUnitName = dishInfoBean.getUnitName();
		
		textView.setText(dishName);
		tvDishPrice.setText(price+currencyTypeName+"/"+dishUnitName);
		return convertView;
	}
	
	/**
     * 从Assets中读取图片
     */
    private Bitmap getImageFromAssetsFile(String photoPath)
    {
        Bitmap image = null;
        //Context.getResources() 返回 Resources对象
        AssetManager am = context.getResources().getAssets();
        try
        {	
            InputStream is = am.open(photoPath);//使用路径获得输入流
            image = BitmapFactory.decodeStream(is);
            is.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return image;

    }

}
