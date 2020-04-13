package com.android.factorytest;


import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class TestGpio extends Activity {
    private static final String TAG = "factorytest";
    private static final String procpath = "/proc/gpio_set/rp_gpio_set";

    Button btn_write001, btn_write002, btn_write003, btn_write004,btn_write005,btn_write006,btn_write007,
            btn_read001, btn_read002, btn_read003, btn_read004,btn_read005,btn_read006,btn_read007,
            btn_show_gpio_level001, btn_show_gpio_level002, btn_show_gpio_level003,btn_show_gpio_level004,btn_show_gpio_level005,btn_show_gpio_level006,btn_show_gpio_level007,
            mReturn;

    EditText et_gpio_function001, et_gpio_function002,
            et_gpio_function003, et_gpio_function004,
            et_gpio_function005, et_gpio_function006,
            et_gpio_function007;

    private int musekey = 0;
    private int mstate = 0;
    private Button mAlarmState = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.test_pin);

        initUI();
    }
    DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener()

    {

        public void onClick(DialogInterface dialog, int which)

        {

            switch (which)

            {

                case AlertDialog.BUTTON_POSITIVE:// "成功"按钮退出程序
                    Log.i(TAG,"成功");
                    mstate = 1;
                    Intent itok = new Intent();
                    itok.putExtra("sucess", true);
                    setResult(1, itok);
                    if(musekey == 1)
                        finish();
//	            	else
//	            	{
//						mIntent = new Intent(TestPin.this, TestAudioVideo.class);
//						//mIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//						finish();
//						startActivity(mIntent);
//	            	}
                    break;

                case AlertDialog.BUTTON_NEGATIVE:// "失败"第二个按钮取消对话框
                    Log.i(TAG,"失败");
                    mstate = 2;
                    Intent itfaile = new Intent();
                    itfaile.putExtra("sucess", false);
                    setResult(1, itfaile);
                    if(musekey == 1)
                        finish();
//	            	else
//	            	{
//						mIntent = new Intent(TestPin.this, TestAudioVideo.class);
//						//mIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//						finish();
//						startActivity(mIntent);
//	            	}
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
        if(keyCode==300){
            Log.d(TAG,"get the key");
//	        	mAlarmState.setText("报警按钮:按下");
            mAlarmState.setText(getResources().getString(R.string.bt_alarm_down));
            //mAlarmState.setBackgroundColor(Color.GREEN);
        }
        else if(keyCode==KeyEvent.KEYCODE_BACK )
        {
            // 创建退出对话框
            musekey = 1;
            showAlert();

        }
        return super.onKeyDown(keyCode, keyEvent);
    }

    public boolean onKeyUp(int keyCode,KeyEvent keyEvent)
    {
        // TODO Auto-generated method stub
        Log.d(TAG,"get the key "+keyCode);
        if(keyCode==300){
            Log.d(TAG,"get the key");
//	        	mAlarmState.setText("报警按钮:松开");
            mAlarmState.setText(getResources().getString(R.string.bt_alarm_up));
        }
        return super.onKeyDown(keyCode, keyEvent);
    }
    private void initUI() {
        // TODO Auto-generated method stub
        mReturn = (Button) findViewById(R.id.but_return);

        et_gpio_function001 = (EditText) findViewById(R.id.et_gpio_function001);
        btn_write001 = (Button) findViewById(R.id.btn_write001);
        btn_write001.setOnClickListener(myclick);

        et_gpio_function002 = (EditText) findViewById(R.id.et_gpio_function002);
        btn_write002 = (Button) findViewById(R.id.btn_write002);
        btn_write002.setOnClickListener(myclick);

        et_gpio_function003 = (EditText) findViewById(R.id.et_gpio_function003);
        btn_write003 = (Button) findViewById(R.id.btn_write003);
        btn_write003.setOnClickListener(myclick);

        et_gpio_function004 = (EditText) findViewById(R.id.et_gpio_function004);
        btn_write004 = (Button) findViewById(R.id.btn_write004);
        btn_write004.setOnClickListener(myclick);

        et_gpio_function005 = (EditText) findViewById(R.id.et_gpio_function005);
        btn_write005 = (Button) findViewById(R.id.btn_write005);
        btn_write005.setOnClickListener(myclick);

        et_gpio_function006 = (EditText) findViewById(R.id.et_gpio_function006);
        btn_write006 = (Button) findViewById(R.id.btn_write006);
        btn_write006.setOnClickListener(myclick);

        et_gpio_function007 = (EditText) findViewById(R.id.et_gpio_function007);
        btn_write007 = (Button) findViewById(R.id.btn_write007);
        btn_write007.setOnClickListener(myclick);

        btn_read001 = (Button) findViewById(R.id.btn_read001);
        btn_read001.setOnClickListener(myclick);
        btn_read002 = (Button) findViewById(R.id.btn_read002);
        btn_read002.setOnClickListener(myclick);
        btn_read003 = (Button) findViewById(R.id.btn_read003);
        btn_read003.setOnClickListener(myclick);
        btn_read004 = (Button) findViewById(R.id.btn_read004);
        btn_read004.setOnClickListener(myclick);
        btn_read005 = (Button) findViewById(R.id.btn_read005);
        btn_read005.setOnClickListener(myclick);
        btn_read006 = (Button) findViewById(R.id.btn_read006);
        btn_read006.setOnClickListener(myclick);
        btn_read007 = (Button) findViewById(R.id.btn_read007);
        btn_read007.setOnClickListener(myclick);




        btn_show_gpio_level001 = (Button) findViewById(R.id.btn_show_gpio_level001);
        btn_show_gpio_level002 = (Button) findViewById(R.id.btn_show_gpio_level002);
        btn_show_gpio_level003 = (Button) findViewById(R.id.btn_show_gpio_level003);
        btn_show_gpio_level004 = (Button) findViewById(R.id.btn_show_gpio_level004);
        btn_show_gpio_level005 = (Button) findViewById(R.id.btn_show_gpio_level005);
        btn_show_gpio_level006 = (Button) findViewById(R.id.btn_show_gpio_level006);
        btn_show_gpio_level007 = (Button) findViewById(R.id.btn_show_gpio_level007);
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

    OnClickListener myclick = new OnClickListener() {

        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            switch (v.getId()) {

                case R.id.btn_write001:
                    String str_wtemp001 = et_gpio_function001.getText().toString();
                    if(str_wtemp001.length()<1)
                        break;
                    writeProc(procpath, str_wtemp001.getBytes());
                    break;

                case R.id.btn_write002:
                    String str_wtemp002 = et_gpio_function002.getText().toString();
                    if(str_wtemp002.length()<1)
                        break;
                    writeProc(procpath, str_wtemp002.getBytes());
                    break;

                case R.id.btn_write003:
                    String str_wtemp003 = et_gpio_function003.getText().toString();
                    if(str_wtemp003.length()<1)
                        break;
                    writeProc(procpath, str_wtemp003.getBytes());
                    break;

                case R.id.btn_write004:
                    String str_wtemp004 = et_gpio_function004.getText().toString();
                    if(str_wtemp004.length()<1)
                        break;
                    writeProc(procpath, str_wtemp004.getBytes());
                    break;

                case R.id.btn_write005:
                    String str_wtemp005 = et_gpio_function005.getText().toString();
                    if(str_wtemp005.length()<1)
                        break;
                    writeProc(procpath, str_wtemp005.getBytes());
                    break;

                case R.id.btn_write006:
                    String str_wtemp006 = et_gpio_function006.getText().toString();
                    if(str_wtemp006.length()<1)
                        break;
                    writeProc(procpath, str_wtemp006.getBytes());
                    break;

                case R.id.btn_write007:
                    String str_wtemp007 = et_gpio_function007.getText().toString();
                    if(str_wtemp007.length()<1)
                        break;
                    writeProc(procpath, str_wtemp007.getBytes());
                    break;


                case R.id.btn_read001:
                    String str_rtemp001 = et_gpio_function001.getText().toString();
                    if(str_rtemp001.length()<1)
                        break;
                    String str1 = getProcValue(procpath, str_cut_off(str_rtemp001).getBytes());
                    char[] char_str1 = str1.toCharArray();
                    String temp1 = "GPIO的电平: " + (char_str1[26] + "");
                    btn_show_gpio_level001.setText(temp1);
                    break;

                case R.id.btn_read002:
                    String str_rtemp002 = et_gpio_function002.getText().toString();
                    if(str_rtemp002.length()<1)
                        break;
                    String str2 = getProcValue(procpath, str_cut_off(str_rtemp002).getBytes());
                    char[] char_str2 = str2.toCharArray();
                    String temp2 = "GPIO的电平: " + (char_str2[26] + "");
                    btn_show_gpio_level002.setText(temp2);
                    break;

                case R.id.btn_read003:
                    String str_rtemp003 = et_gpio_function003.getText().toString();
                    if(str_rtemp003.length()<1)
                        break;
                    String str3 = getProcValue(procpath, str_cut_off(str_rtemp003).getBytes());
                    char[] char_str3 = str3.toCharArray();
                    String temp3 = "GPIO的电平: " + (char_str3[26] + "");
                    btn_show_gpio_level003.setText(temp3);
                    break;

                case R.id.btn_read004:
                    String str_rtemp004 = et_gpio_function004.getText().toString();
                    if(str_rtemp004.length()<1)
                        break;
                    String str4 = getProcValue(procpath, str_cut_off(str_rtemp004).getBytes());
                    char[] char_str4 = str4.toCharArray();
                    String temp4 = "GPIO的电平: " + (char_str4[26] + "");
                    btn_show_gpio_level004.setText(temp4);
                    break;

                case R.id.btn_read005:
                    String str_rtemp005 = et_gpio_function005.getText().toString();
                    if(str_rtemp005.length()<1)
                        break;
                    String str5 = getProcValue(procpath, str_cut_off(str_rtemp005).getBytes());
                    char[] char_str5 = str5.toCharArray();
                    String temp5 = "GPIO的电平: " + (char_str5[26] + "");
                    btn_show_gpio_level005.setText(temp5);
                    break;

                case R.id.btn_read006:
                    String str_rtemp006 = et_gpio_function006.getText().toString();
                    if(str_rtemp006.length()<1)
                        break;
                    String str6 = getProcValue(procpath, str_cut_off(str_rtemp006).getBytes());
                    char[] char_str6 = str6.toCharArray();
                    String temp6 = "GPIO的电平: " + (char_str6[26] + "");
                    btn_show_gpio_level006.setText(temp6);
                    break;

                case R.id.btn_read007:
                    String str_rtemp007 = et_gpio_function007.getText().toString();
                    if(str_rtemp007.length()<1)
                        break;
                    String str7 = getProcValue(procpath, str_cut_off(str_rtemp007).getBytes());
                    char[] char_str7 = str7.toCharArray();
                    String temp7 = "GPIO的电平: " + (char_str7[26] + "");
                    btn_show_gpio_level007.setText(temp7);
                    break;

            }
        }
    };

    private String str_cut_off(String str){
        char[] char_str = str.toString().toCharArray();
        int count_line=0;
        int flag_line=0;
        String tras_str = null;

        tras_str = String.valueOf(char_str[0]);
        for(int i=0; i<str.length(); i++){
            String str009 = String.valueOf(char_str[i]);
            if(str009.indexOf("_")!=-1){
                count_line++;
                if(2 == count_line){
                    break;
                }

            }
            flag_line++;
        }

        if(2 == count_line){
            for(int j=1; j<flag_line; j++){
                tras_str = tras_str + String.valueOf(char_str[j]);
            }

            return tras_str;
        }else if(1 == count_line){

            return str;
        }

        return "cut off error";
    }

    private String writeProc(String path, byte[] buffer) {
        try {
            File file = new File(path);
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(buffer);
            fos.close();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return "write error!";
        }
        return (buffer.toString());
    }

//	private String getProcValue(String procPath) {
//		char[] buffer = new char[32];
//		try {
//			FileReader mReader = new FileReader(new File(procPath));
//			mReader.read(buffer, 0, 32);
//			mReader.close();
//		} catch (Exception e) {
//
//			return "read error!!";
//		}
//		String str = new String(buffer);
//		return str;
//	}

    private String getProcValue(String procPath, byte[] buf_pag) {
        char[] buffer = new char[32];

        try {
            File file = new File(procPath);
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(buf_pag);
            fos.close();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return "write error!";
        }

        try {
            FileReader mReader = new FileReader(new File(procPath));
            mReader.read(buffer, 0, 32);
            mReader.close();
        } catch (Exception e) {

            return "read error!!";
        }
        String str = new String(buffer);
        return str;
    }
}

