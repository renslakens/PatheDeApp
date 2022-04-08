package com.groep3.pathedeapp.dataacces;

import com.groep3.pathedeapp.domain.List;
import com.groep3.pathedeapp.domain.LoadedGenres;
import com.groep3.pathedeapp.domain.LoadedLists;
import com.groep3.pathedeapp.domain.LoadedMovies;
import com.groep3.pathedeapp.domain.LoadedReviews;
import com.groep3.pathedeapp.domain.LoadedVideos;
import com.groep3.pathedeapp.domain.Movie;
import com.groep3.pathedeapp.domain.Rating;
import com.groep3.pathedeapp.domain.User;
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
            @Query("with_original_language") String language,
            @Query("with_genres") String genre
    );

    @GET("genre/movie/list")
    Call<LoadedGenres> getGenres(
            @Query("api_key") String key
    );

    //Get movie by ID
    @GET("movie/{movie_id}")
    Call<Movie> getMovie(
            @Path("movie_id") int id,
            @Query("api_key") String apiKey
    );

    @GET("movie/{movie_id}/videos")
    Call<LoadedVideos> getVideos(
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

    @POST("movie/{movie_id}/rating")
    @FormUrlEncoded
    Call<Rating> rateMovie(
            @Path("movie_id") Integer path,
            @Query("api_key") String key,
            @Query("guest_session_id") String guestId,
            @Query("session_id") String id,
            @Field("value") Number rating

    );

    @POST("list")
    @FormUrlEncoded
    Call<List> createList(
            @Query("api_key") String apiKey,
            @Query("session_id") String id,
            @Field("name") String name,
            @Field("description") String description
    );

    @POST("list/{list_id}/add_item")
    @FormUrlEncoded
    Call<LoadedLists> addItem(
            @Path("list_id") Integer path,
            @Query("api_key") String key,
            @Query("session_id") String id,
            @Field("media_id") String media_id
    );

    @GET("account")
    Call<User> getAccount(
            @Query("api_key") String key,
            @Query("session_id") String session
    );

    @GET("account/{account_id}/lists")
    Call<LoadedLists> getLists(
            @Path("account_id") Integer accountId,
            @Query("api_key") String key,
            @Query("session_id") String session
    );

    @GET("list/{list_id}")
    Call<List> getList(
            @Path("list_id") Integer id,
            @Query("api_key") String key
    );


}
