package com.maulanakurnia.movieroom.ui.views;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.maulanakurnia.movieroom.R;
import com.maulanakurnia.movieroom.ui.adapter.SearchAdapter;
import com.maulanakurnia.movieroom.ui.viewmodel.HomeViewModel;
import com.maulanakurnia.movieroom.utils.Constants;

import java.util.ArrayList;

/**
 * Created by Maulana Kurnia on 6/1/2021
 * Keep Coding & Stay Awesome!
 **/
public class SearchActivity extends AppCompatActivity {

    protected RecyclerView rv_movie;
    protected SearchAdapter searchAdapter;
    protected HomeViewModel viewModel;

    protected ImageView arrow_back;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initViews();
        initRecycleView();
        setupSearch();
    }

    private void initViews() {
        setContentView(R.layout.fragment_search);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        rv_movie            = findViewById(R.id.rv_search);
        viewModel           = new ViewModelProvider(this).get(HomeViewModel.class);
        arrow_back          = findViewById(R.id.search_back);

        arrow_back.setOnClickListener(v -> finish());
    }

    private void initRecycleView() {
        searchAdapter = new SearchAdapter(this);
        rv_movie.setAdapter(searchAdapter);
        rv_movie.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }

    private void setupSearch() {
        final TextView searchView = findViewById(R.id.search_keyword);
        searchView.requestFocus();
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);

        searchView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 2)
                    viewModel.getSearchMovie(s.toString().trim().toLowerCase()).observe(SearchActivity.this, results -> {
                        if(results != null) {
                            searchAdapter.setMovie(results);
                        }
                    });
            }

            @Override
            public void afterTextChanged(Editable s) { }
        });
    }

}
