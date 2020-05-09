package com.android.factorytest;

import android.content.Context;
import android.widget.Button;

import androidx.annotation.Nullable;
import android.annotation.SuppressLint;
@SuppressLint("AppCompatCustomView")

public class TimerButton extends Button {
    public TimerButton(Context context) {
        super(context);
    }

    @Override
    public void setOnClickListener(@Nullable OnClickListener l) {
        super.setOnClickListener(l);
    }
}
