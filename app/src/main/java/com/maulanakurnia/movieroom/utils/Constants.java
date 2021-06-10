package com.maulanakurnia.movieroom.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.maulanakurnia.movieroom.BuildConfig;

import java.util.HashMap;

/**
 * Created by Maulana Kurnia on 6/1/2021
 * Keep Coding & Stay Awesome!
 **/
public class Constants {

    public static final String API_KEY              = BuildConfig.TMDB_API_KEY;
    public static final String BASE_URL             = "https://api.themoviedb.org/3/";
    public static final String IMG_URL_200          = "https://image.tmdb.org/t/p/w200/";
    public static final String IMG_URL_300          = "https://image.tmdb.org/t/p/w300/";
    public static final String IMG_URL_ORIGINAL     = "https://image.tmdb.org/t/p/original/";

    public static final String POSTER_HORIZONTAL    = "horizontal";
    public static final String POSTER_VERTICAL      = "vertical";
    public static final String BANNER               = "banner";
}
