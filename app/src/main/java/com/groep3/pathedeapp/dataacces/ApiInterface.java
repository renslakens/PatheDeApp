package com.groep3.pathedeapp.dataacces;

import com.groep3.pathedeapp.domain.LoadedMovies;
import com.groep3.pathedeapp.domain.Movie;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {
    @GET("discover/movie")
    Call<LoadedMovies> getMovies(
            @Query("api_key") String apiKey,
            @Query("page") int page
    );

    @GET("movie/{movie_id}")
    Call<Movie> getMovie(
            @Path("movie_id") int id,
            @Query("api_key") String apiKey
    );

    @GET("authentication/token/new")
    Call authenticateUser(
            @Query("api_key") String apiKey
    );
}