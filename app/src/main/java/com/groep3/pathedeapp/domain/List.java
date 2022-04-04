package com.groep3.pathedeapp.domain;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class List {

    @SerializedName("created_by")
    @Expose
    private String createdBy;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("favorite_count")
    @Expose
    private int favoriteCount;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("items")
    @Expose
    private ArrayList<Movie> items;
    @SerializedName("item_count")
    @Expose
    private int itemCount;
    @SerializedName("iso_639_1")
    @Expose
    private String iso;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("poster_path")
    @Expose
    private String posterPath;

    public String getCreatedBy() {
        return createdBy;
    }

    public String getDescription() {
        return description;
    }

    public int getFavoriteCount() {
        return favoriteCount;
    }

    public String getId() {
        return id;
    }

    public ArrayList<Movie> getItems() {
        return items;
    }

    public int getItemCount() {
        return itemCount;
    }

    public String getIso() {
        return iso;
    }

    public String getName() {
        return name;
    }

    public String getPosterPath() {
        return posterPath;
    }
}
