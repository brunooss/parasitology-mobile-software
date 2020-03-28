package com.android.parasitologymobilesoftware;

import android.app.SearchManager;
import android.content.Context;
import android.util.Log;
import android.view.*;
import android.webkit.WebView;
import android.widget.SearchView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

public class CategoryActivity extends AppCompatActivity {

    //public String category = "Ascaridíase";
    public String category;
    private static final String TAG = "CategoryActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        Bundle extras = getIntent().getExtras();
        category = extras.getString("category");
        Log.d(TAG, "Category received: " +category);

        final Toolbar toolbar = findViewById(R.id.toolbarSubject);
        toolbar.setTitle(category);
        toolbar.setTitleTextAppearance(this, R.style.FuturaMediumTextAppearance);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        final ViewPager viewPager = findViewById(R.id.viewPagerSubject);
        viewPager.setAdapter(new CategoryFragmentPagerAdapter(getSupportFragmentManager()));


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
        }); //2643 x 1900

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
                    // @Todo
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
