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
                boolean nameValidation = false;  String firstName;
                boolean emailValidation = false;

    /*methods*/
        /*name*/
    public boolean isNameValid(String name) {
        String regexFirstName = "^(?:[\\p{Lu}&&[\\p{IsLatin}]])(?:(?:')?(?:[\\p{Ll}&&[\\p{IsLatin}]]))+(?:\\-(?:[\\p{Lu}&&[\\p{IsLatin}]])(?:(?:')?(?:[\\p{Ll}&&[\\p{IsLatin}]]))+)*(?: (?:(?:e|y|de(?:(?: la| las| lo| los))?|do|dos|da|das|del|van|von|bin|le) )?(?:(?:(?:d'|D'|O'|Mc|Mac|al\\-))?(?:[\\p{Lu}&&[\\p{IsLatin}]])(?:(?:')?(?:[\\p{Ll}&&[\\p{IsLatin}]]))+|(?:[\\p{Lu}&&[\\p{IsLatin}]])(?:(?:')?(?:[\\p{Ll}&&[\\p{IsLatin}]]))+(?:\\-(?:[\\p{Lu}&&[\\p{IsLatin}]])(?:(?:')?(?:[\\p{Ll}&&[\\p{IsLatin}]]))+)*))+(?: (?:Jr\\.|II|III|IV))?$"; //essa regex pega todos os caracteres até chegar no espaço

        Pattern r = Pattern.compile(regexFirstName);
        Matcher m = r.matcher(name);

        if (m.find()) {
            nameValidation = true;
        }
        return nameValidation;
    } //valida o nome inserido, retornando true se válido e false se inválido
    public String getFirstName(String name){
        if (isNameValid()) {
            String regexFirstName = "\\S+";

            Pattern p = Pattern.compile(regexFirstName);
            Matcher m = p.matcher(name);

            m.find();
            firstName = m.group(0);
            return m.group(0);
        }
    } //só funciona se o nome for válido; retorna o primeiro nome.
        /*email*/
    public boolean isEmailValid(String email){
        String regexEmail = "^\\w*(\\.\\w*)?@\\w*\\.[a-z]+(\\.[a-z]+)?$";

        Pattern r = Pattern.compile(regexEmail);
        Matcher m = r.matcher(email);

        if(m.find()){
            emailValidation = true;
        }
        return emailValidation;
    } //valida o e-mail, retornando true se válido e false se inválido

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



