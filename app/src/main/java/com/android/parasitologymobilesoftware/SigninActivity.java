package com.android.parasitologymobilesoftware;

import android.content.Intent;
import android.os.Build;
import android.transition.Slide;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;

import android.os.Bundle;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SigninActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_signin);
    }

    public void onButtonLogInClick(View view) {
        EditText editTextEmail = findViewById(R.id.editTextSignInEmail);
        EditText editTextPassword = findViewById(R.id.editTextSignInPassword);
        firebaseAuth.signInWithEmailAndPassword(editTextEmail.getText().toString(), editTextPassword.getText().toString())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Toast.makeText(getBaseContext(), "Sucesso! Logando com sua conta...", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getBaseContext(), "Falhou... Essa conta não existe.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void onButtonLogInRegisterClick(View view) {
        Intent intent = new Intent(this, SignupActivity.class);
        startActivity(intent);
    }

    public void onButtonRecoverPassword(View view){
        Intent intent = new Intent(this, RecoverPasswordActivity.class);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Slide moveScreen = new Slide();
            moveScreen.setDuration(500);

            getWindow().setExitTransition(moveScreen);

            ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(this, null);

            startActivity(intent, options.toBundle());
        } else {
            startActivity(intent);
        }
    }
}
