package com.maulanakurnia.movieroom.ui.views.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.maulanakurnia.movieroom.R;
import com.maulanakurnia.movieroom.data.model.Movie;
import com.maulanakurnia.movieroom.ui.adapter.MoviePosterAdapter;
import com.maulanakurnia.movieroom.ui.viewmodel.HomeViewModel;
import com.maulanakurnia.movieroom.ui.views.SearchActivity;
import com.maulanakurnia.movieroom.utils.Constants;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Created by Maulana Kurnia on 6/1/2021
 * Keep Coding & Stay Awesome!
 **/
public class MovieFragament extends Fragment {
    public static final String EXTRA_ID    = "EXTRA_ID";

    protected RecyclerView rv_now_playing, rv_popular, rv_banner;
    protected MoviePosterAdapter nowPlaying, upComing, popular, discover;
    protected HomeViewModel viewModel;
    protected ImageView search;

    public MovieFragament() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_movie,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initViews(view);
        setupSearch(view);
        initRecycleView();
        onObserverAnyChange();
    }

    private void initViews(View view) {
        requireActivity().getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        rv_now_playing  = view.findViewById(R.id.rv_now_playing);
        rv_popular      = view.findViewById(R.id.rv_popular);
        rv_banner       = view.findViewById(R.id.rv_discover);

        viewModel = new ViewModelProvider(this).get(HomeViewModel.class);
    }

    private void initRecycleView() {
        nowPlaying  = new MoviePosterAdapter(getContext(), Constants.POSTER_HORIZONTAL);
        popular     = new MoviePosterAdapter(getContext(), Constants.POSTER_HORIZONTAL);
        discover    = new MoviePosterAdapter(getContext(), Constants.BANNER);

        rv_now_playing.setAdapter(nowPlaying);
        rv_popular.setAdapter(popular);
        rv_banner.setAdapter(discover);
    }

    private void onObserverAnyChange() {
        nowPlaying.notifyDataSetChanged();
        popular.notifyDataSetChanged();
        discover.notifyDataSetChanged();

        viewModel.getNowPlaying().observe(getViewLifecycleOwner(), nowPlayings -> {
            if(nowPlayings != null)
                nowPlaying.setData(nowPlayings);
        });

        viewModel.getPopularMovie().observe(getViewLifecycleOwner(), populars -> {
            if(populars != null)
                popular.setData(populars);

        });

        viewModel.getDiscoverMovie().observe(getViewLifecycleOwner(), discovers -> {
            if(discovers != null)
                discover.setData(discovers);

        });
    }

    private void setupSearch(View view) {
        search          = view.findViewById(R.id.movie_search);

        search.setOnClickListener(v -> {
            Intent i = new Intent(getContext(), SearchActivity.class);
            requireContext().startActivity(i);
        });
    }
}
