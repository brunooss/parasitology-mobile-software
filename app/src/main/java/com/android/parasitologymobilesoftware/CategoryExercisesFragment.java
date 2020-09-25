package com.android.parasitologymobilesoftware;

import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.webkit.WebView;
import android.widget.*;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.android.MyApplication;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class CategoryExercisesFragment extends Fragment {

    static InputStream inputStream;
    private WebView webViewText;

    private static Button buttonNext;
    private static Button buttonPrevious;

    public RadioGroup radioGroup;
    public RadioButton alternativeA;
    public RadioButton alternativeB;
    public RadioButton alternativeC;
    public RadioButton alternativeD;
    public RadioButton alternativeE;

    private FirebaseFirestore dataBase;
    private FirebaseAuth firebaseAuth;

    private String email;

    private StudentPreferencesFragment studentPreferencesFragment;

    private static String categoryParent = null;
    private static int nextCategoryId = 0, concludeProgress = 0;

    private static boolean categoryStatus;

    private static int progressApp = -1;
    private String databaseCategoryProgress;

    public static String category = "notNull";
    public static int categoryId;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public CategoryExercisesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        dataBase = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();

        studentPreferencesFragment = new StudentPreferencesFragment();

        email = firebaseAuth.getCurrentUser().getEmail();

        final View rootView = inflater.inflate(R.layout.fragment_category_exercises, container, false);
        TextView textViewTitle = rootView.findViewById(R.id.textViewExercisesTitle);
        webViewText = rootView.findViewById(R.id.webViewExercisesText);
        ImageView imageView = rootView.findViewById(R.id.imageViewCategoryImage);
        buttonNext = rootView.findViewById(R.id.buttonExercisesGroup).findViewById(R.id.buttonExercisesNext);
        buttonPrevious = rootView.findViewById(R.id.buttonExercisesGroup).findViewById(R.id.buttonExercisesPrevious);
        radioGroup = rootView.findViewById(R.id.radioGroupExercisesAlternatives);
        alternativeA = rootView.findViewById(R.id.radioButtonA);
        alternativeB = rootView.findViewById(R.id.radioButtonB);
        alternativeC = rootView.findViewById(R.id.radioButtonC);
        alternativeD = rootView.findViewById(R.id.radioButtonD);
        alternativeE = rootView.findViewById(R.id.radioButtonE);

        if (getArguments().getString("type") != null) {
                textViewTitle.setText(Html.fromHtml(this.getArguments().getString("title"), Html.FROM_HTML_MODE_COMPACT));
                webViewText.loadData("<body><style>* { text-align: justify; }</style>" + this.getArguments().getString("body") + "</body>", "text/html", "UTF-8");
                webViewText.scrollTo(0, 0);
                alternativeA.setText(this.getArguments().getString("A"));
                alternativeB.setText(this.getArguments().getString("B"));
                alternativeC.setText(this.getArguments().getString("C"));
                alternativeD.setText(this.getArguments().getString("D"));
                alternativeE.setText(this.getArguments().getString("E"));
        }

        if (getArguments().getInt("position") == 0) {
            buttonPrevious.setBackgroundResource(R.drawable.custom_button_category_gray);
        }
        if (getArguments().getInt("number") == (getArguments().getInt("position") + 1)) {
            buttonNext.setBackgroundResource(R.drawable.custom_button_category_yellow);
            buttonNext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ProgressBar progressBar = rootView.findViewById(R.id.progressBarExercisesComplete);
                    progressBar.setVisibility(View.VISIBLE);
                    buttonNext.setClickable(false);
                    nextCategoryId = getArguments().getInt("nextCategoryId");
                    categoryParent = getArguments().getString("parentCategory");
                    setConcludeProgress(getArguments().getInt("concludeProgress"));

                    dataBase.collection("generalUserInfo").document(email).collection("specific info").document("progress categories")
                            .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            setCategoryStatus(documentSnapshot.getBoolean(category));

                            Log.i("CategoryTextBookFragment", "solicitou status da categoria do banco de dados");

                            Log.d("CategoryTextBookFragment", "Status da categoria: " + categoryStatus);

                            if (!categoryStatus) {

                                dataBase.collection("generalUserInfo").document(email).collection("specific info").document("progress")
                                        .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                    @Override
                                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                                        Log.d("CategoryTextBookFragment", ""+getConcludeProgress());
                                        switch (categoryParent) {
                                            case "Introdução":
                                                //setConcludeProgress(documentSnapshot.getLong("progress introduction").intValue());
                                                databaseCategoryProgress = "progress introduction";
                                                progressApp = concludeProgress / 4;
                                                break;
                                            case "Protozoários":
                                                //setConcludeProgress(documentSnapshot.getLong("progress protozoarios").intValue());
                                                databaseCategoryProgress = "progress protozoarios";
                                                progressApp = 25 + concludeProgress / 4;
                                                break;
                                            case "Helmintos":
                                                //setConcludeProgress(documentSnapshot.getLong("progress helmintos").intValue());
                                                databaseCategoryProgress = "progress helmintos";
                                                progressApp = 50 + concludeProgress / 4;
                                                break;
                                            case "Artrópodes":
                                                //setConcludeProgress(documentSnapshot.getLong("progress status").intValue());
                                                databaseCategoryProgress = "progress artropodes";
                                                progressApp = 75 + concludeProgress / 4;
                                                break;
                                        }
                                        studentPreferencesFragment.setProgressToGo(progressApp);

                                        Log.d("CategoryTextBookFragment", "Progresso do app: "+progressApp);
                                        Map<String, Object> newProgressInfo = new HashMap<>();
                                        newProgressInfo.put(databaseCategoryProgress, concludeProgress);
                                        newProgressInfo.put("progress status", progressApp);
                                        dataBase.collection("generalUserInfo").document(email).collection("specific info").document("progress")
                                                .update(newProgressInfo);
                                    }
                                });
                                Log.d("CategoryTextBookFragment", "Category's status: " + categoryStatus);
                                Log.d("CategoryTextBookFragment", "Next Category's id received here: " + nextCategoryId);
                                Log.d("CategoryTextBookFragment", "Category's parent received here: " + categoryParent);
                                Log.d("CategoryTextBookFragment", "O progresso concluído na categoria é: " + concludeProgress);
                            } else {
                                Log.i("CategoryTextBookFragment", "Esta categoria já está feita");
                            }
                        }
                    });
                    progressBar.setVisibility(View.INVISIBLE);
                    getActivity().finish();
                }
            });
            buttonNext.setText("Completar");
        }
        return rootView;
    }

    public static CategoryExercisesFragment newInstance(int position) {
        Bundle args = new Bundle();
        HomeFragment homeFragment = new HomeFragment();

        category = homeFragment.getCategory();
        categoryId = homeFragment.getCategoryId();
        Log.d("CategoryFragmentInstance", "Category received: " +category);
        Log.d("CategoryFragmentInstance", "Category Id received: " +categoryId);


        try {
            inputStream = MyApplication.getMyApplicationContext().getAssets().open("fragments_settings.json");
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            String JSON = new String(buffer, "UTF-8");
            JSONArray jsonArray = new JSONArray(JSON);


            for(int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                if(jsonObject.getString("categoryName").equals(category) && !(jsonObject.getJSONArray("exercises").length() == position)) {
                    JSONObject tab = jsonObject.getJSONArray("exercises").getJSONObject(position);
                    args.putString("title", tab.getString("title"));
                    args.putString("type", tab.getString("type"));
                    args.putString("body", tab.getString("body"));
                    args.putString("A", tab.getJSONObject("choices").getString("A"));
                    args.putString("B", tab.getJSONObject("choices").getString("B"));
                    args.putString("C", tab.getJSONObject("choices").getString("C"));
                    args.putString("D", tab.getJSONObject("choices").getString("D"));
                    args.putString("E", tab.getJSONObject("choices").getString("E"));
                    args.putString("correctChoice", tab.getString("correctChoice"));
                    args.putString("comment", tab.getString("comment"));
                    args.putInt("number", jsonObject.getJSONArray("exercises").length());
                    args.putString("parentCategory", jsonObject.getString("parentCategory"));
                    args.putInt("nextCategoryId", jsonObject.getInt("nextCategoryId"));
                    args.putInt("concludeProgress", jsonObject.getInt("concludeProgress"));

                    try {
                        tab.get("imageAddress");
                        args.putString("imageAddress", tab.getString("imageAddress"));
                    } catch (JSONException ignored) { }

                }
            }


        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(MyApplication.getMyApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();

        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(MyApplication.getMyApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
        }

        args.putInt("position", position);

        CategoryExercisesFragment fragment = new CategoryExercisesFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public void setCategoryStatus(boolean categoryStatus) {
        this.categoryStatus = categoryStatus;
    }

    public void setConcludeProgress(int concludeProgress) {
        this.concludeProgress = concludeProgress;
    }

    public String getCategoryParent() {
        return categoryParent;
    }

    public int getNextCategoryId() {
        return nextCategoryId;
    }

    public int getConcludeProgress() {
        return concludeProgress;
    }

    public boolean isCategoryStatus() {
        return categoryStatus;
    }

    public static String getCategory() {
        return category;
    }

    public static void setCategory(String category) {
        CategoryTextBookFragment.category = category;
    }
}
