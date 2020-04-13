package com.android.factorytest;



import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.InvalidParameterException;

import android.util.Log;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;

public abstract class SerialPortActivity extends Activity {
	private static final String TAG = "factorytest";
	//protected Application mApplication;
	protected SerialPort mSerialPort1;
	protected OutputStream mOutputStream1;
	private InputStream mInputStream1;
	private ReadThread1 mReadThread1;
	protected SerialPort mSerialPort2;
	protected OutputStream mOutputStream2;
	private InputStream mInputStream2;
	private ReadThread2 mReadThread2;
	protected SerialPort mSerialPort3;
	protected OutputStream mOutputStream3;
	private InputStream mInputStream3;
	private ReadThread3 mReadThread3;

	private class ReadThread1 extends Thread {

		@Override
		public void run() {
			super.run();
			while (!isInterrupted()) {
				int size;
				try {
					byte[] buffer = new byte[64];
					if (mInputStream1 == null)
						return;
					

					size = mInputStream1.read(buffer);
					if (size > 0) {
						onDataReceived1(buffer, size);
					}
				} catch (IOException e) {
					e.printStackTrace();
					return;
				}
			}
		}
	}
	private class ReadThread2 extends Thread {

		@Override
		public void run() {
			super.run();
			while (!isInterrupted()) {
				int size;
				try {
					byte[] buffer = new byte[64];
					if (mInputStream2 == null)
						return;


					size = mInputStream2.read(buffer);
					if (size > 0) {
						onDataReceived2(buffer, size);
					}
				} catch (IOException e) {
					e.printStackTrace();
					return;
				}
			}
		}
	}
	private class ReadThread3 extends Thread {

		@Override
		public void run() {
			super.run();
			while (!isInterrupted()) {
				int size;
				try {
					byte[] buffer = new byte[64];
					if (mInputStream3 == null)
						return;


					size = mInputStream3.read(buffer);
					if (size > 0) {
						onDataReceived3(buffer, size);
					}
				} catch (IOException e) {
					e.printStackTrace();
					return;
				}
			}
		}
	}

	private void DisplayError(int resourceId) {
		AlertDialog.Builder b = new AlertDialog.Builder(this);
		b.setTitle("Error");
		b.setMessage(resourceId);
		b.setPositiveButton("OK", new OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				SerialPortActivity.this.finish();
			}
		});
		b.show();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d(TAG,"SerialPortActivity");
		Intent intent1 = this.getIntent(); 
		//String serialpath = intent1.getStringExtra("serial_path");
		//int serialbaudrate = intent1.getIntExtra("serial_baudrate",115200);
		String serialpath1 = "/dev/ttyS0";
		//String serialpath1 = "/dev/ttyData0";
		int serialbaudrate1 = 115200;
		String serialpath2 = "/dev/ttyS2";
		//String serialpath2 = "/dev/ttyData2";
		int serialbaudrate2 = 115200;
		String serialpath3 = "/dev/ttyS3";
		int serialbaudrate3 = 115200;
		//Log.d(TAG,"SerialPortActivity:0");
		//mApplication = (Application) getApplication();
        Log.d(TAG,"start serial1");
		try {
            if (mSerialPort1 == null) {

            	mSerialPort1 = new SerialPort(new File(serialpath1), serialbaudrate1, 0);
            }
			//mSerialPort = mApplication.getSerialPort(serialpath, serialbaudrate);
			mOutputStream1 = mSerialPort1.getOutputStream();
			mInputStream1 = mSerialPort1.getInputStream();
			//Log.d(TAG,"SerialPortActivity:2");
			/* Create a receiving thread */
			mReadThread1 = new ReadThread1();
			mReadThread1.start();

			if (mSerialPort2 == null) {
				Log.d(TAG,"start serial2");
				mSerialPort2 = new SerialPort(new File(serialpath2), serialbaudrate2, 0);
				Log.d(TAG,"start serial2 " + mSerialPort2);
			}
			//mSerialPort = mApplication.getSerialPort(serialpath, serialbaudrate);
			mOutputStream2 = mSerialPort2.getOutputStream();
			mInputStream2 = mSerialPort2.getInputStream();
			//Log.d(TAG,"SerialPortActivity:2");
			/* Create a receiving thread */
			mReadThread2 = new ReadThread2();
			mReadThread2.start();
			if (mSerialPort3 == null) {

				mSerialPort3 = new SerialPort(new File(serialpath3), serialbaudrate3, 0);
			}
			//mSerialPort = mApplication.getSerialPort(serialpath, serialbaudrate);
			mOutputStream3 = mSerialPort3.getOutputStream();
			mInputStream3 = mSerialPort3.getInputStream();
			//Log.d(TAG,"SerialPortActivity:2");
			/* Create a receiving thread */
			mReadThread3 = new ReadThread3();
			mReadThread3.start();
		} catch (SecurityException e) {
			Log.d(TAG,"You do not have read/write permission to the serial");
			DisplayError(R.string.error_security);
		} catch (IOException e) {
			Log.d(TAG,"The serial port can not be opened for an unknown");
			DisplayError(R.string.error_unknown);
		} catch (InvalidParameterException e) {
			Log.d(TAG,"Please configure your serial port first.");
			DisplayError(R.string.error_configuration);
		}
	}

	protected abstract void onDataReceived1(final byte[] buffer, final int size);
	protected abstract void onDataReceived2(final byte[] buffer, final int size);
	protected abstract void onDataReceived3(final byte[] buffer, final int size);
	@Override
	protected void onDestroy() {
		Log.d(TAG,"SerialPortActivity Destroy");
		if (mReadThread1 != null)
			mReadThread1.interrupt();
        if (mSerialPort1 != null) {
            mSerialPort1.close();
            mSerialPort1 = null;
    }
		if (mReadThread2 != null)
			mReadThread2.interrupt();
		if (mSerialPort2 != null) {
			mSerialPort2.close();
			mSerialPort2 = null;
		}
		if (mReadThread3 != null)
			mReadThread3.interrupt();
		if (mSerialPort3 != null) {
			mSerialPort3.close();
			mSerialPort3 = null;
		}

		//mApplication.closeSerialPort();
		mSerialPort1 = null;
		mSerialPort2 = null;
		mSerialPort3 = null;




		super.onDestroy();
	}

}
