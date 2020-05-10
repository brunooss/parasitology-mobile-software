package com.android.parasitologymobilesoftware;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;

public class FeedbackActivity extends AppCompatActivity {

    private EditText editTextFeedback;
    private TextView textViewCharFeedback, textViewInsertFeedback;
    private Spinner spinnerFeedback;

    private String auxTextCount = " / ";
    private int characAllowed = 762;

    private FirebaseFirestore dataBase;
    private FirebaseAuth firebaseAuth;

    private int lenghCharac;

    private String email;
    private String nome;
    private String feedback;

    private View dialogConfirmFeedback;
    private AlertDialog.Builder builderDialogConfirmFeedback;
    private AlertDialog alertDialogConfirmFeedback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        LayoutInflater inflater = getLayoutInflater();
        dialogConfirmFeedback = inflater.inflate(R.layout.dialog_confirm_feedback, null);
        builderDialogConfirmFeedback = new AlertDialog.Builder(FeedbackActivity.this);
        builderDialogConfirmFeedback.setView(dialogConfirmFeedback);
        builderDialogConfirmFeedback.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(FeedbackActivity.this, HomeActivity.class);
            }
        });
        alertDialogConfirmFeedback = builderDialogConfirmFeedback.create();

        final Toolbar toolbar = findViewById(R.id.toolbarFeedback);
        toolbar.setTitle("FEEDBACK");
        toolbar.setTitleTextAppearance(this, R.style.FuturaMediumTextAppearance);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        textViewCharFeedback = findViewById(R.id.textViewNumCharFeedback);
        editTextFeedback = findViewById(R.id.editTextFeedBackInput);
        textViewInsertFeedback = findViewById(R.id.textView14);
        editTextFeedback.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (editTextFeedback.getText().length() == 0)
                    textViewCharFeedback.setTextColor(getResources().getColor(R.color.colorRedError, getTheme()));
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                lenghCharac = editTextFeedback.getText().length();
                if (lenghCharac <= characAllowed) {
                    textViewInsertFeedback.setTextColor(getResources().getColor(R.color.colorAccent, getTheme()));
                    editTextFeedback.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.colorAccent));
                    textViewCharFeedback.setTextColor(getResources().getColor(R.color.colorAccent, getTheme()));
                    String firsInput = ""+lenghCharac;
                    String secondInput = ""+characAllowed;
                    textViewCharFeedback.setText(firsInput.concat(auxTextCount.concat(secondInput)));

                    if (lenghCharac == 0){ // alert user to type words
                        editTextFeedback.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.colorRedError));
                        textViewCharFeedback.setTextColor(getResources().getColor(R.color.colorRedError, getTheme()));
                        textViewInsertFeedback.setTextColor(getResources().getColor(R.color.colorRedError, getTheme()));
                    }

                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        spinnerFeedback = findViewById(R.id.spinnerFeedback);
                ArrayAdapter <CharSequence> stringArrayAdapter = ArrayAdapter.createFromResource(
                        this,
                        R.array.feedback_array,
                        R.layout.simple_spinner_item_feedback);
        stringArrayAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item_feedback);
        spinnerFeedback.setAdapter(stringArrayAdapter);

    }

    public void onButtonSendFeedback(View view) {
        if (lenghCharac == 0){
            editTextFeedback.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.colorRedError));
            textViewCharFeedback.setTextColor(getResources().getColor(R.color.colorRedError, getTheme()));
            textViewInsertFeedback.setTextColor(getResources().getColor(R.color.colorRedError, getTheme()));
        } else {
            alertDialogConfirmFeedback.show();

            firebaseAuth = FirebaseAuth.getInstance();
            dataBase = FirebaseFirestore.getInstance();

            email = firebaseAuth.getCurrentUser().getEmail();
            nome = firebaseAuth.getCurrentUser().getDisplayName();
            feedback = editTextFeedback.getText().toString();

            Map<String, Object> feedbackMap = new HashMap<>();
            feedbackMap.put("nome", nome);
            feedbackMap.put("email", email);
            feedbackMap.put("feedback", feedback);


            dataBase.collection("feedback").document(email)
                    .set(feedbackMap, SetOptions.merge());
        }

    }

}
