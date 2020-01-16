package com.android.parasitologymobilesoftware;

import android.content.Intent;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class SplashScreenActivity extends AppCompatActivity {

    private Handler handler = new Handler();

    Bundle extras = getIntent().getExtras();
    usuarioUsado usuarioUsado = null;
    if(extras != null && extras.contains("usuario")) {
        usuarioUsado = extras.getSerializable("usuario");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent =  new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, 4000);

    }
}