package com.groep3.pathedeapp.domain;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserRequestToken {
    @SerializedName("request_token")
    @Expose
    private String requestToken;

    public String getRequestToken() {
        return requestToken;
    }
}
