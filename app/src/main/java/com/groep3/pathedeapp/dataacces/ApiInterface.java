package com.groep3.pathedeapp.dataacces;

import com.groep3.pathedeapp.domain.LoadedMovies;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {
    @GET("discover/movie")
    Call<LoadedMovies> getMovies(
            @Query("api_key") String apiKey,
            @Query("page") int page
    );

    @GET("authentication/token/new")
    Call<AuthenticateUser> authenticate(

    );
}
