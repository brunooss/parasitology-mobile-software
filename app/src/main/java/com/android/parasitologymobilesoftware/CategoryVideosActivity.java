package com.android.parasitologymobilesoftware;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import android.view.View;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.*;

public class CategoryVideosActivity extends AppCompatActivity {

    protected RecyclerView recyclerView;

    private List<VideoCard> videosList;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        videosList = new ArrayList<>();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_videos);
        VideoCard video = new VideoCard();
        video.titleSite = "ESQUISTOSSOMOSE - PARASITOLOGIA | Biologia com Samuel Cunha";
        video.urlSite = "https://www.youtube.com/watch?v=Fiup02BGTvM";

        String youtubeUrl = "https://www.youtube.com/watch?v=Rxo0Upfz48Q";

        Uri uri = Uri.parse(youtubeUrl);
        String videoID = uri.getQueryParameter("v");

        String imageUrl = "http://img.youtube.com/vi/" + videoID +"/0.jpg";

        video.urlImage = imageUrl;

        // TODO Change way to add videos

        videosList.add(video);
        videosList.add(video);
        videosList.add(video);
        videosList.add(video);
        videosList.add(video);
        videosList.add(video);
        videosList.add(video);
        videosList.add(video);
        videosList.add(video);
        videosList.add(video);
        videosList.add(video);
        videosList.add(video);
        videosList.add(video);
        videosList.add(video);



        recyclerView = findViewById(R.id.recyclerViewActivityVideos);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(new VideoAdapter(getBaseContext(), videosList, onClickVideo()));
    }

    private VideoAdapter.VideoOnClickListener onClickVideo() {
        return new VideoAdapter.VideoOnClickListener() {

            @Override
            public void onClickVideoCard(View view, int index) {
                VideoCard v = videosList.get(index);
                Toast.makeText(getBaseContext(), "Video: " + v.urlSite, Toast.LENGTH_LONG);

                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(v.urlSite)));
            }
        };
    }

}
