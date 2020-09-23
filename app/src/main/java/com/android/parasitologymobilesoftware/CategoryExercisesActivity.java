package com.android.parasitologymobilesoftware;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class CategoryExercisesActivity extends AppCompatActivity {

    // Our Activity TAG to LOG
    private static final String TAG = "CategoryExercisesActivity";

    // JSON Exercises attributes
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

    }
}
