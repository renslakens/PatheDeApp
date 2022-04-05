package com.groep3.pathedeapp.domain;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserAuthenticate {

    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("expires_at")
    @Expose
    private String expiresAt;
    @SerializedName("request_token")
    @Expose
    private String requestToken;
    @SerializedName("session_id")
    @Expose
    private String sessionID;
    @SerializedName("guest_session_id")
    @Expose
    private String guestSessionID;

    public Boolean getSuccess() {
        return success;
    }

    public String getExpiresAt() {
        return expiresAt;
    }

    public String getRequestToken() {
        return requestToken;
    }

    public String getSessionID() {
        return sessionID;
    }

    public String getGuestSessionID() {
        return guestSessionID;
    }

    public void setGuestSessionID(String guestSessionID) {
        this.guestSessionID = guestSessionID;
    }

    @Override
    public String toString() {
        return "UserAuthenticate{" +
                "success=" + success +
                ", expiresAt='" + expiresAt + '\'' +
                ", requestToken='" + requestToken + '\'' +
                ", sessionID='" + sessionID + '\'' +
                ", guestSessionID='" + guestSessionID + '\'' +
                '}';
    }
}
