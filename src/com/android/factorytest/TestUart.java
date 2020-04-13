package com.android.factorytest;


//
//
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileOutputStream;
//import java.io.IOException;
//
//import android.app.Activity;
//import android.app.AlertDialog;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.os.Bundle;
//
//
//
////import android.serialport.sample.R;
////import android.serialport.R;
//
//import android.util.Log;
//import android.view.KeyEvent;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.TextView;
//import android.widget.Toast;
//
//public class TestUart extends Activity {
//    /** Called when the activity is first created. */
//	private static final String TAG = "factorytest";
//	private int mstate = 0;
//	private int musekey = 0;
//	private boolean mresult = false;
//	private Button mReturn = null;
//	String recdata;
//	 EditText mReception;
//	 TextView mRectext;
//	 FileOutputStream mOutputStream;
//	 FileInputStream mInputStream;
//	 SerialPort sp;
//	  ReadThread  mReadThread;
//	// Thread mReadThread = null;
//	  Button buttonsend;
//    private void initView()
//    {
//    	mReception = (EditText) findViewById(R.id.EditTextEmission);
//    	buttonsend = (Button)findViewById(R.id.ButtonSent1);
//    	mRectext = (TextView) findViewById(R.id.show_data);
//    	mReception.setEnabled(false);
//    	mReception.setText("");
//    	mReturn = (Button)findViewById(R.id.but_return);
//    	buttonsend.setEnabled(false);
//    	mReturn.setOnClickListener(new View.OnClickListener()
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
//    }
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.test_uart);
//        initView();
//
//    final Button buttonSetup = (Button)findViewById(R.id.ButtonSent);
//    buttonSetup.setOnClickListener(new View.OnClickListener() {
//		public void onClick(View v) {
////			mReception = (EditText) findViewById(R.id.EditTextEmission);
//
//		      try {
//			//sp=new SerialPort(new File("/dev/ttyS0"),115200);
//			sp=new SerialPort(new File("/dev/ttyS0"),115200,0);
//			} catch (SecurityException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//
//		      mReadThread = new ReadThread();
//			  mReadThread.start();
////		      mReadThread = new Thread();
////		      mReadThread.start();
//		      mInputStream=(FileInputStream) sp.getInputStream();
//		      buttonsend.setEnabled(true);
//		     //Toast.makeText(getApplicationContext(), "open",
//		    		    // Toast.LENGTH_SHORT).show();
//
//		}
//	});
//
//
//
//
//    buttonsend.setOnClickListener(new View.OnClickListener() {
//		public void onClick(View v) {
//
//			try {
//				mOutputStream=(FileOutputStream) sp.getOutputStream();
//
//				mOutputStream.write(new String("send").getBytes());
//				//mOutputStream.write('\n');
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//
//
//		     // Toast.makeText(getApplicationContext(), "send",
//		    	//	     Toast.LENGTH_SHORT).show();
//
//		}
//	});
//
//
//   /* final Button buttonrec= (Button)findViewById(R.id.ButtonRec);
//    buttonrec.setOnClickListener(new View.OnClickListener() {
//		public void onClick(View v) {
//			int size;
//
//			try {
//			byte[] buffer = new byte[64];
//			if (mInputStream == null) return;
//			size = mInputStream.read(buffer);
//			if (size > 0) {
//				onDataReceived(buffer, size);
//
//			}
//		} catch (IOException e) {
//			e.printStackTrace();
//			return;
//		}
//
//
//
//
//		}
//	});
//
//   */
//
//
//
//    }
//    DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener()
//
//    {
//
//        public void onClick(DialogInterface dialog, int which)
//
//        {
////        	Bundle bundle = new Bundle();
//            switch (which)
//
//            {
//
//            case AlertDialog.BUTTON_POSITIVE:// "成功"按钮退出程序
//            	Log.i(TAG,"成功");
//            	mstate = 1;
////            	mextras.putBoolean("sucess", true);
////            	mIntent.putExtras(mextras);
//            	Intent itok = new Intent();
//            	itok.putExtra("sucess", true);
//            	setResult(1, itok);
//            	sp.SeaialClose();
//            	if(musekey == 1)
//            		finish();
////            	else
////            	{
////					mIntent = new Intent(TestColor.this, TestAudioVideo.class);
////					//mIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
////					finish();
////					startActivity(mIntent);
////            	}
//                break;
//
//            case AlertDialog.BUTTON_NEGATIVE:// "失败"第二个按钮取消对话框
//            	Log.i(TAG,"失败");
//            	mstate = 2;
//            	Intent itfaile = new Intent();
//            	itfaile.putExtra("sucess", false);
//            	setResult(1, itfaile);
//            	sp.SeaialClose();
//            	if(musekey == 1)
//            		finish();
////            	else
////            	{
////					mIntent = new Intent(TestColor.this, TestAudioVideo.class);
////					//mIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
////					finish();
////					startActivity(mIntent);
////            	}
//                break;
//
//            case AlertDialog.BUTTON_NEUTRAL:// "重测"第二个按钮取消对话框
//            	Log.i(TAG,"重测");
//            	mstate = 3;
//            	//finish();
//                break;
//            default:
//
//                break;
//
//            }
//
//        }
//
//    };
// private void showAlert()
// {
//	 AlertDialog isExit = new AlertDialog.Builder(this).create();
//
//     // 设置对话框标题
//
//     isExit.setTitle("系统提示");
//
//     // 设置对话框消息
//
//     isExit.setMessage("测试是否成功");
//
//     // 添加选择按钮并注册监听
//
//     isExit.setButton(AlertDialog.BUTTON_POSITIVE,"成功", listener);
//
//     isExit.setButton(AlertDialog.BUTTON_NEGATIVE,"失败", listener);
//
//     isExit.setButton(AlertDialog.BUTTON_NEUTRAL,"重测", listener);
//
//     // 显示对话框
//
//     isExit.show();
// }
// public boolean onKeyDown(int keyCode,KeyEvent keyEvent)
// {
//        // TODO Auto-generated method stub
//	 	Log.d(TAG,"get the key "+keyCode);
//	 	if(keyCode==KeyEvent.KEYCODE_BACK )
//        {
//        	 // 创建退出对话框
//        	musekey = 1;
//        	showAlert();
//
//        }
//        return super.onKeyDown(keyCode, keyEvent);
// }
//// public void run() {
////	 Log.d(TAG,"in thread ");
////		while(sp!=null) {
////			int size;
////			try {
////				byte[] buffer = new byte[64];
////				if (mInputStream == null) return;
////				size = mInputStream.read(buffer);
////				mRectext.append(new String(buffer, 0, size));
////				Log.d(TAG,"get the data: "+(new String(buffer, 0, size)));
////				//if (size > 0) {
////					//onDataReceived(buffer, size);
////
////				//}
////			} catch (IOException e) {
////				e.printStackTrace();
////				return;
////			}
////			//mReception.append(new String("recive"));
////		}
//// }
//    private class ReadThread extends Thread {
//
//		@Override
//		public void run() {
//			super.run();
//			while(!isInterrupted()) {
//			//while(sp != null) {
//				int size;
//				try {
//					byte[] buffer = new byte[64];
//					if (mInputStream == null) return;
//					size = mInputStream.read(buffer);
//					Log.d(TAG,"get the size "+size);
//					//mRectext.append(new String(buffer, 0, size));
//					if (size > 0) {
//						onDataReceived(buffer, size);
//
//					}
//				} catch (IOException e) {
//					e.printStackTrace();
//					return;
//				}
//
//				//mReception.append(new String(" recive:"));
//			}
//		}
//	}
//
//
//
//    void onDataReceived(final byte[] buffer, final int size) {
//    	/*if (mReception != null) {
//			//mReception.append(new String(buffer, 0, size));
//    		mReception.setText(new String(buffer, 0, size));
//    	}*/
//		runOnUiThread(new Runnable() {
//			public void run() {
//				if (mReception != null) {
//					recdata = (new String(buffer, 0, size));
//					//mReception.setText("");
//					//mReception.setText(recdata);
//					mRectext.append(recdata);
//					//mReception.append(recdata);
//					Log.d(TAG,"get the data: "+recdata);
//					//mReception.append((new String(buffer, 0, size)));
//					//Log.d(TAG,"get the data: "+(new String(buffer, 0, size)));
//				}
//			}
//		});
//	}
//
//
//}



import java.io.ByteArrayOutputStream;
import java.io.IOException;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import static android.net.wifi.SupplicantState.COMPLETED;

public class TestUart extends SerialPortActivity {
	private static final String TAG = "factorytest";
	EditText mReception1,mReception2,mReception3,mSendCount1,mSendCount2,mSendCount3,mReceiveCount1,mReceiveCount2,mReceiveCount3;
	EditText Emission1,Emission2,Emission3, hexinput;
	public Button charsendbt1,charsendbt2,charsendbt3, hexsendbt,autosendbt1,autosendbt2,autosendbt3;
	private WriteThread1 mWriteThread1 = new WriteThread1();
	private WriteThread2 mWriteThread2 = new WriteThread2();
	private WriteThread3 mWriteThread3 = new WriteThread3();
	private boolean autoflag1=false,autoflag2=false,autoflag3=false;
	private Button mReturn = null;
	private int mstate = 0;
	private int musekey = 0;
	private long  txcount1,txcount2,txcount3,rxcount1,rxcount2,rxcount3;
	private Runnable myable1 = new Runnable() {
		@Override
		public void run() {
			while (autoflag1) {
				int i;
				//Emission1.setText("0123456789");
				CharSequence t = "1234567890";//Emission1.getText();
				txcount1 += t.length();
				char[] text = new char[t.length()];
				for (i = 0; i < t.length(); i++) {
					text[i] = t.charAt(i);
				}
				try {
					mOutputStream1.write(new String(text).getBytes());
					mOutputStream1.write('\n');
					Message msg = new Message();
					msg.what = 1;
					handle1.sendMessage(msg);
					//mSendCount1.setText(String.valueOf(txcount1));
//                    try {
//                        sleep(3000);
//                    } catch (InterruptedException e) {
//                        // TODO Auto-generated catch block
//                        e.printStackTrace();
//                        break;
//                    }

				} catch (IOException e) {
					e.printStackTrace();
				}


			}

		}
	};
	private  Runnable myable2 = new Runnable() {
		@Override
		public void run() {

		}
	};
	private Runnable myable3 = new Runnable() {
		@Override
		public void run() {

		}
	};
	private Handler handle1 = new Handler(){
		public void handleMessage(Message msg){
			if(msg.what == 1){
				mSendCount1.setText(String.valueOf(txcount1));
			}
		}
	};
	private Handler handle2 = new Handler(){
		public void handleMessage(Message msg){
			if(msg.what == 1){
				mSendCount2.setText(String.valueOf(txcount2));
			}
		}
	};
	private Handler handle3 = new Handler(){
		public void handleMessage(Message msg){
			if(msg.what == 1){
				mSendCount3.setText(String.valueOf(txcount3));
			}

		}
	};
	private class WriteThread1 extends Thread {

		@Override
		public void run() {
			//super.run();
			//while (!isInterrupted()) {
			while (true) {
				int i;
				//Emission1.setText("0123456789");
				CharSequence t = "1234567890";//Emission1.getText();
				txcount1 += t.length();
				char[] text = new char[t.length()];
				for (i = 0; i < t.length(); i++) {
					text[i] = t.charAt(i);
				}
				if(autoflag1) {
					try {
						mOutputStream1.write(new String(text).getBytes());
						//mOutputStream1.write('\n');
						Message msg = new Message();
						msg.what = 1;
						handle1.sendMessage(msg);
						//mSendCount1.setText(String.valueOf(txcount1));
						try {
							sleep(2000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							break;
						}

					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				else{
					try {
						sleep(2000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

			}
		}
	}
	private class WriteThread2 extends Thread {

		@Override
		public void run() {
			//super.run();
			//
			while (true) {
				int i;
				//Emission2.setText("0123456789");
				CharSequence t = "1234567890";//Emission2.getText();
				txcount2 += t.length();
				char[] text = new char[t.length()];
				for (i = 0; i < t.length(); i++) {
					text[i] = t.charAt(i);
				}
				if (autoflag2) {
					try {
						mOutputStream2.write(new String(text).getBytes());
						////mOutputStream2.write('\n');
						//mSendCount2.setText(String.valueOf(txcount2));
						Message msg = new Message();
						msg.what = 1;
						handle2.sendMessage(msg);
						try {
							sleep(2000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}


					} catch (IOException e) {
						e.printStackTrace();
					}
				} else {
					try {
						sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}

	}
	private class WriteThread3 extends Thread {

		@Override
		public void run() {
			//super.run();
//		while (!isInterrupted()) {
			while (true) {
				int i;
				//Emission3.setText("0123456789");

				CharSequence t = "1234567890";//Emission3.getText();
				txcount3 += t.length();
				char[] text = new char[t.length()];
				for (i = 0; i < t.length(); i++) {
					text[i] = t.charAt(i);
				}
				if (autoflag3) {
					try {
						mOutputStream3.write(new String(text).getBytes());
						//mOutputStream3.write('\n');
						//mSendCount3.setText(String.valueOf(txcount3));
						Message msg = new Message();
						msg.what = 1;
						handle3.sendMessage(msg);
						try {
							sleep(2000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}


					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				else{
					try {
						sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

			}
		}
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.test_uart);
		Log.i(TAG,"Uart OnCreate");
		txcount1=txcount2=txcount3=0;
		rxcount1=rxcount2=rxcount3=0;
		// setTitle("Loopback test");
		mReturn = (Button)findViewById(R.id.but_return);
		mReception1 = (EditText) findViewById(R.id.EditTextReception1);
//		hexinput = (EditText) findViewById(R.id.hexedittext);
		charsendbt1 = (Button) findViewById(R.id.okbt1);
//		hexsendbt = (Button) findViewById(R.id.sendhexbt);
		autosendbt1 = (Button)findViewById(R.id.btauto1);
		Emission1 = (EditText) findViewById(R.id.EditTextEmission1);
		mSendCount1 = (EditText)findViewById(R.id.txCount1);
		mReceiveCount1 = (EditText)findViewById(R.id.rxCount1);

		mReception2 = (EditText) findViewById(R.id.EditTextReception2);
//		hexinput = (EditText) findViewById(R.id.hexedittext);
		charsendbt2 = (Button) findViewById(R.id.okbt2);
//		hexsendbt = (Button) findViewById(R.id.sendhexbt);
		autosendbt2 = (Button)findViewById(R.id.btauto2);
		Emission2 = (EditText) findViewById(R.id.EditTextEmission2);
		mSendCount2 = (EditText)findViewById(R.id.txCount2);
		mReceiveCount2 = (EditText)findViewById(R.id.rxCount2);

		mReception3 = (EditText) findViewById(R.id.EditTextReception3);
//		hexinput = (EditText) findViewById(R.id.hexedittext);
		charsendbt3 = (Button) findViewById(R.id.okbt3);
//		hexsendbt = (Button) findViewById(R.id.sendhexbt);
		autosendbt3 = (Button)findViewById(R.id.btauto3);
		Emission3 = (EditText) findViewById(R.id.EditTextEmission3);
		mSendCount3 = (EditText)findViewById(R.id.txCount3);
		mReceiveCount3 = (EditText)findViewById(R.id.rxCount3);
		mReception1.setEnabled(false);
		mReception2.setEnabled(false);
		mReception3.setEnabled(false);
		mSendCount1.setEnabled(false);
		mSendCount2.setEnabled(false);
		mSendCount3.setEnabled(false);
		mReceiveCount1.setEnabled(false);
		mReceiveCount2.setEnabled(false);
		mReceiveCount3.setEnabled(false);
		Emission1.setOnEditorActionListener(new OnEditorActionListener() {
			public boolean onEditorAction(TextView v, int actionId,
										  KeyEvent event) {
				Log.e("SerialDemoActivity",
						"=======setOnEditorActionListener=========");
				return false;
			}
		});
		Emission2.setOnEditorActionListener(new OnEditorActionListener() {
			public boolean onEditorAction(TextView v, int actionId,
										  KeyEvent event) {
				Log.e("SerialDemoActivity",
						"=======setOnEditorActionListener=========");
				return false;
			}
		});
		Emission3.setOnEditorActionListener(new OnEditorActionListener() {
			public boolean onEditorAction(TextView v, int actionId,
										  KeyEvent event) {
				Log.e("SerialDemoActivity",
						"=======setOnEditorActionListener=========");
				return false;
			}
		});
		mReturn.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View v)
			{
				//mIntent = new Intent(TestColor.this, MainActivity.class);
				musekey = 1;
				showAlert();
				//finish();
				//startActivity(mIntent);
			}
		});
//		hexsendbt.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//
//				try {
//
//					ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//					byteArrayOutputStream.write(Integer.decode(
//							hexinput.getText().toString()).byteValue());
//					mOutputStream.write(byteArrayOutputStream.toByteArray());
//
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//
//			}
//		});



		charsendbt1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				int i;
				CharSequence t = Emission1.getText();
				char[] text = new char[t.length()];
				for (i = 0; i < t.length(); i++) {
					text[i] = t.charAt(i);
				}
				try {
					mOutputStream1.write(new String(text).getBytes());
					mOutputStream1.write('\n');

				} catch (IOException e) {
					e.printStackTrace();
				}

			}
		});

		autosendbt1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if(autoflag1 == false) {
					Emission1.setText("0123456789");
					//mWriteThread1 = new WriteThread1();
					mWriteThread1.start();
//                    new WriteThread1(myable1).start();
					//new Thread(myable1).start();
					autoflag1 = true;
					autosendbt1.setText(R.string.autostop);
				}
				else
				{
					autoflag1 = false;
					//mWriteThread1.interrupt();
					autosendbt1.setText(R.string.autosend);
					autosendbt1.setEnabled(false);

				}


//				int i;
//				Emission1.setText("0123456789");
//				CharSequence t = Emission1.getText();
//				txcount1 += t.length();
//				char[] text = new char[t.length()];
//				for (i = 0; i < t.length(); i++) {
//					text[i] = t.charAt(i);
//				}
//				try {
//					mOutputStream1.write(new String(text).getBytes());
//					mOutputStream1.write('\n');
//					mSendCount1.setText(String.valueOf(txcount1));
//
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//
			}
		});

		charsendbt2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				int i;
				CharSequence t = Emission2.getText();
				char[] text = new char[t.length()];
				for (i = 0; i < t.length(); i++) {
					text[i] = t.charAt(i);
				}
				try {
					mOutputStream2.write(new String(text).getBytes());
					mOutputStream2.write('\n');

				} catch (IOException e) {
					e.printStackTrace();
				}

			}
		});
		autosendbt2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if(autoflag2 == false) {
					Emission2.setText("0123456789");
					//mWriteThread2 = new WriteThread2();
					mWriteThread2.start();
//                    new WriteThread1(myable1).start();
					// new Thread(myable2).start();
					autoflag2 = true;
					autosendbt2.setText(R.string.autostop);
				}
				else
				{
					autoflag2 = false;
					// mWriteThread2.interrupt();
					autosendbt2.setText(R.string.autosend);
					autosendbt2.setEnabled(false);

				}
				//				int i;
//				Emission2.setText("0123456789");
//				CharSequence t = Emission2.getText();
//				txcount2 += t.length();
//				char[] text = new char[t.length()];
//				for (i = 0; i < t.length(); i++) {
//					text[i] = t.charAt(i);
//				}
//				try {
//					mOutputStream2.write(new String(text).getBytes());
//					mOutputStream2.write('\n');
//					mSendCount2.setText(String.valueOf(txcount2));
//
//				} catch (IOException e) {
//					e.printStackTrace();
//				}

			}
		});
		charsendbt3.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				int i;
				CharSequence t = Emission3.getText();
				char[] text = new char[t.length()];
				for (i = 0; i < t.length(); i++) {
					text[i] = t.charAt(i);
				}
				try {
					mOutputStream3.write(new String(text).getBytes());
					mOutputStream3.write('\n');

				} catch (IOException e) {
					e.printStackTrace();
				}

			}
		});
		autosendbt3.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if(autoflag3 == false) {
					Emission3.setText("0123456789");
					//mWriteThread3 = new WriteThread3();
					mWriteThread3.start();
//                    new WriteThread1(myable1).start();
					//new Thread(myable3).start();
					autoflag3 = true;
					autosendbt3.setText(R.string.autostop);
				}
				else
				{
					autoflag3 = false;
					// mWriteThread3.interrupt();
					autosendbt3.setText(R.string.autosend);
					autosendbt3.setEnabled(false);

				}
//				int i;
//				Emission3.setText("0123456789");
//				CharSequence t = Emission3.getText();
//				txcount3 += t.length();
//				char[] text = new char[t.length()];
//				for (i = 0; i < t.length(); i++) {
//					text[i] = t.charAt(i);
//				}
//				try {
//					mOutputStream3.write(new String(text).getBytes());
//					mOutputStream3.write('\n');
//					mSendCount3.setText(String.valueOf(txcount3));
//
//				} catch (IOException e) {
//					e.printStackTrace();
//				}

			}
		});
	}
	DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener()

	{

		public void onClick(DialogInterface dialog, int which)

		{
//      	Bundle bundle = new Bundle();
			switch (which)

			{

				case AlertDialog.BUTTON_POSITIVE:// "成功"按钮退出程序
					Log.i(TAG,"成功");
					mstate = 1;
//          	mextras.putBoolean("sucess", true);
//          	mIntent.putExtras(mextras);
					Intent itok = new Intent();
					itok.putExtra("sucess", true);
					setResult(1, itok);

					if(musekey == 1)
						finish();
//          	else
//          	{
//					mIntent = new Intent(TestColor.this, TestAudioVideo.class);
//					//mIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//					finish();
//					startActivity(mIntent);
//          	}
					break;

				case AlertDialog.BUTTON_NEGATIVE:// "失败"第二个按钮取消对话框
					Log.i(TAG,"失败");
					mstate = 2;
					Intent itfaile = new Intent();
					itfaile.putExtra("sucess", false);
					setResult(1, itfaile);

					if(musekey == 1)
						finish();
//          	else
//          	{
//					mIntent = new Intent(TestColor.this, TestAudioVideo.class);
//					//mIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//					finish();
//					startActivity(mIntent);
//          	}
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
	@Override
	protected void onDataReceived1(final byte[] buffer, final int size) {
		runOnUiThread(new Runnable() {
			public void run() {
				if (mReception1 != null) {
					mReception1.append(new String(buffer, 0, size));
					rxcount1 += size;
					mReceiveCount1.setText(String.valueOf(rxcount1));
					Log.d(TAG,(new String(buffer, 0, size)));
				}
			}
		});
	}
	protected void onDataReceived2(final byte[] buffer, final int size) {
		runOnUiThread(new Runnable() {
			public void run() {
				if (mReception2 != null) {
					mReception2.append(new String(buffer, 0, size));
					rxcount2 += size;
					mReceiveCount2.setText(String.valueOf(rxcount2));
					Log.d(TAG,(new String(buffer, 0, size)));
				}
			}
		});
	}
	protected void onDataReceived3(final byte[] buffer, final int size) {
		runOnUiThread(new Runnable() {
			public void run() {
				if (mReception3 != null) {
					mReception3.append(new String(buffer, 0, size));
					rxcount3 += size;
					mReceiveCount3.setText(String.valueOf(rxcount3));
					Log.d(TAG,(new String(buffer, 0, size)));
				}
			}
		});
	}

}