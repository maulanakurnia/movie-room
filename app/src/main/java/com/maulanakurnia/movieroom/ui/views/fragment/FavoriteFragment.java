package com.maulanakurnia.movieroom.ui.views.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.maulanakurnia.movieroom.R;
import com.maulanakurnia.movieroom.data.model.Favorite;
import com.maulanakurnia.movieroom.ui.adapter.FavoriteAdapter;
import com.maulanakurnia.movieroom.ui.viewmodel.DetailsViewModel;
import com.maulanakurnia.movieroom.ui.viewmodel.HomeViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

/**
 * Created by Maulana Kurnia on 6/1/2021
 * Keep Coding & Stay Awesome!
 **/
public class FavoriteFragment extends Fragment {

    protected Favorite favorite;
    private HomeViewModel homeViewModel;
    private DetailsViewModel detailsViewModel;
    private FavoriteAdapter favoriteAdapter;
    private List<Favorite> favoriteList;
    private TextView placeholder;
    protected RecyclerView rv_movie_favorite;
    protected FloatingActionButton fa_delete;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_favorite,container,false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initViews(view);
        initViewModel();
        initRecyclerView();
        onObserveData();
    }

    private void initViewModel() {
        homeViewModel       = new ViewModelProvider(requireActivity()).get(HomeViewModel.class);
        detailsViewModel    = new ViewModelProvider(requireActivity()).get(DetailsViewModel.class);
    }

    private void initViews(View view) {
        requireActivity().getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        fa_delete           = view.findViewById(R.id.delete_favorite);
        rv_movie_favorite   = view.findViewById(R.id.rv_movie_favorite);
        placeholder         = view.findViewById(R.id.placeHolderText);

        fa_delete.setOnClickListener(view1 -> {
            detailsViewModel.clearWishList();
            Toast.makeText(getContext(),"Favorite List Cleared!",Toast.LENGTH_SHORT).show();
            favoriteList.clear();
            favoriteAdapter.setData(favoriteList);
        });
    }

    private void initRecyclerView() {
        favoriteAdapter  = new FavoriteAdapter(favoriteList, getContext());
        rv_movie_favorite.setAdapter(favoriteAdapter);
    }

    private void onObserveData() {
        homeViewModel.getAllFavorite().observe(getViewLifecycleOwner(), favorites -> {
            if(favorites.size() == 0) {
                placeholder.setVisibility(View.VISIBLE);
                fa_delete.setVisibility(View.GONE);
            }else {
                placeholder.setVisibility(View.GONE);
                fa_delete.setVisibility(View.VISIBLE);
                favoriteAdapter.setData(favorites);
                favoriteList = favorites;
            }
        });
    }
}
