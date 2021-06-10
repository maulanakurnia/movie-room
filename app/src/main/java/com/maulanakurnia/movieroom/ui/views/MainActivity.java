package com.maulanakurnia.movieroom.ui.views;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.maulanakurnia.movieroom.R;
import com.maulanakurnia.movieroom.ui.views.fragment.MovieFragament;
import com.maulanakurnia.movieroom.ui.views.fragment.FavoriteFragment;
import com.maulanakurnia.movieroom.ui.views.fragment.SettingFragment;

/**
 * Created by Maulana Kurnia on 6/1/2021
 * Keep Coding & Stay Awesome!
 **/
@SuppressLint("NonConstantResourceId")
public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{

    protected BottomNavigationView navView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        navView = findViewById(R.id.bottom_navigation);
        navView.setOnNavigationItemSelectedListener(this);
        loadFragment(new MovieFragament());
    }

    private boolean loadFragment(Fragment fragment) {
        if (fragment != null){
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fr_main_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        Fragment fragment = null;
        switch (item.getItemId()){
            case R.id.movie:
                fragment = new MovieFragament();
                break;
            case R.id.favorite:
                fragment = new FavoriteFragment();
                break;
            case R.id.setting:
                fragment = new SettingFragment();
                break;
        }
        return loadFragment(fragment);
    }
}
