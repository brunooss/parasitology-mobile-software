package com.android.parasitologymobilesoftware;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

import androidx.appcompat.widget.Toolbar;

public class AboutActivity extends AppCompatActivity {
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        String stringTest = "O &quot;Parasite Combat&quot; é um aplicativo cujo intuito é auxiliar os alunos a estudarem parasitologia de uma maneira mais rápida, eficaz e interativa!";


        final Toolbar toolbar = findViewById(R.id.toolbarAbout);
        toolbar.setTitle("SOBRE");
        toolbar.setTitleTextAppearance(this, R.style.FuturaMediumTextAppearance);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


    }
}
