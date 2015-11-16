/************************************************************
 *	版权所有  (c)2011,   hxf<p>	
 *  文件名称	：TableInfoActivity.java<p>
 *
 *  创建时间	：2015年10月16日 下午8:58:09 
 *  当前版本号：v1.0
 ************************************************************/
package com.example.orderservice.view.waiter;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.example.orderservice.R;
import com.example.orderservice.dao.OrderDao;
import com.example.orderservice.dao.TableInfoDao;
import com.example.orderservice.dao.TableSizeDao;
import com.example.orderservice.model.OrderBean;
import com.example.orderservice.model.TableInfoBean;
import com.example.orderservice.view.BaseActivity;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

/************************************************************
 * 内容摘要 ：
 * <p>
 *
 * 作者 ：何雪峰 
 * 创建时间 ：2015年10月16日 下午8:58:09 当前版本号：v1.0 历史记录 : 日期 : 2015年10月16日
 * 下午8:58:09 修改人： 描述 :
 ************************************************************/
public class TableInfoActivity extends BaseActivity {
	private TextView tvWaiterAccount;
	private TextView tvTableNo;
	private TextView tvTableDescribe;
	private TextView tvTableSize;
	private Button btnOk;
	private Button btnCancel;
	private TableInfoBean tableInfoBean;

	private String waiterAccount;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_table_info);

		// 获取从Intent传递过来的数据。
		getDataFromIntent();

		// 初始化布局组件
		initComponent();

		// 设置监听器
		registerListener();
	}

	/**
	 * 函数名称 : getDataFromIntent 功能描述 : 参数及返回值说明：
	 *
	 */
	private void getDataFromIntent() {

		// 获取传递过来的TableInfoBean实例
		Intent intentPass = getIntent();
		tableInfoBean = (TableInfoBean) intentPass.getSerializableExtra("tableInfoBean");
		waiterAccount = intentPass.getStringExtra("WaiterAccount");
	}

	/**
	 * 函数名称 : initComponent 功能描述 : 参数及返回值说明：
	 *
	 */
	private void initComponent() {
		tvWaiterAccount = (TextView) findViewById(R.id.text_view);
		tvTableNo = (TextView) findViewById(R.id.tv_table_no);
		tvTableDescribe = (TextView) findViewById(R.id.tv_table_describe);
		tvTableSize = (TextView) findViewById(R.id.tv_table_size);
		btnOk = (Button) findViewById(R.id.btn_ok);
		btnCancel = (Button) findViewById(R.id.btn_cancel);
		
		tvWaiterAccount.setText(waiterAccount);

		// 通过tableInfoBean.getSizeId()获得T_TABLE_INFO表的SIZE_ID
		int sizeId = tableInfoBean.getSizeId();
		// 然后从T_TABLE_SIZE表查出该桌可以坐多少人
		int size = TableSizeDao.getInstance(this).querySize(sizeId);

		tvTableNo.setText(tableInfoBean.getNo() + "号桌 ---》 未开桌");
		tvTableDescribe.setText(tableInfoBean.getDescribe());
		tvTableSize.setText(size + "人桌");

	}

	/**
	 * 函数名称 : registerListener 功能描述 : 参数及返回值说明：
	 *
	 */
	private void registerListener() {
		btnOk.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
				OrderBean orderBean = new OrderBean();
				
				orderBean.setTableInfoId(tableInfoBean.getId());

				int waiterId = new Intent().getIntExtra("waiterId", 0);
				orderBean.setWaiterId(waiterId);

				Date date = new Date();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String beginDateTime = sdf.format(date);
				orderBean.setBeginDateTime(beginDateTime);

				orderBean.setIsPay(10);
				
				// 往订单表T_ORDER中插入一条订单，只要插入tableInfoId，服务员的id，开始时间，付款状态这四个数据
				OrderDao.getInstance(TableInfoActivity.this).saveOrder(orderBean);

				// 更新T_TABLE_INFO表的对应桌子的STATE字段
				tableInfoBean.setState(2);
				TableInfoDao.getInstance(TableInfoActivity.this).modifyTableInfo(tableInfoBean);

				// 跳转到OrderDishActivity页，记得要把TableInfoBean对象传过去
				Intent intent = new Intent(TableInfoActivity.this, OrderDishActivity.class);
				intent.putExtra("TableInfoBean", tableInfoBean);

				// 注意：这里不能直接把上面创建的OrderBean对象当成订单进行传递，因为自己创建OrderBean对象时不能给该对象设置ID值
				// 由于同一个桌子所对应的 未付款的 订单只有一个，
				// 所以：要么在这里根据桌号获得 该桌所对应的 未付款的 OrderBean对象 用来传递，
				// 要么传递桌号，然后在之后通过桌号获得 该桌所对应的 未付款的 OrderBean对象
				// intent.putExtra("OrderBean", orderBean);

				startActivity(intent);
			}
		});
		
		btnCancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}

}
