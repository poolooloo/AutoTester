package com.android.factorytest;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class AlertDialogFragment extends DialogFragment {


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.lcd_prompt_message)
        .setPositiveButton(R.string.lcd_success,new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog,int id){
                          
            }
        }).setNegativeButton(R.string.lcd_failure,new DialogInterface.OnClickListener(){
                public void onClick(DialogInterface dialog,int id){
                    
                }
            }).setNeutralButton(R.string.lcd_retest,new DialogInterface.OnClickListener(){
                public void onClick(DialogInterface dialog,int id){
                       
                }

        });
        return builder.create();
    }
}


//androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(TestColor.this);
//                    builder.setTitle("error");
//                            builder.setMessage(R.string.lcd_prompt_message)
//                            .setPositiveButton(R.string.lcd_success,new DialogInterface.OnClickListener(){
//public void onClick(DialogInterface dialog,int id){
//        mstate = 1;
//        Intent itok = new Intent();
//        itok.putExtra("sucess", true);
//        setResult(1, itok);
//        if (musekey == 1) finish();
//        }
//        }).setNegativeButton(R.string.lcd_failure,new DialogInterface.OnClickListener(){
//public void onClick(DialogInterface dialog,int id){
//        mstate = 2;
//        Intent itfaile = new Intent();
//        itfaile.putExtra("sucess", false);
//        setResult(1, itfaile);
//        if (musekey == 1) finish();
//        }
//        }).setNeutralButton(R.string.lcd_retest,new DialogInterface.OnClickListener(){
//public void onClick(DialogInterface dialog,int id){
//        mstate = 3;
////                try {
////                    startTestColor();
////                } catch (InterruptedException e) {
////                    e.printStackTrace();
////                }
//        }
//
//        });
//        AlertDialog dialog = builder.create();
//        dialog.show();