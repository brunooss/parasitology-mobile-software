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

    public static final int introductionConclude = 14;
    public static final int introductionEcologiaConclude = 28;
    public static final int introductionConceitosConclude = 42;
    public static final int introductionClassificacaoConclude = 56;
    public static final int introductionReproducaoConclude = 70;
    public static final int introductionCicloBiologicoConclude = 85;
    public static final int introductionAtualidadesConclude = 100;

    public static final int protozoariosIntroducaoConclude = 10;
    public static final int protozoariosAmebiaseConclude = 20;
    public static final int protozoariosGiardiaseConclude = 30;
    public static final int protozoariosLeishmaniosesIntroducaoConclude = 40;
    public static final int protozoariosTricomonoseConclude = 50;
    public static final int protozoariosChagasConclude = 60;
    public static final int protozoariosMalariaConclude = 70;
    public static final int protozoariosToxoplasmoseConclude = 80;
    public static final int protozoariosBalantidiaseConclude = 90;
    public static final int protozoariosProtozooseConclude = 100;

    public static final int helmintosIntroducaoConclude = 6;
    public static final int helmintosEsquistossomoseConclude = 12;
    public static final int helmintosFascioliaseConclude = 18;
    public static final int helmintosTeniaseConclude = 24;
    public static final int helmintosCisticercoseConclude = 30;
    public static final int helmintosHidatidoseConclude = 36;
    public static final int helmintosHimenolepiaseConclude = 42;
    public static final int helmintosEstrongiloidiaseConclude = 48;
    public static final int helmintosTricuriaseConclude = 54;
    public static final int helmintosAncilostomiaseConclude = 60;
    public static final int helmintosNecatoriaseConclude = 66;
    public static final int helmintosEnterobiaseConclude = 72;
    public static final int helmintosAscaridiaseConclude = 79;
    public static final int helmintosLarvaMigransConclude = 86;
    public static final int helmintosFilariosesConclude = 93;
    public static final int helmintosOutrasHelmintosesConclude = 100;

    public static final int artropodesIntroducaoConclude = 20;
    public static final int artropodesHemipterosConclude = 40;
    public static final int artropodesMosquitosConclude = 60;
    public static final int artropodesMoscasConclude = 80;
    public static final int artropodesEctoparasitosConclude = 100;

    private int progressToGo, nextCategoryId;
    private String parentCategory, category;
    private boolean categoryStatus;

    private String email, nome;

    private ProgressBar progressBarIntroduction, progressBarProtozoarios,
            progressBarHelmintos, progressBarArtropodes, progressBarApp;

    private int progressBarIntroductionValue, progressBarProtozoariosValue,
            progressBarHelmintosValue, progressBarArtropodesValue;

    public static int id = 0;


    private ConstraintLayout constraintLayoutIntroduction, constraintLayoutIntroductionEcologia, constraintLayoutIntroductionConceitos,
                             constraintLayoutIntroductionClassificacao, constraintLayoutIntroductionReproducao, constraintLayoutIntroductionCiclo,
                             constraintLayoutIntroductionAtualidades, constraintLayoutProtozoarios, constraintLayoutProtozoariosAmebiase,
                             constraintLayoutProtozoariosGiardiase, constraintLayoutProtozoariosLeishmanioses, constraintLayoutProtozoariosTricomonose,
                             constraintLayoutProtozoariosChagas, constraintLayoutProtozoariosMalaria, constraintLayoutProtozoariosToxoplasmose,
                             constraintLayoutProtozoariosBalantidiase, constraintLayoutProtozoariosProtozooses, constraintLayoutHelmintos,
                             constraintLayoutHelmintosEsquistossomose, constraintLayoutHelmintosFascioliase, constraintLayoutHelmintosTeniase,
                             constraintLayoutHelmintosCisticercose, constraintLayoutHelmintosHidatidose, constraintLayoutHelmintosHimenolepíase,
                             constraintLayoutHelmintosEstrongiloidiase, constraintLayoutHelmintosTricuriase, constraintLayoutHelmintosAncilostomiase,
                             constraintLayoutHelmintosNecatoriase, constraintLayoutHelmintosEnterobiase, constraintLayoutHelmintosAscaridiase,
                             constraintLayoutHelmintosLarvaMigrans, constraintLayoutHelmintosFilarioses, constraintLayoutHelmintosOutrasHelmintoses,
                             constraintLayoutArtropodes, constraintLayoutArtropodesHemipteros, constraintLayoutArtropodesMosquitos, constraintLayoutArtropodesMoscas,
                             constraintLayoutArtropodesEctoparasitos;

    private TextView textViewCategoryIntroduction, textViewCategoryProtozoarios,
            textViewCategoryHelmintos, textViewCategoryArtropodes;

    public void updateId(int id) {
        this.id = id;
    }

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


        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        if (id != 0) {
            View nextView = rootView.findViewById(id);
            Toast.makeText(getContext(), String.valueOf(nextView.getId()), Toast.LENGTH_LONG).show();
            nextView.setBackgroundResource(R.drawable.category_button_background_filled);
            nextView.setClickable(true);
        }



        // Progress Bars
        progressBarIntroduction = rootView.findViewById(R.id.progressAppIntroduction);
        progressBarProtozoarios = rootView.findViewById(R.id.progressAppProtozoarios);
        progressBarHelmintos = rootView.findViewById(R.id.progressAppHelmintos);
        progressBarArtropodes = rootView.findViewById(R.id.progressAppArtropodes);

        // Categorys
            // Introduction
        constraintLayoutIntroduction = rootView.findViewById(R.id.categoryHomeFragmentIntroduction);
        constraintLayoutIntroductionEcologia = rootView.findViewById(R.id.categoryHomeFragmentIntroductionEcologia);
        // teste settando icon
        //ImageView teste = constraintLayoutIntroductionEcologia.findViewById(R.id.imageViewCategoryBackgroundIcon);
        //teste.setImageDrawable(getActivity().getDrawable(R.drawable.icons8_happy_100));

        constraintLayoutIntroductionConceitos = rootView.findViewById(R.id.categoryHomeFragmentIntroductionConceitosGerais);
        constraintLayoutIntroductionClassificacao = rootView.findViewById(R.id.categoryHomeFragmentIntroductionClassificacao);
        constraintLayoutIntroductionReproducao = rootView.findViewById(R.id.categoryHomeFragmentIntroductionReproducao);
        constraintLayoutIntroductionCiclo = rootView.findViewById(R.id.categoryHomeFragmentIntroductionCicloBiologico);
        constraintLayoutIntroductionAtualidades = rootView.findViewById(R.id.categoryHomeFragmentIntroductionAtualidades);
            // Protozoários
        constraintLayoutProtozoarios = rootView.findViewById(R.id.categoryHomeFragmentProtozoarios);
        constraintLayoutProtozoariosAmebiase = rootView.findViewById(R.id.categoryHomeFragmentProtozoariosAmebiase);
        constraintLayoutProtozoariosGiardiase = rootView.findViewById(R.id.categoryHomeFragmentProtozoariosGiardiase);
        constraintLayoutProtozoariosLeishmanioses = rootView.findViewById(R.id.categoryHomeFragmentProtozoariosLeishmanioses);
        constraintLayoutProtozoariosTricomonose = rootView.findViewById(R.id.categoryHomeFragmentProtozoariosTricomonose);
        constraintLayoutProtozoariosChagas = rootView.findViewById(R.id.categoryHomeFragmentProtozoariosChagas);
        constraintLayoutProtozoariosMalaria = rootView.findViewById(R.id.categoryHomeFragmentProtozoariosMalaria);
        constraintLayoutProtozoariosToxoplasmose = rootView.findViewById(R.id.categoryHomeFragmentProtozoariosToxoplasmose);
        constraintLayoutProtozoariosBalantidiase = rootView.findViewById(R.id.categoryHomeFragmentProtozoariosBalantidiase);
        constraintLayoutProtozoariosProtozooses = rootView.findViewById(R.id.categoryHomeFragmentProtozoariosProtozooses);
            // Helmintos
        constraintLayoutHelmintos = rootView.findViewById(R.id.categoryHomeFragmentHelmintos);
        constraintLayoutHelmintosEsquistossomose = rootView.findViewById(R.id.categoryHomeFragmentHelmintosEsquistossomose);
        constraintLayoutHelmintosFascioliase = rootView.findViewById(R.id.categoryHomeFragmentHelmintosFascioliase);
        constraintLayoutHelmintosTeniase = rootView.findViewById(R.id.categoryHomeFragmentHelmintosTeniase);
        constraintLayoutHelmintosCisticercose = rootView.findViewById(R.id.categoryHomeFragmentHelmintosCisticercose);
        constraintLayoutHelmintosHidatidose = rootView.findViewById(R.id.categoryHomeFragmentHelmintosHidatidose);
        constraintLayoutHelmintosHimenolepíase = rootView.findViewById(R.id.categoryHomeFragmentHelmintosHimenolepiase);
        constraintLayoutHelmintosEstrongiloidiase = rootView.findViewById(R.id.categoryHomeFragmentHelmintosEstrongiloidiase);
        constraintLayoutHelmintosTricuriase = rootView.findViewById(R.id.categoryHomeFragmentHelmintosTricuriase);
        constraintLayoutHelmintosAncilostomiase= rootView.findViewById(R.id.categoryHomeFragmentHelmintosAncilostomiase);
        constraintLayoutHelmintosNecatoriase = rootView.findViewById(R.id.categoryHomeFragmentHelmintosNecatoriase);
        constraintLayoutHelmintosEnterobiase = rootView.findViewById(R.id.categoryHomeFragmentHelmintosEnterobiase);
        constraintLayoutHelmintosAscaridiase = rootView.findViewById(R.id.categoryHomeFragmentHelmintosAscaridiase);
        constraintLayoutHelmintosLarvaMigrans = rootView.findViewById(R.id.categoryHomeFragmentHelmintosLarvaMigrans);
        constraintLayoutHelmintosFilarioses = rootView.findViewById(R.id.categoryHomeFragmentHelmintosFilarioses);
        constraintLayoutHelmintosOutrasHelmintoses = rootView.findViewById(R.id.categoryHomeFragmentHelmintosOutrasHelmintoses);
            // Artropodes
        constraintLayoutArtropodes = rootView.findViewById(R.id.categoryHomeFragmentArtropodes);
        constraintLayoutArtropodesHemipteros = rootView.findViewById(R.id.categoryHomeFragmentArtropodesHemipteros);
        constraintLayoutArtropodesMosquitos = rootView.findViewById(R.id.categoryHomeFragmentArtropodesMosquitos);
        constraintLayoutArtropodesMoscas = rootView.findViewById(R.id.categoryHomeFragmentArtropodesMoscas);
        constraintLayoutArtropodesEctoparasitos = rootView.findViewById(R.id.categoryHomeFragmentArtropodesEctoparasitos);




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
                    switch (progressBarIntroductionValue) {
                        case 0:
                            constraintLayoutIntroduction.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            break;
                        case introductionConclude :
                            constraintLayoutIntroduction.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            constraintLayoutIntroductionEcologia.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            break;
                        case introductionEcologiaConclude :
                            constraintLayoutIntroduction.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            constraintLayoutIntroductionEcologia.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            constraintLayoutIntroductionConceitos.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            break;
                        case introductionConceitosConclude :
                            constraintLayoutIntroduction.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            constraintLayoutIntroductionEcologia.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            constraintLayoutIntroductionConceitos.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            constraintLayoutIntroductionClassificacao.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            break;
                        case introductionClassificacaoConclude :
                            constraintLayoutIntroduction.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            constraintLayoutIntroductionEcologia.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            constraintLayoutIntroductionConceitos.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            constraintLayoutIntroductionClassificacao.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            constraintLayoutIntroductionReproducao.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            break;
                        case introductionReproducaoConclude :
                            constraintLayoutIntroduction.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            constraintLayoutIntroductionEcologia.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            constraintLayoutIntroductionConceitos.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            constraintLayoutIntroductionClassificacao.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            constraintLayoutIntroductionReproducao.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            constraintLayoutIntroductionCiclo.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            break;
                        case introductionCicloBiologicoConclude :
                            constraintLayoutIntroduction.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            constraintLayoutIntroductionEcologia.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            constraintLayoutIntroductionConceitos.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            constraintLayoutIntroductionClassificacao.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            constraintLayoutIntroductionReproducao.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            constraintLayoutIntroductionCiclo.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            constraintLayoutIntroductionAtualidades.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            break;
//                        case 100 :
//                            constraintLayoutIntroduction.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
//                            constraintLayoutIntroductionEcologia.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
//                            constraintLayoutIntroductionConceitos.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
//                            constraintLayoutIntroductionClassificacao.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
//                            constraintLayoutIntrohelmintosFilariosesConcludeductionReproducao.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
//                            constraintLayoutIntroductionCiclo.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
//                            constraintLayoutIntroductionAtualidades.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
//
//                            constraintLayoutProtozoarios.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
//                            break;
                    }
                } else if (progressBarProtozoariosValue < 100) {
                    setProgressIntroduction();
                    constraintLayoutProtozoarios.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                    switch (progressBarProtozoariosValue) {
                        case protozoariosIntroducaoConclude :
                            constraintLayoutProtozoariosAmebiase.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            break;
                        case protozoariosAmebiaseConclude :
                            constraintLayoutProtozoariosAmebiase.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            constraintLayoutProtozoariosGiardiase.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            break;
                        case protozoariosGiardiaseConclude :
                            constraintLayoutProtozoariosAmebiase.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            constraintLayoutProtozoariosGiardiase.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            constraintLayoutProtozoariosLeishmanioses.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            break;
                        case protozoariosLeishmaniosesIntroducaoConclude :
                            constraintLayoutProtozoariosAmebiase.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            constraintLayoutProtozoariosGiardiase.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            constraintLayoutProtozoariosLeishmanioses.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            constraintLayoutProtozoariosTricomonose.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            break;
                        case protozoariosTricomonoseConclude :
                            constraintLayoutProtozoariosAmebiase.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            constraintLayoutProtozoariosGiardiase.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            constraintLayoutProtozoariosLeishmanioses.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            constraintLayoutProtozoariosTricomonose.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            constraintLayoutProtozoariosChagas.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            break;
                        case protozoariosChagasConclude :
                            constraintLayoutProtozoariosAmebiase.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            constraintLayoutProtozoariosGiardiase.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            constraintLayoutProtozoariosLeishmanioses.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            constraintLayoutProtozoariosTricomonose.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            constraintLayoutProtozoariosChagas.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            constraintLayoutProtozoariosMalaria.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            break;
                        case protozoariosMalariaConclude :
                            constraintLayoutProtozoariosAmebiase.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            constraintLayoutProtozoariosGiardiase.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            constraintLayoutProtozoariosLeishmanioses.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            constraintLayoutProtozoariosTricomonose.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            constraintLayoutProtozoariosChagas.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            constraintLayoutProtozoariosMalaria.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            constraintLayoutProtozoariosToxoplasmose.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            break;
                        case protozoariosToxoplasmoseConclude :
                            constraintLayoutProtozoariosAmebiase.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            constraintLayoutProtozoariosGiardiase.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            constraintLayoutProtozoariosLeishmanioses.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            constraintLayoutProtozoariosTricomonose.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            constraintLayoutProtozoariosChagas.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            constraintLayoutProtozoariosMalaria.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            constraintLayoutProtozoariosToxoplasmose.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            constraintLayoutProtozoariosBalantidiase.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            break;
                        case protozoariosBalantidiaseConclude :
                            constraintLayoutProtozoariosAmebiase.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            constraintLayoutProtozoariosGiardiase.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            constraintLayoutProtozoariosLeishmanioses.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            constraintLayoutProtozoariosTricomonose.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            constraintLayoutProtozoariosChagas.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            constraintLayoutProtozoariosMalaria.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            constraintLayoutProtozoariosToxoplasmose.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            constraintLayoutProtozoariosBalantidiase.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            constraintLayoutProtozoariosProtozooses.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            break;
//                        case 100 :
//                            constraintLayoutProtozoariosAmebiase.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
//                            constraintLayoutProtozoariosGiardiase.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
//                            constraintLayoutProtozoariosLeishmanioses.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
//                            constraintLayoutProtozoariosTricomonose.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
//                            constraintLayoutProtozoariosChagas.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
//                            constraintLayoutProtozoariosMalaria.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
//                            constraintLayoutProtozoariosToxoplasmose.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
//                            constraintLayoutProtozoariosBalantidiase.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
//                            constraintLayoutProtozoariosProtozooses.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
//
//                            constraintLayoutHelmintos.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
//                            break;
                    }
                } else if (progressBarHelmintosValue < 100) {
                    setProgressIntroduction();
                    setProgressProtozoarios();
                    constraintLayoutHelmintos.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                    switch (progressBarHelmintosValue) {
                        case helmintosIntroducaoConclude:
                            constraintLayoutHelmintosEsquistossomose.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            break;
                        case helmintosEsquistossomoseConclude :
                            constraintLayoutHelmintosEsquistossomose.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            constraintLayoutHelmintosFascioliase.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            break;
                        case helmintosFascioliaseConclude :
                            constraintLayoutHelmintosEsquistossomose.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            constraintLayoutHelmintosFascioliase.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            constraintLayoutHelmintosTeniase.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            break;
                        case helmintosTeniaseConclude :
                            constraintLayoutHelmintosEsquistossomose.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            constraintLayoutHelmintosFascioliase.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            constraintLayoutHelmintosTeniase.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            constraintLayoutHelmintosCisticercose.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            break;
                        case helmintosCisticercoseConclude :
                            constraintLayoutHelmintosEsquistossomose.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            constraintLayoutHelmintosFascioliase.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            constraintLayoutHelmintosTeniase.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            constraintLayoutHelmintosCisticercose.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            constraintLayoutHelmintosHidatidose.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            break;
                        case helmintosHidatidoseConclude :
                            constraintLayoutHelmintosEsquistossomose.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            constraintLayoutHelmintosFascioliase.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            constraintLayoutHelmintosTeniase.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            constraintLayoutHelmintosCisticercose.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            constraintLayoutHelmintosHidatidose.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            constraintLayoutHelmintosHimenolepíase.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            break;
                        case helmintosHimenolepiaseConclude :
                            constraintLayoutHelmintosEsquistossomose.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            constraintLayoutHelmintosFascioliase.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            constraintLayoutHelmintosTeniase.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            constraintLayoutHelmintosCisticercose.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            constraintLayoutHelmintosHidatidose.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            constraintLayoutHelmintosHimenolepíase.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            constraintLayoutHelmintosEstrongiloidiase.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            break;
                        case helmintosEstrongiloidiaseConclude :
                            constraintLayoutHelmintosEsquistossomose.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            constraintLayoutHelmintosFascioliase.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            constraintLayoutHelmintosTeniase.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            constraintLayoutHelmintosCisticercose.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            constraintLayoutHelmintosHidatidose.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            constraintLayoutHelmintosHimenolepíase.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            constraintLayoutHelmintosEstrongiloidiase.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            constraintLayoutHelmintosTricuriase.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            break;
                        case helmintosTricuriaseConclude:
                            constraintLayoutHelmintosEsquistossomose.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            constraintLayoutHelmintosFascioliase.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            constraintLayoutHelmintosTeniase.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            constraintLayoutHelmintosCisticercose.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            constraintLayoutHelmintosHidatidose.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            constraintLayoutHelmintosHimenolepíase.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            constraintLayoutHelmintosEstrongiloidiase.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            constraintLayoutHelmintosTricuriase.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            constraintLayoutHelmintosAncilostomiase.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            break;
                        case helmintosAncilostomiaseConclude :
                            constraintLayoutHelmintosEsquistossomose.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            constraintLayoutHelmintosFascioliase.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            constraintLayoutHelmintosTeniase.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            constraintLayoutHelmintosCisticercose.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            constraintLayoutHelmintosHidatidose.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            constraintLayoutHelmintosHimenolepíase.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            constraintLayoutHelmintosEstrongiloidiase.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            constraintLayoutHelmintosTricuriase.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            constraintLayoutHelmintosAncilostomiase.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            constraintLayoutHelmintosNecatoriase.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            break;
                        case helmintosNecatoriaseConclude :
                            constraintLayoutHelmintosEsquistossomose.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            constraintLayoutHelmintosFascioliase.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            constraintLayoutHelmintosTeniase.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            constraintLayoutHelmintosCisticercose.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            constraintLayoutHelmintosHidatidose.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            constraintLayoutHelmintosHimenolepíase.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            constraintLayoutHelmintosEstrongiloidiase.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            constraintLayoutHelmintosTricuriase.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            constraintLayoutHelmintosAncilostomiase.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            constraintLayoutHelmintosNecatoriase.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            constraintLayoutHelmintosEnterobiase.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            break;
                        case helmintosEnterobiaseConclude :
                            constraintLayoutHelmintosEsquistossomose.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            constraintLayoutHelmintosFascioliase.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            constraintLayoutHelmintosTeniase.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            constraintLayoutHelmintosCisticercose.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            constraintLayoutHelmintosHidatidose.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            constraintLayoutHelmintosHimenolepíase.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            constraintLayoutHelmintosEstrongiloidiase.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            constraintLayoutHelmintosTricuriase.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            constraintLayoutHelmintosAncilostomiase.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            constraintLayoutHelmintosNecatoriase.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            constraintLayoutHelmintosEnterobiase.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            constraintLayoutHelmintosAscaridiase.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            break;
                        case helmintosAscaridiaseConclude :
                            constraintLayoutHelmintosEsquistossomose.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            constraintLayoutHelmintosFascioliase.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            constraintLayoutHelmintosTeniase.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            constraintLayoutHelmintosCisticercose.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            constraintLayoutHelmintosHidatidose.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            constraintLayoutHelmintosHimenolepíase.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            constraintLayoutHelmintosEstrongiloidiase.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            constraintLayoutHelmintosTricuriase.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            constraintLayoutHelmintosAncilostomiase.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            constraintLayoutHelmintosNecatoriase.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            constraintLayoutHelmintosEnterobiase.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            constraintLayoutHelmintosAscaridiase.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            constraintLayoutHelmintosLarvaMigrans.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            break;
                        case helmintosLarvaMigransConclude :
                            constraintLayoutHelmintosEsquistossomose.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            constraintLayoutHelmintosFascioliase.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            constraintLayoutHelmintosTeniase.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            constraintLayoutHelmintosCisticercose.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            constraintLayoutHelmintosHidatidose.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            constraintLayoutHelmintosHimenolepíase.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            constraintLayoutHelmintosEstrongiloidiase.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            constraintLayoutHelmintosTricuriase.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            constraintLayoutHelmintosAncilostomiase.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            constraintLayoutHelmintosNecatoriase.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            constraintLayoutHelmintosEnterobiase.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            constraintLayoutHelmintosAscaridiase.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            constraintLayoutHelmintosLarvaMigrans.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            constraintLayoutHelmintosFilarioses.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            break;
                        case helmintosFilariosesConclude :
                            constraintLayoutHelmintosEsquistossomose.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            constraintLayoutHelmintosFascioliase.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            constraintLayoutHelmintosTeniase.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            constraintLayoutHelmintosCisticercose.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            constraintLayoutHelmintosHidatidose.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            constraintLayoutHelmintosHimenolepíase.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            constraintLayoutHelmintosEstrongiloidiase.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            constraintLayoutHelmintosTricuriase.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            constraintLayoutHelmintosAncilostomiase.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            constraintLayoutHelmintosNecatoriase.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            constraintLayoutHelmintosEnterobiase.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            constraintLayoutHelmintosAscaridiase.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            constraintLayoutHelmintosLarvaMigrans.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            constraintLayoutHelmintosFilarioses.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            constraintLayoutHelmintosOutrasHelmintoses.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            break;
//                        case 100:
//                            constraintLayoutHelmintosEsquistossomose.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
//                            constraintLayoutHelmintosFascioliase.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
//                            constraintLayoutHelmintosTeniase.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
//                            constraintLayoutHelmintosCisticercose.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
//                            constraintLayoutHelmintosHidatidose.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
//                            constraintLayoutHelmintosHimenolepíase.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
//                            constraintLayoutHelmintosEstrongiloidiase.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
//                            constraintLayoutHelmintosTricuriase.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
//                            constraintLayoutHelmintosAncilostomiase.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
//                            constraintLayoutHelmintosNecatoriase.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
//                            constraintLayoutHelmintosEnterobiase.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
//                            constraintLayoutHelmintosAscaridiase.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
//                            constraintLayoutHelmintosLarvaMigrans.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
//                            constraintLayoutHelmintosFilarioses.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
//                            constraintLayoutHelmintosOutrasHelmintoses.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
//                            break;
                    }
                } else if (progressBarArtropodesValue <= 100) {
                    setProgressIntroduction();
                    setProgressProtozoarios();
                    setProgressHelmintos();
                    constraintLayoutArtropodes.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                    switch (progressBarArtropodesValue) {
                        case artropodesIntroducaoConclude :
                            constraintLayoutArtropodesHemipteros.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            break;
                        case artropodesHemipterosConclude :
                            constraintLayoutArtropodesHemipteros.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            constraintLayoutArtropodesMosquitos.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            break;
                        case artropodesMosquitosConclude :
                            constraintLayoutArtropodesHemipteros.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            constraintLayoutArtropodesMosquitos.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            constraintLayoutArtropodesMoscas.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            break;
                        case artropodesMoscasConclude :
                            constraintLayoutArtropodesHemipteros.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            constraintLayoutArtropodesMosquitos.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            constraintLayoutArtropodesMoscas.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            constraintLayoutArtropodesEctoparasitos.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            break;
                        case artropodesEctoparasitosConclude :
                            constraintLayoutArtropodesHemipteros.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            constraintLayoutArtropodesMosquitos.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            constraintLayoutArtropodesMoscas.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            constraintLayoutArtropodesEctoparasitos.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            //TODO: user finished all progress.
                            break;
                    }
                }
            }
        });

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

        Log.d("HomeFragment", "Next Category's id received here: " + nextCategoryId);
        Log.d("HomeFragment", "Category's parent received here: " + parentCategory);
        Log.d("HomeFragment", "O progresso concluído é: " + progressToGo);
        Log.d("HomeFragment", "O nome da categoria que estamos é: " + category);


        if (parentCategory == null && progressToGo == 0 && nextCategoryId == 0) {
            Log.i("HomeFragment", "esse é o primeiro onResume!");
        } else {
            Log.i("HomeFragment", "outros onResume!");
            DocumentReference docRef = firebaseFirestore.collection("generalUserInfo").document(email).collection("specific info").document("progress categories");
            docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    setCategoryStatus(documentSnapshot.getBoolean(getCategory()));
                    Log.i("HomeFragment", "solicitou status da categoria do banco de dados");

                    Log.d("HomeFragment", "Status da categoria: " + categoryStatus);
                    if (categoryStatus == false) { //não completou a categoria
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
                        switch (nextCategoryId) {
                            //introdução
                            case R.id.categoryHomeFragmentIntroductionEcologia:
                                constraintLayoutIntroductionEcologia.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                                break;
                            case R.id.categoryHomeFragmentIntroductionConceitosGerais:
                                constraintLayoutIntroductionConceitos.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                                break;
                            case R.id.categoryHomeFragmentIntroductionClassificacao:
                                constraintLayoutIntroductionClassificacao.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                                break;
                            case R.id.categoryHomeFragmentIntroductionReproducao:
                                constraintLayoutIntroductionReproducao.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                                break;
                            case R.id.categoryHomeFragmentIntroductionCicloBiologico:
                                constraintLayoutIntroductionCiclo.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                                break;
                            case R.id.categoryHomeFragmentIntroductionAtualidades:
                                constraintLayoutIntroductionAtualidades.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                                break;
                            //protozoários
                            case R.id.categoryHomeFragmentProtozoarios:
                                constraintLayoutProtozoarios.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                                break;
                            case R.id.categoryHomeFragmentProtozoariosAmebiase:
                                constraintLayoutProtozoariosAmebiase.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                                break;
                            case R.id.categoryHomeFragmentProtozoariosGiardiase:
                                constraintLayoutProtozoariosGiardiase.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                                break;
                            case R.id.categoryHomeFragmentProtozoariosLeishmanioses:
                                constraintLayoutProtozoariosLeishmanioses.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                                break;
                            case R.id.categoryHomeFragmentProtozoariosTricomonose:
                                constraintLayoutProtozoariosTricomonose.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                                break;
                            case R.id.categoryHomeFragmentProtozoariosChagas:
                                constraintLayoutProtozoariosChagas.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                                break;
                            case R.id.categoryHomeFragmentProtozoariosMalaria:
                                constraintLayoutProtozoariosMalaria.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                                break;
                            case R.id.categoryHomeFragmentProtozoariosToxoplasmose:
                                constraintLayoutProtozoariosToxoplasmose.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                                break;
                            case R.id.categoryHomeFragmentProtozoariosBalantidiase:
                                constraintLayoutProtozoariosBalantidiase.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                                break;
                            case R.id.categoryHomeFragmentProtozoariosProtozooses:
                                constraintLayoutProtozoariosProtozooses.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                                break;
                            //helmintos
                            case R.id.categoryHomeFragmentHelmintos:
                                constraintLayoutHelmintos.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                                break;
                            case R.id.categoryHomeFragmentHelmintosEsquistossomose:
                                constraintLayoutHelmintosEsquistossomose.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                                break;
                            case R.id.categoryHomeFragmentHelmintosFascioliase:
                                constraintLayoutHelmintosFascioliase.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                                break;
                            case R.id.categoryHomeFragmentHelmintosTeniase:
                                constraintLayoutHelmintosTeniase.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                                break;
                            case R.id.categoryHomeFragmentHelmintosCisticercose:
                                constraintLayoutHelmintosCisticercose.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                                break;
                            case R.id.categoryHomeFragmentHelmintosHidatidose:
                                constraintLayoutHelmintosHidatidose.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                                break;
                            case R.id.categoryHomeFragmentHelmintosHimenolepiase:
                                constraintLayoutHelmintosHimenolepíase.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                                break;
                            case R.id.categoryHomeFragmentHelmintosEstrongiloidiase:
                                constraintLayoutHelmintosEstrongiloidiase.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                                break;
                            case R.id.categoryHomeFragmentHelmintosTricuriase:
                                constraintLayoutHelmintosTricuriase.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                                break;
                            case R.id.categoryHomeFragmentHelmintosAncilostomiase:
                                constraintLayoutHelmintosAncilostomiase.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                                break;
                            case R.id.categoryHomeFragmentHelmintosNecatoriase:
                                constraintLayoutHelmintosNecatoriase.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                                break;
                            case R.id.categoryHomeFragmentHelmintosEnterobiase:
                                constraintLayoutHelmintosEnterobiase.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                                break;
                            case R.id.categoryHomeFragmentHelmintosAscaridiase:
                                constraintLayoutHelmintosAscaridiase.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                                break;
                            case R.id.categoryHomeFragmentHelmintosLarvaMigrans:
                                constraintLayoutHelmintosLarvaMigrans.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                                break;
                            case R.id.categoryHomeFragmentHelmintosFilarioses:
                                constraintLayoutHelmintosFilarioses.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                                break;
                            case R.id.categoryHomeFragmentHelmintosOutrasHelmintoses:
                                constraintLayoutHelmintosOutrasHelmintoses.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                                break;
                            //artrópodes
                            case R.id.categoryHomeFragmentArtropodes:
                                constraintLayoutArtropodes.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                                break;
                            case R.id.categoryHomeFragmentArtropodesHemipteros:
                                constraintLayoutArtropodesHemipteros.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                                break;
                            case R.id.categoryHomeFragmentArtropodesMosquitos:
                                constraintLayoutArtropodesMosquitos.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                                break;
                            case R.id.categoryHomeFragmentArtropodesMoscas:
                                constraintLayoutArtropodesMoscas.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                                break;
                            case R.id.categoryHomeFragmentArtropodesEctoparasitos:
                                constraintLayoutArtropodesEctoparasitos.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                                break;

                        }

                        Log.d("HomeFragment", "Next Category's id received here: " + nextCategoryId);
                        Log.d("HomeFragment", "Category's parent received here: " + parentCategory);
                        Log.d("HomeFragment", "O progresso concluído é: " + progressToGo);


                        Map<String, Object> newStatusCategory = new HashMap<>();
                        newStatusCategory.put(category, true);
                        firebaseFirestore.collection("generalUserInfo").document(email).collection("specific info").document("progress categories")
                                .update(newStatusCategory);
                    }
                }
            });

        }
    }

    public void setProgressBarIntroductionValue(int progressBarIntroductionVaIntroductionlue) {
        this.progressBarIntroductionValue = progressBarIntroductionValue;
    }

    public void setProgressBarProtozoariosValue(int progressBarProtozoariosValue) {
        this.progressBarProtozoariosValue = progressBarProtozoariosValue;
    }

    public void setProgressBarHelmintosValue(int progressBarHelmintosValue) {
        this.progressBarHelmintosValue = progressBarHelmintosValue;
    }

    public void setProgressBarArtropodesValue(int progressBarArtropodesValue) {
        this.progressBarArtropodesValue = progressBarArtropodesValue;
    }

    public void setProgressIntroduction(){
        // DRY measure
        constraintLayoutIntroduction.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
        constraintLayoutIntroductionEcologia.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
        constraintLayoutIntroductionConceitos.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
        constraintLayoutIntroductionClassificacao.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
        constraintLayoutIntroductionReproducao.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
        constraintLayoutIntroductionCiclo.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
        constraintLayoutIntroductionAtualidades.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
    }
    public void setProgressProtozoarios(){
        // DRY measure
        constraintLayoutProtozoarios.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
        constraintLayoutProtozoariosAmebiase.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
        constraintLayoutProtozoariosGiardiase.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
        constraintLayoutProtozoariosLeishmanioses.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
        constraintLayoutProtozoariosTricomonose.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
        constraintLayoutProtozoariosChagas.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
        constraintLayoutProtozoariosMalaria.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
        constraintLayoutProtozoariosToxoplasmose.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
        constraintLayoutProtozoariosBalantidiase.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
        constraintLayoutProtozoariosProtozooses.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
    }
    public void setProgressHelmintos(){
        // DRY measure
        constraintLayoutHelmintos.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
        constraintLayoutHelmintosEsquistossomose.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
        constraintLayoutHelmintosFascioliase.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
        constraintLayoutHelmintosTeniase.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
        constraintLayoutHelmintosCisticercose.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
        constraintLayoutHelmintosHidatidose.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
        constraintLayoutHelmintosHimenolepíase.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
        constraintLayoutHelmintosEstrongiloidiase.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
        constraintLayoutHelmintosTricuriase.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
        constraintLayoutHelmintosAncilostomiase.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
        constraintLayoutHelmintosNecatoriase.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
        constraintLayoutHelmintosEnterobiase.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
        constraintLayoutHelmintosAscaridiase.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
        constraintLayoutHelmintosLarvaMigrans.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
        constraintLayoutHelmintosFilarioses.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
        constraintLayoutHelmintosOutrasHelmintoses.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
    }

    public void setCategoryStatus(boolean categoryStatus) {
        this.categoryStatus = categoryStatus;
    }
    public boolean isCategoryStatus() {
        return categoryStatus;
    }

    public String getCategory() {
        return category;
    }
}
