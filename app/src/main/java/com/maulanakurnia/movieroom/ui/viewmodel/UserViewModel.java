package com.maulanakurnia.movieroom.ui.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.maulanakurnia.movieroom.data.AppRepository;
import com.maulanakurnia.movieroom.data.model.User;

/**
 * Created by Maulana Kurnia on 6/4/2021
 * Keep Coding & Stay Awesome!
 **/
public class UserViewModel extends AndroidViewModel {

    public AppRepository repository;

    public UserViewModel(@NonNull Application application) {
        super(application);
        repository  = new AppRepository(application);
    }

    public LiveData<User> login(String username, String password) {
        return repository.login(username,password);
    }

    public LiveData<User> checkUsername(String username) {
        return repository.checkUsername(username);
    }

    public void insert(User user) {
        repository.register(user);
    }

    public void update(User user) {
        repository.updateUser(user);
    }

    public void updatePassword(User user) {
        repository.updatePassword(user);
    }

    public LiveData<User> getUser(Integer id) {
        return repository.getUser(id);
    }

}
