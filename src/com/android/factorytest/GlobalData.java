package com.android.factorytest;

import android.app.Application;

public class GlobalData extends Application {
       private boolean mAutoRunFlag = true;//自动测试与手动测试标志
       public boolean getAutoRunFlag(){
           return mAutoRunFlag;
       }
       public void setAutoRunFlag(boolean flag ){
           mAutoRunFlag = flag;
       }
}
