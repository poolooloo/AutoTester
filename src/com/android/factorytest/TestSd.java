package com.android.factorytest;

import java.io.File;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.os.storage.StorageManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import android.content.Context;

public class TestSd extends Activity 
{
	private static final String TAG = "factorytest";
	private TextView mTestSd = null;
	private Button mReturn = null;
//	private Button mNext = null;
	private Intent mIntent = null;
	private String devicename;
	private UsbManager mManager;
	//private File sdcardDir;
	private String sdcardDir;
	//private String PATH ="/abc";
	//private String FILENAME = "/sdcard/cet4hard.txt";
	//private String PATH =Environment.getExternalStorageDirectory().toString();
	private String FILENAME = "/cet4hard.txt";
	private String sddirname = "/external_sd";
	private String usbFileName = "/usb_storage/USB_DISK1/(1)/test.txt";
	private String usbdirname = "/usb_storage/USB_DISK1/(1)";
	private String usbFileName1 = "/usb_storage/USB_DISK1/udisk0/test.txt";
	private String usbdirname1 = "/usb_storage/USB_DISK1/udisk0";
	//private String FILENAME = "/mnt/sdcard/cet4hard.txt";//emulator test
	private int mstate = 0;
	private int musekey = 0;
	private boolean mresult = false;
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test_sd);
		boolean is_removale = true;
		//sdcardDir = Environment.getExternalStorageDirectory();//无法使用getExternalDirectory，获取到的外置SD卡目录不正确
		sdcardDir = "/mnt";
		mManager = (UsbManager) getSystemService(Context.USB_SERVICE);
	/*	StorageManager mStorageManager = (StorageManager) getSystemService(Context.STORAGE_SERVICE);

		Class<?> storageVolumeClazz = null;
		try {
			storageVolumeClazz = Class.forName("android.os.storage.StorageVolume");
			Method getVolumeList = mStorageManager.getClass().getMethod("getVolumeList");
			Method getPath = storageVolumeClazz.getMethod("getPath");
			Method isRemovable = storageVolumeClazz.getMethod("isRemovable");
			Method getUserLabel = storageVolumeClazz.getMethod("getUserLabel");

			Object result = getVolumeList.invoke(mStorageManager);
			final int length = Array.getLength(result);
			for (int i = 0; i < length; i++) {
				Object storageVolumeElement = Array.get(result, i);
				String path = (String) getPath.invoke(storageVolumeElement);
				String userLabel = (String) getUserLabel.invoke(storageVolumeElement);
				boolean removable = (Boolean) isRemovable.invoke(storageVolumeElement);

				if (is_removale == removable) {
					//return path;
					Log.i(TAG,"path="+path);
					Log.i(TAG,"label="+userLabel);
					Log.i(TAG,"is "+getDisk.invoke(storageVolumeElement));
				}
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}

*/
/*
		int TYPE_PUBLIC = 0;

		File file = null;

		String path = null;

		//StorageManager mStorageManager = getSystemService(Context.STORAGE_SERVICE);
		StorageManager mStorageManager = (StorageManager) getSystemService(Context.STORAGE_SERVICE);

		Class<?> mVolumeInfo = null;
		try {
			mVolumeInfo = Class.forName("android.os.storage.VolumeInfo");


			Method getVolumes = mStorageManager.getClass().getMethod(
					"getVolumes");


			Method volType = mVolumeInfo.getMethod("getType");

			Method isMount = mVolumeInfo.getMethod("isMountedReadable");

			Method getPath = mVolumeInfo.getMethod("getPath");
			//Method getUserLabel = mVolumeInfo.getMethod("getUserLabel");
			Method getDisk = mVolumeInfo.getMethod("getDisk");
			List<Object> mListVolumeinfo = (List<Object>) getVolumes
					.invoke(mStorageManager);



			Log.d("getSdCardPath", "mListVolumeinfo.size="+mListVolumeinfo.size());

			for (int i = 0; i < mListVolumeinfo.size(); i++) {

				int mType = (Integer) volType.invoke(mListVolumeinfo.get(i));

				Log.d("getSdCardPath", "mType=" + mType);

				if (mType == TYPE_PUBLIC) {
					boolean misMount = (Boolean) isMount.invoke(mListVolumeinfo.get(i));
					Log.d("getSdCardPath", "misMount=" + misMount);
					if (misMount) {
						file = (File) getPath.invoke(mListVolumeinfo.get(i));
						if (file != null) {
							path = file.getAbsolutePath();
							Log.d("getSdCardPath", "path=" + path);
							//return path;
						}
					}
				}

			}

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


*/

		String sdcardDir = null;
		File file = null;
		StorageManager storageManager = (StorageManager) getSystemService(Context.STORAGE_SERVICE);
		Class<?> volumeInfoClazz = null;
		Class<?> diskInfoClazz = null;
		try {
			diskInfoClazz = Class.forName("android.os.storage.DiskInfo");
			Method isSd = diskInfoClazz.getMethod("isSd");
			Method isUsb = diskInfoClazz.getMethod("isUsb");
			volumeInfoClazz = Class.forName("android.os.storage.VolumeInfo");
			Method getType = volumeInfoClazz.getMethod("getType");
			Method getDisk = volumeInfoClazz.getMethod("getDisk");
			Method getPath = volumeInfoClazz.getMethod("getPath");
			//Field path = volumeInfoClazz.getDeclaredField("path");
			Method getVolumes = storageManager.getClass().getMethod("getVolumes");
			List<Class<?>> result = (List<Class<?>>) getVolumes.invoke(storageManager);
			for (int i = 0; i < result.size(); i++) {
				Object volumeInfo = result.get(i);
				file = (File) getPath.invoke(volumeInfo);
				if ((int) getType.invoke(volumeInfo) == 0) {
					Object disk = getDisk.invoke(volumeInfo);

					if (disk != null) {
						if ((boolean) isSd.invoke(disk)) {
							sddirname =  file.getAbsolutePath();
							Log.d("getSdCardPath", "path=" + sddirname);
							//sdcardDir = (String) path.get(volumeInfo);
							continue;
						}
						if ((boolean) isUsb.invoke(disk)) {
							usbdirname = file.getAbsolutePath();
							Log.d("getUSBPath", "path=" + usbdirname);
							//sdcardDir = (String) path.get(volumeInfo);
							continue;
						}
					}
				}
			}
			//return sdcardDir + File.separator;
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		//return null;

		for (UsbDevice device : mManager.getDeviceList().values()) {

	            lsusb(device);

	        }

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
	 private static void lsusb(UsbDevice device) {

	        if(device == null)

	            return;

	        

	        String deviceName = device.getDeviceName();

	        String[] deviceNameArray = deviceName.split("/");
	        
	        Log.i(TAG, deviceName); 

//	        toLsusbString(
//
//	                deviceNameArray[deviceNameArray.length - 2],  
//
//	                deviceNameArray[deviceNameArray.length - 1], 
//
//	                String.format("%04x", device.getVendorId()), 
//
//	                String.format("%04x", device.getProductId()),
//
//	                "see:www.linux-usb.org/usb.ids"); // 由于Android系统中没有带usb.ids，这里也只要给出一个网址。

	    }

	 

	    private static String toLsusbString(String bus, String device,

	            String idVendor, String idProduct, String other) {

	        // Bus 002 Device 002: ID 192f:0416 Avago Technologies, Pte.

	        String str = "Bus " + bus + " Device " + device + ": ID " + idVendor

	                + ":" + idProduct + " " + other;

	        Log.i(TAG, str);

	        return str;

	}

	private void initView() 
	{
		try {
			//UsbDeivce device = ;
			//devicename = UsbDevice.getDeviceName()
			Log.d(TAG, "Start Write,");
			//if(Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()))
			if (true) {

				//Log.d(TAG,"step 1,sdcardDir=" + sdcardDir + FILENAME);
				Log.d(TAG, "step 1,sdcardDir=" + sddirname);
				Log.d(TAG, "step 1,sdcardfrle=" + sddirname + FILENAME);
				//File path = new File(sdcardDir+PATH);
				File f = new File(sddirname + FILENAME);
				File fdir = new File(sddirname);

				//File f = new File(FILENAME);//emulator test
				boolean flag = false;

				//if(!path.exists())
				//{
				//	flag = path.mkdirs();
				//	if(flag)
				//	{
				//		mTestSd = (TextView)findViewById(R.id.test_sd);
				//		mTestSd.setText(R.string.test_sd_mess3);
				//	}
				//	else
				//	{
				//		mTestSd = (TextView)findViewById(R.id.test_sd_delete);
				//		setContentView(R.id.test_sd_delete);
				//	}
				//}
				//SDCARD
				try {
					if (fdir.exists()) {
						if (!f.exists()) {
							//Log.d(TAG,"step 2");
							flag = f.createNewFile();
							if (flag) {
								Log.d(TAG, "creat file ok");
								mTestSd = (TextView) findViewById(R.id.test_sd_create);
								mTestSd.setText(R.string.test_sd_mess3);
								mTestSd.setTextColor(Color.GREEN);
								OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(f));
								String editor = "fdfd";
								osw.write(editor.toString());
								osw.close();
							} else {
								Log.d(TAG, "create file failed");
								mTestSd = (TextView) findViewById(R.id.test_sd_create);
								mTestSd.setText(R.string.test_sd_mess5);
								mTestSd.setTextColor(Color.RED);
							}
						} else {
							Log.d(TAG, "sd step 3");
							mTestSd = (TextView) findViewById(R.id.test_sd_create);
							mTestSd.setText(R.string.test_sd_mess5);
							mTestSd.setTextColor(Color.RED);
						}
						if (f.exists()) {
							Log.d(TAG, "sd step 4");
							flag = f.delete();
							if (flag) {
								mTestSd = (TextView) findViewById(R.id.test_sd_delete);
								mTestSd.setText(R.string.test_sd_mess4);
								mTestSd.setTextColor(Color.GREEN);
							} else {
								mTestSd = (TextView) findViewById(R.id.test_sd_delete);
								mTestSd.setText(R.string.test_sd_mess6);
								mTestSd.setTextColor(Color.RED);
							}
						} else {
							Log.d(TAG, "sd step 5");
							mTestSd = (TextView) findViewById(R.id.test_sd_delete);
							mTestSd.setText(R.string.test_sd_mess6);
							mTestSd.setTextColor(Color.RED);
						}
					} else {
						Log.d(TAG, "there is no sdcard");
						mTestSd = (TextView) findViewById(R.id.test_sd_create);
						mTestSd.setText(R.string.test_sd_mess2);
						mTestSd.setTextColor(Color.RED);
					}
				} catch (Exception e) {
					Log.d(TAG, "file create error");
					mTestSd = (TextView) findViewById(R.id.test_sd);
					mTestSd.setText(R.string.test_sd_mess2);
					mTestSd.setTextColor(Color.RED);
				}
				try {
					File fusb = new File(sdcardDir + usbFileName);
					File fdirusb = new File(sdcardDir + usbdirname);
					if (!fdirusb.exists()) {
						fusb = new File(sdcardDir + usbFileName1);
						fdirusb = new File(sdcardDir + usbdirname1);
					}

					//usb
					if (fdirusb.exists()) {
						if (!fusb.exists()) {
							Log.d(TAG, "usb step 2 " + fusb.exists());
							flag = fusb.createNewFile();
							if (flag) {
								Log.d(TAG, "usb creat file ok");
								mTestSd = (TextView) findViewById(R.id.test_usb_create);
								mTestSd.setText(R.string.test_usb_mess3);
								mTestSd.setTextColor(Color.GREEN);
								OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(fusb));
								String editor = "fdfd";
								osw.write(editor.toString());
								osw.close();
							} else {
								Log.d(TAG, "usb create file failed");
								mTestSd = (TextView) findViewById(R.id.test_usb_create);
								mTestSd.setText(R.string.test_usb_mess5);
								mTestSd.setTextColor(Color.RED);
							}
						} else {
							Log.d(TAG, "usb step 3");
							mTestSd = (TextView) findViewById(R.id.test_usb_create);
							mTestSd.setText(R.string.test_usb_mess5);
							mTestSd.setTextColor(Color.RED);
						}
						if (fusb.exists()) {
							Log.d(TAG, "usb step 4");
							flag = fusb.delete();
							if (flag) {
								mTestSd = (TextView) findViewById(R.id.test_usb_delete);
								mTestSd.setText(R.string.test_usb_mess4);
								mTestSd.setTextColor(Color.GREEN);
							} else {
								mTestSd = (TextView) findViewById(R.id.test_usb_delete);
								mTestSd.setText(R.string.test_usb_mess6);
								mTestSd.setTextColor(Color.RED);
							}
						} else {
							Log.d(TAG, "usb step 5");
							mTestSd = (TextView) findViewById(R.id.test_usb_delete);
							mTestSd.setText(R.string.test_usb_mess6);
							mTestSd.setTextColor(Color.RED);
						}
					} else {
						Log.d(TAG, "there is no udisk");
						mTestSd = (TextView) findViewById(R.id.test_usb_create);
						mTestSd.setText(R.string.test_usb_mess2);
						mTestSd.setTextColor(Color.RED);
					}
				}
				catch(Exception e)
				{
					Log.d(TAG,"file create error");
					mTestSd = (TextView) findViewById(R.id.test_usb_create);
					mTestSd.setText(R.string.test_usb_mess2);
					mTestSd.setTextColor(Color.RED);
				}

			}
			else
			{
				Log.d(TAG, "step 6");
				mTestSd = (TextView) findViewById(R.id.test_sd);
				mTestSd.setText(R.string.test_sd_mess2);
				mTestSd.setTextColor(Color.RED);
			}
		}
		catch(Exception e)
		{
			Log.d(TAG,"file create error");
		}

			mReturn = (Button)findViewById(R.id.but_return);
//		mNext = (Button)findViewById(R.id.but_next);
		
		mReturn.setOnClickListener(new View.OnClickListener() 
		{
			public void onClick(View v) 
			{
				//mIntent = new Intent(TestSd.this, MainActivity.class);
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
//				mIntent = new Intent(TestSd.this, TestCamera.class);
//				//mIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//				finish(); 
//				startActivity(mIntent);
//			}
//		});
	}

}
