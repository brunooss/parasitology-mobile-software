package com.android.parasitologymobilesoftware;

import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
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
    EditText textName;
    EditText textEmail;
    EditText textPassword;
    boolean nameValidation = false;
    String firstName;
    boolean emailValidation = false;

    public String username;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        firebaseAuth = FirebaseAuth.getInstance();

        textName = findViewById(R.id.editTextSignUpName);
        textEmail = findViewById(R.id.editTextSignUpEmail);
        textPassword = findViewById(R.id.editTextSignUpPassword);

        textName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(isNameValid(charSequence.toString())) {
                    Toast.makeText(getBaseContext(), "Valid Name!!!", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getBaseContext(), "Invalid Name...", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        textEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(isEmailValid(charSequence.toString())) {
                    Toast.makeText(getBaseContext(), "Valid Email!!!", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getBaseContext(), "Invalid Email...", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        Spinner spinner = findViewById(R.id.spinnerSignUpYear);

        ArrayAdapter<CharSequence> stringArrayAdapter = ArrayAdapter.createFromResource(this, R.array.years_array, R.layout.simple_spinner_item);
        stringArrayAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(stringArrayAdapter);
    }

    /* Methods */
    public boolean isNameValid(String name) { // Validates the name input, returning true if it's valid or false if it's not.

        String regexName = "^(?:[\\\\p{Lu}&&[\\\\p{IsLatin}]])(?:(?:')?(?:[\\\\p{Ll}&&[\\\\p{IsLatin}]]))+(?:\\\\-(?:[\\\\p{Lu}&&[\\\\p{IsLatin}]])(?:(?:')?(?:[\\\\p{Ll}&&[\\\\p{IsLatin}]]))+)*(?: (?:(?:e|y|de(?:(?: la| las| lo| los))?|do|dos|da|das|del|van|von|bin|le) )?(?:(?:(?:d'|D'|O'|Mc|Mac|al\\\\-))?(?:[\\\\p{Lu}&&[\\\\p{IsLatin}]])(?:(?:')?(?:[\\\\p{Ll}&&[\\\\p{IsLatin}]]))+|(?:[\\\\p{Lu}&&[\\\\p{IsLatin}]])(?:(?:')?(?:[\\\\p{Ll}&&[\\\\p{IsLatin}]]))+(?:\\\\-(?:[\\\\p{Lu}&&[\\\\p{IsLatin}]])(?:(?:')?(?:[\\\\p{Ll}&&[\\\\p{IsLatin}]]))+)*))+(?: (?:Jr\\\\.|II|III|IV))?$"; //essa regex pega todos os caracteres até chegar no espaço

        Pattern pattern = Pattern.compile(regexName);
        Matcher matcher = pattern.matcher(name);

        if (matcher.find()) {
            nameValidation = true;
        }
        return nameValidation;
    }
    public String getFirstName(String name) { // If name is valid, returns the first name.
        if (isNameValid(name)) {
            String regexName = "\\S+";

            Pattern pattern = Pattern.compile(regexName);
            Matcher matcher = pattern.matcher(name);

            //matcher.find();
            firstName = matcher.group(0);
            return matcher.group(0);
        }
        return null;
    }
    public boolean isEmailValid(String email) { // Validates the email input, returning true if it's valid or false if it's not.

        if(email != null) {
            String regexEmail = "^\\w*(\\.\\w*)?@\\w*\\.[a-z]+(\\.[a-z]+)?$";

            Pattern r = Pattern.compile(regexEmail);
            Matcher m = r.matcher(email);

            if(m.find()){
                emailValidation = true;
            }
            return emailValidation;
        }
        return false;
    }

    public void onButtonSignUpClick(View view) {
        if(isEmailValid(textEmail.getText().toString()))
            Toast.makeText(this, "Email valid!", Toast.LENGTH_SHORT).show();
        firebaseAuth.createUserWithEmailAndPassword(textEmail.getText().toString(), textPassword.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        final FirebaseUser user = firebaseAuth.getCurrentUser();

                        UserProfileChangeRequest profileChangeRequest = new UserProfileChangeRequest
                                .Builder()
                                .setDisplayName(textName.getText().toString())
                                .build();
                        user.updateProfile(profileChangeRequest);

                        username = textName.getText().toString();

                        Intent intent = new Intent(getBaseContext(), IntroductionActivity.class);
                        startActivity(intent);
                        finish();

                    }
                });
    }
}



