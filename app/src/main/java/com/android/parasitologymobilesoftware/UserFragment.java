package com.android.parasitologymobilesoftware;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.nfc.Tag;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.security.keystore.UserNotAuthenticatedException;
import android.util.Log;
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
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.*;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;

public class UserFragment extends Fragment {

    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore dataBase;
    private FirebaseUser user;

    private Bundle data;
    private String schoolGrade;
    private String fdp;
    private String email;
    private String completeName;
    private String oldPassword;
    private String newPassword;
    private String newPasswordAuth;

    private AlertDialog.Builder builder, builderConfirmChangeEmail, builderConfirmChangePassword;
    private View dialogView, dialogViewPassword, dialogConfirmChangeEmail, dialogConfirmChangePassword;
    private EditText dialogChangePassword, dialogChangeNewPassword, dialogChangeConfirmNewPassword;

    private SharedPreferences preferences;

    private TextView dialogTitle;
    private EditText dialogChange, dialogChange2;

    private static final String TAG = "UserFragment";


    TextView textViewTitleEmail;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dataBase = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();


        if (getArguments() != null) {
        }

        LayoutInflater inflater = requireActivity().getLayoutInflater();
        dialogView = inflater.inflate(R.layout.dialog_change_information, null);
        dialogTitle = dialogView.findViewById(R.id.textViewDialogTitle);
        dialogChange = dialogView.findViewById(R.id.editTextDialogChange);
        dialogChange2 = dialogView.findViewById(R.id.editTextDialogChange2);

        dialogViewPassword = inflater.inflate(R.layout.dialog_change_password, null);
        dialogChangePassword = dialogViewPassword.findViewById(R.id.editTextDialogChangePassword1);
        dialogChangeNewPassword = dialogViewPassword.findViewById(R.id.editTextDialogChangePassword2);
        dialogChangeConfirmNewPassword = dialogViewPassword.findViewById(R.id.editTextDialogChangePassword3);


        dialogConfirmChangePassword = inflater.inflate(R.layout.dialog_change_confirm_password_change, null);
        builderConfirmChangePassword = new AlertDialog.Builder(getContext());
        builderConfirmChangePassword.setView(dialogConfirmChangePassword);

        dialogConfirmChangeEmail = inflater.inflate(R.layout.dialog_change_confirm_email_change, null);
        builderConfirmChangeEmail = new AlertDialog.Builder(getContext());
        builderConfirmChangeEmail.setView(dialogConfirmChangeEmail);

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
        /* Alert Dialog To Change Email */
        builder = new AlertDialog.Builder(getContext());
        builder.setView(dialogView);
        final AlertDialog alertDialogConfirmEmail = builderConfirmChangeEmail.create();
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
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    FirebaseAuth.getInstance().getCurrentUser().updateEmail(dialogChange.getText().toString())
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {

                                                    //Coping a document and send it's data to another document (which name is the new email) - FireStore does not allows us to change the document's name!
                                                    DocumentReference oldDocument = dataBase.collection("generalUserInfo").document(email);
                                                    DocumentReference newDocument = dataBase.collection("generalUserInfo").document(firebaseAuth.getCurrentUser().getEmail());
                                                    moveFirestoreDocument(oldDocument, newDocument);

                                                    // Updating the reporting database
                                                    Map<String, Object> newEmail = new HashMap<>();
                                                    newEmail.put("email", firebaseAuth.getCurrentUser().getEmail());
                                                    dataBase.collection(schoolGrade).document(completeName)
                                                            .set(newEmail, SetOptions.merge());

                                                    textViewTitleEmail.setText(firebaseAuth.getCurrentUser().getEmail());
                                                    setEmail(firebaseAuth.getCurrentUser().getEmail());
                                                    alertDialogConfirmEmail.show();
                                                }
                                            }).addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Toast.makeText(getContext(),"O E-mail inserido já está em uso.", Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            dialogChange.clearComposingText();
                            Toast.makeText(getContext(), "Algo deu errado. Certifique-se de que a senha inserida está correta.", Toast.LENGTH_SHORT).show();
                        }
                    });

                } else {
                    Toast.makeText(getContext(), "Insira um e-mail válido.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        final AlertDialog alertDialog = builder.create();
        textViewChangeEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {    //TODO: solve bug at the second time the user opens this click listener - an option is taking this method out of the onCreateView
                alertDialog.show();
            }
        });

        // Password field.
        ImageView imageViewIconPassword = rootView.findViewById(R.id.includeFieldUserInformationPassword).findViewById(R.id.imageViewUserInformationIcon);
        imageViewIconPassword.setImageResource(R.drawable.icons8_password_);
        TextView textViewTitlePassword = rootView.findViewById(R.id.includeFieldUserInformationPassword).findViewById(R.id.textViewUserInformationTitle);
        StringBuilder length = new StringBuilder();
        for (int i = 0; i < 8; i++) length.append("*");
        textViewTitlePassword.setText(length.toString());
        final TextView textViewChangePassword = rootView.findViewById(R.id.includeFieldUserInformationPassword).findViewById(R.id.textViewUserInformationChange);
        /* Alert Dialog To Change Password */
        builder = new AlertDialog.Builder(getContext());
        builder.setView(dialogViewPassword);
        final AlertDialog alertDialogConfirmPassword = builderConfirmChangePassword.create();
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        }).setPositiveButton("Alterar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                oldPassword = dialogChangePassword.getText().toString();
                newPassword = dialogChangeNewPassword.getText().toString();
                newPasswordAuth = dialogChangeConfirmNewPassword.getText().toString();
                if (newPassword.length() < 6) {
                    Toast.makeText(getContext(), "A nova senha deve possuir ao menos 6 caracteres", Toast.LENGTH_SHORT).show();
                } else if (!newPasswordAuth.equals(newPassword)) {
                    Toast.makeText(getContext(), "Os campos 'Nova Senha' e 'Confirmar Nova Senha' estão diferentes", Toast.LENGTH_SHORT).show();
                } else {
                    user = firebaseAuth.getCurrentUser();
                    AuthCredential credential = EmailAuthProvider.getCredential(email, oldPassword);
                    user.reauthenticate(credential).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            user.updatePassword(newPassword);
                            alertDialogConfirmPassword.show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getContext(), "A senha inserida está incorreta", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
        final AlertDialog alertDialogPassword = builder.create();
        textViewChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialogPassword.show();
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
        });
        TextView textViewChangeSchoolYear = rootView.findViewById(R.id.includeFieldUserInformationYear).findViewById(R.id.textViewUserInformationChange);
        textViewChangeSchoolYear.setTextColor(Color.TRANSPARENT);


        // Inflate the layout for this fragment
        return rootView;
    }

    public void moveFirestoreDocument(final DocumentReference fromPath, final DocumentReference toPath) {
        fromPath.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot documentSnapshot = task.getResult();
                    toPath.set(documentSnapshot.getData())
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    fromPath.delete();
                                }
                            });
                }
            }
        });

    }
    public void setEmail(String email){
        this.email = email;
    }
}
