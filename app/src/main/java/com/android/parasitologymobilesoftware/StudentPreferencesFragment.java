package com.android.parasitologymobilesoftware;

import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import org.w3c.dom.Document;

import java.util.HashMap;
import java.util.Map;

public class StudentPreferencesFragment extends Fragment {

    private FirebaseFirestore dataBase;
    private FirebaseAuth firebaseAuth;

    private int progressStatus;
    private ProgressBar progressBar;

    private Button buttonSetCalendar;

    private int studentPreference;
    private int studentPreferenceDataBase;
    private String studentPref;

    private String email;
    private String completeName;

    private ConstraintLayout constraintLayoutStudentFirst;
    private ConstraintLayout constraintLayoutStudentSecond;

    private Button buttonReview;

    private String schoolGrade;

    private Button buttonAlert;
    private Boolean alertState;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
        dataBase = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();

        email = firebaseAuth.getCurrentUser().getEmail();
        completeName = firebaseAuth.getCurrentUser().getDisplayName();

        DocumentReference docRef = dataBase.collection("generalUserInfo").document(email);
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                setSchoolGrade(documentSnapshot.get("school grade").toString());                     // School Grade
            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_student_preferences, container, false);

        buttonSetCalendar = rootView.findViewById(R.id.buttonStudentPreferencesSetDate);
        buttonAlert = rootView.findViewById(R.id.buttonStudentPreferencesSetAlert);
        buttonReview = rootView.findViewById(R.id.buttonReview);
        progressBar = rootView.findViewById(R.id.progressApp);

        constraintLayoutStudentFirst = rootView.findViewById(R.id.constraintLayoutStudentFirstFrag);
        constraintLayoutStudentSecond = rootView.findViewById(R.id.constraintLayoutStudentSecondFrag);

        DocumentReference docRefProgress = dataBase.collection("generalUserInfo").document(email).collection("specific info").document("progress");
                docRefProgress.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                progressStatus = documentSnapshot.getLong("progress status").intValue();
                progressBar.setProgress(progressStatus, true);
            }
        });

        final DocumentReference docRef = dataBase.collection("generalUserInfo").document(email).collection("specific info").document("state");
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                setStudentPreference(documentSnapshot.getLong("student preference").intValue());    // Student's preference
                studentPreference = documentSnapshot.getLong("student preference").intValue();

                alertState = documentSnapshot.getBoolean("alert state");
                if (alertState)
                    buttonAlert.setBackground(getActivity().getDrawable(R.drawable.custom_linear_layout_alert_true));
                else {
                    buttonAlert.setBackground(getActivity().getDrawable(R.drawable.custom_linear_layout_alert_false));
                    buttonSetCalendar.setElevation(0);
                    buttonSetCalendar.setClickable(false);
                }

                if (studentPreference == 1) {
                    constraintLayoutStudentFirst.setElevation(20);
                    constraintLayoutStudentSecond.setElevation(1);
                }
                else if (studentPreference == 2) {
                    constraintLayoutStudentSecond.setElevation(20);
                    constraintLayoutStudentFirst.setElevation(1);
                }
            }
        });

        constraintLayoutStudentFirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (constraintLayoutStudentFirst.getElevation() == 1){
                    studentPreference = 1;

                    /* Sending int preference to data base*/
                    Map<String, Object> studentPreferenceInt = new HashMap<>();
                    studentPreferenceInt.put("student preference", studentPreference);
                    dataBase.collection("generalUserInfo").document(email).collection("specific info").document("state")
                            .update(studentPreferenceInt);

                    /* Sending String preference to data base*/
                    Map<String, Object> studentPreferenceString = new HashMap<>();
                    studentPreferenceString.put("student preference", "tradicional");
                    dataBase.collection(schoolGrade).document(completeName)
                            .update(studentPreferenceString);

                    constraintLayoutStudentFirst.setElevation(20);
                    constraintLayoutStudentSecond.setElevation(1);
                }
            }
        });

        constraintLayoutStudentSecond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (constraintLayoutStudentSecond.getElevation() == 1){
                    studentPreference = 2;

                    /* Sending int preference to data base */
                    Map<String, Object> studentPreferenceInt = new HashMap<>();
                    studentPreferenceInt.put("student preference", studentPreference);
                    dataBase.collection("generalUserInfo").document(email).collection("specific info").document("state")
                            .update(studentPreferenceInt);

                    /* Sending String preference to data base*/
                    Map<String, Object> studentPreferenceString = new HashMap<>();
                    studentPreferenceString.put("student preference", "modern");
                    dataBase.collection(schoolGrade).document(completeName)
                            .update(studentPreferenceString);

                    constraintLayoutStudentSecond.setElevation(20);
                    constraintLayoutStudentFirst.setElevation(1);
                }
            }
        });
        // Inflate the layout for this category
        return rootView;
    }

    public void setSchoolGrade(String schoolGrade){
        this.schoolGrade = schoolGrade;
    }

    public void setStudentPreference(int studentPreference){
        this.studentPreferenceDataBase = studentPreference;
    }

    public void setAlertState(boolean alertState){
        this.alertState = alertState;
    }

    public void setProgressStatus(int progressStatus){
        this.progressStatus = progressStatus;
    }
}
