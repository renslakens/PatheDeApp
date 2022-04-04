package com.groep3.pathedeapp.dataacces;

import com.groep3.pathedeapp.domain.LoadedMovies;
import com.groep3.pathedeapp.domain.LoadedReviews;
import com.groep3.pathedeapp.domain.Movie;
import com.groep3.pathedeapp.domain.UserAuthenticate;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {
    //Get all movies
    @GET("discover/movie")
    Call<LoadedMovies> getMovies(
            @Query("api_key") String apiKey,
            @Query("page") int page,
            @Query("sort_by") String sort,
            @Query("vote_count.gte") Integer voteCount,
            @Query("primary_release_year") Integer year,
            @Query("vote_average.gte") Integer voteAverage,
            @Query("with_original_language") String language
    );

    //Get movie by ID
    @GET("movie/{movie_id}")
    Call<Movie> getMovie(
            @Path("movie_id") int id,
            @Query("api_key") String apiKey
    );

    //Search movie
    @GET("search/movie")
    Call<LoadedMovies> searchMovies(
            @Query("api_key") String apiKey,
            @Query("query") String query,
            @Query("page") int page
    );

    //Get movie reviews
    @GET("movie/{movie_id}/reviews")
    Call<LoadedReviews> getReview(
            @Path("movie_id") int id,
            @Query("api_key") String apiKey,
            @Query("page") int page
    );

    //Get request_token
    @GET("authentication/token/new")
    Call<UserAuthenticate> createNewSession(
            @Query("api_key") String apiKey
    );

    //Validate request_token with login
    @POST("authentication/token/validate_with_login")
    @FormUrlEncoded
    Call<UserAuthenticate> validateRequestToken(
            @Query("api_key") String apiKey,
            @Field("username") String username,
            @Field("password") String password,
            @Field("request_token") String requestToken
    );

    //Create session ID with request token
    @POST("authentication/session/new")
    @FormUrlEncoded
    Call<UserAuthenticate> createSessionID(
            @Query("api_key") String apiKey,
            @Field("request_token") String requestToken
    );

    //Create guest session
    @POST("authentication/guest_session/new")
    Call<UserAuthenticate> newGuestSession(
            @Query("api_key") String apiKey
    );
}
