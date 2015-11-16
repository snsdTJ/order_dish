/************************************************************
 *	��Ȩ����  (c)2011,   hxf<p>	
 *  �ļ�����	��OrderDishAdapter.java<p>
 *
 *  ����ʱ��	��2015��10��17�� ����6:55:30 
 *  ��ǰ�汾�ţ�v1.0
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
 *  ����ժҪ	��<p>
 *
 *  ����	��hxf
 *  ����ʱ��	��2015��10��17�� ����6:55:30 
 *  ��ǰ�汾�ţ�v1.0
 *  ��ʷ��¼	:
 *  	����	: 2015��10��17�� ����6:55:30 	�޸��ˣ�
 *  	����	:
 ************************************************************/
public class OrderDishAdapter extends ArrayAdapter<DishInfoBean> {
	
	private int resourceId;
	private Context context;
	
	//��ѡ�еı�־
	private int clickItem = -1;
	
	public void setSelectedItem(int position){
		clickItem = position;
	}
	
	/**
	 * ���캯����OrderDishAdapter
	 * ��������:
	 * ����˵����
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

		//-----------------------------------------��
		//��ȡͼƬ·������·�����������ݿ⣬Ȼ������ݿ�ȡ��·�������ͼƬ������������BitmapFactory������Bitmap ������̳�������
		//-----------------------------------------��
		
		//��dishInfoBean�л�ȡͼƬ��·������·���Ǵ������ݿ�PHOTO�ֶ��У�
		String photoPath = dishInfoBean.getPhoto();	
		//��Bitmap�������������Bitmap����
		Bitmap bitmap = getImageFromAssetsFile(photoPath);	
		//��ͼƬ���ø�ImageView
		imageView.setImageBitmap(bitmap);
		
		//����ѡ��ʱͼƬ��͸����
		if (clickItem == position) {
			imageView.setImageAlpha(70);
		}else{ //�������else���Ļ�����ôѡ��һ��ͼƬ����ѡ��һ��ͼƬʱ��ǰ�Ǹ�ͼƬ͸���Ȼ��Ǳ�����Ϊ70��Ҳ����˵��ѡ�к��ָܻ�
			imageView.setImageAlpha(255);
		}
		
		
		
		//��dishInfoBean�л��CURRENCY_TYPE����Ӧ��T_TABLE_DICTIONARY���еĻ�������
		String currencyTypeName = dishInfoBean.getCurrencyTypeName();
		//��ȡ�˵ļ۸�
		float price = dishInfoBean.getPrice();
		//��ȡ�˵�����
		String dishName = dishInfoBean.getName();
		//��dishInfoBean�л��UNIT����Ӧ��T_TABLE_DICTIONARY���еĲ˵ĵ�λ
		String dishUnitName = dishInfoBean.getUnitName();
		
		textView.setText(dishName);
		tvDishPrice.setText(price+currencyTypeName+"/"+dishUnitName);
		return convertView;
	}
	
	/**
     * ��Assets�ж�ȡͼƬ
     */
    private Bitmap getImageFromAssetsFile(String photoPath)
    {
        Bitmap image = null;
        //Context.getResources() ���� Resources����
        AssetManager am = context.getResources().getAssets();
        try
        {	
            InputStream is = am.open(photoPath);//ʹ��·�����������
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
