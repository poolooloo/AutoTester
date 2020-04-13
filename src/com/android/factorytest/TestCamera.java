package com.android.factorytest;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;

public class TestCamera extends Activity
{
	
	private static final String TAG = "factorytest";
	private Button mReturn = null;
	private Button mChangeCamera = null;
//	private Button mNext = null;
	private Intent mIntent = null;
	private Button mRecord = null;
//	private Button mTorch = null;
//	private Button mTorch_off = null;
	private static int TAKE_PICTURE = 1;
	private int mstate = 0;
	private int musekey = 0;
	private boolean mresult = false;
	
	private Camera camera = null;
	private Parameters parameters = null;
	
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test_camera);
		initView();
		RegListener();
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
    public void RegListener() {  
    	Log.d(TAG,"RegListener");
        ExitListenerReceiver exitre = new ExitListenerReceiver();  
        IntentFilter intentfilter = new IntentFilter();  
        intentfilter.addAction(this.getPackageName() + "."  
                        + "ExitListenerReceiver");  
        this.registerReceiver(exitre, intentfilter);  
    }  
  
	class ExitListenerReceiver extends BroadcastReceiver {  
			@Override
			public void onReceive(Context arg0, Intent arg1) {
				// TODO Auto-generated method stub
				((Activity) arg0).finish();  				
			}  	
	} 

	private void initView()
	{
		Log.d(TAG,"initView");
		setTitle(R.string.test_camera_mess);
		mReturn = (Button)findViewById(R.id.but_return);
		mChangeCamera = (Button)findViewById(R.id.but_changecamera);
		mRecord = (Button)findViewById(R.id.but_record);
//		mTorch = (Button)findViewById(R.id.but_torch);
//		mTorch_off = (Button)findViewById(R.id.but_torch_off);
//		mTorch_off.setEnabled(false);
//		mNext = (Button)findViewById(R.id.but_next);
		Log.d(TAG,"step 1");
		mReturn.setOnClickListener(new View.OnClickListener() 
		{
			public void onClick(View v) 
			{
				//mIntent = new Intent(TestCamera.this, MainActivity.class);
				musekey = 1;
				showAlert();
//				finish();
				//startActivity(mIntent);
			}
		});
		
//		mTorch.setOnClickListener(new View.OnClickListener() 
//		{
//			public void onClick(View v) 
//			{
//
//				camera = Camera.open(0);
//				parameters = camera.getParameters();  
//				parameters.setFlashMode(Parameters.FLASH_MODE_TORCH);//开启  
//				camera.setParameters(parameters);  
//				mTorch.setEnabled(false);
//				mTorch_off.setEnabled(true);
//
//			}
//		});
//		mTorch.setOnClickListener(new View.OnClickListener() 
//		{
//			public void onClick(View v) 
//			{
//
////				camera = Camera.open(1);
//				parameters = camera.getParameters();  
//				parameters.setFlashMode(Parameters.FLASH_MODE_OFF);//开启  
//				camera.setParameters(parameters); 
//				camera.release();
//				mTorch.setEnabled(true);
//				mTorch_off.setEnabled(false);
//
//			}
//		});
		mChangeCamera.setOnClickListener(new View.OnClickListener() 
		{
			public void onClick(View v) 
			{
				mIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				//mIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE );
				startActivityForResult(mIntent,TAKE_PICTURE);
			}
		});
		mRecord.setOnClickListener(new View.OnClickListener() 
		{
			public void onClick(View v) 
			{
				//mIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				mIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE );
				
				startActivityForResult(mIntent,TAKE_PICTURE);
			}
		});
	}
}

