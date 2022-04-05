package com.groep3.pathedeapp.domain;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Video {
    @SerializedName("key")
    @Expose
    private String key;

    @SerializedName("site")
    @Expose
    private String site;

    @SerializedName("type")
    @Expose
    private String type;

    public String getKey() {
        return key;
    }

    public String getSite() {
        return site;
    }

    public String getType() {
        return type;
    }
}
