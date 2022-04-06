package com.groep3.pathedeapp.domain;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LoadedGenres {
    @SerializedName("genres")
    @Expose
    private List<Genre> genreList = null;

    public List<Genre> getGenres(){
        return genreList;
    }

    public Integer getSize(){
        return genreList.size();
    }


}

