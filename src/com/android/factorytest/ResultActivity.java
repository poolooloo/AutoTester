package com.android.factorytest;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

public class ResultActivity extends AppCompatActivity {

    private static final String TAG = "factorytest";
    private static String[] mstrarrayresult = new String[13];
    private static String[] mstrarrayname = new String[13];
    private static String ResultFile = "testresult.xls";

    private TextView mTestLcdtxt = null;
    private TextView mTestsdtxt = null;
    private TextView mTestcameratxt = null;
    private TextView mTestwifitxt = null;
    private TextView mTestbttxt = null;
    private TextView mTesttouchtxt = null;
    private TextView mTestgpiotxt = null;
    private TextView mTestavtxt = null;
    private TextView mTestrecordtxt = null;
    private TextView mTestfptxt = null;
    private TextView mTestGsensortxt = null;
    private TextView mTestfpflashtxt = null;
    private TextView mTestUarttxt = null;
    private TextView mTestLastTime = null;
    private TextView mWordCount = null;
    private Intent mIntent = null;

    private TableLayout tableLayout = null;
    private boolean isFileexist = false;
    
    private static final int mlcd = 2;
    private static final int msd = 1;
    private static final int mcamera = 3;
    private static final int mwifi = 4;
    private static final int mbluetooth = 5;
    private static final int mtouch = 6;
    private static final int mgpio = 7;
    private static final int mav = 8;
    private static final int mrecord = 9;
    private static final int mfingerprint = 10;
    private static final int mgsensor = 11;
    private static final int mfpflash = 12;
    private static final int muart = 13;
    private int requestCode = 0;

    private static Boolean mlcdresult = false;
    private static Boolean msdresult = false;
    private static Boolean mcameraresult = false;
    private static Boolean mwifiresult = false;
    private static Boolean mbtresult = false;
    private static Boolean mtouchresult = false;
    private static Boolean mgpioresult = false;
    private static Boolean mavresult = false;
    private static Boolean mrecordresult = false;
    private static Boolean mfpresult = false;
    private static Boolean mgsensorresult = false;
    private static Boolean mgfpflashresult = false;
    private static Boolean muartresult = false;
    
    static {
        System.loadLibrary("factorytest");
    }
    public native int ReadAdc();
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        
        initText();

        final Button btnExt = findViewById(R.id.test_exit);
        btnExt.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                System.exit(0);
            }
        });

        try {
            initanything();
        } catch (RowsExceededException e) {
            // TODO 自动生成的 catch 块
            e.printStackTrace();
        } catch (WriteException e) {
            // TODO 自动生成的 catch 块
            e.printStackTrace();
        }

    }


    private void initText()
    {
        mTestLcdtxt = (TextView)findViewById(R.id.test_colorresult);
        mTestsdtxt = (TextView)findViewById(R.id.test_sdresult);
        mTestcameratxt = (TextView)findViewById(R.id.test_cameraresult);
        mTestwifitxt = (TextView)findViewById(R.id.test_wifiresult);
        mTestbttxt = (TextView)findViewById(R.id.test_btresult);
        mTesttouchtxt = (TextView)findViewById(R.id.test_touchresult);
        mTestgpiotxt = (TextView)findViewById(R.id.test_gpioresult);
        mTestavtxt = (TextView)findViewById(R.id.test_avresult);
        mTestrecordtxt = (TextView)findViewById(R.id.test_recordresult);
        mTestfptxt = (TextView)findViewById(R.id.test_fpresult);
        mTestGsensortxt = (TextView)findViewById(R.id.test_gsensorresult);
        mTestfpflashtxt = (TextView)findViewById(R.id.test_fpflashresult);
        mTestLastTime = (TextView)findViewById(R.id.lasttime);
        mTestUarttxt = (TextView)findViewById(R.id.test_uartresult);
        mWordCount = (TextView)findViewById(R.id.wordcount);
        if(ReadAdc() == 0)
            mWordCount.setText("adc OK");
        else
            mWordCount.setText("adc Failed");

    }

    public void initanything() throws WriteException
    {
        mstrarrayresult[0] = getResources().getString(R.string.test_result_none) ;
        mstrarrayresult[1] = getResources().getString(R.string.test_result_none);
        mstrarrayresult[2] = getResources().getString(R.string.test_result_none);
        mstrarrayresult[3] = getResources().getString(R.string.test_result_none);
        mstrarrayresult[4] = getResources().getString(R.string.test_result_none);
        mstrarrayresult[5] = getResources().getString(R.string.test_result_none);
        mstrarrayresult[6] = getResources().getString(R.string.test_result_none);
        mstrarrayresult[7] = getResources().getString(R.string.test_result_none);
        mstrarrayresult[8] = getResources().getString(R.string.test_result_none);
        mstrarrayresult[9] = getResources().getString(R.string.test_result_none);
        mstrarrayresult[10] = getResources().getString(R.string.test_result_none);
        mstrarrayresult[11] = getResources().getString(R.string.test_result_none);
        mstrarrayresult[12] = getResources().getString(R.string.test_result_none);
        mstrarrayname[0] = getResources().getString(R.string.test_colorresult_name) ;
        mstrarrayname[1] = getResources().getString(R.string.test_sdresult_name);
        mstrarrayname[2] = getResources().getString(R.string.test_cameraresult_name);
        mstrarrayname[3] = getResources().getString(R.string.test_wifiresult_name);
        mstrarrayname[4] = getResources().getString(R.string.test_btresult_name);
        mstrarrayname[5] = getResources().getString(R.string.test_touchresult_name);
        mstrarrayname[6] = getResources().getString(R.string.test_gpioresult_name);
        mstrarrayname[7] = getResources().getString(R.string.test_avresult_name);
        mstrarrayname[8] = getResources().getString(R.string.test_recordresult_name);
        mstrarrayname[9] = getResources().getString(R.string.test_fpresult_name);
        mstrarrayname[10] = getResources().getString(R.string.test_gsensorresult_name);
        mstrarrayname[11] = getResources().getString(R.string.test_fpflash_name);
        mstrarrayname[12] = getResources().getString(R.string.test_uart_name);


        CreateShowTable();

        if(!isFileexist){
            initResultFile();
//            testLcd();
//            testSdCard();
            testCamera();
            showresult();
        } else{
//            testLcd();
//            testSdCard();
            testCamera();
            showresult();
        }


    }

    public void checkfile()
    {
        File ResultFileDir = getExternalFilesDir("autoTest");
        String ResultFile = ResultFileDir.getAbsolutePath() + "/testresult.xls";
        File fp = new File(ResultFile);
        isFileexist = fp.exists();
    }
    
    public void showresult()
    {
        InitResult();
        mTestLcdtxt.setText(mstrarrayresult[0]);
        mTestsdtxt.setText(mstrarrayresult[1]);
        mTestcameratxt.setText(mstrarrayresult[2]);
        mTestwifitxt.setText(mstrarrayresult[3]);
        mTestbttxt.setText(mstrarrayresult[4]);
        mTesttouchtxt.setText(mstrarrayresult[5]);
        mTestgpiotxt.setText(mstrarrayresult[6]);
        mTestavtxt.setText(mstrarrayresult[7]);
        mTestrecordtxt.setText(mstrarrayresult[8]);
        //mTestfptxt.setText(mstrarrayresult[9]);
        //mTestGsensortxt.setText(mstrarrayresult[10]);
        //mTestfpflashtxt.setText(mstrarrayresult[11]);
        mTestUarttxt.setText(mstrarrayresult[12]);
        
        
        
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        switch (requestCode)
        {
            case mlcd:
                mlcdresult = data.getBooleanExtra("sucess",false);
                if(mlcdresult == true)
                {
                    mTestLcdtxt.setText(R.string.test_colorresult_ok);
                    mTestLcdtxt.setTextColor(Color.GREEN);
                    try {
                        writeExcel(ResultFile,requestCode,true);
                    } catch (BiffException e) {
                        // TODO 自动生成的 catch 块
                        e.printStackTrace();
                    }
                }
                else
                {
                    mTestLcdtxt.setText(R.string.test_colorresult_failed);
                    mTestLcdtxt.setTextColor(Color.RED);
//    	    	 Log.i(TAG,"lcd test failed");
                    try {
                        writeExcel(ResultFile,requestCode,false);
                    } catch (BiffException e) {
                        // TODO 自动生成的 catch 块
                        e.printStackTrace();
                    }
                }
                testSdCard();
                break;
            case  msd:
                msdresult = data.getBooleanExtra("sucess",false);
                if(msdresult == true){
                    mTestsdtxt.setText(R.string.test_sdresult_ok);
                    mTestsdtxt.setTextColor(Color.GREEN);
                    try {
                        writeExcel(ResultFile,requestCode,true);
                    } catch (BiffException e) {
                        // TODO 自动生成的 catch 块
                        e.printStackTrace();
                    }
//	   	    	Log.i(TAG,"sdcard test sucess");
                }
                else{
                    mTestsdtxt.setText(R.string.test_sdresult_failed);
                    mTestsdtxt.setTextColor(Color.RED);
//	   	    	Log.i(TAG,"sdcard test failed");
                    try {
                        writeExcel(ResultFile,requestCode,false);
                    } catch (BiffException e) {
                        // TODO 自动生成的 catch 块
                        e.printStackTrace();
                    }
                }
//                testCamera();
                break;
            case mcamera:
                mcameraresult = data.getBooleanExtra("sucess",false);
                if(mcameraresult == true){
                    mTestcameratxt.setText(R.string.test_cameraresult_ok);
                    mTestcameratxt.setTextColor(Color.GREEN);
                    try {
                        writeExcel(ResultFile,requestCode,true);
                    } catch (BiffException e) {
                        // TODO 自动生成的 catch 块
                        e.printStackTrace();
                    }
//	   	    	Log.i(TAG,"camera test sucess");
                }
                else{
                    mTestcameratxt.setText(R.string.test_cameraresult_failed);
                    mTestcameratxt.setTextColor(Color.RED);
                    try {
                        writeExcel(ResultFile,requestCode,false);
                    } catch (BiffException e) {
                        // TODO 自动生成的 catch 块
                        e.printStackTrace();
                    }
//	   	    	Log.i(TAG,"camera test failed");
                }
                testNetWork();
                break;
            case mwifi:
                mwifiresult = data.getBooleanExtra("sucess",false);
                if(mwifiresult == true){
                    mTestwifitxt.setText(R.string.test_wifiresult_ok);
                    mTestwifitxt.setTextColor(Color.GREEN);
//	   	    	Log.i(TAG,"wifi test sucess");
                    try {
                        writeExcel(ResultFile,requestCode,true);
                    } catch (BiffException e) {
                        // TODO 自动生成的 catch 块
                        e.printStackTrace();
                    }
                }
                else{
                    mTestwifitxt.setText(R.string.test_wifiresult_failed);
                    mTestwifitxt.setTextColor(Color.RED);
//	   	    	Log.i(TAG,"wifi test failed");
                    try {
                        writeExcel(ResultFile,requestCode,false);
                    } catch (BiffException e) {
                        // TODO 自动生成的 catch 块
                        e.printStackTrace();
                    }
                }
                testBlueTooth();
                break;
            case mbluetooth:
                mbtresult = data.getBooleanExtra("sucess",false);
                if(mbtresult == true){
                    mTestbttxt.setText(R.string.test_btresult_ok);
                    mTestbttxt.setTextColor(Color.GREEN);
//	   	    	Log.i(TAG,"bluetooth test sucess");
                    try {
                        writeExcel(ResultFile,requestCode,true);
                    } catch (BiffException e) {
                        // TODO 自动生成的 catch 块
                        e.printStackTrace();
                    }
                }else{
                    mTestbttxt.setText(R.string.test_btresult_failed);
                    mTestbttxt.setTextColor(Color.RED);
//	   	    	Log.i(TAG,"bluetooth test failed");
                    try {
                        writeExcel(ResultFile,requestCode,false);
                    } catch (BiffException e) {
                        // TODO 自动生成的 catch 块
                        e.printStackTrace();
                    }
                }
                testTouchScreen();
                break;

            case mtouch:
                mtouchresult = data.getBooleanExtra("sucess",false);
                if(mtouchresult == true){
                    mTesttouchtxt.setText(R.string.test_touchresult_ok);
                    mTesttouchtxt.setTextColor(Color.GREEN);
                    try {
                        writeExcel(ResultFile,requestCode,true);
                    } catch (BiffException e) {
                        // TODO 自动生成的 catch 块
                        e.printStackTrace();
                    }
//	   	    	Log.i(TAG,"touch test sucess");
                }else{
                    mTesttouchtxt.setText(R.string.test_touchresult_failed);
                    mTesttouchtxt.setTextColor(Color.RED);
                    try {
                        writeExcel(ResultFile,requestCode,false);
                    } catch (BiffException e) {
                        // TODO 自动生成的 catch 块
                        e.printStackTrace();
                    }
//	   	    	Log.i(TAG,"touch test failed");
                }
                testGpio();
                break;

            case mgpio:
                mgpioresult = data.getBooleanExtra("sucess",false);
                if(mgpioresult == true){
                    mTestgpiotxt.setText(R.string.test_gpioresult_ok);
                    mTestgpiotxt.setTextColor(Color.GREEN);
//	   	    	Log.i(TAG,"gpio test sucess");
                    try {
                        writeExcel(ResultFile,requestCode,true);
                    } catch (BiffException e) {
                        // TODO 自动生成的 catch 块
                        e.printStackTrace();
                    }
                }else{
                    mTestgpiotxt.setText(R.string.test_gpioresult_failed);
                    mTestgpiotxt.setTextColor(Color.RED);
//	   	    	Log.i(TAG,"gpio test failed");
                    try {
                        writeExcel(ResultFile,requestCode,false);
                    } catch (BiffException e) {
                        // TODO 自动生成的 catch 块
                        e.printStackTrace();
                    }
                }
                testAudioVideo();
                break;
            case mav:
                mavresult = data.getBooleanExtra("sucess",false);
                if(mavresult == true){
                    mTestavtxt.setText(R.string.test_avresult_ok);
                    mTestavtxt.setTextColor(Color.GREEN);
                    try {
                        writeExcel(ResultFile,requestCode,true);
                    } catch (BiffException e) {
                        // TODO 自动生成的 catch 块
                        e.printStackTrace();
                    }
//	   	    	Log.i(TAG,"video and audio test sucess");
                }else{
                    mTestavtxt.setText(R.string.test_avresult_failed);
                    mTestavtxt.setTextColor(Color.RED);
                    try {
                        writeExcel(ResultFile,requestCode,false);
                    } catch (BiffException e) {
                        // TODO 自动生成的 catch 块
                        e.printStackTrace();
                    }
//	   	    	Log.i(TAG,"video and audio test failed");
                }
                testRecord();
                break;
            case mrecord:
                mrecordresult = data.getBooleanExtra("sucess",false);
                if(mrecordresult == true){
                    mTestrecordtxt.setText(R.string.test_recordresult_ok);
                    mTestrecordtxt.setTextColor(Color.GREEN);
                    try {
                        writeExcel(ResultFile,requestCode,true);
                    } catch (BiffException e) {
                        // TODO 自动生成的 catch 块
                        e.printStackTrace();
                    }
//	   	    	Log.i(TAG,"record test sucess");
                }else{
                    mTestrecordtxt.setText(R.string.test_recordresult_failed);
                    mTestrecordtxt.setTextColor(Color.RED);
//	   	    	Log.i(TAG,"record test failed");
                    try {
                        writeExcel(ResultFile,requestCode,false);
                    } catch (BiffException e) {
                        // TODO 自动生成的 catch 块
                        e.printStackTrace();
                    }
                }
                testUart();
                break;
            case mfingerprint:
                mfpresult = data.getBooleanExtra("sucess",false);
                if(mfpresult == true){
                    mTestfptxt.setText(R.string.test_fpresult_ok);
                    mTestfptxt.setTextColor(Color.GREEN);
//	   	    	Log.i(TAG,"fingerprint test sucess");
                    try {
                        writeExcel(ResultFile,requestCode,true);
                    } catch (BiffException e) {
                        // TODO 自动生成的 catch 块
                        e.printStackTrace();
                    }
                }else{
                    mTestfptxt.setText(R.string.test_fpresult_failed);
                    mTestfptxt.setTextColor(Color.RED);
//	   	    	Log.i(TAG,"fingerprint test failed");
                    try {
                        writeExcel(ResultFile,requestCode,false);
                    } catch (BiffException e) {
                        // TODO 自动生成的 catch 块
                        e.printStackTrace();
                    }
                }
                break;

            case mgsensor:
                mgsensorresult = data.getBooleanExtra("sucess",false);
                if(mgsensorresult == true){
                    mTestGsensortxt.setText(R.string.test_gsensorresult_ok);
                    mTestGsensortxt.setTextColor(Color.GREEN);
//	   	    	Log.i(TAG,"fingerprint test sucess");
                    try {
                        writeExcel(ResultFile,requestCode,true);
                    } catch (BiffException e) {
                        // TODO 自动生成的 catch 块
                        e.printStackTrace();
                    }
                }else{
                    mTestGsensortxt.setText(R.string.test_gsensorresult_failed);
                    mTestGsensortxt.setTextColor(Color.RED);
//	   	    	Log.i(TAG,"fingerprint test failed");
                    try {
                        writeExcel(ResultFile,requestCode,false);
                    } catch (BiffException e) {
                        // TODO 自动生成的 catch 块
                        e.printStackTrace();
                    }
                }
                break;
            case mfpflash:
                mgfpflashresult = data.getBooleanExtra("sucess",false);
                if(mgfpflashresult == true){
                    mTestfpflashtxt.setText(R.string.test_gfpflashresult_ok);
                    mTestfpflashtxt.setTextColor(Color.GREEN);
//	   	    	Log.i(TAG,"fingerprint test sucess");
                    try {
                        writeExcel(ResultFile,requestCode,true);
                    } catch (BiffException e) {
                        // TODO 自动生成的 catch 块
                        e.printStackTrace();
                    }
                }else{
                    mTestfpflashtxt.setText(R.string.test_gfpflashresult_failed);
                    mTestfpflashtxt.setTextColor(Color.RED);
//	   	    	Log.i(TAG,"fingerprint test failed");
                    try {
                        writeExcel(ResultFile,requestCode,false);
                    } catch (BiffException e) {
                        // TODO 自动生成的 catch 块
                        e.printStackTrace();
                    }
                }
                break;
            case muart:
                muartresult = data.getBooleanExtra("sucess",false);
                if(muartresult == true){
                    mTestUarttxt.setText(R.string.test_uartresult_ok);
                    mTestUarttxt.setTextColor(Color.GREEN);
//	   	    	Log.i(TAG,"fingerprint test sucess");
                    try {
                        writeExcel(ResultFile,requestCode,true);
                    } catch (BiffException e) {
                        // TODO 自动生成的 catch 块
                        e.printStackTrace();
                    }
                }else{
                    mTestUarttxt.setText(R.string.test_uartresult_failed);
                    mTestUarttxt.setTextColor(Color.RED);
//	   	    	Log.i(TAG,"fingerprint test failed");
                    try {
                        writeExcel(ResultFile,requestCode,false);
                    } catch (BiffException e) {
                        // TODO 自动生成的 catch 块
                        e.printStackTrace();
                    }
                }
                break;
            default:
                break;

        }

        super.onActivityResult(requestCode,resultCode,data);
    }

    private int testLcd() {
//        DialogFragment newFragment = new AutoRunDialog(this);
//        newFragment.show(getSupportFragmentManager(), "missiles");
        
        mIntent =new Intent(ResultActivity.this,TestColor.class);
        //mIntent.putExtras(mLcdBundle);
        //mIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        //finish();
        requestCode = mlcd;
        startActivityForResult(mIntent, requestCode);
            
        return requestCode;
    }
    private int testSdCard() {
        mIntent =new Intent(ResultActivity.this,TestSd.class);
        //mIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        //finish();
        requestCode = msd;
        startActivityForResult(mIntent, requestCode);
        

        return requestCode;
    }
    private int testCamera() {
        mIntent =new Intent(ResultActivity.this,TestCamera.class);
        //mIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        //finish();
        requestCode = mcamera;
        startActivityForResult(mIntent, requestCode);
//				startActivity(mIntent);


        return requestCode;
    }
    private int testNetWork() {
        mIntent =new Intent(ResultActivity.this,TestWiFi.class);
        //mIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        //finish();
        requestCode = mwifi;
        startActivityForResult(mIntent, requestCode);
//				startActivity(mIntent);


        return requestCode;
    }
    private int testBlueTooth() {
        mIntent =new Intent(ResultActivity.this,TestBluetooth.class);
        //mIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        //finish();
        requestCode = mbluetooth;
        startActivityForResult(mIntent, requestCode);
//				startActivity(mIntent);

        return requestCode;
    }
    private int testTouchScreen() {
        mIntent =new Intent(ResultActivity.this,TestTouch.class);
        //mIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        //finish();
        requestCode = mtouch;
        startActivityForResult(mIntent, requestCode);
//				startActivity(mIntent);

        return requestCode;
    }
    private int testGpio() {
        mIntent =new Intent(ResultActivity.this,TestGpio.class);
        //mIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        //finish();
        requestCode = mgpio;
        startActivityForResult(mIntent, requestCode);
//				startActivity(mIntent);

        return requestCode;
    }
    private int testAudioVideo() {
        mIntent =new Intent(ResultActivity.this,TestAudioVideo.class);
        //mIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        //finish();
        requestCode = mav;
        startActivityForResult(mIntent, requestCode);
//				startActivity(mIntent);

        return requestCode;
    }
    private int testRecord() {
        mIntent =new Intent(ResultActivity.this,TestRecord.class);
        //mIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        //finish();
        requestCode = mrecord;
        startActivityForResult(mIntent, requestCode);
//				startActivity(mIntent);

        return requestCode;
    }
    private void testFingerPrint() {
        //                mIntent =new Intent(MainActivity.this,TestFingerprint.class);
//                //mIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                //finish();
//                requestCode = mfingerprint;
//                startActivityForResult(mIntent, requestCode);
////				startActivity(mIntent);
    }
    private void testGSensor() {
//                        mIntent =new Intent(MainActivity.this,TestGsensor.class);
//                //mIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                //finish();
//                requestCode = mgsensor;
//                startActivityForResult(mIntent, requestCode);
////				startActivity(mIntent);
    }
    private void testFpFlash() {
        //                mIntent =new Intent(MainActivity.this,TestFpFlash.class);
//                //mIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                //finish();
//                requestCode = mfpflash;
//                startActivityForResult(mIntent, requestCode);
//                //startActivity(mIntent);
    }
    private int testUart() {
        //mLcdBundle = new Bundle();
        //mLcdBundle.putBoolean("result", false);
        Log.d(TAG,"testuart");
        mIntent =new Intent(ResultActivity.this,TestUart.class);
        //mIntent =new Intent(MainActivity.this,SerialPortSet.class);
        //mIntent.putExtras(mLcdBundle);
        //mIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        //finish();
        requestCode = muart;
        startActivityForResult(mIntent, requestCode);
        //startActivity(mIntent);

        
        return requestCode;
    }

    public void CreateShowTable()
    {
        //新建TableLayout01的实例
        tableLayout = (TableLayout)findViewById(R.id.TableLayout01);
        //全部列自动填充空白处
        tableLayout.setStretchAllColumns(true);

//       //生成12行，2列的表格
//       for(int row=0;row<12;row++)
//       {
//           TableRow tableRow=new TableRow(this);
//           for(int col=0;col<2;col++)
//           {
//        	   if(col == 0)
//        	   {
//                   //tv用于显示
//                   TextView tv=new TextView(this);
//                   tv.setText(mstrarrayname[row]);
//                   tableRow.addView(tv);
//        	   }
//        	   else
//        	   {
//                   TextView tv=new TextView(this);
//                   //if(mstrarrayresult[row] == "noused")
//                   tv.setText("未测试");
//                   tableRow.addView(tv);
//        	   }
//           }
//           //新建的TableRow添加到TableLayout
//           tableLayout.addView(tableRow, new TableLayout.LayoutParams(FP, WC));
//       }


    }

    public void initResultFile() throws WriteException
    {
        Label lbl1;
        Label bll2;
        Label bll3;
        WritableWorkbook wwb = null;
        File ResultFileDir = getExternalFilesDir("autoTest");
        File resultFile = new File(ResultFileDir.getAbsolutePath() + "/testresult.xls");
        if (!resultFile.exists()) {
            try {
                boolean isExist = resultFile.createNewFile();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            // 创建一个可写入的工作薄(Workbook)对象
            wwb = Workbook.createWorkbook(resultFile);
        } catch (IOException e) {
            Log.e(TAG, "create work book failed");
            e.printStackTrace();
        }
        if (wwb != null) {
            // 第一个参数是工作表的名称，第二个是工作表在工作薄中的位置
            WritableSheet ws = wwb.createSheet("sheet0", 0);
            //WritableSheet ws = wwb.getSheet(0);
            // 在指定单元格插入数据
//
            for (int i = 0; i < 13; i++) {
                for (int j = 0; j < 2; j++) {
                    if (j == 0) {
                        lbl1 = new Label(j, i, mstrarrayname[i]);

                        try {
                            ws.addCell(lbl1);
                        } catch (WriteException e) {
                            e.printStackTrace();
                        }


                    } else {
                        bll2 = new Label(j, i, mstrarrayresult[i]);
                        try {
                            ws.addCell(bll2);
                        } catch (WriteException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
            for (int k = 0; k < 13; k++) {
                bll3 = new Label(2, k, "0000-00-00   00:00:00");
                try {
                    ws.addCell(bll3);
                } catch (WriteException e) {
                    e.printStackTrace();
                }
            }
//			try {
//				ws.addCell(lbl1);
//				//ws.addCell(bll2);
//			} catch (RowsExceededException e1) {
//				e1.printStackTrace();
//			} catch (WriteException e1) {
//				e1.printStackTrace();
//			}
            try {
                // 从内存中写入文件中
                wwb.write();
                wwb.close();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (WriteException e) {
                e.printStackTrace();
            }
        }
    }

    public void InitResult()
    {
        try{
            Workbook wb=Workbook.getWorkbook(new File(ResultFile));//获取文档
            Sheet sheet=wb.getSheet(0);             //获取工作簿
            Cell c;
            Cell ct1,ct2;
            Date d1,d2 = null,dmax = null;
            Date[] d = new Date[13];
            SimpleDateFormat dataformat = new   SimpleDateFormat("yyyy-MM-dd   hh:mm:ss");

            for(int i = 0;i<13; i++)
            {
                c=sheet.getCell(1, i);             //获取单元格
                mstrarrayresult[i] = c.getContents();
            }
            for(int j = 0;j<13;j++)
            {
                ct1 = sheet.getCell(2,j);
                d[j] = dataformat.parse(ct1.getContents());
                //ct2 = sheet.getCell(2,j+1);
                //d1 = dataformat.parse(ct1.getContents());
                //Log.e(TAG,"d1 "+d[j]);
                //d2 = dataformat.parse(ct2.getContents());
                //Log.e(TAG,"d1 "+d2);
                //if((d1.getTime()-d2.getTime())>0)
                //dmax = d1;
                //else
                //dmax = d2;

            }
            for(int k = 0;k<12;k++)
            {
                if (d[k].getTime()>d[k+1].getTime())
                    d[k+1] = d[k];

            }
            dmax = d[11];
            String formattedTime = dataformat.format(dmax);
            Log.e(TAG,"the last time is "+formattedTime);
            mTestLastTime.setText(formattedTime);
            wb.close();
        }catch(IOException | BiffException | ParseException e) {
            Log.e(TAG, "INITRESULT failed");
            e.printStackTrace();
        }
    }

    public void writeExcel(String fileName,int num,boolean isok) throws BiffException {
        WritableWorkbook wwb = null;
        Workbook wb = null;
        Label lbl1;
        Label bll2;
        SimpleDateFormat   sDateFormat   =   new   SimpleDateFormat("yyyy-MM-dd   hh:mm:ss");
        String   strtime   =   sDateFormat.format(new   java.util.Date());

        try {
            // 创建一个可写入的工作薄(Workbook)对象
            wb = Workbook.getWorkbook(new File(ResultFile));//获取文档
            wwb = Workbook.createWorkbook(new File(fileName), wb);//获取文档副本
        } catch (IOException e) {
            Log.e(TAG,"create work book failed");
            e.printStackTrace();
        }
        if (wwb != null) {
            // 第一个参数是工作表的名称，第二个是工作表在工作薄中的位置
            //WritableSheet ws = wwb.createSheet("sheet0", 0);
            WritableSheet ws = wwb.getSheet(0);
            // 在指定单元格插入数据
//
            if(isok)
                lbl1 = new Label(1, num-1, "成功");
            else
                lbl1 = new Label(1, num-1, "失败");
            bll2 = new Label(2,num-1,strtime);
            try {
                ws.addCell(lbl1);
                ws.addCell(bll2);
            } catch (RowsExceededException e1) {
                e1.printStackTrace();
            } catch (WriteException e1) {
                e1.printStackTrace();
            }
            try {
                // 从内存中写入文件中
                wwb.write();
                wwb.close();
                //wb.close();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (WriteException e) {
                e.printStackTrace();
            }
        }
    }

}
