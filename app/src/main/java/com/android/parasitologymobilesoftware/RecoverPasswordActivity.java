package com.android.parasitologymobilesoftware;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;


public class RecoverPasswordActivity extends AppCompatActivity {

    SignupActivity signupActivity = new SignupActivity();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_recoverpassword);
    }
    public void onButtonSendEmail(View view){

        FirebaseAuth auth = FirebaseAuth.getInstance();                 // Instantiating Firebase
        EditText editTextEmail = findViewById(R.id.SendEmailToRecover); // Taking the email that the User inserted
        String emailAdress = editTextEmail.getText().toString();        // Taking the email String

        if(signupActivity.isEmailValid(emailAdress)){                   // Checks whether the user har entered a valid email
            auth.sendPasswordResetEmail(emailAdress)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){

                            } else {
                                //an error has occurred while sending the email (a possible google server problem)
                                //maybe we can send a message asking the user to try it again
                            }
                        }
                    });

        } else {                                                        // User hasnt entered a valid email
                // we must to send him a message that says he should insert a valid email
        }
    }
    public void onButtonCloseWindow(View view){
        finish(); // Easier way to go back to previous activity.
    }
}
