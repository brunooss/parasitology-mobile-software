package com.android.parasitologymobilesoftware;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;
import com.android.MyApplication;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

public class CategoryExercisesActivity extends AppCompatActivity {

    // Our Activity TAG to LOG
    private static final String TAG = "CategoryExercisesActivity";

    // JSON data
    public static int numberOfExercises;

    // JSON Exercises attributes TODO: move it to FRAGMENT
    private String type, title, body, source, correctChoice, comment;
    private String[] choices;

    // Parasitology Category
    private String category;
    private int categoryId;

    private AlertDialog.Builder exitAlert;
    private AlertDialog alertDialogExit;
    private View dialogExitActivity;
    private TextView textViewMessageExitActivity;
    private String firstMessage = "Tem certeza que deseja abandonar ";
    private String secondMessage;
    private String thirdMessage = "? Seu progresso nos exercícios desta categoria será perdido";
    private ViewPager viewPager;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_category_exercises);

        // This code snippet answers the following question: we want to show the exercises of which category?
        Bundle extras = getIntent().getExtras();
        category = extras.getString("category");
        categoryId = extras.getInt("categoryId");
        Log.i(TAG, "Category received: " +category);
        Log.i(TAG, "Category Id received: " +categoryId);

        // Exit Alert Dialog config
        secondMessage = category;
        LayoutInflater inflater = getLayoutInflater();
        dialogExitActivity = inflater.inflate(R.layout.dialog_change_exit_activity, null);
        textViewMessageExitActivity = dialogExitActivity.findViewById(R.id.textViewDialogMessageExitActivity);
        textViewMessageExitActivity.setText(firstMessage.concat(secondMessage.concat(thirdMessage)));
        exitAlert = new AlertDialog.Builder(CategoryExercisesActivity.this, R.style.Theme_AppCompat_Light_Dialog_Alert)
                .setView(dialogExitActivity);
        exitAlert.setNegativeButton("Retomar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        }).setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });
        alertDialogExit = exitAlert.create();

        // Changing dialog button text color
        alertDialogExit.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                alertDialogExit.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getColor(R.color.colorAccent));
                alertDialogExit.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(getColor(R.color.colorAccent));
            }
        });


        // Toolbar config
        final Toolbar toolbar = findViewById(R.id.toolbarExercises);
        toolbar.setTitle(category);
        toolbar.setTitleTextAppearance(this, R.style.FuturaMediumTextAppearance);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialogExit.show();
            }
        });

        // Taking number of exercises from JSON - tabs we will need to implement with pagerAdapter
        try {
            InputStream inputStream;
            inputStream = MyApplication.getMyApplicationContext().getAssets().open("fragments_settings.json");
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            String JSON = new String(buffer, "UTF-8");
            JSONArray jsonArray = new JSONArray(JSON);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                if (jsonObject.getString("categoryName").equals(category)) {
                    JSONArray exercisesArray = jsonObject.getJSONArray("exercises");
                    numberOfExercises = exercisesArray.length();
                    Log.i(TAG, "Número de exercícios: " + numberOfExercises);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }


        viewPager = findViewById(R.id.viewPagerExercises);
        viewPager.setAdapter(new CategoryTextBookFragmentPagerAdapter(getSupportFragmentManager()));

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }

        });

    }

    public static int getNumberOfExercises() {
        return numberOfExercises;
    }



    public void nextPage(View view) {
        viewPager.setCurrentItem(viewPager.getCurrentItem() + 1, true);
    }

    public void previousPage(View view) {
        viewPager.setCurrentItem(viewPager.getCurrentItem() - 1, true);
    }

}
