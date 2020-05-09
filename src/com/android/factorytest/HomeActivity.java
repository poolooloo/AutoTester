package com.android.factorytest;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {

    private Intent mIntent = null;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_home);

        final Button btnAutoTest =  findViewById(R.id.btnAutoTest);
        btnAutoTest.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                final GlobalData globalData = (GlobalData)getApplication();
                globalData.setAutoRunFlag(true);
                mIntent =new Intent(HomeActivity.this,ResultActivity.class);
                HomeActivity.this.startActivity(mIntent);
            }
        });

        final Button btnManualTest = findViewById(R.id.btnManualTest);
        btnManualTest.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                final GlobalData globalData = (GlobalData)getApplication();
                globalData.setAutoRunFlag(false);
                mIntent =new Intent(HomeActivity.this,MainActivity.class);
                HomeActivity.this.startActivity(mIntent);

            }
        });
    }

    
}
