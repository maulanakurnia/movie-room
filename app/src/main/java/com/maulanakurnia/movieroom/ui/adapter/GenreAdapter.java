package com.maulanakurnia.movieroom.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.maulanakurnia.movieroom.R;

import java.util.ArrayList;

/**
 * Created by Maulana Kurnia on 6/1/2021
 * Keep Coding & Stay Awesome!
 **/
public class GenreAdapter extends RecyclerView.Adapter<GenreAdapter.ViewHolder> {

    protected ArrayList<String> genres;
    protected Context mContext;

    public GenreAdapter(Context context, ArrayList<String> genres){
        this.genres = genres;
        this.mContext = context;
    }

    @NonNull @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.list_genre, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final String genre = genres.get(position);
        holder.setGenre(genre);
    }

    @Override
    public int getItemCount() {
        return genres == null ? 0 : genres.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvGenre;

        public ViewHolder(View itemView){
            super(itemView);
            tvGenre = itemView.findViewById(R.id.tv_genre_rv);
        }

        public void setGenre(String genre){
            tvGenre.setText(genre);
        }
    }
}
