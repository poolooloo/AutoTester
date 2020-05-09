package com.android.factorytest;

public class PublicData {
    private static Boolean mAutoRunFlag = true;
    public static Boolean getAutoRunFlag(){
        return mAutoRunFlag;
    }
    public static void setAutoRunFlag(Boolean flag){
        mAutoRunFlag = flag;
    }
}
