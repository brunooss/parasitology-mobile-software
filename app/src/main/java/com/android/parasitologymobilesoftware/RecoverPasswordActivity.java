package com.android.parasitologymobilesoftware;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;


public class RecoverPasswordActivity extends AppCompatActivity {

    SignupActivity signupActivity = new SignupActivity();

    private TextView textViewErrorButton; // Error message
    private ProgressBar progressBar;      // Progress Bar


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_recoverpassword);

        /* Instantiating components */
        textViewErrorButton = findViewById(R.id.textViewRecoverPasswordErrorButton);
        progressBar = findViewById(R.id.progressBarRecoverPassword);
        progressBar.setVisibility(View.INVISIBLE);
    }

    public void onButtonSendEmail(View view){
        progressBar.setVisibility(View.VISIBLE);                          // Loading...
        FirebaseAuth auth = FirebaseAuth.getInstance();                  // Instantiating Firebase
        EditText editTextEmail = findViewById(R.id.SendEmailToRecover); // Taking the email that the User inserted
        String emailAdress = editTextEmail.getText().toString();       // Taking the email String

        if(signupActivity.isEmailValid(emailAdress)){                // Checks whether the user har entered a valid email
            auth.sendPasswordResetEmail(emailAdress)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                progressBar.setVisibility(View.INVISIBLE);    // Already loaded
                                textViewErrorButton.setTextColor(Color.TRANSPARENT);
                            } else {                                         // An error has occurred while sending the email
                                progressBar.setVisibility(View.INVISIBLE);  // Already loaded
                                textViewErrorButton.setText("Algo deu errado. Certifique-se de que o email utilizado está cadastrado.");
                                textViewErrorButton.setTextColor(getResources().getColor(R.color.colorRedError, getTheme()));  //Error Message
                            }
                        }
                    });

        } else {                                                      // User hasnt entered a valid email
            progressBar.setVisibility(View.INVISIBLE);
            textViewErrorButton.setText("E-mail inválido");
            textViewErrorButton.setTextColor(getResources().getColor(R.color.colorRedError, getTheme()));
        }
    }

    public void onButtonCloseWindow(View view){
        finish();                                             // Easier way to go back to previous activity.
    }
}
