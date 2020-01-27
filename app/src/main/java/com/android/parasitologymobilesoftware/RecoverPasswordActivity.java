package com.android.parasitologymobilesoftware;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;


public class RecoverPasswordActivity extends AppCompatActivity {

    SignupActivity signupActivity = new SignupActivity();

    private TextView textViewErrorButton; // Error message
    private ProgressBar progressBar;     // Progress Bar
    private Button buttonSendEmail;     // Button above the frame_container, preventing the fragment from overlapping

    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_recoverpassword);


        /* Instantiating components */
        textViewErrorButton = findViewById(R.id.textViewRecoverPasswordErrorButton);
        progressBar = findViewById(R.id.progressBarRecoverPassword);
        progressBar.setVisibility(View.INVISIBLE);
        buttonSendEmail = findViewById(R.id.buttonSendEmail);
    }

    public void onButtonSendEmail(View view){
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();

        progressBar.setVisibility(View.VISIBLE);                          // Loading...
        FirebaseAuth auth = FirebaseAuth.getInstance();                  // Instantiating Firebase
        EditText editTextEmail = findViewById(R.id.SendEmailToRecover); // Taking the email that the User inserted
        String emailAdress = editTextEmail.getText().toString();       // Taking the email String

        RecoverPasswordDialog recoverPasswordDialog = new RecoverPasswordDialog();
        recoverPasswordDialog.setTextEmailSent(emailAdress);

        if(signupActivity.isEmailValid(emailAdress)){                // Checks whether the user har entered a valid email
            auth.sendPasswordResetEmail(emailAdress)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                progressBar.setVisibility(View.INVISIBLE);         // Already loaded
                                textViewErrorButton.setTextColor(Color.TRANSPARENT);
                                openDialog();
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

    public void sendDataToFragment(String message, Fragment fragment){   // Function that passes the email's string to the dialog fragment
        Bundle bundle = new Bundle();
        bundle.putString("emailAdress", message);
        fragment.setArguments(bundle);
    }

    public void openDialog(){
        RecoverPasswordDialog recoverPasswordDialog = new RecoverPasswordDialog();
        recoverPasswordDialog.show(getSupportFragmentManager(), "emailSentMessage");
    }

    public void onButtonCloseWindow(View view){
        finish();                                             // Easier way to go back to previous activity.
    }
}
