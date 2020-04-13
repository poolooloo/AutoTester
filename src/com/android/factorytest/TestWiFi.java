package com.android.factorytest;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
//import android.net.ConnectivityManager;
//import android.net.wifi.WifiConfiguration;
//import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.net.Uri;
import android.content.Context;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Method;

public class TestWiFi extends Activity
{
	private static final String TAG = "factorytest";

	private Button mReturn = null;
	private Button mChangeState = null;
	private Button mTestNet = null;
	private Button mEthTest = null;
	private Button m4GTest = null;
	private Button mbclear = null;
	private TextView tWiFiState = null;
	private TextView tEthState = null;
	private TextView t4GState = null;
	private TextView tTestNetResult = null;
	//	private Button mNext = null;
	private Intent mIntent = null;
	private WifiManager mWifiManager = null;
	//private ConnectivityManager connectivity  = null;

	private int mWifiState = -1;

	//private int networkPreference = -1;
	private int mstate = 0;
	private int musekey = 0;
	private boolean mresult = false;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test_wifi);
		initView();
	}
	DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener()

	{

		public void onClick(DialogInterface dialog, int which)

		{
//        	Bundle bundle = new Bundle();
			switch (which)

			{

				case AlertDialog.BUTTON_POSITIVE:// "成功"按钮退出程序
					Log.i(TAG,"成功");
					mstate = 1;
//            	mextras.putBoolean("sucess", true);
//            	mIntent.putExtras(mextras);
					Intent itok = new Intent();
					itok.putExtra("sucess", true);
					setResult(1, itok);
					if(musekey == 1)
						finish();
//            	else
//            	{
//					mIntent = new Intent(TestColor.this, TestAudioVideo.class);
//					//mIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//					finish();
//					startActivity(mIntent);
//            	}
					break;

				case AlertDialog.BUTTON_NEGATIVE:// "失败"第二个按钮取消对话框
					Log.i(TAG,"失败");
					mstate = 2;
					Intent itfaile = new Intent();
					itfaile.putExtra("sucess", false);
					setResult(1, itfaile);
					if(musekey == 1)
						finish();
//            	else
//            	{
//					mIntent = new Intent(TestColor.this, TestAudioVideo.class);
//					//mIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//					finish();
//					startActivity(mIntent);
//            	}
					break;

				case AlertDialog.BUTTON_NEUTRAL:// "重测"第二个按钮取消对话框
					Log.i(TAG,"重测");
					mstate = 3;
					//finish();
					break;
				default:

					break;

			}

		}

	};
	private void showAlert()
	{
		AlertDialog isExit = new AlertDialog.Builder(this).create();

		// 设置对话框标题

		isExit.setTitle("系统提示");

		// 设置对话框消息

		isExit.setMessage("测试是否成功");

		// 添加选择按钮并注册监听

		isExit.setButton(AlertDialog.BUTTON_POSITIVE,"成功", listener);

		isExit.setButton(AlertDialog.BUTTON_NEGATIVE,"失败", listener);

		isExit.setButton(AlertDialog.BUTTON_NEUTRAL,"重测", listener);

		// 显示对话框

		isExit.show();
	}
	public boolean onKeyDown(int keyCode,KeyEvent keyEvent)
	{
		// TODO Auto-generated method stub
		Log.d(TAG,"get the key "+keyCode);
		if(keyCode==KeyEvent.KEYCODE_BACK )
		{
			// 创建退出对话框
			musekey = 1;
			showAlert();

		}
		return super.onKeyDown(keyCode, keyEvent);
	}
	private void initView()
	{
		mReturn = (Button) findViewById(R.id.but_return);
//		mNext = (Button) findViewById(R.id.but_next);
		mChangeState = (Button) findViewById(R.id.but_changestate);
		mTestNet = (Button)findViewById(R.id.but_openurl);
		//mTestNet.setText("test net");
		mEthTest = (Button)findViewById(R.id.but_wlan);
		m4GTest = (Button)findViewById(R.id.but_4G);
		tTestNetResult = (TextView)findViewById(R.id.EditTestNetResult);
		mbclear = (Button)findViewById(R.id.but_clear);
		tTestNetResult.setEnabled(false);
		//tWiFiState = (TextView)findViewById(R.id.twifi_state);
		//tEthState = (TextView)findViewById(R.id.teth_state);
		//t4GState = (TextView)findViewById(R.id.t4g_state);
		mWifiManager = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
		if(getMobileDataState())
			//t4GState.setText("打开");
			m4GTest.setText("4G:打开");
		else
			m4GTest.setText("4G:关闭");
			//t4GState.setText("关闭");
		//if((mWifiManager.getWifiState() == WifiManager.WIFI_STATE_DISABLED )|| (mWifiManager.getWifiState() == WifiManager.WIFI_STATE_DISABLING) )
			//tWiFiState.setText("关闭");
		//else
			//tWiFiState.setText("打开");

		//mEthTest.setEnabled(false);
		if (mChangeState == null)
			Log.e(TAG, "mChangeState is null ");

		mEthTest.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View v)
			{
				//mWifiManager.setWifiEnabled(false);
				//mEthTest.setEnabled(false);
				startActivity(new Intent(Settings.ACTION_SETTINGS)); //直接进入wifi网络设置界面
			}
		});
		m4GTest.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View v)
			{
				Context context ;
				//mWifiManager.setWifiEnabled(false);
				//mEthTest.setEnabled(false);
				//startActivity(new Intent(Settings.ACTION_SETTINGS)); //直接进入wifi网络设置界面
				if(getMobileDataState())
				{
					Log.e(TAG, "4G is on");
					setMobileDataState(false);

				}
				else
				{
					Log.e(TAG, "4G is off");
					setMobileDataState(true);
				}

			}
		});
		mTestNet.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View v)
			{
				/*Intent intent=new Intent();
				intent.setAction("android.intent.action.VIEW");
				Uri CONTENT_URI_BROWSERS = Uri.parse("http://www.baidu.com");
				intent.setData(CONTENT_URI_BROWSERS);
				intent.setClassName("com.android.browser", "com.android.browser.BrowserActivity");
				startActivity(intent);*/
				String strResult;
				//mTestNet.setText("test net");
				tTestNetResult.setText("");
				mTestNet.setEnabled(false);
				mTestNet.setText("测试中，请勿操作！");
				strResult = isPingSuccess(5,"www.baidu.com");
				tTestNetResult.setText(strResult);
				mTestNet.setEnabled(true);
				mTestNet.setText("测试网络");

			}
		});
		mbclear.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View v)
			{
				tTestNetResult.setText("");

			}
		});
		mReturn.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View v)
			{
				//mIntent = new Intent(TestWiFi.this, MainActivity.class);
//				finish();
				musekey = 1;
				showAlert();
				//startActivity(mIntent);
			}
		});

//		mNext.setOnClickListener(new View.OnClickListener()
//		{
//			public void onClick(View v)
//			{
//				mIntent = new Intent(TestWiFi.this, TestBluetooth.class);
//				//mIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//				finish();
//				startActivity(mIntent);
//			}
//		});

		//mWifiManager = (WifiManager) getSystemService(WIFI_SERVICE);
		//mWifiManager = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
		if (mWifiManager.getWifiState() == WifiManager.WIFI_STATE_ENABLED)
		{
			mWifiState = 1;
			//将当前网络设置为首选网络
			//connectivity = (ConnectivityManager)getSystemService(WIFI_SERVICE);
			//networkPreference  = connectivity .getNetworkPreference();
			//connectivity.setNetworkPreference(networkPreference);
			//mChangeState.setText(R.string.but_wifi_close);
			mChangeState.setText(R.string.test_wifi_mess_enter_wifisetting);
		}
		else if (mWifiManager.getWifiState() == WifiManager.WIFI_STATE_DISABLED)
		{
			mWifiState = 0;
			//mChangeState.setText(R.string.but_wifi_open);
			mChangeState.setText(R.string.test_wifi_mess_enter_wifisetting);
		}
		else
		{
			Log.e(TAG, "Wifi State = " + mWifiManager.getWifiState());
		}
		mChangeState.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View v)
			{
				changeWifiState(mWifiState);
				mWifiState ++;
			}
		});
	}

	public void setMobileDataState(boolean enabled) {
		TelephonyManager telephonyService = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
		try {
			Method setDataEnabled = telephonyService.getClass().getDeclaredMethod("setDataEnabled",boolean.class);
			if (null != setDataEnabled) {
				setDataEnabled.invoke(telephonyService, enabled);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 推断Ping 网址是否返回成功
	 *
	 * @param netAddress
	 * @return
	 */
	public static String isPingSuccess(int pingNum, String m_strForNetAddress) {
		StringBuffer tv_PingInfo = new StringBuffer();
		try {


			Process p = Runtime.getRuntime().exec("su \\ /system/bin/ping -c " + pingNum + " " + m_strForNetAddress); // 10.83.50.111
// m_strForNetAddress
			int status = p.waitFor();
			status = p.exitValue();
			String result ="";
			if (status == 0) {
				result = "success" ;
			} else {
				result = "failed,exit = " + status;
				return result;
			}
			String lost = new String();
			String delay = new String();
			BufferedReader buf = new BufferedReader(new InputStreamReader(p.getInputStream()));


			String str = new String();
// 读出全部信息并显示
			while ((str = buf.readLine()) != null) {
				str = str + "\r\n";
				tv_PingInfo.append(str);
			}


			return tv_PingInfo.toString();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return "";
	}
	public boolean getMobileDataState() {
		TelephonyManager telephonyService = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
		try {
			Method getDataEnabled = telephonyService.getClass().getDeclaredMethod("getDataEnabled");
			if (null != getDataEnabled) {
				return (Boolean) getDataEnabled.invoke(telephonyService);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	private void changeWifiState(int state)
	{
		Log.e(TAG, "mWifiState = " + state);
		Toast.makeText(this, R.string.test_wifi_mess_enter_wifisetting, Toast.LENGTH_LONG).show();
		//startActivity(new Intent(Settings.ACTION_WIRELESS_SETTINGS));//进入无线网络配置界面
		startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS)); //直接进入wifi网络设置界面
		switch (state % 2)
		{
			case 0:
			{
				new Thread()
				{
					public void run()
					{
						Log.e(TAG, "setWifiEnabled(true)");
						mWifiManager.setWifiEnabled(true);
						//mEthTest.setEnabled(true);
					}
				}.start();
//			if(mIntent.getAction().equals(WifiManager.WIFI_STATE_CHANGED_ACTION))
//			{
//				Toast.makeText(this, R.string.test_wifi_mess_state_changed, Toast.LENGTH_LONG).show();
//			}
				while ((mWifiManager.getWifiState() == WifiManager.WIFI_STATE_ENABLING) ||
						(mWifiManager.getWifiState() == WifiManager.WIFI_STATE_ENABLED))
				{
					Toast.makeText(this, R.string.test_wifi_mess_enable, Toast.LENGTH_LONG).show();
					break;
				}
				//获取网络列表并激活一个网络连接
				//List<WifiConfiguration> configurations = mWifiManager.getConfiguredNetworks();
				//if(configurations.size() > 0)
				//{
				//	int netID = configurations.get(0).networkId;
				//	boolean disableAllOthers = true;
				//	mWifiManager.enableNetwork(netID, disableAllOthers);
				//}
				//mChangeState.setText(R.string.but_wifi_close);
				mChangeState.setText(R.string.test_wifi_mess_enter_wifisetting);
				break;
			}
			case 1:
			{
				new Thread()
				{
					public void run()
					{
						Log.e(TAG, "setWifiEnabled(false)");
						mWifiManager.setWifiEnabled(false);
						//mEthTest.setEnabled(false);
					}
				}.start();
				//mChangeState.setText(R.string.but_wifi_open);
				mChangeState.setText(R.string.test_wifi_mess_enter_wifisetting);
//			if(mIntent.getAction().equals(WifiManager.WIFI_STATE_CHANGED_ACTION))
//			{
//				Toast.makeText(this, R.string.test_wifi_mess_state_changed, Toast.LENGTH_LONG).show();
//			}
				while ((mWifiManager.getWifiState() == WifiManager.WIFI_STATE_DISABLING) ||
						(mWifiManager.getWifiState() == WifiManager.WIFI_STATE_DISABLED))
				{
					Toast.makeText(this, R.string.test_wifi_mess_disable, Toast.LENGTH_LONG).show();
					break;
				}
				break;
			}
			default:
				Log.e(TAG, "Wifi state error !");
				Toast.makeText(this, R.string.test_wifi_mess_error, Toast.LENGTH_LONG).show();
				break;
		}
	}
}
