package com.android.parasitologymobilesoftware;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
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

import java.util.concurrent.ConcurrentNavigableMap;

public class HomeFragment extends Fragment {

    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;

    private String email, nome;

    private ProgressBar progressBarIntroduction, progressBarProtozoarios,
            progressBarHelmintos, progressBarArtropodes;

    private int progressBarIntroductionValue, progressBarProtozoariosValue,
            progressBarHelmintosValue, progressBarArtropodesValue;


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
        ImageView teste = constraintLayoutIntroductionEcologia.findViewById(R.id.imageViewCategoryBackgroundIcon);
        teste.setImageDrawable(getActivity().getDrawable(R.drawable.icons8_happy_100));

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
                        case 14 :
                            constraintLayoutIntroduction.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            constraintLayoutIntroductionEcologia.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            constraintLayoutIntroductionEcologia.setElevation(4);
                            break;
                        case 28 :
                            constraintLayoutIntroduction.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            constraintLayoutIntroductionEcologia.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            constraintLayoutIntroductionConceitos.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            break;
                        case 42 :
                            constraintLayoutIntroduction.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            constraintLayoutIntroductionEcologia.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            constraintLayoutIntroductionConceitos.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            constraintLayoutIntroductionClassificacao.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            break;
                        case 56 :
                            constraintLayoutIntroduction.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            constraintLayoutIntroductionEcologia.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            constraintLayoutIntroductionConceitos.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            constraintLayoutIntroductionClassificacao.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            constraintLayoutIntroductionReproducao.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            break;
                        case 70 :
                            constraintLayoutIntroduction.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            constraintLayoutIntroductionEcologia.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            constraintLayoutIntroductionConceitos.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            constraintLayoutIntroductionClassificacao.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            constraintLayoutIntroductionReproducao.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            constraintLayoutIntroductionCiclo.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            break;
                        case 85 :
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
//                            constraintLayoutIntroductionReproducao.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
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
                        case 10 :
                            constraintLayoutProtozoariosAmebiase.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            break;
                        case 20 :
                            constraintLayoutProtozoariosAmebiase.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            constraintLayoutProtozoariosGiardiase.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            break;
                        case 30 :
                            constraintLayoutProtozoariosAmebiase.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            constraintLayoutProtozoariosGiardiase.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            constraintLayoutProtozoariosLeishmanioses.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            break;
                        case 40 :
                            constraintLayoutProtozoariosAmebiase.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            constraintLayoutProtozoariosGiardiase.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            constraintLayoutProtozoariosLeishmanioses.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            constraintLayoutProtozoariosTricomonose.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            break;
                        case 50 :
                            constraintLayoutProtozoariosAmebiase.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            constraintLayoutProtozoariosGiardiase.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            constraintLayoutProtozoariosLeishmanioses.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            constraintLayoutProtozoariosTricomonose.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            constraintLayoutProtozoariosChagas.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            break;
                        case 60 :
                            constraintLayoutProtozoariosAmebiase.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            constraintLayoutProtozoariosGiardiase.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            constraintLayoutProtozoariosLeishmanioses.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            constraintLayoutProtozoariosTricomonose.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            constraintLayoutProtozoariosChagas.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            constraintLayoutProtozoariosMalaria.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            break;
                        case 70 :
                            constraintLayoutProtozoariosAmebiase.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            constraintLayoutProtozoariosGiardiase.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            constraintLayoutProtozoariosLeishmanioses.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            constraintLayoutProtozoariosTricomonose.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            constraintLayoutProtozoariosChagas.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            constraintLayoutProtozoariosMalaria.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            constraintLayoutProtozoariosToxoplasmose.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            break;
                        case 80 :
                            constraintLayoutProtozoariosAmebiase.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            constraintLayoutProtozoariosGiardiase.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            constraintLayoutProtozoariosLeishmanioses.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            constraintLayoutProtozoariosTricomonose.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            constraintLayoutProtozoariosChagas.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            constraintLayoutProtozoariosMalaria.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            constraintLayoutProtozoariosToxoplasmose.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            constraintLayoutProtozoariosBalantidiase.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            break;
                        case 90 :
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
                        case 6 :
                            constraintLayoutHelmintosEsquistossomose.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            break;
                        case 12 :
                            constraintLayoutHelmintosEsquistossomose.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            constraintLayoutHelmintosFascioliase.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            break;
                        case 18 :
                            constraintLayoutHelmintosEsquistossomose.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            constraintLayoutHelmintosFascioliase.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            constraintLayoutHelmintosTeniase.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            break;
                        case 24 :
                            constraintLayoutHelmintosEsquistossomose.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            constraintLayoutHelmintosFascioliase.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            constraintLayoutHelmintosTeniase.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            constraintLayoutHelmintosCisticercose.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            break;
                        case 30 :
                            constraintLayoutHelmintosEsquistossomose.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            constraintLayoutHelmintosFascioliase.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            constraintLayoutHelmintosTeniase.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            constraintLayoutHelmintosCisticercose.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            constraintLayoutHelmintosHidatidose.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            break;
                        case 36 :
                            constraintLayoutHelmintosEsquistossomose.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            constraintLayoutHelmintosFascioliase.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            constraintLayoutHelmintosTeniase.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            constraintLayoutHelmintosCisticercose.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            constraintLayoutHelmintosHidatidose.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            constraintLayoutHelmintosHimenolepíase.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            break;
                        case 42 :
                            constraintLayoutHelmintosEsquistossomose.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            constraintLayoutHelmintosFascioliase.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            constraintLayoutHelmintosTeniase.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            constraintLayoutHelmintosCisticercose.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            constraintLayoutHelmintosHidatidose.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            constraintLayoutHelmintosHimenolepíase.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            constraintLayoutHelmintosEstrongiloidiase.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            break;
                        case 48 :
                            constraintLayoutHelmintosEsquistossomose.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            constraintLayoutHelmintosFascioliase.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            constraintLayoutHelmintosTeniase.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            constraintLayoutHelmintosCisticercose.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            constraintLayoutHelmintosHidatidose.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            constraintLayoutHelmintosHimenolepíase.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            constraintLayoutHelmintosEstrongiloidiase.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            constraintLayoutHelmintosTricuriase.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            break;
                        case 54 :
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
                        case 60 :
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
                        case 66 :
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
                        case 72 :
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
                        case 79 :
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
                        case 86 :
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
                        case 93 :
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
                        case 20 :
                            constraintLayoutArtropodesHemipteros.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            break;
                        case 40 :
                            constraintLayoutArtropodesHemipteros.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            constraintLayoutArtropodesMosquitos.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            break;
                        case 60 :
                            constraintLayoutArtropodesHemipteros.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            constraintLayoutArtropodesMosquitos.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            constraintLayoutArtropodesMoscas.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            break;
                        case 80 :
                            constraintLayoutArtropodesHemipteros.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            constraintLayoutArtropodesMosquitos.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            constraintLayoutArtropodesMoscas.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            constraintLayoutArtropodesEctoparasitos.setBackground(getActivity().getDrawable(R.drawable.category_button_background_filled));
                            break;
                        case 100 :
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

    public void setProgressBarIntroductionValue(int progressBarIntroductionValue) {
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
}
