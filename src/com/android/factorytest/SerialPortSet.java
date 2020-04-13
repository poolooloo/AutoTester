package com.android.factorytest;



import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.util.Log;
public class SerialPortSet extends Activity{
	private static final String TAG = "factorytest";
	public Button surebt;
	public EditText serialportpath,serialportbaudrateinput;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d(TAG,"SerialPortSet");
		setContentView(R.layout.serialset);
		surebt = (Button)findViewById(R.id.surebt);
		serialportpath = (EditText)findViewById(R.id.serialportinput);
		serialportbaudrateinput = (EditText)findViewById(R.id.serialportbaudrateinput);
		
		surebt.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(SerialPortSet.this, TestUart.class);

				intent.putExtra("serial_path", serialportpath.getText().toString());
				intent.putExtra("serial_baudrate", Integer.parseInt(serialportbaudrateinput.getText().toString()));

				startActivity(intent);
				
			}
		});
	}
}
