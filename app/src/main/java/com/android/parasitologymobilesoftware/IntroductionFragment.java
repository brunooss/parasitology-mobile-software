package com.android.parasitologymobilesoftware;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

public class IntroductionFragment extends Fragment {

    private int page;
    private String username;

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

        switch (page) {
            case 1:
                constraintLayout.setBackgroundColor(getResources().getColor(R.color.colorBackgroundIntroductionFirst, getResources().newTheme()));
                imageViewIcon.setImageResource(R.drawable.icons8_happy_100);
                //@Todo Change setText to string resource.
                textViewTitle.setText("Olá, nome");
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
