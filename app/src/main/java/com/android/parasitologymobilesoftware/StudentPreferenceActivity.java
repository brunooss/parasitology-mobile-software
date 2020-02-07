package com.android.parasitologymobilesoftware;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;


public class StudentPreferenceActivity extends AppCompatActivity {

    private FirebaseFirestore dataBase;
    private FirebaseAuth firebaseAuth;

    private String email;
    private String schoolGrade;
    private String completeName;

    private int studentPreference = 0;
    private String studentPref;

    private ConstraintLayout studentFirstPref;
    private ConstraintLayout studentSecondPref;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_preference);

        dataBase = FirebaseFirestore.getInstance();                 // Cloud Firestore
        firebaseAuth = FirebaseAuth.getInstance();                 // Firebase Auth

        studentFirstPref = findViewById(R.id.constraintLayoutStudentFirst);
        studentSecondPref = findViewById(R.id.constraintLayoutStudentSecond);
        progressBar = findViewById(R.id.progressBarPreferenceActivity);
        progressBar.setVisibility(View.INVISIBLE);

        completeName = firebaseAuth.getCurrentUser().getDisplayName();   // Getting user's complete name from Firebase Auth
        email = firebaseAuth.getCurrentUser().getEmail();               // Getting user's email from Firebase Auth
    }

    public void onButtonStudentFirst(View view){
        if (studentPreference == 0 || studentPreference == 2) {
            studentFirstPref.setBackground(getDrawable(R.drawable.custom_button_yellow_background_pressed));
            studentSecondPref.setBackground(getDrawable(R.drawable.custom_buttom_yellow_background));
            studentPreference = 1;
        }
        else {  // The student preference is already this one (1)
            studentFirstPref.setBackground(getDrawable(R.drawable.custom_buttom_yellow_background));
            studentPreference = 0;
        }
    }

    public void onButtonStudentSecond(View view){
        if (studentPreference == 0 || studentPreference == 1) {
            studentFirstPref.setBackground(getDrawable(R.drawable.custom_buttom_yellow_background));
            studentSecondPref.setBackground(getDrawable(R.drawable.custom_button_yellow_background_pressed));
            studentPreference = 2;
        }
        else {  // The student preference is already this one (2)
            studentSecondPref.setBackground(getDrawable(R.drawable.custom_buttom_yellow_background));
            studentPreference = 0;
        }
    }

    public void onButtonStudentPreferenceClick(View view){
        if (studentPreference != 0) {
            progressBar.setVisibility(View.VISIBLE);

            if (studentPreference == 1) studentPref = "tradicional";
            else studentPref = "modern";

            Map<String, Object> studentPrefInt = new HashMap<>();
            studentPrefInt.put("student preference", studentPreference);

            dataBase.collection("generalUserInfo").document(email)
                    .set(studentPrefInt, SetOptions.merge());

            DocumentReference docRef = dataBase.collection("generalUserInfo").document(email);       // Getting the school grade from the data base
            docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    schoolGrade = documentSnapshot.getString("school grade");

                    Map<String, Object> studentPrefString = new HashMap<>();
                    studentPrefString.put("student preference", studentPref);

                    dataBase.collection(schoolGrade).document(completeName)
                            .set(studentPrefString, SetOptions.merge());
                }
            });

            Intent intent = new Intent(getBaseContext(), HomeActivity.class);
            startActivity(intent);
            finish();
        }
    }

}

