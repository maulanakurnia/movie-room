package com.maulanakurnia.movieroom.ui.viewmodel;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.EditText;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.maulanakurnia.movieroom.data.AppRepository;
import com.maulanakurnia.movieroom.data.model.Credit;
import com.maulanakurnia.movieroom.data.model.Favorite;
import com.maulanakurnia.movieroom.data.model.Movie;
import com.maulanakurnia.movieroom.data.model.User;
import com.maulanakurnia.movieroom.data.service.ApiService;
import com.maulanakurnia.movieroom.ui.views.MainActivity;
import com.maulanakurnia.movieroom.utils.Constants;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Maulana Kurnia on 6/1/2021
 * Keep Coding & Stay Awesome!
 **/
@SuppressLint("CommitPrefEdits")
public class HomeViewModel extends AndroidViewModel {

    protected AppRepository repository;
    public final LiveData<List<User>> listUser;

    public HomeViewModel(Application application) {
        super(application);
        repository          = new AppRepository(application);
        listUser            = repository.getAllUser();
    }

    public final LiveData<List<Favorite>> getAllFavorite(long user_id) {
        return repository.getAllFavorite(user_id);
    }

    public final LiveData<Movie.Results> getMovie(int id) {
        return repository.getMovie(id);
    }

    public final LiveData<ArrayList<Movie.Results>> getNowPlaying() {
        return repository.getNowPlaying();
    }

    public final LiveData<ArrayList<Movie.Results>> getSimiliarMovie(int id) {
        return repository.getSimilarMovie(id);
    }

    public final LiveData<ArrayList<Movie.Results>> getPopularMovie() {
        return repository.getPopularMovie();
    }

    public final LiveData<ArrayList<Movie.Results>> getDiscoverMovie() {
        return repository.getDiscoverMovie();
    }

    public final LiveData<ArrayList<Credit.Cast>> getCast(int id) {
        return repository.getCasts(id);
    }

    public final LiveData<ArrayList<Movie.Results>> getSearchMovie(String query) {
        return repository.getSearchMovie(query);
    }
}
