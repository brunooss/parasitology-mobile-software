package com.android.parasitologymobilesoftware;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.*;
import android.webkit.WebView;
import android.widget.*;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.ViewPager;
import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;
import com.google.android.material.navigation.NavigationView;
import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Calendar;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Handler handler;

    private int progressStatus = 0;
    private ProgressBar progressBar;
    private Button buttonAlert;
    private Button buttonDate;
    boolean alert = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("");

        setSupportActionBar(toolbar);

        ViewPager viewPager = findViewById(R.id.viewPagerHome);
        viewPager.setOffscreenPageLimit(2);
        viewPager.setAdapter(new HomeTabsAdapter(getSupportFragmentManager()));
        viewPager.setCurrentItem(1);

        TabLayout tabLayout = findViewById(R.id.tabLayoutHome);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.getTabAt(0).setIcon(R.drawable.ic_person_white_24dp);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_home_white_24dp);
        tabLayout.getTabAt(2).setIcon(R.drawable.ic_book_white_24dp);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        // if (id == R.id.action_settings) {return true; }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_tools) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void onSignOutButtonClick(View view) {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(this, SigninActivity.class));
        finish();
    }

    public void onSetAlertButtonClick(View view) {
        buttonAlert = (Button) findViewById(R.id.buttonStudentPreferencesSetAlert);
        buttonDate = (Button) findViewById(R.id.buttonStudentPreferencesSetDate);
        if (alert == true) {
            buttonAlert.setBackground(getResources().getDrawable(R.drawable.custom_linear_layout_alert_false, getTheme()));
            alert = false;
        } else {
            buttonAlert.setBackground(getResources().getDrawable(R.drawable.custom_linear_layout_alert_true, getTheme()));
            alert = true;
        }

//        final Calendar calendar = Calendar.getInstance();
//        int hour = calendar.get(Calendar.HOUR_OF_DAY);
//        int minute = calendar.get(Calendar.MINUTE);
//
//        TimePickerDialog timePickerDialog = new TimePickerDialog(this, R.style.PickerTheme, new TimePickerDialog.OnTimeSetListener() {
//            @Override
//            public void onTimeSet(TimePicker timePicker, int i, int i1) {
//
//            }
//        }, hour, minute, true);
//        timePickerDialog.show();
    }

    public void onSetDateButtonClick(View view) {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, R.style.PickerTheme, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {

            }
        }, year, month, day);
        datePickerDialog.show();
    }

    public void onButtonTest(View view){
        progressBar = (ProgressBar) findViewById(R.id.progressApp);
        if (progressBar.getProgress() == 100)
            settingProgress(0);
        else
            settingProgress(100);
    }

    public void settingProgress(int progressToGo){                      // Call this function to change the bar progress, passing an int variable that goes from 0 to 100
        progressBar = (ProgressBar) findViewById(R.id.progressApp);
        progressBar.setProgress(progressToGo, true);
    }

    public void onCategoryButtonClick(View view) {
        Intent intent = new Intent(this, SubjectActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("id", view.getId());
        intent.putExtras(bundle);
        startActivity(intent);

    }
}
