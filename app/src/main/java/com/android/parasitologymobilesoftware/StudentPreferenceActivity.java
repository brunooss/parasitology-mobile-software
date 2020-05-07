package com.android.parasitologymobilesoftware;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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

    private RadioGroup radioGroup01, radioGroup02, radioGroup03, radioGroup04;
    private RadioButton radioButton1, radioButton2, radioButton3, radioButton4, radioButton5, radioButton6, radioButton7, radioButton8, radioButton9, radioButton10, radioButton11;

    private String answer1, answer2, answer3, answer4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_preference);

        dataBase = FirebaseFirestore.getInstance();                 // Cloud Firestore
        firebaseAuth = FirebaseAuth.getInstance();                 // Firebase Auth

        //           studentFirstPref = findViewById(R.id.constraintLayoutStudentFirst);
        //           studentSecondPref = findViewById(R.id.constraintLayoutStudentSecond);
        progressBar = findViewById(R.id.progressBarPreferenceActivity);
        progressBar.setVisibility(View.INVISIBLE);

        completeName = firebaseAuth.getCurrentUser().getDisplayName();   // Getting user's complete name from Firebase Auth
        email = firebaseAuth.getCurrentUser().getEmail();               // Getting user's email from Firebase Auth

        radioGroup01 = findViewById(R.id.radioGroup);
        radioGroup02 = findViewById(R.id.radioGroup2);
        radioGroup03 = findViewById(R.id.radioGroup3);
        radioGroup04 = findViewById(R.id.radioGroup4);
    }

    public void onButtonStudentPreferenceClick(View view){
            boolean allOptions = true;
            switch (radioGroup01.getCheckedRadioButtonId()) {
                case R.id.radioButton:
                    answer1 = "Baixo";
                    break;
                case R.id.radioButton2:
                    answer1 = "Razoável";
                    break;
                case R.id.radioButton3:
                    answer1 = "Alto";
                    break;
                default: // user hasnt made any choice
                    allOptions = false;
            }
            switch (radioGroup02.getCheckedRadioButtonId()) {
                case R.id.radioButton4:
                    answer2 = "Baixo";
                    break;
                case R.id.radioButton5:
                    answer2 = "Razoável";
                    break;
                case R.id.radioButton6:
                    answer2 = "Alto";
                    break;
                default: // user hasnt made any choice
                    allOptions = false;
            }
            switch (radioGroup03.getCheckedRadioButtonId()) {
                case R.id.radioButton7:
                    answer3 = "Baixa";
                    break;
                case R.id.radioButton8:
                    answer3 = "Razoável";
                    break;
                case R.id.radioButton9:
                    answer3 = "Alta";
                    break;
                default: // user hasnt made any choice
                    allOptions = false;
            }
            switch (radioGroup04.getCheckedRadioButtonId()) {
                case R.id.radioButton10:
                    answer4 = "Não";
                    break;
                case R.id.radioButton11:
                    answer4 = "Sim";
                    break;
                default: // user hasnt made any choice
                    allOptions = false;
            }
            if (allOptions) {
                Log.i("buttonStudentPref", "All the options are selected!");
                progressBar.setVisibility(View.VISIBLE);
                DocumentReference docRef = dataBase.collection("generalUserInfo").document(email).collection("surveys").document("previousUse");

                Map<String, Object> answers = new HashMap<>();
                answers.put("Qual é o seu nível de conhecimento em Parasitologia?", answer1);
                answers.put("Qual é o seu interesse por Parasitologia?", answer2);
                answers.put("Como você avalia a importância do estudo das parasitoses?", answer3);
                answers.put("Tem interesse pelas Ciências Biológicas e materiais científicos da área?", answer4);
                docRef.set(answers, SetOptions.merge());

                Map<String, Object> previousSurvey = new HashMap<>();
                previousSurvey.put("previous survey state", true);
                dataBase.collection("generalUserInfo").document(email).collection("specific info").document("state")
                        .update(previousSurvey);

                Map<String, Object> progressStatus = new HashMap<>();
                progressStatus.put("progress status", 0);
                progressStatus.put("progress introduction", 0);
                progressStatus.put("progress protozoarios", 0);
                progressStatus.put("progress helmintos", 0);
                progressStatus.put("progress artropodes", 0);
                dataBase.collection("generalUserInfo").document(email).collection("specific info").document("progress")
                        .set(progressStatus, SetOptions.merge());

                // boolean values - indicates user hasnt made any progress yet
                Map<String, Object> categoriesProgress = new HashMap<>();
                categoriesProgress.put("Introdução", false);
                categoriesProgress.put("Ecologia", false);
                categoriesProgress.put("Conceitos Gerais", false);
                categoriesProgress.put("Classificação", false);
                categoriesProgress.put("Reprodução", false);
                categoriesProgress.put("Ciclo Biológico", false);
                categoriesProgress.put("Atualidades", false);
                categoriesProgress.put("Protozoários", false);
                categoriesProgress.put("Amebíase", false);
                categoriesProgress.put("Giardíase", false);
                categoriesProgress.put("Leishmanioses", false);
                categoriesProgress.put("Tricomonose", false);
                categoriesProgress.put("Doença de Chagas", false);
                categoriesProgress.put("Malária", false);
                categoriesProgress.put("Toxoplasmose", false);
                categoriesProgress.put("Balantidíase", false);
                categoriesProgress.put("Protozooses", false);
                categoriesProgress.put("Helmintos", false);
                categoriesProgress.put("Esquistossomose", false);
                categoriesProgress.put("Fasciolíase", false);
                categoriesProgress.put("Teníase", false);
                categoriesProgress.put("Cisticercose", false);
                categoriesProgress.put("Hidatidose", false);
                categoriesProgress.put("Himenolepíase", false);
                categoriesProgress.put("Estrongiloidíase", false);
                categoriesProgress.put("Tricuríase", false);
                categoriesProgress.put("Ancilostomíase", false);
                categoriesProgress.put("Necatoríase", false);
                categoriesProgress.put("Enterobíase", false);
                categoriesProgress.put("Ascaridíase", false);
                categoriesProgress.put("Larva Migrans", false);
                categoriesProgress.put("Filarioses", false);
                categoriesProgress.put("Outras Helmintoses", false);
                categoriesProgress.put("Artrópodes", false);
                categoriesProgress.put("Hemípteros", false);
                categoriesProgress.put("Mosquitos", false);
                categoriesProgress.put("Moscas", false);
                categoriesProgress.put("Ectoparasitos", false);
                dataBase.collection("generalUserInfo").document(email).collection("specific info").document("progress categories")
                        .set(categoriesProgress,
                                SetOptions.merge());

                Intent intent = new Intent(getBaseContext(), HomeActivity.class);
                startActivity(intent);
                finish();
            } else {
                Log.i("buttonStudentPref", "Some of the options are not selected!");
                //TODO: tell user he hasnt answered all questions
            }


        }

    public void setSchoolGrade(String schoolGrade){
        this.schoolGrade = schoolGrade;
    }
}

