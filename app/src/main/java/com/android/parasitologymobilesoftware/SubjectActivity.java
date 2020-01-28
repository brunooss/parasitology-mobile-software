package com.android.parasitologymobilesoftware;

import android.webkit.WebView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;

public class SubjectActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject);

        Toolbar toolbar = findViewById(R.id.toolbarSubject);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        WebView webView = findViewById(R.id.webViewSubject);
        webView.getSettings().setJavaScriptEnabled(true);

        int id = getIntent().getIntExtra("id", 1);

        if(id == R.id.categoryHomeFragmentArtropodes1)
            webView.scrollTo(0, 0);
        else if(id == R.id.categoryHomeFragmentArtropodes2)
            webView.scrollTo(0, 600);
        else if(id == R.id.categoryHomeFragmentArtropodes3)
            webView.scrollTo(0, 1400);

        webView.loadUrl("file:///android_asset/index.html");
    }
}
