package com.maulanakurnia.movieroom.data.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

import static androidx.room.ForeignKey.CASCADE;

/**
 * Created by Maulana Kurnia on 6/1/2021
 * Keep Coding & Stay Awesome!
 **/
@Entity(tableName = "favorite", foreignKeys = @ForeignKey(entity = User.class,
        parentColumns = "id",
        childColumns = "id_user",
        onDelete = CASCADE))
public class Favorite {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    private Integer id;
    private Integer runtime, vote_count;
    private String poster_path,overview,release_date,title,backdrop_path;
    private Boolean selected;
    private Integer id_user;

    public Boolean getSelected() {
        return selected;
    }

    public void setSelected(Boolean selected) {
        this.selected = selected;
    }

    public Favorite(@NotNull Integer id, String poster_path, String overview, String release_date, String title, String backdrop_path, @NotNull Integer vote_count, @NotNull Integer runtime) {
        this.id = id;
        this.poster_path = poster_path;
        this.overview = overview;
        this.release_date = release_date;
        this.title = title;
        this.backdrop_path = backdrop_path;
        this.vote_count = vote_count;
        this.runtime = runtime;
    }

    public @NotNull Integer getId() {
        return id;
    }

    public void setId(@NotNull Integer id) {
        this.id = id;
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

    public Integer getId_user() {
        return id_user;
    }

    public void setId_user(Integer id_user) {
        this.id_user = id_user;
    }
}
