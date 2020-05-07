package com.android.parasitologymobilesoftware;

import android.os.Bundle;

import android.util.Log;
import android.widget.RadioGroup;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class StudentPreferencesFragment extends Fragment {

    private FirebaseFirestore dataBase;
    private FirebaseAuth firebaseAuth;

    private int progressStatus;
    private ProgressBar progressBar;

    private Button buttonSetCalendar;

    private String email;
    private String completeName;
    //           private Button buttonReview;

    private int progressToGo;

    private String schoolGrade;

    private Button buttonAlert;
    private Boolean alertState;

    private RadioGroup radioGroupInitial1;
    private RadioGroup radioGroupInitial2;
    private RadioGroup radioGroupInitial3;

    private DocumentReference docRef;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
        dataBase = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();

        email = firebaseAuth.getCurrentUser().getEmail();
        completeName = firebaseAuth.getCurrentUser().getDisplayName();

        docRef = dataBase.collection("generalUserInfo").document(email);
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

        final View rootView = inflater.inflate(R.layout.fragment_student_preferences, container, false);

        buttonSetCalendar = rootView.findViewById(R.id.buttonStudentPreferencesSetDate);
        buttonAlert = rootView.findViewById(R.id.buttonStudentPreferencesSetAlert);
        //           buttonReview = rootView.findViewById(R.id.buttonReview);
        progressBar = rootView.findViewById(R.id.progressApp);

        radioGroupInitial1 = rootView.findViewById(R.id.radioGroup);
        radioGroupInitial2 = rootView.findViewById(R.id.radioGroup2);
        radioGroupInitial3 = rootView.findViewById(R.id.radioGroup3);

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

                alertState = documentSnapshot.getBoolean("alert state");
                if (alertState)
                    buttonAlert.setBackground(getActivity().getDrawable(R.drawable.custom_linear_layout_alert_true));
                else {
                    buttonAlert.setBackground(getActivity().getDrawable(R.drawable.custom_linear_layout_alert_false));
                    buttonSetCalendar.setElevation(0);
                    buttonSetCalendar.setClickable(false);
                }
            }
        });
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();

        Log.i("StudentPreferenceFragment", "OnResume");
        docRef.collection("specific info").document("progress")
                .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                setProgressToGo(documentSnapshot.getLong("progress status").intValue());
                Log.i("StudentPreferenceFragment", "vamos entender melhor isso");
            }
        });
        progressBar.setProgress(progressToGo, true);
        Log.d("StudentPreferenceFragment", "Everything ok here. Our new app progress is: "+progressToGo);
    }

    public void setSchoolGrade(String schoolGrade){
        this.schoolGrade = schoolGrade;
    }

    public void setAlertState(boolean alertState){
        this.alertState = alertState;
    }

    public void setProgressStatus(int progressStatus){
        this.progressStatus = progressStatus;
    }

    public void setProgressToGo(int progressToGo) {
        this.progressToGo = progressToGo;
    }
}
