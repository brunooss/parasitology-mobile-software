package com.android.parasitologymobilesoftware;

import android.app.*;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.*;
import android.widget.*;

import androidx.core.app.NotificationCompat;
import androidx.viewpager.widget.ViewPager;
import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "HomeActivity";

    private int progressStatus;
    private ProgressBar progressBar, progressBarIntroduction, progressBarProtozoarios,
            progressBarHelmintos, progressBarArtropodes;

    private Button buttonSetCalendar;

    private Button buttonAlert;
    private boolean alertState;

    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore dataBase;

    private String email;
    private String schoolGrade;
    private String completeName;

    public static String category = "notNull";
    public static int categoryId;

    private View dialogView;

    private AlertDialog alertDialogFeedback;

    private CategoryTextBookFragment categoryTextBookFragment;

    public int [] categoriesIds = {R.id.categoryHomeFragmentIntroduction, R.id.categoryHomeFragmentIntroductionEcologia,
            R.id.categoryHomeFragmentIntroductionConceitosGerais, R.id.categoryHomeFragmentIntroductionClassificacao,
            R.id.categoryHomeFragmentIntroductionReproducao, R.id.categoryHomeFragmentIntroductionCicloBiologico,
            R.id.categoryHomeFragmentIntroductionAtualidades, R.id.categoryHomeFragmentProtozoarios,
            R.id.categoryHomeFragmentProtozoariosAmebiase, R.id.categoryHomeFragmentProtozoariosGiardiase,
            R.id.categoryHomeFragmentProtozoariosLeishmanioses, R.id.categoryHomeFragmentProtozoariosTricomonose,
            R.id.categoryHomeFragmentProtozoariosChagas, R.id.categoryHomeFragmentProtozoariosMalaria,
            R.id.categoryHomeFragmentProtozoariosToxoplasmose, R.id.categoryHomeFragmentProtozoariosBalantidiase,
            R.id.categoryHomeFragmentProtozoariosProtozooses, R.id.categoryHomeFragmentHelmintos,
            R.id.categoryHomeFragmentHelmintosEsquistossomose, R.id.categoryHomeFragmentHelmintosFascioliase,
            R.id.categoryHomeFragmentHelmintosTeniase, R.id.categoryHomeFragmentHelmintosCisticercose,
            R.id.categoryHomeFragmentHelmintosHidatidose, R.id.categoryHomeFragmentHelmintosHimenolepiase,
            R.id.categoryHomeFragmentHelmintosEstrongiloidiase, R.id.categoryHomeFragmentHelmintosTricuriase,
            R.id.categoryHomeFragmentHelmintosAncilostomiase, R.id.categoryHomeFragmentHelmintosNecatoriase,
            R.id.categoryHomeFragmentHelmintosEnterobiase, R.id.categoryHomeFragmentHelmintosAscaridiase,
            R.id.categoryHomeFragmentHelmintosLarvaMigrans, R.id.categoryHomeFragmentHelmintosFilarioses,
            R.id.categoryHomeFragmentHelmintosOutrasHelmintoses, R.id.categoryHomeFragmentArtropodes,
            R.id.categoryHomeFragmentArtropodesHemipteros, R.id.categoryHomeFragmentArtropodesMosquitos,
            R.id.categoryHomeFragmentArtropodesMoscas, R.id.categoryHomeFragmentArtropodesEctoparasitos};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        categoryTextBookFragment = new CategoryTextBookFragment();

        progressBarProtozoarios = findViewById(R.id.progressAppProtozoarios);
        progressBarHelmintos = findViewById(R.id.progressAppHelmintos);
        progressBarArtropodes = findViewById(R.id.progressAppArtropodes);

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
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation  view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_about) {
            startActivity(new Intent(this, AboutActivity.class));
            // Open the AboutActivity //@Todo
        } else if (id == R.id.nav_feedback) {
            startActivity(new Intent(this, FeedbackActivity.class));
        } else if (id == R.id.nav_out) {
            Toast.makeText(this, "Aguarde, saindo de sua conta.", Toast.LENGTH_SHORT).show();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    try {
                        FirebaseAuth.getInstance().signOut();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    finish();
                    System.exit(0);
                }
            }, 1000);
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return false;
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
            sendNotification(0,
                    "CHANNEL_ID",
                    "CHANNEL_NAME",
                    "Alertas definidos com sucesso!",
                    "Alertas do Parasite Combat aparecerão em breve !",
                    "Essa notificação será responsável por notificar quantos módulos você ainda precisa estudar.",
                     NotificationCompat.PRIORITY_HIGH,
                     Color.parseColor("#FF4500"));
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

    public void onButtonReview(View view) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(getLayoutInflater().inflate(R.layout.dialog_not_ready, null));
        builder.setPositiveButton("OK", null);
        builder.create().show();
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

    public void sendNotification(int id, String channelId, String channelName, String contentTitle, String contentSubText, String contentText, int priority, int color) {
        NotificationChannel channel = new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH);
        NotificationManager manager = getSystemService(NotificationManager.class);
        manager.createNotificationChannel(channel);


        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, channelId);
        builder.setSmallIcon(R.drawable.icons8_microscope_splashscreen_100)
                .setContentTitle(contentTitle)
                .setSubText(contentSubText)
                .setContentText(contentText)
                .setPriority(priority)
                .setColor(color);

        manager.notify(id, builder.build());
    }

    public static String getCategory() {
        return category;
    }

    public static int getCategoryId() {
        return categoryId;
    }
}


