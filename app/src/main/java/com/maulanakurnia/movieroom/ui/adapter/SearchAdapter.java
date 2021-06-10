package com.maulanakurnia.movieroom.ui.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.maulanakurnia.movieroom.R;
import com.maulanakurnia.movieroom.data.model.Genre;
import com.maulanakurnia.movieroom.data.model.Movie;
import com.maulanakurnia.movieroom.ui.views.DetailActivity;
import com.maulanakurnia.movieroom.ui.views.fragment.MovieFragament;
import com.maulanakurnia.movieroom.utils.Constants;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Maulana Kurnia on 6/3/2021
 * Keep Coding & Stay Awesome!
 **/
@SuppressLint("SetTextI18n")
public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {

    protected ArrayList<Movie.Results> movie = new ArrayList<>();
    protected final Context context;

    public SearchAdapter(Context context) {
        this.context = context;
    }

    public void setMovie(ArrayList<Movie.Results> items) {
        movie.clear();
        movie.addAll(items);
        notifyDataSetChanged();
    }

    @NonNull @NotNull @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_poster_complete,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull SearchAdapter.ViewHolder holder, int position) {
        Glide.with(holder.itemView.getContext())
                .load(Constants.IMG_URL_200 + movie.get(position).getPoster_path())
                .into(holder.iv_poster);

        holder.tv_title.setText(movie.get(position).getTitle() + " (" +movie.get(position).getRelease_date().split("-")[0] + ")");
        holder.rating.setRating((float) (movie.get(position).getVote_average() /2));
        holder.tv_rating.setText(movie.get(position).getVote_average().toString());
        holder.tv_overview.setText(movie.get(position).getOverview());

        holder.item.setOnClickListener(v -> {
            Intent detailActivity = new Intent(context, DetailActivity.class);
            detailActivity.putExtra(MovieFragament.EXTRA_ID, movie.get(position).getId());
            context.startActivity(detailActivity);
        });
    }

    @Override
    public int getItemCount() {
        return movie == null ? 0 : movie.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_poster;
        CardView card_view;
        TextView tv_title, tv_rating, tv_overview;
        RatingBar rating;
        ConstraintLayout item;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_poster   = itemView.findViewById(R.id.iv_movie_poster);
            card_view   = itemView.findViewById(R.id.card_list);
            tv_title    = itemView.findViewById(R.id.tv_movie_title);
            rating      = itemView.findViewById(R.id.rv_rb_detail);
            tv_rating   = itemView.findViewById(R.id.tv_movie_rating);
            tv_overview = itemView.findViewById(R.id.tv_movie_overview);
            item        = itemView.findViewById(R.id.move_item_layout);
        }
    }



}
