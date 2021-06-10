package com.maulanakurnia.movieroom.data;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.maulanakurnia.movieroom.data.dao.FavoriteDao;
import com.maulanakurnia.movieroom.data.dao.UserDao;
import com.maulanakurnia.movieroom.data.model.Favorite;
import com.maulanakurnia.movieroom.data.model.User;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Maulana Kurnia on 6/1/2021
 * Keep Coding & Stay Awesome!
 **/
@Database(entities = {User.class, Favorite.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public abstract UserDao userDao();
    public abstract FavoriteDao favoriteDao();
    public static final int NUMBER_OF_THREADS = 4;
    private static volatile AppDatabase INSTANCE;
    public static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static AppDatabase getDatabase(final Context context) {
        if(INSTANCE == null) {
            synchronized (AppDatabase.class) {
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                        AppDatabase.class, "roommovie.db")
                        .fallbackToDestructiveMigration()
                        .addCallback(sRoomDatabaseCallback)
                        .build();
            }
        }

        return INSTANCE;
    }

    private static final RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            databaseWriteExecutor.execute(() -> {
                UserDao userDao = INSTANCE.userDao();
                userDao.deleteAll();

                User user = new User();
                user.setFullname("Maulana Kurnia Fiqih Ainul Yaqin");
                user.setNickname("Maulana Kurnia");
                user.setUsername("maulanakurnia");
                user.setPassword("1221");
                user.setImage_path(null);
                userDao.insert(user);
            });
        }
    };
}
