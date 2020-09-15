package com.android.parasitologymobilesoftware;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.util.Log;
import android.view.*;
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

public class CategoryTextBookActivity extends AppCompatActivity {

    //public String category = "Ascaridíase";
    private static final String TAG = "CategoryTextBookActivity";
    public static int numberOfTabs;

    public String category;
    public int categoryId;

    private AlertDialog.Builder exitAlert;
    private AlertDialog alertDialogExit;
    private View dialogExitActivity;
    private TextView textViewMessageExitActivity;
    private String firstMessage = "Tem certeza que deseja abandonar ";
    private String secondMessage;
    private String thirdMessage = "? Seu progresso nesta categoria será perdido";
    private ViewPager viewPager;

//    private CategoryTextBookFragment fragment = new CategoryTextBookFragment();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_textbook);

        Bundle extras = getIntent().getExtras();
        category = extras.getString("category");
        categoryId = extras.getInt("CategoryId");
        Log.i(TAG, "Category received: " +category);
        Log.i(TAG, "Category Id received: " +categoryId);

        // Exit Alert Dialog config
        secondMessage = category;
        LayoutInflater inflater = getLayoutInflater();
        dialogExitActivity = inflater.inflate(R.layout.dialog_change_exit_activity, null);
        textViewMessageExitActivity = dialogExitActivity.findViewById(R.id.textViewDialogMessageExitActivity);
        textViewMessageExitActivity.setText(firstMessage.concat(secondMessage.concat(thirdMessage)));
        exitAlert = new AlertDialog.Builder(CategoryTextBookActivity.this, R.style.Theme_AppCompat_Light_Dialog_Alert)
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
        final Toolbar toolbar = findViewById(R.id.toolbarSubjectCategoryMenu);
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
                    numberOfTabs = jsonObject.getInt("numberOftextBookTabs");
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(MyApplication.getMyApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();

        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(MyApplication.getMyApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
        }


        viewPager = findViewById(R.id.viewPagerSubject);
        viewPager.setAdapter(new CategoryTextBookFragmentPagerAdapter(getSupportFragmentManager()));


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
    public void onBackPressed() {
        alertDialogExit.show();

        //super.onBackPressed();
        return ;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();


        return super.onOptionsItemSelected(item);
    }

    public static int getNumberOfTabs() {
        return numberOfTabs;
    }

    public void nextPage(View view) {
        viewPager.setCurrentItem(viewPager.getCurrentItem() + 1, true);
    }

    public void previousPage(View view) {
        viewPager.setCurrentItem(viewPager.getCurrentItem() - 1, true);
    }

}
