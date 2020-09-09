package com.android.parasitologymobilesoftware;

import android.net.Uri;

public class VideoCard {

    public String urlImage;
    public String urlSite;
    public String urlId;
    public String titleSite;

    public void setUrlId(String url) {
        Uri uri = Uri.parse(url);
        this.urlId = uri.getQueryParameter("v");
    }

    public void setUrlImage(String urlId) {
        this.urlImage = "http://img.youtube.com/vi/" + urlId + "/0.jpg";
    }
}
