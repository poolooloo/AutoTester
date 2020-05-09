package com.android.factorytest;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class AutoRunDialog extends DialogFragment implements View.OnClickListener{

    private Dialog dialog;
    private View view;

    public AutoRunDialog(Context context) {
        
          view = LayoutInflater.from(context).inflate(R.layout.alert_dialog,null);
          initUi();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_FRAME,R.style.AppTheme);
    }
//    @NonNull
//    @Override
//    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
//        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//        builder.setMessage("Prompt");
//
//        return builder.create();

//    }


//    @NonNull
//    @Override
//    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
//        return super.onCreateDialog(savedInstanceState);
//    }

    @Override
    public void onClick(View v) {
        
    }

    private void initUi(){
        final Button btnAlertCancel = view.findViewById(R.id.btnAlertCancel);
        final Button btnAlertOK = view.findViewById(R.id.btnAlertOK);
    }
}
