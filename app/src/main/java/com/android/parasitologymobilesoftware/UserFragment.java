package com.android.parasitologymobilesoftware;

import android.os.Bundle;
import android.security.keystore.UserNotAuthenticatedException;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.firebase.auth.FirebaseAuth;

public class UserFragment extends Fragment {

    private FirebaseAuth firebaseAuth;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        firebaseAuth = FirebaseAuth.getInstance();
        if (getArguments() != null) {

        }
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

        // Email field.
        ImageView imageViewIconEmail = rootView.findViewById(R.id.includeFieldUserInformationEmail).findViewById(R.id.imageViewUserInformationIcon);
        imageViewIconEmail.setImageResource(R.drawable.icons8_mail_40);
        TextView textViewTitleEmail = rootView.findViewById(R.id.includeFieldUserInformationEmail).findViewById(R.id.textViewUserInformationTitle);
        textViewTitleEmail.setText(firebaseAuth.getCurrentUser().getEmail());

        // Password field.
        ImageView imageViewIconPassword = rootView.findViewById(R.id.includeFieldUserInformationPassword).findViewById(R.id.imageViewUserInformationIcon);
        imageViewIconPassword.setImageResource(R.drawable.icons8_password_);
        TextView textViewTitlePassword = rootView.findViewById(R.id.includeFieldUserInformationPassword).findViewById(R.id.textViewUserInformationTitle);
        textViewTitlePassword.setText("Senha do usuário");

        // School Year field.
        ImageView imageViewIconSchoolYear = rootView.findViewById(R.id.includeFieldUserInformationYear).findViewById(R.id.imageViewUserInformationIcon);
        imageViewIconSchoolYear.setImageResource(R.drawable.icons8_graduation_hat_100);
        TextView textViewTitleSchoolYear = rootView.findViewById(R.id.includeFieldUserInformationYear).findViewById(R.id.textViewUserInformationTitle);
        textViewTitleSchoolYear.setText("Var do Banco de Dados");


        // Inflate the layout for this fragment
        return rootView;
    }
}
