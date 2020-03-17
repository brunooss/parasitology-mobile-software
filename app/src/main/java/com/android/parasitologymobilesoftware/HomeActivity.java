package com.android.parasitologymobilesoftware;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.icu.util.BuddhistCalendar;
import android.media.MediaPlayer;
import android.media.effect.Effect;
import android.os.Bundle;
import android.os.Handler;
import android.view.*;
import android.webkit.WebView;
import android.widget.*;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.ViewPager;
import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.firestore.auth.User;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private int progressStatus;
    private ProgressBar progressBar;

    private Button buttonSetCalendar;

    private Button buttonAlert;
    private boolean alertState;

    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore dataBase;

    private String email;
    private String schoolGrade;
    private String completeName;

    private ConstraintLayout constraintLayoutStudentFirst;
    private ConstraintLayout constraintLayoutStudentSecond;

    private View dialogView;

    private AlertDialog alertDialogFeedback;

    public String searchSubcategories[][] = {
            {"Protozoários", "Doença de Chagas", "Tricomonose", "Balantidíase","Malária", "Giardíase", "Leishmanioses"},
            {"Helmintos", "Esquistossomose mansoni", "Teníase", "Cisticercose", "Ascaridíase", "Enterobíase", "Hidatidose"},
            {"Artrópodes", "Hemípteros", "Mosquitos", "Moscas", "Piolhos", "Pulgas", "Ácaros"}
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);



        firebaseAuth = FirebaseAuth.getInstance();
        dataBase = FirebaseFirestore.getInstance();

        email = firebaseAuth.getCurrentUser().getEmail();
        completeName = firebaseAuth.getCurrentUser().getDisplayName();

        DocumentReference docRef = dataBase.collection("generalUserInfo").document(email);
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                setSchoolGrade(documentSnapshot.get("school grade").toString());
            }
        });

        docRef = docRef.collection("specific info").document("state");
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                setAlertState(documentSnapshot.getBoolean("alert state"));
            }
        });

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
        navigationView.setElevation(5);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        dialogView = getLayoutInflater().inflate(R.layout.dialog_feedback, null);

        AlertDialog.Builder builder;
        builder = new AlertDialog.Builder(this);
        builder.setView(dialogView);
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        }).setPositiveButton("Enviar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        alertDialogFeedback = builder.create();
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

        final ListView listView = findViewById(R.id.listViewHome);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String selected = adapterView.getItemAtPosition(i).toString();

                Intent intent = new Intent(getBaseContext(), SubjectActivity.class);
                if (selected.contains("Protozoários"))
                    intent.putExtra("index", "protozoarios.html");
                else if (selected.contains("Helmintos"))
                    intent.putExtra("index", "helmintos.html");
                else if (selected.contains("Artrópodes"))
                    intent.putExtra("index", "artropodes.html");

                startActivity(intent);
            }
        });

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.app_bar_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                if(s != null && !s.isEmpty()) {
                    ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<>(getBaseContext(), android.R.layout.simple_list_item_1);
                    for (String[] searchSubcategory : searchSubcategories) {
                        for (int j = 1; j <= searchSubcategory.length - 1; j++) {
                            if (searchSubcategory[j].toLowerCase().contains(s.toLowerCase()))
                                stringArrayAdapter.add(searchSubcategory[j] + "\nEm " + searchSubcategory[0]);
                        }
                    }
                    listView.setVisibility(View.VISIBLE);
                    listView.setAdapter(stringArrayAdapter);
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
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                listView.setVisibility(View.INVISIBLE);
                return true;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_about) {
            startActivity(new Intent(this, AboutActivity.class));
            // Open the AboutActivity //@Todo
        } else if (id == R.id.nav_feedback) {
            startActivity(new Intent(this, FeedbackActivity.class));
        } else if (id == R.id.nav_out) {
            Toast.makeText(this, "Aguarde, saindo de sua conta.", Toast.LENGTH_SHORT).show();
            FirebaseAuth.getInstance().signOut();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    finish();
                    System.exit(0);
                }
            }, 1000);
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
        buttonSetCalendar = (Button) findViewById(R.id.buttonStudentPreferencesSetDate);

        if (alertState) {
            buttonAlert.setBackground(getDrawable(R.drawable.custom_linear_layout_alert_false));
            setAlertState(false);
            buttonSetCalendar.setClickable(false);
            buttonSetCalendar.setElevation(0);

            Map<String, Object> alertStateMap = new HashMap<>();
            alertStateMap.put("alert state", alertState);
            dataBase.collection("generalUserInfo").document(email).collection("specific info").document("state")
                    .update(alertStateMap);
        } else {
            buttonAlert.setBackground(getDrawable(R.drawable.custom_linear_layout_alert_true));
            setAlertState(true);
            buttonSetCalendar.setClickable(true);
            buttonSetCalendar.setElevation(8);

            Map<String, Object> alertStateMap = new HashMap<>();
            alertStateMap.put("alert state", alertState);
            dataBase.collection("generalUserInfo").document(email).collection("specific info").document("state")
                    .update(alertStateMap);
        }
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

    public void onCategoryButtonClick(View view) {
        Intent intent = new Intent(this, SubjectActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("id", view.getId());
        if(getResources().getResourceEntryName(view.getId()).contains("Artropodes"))
            bundle.putString("index", "helmintos.html");
        else if(getResources().getResourceEntryName(view.getId()).contains("ChapterIV"))
            bundle.putString("index", "artropodes.html");
        else if(getResources().getResourceEntryName(view.getId()).contains("Helmintos"))
            bundle.putString("index", "protozoarios.html");
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void settingProgress(int progressToGo){                      // Call this function to change the progress bar, passing an int variable that goes from 0 to 100
        DocumentReference docRef = dataBase.collection("generalUserInfo").document(email);
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                setProgressStatus(documentSnapshot.getLong("progress status").intValue());
            }
        });
        progressStatus += progressToGo;         // Old Progress (progressStatus) + New Progress (progressToGo) = progressStatus
        if (progressStatus <= 100) {
            //progressBar = findViewById(R.id.progressApp);
            progressBar.setProgress(progressStatus, true);

            /* Updating the database with */
            Map<String, Object> updateProgress = new HashMap<>();
            updateProgress.put("progress status", progressStatus);

            /* Updating the progress status in the both collections's kind */
            dataBase.collection("generalUserInfo").document(email)
                    .update(updateProgress);
            dataBase.collection(schoolGrade).document(completeName)
                    .update(updateProgress);
        }
    }

    public void onButtonReview(View view){
    }

    public void setProgressStatus(int progressStatus){
        this.progressStatus = progressStatus;
    }

    public void setSchoolGrade(String schoolGrade){
        this.schoolGrade = schoolGrade;
    }

    public void setAlertState(boolean alertState){
        this.alertState = alertState;
    }
}


