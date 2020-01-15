package com.android.parasitologymobilesoftware;

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

public class SignupActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        firebaseAuth = FirebaseAuth.getInstance();

    }

    public void onButtonSignInClick(View view) {
        final EditText editTextName = findViewById(R.id.editTextSignUpName);
        EditText editTextEmail = findViewById(R.id.editTextSignUpEmail);
        EditText editTextPassword = findViewById(R.id.editTextSignUpPassword);

        firebaseAuth.createUserWithEmailAndPassword(editTextEmail.getText().toString(), editTextPassword.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        final FirebaseUser user = firebaseAuth.getCurrentUser();

                        UserProfileChangeRequest profileChangeRequest = new UserProfileChangeRequest
                                .Builder()
                                .setDisplayName(editTextName.getText().toString())
                                .build();
                        user.updateProfile(profileChangeRequest);

                        Toast.makeText(getBaseContext(), "Name: " +
                                user.getDisplayName() + ", Email: " +
                                user.getEmail(), Toast.LENGTH_SHORT).show();

                    }
                });
    }
}
