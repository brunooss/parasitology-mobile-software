package com.android.parasitologymobilesoftware;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.*;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class UserFragment extends Fragment {

    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore dataBase;

    private Bundle data;
    private String schoolGrade;
    private String fdp;
    private String email;
    private String completeName;

    private AlertDialog.Builder builder;
    private View dialogView;

    private SharedPreferences preferences;

    private TextView dialogTitle;
    private EditText dialogChange, dialogChange2;

    TextView textViewTitleEmail;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dataBase = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();


        if (getArguments() != null) { }


        builder = new AlertDialog.Builder(getContext());

        LayoutInflater inflater = requireActivity().getLayoutInflater();
        dialogView = inflater.inflate(R.layout.dialog_change_information, null);
        dialogTitle = dialogView.findViewById(R.id.textViewDialogTitle);
        dialogChange = dialogView.findViewById(R.id.editTextDialogChange);
        dialogChange2 = dialogView.findViewById(R.id.editTextDialogChange2);


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_user, container, false);

        email = firebaseAuth.getCurrentUser().getEmail();
        completeName = firebaseAuth.getCurrentUser().getDisplayName();


        preferences = this.getActivity().getSharedPreferences("prefs", 0);
        final SharedPreferences.Editor editor = preferences.edit();

        // Username field.
        ImageView imageViewIconName = rootView.findViewById(R.id.includeFieldUserInformationName).findViewById(R.id.imageViewUserInformationIcon);
        imageViewIconName.setImageResource(R.drawable.icons8_user_40);
        final TextView textViewTitleName = rootView.findViewById(R.id.includeFieldUserInformationName).findViewById(R.id.textViewUserInformationTitle);
        textViewTitleName.setText(completeName);
        TextView textViewChangeName = rootView.findViewById(R.id.includeFieldUserInformationName).findViewById(R.id.textViewUserInformationChange);
        textViewChangeName.setTextColor(Color.TRANSPARENT);

        // Email field.
        ImageView imageViewIconEmail = rootView.findViewById(R.id.includeFieldUserInformationEmail).findViewById(R.id.imageViewUserInformationIcon);
        imageViewIconEmail.setImageResource(R.drawable.icons8_mail_40);
        textViewTitleEmail = rootView.findViewById(R.id.includeFieldUserInformationEmail).findViewById(R.id.textViewUserInformationTitle);
        textViewTitleEmail.setTextSize(16);
        textViewTitleEmail.setText(email);   // it is a better option we use shared preferences to set the textView, because of the Internet connection issue.
        TextView textViewChangeEmail = rootView.findViewById(R.id.includeFieldUserInformationEmail).findViewById(R.id.textViewUserInformationChange);
        textViewChangeEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {    //TODO: solve bug at the second time the user opens this click listener - an option is taking this method out of the onCreateView
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
                                        }
                                    });
                            email = dialogChange.getText().toString();
                            editor.putString("email", email);
                            editor.apply();
                            textViewTitleEmail.setText(firebaseAuth.getCurrentUser().getEmail());
                        }
                        else
                            Toast.makeText(getContext(), "Insira um email válido.", Toast.LENGTH_SHORT).show();
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
        for(int i = 0; i < 8; i++) length.append("*");
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
        final TextView textViewTitleSchoolYear = rootView.findViewById(R.id.includeFieldUserInformationYear).findViewById(R.id.textViewUserInformationTitle);

        DocumentReference docRef = dataBase.collection("generalUserInfo").document(firebaseAuth.getCurrentUser().getEmail());
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {
                    schoolGrade = documentSnapshot.getString("school grade");
                    textViewTitleSchoolYear.setText(schoolGrade);
                }
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                    }
                });
        TextView textViewChangeSchoolYear = rootView.findViewById(R.id.includeFieldUserInformationYear).findViewById(R.id.textViewUserInformationChange);
        textViewChangeSchoolYear.setTextColor(Color.TRANSPARENT);


        // Inflate the layout for this fragment
        return rootView;
    }



}
