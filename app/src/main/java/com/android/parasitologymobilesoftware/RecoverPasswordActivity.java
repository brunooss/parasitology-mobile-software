package com.android.parasitologymobilesoftware;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.transition.Slide;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class RecoverPasswordActivity extends Activity{

    SignupActivity signupActivity = new SignupActivity();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

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
                                //email sent
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
}
