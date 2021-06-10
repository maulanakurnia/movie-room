package com.maulanakurnia.movieroom.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.maulanakurnia.movieroom.R;
import com.maulanakurnia.movieroom.data.model.Favorite;
import com.maulanakurnia.movieroom.ui.views.DetailActivity;
import com.maulanakurnia.movieroom.ui.views.fragment.MovieFragament;
import com.maulanakurnia.movieroom.utils.Constants;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by Maulana Kurnia on 6/4/2021
 * Keep Coding & Stay Awesome!
 **/
public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.ViewHolder> {

    protected List<Favorite> favoriteList;
    protected Context context;
    protected AtomicBoolean isChecked;

    public FavoriteAdapter(List<Favorite> favoriteList, Context context) {
        this.favoriteList = favoriteList;
        this.context = context;
    }

    public void setData(List<Favorite> favoriteList) {
        this.favoriteList = favoriteList;
        notifyDataSetChanged();
    }


    @NonNull @NotNull @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_poster, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull FavoriteAdapter.ViewHolder holder, int position) {
        isChecked = new AtomicBoolean(false);
        holder.card_view.getLayoutParams().height = 700;
        holder.card_view.getLayoutParams().width = 460;

        holder.iv_poster.getLayoutParams().height = 700;
        holder.iv_poster.getLayoutParams().width = 460;

        Glide.with(context).load(Constants.IMG_URL_ORIGINAL + favoriteList.get(position).getPoster_path())
                .into(holder.iv_poster);


        if(!isChecked.get()) {
            holder.card_view.setOnLongClickListener(v -> {
                if(!holder.card_view.isChecked()) {
                    holder.card_view.setChecked(true);
                    holder.iv_overlay.setVisibility(View.VISIBLE);
                    isChecked.set(true);
                    favoriteList.get(position).setSelected(true);
                }
                return true;
            });
        }
        holder.card_view.setOnClickListener(v -> {
            holder.card_view.setChecked(false);
            holder.iv_overlay.setVisibility(View.GONE);
            isChecked.set(false);
            favoriteList.get(position).setSelected(false);
        });
        holder.card_view.setOnClickListener(v -> {
            Intent detailActivity = new Intent(context, DetailActivity.class);
            detailActivity.putExtra(MovieFragament.EXTRA_ID, favoriteList.get(position).getId());
            context.startActivity(detailActivity);
        });
    }

    @Override
    public int getItemCount() {
        return favoriteList == null ? 0 :favoriteList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView iv_poster, iv_overlay;
        MaterialCardView card_view;
        TextView tv_title, tv_release;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_poster   = itemView.findViewById(R.id.iv_movie_poster);
            card_view   = itemView.findViewById(R.id.card_list);
            tv_title    = itemView.findViewById(R.id.tv_movie_title);
            tv_release  = itemView.findViewById(R.id.tv_movie_release);
            iv_overlay  = itemView.findViewById(R.id.poster_overlay);
        }
    }

    // confirmation dialog box to delete an unit
    private void deleteItemFromList(View v, final int position) {

        AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());

        //builder.setTitle("Dlete ");
        builder.setMessage("Delete Item ?")
                .setCancelable(false)
                .setPositiveButton("CONFIRM", (dialog, id) -> {
                            favoriteList.remove(position);
                            notifyDataSetChanged();
                        }).setNegativeButton("CANCEL", (dialog, id) -> {
                });

        builder.show();

    }
}
