/************************************************************
 *	��Ȩ����  (c)2011,   hxf<p>	
 *  �ļ�����	��GridViewImageAdapter.java<p>
 *
 *  ����ʱ��	��2015��10��23�� ����3:26:32 
 *  ��ǰ�汾�ţ�v1.0
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
 *  ����ժҪ	��<p>
 *
 *  ����	��hxf
 *  ����ʱ��	��2015��10��23�� ����3:26:32 
 *  ��ǰ�汾�ţ�v1.0
 *  ��ʷ��¼	:
 *  	����	: 2015��10��23�� ����3:26:32 	�޸��ˣ�
 *  	����	:
 ************************************************************/
public class GridViewImageAdapter extends ArrayAdapter<String> {
	
	private int clickNumber = -1;
	
	private int resource;
	private Context context;
	
	/**
	 * ���캯����GridViewImageAdapter
	 * ��������:
	 * ����˵����
	 * 		@param context
	 * 		@param resource
	 * 		@param objects
	 */
	public GridViewImageAdapter(Context context, int resource, String[] objects) {
		super(context, resource, objects);
		this.resource = resource;
		this.context = context;
	}
	
	//�÷������ڸ��ֶ�clickNumber��ֵ��
	public void setSelection(int position){
		clickNumber = position;
	}
	
	/**
	 *  �������� ��getView
	 *  �������� ��  
	 *  ����˵�� ��
	 *  	@param position
	 *  	@param convertView
	 *  	@param parent
	 *  	@return
	 *  ����ֵ��
	 *  	
	 *  �޸ļ�¼��
	 *  ���� ��2015��10��23�� ����3:27:26	�޸��ˣ�hxf
	 *  ���� ��
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
