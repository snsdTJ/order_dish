/************************************************************
 *	��Ȩ����  (c)2011,   hxf<p>	
 *  �ļ�����	��FileHelper.java<p>
 *
 *  ����ʱ��	��2015��10��23�� ����4:23:46 
 *  ��ǰ�汾�ţ�v1.0
 ************************************************************/
package com.example.orderservice.util;

import java.io.IOException;
import java.io.InputStream;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/************************************************************
 *  ����ժҪ	��<p>
 *
 *  ����	��hxf
 *  ����ʱ��	��2015��10��23�� ����4:23:46 
 *  ��ǰ�汾�ţ�v1.0
 *  ��ʷ��¼	:
 *  	����	: 2015��10��23�� ����4:23:46 	�޸��ˣ�
 *  	����	:
 ************************************************************/
public class FileHelper {
	
	/**
     * ��Assets�ж�ȡͼƬ
     */
    public static Bitmap getImageFromAssetsFile(Context context, String photoPath)
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
