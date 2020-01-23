package com.android.parasitologymobilesoftware;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.security.keystore.UserNotAuthenticatedException;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.*;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class UserFragment extends Fragment {

    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore database;

    private SharedPreferences prefs = null;

    private String schoolYear = "";

    private  AlertDialog.Builder builder;
    private View dialogView;

    private TextView dialogTitle;
    private EditText dialogChange, dialogChange2;

    TextView textViewTitleEmail;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        firebaseAuth = FirebaseAuth.getInstance();
        if (getArguments() != null) {

        }
        database = FirebaseFirestore.getInstance();
        prefs = getContext().getSharedPreferences("com.android.parasitologymobilesoftware", Context.MODE_PRIVATE);
        schoolYear = prefs.getString("school grade", "string");

        builder = new AlertDialog.Builder(getContext());

        LayoutInflater inflater = requireActivity().getLayoutInflater();
        dialogView = inflater.inflate(R.layout.dialog_change_information, null);
        dialogTitle = dialogView.findViewById(R.id.textViewDialogTitle);
        dialogChange = dialogView.findViewById(R.id.editTextDialogChange);
        dialogChange2 = dialogView.findViewById(R.id.editTextDialogChange2);
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_user, container, false);

        // Username field.
        ImageView imageViewIconName = rootView.findViewById(R.id.includeFieldUserInformationName).findViewById(R.id.imageViewUserInformationIcon);
        imageViewIconName.setImageResource(R.drawable.icons8_user_40);
        TextView textViewTitleName = rootView.findViewById(R.id.includeFieldUserInformationName).findViewById(R.id.textViewUserInformationTitle);
        textViewTitleName.setText(firebaseAuth.getCurrentUser().getDisplayName());
        TextView textViewChangeName = rootView.findViewById(R.id.includeFieldUserInformationName).findViewById(R.id.textViewUserInformationChange);
        textViewChangeName.setTextColor(Color.TRANSPARENT);

        // Email field.
        ImageView imageViewIconEmail = rootView.findViewById(R.id.includeFieldUserInformationEmail).findViewById(R.id.imageViewUserInformationIcon);
        imageViewIconEmail.setImageResource(R.drawable.icons8_mail_40);
        textViewTitleEmail = rootView.findViewById(R.id.includeFieldUserInformationEmail).findViewById(R.id.textViewUserInformationTitle);
        textViewTitleEmail.setText(firebaseAuth.getCurrentUser().getEmail());
        final TextView textViewChangeEmail = rootView.findViewById(R.id.includeFieldUserInformationEmail).findViewById(R.id.textViewUserInformationChange);
        textViewChangeEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogTitle.setText("Alterar Email");
                builder.setView(dialogView);
                builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                }).setPositiveButton("Alterar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (new SignupActivity().isEmailValid(dialogChange.getText().toString())) {
                            AuthCredential credential = EmailAuthProvider
                                    .getCredential(firebaseAuth.getCurrentUser().getEmail(), dialogChange2.getText().toString()); // Current Login Credentials \\
                            // Prompt the user to re-provide their sign-in credentials
                            firebaseAuth.getCurrentUser().reauthenticate(credential)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            FirebaseAuth.getInstance().getCurrentUser().updateEmail(dialogChange.getText().toString());
                                            textViewChangeEmail.setText(FirebaseAuth.getInstance().getCurrentUser().getEmail());
                                        }
                                    });
                        }
                        else Toast.makeText(getContext(), "Insira um email válido.", Toast.LENGTH_SHORT).show();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });

        // Password field.
        ImageView imageViewIconPassword = rootView.findViewById(R.id.includeFieldUserInformationPassword).findViewById(R.id.imageViewUserInformationIcon);
        imageViewIconPassword.setImageResource(R.drawable.icons8_password_);
        TextView textViewTitlePassword = rootView.findViewById(R.id.includeFieldUserInformationPassword).findViewById(R.id.textViewUserInformationTitle);
        StringBuilder length = new StringBuilder();
        for(int i = 0; i < prefs.getInt("passwordLength", 8); i++) length.append("*");
        textViewTitlePassword.setText(length.toString());
        final TextView textViewChangePassword = rootView.findViewById(R.id.includeFieldUserInformationPassword).findViewById(R.id.textViewUserInformationChange);
        textViewChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), RecoverPasswordActivity.class));
            }
        });

        // School Year field.
        ImageView imageViewIconSchoolYear = rootView.findViewById(R.id.includeFieldUserInformationYear).findViewById(R.id.imageViewUserInformationIcon);
        imageViewIconSchoolYear.setImageResource(R.drawable.icons8_graduation_hat_100);
        TextView textViewTitleSchoolYear = rootView.findViewById(R.id.includeFieldUserInformationYear).findViewById(R.id.textViewUserInformationTitle);
        textViewTitleSchoolYear.setText(schoolYear);
        TextView textViewChangeSchoolYear = rootView.findViewById(R.id.includeFieldUserInformationYear).findViewById(R.id.textViewUserInformationChange);
        textViewChangeSchoolYear.setTextColor(Color.TRANSPARENT);


        // Inflate the layout for this fragment
        return rootView;
    }
}
