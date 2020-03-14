package com.android.parasitologymobilesoftware;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class HomeFragment extends Fragment {

    private TextView textViewCategoryIntroduction, textViewCategoryProtozoarios,
                     textViewCategoryHelmintos, textViewCategoryArtropodes;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        HomeActivity activity = new HomeActivity();
        String text[][] = activity.searchSubcategories;

        textViewCategoryIntroduction = rootView.findViewById(R.id.categoryHomeFragmentIntroduction).findViewById(R.id.textViewCategory);
        textViewCategoryIntroduction.setText("1"); // I
        textViewCategoryIntroduction.setVisibility(View.VISIBLE);

        textViewCategoryProtozoarios = rootView.findViewById(R.id.categoryHomeFragmentProtozoarios).findViewById(R.id.textViewCategory);
        textViewCategoryProtozoarios.setText("11"); /// II
        textViewCategoryProtozoarios.setVisibility(View.VISIBLE);

        textViewCategoryHelmintos = rootView.findViewById(R.id.categoryHomeFragmentHelmintos).findViewById(R.id.textViewCategory);
        textViewCategoryHelmintos.setText("111");  // III
        textViewCategoryHelmintos.setVisibility(View.VISIBLE);

        textViewCategoryArtropodes = rootView.findViewById(R.id.categoryHomeFragmentArtropodes).findViewById(R.id.textViewCategory);
        textViewCategoryArtropodes.setText("15"); // IV
        textViewCategoryArtropodes.setVisibility(View.VISIBLE);

        // Inflate the layout for this fragment
        return rootView;
    }
}
