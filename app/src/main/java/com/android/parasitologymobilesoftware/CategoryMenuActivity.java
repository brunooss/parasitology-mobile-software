package com.android.parasitologymobilesoftware;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class CategoryMenuActivity extends AppCompatActivity {

    private String category;
    private int categoryId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_menu);

        Bundle extras = getIntent().getExtras();
        category = extras.getString("category");
        categoryId = extras.getInt("categoryId");


        // Toolbar config
        final Toolbar toolbar = findViewById(R.id.toolbarSubject);
        toolbar.setTitle(category);
        toolbar.setTitleTextAppearance(this, R.style.FuturaMediumTextAppearance);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    public void onButtonCategoryTextBook(View view) {
        Intent intent = new Intent(this, CategoryTextBookActivity.class);
        Bundle data = new Bundle();

        data.putString("category", category);
        data.putInt("categoryId", categoryId);

        intent.putExtras(data);

        startActivity(intent);
    }

    public void onButtonCategoryVideos(View view) {
        Intent intent = new Intent(this, CategoryVideosActivity.class);
        startActivity(intent);
    }

    public void onButtonCategoryExercises(View view) {

    }

    public void onButtonCategoryForum(View view) {

    }
}
