package com.android.parasitologymobilesoftware;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.fragment.app.Fragment;

public class IntroductionFragment extends Fragment {

    private int page;
    private String username;
    private String hello = "Olá, ";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        if(getArguments() != null) {
            page = getArguments().getInt("page") + 1;
            username = getArguments().getString("username");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_introduction, container, false);

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
                textViewTitle.setText(hello.concat());
                break;
            case 2:
                constraintLayout.setBackgroundColor(getResources().getColor(R.color.colorBackgroundIntroductionSecond, getResources().newTheme()));
                imageViewIcon.setImageResource(R.drawable.icons8_books_100);
                textViewTitle.setText(R.string.app_introduction_title_second);
                break;
            case 3:
                constraintLayout.setBackgroundColor(getResources().getColor(R.color.colorBackgroundIntroductionThird, getResources().newTheme()));
                imageViewIcon.setImageResource(R.drawable.icons8_microscope_100);
                textViewTitle.setText(R.string.app_introduction_title_third);
                break;
            case 4:
                constraintLayout.setBackgroundColor(getResources().getColor(R.color.colorBackgroundIntroductionFourth, getResources().newTheme()));
                imageViewIcon.setImageResource(R.drawable.icons8_rocket_100);
                textViewTitle.setText(R.string.app_introduction_title_fourth);
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
}
