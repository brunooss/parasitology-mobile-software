package com.android.parasitologymobilesoftware;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.viewpager.widget.ViewPager;
import com.matthewtamlin.sliding_intro_screen_library.indicators.DotIndicator;

public class IntroductionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introduction);
        ViewPager viewPager = findViewById(R.id.viewPagerIntroduction);
        viewPager.setOffscreenPageLimit(3);
        viewPager.setAdapter(new IntroductionTabsAdapter(getSupportFragmentManager()));
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                DotIndicator dotIndicator = findViewById(R.id.dotIndicatorIntroduction);
                dotIndicator.setSelectedItem(position, true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}
