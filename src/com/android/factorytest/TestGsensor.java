package com.android.factorytest;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.hardware.usb.UsbDevice;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class TestGsensor extends Activity implements SensorEventListener{     //在继承activity类的同时使用listsen接口
	private final String TAG = "factorytest";
	private SensorManager mSensorManager;
	private Sensor sensor;
	private float mLastX,mLastY,mLastZ;
	private String sX,sY,sZ;
	
	private int mstate = 0;
	private int musekey = 0;
	private boolean mresult = false;
	private Button mReturn = null;
	private TextView mTextViewX = null;
	private TextView mTextViewY = null;
	private TextView mTextViewZ = null;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_gsensor);
        initView();
        mTextViewX = (TextView)findViewById(R.id.show_x);
        mTextViewY = (TextView)findViewById(R.id.show_y);
        mTextViewZ = (TextView)findViewById(R.id.show_z);    //绑定显示控件句柄
        
        mSensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        sensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        
        if(mSensorManager == null){
			Log.i(TAG,"sensor not supported");
	}
	mSensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
     }
    private void initView()
	{
		mReturn = (Button) findViewById(R.id.but_return);
		mReturn.setOnClickListener(new View.OnClickListener() 
		{
			public void onClick(View v) 
			{
				musekey = 1;
				showAlert();
//				mIntent = new Intent(TestPin.this, MainActivity.class);
//				finish();
//				startActivity(mIntent);
			}
		});
	}
	public void onAccuracyChanged(Sensor arg0, int arg1){		
	}
	
	public void onSensorChanged(SensorEvent event){          //在activity中完成该接口函数
		if(event.sensor == null){
			return;
		}
		
		if(event.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
			mLastX = event.values[0];
			mLastY = event.values[1];
			mLastZ = event.values[2];
			
			sX = String.valueOf(mLastX);
			sY = String.valueOf(mLastY);
			sZ = String.valueOf(mLastZ);
			
			mTextViewX.setText(sX);
			mTextViewY.setText(sY);
			mTextViewZ.setText(sZ);   //由于该接口函数不断刷新，所以可以把控件显示函数放在这里显示
		}
	}
    
    protected void onStop(){                          //同样在退出activity时要注销监听
    	super.onStop();
		if(mSensorManager != null){
			mSensorManager.unregisterListener(this);
			mSensorManager = null;
		}
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

}

