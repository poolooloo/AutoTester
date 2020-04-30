package com.android.factorytest;

import android.os.Bundle;
import android.os.Environment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

import androidx.appcompat.app.AppCompatActivity;

//


public class JsonCreatorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_json_creator);

        if( !Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED )){
            return;
        }
        writeJsonObjToSdCard( createJsonObject() );
    }

    // 创建以下的JSON对象
    // {
    // "phone" : ["12345678", "87654321"], // 数组
    // "name" : "tianjiefeng", // 字符串
    // "age" : 100, // 数值
    // "address" : { "country" : "china", "province" : "jiangsu" }, // JSON对象
    // "married" : false // 布尔值
    // }

    public JSONObject createJsonObject(){
        JSONObject jsonInfo = new JSONObject();
        try{
            //value1
            JSONArray phone = new JSONArray();
            phone.put("123456");
            phone.put("99999");
            jsonInfo.put("phone",phone);
            //value2
            jsonInfo.put("name","kkkk");
            //value3
            jsonInfo.put("age",100);
            //value4
            JSONObject address = new JSONObject();
            address.put("country","chine");
            address.put("province","beijing");
            address.put("address",address);
            //value5
            address.put("married",false);
        } catch ( JSONException e){
            e.printStackTrace();
        }
        return jsonInfo;
    }

    private void writeJsonObjToSdCard(JSONObject jsonObj){
          File file = new File(Environment.getExternalStorageDirectory() + File.separator + "json" + File.separator + "json.txt" );
          //文件夹是否存在，创建文件夹
          if( !file.getParentFile().exists() ){
              file.getParentFile().mkdirs();
          }
          //写入内存卡
        PrintStream outputStream = null;
          try{
              outputStream = new PrintStream( new FileOutputStream(file));
              outputStream.print( jsonObj.toString());
          }catch ( FileNotFoundException e){
               e.printStackTrace();
          }finally{
              if( outputStream != null ){
                  outputStream.close();
              }
          }
    }
}
