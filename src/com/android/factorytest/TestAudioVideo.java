package com.android.factorytest;


import java.io.File;
import java.util.HashMap;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.net.Uri;
import android.os.Bundle;
import android.R.integer;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.MediaController;
import android.widget.VideoView;

public class TestAudioVideo extends Activity implements OnClickListener{
	private static final String TAG = "factorytest";
	//private static final String PATH_STRING = "/storage/external_storage/sdcard1/test.mp4";
	private static final String PATH_STRING = "file:///android_asset/wow.mp4";
	VideoView videoView;
	Button startVideo, pauseVideo, startMusic, pauseMusic, startSound, pauseSound;
	MediaPlayer mediaPlayer;
	SoundPool soundPool;
	HashMap< Integer, Integer> soundMap;
	int soundPoolId;
	int videoPosition;
	boolean isVideoPause = false;
	boolean isVideoPlay = false;
	private Button mReturn = null;
	private int mstate = 0;
	private int musekey = 0;
	private boolean mresult = false;
    private void initView()
    {

    	mReturn = (Button)findViewById(R.id.but_return);
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
    }
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test_audiovideo);
		initView();
		videoView = (VideoView) findViewById(R.id.videoView1);
		startVideo = (Button) findViewById(R.id.button1);
		pauseVideo = (Button) findViewById(R.id.button2);
		startMusic = (Button) findViewById(R.id.button3);
		pauseMusic = (Button) findViewById(R.id.button4);
		startSound = (Button) findViewById(R.id.button5);
		pauseSound = (Button) findViewById(R.id.button6);
		
		startVideo.setOnClickListener(this);
		pauseVideo.setOnClickListener(this);
		startMusic.setOnClickListener(this);
		pauseMusic.setOnClickListener(this);
		startSound.setOnClickListener(this);
		pauseSound.setOnClickListener(this);
		initVideo();
		initMusic();
		initSoundPool();
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
            //退出前停止播放。
            mediaPlayer.pause(); 
            soundPool.pause(id);
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
	
	void initVideo(){
		//File file = new File(PATH_STRING);
		//String path = Uri.parse("androd.resource://com.android.factorytest" + R.raw.wow);
//		if(!file.exists()){
//			Log.w("播放视频错误", "视频文件不存在");
//		}
//		else{
			
//			String path = file.getAbsolutePath();
			videoView.setVideoPath("android.resource://com.android.factorytest/"+R.raw.wow);
//			videoView.setVideoURI(Uri.parse("file:///android_asset/wow.mp4"));
			videoView.setMediaController(new MediaController(this));
//		}
	}
	
	void initMusic(){
		mediaPlayer = MediaPlayer.create(this, R.raw.test);
	}
	
	void initSoundPool(){
		soundPool = new SoundPool(2, AudioManager.STREAM_MUSIC, 0);
		soundMap = new HashMap<Integer, Integer>();
		soundMap.put(1, soundPool.load(this,R.raw.sound2 , 1));
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//soundPoolId = soundPool.load(this, R.raw.summer, 1);
		//Log.w("soundPoolId",Integer.toString(soundPoolId));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	int id=0;
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.button1:
			videoView.start();
			startVideo.setEnabled(false);
			break;
		case R.id.button2:
			if(isVideoPause==false){
				videoPosition = videoView.getCurrentPosition();
				videoView.pause();
				isVideoPause = true;
			}
			else{
				videoView.start();
				isVideoPause = false;
			}

			break;
		case R.id.button3:
			mediaPlayer.start();
			startMusic.setEnabled(false);
			break;
		case R.id.button4:
			mediaPlayer.pause(); 
			break;
		case R.id.button5:
			id = soundPool.play(soundMap.get(1), 1, 1, 1, 0, 1);
			startSound.setEnabled(false);
			Log.w("play", Integer.toString(id));
			break;
		case R.id.button6:
			soundPool.pause(id);
			Log.w("pause",Integer.toString(id));
			break;

		default:
			break;
		}
		
	}
	
	
	

}
