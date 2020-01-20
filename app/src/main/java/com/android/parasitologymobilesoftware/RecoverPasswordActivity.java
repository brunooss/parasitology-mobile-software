package com.android.parasitologymobilesoftware;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.transition.Slide;

import androidx.annotation.Nullable;


public class RecoverPasswordActivity extends Activity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Slide moveScreen = new Slide();
            moveScreen.setDuration(500);

            getWindow().setEnterTransition(moveScreen);
        }

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_recoverpassword);
    }
}
