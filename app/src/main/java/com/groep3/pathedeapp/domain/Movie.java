package com.groep3.pathedeapp.domain;

public class Movie {
    public boolean adult;
    public String backdropPath;
//    public BelongsToCollection belongsToCollection;
    public int budget;
//    public List<Genre> genres = null;
    public String homepage;
    public int id;
    public String imdbId;
    public String originalLanguage;
    public String originalTitle;
    public String overview;
    public float popularity;
    public String posterPath;
//    public List<ProductionCompany> productionCompanies = null;
//    public List<ProductionCountry> productionCountries = null;
    public String releaseDate;
    public int revenue;
    public int runtime;
//    public List<SpokenLanguage> spokenLanguages = null;
    public String status;
    public String tagline;
    public String title;
    public boolean video;
    public float voteAverage;
    public int voteCount;

    public Movie(String name, String posterPath) {
        //main info
        this.title = name;
        this.posterPath = posterPath;

    }


    public String getName() {
        return title;
    }

    public String getPosterPath(){
        return posterPath;
    }
}
