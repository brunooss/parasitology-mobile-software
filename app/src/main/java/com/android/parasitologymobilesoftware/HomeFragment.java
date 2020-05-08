package com.android.parasitologymobilesoftware;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentNavigableMap;

public class HomeFragment extends Fragment {

    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;

    private int [] introductionValuesConclude = {0, 14, 28, 42, 56, 70, 85, 100};
    private int [] protozoariosValuesConclude = {0, 10, 20, 30, 40, 50, 60, 70, 80, 90, 100};
    private int [] helmintosValuesConclude = {0, 6, 12, 18, 24, 30, 36, 42, 48, 54, 60, 66, 72, 79, 86, 93, 100};
    private int [] artropodesValuesConclude = {0, 20, 40, 60, 80, 100};

    private int progressToGo, nextCategoryId;
    private String parentCategory, category;
    private boolean categoryStatus;
    private String email, nome;
    private ProgressBar progressBarIntroduction, progressBarProtozoarios, progressBarHelmintos, progressBarArtropodes, progressBarApp;
    private int progressBarIntroductionValue, progressBarProtozoariosValue, progressBarHelmintosValue, progressBarArtropodesValue;
    private HomeActivity homeActivity = new HomeActivity();
    private View rootView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        email = firebaseAuth.getCurrentUser().getEmail();
        nome = firebaseAuth.getCurrentUser().getDisplayName();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        rootView = inflater.inflate(R.layout.fragment_home, container, false);
        // Progress Bars
        progressBarIntroduction = rootView.findViewById(R.id.progressAppIntroduction);
        progressBarProtozoarios = rootView.findViewById(R.id.progressAppProtozoarios);
        progressBarHelmintos = rootView.findViewById(R.id.progressAppHelmintos);
        progressBarArtropodes = rootView.findViewById(R.id.progressAppArtropodes);

        DocumentReference docRef = firebaseFirestore.collection("generalUserInfo").document(email).collection("specific info").document("progress");
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                progressBarIntroductionValue = documentSnapshot.getLong("progress introduction").intValue();
                progressBarProtozoariosValue = documentSnapshot.getLong("progress protozoarios").intValue();
                progressBarHelmintosValue = documentSnapshot.getLong("progress helmintos").intValue();
                progressBarArtropodesValue = documentSnapshot.getLong("progress artropodes").intValue();

                progressBarIntroduction.setProgress(progressBarIntroductionValue, true);
                progressBarProtozoarios.setProgress(progressBarProtozoariosValue, true);
                progressBarHelmintos.setProgress(progressBarHelmintosValue, true);
                progressBarArtropodes.setProgress(progressBarArtropodesValue, true);

                if (progressBarIntroductionValue < 100) {
                    for (int i = 0; i < introductionValuesConclude.length; i++) {
                        if(progressBarIntroductionValue == introductionValuesConclude[i]) {
                            for (int b = 0; b <= i; b++) {
                                rootView.findViewById(homeActivity.categoriesIds[b]).setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                                rootView.findViewById(homeActivity.categoriesIds[b]).setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        homeActivity.onCategoryButtonClick(view);
                                    }
                                });
                            }
                        }
                    }
                }
                else if (progressBarProtozoariosValue < 100) {
                    for (int i = 0; i < protozoariosValuesConclude.length; i++) {
                        if(progressBarProtozoariosValue == protozoariosValuesConclude[i]) {
                            for (int b = 0; b <= i + 7; b++) {
                                rootView.findViewById(homeActivity.categoriesIds[b]).setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                                rootView.findViewById(homeActivity.categoriesIds[b]).setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        homeActivity.onCategoryButtonClick(view);
                                    }
                                });
                            }
                        }
                    }
                }
                else if (progressBarHelmintosValue < 100) {
                    for (int i = 0; i < helmintosValuesConclude.length; i++) {
                        if(progressBarHelmintosValue == helmintosValuesConclude[i]) {
                            for (int b = 0; b <= i + 7 + 10; b++) {
                                rootView.findViewById(homeActivity.categoriesIds[b]).setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                                rootView.findViewById(homeActivity.categoriesIds[b]).setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        homeActivity.onCategoryButtonClick(view);
                                    }
                                });
                            }
                        }
                    }
                }
                else if (progressBarArtropodesValue <= 100) {
                    for (int i = 0; i < artropodesValuesConclude.length; i++) {
                        if(progressBarArtropodesValue == artropodesValuesConclude[i]) {
                            for (int b = 0; b <= i + 7 + 10 + 16; b++) {
                                rootView.findViewById(homeActivity.categoriesIds[b]).setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                                rootView.findViewById(homeActivity.categoriesIds[b]).setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                         homeActivity.onCategoryButtonClick(view);
                                    }
                                });

                            }
                        }
                    }
                }
            }
        });

        for (int i = 1; i < homeActivity.categoriesIds.length; i++) {
            if (i < 7) {
                rootView.findViewById(homeActivity.categoriesIds[i]).findViewById(R.id.imageViewCategoryBackgroundIcon).setBackgroundResource(R.drawable.icons8_books_64);
            } else if (i > 7 && i < 17) {
                rootView.findViewById(homeActivity.categoriesIds[i]).findViewById(R.id.imageViewCategoryBackgroundIcon).setBackgroundResource(R.drawable.icons8_microorganismos_50);
            } else if (i > 17 && i < 33) {
                rootView.findViewById(homeActivity.categoriesIds[i]).findViewById(R.id.imageViewCategoryBackgroundIcon).setBackgroundResource(R.drawable.icons8_soil_80);
            } else if (i > 33) {
                rootView.findViewById(homeActivity.categoriesIds[i]).findViewById(R.id.imageViewCategoryBackgroundIcon).setBackgroundResource(R.drawable.icons8_bug_32);
            }
        }

        TextView textViewCategoryIntroduction = rootView.findViewById(R.id.categoryHomeFragmentIntroduction).findViewById(R.id.textViewCategory);
        textViewCategoryIntroduction.setText("1"); // I
        textViewCategoryIntroduction.setVisibility(View.VISIBLE);

        TextView textViewCategoryProtozoarios = rootView.findViewById(R.id.categoryHomeFragmentProtozoarios).findViewById(R.id.textViewCategory);
        textViewCategoryProtozoarios.setText("11"); /// II
        textViewCategoryProtozoarios.setVisibility(View.VISIBLE);

        TextView textViewCategoryHelmintos = rootView.findViewById(R.id.categoryHomeFragmentHelmintos).findViewById(R.id.textViewCategory);
        textViewCategoryHelmintos.setText("111");  // III
        textViewCategoryHelmintos.setVisibility(View.VISIBLE);

        TextView textViewCategoryArtropodes = rootView.findViewById(R.id.categoryHomeFragmentArtropodes).findViewById(R.id.textViewCategory);
        textViewCategoryArtropodes.setText("15"); // IV
        textViewCategoryArtropodes.setVisibility(View.VISIBLE);

        // Inflate the layout for this category
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();

        CategoryFragment categoryFragment = new CategoryFragment();

        parentCategory = categoryFragment.getCategoryParent();
        progressToGo = categoryFragment.getConcludeProgress();
        nextCategoryId = categoryFragment.getNextCategoryId();
        categoryStatus = categoryFragment.isCategoryStatus();
        category = categoryFragment.getCategory();

        if (!(parentCategory == null && progressToGo == 0 && nextCategoryId == 0)) {
            DocumentReference docRef = firebaseFirestore.collection("generalUserInfo").document(email).collection("specific info").document("progress categories");
            docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    setCategoryStatus(documentSnapshot.getBoolean(getCategory()));
                    if (!categoryStatus) { //não completou a categoria
                        switch (parentCategory) {
                            case "Introdução":
                                progressBarIntroduction.setProgress(progressToGo, true);
                                break;
                            case "Protozoários":
                                progressBarProtozoarios.setProgress(progressToGo, true);
                                break;
                            case "Helmintos":
                                progressBarHelmintos.setProgress(progressToGo, true);
                                break;
                            case "Artrópodes":
                                progressBarArtropodes.setProgress(progressToGo, true);
                                break;
                        }

                        for (int i = 0; i < homeActivity.categoriesIds.length; i++) {
                            if(nextCategoryId == homeActivity.categoriesIds[i]) {
                                rootView.findViewById(homeActivity.categoriesIds[i]).setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            }
                        }

                        Map<String, Object> newStatusCategory = new HashMap<>();
                        newStatusCategory.put(category, true);
                        firebaseFirestore.collection("generalUserInfo").document(email).collection("specific info").document("progress categories")
                                .update(newStatusCategory);
                    }
                }
            });
        }
    }

    public void setCategoryStatus(boolean categoryStatus) {
        this.categoryStatus = categoryStatus;
    }

    public String getCategory() {
        return category;
    }
}
