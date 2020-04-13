package com.android.factorytest;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class TestColor extends Activity{
	private static final String TAG = "factorytest";
	
	//private Button mReturn = null;
	//private Button mChangeColor = null;
	//private Button mNext = null;
	private Button mAlarmState = null;
	private TextView mText1 = null;
	private TextView mText2 = null;
	private TextView mText3 = null;
	private Intent mIntent = null;
	
	private int mNum = 0;
	
	private int mstate = 0;
	private int musekey = 0;
	private boolean mresult = false;
//	private Bundle mextras;
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.test_color);
		initView();
		Log.d(TAG,"testcolor");
//		mextras = getIntent().getExtras(); 
//		mresult = mextras.getBoolean("sucess");
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
     // Ìí¼ÓÑ¡Ôñ°´Å¥²¢×¢²á¼àÌý  

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
		setTitle(R.string.test_color_mess);
		//requestWindowFeature(Window.FEATURE_NO_TITLE); 
		//mReturn = (Button)findViewById(R.id.but_return);
		//mChangeColor = (Button)findViewById(R.id.but_changecolor);
		//mNext = (Button)findViewById(R.id.but_next);
		
		mText1 = (TextView)findViewById(R.id.test_color_text1);
//		mText2 = (TextView)findViewById(R.id.test_color_text2);
//		mText3 = (TextView)findViewById(R.id.test_color_text3);
		mText1.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View v) {
				mNum ++;
				if(mNum == 5)
				{
					musekey = 1;
					mNum = -1;
					showAlert();
				}
				else
					changeColor(mNum);

					
			}
		});
//		mReturn.setOnClickListener(new View.OnClickListener() 
//		{
//			public void onClick(View v) 
//			{
//				//mIntent = new Intent(TestColor.this, MainActivity.class);
//				musekey = 1;
//				showAlert();
//				//finish();
//				//startActivity(mIntent);
//			}
//		});

//		mNext.setOnClickListener(new View.OnClickListener() 
//		{
//			public void onClick(View v) 
//			{
//				mIntent = new Intent(TestColor.this, TestSd.class);
//				//mIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//				finish(); 
//				startActivity(mIntent);
//			}
//		});
//		mChangeColor.setOnClickListener(new View.OnClickListener() 
//		{
//			public void onClick(View v) 
//			{
//				mNum ++;
//				changeColor(mNum);
//			}
//		});
	}
	
	private void changeColor(int num)
	{
		Log.e(TAG, "num = " + (num%6));
		switch(num % 6)
		{
		case 0:
			mText1.setBackgroundColor(Color.RED);
//			mText2.setBackgroundColor(Color.RED);
//			mText3.setBackgroundColor(Color.RED);
			break;
		case 1:
			mText1.setBackgroundColor(Color.GREEN);
//			mText2.setBackgroundColor(Color.GREEN);
//			mText3.setBackgroundColor(Color.GREEN);
			break;
		case 2:
			mText1.setBackgroundColor(Color.BLUE);
//			mText2.setBackgroundColor(Color.BLUE);
//			mText3.setBackgroundColor(Color.BLUE);
			break;
		case 3:
			mText1.setBackgroundColor(Color.BLACK);
//			mText2.setBackgroundColor(Color.RED);
//			mText3.setBackgroundColor(Color.RED);
			break;
		case 4:
			mText1.setBackgroundColor(Color.WHITE);
//			mText2.setBackgroundColor(Color.GREEN);
//			mText3.setBackgroundColor(Color.GREEN);
			break;
		case 5:
			mText1.setBackgroundColor(Color.BLUE);
//			mText2.setBackgroundColor(Color.BLUE);
//			mText3.setBackgroundColor(Color.BLUE);
			break;
		}
	}
}

