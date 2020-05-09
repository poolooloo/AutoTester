package com.android.factorytest;

import android.provider.ContactsContract;

public class DataSingleton {
    private static volatile DataSingleton instance = null;

    private DataSingleton(){

    }

    public static DataSingleton getInstance(){
        if( instance == null ){
            synchronized (DataSingleton.class){
                if( instance == null ){
                    instance = new DataSingleton();
                }
            }
        }
        return instance;
    }
}
