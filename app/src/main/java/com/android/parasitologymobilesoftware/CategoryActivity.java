package com.android.parasitologymobilesoftware;

import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.*;
import android.widget.SearchView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;
import com.android.MyApplication;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

public class CategoryActivity extends AppCompatActivity {

    //public String category = "Ascaridíase";
    public String category;
    private static final String TAG = "CategoryActivity";
    public static int numberOfTabs;

    private AlertDialog.Builder exitAlert;
    private AlertDialog alertDialogExit;
    private View dialogExitActivity;
    private TextView textViewMessageExitActivity;
    private String firstMessage = "Tem certeza que deseja abandonar ";
    private String secondMessage;
    private String thirdMessage = "? Seu progresso nesta categoria será perdido";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        Bundle extras = getIntent().getExtras();
        category = extras.getString("category");
        Log.d(TAG, "Category received: " +category);

        // Alert Dialog config
        secondMessage = category;
        LayoutInflater inflater = getLayoutInflater();
        dialogExitActivity = inflater.inflate(R.layout.dialog_change_exit_activity, null);
        textViewMessageExitActivity = dialogExitActivity.findViewById(R.id.textViewDialogMessageExitActivity);
        textViewMessageExitActivity.setText(firstMessage.concat(secondMessage.concat(thirdMessage)));
        exitAlert = new AlertDialog.Builder(CategoryActivity.this, R.style.Theme_AppCompat_Light_Dialog_Alert)
                .setView(dialogExitActivity);
        exitAlert.setNegativeButton("Retomar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        }).setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });
        alertDialogExit = exitAlert.create();

        // Changing dialog button text color
        alertDialogExit.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                alertDialogExit.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getColor(R.color.colorAccent));
                alertDialogExit.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(getColor(R.color.colorAccent));
            }
        });


        // Toolbar config
        final Toolbar toolbar = findViewById(R.id.toolbarSubject);
        toolbar.setTitle(category);
        toolbar.setTitleTextAppearance(this, R.style.FuturaMediumTextAppearance);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialogExit.show();
            }
        });

        try {
            InputStream inputStream;
            inputStream = MyApplication.getMyApplicationContext().getAssets().open("fragments_settings.json");
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            String JSON = new String(buffer, "UTF-8");
            JSONArray jsonArray = new JSONArray(JSON);

            for(int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                if(jsonObject.getString("categoryName").equals(category)) {
                    numberOfTabs = jsonObject.getInt("numberOfTabs");
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(MyApplication.getMyApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();

        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(MyApplication.getMyApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
        }


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

    public static int getNumberOfTabs() {
        return numberOfTabs;
    }

}
