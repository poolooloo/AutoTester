package com.android.factorytest;

import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AlertDialog;

public class TestBluetooth extends AppCompatActivity
{
	private static final String TAG = "factorytest";
	private TextView mBluetoothstate = null;
	private Button mReturn = null;
	private Button mChangeState = null;
//	private Button mNext = null;
	private Intent mIntent = null;
	private BluetoothAdapter mBluetooth = null;
	private int mBluetoothState = -1;
	
	private int mstate = 0;
	private int musekey = 0;
	private boolean mresult = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test_bluetooth);
		initView();
	}
	DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener()  

    {  

        public void onClick(DialogInterface dialog, int which)  

        {  
//        	Bundle bundle = new Bundle();
            switch (which)  

            {  

            case  AlertDialog.BUTTON_POSITIVE:// "成功"按钮退出程序
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

            case  AlertDialog.BUTTON_NEGATIVE:// "失败"第二个按钮取消对话框
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
            
            case  AlertDialog.BUTTON_NEUTRAL:// "重测"第二个按钮取消对话框
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
	  AlertDialog isExit = new  AlertDialog.Builder(this).create();

     // ÉèÖÃ¶Ô»°¿ò±êÌâ  


     isExit.setTitle("系统提示");  

     // 设置对话框消息  

     isExit.setMessage("测试是否成功");  

     // 添加选择按钮并注册监听  

     isExit.setButton( AlertDialog.BUTTON_POSITIVE,"成功", listener);

     isExit.setButton(AlertDialog.BUTTON_NEGATIVE,"失败", listener);  
     
     isExit.setButton( AlertDialog.BUTTON_NEUTRAL,"重测", listener);

     // ÏÔÊ¾¶Ô»°¿ò  

     isExit.show();  
 }
 public boolean onKeyDown(int keyCode,KeyEvent keyEvent)
 {
        // TODO Auto-generated method stub
	 	Log.d(TAG,"get the key "+keyCode);
	 	if(keyCode==KeyEvent.KEYCODE_BACK )
        {
        	 // ´´½¨ÍË³ö¶Ô»°¿ò  
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
		mBluetoothstate = (TextView)findViewById(R.id.bluetooth_state);

		if (mChangeState == null)
			Log.e(TAG, "mChangeState is null ");

		mReturn.setOnClickListener(new View.OnClickListener() 
		{
			public void onClick(View v) 
			{
				//mIntent = new Intent(TestBluetooth.this, MainActivity.class);
				musekey = 1;
				showAlert();
//				finish();
				//startActivity(mIntent);
			}
		});

//		mNext.setOnClickListener(new View.OnClickListener() 
//		{
//			public void onClick(View v) 
//			{
//				mIntent = new Intent(TestBluetooth.this, TestTouch.class);
//				finish(); 
//				startActivity(mIntent);
//			}
//		});

		mBluetooth = BluetoothAdapter.getDefaultAdapter();
		if(null == mBluetooth)
		{
			mBluetoothstate.setText("No Bluetooth Device");
			mBluetoothstate.setTextColor(Color.RED);
		}
		if (mBluetooth.isEnabled()) 
		{
			mBluetoothState = 1;
			mChangeState.setText(R.string.but_bluetooth_close);
		} 
		else if (!mBluetooth.isEnabled()) 
		{
			mBluetoothState = 0;
			mChangeState.setText(R.string.but_bluetooth_open);
		} 
		else 
		{
			Log.e(TAG, "Bluetooth State = ");
		}
		mChangeState.setOnClickListener(new View.OnClickListener() 
		{
			public void onClick(View v) 
			{
				startActivity(new Intent(Settings.ACTION_BLUETOOTH_SETTINGS));//进入Bluetooth配置界面
				changeBluetoothState(mBluetoothState);
				mBluetoothState ++;
			}
		});
	}

	private void changeBluetoothState(int state) 
	{
		Log.e(TAG, "mBluetoothState = " + state);
		switch (state % 2) 
		{
			case 0: 
			{
				startBluetooth();
				Toast.makeText(this, R.string.test_bluetooth_mess_state_changed, Toast.LENGTH_LONG).show();
				mChangeState.setText(R.string.but_bluetooth_close);
				break;
			}
			case 1:
			{
				stopBluetooth();
				Toast.makeText(this, R.string.test_bluetooth_mess_state_changed, Toast.LENGTH_LONG).show();
				mChangeState.setText(R.string.but_bluetooth_open);
				break;
			}
			default:
				Log.e(TAG, "Wifi state error !");
				Toast.makeText(this, R.string.test_bluetooth_mess_error, Toast.LENGTH_LONG).show();
				break;
		}
	}
	
	private void startBluetooth()
	{
		mBluetoothstate = (TextView)findViewById(R.id.bluetooth_state);
		mBluetooth = BluetoothAdapter.getDefaultAdapter();
		if(null == mBluetooth)
		{
			mBluetoothstate.setText("No Bluetooth Device");
			mBluetoothstate.setTextColor(Color.RED);
		}
		BroadcastReceiver BluetoothState = new BroadcastReceiver()
		{

			@Override
			public void onReceive(Context context, Intent intent) 
			{
				//String prevStateExtra = BluetoothAdapter.EXTRA_PREVIOUS_STATE;
				String stateExtra = BluetoothAdapter.EXTRA_STATE;
				int state = intent.getIntExtra(stateExtra, -1);
				//int previousState = intent.getIntExtra(prevStateExtra, -1);
				String tt = "";
				switch(state)
				{
					case (BluetoothAdapter.STATE_TURNING_ON):
					{
						tt = "Bluetooth turning on";
						break;
					}
					case (BluetoothAdapter.STATE_ON):
					{
						tt = "Bluetooth on";
						unregisterReceiver(this);
						break;
					}
					case (BluetoothAdapter.STATE_TURNING_OFF):
					{
						tt = "Bluetooth turning off";
						break;
					}
					case (BluetoothAdapter.STATE_OFF):
					{
						tt = "Bluetooth off";
						break;
					}
					default:
						tt = "Unkown Bluetooth state!";
						break;
				}
				//Toast.makeText(this, tt, Toast.LENGTH_LONG).show();	
				mBluetoothstate.setText(tt);			
			}
		};
		if(!mBluetooth.isEnabled())
		{
			String actionStateChanged = BluetoothAdapter.ACTION_LOCAL_NAME_CHANGED;
			//String actionRequestEnable = BluetoothAdapter.ACTION_REQUEST_ENABLE;
			registerReceiver(BluetoothState, new IntentFilter(actionStateChanged));
			//startActivityForResult(new Intent(actionRequestEnable),0);
			mBluetooth.enable();
		}
	}	
	private void stopBluetooth()
	{
		mBluetoothstate = (TextView)findViewById(R.id.bluetooth_state);
		mBluetooth = BluetoothAdapter.getDefaultAdapter();
		if(null == mBluetooth)
		{
			mBluetoothstate.setText("No Bluetooth Device");
			mBluetoothstate.setTextColor(Color.RED);
		}
		BroadcastReceiver BluetoothState = new BroadcastReceiver()
		{

			@Override
			public void onReceive(Context context, Intent intent) 
			{
				//String prevStateExtra = BluetoothAdapter.EXTRA_PREVIOUS_STATE;
				String stateExtra = BluetoothAdapter.EXTRA_STATE;
				int state = intent.getIntExtra(stateExtra, -1);
				//int previousState = intent.getIntExtra(prevStateExtra, -1);
				String tt = "Unkown Bluetooth";
				switch(state)
				{
					case (BluetoothAdapter.STATE_TURNING_ON):
					{
						tt = "Bluetooth turning on";
						break;
					}
					case (BluetoothAdapter.STATE_ON):
					{
						tt = "Bluetooth on";
						unregisterReceiver(this);
						break;
					}
					case (BluetoothAdapter.STATE_TURNING_OFF):
					{
						tt = "Bluetooth turning off";
						break;
					}
					case (BluetoothAdapter.STATE_OFF):
					{
						tt = "Bluetooth off";
						break;
					}
					default:
						tt = "Unkown Bluetooth state!";
						break;
				}
				//Toast.makeText(this, tt, Toast.LENGTH_LONG).show();	
				mBluetoothstate.setText(tt);
			}
		};
		if(mBluetooth.isEnabled())
		{
			String actionStateChanged = BluetoothAdapter.ACTION_LOCAL_NAME_CHANGED;
			registerReceiver(BluetoothState, new IntentFilter(actionStateChanged));
			mBluetooth.disable();			
		}
		
	}
}
