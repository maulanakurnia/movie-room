package com.maulanakurnia.movieroom.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.maulanakurnia.movieroom.R;

/**
 * Created by Maulana Kurnia on 6/4/2021
 * Keep Coding & Stay Awesome!
 **/
public class SharedPrefConfig {
    private final SharedPreferences sharedPreferences;
    protected final Context context;
    protected final String LOGIN_PREFERENCE         = "login_preference";
    protected final String LOGIN_STATUS_PREFERENCES = "login_status_preference";
    protected final String LOGIN_USER_ID            = "login_user_id";

    public SharedPrefConfig(Context context){
        this.context=context;
        sharedPreferences = context.getSharedPreferences(LOGIN_PREFERENCE, Context.MODE_PRIVATE);
    }

    public void writeLoginStatus(boolean status){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(LOGIN_STATUS_PREFERENCES,status);
        editor.apply();
    }

    public void writeLoginUserID(Integer id) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(LOGIN_USER_ID, id);
        editor.apply();
    }

    public boolean readLoginStatus(){
        boolean status = false;
        status = sharedPreferences.getBoolean(LOGIN_STATUS_PREFERENCES, false);
        return status;
    }

    public Integer readLoginUserID() {
        return sharedPreferences.getInt(LOGIN_USER_ID, 0);
    }

    public void clearLoggedInUser (Context context){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(LOGIN_PREFERENCE);
        editor.remove(LOGIN_STATUS_PREFERENCES);
        editor.remove(LOGIN_USER_ID);
        editor.apply();
    }
}
