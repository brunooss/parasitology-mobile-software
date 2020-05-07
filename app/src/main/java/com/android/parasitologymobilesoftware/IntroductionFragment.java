package com.android.parasitologymobilesoftware;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IntroductionFragment extends Fragment {

    private int page;
    private String username;

    private String hello = "Olá, ";
    private String firstName;
    private SharedPreferences preferences;

    private FirebaseUser firebaseUser;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        username = getActivity().getIntent().getStringExtra("username");

        Log.d("IntroFrag", "la " +username);
        super.onCreate(savedInstanceState);
        if(getArguments() != null) {
            page = getArguments().getInt("page") + 1;
        }

        preferences = this.getActivity().getSharedPreferences("prefs", 0);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_introduction, container, false);

        firstName = getFirstName(username);

       ConstraintLayout constraintLayout = view.findViewById(R.id.constraindLayoutIntroduction);
        ImageView imageViewIcon = view.findViewById(R.id.imageViewIntroductionIcon);
        TextView textViewTitle = view.findViewById(R.id.textViewIntroductionTitle);
        TextView textViewDescription = view.findViewById(R.id.textViewIntroductionDescription);
        TextView textViewSkip = view.findViewById(R.id.textViewIntroductionSkip);
        TextView textViewStart = view.findViewById(R.id.textViewIntroductionStart);

        textViewSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), StudentPreferenceActivity.class));
            }
        });
        textViewStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), StudentPreferenceActivity.class));
            }
        });

        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(constraintLayout);

        switch (page) {
            case 1:
                constraintLayout.setBackgroundColor(getResources().getColor(R.color.colorBackgroundIntroductionFirst, getResources().newTheme()));
                imageViewIcon.setImageResource(R.drawable.icons8_happy_100);
                //TODO Change setText to string resource.
                textViewTitle.setText(hello.concat(firstName));
                textViewDescription.setText("Seja muito bem-vindo(a) ao Parasite Combat, é um imenso prazer tê-lo(a) conosco!");
                break;
            case 2:
                constraintLayout.setBackgroundColor(getResources().getColor(R.color.colorBackgroundIntroductionSecond, getResources().newTheme()));
                imageViewIcon.setImageResource(R.drawable.icons8_books_100);
                textViewTitle.setText(R.string.app_introduction_title_second);
                textViewDescription.setText("Nossa metodologia de ensino aumenta a autonomia dos estudantes e promove um maior interesse pelo conteúdo, inovando o processo ensino-aprendizagem.");
                //textViewDescription.setText("Inovação do processo ensino-aprendizagem, aumentando a autonomia dos estudantes e o interesse pelo conteúdo.");
                break;
            case 3:
                constraintLayout.setBackgroundColor(getResources().getColor(R.color.colorBackgroundIntroductionThird, getResources().newTheme()));
                imageViewIcon.setImageResource(R.drawable.icons8_microscope_100);
                textViewTitle.setText(R.string.app_introduction_title_third);
                textViewDescription.setText("A pesquisa científica prepara cidadãos mais conscientes, críticos e participativos, que contribuirão ativamente para a solução de problemas da sociedade.");
                //textViewDescription.setText("");
                break;
            case 4:
                constraintLayout.setBackgroundColor(getResources().getColor(R.color.colorBackgroundIntroductionFourth, getResources().newTheme()));
                imageViewIcon.setImageResource(R.drawable.icons8_rocket_100);
                textViewTitle.setText(R.string.app_introduction_title_fourth);
                textViewDescription.setText("Esperamos que tenha uma experiência inovadora no estudo de Parasitologia.");
                textViewSkip.setVisibility(View.INVISIBLE);
                textViewStart.setVisibility(View.VISIBLE);
                break;
        }
        return view;
    }

    public static IntroductionFragment newInstance(int page) {

        Bundle args = new Bundle();

        args.putInt("page", page);
        args.putString("username", "nome");
        IntroductionFragment fragment = new IntroductionFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public String getFirstName(String name) { // If name is valid, returns the first name.
            String regexName = "\\S+"; //Capture only blank spaces.

            Pattern pattern = Pattern.compile(regexName);
            Matcher matcher = pattern.matcher(name);
            if (matcher.find()) return matcher.group(0);
            else return name; // Returns the name before the blank space or the entire name.
    }
}
