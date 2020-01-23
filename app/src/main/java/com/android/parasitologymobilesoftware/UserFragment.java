package com.android.parasitologymobilesoftware;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.security.keystore.UserNotAuthenticatedException;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class UserFragment extends Fragment {

    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore database;

    private SharedPreferences prefs = null;

    private String schoolYear = "";

    private  AlertDialog.Builder builder;
    private View dialogView;

    private TextView dialogTitle;
    private EditText dialogChange;

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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_user, container, false);

        // Username field.
        ImageView imageViewIconName = rootView.findViewById(R.id.includeFieldUserInformationName).findViewById(R.id.imageViewUserInformationIcon);
        imageViewIconName.setImageResource(R.drawable.icons8_user_40);
        TextView textViewTitleName = rootView.findViewById(R.id.includeFieldUserInformationName).findViewById(R.id.textViewUserInformationTitle);
        textViewTitleName.setText(firebaseAuth.getCurrentUser().getDisplayName());
        TextView textViewChangeName = rootView.findViewById(R.id.includeFieldUserInformationName).findViewById(R.id.textViewUserInformationChange);
        textViewChangeName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogTitle.setText("Alterar Nome");
                dialogChange.setText(firebaseAuth.getCurrentUser().getDisplayName());
                builder.setView(dialogView);
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });

        // Email field.
        ImageView imageViewIconEmail = rootView.findViewById(R.id.includeFieldUserInformationEmail).findViewById(R.id.imageViewUserInformationIcon);
        imageViewIconEmail.setImageResource(R.drawable.icons8_mail_40);
        TextView textViewTitleEmail = rootView.findViewById(R.id.includeFieldUserInformationEmail).findViewById(R.id.textViewUserInformationTitle);
        textViewTitleEmail.setText(firebaseAuth.getCurrentUser().getEmail());

        // Password field.
        ImageView imageViewIconPassword = rootView.findViewById(R.id.includeFieldUserInformationPassword).findViewById(R.id.imageViewUserInformationIcon);
        imageViewIconPassword.setImageResource(R.drawable.icons8_password_);
        TextView textViewTitlePassword = rootView.findViewById(R.id.includeFieldUserInformationPassword).findViewById(R.id.textViewUserInformationTitle);
        StringBuilder length = new StringBuilder();
        for(int i = 0; i < prefs.getInt("passwordLength", 8); i++) length.append("*");
        textViewTitlePassword.setText(length.toString());

        // School Year field.
        ImageView imageViewIconSchoolYear = rootView.findViewById(R.id.includeFieldUserInformationYear).findViewById(R.id.imageViewUserInformationIcon);
        imageViewIconSchoolYear.setImageResource(R.drawable.icons8_graduation_hat_100);
        TextView textViewTitleSchoolYear = rootView.findViewById(R.id.includeFieldUserInformationYear).findViewById(R.id.textViewUserInformationTitle);
        textViewTitleSchoolYear.setText(schoolYear);


        // Inflate the layout for this fragment
        return rootView;
    }
}
