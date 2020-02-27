package com.android.parasitologymobilesoftware;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.view.*;
import android.webkit.WebView;
import android.widget.ScrollView;
import android.widget.SearchView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;
import com.matthewtamlin.sliding_intro_screen_library.indicators.DotIndicator;

public class SubjectActivity extends AppCompatActivity {

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject);


        final Toolbar toolbar = findViewById(R.id.toolbarSubject);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        final DotIndicator dotIndicator = findViewById(R.id.dotIndicatorSubject);

        final ViewPager viewPager = findViewById(R.id.viewPagerSubject);
        viewPager.setAdapter(new ImageSubjectFragmentPagerAdapter(getSupportFragmentManager()));
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                dotIndicator.setSelectedItem(position, true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        webView = findViewById(R.id.webViewSubject);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setAppCacheEnabled(true);
        webView.getSettings().setLoadsImagesAutomatically(true);

        String index = getIntent().getStringExtra("index");
        int id = getIntent().getIntExtra("id", 1);

        if (id == R.id.categoryHomeFragmentArtropodes1) {
            webView.findAllAsync("1.1. Mosquitos");
            webView.findNext(true);
        }
        else if(id == R.id.categoryHomeFragmentArtropodes2) {
            webView.findAllAsync("1.2. Besouros");
            webView.findNext(true);
        }
        else if(id == R.id.categoryHomeFragmentArtropodes3) {
            webView.findAllAsync("1.3. Moscas");
            webView.findNext(true);
        }
        webView.loadUrl("file:///android_asset/".concat(index));

        webView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View view, int i, int i1, int i2, int i3) {
            }
        });
        webView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return (motionEvent.getAction() == MotionEvent.ACTION_MOVE);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.app_bar_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                if(s != null && !s.isEmpty()) {
                    webView.findAllAsync(s);
                    webView.findNext(true);
                }
                else {
                    return false;
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();


        return super.onOptionsItemSelected(item);
    }

}
