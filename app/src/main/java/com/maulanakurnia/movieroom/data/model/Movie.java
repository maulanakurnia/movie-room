package com.maulanakurnia.movieroom.data.model;

import com.google.gson.JsonObject;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Maulana Kurnia on 6/1/2021
 * Keep Coding & Stay Awesome!
 **/
public class Movie {

    @SerializedName("page")
    public int page;

    @SerializedName("results")
    @Expose
    public ArrayList<Movie.Results> results;

    public ArrayList<Movie.Results> getResults() {
        return results;
    }

    public static class Results {
        private String poster_path, overview, release_date, title,backdrop_path, original_language;
        private Integer id, vote_count, runtime;
        private Number popularity;
        private Double vote_average;
        private ArrayList<Integer> genre_ids;
        private ArrayList<String> genre_names;
        private ArrayList<Genre> genres;
        private JsonObject videos;
        private List<Credit> credits;

        public String getOriginal_language() {
            return original_language;
        }

        public void setOriginal_language(String original_language) {
            this.original_language = original_language;
        }

        public String getPoster_path() {
            return poster_path;
        }

        public void setPoster_path(String poster_path) {
            this.poster_path = poster_path;
        }

        public String getOverview() {
            return overview;
        }

        public void setOverview(String overview) {
            this.overview = overview;
        }

        public String getRelease_date() {
            return release_date;
        }

        public void setRelease_date(String release_date) {
            this.release_date = release_date;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getBackdrop_path() {
            return backdrop_path;
        }

        public void setBackdrop_path(String backdrop_path) {
            this.backdrop_path = backdrop_path;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Integer getVote_count() {
            return vote_count;
        }

        public void setVote_count(Integer vote_count) {
            this.vote_count = vote_count;
        }

        public Integer getRuntime() {
            return runtime;
        }

        public void setRuntime(Integer runtime) {
            this.runtime = runtime;
        }

        public Number getPopularity() {
            return popularity;
        }

        public void setPopularity(Number popularity) {
            this.popularity = popularity;
        }

        public Double getVote_average() {
            return vote_average;
        }

        public void setVote_average(Double vote_average) {
            this.vote_average = vote_average;
        }

        public ArrayList<Integer> getGenre_ids() {
            return genre_ids;
        }

        public void setGenre_ids(ArrayList<Integer> genre_ids) {
            this.genre_ids = genre_ids;
        }

        public ArrayList<String> getGenre_names() {
            return genre_names;
        }

        public void setGenre_names(ArrayList<String> genre_names) {
            this.genre_names = genre_names;
        }

        public ArrayList<Genre> getGenres() {
            return genres;
        }

        public void setGenres(ArrayList<Genre> genres) {
            this.genres = genres;
        }

        public JsonObject getVideos() {
            return videos;
        }

        public void setVideos(JsonObject videos) {
            this.videos = videos;
        }

        public List<Credit> getCredits() {
            return credits;
        }

        public void setCredits(List<Credit> credits) {
            this.credits = credits;
        }
    }

}