package com.android.parasitologymobilesoftware;

import android.view.View;
import android.view.ViewTreeObserver;
import android.webkit.WebView;
import android.widget.ScrollView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;
import com.matthewtamlin.sliding_intro_screen_library.indicators.DotIndicator;

public class SubjectActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject);

        Toolbar toolbar = findViewById(R.id.toolbarSubject);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        WebView webView = findViewById(R.id.webViewSubject);
        webView.getSettings().setJavaScriptEnabled(true);

        final ScrollView scrollView = findViewById(R.id.scrollViewSubject);

        int id = getIntent().getIntExtra("id", 1);

        if (id == R.id.categoryHomeFragmentArtropodes1) {
            scrollView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    scrollView.post(new Runnable() {
                        public void run() {
                            scrollView.scrollTo(0, 0);
                        }
                    });
                }
            });
        }
        else if(id == R.id.categoryHomeFragmentArtropodes2) {
            scrollView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    scrollView.post(new Runnable() {
                        public void run() {
                            scrollView.scrollTo(0, 600);
                        }
                    });
                }
            });
        }
        else if(id == R.id.categoryHomeFragmentArtropodes3) {
            scrollView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    scrollView.post(new Runnable() {
                        public void run() {
                            scrollView.scrollTo(0, 1400);
                        }
                    });
                }
            });
        }
        webView.loadUrl("file:///android_asset/index.html");

        // final DotIndicator dotIndicator = findViewById(R.id.dotIndicatorSubject);

        ViewPager viewPager = findViewById(R.id.viewPagerSubject);
        viewPager.setAdapter(new ImageSubjectFragmentPagerAdapter(getSupportFragmentManager()));
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                //dotIndicator.setSelectedItem(position, true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}
