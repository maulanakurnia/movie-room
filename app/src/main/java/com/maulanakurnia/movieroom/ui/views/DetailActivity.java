package com.maulanakurnia.movieroom.ui.views;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.button.MaterialButton;
import com.maulanakurnia.movieroom.R;
import com.maulanakurnia.movieroom.data.model.Credit;
import com.maulanakurnia.movieroom.data.model.Favorite;
import com.maulanakurnia.movieroom.data.model.Genre;
import com.maulanakurnia.movieroom.data.model.Movie;
import com.maulanakurnia.movieroom.data.service.ApiService;
import com.maulanakurnia.movieroom.ui.adapter.CastAdapter;
import com.maulanakurnia.movieroom.ui.adapter.GenreAdapter;
import com.maulanakurnia.movieroom.ui.adapter.MoviePosterAdapter;
import com.maulanakurnia.movieroom.ui.viewmodel.DetailsViewModel;
import com.maulanakurnia.movieroom.ui.viewmodel.HomeViewModel;
import com.maulanakurnia.movieroom.ui.views.fragment.MovieFragament;
import com.maulanakurnia.movieroom.utils.Constants;

import org.jetbrains.annotations.NotNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Maulana Kurnia on 6/1/2021
 * Keep Coding & Stay Awesome!
 **/
@SuppressLint({"SetTextI18n","SimpleDateFormat"})
public class DetailActivity extends AppCompatActivity {

    private TextView title, duration, description, original_language, release_date;
    private RatingBar rating;
    protected ImageView cover, poster, arrow_back;
    private Integer id;

    protected RecyclerView rv_cast, rv_genre, rv_similar;
    protected CastAdapter castAdapter;
    protected MoviePosterAdapter moviePosterAdapter;
    protected HomeViewModel homeViewModel;

    protected ArrayList<String> genres;

    protected DetailsViewModel detailsViewModel;
    protected Boolean inFavList = false;
    protected MaterialButton btnFavorite;

    public DetailActivity() { }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        homeViewModel       = new ViewModelProvider(this).get(HomeViewModel.class);

        initViews();
        initAdapter();
        initRecyclerview();
        onObserveData();
    }

    private void initViews() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        getWindow().setStatusBarColor(Color.TRANSPARENT);

        id                  = getIntent().getIntExtra(MovieFragament.EXTRA_ID,0);
        genres              = new ArrayList<>();

        title               = findViewById(R.id.tv_detail_title);
        duration            = findViewById(R.id.tv_detail_duration);
        rating              = findViewById(R.id.rb_detail);
        cover               = findViewById(R.id.iv_detail_cover);
        poster              = findViewById(R.id.iv_detail_poster);
        description         = findViewById(R.id.tv_detail_overview);
        arrow_back          = findViewById(R.id.iv_detail_back);
        btnFavorite         = findViewById(R.id.btn_detail_favorite);
        original_language   = findViewById(R.id.tv_detail_original_language);
        release_date        = findViewById(R.id.tv_detail_release);

        rv_genre            = findViewById(R.id.rv_genre);
        rv_cast             = findViewById(R.id.rv_casts);
        rv_similar          = findViewById(R.id.rv_similar);

        arrow_back.setOnClickListener(v -> finish());
    }

    private void initAdapter() {
        castAdapter = new CastAdapter(this);
        castAdapter.notifyDataSetChanged();

        detailsViewModel    = new ViewModelProvider(this).get(DetailsViewModel.class);

        moviePosterAdapter = new MoviePosterAdapter(this, Constants.POSTER_VERTICAL);
        moviePosterAdapter.notifyDataSetChanged();
    }

    private void initRecyclerview() {
        rv_genre.setHasFixedSize(true);
        rv_genre.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        rv_cast.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        rv_similar.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));

        rv_genre.setAdapter(new GenreAdapter(this, genres));
        rv_similar.setAdapter(new MoviePosterAdapter(this, Constants.POSTER_VERTICAL));
        rv_cast.setAdapter(castAdapter);
        rv_similar.setAdapter(moviePosterAdapter);
    }

    private void isInFavoriteList(int id) {
        detailsViewModel.getFavoriteList(id).observe(this, favorite -> {
            try {
                btnFavorite.setIconResource(R.drawable.ic_filled_love);
                inFavList = true;
                Log.d("FAVORITE_ID", favorite.getId().toString());
            }catch (Exception err) {
                err.printStackTrace();
                btnFavorite.setIconResource(R.drawable.ic_outline_love);
                inFavList = false;
            }
        });
    }

    private void onObserveData() {
        homeViewModel.getMovie(id).observe(this, results -> {
            Glide.with(DetailActivity.this)
                    .load(Constants.IMG_URL_ORIGINAL + results.getBackdrop_path())
                    .into(cover);

            Glide.with(DetailActivity.this)
                    .load(Constants.IMG_URL_300 + results.getPoster_path())
                    .into(poster);

            title.setText(results.getTitle()+ " (" +results.getRelease_date().split("-")[0] + ")");
            duration.setText(results.getRuntime() + "min");
            rating.setRating((float) (results.getVote_average() /2));
            description.setText(results.getOverview());
            original_language.setText("$ "+results.getPopularity().toString());
            SimpleDateFormat DateFor = new SimpleDateFormat("yyyy-MM-dd");

            try{
                Date date = DateFor.parse(results.getRelease_date());
                DateFor = new SimpleDateFormat("dd MMMM yyyy");
                assert date != null;
                String stringDate = DateFor.format(date);
                release_date.setText(stringDate);

            }catch (ParseException err) {
                Log.d("Exception", String.valueOf(err));
            }

            setGenres(results.getGenres());

            btnFavorite.setOnClickListener(v -> {
                if(inFavList) {
                    detailsViewModel.deleteFavorite(id);
                    btnFavorite.setIconResource(R.drawable.ic_outline_love);
                    Toast.makeText(this,"Removed from Favorite List",Toast.LENGTH_SHORT).show();
                } else {
                    Favorite favorite = new Favorite(
                            results.getId(),
                            results.getPoster_path(),
                            results.getOverview(),
                            results.getRelease_date(),
                            results.getTitle(),
                            results.getBackdrop_path(),
                            results.getVote_count(),
                            results.getRuntime()
                    );

                    detailsViewModel.addFavorite(favorite);
                    btnFavorite.setIconResource(R.drawable.ic_filled_love);
                    Toast.makeText(this,"Added to Favorite List.",Toast.LENGTH_SHORT).show();
                }
            });

            isInFavoriteList(id);
        });

        homeViewModel.getCast(id).observe(this, casts -> {
            if(casts != null) {
                castAdapter.setData(casts);
            }
        });

        homeViewModel.getSimiliarMovie(id).observe(this, similar -> {
            if(similar != null) {
                moviePosterAdapter.setData(similar);
            }
        });
    }
    
    private void setGenres(List<Genre> genresList){
        for(int i = 0; i< genresList.size(); i++){
            genres.add(genresList.get(i).getName());
        }
    }
}
