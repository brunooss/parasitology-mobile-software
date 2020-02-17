package com.android.parasitologymobilesoftware;

import android.app.AlertDialog;
import android.app.LauncherActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
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

    private AlertDialog.Builder builder;
    private View dialogView;
    private TextView dialogInfo;
    private String inicio = "Enviamos um e-mail para ";
    private String fim = ". Caso não tenha recebido, verifique sua caixa de spam ou tente novamente.";




    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_recoverpassword);


        final Toolbar toolbar = findViewById(R.id.toolbarRecoverPassword);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close_close_24dp);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        /* Instantiating components */
        textViewErrorButton = findViewById(R.id.textViewRecoverPasswordErrorButton);
        progressBar = findViewById(R.id.progressBarRecoverPassword);
        progressBar.setVisibility(View.INVISIBLE);
        buttonSendEmail = findViewById(R.id.buttonSendEmail);

        builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        dialogView = inflater.inflate(R.layout.dialog_recover_password, null);
        dialogInfo = dialogView.findViewById(R.id.TextViewDialogInfo);
    }

    public void onButtonSendEmail(View view){
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();

        progressBar.setVisibility(View.VISIBLE);                          // Loading...
        FirebaseAuth auth = FirebaseAuth.getInstance();                  // Instantiating Firebase
        EditText editTextEmail = findViewById(R.id.SendEmailToRecover); // Taking the email that the User inserted
        final String emailAdress = editTextEmail.getText().toString();       // Taking the email String
        if(signupActivity.isEmailValid(emailAdress)){                // Checks whether the user har entered a valid email
            auth.sendPasswordResetEmail(emailAdress)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                progressBar.setVisibility(View.INVISIBLE);             // Already loaded
                                textViewErrorButton.setTextColor(Color.TRANSPARENT);

                                dialogInfo.setText(inicio.concat(emailAdress.concat(fim)));
                                builder.setView(dialogView);
                                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        Intent intent = new Intent(getBaseContext(), SigninActivity.class);
                                        startActivity(intent);
                                    }
                                });
                                AlertDialog alertDialog = builder.create();
                                alertDialog.show();

                            } else {                                         // An error has occurred while sending the email
                                progressBar.setVisibility(View.INVISIBLE);  // Already loaded
                                textViewErrorButton.setText("O email utilizado não está cadastrado.");
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
}
