/************************************************************
 *	版权所有  (c)2011,   hxf<p>	
 *  文件名称	：ManagerCountActivityBean.java<p>
 *
 *  创建时间	：2015年10月29日 下午10:29:23 
 *  当前版本号：v1.0
 ************************************************************/
package com.example.orderservice.model;

import java.util.List;

import com.example.orderservice.dao.ManagerCountActivityDao;

import android.content.Context;

/************************************************************
 * 内容摘要 ：这个类是ManagerCountActivity活动页面的实体类
 *
 * 作者 ：hxf 创建时间 ：2015年10月29日 下午10:29:23 当前版本号：v1.0 历史记录 : 日期 : 2015年10月29日
 * 下午10:29:23 修改人： 描述 :
 ************************************************************/
public class ManagerCountActivityBean {

	private List<OrderBean> orderBeanList;

	private float sumTurnover;

	public List<OrderBean> getOrderBeanList() {
		return orderBeanList;
	}

	// 输入开始日期和结束日期，然后调用ManagerCountActivityDAO类中的方法读取数据库中数据，存储在集合中
	public boolean setOrderBeanList(String startDate, String endDate, Context context) {

		orderBeanList = ManagerCountActivityDao.getInstance(context).getOrderBeanList(startDate, endDate);
		
		if (orderBeanList==null) {
			return false;
		}

		return true;
	}

	public float getSumTurnover() {
		return sumTurnover;
	}

	public boolean setSumTurnover() {
		if (orderBeanList != null && !orderBeanList.isEmpty()) {
			for (OrderBean ob : orderBeanList) {
				sumTurnover += ob.getTotalMoney();
			}
			return true;
		}
		return false;
	}

}
