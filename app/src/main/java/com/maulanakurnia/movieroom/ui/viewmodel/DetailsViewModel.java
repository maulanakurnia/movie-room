package com.maulanakurnia.movieroom.ui.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.maulanakurnia.movieroom.data.AppRepository;
import com.maulanakurnia.movieroom.data.model.Favorite;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Created by Maulana Kurnia on 6/2/2021
 * Keep Coding & Stay Awesome!
 **/
public class DetailsViewModel extends AndroidViewModel {

    protected AppRepository repository;

    public DetailsViewModel(@NonNull @NotNull Application application) {
        super(application);

        this.repository = new AppRepository(application);
    }

    public LiveData<List<Favorite>> getAllFavorite(long user_id) {
        return repository.getAllFavorite(user_id);
    }

    public LiveData<Favorite> getFavoriteList(int id) {
        return repository.getFavorite(id);
    }

    public void addFavorite(Favorite favorite) {
        repository.insertFavorite(favorite);
    }

    public void clearWishList(){
        repository.clearFavorite();
    }

    public void deleteFavorite(int id) {
        repository.deleteFavorite(id);
    }
}
