package com.android.parasitologymobilesoftware;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

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
import java.util.Objects;
import java.util.concurrent.ConcurrentNavigableMap;

public class HomeFragment extends Fragment {

    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;

    private int [] introductionValuesConclude = {0, 14, 28, 42, 56, 70, 85, 100};
    private int [] protozoariosValuesConclude = {0, 10, 20, 30, 40, 50, 60, 70, 80, 90, 100};
    private int [] helmintosValuesConclude = {0, 6, 12, 18, 24, 30, 36, 42, 48, 54, 60, 66, 72, 79, 86, 93, 100};
    private int [] artropodesValuesConclude = {0, 20, 40, 60, 80, 100};

    private int progressToGo, nextCategoryId;
    private static String parentCategory, category;
    private boolean categoryStatus;
    private String email, nome;
    private ProgressBar progressBarIntroduction, progressBarProtozoarios, progressBarHelmintos, progressBarArtropodes, progressBarApp;
    private int progressBarIntroductionValue, progressBarProtozoariosValue, progressBarHelmintosValue, progressBarArtropodesValue;
    private HomeActivity homeActivity = new HomeActivity();
    private View rootView;

    public static int categoryId;
    private CategoryFragment categoryFragment;

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
                                Log.i("HomeFragment",  "Oi!");
                                rootView.findViewById(homeActivity.categoriesIds[b]).setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        onCategoryButtonClick(view);
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
                                        onCategoryButtonClick(view);
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
                                        onCategoryButtonClick(view);
                                    }
                                });
                            }
                        }
                    }
                }
                else if (progressBarArtropodesValue <= 100) {
                    for (int i = 0; i < artropodesValuesConclude.length; i++) {
                        if(progressBarArtropodesValue == artropodesValuesConclude[i]) {
                            for (int b = 0; b <= i + 7 + 10 + 16 && b < 38; b++) {
                                rootView.findViewById(homeActivity.categoriesIds[b]).setBackground(Objects.requireNonNull(getActivity()).getDrawable(R.drawable.category_button_background_filled));
                                rootView.findViewById(homeActivity.categoriesIds[b]).setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                         onCategoryButtonClick(view);
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
                rootView.findViewById(homeActivity.categoriesIds[i]).findViewById(R.id.imageViewCategoryBackgroundIcon).setBackgroundResource(R.drawable.icons8_open_book_50);
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
                                rootView.findViewById(homeActivity.categoriesIds[i]).setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        onCategoryButtonClick(view);
                                    }
                                });
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

    public void onCategoryButtonClick(View view) {
        if (true) { //@Todo Arrumar isso ae
            Intent intent = new Intent(getActivity(), CategoryActivity.class);
            categoryId = view.getId();

            switch (categoryId) {
                case R.id.categoryHomeFragmentIntroduction:
                    category = "Introdução";
                    break;
                case R.id.categoryHomeFragmentIntroductionEcologia:
                    category = "Ecologia";
                    break;
                case R.id.categoryHomeFragmentIntroductionConceitosGerais:
                    category = "Conceitos Gerais";
                    break;
                case R.id.categoryHomeFragmentIntroductionClassificacao:
                    category = "Classificação";
                    break;
                case R.id.categoryHomeFragmentIntroductionReproducao:
                    category = "Reprodução";
                    break;
                case R.id.categoryHomeFragmentIntroductionCicloBiologico:
                    category = "Ciclo Biológico";
                    break;
                case R.id.categoryHomeFragmentIntroductionAtualidades:
                    category = "Atualidades";
                    break;
                case R.id.categoryHomeFragmentProtozoarios:
                    category = "Protozoários";
                    break;
                case R.id.categoryHomeFragmentProtozoariosAmebiase:
                    category = "Amebíase";
                    break;
                case R.id.categoryHomeFragmentProtozoariosGiardiase:
                    category = "Giardíase";
                    break;
                case R.id.categoryHomeFragmentProtozoariosLeishmanioses:
                    category = "Leishmanioses";
                    break;
                case R.id.categoryHomeFragmentProtozoariosTricomonose:
                    category = "Tricomonose";
                    break;
                case R.id.categoryHomeFragmentProtozoariosChagas:
                    category = "Doença de Chagas";
                    break;
                case R.id.categoryHomeFragmentProtozoariosMalaria:
                    category = "Malária";
                    break;
                case R.id.categoryHomeFragmentProtozoariosToxoplasmose:
                    category = "Toxoplasmose";
                    break;
                case R.id.categoryHomeFragmentProtozoariosBalantidiase:
                    category = "Balantidíase";
                    break;
                case R.id.categoryHomeFragmentProtozoariosProtozooses:
                    category = "Protozooses";
                    break;
                case R.id.categoryHomeFragmentHelmintos:
                    category = "Helmintos";
                    break;
                case R.id.categoryHomeFragmentHelmintosEsquistossomose:
                    category = "Esquistossomose";
                    break;
                case R.id.categoryHomeFragmentHelmintosFascioliase:
                    category = "Fasciolíase";
                    break;
                case R.id.categoryHomeFragmentHelmintosTeniase:
                    category = "Teníase";
                    break;
                case R.id.categoryHomeFragmentHelmintosCisticercose:
                    category = "Cisticercose";
                    break;
                case R.id.categoryHomeFragmentHelmintosHidatidose:
                    category = "Hidatidose";
                    break;
                case R.id.categoryHomeFragmentHelmintosHimenolepiase:
                    category = "Himenolepíase";
                    break;
                case R.id.categoryHomeFragmentHelmintosEstrongiloidiase:
                    category = "Estrongiloidíase";
                    break;
                case R.id.categoryHomeFragmentHelmintosTricuriase:
                    category = "Tricuríase";
                    break;
                case R.id.categoryHomeFragmentHelmintosAncilostomiase:
                    category = "Ancilostomíase";
                    break;
                case R.id.categoryHomeFragmentHelmintosNecatoriase:
                    category = "Necatoríase";
                    break;
                case R.id.categoryHomeFragmentHelmintosEnterobiase:
                    category = "Enterobíase";
                    break;
                case R.id.categoryHomeFragmentHelmintosAscaridiase:
                    category = "Ascaridíase";
                    break;
                case R.id.categoryHomeFragmentHelmintosLarvaMigrans:
                    category = "Larva Migrans";
                    break;
                case R.id.categoryHomeFragmentHelmintosFilarioses:
                    category = "Filarioses";
                    break;
                case R.id.categoryHomeFragmentHelmintosOutrasHelmintoses:
                    category = "Outras Helmintoses";
                    break;
                case R.id.categoryHomeFragmentArtropodes:
                    category = "Artrópodes";
                    break;
                case R.id.categoryHomeFragmentArtropodesHemipteros:
                    category = "Hemípteros";
                    break;
                case R.id.categoryHomeFragmentArtropodesMosquitos:
                    category = "Mosquitos";
                    break;
                case R.id.categoryHomeFragmentArtropodesMoscas:
                    category = "Moscas";
                    break;
                case R.id.categoryHomeFragmentArtropodesEctoparasitos:
                    category = "Ectoparasitos";
                    break;
            }
            Bundle extra = new Bundle();
            extra.putString("category", category);
            extra.putInt("CategoryId", categoryId);

            // Passing category's name to activity
            intent.putExtras(extra);

            Log.i("HomeFragment", "nome da categoria ao clicar o botão: "+ category);

            startActivity(intent);
        }

    }

    public void setCategoryStatus(boolean categoryStatus) {
        this.categoryStatus = categoryStatus;
    }

    public String getCategory() {
        return category;
    }

    public static int getCategoryId() {
        return categoryId;
    }
}
