/************************************************************
 *	��Ȩ����  (c)2011,   hxf<p>	
 *  �ļ�����	��LoginActivity.java<p>
 *
 *  ����ʱ��	��2015��10��14�� ����9:25:48 
 *  ��ǰ�汾�ţ�v1.0
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
 * ����ժҪ ��
 * <p>
 *
 * ���� ��hxf ����ʱ�� ��2015��10��14�� ����9:25:48 ��ǰ�汾�ţ�v1.0 ��ʷ��¼ : ���� : 2015��10��14��
 * ����9:25:48 �޸��ˣ� ���� :
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
		

		// ��ʼ�����
		initComponent();

		//��ʼ��SharedPreferences
		initSharedPreferences();

		// ���ü�����
		registerListener();	

	}

	/**
	 *  �������� : initSharedPreferences
	 *  �������� :  
	 *  ����������ֵ˵����
	 *
	 *  �޸ļ�¼��
	 *  	���� ��2015��11��9�� ����4:37:34	�޸��ˣ�hxf
	 *  	����	��
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
	 *  �������� : registerListener
	 *  �������� :  
	 *  ����������ֵ˵����
	 *
	 *  �޸ļ�¼��
	 *  	���� ��2015��11��9�� ����4:33:02	�޸��ˣ�hxf
	 *  	����	��
	 * 					
	 */
	private void registerListener() {
		// TODO Auto-generated method stub
		btnLogin.setOnClickListener(this);
	}

	/**
	 *  �������� : initComponent
	 *  �������� :  
	 *  ����������ֵ˵����
	 *
	 *  �޸ļ�¼��
	 *  	���� ��2015��11��9�� ����4:32:56	�޸��ˣ�hxf
	 *  	����	��
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
	 * �������� ��onClick �������� �� ����˵�� ��
	 * 
	 * @param v
	 *            ����ֵ��
	 * 
	 *            �޸ļ�¼�� ���� ��2015��10��14�� ����10:15:18 �޸��ˣ�hxf ���� ��
	 * 
	 */
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.login:
			// ��ȡ�༭�����˺��ı��������ı�
			String account = etAccount.getText().toString();
			String password = etPassword.getText().toString();

			// ʵ������Ҫ�����ݿ��е��û����������������û��������������֤
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
				Log.d("LoginActivity", "��ת������Աҳ��");

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
	 * �������� : saveLoginInfo �������� : �����login��ť�����Ҽ���˺���������ȷ�� �����ס���븴ѡ��ѡ�к󣬿���ͨ��
	 * ����������˺ź����뱣�浽SharedPreference�ļ��У��Ա��´ε���ʱ�Զ������˺����롣 ����������ֵ˵����
	 *
	 * �޸ļ�¼�� ���� ��2015��10��14�� ����10:36:08 �޸��ˣ�hxf ���� ��
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
