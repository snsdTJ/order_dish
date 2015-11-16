/************************************************************
 *	版权所有  (c)2011,   hxf<p>	
 *  文件名称	：LoginActivity.java<p>
 *
 *  创建时间	：2015年10月14日 下午9:25:48 
 *  当前版本号：v1.0
 ************************************************************/
package com.example.orderservice.view;

import com.example.orderservice.MainActivity;
import com.example.orderservice.R;
import com.example.orderservice.view.admin.AdminMainActivity;
import com.example.orderservice.view.manager.ManagerMainActivity;
import com.example.orderservice.view.waiter.WaiterMainActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

/************************************************************
 * 内容摘要 ：
 * <p>
 *
 * 作者 ：hxf 创建时间 ：2015年10月14日 下午9:25:48 当前版本号：v1.0 历史记录 : 日期 : 2015年10月14日
 * 下午9:25:48 修改人： 描述 :
 ************************************************************/
public class LoginActivity extends Activity implements OnClickListener {

	private EditText etAccount, etPassword;

	private CheckBox cbRememberPassword;

	private Button btnLogin;

	private SharedPreferences sp;

	private SharedPreferences.Editor sp_editor;

	private boolean isRemember;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		

		// 初始化组件
		initComponent();

		//初始化SharedPreferences
		initSharedPreferences();

		// 设置监听器
		registerListener();	

	}

	/**
	 *  函数名称 : initSharedPreferences
	 *  功能描述 :  
	 *  参数及返回值说明：
	 *
	 *  修改记录：
	 *  	日期 ：2015年11月9日 下午4:37:34	修改人：hxf
	 *  	描述	：
	 * 					
	 */
	private void initSharedPreferences() {
		// TODO Auto-generated method stub
		sp = getSharedPreferences("pref_login", MODE_PRIVATE);
		sp_editor = sp.edit();

		isRemember = sp.getBoolean("rememberPassword", false);
		
		if (isRemember) {
			String account = sp.getString("account", "");
			String password = sp.getString("password", "");

			etAccount.setText(account);
			etPassword.setText(password);

			cbRememberPassword.setChecked(true);
		}
	}

	/**
	 *  函数名称 : registerListener
	 *  功能描述 :  
	 *  参数及返回值说明：
	 *
	 *  修改记录：
	 *  	日期 ：2015年11月9日 下午4:33:02	修改人：hxf
	 *  	描述	：
	 * 					
	 */
	private void registerListener() {
		// TODO Auto-generated method stub
		btnLogin.setOnClickListener(this);
	}

	/**
	 *  函数名称 : initComponent
	 *  功能描述 :  
	 *  参数及返回值说明：
	 *
	 *  修改记录：
	 *  	日期 ：2015年11月9日 下午4:32:56	修改人：hxf
	 *  	描述	：
	 * 					
	 */
	private void initComponent() {
		// TODO Auto-generated method stub
		etAccount = (EditText) findViewById(R.id.account);
		etPassword = (EditText) findViewById(R.id.password);
		cbRememberPassword = (CheckBox) findViewById(R.id.remember_pass);
		btnLogin = (Button) findViewById(R.id.login);
	}

	/**
	 * 函数名称 ：onClick 功能描述 ： 参数说明 ：
	 * 
	 * @param v
	 *            返回值：
	 * 
	 *            修改记录： 日期 ：2015年10月14日 下午10:15:18 修改人：hxf 描述 ：
	 * 
	 */
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.login:
			// 获取编辑框中账号文本和密码文本
			String account = etAccount.getText().toString();
			String password = etPassword.getText().toString();

			// 实际上是要用数据库中的用户名和密码和输入的用户名和密码进行验证
			if ("100001".equals(account) && "100001".equals(password)) {
				saveLoginInfo(account, password);
				Intent intent = new Intent(this, AdminMainActivity.class);
				startActivity(intent);

			} else if ("100002".equals(account) && "100002".equals(password)) {
				saveLoginInfo(account, password);
				Intent intent = new Intent(this, ManagerMainActivity.class);
				intent.putExtra("ManagerAccount", account);
				startActivity(intent);

			} else if ("cashier".equals(account) && "100003".equals(password)) {
				saveLoginInfo(account, password);
				Log.d("LoginActivity", "跳转到收银员页面");

			} else if ("100004".equals(account) && "100004".equals(password)) {
				saveLoginInfo(account, password);
				Intent intent = new Intent(this, WaiterMainActivity.class);
				intent.putExtra("waiterId", 4);
				intent.putExtra("waiterAccount", account);
				startActivity(intent);
			} else {
				Toast.makeText(this, "account or password is invalid", Toast.LENGTH_SHORT).show();
			}
			break;

		default:
			break;
		}
	}

	/**
	 * 函数名称 : saveLoginInfo 功能描述 : 当点击login按钮，并且检查账号密码是正确后， 如果记住密码复选框被选中后，可以通过
	 * 这个方法把账号和密码保存到SharedPreference文件中，以便下次登入时自动加载账号密码。 参数及返回值说明：
	 *
	 * 修改记录： 日期 ：2015年10月14日 下午10:36:08 修改人：hxf 描述 ：
	 * 
	 */
	private void saveLoginInfo(String account, String password) {
		if (cbRememberPassword.isChecked()) {
			sp_editor.putBoolean("rememberPassword", true);
			sp_editor.putString("account", account);
			sp_editor.putString("password", password);
		} else {
			sp_editor.clear();
		}
		sp_editor.commit();
	}

}
