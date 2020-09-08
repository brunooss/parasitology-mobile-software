package com.android.parasitologymobilesoftware;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.MyApplication;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;


import okio.Utf8;

public class CategoryVideosActivity extends AppCompatActivity implements VideoAdapter.VideoOnClickListener {

    private static final String TAG = "CategoryVideosActivity";

    static
    InputStream inputStream;

    protected RecyclerView recyclerView;

    private List<VideoCard> videosList;

    private String category;
    private int categoryId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_videos);

        // getting data from menu activity
        Bundle data = getIntent().getExtras();
        category = data.getString("category");
        categoryId = data.getInt("categoryId");
        Log.i(TAG, "Category name received: " +category);
        Log.i(TAG, "Category Id received: " +categoryId);

        // Toolbar Configuration
        final Toolbar toolbar = findViewById(R.id.toolbarSubjectCategoryVideos);
        toolbar.setTitle("Vídeos");
        toolbar.setTitleTextAppearance(this, R.style.FuturaMediumTextAppearance);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        // Managing video player
        YouTubePlayerView youTubePlayerView = findViewById(R.id.youtube_player_view);


        videosList = new ArrayList<>();

        // Managing json file
        try {
            inputStream = MyApplication.getMyApplicationContext().getAssets().open("fragments_settings.json");
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            String JSON = new String(buffer, "UTF-8");
            JSONArray jsonArray = new JSONArray(JSON);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                if (jsonObject.getString("categoryName").equals(category)) {

                    JSONArray videosArray = jsonObject.getJSONArray("videos");
                    int videosSize = videosArray.length();

                    for (int j = 0; j < videosSize; j++) {
                        VideoCard videoCard = new VideoCard();
                        JSONObject video = videosArray.getJSONObject(j);
                        videoCard.titleSite = video.getString("title");
                        videoCard.urlSite = video.getString("url");
                        videosList.add(videoCard);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(MyApplication.getMyApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();

        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(MyApplication.getMyApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
        }

        recyclerView = findViewById(R.id.recyclerViewActivityVideos);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(new VideoAdapter(getBaseContext(), videosList, this));
    }

    @Override
    public void onClickVideoCard(int index) {
        VideoCard v = videosList.get(index);
        Log.d(TAG, "onClickVideoCard: clicked");
    }
}

