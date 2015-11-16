/************************************************************
 *	版权所有  (c)2011,   hxf<p>	
 *  文件名称	：FileHelper.java<p>
 *
 *  创建时间	：2015年10月23日 下午4:23:46 
 *  当前版本号：v1.0
 ************************************************************/
package com.example.orderservice.util;

import java.io.IOException;
import java.io.InputStream;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/************************************************************
 *  内容摘要	：<p>
 *
 *  作者	：hxf
 *  创建时间	：2015年10月23日 下午4:23:46 
 *  当前版本号：v1.0
 *  历史记录	:
 *  	日期	: 2015年10月23日 下午4:23:46 	修改人：
 *  	描述	:
 ************************************************************/
public class FileHelper {
	
	/**
     * 从Assets中读取图片
     */
    public static Bitmap getImageFromAssetsFile(Context context, String photoPath)
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
