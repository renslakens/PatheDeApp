package com.groep3.pathedeapp.domain;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LoadedLists
{
    @SerializedName("results")
    @Expose
    private java.util.List<com.groep3.pathedeapp.domain.List> lists = null;

    @SerializedName("success")
    @Expose
    private Boolean success;

    public List<com.groep3.pathedeapp.domain.List> getLists() {
        return lists;
    }
}
