package com.groep3.pathedeapp.domain;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User {
    @SerializedName("api_key")
    @Expose
    private String apiKey;

    public String getApiKey() {
        return apiKey;
    }
}
