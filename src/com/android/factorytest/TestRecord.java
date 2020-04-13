package com.android.factorytest;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioRecord;
import android.media.AudioTrack;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.widget.Button;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;

public class TestRecord extends Activity {
	private static final String TAG = "factorytest";
    private boolean isRecording = false ;

    private Object tmp = new Object() ;
    private String str_dir = "/sdcard/";
   
	private int mstate = 0;
	private int musekey = 0;
	private boolean mresult = false;
   
	private Button mReturn = null;
    @Override

    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.test_record);
        initButton();
       

        Button start = (Button)findViewById(R.id.start_bt) ;

        start.setOnClickListener(new OnClickListener()

        {

 

            @Override

            public void onClick(View arg0) {

                // TODO Auto-generated method stub

                Thread thread = new Thread(new Runnable() {

                    public void run() {

                      record();

                    }    

                  });

                  thread.start();

                  findViewById(R.id.start_bt).setEnabled(false) ;

                  findViewById(R.id.end_bt).setEnabled(true) ;

            }

         

        }) ;

       

        Button play = (Button)findViewById(R.id.play_bt) ;

        play.setOnClickListener(new OnClickListener(){

 

            @Override

            public void onClick(View v) {

                // TODO Auto-generated method stub

                play();

            }

         

        }) ;

       

        Button stop = (Button)findViewById(R.id.end_bt) ;

        stop.setEnabled(false) ;

        stop.setOnClickListener(new OnClickListener(){

            @Override

            public void onClick(View v) {

                // TODO Auto-generated method stub

                isRecording = false ;

                findViewById(R.id.start_bt).setEnabled(true) ;

                findViewById(R.id.end_bt).setEnabled(false) ;

            }

         

        }) ;

       

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
 

    public void play() {

      // Get the file we want to playback.

      //File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/reverseme.pcm");
      File file = new File(str_dir + "/reverseme.pcm");
      // Get the length of the audio stored in the file (16 bit so 2 bytes per short)

      // and create a short array to store the recorded audio.

      int musicLength = (int)(file.length()/2);

      short[] music = new short[musicLength];

 

 

      try {

        // Create a DataInputStream to read the audio data back from the saved file.

        InputStream is = new FileInputStream(file);

        BufferedInputStream bis = new BufferedInputStream(is);

        DataInputStream dis = new DataInputStream(bis);

         

        // Read the file into the music array.

        int i = 0;

        while (dis.available() > 0) {

          music[i] = dis.readShort();

          i++;

        }

 

 

        // Close the input streams.

        dis.close();    

 

 

        // Create a new AudioTrack object using the same parameters as the AudioRecord

        // object used to create the file.

        AudioTrack audioTrack = new AudioTrack(AudioManager.STREAM_MUSIC,

                                               44100,

                                               AudioFormat.CHANNEL_OUT_MONO,

                                               AudioFormat.ENCODING_PCM_16BIT,

                                               musicLength*2,

                                               AudioTrack.MODE_STREAM);

        // Start playback

        audioTrack.play();

     

        // Write the music buffer to the AudioTrack object

        audioTrack.write(music, 0, musicLength);

 

        audioTrack.stop() ;

 

      } catch (Throwable t) {

        Log.e("AudioTrack","Playback Failed");

      }

    }

 

    public void record() {

      int frequency = 44100;

      int channelConfiguration = AudioFormat.CHANNEL_IN_MONO;

      int audioEncoding = AudioFormat.ENCODING_PCM_16BIT;
      
      //File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/reverseme.pcm");

      File file = new File(str_dir + "/reverseme.pcm");

      // Delete any previous recording.

      if (file.exists())

        file.delete();

 

 

      // Create the new file.

      try {

        file.createNewFile();

      } catch (IOException e) {

        throw new IllegalStateException("Failed to create " + file.toString());

      }

      

      try {

        // Create a DataOuputStream to write the audio data into the saved file.

        OutputStream os = new FileOutputStream(file);

        BufferedOutputStream bos = new BufferedOutputStream(os);

        DataOutputStream dos = new DataOutputStream(bos);

        

        // Create a new AudioRecord object to record the audio.

        int bufferSize = AudioRecord.getMinBufferSize(frequency, channelConfiguration,  audioEncoding);
        Log.d(TAG,"buffer size is  "+bufferSize);
       AudioRecord audioRecord = new AudioRecord(MediaRecorder.AudioSource.MIC,
                //AudioRecord audioRecord = new AudioRecord(MediaRecorder.AudioSource.CAMCORDER,

                                                  frequency, channelConfiguration,

                                                  audioEncoding, bufferSize);

      

        short[] buffer = new short[bufferSize];  

        audioRecord.startRecording();

 

        isRecording = true ;

        while (isRecording) {

          int bufferReadResult = audioRecord.read(buffer, 0, bufferSize);

          for (int i = 0; i < bufferReadResult; i++)

            dos.writeShort(buffer[i]);

        }

 

 

        audioRecord.stop();

        dos.close();

      

      } catch (Throwable t) {

        Log.e("AudioRecord","Recording Failed");

      }

    }
    private void initButton()
    {
        mReturn = (Button)findViewById(R.id.but_return);
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
    }

}

