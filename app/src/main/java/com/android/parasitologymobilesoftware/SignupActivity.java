package com.android.parasitologymobilesoftware;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.*;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.firestore.auth.User;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignupActivity extends AppCompatActivity {

    /* Variables */
    private FirebaseAuth firebaseAuth;   // Authentication
    private FirebaseFirestore dataBase; // Database

    private SharedPreferences preferences;

    private EditText editTextName, editTextEmail, editTextPassword, editTextPasswordConfirm;
    private TextView textViewNameError, textViewEmailError, textViewPasswordError, textViewPasswordConfirmError, textViewButtonError;
    private ProgressBar progressBar;
    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);


        final Toolbar toolbar = findViewById(R.id.toolbarSignUp);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        preferences = getSharedPreferences("prefs", 0);  // 0 = MODE_PRIVATE

        firebaseAuth = FirebaseAuth.getInstance();
        dataBase = FirebaseFirestore.getInstance();

        editTextName = findViewById(R.id.editTextSignUpName);
        textViewNameError = findViewById(R.id.textViewSignUpErrorName);

        editTextEmail = findViewById(R.id.editTextSignUpEmail);
        textViewEmailError = findViewById(R.id.textViewSignUpErrorEmail);

        editTextPassword = findViewById(R.id.editTextSignUpPassword);
        textViewPasswordError = findViewById(R.id.textViewSignUpErrorPassword);

        editTextPasswordConfirm = findViewById(R.id.editTextSignUpConfirmPassword);
        textViewPasswordConfirmError = findViewById(R.id.textViewSignUpErrorPasswordConfirm);

        editTextName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b) { // If is not focused
                    if (isNameValid(editTextName.getText().toString()))
                        textViewNameError.setTextColor(Color.TRANSPARENT);
                    else
                        textViewNameError.setTextColor(getResources().getColor(R.color.colorRedError, getTheme()));
                }
            }
        });
        editTextEmail.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b) { // If is not focused
                    if (isEmailValid(editTextEmail.getText().toString()))
                        textViewEmailError.setTextColor(Color.TRANSPARENT);
                    else
                        textViewEmailError.setTextColor(getResources().getColor(R.color.colorRedError, getTheme()));
                }
            }
        });
        editTextPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b) { // If is not focused
                    if (isPasswordValid(editTextPassword.getText().toString()))
                        textViewPasswordError.setTextColor(Color.TRANSPARENT);
                    else
                        textViewPasswordError.setTextColor(getResources().getColor(R.color.colorRedError, getTheme()));
                }
            }
        });
        editTextPasswordConfirm.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b) { // If is not focused
                    if (isPasswordConfirmValid(editTextPassword.getText().toString(), editTextPasswordConfirm.getText().toString()))
                        textViewPasswordConfirmError.setTextColor(Color.TRANSPARENT);
                    else
                        textViewPasswordConfirmError.setTextColor(getResources().getColor(R.color.colorRedError, getTheme()));
                }
            }
        });

        spinner = findViewById(R.id.spinnerSignUpYear);

        ArrayAdapter<CharSequence> stringArrayAdapter = ArrayAdapter.createFromResource(
                this,
                R.array.years_array,
                R.layout.simple_spinner_item);
        stringArrayAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(stringArrayAdapter);

        progressBar = findViewById(R.id.progressBarSignUp);
        progressBar.setVisibility(View.INVISIBLE);

        textViewButtonError = findViewById(R.id.textViewSignUpErrorButton);
    }
    /* Methods */
    public boolean isNameValid(String name) { // Validates the name input, returning true if it's valid or false if it's not.
        String regexName = "\\d+"; // Capture only numbers.

        Pattern pattern = Pattern.compile(regexName);
        Matcher m = pattern.matcher(name);

        return !(name.startsWith(" ") || m.find() || name.equals("")); // Returns true only if the name neither starts with a blank space nor has numbers.
    }

    public boolean isEmailValid(String email) { // Validates the email input, returning true if it's valid or false if it's not.
        String regexEmail = "^\\w*(\\.\\w*)?@\\w*\\.[a-z]+(\\.[a-z]+)?$";

        Pattern r = Pattern.compile(regexEmail);
        Matcher m = r.matcher(email);

        return m.find();
    }

    public boolean isPasswordValid(String password) { //Validates the password, returning true if it's valid or false if it's not.
        return password.length() > 5; // Returns true only if the password length is longer than 7 chars.
    }

    public boolean isPasswordConfirmValid(String password, String passwordConfirm) { //Validates the password confirmation, returning true if it's valid or false if it's not.
        return password.equals(passwordConfirm); // Returns true only if the password is equals to the confirmation.
    }

    public void onButtonCloseWindow(View view) {
        finish();
    }

    public void onButtonSignUpClick(View view) {
        if (isNameValid(editTextName.getText().toString()) && isEmailValid(editTextEmail.getText().toString())
                && isPasswordValid(editTextPassword.getText().toString())
                && isPasswordConfirmValid(editTextPassword.getText().toString(), editTextPasswordConfirm.getText().toString())) {
            textViewButtonError.setTextColor(Color.TRANSPARENT);
            progressBar.setVisibility(View.VISIBLE);

            firebaseAuth.createUserWithEmailAndPassword(editTextEmail.getText().toString(), editTextPassword.getText().toString())
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                final FirebaseUser user = firebaseAuth.getCurrentUser();
                                UserProfileChangeRequest profileChangeRequest = new UserProfileChangeRequest
                                        .Builder()
                                        .setDisplayName(editTextName.getText().toString())
                                        .build();
                                user.updateProfile(profileChangeRequest);

                                /* User's complete name and school grade will be sent to Cloud Firestore */

                                Map<String, Object> users = new HashMap<>();
                                users.put("complete name", editTextName.getText().toString());
                                users.put("school grade", spinner.getSelectedItem().toString());
                                users.put("email", editTextEmail.getText().toString());

                                Map<String, Object> state = new HashMap<>();
                                state.put("previous survey state", false);
                                state.put("alert state", false);

                                Map<String, Object> progress = new HashMap<>();
                                progress.put("progress artropodes", 0);
                                progress.put("progress helmintos", 0);
                                progress.put("progress introduction", 0);
                                progress.put("progress protozoarios", 0);
                                progress.put("progress status", 0);

                                Map<String, Object> progressCategories = new HashMap<>();
                                progressCategories.put("Amebíase", false);
                                progressCategories.put("Ancilostomíase", false);
                                progressCategories.put("Artrópodes", false);
                                progressCategories.put("Ascaridíase", false);
                                progressCategories.put("Atualidades", false);
                                progressCategories.put("Balantidíase", false);
                                progressCategories.put("Ciclo Biológico", false);
                                progressCategories.put("Cisticercose", false);
                                progressCategories.put("Classificação", false);
                                progressCategories.put("Conceitos Gerais", false);
                                progressCategories.put("Doença de Chagas", false);
                                progressCategories.put("Ecologia", false);
                                progressCategories.put("Ectoparasitos", false);
                                progressCategories.put("Enterobíase", false);
                                progressCategories.put("Esquistossomose", false);
                                progressCategories.put("Estrongiloidíase", false);
                                progressCategories.put("Fasciolíase", false);
                                progressCategories.put("Filarioses", false);
                                progressCategories.put("Giardíase", false);
                                progressCategories.put("Helmintos", false);
                                progressCategories.put("Hemípteros", false);
                                progressCategories.put("Hidatidose", false);
                                progressCategories.put("Himenolepíase", false);
                                progressCategories.put("Introdução", false);
                                progressCategories.put("Larva Migrans", false);
                                progressCategories.put("Leishmanioses", false);
                                progressCategories.put("Malária", false);
                                progressCategories.put("Moscas", false);
                                progressCategories.put("Mosquitos", false);
                                progressCategories.put("Necatoríase", false);
                                progressCategories.put("Outras Helmintoses", false);
                                progressCategories.put("Protozooses", false);
                                progressCategories.put("Protozoários", false);
                                progressCategories.put("Reprodução", false);
                                progressCategories.put("Teníase", false);
                                progressCategories.put("Toxoplasmose", false);
                                progressCategories.put("Tricomonose", false);
                                progressCategories.put("Tricuríase", false);


                                dataBase.collection(spinner.getSelectedItem().toString()).document(editTextName.getText().toString())
                                        .set(users);

                                dataBase.collection("generalUserInfo").document(editTextEmail.getText().toString())
                                        .set(users);

                                dataBase.collection("generalUserInfo").document(editTextEmail.getText().toString()).collection("specific info").document("state")
                                        .set(state);

                                dataBase.collection("generalUserInfo").document(editTextEmail.getText().toString()).collection("specific info").document("progress")
                                        .set(progress);

                                dataBase.collection("generalUserInfo").document(editTextEmail.getText().toString()).collection("specific info").document("progress categories")
                                        .set(progressCategories);

                                progressBar.setVisibility(View.INVISIBLE);

                                Intent intent = new Intent(getBaseContext(), IntroductionActivity.class);
                                intent.putExtra("username", editTextName.getText().toString());

                                startActivity(intent);
                                finish();
                            } else {
                                try {
                                    throw task.getException();
                                } catch (FirebaseAuthUserCollisionException existEmail) {
                                    textViewButtonError.setTextColor(getResources().getColor(R.color.colorRedError, getTheme()));
                                    progressBar.setVisibility(View.INVISIBLE);
                                } catch (FirebaseAuthInvalidCredentialsException malformedEmail) {
                                    textViewButtonError.setTextColor(getResources().getColor(R.color.colorRedError, getTheme()));
                                    progressBar.setVisibility(View.INVISIBLE);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }).addOnFailureListener(this, new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });
        } else {
            if (!isNameValid(editTextName.getText().toString()))
                textViewNameError.setTextColor(getResources().getColor(R.color.colorRedError, getTheme()));
            if (!isEmailValid(editTextEmail.getText().toString()))
                textViewEmailError.setTextColor(getResources().getColor(R.color.colorRedError, getTheme()));
            if (!isPasswordValid(editTextPassword.getText().toString()))
                textViewPasswordError.setTextColor(getResources().getColor(R.color.colorRedError, getTheme()));
            if (!isPasswordConfirmValid(editTextPassword.getText().toString(), editTextPasswordConfirm.getText().toString()))
                textViewPasswordConfirmError.setTextColor(getResources().getColor(R.color.colorRedError, getTheme()));
        }
    }
}
