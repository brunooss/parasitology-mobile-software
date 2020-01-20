package com.android.parasitologymobilesoftware;

import android.view.View;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignupActivity extends AppCompatActivity {

    /* Variables */
    private FirebaseAuth firebaseAuth;

    EditText editTextName, editTextEmail, ediTextPassword;
    TextView textViewNameError, textViewEmailError, textViewPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        firebaseAuth = FirebaseAuth.getInstance();

        editTextName = findViewById(R.id.editTextSignUpName);
        textViewNameError = findViewById(R.id.textViewN);
        
        editTextEmail = findViewById(R.id.editTextSignUpEmail);
        ediTextPassword = findViewById(R.id.editTextSignUpPassword);

        editTextName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(!b) { // If is not focused
                    if(isNameValid(editTextName.getText().toString())) 
                        Toast.makeText(getBaseContext(), "Valid Name!!!", Toast.LENGTH_LONG).show();
                    else
                        Toast.makeText(getBaseContext(), "Invalid Name...", Toast.LENGTH_LONG).show();
                }
            }
        });
        editTextEmail.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(!b) { // If is not focused
                    if(isEmailValid(editTextName.getText().toString()))
                        Toast.makeText(getBaseContext(), "Valid Email!!!", Toast.LENGTH_LONG).show();
                    else 
                        Toast.makeText(getBaseContext(), "Invalid Email...", Toast.LENGTH_LONG).show();
                }
            }
        });

        Spinner spinner = findViewById(R.id.spinnerSignUpYear);
        ArrayAdapter<CharSequence> stringArrayAdapter = ArrayAdapter.createFromResource(
                this,
                R.array.years_array,
                R.layout.simple_spinner_item);
        stringArrayAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(stringArrayAdapter);
    }


    /* Methods */
    public boolean isNameValid(String name) { // Validates the name input, returning true if it's valid or false if it's not.
        String regexName = "\\d+"; // Capture only numbers.

        Pattern pattern = Pattern.compile(regexName);
        Matcher m = pattern.matcher(name);

        return !(name.startsWith(" ") || m.find()); // Returns true only if the name neither starts with a blank space nor has numbers.
    }
    public boolean isEmailValid(String email){ // Validates the email input, returning true if it's valid or false if it's not.
        String regexEmail = "^\\w*(\\.\\w*)?@\\w*\\.[a-z]+(\\.[a-z]+)?$";

        Pattern r = Pattern.compile(regexEmail);
        Matcher m = r.matcher(email);

        return m.find();
    }
    public String getFirstName(String name) { // If name is valid, returns the first name.
        if (isNameValid(name)) {
            String regexName = "\\S+"; //Capture only blank spaces.

            Pattern pattern = Pattern.compile(regexName);
            Matcher matcher = pattern.matcher(name);
            if(matcher.find()) return matcher.group(0); else return name; // Returns the name before the blank space or the entire name.
        }
        return null;
    }

    public void onButtonSignUpClick(View view) {
        if(isEmailValid(editTextEmail.getText().toString()))
            Toast.makeText(this, "Email valid!", Toast.LENGTH_SHORT).show();
        Toast.makeText(this, "Name valid! Name: " + getFirstName(editTextName.getText().toString()), Toast.LENGTH_LONG).show();
        firebaseAuth.createUserWithEmailAndPassword(editTextEmail.getText().toString(), ediTextPassword.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        final FirebaseUser user = firebaseAuth.getCurrentUser();

                        UserProfileChangeRequest profileChangeRequest = new UserProfileChangeRequest
                                .Builder()
                                .setDisplayName(editTextName.getText().toString())
                                .build();
                        user.updateProfile(profileChangeRequest);

                        // Toast.makeText(getBaseContext(), "Name: " +user.getDisplayName() + ", Email: " + user.getEmail(), Toast.LENGTH_SHORT).show();

                    }
                });
    }
}
