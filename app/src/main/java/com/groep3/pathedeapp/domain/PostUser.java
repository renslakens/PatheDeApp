package com.groep3.pathedeapp.domain;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PostUser {
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("request_token")
    @Expose
    private String requestToken;

    public PostUser(String username, String password, String requestToken) {
        this.username = username;
        this.password = password;
        this.requestToken = requestToken;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
