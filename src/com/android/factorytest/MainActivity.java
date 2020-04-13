package com.android.factorytest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.OutputStreamWriter;

import android.os.Build;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;
import jxl.Cell;
public class MainActivity extends Activity
{
    Build bd = new Build();
    String model = bd.MODEL;


    private static final String TAG = "factorytest";
    private Button mTestColor = null;
    private Button mTestSd = null;
    private Button mTestCamera = null;
    private Button mTestWiFi = null;
    private Button mTestBluetooth = null;
    private Button mTestTouch = null;
    private Button mTestPin = null;
    private Button mTestAudioVideo = null;
    private Button mTestRecord = null;
    private Button mTestFingerprint = null;
    private Button mTestGsensor = null;
    private Button mTestFpFlash = null;
    private Button mExporttxt = null;
    private Button mExit = null;
    private Button mTestUart= null;

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

    private Bundle mLcdBundle = null;

    private int requestCode = 0;

    private static  Boolean mlcdresult = false;
    private static  Boolean msdresult = false;
    private static  Boolean mcameraresult = false;
    private static  Boolean mwifiresult = false;
    private static  Boolean mbtresult = false;
    private static  Boolean mtouchresult = false;
    private static  Boolean mgpioresult = false;
    private static  Boolean mavresult = false;
    private static  Boolean mrecordresult = false;
    private static  Boolean mfpresult = false;
    private static  Boolean mgsensorresult = false;
    private static  Boolean mgfpflashresult = false;
    private static  Boolean muartresult = false;

    private static final int mlcd = 1;
    private static final int msd = 2;
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
//	private static final int mlcd = 1;

//	private static  String mstrlcdresult = "noused";
//	private static  String mstrsdresult = "noused";
//	private static  String mstrcameraresult = "noused";
//	private static  String mstrwifiresult = "noused";
//	private static  String mstrbtresult = "noused";
//	private static  String mstrtouchresult = "noused";
//	private static  String mstrgpioresult = "noused";
//	private static  String mstravresult = "noused";
//	private static  String mstrrecordresult ="noused";
//	private static  String mstrfpresult = "noused";
//	private static  String mstrgsensorresult = "noused";
//	private static  String mstrfpflashresult = "notused";

    private static  String mstrarrayresult[] = new String[13];
    private static  String mstrarrayname[] = new String[13];
    private static String ResultFile = "/sdcard/testresult.xls";
    static {
        System.loadLibrary("factorytest");
    }
    public native int ReadAdc();
    private final int WC = ViewGroup.LayoutParams.WRAP_CONTENT;
    private final int FP = ViewGroup.LayoutParams.FILL_PARENT;

    private boolean isFileexist = false;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        showstate();
//        initText();
        initButton();
//        checkfile();
//        try {
//            initanything();
//        } catch (RowsExceededException e) {
//            // TODO 自动生成的 catch 块
//            e.printStackTrace();
//        } catch (WriteException e) {
//            // TODO 自动生成的 catch 块
//            e.printStackTrace();
//        }



    }
    public void showstate()
    {
        TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        StringBuilder sb = new StringBuilder();
        sb.append("\nDeviceId(IMEI) = " + tm.getDeviceId());
        /*sb.append("\nDeviceSoftwareVersion = " + tm.getDeviceSoftwareVersion());
        sb.append("\nLine1Number = " + tm.getLine1Number());
        sb.append("\nNetworkCountryIso = " + tm.getNetworkCountryIso());
        sb.append("\nNetworkOperator = " + tm.getNetworkOperator());
        sb.append("\nNetworkOperatorName = " + tm.getNetworkOperatorName());
        sb.append("\nNetworkType = " + tm.getNetworkType());
        sb.append("\nPhoneType = " + tm.getPhoneType());
        sb.append("\nSimCountryIso = " + tm.getSimCountryIso());
        sb.append("\nSimOperator = " + tm.getSimOperator());
        sb.append("\nSimOperatorName = " + tm.getSimOperatorName());
        sb.append("\nSimSerialNumber = " + tm.getSimSerialNumber());
        sb.append("\nSimState = " + tm.getSimState());
        sb.append("\nSubscriberId(IMSI) = " + tm.getSubscriberId());
        sb.append("\nVoiceMailNumber = " + tm.getVoiceMailNumber());*/
        sb.append("\nModel = " + android.os.Build.MODEL);
        sb.append("\nBoard = " + android.os.Build.BOARD);
        //sb.append("\nBootloader = " + android.os.Build.BOOTLOADER);
        sb.append("\nBrand = " + android.os.Build.BRAND);
        sb.append("\nDevice = " + android.os.Build.DEVICE);
        sb.append("\nDisplay = " + android.os.Build.DISPLAY);
        sb.append("\nFINGERPRINT = " + android.os.Build.FINGERPRINT);
        sb.append("\nHARDWARE = " + android.os.Build.HARDWARE);
        sb.append("\nHOST = " + android.os.Build.HOST);
        sb.append("\nID = " + android.os.Build.ID);
        sb.append("\nMANUFACTURER   = " + android.os.Build.MANUFACTURER  );
        sb.append("\nPRODUCT = " + android.os.Build.PRODUCT);
        sb.append("\nTAGS = " + android.os.Build.TAGS);
//        sb.append("\nSERIAL = " + android.os.Build.SERIAL);
        //sb.append("\nTIME  = " + android.os.Build. TIME );
        sb.append("\nTYPE = " + android.os.Build.TYPE);
        //sb.append("\nUSER = " + android.os.Build.USER);
        //String mtype = android.os.Build.MODEL;
        Log.e("info", sb.toString());
    }
    public void checkfile()
    {
        File fp = new File(ResultFile);
        if(fp.exists())
            isFileexist = true;
        else
            isFileexist = false;
    }
    public void initResultFile() throws RowsExceededException, WriteException
    {
        Label lbl1;
        Label bll2;
        Label bll3;
        WritableWorkbook wwb = null;
        try {
            // 创建一个可写入的工作薄(Workbook)对象
            wwb = Workbook.createWorkbook(new File(ResultFile));
        } catch (IOException e) {
            Log.e(TAG,"create work book failed");
            e.printStackTrace();
        }
        if (wwb != null) {
            // 第一个参数是工作表的名称，第二个是工作表在工作薄中的位置
            WritableSheet ws = wwb.createSheet("sheet0", 0);
            //WritableSheet ws = wwb.getSheet(0);
            // 在指定单元格插入数据
//
            for(int i = 0; i<13; i++)
            {
                for(int j = 0; j<2; j++)
                {
                    if(j==0)
                    {
                        lbl1 = new Label(j, i,mstrarrayname[i] );
                        ws.addCell(lbl1);
                    }
                    else
                    {
                        bll2 = new Label(j, i,mstrarrayresult[i] );
                        ws.addCell(bll2);
                    }
                }
            }
            for(int k=0;k<13;k++)
            {
                bll3 = new Label(2,k,"0000-00-00   00:00:00");
                ws.addCell(bll3);
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
    public void initanything() throws RowsExceededException, WriteException
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

        if(!isFileexist)
            initResultFile();
        else
            showresult();

    }
    public void readfromfile()
    {

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
    public void InitResult()
    {
        try{
            Workbook wb=Workbook.getWorkbook(new File(ResultFile));//获取文档
            Sheet sheet=wb.getSheet(0);             //获取工作簿
            Cell c;
            Cell ct1,ct2;
            Date d1,d2 = null,dmax = null;
            Date d[] = new Date[13];
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

    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        switch (requestCode)
        {
            case mlcd:
//    	     Bundle bundle = data.getExtras();
//    	     boolean lcd = bundle.getBoolean("sucess");
                mlcdresult = data.getBooleanExtra("sucess",false);
                if(mlcdresult == true)
                {
                    mTestColor.setBackgroundColor(Color.GREEN);
                    mTestLcdtxt.setText(R.string.test_colorresult_ok);
                    mTestLcdtxt.setTextColor(Color.GREEN);
//    	    	 Log.i(TAG,"lcd test sucess");
                    try {
                        writeExcel(ResultFile,requestCode,true);
                    } catch (BiffException e) {
                        // TODO 自动生成的 catch 块
                        e.printStackTrace();
                    }
                }
                else
                {
                    mTestColor.setBackgroundColor(Color.RED);
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
                break;
            case  msd:
                msdresult = data.getBooleanExtra("sucess",false);
                if(msdresult == true){
                    mTestSd.setBackgroundColor(Color.GREEN);
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
                    mTestSd.setBackgroundColor(Color.RED);
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
                break;
            case mcamera:
                mcameraresult = data.getBooleanExtra("sucess",false);
                if(mcameraresult == true){
                    mTestCamera.setBackgroundColor(Color.GREEN);
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
                    mTestCamera.setBackgroundColor(Color.RED);
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
                break;
            case mwifi:
                mwifiresult = data.getBooleanExtra("sucess",false);
                if(mwifiresult == true){
                    mTestWiFi.setBackgroundColor(Color.GREEN);
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
                    mTestWiFi.setBackgroundColor(Color.RED);
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
                break;
            case mbluetooth:
                mbtresult = data.getBooleanExtra("sucess",false);
                if(mbtresult == true){
                    mTestBluetooth.setBackgroundColor(Color.GREEN);
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
                    mTestBluetooth.setBackgroundColor(Color.RED);
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
                break;
            case mtouch:
                mtouchresult = data.getBooleanExtra("sucess",false);
                if(mtouchresult == true){
                    mTestTouch.setBackgroundColor(Color.GREEN);
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
                    mTestTouch.setBackgroundColor(Color.RED);
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
                break;
            case mgpio:
                mgpioresult = data.getBooleanExtra("sucess",false);
                if(mgpioresult == true){
                    mTestPin.setBackgroundColor(Color.GREEN);
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
                    mTestPin.setBackgroundColor(Color.RED);
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
                break;
            case mav:
                mavresult = data.getBooleanExtra("sucess",false);
                if(mavresult == true){
                    mTestAudioVideo.setBackgroundColor(Color.GREEN);
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
                    mTestAudioVideo.setBackgroundColor(Color.RED);
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
                break;
            case mrecord:
                mrecordresult = data.getBooleanExtra("sucess",false);
                if(mrecordresult == true){
                    mTestRecord.setBackgroundColor(Color.GREEN);
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
                    mTestRecord.setBackgroundColor(Color.RED);
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
                break;
            case mfingerprint:
                mfpresult = data.getBooleanExtra("sucess",false);
                if(mfpresult == true){
                    mTestFingerprint.setBackgroundColor(Color.GREEN);
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
                    mTestFingerprint.setBackgroundColor(Color.RED);
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
                    mTestGsensor.setBackgroundColor(Color.GREEN);
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
                    mTestGsensor.setBackgroundColor(Color.RED);
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
                    mTestFpFlash.setBackgroundColor(Color.GREEN);
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
                    mTestFpFlash.setBackgroundColor(Color.RED);
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
                    mTestUart.setBackgroundColor(Color.GREEN);
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
                    mTestUart.setBackgroundColor(Color.RED);
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
    private void initButton()
    {
        mTestColor = (Button)findViewById(R.id.test_color);
        mTestSd = (Button)findViewById(R.id.test_sd);
        mTestCamera = (Button)findViewById(R.id.test_camera);
        mTestCamera.setEnabled(false);
        mTestWiFi = (Button)findViewById(R.id.test_wifi);
        mTestBluetooth = (Button)findViewById(R.id.test_bluetooth);
        mTestTouch = (Button)findViewById(R.id.test_touch);
        mTestPin = (Button)findViewById(R.id.test_pin);
        //mTestPin.setEnabled(false);
        mTestAudioVideo = (Button)findViewById(R.id.test_AudioVideo);
        mTestRecord = (Button)findViewById(R.id.test_Record);
        mTestFingerprint = (Button)findViewById(R.id.test_Fingerprint);
        //mTestFingerprint.setEnabled(false);
        mTestGsensor = (Button)findViewById(R.id.test_Gsensor);
        mTestGsensor.setEnabled(false);
        mTestFpFlash = (Button)findViewById(R.id.test_FpFlash);
        mTestFpFlash.setEnabled(false);
        mTestUart = (Button)findViewById(R.id.test_Uart);
        mExporttxt = (Button)findViewById(R.id.test_export);
        mExit = (Button)findViewById(R.id.test_exit);

        mExit.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //mLcdBundle = new Bundle();
                //mLcdBundle.putBoolean("result", false);
                //mIntent =new Intent(MainActivity.this,TestColor.class);
                //mIntent.putExtras(mLcdBundle);
                //mIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                //finish();
                //requestCode = mlcd;
                //startActivityForResult(mIntent, requestCode);
                //startActivity(mIntent);

                System.exit(0);
            }
        });


        mExporttxt.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {


                try {
                    FileOutputStream fos=new FileOutputStream ("/sdcard/testresult.txt");
                    OutputStreamWriter osw=new OutputStreamWriter(fos,"UTF-8");
                    //String[] strs = str.split("!");
                    osw.write("<HTML><HEAD><META content=\"IE=11.0000\"http-equiv=\"X-UA-Compatible\">\r\n");
                    osw.write("<TITLE>测试结果</TITLE>  \r\n");
                    osw.write("<META http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\"></HEAD> \r\n");
                    osw.write("<TABLE width=\"85%\" class=\"p\" border=\"1\" cellspacing=\"0\" cellpadding=\"3\"> \r\n");
                    osw.write("<TBODY> \n<TR class=\"at\">\n<TH>测试项</TH>\n<TH>测试结果</TH></TR> \r\n");
                    osw.write("<TR>\n <TD>LCD</TD> \r\n");
                    osw.write("<TD>" + mTestLcdtxt.getText().toString()+"</TD></TR>");
                    osw.write("<TR>\n <TD>SD卡</TD> \r\n");
                    osw.write("<TD>" + mTestsdtxt.getText().toString()+"</TD></TR>");
                    osw.write("<TR>\n <TD>Camera</TD> \r\n");
                    osw.write("<TD>" + mTestcameratxt.getText().toString()+"</TD></TR>");
                    osw.write("<TR>\n <TD>WIFI</TD> \r\n");
                    osw.write("<TD>" + mTestwifitxt.getText().toString()+"</TD></TR>");
                    osw.write("<TR>\n <TD>蓝牙</TD> \r\n");
                    osw.write("<TD>" + mTestbttxt.getText().toString()+"</TD></TR>");
                    osw.write("<TR>\n <TD>触摸屏</TD> \r\n");
                    osw.write("<TD>" + mTesttouchtxt.getText().toString()+"</TD></TR>");
                    osw.write("<TR>\n <TD>GPIO</TD> \r\n");
                    osw.write("<TD>" + mTestgpiotxt.getText().toString()+"</TD></TR>");
                    osw.write("<TR>\n <TD>音视频</TD> \r\n");
                    osw.write("<TD>" + mTestavtxt.getText().toString()+"</TD></TR>");
                    osw.write("<TR>\n <TD>录音</TD> \r\n");
                    osw.write("<TD>" + mTestrecordtxt.getText().toString()+"</TD></TR>");
                    osw.write("<TR>\n <TD>指纹</TD> \r\n");
                    osw.write("<TD>" + mTestfptxt.getText().toString()+"</TD></TR>");
                    osw.write("<TR>\n <TD>Gsensor</TD> \r\n");
                    osw.write("<TD>" + mTestGsensortxt.getText().toString()+"</TD></TR>");
                    osw.write("<TR>\n <TD>采集仪FLASH</TD> \r\n");
                    osw.write("<TD>" + mTestfpflashtxt.getText().toString()+"</TD></TR>");
                    osw.write("<TR>\n <TD>串口</TD> \r\n");
                    osw.write("<TD>" + mTestUarttxt.getText().toString()+"</TD></TR>");
                    //osw.write("<TR>\n <TD>LCD</TD> \r\n");
                    SimpleDateFormat   sDateFormat   =   new   SimpleDateFormat("yyyy-MM-dd   hh:mm:ss");
                    String   datastr   =   sDateFormat.format(new   java.util.Date());
                    osw.write("<TR>\n <TD>测试日期</TD> \r\n");
                    osw.write("<TD>" + datastr +"</TD></TR>");
                    osw.write( "</TBODY></TABLE></HTML>\r\n");

                    osw.flush();
                    fos.close();
                    File f = new File("/sdcard/testresult.txt");
                    f.renameTo(new File("/sdcard/testresult.htm"));
                    //f.
                } catch (IOException e) {
                    // TODO 自动生成的 catch 块
                    e.printStackTrace();
                }
            }
        });

        mTestUart.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //mLcdBundle = new Bundle();
                //mLcdBundle.putBoolean("result", false);
                Log.d(TAG,"testuart");
                mIntent =new Intent(MainActivity.this,TestUart.class);
                //mIntent =new Intent(MainActivity.this,SerialPortSet.class);
                //mIntent.putExtras(mLcdBundle);
                //mIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                //finish();
                requestCode = muart;
                startActivityForResult(mIntent, requestCode);
                //startActivity(mIntent);
            }
        });

        mTestColor.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //mLcdBundle = new Bundle();
                //mLcdBundle.putBoolean("result", false);
                mIntent =new Intent(MainActivity.this,TestColor.class);
                //mIntent.putExtras(mLcdBundle);
                //mIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                //finish();
                requestCode = mlcd;
                startActivityForResult(mIntent, requestCode);
                //startActivity(mIntent);
            }
        });

        mTestSd.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                mIntent =new Intent(MainActivity.this,TestSd.class);
                //mIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                //finish();
                requestCode = msd;
                startActivityForResult(mIntent, requestCode);
                //startActivity(mIntent);
            }
        });
        mTestCamera.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                mIntent =new Intent(MainActivity.this,TestCamera.class);
                //mIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                //finish();
                requestCode = mcamera;
                startActivityForResult(mIntent, requestCode);
//				startActivity(mIntent);
            }
        });
        mTestWiFi.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                mIntent =new Intent(MainActivity.this,TestWiFi.class);
                //mIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                //finish();
                requestCode = mwifi;
                startActivityForResult(mIntent, requestCode);
//				startActivity(mIntent);
            }
        });
        mTestBluetooth.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                mIntent =new Intent(MainActivity.this,TestBluetooth.class);
                //mIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                //finish();
                requestCode = mbluetooth;
                startActivityForResult(mIntent, requestCode);
//				startActivity(mIntent);
            }
        });
        mTestTouch.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                mIntent =new Intent(MainActivity.this,TestTouch.class);
                //mIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                //finish();
                requestCode = mtouch;
                startActivityForResult(mIntent, requestCode);
//				startActivity(mIntent);
            }
        });

        mTestPin.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                mIntent =new Intent(MainActivity.this,TestGpio.class);
                //mIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                //finish();
                requestCode = mgpio;
                startActivityForResult(mIntent, requestCode);
//				startActivity(mIntent);
            }
        });

        mTestAudioVideo.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                mIntent =new Intent(MainActivity.this,TestAudioVideo.class);
                //mIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                //finish();
                requestCode = mav;
                startActivityForResult(mIntent, requestCode);
//				startActivity(mIntent);
            }
        });

        mTestRecord.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                mIntent =new Intent(MainActivity.this,TestRecord.class);
                //mIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                //finish();
                requestCode = mrecord;
                startActivityForResult(mIntent, requestCode);
//				startActivity(mIntent);
            }
        });
        mTestFingerprint.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
//                mIntent =new Intent(MainActivity.this,TestFingerprint.class);
//                //mIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                //finish();
//                requestCode = mfingerprint;
//                startActivityForResult(mIntent, requestCode);
////				startActivity(mIntent);
            }
        });
        mTestGsensor.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
//                mIntent =new Intent(MainActivity.this,TestGsensor.class);
//                //mIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                //finish();
//                requestCode = mgsensor;
//                startActivityForResult(mIntent, requestCode);
////				startActivity(mIntent);
            }
        });
        mTestFpFlash.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
//                mIntent =new Intent(MainActivity.this,TestFpFlash.class);
//                //mIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                //finish();
//                requestCode = mfpflash;
//                startActivityForResult(mIntent, requestCode);
//                //startActivity(mIntent);
            }
        });
    }
}