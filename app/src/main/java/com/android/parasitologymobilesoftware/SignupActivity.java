package com.android.parasitologymobilesoftware;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;
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

    /*variables*/

        /*firebase*/
            private FirebaseAuth firebaseAuth;

        /*signup*/
            EditText textName = findViewById(R.id.editTextSignUpName);
            EditText textEmail = findViewById(R.id.editTextSignUpEmail);
            EditText textPassword = findViewById(R.id.editTextSignUpPassword);
            /*business rulles*/
                boolean nameValidation = false;

    /*methods*/
        /*name - para validar o nome, chame a função e depois veja se a variável nameValidation é verdadeira ou falsa*/
    public String getFirstName(String name){
        String caracteresExcetoEspaco = "\\S+";

        Pattern r = Pattern.compile(caracteresExcetoEspaco);
        Matcher m = r.matcher(name);

        if(m.find()){
            int tam = m.group(0).length();
            if(name.length() - tam >= 4) {
                if(!name.substring(tam+2, tam+3).equalsIgnoreCase(" "))
                    nameValidation = true;
            }
        }return(m.group(0));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        firebaseAuth = FirebaseAuth.getInstance();
    }

    public void onButtonSignUpClick(View view) {
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

                        Toast.makeText(getBaseContext(), "Name: " +
                                user.getDisplayName() + ", Email: " +
                                user.getEmail(), Toast.LENGTH_SHORT).show();

                    }
                });
    }

    }



