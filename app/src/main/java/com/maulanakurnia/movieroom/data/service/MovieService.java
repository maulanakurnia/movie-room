package com.maulanakurnia.movieroom.data.service;

import com.maulanakurnia.movieroom.data.model.Credit;
import com.maulanakurnia.movieroom.data.model.Movie;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Maulana Kurnia on 6/1/2021
 * Keep Coding & Stay Awesome!
 **/
public interface MovieService {

    @GET("movie/now_playing")
    Call<Movie> getNowPlaying(
        @Query("api_key") String apiKey
    );

    @GET("discover/movie")
    Call<Movie> getDiscover(
        @Query("api_key") String apiKey
    );

    @GET("movie/top_rated")
    Call<Movie> getPopular(
        @Query("api_key") String apiKey
    );

    @GET("movie/{movie_id}/similar")
    Call<Movie> getSimilar(
        @Path("movie_id") Integer id,
        @Query("api_key") String apiKey
    );

    @GET("movie/{movie_id}/credits")
    Call<Credit> getCasts(
        @Path("movie_id") Integer id,
        @Query("api_key") String apiKey
    );

    @GET("search/movie")
    Call<Movie> getSearch(
        @Query("query") String query,
        @Query("api_key") String apiKey
    );

    @GET("movie/{movie_id}")
    Call<Movie.Results> getMovie(
        @Path("movie_id") Integer id,
        @Query("api_key") String apiKey
    );

}
