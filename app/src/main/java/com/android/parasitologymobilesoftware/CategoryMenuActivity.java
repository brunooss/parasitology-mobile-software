package com.android.parasitologymobilesoftware;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class CategoryMenuActivity extends AppCompatActivity {

    private static String category;
    private static int categoryId;

    private TextView textViewCategoryMenuTextBook, textViewCategoryMenuVideos,
            textViewCategoryMenuExercises, textViewCategoryMenuForum;

    private ProgressBar progressBarCategoryMenuTextBook, progressBarCategoryMenuVideos,
            progressBarCategoryMenuExercises, progressBarCategoryMenuForum;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_menu);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            category = extras.getString("category");
            categoryId = extras.getInt("categoryId");
        }


        // Toolbar config
        final Toolbar toolbar = findViewById(R.id.toolbarSubjectCategoryMenu);
        toolbar.setTitle(category);
        toolbar.setTitleTextAppearance(this, R.style.FuturaMediumTextAppearance);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        // TextViews
        textViewCategoryMenuTextBook = findViewById(R.id.textViewMenuCategoryTextBook);
        textViewCategoryMenuVideos = findViewById(R.id.textViewMenuCategoryVideos);
        textViewCategoryMenuExercises = findViewById(R.id.textViewMenuCategoryExercises);
        textViewCategoryMenuForum = findViewById(R.id.textViewMenuCategoryForum);

        // Progress Bars
        progressBarCategoryMenuTextBook = findViewById(R.id.progressBarMenuCategoryTextBook);
        progressBarCategoryMenuVideos = findViewById(R.id.progressBarMenuCategoryVideos);
        progressBarCategoryMenuExercises = findViewById(R.id.progressBarMenuCategoryExercises);
        progressBarCategoryMenuForum = findViewById(R.id.progressBarMenuCategoryForum);

    }

    @Override
    protected void onResume() {
        super.onResume();

        // UX/UI settings
        textViewCategoryMenuTextBook.setVisibility(View.VISIBLE);
        progressBarCategoryMenuTextBook.setVisibility(View.INVISIBLE);

        textViewCategoryMenuVideos.setVisibility(View.VISIBLE);
        progressBarCategoryMenuVideos.setVisibility(View.INVISIBLE);

        textViewCategoryMenuExercises.setVisibility(View.VISIBLE);
        progressBarCategoryMenuExercises.setVisibility(View.INVISIBLE);

        textViewCategoryMenuForum.setVisibility(View.VISIBLE);
        progressBarCategoryMenuForum.setVisibility(View.INVISIBLE);
    }



    // Functions ...



    // OnClickListener Functions ...

    public void onButtonCategoryTextBook(View view) {
        // UX/UI settings
        textViewCategoryMenuTextBook.setVisibility(View.INVISIBLE);
        progressBarCategoryMenuTextBook.setVisibility(View.VISIBLE);

        Intent intent = new Intent(this, CategoryTextBookActivity.class);
        Bundle data = new Bundle();

        data.putString("category", category);
        data.putInt("categoryId", categoryId);

        intent.putExtras(data);

        startActivity(intent);
    }

    public void onButtonCategoryVideos(View view) {
        // UX/UI settings
        textViewCategoryMenuVideos.setVisibility(View.INVISIBLE);
        progressBarCategoryMenuVideos.setVisibility(View.VISIBLE);

        Intent intent = new Intent(this, CategoryVideosActivity.class);

        Bundle data = new Bundle();

        data.putString("category", category);
        data.putInt("categoryId", categoryId);

        intent.putExtras(data);

        startActivity(intent);
    }

    public void onButtonCategoryExercises(View view) {
        // UX/UI settings
        textViewCategoryMenuExercises.setVisibility(View.INVISIBLE);
        progressBarCategoryMenuExercises.setVisibility(View.VISIBLE);

        Intent intent = new Intent(this, CategoryExercisesActivity.class);

        Bundle data = new Bundle();

        data.putString("category", category);
        data.putInt("categoryId", categoryId);

        intent.putExtras(data);

        startActivity(intent);
    }


    public void onButtonCategoryForum(View view) {

    }
}
