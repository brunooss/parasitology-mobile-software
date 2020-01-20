package com.android.parasitologymobilesoftware;

import android.content.Intent;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashScreenActivity extends AppCompatActivity {

    private Handler handler = new Handler();

    /* Firebase instace variables */
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        /* Initialize Firebase Auth */
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intentUnauthenticated =  new Intent(getBaseContext(), SigninActivity.class);
                Intent intentAuthenticated = new Intent(getBaseContext(), MainActivity.class);
                if(mFirebaseUser == null){                  // User is unauthenticated
                    startActivity(intentUnauthenticated);  // Send him to SignIn Activity, so he can Authenticate
                } else {                                        // User is authenticated
                    startActivity(intentUnauthenticated);         // Send him to Main Activity
                }
                finish();
            }
        }, 3000);

    }
}
