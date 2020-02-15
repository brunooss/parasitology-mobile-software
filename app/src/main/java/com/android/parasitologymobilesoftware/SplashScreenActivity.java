package com.android.parasitologymobilesoftware;

import android.content.Intent;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Document;

public class SplashScreenActivity extends AppCompatActivity {

    private Handler handler = new Handler();

    /* Firebase instance variables */
    private FirebaseAuth mFirebaseAuth;
    private FirebaseFirestore dataBase;
    private FirebaseUser mFirebaseUser;
    private boolean test = false; // Test even with authentication
    private boolean studentPrefSetted = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        /* Initialize Firebase Auth */
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();

        dataBase = FirebaseFirestore.getInstance();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intentUnauthenticated =  new Intent(getBaseContext(), SigninActivity.class);
                Intent intentAuthenticated = new Intent(getBaseContext(), HomeActivity.class);
                Intent intentAuthenticatedWithoutPreference = new Intent(getBaseContext(), StudentPreferenceActivity.class);
                if(mFirebaseUser == null){              // User is unauthenticated
                    startActivity(intentUnauthenticated);       // Send him to SignIn Activity, so he can Authenticate
                } else {                    // User is authenticated
                    DocumentReference docRef = dataBase.collection("generalUserInfo").document(mFirebaseAuth.getCurrentUser().getEmail());
                    docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            setStudentPrefSetted(documentSnapshot.getBoolean("preferenceState").booleanValue());
                        }
                    });
                    if (studentPrefSetted == false){
                        startActivity(intentAuthenticatedWithoutPreference);
                    }
                    else startActivity(intentAuthenticated);         // Send him to Home Activity
                }
                finish();
            }
        }, 2000);

    }
    public void setStudentPrefSetted(boolean studentPrefSetted){
        this.studentPrefSetted = studentPrefSetted;
    }
}
