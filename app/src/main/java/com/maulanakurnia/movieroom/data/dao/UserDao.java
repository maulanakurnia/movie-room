package com.maulanakurnia.movieroom.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.maulanakurnia.movieroom.data.model.Favorite;
import com.maulanakurnia.movieroom.data.model.User;

import java.util.List;

/**
 * Created by Maulana Kurnia on 6/1/2021
 * Keep Coding & Stay Awesome!
 **/
@Dao
public interface UserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(User user);

    @Query("SELECT * from user where username=:username and password=:password")
    LiveData<User> login(String username, String password);

    @Query("SELECT * from user where username=:username")
    LiveData<User> checkUsername(String username);

    @Query("SELECT * FROM user ORDER BY fullname ASC")
    LiveData<List<User>> getAll();

    @Query("SELECT * FROM user WHERE id == :id")
    LiveData<User> get(int id);

    @Query("UPDATE user SET password=:password WHERE id == :id")
    void updatePassword(String password, int id);

    @Query("UPDATE user SET username=:username, fullname=:fullname, nickname=:nickname WHERE id == :id")
    void updateUser(String username, String fullname, String nickname, int id);

    @Query("DELETE FROM user")
    void deleteAll();
}
