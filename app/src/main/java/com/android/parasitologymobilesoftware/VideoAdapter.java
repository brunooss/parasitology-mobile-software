package com.android.parasitologymobilesoftware;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideosViewHolder> {
    private final Context context;

    private final List<VideoCard> videos;

    private VideoOnClickListener videoOnClickListener;

    public VideoAdapter(Context context, List<VideoCard> videos, VideoOnClickListener videoOnClickListener) {
        this.context = context;
        this.videos = videos;
        this.videoOnClickListener = videoOnClickListener;
    }

    public int getItemCount() {
        return this.videos != null? this.videos.size() : 0;
    }

    @Override
    public VideosViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_video, viewGroup, false);

        VideosViewHolder holder = new VideosViewHolder(view, videoOnClickListener);
        return holder;
    }

    @Override
    public void onBindViewHolder(final VideosViewHolder holder, final int position) {
        VideoCard v = videos.get(position);

        holder.videoName.setText(v.titleSite);


        Picasso.with(context)
                .load(v.urlImage)
                .into(holder.videoThumbnail);


//        try {
//            Log.d("VideoAdapter", "URL: "+v.urlImage);
//            InputStream is = (InputStream) new URL(v.urlImage).getContent();
//            Drawable d = Drawable.createFromStream(is, "imageVideoThumbnail");
//            holder.videoThumbnail.setImageDrawable(d);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
////        try {
////            holder.videoThumbnail.setImageBitmap(BitmapFactory.decodeStream(new URL(v.urlImage).openStream()));
////        } catch (IOException e) {
////            e.printStackTrace();
////        }
    }

    public interface VideoOnClickListener {
        public void onClickVideoCard(int index);
    }

    public static class VideosViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        VideoOnClickListener videoOnClickListener;

        public TextView videoName;
        public ImageView videoThumbnail;

        public VideosViewHolder(View view, VideoOnClickListener videoOnClickListener) {
            super(view);

            this.videoOnClickListener = videoOnClickListener;

            videoName = view.findViewById(R.id.textViewVideoName);
            videoThumbnail = view.findViewById(R.id.imageViewVideoThumbnail);

            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            videoOnClickListener.onClickVideoCard(getAdapterPosition());
        }
    }
}