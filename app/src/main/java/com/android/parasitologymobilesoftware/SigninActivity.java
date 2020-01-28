package com.android.parasitologymobilesoftware;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.transition.Slide;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;

import android.os.Bundle;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SigninActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    private ProgressBar progressBar;

    private EditText editTextEmail, editTextPassword;

    private TextView textViewInvalidEmail, textViewEmailNonexistent, textViewWrongPassword;  // Error messages


    public boolean isPasswordValid(String password) { //Validates the password, returning true if it's valid or false if it's not.
        return password.length() > 5; // Returns true only if the password length is longer than 7 chars.
    }

    public boolean isEmailValid(String email) { // Validates the email input, returning true if it's valid or false if it's not.
        String regexEmail = "^\\w*(\\.\\w*)?@\\w*\\.[a-z]+(\\.[a-z]+)?$";

        Pattern r = Pattern.compile(regexEmail);
        Matcher m = r.matcher(email);

        return m.find();
    }

    public void openDialog(){
        SigninDialog signinDialog = new SigninDialog();
        signinDialog.show(getSupportFragmentManager(), "errorMessage");
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_signin);

        progressBar = findViewById(R.id.progressBarSignIn);
        progressBar.setVisibility(View.INVISIBLE);

        textViewInvalidEmail = findViewById(R.id.textViewSignInInvalidEmail);
        textViewEmailNonexistent = findViewById(R.id.textViewSignInEmailNonexistent);
        textViewWrongPassword = findViewById(R.id.textViewSignInWrongPassword);

        editTextEmail = findViewById(R.id.editTextSignInEmail);
        editTextPassword = findViewById(R.id.editTextSignInPassword);

        editTextEmail.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b) { // If is not focused
                    if (isEmailValid(editTextEmail.getText().toString())) {
                        textViewInvalidEmail.setTextColor(Color.TRANSPARENT);
                        textViewEmailNonexistent.setTextColor(Color.TRANSPARENT);
                    } else {
                        textViewInvalidEmail.setTextColor(getResources().getColor(R.color.colorRedError, getTheme()));
                        textViewEmailNonexistent.setTextColor(getResources().getColor(R.color.colorRedError, getTheme()));
                    }
                }
            }
        });
        editTextPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b) // If is not focused
                    textViewWrongPassword.setTextColor(Color.TRANSPARENT);
                else
                    textViewWrongPassword.setTextColor(getResources().getColor(R.color.colorRedError, getTheme()));
            }
        });
    }

        public void onButtonLogInClick (View view){
            progressBar.setVisibility(View.VISIBLE);
            EditText editTextEmail = findViewById(R.id.editTextSignInEmail);
            EditText editTextPassword = findViewById(R.id.editTextSignInPassword);
            if (isEmailValid(editTextEmail.getText().toString()) && isPasswordValid(editTextPassword.getText().toString())) {   // if Email's string matches with regexEmail
                firebaseAuth.signInWithEmailAndPassword(editTextEmail.getText().toString(), editTextPassword.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    progressBar.setVisibility(View.INVISIBLE);
                                    Toast.makeText(getBaseContext(), "Sucesso! Logando com sua conta...", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(getBaseContext(), HomeActivity.class));
                                    finish();
                                } else {
                                    try {
                                        throw task.getException();
                                    }
                                    catch (FirebaseAuthInvalidUserException invalidEmail){
                                      Log.d(TAG, "Invalid Email");
                                      textViewEmailNonexistent.setVisibility(View.VISIBLE);
                                    }
                                    catch (FirebaseAuthInvalidCredentialsException wrongPassword){
                                        Log.d(TAG, "Wrong Password");
                                        textViewWrongPassword.setVisibility(View.VISIBLE);
                                    } catch (Exception e) {

                                    }
                                    openDialog();
                                    progressBar.setVisibility(View.INVISIBLE);
                                }
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                            openDialog();
                            Toast.makeText(getBaseContext(), "Falhou... Essa conta não existe.", Toast.LENGTH_SHORT).show();
                    }
                });
            } else {      //Invalid email's string and password
                //TODO Set error red messages on specific cases
                openDialog();
                progressBar.setVisibility(View.INVISIBLE);
            }
        }

        public void onButtonLogInRegisterClick (View view){
            Intent intent = new Intent(this, SignupActivity.class);
            startActivity(intent);
        }

        public void onButtonRecoverPassword (View view){
            Intent intent = new Intent(this, RecoverPasswordActivity.class);
            startActivity(intent);
        }
}
