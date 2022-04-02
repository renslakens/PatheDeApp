package com.groep3.pathedeapp.dataacces;

import com.groep3.pathedeapp.domain.LoadedMovies;
import com.groep3.pathedeapp.domain.Movie;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {
    //Get all movies
    @GET("discover/movie")
    Call<LoadedMovies> getMovies(
            @Query("api_key") String apiKey,
            @Query("page") int page
    );

    //Get movie by ID
    @GET("movie/{movie_id}")
    Call<Movie> getMovie(
            @Path("movie_id") int id,
            @Query("api_key") String apiKey
    );

    //Get request_token
    @GET("authentication/token/new")
    Call<UserRequestToken> createNewSession(
            @Query("api_key") String apiKey
    );

    //Validate request_token with login
    @POST("authentication/token/validate_with_login")
    Call<PostUser> validateRequestToken(
            @Query("api_key") String apiKey,
            @Body() PostUser loginData
    );

    //Create session ID with request token
    @POST("authentication/session/new")
    Call<UserRequestToken> createSessionID(
            @Query("api_key") String apiKey,
            @Body() UserRequestToken requestToken
    );
}
