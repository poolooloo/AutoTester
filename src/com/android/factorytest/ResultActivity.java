package com.android.factorytest;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import java.io.File;
import java.io.IOException;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

public class ResultActivity extends AppCompatActivity {

    private static final String TAG = "factorytest";
    private static String[] mstrarrayresult = new String[13];
    private static String[] mstrarrayname = new String[13];
    private static String ResultFile = "/sdcard/testresult.xls";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);


        Label lbl1;
        Label bll2;
        Label bll3;
        WritableWorkbook wwb = null;
        try {
            // 创建一个可写入的工作薄(Workbook)对象
            wwb = Workbook.createWorkbook(new File(ResultFile));
        } catch (IOException e) {
            Log.e(TAG, "create work book failed");
            e.printStackTrace();
        }
        if (wwb != null) {
            // 第一个参数是工作表的名称，第二个是工作表在工作薄中的位置
            WritableSheet ws = wwb.createSheet("sheet0", 0);
            //WritableSheet ws = wwb.getSheet(0);
            // 在指定单元格插入数据
//
            for (int i = 0; i < 13; i++) {
                for (int j = 0; j < 2; j++) {
                    if (j == 0) {
                        lbl1 = new Label(j, i, mstrarrayname[i]);

                        try {
                            ws.addCell(lbl1);
                        } catch (WriteException e) {
                            e.printStackTrace();
                        }


                    } else {
                        bll2 = new Label(j, i, mstrarrayresult[i]);
                        try {
                            ws.addCell(bll2);
                        } catch (WriteException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
            for (int k = 0; k < 13; k++) {
                bll3 = new Label(2, k, "0000-00-00   00:00:00");
                try {
                    ws.addCell(bll3);
                } catch (WriteException e) {
                    e.printStackTrace();
                }
            }
//			try {
//				ws.addCell(lbl1);
//				//ws.addCell(bll2);
//			} catch (RowsExceededException e1) {
//				e1.printStackTrace();
//			} catch (WriteException e1) {
//				e1.printStackTrace();
//			}
            try {
                // 从内存中写入文件中
                wwb.write();
                wwb.close();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (WriteException e) {
                e.printStackTrace();
            }
        }
    }
}
