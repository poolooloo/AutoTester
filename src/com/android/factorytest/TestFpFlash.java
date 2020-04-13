package com.android.factorytest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.nio.ByteBuffer;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class TestFpFlash extends Activity {

	private static final String TAG = "factorytest";
	public static final int BUFF_SIZE = 8;//4k
	private int mstate = 0;
	private int musekey = 0;
	private boolean mresult = false;
	public byte[] buf = new byte[8];
	public int[] intbuf = new int[8];
    static {
        System.loadLibrary("factorytest");
    }
    private ByteBuffer mDirectbuff;
    
	public native int Test(Object buffer,int len);
	public native int ReadID(Object buffer,int len);
	
	
	
	private TextView mfpflash_show = null;
	private Button mReturn = null;
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test_fpflash);
		mDirectbuff = 	ByteBuffer.allocateDirect(BUFF_SIZE);
		//mDirectbuff = ByteBuffer.wrap(buf);
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
		mfpflash_show = (TextView)findViewById(R.id.test_fpflash_show);
		//Test(mDirectbuff,8);
		ReadID(mDirectbuff,8);
		//mDirectbuff.getchar(buf);
		String str="";
		String hex="";
		for(int i = 0;i<8;i++)
		{
			buf[i]=mDirectbuff.get(i);
			intbuf[i]=buf[i]&0xff;
			hex = Integer.toHexString(intbuf[i]);
			if(hex.length()==1)
				hex = '0' + hex;
			str += hex;
			Log.i(TAG,Integer.toHexString(intbuf[i]));
		}
		mfpflash_show.setText("ID="+str);

	

//	mNext = (Button)findViewById(R.id.but_next);
	mReturn = (Button)findViewById(R.id.but_return);
	mReturn.setOnClickListener(new View.OnClickListener() 
	{
		public void onClick(View v) 
		{
			//mIntent = new Intent(TestSd.this, MainActivity.class);
			musekey = 1;
			showAlert();
//			finish();
			//startActivity(mIntent);
		}
	});
}
}
