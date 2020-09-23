package com.android.parasitologymobilesoftware;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // This code snippet answers the following question: we want to show the exercises of which category?
        Bundle extras = getIntent().getExtras();
        category = extras.getString("category");
        categoryId = extras.getInt("categoryId");
        Log.i(TAG, "Category received: " +category);
        Log.i(TAG, "Category Id received: " +categoryId);


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
                    Log.i(TAG, "Número de exercícios: " +numberOfExercises);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public static int getNumberOfExercises() {
        return numberOfExercises;
    }
}
