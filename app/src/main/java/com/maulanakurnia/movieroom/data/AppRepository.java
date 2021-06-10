package com.maulanakurnia.movieroom.data;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.maulanakurnia.movieroom.data.dao.FavoriteDao;
import com.maulanakurnia.movieroom.data.dao.UserDao;
import com.maulanakurnia.movieroom.data.model.Credit;
import com.maulanakurnia.movieroom.data.model.Favorite;
import com.maulanakurnia.movieroom.data.model.Movie;
import com.maulanakurnia.movieroom.data.model.User;
import com.maulanakurnia.movieroom.data.service.ApiService;
import com.maulanakurnia.movieroom.ui.views.DetailActivity;
import com.maulanakurnia.movieroom.utils.Constants;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Maulana Kurnia on 6/1/2021
 * Keep Coding & Stay Awesome!
 **/
public class AppRepository {

    private final UserDao userDao;
    private final FavoriteDao favoriteDao;
    private final LiveData<List<User>> listUser;
    private final LiveData<List<Favorite>> listFavorite;

    public AppRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        userDao = db.userDao();
        listUser = userDao.getAll();
        favoriteDao = db.favoriteDao();
        listFavorite = favoriteDao.getFavoriteList();
    }

    // -- User
    public LiveData<List<User>> getAllUser() {
        return listUser;
    }
    public LiveData<User> getUser(int id) {
        return userDao.get(id);
    }
    public LiveData<User> login(String username, String password) {
        return userDao.login(username, password);
    }
    public LiveData<User> checkUsername(String username) {
        return userDao.checkUsername(username);
    }

    public void register(User user) {
        AppDatabase.databaseWriteExecutor.execute(() ->userDao.insert(user));
    }

    public void updateUser(User user) {
        AppDatabase.databaseWriteExecutor.execute(() ->userDao.updateUser(user.getUsername(), user.getFullname(), user.getNickname(), user.getId()));
    }

    public void updatePassword(User user) {
        AppDatabase.databaseWriteExecutor.execute(() -> userDao.updatePassword(user.getPassword(), user.getId()));
    }

    // -- Favorite
    public LiveData<List<Favorite>> getAllFavorite() {
        return listFavorite;
    }
    public LiveData<Favorite> getFavorite(int id) {
        return favoriteDao.get(id);
    }

    public void insertFavorite(Favorite favorite) {
        AppDatabase.databaseWriteExecutor.execute(() -> favoriteDao.insert(favorite));
    }

    public void deleteFavorite(int id) {
        AppDatabase.databaseWriteExecutor.execute(() -> favoriteDao.delete(id));
    }

    public void clearFavorite() {
        AppDatabase.databaseWriteExecutor.execute(favoriteDao::clearFavoriteList);
    }


    // -- API
    private final ApiService apiService = new ApiService();

    public LiveData<Movie.Results> getMovie(int id) {
        final MutableLiveData<Movie.Results> movie = new MutableLiveData<>();

        Call<Movie.Results> responseMovie = apiService.getMovie().getMovie(id, Constants.API_KEY);

        responseMovie.enqueue(new Callback<Movie.Results>() {
            @Override
            public void onResponse(@NotNull Call<Movie.Results> call, @NotNull Response<Movie.Results> response) {
                Movie.Results movieResponse = response.body();
                if(response.isSuccessful() && response.body() != null) {
                    assert movieResponse != null;
                    Movie.Results movie_result = movieResponse;
                    movie.postValue(movie_result);
                }
            }

            @Override
            public void onFailure(@NotNull Call<Movie.Results> call, @NotNull Throwable t) {

            }
        });

        return movie;
    }

    public LiveData<ArrayList<Movie.Results>> getNowPlaying() {
        final MutableLiveData<ArrayList<Movie.Results>> nowPlayingMovie = new MutableLiveData<>();

        Call<Movie> responseMovie = apiService.getMovie().getNowPlaying(Constants.API_KEY);
        responseMovie.enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(@NotNull Call<Movie> call, @NotNull Response<Movie> response) {
                Movie movieResponse = response.body();
                if (response.isSuccessful() && response.body() != null) {
                    assert movieResponse != null;
                    ArrayList<Movie.Results> nowPlaying = movieResponse.getResults();
                    nowPlayingMovie.postValue(nowPlaying);
                }
            }

            @Override
            public void onFailure(@NotNull Call<Movie> call, @NotNull Throwable t) {

            }
        });
        return nowPlayingMovie;
    }

    public LiveData<ArrayList<Movie.Results>> getSimilarMovie(int id) {

        final MutableLiveData<ArrayList<Movie.Results>> similarMovie = new MutableLiveData<>();

        Call<Movie> responseMovie = apiService.getMovie().getSimilar(id, Constants.API_KEY);
        responseMovie.enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(@NotNull Call<Movie> call, @NotNull Response<Movie> response) {
                Movie movieResponse = response.body();
                if(response.isSuccessful() && response.body() != null) {
                    assert movieResponse != null;
                    ArrayList<Movie.Results> similar = movieResponse.getResults();
                    similarMovie.postValue(similar);
                }
            }

            @Override
            public void onFailure(@NotNull Call<Movie> call, @NotNull Throwable t) {

            }
        });

        return similarMovie;
    }

    public LiveData<ArrayList<Movie.Results>> getPopularMovie() {
        final MutableLiveData<ArrayList<Movie.Results>> popularMovie = new MutableLiveData<>();

        Call<Movie> responseMovie = apiService.getMovie().getPopular(Constants.API_KEY);
        responseMovie.enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(@NotNull Call<Movie> call, @NotNull Response<Movie> response) {
                Movie movieResponse = response.body();
                if (response.isSuccessful() && response.body() != null) {
                    assert movieResponse != null;
                    ArrayList<Movie.Results> popular = movieResponse.getResults();
                    popularMovie.postValue(popular);
                }
            }

            @Override
            public void onFailure(@NotNull Call<Movie> call, @NotNull Throwable t) {

            }
        });

        return popularMovie;
    }

    public LiveData<ArrayList<Movie.Results>> getDiscoverMovie() {
        final MutableLiveData<ArrayList<Movie.Results>> discoverMovie = new MutableLiveData<>();

        Call<Movie> responseMovie = apiService.getMovie().getDiscover(Constants.API_KEY);
        responseMovie.enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(@NotNull Call<Movie> call, @NotNull Response<Movie> response) {
                Movie movieResponse = response.body();
                if (response.isSuccessful() && response.body() != null) {
                    assert movieResponse != null;
                    ArrayList<Movie.Results> discover = movieResponse.getResults();
                    discoverMovie.postValue(discover);
                }
            }

            @Override
            public void onFailure(@NotNull Call<Movie> call, @NotNull Throwable t) {

            }
        });

        return discoverMovie;
    }

    public LiveData<ArrayList<Credit.Cast>> getCasts(int id) {
        final MutableLiveData<ArrayList<Credit.Cast>> creditCast = new MutableLiveData<>();

        Call<Credit> responseCredit = apiService.getMovie().getCasts(id, Constants.API_KEY);
        responseCredit.enqueue(new Callback<Credit>() {
            @Override
            public void onResponse(@NotNull Call<Credit> call, @NotNull Response<Credit> response) {
                Credit creditResponse = response.body();
                if(response.isSuccessful() && response.body() != null) {
                    assert creditResponse != null;
                    ArrayList<Credit.Cast> casts = creditResponse.getCasts();
                    creditCast.postValue(casts);
                }
            }

            @Override
            public void onFailure(@NotNull Call<Credit> call, @NotNull Throwable t) {

            }
        });

        return creditCast;
    }

    public LiveData<ArrayList<Movie.Results>> getSearchMovie(String query) {
        final MutableLiveData<ArrayList<Movie.Results>> searchMovie = new MutableLiveData<>();

        Call<Movie> responseCredit = apiService.getMovie().getSearch(query, Constants.API_KEY);
        responseCredit.enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(@NotNull Call<Movie> call, @NotNull Response<Movie> response) {
                Movie movieResponse = response.body();
                if(response.isSuccessful() && response.body() != null) {
                    assert movieResponse != null;
                    ArrayList<Movie.Results> movie = movieResponse.getResults();
                    searchMovie.postValue(movie);
                }
            }

            @Override
            public void onFailure(@NotNull Call<Movie> call, @NotNull Throwable t) {

            }
        });

        return searchMovie;
    }

}
