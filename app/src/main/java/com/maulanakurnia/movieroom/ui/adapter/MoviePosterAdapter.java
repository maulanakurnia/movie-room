package com.maulanakurnia.movieroom.ui.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.maulanakurnia.movieroom.R;
import com.maulanakurnia.movieroom.data.model.Movie;
import com.maulanakurnia.movieroom.ui.views.DetailActivity;
import com.maulanakurnia.movieroom.ui.views.fragment.MovieFragament;
import com.maulanakurnia.movieroom.utils.Constants;

import java.util.ArrayList;

/**
 * Created by Maulana Kurnia on 6/1/2021
 * Keep Coding & Stay Awesome!
 **/
@SuppressLint("SetTextI18n")
public class MoviePosterAdapter extends RecyclerView.Adapter<MoviePosterAdapter.ViewHolder> {

    ArrayList<Movie.Results> movie = new ArrayList<>();
    protected final Context context;
    protected String orientation;

    public MoviePosterAdapter(Context context, String orientation) {
        this.context = context;
        this.orientation = orientation;
    }

    public void setData(ArrayList<Movie.Results> items){
        movie.clear();
        movie.addAll(items);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MoviePosterAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if(orientation.equals(Constants.BANNER)) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_banner,parent,false);
        } else if (orientation.equals(Constants.POSTER_VERTICAL)) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_poster_vertical,parent,false);
        }else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_poster,parent,false);
        }
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MoviePosterAdapter.ViewHolder holder, int position) {
        Glide.with(holder.itemView.getContext())
                .load(orientation.equals(Constants.POSTER_HORIZONTAL) ? Constants.IMG_URL_200 + movie.get(position).getPoster_path() :
                        Constants.IMG_URL_ORIGINAL + movie.get(position).getBackdrop_path())
                .into(holder.iv_poster);

        if(!orientation.equals(Constants.POSTER_HORIZONTAL)) {
            holder.tv_title.setText(movie.get(position).getTitle());
            holder.tv_release.setText(movie.get(position).getRelease_date().split("-")[0]);
        }

        holder.card_view.setOnClickListener(v -> {
            Intent detailActivity = new Intent(context, DetailActivity.class);
            detailActivity.putExtra(MovieFragament.EXTRA_ID, movie.get(position).getId());
            context.startActivity(detailActivity);
        });
    }

    @Override
    public int getItemCount() {
        return movie.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView iv_poster;
        CardView card_view;
        TextView tv_title, tv_release;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_poster   = itemView.findViewById(R.id.iv_movie_poster);
            card_view   = itemView.findViewById(R.id.card_list);
            tv_title    = itemView.findViewById(R.id.tv_movie_title);
            tv_release  = itemView.findViewById(R.id.tv_movie_release);
        }
    }
}
