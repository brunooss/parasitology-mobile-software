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

    private int studentPreference;
    private int studentPreferenceDataBase;
    private String studentPref;

    private String email;
    private String completeName;

    private ConstraintLayout constraintLayoutStudentFirst;
    private ConstraintLayout constraintLayoutStudentSecond;

    private Button buttonReview;

    private String schoolGrade;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
        dataBase = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();

        email = firebaseAuth.getCurrentUser().getEmail();
        completeName = firebaseAuth.getCurrentUser().getDisplayName();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_student_preferences, container, false);

        buttonReview = rootView.findViewById(R.id.buttonReview);
        progressBar = rootView.findViewById(R.id.progressApp);
        constraintLayoutStudentFirst = rootView.findViewById(R.id.constraintLayoutStudentFirstFrag);
        constraintLayoutStudentSecond = rootView.findViewById(R.id.constraintLayoutStudentSecondFrag);

        DocumentReference docRef = dataBase.collection("generalUserInfo").document(email);
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                setStudentPreference(documentSnapshot.getLong("student preference").intValue());    // Student's preference
                studentPreference = documentSnapshot.getLong("student preference").intValue();

                setProgressStatus(documentSnapshot.getLong("progress status").intValue());        // Progress
                progressBar.setProgress(progressStatus, true);

                setSchoolGrade(documentSnapshot.get("school grade").toString());                     // School Grade
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
                    dataBase.collection("generalUserInfo").document(email)
                            .update(studentPreferenceInt);

                    /* Sending String preference to data base*/
                    Map<String, Object> studentPreferenceString = new HashMap<>();
                    studentPreferenceString.put("student preference", "tradicional");
                    dataBase.collection(schoolGrade).document(completeName)
                            .update(studentPreferenceString);
                    //Toast.makeText(getActivity(), "metodologia alterada com sucesso: tradicional", Toast.LENGTH_SHORT).show();

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
                    dataBase.collection("generalUserInfo").document(email)
                            .update(studentPreferenceInt);

                    /* Sending String preference to data base*/
                    Map<String, Object> studentPreferenceString = new HashMap<>();
                    studentPreferenceString.put("student preference", "modern");
                    dataBase.collection(schoolGrade).document(completeName)
                            .update(studentPreferenceString);
                    //Toast.makeText(getActivity(), "metodologia alterada com sucesso: modern", Toast.LENGTH_SHORT).show();

                    constraintLayoutStudentSecond.setElevation(20);
                    constraintLayoutStudentFirst.setElevation(1);
                }
            }
        });
        // Inflate the layout for this fragment
        return rootView;
    }

    public void setSchoolGrade(String schoolGrade){
        this.schoolGrade = schoolGrade;
        //Toast.makeText(getActivity(), schoolGrade, Toast.LENGTH_SHORT).show();
    }

    public void setStudentPreference(int studentPreference){
        this.studentPreferenceDataBase = studentPreference;
    }

    public void setProgressStatus(int progressStatus){
        this.progressStatus = progressStatus;
    }
}
