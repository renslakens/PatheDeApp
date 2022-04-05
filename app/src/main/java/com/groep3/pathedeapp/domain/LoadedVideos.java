package com.groep3.pathedeapp.domain;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LoadedVideos {
    @SerializedName("results")
    @Expose
    private List<Video> videos = null;

    public List<Video> getVideos() {
        return videos;
    }
}
