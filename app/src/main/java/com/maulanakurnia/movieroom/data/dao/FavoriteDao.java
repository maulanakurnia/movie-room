package com.maulanakurnia.movieroom.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.maulanakurnia.movieroom.data.model.Favorite;

import java.util.List;

/**
 * Created by Maulana Kurnia on 6/1/2021
 * Keep Coding & Stay Awesome!
 **/
@Dao
public interface FavoriteDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Favorite favorite);

    @Query("DELETE From favorite WHERE id = :id")
    void delete(long id);

    @Query("DELETE FROM favorite")
    void clearFavoriteList();

    @Query("SELECT * FROM favorite")
    LiveData<List<Favorite>> getFavoriteList();

    @Query("SELECT * FROM favorite WHERE id = :id ")
    LiveData<Favorite> get(int id);
}
